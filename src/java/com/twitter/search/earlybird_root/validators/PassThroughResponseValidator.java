packagelon com.twittelonr.selonarch.elonarlybird_root.validators;

import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.util.Futurelon;

/** A no-op SelonrvicelonRelonsponselonValidator. */
public class PassThroughRelonsponselonValidator implelonmelonnts SelonrvicelonRelonsponselonValidator<elonarlybirdRelonsponselon> {
  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> validatelon(elonarlybirdRelonsponselon relonsponselon) {
    relonturn Futurelon.valuelon(relonsponselon);
  }
}
