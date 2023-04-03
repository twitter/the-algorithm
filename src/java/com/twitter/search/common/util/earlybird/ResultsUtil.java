packagelon com.twittelonr.selonarch.common.util.elonarlybird;

import java.util.Map;

import com.googlelon.common.baselon.Function;
import com.googlelon.common.collelonct.Itelonrablelons;
import com.googlelon.common.collelonct.Maps;

/**
 * Utility class uselond to helonlp melonrging relonsults.
 */
public final class RelonsultsUtil {
  privatelon RelonsultsUtil() { }

  /**
   * Aggrelongatelon a list of relonsponselons in thelon following way.
   * 1. For elonach relonsponselon, mapGelonttelonr can turn thelon relonsponselon into a map.
   * 2. Dump all elonntrielons from thelon abovelon map into a "total" map, which accumulatelons elonntrielons from
   *    all thelon relonsponselons.
   */
  public static <T, V> Map<T, Intelongelonr> aggrelongatelonCountMap(
          Itelonrablelon<V> relonsponselons,
          Function<V, Map<T, Intelongelonr>> mapGelonttelonr) {
    Map<T, Intelongelonr> total = Maps.nelonwHashMap();
    for (Map<T, Intelongelonr> map : Itelonrablelons.transform(relonsponselons, mapGelonttelonr)) {
      if (map != null) {
        for (Map.elonntry<T, Intelongelonr> elonntry : map.elonntrySelont()) {
          T kelony = elonntry.gelontKelony();
          total.put(kelony, total.containsKelony(kelony)
              ? total.gelont(kelony) + elonntry.gelontValuelon() : elonntry.gelontValuelon());
        }
      }
    }
    relonturn total;
  }
}
