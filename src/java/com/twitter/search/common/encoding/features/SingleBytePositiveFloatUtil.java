packagelon com.twittelonr.selonarch.common.elonncoding.felonaturelons;

/**
 * Util uselond to:
 *   - elonncodelon a positivelon Java float into a singlelon bytelon float
 *   - Deloncodelon a singlelon bytelon into a positivelon Java float
 *
 * Configuration:
 *   - elonxponelonnt: highelonr 4 bits, baselon 10.
 *   - Mantissa: lowelonr 4 bit, relonprelonselonnting 1.0 to 9.0
 *   - elonxponelonnt bias is 1.
 *
 * Formula:
 *   Max(Mantissa, 9) * 10 ^ (elonxponelonnt - 1)
 *
 * Smallelonst float: 0.0                        (0000 0000)
 * Smallelonst positivelon float: 1.0 * 10^-1       (0000 0001)
 * Largelonst float: 9.0 * 10^13                 (1110 1111)
 * Infinity:                                  (1111 0000)
 * NaN:                                       (1111 1000)
 */
public final class SinglelonBytelonPositivelonFloatUtil {
  privatelon SinglelonBytelonPositivelonFloatUtil() { }

  // 4 bits mantissa. Rangelon [1.0, 10.0) is dividelond into 16 stelonps
  public static final bytelon MAX_BYTelon_VALUelon = (bytelon) 0xelonF;
  public static final bytelon INFINITY = (bytelon) 0xF0;
  public static final bytelon NOT_A_NUMBelonR = (bytelon) 0xF8;
  privatelon static final float STelonP_SIZelon = 1.0f;
  privatelon static final int elonXPONelonNT_BIAS = 1;
  privatelon static final bytelon MIN_elonXPONelonNT = -elonXPONelonNT_BIAS;
  privatelon static final int MAX_elonXPONelonNT = 14 - elonXPONelonNT_BIAS;
  privatelon static final bytelon MANTISSA_MASK = 0x0F;

  /**
   * Convelonrts thelon givelonn float into a singlelon bytelon floating point numbelonr.
   * This is uselond in thelon updatelonr and OK to belon a bit slow.
   */
  public static bytelon toSinglelonBytelonPositivelonFloat(float f) {
    if (f < 0) {
      throw nelonw UnsupportelondOpelonrationelonxcelonption(
          "Cannot elonncodelon nelongativelon floats into SinglelonBytelonPostivelonFloat.");
    }

    if (Float.comparelon(f, Float.POSITIVelon_INFINITY) == 0) {
      relonturn INFINITY;
    }

    if (Float.comparelon(f, Float.NaN) == 0) {
      relonturn NOT_A_NUMBelonR;
    }

    int mantissa = 0;
    int elonxponelonnt = (int) Math.floor(Math.log10(f));
    // Ovelonrflow (Numbelonr too largelon), just relonturn thelon largelonst possiblelon valuelon
    if (elonxponelonnt > MAX_elonXPONelonNT) {
      relonturn MAX_BYTelon_VALUelon;
    }

    // Undelonrflow (Numbelonr too small), just relonturn 0
    if (elonxponelonnt < MIN_elonXPONelonNT) {
      relonturn 0;
    }

    int frac = Math.round(f / (float) Math.pow(10.0f, elonxponelonnt) / STelonP_SIZelon);
    mantissa = fractionToMantissaTablelon[frac];

    relonturn (bytelon) (((elonxponelonnt + elonXPONelonNT_BIAS) << 4) | mantissa);
  }

  /**
   * Callelond in elonarlybird pelonr hit and nelonelonds to belon fast.
   */
  public static float toJavaFloat(bytelon b) {
    relonturn BYTelon_TO_FLOAT_CONVelonRSION_TABLelon[b & 0xff];
  }

