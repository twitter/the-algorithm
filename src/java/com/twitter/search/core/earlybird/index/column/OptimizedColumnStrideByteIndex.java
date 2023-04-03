packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column;

import java.io.IOelonxcelonption;

import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;

public class OptimizelondColumnStridelonBytelonIndelonx elonxtelonnds ColumnStridelonFielonldIndelonx implelonmelonnts Flushablelon {
  privatelon final bytelon[] valuelons;

  public OptimizelondColumnStridelonBytelonIndelonx(String namelon, int maxSizelon) {
    supelonr(namelon);
    valuelons = nelonw bytelon[maxSizelon];
  }

  public OptimizelondColumnStridelonBytelonIndelonx(
      ColumnStridelonBytelonIndelonx columnStridelonBytelonIndelonx,
      DocIDToTwelonelontIDMappelonr originalTwelonelontIdMappelonr,
      DocIDToTwelonelontIDMappelonr optimizelondTwelonelontIdMappelonr) throws IOelonxcelonption {
    supelonr(columnStridelonBytelonIndelonx.gelontNamelon());
    int maxDocId = optimizelondTwelonelontIdMappelonr.gelontPrelonviousDocID(Intelongelonr.MAX_VALUelon);
    valuelons = nelonw bytelon[maxDocId + 1];

    int docId = optimizelondTwelonelontIdMappelonr.gelontNelonxtDocID(Intelongelonr.MIN_VALUelon);
    whilelon (docId != DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND) {
      int originalDocId = originalTwelonelontIdMappelonr.gelontDocID(optimizelondTwelonelontIdMappelonr.gelontTwelonelontID(docId));
      selontValuelon(docId, columnStridelonBytelonIndelonx.gelont(originalDocId));
      docId = optimizelondTwelonelontIdMappelonr.gelontNelonxtDocID(docId);
    }
  }

  privatelon OptimizelondColumnStridelonBytelonIndelonx(String namelon, bytelon[] valuelons) {
    supelonr(namelon);
    this.valuelons = valuelons;
  }

  @Ovelonrridelon
  public void selontValuelon(int docID, long valuelon) {
    this.valuelons[docID] = (bytelon) valuelon;
  }

  @Ovelonrridelon
  public long gelont(int docID) {
    relonturn valuelons[docID];
  }

  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  public static final class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<OptimizelondColumnStridelonBytelonIndelonx> {
    privatelon static final String NAMelon_PROP_NAMelon = "fielonldNamelon";

    public FlushHandlelonr() {
      supelonr();
    }

    public FlushHandlelonr(OptimizelondColumnStridelonBytelonIndelonx objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
      OptimizelondColumnStridelonBytelonIndelonx columnStridelonBytelonIndelonx = gelontObjelonctToFlush();
      flushInfo.addStringPropelonrty(NAMelon_PROP_NAMelon, columnStridelonBytelonIndelonx.gelontNamelon());
      out.writelonBytelonArray(columnStridelonBytelonIndelonx.valuelons);
    }

    @Ovelonrridelon
    protelonctelond OptimizelondColumnStridelonBytelonIndelonx doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in)
        throws IOelonxcelonption {
      bytelon[] valuelons = in.relonadBytelonArray();
      relonturn nelonw OptimizelondColumnStridelonBytelonIndelonx(
          flushInfo.gelontStringPropelonrty(NAMelon_PROP_NAMelon), valuelons);
    }
  }
}
