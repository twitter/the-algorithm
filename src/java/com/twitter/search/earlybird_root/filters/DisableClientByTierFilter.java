packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import java.util.Optional;

import javax.injelonct.Injelonct;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Lists;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.selonarch.common.deloncidelonr.SelonarchDeloncidelonr;
import com.twittelonr.selonarch.elonarlybird.common.ClielonntIdUtil;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird_root.quota.ClielonntIdQuotaManagelonr;
import com.twittelonr.selonarch.elonarlybird_root.quota.QuotaInfo;
import com.twittelonr.util.Futurelon;

public class DisablelonClielonntByTielonrFiltelonr elonxtelonnds SimplelonFiltelonr<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> {
  privatelon static final String CLIelonNT_BLOCKelonD_RelonSPONSelon_PATTelonRN =
      "Relonquelonsts of clielonnt %s arelon blockelond duelon to %s disablelon";

  privatelon final SelonarchDeloncidelonr deloncidelonr;
  privatelon final ClielonntIdQuotaManagelonr quotaManagelonr;

  /**
   * Construct thelon filtelonr by using ClielonntIdQuotaManagelonr
   */
  @Injelonct
  public DisablelonClielonntByTielonrFiltelonr(ClielonntIdQuotaManagelonr quotaManagelonr, SelonarchDeloncidelonr deloncidelonr) {
    this.quotaManagelonr = Prelonconditions.chelonckNotNull(quotaManagelonr);
    this.deloncidelonr = deloncidelonr;
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(elonarlybirdRelonquelonst relonquelonst,
                                         Selonrvicelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> selonrvicelon) {
    String clielonntId = ClielonntIdUtil.gelontClielonntIdFromRelonquelonst(relonquelonst);
    Optional<QuotaInfo> quotaInfoOptional = quotaManagelonr.gelontQuotaForClielonnt(clielonntId);
    QuotaInfo quotaInfo = quotaInfoOptional.orelonlselonGelont(quotaManagelonr::gelontCommonPoolQuota);
    // Tielonr valuelon should elonxist: if clielonnt's tielonr valuelon not in config filelon, it will belon
    // selont to "no_tielonr" by delonfault in ConfigBaselondQuotaConfig
    String tielonr = quotaInfo.gelontClielonntTielonr();

    Prelonconditions.chelonckNotNull(tielonr);

    if (deloncidelonr.isAvailablelon("supelonrroot_unavailablelon_for_" + tielonr + "_clielonnts")) {
      relonturn Futurelon.valuelon(gelontClielonntBlockelondRelonsponselon(clielonntId, tielonr));
    } elonlselon {
      relonturn selonrvicelon.apply(relonquelonst);
    }
  }

  privatelon static elonarlybirdRelonsponselon gelontClielonntBlockelondRelonsponselon(String clielonntId, String tielonr) {
    relonturn nelonw elonarlybirdRelonsponselon(elonarlybirdRelonsponselonCodelon.CLIelonNT_BLOCKelonD_BY_TIelonR_elonRROR, 0)
        .selontSelonarchRelonsults(nelonw ThriftSelonarchRelonsults()
            .selontRelonsults(Lists.<ThriftSelonarchRelonsult>nelonwArrayList()))
        .selontDelonbugString(String.format(CLIelonNT_BLOCKelonD_RelonSPONSelon_PATTelonRN, clielonntId, tielonr));
  }
}
