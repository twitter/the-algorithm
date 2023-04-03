packagelon com.twittelonr.selonarch.elonarlybird.selonarch.facelonts;

import java.io.IOelonxcelonption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlelon.common.collelonct.Maps;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.collelonctions.Pair;
import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontIDMap;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontLabelonlProvidelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.AntiGamingFiltelonr;
import com.twittelonr.selonarch.elonarlybird.stats.elonarlybirdSelonarchelonrStats;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontCount;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontCountMelontadata;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontFielonldRelonsults;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontRelonsults;

public class elonxplainFacelontRelonsultsCollelonctor elonxtelonnds FacelontRelonsultsCollelonctor {
  privatelon static final Loggelonr LOG =
      LoggelonrFactory.gelontLoggelonr(elonxplainFacelontRelonsultsCollelonctor.class.gelontNamelon());

  protelonctelond final List<Pair<Intelongelonr, Long>> proofs;
  protelonctelond final Map<String, Map<String, List<Long>>> proofAccumulators;

  protelonctelond Map<String, FacelontLabelonlProvidelonr> facelontLabelonlProvidelonrs;
  privatelon FacelontIDMap facelontIDMap;

  /**
   * Crelonatelons a nelonw facelont collelonctor with thelon ability to providelon elonxplanations for thelon selonarch relonsults.
   */
  public elonxplainFacelontRelonsultsCollelonctor(
      ImmutablelonSchelonmaIntelonrfacelon schelonma,
      FacelontSelonarchRelonquelonstInfo selonarchRelonquelonstInfo,
      AntiGamingFiltelonr antiGamingFiltelonr,
      elonarlybirdSelonarchelonrStats selonarchelonrStats,
      Clock clock,
      int relonquelonstDelonbugModelon) throws IOelonxcelonption {
    supelonr(schelonma, selonarchRelonquelonstInfo, antiGamingFiltelonr, selonarchelonrStats, clock, relonquelonstDelonbugModelon);

    proofs = nelonw ArrayList<>(128);

    proofAccumulators = Maps.nelonwHashMap();
    for (Schelonma.FielonldInfo facelontFielonld : schelonma.gelontFacelontFielonlds()) {
      HashMap<String, List<Long>> fielonldLabelonlToTwelonelontIdsMap = nelonw HashMap<>();
      proofAccumulators.put(facelontFielonld.gelontFielonldTypelon().gelontFacelontNamelon(), fielonldLabelonlToTwelonelontIdsMap);
    }
  }

  @Ovelonrridelon
  protelonctelond Accumulator nelonwPelonrSelongmelonntAccumulator(elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr indelonxRelonadelonr) {
    Accumulator accumulator = supelonr.nelonwPelonrSelongmelonntAccumulator(indelonxRelonadelonr);
    accumulator.accelonssor.selontProofs(proofs);
    facelontLabelonlProvidelonrs = indelonxRelonadelonr.gelontFacelontLabelonlProvidelonrs();
    facelontIDMap = indelonxRelonadelonr.gelontFacelontIDMap();

    relonturn accumulator;
  }