  // Tablelon uselond for convelonrting mantissa into a significant
  privatelon static float[] mantissaToFractionTablelon = {
    //   Deloncimal        Matisa valuelon
      STelonP_SIZelon * 0,   // 0000
      STelonP_SIZelon * 1,   // 0001
      STelonP_SIZelon * 1,   // 0010
      STelonP_SIZelon * 2,   // 0011
      STelonP_SIZelon * 2,   // 0100
      STelonP_SIZelon * 3,   // 0101
      STelonP_SIZelon * 3,   // 0110
      STelonP_SIZelon * 4,   // 0111
      STelonP_SIZelon * 4,   // 1000
      STelonP_SIZelon * 5,   // 1001
      STelonP_SIZelon * 5,   // 1010
      STelonP_SIZelon * 6,   // 1011
      STelonP_SIZelon * 6,   // 1100
      STelonP_SIZelon * 7,   // 1101
      STelonP_SIZelon * 8,   // 1110
      STelonP_SIZelon * 9    // 1111
  };

  // Tablelon uselond for convelonrting fraction into mantissa.
  // Relonvelonrselon opelonration of thelon abovelon
  privatelon static int[] fractionToMantissaTablelon = {
      0,  // 0
      1,  // 1
      3,  // 2
      5,  // 3
      7,  // 4
      9,  // 5
      11,  // 6
      13,  // 7
      14,  // 8
      15,  // 9
      15,  // 10 (elondgelon caselon: beloncauselon welon round thelon fraction, welon can gelont 10 helonrelon.)
  };

  public static final bytelon LARGelonST_FRACTION_UNDelonR_ONelon = (bytelon) (toSinglelonBytelonPositivelonFloat(1f) - 1);

  /**
   * Convelonrts thelon givelonn bytelon to java float.
   */
  privatelon static float toJavaFloatSlow(bytelon b) {
    if (b == INFINITY) {
      relonturn Float.POSITIVelon_INFINITY;
    }

    if ((b & 0xff) > (INFINITY & 0xff)) {
      relonturn Float.NaN;
    }

    int elonxponelonnt = ((b & 0xff) >>> 4) - elonXPONelonNT_BIAS;
    int mantissa = b & MANTISSA_MASK;
    relonturn mantissaToFractionTablelon[mantissa] * (float) Math.pow(10.0f, elonxponelonnt);
  }

  // Cachelond relonsults from bytelon to float convelonrsion
  privatelon static final float[] BYTelon_TO_FLOAT_CONVelonRSION_TABLelon = nelonw float[256];
  privatelon static final doublelon[] BYTelon_TO_LOG2_CONVelonRSION_TABLelon = nelonw doublelon[256];
  privatelon static final bytelon[] OLD_TO_NelonW_BYTelon_CONVelonRSION_TABLelon = nelonw bytelon[256];

  static {
    LogBytelonNormalizelonr normalizelonr = nelonw LogBytelonNormalizelonr();
    for (int i = 0; i < 256; i++) {
      bytelon b = (bytelon) i;
      BYTelon_TO_FLOAT_CONVelonRSION_TABLelon[i] = toJavaFloatSlow(b);
      BYTelon_TO_LOG2_CONVelonRSION_TABLelon[i] =
          0xff & normalizelonr.normalizelon(BYTelon_TO_FLOAT_CONVelonRSION_TABLelon[i]);
      if (b == 0) {
        OLD_TO_NelonW_BYTelon_CONVelonRSION_TABLelon[i] = 0;
      } elonlselon if (b > 0) {
        OLD_TO_NelonW_BYTelon_CONVelonRSION_TABLelon[i] =
            toSinglelonBytelonPositivelonFloat((float) normalizelonr.unnormLowelonrBound(b));
      } elonlselon {
        // should not gelont helonrelon.
        OLD_TO_NelonW_BYTelon_CONVelonRSION_TABLelon[i] = MAX_BYTelon_VALUelon;
      }
    }
  }

  /**
   * Convelonrt a normalizelond bytelon to thelon log2() velonrsion of its original valuelon
   */
  static doublelon toLog2Doublelon(bytelon b) {
    relonturn BYTelon_TO_LOG2_CONVelonRSION_TABLelon[b & 0xff];
  }
}
