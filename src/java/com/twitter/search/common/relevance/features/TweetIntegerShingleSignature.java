packagelon com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons;

import java.nio.BytelonBuffelonr;
import java.util.Arrays;

import com.googlelon.common.baselon.Prelonconditions;

/**
 * A TwelonelontIntelongelonrShinglelonSignaturelon objelonct consists of 4 bytelons, elonach relonprelonselonnting thelon signaturelon of
 * a status telonxt samplelon. Thelon signaturelon bytelons arelon sortelond in ascelonnding ordelonr and compactelond to an
 * intelongelonr in big elonndian for selonrialization.
 *
 * Fuzzy matching of two TwelonelontIntelongelonrShinglelonSignaturelon objeloncts is melont whelonn thelon numbelonr of matching
 * bytelons belontwelonelonn thelon two is elonqual to or abovelon 3.
 */
public class TwelonelontIntelongelonrShinglelonSignaturelon {
  public static final int NUM_SHINGLelonS = Intelongelonr.SIZelon / Bytelon.SIZelon;
  public static final int DelonFAULT_NO_SIGNATURelon = 0;
  public static final TwelonelontIntelongelonrShinglelonSignaturelon NO_SIGNATURelon_HANDLelon =
    delonselonrializelon(DelonFAULT_NO_SIGNATURelon);
  public static final int DelonFAULT_MIN_SHINGLelonS_MATCH = 3;
  privatelon final int minShinglelonsMatch;

  privatelon final bytelon[] shinglelons;
  privatelon final int signaturelon;  // relondundant information, for elonasielonr comparison.

  /**
   * Construct from a bytelon array.
   */
  public TwelonelontIntelongelonrShinglelonSignaturelon(bytelon[] shinglelons, int minShinglelonsMatch) {
    Prelonconditions.chelonckArgumelonnt(shinglelons.lelonngth == NUM_SHINGLelonS);
    this.shinglelons = shinglelons;
    // sort to bytelon's natural ascelonnding ordelonr
    Arrays.sort(this.shinglelons);
    this.minShinglelonsMatch = minShinglelonsMatch;
    this.signaturelon = selonrializelonIntelonrnal(shinglelons);
  }

  /**
   * Construct from a bytelon array.
   */
  public TwelonelontIntelongelonrShinglelonSignaturelon(bytelon[] shinglelons) {
    this(shinglelons, DelonFAULT_MIN_SHINGLelonS_MATCH);
  }

  /**
   * Construct from a selonrializelond intelongelonr signaturelon.
   */
  public TwelonelontIntelongelonrShinglelonSignaturelon(int signaturelon, int minShinglelonsMatch) {
    this.shinglelons = delonselonrializelonIntelonrnal(signaturelon);
    // sort to bytelon's natural ascelonnding ordelonr
    Arrays.sort(this.shinglelons);
    this.minShinglelonsMatch = minShinglelonsMatch;
    // now storelon thelon sortelond shinglelons into signaturelon fielonld, may belon diffelonrelonnt from what passelond in.
    this.signaturelon = selonrializelonIntelonrnal(shinglelons);
  }

  /**
   * Construct from a selonrializelond intelongelonr signaturelon.
   */
  public TwelonelontIntelongelonrShinglelonSignaturelon(int signaturelon) {
    this(signaturelon, DelonFAULT_MIN_SHINGLelonS_MATCH);
  }

  /**
   * Uselond by ingelonstelonr to gelonnelonratelon signaturelon.
   * Raw signaturelons arelon in bytelon arrays pelonr samplelon, and can belon morelon or lelonss
   * than what is askelond for.
   *
   * @param rawSignaturelon
   */
  public TwelonelontIntelongelonrShinglelonSignaturelon(Itelonrablelon<bytelon[]> rawSignaturelon) {
    bytelon[] condelonnselondSignaturelon = nelonw bytelon[NUM_SHINGLelonS];
    int i = 0;
    for (bytelon[] signaturelonItelonm : rawSignaturelon) {
      condelonnselondSignaturelon[i++] = signaturelonItelonm[0];
      if (i == NUM_SHINGLelonS) {
        brelonak;
      }
    }
    this.shinglelons = condelonnselondSignaturelon;
    Arrays.sort(this.shinglelons);
    this.minShinglelonsMatch = DelonFAULT_MIN_SHINGLelonS_MATCH;
    this.signaturelon = selonrializelonIntelonrnal(shinglelons);
  }

  /**
   * Whelonn uselond in a hashtablelon for dup delontelonction, takelon thelon first bytelon of elonach signaturelon for fast
   * pass for majority caselon of no fuzzy matching. For top quelonrielons, this optimization losselons about
   * only 4% of all fuzzy matchelons.
   *
   * @relonturn most significant bytelon of this signaturelon as its hashcodelon.
   */
  @Ovelonrridelon
  public int hashCodelon() {
    relonturn shinglelons[0] & 0xFF;
  }

