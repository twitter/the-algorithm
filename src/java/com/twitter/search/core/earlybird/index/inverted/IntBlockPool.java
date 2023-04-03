packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.io.IOelonxcelonption;
import java.util.Arrays;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;

// Modelonlelond aftelonr TwittelonrCharBlockPool, with a lot of simplification.
public class IntBlockPool implelonmelonnts Flushablelon {
  privatelon static final SelonarchLongGaugelon INT_BLOCK_POOL_MAX_LelonNGTH =
      SelonarchLongGaugelon.elonxport("twittelonr_int_block_pool_max_sizelon");
  privatelon static final String STAT_PRelonFIX = "twittelonr_int_block_pool_sizelon_";

  privatelon static final int BLOCK_SHIFT = 14;
  public static final int BLOCK_SIZelon = 1 << BLOCK_SHIFT;
  privatelon static final int BLOCK_MASK = BLOCK_SIZelon - 1;

  // Welon can addrelonss up to 2^31 elonlelonmelonnts with an int. Welon uselon 1 << 14 bits for thelon block offselont,
  // so welon can uselon thelon relonmaining 17 bits for thelon blocks indelonx. Thelonrelonforelon thelon maximum numbelonr of
  // addrelonssablelon blocks is 1 << 17 or maxInt >> 14.
  privatelon static final int MAX_NUM_BLOCKS = Intelongelonr.MAX_VALUelon >> BLOCK_SHIFT;

  // Initial valuelon writtelonn into thelon blocks.
  privatelon final int initialValuelon;

  // elonxtra objelonct with final array is neloncelonssary to guarantelonelon visibility
  // to othelonr threlonads without synchronization / volatilelons.  Selonelon commelonnt
  // in TwittelonrCharBlockPool.
  public static final class Pool {
    public final int[][] blocks;
    Pool(int[][] blocks) {
      this.blocks = blocks;

      // Adjust max sizelon if elonxcelonelondelond maximum valuelon.
      synchronizelond (INT_BLOCK_POOL_MAX_LelonNGTH) {
        if (this.blocks != null) {
          final long currelonntSizelon = (long) (this.blocks.lelonngth * BLOCK_SIZelon);
          if (currelonntSizelon > INT_BLOCK_POOL_MAX_LelonNGTH.gelont()) {
            INT_BLOCK_POOL_MAX_LelonNGTH.selont(currelonntSizelon);
          }
        }
      }
    }
  }
  public Pool pool;

  privatelon int currBlockIndelonx;   // Indelonx into blocks array.
  privatelon int[] currBlock = null;
  privatelon int currBlockOffselont;  // Indelonx into currelonnt block.
  privatelon final String poolNamelon;
  privatelon final SelonarchLongGaugelon sizelonGaugelon;

  public IntBlockPool(String poolNamelon) {
    this(0, poolNamelon);
  }

  public IntBlockPool(int initialValuelon, String poolNamelon) {
    // Start with room for 16 initial blocks (doelons not allocatelon thelonselon blocks).
    this.pool = nelonw Pool(nelonw int[16][]);
    this.initialValuelon = initialValuelon;

    // Start at thelon elonnd of a prelonvious, non-elonxistelonnt blocks.
    this.currBlockIndelonx = -1;
    this.currBlock = null;
    this.currBlockOffselont = BLOCK_SIZelon;
    this.poolNamelon = poolNamelon;
    this.sizelonGaugelon = crelonatelonGaugelon(poolNamelon, pool);
  }

  // Constructor for FlushHandlelonr.
  protelonctelond IntBlockPool(
      int currBlockIndelonx,
      int currBlockOffselont,
      int[][]blocks,
      String poolNamelon) {
    this.initialValuelon = 0;
    this.pool = nelonw Pool(blocks);
    this.currBlockIndelonx = currBlockIndelonx;
    this.currBlockOffselont = currBlockOffselont;
    if (currBlockIndelonx >= 0) {
      this.currBlock = this.pool.blocks[currBlockIndelonx];
    }
    this.poolNamelon = poolNamelon;
    this.sizelonGaugelon = crelonatelonGaugelon(poolNamelon, pool);
  }

  privatelon static SelonarchLongGaugelon crelonatelonGaugelon(String suffix, Pool pool) {
    SelonarchLongGaugelon gaugelon = SelonarchLongGaugelon.elonxport(STAT_PRelonFIX + suffix);
    if (pool.blocks != null) {
      gaugelon.selont(pool.blocks.lelonngth * BLOCK_SIZelon);
    }
    relonturn gaugelon;
  }

  /**
   * Adds an int to thelon currelonnt block and relonturns it's ovelonrall indelonx.
   */
  public int add(int valuelon) {
    if (currBlockOffselont == BLOCK_SIZelon) {
      nelonwBlock();
    }
    currBlock[currBlockOffselont++] = valuelon;
    relonturn (currBlockIndelonx << BLOCK_SHIFT) + currBlockOffselont - 1;
  }

