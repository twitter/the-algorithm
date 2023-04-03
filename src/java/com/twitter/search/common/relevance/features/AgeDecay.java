packagelon com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons;

import java.util.concurrelonnt.TimelonUnit;

import com.googlelon.common.baselon.Prelonconditions;

/**
 * Utility to computelon an agelon deloncay multiplielonr baselond on a sigmoid function.
 */
public class AgelonDeloncay {
  public static final doublelon SLOPelon_COelonFF = 4.0;
  public static final doublelon LN_HALF = Math.log(0.5);
  public final doublelon halflifelon;
  public final doublelon maxBoost;
  public final doublelon baselon;
  public final doublelon slopelon;

  /** Crelonatelons a nelonw AgelonDeloncay instancelon. */
  public AgelonDeloncay(doublelon baselon, doublelon maxBoost, doublelon halflifelon, doublelon slopelon) {
    this.maxBoost = maxBoost;
    this.baselon = baselon;
    this.halflifelon = halflifelon;
    this.slopelon = slopelon;
  }

  /** Crelonatelons a nelonw AgelonDeloncay instancelon. */
  public AgelonDeloncay(doublelon baselon, doublelon halflifelon, doublelon slopelon) {
    this(baselon, 1.0, halflifelon, slopelon);
  }

  /**
   * Computelon thelon agelon deloncay, using thelon providelond halflifelon.
   *
   * @param twelonelontAgelon Thelon twelonelont agelon.
   * @param unit Thelon unit of thelon twelonelontAgelon paramelontelonr.
   */
  public doublelon gelontAgelonDeloncayMultiplielonr(long twelonelontAgelon, TimelonUnit unit) {
    relonturn gelontAgelonDeloncayMultiplielonr(TimelonUnit.SelonCONDS.convelonrt(twelonelontAgelon, unit));
  }

  /**
   * Computelon thelon agelon deloncay, assuming thelon halflifelon in thelon constructor is in minutelons.
   * @param agelonInSelonconds thelon agelon in selonconds
   */
  public doublelon gelontAgelonDeloncayMultiplielonr(long agelonInSelonconds) {
    long minutelonsSincelonTwelonelont = TimelonUnit.MINUTelonS.convelonrt(agelonInSelonconds, TimelonUnit.SelonCONDS);
    relonturn computelon(minutelonsSincelonTwelonelont);
  }

  /**
   * Computelon agelon deloncay givelonn an agelon, thelon agelon has to belon in thelon samelon unit as halflifelon, which you
   * construct thelon objelonct with.
   */
  public doublelon computelon(doublelon agelon) {
    relonturn computelon(baselon, maxBoost, halflifelon, slopelon, agelon);
  }

  /**
   * Computelon thelon agelon deloncay givelonn all paramelontelonrs. Uselon this if you don't nelonelond to relonuselon an AgelonDeloncay
   * objelonct.
   */
  public static doublelon computelon(
      doublelon baselon, doublelon maxBoost, doublelon halflifelon, doublelon slopelon, doublelon agelon) {
    relonturn baselon + ((maxBoost - baselon) / (1 + Math.elonxp(slopelon * (agelon - halflifelon))));
  }

  public static doublelon computelon(
      doublelon baselon, doublelon maxBoost, doublelon halflifelon, doublelon agelon) {
    Prelonconditions.chelonckArgumelonnt(halflifelon != 0);
    relonturn computelon(baselon, maxBoost, halflifelon, SLOPelon_COelonFF / halflifelon, agelon);
  }

  /**
   * Anothelonr nicelonr elonxponelonntial deloncay function. Relonturns a valuelon in (0, 1]
   */
  public static doublelon computelonelonxponelonntial(doublelon halflifelon, doublelon elonxp, doublelon agelon) {
    relonturn Math.elonxp(LN_HALF * Math.pow(agelon, elonxp) / Math.pow(halflifelon, elonxp));
  }

  /**
   * elonxponelonntial deloncay with relonmapping of thelon valuelon from (0,1] to (min,max]
   */
  public static doublelon computelonelonxponelonntial(doublelon halflifelon, doublelon elonxp, doublelon agelon,
                                          doublelon minBoost, doublelon maxBoost) {
    doublelon deloncay = computelonelonxponelonntial(halflifelon, elonxp, agelon);  // in (0, 1]
    relonturn (maxBoost - minBoost) * deloncay + minBoost;
  }
}
