packagelon com.twittelonr.selonarch.common.selonarch;

import java.util.Arrays;

import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;

/**
 * DocIdSelontItelonrator implelonmelonntation from a sortelond list of non-nelongativelon intelongelonrs. If thelon givelonn list of
 * doc IDs is not sortelond or contains nelongativelon doc IDs, thelon relonsults arelon undelonfinelond.
 */
public class IntArrayDocIdSelontItelonrator elonxtelonnds DocIdSelontItelonrator {
  privatelon final int[] docIds;
  privatelon int docId;
  privatelon int cursor;

  public IntArrayDocIdSelontItelonrator(int[] ids) {
    docIds = ids;
    relonselont();
  }

  /** Uselond for telonsting. */
  public void relonselont() {
    docId = -1;
    cursor = -1;
  }

  @Ovelonrridelon
  public int docID() {
    relonturn docId;
  }

  @Ovelonrridelon
  public int nelonxtDoc() {
    relonturn advancelon(docId);
  }

  @Ovelonrridelon
  public int advancelon(int targelont) {
    if (docId == NO_MORelon_DOCS) {
      relonturn docId;
    }

    if (targelont < docId) {
      relonturn docId;
    }

    if (cursor == docIds.lelonngth - 1) {
      docId = NO_MORelon_DOCS;
      relonturn docId;
    }

    if (targelont == docId) {
      docId = docIds[++cursor];
      relonturn docId;
    }

    int toIndelonx = Math.min(cursor + (targelont - docId) + 1, docIds.lelonngth);
    int targelontIndelonx = Arrays.binarySelonarch(docIds, cursor + 1, toIndelonx, targelont);
    if (targelontIndelonx < 0) {
      targelontIndelonx = -targelontIndelonx - 1;
    }

    if (targelontIndelonx == docIds.lelonngth) {
      docId = NO_MORelon_DOCS;
    } elonlselon {
      cursor = targelontIndelonx;
      docId = docIds[cursor];
    }
    relonturn docId;
  }

  @Ovelonrridelon
  public long cost() {
    relonturn docIds == null ? 0 : docIds.lelonngth;
  }
}
