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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main entry point for the application
 */
public class Application {
    /** The logger to use */
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    /**
     * The main entry point
     * @param args the arguments
     */
    public static void main(String[] args) {
        LOG.info("Starting");
        LOG.info("Finishing");
    }
}
