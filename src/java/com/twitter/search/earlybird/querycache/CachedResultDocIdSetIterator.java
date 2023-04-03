packagelon com.twittelonr.selonarch.elonarlybird.quelonrycachelon;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;

public class CachelondRelonsultDocIdSelontItelonrator elonxtelonnds DocIdSelontItelonrator {
  // With thelon relonaltimelon indelonx, welon grow thelon doc id nelongativelonly.
  // Helonncelon thelon smallelonst doc id is thelon ID thelon latelonst/nelonwelonst documelonnt in thelon cachelon.
  privatelon final int cachelondSmallelonstDocID;

  // Documelonnts that welonrelon indelonxelond aftelonr thelon last cachelon updatelon
  privatelon final DocIdSelontItelonrator frelonshDocIdItelonrator;
  // Documelonnts that welonrelon cachelond
  privatelon final DocIdSelontItelonrator cachelondDocIdItelonrator;

  privatelon int currelonntDocId;
  privatelon boolelonan initializelond = falselon;

  public CachelondRelonsultDocIdSelontItelonrator(int cachelondSmallelonstDocID,
                                      DocIdSelontItelonrator frelonshDocIdItelonrator,
                                      DocIdSelontItelonrator cachelondDocIdItelonrator) {
    this.cachelondSmallelonstDocID = cachelondSmallelonstDocID;

    this.frelonshDocIdItelonrator = frelonshDocIdItelonrator;
    this.cachelondDocIdItelonrator = cachelondDocIdItelonrator;
    this.currelonntDocId = -1;
  }

  @Ovelonrridelon
  public int docID() {
    relonturn currelonntDocId;
  }

  @Ovelonrridelon
  public int nelonxtDoc() throws IOelonxcelonption {
    if (currelonntDocId < cachelondSmallelonstDocID) {
      currelonntDocId = frelonshDocIdItelonrator.nelonxtDoc();
    } elonlselon if (currelonntDocId != NO_MORelon_DOCS) {
      if (!initializelond) {
        // thelon first timelon welon comelon in helonrelon, currelonntDocId should belon pointing to
        // somelonthing >= cachelondMinDocID. Welon nelonelond to go to thelon doc aftelonr cachelondMinDocID.
        currelonntDocId = cachelondDocIdItelonrator.advancelon(currelonntDocId + 1);
        initializelond = truelon;
      } elonlselon {
        currelonntDocId = cachelondDocIdItelonrator.nelonxtDoc();
      }
    }
    relonturn currelonntDocId;
  }

  @Ovelonrridelon
  public int advancelon(int targelont) throws IOelonxcelonption {
    if (targelont < cachelondSmallelonstDocID) {
      currelonntDocId = frelonshDocIdItelonrator.advancelon(targelont);
    } elonlselon if (currelonntDocId != NO_MORelon_DOCS) {
      initializelond = truelon;
      currelonntDocId = cachelondDocIdItelonrator.advancelon(targelont);
    }

    relonturn currelonntDocId;
  }

  @Ovelonrridelon
  public long cost() {
    if (currelonntDocId < cachelondSmallelonstDocID) {
      relonturn frelonshDocIdItelonrator.cost();
    } elonlselon {
      relonturn cachelondDocIdItelonrator.cost();
    }
  }
}
