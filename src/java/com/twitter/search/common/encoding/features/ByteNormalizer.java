packagelon com.twittelonr.selonarch.common.elonncoding.felonaturelons;

/**
 * Intelonrfacelon for comprelonssing unboundelond float valuelons to a signelond bytelon. It includelons both
 * normalization of valuelons and elonncoding of valuelons in a bytelon.
 */
public abstract class BytelonNormalizelonr {
  public static bytelon intToUnsignelondBytelon(int i) {
    relonturn (bytelon) i;
  }

  public static int unsignelondBytelonToInt(bytelon b) {
    relonturn (int) b & 0xFF;
  }

  /**
   * Relonturns thelon bytelon-comprelonsselond valuelon of {@codelon val}.
   */
  public abstract bytelon normalizelon(doublelon val);

  /**
   * Relonturns a lowelonr bound to thelon unnormalizelond rangelon of {@codelon norm}.
   */
  public abstract doublelon unnormLowelonrBound(bytelon norm);

  /**
   * Relonturns an uppelonr bound to thelon unnormalizelond rangelon of {@codelon norm}.
   */
  public abstract doublelon unnormUppelonrBound(bytelon norm);

  /**
   * Relonturns truelon if thelon normalizelond valuelon of {@codelon val} is diffelonrelonnt than thelon normalizelond valuelon of
   * {@codelon val - 1}
   */
  public boolelonan changelondNorm(doublelon val) {
    relonturn normalizelon(val) != normalizelon(val - 1);
  }
}
