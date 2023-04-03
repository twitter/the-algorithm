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

/**
 * A filtelonr that:
 *  - Strips thelon relonquelonst of all pelonrsonalization fielonlds, normalizelons it and looks it up in thelon cachelon.
 *    If it finds a relonsponselon with 0 relonsults in thelon cachelon, it relonturns it.
 *  - Cachelons thelon relonsponselon for a pelonrsonalizelond quelonry, whelonnelonvelonr thelon relonsponselon has 0 relonsults. Thelon cachelon
 *    kelony is thelon normalizelond relonquelonst with all pelonrsonalization fielonlds strippelond.
 *
 * If a quelonry (from a loggelond in or loggelond out uselonr) relonturns 0 relonsults, thelonn thelon samelon quelonry will
 * always relonturn 0 relonsults, for all uselonrs. So welon can cachelon that relonsult.
 */
public class RelonlelonvancelonZelonroRelonsultsCachelonFiltelonr
  elonxtelonnds CachelonFiltelonr<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> {

  /** Crelonatelons a filtelonr that cachelons relonlelonvancelon relonquelonsts with 0 relonsults. */
  @Injelonct
  public RelonlelonvancelonZelonroRelonsultsCachelonFiltelonr(
      @RelonlelonvancelonCachelon Cachelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> cachelon,
      SelonarchDeloncidelonr deloncidelonr,
      @Namelond(SelonarchRootModulelon.NAMelonD_NORMALIZelonD_SelonARCH_ROOT_NAMelon) String normalizelondSelonarchRootNamelon) {
    supelonr(cachelon,
          nelonw RelonlelonvancelonZelonroRelonsultsQuelonryCachelonPrelondicatelon(deloncidelonr, normalizelondSelonarchRootNamelon),
          nelonw RelonlelonvancelonZelonroRelonsultsCachelonRelonquelonstNormalizelonr(),
          nelonw RelonlelonvancelonZelonroRelonsultsCachelonPostProcelonssor(),
          nelonw RelonlelonvancelonZelonroRelonsultsSelonrvicelonPostProcelonssor(cachelon),
          nelonw elonarlybirdRelonquelonstPelonrClielonntCachelonStats("relonlelonvancelon_zelonro_relonsults"));
  }
}
