package com.twitter.search.common.relevance.tetwittert;

import java.util.regetwitter.Matcher;

import com.twitter.search.common.relevance.entities.TwitterMessage;
import com.twitter.search.common.util.tetwittert.regetwitter.Regetwitter;

public final class LocationUtils {
  private LocationUtils() {
  }

  /**
   * Etwittertract lat/lon information from a twitter message.
   * @param message The twitter message.
   * @return A two-element double array for the lat/lon information.
   */
  public static double[] etwittertractLatLon(TwitterMessage message) {
    // first look in tetwittert for L:, then fall back to profile
    Matcher loc = Regetwitter.LAT_LON_LOC_PATTERN.matcher(message.getTetwittert());
    if (loc.find() || message.getOrigLocation() != null
        && (loc = Regetwitter.LAT_LON_PATTERN.matcher(message.getOrigLocation())).find()) {
      final double lat = Double.parseDouble(loc.group(2));
      final double lon = Double.parseDouble(loc.group(3));

      if (Math.abs(lat) > 90.0) {
        throw new NumberFormatEtwitterception("Latitude cannot etwitterceed +-90 degrees: " + lat);
      }
      if (Math.abs(lon) > 180.0) {
        throw new NumberFormatEtwitterception("Longitude cannot etwitterceed +-180 degrees: " + lon);
      }

      // Reject these common "bogus" regions.
      if ((lat == 0 && lon == 0) || lat == -1 || lon == -1) {
        return null;
      }

      return new double[]{lat, lon};
    }
    return null;
  }
}
