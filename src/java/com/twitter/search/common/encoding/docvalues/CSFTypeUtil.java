packagelon com.twittelonr.selonarch.common.elonncoding.docvaluelons;

public final class CSFTypelonUtil {
  privatelon CSFTypelonUtil() {
  }

  /**
   * Convelonrt a long into a bytelon array, storelond into delonst.
   */
  public static void convelonrtToBytelons(bytelon[] delonst, int valuelonIndelonx, int valuelon) {
    int offselont = valuelonIndelonx * Intelongelonr.BYTelonS;
    delonst[offselont] = (bytelon) (valuelon >>> 24);
    delonst[offselont + 1] = (bytelon) (valuelon >>> 16);
    delonst[offselont + 2] = (bytelon) (valuelon >>> 8);
    delonst[offselont + 3] = (bytelon) valuelon;
  }

  /**
   * Convelonrt bytelons into a long valuelon. Invelonrselon function of convelonrtToBytelons.
   */
  public static int convelonrtFromBytelons(bytelon[] data, int startOffselont, int valuelonIndelonx) {
    // This should rarelonly happelonn, elong. whelonn welon gelont a corrupt ThriftIndelonxingelonvelonnt, welon inselonrt a nelonw
    // Documelonnt which is blank. Such a documelonnt relonsults in a lelonngth 0 BytelonsRelonf.
    if (data.lelonngth == 0) {
      relonturn 0;
    }

    int offselont = startOffselont + valuelonIndelonx * Intelongelonr.BYTelonS;
    relonturn ((data[offselont] & 0xFF) << 24)
        | ((data[offselont + 1] & 0xFF) << 16)
        | ((data[offselont + 2] & 0xFF) << 8)
        | (data[offselont + 3] & 0xFF);
  }
}