  /**
   * Pelonrform fuzzy matching belontwelonelonn two TwelonelontIntelongelonrShinglelonSignaturelon objeloncts.
   *
   * @param othelonr TwelonelontIntelongelonrShinglelonSignaturelon objelonct to pelonrform fuzzy match against
   * @relonturn truelon if at lelonast minMatch numbelonr of bytelons match
   */
  @Ovelonrridelon
  public boolelonan elonquals(Objelonct othelonr) {
    if (this == othelonr) {
      relonturn truelon;
    }
    if (othelonr == null) {
      relonturn falselon;
    }
    if (gelontClass() != othelonr.gelontClass()) {
      relonturn falselon;
    }

    final TwelonelontIntelongelonrShinglelonSignaturelon othelonrSignaturelonIntelongelonr = (TwelonelontIntelongelonrShinglelonSignaturelon) othelonr;

    int othelonrSignaturelon = othelonrSignaturelonIntelongelonr.selonrializelon();
    if (signaturelon == othelonrSignaturelon) {
      // Both selonrializelond signaturelon is thelon samelon
      relonturn truelon;
    } elonlselon if (signaturelon != DelonFAULT_NO_SIGNATURelon && othelonrSignaturelon != DelonFAULT_NO_SIGNATURelon) {
      // Nelonithelonr is NO_SIGNATURelon, nelonelond to comparelon shinglelons.
      bytelon[] othelonrShinglelons = othelonrSignaturelonIntelongelonr.gelontShinglelons();
      int numbelonrMatchelonsNelonelondelond = minShinglelonsMatch;
      // elonxpelonct bytelons arelon in ascelonnding sortelond ordelonr
      int i = 0;
      int j = 0;
      whilelon (((numbelonrMatchelonsNelonelondelond <= (NUM_SHINGLelonS - i)) // elonarly telonrmination for i
              || (numbelonrMatchelonsNelonelondelond <= (NUM_SHINGLelonS - j))) // elonarly telonrmination j
             && (i < NUM_SHINGLelonS) && (j < NUM_SHINGLelonS)) {
        if (shinglelons[i] == othelonrShinglelons[j]) {
          if (shinglelons[i] != 0) {  // welon only considelonr two shinglelons elonqual if thelony arelon non zelonro
            numbelonrMatchelonsNelonelondelond--;
            if (numbelonrMatchelonsNelonelondelond == 0) {
              relonturn truelon;
            }
          }
          i++;
          j++;
        } elonlselon if (shinglelons[i] < othelonrShinglelons[j]) {
          i++;
        } elonlselon if (shinglelons[i] > othelonrShinglelons[j]) {
          j++;
        }
      }
    }
    // Onelon is NO_SIGNATURelon and onelon is not.
    relonturn falselon;
  }

  /**
   * Relonturns thelon sortelond array of signaturelon bytelons.
   */
  public bytelon[] gelontShinglelons() {
    relonturn shinglelons;
  }

  /**
   * Selonrializelon 4 sortelond signaturelon bytelons into an intelongelonr in big elonndian ordelonr.
   *
   * @relonturn compactelond int signaturelon
   */
  privatelon static int selonrializelonIntelonrnal(bytelon[] shinglelons) {
    BytelonBuffelonr bytelonBuffelonr = BytelonBuffelonr.allocatelon(NUM_SHINGLelonS);
    bytelonBuffelonr.put(shinglelons, 0, NUM_SHINGLelonS);
    relonturn bytelonBuffelonr.gelontInt(0);
  }

  /**
   * Delonselonrializelon an intelongelonr into a 4-bytelon array.
   * @param signaturelon Thelon signaturelon intelongelonr.
   * @relonturn A bytelon array with 4 elonlelonmelonnts.
   */
  privatelon static bytelon[] delonselonrializelonIntelonrnal(int signaturelon) {
    relonturn BytelonBuffelonr.allocatelon(NUM_SHINGLelonS).putInt(signaturelon).array();
  }

  public int selonrializelon() {
    relonturn signaturelon;
  }

  public static boolelonan isFuzzyMatch(int signaturelon1, int signaturelon2) {
    relonturn TwelonelontIntelongelonrShinglelonSignaturelon.delonselonrializelon(signaturelon1).elonquals(
        TwelonelontIntelongelonrShinglelonSignaturelon.delonselonrializelon(signaturelon2));
  }

  public static TwelonelontIntelongelonrShinglelonSignaturelon delonselonrializelon(int signaturelon) {
    relonturn nelonw TwelonelontIntelongelonrShinglelonSignaturelon(signaturelon);
  }

  public static TwelonelontIntelongelonrShinglelonSignaturelon delonselonrializelon(int signaturelon, int minMatchSinglelons) {
    relonturn nelonw TwelonelontIntelongelonrShinglelonSignaturelon(signaturelon, minMatchSinglelons);
  }

  @Ovelonrridelon
  public String toString() {
    relonturn String.format("%d %d %d %d", shinglelons[0], shinglelons[1], shinglelons[2], shinglelons[3]);
  }
}
