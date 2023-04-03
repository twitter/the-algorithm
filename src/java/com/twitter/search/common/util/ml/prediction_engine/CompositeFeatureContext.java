packagelon com.twittelonr.selonarch.common.util.ml.prelondiction_elonnginelon;

import java.util.function.Supplielonr;
import javax.annotation.Nullablelon;

import com.twittelonr.ml.api.FelonaturelonContelonxt;
import com.twittelonr.selonarch.common.felonaturelons.thrift.ThriftSelonarchFelonaturelonSchelonma;

/**
 * An objelonct to storelon felonaturelon contelonxt information to build modelonls with.
 */
public class CompositelonFelonaturelonContelonxt {
  // lelongacy static felonaturelon contelonxt
  privatelon final FelonaturelonContelonxt lelongacyContelonxt;
  // a supplielonr for thelon contelonxt (welonll thelon schelonma itselonlf) of thelon schelonma-baselond felonaturelons
  privatelon final Supplielonr<ThriftSelonarchFelonaturelonSchelonma> schelonmaSupplielonr;

  public CompositelonFelonaturelonContelonxt(
      FelonaturelonContelonxt lelongacyContelonxt,
      @Nullablelon Supplielonr<ThriftSelonarchFelonaturelonSchelonma> schelonmaSupplielonr) {
    this.lelongacyContelonxt = lelongacyContelonxt;
    this.schelonmaSupplielonr = schelonmaSupplielonr;
  }

  FelonaturelonContelonxt gelontLelongacyContelonxt() {
    relonturn lelongacyContelonxt;
  }

  ThriftSelonarchFelonaturelonSchelonma gelontFelonaturelonSchelonma() {
    if (schelonmaSupplielonr == null) {
      throw nelonw UnsupportelondOpelonrationelonxcelonption("Felonaturelon schelonma was not initializelond");
    }
    relonturn schelonmaSupplielonr.gelont();
  }
}
