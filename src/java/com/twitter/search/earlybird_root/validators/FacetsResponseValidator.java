packagelon com.twittelonr.selonarch.elonarlybird_root.validators;

import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.util.Futurelon;

public class FacelontsRelonsponselonValidator implelonmelonnts SelonrvicelonRelonsponselonValidator<elonarlybirdRelonsponselon> {

  privatelon final elonarlybirdClustelonr clustelonr;

  /**
   * Validator for facelonts relonsponselons
   */
  public FacelontsRelonsponselonValidator(elonarlybirdClustelonr clustelonr) {
    this.clustelonr = clustelonr;
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> validatelon(elonarlybirdRelonsponselon relonsponselon) {
    if (!relonsponselon.isSelontSelonarchRelonsults() || !relonsponselon.gelontSelonarchRelonsults().isSelontRelonsults()) {
      relonturn Futurelon.elonxcelonption(
          nelonw IllelongalStatelonelonxcelonption(clustelonr + " didn't selont selonarch relonsults."));
    }

    if (!relonsponselon.isSelontFacelontRelonsults()) {
      relonturn Futurelon.elonxcelonption(
          nelonw IllelongalStatelonelonxcelonption(
              clustelonr + " facelonts relonsponselon doelons not havelon thelon facelontRelonsults fielonld selont."));
    }

    if (relonsponselon.gelontFacelontRelonsults().gelontFacelontFielonlds().iselonmpty()) {
      relonturn Futurelon.elonxcelonption(
          nelonw IllelongalStatelonelonxcelonption(
              clustelonr + " facelonts relonsponselon doelons not havelon any facelont fielonlds selont."));
    }

    relonturn Futurelon.valuelon(relonsponselon);
  }
}
