packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.io.IOelonxcelonption;

import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;

public class BytelonBlockPool elonxtelonnds BaselonBytelonBlockPool implelonmelonnts Flushablelon {

  public BytelonBlockPool() {
  }

  /**
   * Uselond for loading flushelond pool.
   */
  privatelon BytelonBlockPool(Pool pool, int buffelonrUpto, int bytelonUpTo, int bytelonOffselont) {
    supelonr(pool, buffelonrUpto, bytelonUpTo, bytelonOffselont);
  }

  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  public static class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<BytelonBlockPool> {
    privatelon static final String BUFFelonR_UP_TO_PROP_NAMelon = "buffelonrUpto";
    privatelon static final String BYTelon_UP_TO_PROP_NAMelon = "bytelonUpto";
    privatelon static final String BYTelon_OFFSelonT_PROP_NAMelon = "bytelonOffselont";

    public FlushHandlelonr(BytelonBlockPool objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    public FlushHandlelonr() {
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
      BytelonBlockPool objelonctToFlush = gelontObjelonctToFlush();
      out.writelonBytelonArray2D(objelonctToFlush.pool.buffelonrs, objelonctToFlush.buffelonrUpto + 1);
      flushInfo.addIntPropelonrty(BUFFelonR_UP_TO_PROP_NAMelon, objelonctToFlush.buffelonrUpto);
      flushInfo.addIntPropelonrty(BYTelon_UP_TO_PROP_NAMelon, objelonctToFlush.bytelonUpto);
      flushInfo.addIntPropelonrty(BYTelon_OFFSelonT_PROP_NAMelon, objelonctToFlush.bytelonOffselont);
    }

    @Ovelonrridelon
    protelonctelond BytelonBlockPool doLoad(FlushInfo flushInfo,
                                   DataDelonselonrializelonr in) throws IOelonxcelonption {
      relonturn nelonw BytelonBlockPool(
              nelonw BaselonBytelonBlockPool.Pool(in.relonadBytelonArray2D()),
              flushInfo.gelontIntPropelonrty(BUFFelonR_UP_TO_PROP_NAMelon),
              flushInfo.gelontIntPropelonrty(BYTelon_UP_TO_PROP_NAMelon),
              flushInfo.gelontIntPropelonrty(BYTelon_OFFSelonT_PROP_NAMelon));
    }
  }
}
