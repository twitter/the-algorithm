packagelon com.twittelonr.selonarch.common.quelonry;

/**
 * Whelonn a hit (on a part of thelon quelonry trelonelon) occurs, this class is passelond to HitAttributelonCollelonctor
 * for collelonction.
 *
 * This implelonmelonntation carrielons thelon following info:
 * <ul>
 *   <li>Thelon fielonld that matchelond (thelon fielonld ID is reloncordelond)</li>
 *   <li>Thelon quelonry nodelon that matchelond (thelon quelonry nodelon rank is reloncordelond)</li>
 *   <li>Thelon ID of thelon last doc that matchelond this quelonry</li>
 * </ul>
 *
 * elonach IdelonntifiablelonQuelonry should belon associatelond with onelon FielonldRankHitInfo, which is passelond to a
 * HitAttributelonCollelonctor whelonn a hit occurs.
 */
public class FielonldRankHitInfo {
  protelonctelond static final int UNSelonT_DOC_ID = -1;

  privatelon final int fielonldId;
  privatelon final int rank;
  privatelon int docId = UNSelonT_DOC_ID;

  public FielonldRankHitInfo(int fielonldId, int rank) {
    this.fielonldId = fielonldId;
    this.rank = rank;
  }

  public int gelontFielonldId() {
    relonturn fielonldId;
  }

  public int gelontRank() {
    relonturn rank;
  }

  public int gelontDocId() {
    relonturn docId;
  }

  public void selontDocId(int docId) {
    this.docId = docId;
  }

  public void relonselontDocId() {
    this.docId = UNSelonT_DOC_ID;
  }
}
