package edu.uoregon.secondlook;



import java.net.*;
import java.io.*;
import java.util.*;




/* ====================================================================== */
/**
 *   Currently only looks at passage1/cut1.
 *
 */
public class TWR {

	private static final long serialVersionUID = 6458681232131815351L;

	private static String[] intResults = {"02","05","06","07","08","09","11","12","13","15","16","19","23","24","25","33","38","45","47","48","52","55","56","57","61","62","66","68","70","73","76","77","79","81","87","89","91","92","94","97","100","101","102","104","113"};

  	//Instance variables **********************************************


	private String timingsFilepath = "../timings2.all.txt";

	private String transcriptsFilepath = "../transcripts.txt";


	private String[] passage1 = new String[0];
	private String[] passage2 = new String[0];
	private String[] passage3 = new String[0];


	private TreeMap<String, String> rawTimings = new TreeMap<String, String>();
	private TreeMap<String, String[]> studentTimings = new TreeMap<String, String[]>();

	private TreeMap<String, String> studentResults = new TreeMap<String, String>();


  	//Constructors ****************************************************








	//Methods ********************************************************

	/* ====================================================================== */
  	/**
  	 *
  	 */
  	public void parseTranscripts() {


		//Load the goal data and store in this object.
		try{
			BufferedReader input = new BufferedReader(new FileReader(new File(transcriptsFilepath)));

			String rawPassage1 = input.readLine().trim();
			rawPassage1 = rawPassage1.split("<s>")[1].trim().split("</s>")[0].trim();

			passage1 = rawPassage1.split(" ");


			String rawPassage2 = input.readLine().trim();
			rawPassage2 = rawPassage2.split("<s>")[1].trim().split("</s>")[0].trim();

			passage2 = rawPassage2.split(" ");


			String rawPassage3 = input.readLine().trim();
			rawPassage3 = rawPassage3.split("<s>")[1].trim().split("</s>")[0].trim();

			passage3 = rawPassage3.split(" ");

			input.close();

		}catch(Exception e){}


  	} //method: parseTranscripts


	/* ====================================================================== */
  	/**
  	 *
  	 */
  	public void parseTimings() {


		//timing file that contains output for all gina students.
		try{
			BufferedReader input = new BufferedReader(new FileReader(new File(timingsFilepath)));


			String line = input.readLine().trim();

			float offset = 60.0f;

			while( line != null ){

				String[] parts = line.split(" ");

				String wordsSoFar = rawTimings.get(parts[0]);

				//First time seeing this student so reset the offset.
				if( wordsSoFar == null ){
					wordsSoFar = "";
					offset = 60.0f;
				}

				if( Float.parseFloat(parts[2]) < (61.0+offset) && parts[4].charAt(0) != '<' && parts[4].charAt(0) != '-' ){

					if( offset > Float.parseFloat(parts[2]) ) offset = Float.parseFloat(parts[2]);

					wordsSoFar += parts[4]+" ";
					rawTimings.put(parts[0], wordsSoFar);
				}

				line = input.readLine().trim();
			}

			input.close();

		}catch(Exception e){}


		Iterator<String> keys = rawTimings.keySet().iterator();
		while( keys.hasNext() ){

			String key = keys.next();

			//Only look at students with good audio
			String studentid = key.split("\\.")[0];
			if( Arrays.asList(intResults).contains(studentid) ){

				String words = rawTimings.get(key);

				studentTimings.put(key, words.trim().split(" "));
			}

		}



  	} //method: parseTimings





	public void firstPass(){

/*
		//Passage 1
		Iterator<String> keys = studentTimings.keySet().iterator();
		while( keys.hasNext() ){

			String key = keys.next();

			if( key.indexOf("cut1") >= 0 ){

				String[] words = studentTimings.get(key);

				System.out.println("Student: "+key);

				int result = 0;
				//if( key.equals("56.cut1") )
					result = TWR.findBiggestIsland(words, passage1);

				System.out.println("");

				studentResults.put(key, ""+result);

			}
		}


*/
		//Passage 2
		Iterator<String> keys = studentTimings.keySet().iterator();
		while( keys.hasNext() ){

			String key = keys.next();

			if( key.indexOf("cut2") >= 0 ){

				String[] words = studentTimings.get(key);

				System.out.println("Student: "+key);

				int result = 0;
				//if( key.equals("89.cut2") )
					result = TWR.findBiggestIsland(words, passage2);
				System.out.println("");

				studentResults.put(key, ""+result);

			}
		}
/*
		//Passage 3
		Iterator<String> keys = studentTimings.keySet().iterator();
		while( keys.hasNext() ){

			String key = keys.next();

			if( key.indexOf("cut3") >= 0 ){

				String[] words = studentTimings.get(key);

				System.out.println("Student: "+key);

				int result = 0;
				//if( key.equals("08.cut3") )
					result = TWR.findBiggestIsland(words, passage3);
				System.out.println("");

				studentResults.put(key, ""+result);

			}
		}
*/

	}




