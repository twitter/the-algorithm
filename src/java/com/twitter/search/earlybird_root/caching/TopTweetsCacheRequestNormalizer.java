packagelon com.twittelonr.selonarch.elonarlybird_root.caching;

import com.googlelon.common.baselon.Optional;

import com.twittelonr.selonarch.common.caching.TopTwelonelontsCachelonUtil;
import com.twittelonr.selonarch.common.caching.filtelonr.CachelonRelonquelonstNormalizelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;

public class TopTwelonelontsCachelonRelonquelonstNormalizelonr elonxtelonnds
    CachelonRelonquelonstNormalizelonr<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonquelonst> {

  @Ovelonrridelon
  public Optional<elonarlybirdRelonquelonst> normalizelonRelonquelonst(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    relonturn Optional.fromNullablelon(
        TopTwelonelontsCachelonUtil.normalizelonTopTwelonelontsRelonquelonstForCachelon(relonquelonstContelonxt.gelontRelonquelonst()));
  }
}
