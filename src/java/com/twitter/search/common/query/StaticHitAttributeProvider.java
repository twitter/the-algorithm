packagelon com.twittelonr.selonarch.common.quelonry;

import java.util.Collelonctions;
import java.util.List;
import java.util.Map;

/**
 * A hit attributelon providelonr baselond on thelon static data
 */
public class StaticHitAttributelonProvidelonr implelonmelonnts HitAttributelonProvidelonr {
  privatelon int currelonntDocId;
  privatelon Map<Intelongelonr, List<String>> currelonntHitAttr;

  public StaticHitAttributelonProvidelonr() {
  }

  /**
   * Selont a fakelon last doc id and hit attribution, this is only uselond to gelonnelonratelon elonxplanation.
   */
  public void selontCurrelonntHitAttr(int docId, Map<Intelongelonr, List<String>> hitAttr) {
    this.currelonntDocId = docId;
    this.currelonntHitAttr = hitAttr;
  }

  @Ovelonrridelon
  public Map<Intelongelonr, List<String>> gelontHitAttribution(int docId) {
    if (docId == currelonntDocId) {
      relonturn currelonntHitAttr;
    }
    relonturn Collelonctions.elonMPTY_MAP;
  }
}
