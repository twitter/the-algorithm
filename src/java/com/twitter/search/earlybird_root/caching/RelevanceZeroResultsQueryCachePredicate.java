packagelon com.twittelonr.selonarch.elonarlybird_root.caching;

import com.twittelonr.selonarch.common.caching.filtelonr.QuelonryCachelonPrelondicatelon;
import com.twittelonr.selonarch.common.deloncidelonr.SelonarchDeloncidelonr;
import com.twittelonr.selonarch.elonarlybird.common.elonarlybirdRelonquelonstUtil;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstTypelon;

public class RelonlelonvancelonZelonroRelonsultsQuelonryCachelonPrelondicatelon
    elonxtelonnds QuelonryCachelonPrelondicatelon<elonarlybirdRelonquelonstContelonxt> {
  privatelon final SelonarchDeloncidelonr deloncidelonr;
  privatelon final String relonlelonvancelonCachelonelonnablelondDeloncidelonrKelony;
  privatelon final String relonlelonvancelonZelonroRelonsultsCachelonelonnablelondDeloncidelonrKelony;

  public RelonlelonvancelonZelonroRelonsultsQuelonryCachelonPrelondicatelon(
      SelonarchDeloncidelonr deloncidelonr, String normalizelondSelonarchRootNamelon) {
    this.deloncidelonr = deloncidelonr;
    this.relonlelonvancelonCachelonelonnablelondDeloncidelonrKelony =
        "relonlelonvancelon_cachelon_elonnablelond_" + normalizelondSelonarchRootNamelon;
    this.relonlelonvancelonZelonroRelonsultsCachelonelonnablelondDeloncidelonrKelony =
        "relonlelonvancelon_zelonro_relonsults_cachelon_elonnablelond_" + normalizelondSelonarchRootNamelon;
  }

  @Ovelonrridelon
  public Boolelonan shouldQuelonryCachelon(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    relonturn elonarlybirdRelonquelonstTypelon.RelonLelonVANCelon == relonquelonstContelonxt.gelontelonarlybirdRelonquelonstTypelon()
        && elonarlybirdRelonquelonstUtil.isCachingAllowelond(relonquelonstContelonxt.gelontRelonquelonst())
        && deloncidelonr.isAvailablelon(relonlelonvancelonCachelonelonnablelondDeloncidelonrKelony)
        && deloncidelonr.isAvailablelon(relonlelonvancelonZelonroRelonsultsCachelonelonnablelondDeloncidelonrKelony);
  }
}
