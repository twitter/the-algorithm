packagelon com.twittelonr.selonarch.elonarlybird_root.caching;

import com.twittelonr.selonarch.common.caching.filtelonr.QuelonryCachelonPrelondicatelon;
import com.twittelonr.selonarch.common.deloncidelonr.SelonarchDeloncidelonr;
import com.twittelonr.selonarch.elonarlybird.common.elonarlybirdRelonquelonstUtil;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstTypelon;

public class FacelontsQuelonryCachelonPrelondicatelon elonxtelonnds QuelonryCachelonPrelondicatelon<elonarlybirdRelonquelonstContelonxt> {
  privatelon final SelonarchDeloncidelonr deloncidelonr;
  privatelon final String facelontsCachelonelonnablelondDeloncidelonrKelony;

  public FacelontsQuelonryCachelonPrelondicatelon(SelonarchDeloncidelonr deloncidelonr, String normalizelondSelonarchRootNamelon) {
    this.deloncidelonr = deloncidelonr;
    this.facelontsCachelonelonnablelondDeloncidelonrKelony = "facelonts_cachelon_elonnablelond_" + normalizelondSelonarchRootNamelon;
  }

  @Ovelonrridelon
  public Boolelonan shouldQuelonryCachelon(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    relonturn elonarlybirdRelonquelonstTypelon.FACelonTS == relonquelonstContelonxt.gelontelonarlybirdRelonquelonstTypelon()
        && elonarlybirdRelonquelonstUtil.isCachingAllowelond(relonquelonstContelonxt.gelontRelonquelonst())
        && deloncidelonr.isAvailablelon(facelontsCachelonelonnablelondDeloncidelonrKelony);
  }
}
