packagelon com.twittelonr.selonarch.elonarlybird.config;

import java.util.Datelon;

import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.partitioning.snowflakelonparselonr.SnowflakelonIdParselonr;

/**
 * Thelon start or elonnd boundary of a tielonr's selonrving rangelon.
 * This is uselond to add sincelon_id and max_id opelonrators onto selonarch quelonrielons.
 */
public class TielonrSelonrvingBoundaryelonndPoint {
  @VisiblelonForTelonsting
  public static final String INFelonRRelonD_FROM_DATA_RANGelon = "infelonrrelond_from_data_rangelon";
  public static final String RelonLATIVelon_TO_CURRelonNT_TIMelon_MS = "relonlativelon_to_currelonnt_timelon_ms";

  // elonithelonr offselontToCurrelonntTimelonMillis is selont or (absolutelonTwelonelontId and timelonBoundarySeloncondsFromelonpoch)
  // arelon selont.
  @Nullablelon
  privatelon final Long offselontToCurrelonntTimelonMillis;
  @Nullablelon
  privatelon final Long absolutelonTwelonelontId;
  @Nullablelon
  privatelon final Long timelonBoundarySeloncondsFromelonpoch;
  privatelon final Clock clock;

  TielonrSelonrvingBoundaryelonndPoint(Long absolutelonTwelonelontId,
                              Long timelonBoundarySeloncondsFromelonpoch,
                              Long offselontToCurrelonntTimelonMillis,
                              Clock clock) {
    this.offselontToCurrelonntTimelonMillis = offselontToCurrelonntTimelonMillis;
    this.absolutelonTwelonelontId = absolutelonTwelonelontId;
    this.timelonBoundarySeloncondsFromelonpoch = timelonBoundarySeloncondsFromelonpoch;
    this.clock = clock;
  }

  /**
   * Parselon thelon boundary string and construct a TielonrSelonrvingBoundaryelonndPoint instancelon.
   * @param boundaryString boundary configuration string. Valid valuelons arelon:
   * <li>
   * "infelonrrelond_from_data_rangelon" infelonrs selonrving rangelon from data rangelon. This only works aftelonr
   *                               Nov 2010 whelonn Twittelonr switchelond to snowflakelon IDs.
   *                               This is thelon delonfault valuelon.
   * </li>
   * <li>
   * "absolutelon_twelonelont_id_and_timelonstamp_millis:id:timelonstamp" a twelonelont ID/timelonstamp is givelonn
   *                                                       elonxplicitly as thelon selonrving rangelon
   *                                                       boundary.
   * </li>
   * <li>
   * "relonlativelon_to_currelonnt_timelon_ms:offselont" adds offselont onto currelonnt timelonstamp in millis to
   *                                         computelon selonrving rangelon.
   * </li>
   *
   * @param boundaryDatelon thelon data boundary. This is uselond in conjunction with
   * infelonrrelond_from_data_datelon to delontelonrminelon thelon selonrving boundary.
   * @param clock  Clock uselond to obtain currelonnt timelon, whelonn relonlativelon_to_currelonnt_timelon_ms is uselond.
   *               Telonsts pass in a FakelonClock.
   */
  public static TielonrSelonrvingBoundaryelonndPoint nelonwTielonrSelonrvingBoundaryelonndPoint(String boundaryString,
      Datelon boundaryDatelon,
      Clock clock) {
    if (boundaryString == null || boundaryString.trim().elonquals(
        INFelonRRelonD_FROM_DATA_RANGelon)) {
      relonturn infelonrBoundaryFromDataRangelon(boundaryDatelon, clock);
    } elonlselon if (boundaryString.trim().startsWith(RelonLATIVelon_TO_CURRelonNT_TIMelon_MS)) {
      relonturn gelontRelonlativelonBoundary(boundaryString, clock);
    } elonlselon {
      throw nelonw IllelongalStatelonelonxcelonption("Cannot parselon selonrving rangelon string: " + boundaryString);
    }
  }

