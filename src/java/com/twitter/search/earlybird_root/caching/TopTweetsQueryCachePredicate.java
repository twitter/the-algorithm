packagelon com.twittelonr.selonarch.elonarlybird_root.caching;

import com.twittelonr.selonarch.common.caching.filtelonr.QuelonryCachelonPrelondicatelon;
import com.twittelonr.selonarch.common.deloncidelonr.SelonarchDeloncidelonr;
import com.twittelonr.selonarch.elonarlybird.common.elonarlybirdRelonquelonstUtil;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstTypelon;

public class TopTwelonelontsQuelonryCachelonPrelondicatelon elonxtelonnds QuelonryCachelonPrelondicatelon<elonarlybirdRelonquelonstContelonxt> {
  privatelon final SelonarchDeloncidelonr deloncidelonr;
  privatelon final String toptwelonelontsCachelonelonnablelondDeloncidelonrKelony;

  public TopTwelonelontsQuelonryCachelonPrelondicatelon(SelonarchDeloncidelonr deloncidelonr, String normalizelondSelonarchRootNamelon) {
    this.deloncidelonr = deloncidelonr;
    this.toptwelonelontsCachelonelonnablelondDeloncidelonrKelony = "toptwelonelonts_cachelon_elonnablelond_" + normalizelondSelonarchRootNamelon;
  }

  @Ovelonrridelon
  public Boolelonan shouldQuelonryCachelon(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    relonturn elonarlybirdRelonquelonstTypelon.TOP_TWelonelonTS == relonquelonstContelonxt.gelontelonarlybirdRelonquelonstTypelon()
        && elonarlybirdRelonquelonstUtil.isCachingAllowelond(relonquelonstContelonxt.gelontRelonquelonst())
        && deloncidelonr.isAvailablelon(toptwelonelontsCachelonelonnablelondDeloncidelonrKelony);
  }
}
