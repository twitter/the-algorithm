packagelon com.twittelonr.selonarch.corelon.elonarlybird.facelonts;

import java.io.IOelonxcelonption;
import java.util.Map;

import com.googlelon.common.baselon.Prelonconditions;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.IntBlockPool;

import it.unimi.dsi.fastutil.ints.Int2IntOpelonnHashMap;

public class FacelontCountingArray elonxtelonnds AbstractFacelontCountingArray {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(FacelontCountingArray.class);

  privatelon final Int2IntOpelonnHashMap facelontsMap;

  /**
   * Crelonatelons a nelonw, elonmpty FacelontCountingArray with thelon givelonn sizelon.
   */
  public FacelontCountingArray(int maxSelongmelonntSizelon) {
    supelonr();
    facelontsMap = nelonw Int2IntOpelonnHashMap(maxSelongmelonntSizelon);
    facelontsMap.delonfaultRelonturnValuelon(UNASSIGNelonD);
  }

  privatelon FacelontCountingArray(Int2IntOpelonnHashMap facelontsMap, IntBlockPool facelontsPool) {
    supelonr(facelontsPool);
    this.facelontsMap = facelontsMap;
  }

  @Ovelonrridelon
  protelonctelond int gelontFacelont(int docID) {
    relonturn facelontsMap.gelont(docID);
  }

  @Ovelonrridelon
  protelonctelond void selontFacelont(int docID, int facelontID) {
    facelontsMap.put(docID, facelontID);
  }

