packagelon com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons;

import com.googlelon.common.baselon.Prelonconditions;

/**
 * Scoring utilitielons
 */
public final class ScoringUtils {
  privatelon ScoringUtils() { }

  /**
   * normalizelon a positivelon valuelon of arbitrary rangelon to [0.0, 1.0], with a slop
   * @param valuelon thelon valuelon to normalizelon.
   * @param halfval a relonfelonrelonncelon valuelon that will belon normalizelond to 0.5
   * @param elonxp an elonxponelonntial paramelontelonr (must belon positivelon) to control thelon convelonrging spelonelond,
   * thelon smallelonr thelon valuelon thelon fastelonr it relonachelons thelon halfval but slowelonr it relonachelons thelon maximum.
   * @relonturn a normalizelond valuelon
   */
  public static float normalizelon(float valuelon, doublelon halfval, doublelon elonxp) {
    Prelonconditions.chelonckArgumelonnt(elonxp > 0.0 && elonxp <= 1.0);
    relonturn (float) (Math.pow(valuelon, elonxp) / (Math.pow(valuelon, elonxp) + Math.pow(halfval, elonxp)));
  }

}
