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
 * A Ping Message
 */
public class PingMessage {
    /** The ID of the Ping */
    private int id;
    /** The timestamp of the ping */
    private long timestamp;

    /**
     * Get the ID of the message
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID
     * @param id the ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the timestamp of the message
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Set the timestamp
     * @param timestamp the timestamp
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Get the ping time as a ZonedDateTime
     * @return the ping time
     */
    @JsonIgnore
    public ZonedDateTime getPingTime() {
        return ZonedDateTime.from(Instant.ofEpochSecond(timestamp));
    }

    /**
     * Set the ping time as a ZonedDateTime
     * @param time the ping time
     */
    @JsonIgnore
    public void setPingTime(ZonedDateTime time) {
        timestamp = time.toInstant().getEpochSecond();
    }
}
