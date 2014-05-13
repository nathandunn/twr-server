package edu.uoregon.secondlook;



import java.net.*;
import java.io.*;
import java.util.*;




/* ====================================================================== */
/**
 *   Currently only looks at passage1/cut1.
 *
 */
public class ItemLevelMatching {

	private static final long serialVersionUID = 6458661232131815351L;


  	//Instance variables **********************************************




  	//Constructors ****************************************************








	//Methods ********************************************************


	public static int[] islandMatching(String[] words, String[] passage){

		int[][] islands = new int[words.length][];

		for(int i=words.length-1; i>=0; i--){

			//Collect all the islands
			islands[i] = islandAtPosition(words, passage, i, islands);

			//Debugging
			//System.out.println(""+words[i]+" "+islands[i][0]+":"+islands[i][1]+" ");
		}



		//Mark all words in an island of size 3 or greater with the size of
		//the island to better group them.
		for(int i=words.length-1; i>=0; i--){

			if( islands[i][0] > 2 ){
				for(int j=i-1; j>i-islands[i][0]; j--){
					islands[j][0] = islands[i][0];
					islands[j][1] = islands[j+1][1]-1;
				}
				i = i-islands[i][0];
			}

		}

		//DEBUGGING
		//for(int i=words.length-1; i>=0; i--){
		//	System.out.println(""+words[i]+" "+islands[i][0]+":"+islands[i][1]+" ");
		//}




		//Run again now that we have some position info.
		//Helps re-align small islands that might have matched at wrong position.
		int limit = words.length-1;
		for(int i=words.length-1; i>=0; i--){


			//Re-evaluate the island's position if less than a size of 3.
			if( islands[i][0] < 3 )
				islands[i] = islandAtPositionWithLimit(words, passage, i, Math.min(i, limit));

			//Look at next island to try and find a lower limit.
			if( i > 0 && islands[i-1][0] > 0 )
				limit = islands[i-1][1];

			//Debugging
			//System.out.println(""+limit+" "+i);
			//System.out.println(""+words[i]+" "+islands[i][0]+":"+islands[i][1]+" ");
		}




		int[] matches = new int[passage.length];  //1 == correct, 0 == mistake, -1 == omission
		int position = 0;
		for(int i=0; i<matches.length; i++){

			boolean found = false;

			//Look for a correct (skips over insertions)
			for(int j=position; j<islands.length; j++){

				if( islands[j][0] > 0 && islands[j][1] == i ){
					found = true;
					position = j+1;
					matches[i] = 1;
				}
			}


			//Look for a mistakes
			if( !found && position < islands.length ){

				//System.out.println( "Position: "+position);
				if( islands[position][0] == 0 || (islands[position][0] == 1 && islands[position][1]-4 > i ) || (islands[position][0] == 1 && islands[position][1]+6 < i ) ){
						found = true;
						position++;
						matches[i] = 0;
				}
			}

			//Must have been an omission
			if( !found )
				matches[i] = -1;

		}



		//DEUGGING
		//for(int i=0; i<150; i++){
		//	System.out.println(""+i+" "+matches[i]);
		//}





		//Now find the TWR

		//Find the island that is closest to end of passage.
		int positionInPassage = 0;
		int positionInStudent = 0;
		boolean foundBigOne = false;
		for(int i=islands.length-1; i>=0; i--){

			if( islands[i][0] >= 3 && islands[i][1] > positionInPassage && i > positionInStudent ){
				positionInPassage = islands[i][1];
				positionInStudent = i;
			}

			if( islands[i][0] >= 5 && islands[i][1] > positionInPassage ){
				positionInPassage = islands[i][1];
				positionInStudent = i;
			}

			if( !foundBigOne && islands[i][0] >= 10 && (i+6 > positionInStudent) ){
				positionInPassage = islands[i][1];
				positionInStudent = i;
				foundBigOne = true;
			}

		}


		//Do some more checks on biggestPosition (maybe find smaller islands or look at words.length or look at gaps.
		//System.out.println("SO FAR: "+ positionInPassage+" "+positionInStudent);

		//Find islands of 2 that aren't too far away from other island.
		for(int i=islands.length-1; i>=positionInStudent; i--){

			if( islands[i][0] == 2 && islands[i][1] > positionInPassage && islands[i][1]-10 <= positionInPassage ){
				positionInPassage = islands[i][1];
				positionInStudent = i;
			}

		}

		//System.out.println("SO FAR: "+ positionInPassage+" "+positionInStudent);

		//Find islands of 1 that aren't too far away from other island.
		for(int i=islands.length-1; i>=positionInStudent; i--){

			if( islands[i][0] == 1 && islands[i][1] > positionInPassage && islands[i][1]-6 <= positionInPassage ){
				positionInPassage = islands[i][1];
				positionInStudent = i;
			}

		}


		//Add any leftover words to the position
		positionInPassage += ((islands.length-1)-positionInStudent)+1;


		int[] markings = new int[positionInPassage];
		for(int i=0; i<positionInPassage; i++){
			markings[i] = matches[i];
		}

		return markings;

	}



