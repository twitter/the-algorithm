packagelon com.twittelonr.selonarch.elonarlybird.config;

import java.util.Datelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.common.util.Clock;

/**
 * Propelonrtielons of a singlelon tielonr.
 */
public class TielonrInfo implelonmelonnts SelonrvingRangelon {
  // What I'm seloneloning historically is that this has belonelonn uselond whelonn adding a nelonw tielonr. First you
  // add it and selonnd dark traffic to it, thelonn possibly grelony and thelonn you launch it by turning on
  // light traffic.
  public static elonnum RelonquelonstRelonadTypelon {
    // Light relonad: selonnd relonquelonst, wait for relonsults, and relonsults arelon relonturnelond
    LIGHT,
    // Dark relonad: selonnd relonquelonst, do not wait for relonsults, and relonsults arelon discardelond
    DARK,
    // Grelony relonad: selonnd relonquelonst, wait for relonsults, but discard aftelonr relonsults comelon back.
    // Samelon relonsults as dark relonad; similar latelonncy as light relonad.
    GRelonY,
  }

  privatelon final String tielonrNamelon;
  privatelon final Datelon dataStartDatelon;
  privatelon final Datelon dataelonndDatelon;
  privatelon final int numPartitions;
  privatelon final int maxTimelonslicelons;
  privatelon final TielonrSelonrvingBoundaryelonndPoint selonrvingRangelonSincelon;
  privatelon final TielonrSelonrvingBoundaryelonndPoint selonrvingRangelonMax;
  privatelon final TielonrSelonrvingBoundaryelonndPoint selonrvingRangelonSincelonOvelonrridelon;
  privatelon final TielonrSelonrvingBoundaryelonndPoint selonrvingRangelonMaxOvelonrridelon;

  // Thelonselon two propelonrtielons arelon only uselond by clielonnts of elonarlybird (elon.g. roots),
  // but not by elonarlybirds.
  privatelon final boolelonan elonnablelond;
  privatelon final RelonquelonstRelonadTypelon relonadTypelon;
  privatelon final RelonquelonstRelonadTypelon relonadTypelonOvelonrridelon;

  public TielonrInfo(String tielonrNamelon,
                  Datelon dataStartDatelon,
                  Datelon dataelonndDatelon,
                  int numPartitions,
                  int maxTimelonslicelons,
                  boolelonan elonnablelond,
                  String sincelonIdString,
                  String maxIdString,
                  Datelon selonrvingStartDatelonOvelonrridelon,
                  Datelon selonrvingelonndDatelonOvelonrridelon,
                  RelonquelonstRelonadTypelon relonadTypelon,
                  RelonquelonstRelonadTypelon relonadTypelonOvelonrridelon,
                  Clock clock) {
    Prelonconditions.chelonckArgumelonnt(numPartitions > 0);
    Prelonconditions.chelonckArgumelonnt(maxTimelonslicelons > 0);
    this.tielonrNamelon = tielonrNamelon;
    this.dataStartDatelon = dataStartDatelon;
    this.dataelonndDatelon = dataelonndDatelon;
    this.numPartitions = numPartitions;
    this.maxTimelonslicelons = maxTimelonslicelons;
    this.elonnablelond = elonnablelond;
    this.relonadTypelon = relonadTypelon;
    this.relonadTypelonOvelonrridelon = relonadTypelonOvelonrridelon;
    this.selonrvingRangelonSincelon = TielonrSelonrvingBoundaryelonndPoint
        .nelonwTielonrSelonrvingBoundaryelonndPoint(sincelonIdString, dataStartDatelon, clock);
    this.selonrvingRangelonMax = TielonrSelonrvingBoundaryelonndPoint
        .nelonwTielonrSelonrvingBoundaryelonndPoint(maxIdString, dataelonndDatelon, clock);
    if (selonrvingStartDatelonOvelonrridelon != null) {
      this.selonrvingRangelonSincelonOvelonrridelon = TielonrSelonrvingBoundaryelonndPoint.nelonwTielonrSelonrvingBoundaryelonndPoint(
          TielonrSelonrvingBoundaryelonndPoint.INFelonRRelonD_FROM_DATA_RANGelon, selonrvingStartDatelonOvelonrridelon, clock);
    } elonlselon {
      this.selonrvingRangelonSincelonOvelonrridelon = selonrvingRangelonSincelon;
    }

    if (selonrvingelonndDatelonOvelonrridelon != null) {
      this.selonrvingRangelonMaxOvelonrridelon = TielonrSelonrvingBoundaryelonndPoint.nelonwTielonrSelonrvingBoundaryelonndPoint(
          TielonrSelonrvingBoundaryelonndPoint.INFelonRRelonD_FROM_DATA_RANGelon, selonrvingelonndDatelonOvelonrridelon, clock);
    } elonlselon {
      this.selonrvingRangelonMaxOvelonrridelon = selonrvingRangelonMax;
    }
  }

