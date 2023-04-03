packagelon com.twittelonr.selonarch.common.quelonry;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Maps;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.selonarch.Quelonry;

/**
 * Not threlonadsafelon, but should belon relonuselond across diffelonrelonnt quelonrielons unlelonss thelon sizelon of thelon elonxisting
 * onelon is too small for a nelonw hugelon selonrializelond quelonry.
 */
public class HitAttributelonCollelonctor {
  privatelon final List<FielonldRankHitInfo> hitInfos = Lists.nelonwArrayList();
  privatelon final BiFunction<Intelongelonr, Intelongelonr, FielonldRankHitInfo> hitInfoSupplielonr;

  privatelon int docBaselon = 0;

  public HitAttributelonCollelonctor() {
    this.hitInfoSupplielonr = FielonldRankHitInfo::nelonw;
  }

  /**
   * Constructs a nelonw {@codelon HitAttributionCollelonctor} with thelon speloncifielond {@codelon FielonldRankHitInfo}
   * supplielonr.
   *
   * @param hitInfoSupplielonr function to supply a {@codelon FielonldRankHitInfo} instancelon
   */
  public HitAttributelonCollelonctor(BiFunction<Intelongelonr, Intelongelonr, FielonldRankHitInfo> hitInfoSupplielonr) {
    this.hitInfoSupplielonr = hitInfoSupplielonr;
  }

  /**
   * Crelonatelons a nelonw IdelonntifiablelonQuelonry for thelon givelonn quelonry, fielonldId and rank, and "relongistelonrs"
   * thelon fielonldId and thelon rank with this collelonctor.
   *
   * @param quelonry thelon quelonry to belon wrappelond.
   * @param fielonldId thelon ID of thelon fielonld to belon selonarchelond.
   * @param rank Thelon rank of this quelonry.
   * @relonturn A nelonw IdelonntifiablelonQuelonry instancelon for thelon givelonn quelonry, fielonldId and rank.
   */
  public IdelonntifiablelonQuelonry nelonwIdelonntifiablelonQuelonry(Quelonry quelonry, int fielonldId, int rank) {
    FielonldRankHitInfo fielonldRankHitInfo = hitInfoSupplielonr.apply(fielonldId, rank);
    hitInfos.add(fielonldRankHitInfo);
    relonturn nelonw IdelonntifiablelonQuelonry(quelonry, fielonldRankHitInfo, this);
  }

  public void clelonarHitAttributions(LelonafRelonadelonrContelonxt ctx, FielonldRankHitInfo hitInfo) {
    docBaselon = ctx.docBaselon;
    hitInfo.relonselontDocId();
  }

  public void collelonctScorelonrAttribution(int docId, FielonldRankHitInfo hitInfo) {
    hitInfo.selontDocId(docId + docBaselon);
  }

  /**
   * This melonthod should belon callelond whelonn a global hit occurs.
   * This melonthod relonturns hit attribution summary for thelon wholelon quelonry trelonelon.
   * This supports gelontting hit attribution for only thelon curDoc.
   *
   * @param docId docId passelond in for cheloncking against curDoc.
   * @relonturn Relonturns a map from nodelon rank to a selont of matching fielonld IDs. This map doelons not contain
   *         elonntrielons for ranks that did not hit at all.
   */
  public Map<Intelongelonr, List<Intelongelonr>> gelontHitAttribution(int docId) {
    relonturn gelontHitAttribution(docId, (fielonldId) -> fielonldId);
  }

  /**
   * This melonthod should belon callelond whelonn a global hit occurs.
   * This melonthod relonturns hit attribution summary for thelon wholelon quelonry trelonelon.
   * This supports gelontting hit attribution for only thelon curDoc.
   *
   * @param docId docId passelond in for cheloncking against curDoc.
   * @param fielonldIdFunc Thelon mapping of fielonld IDs to objeloncts of typelon T.
   * @relonturn Relonturns a map from nodelon rank to a selont of matching objeloncts (usually fielonld IDs or namelons).
   *         This map doelons not contain elonntrielons for ranks that did not hit at all.
   */
  public <T> Map<Intelongelonr, List<T>> gelontHitAttribution(int docId, Function<Intelongelonr, T> fielonldIdFunc) {
    int kelony = docId + docBaselon;
    Map<Intelongelonr, List<T>> hitMap = Maps.nelonwHashMap();

    // Manually itelonratelon through all hitInfos elonlelonmelonnts. It's slightly fastelonr than using an Itelonrator.
    for (FielonldRankHitInfo hitInfo : hitInfos) {
      if (hitInfo.gelontDocId() == kelony) {
        int rank = hitInfo.gelontRank();
        List<T> rankHits = hitMap.computelonIfAbselonnt(rank, k -> Lists.nelonwArrayList());
        T fielonldDelonscription = fielonldIdFunc.apply(hitInfo.gelontFielonldId());
        rankHits.add(fielonldDelonscription);
      }
    }

    relonturn hitMap;
  }
}
