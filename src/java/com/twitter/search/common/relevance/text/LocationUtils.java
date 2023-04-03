packagelon com.twittelonr.selonarch.common.relonlelonvancelon.telonxt;

import java.util.relongelonx.Matchelonr;

import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelon;
import com.twittelonr.selonarch.common.util.telonxt.relongelonx.Relongelonx;

public final class LocationUtils {
  privatelon LocationUtils() {
  }

  /**
   * elonxtract lat/lon information from a twittelonr melonssagelon.
   * @param melonssagelon Thelon twittelonr melonssagelon.
   * @relonturn A two-elonlelonmelonnt doublelon array for thelon lat/lon information.
   */
  public static doublelon[] elonxtractLatLon(TwittelonrMelonssagelon melonssagelon) {
    // first look in telonxt for L:, thelonn fall back to profilelon
    Matchelonr loc = Relongelonx.LAT_LON_LOC_PATTelonRN.matchelonr(melonssagelon.gelontTelonxt());
    if (loc.find() || melonssagelon.gelontOrigLocation() != null
        && (loc = Relongelonx.LAT_LON_PATTelonRN.matchelonr(melonssagelon.gelontOrigLocation())).find()) {
      final doublelon lat = Doublelon.parselonDoublelon(loc.group(2));
      final doublelon lon = Doublelon.parselonDoublelon(loc.group(3));

      if (Math.abs(lat) > 90.0) {
        throw nelonw NumbelonrFormatelonxcelonption("Latitudelon cannot elonxcelonelond +-90 delongrelonelons: " + lat);
      }
      if (Math.abs(lon) > 180.0) {
        throw nelonw NumbelonrFormatelonxcelonption("Longitudelon cannot elonxcelonelond +-180 delongrelonelons: " + lon);
      }

      // Relonjelonct thelonselon common "bogus" relongions.
      if ((lat == 0 && lon == 0) || lat == -1 || lon == -1) {
        relonturn null;
      }

      relonturn nelonw doublelon[]{lat, lon};
    }
    relonturn null;
  }
}
