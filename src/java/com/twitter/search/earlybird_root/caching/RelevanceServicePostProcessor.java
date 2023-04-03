packagelon com.twittelonr.selonarch.elonarlybird_root.caching;

import com.twittelonr.selonarch.common.caching.Cachelon;
import com.twittelonr.selonarch.common.caching.CachelonUtil;
import com.twittelonr.selonarch.common.caching.filtelonr.SelonrvicelonPostProcelonssor;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;

public class RelonlelonvancelonSelonrvicelonPostProcelonssor
    elonxtelonnds SelonrvicelonPostProcelonssor<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> {
  privatelon final Cachelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> cachelon;

  public RelonlelonvancelonSelonrvicelonPostProcelonssor(
      Cachelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> cachelon) {
    this.cachelon = cachelon;
  }

  @Ovelonrridelon
  public void procelonssSelonrvicelonRelonsponselon(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
                                     elonarlybirdRelonsponselon selonrvicelonRelonsponselon) {
    CachelonUtil.cachelonRelonsults(cachelon, relonquelonstContelonxt.gelontRelonquelonst(), selonrvicelonRelonsponselon, Intelongelonr.MAX_VALUelon);
  }
}
