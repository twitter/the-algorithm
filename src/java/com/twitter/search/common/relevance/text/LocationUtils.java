package com.tw ter.search.common.relevance.text;

 mport java.ut l.regex.Matc r;

 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;
 mport com.tw ter.search.common.ut l.text.regex.Regex;

publ c f nal class Locat onUt ls {
  pr vate Locat onUt ls() {
  }

  /**
   * Extract lat/lon  nformat on from a tw ter  ssage.
   * @param  ssage T  tw ter  ssage.
   * @return A two-ele nt double array for t  lat/lon  nformat on.
   */
  publ c stat c double[] extractLatLon(Tw ter ssage  ssage) {
    // f rst look  n text for L:, t n fall back to prof le
    Matc r loc = Regex.LAT_LON_LOC_PATTERN.matc r( ssage.getText());
     f (loc.f nd() ||  ssage.getOr gLocat on() != null
        && (loc = Regex.LAT_LON_PATTERN.matc r( ssage.getOr gLocat on())).f nd()) {
      f nal double lat = Double.parseDouble(loc.group(2));
      f nal double lon = Double.parseDouble(loc.group(3));

       f (Math.abs(lat) > 90.0) {
        throw new NumberFormatExcept on("Lat ude cannot exceed +-90 degrees: " + lat);
      }
       f (Math.abs(lon) > 180.0) {
        throw new NumberFormatExcept on("Long ude cannot exceed +-180 degrees: " + lon);
      }

      // Reject t se common "bogus" reg ons.
       f ((lat == 0 && lon == 0) || lat == -1 || lon == -1) {
        return null;
      }

      return new double[]{lat, lon};
    }
    return null;
  }
}
