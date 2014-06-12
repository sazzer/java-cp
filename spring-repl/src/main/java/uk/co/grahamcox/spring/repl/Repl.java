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
package uk.co.grahamcox.spring.repl;

import java.io.IOException;

import jline.console.ConsoleReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * The actual REPL class
 */
public class Repl implements ApplicationContextAware, Runnable {
    /** The logger to use */
    private static final Logger LOG = LoggerFactory.getLogger(Repl.class);

    /** The Application Context to use */
    private ApplicationContext applicationContext;

    /**
     * Default Constructor. Use this if we are going to inject the Application Context some other way
     */
    public Repl() {
        // Nothing here
    }

    /**
     * Construct the REPL from the provided Application Context
     * @param applicationContext the Application Context to use
     */
    public Repl(ApplicationContext applicationContext) {
        setApplicationContext(applicationContext);
    }

    /**
     * Actually start running the Repl
     */
    @Override
    public void run() {
        LOG.info("Starting Repl");
        ConsoleReader consoleReader = createConsoleReader();

        boolean running = true;
        while (running) {
            try {
                String line = consoleReader.readLine("> ");
                if ("exit".equals(line) || "quit".equals(line)) {
                    running = false;
                }
            } catch (IOException e) {
                LOG.error("Error reading command", e);
                running = false;
            }
        }
    }

    /**
     * Create the console reader to use
     * @return the console reader
     */
    private ConsoleReader createConsoleReader() {
        ConsoleReader consoleReader;
        try {
            consoleReader = new ConsoleReader();
        } catch (IOException e) {
            LOG.error("Error creating the console reader", e);
            throw new RuntimeException(e);
        }
        return consoleReader;
    }

    /**
     * Inject the Application Context to use
     * @param applicationContext the Application Context
     */
    @Override
    public final void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
