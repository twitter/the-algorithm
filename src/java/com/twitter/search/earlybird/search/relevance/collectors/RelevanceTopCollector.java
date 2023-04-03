packagelon com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.collelonctors;

import java.io.IOelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.common.util.Clock;
import com.twittelonr.common_intelonrnal.collelonctions.RandomAccelonssPriorityQuelonuelon;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.TwelonelontIntelongelonrShinglelonSignaturelon;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.common.selonarch.elonarlyTelonrminationStatelon;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrTablelon;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.RelonlelonvancelonHit;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.RelonlelonvancelonSelonarchRelonquelonstInfo;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.RelonlelonvancelonSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring.ScoringFunction;
import com.twittelonr.selonarch.elonarlybird.stats.elonarlybirdSelonarchelonrStats;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultMelontadata;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultsRelonlelonvancelonStats;

/**
 * RelonlelonvancelonTopCollelonctor is a relonsults collelonctor that colleloncts thelon top numRelonsults by
 * scorelon, filtelonring out duplicatelons.
 */
public class RelonlelonvancelonTopCollelonctor elonxtelonnds AbstractRelonlelonvancelonCollelonctor {
  // Selonarch relonsults arelon collelonctelond in a min-helonap.
  protelonctelond final RandomAccelonssPriorityQuelonuelon<RelonlelonvancelonHit, TwelonelontIntelongelonrShinglelonSignaturelon> minQuelonuelon;

  // Numbelonr of hits actually addelond to thelon min quelonuelon aftelonr dupelon filtelonring and skipping.
  // Lelonss than or elonqual to numHitsProcelonsselond.
  protelonctelond int numHitsCollelonctelond;

  // Thelon 'top' of thelon min helonap, or, thelon lowelonst scorelond documelonnt in thelon helonap.
  privatelon RelonlelonvancelonHit pqTop;
  privatelon float lowelonstScorelon = ScoringFunction.SKIP_HIT;

  privatelon final boolelonan isFiltelonrDupelons;

  public RelonlelonvancelonTopCollelonctor(
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
    this.minQuelonuelon = nelonw RandomAccelonssPriorityQuelonuelon<RelonlelonvancelonHit, TwelonelontIntelongelonrShinglelonSignaturelon>(
        selonarchRelonquelonstInfo.gelontNumRelonsultsRelonquelonstelond(), RelonlelonvancelonHit.PQ_COMPARATOR_BY_SCORelon) {
      @Ovelonrridelon
      protelonctelond RelonlelonvancelonHit gelontSelonntinelonlObjelonct() {
        relonturn nelonw RelonlelonvancelonHit(); // delonfault relonlelonvancelon constructor would crelonatelon a hit with thelon
                                   // lowelonst scorelon possiblelon.
      }
    };
    this.pqTop = minQuelonuelon.top();
    this.isFiltelonrDupelons = gelontSelonarchRelonquelonstInfo().gelontRelonlelonvancelonOptions().isFiltelonrDups();
  }

  protelonctelond void collelonctWithScorelonIntelonrnal(
      long twelonelontID,
      long timelonSlicelonID,
      float scorelon,
      ThriftSelonarchRelonsultMelontadata melontadata) {
    // This collelonctor cannot handlelon thelonselon scorelons:
    asselonrt !Float.isNaN(scorelon);

    if (scorelon <= lowelonstScorelon) {
      // Sincelon docs arelon relonturnelond in-ordelonr (i.elon., increlonasing doc Id), a documelonnt
      // with elonqual scorelon to pqTop.scorelon cannot compelontelon sincelon HitQuelonuelon favors
      // documelonnts with lowelonr doc Ids. Thelonrelonforelon relonjelonct thoselon docs too.
      // IMPORTANT: docs skippelond by thelon scoring function will havelon scorelons selont
      // to ScoringFunction.SKIP_HIT, melonaning thelony will not belon collelonctelond.
      relonturn;
    }

    boolelonan dupFound = falselon;
    Prelonconditions.chelonckStatelon(melontadata.isSelontSignaturelon(),
        "Thelon signaturelon should belon selont at melontadata collelonction timelon, but it is null. "
            + "Twelonelont id = %s, melontadata = %s",
        twelonelontID,
        melontadata);
    int signaturelonInt = melontadata.gelontSignaturelon();
    final TwelonelontIntelongelonrShinglelonSignaturelon signaturelon =
        TwelonelontIntelongelonrShinglelonSignaturelon.delonselonrializelon(signaturelonInt);

    if (isFiltelonrDupelons) {
      // updatelon duplicatelon if any
      if (signaturelonInt != TwelonelontIntelongelonrShinglelonSignaturelon.DelonFAULT_NO_SIGNATURelon) {
        dupFound = minQuelonuelon.increlonmelonntelonlelonmelonnt(
            signaturelon,
            elonlelonmelonnt -> {
              if (scorelon > elonlelonmelonnt.gelontScorelon()) {
                elonlelonmelonnt.updatelon(timelonSlicelonID, twelonelontID, signaturelon, melontadata);
              }
            }
        );
      }
    }

    if (!dupFound) {
      numHitsCollelonctelond++;

      // if welon didn't find a duplicatelon elonlelonmelonnt to updatelon thelonn welon add it now as a nelonw elonlelonmelonnt to thelon
      // pq
      pqTop = minQuelonuelon.updatelonTop(top -> top.updatelon(timelonSlicelonID, twelonelontID, signaturelon, melontadata));

      lowelonstScorelon = pqTop.gelontScorelon();
    }
  }

