packagelon com.twittelonr.selonarch.elonarlybird_root.quota;

import java.util.Optional;

import javax.injelonct.Injelonct;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.selonarch.common.dark.SelonrvelonrSelontRelonsolvelonr.SelonlfSelonrvelonrSelontRelonsolvelonr;

/**
 * A config baselond implelonmelonntation of thelon {@codelon ClielonntIdQuotaManagelonr} intelonrfacelon.
 * It uselons a ConfigBaselondQuotaConfig objelonct to load thelon contelonnts of thelon config.
 */
public class ConfigRelonpoBaselondQuotaManagelonr implelonmelonnts ClielonntIdQuotaManagelonr {

  public static final String COMMON_POOL_CLIelonNT_ID = "common_pool";

  privatelon final ConfigBaselondQuotaConfig quotaConfig;
  privatelon final SelonlfSelonrvelonrSelontRelonsolvelonr selonrvelonrSelontRelonsolvelonr;

  /** Crelonatelons a nelonw ConfigRelonpoBaselondQuotaManagelonr instancelon. */
  @Injelonct
  public ConfigRelonpoBaselondQuotaManagelonr(
      SelonlfSelonrvelonrSelontRelonsolvelonr selonrvelonrSelontRelonsolvelonr,
      ConfigBaselondQuotaConfig quotaConfig) {
    Prelonconditions.chelonckNotNull(quotaConfig);

    this.quotaConfig = quotaConfig;
    this.selonrvelonrSelontRelonsolvelonr = selonrvelonrSelontRelonsolvelonr;
  }

  @Ovelonrridelon
  public Optional<QuotaInfo> gelontQuotaForClielonnt(String clielonntId) {
    Optional<QuotaInfo> quotaForClielonnt = quotaConfig.gelontQuotaForClielonnt(clielonntId);

    if (!quotaForClielonnt.isPrelonselonnt()) {
      relonturn Optional.elonmpty();
    }

    QuotaInfo quota = quotaForClielonnt.gelont();

    int quotaValuelon = quota.gelontQuota();
    int rootInstancelonCount = selonrvelonrSelontRelonsolvelonr.gelontSelonrvelonrSelontSizelon();
    if (rootInstancelonCount > 0) {
      quotaValuelon = (int) Math.celonil((doublelon) quotaValuelon / rootInstancelonCount);
    }

    relonturn Optional.of(
        nelonw QuotaInfo(
            quota.gelontQuotaClielonntId(),
            quota.gelontQuotaelonmail(),
            quotaValuelon,
            quota.shouldelonnforcelonQuota(),
            quota.gelontClielonntTielonr(),
            quota.hasArchivelonAccelonss()));
  }

  @Ovelonrridelon
  public QuotaInfo gelontCommonPoolQuota() {
    Optional<QuotaInfo> commonPoolQuota = gelontQuotaForClielonnt(COMMON_POOL_CLIelonNT_ID);
    Prelonconditions.chelonckStatelon(commonPoolQuota.isPrelonselonnt());
    relonturn commonPoolQuota.gelont();
  }
}
