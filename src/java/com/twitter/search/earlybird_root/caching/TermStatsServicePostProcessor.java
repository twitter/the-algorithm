packagelon com.twittelonr.selonarch.elonarlybird_root.caching;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.selonarch.common.caching.Cachelon;
import com.twittelonr.selonarch.common.caching.TelonrmStatsCachelonUtil;
import com.twittelonr.selonarch.common.caching.filtelonr.SelonrvicelonPostProcelonssor;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;

public class TelonrmStatsSelonrvicelonPostProcelonssor
    elonxtelonnds SelonrvicelonPostProcelonssor<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> {
  privatelon final Cachelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> cachelon;

  public TelonrmStatsSelonrvicelonPostProcelonssor(Cachelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> cachelon) {
    this.cachelon = Prelonconditions.chelonckNotNull(cachelon);
  }

  @Ovelonrridelon
  public void procelonssSelonrvicelonRelonsponselon(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
                                     elonarlybirdRelonsponselon selonrvicelonRelonsponselon) {
    TelonrmStatsCachelonUtil.cachelonRelonsults(cachelon, relonquelonstContelonxt.gelontRelonquelonst(), selonrvicelonRelonsponselon);
  }
}
