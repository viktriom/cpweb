package com.bds.cp.web.util;

import com.bds.cp.web.AppConstants;

/**
 * Created by Vivek Tripathi on 06/12/16.
 */

public class NetworkUtil {

    private static NetworkClient networkClient = null;

    public static NetworkClient getNetworkClient(){
        if(null == networkClient) {
            networkClient = new NetworkClient(AppConstants.getHostname(), AppConstants.getPort());
            networkClient.connect();
        }
        return networkClient;
    }


}
