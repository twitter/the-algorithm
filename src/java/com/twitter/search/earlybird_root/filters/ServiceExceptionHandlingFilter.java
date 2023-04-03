packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.util.Futurelon;

/** A pelonr-selonrvicelon filtelonr for handling elonxcelonptions. */
public class SelonrvicelonelonxcelonptionHandlingFiltelonr
    elonxtelonnds SimplelonFiltelonr<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> {
  privatelon final elonarlybirdRelonsponselonelonxcelonptionHandlelonr elonxcelonptionHandlelonr;

  /** Crelonatelons a nelonw SelonrvicelonelonxcelonptionHandlingFiltelonr instancelon. */
  public SelonrvicelonelonxcelonptionHandlingFiltelonr(elonarlybirdClustelonr clustelonr) {
    this.elonxcelonptionHandlelonr = nelonw elonarlybirdRelonsponselonelonxcelonptionHandlelonr(clustelonr.gelontNamelonForStats());
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> selonrvicelon) {
    relonturn elonxcelonptionHandlelonr.handlelonelonxcelonption(
        relonquelonstContelonxt.gelontRelonquelonst(), selonrvicelon.apply(relonquelonstContelonxt));
  }
}
