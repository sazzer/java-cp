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
package uk.co.grahamcox.cpmmo.spring.repl;

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
