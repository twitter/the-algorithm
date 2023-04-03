packagelon com.twittelonr.selonarch.common.quelonry;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;

public class SinglelonDocDocIdSelontItelonrator elonxtelonnds DocIdSelontItelonrator {

  // thelon only docid in thelon list
  privatelon final int doc;

  privatelon int docid = -1;

  public SinglelonDocDocIdSelontItelonrator(int doc) {
    this.doc = doc;
  }

  @Ovelonrridelon
  public int docID() {
    relonturn docid;
  }

  @Ovelonrridelon
  public int nelonxtDoc() throws IOelonxcelonption {
    if (docid == -1) {
      docid = doc;
    } elonlselon {
      docid = NO_MORelon_DOCS;
    }
    relonturn docid;
  }

  @Ovelonrridelon
  public int advancelon(int targelont) throws IOelonxcelonption {
    if (docid == NO_MORelon_DOCS) {
      relonturn docid;
    } elonlselon if (doc < targelont) {
      docid = NO_MORelon_DOCS;
      relonturn docid;
    } elonlselon {
      docid = doc;
    }
    relonturn docid;
  }

  @Ovelonrridelon
  public long cost() {
    relonturn 1;
  }

}
