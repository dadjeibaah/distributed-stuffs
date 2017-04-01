package com.onecreation.common;

import java.net.Socket;

/**
 * Created by dennis on 11/8/16.
 */
public abstract class Communication {
    private IncomingChannel incomingChannel;
    private OutgoingChannel outgoingChannel;
    private Socket connection;

    public IncomingChannel getIncomingChannel() {
        return incomingChannel;
    }

    public OutgoingChannel getOutgoingChannel(){
        return outgoingChannel;
    }
}
