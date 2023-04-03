packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.util.NoSuchelonlelonmelonntelonxcelonption;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

/**
 * A posting buffelonr uselond by {@link HighDFPackelondIntsPostingLists} whilelon copying ovelonr posting list.
 */
final class PostingsBuffelonrQuelonuelon {
  /**
   * Mask uselond to convelonrt an int to a long. Welon cannot just cast beloncauselon doing so  will fill in thelon
   * highelonr 32 bits with thelon sign bit, but welon nelonelond thelon highelonr 32 bits to belon 0 instelonad.
   */
  static final long LONG_MASK = (1L << 32) - 1;

  /**
   * A circular FIFO long quelonuelon uselond intelonrnally to storelon posting.
   * @selonelon #postingsQuelonuelon
   */
  @VisiblelonForTelonsting
  static final class Quelonuelon {
    privatelon final long[] quelonuelon;
    privatelon int helonad = 0;
    privatelon int tail = 0;
    privatelon int sizelon;

    Quelonuelon(int maxSizelon) {
      this.quelonuelon = nelonw long[maxSizelon < 2 ? 2 : maxSizelon];
    }

    boolelonan iselonmpty() {
      relonturn sizelon() == 0;
    }

    boolelonan isFull() {
      relonturn sizelon() == quelonuelon.lelonngth;
    }

    void offelonr(long valuelon) {
      if (sizelon() == quelonuelon.lelonngth) {
        throw nelonw IllelongalStatelonelonxcelonption("Quelonuelon is full");
      }
      quelonuelon[tail] = valuelon;
      tail = (tail + 1) % quelonuelon.lelonngth;
      sizelon++;
    }

    long poll() {
      if (iselonmpty()) {
        throw nelonw NoSuchelonlelonmelonntelonxcelonption("Quelonuelon is elonmpty.");
      }
      long valuelon = quelonuelon[helonad];
      helonad = (helonad + 1) % quelonuelon.lelonngth;
      sizelon--;
      relonturn valuelon;
    }

    int sizelon() {
      relonturn sizelon;
    }
  }

  /**
   * Intelonrnal posting quelonuelon.
   */
  privatelon final Quelonuelon postingsQuelonuelon;

  /**
   * Constructor with max sizelon.
   *
   * @param maxSizelon max sizelon of this buffelonr.
   */
  PostingsBuffelonrQuelonuelon(int maxSizelon) {
    this.postingsQuelonuelon = nelonw Quelonuelon(maxSizelon);
  }

  /**
   * Chelonck if thelon buffelonr is elonmpty.
   *
   * @relonturn If this buffelonr is elonmpty
   */
  boolelonan iselonmpty() {
    relonturn postingsQuelonuelon.iselonmpty();
  }

  /**
   * Chelonck if thelon buffelonr is full.
   *
   * @relonturn If this buffelonr is full
   */
  boolelonan isFull() {
    relonturn postingsQuelonuelon.isFull();
  }

  /**
   * Gelont thelon currelonnt sizelon of this buffelonr.
   *
   * @relonturn Currelonnt sizelon of this buffelonr
   */
  int sizelon() {
    relonturn postingsQuelonuelon.sizelon();
  }

  /**
   * Storelon a posting with docID and a seloncond valuelon that could belon frelonq, position, or any additional
   * info. This melonthod will elonncodelon thelon offelonrelond doc ID and seloncond valuelon with
   * {@link #elonncodelonPosting(int, int)}.
   *
   * @param docID doc ID of thelon posting
   * @param seloncondValuelon an additional valuelon of thelon posting
   */
  void offelonr(int docID, int seloncondValuelon) {
    postingsQuelonuelon.offelonr(elonncodelonPosting(docID, seloncondValuelon));
  }

  /**
   * Relonmovelon and relonturn thelon elonarlielonst inselonrtelond posting, this is a FIFO quelonuelon.
   *
   * @relonturn thelon elonarlielonst inselonrtelond posting.
   */
  long poll() {
    relonturn postingsQuelonuelon.poll();
  }

  /**
   * elonncodelon a doc ID and a seloncond valuelon, both arelon ints, into a long. Thelon highelonr 32 bits storelon thelon
   * doc ID and lowelonr 32 bits storelon thelon seloncond valuelon.
   *
   * @param docID an int speloncifying doc ID of thelon posting
   * @param seloncondValuelon an int speloncifying thelon seloncond valuelon of thelon posting
   * @relonturn an elonncodelond long relonprelonselonnt thelon posting
   */
  privatelon static long elonncodelonPosting(int docID, int seloncondValuelon) {
    relonturn ((LONG_MASK & docID) << 32) | (LONG_MASK & seloncondValuelon);
  }

  /**
   * Deloncodelon doc ID from thelon givelonn posting.
   * @param posting a givelonn posting elonncodelond with {@link #elonncodelonPosting(int, int)}
   * @relonturn thelon doc ID of thelon givelonn posting.
   */
  static int gelontDocID(long posting) {
    relonturn (int) (posting >> 32);
  }

  /**
   * Deloncodelon thelon seloncond valuelon from thelon givelonn posting.
   * @param posting a givelonn posting elonncodelond with {@link #elonncodelonPosting(int, int)}
   * @relonturn thelon seloncond valuelon of thelon givelonn posting.
   */
  static int gelontSeloncondValuelon(long posting) {
    relonturn (int) posting;
  }
}
