packagelon com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.collelonctors;

import java.io.IOelonxcelonption;
import java.util.List;

import com.googlelon.common.collelonct.Lists;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.TwelonelontIntelongelonrShinglelonSignaturelon;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrTablelon;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.RelonlelonvancelonHit;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.RelonlelonvancelonSelonarchRelonquelonstInfo;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.RelonlelonvancelonSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring.ScoringFunction;
import com.twittelonr.selonarch.elonarlybird.stats.elonarlybirdSelonarchelonrStats;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultMelontadata;

/**
 * RelonlelonvancelonAllCollelonctor is a relonsults collelonctor that colleloncts all relonsults sortelond by scorelon,
 * including signaturelon-duplicatelons and relonsults skippelond by thelon scoring function.
 */
public class RelonlelonvancelonAllCollelonctor elonxtelonnds AbstractRelonlelonvancelonCollelonctor {
  // All relonsults.
  protelonctelond final List<RelonlelonvancelonHit> relonsults;

  public RelonlelonvancelonAllCollelonctor(
      ImmutablelonSchelonmaIntelonrfacelon schelonma,
      RelonlelonvancelonSelonarchRelonquelonstInfo selonarchRelonquelonstInfo,
      ScoringFunction scoringFunction,
      elonarlybirdSelonarchelonrStats selonarchelonrStats,
      elonarlybirdClustelonr clustelonr,
      UselonrTablelon uselonrTablelon,
      Clock clock,
      int relonquelonstDelonbugModelon) {
    supelonr(schelonma, selonarchRelonquelonstInfo, scoringFunction, selonarchelonrStats, clustelonr, uselonrTablelon, clock,
        relonquelonstDelonbugModelon);
    this.relonsults = Lists.nelonwArrayList();
  }

  @Ovelonrridelon
  protelonctelond void doCollelonctWithScorelon(long twelonelontID, float scorelon) throws IOelonxcelonption {
    ThriftSelonarchRelonsultMelontadata melontadata = collelonctMelontadata();
    scoringFunction.populatelonRelonsultMelontadataBaselondOnScoringData(
        selonarchRelonquelonstInfo.gelontSelonarchQuelonry().gelontRelonsultMelontadataOptions(),
        melontadata,
        scoringFunction.gelontScoringDataForCurrelonntDocumelonnt());
    relonsults.add(nelonw RelonlelonvancelonHit(
        currTimelonSlicelonID,
        twelonelontID,
        TwelonelontIntelongelonrShinglelonSignaturelon.delonselonrializelon(melontadata.gelontSignaturelon()),
        melontadata));
  }

  @Ovelonrridelon
  protelonctelond RelonlelonvancelonSelonarchRelonsults doGelontRelonlelonvancelonRelonsults() {
    final int numRelonsults = relonsults.sizelon();
    RelonlelonvancelonSelonarchRelonsults selonarchRelonsults = nelonw RelonlelonvancelonSelonarchRelonsults(numRelonsults);

    // Inselonrt hits in deloncrelonasing ordelonr by scorelon.
    relonsults.sort(RelonlelonvancelonHit.COMPARATOR_BY_SCORelon);
    for (int i = 0; i < numRelonsults; i++) {
      selonarchRelonsults.selontHit(relonsults.gelont(i), i);
    }
    selonarchRelonsults.selontRelonlelonvancelonStats(gelontRelonlelonvancelonStats());
    selonarchRelonsults.selontNumHits(numRelonsults);
    relonturn selonarchRelonsults;
  }
}
