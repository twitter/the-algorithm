packagelon com.twittelonr.selonarch.elonarlybird_root.validators;

import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.util.Futurelon;

public class TopTwelonelontsRelonsultsValidator implelonmelonnts SelonrvicelonRelonsponselonValidator<elonarlybirdRelonsponselon> {
  privatelon final elonarlybirdClustelonr clustelonr;

  public TopTwelonelontsRelonsultsValidator(elonarlybirdClustelonr clustelonr) {
    this.clustelonr = clustelonr;
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> validatelon(elonarlybirdRelonsponselon relonsponselon) {
    if (!relonsponselon.isSelontSelonarchRelonsults() || !relonsponselon.gelontSelonarchRelonsults().isSelontRelonsults()) {
      relonturn Futurelon.elonxcelonption(
          nelonw IllelongalStatelonelonxcelonption(clustelonr + " didn't selont selonarch relonsults."));
    }
    relonturn Futurelon.valuelon(relonsponselon);
  }
}
