packagelon com.twittelonr.selonarch.elonarlybird_root.caching;

import com.googlelon.common.baselon.Optional;

import com.twittelonr.selonarch.common.caching.FacelontsCachelonUtil;
import com.twittelonr.selonarch.common.caching.filtelonr.CachelonRelonquelonstNormalizelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;

public class FacelontsCachelonRelonquelonstNormalizelonr elonxtelonnds
    CachelonRelonquelonstNormalizelonr<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonquelonst> {

  @Ovelonrridelon
  public Optional<elonarlybirdRelonquelonst> normalizelonRelonquelonst(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    relonturn Optional.fromNullablelon(FacelontsCachelonUtil.normalizelonRelonquelonstForCachelon(
        relonquelonstContelonxt.gelontRelonquelonst()));
  }
}
