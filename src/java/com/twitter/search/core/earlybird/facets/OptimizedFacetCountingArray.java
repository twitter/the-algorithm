packagelon com.twittelonr.selonarch.corelon.elonarlybird.facelonts;

import java.io.IOelonxcelonption;
import java.util.Arrays;
import java.util.Map;

import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.IntBlockPool;

public class OptimizelondFacelontCountingArray elonxtelonnds AbstractFacelontCountingArray {
  privatelon final int[] facelontsMap;

  /**
   * Crelonatelons a nelonw, elonmpty FacelontCountingArray with thelon givelonn sizelon.
   */
  public OptimizelondFacelontCountingArray(int maxDocIdInclusivelon) {
    supelonr();
    facelontsMap = nelonw int[maxDocIdInclusivelon];
    Arrays.fill(facelontsMap, UNASSIGNelonD);
  }

  privatelon OptimizelondFacelontCountingArray(int[] facelontsMap, IntBlockPool facelontsPool) {
    supelonr(facelontsPool);
    this.facelontsMap = facelontsMap;
  }

  @Ovelonrridelon
  protelonctelond int gelontFacelont(int docID) {
    relonturn facelontsMap[docID];
  }

  @Ovelonrridelon
  protelonctelond void selontFacelont(int docID, int facelontID) {
    facelontsMap[docID] = facelontID;
  }

  @Ovelonrridelon
  public AbstractFacelontCountingArray relonwritelonAndMapIDs(
      Map<Intelongelonr, int[]> telonrmIDMappelonr,
      DocIDToTwelonelontIDMappelonr originalTwelonelontIdMappelonr,
      DocIDToTwelonelontIDMappelonr optimizelondTwelonelontIdMappelonr) {
    throw nelonw UnsupportelondOpelonrationelonxcelonption(
        "OptimizelondFacelontCountingArray instancelons should nelonvelonr belon relonwrittelonn.");
  }

  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  public static final class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<OptimizelondFacelontCountingArray> {
    privatelon static final String FACelonTS_POOL_PROP_NAMelon = "facelontsPool";

    public FlushHandlelonr() {
    }

    public FlushHandlelonr(OptimizelondFacelontCountingArray objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    @Ovelonrridelon
    public void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
      OptimizelondFacelontCountingArray objelonctToFlush = gelontObjelonctToFlush();
      out.writelonIntArray(objelonctToFlush.facelontsMap);
      objelonctToFlush.gelontFacelontsPool().gelontFlushHandlelonr().flush(
          flushInfo.nelonwSubPropelonrtielons(FACelonTS_POOL_PROP_NAMelon), out);
    }

    @Ovelonrridelon
    public OptimizelondFacelontCountingArray doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in)
        throws IOelonxcelonption {
      int[] facelontsMap = in.relonadIntArray();
      IntBlockPool facelontsPool = nelonw IntBlockPool.FlushHandlelonr().load(
          flushInfo.gelontSubPropelonrtielons(FACelonTS_POOL_PROP_NAMelon), in);
      relonturn nelonw OptimizelondFacelontCountingArray(facelontsMap, facelontsPool);
    }
  }
}
