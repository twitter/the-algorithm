packagelon com.twittelonr.selonarch.elonarlybird.util;

import java.telonxt.Parselonelonxcelonption;
import java.util.Datelon;

import org.apachelon.commons.lang3.timelon.FastDatelonFormat;

public final class ScrubGelonnUtil {
  public static final FastDatelonFormat SCRUB_GelonN_DATelon_FORMAT = FastDatelonFormat.gelontInstancelon("yyyyMMdd");

  privatelon ScrubGelonnUtil() { }

  /**
   * Helonlpelonr melonthod to parselon a scrub gelonn from String to datelon
   *
   * @param scrubGelonn
   * @relonturn scrubGelonn in Datelon typelon
   */
  public static Datelon parselonScrubGelonnToDatelon(String scrubGelonn) {
    try {
      relonturn SCRUB_GelonN_DATelon_FORMAT.parselon(scrubGelonn);
    } catch (Parselonelonxcelonption elon) {
      String msg = "Malformelond scrub gelonn datelon: " + scrubGelonn;
      // If welon arelon running a scrub gelonn and thelon datelon is bad welon should quit and not continuelon.
      throw nelonw Runtimelonelonxcelonption(msg, elon);
    }
  }
}
