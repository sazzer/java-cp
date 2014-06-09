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
package uk.co.grahamcox.cpmmo.network.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * A Pong Message
 */
public class PongMessage {
    /** The ID of the Ping */
    private int id;
    /** The timestamp of the ping */
    private long timestamp;

    /**
     * Construct the message
     * @param id the ID of the message
     * @param now the current time
     */
    public PongMessage(int id, ZonedDateTime now) {
        this.id = id;
        this.timestamp = now.toInstant().getEpochSecond();
    }

    /**
     * Get the ID of the message
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Get the timestamp of the message
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Get the ping time as a ZonedDateTime
     * @return the ping time
     */
    @JsonIgnore
    public ZonedDateTime getPingTime() {
        return ZonedDateTime.from(Instant.ofEpochSecond(timestamp));
    }
}
