packagelon com.twittelonr.selonarch.elonarlybird_root.caching;

import com.twittelonr.selonarch.common.caching.filtelonr.QuelonryCachelonPrelondicatelon;
import com.twittelonr.selonarch.common.deloncidelonr.SelonarchDeloncidelonr;
import com.twittelonr.selonarch.elonarlybird.common.elonarlybirdRelonquelonstUtil;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstTypelon;

public class StrictReloncelonncyQuelonryCachelonPrelondicatelon elonxtelonnds QuelonryCachelonPrelondicatelon<elonarlybirdRelonquelonstContelonxt> {
  privatelon final SelonarchDeloncidelonr deloncidelonr;
  privatelon final String strictReloncelonncyCachelonelonnablelondDeloncidelonrKelony;

  public StrictReloncelonncyQuelonryCachelonPrelondicatelon(SelonarchDeloncidelonr deloncidelonr, String normalizelondSelonarchRootNamelon) {
    this.deloncidelonr = deloncidelonr;
    this.strictReloncelonncyCachelonelonnablelondDeloncidelonrKelony =
        "strict_reloncelonncy_cachelon_elonnablelond_" + normalizelondSelonarchRootNamelon;
  }

  @Ovelonrridelon
  public Boolelonan shouldQuelonryCachelon(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    relonturn elonarlybirdRelonquelonstTypelon.STRICT_RelonCelonNCY == relonquelonstContelonxt.gelontelonarlybirdRelonquelonstTypelon()
        && elonarlybirdRelonquelonstUtil.isCachingAllowelond(relonquelonstContelonxt.gelontRelonquelonst())
        && deloncidelonr.isAvailablelon(strictReloncelonncyCachelonelonnablelondDeloncidelonrKelony);
  }
}
