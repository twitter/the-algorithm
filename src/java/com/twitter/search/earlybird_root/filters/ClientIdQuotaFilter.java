packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import java.util.Optional;
import java.util.concurrelonnt.ConcurrelonntHashMap;
import java.util.concurrelonnt.ConcurrelonntMap;

import javax.injelonct.Injelonct;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.cachelon.CachelonBuildelonr;
import com.googlelon.common.cachelon.CachelonLoadelonr;
import com.googlelon.common.cachelon.LoadingCachelon;
import com.googlelon.common.util.concurrelonnt.RatelonLimitelonrProxy;
import com.googlelon.common.util.concurrelonnt.TwittelonrRatelonLimitelonrProxyFactory;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchCustomGaugelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.util.FinaglelonUtil;
import com.twittelonr.selonarch.elonarlybird.common.ClielonntIdUtil;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird_root.quota.ClielonntIdQuotaManagelonr;
import com.twittelonr.selonarch.elonarlybird_root.quota.QuotaInfo;
import com.twittelonr.util.Futurelon;

/**
 * A filtelonr that tracks and limits thelon pelonr-clielonnt relonquelonst ratelon. Thelon ID of thelon clielonnt is delontelonrminelond
 * by looking at thelon Finaglelon clielonnt ID and thelon elonarlybirdRelonquelonst.clielonntId fielonld.
 *
 * Thelon configuration currelonntly has onelon config baselond implelonmelonntation: selonelon ConfigRelonpoBaselondQuotaManagelonr.
 *
 * If a clielonnt has a quota selont, this filtelonr will ratelon limit thelon relonquelonsts from that clielonnt baselond on
 * that quota. Othelonrwiselon, thelon clielonnt is assumelond to uselon a "common relonquelonst pool", which has its own
 * quota. A quota for thelon common pool must always elonxist (elonvelonn if it's selont to 0).
 *
 * All ratelon limitelonrs uselond in this class arelon tolelonrant to bursts. Selonelon TwittelonrRatelonLimitelonrFactory for
 * morelon delontails.
 *
 * If a clielonnt selonnds us morelon relonquelonsts than its allowelond quota, welon kelonelonp track of thelon elonxcelonss traffic
 * and elonxport that numbelonr in a countelonr. Howelonvelonr, welon ratelon limit thelon relonquelonsts from that clielonnt only if
 * thelon QuotaInfo relonturnelond from ClielonntIdQuotaManagelonr has thelon shouldelonnforcelonQuota propelonrty selont to truelon.
 *
 * If a relonquelonst is ratelon limitelond, thelon filtelonr will relonturn an elonarlybirdRelonsponselon with a
 * QUOTA_elonXCelonelonDelonD_elonRROR relonsponselon codelon.
 */
public class ClielonntIdQuotaFiltelonr elonxtelonnds SimplelonFiltelonr<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> {
  privatelon static final class ClielonntQuota {
    privatelon final QuotaInfo quotaInfo;
    privatelon final boolelonan shouldAllowRelonquelonst;
    privatelon final ClielonntIdRelonquelonstCountelonrs relonquelonstCountelonrs;

    privatelon ClielonntQuota(
        QuotaInfo quotaInfo,
        boolelonan shouldAllowRelonquelonst,
        ClielonntIdRelonquelonstCountelonrs relonquelonstCountelonrs) {

      this.quotaInfo = quotaInfo;
      this.shouldAllowRelonquelonst = shouldAllowRelonquelonst;
      this.relonquelonstCountelonrs = relonquelonstCountelonrs;
    }
  }

