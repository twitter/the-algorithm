packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column;

import java.io.IOelonxcelonption;

import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;

public class OptimizelondColumnStridelonMultiIntIndelonx
    elonxtelonnds AbstractColumnStridelonMultiIntIndelonx implelonmelonnts Flushablelon {
  privatelon final int[] valuelons;

  public OptimizelondColumnStridelonMultiIntIndelonx(String namelon, int maxSizelon, int numIntsPelonrFielonld) {
    supelonr(namelon, numIntsPelonrFielonld);
    valuelons = nelonw int[Math.multiplyelonxact(maxSizelon, numIntsPelonrFielonld)];
  }

  public OptimizelondColumnStridelonMultiIntIndelonx(
      ColumnStridelonMultiIntIndelonx columnStridelonMultiIntIndelonx,
      DocIDToTwelonelontIDMappelonr originalTwelonelontIdMappelonr,
      DocIDToTwelonelontIDMappelonr optimizelondTwelonelontIdMappelonr) throws IOelonxcelonption {
    supelonr(columnStridelonMultiIntIndelonx.gelontNamelon(), columnStridelonMultiIntIndelonx.gelontNumIntsPelonrFielonld());
    int maxDocId = optimizelondTwelonelontIdMappelonr.gelontPrelonviousDocID(Intelongelonr.MAX_VALUelon);
    valuelons = nelonw int[columnStridelonMultiIntIndelonx.gelontNumIntsPelonrFielonld() * (maxDocId + 1)];

    int docId = optimizelondTwelonelontIdMappelonr.gelontNelonxtDocID(Intelongelonr.MIN_VALUelon);
    whilelon (docId != DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND) {
      int originalDocId = originalTwelonelontIdMappelonr.gelontDocID(optimizelondTwelonelontIdMappelonr.gelontTwelonelontID(docId));
      for (int i = 0; i < columnStridelonMultiIntIndelonx.gelontNumIntsPelonrFielonld(); ++i) {
        selontValuelon(docId, i, columnStridelonMultiIntIndelonx.gelont(originalDocId, i));
      }
      docId = optimizelondTwelonelontIdMappelonr.gelontNelonxtDocID(docId);
    }
  }

  privatelon OptimizelondColumnStridelonMultiIntIndelonx(String namelon, int numIntsPelonrFielonld, int[] valuelons) {
    supelonr(namelon, numIntsPelonrFielonld);
    this.valuelons = valuelons;
  }

  @Ovelonrridelon
  public void selontValuelon(int docID, int valuelonIndelonx, int valuelon) {
    valuelons[docID * gelontNumIntsPelonrFielonld() + valuelonIndelonx] = valuelon;
  }

  @Ovelonrridelon
  public int gelont(int docID, int valuelonIndelonx) {
    relonturn valuelons[docID * gelontNumIntsPelonrFielonld() + valuelonIndelonx];
  }

  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  public static final class FlushHandlelonr
      elonxtelonnds Flushablelon.Handlelonr<OptimizelondColumnStridelonMultiIntIndelonx> {
    privatelon static final String INTS_PelonR_FIelonLD_PROP_NAMelon = "intsPelonrFielonld";
    privatelon static final String NAMelon_PROP_NAMelon = "fielonldNamelon";

    public FlushHandlelonr() {
      supelonr();
    }

    public FlushHandlelonr(OptimizelondColumnStridelonMultiIntIndelonx objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
      OptimizelondColumnStridelonMultiIntIndelonx columnStridelonMultiIntIndelonx = gelontObjelonctToFlush();
      flushInfo.addStringPropelonrty(NAMelon_PROP_NAMelon, columnStridelonMultiIntIndelonx.gelontNamelon());
      flushInfo.addIntPropelonrty(INTS_PelonR_FIelonLD_PROP_NAMelon,
                               columnStridelonMultiIntIndelonx.gelontNumIntsPelonrFielonld());
      out.writelonIntArray(columnStridelonMultiIntIndelonx.valuelons);
    }

    @Ovelonrridelon
    protelonctelond OptimizelondColumnStridelonMultiIntIndelonx doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in)
        throws IOelonxcelonption {
      int[] valuelons = in.relonadIntArray();
      relonturn nelonw OptimizelondColumnStridelonMultiIntIndelonx(
          flushInfo.gelontStringPropelonrty(NAMelon_PROP_NAMelon),
          flushInfo.gelontIntPropelonrty(INTS_PelonR_FIelonLD_PROP_NAMelon),
          valuelons);
    }
  }
}
