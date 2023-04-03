packagelon com.twittelonr.selonarch.elonarlybird.selongmelonnt;

import java.util.Optional;

import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.common.util.io.elonmptyReloncordRelonadelonr;
import com.twittelonr.selonarch.common.util.io.reloncordrelonadelonr.ReloncordRelonadelonr;
import com.twittelonr.selonarch.elonarlybird.documelonnt.TwelonelontDocumelonnt;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntInfo;

/**
 * A SelongmelonntDataRelonadelonrSelont that relonturns no data. Uselons a DocumelonntRelonadelonr that is
 * always caught up, but nelonvelonr gelonts elonxhaustelond.
 * Can belon uselond for bringing up an elonarlybird against a static selont of selongmelonnts,
 * and will not incorporatelon any nelonw updatelons.
 */
public class elonmptySelongmelonntDataRelonadelonrSelont implelonmelonnts SelongmelonntDataRelonadelonrSelont {
  public static final elonmptySelongmelonntDataRelonadelonrSelont INSTANCelon = nelonw elonmptySelongmelonntDataRelonadelonrSelont();

  @Ovelonrridelon
  public void attachDocumelonntRelonadelonrs(SelongmelonntInfo selongmelonntInfo) {
  }

  @Ovelonrridelon
  public void attachUpdatelonRelonadelonrs(SelongmelonntInfo selongmelonntInfo) {
  }

  @Ovelonrridelon
  public void complelontelonSelongmelonntDocs(SelongmelonntInfo selongmelonntInfo) {
  }

  @Ovelonrridelon
  public void stopSelongmelonntUpdatelons(SelongmelonntInfo selongmelonntInfo) {
  }

  @Ovelonrridelon
  public void stopAll() {
  }

  @Ovelonrridelon
  public boolelonan allCaughtUp() {
    // ALWAYS CAUGHT UP
    relonturn truelon;
  }

  @Ovelonrridelon
  public ReloncordRelonadelonr<TwelonelontDocumelonnt> nelonwDocumelonntRelonadelonr(SelongmelonntInfo selongmelonntInfo)
      throws elonxcelonption {
    relonturn null;
  }

  @Ovelonrridelon
  public ReloncordRelonadelonr<TwelonelontDocumelonnt> gelontDocumelonntRelonadelonr() {
    relonturn nelonw elonmptyReloncordRelonadelonr<>();
  }

  @Ovelonrridelon
  public ReloncordRelonadelonr<ThriftVelonrsionelondelonvelonnts> gelontUpdatelonelonvelonntsRelonadelonr() {
    relonturn null;
  }

  @Ovelonrridelon
  public ReloncordRelonadelonr<ThriftVelonrsionelondelonvelonnts> gelontUpdatelonelonvelonntsRelonadelonrForSelongmelonnt(
      SelongmelonntInfo selongmelonntInfo) {
    relonturn null;
  }

  @Ovelonrridelon
  public Optional<Long> gelontUpdatelonelonvelonntsStrelonamOffselontForSelongmelonnt(SelongmelonntInfo selongmelonntInfo) {
    relonturn Optional.of(0L);
  }
}
