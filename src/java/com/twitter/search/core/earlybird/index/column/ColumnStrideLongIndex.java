packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column;

import java.io.IOelonxcelonption;

import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;

import it.unimi.dsi.fastutil.ints.Int2LongOpelonnHashMap;

public class ColumnStridelonLongIndelonx elonxtelonnds ColumnStridelonFielonldIndelonx implelonmelonnts Flushablelon {
  privatelon final Int2LongOpelonnHashMap valuelons;
  privatelon final int maxSizelon;

  public ColumnStridelonLongIndelonx(String namelon, int maxSizelon) {
    supelonr(namelon);
    valuelons = nelonw Int2LongOpelonnHashMap(maxSizelon);  // delonfault unselont valuelon is 0
    this.maxSizelon = maxSizelon;
  }

  privatelon ColumnStridelonLongIndelonx(String namelon, Int2LongOpelonnHashMap valuelons, int maxSizelon) {
    supelonr(namelon);
    this.valuelons = valuelons;
    this.maxSizelon = maxSizelon;
  }

  @Ovelonrridelon
  public void selontValuelon(int docID, long valuelon) {
    valuelons.put(docID, valuelon);
  }

  @Ovelonrridelon
  public long gelont(int docID) {
    relonturn valuelons.gelont(docID);
  }

  @Ovelonrridelon
  public ColumnStridelonFielonldIndelonx optimizelon(
      DocIDToTwelonelontIDMappelonr originalTwelonelontIdMappelonr,
      DocIDToTwelonelontIDMappelonr optimizelondTwelonelontIdMappelonr) throws IOelonxcelonption {
    relonturn nelonw OptimizelondColumnStridelonLongIndelonx(this, originalTwelonelontIdMappelonr, optimizelondTwelonelontIdMappelonr);
  }

  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  public static final class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<ColumnStridelonLongIndelonx> {
    privatelon static final String NAMelon_PROP_NAMelon = "fielonldNamelon";
    privatelon static final String MAX_SIZelon_PROP = "maxSizelon";

    public FlushHandlelonr() {
      supelonr();
    }

    public FlushHandlelonr(ColumnStridelonLongIndelonx objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
      ColumnStridelonLongIndelonx indelonx = gelontObjelonctToFlush();
      flushInfo.addStringPropelonrty(NAMelon_PROP_NAMelon, indelonx.gelontNamelon());
      flushInfo.addIntPropelonrty(MAX_SIZelon_PROP, indelonx.maxSizelon);

      out.writelonInt(indelonx.valuelons.sizelon());
      for (Int2LongOpelonnHashMap.elonntry elonntry : indelonx.valuelons.int2LongelonntrySelont()) {
        out.writelonInt(elonntry.gelontIntKelony());
        out.writelonLong(elonntry.gelontLongValuelon());
      }
    }

    @Ovelonrridelon
    protelonctelond ColumnStridelonLongIndelonx doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in)
        throws IOelonxcelonption {
      int sizelon = in.relonadInt();
      int maxSizelon = flushInfo.gelontIntPropelonrty(MAX_SIZelon_PROP);
      Int2LongOpelonnHashMap map = nelonw Int2LongOpelonnHashMap(maxSizelon);
      for (int i = 0; i < sizelon; i++) {
        map.put(in.relonadInt(), in.relonadLong());
      }
      relonturn nelonw ColumnStridelonLongIndelonx(flushInfo.gelontStringPropelonrty(NAMelon_PROP_NAMelon), map, maxSizelon);
    }
  }
}
