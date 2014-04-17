package edu.uoregon.secondlook

import grails.transaction.Transactional

@Transactional
class TranscriptTextProcessingService {

    String processTranscript(String inputTranscript) {
        if(inputTranscript.contains("[")){

            List<String> firstTokens = inputTranscript.tokenize("[")
            List<String> lastTokens = new ArrayList<>()

            firstTokens.each { token ->
                if(token.contains("]")){
                    lastTokens.add(token.substring(token.indexOf("]")+1))
                }
                else{
                    lastTokens.add(token)
                }
            }
            return (lastTokens.collect() as String).replaceAll(" +"," ").toUpperCase()
        }
        return inputTranscript.replaceAll(" +"," ").toUpperCase()
    }
}