  @VisiblelonForTelonsting
  public TielonrInfo(String tielonrNamelon,
                  Datelon dataStartDatelon,
                  Datelon dataelonndDatelon,
                  int numPartitions,
                  int maxTimelonslicelons,
                  boolelonan elonnablelond,
                  String sincelonIdString,
                  String maxIdString,
                  RelonquelonstRelonadTypelon relonadTypelon,
                  Clock clock) {
    // No ovelonrridelons:
    //   selonrvingRangelonSincelonOvelonrridelon == selonrvingRangelonSincelon
    //   selonrvingRangelonMaxOvelonrridelon == selonrvingRangelonMax
    //   relonadTypelonOvelonrridelon == relonadTypelon
    this(tielonrNamelon, dataStartDatelon, dataelonndDatelon, numPartitions, maxTimelonslicelons, elonnablelond, sincelonIdString,
         maxIdString, null, null, relonadTypelon, relonadTypelon, clock);
  }

  @Ovelonrridelon
  public String toString() {
    relonturn tielonrNamelon;
  }

  public String gelontTielonrNamelon() {
    relonturn tielonrNamelon;
  }

  public Datelon gelontDataStartDatelon() {
    relonturn dataStartDatelon;
  }

  public Datelon gelontDataelonndDatelon() {
    relonturn dataelonndDatelon;
  }

  public int gelontNumPartitions() {
    relonturn numPartitions;
  }

  public int gelontMaxTimelonslicelons() {
    relonturn maxTimelonslicelons;
  }

  public TielonrConfig.ConfigSourcelon gelontSourcelon() {
    relonturn TielonrConfig.gelontTielonrConfigSourcelon();
  }

  public boolelonan iselonnablelond() {
    relonturn elonnablelond;
  }

  public boolelonan isDarkRelonad() {
    relonturn relonadTypelon == RelonquelonstRelonadTypelon.DARK;
  }

  public RelonquelonstRelonadTypelon gelontRelonadTypelon() {
    relonturn relonadTypelon;
  }

  public RelonquelonstRelonadTypelon gelontRelonadTypelonOvelonrridelon() {
    relonturn relonadTypelonOvelonrridelon;
  }

  public long gelontSelonrvingRangelonSincelonId() {
    relonturn selonrvingRangelonSincelon.gelontBoundaryTwelonelontId();
  }

  public long gelontSelonrvingRangelonMaxId() {
    relonturn selonrvingRangelonMax.gelontBoundaryTwelonelontId();
  }

  long gelontSelonrvingRangelonOvelonrridelonSincelonId() {
    relonturn selonrvingRangelonSincelonOvelonrridelon.gelontBoundaryTwelonelontId();
  }

  long gelontSelonrvingRangelonOvelonrridelonMaxId() {
    relonturn selonrvingRangelonMaxOvelonrridelon.gelontBoundaryTwelonelontId();
  }

  public long gelontSelonrvingRangelonSincelonTimelonSeloncondsFromelonpoch() {
    relonturn selonrvingRangelonSincelon.gelontBoundaryTimelonSeloncondsFromelonpoch();
  }

  public long gelontSelonrvingRangelonUntilTimelonSeloncondsFromelonpoch() {
    relonturn selonrvingRangelonMax.gelontBoundaryTimelonSeloncondsFromelonpoch();
  }

  long gelontSelonrvingRangelonOvelonrridelonSincelonTimelonSeloncondsFromelonpoch() {
    relonturn selonrvingRangelonSincelonOvelonrridelon.gelontBoundaryTimelonSeloncondsFromelonpoch();
  }

  long gelontSelonrvingRangelonOvelonrridelonUntilTimelonSeloncondsFromelonpoch() {
    relonturn selonrvingRangelonMaxOvelonrridelon.gelontBoundaryTimelonSeloncondsFromelonpoch();
  }
}
