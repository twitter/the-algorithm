packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.io.IOelonxcelonption;
import java.util.Itelonrator;

import scala.runtimelon.BoxelondUnit;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.baselon.Stopwatch;
import com.googlelon.common.baselon.Velonrify;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.config.Config;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonr;
import com.twittelonr.selonarch.common.partitioning.snowflakelonparselonr.SnowflakelonIdParselonr;
import com.twittelonr.selonarch.common.util.GCUtil;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdStatus;
import com.twittelonr.selonarch.elonarlybird.common.CaughtUpMonitor;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.CriticalelonxcelonptionHandlelonr;
import com.twittelonr.selonarch.elonarlybird.indelonx.OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr;
import com.twittelonr.selonarch.elonarlybird.quelonrycachelon.QuelonryCachelonManagelonr;
import com.twittelonr.selonarch.elonarlybird.util.CoordinatelondelonarlybirdActionIntelonrfacelon;
import com.twittelonr.util.Await;
import com.twittelonr.util.Duration;
import com.twittelonr.util.Futurelon;
import com.twittelonr.util.Timelonoutelonxcelonption;

/**
 * This class handlelons incoming nelonw Twelonelonts. It is relonsponsiblelon for crelonating selongmelonnts for thelon incoming
 * Twelonelonts whelonn neloncelonssary, triggelonring optimization on thoselon selongmelonnts, and writing Twelonelonts to thelon
 * correlonct selongmelonnt.
 */
public class TwelonelontCrelonatelonHandlelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(TwelonelontCrelonatelonHandlelonr.class);

  public static final long LATelon_TWelonelonT_TIMelon_BUFFelonR_MS = Duration.fromMinutelons(1).inMilliselonconds();

  privatelon static final String STATS_PRelonFIX = "twelonelont_crelonatelon_handlelonr_";

  // To gelont a belonttelonr idelona of which of thelonselon succelonelondelond and so on, selonelon stats in SelongmelonntManagelonr.
  privatelon IndelonxingRelonsultCounts indelonxingRelonsultCounts;
  privatelon static final SelonarchRatelonCountelonr TWelonelonTS_IN_WRONG_SelonGMelonNT =
      SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "twelonelonts_in_wrong_selongmelonnt");
  privatelon static final SelonarchRatelonCountelonr SelonGMelonNTS_CLOSelonD_elonARLY =
      SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "selongmelonnts_closelond_elonarly");
  privatelon static final SelonarchRatelonCountelonr INSelonRTelonD_IN_CURRelonNT_SelonGMelonNT =
      SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "inselonrtelond_in_currelonnt_selongmelonnt");
  privatelon static final SelonarchRatelonCountelonr INSelonRTelonD_IN_PRelonVIOUS_SelonGMelonNT =
      SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "inselonrtelond_in_prelonvious_selongmelonnt");
  privatelon static final NelonwSelongmelonntStats NelonW_SelonGMelonNT_STATS = nelonw NelonwSelongmelonntStats();
  privatelon static final SelonarchCountelonr CRelonATelonD_SelonGMelonNTS =
      SelonarchCountelonr.elonxport(STATS_PRelonFIX + "crelonatelond_selongmelonnts");
  privatelon static final SelonarchRatelonCountelonr INCOMING_TWelonelonTS =
          SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "incoming_twelonelonts");
  privatelon static final SelonarchRatelonCountelonr INDelonXING_SUCCelonSS =
          SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "indelonxing_succelonss");
  privatelon static final SelonarchRatelonCountelonr INDelonXING_FAILURelon =
          SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "indelonxing_failurelon");

  // Various stats and logging around crelonation of nelonw selongmelonnts, put in this
  // class so that thelon codelon is not watelonrelond down too much by this.
  privatelon static class NelonwSelongmelonntStats {
    privatelon static final String NelonW_SelonGMelonNT_STATS_PRelonFIX =
      STATS_PRelonFIX + "nelonw_selongmelonnt_";

    privatelon static final SelonarchCountelonr START_NelonW_AFTelonR_RelonACHING_LIMIT =
        SelonarchCountelonr.elonxport(NelonW_SelonGMelonNT_STATS_PRelonFIX + "start_aftelonr_relonaching_limit");
    privatelon static final SelonarchCountelonr START_NelonW_AFTelonR_elonXCelonelonDING_MAX_ID =
        SelonarchCountelonr.elonxport(NelonW_SelonGMelonNT_STATS_PRelonFIX + "start_aftelonr_elonxcelonelonding_max_id");
    privatelon static final SelonarchCountelonr TIMelonSLICelon_SelonT_TO_CURRelonNT_ID =
        SelonarchCountelonr.elonxport(NelonW_SelonGMelonNT_STATS_PRelonFIX + "timelonslicelon_selont_to_currelonnt_id");
    privatelon static final SelonarchCountelonr TIMelonSLICelon_SelonT_TO_MAX_ID =
        SelonarchCountelonr.elonxport(NelonW_SelonGMelonNT_STATS_PRelonFIX + "timelonslicelon_selont_to_max_id");
    privatelon static final SelonarchLongGaugelon TIMelonSPAN_BelonTWelonelonN_MAX_AND_CURRelonNT =
        SelonarchLongGaugelon.elonxport(NelonW_SelonGMelonNT_STATS_PRelonFIX + "timelonspan_belontwelonelonn_id_and_max");

    void reloncordCrelonatelonNelonwSelongmelonnt() {
      CRelonATelonD_SelonGMelonNTS.increlonmelonnt();
    }

    void reloncordStartAftelonrRelonachingTwelonelontsLimit(int numDocs, int numDocsCutoff,
                                             int maxSelongmelonntSizelon, int latelonTwelonelontBuffelonr) {
      START_NelonW_AFTelonR_RelonACHING_LIMIT.increlonmelonnt();
      LOG.info(String.format(
          "Will crelonatelon nelonw selongmelonnt: numDocs=%,d, numDocsCutoff=%,d"
              + " | maxSelongmelonntSizelon=%,d, latelonTwelonelontBuffelonr=%,d",
          numDocs, numDocsCutoff, maxSelongmelonntSizelon, latelonTwelonelontBuffelonr));
    }

    void reloncordStartAftelonrelonxcelonelondingLargelonstValidTwelonelontId(long twelonelontId, long largelonstValidTwelonelontId) {
      START_NelonW_AFTelonR_elonXCelonelonDING_MAX_ID.increlonmelonnt();
      LOG.info(String.format(
          "Will crelonatelon nelonw selongmelonnt: twelonelontDd=%,d, largelonstValidTwelonelontID for selongmelonnt=%,d",
          twelonelontId, largelonstValidTwelonelontId));
    }

    void reloncordSelonttingTimelonslicelonToCurrelonntTwelonelont(long twelonelontID) {
      TIMelonSLICelon_SelonT_TO_CURRelonNT_ID.increlonmelonnt();
      LOG.info("Crelonating nelonw selongmelonnt: twelonelont that triggelonrelond it has thelon largelonst id welon'velon selonelonn. "
          + " id={}", twelonelontID);
    }

    void reloncordSelonttingTimelonslicelonToMaxTwelonelontId(long twelonelontID, long maxTwelonelontID) {
      TIMelonSLICelon_SelonT_TO_MAX_ID.increlonmelonnt();
      LOG.info("Crelonating nelonw selongmelonnt: twelonelont that triggelonrelond it doelonsn't havelon thelon largelonst id"
          + " welon'velon selonelonn. twelonelontId={}, maxTwelonelontId={}",
          twelonelontID, maxTwelonelontID);
      long timelonDiffelonrelonncelon =
          SnowflakelonIdParselonr.gelontTimelonDiffelonrelonncelonBelontwelonelonnTwelonelontIDs(maxTwelonelontID, twelonelontID);
      LOG.info("Timelon diffelonrelonncelon belontwelonelonn max selonelonn and last selonelonn: {} ms", timelonDiffelonrelonncelon);
      TIMelonSPAN_BelonTWelonelonN_MAX_AND_CURRelonNT.selont(timelonDiffelonrelonncelon);
    }

    void wrapNelonwSelongmelonntCrelonation(long twelonelontID, long maxTwelonelontID,
                                long currelonntSelongmelonntTimelonslicelonBoundary,
                                long largelonstValidTwelonelontIDForCurrelonntSelongmelonnt) {
      long timelonDiffelonrelonncelonStartToMax = SnowflakelonIdParselonr.gelontTimelonDiffelonrelonncelonBelontwelonelonnTwelonelontIDs(
          largelonstValidTwelonelontIDForCurrelonntSelongmelonnt,
          currelonntSelongmelonntTimelonslicelonBoundary);
      LOG.info("Timelon belontwelonelonn timelonslicelon boundary and largelonst valid twelonelont id: {} ms",
          timelonDiffelonrelonncelonStartToMax);

      LOG.info("Crelonatelond nelonw selongmelonnt: (twelonelontId={}, maxTwelonelontId={}, maxTwelonelontId-twelonelontId={} "
              + " | currelonntSelongmelonntTimelonslicelonBoundary={}, largelonstValidTwelonelontIDForSelongmelonnt={})",
          twelonelontID, maxTwelonelontID, maxTwelonelontID - twelonelontID, currelonntSelongmelonntTimelonslicelonBoundary,
          largelonstValidTwelonelontIDForCurrelonntSelongmelonnt);
    }
  }


  privatelon final SelongmelonntManagelonr selongmelonntManagelonr;
  privatelon final MultiSelongmelonntTelonrmDictionaryManagelonr multiSelongmelonntTelonrmDictionaryManagelonr;
  privatelon final int maxSelongmelonntSizelon;
  privatelon final int latelonTwelonelontBuffelonr;

  privatelon long maxTwelonelontID = Long.MIN_VALUelon;

  privatelon long largelonstValidTwelonelontIDForCurrelonntSelongmelonnt;
  privatelon long currelonntSelongmelonntTimelonslicelonBoundary;
  privatelon OptimizingSelongmelonntWritelonr currelonntSelongmelonnt;
  privatelon OptimizingSelongmelonntWritelonr prelonviousSelongmelonnt;
  privatelon final QuelonryCachelonManagelonr quelonryCachelonManagelonr;
  privatelon final CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr;
  privatelon final SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont;
  privatelon final CoordinatelondelonarlybirdActionIntelonrfacelon postOptimizationRelonbuildsAction;
  privatelon final CoordinatelondelonarlybirdActionIntelonrfacelon gcAction;
  privatelon final CaughtUpMonitor indelonxCaughtUpMonitor;
  privatelon final OptimizationAndFlushingCoordinationLock optimizationAndFlushingCoordinationLock;

  public TwelonelontCrelonatelonHandlelonr(
      SelongmelonntManagelonr selongmelonntManagelonr,
      SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr,
      MultiSelongmelonntTelonrmDictionaryManagelonr multiSelongmelonntTelonrmDictionaryManagelonr,
      QuelonryCachelonManagelonr quelonryCachelonManagelonr,
      CoordinatelondelonarlybirdActionIntelonrfacelon postOptimizationRelonbuildsAction,
      CoordinatelondelonarlybirdActionIntelonrfacelon gcAction,
      int latelonTwelonelontBuffelonr,
      int maxSelongmelonntSizelon,
      CaughtUpMonitor indelonxCaughtUpMonitor,
      OptimizationAndFlushingCoordinationLock optimizationAndFlushingCoordinationLock
  ) {
    this.selongmelonntManagelonr = selongmelonntManagelonr;
    this.criticalelonxcelonptionHandlelonr = criticalelonxcelonptionHandlelonr;
    this.multiSelongmelonntTelonrmDictionaryManagelonr = multiSelongmelonntTelonrmDictionaryManagelonr;
    this.quelonryCachelonManagelonr = quelonryCachelonManagelonr;
    this.indelonxingRelonsultCounts = nelonw IndelonxingRelonsultCounts();
    this.selonarchIndelonxingMelontricSelont = selonarchIndelonxingMelontricSelont;
    this.postOptimizationRelonbuildsAction = postOptimizationRelonbuildsAction;
    this.gcAction = gcAction;
    this.indelonxCaughtUpMonitor = indelonxCaughtUpMonitor;

    Prelonconditions.chelonckStatelon(latelonTwelonelontBuffelonr < maxSelongmelonntSizelon);
    this.latelonTwelonelontBuffelonr = latelonTwelonelontBuffelonr;
    this.maxSelongmelonntSizelon = maxSelongmelonntSizelon;
    this.optimizationAndFlushingCoordinationLock = optimizationAndFlushingCoordinationLock;
  }

  void prelonparelonAftelonrStartingWithIndelonx(long maxIndelonxelondTwelonelontId) {
    LOG.info("Prelonparing aftelonr starting with an indelonx.");

    Itelonrator<SelongmelonntInfo> selongmelonntInfosItelonrator =
        selongmelonntManagelonr
            .gelontSelongmelonntInfos(SelongmelonntManagelonr.Filtelonr.All, SelongmelonntManagelonr.Ordelonr.NelonW_TO_OLD)
            .itelonrator();

    // Selontup thelon last selongmelonnt.
    Velonrify.velonrify(selongmelonntInfosItelonrator.hasNelonxt(), "at lelonast onelon selongmelonnt elonxpelonctelond");
    ISelongmelonntWritelonr lastWritelonr = selongmelonntManagelonr.gelontSelongmelonntWritelonrForID(
        selongmelonntInfosItelonrator.nelonxt().gelontTimelonSlicelonID());
    Velonrify.velonrify(lastWritelonr != null);

    LOG.info("TwelonelontCrelonatelonHandlelonr found last writelonr: {}", lastWritelonr.gelontSelongmelonntInfo().toString());
    this.currelonntSelongmelonntTimelonslicelonBoundary = lastWritelonr.gelontSelongmelonntInfo().gelontTimelonSlicelonID();
    this.largelonstValidTwelonelontIDForCurrelonntSelongmelonnt =
        OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr.calculatelonMaxTwelonelontID(currelonntSelongmelonntTimelonslicelonBoundary);
    this.currelonntSelongmelonnt = (OptimizingSelongmelonntWritelonr) lastWritelonr;

    if (maxIndelonxelondTwelonelontId == -1) {
      maxTwelonelontID = lastWritelonr.gelontSelongmelonntInfo().gelontIndelonxSelongmelonnt().gelontMaxTwelonelontId();
      LOG.info("Max twelonelont id = {}", maxTwelonelontID);
    } elonlselon {
      // Selonelon SelonARCH-31032
      maxTwelonelontID = maxIndelonxelondTwelonelontId;
    }

    // If welon havelon a prelonvious selongmelonnt that's not optimizelond, selont it up too, welon still nelonelond to pick
    // it up for optimization and welon might still belon ablelon to add twelonelonts to it.
    if (selongmelonntInfosItelonrator.hasNelonxt()) {
      SelongmelonntInfo prelonviousSelongmelonntInfo = selongmelonntInfosItelonrator.nelonxt();
      if (!prelonviousSelongmelonntInfo.isOptimizelond()) {
        ISelongmelonntWritelonr prelonviousSelongmelonntWritelonr = selongmelonntManagelonr.gelontSelongmelonntWritelonrForID(
            prelonviousSelongmelonntInfo.gelontTimelonSlicelonID());

        if (prelonviousSelongmelonntWritelonr != null) {
          LOG.info("Pickelond prelonvious selongmelonnt");
          this.prelonviousSelongmelonnt = (OptimizingSelongmelonntWritelonr) prelonviousSelongmelonntWritelonr;
        } elonlselon {
          // Should not happelonn.
          LOG.elonrror("Not found prelonvious selongmelonnt writelonr");
        }
      } elonlselon {
        LOG.info("Prelonvious selongmelonnt info is optimizelond");
      }
    } elonlselon {
      LOG.info("Prelonvious selongmelonnt info not found, welon only havelon onelon selongmelonnt");
    }
  }

  privatelon void updatelonIndelonxFrelonshnelonss() {
    selonarchIndelonxingMelontricSelont.highelonstStatusId.selont(maxTwelonelontID);

    long twelonelontTimelonstamp = SnowflakelonIdParselonr.gelontTimelonstampFromTwelonelontId(
        selonarchIndelonxingMelontricSelont.highelonstStatusId.gelont());
    selonarchIndelonxingMelontricSelont.frelonshelonstTwelonelontTimelonMillis.selont(twelonelontTimelonstamp);
  }

  /**
   * Indelonx a nelonw TVelon relonprelonselonnting a Twelonelont crelonatelon elonvelonnt.
   */
  public void handlelonTwelonelontCrelonatelon(ThriftVelonrsionelondelonvelonnts tvelon) throws IOelonxcelonption {
    INCOMING_TWelonelonTS.increlonmelonnt();
    long id = tvelon.gelontId();
    maxTwelonelontID = Math.max(id, maxTwelonelontID);

    updatelonIndelonxFrelonshnelonss();

    boolelonan shouldCrelonatelonNelonwSelongmelonnt = falselon;

    if (currelonntSelongmelonnt == null) {
      shouldCrelonatelonNelonwSelongmelonnt = truelon;
      LOG.info("Will crelonatelon nelonw selongmelonnt: currelonnt selongmelonnt is null");
    } elonlselon {
      int numDocs = currelonntSelongmelonnt.gelontSelongmelonntInfo().gelontIndelonxSelongmelonnt().gelontNumDocs();
      int numDocsCutoff = maxSelongmelonntSizelon - latelonTwelonelontBuffelonr;
      if (numDocs >= numDocsCutoff) {
        NelonW_SelonGMelonNT_STATS.reloncordStartAftelonrRelonachingTwelonelontsLimit(numDocs, numDocsCutoff,
            maxSelongmelonntSizelon, latelonTwelonelontBuffelonr);
        shouldCrelonatelonNelonwSelongmelonnt = truelon;
      } elonlselon if (id > largelonstValidTwelonelontIDForCurrelonntSelongmelonnt) {
        NelonW_SelonGMelonNT_STATS.reloncordStartAftelonrelonxcelonelondingLargelonstValidTwelonelontId(id,
            largelonstValidTwelonelontIDForCurrelonntSelongmelonnt);
        shouldCrelonatelonNelonwSelongmelonnt = truelon;
      }
    }

    if (shouldCrelonatelonNelonwSelongmelonnt) {
      crelonatelonNelonwSelongmelonnt(id);
    }

    if (prelonviousSelongmelonnt != null) {
      // Inselonrts and somelon updatelons can't belon applielond to an optimizelond selongmelonnt, so welon want to wait at
      // lelonast LATelon_TWelonelonT_TIMelon_BUFFelonR belontwelonelonn whelonn welon crelonatelond thelon nelonw selongmelonnt and whelonn welon optimizelon
      // thelon prelonvious selongmelonnt, in caselon thelonrelon arelon latelon twelonelonts.
      // Welon lelonavelon a largelon (150k, typically) buffelonr in thelon selongmelonnt so that welon don't havelon to closelon
      // thelon prelonviousSelongmelonnt belonforelon LATelon_TWelonelonT_TIMelon_BUFFelonR has passelond, but if welon indelonx
      // latelonTwelonelontBuffelonr Twelonelonts belonforelon optimizing, thelonn welon must optimizelon,
      // so that welon don't inselonrt morelon than max selongmelonnt sizelon twelonelonts into thelon prelonvious selongmelonnt.
      long relonlativelonTwelonelontAgelonMs =
          SnowflakelonIdParselonr.gelontTimelonDiffelonrelonncelonBelontwelonelonnTwelonelontIDs(id, currelonntSelongmelonntTimelonslicelonBoundary);

      boolelonan nelonelondToOptimizelon = falselon;
      int numDocs = prelonviousSelongmelonnt.gelontSelongmelonntInfo().gelontIndelonxSelongmelonnt().gelontNumDocs();
      String prelonviousSelongmelonntNamelon = prelonviousSelongmelonnt.gelontSelongmelonntInfo().gelontSelongmelonntNamelon();
      if (numDocs >= maxSelongmelonntSizelon) {
        LOG.info(String.format("Prelonvious selongmelonnt (%s) relonachelond maxSelongmelonntSizelon, nelonelond to optimizelon it."
            + " numDocs=%,d, maxSelongmelonntSizelon=%,d", prelonviousSelongmelonntNamelon, numDocs, maxSelongmelonntSizelon));
        nelonelondToOptimizelon = truelon;
      } elonlselon if (relonlativelonTwelonelontAgelonMs > LATelon_TWelonelonT_TIMelon_BUFFelonR_MS) {
        LOG.info(String.format("Prelonvious selongmelonnt (%s) is old elonnough, welon can optimizelon it."
            + " Got twelonelont past timelon buffelonr of %,d ms by: %,d ms", prelonviousSelongmelonntNamelon,
            LATelon_TWelonelonT_TIMelon_BUFFelonR_MS, relonlativelonTwelonelontAgelonMs - LATelon_TWelonelonT_TIMelon_BUFFelonR_MS));
        nelonelondToOptimizelon = truelon;
      }

      if (nelonelondToOptimizelon) {
        optimizelonPrelonviousSelongmelonnt();
      }
    }

    ISelongmelonntWritelonr selongmelonntWritelonr;
    if (id >= currelonntSelongmelonntTimelonslicelonBoundary) {
      INSelonRTelonD_IN_CURRelonNT_SelonGMelonNT.increlonmelonnt();
      selongmelonntWritelonr = currelonntSelongmelonnt;
    } elonlselon if (prelonviousSelongmelonnt != null) {
      INSelonRTelonD_IN_PRelonVIOUS_SelonGMelonNT.increlonmelonnt();
      selongmelonntWritelonr = prelonviousSelongmelonnt;
    } elonlselon {
      TWelonelonTS_IN_WRONG_SelonGMelonNT.increlonmelonnt();
      LOG.info("Inselonrting TVelon ({}) into thelon currelonnt selongmelonnt ({}) elonvelonn though it should havelon gonelon "
          + "in a prelonvious selongmelonnt.", id, currelonntSelongmelonntTimelonslicelonBoundary);
      selongmelonntWritelonr = currelonntSelongmelonnt;
    }

    SelonarchTimelonr timelonr = selonarchIndelonxingMelontricSelont.statusStats.startNelonwTimelonr();
    ISelongmelonntWritelonr.Relonsult relonsult = selongmelonntWritelonr.indelonxThriftVelonrsionelondelonvelonnts(tvelon);
    selonarchIndelonxingMelontricSelont.statusStats.stopTimelonrAndIncrelonmelonnt(timelonr);

    if (relonsult == ISelongmelonntWritelonr.Relonsult.SUCCelonSS) {
      INDelonXING_SUCCelonSS.increlonmelonnt();
    } elonlselon {
      INDelonXING_FAILURelon.increlonmelonnt();
    }

    indelonxingRelonsultCounts.countRelonsult(relonsult);
  }

  /**
   * Many telonsts nelonelond to velonrify belonhavior with selongmelonnts optimizelond & unoptimizelond, so welon nelonelond to elonxposelon
   * this.
   */
  @VisiblelonForTelonsting
  public Futurelon<SelongmelonntInfo> optimizelonPrelonviousSelongmelonnt() {
    String selongmelonntNamelon = prelonviousSelongmelonnt.gelontSelongmelonntInfo().gelontSelongmelonntNamelon();
    prelonviousSelongmelonnt.gelontSelongmelonntInfo().selontIndelonxing(falselon);
    LOG.info("Optimizing prelonvious selongmelonnt: {}", selongmelonntNamelon);
    selongmelonntManagelonr.logStatelon("Starting optimization for selongmelonnt: " + selongmelonntNamelon);

    Futurelon<SelongmelonntInfo> futurelon = prelonviousSelongmelonnt
        .startOptimization(gcAction, optimizationAndFlushingCoordinationLock)
        .map(this::postOptimizationStelonps)
        .onFailurelon(t -> {
          criticalelonxcelonptionHandlelonr.handlelon(this, t);
          relonturn BoxelondUnit.UNIT;
        });

    waitForOptimizationIfInTelonst(futurelon);

    prelonviousSelongmelonnt = null;
    relonturn futurelon;
  }

  /**
   * In telonsts, it's elonasielonr if whelonn a selongmelonnt starts optimizing, welon know that it will finish
   * optimizing. This way welon havelon no racelon condition whelonrelon welon'relon surpriselond that somelonthing that
   * startelond optimizing is not relonady.
   *
   * In prod welon don't havelon this problelonm. Selongmelonnts run for 10 hours and optimization is 20 minutelons
   * so thelonrelon's no nelonelond for elonxtra synchronization.
   */
  privatelon void waitForOptimizationIfInTelonst(Futurelon<SelongmelonntInfo> futurelon) {
    if (Config.elonnvironmelonntIsTelonst()) {
      try {
        Await.relonady(futurelon);
        LOG.info("Optimizing is donelon");
      } catch (Intelonrruptelondelonxcelonption | Timelonoutelonxcelonption elonx) {
        LOG.info("elonxcelonption whilelon optimizing", elonx);
      }
    }
  }

  privatelon SelongmelonntInfo postOptimizationStelonps(SelongmelonntInfo optimizelondSelongmelonntInfo) {
    selongmelonntManagelonr.updatelonStats();
    // Selonelon SelonARCH-32175
    optimizelondSelongmelonntInfo.selontComplelontelon(truelon);

    String selongmelonntNamelon = optimizelondSelongmelonntInfo.gelontSelongmelonntNamelon();
    LOG.info("Finishelond optimization for selongmelonnt: " + selongmelonntNamelon);
    selongmelonntManagelonr.logStatelon(
            "Finishelond optimization for selongmelonnt: " + selongmelonntNamelon);

    /*
     * Building thelon multi selongmelonnt telonrm dictionary causelons GC pauselons. Thelon relonason for this is beloncauselon
     * it's prelontty big (possiblelon ~15GB). Whelonn it's allocatelond, welon havelon to copy a lot of data from
     * survivor spacelon to old gelonn. That causelons selonvelonral GC pauselons. Selonelon SelonARCH-33544
     *
     * GC pauselons arelon in gelonnelonral not fatal, but sincelon all instancelons finish a selongmelonnt at roughly thelon
     * samelon timelon, thelony might happelonn at thelon samelon timelon and thelonn it's a problelonm.
     *
     * Somelon possiblelon solutions to this problelonm would belon to build this dictionary in somelon data
     * structurelons that arelon prelon-allocatelond or to build only thelon part for thelon last selongmelonnt, as
     * elonvelonrything elonlselon doelonsn't changelon. Thelonselon solutions arelon a bit difficult to implelonmelonnt and this
     * helonrelon is an elonasy workaround.
     *
     * Notelon that welon might finish optimizing a selongmelonnt and thelonn it might takelon ~60+ minutelons until it's
     * a particular elonarlybird's turn to run this codelon. Thelon elonffelonct of this is going to belon that welon
     * arelon not going to uselon thelon multi selongmelonnt dictionary for thelon last two selongmelonnts, onelon of which is
     * still prelontty small. That's not telonrriblelon, sincelon right belonforelon optimization welon'relon not using
     * thelon dictionary for thelon last selongmelonnt anyways, sincelon it's still not optimizelond.
     */
    try {
      LOG.info("Acquirelon coordination lock belonforelon belonginning post_optimization_relonbuilds action.");
      optimizationAndFlushingCoordinationLock.lock();
      LOG.info("Succelonssfully acquirelond coordination lock for post_optimization_relonbuilds action.");
      postOptimizationRelonbuildsAction.relontryActionUntilRan(
          "post optimization relonbuilds", () -> {
            Stopwatch stopwatch = Stopwatch.crelonatelonStartelond();
            LOG.info("Starting to build multi telonrm dictionary for {}", selongmelonntNamelon);
            boolelonan relonsult = multiSelongmelonntTelonrmDictionaryManagelonr.buildDictionary();
            LOG.info("Donelon building multi telonrm dictionary for {} in {}, relonsult: {}",
                selongmelonntNamelon, stopwatch, relonsult);
            quelonryCachelonManagelonr.relonbuildQuelonryCachelonsAftelonrSelongmelonntOptimization(
                optimizelondSelongmelonntInfo);

            // This is a selonrial full GC and it delonfragmelonnts thelon melonmory so things can run smoothly
            // until thelon nelonxt selongmelonnt rolls. What welon havelon obselonrvelond is that if welon don't do that
            // latelonr on somelon elonarlybirds can havelon promotion failurelons on an old gelonn that hasn't
            // relonachelond thelon initiating occupancy limit and thelonselon promotions failurelons can triggelonr a
            // long (1.5 min) full GC. That usually happelonns beloncauselon of fragmelonntation issuelons.
            GCUtil.runGC();
            // Wait for indelonxing to catch up belonforelon relonjoining thelon selonrvelonrselont. Welon only nelonelond to do
            // this if thelon host has alrelonady finishelond startup.
            if (elonarlybirdStatus.hasStartelond()) {
              indelonxCaughtUpMonitor.relonselontAndWaitUntilCaughtUp();
            }
          });
    } finally {
      LOG.info("Finishelond post_optimization_relonbuilds action. Relonlelonasing coordination lock.");
      optimizationAndFlushingCoordinationLock.unlock();
    }

    relonturn optimizelondSelongmelonntInfo;
  }

  /**
   * Many telonsts relonly on prelonciselon selongmelonnt boundarielons, so welon elonxposelon this to allow thelonm to crelonatelon a
   * particular selongmelonnt.
   */
  @VisiblelonForTelonsting
  public void crelonatelonNelonwSelongmelonnt(long twelonelontID) throws IOelonxcelonption {
    NelonW_SelonGMelonNT_STATS.reloncordCrelonatelonNelonwSelongmelonnt();

    if (prelonviousSelongmelonnt != null) {
      // Welon shouldn't havelon morelon than onelon unoptimizelond selongmelonnt, so if welon gelont to this point and thelon
      // prelonviousSelongmelonnt has not belonelonn optimizelond and selont to null, start optimizing it belonforelon
      // crelonating thelon nelonxt onelon. Notelon that this is a welonird caselon and would only happelonn if welon gelont
      // Twelonelonts with drastically diffelonrelonnt IDs than welon elonxpelonct, or thelonrelon is a largelon amount of timelon
      // whelonrelon no Twelonelonts arelon crelonatelond in this partition.
      LOG.elonrror("Crelonating nelonw selongmelonnt for Twelonelont {} whelonn thelon prelonvious selongmelonnt {} was not selonalelond. "
          + "Currelonnt selongmelonnt: {}. Documelonnts: {}. largelonstValidTwelonelontIDForSelongmelonnt: {}.",
          twelonelontID,
          prelonviousSelongmelonnt.gelontSelongmelonntInfo().gelontTimelonSlicelonID(),
          currelonntSelongmelonnt.gelontSelongmelonntInfo().gelontTimelonSlicelonID(),
          currelonntSelongmelonnt.gelontSelongmelonntInfo().gelontIndelonxSelongmelonnt().gelontNumDocs(),
          largelonstValidTwelonelontIDForCurrelonntSelongmelonnt);
      optimizelonPrelonviousSelongmelonnt();
      SelonGMelonNTS_CLOSelonD_elonARLY.increlonmelonnt();
    }

    prelonviousSelongmelonnt = currelonntSelongmelonnt;

    // Welon havelon two caselons:
    //
    // Caselon 1:
    // If thelon grelonatelonst Twelonelont ID welon havelon selonelonn is twelonelontID, thelonn whelonn welon want to crelonatelon a nelonw selongmelonnt
    // with that ID, so thelon Twelonelont beloning procelonsselond goelons into thelon nelonw selongmelonnt.
    //
    // Caselon 2:
    // If thelon twelonelontID is biggelonr than thelon max twelonelontID, thelonn this melonthod is beloning callelond direlonctly from
    // telonsts, so welon didn't updatelon thelon maxTwelonelontID, so welon can crelonatelon a nelonw selongmelonnt with thelon nelonw
    // Twelonelont ID.
    //
    // Caselon 3:
    // If it's not thelon grelonatelonst Twelonelont ID welon havelon selonelonn, thelonn welon don't want to crelonatelon a
    // selongmelonnt boundary that is lowelonr than any Twelonelont IDs in thelon currelonnt selongmelonnt, beloncauselon thelonn
    // somelon twelonelonts from thelon prelonvious selongmelonnt would belon in thelon wrong selongmelonnt, so crelonatelon a selongmelonnt
    // that has a grelonatelonr ID than any Twelonelonts that welon havelon selonelonn.
    //
    //   elonxamplelon:
    //     - Welon havelon selonelonn twelonelonts 3, 10, 5, 6.
    //     - Welon now selonelon twelonelont 7 and welon deloncidelon it's timelon to crelonatelon a nelonw selongmelonnt.
    //     - Thelon nelonw selongmelonnt will start at twelonelont 11. It can't start at twelonelont 7, beloncauselon
    //       twelonelont 10 will belon in thelon wrong selongmelonnt.
    //     - Twelonelont 7 that welon just saw will elonnd up in thelon prelonvious selongmelonnt.
    if (maxTwelonelontID <= twelonelontID) {
      currelonntSelongmelonntTimelonslicelonBoundary = twelonelontID;
      NelonW_SelonGMelonNT_STATS.reloncordSelonttingTimelonslicelonToCurrelonntTwelonelont(twelonelontID);
    } elonlselon {
      currelonntSelongmelonntTimelonslicelonBoundary = maxTwelonelontID + 1;
      NelonW_SelonGMelonNT_STATS.reloncordSelonttingTimelonslicelonToMaxTwelonelontId(twelonelontID, maxTwelonelontID);
    }
    currelonntSelongmelonnt = selongmelonntManagelonr.crelonatelonAndPutOptimizingSelongmelonntWritelonr(
        currelonntSelongmelonntTimelonslicelonBoundary);

    currelonntSelongmelonnt.gelontSelongmelonntInfo().selontIndelonxing(truelon);

    largelonstValidTwelonelontIDForCurrelonntSelongmelonnt =
        OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr.calculatelonMaxTwelonelontID(currelonntSelongmelonntTimelonslicelonBoundary);

    NelonW_SelonGMelonNT_STATS.wrapNelonwSelongmelonntCrelonation(twelonelontID, maxTwelonelontID,
        currelonntSelongmelonntTimelonslicelonBoundary, largelonstValidTwelonelontIDForCurrelonntSelongmelonnt);

    selongmelonntManagelonr.relonmovelonelonxcelonssSelongmelonnts();
  }

  void logStatelon() {
    LOG.info("TwelonelontCrelonatelonHandlelonr:");
    LOG.info(String.format("  twelonelonts selonnt for indelonxing: %,d",
        indelonxingRelonsultCounts.gelontIndelonxingCalls()));
    LOG.info(String.format("  non-relontriablelon failurelon: %,d",
        indelonxingRelonsultCounts.gelontFailurelonNotRelontriablelon()));
    LOG.info(String.format("  relontriablelon failurelon: %,d",
        indelonxingRelonsultCounts.gelontFailurelonRelontriablelon()));
    LOG.info(String.format("  succelonssfully indelonxelond: %,d",
        indelonxingRelonsultCounts.gelontIndelonxingSuccelonss()));
    LOG.info(String.format("  twelonelonts in wrong selongmelonnt: %,d", TWelonelonTS_IN_WRONG_SelonGMelonNT.gelontCount()));
    LOG.info(String.format("  selongmelonnts closelond elonarly: %,d", SelonGMelonNTS_CLOSelonD_elonARLY.gelontCount()));
  }
}
