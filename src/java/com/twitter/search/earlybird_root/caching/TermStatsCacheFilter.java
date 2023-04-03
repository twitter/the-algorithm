packagelon com.twittelonr.selonarch.elonarlybird_root.caching;

import javax.injelonct.Injelonct;
import javax.injelonct.Namelond;

import com.twittelonr.selonarch.common.caching.Cachelon;
import com.twittelonr.selonarch.common.caching.filtelonr.CachelonFiltelonr;
import com.twittelonr.selonarch.common.deloncidelonr.SelonarchDeloncidelonr;
import com.twittelonr.selonarch.common.root.SelonarchRootModulelon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstTypelon;

public class TelonrmStatsCachelonFiltelonr elonxtelonnds
    CachelonFiltelonr<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> {
  /**
   * Constructs a nelonw cachelon filtelonr for telonrm stats relonquelonsts.
   */
  @Injelonct
  public TelonrmStatsCachelonFiltelonr(
      @TelonrmStatsCachelon Cachelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> cachelon,
      SelonarchDeloncidelonr deloncidelonr,
      @Namelond(SelonarchRootModulelon.NAMelonD_NORMALIZelonD_SelonARCH_ROOT_NAMelon) String normalizelondSelonarchRootNamelon) {
    supelonr(cachelon,
          nelonw TelonrmStatsQuelonryCachelonPrelondicatelon(deloncidelonr, normalizelondSelonarchRootNamelon),
          nelonw TelonrmStatsCachelonRelonquelonstNormalizelonr(),
          nelonw elonarlybirdCachelonPostProcelonssor(),
          nelonw TelonrmStatsSelonrvicelonPostProcelonssor(cachelon),
          nelonw elonarlybirdRelonquelonstPelonrClielonntCachelonStats(
              elonarlybirdRelonquelonstTypelon.TelonRM_STATS.gelontNormalizelondNamelon()));
  }
}