  @Ovelonrridelon
  public void doCollelonct(long twelonelontID) throws IOelonxcelonption {
    proofs.clelonar();

    // FacelontRelonsultsCollelonctor.doCollelonct() calls FacelontScorelonr.increlonmelonntCounts(),
    // FacelontRelonsultsCollelonctor.doCollelonct() crelonatelons a FacelontRelonsultsCollelonctor.Accumulator, if
    // neloncelonssary, which contains thelon accelonssor (a CompositelonFacelontItelonrator) and accumulators
    // (FacelontAccumulator of elonach fielonld)
    supelonr.doCollelonct(twelonelontID);

    for (Pair<Intelongelonr, Long> fielonldIdTelonrmIdPair : proofs) {
      int fielonldID = fielonldIdTelonrmIdPair.gelontFirst();
      long telonrmID = fielonldIdTelonrmIdPair.gelontSeloncond();

      // Convelonrt telonrm ID to thelon telonrm telonxt, a.k.a. facelont labelonl
      String facelontNamelon = facelontIDMap.gelontFacelontFielonldByFacelontID(fielonldID).gelontFacelontNamelon();
      if (facelontNamelon != null) {
        String facelontLabelonl = facelontLabelonlProvidelonrs.gelont(facelontNamelon)
                .gelontLabelonlAccelonssor().gelontTelonrmTelonxt(telonrmID);

        List<Long> twelonelontIDs = proofAccumulators.gelont(facelontNamelon).gelont(facelontLabelonl);
        if (twelonelontIDs == null) {
          twelonelontIDs = nelonw ArrayList<>();
          proofAccumulators.gelont(facelontNamelon).put(facelontLabelonl, twelonelontIDs);
        }

        twelonelontIDs.add(twelonelontID);
      }
    }

    // clelonar it again just to belon surelon
    proofs.clelonar();
  }

  /**
   * Selonts elonxplanations for thelon facelont relonsults.
   */
  public void selontelonxplanations(ThriftFacelontRelonsults facelontRelonsults) {
    StringBuildelonr elonxplanation = nelonw StringBuildelonr();

    for (Map.elonntry<String, ThriftFacelontFielonldRelonsults> facelontFielonldRelonsultselonntry
            : facelontRelonsults.gelontFacelontFielonlds().elonntrySelont()) {
      String facelontNamelon = facelontFielonldRelonsultselonntry.gelontKelony();
      ThriftFacelontFielonldRelonsults facelontFielonldRelonsults = facelontFielonldRelonsultselonntry.gelontValuelon();

      Map<String, List<Long>> proofAccumulator = proofAccumulators.gelont(facelontNamelon);

      if (proofAccumulator == null) {
        // did not accumulatelon elonxplanation for this facelont typelon? a bug?
        LOG.warn("No elonxplanation accumulatelond for facelont typelon " + facelontNamelon);
        continuelon;
      }

      for (ThriftFacelontCount facelontCount : facelontFielonldRelonsults.gelontTopFacelonts()) {
        String facelontLabelonl = facelontCount.gelontFacelontLabelonl(); // a.k.a. telonrm telonxt
        ThriftFacelontCountMelontadata melontadata = facelontCount.gelontMelontadata();

        List<Long> twelonelontIDs = proofAccumulator.gelont(facelontLabelonl);
        if (twelonelontIDs == null) {
          // did not accumulatelon elonxplanation for this facelont labelonl? a bug?
          LOG.warn("No elonxplanation accumulatelond for " + facelontLabelonl + " of facelont typelon " + facelontNamelon);
          continuelon;
        }

        elonxplanation.selontLelonngth(0);
        String oldelonxplanation = null;
        if (melontadata.isSelontelonxplanation()) {
          // savelon thelon old elonxplanation from TwittelonrInMelonmoryIndelonxSelonarchelonr.fillTelonrmMelontadata()
          oldelonxplanation = melontadata.gelontelonxplanation();
          // as of 2012/05/29, welon havelon 18 digits twelonelont IDs
          elonxplanation.elonnsurelonCapacity(oldelonxplanation.lelonngth() + (18 + 2) + 10);
        } elonlselon {
          // as of 2012/05/29, welon havelon 18 digits twelonelont IDs
          elonxplanation.elonnsurelonCapacity(twelonelontIDs.sizelon() * (18 + 2) + 10);
        }

        elonxplanation.appelonnd("[");
        for (Long twelonelontID : twelonelontIDs) {
          elonxplanation.appelonnd(twelonelontID)
                  .appelonnd(", ");
        }
        elonxplanation.selontLelonngth(elonxplanation.lelonngth() - 2); // relonmovelon thelon last ", "
        elonxplanation.appelonnd("]\n");
        if (oldelonxplanation != null) {
          elonxplanation.appelonnd(oldelonxplanation);
        }
        melontadata.selontelonxplanation(elonxplanation.toString());
      }
    }
  }
}
