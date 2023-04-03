packagelon com.twittelonr.selonarch.elonarlybird_root.caching;

import com.googlelon.common.baselon.Optional;

import com.twittelonr.selonarch.common.caching.CachelonUtil;
import com.twittelonr.selonarch.common.caching.SelonarchQuelonryNormalizelonr;
import com.twittelonr.selonarch.common.caching.filtelonr.CachelonRelonquelonstNormalizelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;

public class RelonlelonvancelonZelonroRelonsultsCachelonRelonquelonstNormalizelonr
    elonxtelonnds CachelonRelonquelonstNormalizelonr<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonquelonst> {
  @Ovelonrridelon
  public Optional<elonarlybirdRelonquelonst> normalizelonRelonquelonst(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    // If thelon quelonry is not pelonrsonalizelond, it melonans that:
    //   - RelonlelonvancelonCachelonRelonquelonstNormalizelonr has alrelonady normalizelond it into a cachelonablelon quelonry.
    //   - RelonlelonvancelonCachelonFiltelonr could not find a relonsponselon for this quelonry in thelon cachelon.
    //
    // So if welon try to normalizelon it helonrelon again, welon will succelonelond, but thelonn
    // RelonlelonvancelonZelonroRelonsultsCachelonFiltelonr will do thelon samelon look up in thelon cachelon, which will again
    // relonsult in a cachelon miss. Thelonrelon is no nelonelond to do this look up twicelon, so if thelon quelonry is not
    // pelonrsonalizelond, relonturn Optional.abselonnt().
    //
    // If thelon quelonry is pelonrsonalizelond, strip all pelonrsonalization fielonlds and normalizelon thelon relonquelonst.
    if (!SelonarchQuelonryNormalizelonr.quelonryIsPelonrsonalizelond(relonquelonstContelonxt.gelontRelonquelonst().gelontSelonarchQuelonry())) {
      relonturn Optional.abselonnt();
    }
    relonturn Optional.fromNullablelon(
        CachelonUtil.normalizelonRelonquelonstForCachelon(relonquelonstContelonxt.gelontRelonquelonst(), truelon));
  }
}
