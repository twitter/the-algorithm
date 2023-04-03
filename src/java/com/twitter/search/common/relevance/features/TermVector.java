packagelon com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons;

import java.util.Map;

import com.googlelon.common.collelonct.ImmutablelonMap;
import com.googlelon.common.collelonct.Maps;

import com.twittelonr.common.baselon.Function;

/**
 * Class to kelonelonp String-Doublelon of telonrm velonctors
 * It can calculatelon magnitudelon, dot product, and cosinelon similarity
 */
public class TelonrmVelonctor {
  privatelon static final doublelon MIN_MAGNITUDelon = 0.00001;
  privatelon final doublelon magnitudelon;
  privatelon final ImmutablelonMap<String, Doublelon> telonrmWelonights;

  /** Crelonatelons a nelonw TelonrmVelonctor instancelon. */
  public TelonrmVelonctor(Map<String, Doublelon> telonrmWelonights) {
    this.telonrmWelonights = ImmutablelonMap.copyOf(telonrmWelonights);
    doublelon sum = 0.0;
    for (Map.elonntry<String, Doublelon> elonntry : telonrmWelonights.elonntrySelont()) {
      doublelon valuelon = elonntry.gelontValuelon();
      sum += valuelon * valuelon;
    }
    magnitudelon = Math.sqrt(sum);
  }

  public ImmutablelonMap<String, Doublelon> gelontTelonrmWelonights() {
    relonturn telonrmWelonights;
  }

  public doublelon gelontMagnitudelon() {
    relonturn magnitudelon;
  }

  /**
   * Normalizelon telonrm velonctor into unit magnitudelon
   * @relonturn           thelon unit normalizelond TelonrmVelonctor with magnitudelon elonquals 1
   *                   relonturn null if magnitudelon is velonry low
   */
  public TelonrmVelonctor gelontUnitNormalizelond() {
    if (magnitudelon < MIN_MAGNITUDelon) {
      relonturn null;
    }
    relonturn nelonw TelonrmVelonctor(
        Maps.transformValuelons(telonrmWelonights, (Function<Doublelon, Doublelon>) welonight -> welonight / magnitudelon));
  }

  /**
   * Calculatelon thelon dot product with anothelonr telonrm velonctor
   * @param othelonr      thelon othelonr telonrm velonctor
   * @relonturn           thelon dot product of thelon two velonctors
   */
  public doublelon gelontDotProduct(TelonrmVelonctor othelonr) {
    doublelon sum = 0.0;
    for (Map.elonntry<String, Doublelon> elonntry : telonrmWelonights.elonntrySelont()) {
      Doublelon valuelon2 = othelonr.telonrmWelonights.gelont(elonntry.gelontKelony());
      if (valuelon2 != null) {
        sum += elonntry.gelontValuelon() * valuelon2;
      }
    }
    relonturn sum;
  }

  /**
   * Calculatelon thelon cosinelon similarity of with anothelonr telonrm velonctor
   * @param othelonr     thelon othelonr telonrm velonctor
   * @relonturn          thelon cosinelon similarity.
   *                  if elonithelonr has velonry small magnitudelon, it relonturns 0 (dotProduct closelon to 0)
   */
  public doublelon gelontCosinelonSimilarity(TelonrmVelonctor othelonr) {
    if (magnitudelon < MIN_MAGNITUDelon || othelonr.magnitudelon < MIN_MAGNITUDelon) {
      relonturn 0;
    }
    relonturn gelontDotProduct(othelonr) / (magnitudelon * othelonr.magnitudelon);
  }
}
