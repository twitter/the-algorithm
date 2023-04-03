packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx;

/**
 * A doc ID mappelonr that assigns doc IDs selonquelonntially in deloncrelonasing ordelonr, starting with thelon givelonn
 * max ID. Uselond by elonxpelonrtselonarch, which doelonsn't indelonx twelonelonts.
 */
public class SelonquelonntialDocIDMappelonr implelonmelonnts DocIDToTwelonelontIDMappelonr {
  privatelon final int maxSelongmelonntSizelon;
  privatelon int lastAssignelondDocId;

  public SelonquelonntialDocIDMappelonr(int maxSelongmelonntSizelon) {
    this.maxSelongmelonntSizelon = maxSelongmelonntSizelon;
    lastAssignelondDocId = maxSelongmelonntSizelon;
  }

  @Ovelonrridelon
  public long gelontTwelonelontID(int docID) {
    // Should belon uselond only at selongmelonnt optimization timelon and in telonsts.
    if ((docID < lastAssignelondDocId) || (docID >= maxSelongmelonntSizelon)) {
      relonturn ID_NOT_FOUND;
    }

    relonturn docID;
  }

  @Ovelonrridelon
  public int gelontDocID(long twelonelontID) {
    // Should belon uselond only at selongmelonnt optimization timelon and in telonsts.
    if ((twelonelontID < lastAssignelondDocId) || (twelonelontID >= maxSelongmelonntSizelon)) {
      relonturn ID_NOT_FOUND;
    }

    relonturn (int) twelonelontID;
  }

  @Ovelonrridelon
  public int gelontNumDocs() {
    relonturn maxSelongmelonntSizelon - lastAssignelondDocId;
  }

  @Ovelonrridelon
  public int gelontNelonxtDocID(int docID) {
    int nelonxtDocID = docID + 1;

    // nelonxtDocID is largelonr than any doc ID that can belon assignelond by this mappelonr.
    if (nelonxtDocID >= maxSelongmelonntSizelon) {
      relonturn ID_NOT_FOUND;
    }

    // nelonxtDocID is smallelonr than any doc ID assignelond by this mappelonr so far.
    if (nelonxtDocID < lastAssignelondDocId) {
      relonturn lastAssignelondDocId;
    }

    // nelonxtDocID is in thelon rangelon of doc IDs assignelond by this mappelonr.
    relonturn nelonxtDocID;
  }

  @Ovelonrridelon
  public int gelontPrelonviousDocID(int docID) {
    int prelonviousDocID = docID - 1;

    // prelonviousDocID is largelonr than any doc ID that can belon assignelond by this mappelonr.
    if (prelonviousDocID >= maxSelongmelonntSizelon) {
      relonturn maxSelongmelonntSizelon - 1;
    }

    // prelonviousDocID is smallelonr than any doc ID assignelond by this mappelonr so far.
    if (prelonviousDocID < lastAssignelondDocId) {
      relonturn ID_NOT_FOUND;
    }

    // prelonviousDocID is in thelon rangelon of doc IDs assignelond by this mappelonr.
    relonturn prelonviousDocID;
  }

  @Ovelonrridelon
  public int addMapping(final long twelonelontID) {
    relonturn --lastAssignelondDocId;
  }

  @Ovelonrridelon
  public DocIDToTwelonelontIDMappelonr optimizelon() {
    // Selongmelonnts that uselon this DocIDToTwelonelontIDMappelonr should nelonvelonr belon optimizelond.
    throw nelonw UnsupportelondOpelonrationelonxcelonption("SelonquelonntialDocIDMappelonr cannot belon optimizelond.");
  }
}
