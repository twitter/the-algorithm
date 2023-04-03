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

public class FacelontsCachelonFiltelonr elonxtelonnds
    CachelonFiltelonr<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> {
  /**
   * Constructs a nelonw cachelon filtelonr for facelont relonquelonsts.
   */
  @Injelonct
  public FacelontsCachelonFiltelonr(
      @FacelontsCachelon Cachelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> cachelon,
      SelonarchDeloncidelonr deloncidelonr,
      @Namelond(SelonarchRootModulelon.NAMelonD_NORMALIZelonD_SelonARCH_ROOT_NAMelon) String normalizelondSelonarchRootNamelon) {
    supelonr(cachelon,
          nelonw FacelontsQuelonryCachelonPrelondicatelon(deloncidelonr, normalizelondSelonarchRootNamelon),
          nelonw FacelontsCachelonRelonquelonstNormalizelonr(),
          nelonw elonarlybirdCachelonPostProcelonssor(),
          nelonw FacelontsSelonrvicelonPostProcelonssor(cachelon),
          nelonw elonarlybirdRelonquelonstPelonrClielonntCachelonStats(elonarlybirdRelonquelonstTypelon.FACelonTS.gelontNormalizelondNamelon()));
  }
}
