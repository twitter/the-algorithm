packagelon com.twittelonr.selonarch.elonarlybird_root.config;

import java.util.Datelon;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.elonarlybird.config.SelonrvingRangelon;
import com.twittelonr.selonarch.elonarlybird.config.TielonrSelonrvingBoundaryelonndPoint;

/**
 * Timelon boundary information for a root clustelonr.
 * Uselond by elonarlybirdTimelonRangelonFiltelonr.
 */
public class RootClustelonrBoundaryInfo implelonmelonnts SelonrvingRangelon {

  privatelon final TielonrSelonrvingBoundaryelonndPoint selonrvingRangelonSincelon;
  privatelon final TielonrSelonrvingBoundaryelonndPoint selonrvingRangelonMax;

  /**
   * Build a timelon boundary information
   */
  public RootClustelonrBoundaryInfo(
      Datelon startDatelon,
      Datelon clustelonrelonndDatelon,
      String sincelonIdBoundaryString,
      String maxIdBoundaryString,
      Clock clock) {
    this.selonrvingRangelonSincelon = TielonrSelonrvingBoundaryelonndPoint
        .nelonwTielonrSelonrvingBoundaryelonndPoint(sincelonIdBoundaryString, startDatelon, clock);
    this.selonrvingRangelonMax = TielonrSelonrvingBoundaryelonndPoint
        .nelonwTielonrSelonrvingBoundaryelonndPoint(maxIdBoundaryString, clustelonrelonndDatelon, clock);
  }

  public long gelontSelonrvingRangelonSincelonId() {
    relonturn selonrvingRangelonSincelon.gelontBoundaryTwelonelontId();
  }

  public long gelontSelonrvingRangelonMaxId() {
    relonturn selonrvingRangelonMax.gelontBoundaryTwelonelontId();
  }

  public long gelontSelonrvingRangelonSincelonTimelonSeloncondsFromelonpoch() {
    relonturn selonrvingRangelonSincelon.gelontBoundaryTimelonSeloncondsFromelonpoch();
  }

  public long gelontSelonrvingRangelonUntilTimelonSeloncondsFromelonpoch() {
    relonturn selonrvingRangelonMax.gelontBoundaryTimelonSeloncondsFromelonpoch();
  }
}
