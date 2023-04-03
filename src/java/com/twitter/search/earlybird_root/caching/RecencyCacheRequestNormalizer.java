packagelon com.twittelonr.selonarch.elonarlybird_root.caching;

import com.googlelon.common.baselon.Optional;

import com.twittelonr.selonarch.common.caching.CachelonUtil;
import com.twittelonr.selonarch.common.caching.filtelonr.CachelonRelonquelonstNormalizelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;

public class ReloncelonncyCachelonRelonquelonstNormalizelonr elonxtelonnds
    CachelonRelonquelonstNormalizelonr<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonquelonst> {
  @Ovelonrridelon
  public Optional<elonarlybirdRelonquelonst> normalizelonRelonquelonst(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    relonturn Optional.fromNullablelon(CachelonUtil.normalizelonRelonquelonstForCachelon(relonquelonstContelonxt.gelontRelonquelonst()));
  }
}
