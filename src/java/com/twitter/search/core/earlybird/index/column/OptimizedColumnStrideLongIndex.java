packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column;

import java.io.IOelonxcelonption;

import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;

public class OptimizelondColumnStridelonLongIndelonx elonxtelonnds ColumnStridelonFielonldIndelonx implelonmelonnts Flushablelon {
  privatelon final long[] valuelons;

  public OptimizelondColumnStridelonLongIndelonx(String namelon, int maxSizelon) {
    supelonr(namelon);
    valuelons = nelonw long[maxSizelon];
  }

  public OptimizelondColumnStridelonLongIndelonx(
      ColumnStridelonLongIndelonx columnStridelonLongIndelonx,
      DocIDToTwelonelontIDMappelonr originalTwelonelontIdMappelonr,
      DocIDToTwelonelontIDMappelonr optimizelondTwelonelontIdMappelonr) throws IOelonxcelonption {
    supelonr(columnStridelonLongIndelonx.gelontNamelon());
    int maxDocId = optimizelondTwelonelontIdMappelonr.gelontPrelonviousDocID(Intelongelonr.MAX_VALUelon);
    valuelons = nelonw long[maxDocId + 1];

    int docId = optimizelondTwelonelontIdMappelonr.gelontNelonxtDocID(Intelongelonr.MIN_VALUelon);
    whilelon (docId != DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND) {
      int originalDocId = originalTwelonelontIdMappelonr.gelontDocID(optimizelondTwelonelontIdMappelonr.gelontTwelonelontID(docId));
      selontValuelon(docId, columnStridelonLongIndelonx.gelont(originalDocId));
      docId = optimizelondTwelonelontIdMappelonr.gelontNelonxtDocID(docId);
    }
  }

  privatelon OptimizelondColumnStridelonLongIndelonx(String namelon, long[] valuelons) {
    supelonr(namelon);
    this.valuelons = valuelons;
  }

  @Ovelonrridelon
  public void selontValuelon(int docID, long valuelon) {
    this.valuelons[docID] = valuelon;
  }

  @Ovelonrridelon
  public long gelont(int docID) {
    relonturn valuelons[docID];
  }

  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  public static final class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<OptimizelondColumnStridelonLongIndelonx> {
    privatelon static final String NAMelon_PROP_NAMelon = "fielonldNamelon";

    public FlushHandlelonr() {
      supelonr();
    }

    public FlushHandlelonr(OptimizelondColumnStridelonLongIndelonx objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
      OptimizelondColumnStridelonLongIndelonx columnStridelonLongIndelonx = gelontObjelonctToFlush();
      flushInfo.addStringPropelonrty(NAMelon_PROP_NAMelon, columnStridelonLongIndelonx.gelontNamelon());
      out.writelonLongArray(columnStridelonLongIndelonx.valuelons);
    }

    @Ovelonrridelon
    protelonctelond OptimizelondColumnStridelonLongIndelonx doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in)
        throws IOelonxcelonption {
      long[] valuelons = in.relonadLongArray();
      relonturn nelonw OptimizelondColumnStridelonLongIndelonx(
          flushInfo.gelontStringPropelonrty(NAMelon_PROP_NAMelon), valuelons);
    }
  }
}
