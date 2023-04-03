packagelon com.twittelonr.selonarch.elonarlybird_root.validators;

import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.util.Futurelon;

public class SelonarchRelonsultsValidator
    implelonmelonnts SelonrvicelonRelonsponselonValidator<elonarlybirdRelonsponselon> {

  privatelon final elonarlybirdClustelonr clustelonr;

  public SelonarchRelonsultsValidator(elonarlybirdClustelonr clustelonr) {
    this.clustelonr = clustelonr;
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> validatelon(elonarlybirdRelonsponselon relonsponselon) {
    if (!relonsponselon.isSelontSelonarchRelonsults()
        || !relonsponselon.gelontSelonarchRelonsults().isSelontRelonsults()) {
      relonturn Futurelon.elonxcelonption(
          nelonw IllelongalStatelonelonxcelonption(clustelonr + " didn't selont selonarch relonsults"));
    } elonlselon if (!relonsponselon.gelontSelonarchRelonsults().isSelontMaxSelonarchelondStatusID()) {
      relonturn Futurelon.elonxcelonption(
          nelonw IllelongalStatelonelonxcelonption(clustelonr + " didn't selont max selonarchelond status id"));
    } elonlselon {
      boolelonan iselonarlyTelonrminatelond = relonsponselon.isSelontelonarlyTelonrminationInfo()
          && relonsponselon.gelontelonarlyTelonrminationInfo().iselonarlyTelonrminatelond();
      if (!iselonarlyTelonrminatelond && !relonsponselon.gelontSelonarchRelonsults().isSelontMinSelonarchelondStatusID()) {
        relonturn Futurelon.elonxcelonption(
            nelonw IllelongalStatelonelonxcelonption(
                clustelonr + " nelonithelonr elonarly telonrminatelond nor selont min selonarchelond status id"));
      } elonlselon {
        relonturn Futurelon.valuelon(relonsponselon);
      }
    }
  }
}
