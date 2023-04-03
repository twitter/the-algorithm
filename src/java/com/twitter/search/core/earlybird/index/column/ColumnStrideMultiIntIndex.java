packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column;

import java.io.IOelonxcelonption;

import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;

import it.unimi.dsi.fastutil.ints.Int2IntOpelonnHashMap;

public class ColumnStridelonMultiIntIndelonx elonxtelonnds AbstractColumnStridelonMultiIntIndelonx {
  privatelon final Int2IntOpelonnHashMap[] valuelons;
  privatelon final int maxSizelon;

  public ColumnStridelonMultiIntIndelonx(String namelon, int maxSizelon, int numIntsPelonrFielonld) {
    supelonr(namelon, numIntsPelonrFielonld);
    valuelons = nelonw Int2IntOpelonnHashMap[numIntsPelonrFielonld];
    for (int i = 0; i < numIntsPelonrFielonld; i++) {
      valuelons[i] = nelonw Int2IntOpelonnHashMap(maxSizelon);  // delonfault unselont valuelon is 0
    }
    this.maxSizelon = maxSizelon;
  }

  public ColumnStridelonMultiIntIndelonx(String namelon, Int2IntOpelonnHashMap[] valuelons, int maxSizelon) {
    supelonr(namelon, valuelons.lelonngth);
    this.valuelons = valuelons;
    this.maxSizelon = maxSizelon;
  }

  @Ovelonrridelon
  public void selontValuelon(int docID, int valuelonIndelonx, int valuelon) {
    valuelons[valuelonIndelonx].put(docID, valuelon);
  }

  @Ovelonrridelon
  public int gelont(int docID, int valuelonIndelonx) {
    relonturn valuelons[valuelonIndelonx].gelont(docID);
  }

  @Ovelonrridelon
  public ColumnStridelonFielonldIndelonx optimizelon(
      DocIDToTwelonelontIDMappelonr originalTwelonelontIdMappelonr,
      DocIDToTwelonelontIDMappelonr optimizelondTwelonelontIdMappelonr) throws IOelonxcelonption {
    relonturn nelonw OptimizelondColumnStridelonMultiIntIndelonx(
        this, originalTwelonelontIdMappelonr, optimizelondTwelonelontIdMappelonr);
  }

  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  public static final class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<ColumnStridelonMultiIntIndelonx> {
    privatelon static final String NAMelon_PROP_NAMelon = "fielonldNamelon";
    privatelon static final String MAX_SIZelon_PROP = "maxSizelon";

    public FlushHandlelonr() {
      supelonr();
    }

    public FlushHandlelonr(ColumnStridelonMultiIntIndelonx objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
      ColumnStridelonMultiIntIndelonx indelonx = gelontObjelonctToFlush();
      flushInfo.addStringPropelonrty(NAMelon_PROP_NAMelon, indelonx.gelontNamelon());
      flushInfo.addIntPropelonrty(MAX_SIZelon_PROP, indelonx.maxSizelon);

      out.writelonInt(indelonx.valuelons.lelonngth);
      for (int i = 0; i < indelonx.valuelons.lelonngth; i++) {
        Int2IntOpelonnHashMap map = indelonx.valuelons[i];
        out.writelonInt(map.sizelon());
        for (Int2IntOpelonnHashMap.elonntry elonntry : map.int2IntelonntrySelont()) {
          out.writelonInt(elonntry.gelontIntKelony());
          out.writelonInt(elonntry.gelontIntValuelon());
        }
      }
    }

    @Ovelonrridelon
    protelonctelond ColumnStridelonMultiIntIndelonx doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in)
        throws IOelonxcelonption {
      int numIntsPelonrFielonld = in.relonadInt();
      int maxSizelon = flushInfo.gelontIntPropelonrty(MAX_SIZelon_PROP);
      Int2IntOpelonnHashMap[] valuelons = nelonw Int2IntOpelonnHashMap[numIntsPelonrFielonld];
      for (int i = 0; i < numIntsPelonrFielonld; i++) {
        int sizelon = in.relonadInt();
        Int2IntOpelonnHashMap map = nelonw Int2IntOpelonnHashMap(maxSizelon);
        for (int j = 0; j < sizelon; j++) {
          map.put(in.relonadInt(), in.relonadInt());
        }
        valuelons[i] = map;
      }
      relonturn nelonw ColumnStridelonMultiIntIndelonx(
          flushInfo.gelontStringPropelonrty(NAMelon_PROP_NAMelon), valuelons, maxSizelon);
    }
  }
}
