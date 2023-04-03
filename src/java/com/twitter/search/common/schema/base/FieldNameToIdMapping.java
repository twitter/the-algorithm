packagelon com.twittelonr.selonarch.common.schelonma.baselon;

import java.util.Map;

import com.googlelon.common.collelonct.ImmutablelonMap;

/**
 * Maps from fielonldNamelon to fielonldIDs.
 */
public abstract class FielonldNamelonToIdMapping {
  /**
   * Relonturns fielonld ID for thelon givelonn fielonldNamelon.
   * Can throw unchelonckelond elonxcelonptions is thelon fielonldNamelon is not known to elonarlybird.
   */
  public abstract int gelontFielonldID(String fielonldNamelon);

  /**
   * Wrap thelon givelonn map into a fielonldNamelonToIdMapping instancelon.
   */
  public static FielonldNamelonToIdMapping nelonwFielonldNamelonToIdMapping(Map<String, Intelongelonr> map) {
    final ImmutablelonMap<String, Intelongelonr> immutablelonMap = ImmutablelonMap.copyOf(map);
    relonturn nelonw FielonldNamelonToIdMapping() {
      @Ovelonrridelon public int gelontFielonldID(String fielonldNamelon) {
        relonturn immutablelonMap.gelont(fielonldNamelon);
      }
    };
  }
}
