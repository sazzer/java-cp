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

import jline.console.ConsoleReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.grahamcox.cpmmo.network.Server;

import java.io.IOException;

/**
 * The main app for the server
 */
public class MainServerApp implements MainApp {
    /** The logger to use */
    private static final Logger LOG = LoggerFactory.getLogger(MainServerApp.class);
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
        runConsole();
        server.stop();
    }

    /**
     * Run the console
     */
    private void runConsole() {
        try {
            ConsoleReader consoleReader = new ConsoleReader();
            runInteractiveConsole(consoleReader);
        } catch (IOException e) {
            LOG.warn("Failed to open JLine. Console commands not available", e);
        }
    }

    /**
     * Run the interactive console
     * @param consoleReader the console reader to use
     */
    private void runInteractiveConsole(ConsoleReader consoleReader) {
        boolean running = true;
        while (running) {
            try {
                String line = consoleReader.readLine("> ");
                if (line != null && !line.isEmpty()) {
                    String[] parts = line.split(" ");
                    if ("shutdown".equals(parts[0])) {
                        running = false;
                    }
                }
            } catch (IOException e) {
                LOG.warn("Error reading from console", e);
            }
        }
    }
}
