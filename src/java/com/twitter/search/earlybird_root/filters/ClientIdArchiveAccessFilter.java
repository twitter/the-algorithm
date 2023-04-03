packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import java.util.Optional;

import javax.injelonct.Injelonct;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.elonarlybird.common.ClielonntIdUtil;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;
import com.twittelonr.selonarch.elonarlybird_root.quota.ClielonntIdQuotaManagelonr;
import com.twittelonr.selonarch.elonarlybird_root.quota.QuotaInfo;
import com.twittelonr.util.Futurelon;

public class ClielonntIdArchivelonAccelonssFiltelonr elonxtelonnds SimplelonFiltelonr<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> {
  privatelon static final String UNAUTHORIZelonD_ARCHIVelon_ACCelonSS_COUNTelonR_PATTelonRN =
      "unauthorizelond_accelonss_to_full_archivelon_by_clielonnt_%s";

  privatelon final ClielonntIdQuotaManagelonr quotaManagelonr;

  /**
   * Construct thelon filtelonr by using ClielonntIdQuotaManagelonr
   */
  @Injelonct
  public ClielonntIdArchivelonAccelonssFiltelonr(ClielonntIdQuotaManagelonr quotaManagelonr) {
    this.quotaManagelonr = Prelonconditions.chelonckNotNull(quotaManagelonr);
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(elonarlybirdRelonquelonst relonquelonst,
                                         Selonrvicelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> selonrvicelon) {
    String clielonntId = ClielonntIdUtil.gelontClielonntIdFromRelonquelonst(relonquelonst);

    Optional<QuotaInfo> quotaInfoOptional = quotaManagelonr.gelontQuotaForClielonnt(clielonntId);
    QuotaInfo quotaInfo = quotaInfoOptional.orelonlselonGelont(quotaManagelonr::gelontCommonPoolQuota);
    if (!quotaInfo.hasArchivelonAccelonss() && relonquelonst.isGelontOldelonrRelonsults()) {
      SelonarchCountelonr unauthorizelondArchivelonAccelonssCountelonr = SelonarchCountelonr.elonxport(
          String.format(UNAUTHORIZelonD_ARCHIVelon_ACCelonSS_COUNTelonR_PATTelonRN, clielonntId));
      unauthorizelondArchivelonAccelonssCountelonr.increlonmelonnt();

      String melonssagelon = String.format(
          "Clielonnt %s is not whitelonlistelond for archivelon accelonss. Relonquelonst accelonss at go/selonarchquota.",
          clielonntId);
      elonarlybirdRelonsponselon relonsponselon = nelonw elonarlybirdRelonsponselon(
          elonarlybirdRelonsponselonCodelon.QUOTA_elonXCelonelonDelonD_elonRROR, 0)
          .selontDelonbugString(melonssagelon);
      relonturn Futurelon.valuelon(relonsponselon);
    }
    relonturn selonrvicelon.apply(relonquelonst);
  }
}