	public static int[] islandAtPosition(String[] words, String[] passage, int position, int[][] islands){

		int[] biggestMatch = {0,0};

		int tempSize = 0;
		int tempOffset = 0;
		for(int i=passage.length-1; i>=0; i--){

			if( position-tempOffset >= 0
				&& passage[i].equals(words[position-tempOffset])
				&& i < (200 - (words.length-position)) ){

				tempOffset++;
				tempSize++;

			}else{

				if( tempSize > biggestMatch[0] ){
					biggestMatch[0] = tempSize;
					biggestMatch[1] = i+tempOffset;
				}

				tempSize = 0;
				tempOffset = 0;
			}
		}

		return biggestMatch;
	}


	public static int[] islandAtPositionWithLimit(String[] words, String[] passage, int position, int limit){

		int[] biggestMatch = {0,0};

		int tempSize = 0;
		int tempOffset = 0;
		for(int i=passage.length-1; i>=0; i--){

			if( position-tempOffset >= 0
				&& passage[i].equals(words[position-tempOffset])
				&& i < (200 - (words.length-position)) ){

				tempOffset++;
				tempSize++;

			}else{

				if( tempSize > 0 && tempSize >= biggestMatch[0] && (i+tempOffset) > limit ){
					biggestMatch[0] = tempSize;
					biggestMatch[1] = i+tempOffset;
				}

				tempSize = 0;
				tempOffset = 0;
			}
		}

		return biggestMatch;
	}



	/* =============================================================================== */
	/**
	 *  Takes in a passage text, transcription with timings, and returns the TWR.
	 *  Calculate the total words read of what a person said in one minute based on
	 *  the given passage of what the person is supposed to be reading for one minute.
	 *
	 *  @param	passage	The passage text to check against.
	 *  @param	timingTranscription	Transcript of what the person said with timings.
	 *
	 *  @return	int[]	Array of length TWR. 1 == correct, 0 == error, -1 == omission.
	 *
	 */
	public static int[] matchItems(String passage, String timingTranscription){

		//Passage text is what we are matching against.
		String[] passageWords = passage.trim().split(" ");

		//Hold all the words the student spoke in 60 second time interval.
		String[] studentWords = new String[0];


		//Parse the transcription file with timings.
		String[] timingLines = timingTranscription.trim().split("\n");
		String wordsSoFar = "";
		float offset = 60.0f;
		for(int i=0; i<timingLines.length; i++){

			String line = timingLines[i].trim();		//A line looks like: A-222 1 4.85 0.60 FAMILY

			String[] parts = line.split(" ");

			if( Float.parseFloat(parts[2]) < (60.5+offset) && parts[4].charAt(0) != '<' && parts[4].charAt(0) != '-' ){

				if( offset > Float.parseFloat(parts[2]) )
					offset = Float.parseFloat(parts[2]);

				wordsSoFar += parts[4].trim()+" ";
			}

		}

		studentWords = wordsSoFar.trim().split(" ");


		//Now find the markings
		System.out.println("Starting the island matching...");


		int[] result = islandMatching(studentWords, passageWords);

		for(int i=0; i<result.length; i++){
			System.out.println(""+i+" "+result[i]);
		}
		System.out.println("Result: "+result.length);


		return result;

	}





