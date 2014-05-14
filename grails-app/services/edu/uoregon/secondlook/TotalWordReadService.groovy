package edu.uoregon.secondlook

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

//@CompileStatic
class TotalWordReadService {

//    private TreeMap<String, String> rawTimings = new TreeMap<String, String>();
//    private TreeMap<String, String[]> studentTimings = new TreeMap<String, String[]>();
//
//    private TreeMap<String, String> studentResults = new TreeMap<String, String>();
//    private static String[] intResults = ["02","05","06","07","08","09","11","12","13","15","16","19","23","24","25","33","38","45","47","48","52","55","56","57","61","62","66","68","70","73","76","77","79","81","87","89","91","92","94","97","100","101","102","104","113"]

    // TODO: use an actual method
    @Transactional
    Integer calculateTotalWordsReadFromComputerTranscript(ComputerTranscript computerTranscript){
        println "calc twr ${computerTranscript}"
        String transcript = computerTranscript.transcript
        println "transcript ${transcript.size()}"
//        Passage passage = Passage.executeQuery("select p from ComputerTranscript t join t.audioFile.passage p where t=:transcript",[transcript: computerTranscript],[max:1])?.get(0)
        Passage passage = computerTranscript.audioFile.passage
        String passageText = passage.text
//        passageText = passageText.replaceAll("\n"," ")
        passageText = passageText.replaceAll("\\s{2,}"," ")
        println "passage ${passageText}"
        if(transcript){
            //return TWR.findTWR(passageText,transcript)
			return ItemLevelMatching.matchItems(passageText, transcript).length
        }
        else{
            return -1
        }
    }


    // TODO: use an actual method
    @Transactional
    Integer calculateTotalWordsRead(Transcription transcription){
        println "calc twr ${transcription}" 
        String transcript = transcription.transcript
        println "transcript ${transcript.size()}"
        Passage passage = Passage.executeQuery("select p from Transcription t join t.passage p where t=:transcript",[transcript: transcription],[max:1])?.get(0)
        String passageText = passage.text
//        passageText = passageText.replaceAll("\n"," ")
        passageText = passageText.replaceAll("\\s{2,}"," ")
        println "passage ${passageText}"
        //return TWR.findTWR(passageText,transcript)
		return ItemLevelMatching.matchItems(passageText, transcript).length
    }

    String processTranscript(String inputHtmlTranscript) {


        Document doc = Jsoup.parse(inputHtmlTranscript);
        String inputTranscript = doc.text()


        if(inputTranscript.contains("[")){
//            println "contains ["

            List<String> firstTokens = inputTranscript.tokenize("[")
//            println "tokens ${firstTokens}"
            List<String> lastTokens = new ArrayList<>()

            firstTokens.each { token ->
                println "token: ${token}"
                if(token.contains("]")){
                    String tokenToAdd = token.substring(token.indexOf("]")+1)
//                    println "adding token '${tokenToAdd}'"
                    lastTokens.add(tokenToAdd)
                }
                else{
//                    println "ELSE adding token ${token}"
                    lastTokens.add(token)
                }
            }
//            println "lasttokens ${lastTokens}"
            String returnString = ""
            for(String aToken in lastTokens){
                returnString += aToken.replaceAll("\\p{Punct}","") +" "
            }
            returnString = returnString.replaceAll(" +"," ")
            return returnString.toUpperCase().trim()
        }
        else{
//            println "DOES NOTE contain ["
//            return inputTranscript.replaceAll(" +"," ").toUpperCase()
            return inputTranscript.replaceAll("\\p{Punct}","").replaceAll(" +"," ").toUpperCase().trim()
        }
    }
}
