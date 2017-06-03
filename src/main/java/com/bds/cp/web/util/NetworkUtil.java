package com.bds.cp.web.util;

/**
 * Created by Vivek Tripathi on 06/12/16.
 */

public class NetworkUtil {

    private static final String serverAddr = "192.168.1.111";
    private static final int serverPort = 1717;

    private static NetworkClient networkClient = null;

    public static NetworkClient getNetworkClient(){
        if(null == networkClient) {
            networkClient = new NetworkClient(serverAddr, serverPort);
            networkClient.connect();
        }
        return networkClient;
    }


}
