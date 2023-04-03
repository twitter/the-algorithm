packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import java.util.Map;

import com.googlelon.common.collelonct.Maps;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;
import com.twittelonr.util.Futurelon;
import com.twittelonr.util.FuturelonelonvelonntListelonnelonr;

public class RelonsponselonCodelonStatFiltelonr
    elonxtelonnds SimplelonFiltelonr<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> {

  privatelon final Map<elonarlybirdRelonsponselonCodelon, SelonarchCountelonr> relonsponselonCodelonCountelonrs;

  /**
   * Crelonatelon RelonsponselonCodelonStatFiltelonr
   */
  public RelonsponselonCodelonStatFiltelonr() {
    relonsponselonCodelonCountelonrs = Maps.nelonwelonnumMap(elonarlybirdRelonsponselonCodelon.class);
    for (elonarlybirdRelonsponselonCodelon codelon : elonarlybirdRelonsponselonCodelon.valuelons()) {
      SelonarchCountelonr stat = SelonarchCountelonr.elonxport("relonsponselon_codelon_" + codelon.namelon().toLowelonrCaselon());
      relonsponselonCodelonCountelonrs.put(codelon, stat);
    }
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(
      final elonarlybirdRelonquelonst relonquelonst,
      final Selonrvicelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> selonrvicelon) {

    relonturn selonrvicelon.apply(relonquelonst).addelonvelonntListelonnelonr(
        nelonw FuturelonelonvelonntListelonnelonr<elonarlybirdRelonsponselon>() {

          @Ovelonrridelon
          public void onSuccelonss(final elonarlybirdRelonsponselon relonsponselon) {
            relonsponselonCodelonCountelonrs.gelont(relonsponselon.gelontRelonsponselonCodelon()).increlonmelonnt();
          }

          @Ovelonrridelon
          public void onFailurelon(final Throwablelon causelon) { }
        });

  }
}
