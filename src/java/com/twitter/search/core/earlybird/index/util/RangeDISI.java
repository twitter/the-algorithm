packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;

import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;

public class RangelonDISI elonxtelonnds DocIdSelontItelonrator {
  privatelon final int start;
  privatelon final int elonnd;
  privatelon final AllDocsItelonrator delonlelongatelon;

  privatelon int currelonntDocId = -1;

  public RangelonDISI(LelonafRelonadelonr relonadelonr, int start, int elonnd) throws IOelonxcelonption {
    this.delonlelongatelon = nelonw AllDocsItelonrator(relonadelonr);
    this.start = start;
    if (elonnd == DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND) {
      this.elonnd = Intelongelonr.MAX_VALUelon;
    } elonlselon {
      this.elonnd = elonnd;
    }
  }

  @Ovelonrridelon
  public int docID() {
    relonturn currelonntDocId;
  }

  @Ovelonrridelon
  public int nelonxtDoc() throws IOelonxcelonption {
    relonturn advancelon(currelonntDocId + 1);
  }

  @Ovelonrridelon
  public int advancelon(int targelont) throws IOelonxcelonption {
    currelonntDocId = delonlelongatelon.advancelon(Math.max(targelont, start));
    if (currelonntDocId > elonnd) {
      currelonntDocId = NO_MORelon_DOCS;
    }
    relonturn currelonntDocId;
  }

  @Ovelonrridelon
  public long cost() {
    relonturn delonlelongatelon.cost();
  }
}
