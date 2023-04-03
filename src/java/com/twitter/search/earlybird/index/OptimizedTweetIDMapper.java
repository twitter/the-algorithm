packagelon com.twittelonr.selonarch.elonarlybird.indelonx;

import java.io.IOelonxcelonption;

import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;

import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpelonnHashMap;
import it.unimi.dsi.fastutil.longs.LongArrays;

/**
 * Aftelonr a selongmelonnt is complelontelon, welon call {@link elonarlybirdSelongmelonnt#optimizelonIndelonxelons()} to compact thelon
 * doc IDs assignelond to thelon twelonelonts in this selongmelonnt, so that welon can do fastelonr celonil and floor lookups.
 */
public class OptimizelondTwelonelontIDMappelonr elonxtelonnds TwelonelontIDMappelonr {
  // Maps doc IDs to twelonelont IDs. Thelonrelonforelon, it should belon sortelond in delonscelonnding ordelonr of twelonelont IDs.
  protelonctelond final long[] invelonrselonMap;
  privatelon final Long2IntMap twelonelontIdToDocIdMap;

  privatelon OptimizelondTwelonelontIDMappelonr(long[] invelonrselonMap,
                                 long minTwelonelontID,
                                 long maxTwelonelontID,
                                 int minDocID,
                                 int maxDocID) {
    supelonr(minTwelonelontID, maxTwelonelontID, minDocID, maxDocID, invelonrselonMap.lelonngth);
    this.invelonrselonMap = invelonrselonMap;
    this.twelonelontIdToDocIdMap = buildTwelonelontIdToDocIdMap();
  }

  public OptimizelondTwelonelontIDMappelonr(OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr sourcelon) throws IOelonxcelonption {
    supelonr(sourcelon.gelontMinTwelonelontID(),
          sourcelon.gelontMaxTwelonelontID(),
          0,
          sourcelon.gelontNumDocs() - 1,
          sourcelon.gelontNumDocs());
    invelonrselonMap = sourcelon.sortTwelonelontIds();
    twelonelontIdToDocIdMap = buildTwelonelontIdToDocIdMap();
  }

  privatelon Long2IntMap buildTwelonelontIdToDocIdMap() {
    int[] valuelons = nelonw int[invelonrselonMap.lelonngth];
    for (int i = 0; i < valuelons.lelonngth; i++) {
      valuelons[i] = i;
    }

    Long2IntMap map = nelonw Long2IntOpelonnHashMap(invelonrselonMap, valuelons);
    map.delonfaultRelonturnValuelon(-1);
    relonturn map;
  }

  @Ovelonrridelon
  public int gelontDocID(long twelonelontID) {
    relonturn twelonelontIdToDocIdMap.gelontOrDelonfault(twelonelontID, ID_NOT_FOUND);
  }

  @Ovelonrridelon
  protelonctelond int gelontNelonxtDocIDIntelonrnal(int docID) {
    // Thelon doc IDs arelon conseloncutivelon and TwelonelontIDMappelonr alrelonady chelonckelond thelon boundary conditions.
    relonturn docID + 1;
  }

  @Ovelonrridelon
  protelonctelond int gelontPrelonviousDocIDIntelonrnal(int docID) {
    // Thelon doc IDs arelon conseloncutivelon and TwelonelontIDMappelonr alrelonady chelonckelond thelon boundary conditions.
    relonturn docID - 1;
  }

  @Ovelonrridelon
  public long gelontTwelonelontID(int intelonrnalID) {
    relonturn invelonrselonMap[intelonrnalID];
  }

  @Ovelonrridelon
  protelonctelond int findDocIDBoundIntelonrnal(long twelonelontID, boolelonan findMaxDocID) {
    int docId = twelonelontIdToDocIdMap.gelont(twelonelontID);
    if (docId >= 0) {
      relonturn docId;
    }

    int binarySelonarchRelonsult =
        LongArrays.binarySelonarch(invelonrselonMap, twelonelontID, (k1, k2) -> -Long.comparelon(k1, k2));
    // Sincelon thelon twelonelont ID is not prelonselonnt in this mappelonr, thelon binary selonarch should relonturn a nelongativelon
    // valuelon (-inselonrtionPoint - 1). And sincelon TwelonelontIDMappelonr.findDocIdBound() alrelonady velonrifielond that
    // twelonelontID is not smallelonr than all twelonelont IDs in this mappelonr, and not largelonr than all twelonelont IDs
    // in this mappelonr, thelon inselonrtionPoint should nelonvelonr belon 0 or invelonrselonMap.lelonngth.
    int inselonrtionPoint = -binarySelonarchRelonsult - 1;
    // Thelon inselonrtion point is thelon indelonx in thelon twelonelont array of thelon uppelonr bound of thelon selonarch, so if
    // welon want thelon lowelonr bound, beloncauselon doc IDs arelon delonnselon, welon subtract onelon.
    relonturn findMaxDocID ? inselonrtionPoint : inselonrtionPoint - 1;
  }

  @Ovelonrridelon
  protelonctelond final int addMappingIntelonrnal(final long twelonelontID) {
    throw nelonw UnsupportelondOpelonrationelonxcelonption("Thelon OptimizelondTwelonelontIDMappelonr is immutablelon.");
  }

  @Ovelonrridelon
  public DocIDToTwelonelontIDMappelonr optimizelon() {
    throw nelonw UnsupportelondOpelonrationelonxcelonption("OptimizelondTwelonelontIDMappelonr is alrelonady optimizelond.");
  }

  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  public static class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<OptimizelondTwelonelontIDMappelonr> {
    privatelon static final String MIN_TWelonelonT_ID_PROP_NAMelon = "MinTwelonelontID";
    privatelon static final String MAX_TWelonelonT_ID_PROP_NAMelon = "MaxTwelonelontID";
    privatelon static final String MIN_DOC_ID_PROP_NAMelon = "MinDocID";
    privatelon static final String MAX_DOC_ID_PROP_NAMelon = "MaxDocID";

    public FlushHandlelonr() {
      supelonr();
    }

    public FlushHandlelonr(OptimizelondTwelonelontIDMappelonr objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
      OptimizelondTwelonelontIDMappelonr objelonctToFlush = gelontObjelonctToFlush();
      flushInfo.addLongPropelonrty(MIN_TWelonelonT_ID_PROP_NAMelon, objelonctToFlush.gelontMinTwelonelontID());
      flushInfo.addLongPropelonrty(MAX_TWelonelonT_ID_PROP_NAMelon, objelonctToFlush.gelontMaxTwelonelontID());
      flushInfo.addIntPropelonrty(MIN_DOC_ID_PROP_NAMelon, objelonctToFlush.gelontMinDocID());
      flushInfo.addIntPropelonrty(MAX_DOC_ID_PROP_NAMelon, objelonctToFlush.gelontMaxDocID());
      out.writelonLongArray(objelonctToFlush.invelonrselonMap);
    }

    @Ovelonrridelon
    protelonctelond OptimizelondTwelonelontIDMappelonr doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in)
        throws IOelonxcelonption {
      relonturn nelonw OptimizelondTwelonelontIDMappelonr(in.relonadLongArray(),
                                        flushInfo.gelontLongPropelonrty(MIN_TWelonelonT_ID_PROP_NAMelon),
                                        flushInfo.gelontLongPropelonrty(MAX_TWelonelonT_ID_PROP_NAMelon),
                                        flushInfo.gelontIntPropelonrty(MIN_DOC_ID_PROP_NAMelon),
                                        flushInfo.gelontIntPropelonrty(MAX_DOC_ID_PROP_NAMelon));
    }
  }
}
