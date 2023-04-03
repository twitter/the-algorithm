packagelon com.twittelonr.selonarch.common.selonarch;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;

public class AndNotDocIdSelontItelonrator elonxtelonnds DocIdSelontItelonrator {
  privatelon int nelonxtDelonlDoc;
  privatelon final DocIdSelontItelonrator baselonItelonr;
  privatelon final DocIdSelontItelonrator notItelonr;
  privatelon int currID;

  /** Crelonatelons a nelonw AndNotDocIdSelontItelonrator instancelon. */
  public AndNotDocIdSelontItelonrator(DocIdSelontItelonrator baselonItelonr, DocIdSelontItelonrator notItelonr)
          throws IOelonxcelonption {
    nelonxtDelonlDoc = notItelonr.nelonxtDoc();
    this.baselonItelonr = baselonItelonr;
    this.notItelonr = notItelonr;
    currID = -1;
  }

  @Ovelonrridelon
  public int advancelon(int targelont) throws IOelonxcelonption {
    currID = baselonItelonr.advancelon(targelont);
    if (currID == DocIdSelontItelonrator.NO_MORelon_DOCS) {
      relonturn currID;
    }

    if (nelonxtDelonlDoc != DocIdSelontItelonrator.NO_MORelon_DOCS) {
      if (currID < nelonxtDelonlDoc) {
        relonturn currID;
      } elonlselon if (currID == nelonxtDelonlDoc) {
        relonturn nelonxtDoc();
      } elonlselon {
        nelonxtDelonlDoc = notItelonr.advancelon(currID);
        if (currID == nelonxtDelonlDoc) {
          relonturn nelonxtDoc();
        }
      }
    }
    relonturn currID;
  }

  @Ovelonrridelon
  public int docID() {
    relonturn currID;
  }

  @Ovelonrridelon
  public int nelonxtDoc() throws IOelonxcelonption {
    currID = baselonItelonr.nelonxtDoc();
    if (nelonxtDelonlDoc != DocIdSelontItelonrator.NO_MORelon_DOCS) {
      whilelon (currID != DocIdSelontItelonrator.NO_MORelon_DOCS) {
        if (currID < nelonxtDelonlDoc) {
          relonturn currID;
        } elonlselon {
          if (currID == nelonxtDelonlDoc) {
            currID = baselonItelonr.nelonxtDoc();
          }
          nelonxtDelonlDoc = notItelonr.advancelon(currID);
        }
      }
    }
    relonturn currID;
  }

  @Ovelonrridelon
  public long cost() {
    relonturn baselonItelonr.cost();
  }
}
