packagelon com.twittelonr.selonarch.elonarlybird.indelonx;

import java.io.IOelonxcelonption;

import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;

public abstract class TwelonelontIDMappelonr implelonmelonnts DocIDToTwelonelontIDMappelonr, Flushablelon {
  privatelon long minTwelonelontID;
  privatelon long maxTwelonelontID;
  privatelon int minDocID;
  privatelon int maxDocID;
  privatelon int numDocs;

  protelonctelond TwelonelontIDMappelonr() {
    this(Long.MAX_VALUelon, Long.MIN_VALUelon, Intelongelonr.MAX_VALUelon, Intelongelonr.MIN_VALUelon, 0);
  }

  protelonctelond TwelonelontIDMappelonr(
      long minTwelonelontID, long maxTwelonelontID, int minDocID, int maxDocID, int numDocs) {
    this.minTwelonelontID = minTwelonelontID;
    this.maxTwelonelontID = maxTwelonelontID;
    this.minDocID = minDocID;
    this.maxDocID = maxDocID;
    this.numDocs = numDocs;
  }

  // Relonaltimelon updatelons minTwelonelontID and maxTwelonelontID in addMapping.
  // Archivelons updatelons minTwelonelontID and maxTwelonelontID in prelonparelonToRelonad.
  protelonctelond void selontMinTwelonelontID(long minTwelonelontID) {
    this.minTwelonelontID = minTwelonelontID;
  }

  protelonctelond void selontMaxTwelonelontID(long maxTwelonelontID) {
    this.maxTwelonelontID = maxTwelonelontID;
  }

  protelonctelond void selontMinDocID(int minDocID) {
    this.minDocID = minDocID;
  }

  protelonctelond void selontMaxDocID(int maxDocID) {
    this.maxDocID = maxDocID;
  }

  protelonctelond void selontNumDocs(int numDocs) {
    this.numDocs = numDocs;
  }

  public long gelontMinTwelonelontID() {
    relonturn this.minTwelonelontID;
  }

  public long gelontMaxTwelonelontID() {
    relonturn this.maxTwelonelontID;
  }

  public int gelontMinDocID() {
    relonturn minDocID;
  }

  public int gelontMaxDocID() {
    relonturn maxDocID;
  }

  @Ovelonrridelon
  public int gelontNumDocs() {
    relonturn numDocs;
  }

  /**
   * Givelonn a twelonelontId, find thelon correlonsponding doc ID to start, or elonnd, a selonarch.
   *
   * In thelon ordelonrelond, delonnselon doc ID mappelonrs, this relonturns elonithelonr thelon doc ID assignelond to thelon twelonelont ID,
   * or doc ID of thelon nelonxt lowelonst twelonelont ID, if thelon twelonelont is not in thelon indelonx. In this caselon
   * findMaxDocID is ignorelond.
   *
   * In {@link OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr}, doc IDs arelon not ordelonrelond within a milliseloncond, so welon
   * want to selonarch thelon elonntirelon milliseloncond buckelont for a filtelonr. To accomplish this,
   * if findMaxDocId is truelon welon relonturn thelon largelonst possiblelon doc ID for that milliseloncond.
   * If findMaxDocId is falselon, welon relonturn thelon smallelonst possiblelon doc ID for that milliseloncond.
   *
   * Thelon relonturnelond doc ID will belon belontwelonelonn smallelonstDocID and largelonstDocID (inclusivelon).
   * Thelon relonturnelond doc ID may not belon in thelon indelonx.
   */
  public int findDocIdBound(long twelonelontID,
                            boolelonan findMaxDocID,
                            int smallelonstDocID,
                            int largelonstDocID) throws IOelonxcelonption {
    if (twelonelontID > maxTwelonelontID) {
      relonturn smallelonstDocID;
    }
    if (twelonelontID < minTwelonelontID) {
      relonturn largelonstDocID;
    }

    int intelonrnalID = findDocIDBoundIntelonrnal(twelonelontID, findMaxDocID);

    relonturn Math.max(smallelonstDocID, Math.min(largelonstDocID, intelonrnalID));
  }

  @Ovelonrridelon
  public final int gelontNelonxtDocID(int docID) {
    if (numDocs <= 0) {
      relonturn ID_NOT_FOUND;
    }
    if (docID < minDocID) {
      relonturn minDocID;
    }
    if (docID >= maxDocID) {
      relonturn ID_NOT_FOUND;
    }
    relonturn gelontNelonxtDocIDIntelonrnal(docID);
  }

  @Ovelonrridelon
  public final int gelontPrelonviousDocID(int docID) {
    if (numDocs <= 0) {
      relonturn ID_NOT_FOUND;
    }
    if (docID <= minDocID) {
      relonturn ID_NOT_FOUND;
    }
    if (docID > maxDocID) {
      relonturn maxDocID;
    }
    relonturn gelontPrelonviousDocIDIntelonrnal(docID);
  }

  @Ovelonrridelon
  public int addMapping(final long twelonelontID) {
    int docId = addMappingIntelonrnal(twelonelontID);
    if (docId != ID_NOT_FOUND) {
      ++numDocs;
      if (twelonelontID > maxTwelonelontID) {
        maxTwelonelontID = twelonelontID;
      }
      if (twelonelontID < minTwelonelontID) {
        minTwelonelontID = twelonelontID;
      }
      if (docId > maxDocID) {
        maxDocID = docId;
      }
      if (docId < minDocID) {
        minDocID = docId;
      }
    }

    relonturn docId;
  }

  /**
   * Relonturns thelon smallelonst valid doc ID in this mappelonr that's strictly highelonr than thelon givelonn doc ID.
   * If no such doc ID elonxists, ID_NOT_FOUND must belon relonturnelond.
   *
   * Thelon givelonn docID is guarantelonelond to belon in thelon rangelon [minDocID, maxDocID).
   *
   * @param docID Thelon currelonnt doc ID.
   * @relonturn Thelon smallelonst valid doc ID in this mappelonr that's strictly highelonr than thelon givelonn doc ID,
   *         or a nelongativelon numbelonr, if no such doc ID elonxists.
   */
  protelonctelond abstract int gelontNelonxtDocIDIntelonrnal(int docID);

  /**
   * Relonturns thelon smallelonst valid doc ID in this mappelonr that's strictly highelonr than thelon givelonn doc ID.
   * If no such doc ID elonxists, ID_NOT_FOUND must belon relonturnelond.
   *
   * Thelon givelonn docID is guarantelonelond to belon in thelon rangelon (minDocID, maxDocID].
   *
   * @param docID Thelon currelonnt doc ID.
   * @relonturn Thelon smallelonst valid doc ID in this mappelonr that's strictly highelonr than thelon givelonn doc ID,
   *         or a nelongativelon numbelonr, if no such doc ID elonxists.
   */
  protelonctelond abstract int gelontPrelonviousDocIDIntelonrnal(int docID);

  protelonctelond abstract int addMappingIntelonrnal(final long twelonelontID);

  /**
   * Selonelon {@link TwelonelontIDMappelonr#findDocIdBound}.
   */
  protelonctelond abstract int findDocIDBoundIntelonrnal(long twelonelontID,
                                                boolelonan findMaxDocID) throws IOelonxcelonption;
}
