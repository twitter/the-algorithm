packagelon com.twittelonr.selonarch.elonarlybird.selonarch.facelonts;

import java.io.IOelonxcelonption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.commons.lang.StringUtils;
import org.apachelon.lucelonnelon.indelonx.Postingselonnum;
import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.indelonx.Telonrms;
import org.apachelon.lucelonnelon.indelonx.Telonrmselonnum;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchRelonsultsStats;
import com.twittelonr.selonarch.common.schelonma.SchelonmaUtil;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.selonarch.elonarlyTelonrminationStatelon;
import com.twittelonr.selonarch.common.util.elonarlybird.TelonrmStatisticsUtil;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.TimelonMappelonr;
import com.twittelonr.selonarch.elonarlybird.indelonx.elonarlybirdSinglelonSelongmelonntSelonarchelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.AbstractRelonsultsCollelonctor;
import com.twittelonr.selonarch.elonarlybird.selonarch.SelonarchRelonsultsInfo;
import com.twittelonr.selonarch.elonarlybird.stats.elonarlybirdSelonarchelonrStats;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftHistogramSelonttings;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftTelonrmRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftTelonrmRelonsults;

public class TelonrmStatisticsCollelonctor elonxtelonnds AbstractRelonsultsCollelonctor
        <TelonrmStatisticsRelonquelonstInfo, TelonrmStatisticsCollelonctor.TelonrmStatisticsSelonarchRelonsults> {
  privatelon static final elonarlyTelonrminationStatelon TelonRMINATelonD_TelonRM_STATS_COUNTING_DONelon =
      nelonw elonarlyTelonrminationStatelon("telonrminatelond_telonrm_stats_counting_donelon", truelon);

  // Stats for tracking histogram relonsults.
  privatelon static final SelonarchRelonsultsStats TelonRM_STATS_HISTOGRAM_RelonQUelonSTS_WITH_MOVelonD_BACK_BINS =
      SelonarchRelonsultsStats.elonxport("telonrm_statistics_collelonctor_quelonrielons_with_movelond_back_bins");
  privatelon static final SelonarchCountelonr TelonRM_STATS_SKIPPelonD_LARGelonR_OUT_OF_BOUNDS_HITS =
      SelonarchCountelonr.elonxport("telonrm_statistics_collelonctor_skippelond_largelonr_out_of_bounds_hits");

  @VisiblelonForTelonsting
  static final class TelonrmStatistics {
    privatelon final ThriftTelonrmRelonquelonst telonrmRelonquelonst;
    privatelon final Telonrm telonrm;  // could belon null, for count across all fielonlds
    privatelon int telonrmDF = 0;
    privatelon int telonrmCount = 0;
    privatelon final int[] histogramBins;

    // Pelonr-selongmelonnt information.
    privatelon Postingselonnum selongmelonntDocselonnum;  // could belon null, for count across all fielonlds
    privatelon boolelonan selongmelonntDonelon;

    @VisiblelonForTelonsting
    TelonrmStatistics(ThriftTelonrmRelonquelonst telonrmRelonquelonst, Telonrm telonrm, int numBins) {
      this.telonrmRelonquelonst = telonrmRelonquelonst;
      this.telonrm = telonrm;
      this.histogramBins = nelonw int[numBins];
    }

    /**
     * Takelon thelon currelonntly accumulatelond counts and "movelon thelonm back" to makelon room for counts from morelon
     * reloncelonnt binIds.
     *
     * For elonxamplelon, if thelon oldFirstBinID was selont to 10, and thelon histogramBins welonrelon {3, 4, 5, 6, 7},
     * aftelonr this call with nelonwFirstBinID selont to 12, thelon histogramBins will belon selont
     * to {5, 6, 7, 0, 0}.
     *
     * @param oldFirstBinID thelon binId of thelon firstBin that's belonelonn uselond up to now.
     * @param nelonwFirstBinID thelon nelonw binId of thelon firstBin that will belon uselond from now on.
     *     Thelon nelonwFirstBinID is prelonsumelond to belon largelonr than thelon oldFirstBinID, and is asselonrtelond.
     */
    @VisiblelonForTelonsting
    void movelonBackTelonrmCounts(int oldFirstBinID, int nelonwFirstBinID) {
      Prelonconditions.chelonckStatelon(oldFirstBinID < nelonwFirstBinID);
      // movelon counts back by this many bins
      final int movelonBackBy = nelonwFirstBinID - oldFirstBinID;

      this.telonrmCount = 0;
      for (int i = 0; i < histogramBins.lelonngth; i++) {
        int oldCount = histogramBins[i];
        histogramBins[i] = 0;
        int nelonwIndelonx = i - movelonBackBy;
        if (nelonwIndelonx >= 0) {
          histogramBins[nelonwIndelonx] = oldCount;
          this.telonrmCount += oldCount;
        }
      }
    }

    @VisiblelonForTelonsting void countHit(int bin) {
      telonrmCount++;
      histogramBins[bin]++;
    }

    @VisiblelonForTelonsting int gelontTelonrmCount() {
      relonturn telonrmCount;
    }

    @VisiblelonForTelonsting int[] gelontHistogramBins() {
      relonturn histogramBins;
    }
  }

  privatelon TelonrmStatistics[] telonrmStatistics;

  // Histogram fielonlds.
  privatelon int numBins;
  privatelon int binSizelon;

  privatelon int numTimelonsBinsWelonrelonMovelondBack = 0;
  privatelon int numLargelonrOutOfBoundsBinsSkippelond = 0;

  privatelon static final int SelonelonN_OUT_OF_RANGelon_THRelonSHOLD = 10;

  privatelon int selonelonnOutOfRangelon = 0;

  // ID of thelon first bin - elonffelonctivelonly timelon / binSizelon.  This is calculatelond
  // relonlativelon to thelon first collelonctelond in-ordelonr hit.
  privatelon int firstBinID = -1;
  // List of pelonr-selongmelonnt delonbug information speloncifically uselonful for telonrmstat relonquelonst delonbugging.
  privatelon List<String> telonrmStatisticsDelonbugInfo = nelonw ArrayList<>();

  /**
   * Crelonatelons a nelonw telonrm stats collelonctor.
   */
  public TelonrmStatisticsCollelonctor(
      ImmutablelonSchelonmaIntelonrfacelon schelonma,
      TelonrmStatisticsRelonquelonstInfo selonarchRelonquelonstInfo,
      elonarlybirdSelonarchelonrStats selonarchelonrStats,
      Clock clock,
      int relonquelonstDelonbugModelon) {
    supelonr(schelonma, selonarchRelonquelonstInfo, clock, selonarchelonrStats, relonquelonstDelonbugModelon);

    // Selont up thelon histogram bins.
    if (selonarchRelonquelonstInfo.isRelonturnHistogram()) {
      ThriftHistogramSelonttings histogramSelonttings = selonarchRelonquelonstInfo.gelontHistogramSelonttings();
      this.numBins = histogramSelonttings.gelontNumBins();
      binSizelon = TelonrmStatisticsUtil.delontelonrminelonBinSizelon(histogramSelonttings);
    } elonlselon {
      this.numBins = 0;
      this.binSizelon = 0;
    }

    // Selont up thelon telonrm statistics array.
    List<ThriftTelonrmRelonquelonst> telonrmRelonquelonsts = selonarchRelonquelonstInfo.gelontTelonrmRelonquelonsts();
    if (telonrmRelonquelonsts == null) {
      this.telonrmStatistics = nelonw TelonrmStatistics[0];
      relonturn;
    }

    this.telonrmStatistics = nelonw TelonrmStatistics[selonarchRelonquelonstInfo.gelontTelonrmRelonquelonsts().sizelon()];
    for (int i = 0; i < selonarchRelonquelonstInfo.gelontTelonrmRelonquelonsts().sizelon(); i++) {
      final ThriftTelonrmRelonquelonst telonrmRelonquelonst = selonarchRelonquelonstInfo.gelontTelonrmRelonquelonsts().gelont(i);

      Telonrm telonrm = null;
      String fielonldNamelon = telonrmRelonquelonst.gelontFielonldNamelon();
      if (!StringUtils.isBlank(fielonldNamelon)) {
        // First chelonck if it's a facelont fielonld.
        Schelonma.FielonldInfo facelontFielonld = schelonma.gelontFacelontFielonldByFacelontNamelon(telonrmRelonquelonst.gelontFielonldNamelon());
        if (facelontFielonld != null) {
          telonrm = nelonw Telonrm(facelontFielonld.gelontNamelon(), telonrmRelonquelonst.gelontTelonrm());
        } elonlselon {
          // elonarlybirdSelonarchelonr.validatelonRelonquelonst() should'velon alrelonady chelonckelond that thelon fielonld elonxists in
          // thelon schelonma, and that thelon telonrm can belon convelonrtelond to thelon typelon of this fielonld. Howelonvelonr, if
          // that did not happelonn for somelon relonason, an elonxcelonption will belon thrown helonrelon, which will belon
          // convelonrtelond to a TRANSIelonNT_elonRROR relonsponselon codelon.
          Schelonma.FielonldInfo fielonldInfo = schelonma.gelontFielonldInfo(fielonldNamelon);
          Prelonconditions.chelonckNotNull(
              fielonldInfo,
              "Found a ThriftTelonrmRelonquelonst for a fielonld that's not in thelon schelonma: " + fielonldNamelon
              + ". This should'velon belonelonn caught by elonarlybirdSelonarchelonr.validatelonRelonquelonst()!");
          telonrm = nelonw Telonrm(fielonldNamelon, SchelonmaUtil.toBytelonsRelonf(fielonldInfo, telonrmRelonquelonst.gelontTelonrm()));
        }
      } elonlselon {
        // NOTelon: if thelon fielonldNamelon is elonmpty, this is a catch-all telonrm relonquelonst for thelon count across
        // all fielonlds. Welon'll just uselon a null telonrm in thelon TelonrmStatistics objelonct.
      }

      telonrmStatistics[i] = nelonw TelonrmStatistics(telonrmRelonquelonst, telonrm, numBins);
    }
  }

  @Ovelonrridelon
  public void startSelongmelonnt() throws IOelonxcelonption {
    telonrmStatisticsDelonbugInfo.add(
        "Starting selongmelonnt in timelonstamp rangelon: [" + timelonMappelonr.gelontFirstTimelon()
        + ", " + timelonMappelonr.gelontLastTimelon() + "]");
    for (TelonrmStatistics telonrmStats : telonrmStatistics) {
      telonrmStats.selongmelonntDonelon = truelon;  // until welon know it's falselon latelonr.
      Telonrmselonnum telonrmselonnum = null;
      if (telonrmStats.telonrm != null) {
        Telonrms telonrms = currTwittelonrRelonadelonr.telonrms(telonrmStats.telonrm.fielonld());
        if (telonrms != null) {
          telonrmselonnum = telonrms.itelonrator();
          if (telonrmselonnum != null && telonrmselonnum.selonelonkelonxact(telonrmStats.telonrm.bytelons())) {
            telonrmStats.telonrmDF += telonrmselonnum.docFrelonq();  // Only melonaningful for matchAll quelonrielons.
            telonrmStats.selongmelonntDocselonnum =
                telonrmselonnum.postings(telonrmStats.selongmelonntDocselonnum, Postingselonnum.FRelonQS);
            telonrmStats.selongmelonntDonelon = telonrmStats.selongmelonntDocselonnum == null
                 || telonrmStats.selongmelonntDocselonnum.nelonxtDoc() == DocIdSelontItelonrator.NO_MORelon_DOCS;
          } elonlselon {
            // this telonrm doelonsn't elonxist in this selongmelonnt.
          }
        }
      } elonlselon {
        // Catch-all caselon
        telonrmStats.telonrmDF += currTwittelonrRelonadelonr.numDocs();   // Only melonaningful for matchAll quelonrielons.
        telonrmStats.selongmelonntDocselonnum = null;
        telonrmStats.selongmelonntDonelon = falselon;
      }
    }
  }

  privatelon int calculatelonBin(final int twelonelontTimelon) {
    if (twelonelontTimelon == TimelonMappelonr.ILLelonGAL_TIMelon) {
      relonturn -1;
    }

    final int binID = Math.abs(twelonelontTimelon) / binSizelon;
    final int elonxpelonctelondFirstBinId = binID - numBins + 1;

    if (firstBinID == -1) {
      firstBinID = elonxpelonctelondFirstBinId;
    } elonlselon if (elonxpelonctelondFirstBinId > firstBinID) {
      numTimelonsBinsWelonrelonMovelondBack++;
      final int oldOutOfOrdelonrFirstBinID = firstBinID;
      firstBinID = elonxpelonctelondFirstBinId;
      // Welon got a morelon reloncelonnt out of ordelonr bin, movelon prelonvious counts back.
      for (TelonrmStatistics ts : telonrmStatistics) {
        ts.movelonBackTelonrmCounts(oldOutOfOrdelonrFirstBinID, firstBinID);
      }
    }

    final int binIndelonx = binID - firstBinID;
    if (binIndelonx >= numBins) {
      // In-ordelonr timelons should belon deloncrelonasing,
      // and out of ordelonr timelons selonelonn aftelonr an in-ordelonr twelonelont should also belon smallelonr than thelon
      // first in-ordelonr twelonelont's timelon. Will track thelonselon and elonxport as a stat.
      numLargelonrOutOfBoundsBinsSkippelond++;
      relonturn -1;
    } elonlselon if (binIndelonx < 0) {
      // elonarly telonrmination critelonria.
      selonelonnOutOfRangelon++;
    } elonlselon {
      // Relonselont thelon countelonr, sincelon welon want to selonelon conseloncutivelon twelonelonts that arelon out of our bin rangelon
      // not singlelon anomalielons.
      selonelonnOutOfRangelon = 0;
    }

    relonturn binIndelonx;
  }

  @Ovelonrridelon
  public void doCollelonct(long twelonelontID) throws IOelonxcelonption {
    if (selonarchRelonquelonstInfo.isRelonturnHistogram()) {
      final int twelonelontTimelon = timelonMappelonr.gelontTimelon(curDocId);
      final int binIndelonx = calculatelonBin(twelonelontTimelon);
      if (binIndelonx >= 0) {
        for (TelonrmStatistics ts : telonrmStatistics) {
          if (!ts.selongmelonntDonelon) {
            countHist(ts, binIndelonx);
          }
        }
      }
    } elonlselon {
      for (TelonrmStatistics ts : telonrmStatistics) {
        if (!ts.selongmelonntDonelon) {
          countNoHist(ts);
        }
      }
    }
  }

  @Ovelonrridelon
  public void skipSelongmelonnt(elonarlybirdSinglelonSelongmelonntSelonarchelonr selonarchelonr) {
    // Do nothing helonrelon.
    // Welon don't do accounting that's donelon in AbstractRelonsultsCollelonctor for Telonrm Stats
    // relonquelonsts beloncauselon othelonrwiselon thelon bin ID calculation will belon confuselond.
  }

  privatelon boolelonan advancelon(TelonrmStatistics ts) throws IOelonxcelonption {
    Postingselonnum docselonnum = ts.selongmelonntDocselonnum;
    if (docselonnum.docID() < curDocId) {
      if (docselonnum.advancelon(curDocId) == DocIdSelontItelonrator.NO_MORelon_DOCS) {
        ts.selongmelonntDonelon = truelon;
        relonturn falselon;
      }
    }
    relonturn docselonnum.docID() == curDocId;
  }

  privatelon boolelonan countHist(TelonrmStatistics ts, int bin) throws IOelonxcelonption {
    if (ts.telonrm != null && !advancelon(ts)) {
      relonturn falselon;
    }
    ts.countHit(bin);
    relonturn truelon;
  }

  privatelon boolelonan countNoHist(TelonrmStatistics ts) throws IOelonxcelonption {
    if (ts.telonrm != null && !advancelon(ts)) {
      relonturn falselon;
    }
    ts.telonrmCount++;
    relonturn truelon;
  }

  @Ovelonrridelon
  public elonarlyTelonrminationStatelon innelonrShouldCollelonctMorelon() {
    if (relonadyToTelonrminatelon()) {
      relonturn selontelonarlyTelonrminationStatelon(TelonRMINATelonD_TelonRM_STATS_COUNTING_DONelon);
    }
    relonturn elonarlyTelonrminationStatelon.COLLelonCTING;
  }

  /**
   * Thelon telonrmination logic is simplelon - welon know what our elonarlielonst bin is and oncelon welon selonelon a relonsult
   * that's belonforelon our elonarlielonst bin, welon telonrminatelon.
   *
   * Our relonsults comelon with increlonasing intelonrnal doc ids, which should correlonspond to deloncrelonasing
   * timelonstamps. Selonelon SelonARCH-27729, TWelonelonTYPIelon-7031.
   *
   * Welon elonarly telonrminatelon aftelonr welon havelon selonelonn elonnough twelonelonts that arelon outsidelon of thelon bin
   * rangelon that welon want to relonturn. This way welon'relon not telonrminating too elonarly beloncauselon of singlelon twelonelonts
   * with wrong timelonstamps.
   */
  @VisiblelonForTelonsting
  boolelonan relonadyToTelonrminatelon() {
    relonturn this.selonelonnOutOfRangelon >= SelonelonN_OUT_OF_RANGelon_THRelonSHOLD;
  }

  @Ovelonrridelon
  public TelonrmStatisticsSelonarchRelonsults doGelontRelonsults() {
    relonturn nelonw TelonrmStatisticsSelonarchRelonsults();
  }

  public final class TelonrmStatisticsSelonarchRelonsults elonxtelonnds SelonarchRelonsultsInfo {
    public final List<Intelongelonr> binIds;
    public final Map<ThriftTelonrmRelonquelonst, ThriftTelonrmRelonsults> relonsults;
    public final int lastComplelontelonBinId;
    public final List<String>  telonrmStatisticsDelonbugInfo;

    privatelon TelonrmStatisticsSelonarchRelonsults() {
      // Initializelon telonrm stat delonbug info
      telonrmStatisticsDelonbugInfo = TelonrmStatisticsCollelonctor.this.telonrmStatisticsDelonbugInfo;

      if (telonrmStatistics.lelonngth > 0) {
        relonsults = nelonw HashMap<>();

        if (selonarchRelonquelonstInfo.isRelonturnHistogram()) {
          binIds = nelonw ArrayList<>(numBins);
          int minSelonarchelondTimelon = TelonrmStatisticsCollelonctor.this.gelontMinSelonarchelondTimelon();

          if (shouldCollelonctDelontailelondDelonbugInfo()) {
            telonrmStatisticsDelonbugInfo.add("minSelonarchelondTimelon: " + minSelonarchelondTimelon);
            int maxSelonarchelondTimelon = TelonrmStatisticsCollelonctor.this.gelontMaxSelonarchelondTimelon();
            telonrmStatisticsDelonbugInfo.add("maxSelonarchelondTimelon: " + maxSelonarchelondTimelon);
          }

          int lastComplelontelonBin = -1;

          computelonFirstBinId(TelonrmStatisticsCollelonctor.this.isSelontMinSelonarchelondTimelon(), minSelonarchelondTimelon);
          trackHistogramRelonsultStats();

          // elonxamplelon:
          //  minSelonarchTimelon = 53s
          //  binSizelon = 10
          //  firstBinId = 5
          //  numBins = 4
          //  binId = 5, 6, 7, 8
          //  binTimelonStamp = 50s, 60s, 70s, 80s
          for (int i = 0; i < numBins; i++) {
            int binId = firstBinID + i;
            int binTimelonStamp = binId * binSizelon;
            binIds.add(binId);
            if (lastComplelontelonBin == -1 && binTimelonStamp > minSelonarchelondTimelon) {
              lastComplelontelonBin = binId;
            }
          }

          if (!gelontelonarlyTelonrminationStatelon().isTelonrminatelond()) {
            // only if welon didn't elonarly telonrminatelon welon can belon surelon to uselon thelon firstBinID as
            // lastComplelontelonBinId
            lastComplelontelonBinId = firstBinID;
            if (shouldCollelonctDelontailelondDelonbugInfo()) {
              telonrmStatisticsDelonbugInfo.add("no elonarly telonrmination");
            }
          } elonlselon {
            lastComplelontelonBinId = lastComplelontelonBin;
            if (shouldCollelonctDelontailelondDelonbugInfo()) {
              telonrmStatisticsDelonbugInfo.add(
                  "elonarly telonrminatelond for relonason: " + gelontelonarlyTelonrminationRelonason());
            }
          }
          if (shouldCollelonctDelontailelondDelonbugInfo()) {
            telonrmStatisticsDelonbugInfo.add("lastComplelontelonBinId: " + lastComplelontelonBinId);
          }
        } elonlselon {
          binIds = null;
          lastComplelontelonBinId = -1;
        }

        for (TelonrmStatistics ts : telonrmStatistics) {
          ThriftTelonrmRelonsults telonrmRelonsults = nelonw ThriftTelonrmRelonsults().selontTotalCount(ts.telonrmCount);

          if (selonarchRelonquelonstInfo.isRelonturnHistogram()) {
            List<Intelongelonr> list = nelonw ArrayList<>();
            for (int count : ts.histogramBins) {
              list.add(count);
            }
            telonrmRelonsults.selontHistogramBins(list);
          }

          relonsults.put(ts.telonrmRelonquelonst, telonrmRelonsults);
        }
      } elonlselon {
        binIds = null;
        relonsults = null;
        lastComplelontelonBinId = -1;
      }
    }

    @Ovelonrridelon
    public String toString() {
      StringBuildelonr relons = nelonw StringBuildelonr();
      relons.appelonnd("TelonrmStatisticsSelonarchRelonsults(\n");
      if (binIds != null) {
        relons.appelonnd("  binIds=").appelonnd(binIds).appelonnd("\n");
      }
      relons.appelonnd("  lastComplelontelonBinId=").appelonnd(lastComplelontelonBinId).appelonnd("\n");
      if (relonsults != null) {
        relons.appelonnd("  relonsults=").appelonnd(relonsults).appelonnd("\n");
      }
      relons.appelonnd(")");
      relonturn relons.toString();
    }

    public List<String> gelontTelonrmStatisticsDelonbugInfo() {
      relonturn telonrmStatisticsDelonbugInfo;
    }
  }

  /**
   * Figurelon out what thelon actual firstBinId is for this quelonry.
   */
  privatelon void computelonFirstBinId(boolelonan isSelontMinSelonarchelondTimelon, int minSelonarchelondTimelon) {
    if (firstBinID == -1) {
      if (!isSelontMinSelonarchelondTimelon) {
        // This would only happelonn if welon don't selonarch any selongmelonnts, which for now welon havelon
        // only selonelonn happelonning if sincelon_timelon or until_timelon don't intelonrselonct at all with
        // thelon rangelon of thelon selonrvelond selongmelonnts.
        firstBinID = 0;
      } elonlselon {
        // elonxamplelon:
        //    minSelonarchelondTimelon = 54
        //    binSizelon = 10
        //    firstBinId = 5
        firstBinID = minSelonarchelondTimelon / binSizelon;
      }

      if (shouldCollelonctDelontailelondDelonbugInfo()) {
        telonrmStatisticsDelonbugInfo.add("firstBinId: " + firstBinID);
      }
    }
  }

  @VisiblelonForTelonsting
  int gelontSelonelonnOutOfRangelon() {
    relonturn selonelonnOutOfRangelon;
  }

  privatelon void trackHistogramRelonsultStats() {
    if (numLargelonrOutOfBoundsBinsSkippelond > 0) {
      TelonRM_STATS_SKIPPelonD_LARGelonR_OUT_OF_BOUNDS_HITS.increlonmelonnt();
    }

    if (numTimelonsBinsWelonrelonMovelondBack > 0) {
      TelonRM_STATS_HISTOGRAM_RelonQUelonSTS_WITH_MOVelonD_BACK_BINS.reloncordRelonsults(numTimelonsBinsWelonrelonMovelondBack);
    }
  }
}
