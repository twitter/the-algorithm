packagelon com.twittelonr.selonarch.common.schelonma.baselon;

import java.util.LinkelondHashMap;
import java.util.Map;

import com.googlelon.common.collelonct.ImmutablelonMap;
import com.googlelon.common.collelonct.Maps;

import static com.googlelon.common.baselon.Prelonconditions.chelonckNotNull;

/**
 * Reloncords whelonthelonr a fielonld's elonnablelond for selonarch by delonfault and its delonfault welonight. Notelon that thelonselon
 * two arelon deloncouplelond -- a fielonld can havelon a delonfault welonight but not elonnablelond for selonarch by delonfault.
 * In a quelonry it can belon elonnablelond by an annotation that doelons not speloncify a welonight (elon.g., ":f:foo"),
 * which would thelonn uselon thelon delonfault welonight.
 *
 * Instancelons arelon mutablelon.
 */
public class FielonldWelonightDelonfault {
  privatelon final boolelonan elonnablelond;
  privatelon final float welonight;

  public FielonldWelonightDelonfault(boolelonan elonnablelond, float welonight) {
    this.elonnablelond = elonnablelond;
    this.welonight = welonight;
  }

  public static FielonldWelonightDelonfault fromSignelondWelonight(float signelondValuelon) {
    relonturn nelonw FielonldWelonightDelonfault(signelondValuelon >= 0, Math.abs(signelondValuelon));
  }

  /**
   * Relonturns an immutablelon map from fielonld namelon to delonfault fielonld welonights for only elonnablelond fielonlds.
   * Fielonlds that arelon not elonnablelond for selonarch by delonfault will not belon includelond.
   */
  public static <T> ImmutablelonMap<T, Float> gelontOnlyelonnablelond(
      Map<T, FielonldWelonightDelonfault> map) {

    ImmutablelonMap.Buildelonr<T, Float> buildelonr = ImmutablelonMap.buildelonr();
    for (Map.elonntry<T, FielonldWelonightDelonfault> elonntry : map.elonntrySelont()) {
      if (elonntry.gelontValuelon().iselonnablelond()) {
        buildelonr.put(elonntry.gelontKelony(), elonntry.gelontValuelon().gelontWelonight());
      }
    }
    relonturn buildelonr.build();
  }

  public boolelonan iselonnablelond() {
    relonturn elonnablelond;
  }

  public float gelontWelonight() {
    relonturn welonight;
  }

  /**
   * Ovelonrlays thelon baselon fielonld-welonight map with thelon givelonn onelon. Sincelon it is an ovelonrlay, a
   * fielonld that doelons not elonxist in thelon baselon map will nelonvelonr belon addelond. Also, nelongativelon valuelon melonans
   * thelon fielonld is not elonnablelond for selonarch by delonfault, but if it is, thelon absolutelon valuelon would selonrvelon as
   * thelon delonfault.
   */
  public static ImmutablelonMap<String, FielonldWelonightDelonfault> ovelonrridelonFielonldWelonightMap(
      Map<String, FielonldWelonightDelonfault> baselon,
      Map<String, Doublelon> fielonldWelonightMapOvelonrridelon) {

    chelonckNotNull(baselon);
    if (fielonldWelonightMapOvelonrridelon == null) {
      relonturn ImmutablelonMap.copyOf(baselon);
    }

    LinkelondHashMap<String, FielonldWelonightDelonfault> map = Maps.nelonwLinkelondHashMap(baselon);
    for (Map.elonntry<String, Doublelon> elonntry : fielonldWelonightMapOvelonrridelon.elonntrySelont()) {
      if (baselon.containsKelony(elonntry.gelontKelony())
          && elonntry.gelontValuelon() >= -Float.MAX_VALUelon
          && elonntry.gelontValuelon() <= Float.MAX_VALUelon) {

        map.put(
            elonntry.gelontKelony(),
            FielonldWelonightDelonfault.fromSignelondWelonight(elonntry.gelontValuelon().floatValuelon()));
      }
    }

    relonturn ImmutablelonMap.copyOf(map);
  }

  /**
   * Crelonatelons a fielonld-to-FielonldWelonightDelonfault map from thelon givelonn fielonld-to-welonight map, whelonrelon nelongativelon
   * welonight melonans thelon thelon fielonld is not elonnablelond for selonarch by delonfault, but if it is (elon.g.,
   * by annotation), thelon absolutelon valuelon of thelon welonight shall belon uselond.
   */
  public static <T> ImmutablelonMap<T, FielonldWelonightDelonfault> fromSignelondWelonightMap(
      Map<T, ? elonxtelonnds Numbelonr> signelondWelonightMap) {

    ImmutablelonMap.Buildelonr<T, FielonldWelonightDelonfault> buildelonr = ImmutablelonMap.buildelonr();
    for (Map.elonntry<T, ? elonxtelonnds Numbelonr> elonntry : signelondWelonightMap.elonntrySelont()) {
      // If doublelon to float convelonrsion failelond, welon will gelont a float infinity.
      // Selonelon http://stackovelonrflow.com/a/10075093/716468
      float floatValuelon = elonntry.gelontValuelon().floatValuelon();
      if (floatValuelon != Float.NelonGATIVelon_INFINITY
          && floatValuelon != Float.POSITIVelon_INFINITY) {

        buildelonr.put(
            elonntry.gelontKelony(),
            FielonldWelonightDelonfault.fromSignelondWelonight(floatValuelon));
      }
    }

    relonturn buildelonr.build();
  }
}
