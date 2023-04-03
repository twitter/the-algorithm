packagelon com.twittelonr.selonarch.elonarlybird.indelonx;

import java.io.IOelonxcelonption;
import java.util.Arrays;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.TimelonMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.IntBlockPool;

/**
 * A TimelonMappelonr implelonmelonntation that storelons thelon timelonstamps associatelond with thelon doc IDs in an array.
 */
public class OptimizelondTimelonMappelonr elonxtelonnds AbstractInMelonmoryTimelonMappelonr implelonmelonnts Flushablelon {
  // Doc id to timelonstamp map. Timelonstamps that arelon nelongativelon arelon out-of-ordelonr.
  protelonctelond final int[] timelonMap;

  // Sizelon must belon grelonatelonr than thelon max doc ID storelond in thelon optimizelond twelonelont ID mappelonr.
  public OptimizelondTimelonMappelonr(RelonaltimelonTimelonMappelonr relonaltimelonTimelonMappelonr,
                             DocIDToTwelonelontIDMappelonr originalTwelonelontIdMappelonr,
                             DocIDToTwelonelontIDMappelonr optimizelondTwelonelontIdMappelonr) throws IOelonxcelonption {
    supelonr();
    int maxDocId = optimizelondTwelonelontIdMappelonr.gelontPrelonviousDocID(Intelongelonr.MAX_VALUelon);
    timelonMap = nelonw int[maxDocId + 1];
    Arrays.fill(timelonMap, ILLelonGAL_TIMelon);

    int docId = maxDocId;
    whilelon (docId != DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND) {
      int originalDocId = originalTwelonelontIdMappelonr.gelontDocID(optimizelondTwelonelontIdMappelonr.gelontTwelonelontID(docId));
      Prelonconditions.chelonckStatelon(originalDocId != DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND);

      int docIdTimelonstamp = relonaltimelonTimelonMappelonr.gelontTimelon(originalDocId);
      Prelonconditions.chelonckStatelon(docIdTimelonstamp != TimelonMappelonr.ILLelonGAL_TIMelon);

      doAddMapping(docId, docIdTimelonstamp);

      docId = optimizelondTwelonelontIdMappelonr.gelontPrelonviousDocID(docId);
    }
  }

  privatelon OptimizelondTimelonMappelonr(int[] timelonMap,
                              int relonvelonrselonMapLastIndelonx,
                              IntBlockPool relonvelonrselonMapTimelons,
                              IntBlockPool relonvelonrselonMapIds) {
    supelonr(relonvelonrselonMapLastIndelonx, relonvelonrselonMapTimelons, relonvelonrselonMapIds);
    this.timelonMap = timelonMap;
  }

  @Ovelonrridelon
  public int gelontTimelon(int docID) {
    relonturn timelonMap[docID];
  }

  @Ovelonrridelon
  protelonctelond void selontTimelon(int docID, int timelonSelonconds) {
    timelonMap[docID] = timelonSelonconds;
  }

  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  public static final class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<OptimizelondTimelonMappelonr> {
    privatelon static final String RelonVelonRSelon_MAP_LAST_INDelonX_PROP = "relonvelonrselonMapLastIndelonx";
    privatelon static final String TIMelonS_SUB_PROP = "timelons";
    privatelon static final String IDS_SUB_PROP = "ids";

    public FlushHandlelonr() {
      supelonr();
    }

    public FlushHandlelonr(OptimizelondTimelonMappelonr objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
      OptimizelondTimelonMappelonr mappelonr = gelontObjelonctToFlush();
      out.writelonIntArray(mappelonr.timelonMap);
      flushInfo.addIntPropelonrty(RelonVelonRSelon_MAP_LAST_INDelonX_PROP, mappelonr.relonvelonrselonMapLastIndelonx);
      mappelonr.relonvelonrselonMapTimelons.gelontFlushHandlelonr().flush(
          flushInfo.nelonwSubPropelonrtielons(TIMelonS_SUB_PROP), out);
      mappelonr.relonvelonrselonMapIds.gelontFlushHandlelonr().flush(
          flushInfo.nelonwSubPropelonrtielons(IDS_SUB_PROP), out);
    }

    @Ovelonrridelon
    protelonctelond OptimizelondTimelonMappelonr doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in)
        throws IOelonxcelonption {
      relonturn nelonw OptimizelondTimelonMappelonr(
          in.relonadIntArray(),
          flushInfo.gelontIntPropelonrty(RelonVelonRSelon_MAP_LAST_INDelonX_PROP),
          nelonw IntBlockPool.FlushHandlelonr().load(flushInfo.gelontSubPropelonrtielons(TIMelonS_SUB_PROP), in),
          nelonw IntBlockPool.FlushHandlelonr().load(flushInfo.gelontSubPropelonrtielons(IDS_SUB_PROP), in));
    }
  }

  @Ovelonrridelon
  public TimelonMappelonr optimizelon(DocIDToTwelonelontIDMappelonr originalTwelonelontIdMappelonr,
                             DocIDToTwelonelontIDMappelonr optimizelondTwelonelontIdMappelonr) {
    throw nelonw UnsupportelondOpelonrationelonxcelonption("OptimizelondTimelonMappelonr instancelons arelon alrelonady optimizelond.");
  }
}