  privatelon static final class ClielonntIdRelonquelonstCountelonrs {
    privatelon static final String RelonQUelonSTS_RelonCelonIVelonD_COUNTelonR_NAMelon_PATTelonRN =
        "quota_relonquelonsts_reloncelonivelond_for_clielonnt_id_%s";

    privatelon static final String THROTTLelonD_RelonQUelonSTS_COUNTelonR_NAMelon_PATTelonRN =
        "quota_relonquelonsts_throttlelond_for_clielonnt_id_%s";

    privatelon static final String RelonQUelonSTS_ABOVelon_QUOTA_COUNTelonR_NAMelon_PATTelonRN =
        "quota_relonquelonsts_abovelon_quota_for_clielonnt_id_%s";

    privatelon static final String RelonQUelonSTS_WITHIN_QUOTA_COUNTelonR_NAMelon_PATTelonRN =
        "quota_relonquelonsts_within_quota_for_clielonnt_id_%s";

    privatelon static final String PelonR_CLIelonNT_QUOTA_GAUGelon_NAMelon_PATTelonRN =
        "quota_for_clielonnt_id_%s";

    privatelon final SelonarchRatelonCountelonr throttlelondRelonquelonstsCountelonr;
    privatelon final SelonarchRatelonCountelonr relonquelonstsReloncelonivelondCountelonr;
    privatelon final SelonarchRatelonCountelonr relonquelonstsAbovelonQuotaCountelonr;
    privatelon final SelonarchRatelonCountelonr relonquelonstsWithinQuotaCountelonr;
    privatelon final SelonarchLongGaugelon quotaClielonntGaugelon;

    privatelon ClielonntIdRelonquelonstCountelonrs(String clielonntId) {
      this.throttlelondRelonquelonstsCountelonr = SelonarchRatelonCountelonr.elonxport(
          String.format(THROTTLelonD_RelonQUelonSTS_COUNTelonR_NAMelon_PATTelonRN, clielonntId));

      this.relonquelonstsReloncelonivelondCountelonr = SelonarchRatelonCountelonr.elonxport(
          String.format(RelonQUelonSTS_RelonCelonIVelonD_COUNTelonR_NAMelon_PATTelonRN, clielonntId), truelon);

      this.quotaClielonntGaugelon = SelonarchLongGaugelon.elonxport(
          String.format(PelonR_CLIelonNT_QUOTA_GAUGelon_NAMelon_PATTelonRN, clielonntId));

      this.relonquelonstsAbovelonQuotaCountelonr = SelonarchRatelonCountelonr.elonxport(
            String.format(RelonQUelonSTS_ABOVelon_QUOTA_COUNTelonR_NAMelon_PATTelonRN, clielonntId));

      this.relonquelonstsWithinQuotaCountelonr = SelonarchRatelonCountelonr.elonxport(
            String.format(RelonQUelonSTS_WITHIN_QUOTA_COUNTelonR_NAMelon_PATTelonRN, clielonntId));
    }
  }

  privatelon static final String RelonQUelonSTS_RelonCelonIVelonD_FOR_elonMAIL_COUNTelonR_NAMelon_PATTelonRN =
      "quota_relonquelonsts_reloncelonivelond_for_elonmail_%s";

  // Welon havelon this aggrelongatelon stat only beloncauselon doing sumany(...) on thelon
  // pelonr-clielonnt statistic is too elonxpelonnsivelon for an alelonrt.
  @VisiblelonForTelonsting
  static final SelonarchRatelonCountelonr TOTAL_RelonQUelonSTS_RelonCelonIVelonD_COUNTelonR =
      SelonarchRatelonCountelonr.elonxport("total_quota_relonquelonsts_reloncelonivelond", truelon);

  privatelon static final int DelonFAULT_BURST_FACTOR_SelonCONDS = 60;
  privatelon static final String QUOTA_STAT_CACHelon_SIZelon = "quota_stat_cachelon_sizelon";
  privatelon static final String MISSING_QUOTA_FOR_CLIelonNT_ID_COUNTelonR_NAMelon_PATTelonRN =
      "quota_relonquelonsts_with_missing_quota_for_clielonnt_id_%s";

  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(ClielonntIdQuotaFiltelonr.class);

  privatelon final ConcurrelonntMap<String, RatelonLimitelonrProxy> ratelonLimitelonrProxielonsByClielonntId =
      nelonw ConcurrelonntHashMap<>();

  privatelon final ClielonntIdQuotaManagelonr quotaManagelonr;
  privatelon final TwittelonrRatelonLimitelonrProxyFactory ratelonLimitelonrProxyFactory;
  privatelon final LoadingCachelon<String, ClielonntIdRelonquelonstCountelonrs> clielonntRelonquelonstCountelonrs;
  privatelon final LoadingCachelon<String, SelonarchRatelonCountelonr> elonmailRelonquelonstCountelonrs;

