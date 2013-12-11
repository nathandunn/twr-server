package edu.uoregon.secondlook

/**
 * Created with IntelliJ IDEA.
 * User: NathanDunn
 * Date: 10/14/13
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */
public enum TranscriptionStatus {

    RECEIVED, // received, not yet processed
    SUBMITTED, // submitted to queue
    ERROR, // finished, error
    FINISHED, // finished, ok
    CALLBACK_OK, // fnished ok, callback ok
    CALLBACK_ERROR, // fnished ok, callback error

}