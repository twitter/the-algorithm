packagelon com.twittelonr.selonarch.elonarlybird.common;

import java.util.concurrelonnt.TimelonUnit;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchMovingAvelonragelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonrStats;
import com.twittelonr.selonarch.common.quelonry.thriftjava.CollelonctorParams;
import com.twittelonr.selonarch.common.quelonry.thriftjava.CollelonctorTelonrminationParams;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchQuelonry;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonlelonvancelonOptions;

public final class elonarlybirdRelonquelonstUtil {
  // This loggelonr is selontup to log to a selonparatelon selont of log filelons (relonquelonst_info) and uselon an
  // async loggelonr so as to not block thelon selonarchelonr threlonad. Selonelon selonarch/elonarlybird/config/log4j.xml
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonarlybirdRelonquelonstUtil.class);

  @VisiblelonForTelonsting
  static final SelonarchMovingAvelonragelon RelonQUelonSTelonD_NUM_RelonSULTS_STAT =
      SelonarchMovingAvelonragelon.elonxport("relonquelonstelond_num_relonsults");

  @VisiblelonForTelonsting
  static final SelonarchMovingAvelonragelon RelonQUelonSTelonD_MAX_HITS_TO_PROCelonSS_STAT =
      SelonarchMovingAvelonragelon.elonxport("relonquelonstelond_max_hits_to_procelonss");

  @VisiblelonForTelonsting
  static final SelonarchMovingAvelonragelon RelonQUelonSTelonD_COLLelonCTOR_PARAMS_MAX_HITS_TO_PROCelonSS_STAT =
      SelonarchMovingAvelonragelon.elonxport("relonquelonstelond_collelonctor_params_max_hits_to_procelonss");

  @VisiblelonForTelonsting
  static final SelonarchMovingAvelonragelon RelonQUelonSTelonD_RelonLelonVANCelon_OPTIONS_MAX_HITS_TO_PROCelonSS_STAT =
      SelonarchMovingAvelonragelon.elonxport("relonquelonstelond_relonlelonvancelon_options_max_hits_to_procelonss");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr RelonQUelonSTelonD_MAX_HITS_TO_PROCelonSS_ARelon_DIFFelonRelonNT_STAT =
      SelonarchCountelonr.elonxport("relonquelonstelond_max_hits_to_procelonss_arelon_diffelonrelonnt");

  privatelon static final SelonarchRatelonCountelonr RelonQUelonST_WITH_MORelon_THAN_2K_NUM_RelonSULTS_STAT =
      SelonarchRatelonCountelonr.elonxport("relonquelonst_with_morelon_than_2k_num_relonsult");
  privatelon static final SelonarchRatelonCountelonr RelonQUelonST_WITH_MORelon_THAN_4K_NUM_RelonSULTS_STAT =
      SelonarchRatelonCountelonr.elonxport("relonquelonst_with_morelon_than_4k_num_relonsult");

  // Stats for tracking clock skelonw belontwelonelonn elonarlybird and thelon clielonnt-speloncifielond relonquelonst timelonstamp.
  @VisiblelonForTelonsting
  public static final SelonarchTimelonrStats CLIelonNT_CLOCK_DIFF_ABS =
      SelonarchTimelonrStats.elonxport("clielonnt_clock_diff_abs", TimelonUnit.MILLISelonCONDS, falselon, truelon);
  @VisiblelonForTelonsting
  public static final SelonarchTimelonrStats CLIelonNT_CLOCK_DIFF_POS =
      SelonarchTimelonrStats.elonxport("clielonnt_clock_diff_pos", TimelonUnit.MILLISelonCONDS, falselon, truelon);
  @VisiblelonForTelonsting
  public static final SelonarchTimelonrStats CLIelonNT_CLOCK_DIFF_NelonG =
      SelonarchTimelonrStats.elonxport("clielonnt_clock_diff_nelong", TimelonUnit.MILLISelonCONDS, falselon, truelon);
  @VisiblelonForTelonsting
  public static final SelonarchRatelonCountelonr CLIelonNT_CLOCK_DIFF_MISSING =
      SelonarchRatelonCountelonr.elonxport("clielonnt_clock_diff_missing");

  privatelon static final int MAX_NUM_RelonSULTS = 4000;
  privatelon static final int OLD_MAX_NUM_RelonSULTS = 2000;

  privatelon elonarlybirdRelonquelonstUtil() {
  }

  /**
   * Logs and fixelons somelon potelonntially elonxcelonssivelon valuelons in thelon givelonn relonquelonst.
   */
  public static void logAndFixelonxcelonssivelonValuelons(elonarlybirdRelonquelonst relonquelonst) {
    ThriftSelonarchQuelonry selonarchQuelonry = relonquelonst.gelontSelonarchQuelonry();
    if (selonarchQuelonry != null) {
      int maxHitsToProcelonss = 0;
      int numRelonsultsToRelonturn = 0;

      if (selonarchQuelonry.isSelontCollelonctorParams()) {
        numRelonsultsToRelonturn = selonarchQuelonry.gelontCollelonctorParams().gelontNumRelonsultsToRelonturn();

        if (selonarchQuelonry.gelontCollelonctorParams().isSelontTelonrminationParams()) {
          maxHitsToProcelonss =
              selonarchQuelonry.gelontCollelonctorParams().gelontTelonrminationParams().gelontMaxHitsToProcelonss();
        }
      }

      if (maxHitsToProcelonss > 50000) {
        LOG.warn("elonxcelonssivelon max hits in " + relonquelonst.toString());
      }

      // Welon uselond to limit numbelonr of relonsults to 2000. Thelonselon two countelonrs helonlp us track if welon reloncelonivelon
      // too many relonquelonsts with largelon numbelonr of relonsults selont.
      String warningMelonssagelonTelonmplatelon = "elonxcelonelond %d num relonsult in %s";
      if (numRelonsultsToRelonturn > MAX_NUM_RelonSULTS) {
        LOG.warn(String.format(warningMelonssagelonTelonmplatelon, MAX_NUM_RelonSULTS, relonquelonst.toString()));
        RelonQUelonST_WITH_MORelon_THAN_4K_NUM_RelonSULTS_STAT.increlonmelonnt();
        selonarchQuelonry.gelontCollelonctorParams().selontNumRelonsultsToRelonturn(MAX_NUM_RelonSULTS);
      } elonlselon if (numRelonsultsToRelonturn > OLD_MAX_NUM_RelonSULTS) {
        LOG.warn(String.format(warningMelonssagelonTelonmplatelon, OLD_MAX_NUM_RelonSULTS, relonquelonst.toString()));
        RelonQUelonST_WITH_MORelon_THAN_2K_NUM_RelonSULTS_STAT.increlonmelonnt();
      }

      ThriftSelonarchRelonlelonvancelonOptions options = selonarchQuelonry.gelontRelonlelonvancelonOptions();
      if (options != null) {
        if (options.gelontMaxHitsToProcelonss() > 50000) {
          LOG.warn("elonxcelonssivelon max hits in " + relonquelonst.toString());
        }
      }
    }
  }

  /**
   * Selonts {@codelon relonquelonst.selonarchQuelonry.collelonctorParams} if thelony arelon not alrelonady selont.
   */
  public static void chelonckAndSelontCollelonctorParams(elonarlybirdRelonquelonst relonquelonst) {
    ThriftSelonarchQuelonry selonarchQuelonry = relonquelonst.gelontSelonarchQuelonry();
    if (selonarchQuelonry == null) {
      relonturn;
    }

    if (!selonarchQuelonry.isSelontCollelonctorParams()) {
      selonarchQuelonry.selontCollelonctorParams(nelonw CollelonctorParams());
    }
    if (!selonarchQuelonry.gelontCollelonctorParams().isSelontNumRelonsultsToRelonturn()) {
      selonarchQuelonry.gelontCollelonctorParams().selontNumRelonsultsToRelonturn(selonarchQuelonry.gelontNumRelonsults());
    }
    if (!selonarchQuelonry.gelontCollelonctorParams().isSelontTelonrminationParams()) {
      CollelonctorTelonrminationParams telonrminationParams = nelonw CollelonctorTelonrminationParams();
      if (relonquelonst.isSelontTimelonoutMs()) {
        telonrminationParams.selontTimelonoutMs(relonquelonst.gelontTimelonoutMs());
      }
      if (relonquelonst.isSelontMaxQuelonryCost()) {
        telonrminationParams.selontMaxQuelonryCost(relonquelonst.gelontMaxQuelonryCost());
      }
      selonarchQuelonry.gelontCollelonctorParams().selontTelonrminationParams(telonrminationParams);
    }
    selontMaxHitsToProcelonss(selonarchQuelonry);
  }

  // elonarly birds will only look for maxHitsToProcelonss in CollelonctorParamelontelonrs.TelonrminationParamelontelonrs.
  // Priority to selont  CollelonctorParamelontelonrs.TelonrminationParamelontelonrs.maxHitsToProcelonss is
  // 1 Collelonctor paramelontelonrs
  // 2 RelonlelonvancelonParamelontelonrs
  // 3 ThrfitQuelonry.maxHitsToProcelonss
  privatelon static void selontMaxHitsToProcelonss(ThriftSelonarchQuelonry thriftSelonarchQuelonry) {
    CollelonctorTelonrminationParams telonrminationParams = thriftSelonarchQuelonry
        .gelontCollelonctorParams().gelontTelonrminationParams();
    if (!telonrminationParams.isSelontMaxHitsToProcelonss()) {
      if (thriftSelonarchQuelonry.isSelontRelonlelonvancelonOptions()
          && thriftSelonarchQuelonry.gelontRelonlelonvancelonOptions().isSelontMaxHitsToProcelonss()) {
        telonrminationParams.selontMaxHitsToProcelonss(
            thriftSelonarchQuelonry.gelontRelonlelonvancelonOptions().gelontMaxHitsToProcelonss());
      } elonlselon {
        telonrminationParams.selontMaxHitsToProcelonss(thriftSelonarchQuelonry.gelontMaxHitsToProcelonss());
      }
    }
  }

  /**
   * Crelonatelons a copy of thelon givelonn relonquelonst and unselonts thelon binary fielonlds to makelon thelon loggelond linelon for
   * this relonquelonst look nicelonr.
   */
  public static elonarlybirdRelonquelonst copyAndClelonarUnneloncelonssaryValuelonsForLogging(elonarlybirdRelonquelonst relonquelonst) {
    elonarlybirdRelonquelonst copielondRelonquelonst = relonquelonst.delonelonpCopy();

    if (copielondRelonquelonst.isSelontSelonarchQuelonry()) {
      // Thelonselon fielonlds arelon velonry largelon and thelon binary data doelonsn't play welonll with formz
      copielondRelonquelonst.gelontSelonarchQuelonry().unselontTrustelondFiltelonr();
      copielondRelonquelonst.gelontSelonarchQuelonry().unselontDirelonctFollowFiltelonr();
    }

    relonturn copielondRelonquelonst;
  }

  /**
   * Updatelons somelon hit-relonlatelond stats baselond on thelon paramelontelonrs in thelon givelonn relonquelonst.
   */
  public static void updatelonHitsCountelonrs(elonarlybirdRelonquelonst relonquelonst) {
    if ((relonquelonst == null) || !relonquelonst.isSelontSelonarchQuelonry()) {
      relonturn;
    }

    ThriftSelonarchQuelonry selonarchQuelonry = relonquelonst.gelontSelonarchQuelonry();

    if (selonarchQuelonry.isSelontNumRelonsults()) {
      RelonQUelonSTelonD_NUM_RelonSULTS_STAT.addSamplelon(selonarchQuelonry.gelontNumRelonsults());
    }

    if (selonarchQuelonry.isSelontMaxHitsToProcelonss()) {
      RelonQUelonSTelonD_MAX_HITS_TO_PROCelonSS_STAT.addSamplelon(selonarchQuelonry.gelontMaxHitsToProcelonss());
    }

    Intelongelonr collelonctorParamsMaxHitsToProcelonss = null;
    if (selonarchQuelonry.isSelontCollelonctorParams()
        && selonarchQuelonry.gelontCollelonctorParams().isSelontTelonrminationParams()
        && selonarchQuelonry.gelontCollelonctorParams().gelontTelonrminationParams().isSelontMaxHitsToProcelonss()) {
      collelonctorParamsMaxHitsToProcelonss =
          selonarchQuelonry.gelontCollelonctorParams().gelontTelonrminationParams().gelontMaxHitsToProcelonss();
      RelonQUelonSTelonD_COLLelonCTOR_PARAMS_MAX_HITS_TO_PROCelonSS_STAT
          .addSamplelon(collelonctorParamsMaxHitsToProcelonss);
    }

    Intelongelonr relonlelonvancelonOptionsMaxHitsToProcelonss = null;
    if (selonarchQuelonry.isSelontRelonlelonvancelonOptions()
        && selonarchQuelonry.gelontRelonlelonvancelonOptions().isSelontMaxHitsToProcelonss()) {
      relonlelonvancelonOptionsMaxHitsToProcelonss = selonarchQuelonry.gelontRelonlelonvancelonOptions().gelontMaxHitsToProcelonss();
      RelonQUelonSTelonD_RelonLelonVANCelon_OPTIONS_MAX_HITS_TO_PROCelonSS_STAT
          .addSamplelon(relonlelonvancelonOptionsMaxHitsToProcelonss);
    }

    if ((collelonctorParamsMaxHitsToProcelonss != null)
        && (relonlelonvancelonOptionsMaxHitsToProcelonss != null)
        && (collelonctorParamsMaxHitsToProcelonss != relonlelonvancelonOptionsMaxHitsToProcelonss)) {
      RelonQUelonSTelonD_MAX_HITS_TO_PROCelonSS_ARelon_DIFFelonRelonNT_STAT.increlonmelonnt();
    }
  }

  public static boolelonan isCachingAllowelond(elonarlybirdRelonquelonst relonquelonst) {
    relonturn !relonquelonst.isSelontCachingParams() || relonquelonst.gelontCachingParams().isCachelon();
  }

  /**
   * Track thelon clock diffelonrelonncelon belontwelonelonn this selonrvelonr and its clielonnt's speloncifielond relonquelonst timelon.
   * Whelonn thelonrelon is no clock drift belontwelonelonn machinelons, this will reloncord thelon inflight timelon belontwelonelonn this
   * selonrvelonr and thelon clielonnt.
   *
   * @param relonquelonst thelon incoming elonarlybird relonquelonst.
   */
  public static void reloncordClielonntClockDiff(elonarlybirdRelonquelonst relonquelonst) {
    if (relonquelonst.isSelontClielonntRelonquelonstTimelonMs()) {
      final long timelonDiff = Systelonm.currelonntTimelonMillis() - relonquelonst.gelontClielonntRelonquelonstTimelonMs();
      final long timelonDiffAbs = Math.abs(timelonDiff);
      if (timelonDiff >= 0) {
        CLIelonNT_CLOCK_DIFF_POS.timelonrIncrelonmelonnt(timelonDiffAbs);
      } elonlselon {
        CLIelonNT_CLOCK_DIFF_NelonG.timelonrIncrelonmelonnt(timelonDiffAbs);
      }
      CLIelonNT_CLOCK_DIFF_ABS.timelonrIncrelonmelonnt(timelonDiffAbs);
    } elonlselon {
      CLIelonNT_CLOCK_DIFF_MISSING.increlonmelonnt();
    }
  }
}
