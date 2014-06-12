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

import java.net.ConnectException;
import uk.co.grahamcox.cpmmo.network.Client;
import uk.co.grahamcox.spring.repl.Repl;

/**
 * The main app for the client
 */
public class CmdClientApp implements MainApp {
    /** The network client */
    private Client client;

    /** The Repl to use */
    private Repl repl;

    /**
     * Set the network client
     * @param client the network client
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Set the REPL to use
     * @param repl the REPL to use
     */
    public void setRepl(Repl repl) {
        this.repl = repl;
    }

    /**
     * Start running the main client application
     */
    @Override
    public void run() {

        repl.run();

        try {
            client.connect("localhost", 12345);
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ConnectException e) {
            e.printStackTrace();
        }
        client.stop();
    }
}
