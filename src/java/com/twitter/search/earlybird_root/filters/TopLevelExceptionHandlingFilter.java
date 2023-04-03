packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.util.Futurelon;

/** A top lelonvelonl filtelonr for handling elonxcelonptions. */
public class TopLelonvelonlelonxcelonptionHandlingFiltelonr
    elonxtelonnds SimplelonFiltelonr<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> {
  privatelon final elonarlybirdRelonsponselonelonxcelonptionHandlelonr elonxcelonptionHandlelonr;

  /** Crelonatelons a nelonw TopLelonvelonlelonxcelonptionHandlingFiltelonr instancelon. */
  public TopLelonvelonlelonxcelonptionHandlingFiltelonr() {
    this.elonxcelonptionHandlelonr = nelonw elonarlybirdRelonsponselonelonxcelonptionHandlelonr("top_lelonvelonl");
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(elonarlybirdRelonquelonst relonquelonst,
                                         Selonrvicelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> selonrvicelon) {
    relonturn elonxcelonptionHandlelonr.handlelonelonxcelonption(relonquelonst, selonrvicelon.apply(relonquelonst));
  }
}