	public static int findBiggestIsland(String[] words, String[] passage){

		int[][] islands = new int[words.length][];

		for(int i=words.length-1; i>=0; i--){

			//Collect all the islands

			islands[i] = islandAtPosition(words, passage, i);

//System.out.println(""+words[i]+" "+islands[i][0]+":"+islands[i][1]+" ");
		}


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

		//TODO: rerun the islandAtPosition with the positionInPassage info.

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

		positionInPassage += ((islands.length-1)-positionInStudent);

		//return ""+(positionInPassage+1)+" "+((islands.length-1)-positionInStudent);
		return (positionInPassage+1);

	}



	public static int[] islandAtPosition(String[] words, String[] passage, int position){

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





	public static void main(String[] args){

		TWR twr = new TWR();

		twr.parseTranscripts();

		twr.parseTimings();

		//twr.debugInputReading();

		twr.firstPass();

		twr.debugResults();




	}



	public void debugInputReading(){

		System.out.println("Master Transcripts:");
		for(int i=0; i<passage1.length; i++){
			System.out.print(passage1[i]+" ");
		}
		System.out.println("");

		for(int i=0; i<passage2.length; i++){
			System.out.print(passage2[i]+" ");
		}
		System.out.println("");

		for(int i=0; i<passage3.length; i++){
			System.out.print(passage3[i]+" ");
		}
		System.out.println("");


		System.out.println("Student Transcripts:");
		Iterator<String> keys = studentTimings.keySet().iterator();
		while( keys.hasNext() ){

			String key = keys.next();
			String[] words = studentTimings.get(key);

			System.out.println("Student: "+key);
			for(int i=0; i<words.length; i++){
				System.out.print(words[i]+" ");
			}
		}

	} //method: debugInputReading



	public void debugResults(){

		System.out.println("Student Results:");
		Iterator<String> keys = studentResults.keySet().iterator();
		while( keys.hasNext() ){

			String key = keys.next();
			String results = studentResults.get(key);

			System.out.println("Student: "+key+"  Results: "+results);
		}

	} //method: debugResults





	/* =============================================================================== */
	/**
	 *  Takes in a passage text, transcription with timings, and returns the TWR.
	 *  Calculate the total words read of what a person said in one minute based on
	 *  the given passage of what the person is supposed to be reading for one minute.
	 *
	 *  @param	passage	The passage text to check against.
	 *  @param	timingTranscription	Transcript of what the person said with timings.
	 *
	 *  @return	int	The TWR of what the student said in one minute.
	 *
	 */
	public static int findTWR(String passage, String timingTranscription){

		//Passage text is what we are matching against.
		String[] passageWords = passage.split(" ");

		//Hold all the words the student spoke in 60 second time interval.
		String[] studentWords = new String[0];


		//Parse the transcription file with timings.
		String[] timingLines = timingTranscription.split("\n");
		String wordsSoFar = "";
		float offset = 60.0f;
		for(int i=0; i<timingLines.length; i++){

			String line = timingLines[i];		//A line looks like: A-222 1 4.85 0.60 FAMILY

			String[] parts = line.split(" ");

			if( Float.parseFloat(parts[2]) < (61.0+offset) && parts[4].charAt(0) != '<' && parts[4].charAt(0) != '-' ){

				if( offset > Float.parseFloat(parts[2]) )
					offset = Float.parseFloat(parts[2]);

				wordsSoFar += parts[4]+" ";
			}

		}

		studentWords = wordsSoFar.trim().split(" ");


		//Now find the TWR
		System.out.println("Starting the island matching for TWR");
		int result = findBiggestIsland(studentWords, passageWords);
		System.out.println("Result: "+result);

		return result;

	}



} //class: TWR


