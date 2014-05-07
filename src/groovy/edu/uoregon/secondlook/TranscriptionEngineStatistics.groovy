package edu.uoregon.secondlook

/**
 * Created by NathanDunn on 5/7/14.
 */
class TranscriptionEngineStatistics {
    float computerTwr
    Integer computerCount
    float humanTwr
    Integer humanCount

    def getCalculateTwrError() {
        Math.abs(computerTwr - humanTwr) as Integer
//        return 7
    }
}
