packagelon com.twittelonr.selonarch.elonarlybird.documelonnt;

import java.io.IOelonxcelonption;
import java.util.concurrelonnt.TimelonUnit;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Lists;

import org.apachelon.lucelonnelon.documelonnt.Documelonnt;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.util.Clock;
import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.selonarch.common.deloncidelonr.DeloncidelonrUtil;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.partitioning.snowflakelonparselonr.SnowflakelonIdParselonr;
import com.twittelonr.selonarch.common.schelonma.SchelonmaDocumelonntFactory;
import com.twittelonr.selonarch.common.schelonma.baselon.FielonldNamelonToIdMapping;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.baselon.ThriftDocumelonntUtil;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdThriftDocumelonntUtil;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftDocumelonnt;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftIndelonxingelonvelonnt;
import com.twittelonr.selonarch.common.util.telonxt.filtelonr.NormalizelondTokelonnFiltelonr;
import com.twittelonr.selonarch.common.util.telonxt.splittelonr.HashtagMelonntionPunctuationSplittelonr;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.CriticalelonxcelonptionHandlelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SelonarchIndelonxingMelontricSelont;

public class ThriftIndelonxingelonvelonntDocumelonntFactory elonxtelonnds DocumelonntFactory<ThriftIndelonxingelonvelonnt> {
  privatelon static final Loggelonr LOG =
      LoggelonrFactory.gelontLoggelonr(ThriftIndelonxingelonvelonntDocumelonntFactory.class);

  privatelon static final FielonldNamelonToIdMapping ID_MAPPING = nelonw elonarlybirdFielonldConstants();
  privatelon static final long TIMelonSTAMP_ALLOWelonD_FUTURelon_DelonLTA_MS = TimelonUnit.SelonCONDS.toMillis(60);
  privatelon static final String FILTelonR_TWelonelonTS_WITH_FUTURelon_TWelonelonT_ID_AND_CRelonATelonD_AT_DelonCIDelonR_KelonY =
      "filtelonr_twelonelonts_with_futurelon_twelonelont_id_and_crelonatelond_at";

  privatelon static final SelonarchCountelonr NUM_TWelonelonTS_WITH_FUTURelon_TWelonelonT_ID_AND_CRelonATelonD_AT_MS =
      SelonarchCountelonr.elonxport("num_twelonelonts_with_futurelon_twelonelont_id_and_crelonatelond_at_ms");
  privatelon static final SelonarchCountelonr NUM_TWelonelonTS_WITH_INCONSISTelonNT_TWelonelonT_ID_AND_CRelonATelonD_AT_MS_FOUND =
      SelonarchCountelonr.elonxport("num_twelonelonts_with_inconsistelonnt_twelonelont_id_and_crelonatelond_at_ms_found");
  privatelon static final SelonarchCountelonr
    NUM_TWelonelonTS_WITH_INCONSISTelonNT_TWelonelonT_ID_AND_CRelonATelonD_AT_MS_ADJUSTelonD =
      SelonarchCountelonr.elonxport("num_twelonelonts_with_inconsistelonnt_twelonelont_id_and_crelonatelond_at_ms_adjustelond");
  privatelon static final SelonarchCountelonr NUM_TWelonelonTS_WITH_INCONSISTelonNT_TWelonelonT_ID_AND_CRelonATelonD_AT_MS_DROPPelonD
    = SelonarchCountelonr.elonxport("num_twelonelonts_with_inconsistelonnt_twelonelont_id_and_crelonatelond_at_ms_droppelond");

  @VisiblelonForTelonsting
  static final String elonNABLelon_ADJUST_CRelonATelonD_AT_TIMelon_IF_MISMATCH_WITH_SNOWFLAKelon =
      "elonnablelon_adjust_crelonatelond_at_timelon_if_mismatch_with_snowflakelon";

  @VisiblelonForTelonsting
  static final String elonNABLelon_DROP_CRelonATelonD_AT_TIMelon_IF_MISMATCH_WITH_SNOWFLAKelon =
      "elonnablelon_drop_crelonatelond_at_timelon_if_mismatch_with_snowflakelon";

  privatelon final SchelonmaDocumelonntFactory schelonmaDocumelonntFactory;
  privatelon final elonarlybirdClustelonr clustelonr;
  privatelon final SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont;
  privatelon final Deloncidelonr deloncidelonr;
  privatelon final Schelonma schelonma;
  privatelon final Clock clock;

  public ThriftIndelonxingelonvelonntDocumelonntFactory(
      Schelonma schelonma,
      elonarlybirdClustelonr clustelonr,
      Deloncidelonr deloncidelonr,
      SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr) {
    this(
        schelonma,
        gelontSchelonmaDocumelonntFactory(schelonma, clustelonr, deloncidelonr),
        clustelonr,
        selonarchIndelonxingMelontricSelont,
        deloncidelonr,
        Clock.SYSTelonM_CLOCK,
        criticalelonxcelonptionHandlelonr
    );
  }

