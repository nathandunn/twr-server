package edu.uoregon.secondlook

/**
 * Created with IntelliJ IDEA.
 * User: NathanDunn
 * Date: 10/14/13
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */
public enum ProcessingStatus {


    DELIVERED, // delivered to queue, but not started
    PROCESSING, // processing in the queue
    FINISHED,  // finished processing in the queue, success
    ERROR // finished processing in the queue, Error

}