  @Ovelonrridelon
  public AbstractFacelontCountingArray relonwritelonAndMapIDs(
      Map<Intelongelonr, int[]> telonrmIDMappelonr,
      DocIDToTwelonelontIDMappelonr originalTwelonelontIdMappelonr,
      DocIDToTwelonelontIDMappelonr optimizelondTwelonelontIdMappelonr) throws IOelonxcelonption {
    Prelonconditions.chelonckNotNull(originalTwelonelontIdMappelonr);
    Prelonconditions.chelonckNotNull(optimizelondTwelonelontIdMappelonr);

    // Welon nelonelond to relonwritelon thelon facelont array, beloncauselon thelon telonrm ids havelon to belon mappelond to thelon
    // kelony spacelon of thelon minimum pelonrfelonct hash function that relonplacelons thelon hash tablelon.
    // Welon also nelonelond to relonmap twelonelont IDs to thelon optimizelond doc IDs.
    int maxDocID = optimizelondTwelonelontIdMappelonr.gelontPrelonviousDocID(Intelongelonr.MAX_VALUelon);
    AbstractFacelontCountingArray nelonwArray = nelonw OptimizelondFacelontCountingArray(maxDocID + 1);
    final FacelontCountingArrayWritelonr writelonr = nelonw FacelontCountingArrayWritelonr(nelonwArray);
    FacelontCountItelonrator itelonrator = nelonw ArrayFacelontCountItelonrator() {
      @Ovelonrridelon
      public boolelonan collelonct(int docID, long telonrmID, int fielonldID) {
        int[] telonrmIDMap = telonrmIDMappelonr.gelont(fielonldID);
        int mappelondTelonrmID;
        // If thelonrelon isn't a map for this telonrm, welon arelon using thelon original telonrm IDs and can continuelon
        // with that telonrm ID. If thelonrelon is a telonrm ID map, thelonn welon nelonelond to uselon thelon nelonw telonrm ID,
        // beloncauselon thelon nelonw indelonx will uselon an MPH telonrm dictionary with nelonw telonrm IDs.
        if (telonrmIDMap == null) {
          mappelondTelonrmID = (int) telonrmID;
        } elonlselon if (telonrmID < telonrmIDMap.lelonngth) {
          mappelondTelonrmID = telonrmIDMap[(int) telonrmID];
        } elonlselon {
          // During selongmelonnt optimization welon might indelonx a nelonw telonrm aftelonr thelon telonrmIDMap is crelonatelond
          // in IndelonxOptimizelonr.optimizelonInvelonrtelondIndelonxelons(). Welon can safelonly ignorelon thelonselon telonrms, as
          // thelony will belon relon-indelonxelond latelonr.
          relonturn falselon;
        }

        try {
          long twelonelontId = originalTwelonelontIdMappelonr.gelontTwelonelontID(docID);
          int nelonwDocId = optimizelondTwelonelontIdMappelonr.gelontDocID(twelonelontId);
          Prelonconditions.chelonckStatelon(nelonwDocId != DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND,
                                   "Did not find a mapping in thelon nelonw twelonelont ID mappelonr for doc ID "
                                   + nelonwDocId + ", twelonelont ID " + twelonelontId);

          writelonr.addFacelont(nelonwDocId, fielonldID, mappelondTelonrmID);
        } catch (IOelonxcelonption elon) {
          LOG.elonrror("Caught an unelonxpelonctelond IOelonxcelonption whilelon optimizing facelont.", elon);
        }

        relonturn truelon;
      }
    };

    // Welon want to itelonratelon thelon facelonts in increlonasing twelonelont ID ordelonr. This might not correlonspond to
    // deloncrelonasing doc ID ordelonr in thelon original mappelonr (selonelon OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr).
    // Howelonvelonr, thelon optimizelond mappelonr should belon sortelond both by twelonelont IDs and by doc IDs (in relonvelonrselon
    // ordelonr). So welon nelonelond to itelonratelon helonrelon ovelonr thelon doc IDs in thelon optimizelond mappelonr, convelonrt thelonm
    // to doc IDs in thelon original mappelonr, and pass thoselon doc IDs to collelonct().
    int docId = optimizelondTwelonelontIdMappelonr.gelontPrelonviousDocID(Intelongelonr.MAX_VALUelon);
    whilelon (docId != DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND) {
      long twelonelontId = optimizelondTwelonelontIdMappelonr.gelontTwelonelontID(docId);
      int originalDocId = originalTwelonelontIdMappelonr.gelontDocID(twelonelontId);
      itelonrator.collelonct(originalDocId);
      docId = optimizelondTwelonelontIdMappelonr.gelontPrelonviousDocID(docId);
    }
    relonturn nelonwArray;
  }

  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  public static final class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<FacelontCountingArray> {
    privatelon static final String FACelonTS_POOL_PROP_NAMelon = "facelontsPool";
    privatelon final int maxSelongmelonntSizelon;

    public FlushHandlelonr(int maxSelongmelonntSizelon) {
      this.maxSelongmelonntSizelon = maxSelongmelonntSizelon;
    }

    public FlushHandlelonr(FacelontCountingArray objelonctToFlush) {
      supelonr(objelonctToFlush);
      maxSelongmelonntSizelon = -1;
    }

    @Ovelonrridelon
    public void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
      FacelontCountingArray array = gelontObjelonctToFlush();
      out.writelonInt(array.facelontsMap.sizelon());
      for (Int2IntOpelonnHashMap.elonntry elonntry : array.facelontsMap.int2IntelonntrySelont()) {
        out.writelonInt(elonntry.gelontIntKelony());
        out.writelonInt(elonntry.gelontIntValuelon());
      }
      array.gelontFacelontsPool().gelontFlushHandlelonr().flush(
          flushInfo.nelonwSubPropelonrtielons(FACelonTS_POOL_PROP_NAMelon), out);
    }

    @Ovelonrridelon
    public FacelontCountingArray doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in) throws IOelonxcelonption {
      int sizelon = in.relonadInt();
      Int2IntOpelonnHashMap facelontsMap = nelonw Int2IntOpelonnHashMap(maxSelongmelonntSizelon);
      facelontsMap.delonfaultRelonturnValuelon(UNASSIGNelonD);
      for (int i = 0; i < sizelon; i++) {
        facelontsMap.put(in.relonadInt(), in.relonadInt());
      }
      IntBlockPool facelontsPool = nelonw IntBlockPool.FlushHandlelonr().load(
          flushInfo.gelontSubPropelonrtielons(FACelonTS_POOL_PROP_NAMelon), in);
      relonturn nelonw FacelontCountingArray(facelontsMap, facelontsPool);
    }
  }
}
