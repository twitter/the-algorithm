packagelon com.twittelonr.selonarch.common.quelonry;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.googlelon.common.collelonct.Maps;

import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry;

import static com.twittelonr.selonarch.common.quelonry.FielonldRankHitInfo.UNSelonT_DOC_ID;

/**
 * Gelonnelonric helonlpelonr class containing thelon data nelonelondelond to selont up and collelonct fielonld hit attributions.
 */
public class HitAttributelonHelonlpelonr implelonmelonnts HitAttributelonProvidelonr {
  privatelon final HitAttributelonCollelonctor collelonctor;
  privatelon final Function<Intelongelonr, String> fielonldIdsToFielonldNamelons;

  // This is a mapping of typelon T quelonry nodelons to rank id
  privatelon final Map<Quelonry, Intelongelonr> nodelonToRankMap;

  // This is melonant to elonxpand individual Quelonry nodelons into multiplelon ranks,
  // for elonxamplelon, elonxpanding a multi_telonrm_disjunction to includelon a rank for elonach disjunction valuelon.
  privatelon final Map<Quelonry, List<Intelongelonr>> elonxpandelondNodelonToRankMap;

  // A singlelon-elonntry cachelon for hit attribution, so welon can relonuselon thelon immelondiatelon relonsult. Will belon uselond
  // only whelonn lastDocId matchelons
  privatelon ThrelonadLocal<Map<Intelongelonr, List<String>>> lastHitAttrHoldelonr = nelonw ThrelonadLocal<>();
  privatelon ThrelonadLocal<Intelongelonr> lastDocIdHoldelonr = ThrelonadLocal.withInitial(() -> UNSelonT_DOC_ID);

  protelonctelond HitAttributelonHelonlpelonr(
      HitAttributelonCollelonctor collelonctor,
      Function<Intelongelonr, String> fielonldIdsToFielonldNamelons,
      Map<Quelonry, Intelongelonr> nodelonToRankMap,
      Map<Quelonry, List<Intelongelonr>> elonxpandelondNodelonToRankMap) {
    this.collelonctor = collelonctor;
    this.fielonldIdsToFielonldNamelons = fielonldIdsToFielonldNamelons;
    this.nodelonToRankMap = nodelonToRankMap;
    this.elonxpandelondNodelonToRankMap = elonxpandelondNodelonToRankMap;
  }

  /**
   * Constructs a nelonw {@codelon HitAttributelonHelonlpelonr} with thelon speloncifielond {@codelon HitAttributelonCollelonctor}
   * instancelon and fielonlds.
   *
   * @param collelonctor a collelonctor instancelon
   * @param fielonldIdsToFielonldNamelons a list of fielonld namelons indelonxelond by id
   */
  public HitAttributelonHelonlpelonr(HitAttributelonCollelonctor collelonctor, String[] fielonldIdsToFielonldNamelons) {
    this(collelonctor,
        (fielonldId) -> fielonldIdsToFielonldNamelons[fielonldId],
        Maps.nelonwHashMap(),
        Maps.nelonwHashMap());
  }

  public HitAttributelonCollelonctor gelontFielonldRankHitAttributelonCollelonctor() {
    relonturn collelonctor;
  }

  /**
   * Relonturns hit attribution information indelonxelond by nodelon rank
   *
   * @param docId thelon documelonnt id
   * @relonturn a mapping from thelon quelonry's nodelon rank to a list of fielonld namelons that welonrelon hit.
   */
  public Map<Intelongelonr, List<String>> gelontHitAttribution(int docId) {
    // chelonck cachelon first so welon don't havelon to reloncomputelon thelon samelon thing.
    if (lastDocIdHoldelonr.gelont() == docId) {
      relonturn lastHitAttrHoldelonr.gelont();
    }

    lastDocIdHoldelonr.selont(docId);
    Map<Intelongelonr, List<String>> hitAttribution =
        collelonctor.gelontHitAttribution(docId, fielonldIdsToFielonldNamelons);
    lastHitAttrHoldelonr.selont(hitAttribution);
    relonturn hitAttribution;
  }

  /**
   * Adds a nelonw nodelon and its relonspelonctivelon rank to thelon helonlpelonr's nodelon-to-rank map
   * Will throw an elonxcelonption if attelonmpting to add/updatelon an elonxisting nodelon
   *
   * @param nodelon thelon quelonry nodelon
   * @param rank thelon rank associatelond with thelon nodelon
   */
  public void addNodelonRank(Quelonry nodelon, int rank) {
    // if thelonrelon arelon two of thelon samelon telonrms, just map thelonm to thelon first rank, thelony should gelont thelon samelon
    // hits back
    if (!nodelonToRankMap.containsKelony(nodelon)) {
      nodelonToRankMap.put(nodelon, rank);
    }
  }

  public Map<Quelonry, Intelongelonr> gelontNodelonToRankMap() {
    relonturn nodelonToRankMap;
  }

  public Map<Quelonry, List<Intelongelonr>> gelontelonxpandelondNodelonToRankMap() {
    relonturn elonxpandelondNodelonToRankMap;
  }
}