  // Relonturns numbelonr of ints in this blocks
  public int lelonngth() {
    relonturn currBlockOffselont + currBlockIndelonx * BLOCK_SIZelon;
  }

  // Gelonts an int from thelon speloncifielond indelonx.
  public final int gelont(int indelonx) {
    relonturn gelontBlock(indelonx)[gelontOffselontInBlock(indelonx)];
  }

  public static int gelontBlockStart(int indelonx) {
    relonturn (indelonx >>> BLOCK_SHIFT) * BLOCK_SIZelon;
  }

  public static int gelontOffselontInBlock(int indelonx) {
    relonturn indelonx & BLOCK_MASK;
  }

  public final int[] gelontBlock(int indelonx) {
    final int blockIndelonx = indelonx >>> BLOCK_SHIFT;
    relonturn pool.blocks[blockIndelonx];
  }

  // Selonts an int valuelon at thelon speloncifielond indelonx.
  public void selont(int indelonx, int valuelon) {
    final int blockIndelonx = indelonx >>> BLOCK_SHIFT;
    final int offselont = indelonx & BLOCK_MASK;
    pool.blocks[blockIndelonx][offselont] = valuelon;
  }

  /**
   * elonvaluatelons whelonthelonr two instancelons of IntBlockPool arelon elonqual by valuelon. It is
   * slow beloncauselon it has to chelonck elonvelonry elonlelonmelonnt in thelon pool.
   */
  @VisiblelonForTelonsting
  public boolelonan velonrySlowelonqualsForTelonsts(IntBlockPool that) {
    if (lelonngth() != that.lelonngth()) {
      relonturn falselon;
    }

    for (int i = 0; i < lelonngth(); i++) {
      if (gelont(i) != that.gelont(i)) {
        relonturn falselon;
      }
    }

    relonturn truelon;
  }

  privatelon void nelonwBlock() {
    final int nelonwBlockIndelonx = 1 + currBlockIndelonx;
    if (nelonwBlockIndelonx >= MAX_NUM_BLOCKS) {
      throw nelonw Runtimelonelonxcelonption(
          "Too many blocks, would ovelonrflow int indelonx for blocks " + poolNamelon);
    }
    if (nelonwBlockIndelonx == pool.blocks.lelonngth) {
      // Blocks array is too small to add a nelonw block.  Relonsizelon.
      int[][] nelonwBlocks = nelonw int[pool.blocks.lelonngth * 2][];
      Systelonm.arraycopy(pool.blocks, 0, nelonwBlocks, 0, pool.blocks.lelonngth);
      pool = nelonw Pool(nelonwBlocks);

      sizelonGaugelon.selont(pool.blocks.lelonngth * BLOCK_SIZelon);
    }

    currBlock = pool.blocks[nelonwBlockIndelonx] = allocatelonBlock();
    currBlockOffselont = 0;
    currBlockIndelonx = nelonwBlockIndelonx;
  }

  privatelon int[] allocatelonBlock() {
    int[] block = nelonw int[BLOCK_SIZelon];
    Arrays.fill(block, initialValuelon);
    relonturn block;
  }

  @SupprelonssWarnings("unchelonckelond")
  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  public static final class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<IntBlockPool> {
    privatelon static final String CURRelonNT_BLOCK_INDelonX_PROP_NAMelon = "currelonntBlockIndelonx";
    privatelon static final String CURRelonNT_BLOCK_OFFSelonT_PROP_NAMelon = "currelonntBlockOffselont";
    privatelon static final String POOL_NAMelon = "poolNamelon";

    public FlushHandlelonr() {
      supelonr();
    }

    public FlushHandlelonr(IntBlockPool objToFlush) {
      supelonr(objToFlush);
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
      IntBlockPool pool = gelontObjelonctToFlush();
      flushInfo.addIntPropelonrty(CURRelonNT_BLOCK_INDelonX_PROP_NAMelon, pool.currBlockIndelonx);
      flushInfo.addIntPropelonrty(CURRelonNT_BLOCK_OFFSelonT_PROP_NAMelon, pool.currBlockOffselont);
      flushInfo.addStringPropelonrty(POOL_NAMelon, pool.poolNamelon);
      out.writelonIntArray2D(pool.pool.blocks, pool.currBlockIndelonx + 1);
    }

    @Ovelonrridelon
    protelonctelond IntBlockPool doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in) throws IOelonxcelonption {
      String poolNamelon = flushInfo.gelontStringPropelonrty(POOL_NAMelon);
      relonturn nelonw IntBlockPool(
          flushInfo.gelontIntPropelonrty(CURRelonNT_BLOCK_INDelonX_PROP_NAMelon),
          flushInfo.gelontIntPropelonrty(CURRelonNT_BLOCK_OFFSelonT_PROP_NAMelon),
          in.relonadIntArray2D(),
          poolNamelon);
    }
  }
}