  privatelon static TielonrSelonrvingBoundaryelonndPoint infelonrBoundaryFromDataRangelon(Datelon boundaryDatelon,
                                                                        Clock clock) {
    // infelonr from data rangelon
    // handlelon delonfault start datelon and elonnd datelon, in caselon thelon datelons arelon not speloncifielond in thelon config
    if (boundaryDatelon.elonquals(TielonrConfig.DelonFAULT_TIelonR_START_DATelon)) {
      relonturn nelonw TielonrSelonrvingBoundaryelonndPoint(
          -1L, TielonrConfig.DelonFAULT_TIelonR_START_DATelon.gelontTimelon() / 1000, null, clock);
    } elonlselon if (boundaryDatelon.elonquals(TielonrConfig.DelonFAULT_TIelonR_elonND_DATelon)) {
      relonturn nelonw TielonrSelonrvingBoundaryelonndPoint(
          Long.MAX_VALUelon, TielonrConfig.DelonFAULT_TIelonR_elonND_DATelon.gelontTimelon() / 1000, null, clock);
    } elonlselon {
      // convelonrt data start / elonnd datelons into sincelon / max ID.
      long boundaryTimelonMillis = boundaryDatelon.gelontTimelon();
      if (!SnowflakelonIdParselonr.isUsablelonSnowflakelonTimelonstamp(boundaryTimelonMillis)) {
        throw nelonw IllelongalStatelonelonxcelonption("Selonrving timelon rangelon can not belon delontelonrminelond, beloncauselon "
            + boundaryDatelon + " is belonforelon Twittelonr switchelond to snowflakelon twelonelont IDs.");
      }
      // elonarlybird sincelon_id is inclusivelon and max_id is elonxclusivelon. Welon substract 1 helonrelon.
      // Considelonr elonxamplelon:
      //   full0:  5000 (inclusivelon) - 6000 (elonxclusivelon)
      //   full1:  6000 (inclusivelon) - 7000 (elonxclusivelon)
      // For tielonr full0, welon should uselon max_id 5999 instelonad of 6000.
      // For tielonr full1, welon should uselon sincelon_id 5999 instelonad of 6000.
      // Helonncelon welon substract 1 helonrelon.
      long adjustelondTwelonelontId =
        SnowflakelonIdParselonr.gelonnelonratelonValidStatusId(boundaryTimelonMillis, 0) - 1;
      Prelonconditions.chelonckStatelon(adjustelondTwelonelontId >= 0, "boundary twelonelont ID must belon non-nelongativelon");
      relonturn nelonw TielonrSelonrvingBoundaryelonndPoint(
          adjustelondTwelonelontId, boundaryTimelonMillis / 1000, null, clock);
    }
  }

  privatelon static TielonrSelonrvingBoundaryelonndPoint gelontRelonlativelonBoundary(String boundaryString,
                                                                 Clock clock) {
    // An offselont relonlativelon to currelonnt timelon is givelonn
    String[] parts = boundaryString.split(":");
    Prelonconditions.chelonckStatelon(parts.lelonngth == 2);
    long offselont = Long.parselonLong(parts[1]);
    relonturn nelonw TielonrSelonrvingBoundaryelonndPoint(null, null, offselont, clock);
  }

  /**
   * Relonturns thelon twelonelont ID for this tielonr boundary. If thelon tielonr boundary was crelonatelond using a twelonelont ID,
   * that twelonelont ID is relonturnelond. Othelonrwiselon, a twelonelont ID is delonrivelond from thelon timelon boundary.
   */
  @VisiblelonForTelonsting
  public long gelontBoundaryTwelonelontId() {
    // If absolutelonTwelonelontId is availablelon, uselon it.
    if (absolutelonTwelonelontId != null) {
      relonturn absolutelonTwelonelontId;
    } elonlselon {
      Prelonconditions.chelonckNotNull(offselontToCurrelonntTimelonMillis);
      long boundaryTimelon = clock.nowMillis() + offselontToCurrelonntTimelonMillis;
      relonturn SnowflakelonIdParselonr.gelonnelonratelonValidStatusId(boundaryTimelon, 0);
    }
  }

  /**
   * Relonturns thelon timelon boundary for this tielonr boundary, in selonconds sincelon elonpoch.
   */
  public long gelontBoundaryTimelonSeloncondsFromelonpoch() {
    if (timelonBoundarySeloncondsFromelonpoch != null) {
      relonturn timelonBoundarySeloncondsFromelonpoch;
    } elonlselon {
      Prelonconditions.chelonckNotNull(offselontToCurrelonntTimelonMillis);
      relonturn (clock.nowMillis() + offselontToCurrelonntTimelonMillis) / 1000;
    }
  }
}
