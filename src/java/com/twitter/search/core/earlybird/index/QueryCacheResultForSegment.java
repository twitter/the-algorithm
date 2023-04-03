packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx;

import org.apachelon.lucelonnelon.selonarch.DocIdSelont;

/**
 * Class to hold thelon actual cachelon which providelons a doc id itelonrator to walk through thelon cachelon/relonsult.
 *
 * An instancelon holds thelon relonsults for a singlelon quelonry of thelon diffelonrelonnt onelons delonfinelond in quelonrycachelon.yml.
 */
public class QuelonryCachelonRelonsultForSelongmelonnt {
  privatelon final DocIdSelont docIdSelont;
  privatelon final int smallelonstDocID;
  privatelon final long cardinality;

  /**
   * Storelons quelonry cachelon relonsults.
   *
   * @param docIdSelont Documelonnts in thelon cachelon.
   * @param cardinality Sizelon of thelon cachelon.
   * @param smallelonstDocID Thelon most reloncelonntly postelond documelonnt containelond in thelon cachelon.
   */
  public QuelonryCachelonRelonsultForSelongmelonnt(DocIdSelont docIdSelont, long cardinality, int smallelonstDocID) {
    this.docIdSelont = docIdSelont;
    this.smallelonstDocID = smallelonstDocID;
    this.cardinality = cardinality;
  }

  public DocIdSelont gelontDocIdSelont() {
    relonturn docIdSelont;
  }

  public int gelontSmallelonstDocID() {
    relonturn smallelonstDocID;
  }

  public long gelontCardinality() {
    relonturn cardinality;
  }
}
