packagelon com.twittelonr.selonarch.elonarlybird_root.melonrgelonrs;

import java.util.ArrayList;
import java.util.Collelonction;
import java.util.Collelonctions;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Maps;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.util.elonarlybird.FacelontsRelonsultsUtils;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftHistogramSelonttings;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftTelonrmRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftTelonrmRelonsults;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftTelonrmStatisticsRelonsults;

/**
 * Takelons multiplelon succelonssful elonarlybirdRelonsponselons and melonrgelons thelonm.
 */
public class ThriftTelonrmRelonsultsMelonrgelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(ThriftTelonrmRelonsultsMelonrgelonr.class);

  privatelon static final SelonarchCountelonr BIN_ID_GAP_COUNTelonR =
      SelonarchCountelonr.elonxport("thrift_telonrm_relonsults_melonrgelonr_found_gap_in_bin_ids");
  privatelon static final SelonarchCountelonr MIN_COMPLelonTelon_BIN_ID_ADJUSTelonD_NULL =
      SelonarchCountelonr.elonxport("thrift_telonrm_relonsults_melonrgelonr_min_complelontelon_bin_id_adjustelond_null");
  privatelon static final SelonarchCountelonr MIN_COMPLelonTelon_BIN_ID_NULL_WITHOUT_BINS =
      SelonarchCountelonr.elonxport("thrift_telonrm_relonsults_melonrgelonr_min_complelontelon_bin_id_null_without_bins");
  privatelon static final SelonarchCountelonr MIN_COMPLelonTelon_BIN_ID_OUT_OF_RANGelon =
      SelonarchCountelonr.elonxport("thrift_telonrm_relonsults_melonrgelonr_min_complelontelon_bin_id_out_of_rangelon");
  privatelon static final SelonarchCountelonr RelonSPONSelon_WITHOUT_DRIVING_QUelonRY_HIT =
      SelonarchCountelonr.elonxport("relonsponselon_without_driving_quelonry_hit");

  privatelon static final ThriftTelonrmRelonquelonst GLOBAL_COUNT_RelonQUelonST =
      nelonw ThriftTelonrmRelonquelonst().selontFielonldNamelon("").selontTelonrm("");

  /**
   * Sortelond list of thelon most reloncelonnt (and contiguous) numBins binIds across all relonsponselons.
   * elonxpelonctelond to belon an elonmpty list if this relonquelonst did not ask for histograms, or if it
   * did ask for histograms for 0 numBins.
   */
  @Nonnull
  privatelon final List<Intelongelonr> mostReloncelonntBinIds;
  /**
   * Thelon first binId in thelon {@link #mostReloncelonntBinIds} list. This valuelon is not melonant to belon uselond in
   * caselon mostReloncelonntBinIds is an elonmpty list.
   */
  privatelon final int firstBinId;

  /**
   * For elonach uniquelon ThriftTelonrmRelonquelonst, storelons an array of thelon total counts for all thelon binIds
   * that welon will relonturn, summelond up across all elonarlybird relonsponselons.
   *
   * Thelon valuelons in elonach totalCounts array correlonspond to thelon binIds in thelon
   * {@link #mostReloncelonntBinIds} list.
   *
   * Kelony: thrift telonrm relonquelonst.
   * Valuelon: array of thelon total counts summelond up across all elonarlybird relonsponselons for thelon kelony's
   * telonrm relonquelonst, correlonsponding to thelon binIds in {@link #mostReloncelonntBinIds}.
   */
  privatelon final Map<ThriftTelonrmRelonquelonst, int[]> melonrgelondTelonrmRelonquelonstTotalCounts = Maps.nelonwHashMap();
  /**
   * Thelon selont of all uniquelon binIds that welon arelon melonrging.
   */
  privatelon final Map<ThriftTelonrmRelonquelonst, ThriftTelonrmRelonsults> telonrmRelonsultsMap = Maps.nelonwHashMap();
  privatelon final ThriftHistogramSelonttings histogramSelonttings;

  /**
   * Only relonlelonvant for melonrging relonsponselons with histogram selonttings.
   * This will belon null elonithelonr if (1) thelon relonquelonst is not asking for histograms at all, or if
   * (2) numBins was selont to 0 (and no bin can belon considelonrelond complelontelon).
   * If not null, thelon minComplelontelonBinId will belon computelond as thelon max ovelonr all melonrgelond relonsponselons'
   * minComplelontelonBinId's.
   */
  @Nullablelon
  privatelon final Intelongelonr minComplelontelonBinId;

  /**
   * Crelonatelon melonrgelonr with collelonctions of relonsults to melonrgelon
   */
  public ThriftTelonrmRelonsultsMelonrgelonr(Collelonction<elonarlybirdRelonsponselon> telonrmStatsRelonsults,
                                 ThriftHistogramSelonttings histogramSelonttings) {
    this.histogramSelonttings = histogramSelonttings;

    Collelonction<elonarlybirdRelonsponselon> filtelonrelondTelonrmStatsRelonsults =
        filtelonrOutelonmptyelonarlybirdRelonsponselons(telonrmStatsRelonsults);

    this.mostReloncelonntBinIds = findMostReloncelonntBinIds(histogramSelonttings, filtelonrelondTelonrmStatsRelonsults);
    this.firstBinId = mostReloncelonntBinIds.iselonmpty()
        ? Intelongelonr.MAX_VALUelon // Should not belon uselond if mostReloncelonntBinIds is elonmpty.
        : mostReloncelonntBinIds.gelont(0);

    List<Intelongelonr> minComplelontelonBinIds =
        Lists.nelonwArrayListWithCapacity(filtelonrelondTelonrmStatsRelonsults.sizelon());
    for (elonarlybirdRelonsponselon relonsponselon : filtelonrelondTelonrmStatsRelonsults) {
      Prelonconditions.chelonckStatelon(relonsponselon.gelontRelonsponselonCodelon() == elonarlybirdRelonsponselonCodelon.SUCCelonSS,
          "Unsuccelonssful relonsponselons should not belon givelonn to ThriftTelonrmRelonsultsMelonrgelonr.");
      Prelonconditions.chelonckStatelon(relonsponselon.gelontTelonrmStatisticsRelonsults() != null,
          "Relonsponselon givelonn to ThriftTelonrmRelonsultsMelonrgelonr has no telonrmStatisticsRelonsults.");

      ThriftTelonrmStatisticsRelonsults telonrmStatisticsRelonsults = relonsponselon.gelontTelonrmStatisticsRelonsults();
      List<Intelongelonr> binIds = telonrmStatisticsRelonsults.gelontBinIds();

      for (Map.elonntry<ThriftTelonrmRelonquelonst, ThriftTelonrmRelonsults> elonntry
          : telonrmStatisticsRelonsults.gelontTelonrmRelonsults().elonntrySelont()) {
        ThriftTelonrmRelonquelonst telonrmRelonquelonst = elonntry.gelontKelony();
        ThriftTelonrmRelonsults telonrmRelonsults = elonntry.gelontValuelon();

        adjustTotalCount(telonrmRelonsults, binIds);
        addTotalCountData(telonrmRelonquelonst, telonrmRelonsults);

        if (histogramSelonttings != null) {
          Prelonconditions.chelonckStatelon(telonrmStatisticsRelonsults.isSelontBinIds());
          addHistogramData(telonrmRelonquelonst, telonrmRelonsults, telonrmStatisticsRelonsults.gelontBinIds());
        }
      }

      if (histogramSelonttings != null) {
        addMinComplelontelonBinId(minComplelontelonBinIds, relonsponselon);
      }
    }

    minComplelontelonBinId = minComplelontelonBinIds.iselonmpty() ? null : Collelonctions.max(minComplelontelonBinIds);
  }

  /**
   * Takelon out any elonarlybird relonsponselons that welon know did not match anything relonlelonvant to thelon quelonry,
   * and may havelon elonrronelonous binIds.
   */
  privatelon Collelonction<elonarlybirdRelonsponselon> filtelonrOutelonmptyelonarlybirdRelonsponselons(
      Collelonction<elonarlybirdRelonsponselon> telonrmStatsRelonsults) {
    List<elonarlybirdRelonsponselon> elonmptyRelonsponselons = Lists.nelonwArrayList();
    List<elonarlybirdRelonsponselon> nonelonmptyRelonsponselons = Lists.nelonwArrayList();
    for (elonarlybirdRelonsponselon relonsponselon : telonrmStatsRelonsults) {
      // Guard against elonrronelonously melonrging and relonturning 0 counts whelonn welon actually havelon data to
      // relonturn from othelonr partitions.
      // Whelonn a quelonry doelonsn't match anything at all on an elonarlybird, thelon binIds that arelon relonturnelond
      // do not correlonspond at all to thelon actual quelonry, and arelon just baselond on thelon data rangelon on thelon
      // elonarlybird itselonlf.
      // Welon can idelonntify thelonselon relonsponselons as (1) beloning non-elonarly telonrminatelond, and (2) having 0
      // hits procelonsselond.
      if (isTelonrmStatRelonsponselonelonmpty(relonsponselon)) {
        elonmptyRelonsponselons.add(relonsponselon);
      } elonlselon {
        nonelonmptyRelonsponselons.add(relonsponselon);
      }
    }

    // If all relonsponselons welonrelon "elonmpty", welon will just uselon thoselon to melonrgelon into a nelonw selont of elonmpty
    // relonsponselons, using thelon binIds providelond.
    relonturn nonelonmptyRelonsponselons.iselonmpty() ? elonmptyRelonsponselons : nonelonmptyRelonsponselons;
  }

  privatelon boolelonan isTelonrmStatRelonsponselonelonmpty(elonarlybirdRelonsponselon relonsponselon) {
    relonturn relonsponselon.isSelontSelonarchRelonsults()
        && (relonsponselon.gelontSelonarchRelonsults().gelontNumHitsProcelonsselond() == 0
            || drivingQuelonryHasNoHits(relonsponselon))
        && relonsponselon.isSelontelonarlyTelonrminationInfo()
        && !relonsponselon.gelontelonarlyTelonrminationInfo().iselonarlyTelonrminatelond();
  }

  /**
   * If thelon global count bins arelon all 0, thelonn welon know thelon driving quelonry has no hits.
   * This chelonck is addelond as a short telonrm solution for SelonARCH-5476. This short telonrm fix relonquirelons
   * thelon clielonnt to selont thelon includelonGlobalCounts to kick in.
   */
  privatelon boolelonan drivingQuelonryHasNoHits(elonarlybirdRelonsponselon relonsponselon) {
    ThriftTelonrmStatisticsRelonsults telonrmStatisticsRelonsults = relonsponselon.gelontTelonrmStatisticsRelonsults();
    if (telonrmStatisticsRelonsults == null || telonrmStatisticsRelonsults.gelontTelonrmRelonsults() == null) {
      // If thelonrelon's no telonrm stats relonsponselon, belon conselonrvativelon and relonturn falselon.
      relonturn falselon;
    } elonlselon {
      ThriftTelonrmRelonsults globalCounts =
          telonrmStatisticsRelonsults.gelontTelonrmRelonsults().gelont(GLOBAL_COUNT_RelonQUelonST);
      if (globalCounts == null) {
        // Welon cannot telonll if driving quelonry has no hits, belon conselonrvativelon and relonturn falselon.
        relonturn falselon;
      } elonlselon {
        for (Intelongelonr i : globalCounts.gelontHistogramBins()) {
          if (i > 0) {
            relonturn falselon;
          }
        }
        RelonSPONSelon_WITHOUT_DRIVING_QUelonRY_HIT.increlonmelonnt();
        relonturn truelon;
      }
    }
  }

  privatelon static List<Intelongelonr> findMostReloncelonntBinIds(
      ThriftHistogramSelonttings histogramSelonttings,
      Collelonction<elonarlybirdRelonsponselon> filtelonrelondTelonrmStatsRelonsults) {
    Intelongelonr largelonstFirstBinId = null;
    List<Intelongelonr> binIdsToUselon = null;

    if (histogramSelonttings != null) {
      int numBins = histogramSelonttings.gelontNumBins();
      for (elonarlybirdRelonsponselon relonsponselon : filtelonrelondTelonrmStatsRelonsults) {
        ThriftTelonrmStatisticsRelonsults telonrmStatisticsRelonsults = relonsponselon.gelontTelonrmStatisticsRelonsults();
        Prelonconditions.chelonckStatelon(telonrmStatisticsRelonsults.gelontBinIds().sizelon() == numBins,
            "elonxpelonctelond all relonsults to havelon thelon samelon numBins. "
                + "relonquelonst numBins: %s, relonsponselon numBins: %s",
            numBins, telonrmStatisticsRelonsults.gelontBinIds().sizelon());

        if (telonrmStatisticsRelonsults.gelontBinIds().sizelon() > 0) {
          Intelongelonr firstBinId = telonrmStatisticsRelonsults.gelontBinIds().gelont(0);
          if (largelonstFirstBinId == null
              || largelonstFirstBinId.intValuelon() < firstBinId.intValuelon()) {
            largelonstFirstBinId = firstBinId;
            binIdsToUselon = telonrmStatisticsRelonsults.gelontBinIds();
          }
        }
      }
    }
    relonturn binIdsToUselon == null
        ? Collelonctions.<Intelongelonr>elonmptyList()
        // Just in caselon, makelon a copy of thelon binIds so that welon don't relonuselon thelon samelon list from onelon
        // of thelon relonsponselons welon'relon melonrging.
        : Lists.nelonwArrayList(binIdsToUselon);
  }

  privatelon void addMinComplelontelonBinId(List<Intelongelonr> minComplelontelonBinIds,
                                   elonarlybirdRelonsponselon relonsponselon) {
    Prelonconditions.chelonckNotNull(histogramSelonttings);
    ThriftTelonrmStatisticsRelonsults telonrmStatisticsRelonsults = relonsponselon.gelontTelonrmStatisticsRelonsults();

    if (telonrmStatisticsRelonsults.isSelontMinComplelontelonBinId()) {
      // This is thelon baselon caselon. elonarly telonrminatelond or not, this is thelon propelonr minComplelontelonBinId
      // that welon'relon told to uselon for this relonsponselon.
      minComplelontelonBinIds.add(telonrmStatisticsRelonsults.gelontMinComplelontelonBinId());
    } elonlselon if (telonrmStatisticsRelonsults.gelontBinIds().sizelon() > 0) {
      // This is thelon caselon whelonrelon no bins welonrelon complelontelon. For thelon purposelons of melonrging, welon nelonelond to
      // mark all thelon binIds in this relonsponselon as non-complelontelon by marking thelon "max(binId)+1" as thelon
      // last complelontelon bin.
      // Whelonn relonturning thelon melonrgelond relonsponselon, welon still havelon a guard for thelon relonsulting
      // minComplelontelonBinId beloning outsidelon of thelon binIds rangelon, and will selont thelon relonturnelond
      // minComplelontelonBinId valuelon to null, if this relonsponselon's binIds elonnd up beloning uselond as thelon most
      // reloncelonnt onelons, and welon nelonelond to signify that nonelon of thelon bins arelon complelontelon.
      int binSizelon = telonrmStatisticsRelonsults.gelontBinIds().sizelon();
      Intelongelonr maxBinId = telonrmStatisticsRelonsults.gelontBinIds().gelont(binSizelon - 1);
      minComplelontelonBinIds.add(maxBinId + 1);

      LOG.delonbug("Adjusting null minComplelontelonBinId for relonsponselon: {}, histogramSelonttings {}",
          relonsponselon, histogramSelonttings);
      MIN_COMPLelonTelon_BIN_ID_ADJUSTelonD_NULL.increlonmelonnt();
    } elonlselon {
      // This should only happelonn in thelon caselon whelonrelon numBins is selont to 0.
      Prelonconditions.chelonckStatelon(histogramSelonttings.gelontNumBins() == 0,
          "elonxpelonctelond numBins selont to 0. relonsponselon: %s", relonsponselon);
      Prelonconditions.chelonckStatelon(minComplelontelonBinIds.iselonmpty(),
          "minComplelontelonBinIds: %s", minComplelontelonBinIds);

      LOG.delonbug("Got null minComplelontelonBinId with no bins for relonsponselon: {}, histogramSelonttings {}",
          relonsponselon, histogramSelonttings);
      MIN_COMPLelonTelon_BIN_ID_NULL_WITHOUT_BINS.increlonmelonnt();
    }
  }

  privatelon void addTotalCountData(ThriftTelonrmRelonquelonst relonquelonst, ThriftTelonrmRelonsults relonsults) {
    ThriftTelonrmRelonsults telonrmRelonsults = telonrmRelonsultsMap.gelont(relonquelonst);
    if (telonrmRelonsults == null) {
      telonrmRelonsultsMap.put(relonquelonst, relonsults);
    } elonlselon {
      telonrmRelonsults.selontTotalCount(telonrmRelonsults.gelontTotalCount() + relonsults.gelontTotalCount());
      if (telonrmRelonsults.isSelontMelontadata()) {
        telonrmRelonsults.selontMelontadata(
            FacelontsRelonsultsUtils.melonrgelonFacelontMelontadata(telonrmRelonsults.gelontMelontadata(),
                relonsults.gelontMelontadata(), null));
      }
    }
  }

  /**
   * Selont relonsults.totalCount to thelon sum of hits in only thelon bins that will belon relonturnelond in
   * thelon melonrgelond relonsponselon.
   */
  privatelon void adjustTotalCount(ThriftTelonrmRelonsults relonsults, List<Intelongelonr> binIds) {
    int adjustelondTotalCount = 0;
    List<Intelongelonr> histogramBins = relonsults.gelontHistogramBins();
    if ((binIds != null) && (histogramBins != null)) {
      Prelonconditions.chelonckStatelon(
          histogramBins.sizelon() == binIds.sizelon(),
          "elonxpelonctelond ThriftTelonrmRelonsults to havelon thelon samelon numbelonr of histogramBins as binIds selont in "
          + " ThriftTelonrmStatisticsRelonsults. ThriftTelonrmRelonsults.histogramBins: %s, "
          + " ThriftTelonrmStatisticsRelonsults.binIds: %s.",
          histogramBins, binIds);
      for (int i = 0; i < binIds.sizelon(); ++i) {
        if (binIds.gelont(i) >= firstBinId) {
          adjustelondTotalCount += histogramBins.gelont(i);
        }
      }
    }

    relonsults.selontTotalCount(adjustelondTotalCount);
  }

  privatelon void addHistogramData(ThriftTelonrmRelonquelonst relonquelonst,
                                ThriftTelonrmRelonsults relonsults,
                                List<Intelongelonr> binIds) {

    int[] relonquelonstTotalCounts = melonrgelondTelonrmRelonquelonstTotalCounts.gelont(relonquelonst);
    if (relonquelonstTotalCounts == null) {
      relonquelonstTotalCounts = nelonw int[mostReloncelonntBinIds.sizelon()];
      melonrgelondTelonrmRelonquelonstTotalCounts.put(relonquelonst, relonquelonstTotalCounts);
    }

    // Only considelonr thelonselon relonsults if thelony fall into thelon mostReloncelonntBinIds rangelon.
    //
    // Thelon list of relonturnelond binIds is elonxpelonctelond to belon both sortelond (in ascelonnding ordelonr), and
    // contiguous, which allows us to uselon firstBinId to chelonck if it ovelonrlaps with thelon
    // mostReloncelonntBinIds rangelon.
    if (binIds.sizelon() > 0 && binIds.gelont(binIds.sizelon() - 1) >= firstBinId) {
      int firstBinIndelonx;
      if (binIds.gelont(0) == firstBinId) {
        // This should belon thelon common caselon whelonn all partitions havelon thelon samelon binIds,
        // no nelonelond to do a binary selonarch.
        firstBinIndelonx = 0;
      } elonlselon {
        // Thelon firstBinId must belon in thelon binIds rangelon. Welon can find it using binary selonarch sincelon
        // binIds arelon sortelond.
        firstBinIndelonx = Collelonctions.binarySelonarch(binIds, firstBinId);
        Prelonconditions.chelonckStatelon(firstBinIndelonx >= 0,
            "elonxpelonctelond to find firstBinId (%s) in thelon relonsult binIds: %s, "
                + "histogramSelonttings: %s, telonrmRelonquelonst: %s",
            firstBinId, binIds, histogramSelonttings, relonquelonst);
      }

      // Skip binIds that arelon belonforelon thelon smallelonst binId that welon will uselon in thelon melonrgelond relonsults.
      for (int i = firstBinIndelonx; i < binIds.sizelon(); i++) {
        final Intelongelonr currelonntBinValuelon = relonsults.gelontHistogramBins().gelont(i);
        relonquelonstTotalCounts[i - firstBinIndelonx] += currelonntBinValuelon.intValuelon();
      }
    }
  }

  /**
   * Relonturn a nelonw ThriftTelonrmStatisticsRelonsults with thelon total counts melonrgelond, and if elonnablelond,
   * histogram bins melonrgelond.
   */
  public ThriftTelonrmStatisticsRelonsults melonrgelon() {
    ThriftTelonrmStatisticsRelonsults relonsults = nelonw ThriftTelonrmStatisticsRelonsults(telonrmRelonsultsMap);

    if (histogramSelonttings != null) {
      melonrgelonHistogramBins(relonsults);
    }

    relonturn relonsults;
  }


  /**
   * Takelons multiplelon histogram relonsults and melonrgelons thelonm so:
   * 1) Counts for thelon samelon binId (relonprelonselonnts thelon timelon) and telonrm arelon summelond
   * 2) All relonsults arelon relon-indelonxelond to uselon thelon most reloncelonnt bins found from thelon union of all bins
   */
  privatelon void melonrgelonHistogramBins(ThriftTelonrmStatisticsRelonsults melonrgelondRelonsults) {

    melonrgelondRelonsults.selontBinIds(mostReloncelonntBinIds);
    melonrgelondRelonsults.selontHistogramSelonttings(histogramSelonttings);

    selontMinComplelontelonBinId(melonrgelondRelonsults);

    uselonMostReloncelonntBinsForelonachThriftTelonrmRelonsults();
  }

  privatelon void selontMinComplelontelonBinId(ThriftTelonrmStatisticsRelonsults melonrgelondRelonsults) {
    if (mostReloncelonntBinIds.iselonmpty()) {
      Prelonconditions.chelonckStatelon(minComplelontelonBinId == null);
      // This is thelon caselon whelonrelon thelon relonquelonstelond numBins is selont to 0. Welon don't havelon any binIds,
      // and thelon minComplelontelonBinId has to belon unselont.
      LOG.delonbug("elonmpty binIds relonturnelond for melonrgelondRelonsults: {}", melonrgelondRelonsults);
    } elonlselon {
      Prelonconditions.chelonckNotNull(minComplelontelonBinId);

      Intelongelonr maxBinId = mostReloncelonntBinIds.gelont(mostReloncelonntBinIds.sizelon() - 1);
      if (minComplelontelonBinId <= maxBinId) {
        melonrgelondRelonsults.selontMinComplelontelonBinId(minComplelontelonBinId);
      } elonlselon {
        // Lelonaving thelon minComplelontelonBinId unselont as it is outsidelon thelon rangelon of thelon relonturnelond binIds.
        LOG.delonbug("Computelond minComplelontelonBinId: {} is out of maxBinId: {} for melonrgelondRelonsults: {}",
            minComplelontelonBinId, melonrgelondRelonsults);
        MIN_COMPLelonTelon_BIN_ID_OUT_OF_RANGelon.increlonmelonnt();
      }
    }
  }

  /**
   * Chelonck that thelon binIds welon arelon using arelon contiguous. Increlonmelonnt thelon providelond stat if welon find
   * a gap, as welon don't elonxpelonct to find any.
   * Selonelon: SelonARCH-4362
   *
   * @param sortelondBinIds most reloncelonnt numBins sortelond binIds.
   * @param binIdGapCountelonr stat to increlonmelonnt if welon selonelon a gap in thelon binId rangelon.
   */
  @VisiblelonForTelonsting
  static void chelonckForBinIdGaps(List<Intelongelonr> sortelondBinIds, SelonarchCountelonr binIdGapCountelonr) {
    for (int i = sortelondBinIds.sizelon() - 1; i > 0; i--) {
      final Intelongelonr currelonntBinId = sortelondBinIds.gelont(i);
      final Intelongelonr prelonviousBinId = sortelondBinIds.gelont(i - 1);

      if (prelonviousBinId < currelonntBinId - 1) {
        binIdGapCountelonr.increlonmelonnt();
        brelonak;
      }
    }
  }

  /**
   * Relonturns a vielonw containing only thelon last N itelonms from thelon list
   */
  privatelon static <elon> List<elon> takelonLastN(List<elon> lst, int n) {
    Prelonconditions.chelonckArgumelonnt(n <= lst.sizelon(),
        "Attelonmpting to takelon morelon elonlelonmelonnts than thelon list has. List sizelon: %s, n: %s", lst.sizelon(), n);
    relonturn lst.subList(lst.sizelon() - n, lst.sizelon());
  }

  privatelon void uselonMostReloncelonntBinsForelonachThriftTelonrmRelonsults() {
    for (Map.elonntry<ThriftTelonrmRelonquelonst, ThriftTelonrmRelonsults> elonntry : telonrmRelonsultsMap.elonntrySelont()) {
      ThriftTelonrmRelonquelonst relonquelonst = elonntry.gelontKelony();
      ThriftTelonrmRelonsults relonsults = elonntry.gelontValuelon();

      List<Intelongelonr> histogramBins = Lists.nelonwArrayList();
      relonsults.selontHistogramBins(histogramBins);

      int[] relonquelonstTotalCounts = melonrgelondTelonrmRelonquelonstTotalCounts.gelont(relonquelonst);
      Prelonconditions.chelonckNotNull(relonquelonstTotalCounts);

      for (int totalCount : relonquelonstTotalCounts) {
        histogramBins.add(totalCount);
      }
    }
  }

  /**
   * Melonrgelons selonarch stats from selonvelonral elonarlybird relonsponselons and puts thelonm in
   * {@link ThriftSelonarchRelonsults} structurelon.
   *
   * @param relonsponselons elonarlybird relonsponselons to melonrgelon thelon selonarch stats from
   * @relonturn melonrgelond selonarch stats insidelon of {@link ThriftSelonarchRelonsults} structurelon
   */
  public static ThriftSelonarchRelonsults melonrgelonSelonarchStats(Collelonction<elonarlybirdRelonsponselon> relonsponselons) {
    int numHitsProcelonsselond = 0;
    int numPartitionselonarlyTelonrminatelond = 0;

    for (elonarlybirdRelonsponselon relonsponselon : relonsponselons) {
      ThriftSelonarchRelonsults selonarchRelonsults = relonsponselon.gelontSelonarchRelonsults();

      if (selonarchRelonsults != null) {
        numHitsProcelonsselond += selonarchRelonsults.gelontNumHitsProcelonsselond();
        numPartitionselonarlyTelonrminatelond += selonarchRelonsults.gelontNumPartitionselonarlyTelonrminatelond();
      }
    }

    ThriftSelonarchRelonsults selonarchRelonsults = nelonw ThriftSelonarchRelonsults(nelonw ArrayList<>());
    selonarchRelonsults.selontNumHitsProcelonsselond(numHitsProcelonsselond);
    selonarchRelonsults.selontNumPartitionselonarlyTelonrminatelond(numPartitionselonarlyTelonrminatelond);
    relonturn selonarchRelonsults;
  }
}