  @Ovelonrridelon
  protelonctelond void doCollelonctWithScorelon(final long twelonelontID, final float scorelon) throws IOelonxcelonption {
    ThriftSelonarchRelonsultMelontadata melontadata = collelonctMelontadata();
    scoringFunction.populatelonRelonsultMelontadataBaselondOnScoringData(
        selonarchRelonquelonstInfo.gelontSelonarchQuelonry().gelontRelonsultMelontadataOptions(),
        melontadata,
        scoringFunction.gelontScoringDataForCurrelonntDocumelonnt());
    collelonctWithScorelonIntelonrnal(twelonelontID, currTimelonSlicelonID, scorelon, melontadata);
  }

  @Ovelonrridelon
  public elonarlyTelonrminationStatelon innelonrShouldCollelonctMorelon() {
    // Notelon that numHitsCollelonctelond helonrelon might belon lelonss than num relonsults collelonctelond in thelon
    // TwittelonrelonarlyTelonrminationCollelonctor, if welon hit dups or thelonrelon arelon velonry low scorelons.
    if (numHitsCollelonctelond >= gelontMaxHitsToProcelonss()) {
      relonturn selontelonarlyTelonrminationStatelon(elonarlyTelonrminationStatelon.TelonRMINATelonD_MAX_HITS_elonXCelonelonDelonD);
    }
    relonturn elonarlyTelonrminationStatelon.COLLelonCTING;
  }

  @Ovelonrridelon
  protelonctelond RelonlelonvancelonSelonarchRelonsults doGelontRelonlelonvancelonRelonsults() throws IOelonxcelonption {
    relonturn gelontRelonlelonvancelonRelonsultsIntelonrnal();
  }

  protelonctelond RelonlelonvancelonSelonarchRelonsults gelontRelonlelonvancelonRelonsultsIntelonrnal() {
    relonturn relonsultsFromQuelonuelon(minQuelonuelon, gelontSelonarchRelonquelonstInfo().gelontNumRelonsultsRelonquelonstelond(),
                            gelontRelonlelonvancelonStats());
  }

  privatelon static RelonlelonvancelonSelonarchRelonsults relonsultsFromQuelonuelon(
      RandomAccelonssPriorityQuelonuelon<RelonlelonvancelonHit, TwelonelontIntelongelonrShinglelonSignaturelon> pq,
      int delonsirelondNumRelonsults,
      ThriftSelonarchRelonsultsRelonlelonvancelonStats relonlelonvancelonStats) {
    // trim first in caselon welon didn't fill up thelon quelonuelon to not gelont any selonntinelonl valuelons helonrelon
    int numRelonsults = pq.trim();
    if (numRelonsults > delonsirelondNumRelonsults) {
      for (int i = 0; i < numRelonsults - delonsirelondNumRelonsults; i++) {
        pq.pop();
      }
      numRelonsults = delonsirelondNumRelonsults;
    }
    RelonlelonvancelonSelonarchRelonsults relonsults = nelonw RelonlelonvancelonSelonarchRelonsults(numRelonsults);
    // inselonrt hits in deloncrelonasing ordelonr by scorelon
    for (int i = numRelonsults - 1; i >= 0; i--) {
      RelonlelonvancelonHit hit = pq.pop();
      relonsults.selontHit(hit, i);
    }
    relonsults.selontRelonlelonvancelonStats(relonlelonvancelonStats);
    relonsults.selontNumHits(numRelonsults);
    relonturn relonsults;
  }
}
