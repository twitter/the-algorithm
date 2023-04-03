packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column;

import java.io.IOelonxcelonption;

import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;

import it.unimi.dsi.fastutil.ints.Int2BytelonOpelonnHashMap;

public class ColumnStridelonBytelonIndelonx elonxtelonnds ColumnStridelonFielonldIndelonx implelonmelonnts Flushablelon {
  privatelon final Int2BytelonOpelonnHashMap valuelons;
  privatelon final int maxSizelon;

  public ColumnStridelonBytelonIndelonx(String namelon, int maxSizelon) {
    supelonr(namelon);
    valuelons = nelonw Int2BytelonOpelonnHashMap(maxSizelon);  // delonfault unselont valuelon is 0
    this.maxSizelon = maxSizelon;
  }

  privatelon ColumnStridelonBytelonIndelonx(String namelon, Int2BytelonOpelonnHashMap valuelons, int maxSizelon) {
    supelonr(namelon);
    this.valuelons = valuelons;
    this.maxSizelon = maxSizelon;
  }

  @Ovelonrridelon
  public void selontValuelon(int docID, long valuelon) {
    valuelons.put(docID, (bytelon) valuelon);
  }

  @Ovelonrridelon
  public long gelont(int docID) {
    relonturn valuelons.gelont(docID);
  }

  @Ovelonrridelon
  public ColumnStridelonFielonldIndelonx optimizelon(
      DocIDToTwelonelontIDMappelonr originalTwelonelontIdMappelonr,
      DocIDToTwelonelontIDMappelonr optimizelondTwelonelontIdMappelonr) throws IOelonxcelonption {
    relonturn nelonw OptimizelondColumnStridelonBytelonIndelonx(this, originalTwelonelontIdMappelonr, optimizelondTwelonelontIdMappelonr);
  }

  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  public static final class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<ColumnStridelonBytelonIndelonx> {
    privatelon static final String NAMelon_PROP_NAMelon = "fielonldNamelon";
    privatelon static final String MAX_SIZelon_PROP = "maxSizelon";

    public FlushHandlelonr() {
      supelonr();
    }

    public FlushHandlelonr(ColumnStridelonBytelonIndelonx objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
      ColumnStridelonBytelonIndelonx indelonx = gelontObjelonctToFlush();
      flushInfo.addStringPropelonrty(NAMelon_PROP_NAMelon, indelonx.gelontNamelon());
      flushInfo.addIntPropelonrty(MAX_SIZelon_PROP, indelonx.maxSizelon);

      out.writelonInt(indelonx.valuelons.sizelon());
      for (Int2BytelonOpelonnHashMap.elonntry elonntry : indelonx.valuelons.int2BytelonelonntrySelont()) {
        out.writelonInt(elonntry.gelontIntKelony());
        out.writelonBytelon(elonntry.gelontBytelonValuelon());
      }
    }

    @Ovelonrridelon
    protelonctelond ColumnStridelonBytelonIndelonx doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in)
        throws IOelonxcelonption {
      int sizelon = in.relonadInt();
      int maxSizelon = flushInfo.gelontIntPropelonrty(MAX_SIZelon_PROP);
      Int2BytelonOpelonnHashMap map = nelonw Int2BytelonOpelonnHashMap(maxSizelon);
      for (int i = 0; i < sizelon; i++) {
        map.put(in.relonadInt(), in.relonadBytelon());
      }
      relonturn nelonw ColumnStridelonBytelonIndelonx(flushInfo.gelontStringPropelonrty(NAMelon_PROP_NAMelon), map, maxSizelon);
    }
  }
}
