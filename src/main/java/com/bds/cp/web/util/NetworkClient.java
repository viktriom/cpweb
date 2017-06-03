package com.bds.cp.web.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

/**
 * Created by Vivek Tripathi on 06/12/16.
 */

public class NetworkClient {

    private int port;
    private String serverIPAddr;
    private Socket client = null;
    private DataOutputStream out = null;
    private DataInputStream in = null;
    
    private static Logger log = Logger.getLogger(NetworkClient.class);

    public NetworkClient(String serverIPAddr, int port){
        this.port = port;
        this.serverIPAddr = serverIPAddr;
    }

    public void connect() {
        if(null == serverIPAddr){
            log.info("The server address provided is incomplete : serverIpAddr = " + serverIPAddr + "and port = " + String.valueOf(port));
            log.info("The server address provided is incomplete : serverIpAddr = " + serverIPAddr + "and port = " + String.valueOf(port));
            return;
        }
        log.info("Attempting to connect to : " + serverIPAddr + ":" + String.valueOf(port));
        try {
            client = new Socket(serverIPAddr, port);
            log.info("Successfully connectd to : " + serverIPAddr + ":" + String.valueOf(port));
            OutputStream oStr = client.getOutputStream();
            out = new DataOutputStream(oStr);
        } catch (IOException e) {
            log.error("There was an error while connecting to the server : " + serverIPAddr + ":" + String.valueOf(port));
            log.error("There was an error while connecting to the server : " + serverIPAddr + ":" + String.valueOf(port));
            client = null;
            out = null;
            in = null;
            e.printStackTrace();
        }
    }

    public String getResponseFromServer(){
        if(client == null){
            log.info("Cannot get response from the server, Client Socket not connected.");
            log.info("Cannot get response from the server, Client Socket not connected.");
            return null;
        }
        String message = null;
        try {
            InputStream inStr = client.getInputStream();
            in = new DataInputStream(inStr);
            message = in.readUTF();
            System.out.println("Message Received from server : " + message);
            log.info("Message Received from server : " + message);
            log.info("Message Received from server : " + message);
        } catch (IOException e) {
            log.info("Exception encountered while reading data from the server : " + serverIPAddr + ":" + String.valueOf(port));
            log.info("Exception encountered while reading data from the server : " + serverIPAddr + ":" + String.valueOf(port));
            e.printStackTrace();
        }
        return message;
    }
    public void sendMessageToServer(String message) {
        if( null == out){
            log.info("Unable to send message to the server, Client Socket not connected.");
            log.info("Unable to send message to the server, Client Socket not connected.");
            return;
        }
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            log.info("Exception encountered while sending message to the server : " + serverIPAddr + ":" + String.valueOf(port));
            log.info("Exception encountered while sending message to the server : " + serverIPAddr + ":" + String.valueOf(port));
            e.printStackTrace();
        }
    }

    public void connectToServer(){

    }

    public void disConnect() throws IOException {
        if (null != client) {
            try {
                client.close();
            } catch (IOException e) {
                log.info("Exception encountered while closing the connection to the server : " + serverIPAddr + ":" + String.valueOf(port));
                log.info("Exception encountered while closing the connection to the server : " + serverIPAddr + ":" + String.valueOf(port));
                e.printStackTrace();
            }
        }
    }

}