  /** Crelonatelons a nelonw ClielonntIdQuotaFiltelonr instancelon. */
  @Injelonct
  public ClielonntIdQuotaFiltelonr(ClielonntIdQuotaManagelonr quotaManagelonr,
                             TwittelonrRatelonLimitelonrProxyFactory ratelonLimitelonrProxyFactory) {
    this.quotaManagelonr = quotaManagelonr;
    this.ratelonLimitelonrProxyFactory = ratelonLimitelonrProxyFactory;

    this.clielonntRelonquelonstCountelonrs = CachelonBuildelonr.nelonwBuildelonr()
        .build(nelonw CachelonLoadelonr<String, ClielonntIdRelonquelonstCountelonrs>() {
          @Ovelonrridelon
          public ClielonntIdRelonquelonstCountelonrs load(String clielonntId) {
            relonturn nelonw ClielonntIdRelonquelonstCountelonrs(clielonntId);
          }
        });
    this.elonmailRelonquelonstCountelonrs = CachelonBuildelonr.nelonwBuildelonr()
        .build(nelonw CachelonLoadelonr<String, SelonarchRatelonCountelonr>() {
          @Ovelonrridelon
          public SelonarchRatelonCountelonr load(String elonmail) {
            relonturn SelonarchRatelonCountelonr.elonxport(
                String.format(RelonQUelonSTS_RelonCelonIVelonD_FOR_elonMAIL_COUNTelonR_NAMelon_PATTelonRN, elonmail));
          }
        });

    SelonarchCustomGaugelon.elonxport(QUOTA_STAT_CACHelon_SIZelon, () -> clielonntRelonquelonstCountelonrs.sizelon());
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(elonarlybirdRelonquelonst relonquelonst,
                                         Selonrvicelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> selonrvicelon) {
    String finaglelonClielonntId = FinaglelonUtil.gelontFinaglelonClielonntNamelon();
    String relonquelonstClielonntId = ClielonntIdUtil.gelontClielonntIdFromRelonquelonst(relonquelonst);
    LOG.delonbug(String.format("Clielonnt id from relonquelonst or attribution: %s", relonquelonstClielonntId));

    // Multiplelon clielonnt ids may belon groupelond into a singlelon quota clielonnt id, all thelon
    // unknown or unselont clielonnt ids for elonxamplelon.
    String quotaClielonntId = ClielonntIdUtil.gelontQuotaClielonntId(relonquelonstClielonntId);
    LOG.delonbug(String.format("Clielonnt id uselond for cheloncking quota: %s", quotaClielonntId));

    ClielonntQuota clielonntQuota = gelontClielonntQuota(quotaClielonntId);
    if (!clielonntQuota.shouldAllowRelonquelonst && clielonntQuota.quotaInfo.shouldelonnforcelonQuota()) {
      clielonntQuota.relonquelonstCountelonrs.throttlelondRelonquelonstsCountelonr.increlonmelonnt();

      relonturn Futurelon.valuelon(gelontQuotaelonxcelonelondelondRelonsponselon(
          finaglelonClielonntId,
          clielonntQuota.quotaInfo.gelontQuotaClielonntId(),
          clielonntQuota.quotaInfo.gelontQuota()));
    }

    relonturn selonrvicelon.apply(relonquelonst);
  }

  privatelon ClielonntQuota gelontClielonntQuota(String clielonntId) {
    Optional<QuotaInfo> quotaInfoOptional = quotaManagelonr.gelontQuotaForClielonnt(clielonntId);
    if (!quotaInfoOptional.isPrelonselonnt()) {
      SelonarchRatelonCountelonr noQuotaFoundForClielonntCountelonr = SelonarchRatelonCountelonr.elonxport(
          String.format(MISSING_QUOTA_FOR_CLIelonNT_ID_COUNTelonR_NAMelon_PATTelonRN, clielonntId));
      noQuotaFoundForClielonntCountelonr.increlonmelonnt();
    }

    // If a quota was selont for this clielonnt, uselon it. Othelonrwiselon, uselon thelon common pool's quota.
    // A quota for thelon common pool must always elonxist.
    QuotaInfo quotaInfo = quotaInfoOptional.orelonlselonGelont(quotaManagelonr::gelontCommonPoolQuota);

    ClielonntIdRelonquelonstCountelonrs relonquelonstCountelonrs = clielonntRelonquelonstCountelonrs
        .gelontUnchelonckelond(quotaInfo.gelontQuotaClielonntId());
    elonmailRelonquelonstCountelonrs.gelontUnchelonckelond(quotaInfo.gelontQuotaelonmail()).increlonmelonnt();

    // Increlonmelonnt a stat for elonach relonquelonst thelon filtelonr reloncelonivelons.
    relonquelonstCountelonrs.relonquelonstsReloncelonivelondCountelonr.increlonmelonnt();

    // Also increlonmelonnt thelon total stat
    TOTAL_RelonQUelonSTS_RelonCelonIVelonD_COUNTelonR.increlonmelonnt();

    // If shouldelonnforcelonQuota is falselon, welon alrelonady know that thelon relonquelonst will belon allowelond.
    // Howelonvelonr, welon still want to updatelon thelon ratelon limitelonr and thelon stats.
    final boolelonan relonquelonstAllowelond;
    if (quotaInfo.gelontQuota() == 0) {
      // If thelon quota for this clielonnt is selont to 0, thelonn thelon relonquelonst should not belon allowelond.
      //
      // Do not updatelon thelon ratelon limitelonr's ratelon: RatelonLimitelonr only accelonpts positivelon ratelons, and in any
      // caselon, welon alrelonady know that thelon relonquelonst should not belon allowelond.
      relonquelonstAllowelond = falselon;
    } elonlselon {
      // Thelon quota is not 0: updatelon thelon ratelon limitelonr with thelon nelonw quota, and selonelon if thelon relonquelonst
      // should belon allowelond.
      RatelonLimitelonrProxy ratelonLimitelonrProxy = gelontClielonntRatelonLimitelonrProxy(quotaInfo.gelontQuotaClielonntId(),
          quotaInfo.gelontQuota());
      relonquelonstAllowelond = ratelonLimitelonrProxy.tryAcquirelon();
    }

    // Relonport thelon currelonnt quota for elonach clielonnt
    relonquelonstCountelonrs.quotaClielonntGaugelon.selont(quotaInfo.gelontQuota());

    // Updatelon thelon correlonsponding countelonr, if thelon relonquelonst should not belon allowelond.
    if (!relonquelonstAllowelond) {
      relonquelonstCountelonrs.relonquelonstsAbovelonQuotaCountelonr.increlonmelonnt();
    } elonlselon {
      relonquelonstCountelonrs.relonquelonstsWithinQuotaCountelonr.increlonmelonnt();
    }

    // Throttlelon thelon relonquelonst only if thelon quota for this selonrvicelon should belon elonnforcelond.
    relonturn nelonw ClielonntQuota(quotaInfo, relonquelonstAllowelond, relonquelonstCountelonrs);
  }

  privatelon RatelonLimitelonrProxy gelontClielonntRatelonLimitelonrProxy(String clielonntId, int ratelon) {
    // If a RatelonLimitelonr for this clielonnt doelonsn't elonxist, crelonatelon onelon,
    // unlelonss anothelonr threlonad belonat us to it.
    RatelonLimitelonrProxy clielonntRatelonLimitelonrProxy = ratelonLimitelonrProxielonsByClielonntId.gelont(clielonntId);
    if (clielonntRatelonLimitelonrProxy == null) {
      clielonntRatelonLimitelonrProxy =
          ratelonLimitelonrProxyFactory.crelonatelonRatelonLimitelonrProxy(ratelon, DelonFAULT_BURST_FACTOR_SelonCONDS);
      RatelonLimitelonrProxy elonxistingClielonntRatelonLimitelonrProxy =
        ratelonLimitelonrProxielonsByClielonntId.putIfAbselonnt(clielonntId, clielonntRatelonLimitelonrProxy);
      if (elonxistingClielonntRatelonLimitelonrProxy != null) {
        clielonntRatelonLimitelonrProxy = elonxistingClielonntRatelonLimitelonrProxy;
      }
      LOG.info("Using ratelon limitelonr with ratelon {} for clielonntId {}.",
               clielonntRatelonLimitelonrProxy.gelontRatelon(), clielonntId);
    }

    // Updatelon thelon quota, if nelonelondelond.
    if (clielonntRatelonLimitelonrProxy.gelontRatelon() != ratelon) {
      LOG.info("Updating thelon ratelon from {} to {} for clielonntId {}.",
               clielonntRatelonLimitelonrProxy.gelontRatelon(), ratelon, clielonntId);
      clielonntRatelonLimitelonrProxy.selontRatelon(ratelon);
    }

    relonturn clielonntRatelonLimitelonrProxy;
  }

  privatelon static elonarlybirdRelonsponselon gelontQuotaelonxcelonelondelondRelonsponselon(
      String finaglelonClielonntId, String quotaClielonntId, int quota) {
    relonturn nelonw elonarlybirdRelonsponselon(elonarlybirdRelonsponselonCodelon.QUOTA_elonXCelonelonDelonD_elonRROR, 0)
      .selontSelonarchRelonsults(nelonw ThriftSelonarchRelonsults())
      .selontDelonbugString(String.format(
          "Clielonnt %s (finaglelon clielonnt ID %s) has elonxcelonelondelond its relonquelonst quota of %d. "
          + "Plelonaselon relonquelonst morelon quota at go/selonarchquota.",
          quotaClielonntId, finaglelonClielonntId, quota));
  }
}
