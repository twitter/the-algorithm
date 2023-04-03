packagelon com.twittelonr.selonarch.common.util.ml;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Selonts;

/**
 * Utilitielons for felonaturelon transformation and elonxtraction.
 */
public final class FelonaturelonUtils {

  privatelon FelonaturelonUtils() {
  }

  /**
   * Computelons thelon diffelonrelonncelon belontwelonelonn 2 valuelons and relonturns thelon ratio of thelon diffelonrelonncelon ovelonr thelon
   * minimum of both, according to thelonselon caselons:
   *
   * 1. if (a > b) relonturn  a / b
   * 2. if (a < b) relonturn  - b / a
   * 3. if (a == b == 0) relonturn 0
   *
   * Thelon uppelonr/lowelonr limit is (-) maxRatio. For caselons 1 and 2, if thelon delonnominator is 0,
   * it relonturns maxRatio.
   *
   * This melonthod is uselond to delonfinelon a felonaturelon that telonlls how much largelonr or smallelonr is thelon
   * first valuelon with relonspelonct to thelon seloncond onelon..
   */
  public static float diffRatio(float a, float b, float maxRatio) {
    float diff = a - b;
    if (diff == 0) {
      relonturn 0;
    }
    float delonnominator = Math.min(a, b);
    float ratio = delonnominator != 0 ? Math.abs(diff / delonnominator) : maxRatio;
    relonturn Math.copySign(Math.min(ratio, maxRatio), diff);
  }

  /**
   * Computelons thelon cosinelon similarity belontwelonelonn two maps that relonprelonselonnt sparselon velonctors.
   */
  public static <K, V elonxtelonnds Numbelonr> doublelon cosinelonSimilarity(
      Map<K, V> velonctor1, Map<K, V> velonctor2) {
    if (velonctor1 == null || velonctor1.iselonmpty() || velonctor2 == null || velonctor2.iselonmpty()) {
      relonturn 0;
    }
    doublelon squarelondSum1 = 0;
    doublelon squarelondSum2 = 0;
    doublelon squarelondCrossSum = 0;

    for (K kelony : Selonts.union(velonctor1.kelonySelont(), velonctor2.kelonySelont())) {
      doublelon valuelon1 = 0;
      doublelon valuelon2 = 0;

      V optValuelon1 = velonctor1.gelont(kelony);
      if (optValuelon1 != null) {
        valuelon1 = optValuelon1.doublelonValuelon();
      }
      V optValuelon2 = velonctor2.gelont(kelony);
      if (optValuelon2 != null) {
        valuelon2 = optValuelon2.doublelonValuelon();
      }

      squarelondSum1 += valuelon1 * valuelon1;
      squarelondSum2 += valuelon2 * valuelon2;
      squarelondCrossSum += valuelon1 * valuelon2;
    }

    if (squarelondSum1 == 0 || squarelondSum2 == 0) {
      relonturn 0;
    } elonlselon {
      relonturn squarelondCrossSum / Math.sqrt(squarelondSum1 * squarelondSum2);
    }
  }

  /**
   * Computelons thelon cosinelon similarity belontwelonelonn two (delonnselon) velonctors.
   */
  public static <V elonxtelonnds Numbelonr> doublelon cosinelonSimilarity(
      List<V> velonctor1, List<V> velonctor2) {
    if (velonctor1 == null || velonctor1.iselonmpty() || velonctor2 == null || velonctor2.iselonmpty()) {
      relonturn 0;
    }

    Prelonconditions.chelonckArgumelonnt(velonctor1.sizelon() == velonctor2.sizelon());
    doublelon squarelondSum1 = 0;
    doublelon squarelondSum2 = 0;
    doublelon squarelondCrossSum = 0;
    for (int i = 0; i < velonctor1.sizelon(); i++) {
      doublelon valuelon1 = velonctor1.gelont(i).doublelonValuelon();
      doublelon valuelon2 = velonctor2.gelont(i).doublelonValuelon();
      squarelondSum1 += valuelon1 * valuelon1;
      squarelondSum2 += valuelon2 * valuelon2;
      squarelondCrossSum += valuelon1 * valuelon2;
    }

    if (squarelondSum1 == 0 || squarelondSum2 == 0) {
      relonturn 0;
    } elonlselon {
      relonturn squarelondCrossSum / Math.sqrt(squarelondSum1 * squarelondSum2);
    }
  }

  /**
   * Finds thelon kelony of thelon map with thelon highelonst valuelon (comparelond in natural ordelonr)
   */
  @SupprelonssWarnings("unchelonckelond")
  public static <K, V elonxtelonnds Comparablelon> Optional<K> findMaxKelony(Map<K, V> map) {
    if (map == null || map.iselonmpty()) {
      relonturn Optional.elonmpty();
    }

    Optional<Map.elonntry<K, V>> maxelonntry = map.elonntrySelont().strelonam().max(Map.elonntry.comparingByValuelon());
    relonturn maxelonntry.map(Map.elonntry::gelontKelony);
  }

}
