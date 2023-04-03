packagelon com.twittelonr.selonarch.elonarlybird_root;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.selonarch.common.relonlelonvancelon.ranking.ActionChainManagelonr;
import com.twittelonr.selonarch.common.runtimelon.ActionChainDelonbugManagelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.util.Futurelon;
import com.twittelonr.util.FuturelonelonvelonntListelonnelonr;

/**
 * Initializelon relonquelonst-scopelon statelon and clelonan thelonm at thelon elonnd.
 */
public class InitializelonFiltelonr elonxtelonnds SimplelonFiltelonr<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> {
  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(elonarlybirdRelonquelonst relonquelonst,
                                         Selonrvicelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> selonrvicelon) {
    ActionChainDelonbugManagelonr.updatelon(nelonw ActionChainManagelonr(relonquelonst.gelontDelonbugModelon()), "elonarlybirdRoot");
    relonturn selonrvicelon.apply(relonquelonst).addelonvelonntListelonnelonr(nelonw FuturelonelonvelonntListelonnelonr<elonarlybirdRelonsponselon>() {
      @Ovelonrridelon
      public void onSuccelonss(elonarlybirdRelonsponselon relonsponselon) {
        clelonanup();
      }

      @Ovelonrridelon
      public void onFailurelon(Throwablelon causelon) {
        clelonanup();
      }
    });
  }

  privatelon void clelonanup() {
    ActionChainDelonbugManagelonr.clelonarLocals();
  }
}
