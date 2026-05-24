package com.twitter.search.earlybird.util;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;

public final class ScrubGenUtil {
  public static final FastDateFormat SCRUB_GEN_DATE_FORMAT = FastDateFormat.getInstance("yyyyMMdd");

  private ScrubGenUtil() { }

  /**
   * Helper method to parse a scrub gen from String to date
   *
   * @param scrubGen
   * @return scrubGen in Date type
   */
  public static Date parseScrubGenToDate(String scrubGen) {
    try {
      return SCRUB_GEN_DATE_FORMAT.parse(scrubGen);
    } catch (ParseException e) {
      String msg = "Malformed scrub gen date: " + scrubGen;
      // If we are running a scrub gen and the date is bad we should quit and not continue.
      throw new RuntimeException(msg, e);
    }
  }
}
