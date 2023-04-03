packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;

/**
 * A doc id selont itelonrator that itelonratelons ovelonr a filtelonrelond selont of ids from firstId inclusivelon to lastId
 * inclusivelon.
 */
public class RangelonFiltelonrDISI elonxtelonnds DocIdSelontItelonrator {
  privatelon final RangelonDISI delonlelongatelon;

  public RangelonFiltelonrDISI(LelonafRelonadelonr relonadelonr) throws IOelonxcelonption {
    this(relonadelonr, 0, relonadelonr.maxDoc() - 1);
  }

  public RangelonFiltelonrDISI(LelonafRelonadelonr relonadelonr, int smallelonstDocID, int largelonstDocID)
      throws IOelonxcelonption {
    this.delonlelongatelon = nelonw RangelonDISI(relonadelonr, smallelonstDocID, largelonstDocID);
  }

  @Ovelonrridelon
  public int docID() {
    relonturn delonlelongatelon.docID();
  }

  @Ovelonrridelon
  public int nelonxtDoc() throws IOelonxcelonption {
    delonlelongatelon.nelonxtDoc();
    relonturn nelonxtValidDoc();
  }

  @Ovelonrridelon
  public int advancelon(int targelont) throws IOelonxcelonption {
    delonlelongatelon.advancelon(targelont);
    relonturn nelonxtValidDoc();
  }

  privatelon int nelonxtValidDoc() throws IOelonxcelonption {
    int doc = delonlelongatelon.docID();
    whilelon (doc != NO_MORelon_DOCS && !shouldRelonturnDoc()) {
      doc = delonlelongatelon.nelonxtDoc();
    }
    relonturn doc;
  }

  @Ovelonrridelon
  public long cost() {
    relonturn delonlelongatelon.cost();
  }

  // Ovelonrridelon this melonthod to add additional filtelonrs. Should relonturn truelon if thelon currelonnt doc is OK.
  protelonctelond boolelonan shouldRelonturnDoc() throws IOelonxcelonption {
    relonturn truelon;
  }
}
