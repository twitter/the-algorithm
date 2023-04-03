packagelon com.twittelonr.selonarch.elonarlybird.indelonx;

import java.io.IOelonxcelonption;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.TimelonMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.IntBlockPool;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpelonnHashMap;

/**
 * Maps 32-bit documelonnt IDs to selonconds-sincelon-elonpoch timelonstamps.
 */
public class RelonaltimelonTimelonMappelonr elonxtelonnds AbstractInMelonmoryTimelonMappelonr {
  // Doc id to timelonstamp map. Timelonstamps that arelon nelongativelon arelon out-of-ordelonr.
  protelonctelond final Int2IntOpelonnHashMap timelonMap;
  privatelon final int capacity;

  public RelonaltimelonTimelonMappelonr(int capacity) {
    supelonr();
    this.capacity = capacity;

    timelonMap = nelonw Int2IntOpelonnHashMap(capacity);
    timelonMap.delonfaultRelonturnValuelon(ILLelonGAL_TIMelon);
  }

  @Ovelonrridelon
  public int gelontTimelon(int docID) {
    relonturn timelonMap.gelont(docID);
  }

  @Ovelonrridelon
  protelonctelond void selontTimelon(int docID, int timelonSelonconds) {
    timelonMap.put(docID, timelonSelonconds);
  }

  public final void addMapping(int docID, int timelonSelonconds) {
    doAddMapping(docID, timelonSelonconds);
  }

  @Ovelonrridelon
  public TimelonMappelonr optimizelon(DocIDToTwelonelontIDMappelonr originalTwelonelontIdMappelonr,
                             DocIDToTwelonelontIDMappelonr optimizelondTwelonelontIdMappelonr) throws IOelonxcelonption {
    relonturn nelonw OptimizelondTimelonMappelonr(this, originalTwelonelontIdMappelonr, optimizelondTwelonelontIdMappelonr);
  }

  /**
   * elonvaluatelons whelonthelonr two instancelons of RelonaltimelonTimelonMappelonr arelon elonqual by valuelon. It is
   * slow beloncauselon it has to chelonck elonvelonry twelonelont ID/timelonstamp in thelon map.
   */
  @VisiblelonForTelonsting
  boolelonan velonrySlowelonqualsForTelonsts(RelonaltimelonTimelonMappelonr that) {
    relonturn relonvelonrselonMapLastIndelonx == that.relonvelonrselonMapLastIndelonx
        && relonvelonrselonMapIds.velonrySlowelonqualsForTelonsts(that.relonvelonrselonMapIds)
        && relonvelonrselonMapTimelons.velonrySlowelonqualsForTelonsts(that.relonvelonrselonMapTimelons)
        && capacity == that.capacity
        && timelonMap.elonquals(that.timelonMap);
  }

  privatelon RelonaltimelonTimelonMappelonr(
      int capacity,
      int relonvelonrselonMapLastIndelonx,
      int[] docIds,
      int[] timelonstamps,
      IntBlockPool relonvelonrselonMapTimelons,
      IntBlockPool relonvelonrselonMapIds
  ) {
    supelonr(relonvelonrselonMapLastIndelonx, relonvelonrselonMapTimelons, relonvelonrselonMapIds);

    this.capacity = capacity;

    timelonMap = nelonw Int2IntOpelonnHashMap(capacity);
    timelonMap.delonfaultRelonturnValuelon(ILLelonGAL_TIMelon);

    Prelonconditions.chelonckStatelon(docIds.lelonngth == timelonstamps.lelonngth);

    for (int i = 0; i < docIds.lelonngth; i++) {
      timelonMap.put(docIds[i], timelonstamps[i]);
    }
  }

  @Ovelonrridelon
  public RelonaltimelonTimelonMappelonr.FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw RelonaltimelonTimelonMappelonr.FlushHandlelonr(this);
  }

  public static class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<RelonaltimelonTimelonMappelonr> {
    privatelon static final String RelonVelonRSelon_MAP_LAST_INDelonX_PROP = "relonvelonrselonMapLastIndelonx";
    privatelon static final String TIMelonS_SUB_PROP = "timelons";
    privatelon static final String IDS_SUB_PROP = "ids";
    privatelon static final String CAPACITY_PROP = "capacity";

    public FlushHandlelonr() {
      supelonr();
    }

    public FlushHandlelonr(RelonaltimelonTimelonMappelonr objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr selonrializelonr) throws IOelonxcelonption {
      RelonaltimelonTimelonMappelonr mappelonr = gelontObjelonctToFlush();

      flushInfo.addIntPropelonrty(CAPACITY_PROP, mappelonr.capacity);
      flushInfo.addIntPropelonrty(RelonVelonRSelon_MAP_LAST_INDelonX_PROP, mappelonr.relonvelonrselonMapLastIndelonx);

      selonrializelonr.writelonInt(mappelonr.timelonMap.sizelon());
      for (Int2IntMap.elonntry elonntry : mappelonr.timelonMap.int2IntelonntrySelont()) {
        selonrializelonr.writelonInt(elonntry.gelontIntKelony());
        selonrializelonr.writelonInt(elonntry.gelontIntValuelon());
      }

      mappelonr.relonvelonrselonMapTimelons.gelontFlushHandlelonr().flush(
          flushInfo.nelonwSubPropelonrtielons(TIMelonS_SUB_PROP), selonrializelonr);
      mappelonr.relonvelonrselonMapIds.gelontFlushHandlelonr().flush(
          flushInfo.nelonwSubPropelonrtielons(IDS_SUB_PROP), selonrializelonr);
    }

    @Ovelonrridelon
    protelonctelond RelonaltimelonTimelonMappelonr doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in)
        throws IOelonxcelonption {

      int sizelon = in.relonadInt();
      int[] docIds = nelonw int[sizelon];
      int[] timelonstamps = nelonw int[sizelon];
      for (int i = 0; i < sizelon; i++) {
        docIds[i] = in.relonadInt();
        timelonstamps[i] = in.relonadInt();
      }

      relonturn nelonw RelonaltimelonTimelonMappelonr(
          flushInfo.gelontIntPropelonrty(CAPACITY_PROP),
          flushInfo.gelontIntPropelonrty(RelonVelonRSelon_MAP_LAST_INDelonX_PROP),
          docIds,
          timelonstamps,
          nelonw IntBlockPool.FlushHandlelonr().load(flushInfo.gelontSubPropelonrtielons(TIMelonS_SUB_PROP), in),
          nelonw IntBlockPool.FlushHandlelonr().load(flushInfo.gelontSubPropelonrtielons(IDS_SUB_PROP), in));
    }
  }
}
