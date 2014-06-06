/****************************************************************************************************************
 *
 *  Copyright (c) 2014 OCLC, Inc. All Rights Reserved.
 *
 *  OCLC proprietary information: the enclosed materials contain
 *  proprietary information of OCLC, Inc. and shall not be disclosed in whole or in
 *  any part to any third party or used by any person for any purpose, without written
 *  consent of OCLC, Inc.  Duplication of any portion of these materials shall include this notice.
 *
 ******************************************************************************************************************/
package uk.co.grahamcox.cpmmo;

import uk.co.grahamcox.cpmmo.network.Server;

/**
 * The main app for the server
 */
public class MainServerApp implements MainApp {
    /** The actual network server */
    private Server server;

    /**
     * Inject the server to use
     * @param server the server to use
     */
    public void setServer(Server server) {
        this.server = server;
    }

    /**
     * Start running the main server application
     */
    @Override
    public void run() {
        server.start();
        server.stop();
    }
}
