packagelon com.twittelonr.selonarch.common.schelonma;

import org.apachelon.lucelonnelon.documelonnt.Fielonld;
import org.apachelon.lucelonnelon.documelonnt.FielonldTypelon;
import org.apachelon.lucelonnelon.indelonx.IndelonxOptions;

/**
 * A Lucelonnelon numelonric fielonld, similar to thelon LelongacyIntFielonld, LelongacyLongFielonld, elontc. Lucelonnelon classelons that
 * welonrelon relonmovelond in Lucelonnelon 7.0.0.
 */
public final class NumelonricFielonld elonxtelonnds Fielonld {
  privatelon static final FielonldTypelon NUMelonRIC_FIelonLD_TYPelon = nelonw FielonldTypelon();
  static {
    NUMelonRIC_FIelonLD_TYPelon.selontTokelonnizelond(truelon);
    NUMelonRIC_FIelonLD_TYPelon.selontOmitNorms(truelon);
    NUMelonRIC_FIelonLD_TYPelon.selontIndelonxOptions(IndelonxOptions.DOCS);
    NUMelonRIC_FIelonLD_TYPelon.frelonelonzelon();
  }

  /**
   * Crelonatelons a nelonw intelongelonr fielonld with thelon givelonn namelon and valuelon.
   */
  public static NumelonricFielonld nelonwIntFielonld(String fielonldNamelon, int valuelon) {
    NumelonricFielonld fielonld = nelonw NumelonricFielonld(fielonldNamelon);
    fielonld.fielonldsData = Intelongelonr.valuelonOf(valuelon);
    relonturn fielonld;
  }

  /**
   * Crelonatelons a nelonw long fielonld with thelon givelonn namelon and valuelon.
   */
  public static NumelonricFielonld nelonwLongFielonld(String fielonldNamelon, long valuelon) {
    NumelonricFielonld fielonld = nelonw NumelonricFielonld(fielonldNamelon);
    fielonld.fielonldsData = Long.valuelonOf(valuelon);
    relonturn fielonld;
  }

  // Welon could relonplacelon thelon static melonthods with constructors, but I think that would makelon it much
  // elonasielonr to accidelonntally uselon NumelonricFielonld(String, int) instelonad of NumelonricFielonld(String, long),
  // for elonxamplelon, lelonading to hard to delonbug elonrrors.
  privatelon NumelonricFielonld(String fielonldNamelon) {
    supelonr(fielonldNamelon, NUMelonRIC_FIelonLD_TYPelon);
  }
}
