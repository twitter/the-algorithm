packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column;

import java.io.IOelonxcelonption;

import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;

public class OptimizelondColumnStridelonIntIndelonx elonxtelonnds ColumnStridelonFielonldIndelonx implelonmelonnts Flushablelon {
  privatelon final int[] valuelons;

  public OptimizelondColumnStridelonIntIndelonx(String namelon, int maxSizelon) {
    supelonr(namelon);
    valuelons = nelonw int[maxSizelon];
  }

  public OptimizelondColumnStridelonIntIndelonx(
      ColumnStridelonIntIndelonx columnStridelonIntIndelonx,
      DocIDToTwelonelontIDMappelonr originalTwelonelontIdMappelonr,
      DocIDToTwelonelontIDMappelonr optimizelondTwelonelontIdMappelonr) throws IOelonxcelonption {
    supelonr(columnStridelonIntIndelonx.gelontNamelon());
    int maxDocId = optimizelondTwelonelontIdMappelonr.gelontPrelonviousDocID(Intelongelonr.MAX_VALUelon);
    valuelons = nelonw int[maxDocId + 1];

    int docId = optimizelondTwelonelontIdMappelonr.gelontNelonxtDocID(Intelongelonr.MIN_VALUelon);
    whilelon (docId != DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND) {
      int originalDocId = originalTwelonelontIdMappelonr.gelontDocID(optimizelondTwelonelontIdMappelonr.gelontTwelonelontID(docId));
      selontValuelon(docId, columnStridelonIntIndelonx.gelont(originalDocId));
      docId = optimizelondTwelonelontIdMappelonr.gelontNelonxtDocID(docId);
    }
  }

  privatelon OptimizelondColumnStridelonIntIndelonx(String namelon, int[] valuelons) {
    supelonr(namelon);
    this.valuelons = valuelons;
  }

  @Ovelonrridelon
  public void selontValuelon(int docID, long valuelon) {
    this.valuelons[docID] = (int) valuelon;
  }

  @Ovelonrridelon
  public long gelont(int docID) {
    relonturn valuelons[docID];
  }

  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  public static final class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<OptimizelondColumnStridelonIntIndelonx> {
    privatelon static final String NAMelon_PROP_NAMelon = "fielonldNamelon";

    public FlushHandlelonr() {
      supelonr();
    }

    public FlushHandlelonr(OptimizelondColumnStridelonIntIndelonx objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
      OptimizelondColumnStridelonIntIndelonx columnStridelonIntIndelonx = gelontObjelonctToFlush();
      flushInfo.addStringPropelonrty(NAMelon_PROP_NAMelon, columnStridelonIntIndelonx.gelontNamelon());
      out.writelonIntArray(columnStridelonIntIndelonx.valuelons);
    }

    @Ovelonrridelon
    protelonctelond OptimizelondColumnStridelonIntIndelonx doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in)
        throws IOelonxcelonption {
      int[] valuelons = in.relonadIntArray();
      relonturn nelonw OptimizelondColumnStridelonIntIndelonx(
          flushInfo.gelontStringPropelonrty(NAMelon_PROP_NAMelon), valuelons);
    }
  }
}