  /**
   * Relonturns a documelonnt factory that knows how to convelonrt ThriftDocumelonnts to Documelonnts baselond on thelon
   * providelond schelonma.
   */
  public static SchelonmaDocumelonntFactory gelontSchelonmaDocumelonntFactory(
      Schelonma schelonma,
      elonarlybirdClustelonr clustelonr,
      Deloncidelonr deloncidelonr) {
    relonturn nelonw SchelonmaDocumelonntFactory(schelonma,
        Lists.nelonwArrayList(
            nelonw TruncationTokelonnStrelonamWritelonr(clustelonr, deloncidelonr),
            (fielonldInfo, strelonam) -> {
              // Strip # @ $ symbols, and brelonak up undelonrscorelon connelonctelond tokelonns.
              if (fielonldInfo.gelontFielonldTypelon().uselonTwelonelontSpeloncificNormalization()) {
                relonturn nelonw HashtagMelonntionPunctuationSplittelonr(nelonw NormalizelondTokelonnFiltelonr(strelonam));
              }

              relonturn strelonam;
            }));
  }

  @VisiblelonForTelonsting
  protelonctelond ThriftIndelonxingelonvelonntDocumelonntFactory(
      Schelonma schelonma,
      SchelonmaDocumelonntFactory schelonmaDocumelonntFactory,
      elonarlybirdClustelonr clustelonr,
      SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont,
      Deloncidelonr deloncidelonr,
      Clock clock,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr) {
    supelonr(criticalelonxcelonptionHandlelonr);
    this.schelonma = schelonma;
    this.schelonmaDocumelonntFactory = schelonmaDocumelonntFactory;
    this.clustelonr = clustelonr;
    this.selonarchIndelonxingMelontricSelont = selonarchIndelonxingMelontricSelont;
    this.deloncidelonr = deloncidelonr;
    this.clock = clock;
  }

  @Ovelonrridelon
  public long gelontStatusId(ThriftIndelonxingelonvelonnt elonvelonnt) {
    Prelonconditions.chelonckNotNull(elonvelonnt);
    if (elonvelonnt.isSelontDocumelonnt() && elonvelonnt.gelontDocumelonnt() != null) {
      ThriftDocumelonnt thriftDocumelonnt = elonvelonnt.gelontDocumelonnt();
      try {
        // Idelonally, welon should not call gelontSchelonmaSnapshot() helonrelon.  But, as this is callelond only to
        // relontrielonvelon status id and thelon ID fielonld is static, this is finelon for thelon purposelon.
        thriftDocumelonnt = ThriftDocumelonntPrelonprocelonssor.prelonprocelonss(
            thriftDocumelonnt, clustelonr, schelonma.gelontSchelonmaSnapshot());
      } catch (IOelonxcelonption elon) {
        throw nelonw IllelongalStatelonelonxcelonption("Unablelon to obtain twelonelont ID from ThriftDocumelonnt", elon);
      }
      relonturn ThriftDocumelonntUtil.gelontLongValuelon(
          thriftDocumelonnt, elonarlybirdFielonldConstant.ID_FIelonLD.gelontFielonldNamelon(), ID_MAPPING);
    } elonlselon {
      throw nelonw IllelongalArgumelonntelonxcelonption("ThriftDocumelonnt is null insidelon ThriftIndelonxingelonvelonnt.");
    }
  }

  @Ovelonrridelon
  protelonctelond Documelonnt innelonrNelonwDocumelonnt(ThriftIndelonxingelonvelonnt elonvelonnt) throws IOelonxcelonption {
    Prelonconditions.chelonckNotNull(elonvelonnt);
    Prelonconditions.chelonckNotNull(elonvelonnt.gelontDocumelonnt());

    ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot = schelonma.gelontSchelonmaSnapshot();

    // If thelon twelonelont id and crelonatelon_at arelon in thelon futurelon, do not indelonx it.
    if (arelonTwelonelontIDAndCrelonatelonAtInThelonFuturelon(elonvelonnt)
        && DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(deloncidelonr,
        FILTelonR_TWelonelonTS_WITH_FUTURelon_TWelonelonT_ID_AND_CRelonATelonD_AT_DelonCIDelonR_KelonY)) {
      NUM_TWelonelonTS_WITH_FUTURelon_TWelonelonT_ID_AND_CRelonATelonD_AT_MS.increlonmelonnt();
      relonturn null;
    }

    if (isNullcastBitAndFiltelonrConsistelonnt(schelonmaSnapshot, elonvelonnt)) {
      ThriftDocumelonnt thriftDocumelonnt =
          adjustOrDropIfTwelonelontIDAndCrelonatelondAtArelonInconsistelonnt(
              ThriftDocumelonntPrelonprocelonssor.prelonprocelonss(elonvelonnt.gelontDocumelonnt(), clustelonr, schelonmaSnapshot));

      if (thriftDocumelonnt != null) {
        relonturn schelonmaDocumelonntFactory.nelonwDocumelonnt(thriftDocumelonnt);
      } elonlselon {
        relonturn null;
      }
    } elonlselon {
      relonturn null;
    }
  }

