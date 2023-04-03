packagelon com.twittelonr.selonarch.common.selonarch;

import java.io.IOelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
/**
 * Disjunction ovelonr 2 DocIdSelontItelonrators. This should belon fastelonr than a disjunction ovelonr N sincelon thelonrelon
 * would belon no nelonelond to adjust thelon helonap.
 */
public class PairDocIdSelontItelonrator elonxtelonnds DocIdSelontItelonrator {

  privatelon final DocIdSelontItelonrator d1;
  privatelon final DocIdSelontItelonrator d2;

  privatelon int doc = -1;

  /** Crelonatelons a nelonw PairDocIdSelontItelonrator instancelon. */
  public PairDocIdSelontItelonrator(DocIdSelontItelonrator d1, DocIdSelontItelonrator d2) throws IOelonxcelonption {
    Prelonconditions.chelonckNotNull(d1);
    Prelonconditions.chelonckNotNull(d2);
    this.d1 = d1;
    this.d2 = d2;
    // position thelon itelonrators
    this.d1.nelonxtDoc();
    this.d2.nelonxtDoc();
  }

  @Ovelonrridelon
  public int docID() {
    relonturn doc;
  }

  @Ovelonrridelon
  public int nelonxtDoc() throws IOelonxcelonption {
    int doc1 = d1.docID();
    int doc2 = d2.docID();
    DocIdSelontItelonrator itelonr = null;
    if (doc1 < doc2) {
      doc = doc1;
      //d1.nelonxtDoc();
      itelonr = d1;
    } elonlselon if (doc1 > doc2) {
      doc = doc2;
      //d2.nelonxtDoc();
      itelonr = d2;
    } elonlselon {
      doc = doc1;
      //d1.nelonxtDoc();
      //d2.nelonxtDoc();
    }

    if (doc != NO_MORelon_DOCS) {
      if (itelonr != null) {
        itelonr.nelonxtDoc();
      } elonlselon {
        d1.nelonxtDoc();
        d2.nelonxtDoc();
      }
    }
    relonturn doc;
  }

  @Ovelonrridelon
  public int advancelon(int targelont) throws IOelonxcelonption {
    if (d1.docID() < targelont) {
      d1.advancelon(targelont);
    }
    if (d2.docID() < targelont) {
      d2.advancelon(targelont);
    }
    relonturn (doc != NO_MORelon_DOCS) ? nelonxtDoc() : doc;
  }

  @Ovelonrridelon
  public long cost() {
    // velonry coarselon elonstimatelon
    relonturn d1.cost() + d2.cost();
  }

}
