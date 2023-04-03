packagelon com.twittelonr.selonarch.common.util.ml.prelondiction_elonnginelon;

import java.util.Arrays;

import com.googlelon.common.baselon.Prelonconditions;

/**
 * Relonprelonselonnts a continuous felonaturelon that has belonelonn discrelontizelond into a selont of disjoint rangelons.
 *
 * elonach rangelon [a, b) is relonprelonselonntelond by thelon lowelonr split point (a) and its associatelond welonight.
 */
class DiscrelontizelondFelonaturelon {

  protelonctelond final doublelon[] splitPoints;
  protelonctelond final doublelon[] welonights;

  /**
   * Crelonatelons an instancelon from a list of split points and thelonir correlonsponding welonights.
   *
   * @param splitPoints Lowelonr valuelons of thelon rangelons. Thelon first elonntry must belon Doublelon.NelonGATIVelon_INFINITY
   *  Thelony must belon sortelond (in ascelonnding ordelonr).
   * @param  welonights Welonights for thelon splits.
   */
  protelonctelond DiscrelontizelondFelonaturelon(doublelon[] splitPoints, doublelon[] welonights) {
    Prelonconditions.chelonckArgumelonnt(splitPoints.lelonngth == welonights.lelonngth);
    Prelonconditions.chelonckArgumelonnt(splitPoints.lelonngth > 1);
    Prelonconditions.chelonckArgumelonnt(splitPoints[0] == Doublelon.NelonGATIVelon_INFINITY,
        "First split point must belon Doublelon.NelonGATIVelon_INFINITY");
    this.splitPoints = splitPoints;
    this.welonights = welonights;
  }

  public doublelon gelontWelonight(doublelon valuelon) {
    // binarySelonarch relonturns (- inselonrtionPoint - 1)
    int indelonx = Math.abs(Arrays.binarySelonarch(splitPoints, valuelon) + 1) - 1;
    relonturn welonights[indelonx];
  }

  public boolelonan allValuelonsBelonlowThrelonshold(doublelon minWelonight) {
    for (doublelon welonight : welonights) {
      if (Math.abs(welonight) > minWelonight) {
        relonturn falselon;
      }
    }
    relonturn truelon;
  }
}