	public static void main(String[] args){


		ItemLevelMatching.matchItems("YOU ARE STANDING ON A SANDY WHITE BEACH ON THE GULF OF MEXICO LOOKING OUT OVER THE WATER YOU NOTICE A HUGE BIRD WITH LONG NARROW WINGS GLIDING OVER THE OCEAN SUDDENLY THE BIRD TUCKS ITS WINGS TO ITS SIDE AND DROPS A HUNDRED FEET STRAIGHT DOWN INTO THE WATER BEFORE YOU CAN BLINK IT SHOOTS OUT OF THE SEA WITH A LARGE FISH IN ITS CLAWS AND SOARS UPWARD INTO THE SKY YOU HAVE JUST SEEN AN OSPREY CATCH ITS BREAKFAST UNFORTUNATELY THE SIGHT YOU HAVE JUST SEEN HAS BECOME QUITE RARE THERE ARE FAR FEWER OSPREYS TODAY THAN THERE ONCE WERE ONE REASON FOR THIS DECLINE IS THAT PEOPLE HAVE MOVED INTO PLACES THAT WERE ONCE OSPREY NESTING AREAS AS MORE AND MORE PEOPLE SETTLED IN THESE AREAS THEY CUT DOWN TREES TO BUILD HOUSES SHOPS AND BUILDINGS THE GRADUAL ENCROACH OF CIVILIZATION HAS LEFT OSPREYS WITH FEWER PLACES TO BUILD THEIR NESTS IN ADDITION PEOPLE BEGAN TO USE FERTILIZERS AND OTHER CHEMICALS THAT DRAINED INTO THE WATER SUPPLY THIS POISONED THE FISH THAT OSPREYS FEED ON FINALLY PEOPLE SOMETIMES FRIGHTENED THE OSPREYS AWAY FROM THEIR NESTS LEAVING THEIR EGGS TO BE DESTROYED BY THE HOT SUN FORTUNATELY HELP IS ON THE WAY MANY AREAS WHERE OSPREYS BUILD THEIR NESTS ARE NOW PROTECTED ONE OF THESE IS THE GULF ISLANDS NATIONAL SEASHORE A PARK THAT STRETCHES FOR MORE THAN ONE HUNDRED MILES ALONG THE GULF OF MEXICO OSPREY NESTS CAN BE FOUND ON ALMOST ALL OF THE ISLANDS IN THE PARK TO PROTECT THE BIRDS PEOPLE ARE NOT ALLOWED IN MANY PARTS OF THE PARK AS A RESULT THE NUMBER OF OSPREYS ON THE ISLANDS IS SLOWLY INCREASING ALSO MANY COMPANIES AND WILDLIFE CLUBS HAVE SET UP MAN MADE NESTING PLATFORMS IN PLACES WHERE OSPREYS LIVE THESE PROVIDE OSPREYS WITH SAFE STURDY NESTING SITES OUT OF THE REACH OF PEOPLE BECAUSE OF THE HARD WORK OF CONCERNED PEOPLE THINGS ARE LOOKING UP FOR OSPREYS SOON WATCHING AN OSPREY SWOOP OUT OF THE SKY TO CATCH ITS MORNING MEAL MIGHT NOT BE SUCH A RARE SIGHT AFTER ALL",
									 "decodable 1 0.00 0.38 STANDING\ndecodable 1 0.38 0.22 ON\ndecodable 1 0.60 0.16 A\ndecodable 1 0.76 0.38 SANDY\ndecodable 1 1.14 0.40 WHITE\ndecodable 1 1.54 0.30 BEACH\ndecodable 1 1.95 0.29 ON\ndecodable 1 2.24 0.10 THE\ndecodable 1 2.34 0.37 GULF\ndecodable 1 2.87 0.19 OF\ndecodable 1 3.06 0.62 MEXICO\ndecodable 1 4.12 0.51 LOOKING\ndecodable 1 4.73 0.38 OUT\ndecodable 1 5.41 0.36 OVER\ndecodable 1 5.77 0.17 THE\ndecodable 1 5.94 0.42 WATER\ndecodable 1 6.75 0.15 YOU\ndecodable 1 6.90 0.44 NOTICE\ndecodable 1 7.34 0.18 A\ndecodable 1 7.52 0.34 HUGE\ndecodable 1 7.86 0.32 BIRD\ndecodable 1 8.18 0.25 WITH\ndecodable 1 8.58 0.56 LONG\ndecodable 1 9.14 0.29 NARROW\ndecodable 1 9.43 0.68 WINGS\ndecodable 1 10.52 0.74 GLIDIN\ndecodable 1 11.26 0.30 OVER\ndecodable 1 11.56 0.09 THE\ndecodable 1 11.77 0.43 OCEAN\ndecodable 1 12.50 0.45 SUDDENLY\ndecodable 1 12.95 0.13 THE\ndecodable 1 13.08 0.38 BIRD\ndecodable 1 13.65 0.56 TUCKS\ndecodable 1 14.60 0.38 ITS\ndecodable 1 14.98 0.53 WINGS\ndecodable 1 15.51 0.61 INTO\ndecodable 1 16.18 0.26 ITS\ndecodable 1 16.44 0.63 SIDES\ndecodable 1 17.15 0.26 AND\ndecodable 1 17.41 0.24 MORE\ndecodable 1 17.83 0.49 THAN\ndecodable 1 18.49 0.07 <UNK>\ndecodable 1 18.56 0.34 HUNDRED\ndecodable 1 18.90 0.38 FEET\ndecodable 1 19.28 0.41 STRAIGHT\ndecodable 1 19.69 0.43 DOWN\ndecodable 1 20.21 0.57 INTO\ndecodable 1 20.78 0.12 THE\ndecodable 1 20.90 0.45 WATER\ndecodable 1 21.92 0.45 BEFORE\ndecodable 1 22.37 0.17 YOU\ndecodable 1 22.54 0.30 CAN\ndecodable 1 23.04 0.36 BLINK\ndecodable 1 23.78 0.30 IT\ndecodable 1 24.08 0.76 SHOOTS\ndecodable 1 25.22 0.35 OUT\ndecodable 1 25.57 0.29 TO\ndecodable 1 25.93 0.52 SEA\ndecodable 1 26.78 0.33 WITH\ndecodable 1 27.11 0.22 A\ndecodable 1 27.33 0.23 LARGE\ndecodable 1 27.56 0.39 FISH\ndecodable 1 27.95 0.37 IN\ndecodable 1 28.42 0.52 ITS\ndecodable 1 29.14 0.53 CLAWS\ndecodable 1 29.71 0.32 AND\ndecodable 1 30.03 0.49 SOARS\ndecodable 1 30.61 0.70 UPWARD\ndecodable 1 31.75 0.27 INTO\ndecodable 1 32.02 0.10 THE\ndecodable 1 32.12 0.38 SKY\ndecodable 1 33.32 0.19 YOU\ndecodable 1 33.51 0.35 HAVE\ndecodable 1 33.86 0.27 JUST\ndecodable 1 34.13 0.49 SEEN\ndecodable 1 34.62 0.36 AN\ndecodable 1 35.07 0.60 OSPREY\ndecodable 1 36.09 0.32 CATCH\ndecodable 1 36.45 0.27 ITS\ndecodable 1 36.77 0.65 BREAKFAST\ndecodable 1 37.92 0.90 UNFORTUNATELY\ndecodable 1 38.82 0.17 THE\ndecodable 1 38.99 0.72 SIGHT\ndecodable 1 40.19 0.27 YOU\ndecodable 1 40.46 0.38 HAVE\ndecodable 1 40.84 0.25 JUST\ndecodable 1 41.09 0.47 SEEN\ndecodable 1 41.56 0.33 HAS\ndecodable 1 41.89 0.48 BECOME\ndecodable 1 42.37 0.52 QUITE\ndecodable 1 42.89 0.41 RARE\ndecodable 1 44.25 0.52 THERE\ndecodable 1 44.77 0.52 ARE\ndecodable 1 45.72 0.48 FAR\ndecodable 1 46.20 0.73 FEWER\ndecodable 1 47.07 0.57 OSPREYS\ndecodable 1 47.64 0.49 TODAY\ndecodable 1 48.13 0.50 THAN\ndecodable 1 48.90 0.34 THERE\ndecodable 1 49.43 0.41 ONCE\ndecodable 1 49.84 0.27 WERE\ndecodable 1 50.59 0.46 ONE\ndecodable 1 51.05 0.60 REASON\ndecodable 1 51.65 0.37 FOR\ndecodable 1 52.02 0.44 THIS\ndecodable 1 52.46 0.84 DECLINE\ndecodable 1 53.30 0.32 IS\ndecodable 1 53.62 0.40 THAT\ndecodable 1 54.02 0.39 PEOPLE\ndecodable 1 54.86 0.30 HAVE\ndecodable 1 55.16 0.34 NOT\ndecodable 1 55.62 0.45 MOVED\ndecodable 1 56.35 0.37 INTO\ndecodable 1 56.72 0.73 PLACES\ndecodable 1 57.45 0.38 THAT\ndecodable 1 58.25 0.35 WERE\ndecodable 1 58.60 0.61 ONCE\ndecodable 1 59.25 0.92 OSPREYS\ndecodable 1 60.17 1.15 <UNK>");

		//ItemLevelMatching.matchItems("YOU ARE STANDING ON A SANDY WHITE BEACH ON THE GULF OF MEXICO LOOKING OUT OVER THE WATER YOU NOTICE A HUGE BIRD WITH LONG NARROW WINGS GLIDING OVER THE OCEAN SUDDENLY THE BIRD TUCKS ITS WINGS TO ITS SIDE AND DROPS A HUNDRED FEET STRAIGHT DOWN INTO THE WATER BEFORE YOU CAN BLINK IT SHOOTS OUT OF THE SEA WITH A LARGE FISH IN ITS CLAWS AND SOARS UPWARD INTO THE SKY YOU HAVE JUST SEEN AN OSPREY CATCH ITS BREAKFAST UNFORTUNATELY THE SIGHT YOU HAVE JUST SEEN HAS BECOME QUITE RARE THERE ARE FAR FEWER OSPREYS TODAY THAN THERE ONCE WERE ONE REASON FOR THIS DECLINE IS THAT PEOPLE HAVE MOVED INTO PLACES THAT WERE ONCE OSPREY NESTING AREAS AS MORE AND MORE PEOPLE SETTLED IN THESE AREAS THEY CUT DOWN TREES TO BUILD HOUSES SHOPS AND BUILDINGS THE GRADUAL ENCROACH OF CIVILIZATION HAS LEFT OSPREYS WITH FEWER PLACES TO BUILD THEIR NESTS IN ADDITION PEOPLE BEGAN TO USE FERTILIZERS AND OTHER CHEMICALS THAT DRAINED INTO THE WATER SUPPLY THIS POISONED THE FISH THAT OSPREYS FEED ON FINALLY PEOPLE SOMETIMES FRIGHTENED THE OSPREYS AWAY FROM THEIR NESTS LEAVING THEIR EGGS TO BE DESTROYED BY THE HOT SUN FORTUNATELY HELP IS ON THE WAY MANY AREAS WHERE OSPREYS BUILD THEIR NESTS ARE NOW PROTECTED ONE OF THESE IS THE GULF ISLANDS NATIONAL SEASHORE A PARK THAT STRETCHES FOR MORE THAN ONE HUNDRED MILES ALONG THE GULF OF MEXICO OSPREY NESTS CAN BE FOUND ON ALMOST ALL OF THE ISLANDS IN THE PARK TO PROTECT THE BIRDS PEOPLE ARE NOT ALLOWED IN MANY PARTS OF THE PARK AS A RESULT THE NUMBER OF OSPREYS ON THE ISLANDS IS SLOWLY INCREASING ALSO MANY COMPANIES AND WILDLIFE CLUBS HAVE SET UP MAN MADE NESTING PLATFORMS IN PLACES WHERE OSPREYS LIVE THESE PROVIDE OSPREYS WITH SAFE STURDY NESTING SITES OUT OF THE REACH OF PEOPLE BECAUSE OF THE HARD WORK OF CONCERNED PEOPLE THINGS ARE LOOKING UP FOR OSPREYS SOON WATCHING AN OSPREY SWOOP OUT OF THE SKY TO CATCH ITS MORNING MEAL MIGHT NOT BE SUCH A RARE SIGHT AFTER ALL",
		//							 "decodable 1 0.00 0.49 STANDING\ndecodable 1 0.49 1.14 UNEASINESS \ndecodable 1 1.92 0.57 SANDY \ndecodable 1 2.98 0.30 WHITE \ndecodable 1 3.28 0.42 BEACH \ndecodable 1 3.70 0.36 ON \ndecodable 1 4.06 0.08 THE \ndecodable 1 4.19 0.37 GULF \ndecodable 1 4.79 0.13 OF \ndecodable 1 4.92 0.66 MEXICO \ndecodable 1 6.08 0.50 LOOKING \ndecodable 1 6.67 0.26 OUT \ndecodable 1 6.93 0.26 OVER \ndecodable 1 7.19 0.12 THE \ndecodable 1 7.31 0.35 WATER \ndecodable 1 7.66 0.29 YOU \ndecodable 1 7.95 0.69 NOTICE \ndecodable 1 8.64 0.13 A \ndecodable 1 8.77 0.40 HUGE \ndecodable 1 9.17 0.36 BIRD \ndecodable 1 9.79 0.19 WITH \ndecodable 1 9.98 0.35 LONG \ndecodable 1 10.33 0.28 NARROW \ndecodable 1 10.61 0.47 WINGS \ndecodable 1 11.15 0.50 GLIDING \ndecodable 1 11.65 0.22 OVER \ndecodable 1 11.87 0.09 THE \ndecodable 1 11.96 0.44 OCEAN \ndecodable 1 12.40 0.53 SUDDENLY \ndecodable 1 13.16 0.15 THE \ndecodable 1 13.34 0.28 BIRD \ndecodable 1 13.62 0.43 TUCKS \ndecodable 1 14.05 0.26 ITS \ndecodable 1 14.31 0.39 WINGS \ndecodable 1 15.09 0.42 INTO \ndecodable 1 15.57 0.36 ITS \ndecodable 1 15.93 0.43 SIDE \ndecodable 1 16.68 0.14 AND \ndecodable 1 16.82 0.36 DROPS \ndecodable 1 17.18 0.09 A \ndecodable 1 17.27 0.50 HUNDRED \ndecodable 1 17.77 0.36 FEET \ndecodable 1 18.13 0.44 STRAIGHT \ndecodable 1 18.57 0.27 DOWN \ndecodable 1 19.14 0.20 INTO \ndecodable 1 19.34 0.09 THE \ndecodable 1 19.43 0.31 WATER \ndecodable 1 19.85 0.36 BEFORE \ndecodable 1 20.21 0.22 YOU \ndecodable 1 20.43 0.27 CAN \ndecodable 1 20.70 0.27 BLINK \ndecodable 1 21.28 0.17 IT \ndecodable 1 21.45 0.45 SHOOTS \ndecodable 1 21.90 0.33 OUT \ndecodable 1 22.23 0.23 OF \ndecodable 1 22.46 0.13 THE \ndecodable 1 22.59 0.28 SEA \ndecodable 1 22.87 0.56 WITH \ndecodable 1 23.43 0.11 A \ndecodable 1 23.54 0.44 LARGE \ndecodable 1 23.98 0.40 FISH \ndecodable 1 24.38 0.29 IN \ndecodable 1 24.67 0.30 ITS \ndecodable 1 24.97 0.35 CLAWS \ndecodable 1 25.32 0.25 AND \ndecodable 1 25.57 0.55 SOARS \ndecodable 1 26.44 0.43 UPWARD \ndecodable 1 26.87 0.28 INTO \ndecodable 1 27.15 0.08 THE \ndecodable 1 27.23 0.30 SKY \ndecodable 1 27.92 0.11 YOU \ndecodable 1 28.03 0.30 HAVE \ndecodable 1 28.33 0.24 JUST \ndecodable 1 28.57 0.35 SEEN \ndecodable 1 28.92 0.24 AN \ndecodable 1 29.23 0.58 SONGS \ndecodable 1 30.29 0.42 GREAT \ndecodable 1 31.23 0.49 CATCH \ndecodable 1 31.78 0.33 ITS \ndecodable 1 32.11 0.67 BREAKFAST \ndecodable 1 33.16 1.26 UNFORTUNATELY \ndecodable 1 34.71 0.12 THE \ndecodable 1 34.83 0.40 SIGHT \ndecodable 1 35.23 0.21 YOU \ndecodable 1 35.44 0.32 HAVE \ndecodable 1 36.14 0.24 JUST \ndecodable 1 36.38 0.45 SEEN \ndecodable 1 37.16 0.28 HAS \ndecodable 1 37.44 0.46 BECOME \ndecodable 1 38.01 0.12 HAS \ndecodable 1 38.56 0.56 BECOME \ndecodable 1 39.31 0.47 QUITE \ndecodable 1 39.84 0.29 RARE \ndecodable 1 40.61 0.29 THERE \ndecodable 1 40.90 0.31 ARE \ndecodable 1 41.53 0.50 FAR \ndecodable 1 42.03 0.45 FEWER \ndecodable 1 43.03 1.45 OSPREYS \ndecodable 1 44.73 0.44 TODAY \ndecodable 1 45.17 0.40 THAN \ndecodable 1 45.57 0.25 THERE \ndecodable 1 46.29 0.40 ONCE \ndecodable 1 46.69 0.24 WERE \ndecodable 1 47.38 0.22 ONE \ndecodable 1 47.60 0.43 REASON \ndecodable 1 48.03 0.21 FOR \ndecodable 1 48.24 0.34 THIS \ndecodable 1 49.00 0.79 DECLINE \ndecodable 1 50.52 0.29 IS \ndecodable 1 50.81 0.26 THAT \ndecodable 1 51.15 0.38 PEOPLE \ndecodable 1 51.53 0.36 HAVE \ndecodable 1 51.89 0.39 MOVED \ndecodable 1 52.28 0.68 INTO \ndecodable 1 53.06 0.47 PLACES \ndecodable 1 53.84 0.20 THAT \ndecodable 1 54.04 0.28 WERE \ndecodable 1 54.32 0.48 ONCE \ndecodable 1 54.80 0.72 OSPREY \ndecodable 1 55.91 0.63 NESTING \ndecodable 1 56.54 0.46 AREAS \ndecodable 1 57.31 0.21 AS \ndecodable 1 57.52 0.46 MORE \ndecodable 1 58.01 0.23 AND \ndecodable 1 58.24 0.24 MORE \ndecodable 1 58.57 0.35 PEOPLE \ndecodable 1 58.92 0.56 SETTLED \ndecodable 1 59.48 0.52 IN \ndecodable 1 60.00 0.66 STOP \ndecodable 1 61.31 1.68 HIS");

		//ItemLevelMatching.matchItems("YOU ARE STANDING ON A SANDY WHITE BEACH ON THE GULF OF MEXICO LOOKING OUT OVER THE WATER YOU NOTICE A HUGE BIRD WITH LONG NARROW WINGS GLIDING OVER THE OCEAN SUDDENLY THE BIRD TUCKS ITS WINGS TO ITS SIDE AND DROPS A HUNDRED FEET STRAIGHT DOWN INTO THE WATER BEFORE YOU CAN BLINK IT SHOOTS OUT OF THE SEA WITH A LARGE FISH IN ITS CLAWS AND SOARS UPWARD INTO THE SKY YOU HAVE JUST SEEN AN OSPREY CATCH ITS BREAKFAST UNFORTUNATELY THE SIGHT YOU HAVE JUST SEEN HAS BECOME QUITE RARE THERE ARE FAR FEWER OSPREYS TODAY THAN THERE ONCE WERE ONE REASON FOR THIS DECLINE IS THAT PEOPLE HAVE MOVED INTO PLACES THAT WERE ONCE OSPREY NESTING AREAS AS MORE AND MORE PEOPLE SETTLED IN THESE AREAS THEY CUT DOWN TREES TO BUILD HOUSES SHOPS AND BUILDINGS THE GRADUAL ENCROACH OF CIVILIZATION HAS LEFT OSPREYS WITH FEWER PLACES TO BUILD THEIR NESTS IN ADDITION PEOPLE BEGAN TO USE FERTILIZERS AND OTHER CHEMICALS THAT DRAINED INTO THE WATER SUPPLY THIS POISONED THE FISH THAT OSPREYS FEED ON FINALLY PEOPLE SOMETIMES FRIGHTENED THE OSPREYS AWAY FROM THEIR NESTS LEAVING THEIR EGGS TO BE DESTROYED BY THE HOT SUN FORTUNATELY HELP IS ON THE WAY MANY AREAS WHERE OSPREYS BUILD THEIR NESTS ARE NOW PROTECTED ONE OF THESE IS THE GULF ISLANDS NATIONAL SEASHORE A PARK THAT STRETCHES FOR MORE THAN ONE HUNDRED MILES ALONG THE GULF OF MEXICO OSPREY NESTS CAN BE FOUND ON ALMOST ALL OF THE ISLANDS IN THE PARK TO PROTECT THE BIRDS PEOPLE ARE NOT ALLOWED IN MANY PARTS OF THE PARK AS A RESULT THE NUMBER OF OSPREYS ON THE ISLANDS IS SLOWLY INCREASING ALSO MANY COMPANIES AND WILDLIFE CLUBS HAVE SET UP MAN MADE NESTING PLATFORMS IN PLACES WHERE OSPREYS LIVE THESE PROVIDE OSPREYS WITH SAFE STURDY NESTING SITES OUT OF THE REACH OF PEOPLE BECAUSE OF THE HARD WORK OF CONCERNED PEOPLE THINGS ARE LOOKING UP FOR OSPREYS SOON WATCHING AN OSPREY SWOOP OUT OF THE SKY TO CATCH ITS MORNING MEAL MIGHT NOT BE SUCH A RARE SIGHT AFTER ALL",
		//							 "input 1 0.00 0.29 ON \ninput 1 0.29 0.09 A \ninput 1 0.38 0.67 SANDY \ninput 1 1.43 0.34 WHITE \ninput 1 1.77 0.37 BEACH \ninput 1 2.14 0.16 ON \ninput 1 2.43 0.12 THE \ninput 1 2.55 0.52 GULF \ninput 1 3.24 0.32 OF \ninput 1 3.56 0.67 MEXICO \ninput 1 4.38 0.45 LOOKING \ninput 1 4.83 0.41 OUT \ninput 1 5.47 0.42 OVER \ninput 1 5.89 0.14 THE \ninput 1 6.03 0.25 WATER \ninput 1 6.86 0.44 BEFORE \ninput 1 7.58 0.14 YOU \ninput 1 7.72 0.48 NOTICE \ninput 1 8.20 0.09 A \ninput 1 8.29 0.45 HUGE \ninput 1 8.74 0.32 BIRD \ninput 1 9.06 1.07 WITH \ninput 1 10.13 0.34 LONG \ninput 1 10.47 0.56 NARROW \ninput 1 11.03 0.43 WINGS \ninput 1 11.74 0.39 WE \ninput 1 12.13 0.11 WERE \ninput 1 12.63 0.59 LEAVING \ninput 1 13.22 0.36 OVER \ninput 1 13.63 0.11 THE \ninput 1 13.74 0.51 OCEAN \ninput 1 14.87 0.59 SUDDENLY \ninput 1 15.46 0.32 THE \ninput 1 15.78 0.50 BIRD \ninput 1 16.50 0.98 THIS \ninput 1 17.48 0.73 <UNK> \ninput 1 18.21 0.40 TUCKS \ninput 1 18.61 0.23 ITS \ninput 1 18.84 0.52 WINGS \ninput 1 19.55 0.21 TO \ninput 1 19.76 0.82 USE \ninput 1 20.77 1.30 DECIDED \ninput 1 22.07 0.28 TO \ninput 1 22.35 0.45 FACE \ninput 1 22.80 0.26 TWO \ninput 1 23.32 0.46 HUNDRED \ninput 1 23.78 0.36 FEET \ninput 1 24.45 1.16 STRAIGHT \ninput 1 25.82 0.30 DOWN \ninput 1 26.12 0.83 INTO \ninput 1 26.95 0.16 THE \ninput 1 27.11 0.32 WATER \ninput 1 27.77 0.43 BEFORE \ninput 1 28.20 0.37 YOU \ninput 1 28.57 0.46 CAN \ninput 1 29.30 0.37 BLINK \ninput 1 29.67 0.43 IT \ninput 1 30.10 0.60 SHOOTS \ninput 1 30.70 0.30 OUT \ninput 1 31.51 0.22 OF \ninput 1 31.73 0.12 THE \ninput 1 31.85 0.40 WALL \ninput 1 32.85 0.14 THE \ninput 1 32.99 0.82 MUSEUM \ninput 1 34.49 0.62 WITH \ninput 1 35.11 0.30 A \ninput 1 35.69 0.39 LARGE \ninput 1 36.08 0.43 FISH \ninput 1 36.51 0.30 IN \ninput 1 36.81 0.34 ITS \ninput 1 37.15 0.50 CLAWS \ninput 1 37.93 0.36 AND \ninput 1 38.70 0.40 SOARS \ninput 1 39.82 0.49 IS \ninput 1 40.31 0.61 AROUND \ninput 1 41.29 0.16 THE \ninput 1 41.64 0.48 WORLD \ninput 1 42.12 0.85 INTO \ninput 1 42.97 0.19 THE \ninput 1 43.16 0.31 SKY \ninput 1 43.80 0.25 YOU \ninput 1 44.05 0.35 HAVE \ninput 1 44.73 0.60 JUST \ninput 1 45.89 0.57 SEEN \ninput 1 46.46 0.34 AN \ninput 1 47.62 0.52 OSPREY \ninput 1 48.14 1.27 SIPPED \ninput 1 49.41 0.42 HIS \ninput 1 49.83 0.22 IN \ninput 1 50.15 0.92 CIRCLES \ninput 1 51.81 0.18 AND \ninput 1 51.99 0.50 FORESTS \ninput 1 52.49 0.85 VALERIAN \ninput 1 53.80 0.31 THE \ninput 1 54.11 0.56 SIGHT \ninput 1 55.40 0.44 YOU \ninput 1 56.72 0.28 HAVE \ninput 1 57.00 0.28 JUST \ninput 1 57.28 0.46 SEEN \ninput 1 57.74 0.57 HAS \ninput 1 58.75 0.39 HAD \ninput 1 59.70 0.16 TO \ninput 1 60.09 0.85 CONVINCE ");


	}

} //class: ItemLevelMatching


