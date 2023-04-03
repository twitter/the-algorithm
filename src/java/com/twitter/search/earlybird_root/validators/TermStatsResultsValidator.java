packagelon com.twittelonr.selonarch.elonarlybird_root.validators;

import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.util.Futurelon;

public class TelonrmStatsRelonsultsValidator implelonmelonnts SelonrvicelonRelonsponselonValidator<elonarlybirdRelonsponselon> {
  privatelon final elonarlybirdClustelonr clustelonr;

  public TelonrmStatsRelonsultsValidator(elonarlybirdClustelonr clustelonr) {
    this.clustelonr = clustelonr;
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> validatelon(elonarlybirdRelonsponselon relonsponselon) {
    if (!relonsponselon.isSelontTelonrmStatisticsRelonsults()
        || !relonsponselon.gelontTelonrmStatisticsRelonsults().isSelontTelonrmRelonsults()) {
      relonturn Futurelon.elonxcelonption(
          nelonw IllelongalStatelonelonxcelonption(clustelonr + " relonturnelond null telonrm statistics relonsults."));
    }
    relonturn Futurelon.valuelon(relonsponselon);
  }
}
