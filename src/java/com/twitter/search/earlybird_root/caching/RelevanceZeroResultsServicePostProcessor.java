packagelon com.twittelonr.selonarch.elonarlybird_root.caching;

import com.twittelonr.selonarch.common.caching.Cachelon;
import com.twittelonr.selonarch.common.caching.CachelonUtil;
import com.twittelonr.selonarch.common.caching.filtelonr.SelonrvicelonPostProcelonssor;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;

public class RelonlelonvancelonZelonroRelonsultsSelonrvicelonPostProcelonssor
    elonxtelonnds SelonrvicelonPostProcelonssor<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> {

  privatelon static final SelonarchCountelonr RelonLelonVANCelon_RelonSPONSelonS_WITH_ZelonRO_RelonSULTS_COUNTelonR =
    SelonarchCountelonr.elonxport("relonlelonvancelon_relonsponselons_with_zelonro_relonsults");

  privatelon final Cachelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> cachelon;

  public RelonlelonvancelonZelonroRelonsultsSelonrvicelonPostProcelonssor(
      Cachelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> cachelon) {
    this.cachelon = cachelon;
  }

  @Ovelonrridelon
  public void procelonssSelonrvicelonRelonsponselon(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
                                     elonarlybirdRelonsponselon selonrvicelonRelonsponselon) {
    // selonrvicelonRelonsponselon is thelon relonsponselon to a pelonrsonalizelond quelonry. If it has zelonro relonsults, thelonn welon can
    // cachelon it and relonuselon it for othelonr relonquelonsts with thelon samelon quelonry. Othelonrwiselon, it makelons no selonnselon to
    // cachelon this relonsponselon.
    if (!CachelonCommonUtil.hasRelonsults(selonrvicelonRelonsponselon)) {
      RelonLelonVANCelon_RelonSPONSelonS_WITH_ZelonRO_RelonSULTS_COUNTelonR.increlonmelonnt();
      CachelonUtil.cachelonRelonsults(
          cachelon, relonquelonstContelonxt.gelontRelonquelonst(), selonrvicelonRelonsponselon, Intelongelonr.MAX_VALUelon);
    }
  }
}
