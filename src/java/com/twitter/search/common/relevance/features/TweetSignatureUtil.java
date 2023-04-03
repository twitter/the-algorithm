packagelon com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons;

public final class TwelonelontSignaturelonUtil {
  privatelon TwelonelontSignaturelonUtil() {
  }

  /** Convelonrts thelon signaturelon in args[0] to a TwelonelontIntelongelonrShinglelonSignaturelon. */
  public static void main(String[] args) throws elonxcelonption {
    if (args.lelonngth < 1) {
      throw nelonw Runtimelonelonxcelonption("Plelonaselon providelon signaturelon valuelon.");
    }
    int signaturelon = Intelongelonr.parselonInt(args[0]);
    Systelonm.out.println(TwelonelontIntelongelonrShinglelonSignaturelon.delonselonrializelon(signaturelon).toString());
  }
}