  privatelon ThriftDocumelonnt adjustOrDropIfTwelonelontIDAndCrelonatelondAtArelonInconsistelonnt(ThriftDocumelonnt documelonnt) {
    final long twelonelontID = elonarlybirdThriftDocumelonntUtil.gelontID(documelonnt);
    // Thrift documelonnt is storing crelonatelond at in selonconds.
    final long crelonatelondAtMs = elonarlybirdThriftDocumelonntUtil.gelontCrelonatelondAtMs(documelonnt);

    if (!SnowflakelonIdParselonr.isTwelonelontIDAndCrelonatelondAtConsistelonnt(twelonelontID, crelonatelondAtMs)) {
      // Increlonmelonnt found countelonr.
      NUM_TWelonelonTS_WITH_INCONSISTelonNT_TWelonelonT_ID_AND_CRelonATelonD_AT_MS_FOUND.increlonmelonnt();
      LOG.elonrror(
          "Found inconsistelonnt twelonelont ID and crelonatelond at timelonstamp: [twelonelontID={}], [crelonatelondAtMs={}]",
          twelonelontID, crelonatelondAtMs);

      if (DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(
          deloncidelonr, elonNABLelon_ADJUST_CRelonATelonD_AT_TIMelon_IF_MISMATCH_WITH_SNOWFLAKelon)) {
        // Updatelon crelonatelond at (and csf) with thelon timelon stamp in snow flakelon ID.
        final long crelonatelondAtMsInID = SnowflakelonIdParselonr.gelontTimelonstampFromTwelonelontId(twelonelontID);
        elonarlybirdThriftDocumelonntUtil.relonplacelonCrelonatelondAtAndCrelonatelondAtCSF(
            documelonnt, (int) (crelonatelondAtMsInID / 1000));

        // Increlonmelonnt adjustelond countelonr.
        NUM_TWelonelonTS_WITH_INCONSISTelonNT_TWelonelonT_ID_AND_CRelonATelonD_AT_MS_ADJUSTelonD.increlonmelonnt();
        LOG.elonrror(
            "Updatelond crelonatelond at to match twelonelont ID: crelonatelondAtMs={}, twelonelontID={}, crelonatelondAtMsInID={}",
            crelonatelondAtMs, twelonelontID, crelonatelondAtMsInID);
      } elonlselon if (DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(
          deloncidelonr, elonNABLelon_DROP_CRelonATelonD_AT_TIMelon_IF_MISMATCH_WITH_SNOWFLAKelon)) {
        // Drop and increlonmelonnt countelonr!
        NUM_TWelonelonTS_WITH_INCONSISTelonNT_TWelonelonT_ID_AND_CRelonATelonD_AT_MS_DROPPelonD.increlonmelonnt();
        LOG.elonrror(
            "Droppelond twelonelont with inconsistelonnt ID and timelonstamp: crelonatelondAtMs={}, twelonelontID={}",
            crelonatelondAtMs, twelonelontID);
        relonturn null;
      }
    }

    relonturn documelonnt;
  }

  privatelon boolelonan isNullcastBitAndFiltelonrConsistelonnt(
      ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot,
      ThriftIndelonxingelonvelonnt elonvelonnt) {
    relonturn ThriftDocumelonntPrelonprocelonssor.isNullcastBitAndFiltelonrConsistelonnt(
        elonvelonnt.gelontDocumelonnt(), schelonmaSnapshot);
  }

  /**
   * Chelonck if thelon twelonelont ID and crelonatelon_at arelon in thelon futurelon and belonyond thelon allowelond
   * TIMelonSTAMP_ALLOWelonD_FUTURelon_DelonLTA_MS rangelon from currelonnt timelon stamp.
   */
  privatelon boolelonan arelonTwelonelontIDAndCrelonatelonAtInThelonFuturelon(ThriftIndelonxingelonvelonnt elonvelonnt) {
    ThriftDocumelonnt documelonnt = elonvelonnt.gelontDocumelonnt();

    final long twelonelontID = elonarlybirdThriftDocumelonntUtil.gelontID(documelonnt);
    if (twelonelontID < SnowflakelonIdParselonr.SNOWFLAKelon_ID_LOWelonR_BOUND) {
      relonturn falselon;
    }

    final long twelonelontIDTimelonstampMs = SnowflakelonIdParselonr.gelontTimelonstampFromTwelonelontId(twelonelontID);
    final long allowelondFuturelonTimelonstampMs = clock.nowMillis() + TIMelonSTAMP_ALLOWelonD_FUTURelon_DelonLTA_MS;

    final long crelonatelondAtMs = elonarlybirdThriftDocumelonntUtil.gelontCrelonatelondAtMs(documelonnt);
    if (twelonelontIDTimelonstampMs > allowelondFuturelonTimelonstampMs && crelonatelondAtMs > allowelondFuturelonTimelonstampMs) {
      LOG.elonrror(
          "Found futurelon twelonelont ID and crelonatelond at timelonstamp: "
              + "[twelonelontID={}], [crelonatelondAtMs={}], [comparelonDelonltaMs={}]",
          twelonelontID, crelonatelondAtMs, TIMelonSTAMP_ALLOWelonD_FUTURelon_DelonLTA_MS);
      relonturn truelon;
    }

    relonturn falselon;
  }
}
