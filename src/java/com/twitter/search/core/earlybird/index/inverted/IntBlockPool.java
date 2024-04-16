package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;
 mport java.ut l.Arrays;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;

// Modeled after Tw terCharBlockPool, w h a lot of s mpl f cat on.
publ c class  ntBlockPool  mple nts Flushable {
  pr vate stat c f nal SearchLongGauge  NT_BLOCK_POOL_MAX_LENGTH =
      SearchLongGauge.export("tw ter_ nt_block_pool_max_s ze");
  pr vate stat c f nal Str ng STAT_PREF X = "tw ter_ nt_block_pool_s ze_";

  pr vate stat c f nal  nt BLOCK_SH FT = 14;
  publ c stat c f nal  nt BLOCK_S ZE = 1 << BLOCK_SH FT;
  pr vate stat c f nal  nt BLOCK_MASK = BLOCK_S ZE - 1;

  //   can address up to 2^31 ele nts w h an  nt.   use 1 << 14 b s for t  block offset,
  // so   can use t  rema n ng 17 b s for t  blocks  ndex. T refore t  max mum number of
  // addressable blocks  s 1 << 17 or max nt >> 14.
  pr vate stat c f nal  nt MAX_NUM_BLOCKS =  nteger.MAX_VALUE >> BLOCK_SH FT;

  //  n  al value wr ten  nto t  blocks.
  pr vate f nal  nt  n  alValue;

  // Extra object w h f nal array  s necessary to guarantee v s b l y
  // to ot r threads w hout synchron zat on / volat les.  See com nt
  //  n Tw terCharBlockPool.
  publ c stat c f nal class Pool {
    publ c f nal  nt[][] blocks;
    Pool( nt[][] blocks) {
      t .blocks = blocks;

      // Adjust max s ze  f exceeded max mum value.
      synchron zed ( NT_BLOCK_POOL_MAX_LENGTH) {
         f (t .blocks != null) {
          f nal long currentS ze = (long) (t .blocks.length * BLOCK_S ZE);
           f (currentS ze >  NT_BLOCK_POOL_MAX_LENGTH.get()) {
             NT_BLOCK_POOL_MAX_LENGTH.set(currentS ze);
          }
        }
      }
    }
  }
  publ c Pool pool;

  pr vate  nt currBlock ndex;   //  ndex  nto blocks array.
  pr vate  nt[] currBlock = null;
  pr vate  nt currBlockOffset;  //  ndex  nto current block.
  pr vate f nal Str ng poolNa ;
  pr vate f nal SearchLongGauge s zeGauge;

  publ c  ntBlockPool(Str ng poolNa ) {
    t (0, poolNa );
  }

  publ c  ntBlockPool( nt  n  alValue, Str ng poolNa ) {
    // Start w h room for 16  n  al blocks (does not allocate t se blocks).
    t .pool = new Pool(new  nt[16][]);
    t . n  alValue =  n  alValue;

    // Start at t  end of a prev ous, non-ex stent blocks.
    t .currBlock ndex = -1;
    t .currBlock = null;
    t .currBlockOffset = BLOCK_S ZE;
    t .poolNa  = poolNa ;
    t .s zeGauge = createGauge(poolNa , pool);
  }

  // Constructor for FlushHandler.
  protected  ntBlockPool(
       nt currBlock ndex,
       nt currBlockOffset,
       nt[][]blocks,
      Str ng poolNa ) {
    t . n  alValue = 0;
    t .pool = new Pool(blocks);
    t .currBlock ndex = currBlock ndex;
    t .currBlockOffset = currBlockOffset;
     f (currBlock ndex >= 0) {
      t .currBlock = t .pool.blocks[currBlock ndex];
    }
    t .poolNa  = poolNa ;
    t .s zeGauge = createGauge(poolNa , pool);
  }

  pr vate stat c SearchLongGauge createGauge(Str ng suff x, Pool pool) {
    SearchLongGauge gauge = SearchLongGauge.export(STAT_PREF X + suff x);
     f (pool.blocks != null) {
      gauge.set(pool.blocks.length * BLOCK_S ZE);
    }
    return gauge;
  }

  /**
   * Adds an  nt to t  current block and returns  's overall  ndex.
   */
  publ c  nt add( nt value) {
     f (currBlockOffset == BLOCK_S ZE) {
      newBlock();
    }
    currBlock[currBlockOffset++] = value;
    return (currBlock ndex << BLOCK_SH FT) + currBlockOffset - 1;
  }

  // Returns number of  nts  n t  blocks
  publ c  nt length() {
    return currBlockOffset + currBlock ndex * BLOCK_S ZE;
  }

  // Gets an  nt from t  spec f ed  ndex.
  publ c f nal  nt get( nt  ndex) {
    return getBlock( ndex)[getOffset nBlock( ndex)];
  }

  publ c stat c  nt getBlockStart( nt  ndex) {
    return ( ndex >>> BLOCK_SH FT) * BLOCK_S ZE;
  }

  publ c stat c  nt getOffset nBlock( nt  ndex) {
    return  ndex & BLOCK_MASK;
  }

  publ c f nal  nt[] getBlock( nt  ndex) {
    f nal  nt block ndex =  ndex >>> BLOCK_SH FT;
    return pool.blocks[block ndex];
  }

  // Sets an  nt value at t  spec f ed  ndex.
  publ c vo d set( nt  ndex,  nt value) {
    f nal  nt block ndex =  ndex >>> BLOCK_SH FT;
    f nal  nt offset =  ndex & BLOCK_MASK;
    pool.blocks[block ndex][offset] = value;
  }

  /**
   * Evaluates w t r two  nstances of  ntBlockPool are equal by value.    s
   * slow because   has to c ck every ele nt  n t  pool.
   */
  @V s bleForTest ng
  publ c boolean verySlowEqualsForTests( ntBlockPool that) {
     f (length() != that.length()) {
      return false;
    }

    for ( nt   = 0;   < length();  ++) {
       f (get( ) != that.get( )) {
        return false;
      }
    }

    return true;
  }

  pr vate vo d newBlock() {
    f nal  nt newBlock ndex = 1 + currBlock ndex;
     f (newBlock ndex >= MAX_NUM_BLOCKS) {
      throw new Runt  Except on(
          "Too many blocks, would overflow  nt  ndex for blocks " + poolNa );
    }
     f (newBlock ndex == pool.blocks.length) {
      // Blocks array  s too small to add a new block.  Res ze.
       nt[][] newBlocks = new  nt[pool.blocks.length * 2][];
      System.arraycopy(pool.blocks, 0, newBlocks, 0, pool.blocks.length);
      pool = new Pool(newBlocks);

      s zeGauge.set(pool.blocks.length * BLOCK_S ZE);
    }

    currBlock = pool.blocks[newBlock ndex] = allocateBlock();
    currBlockOffset = 0;
    currBlock ndex = newBlock ndex;
  }

  pr vate  nt[] allocateBlock() {
     nt[] block = new  nt[BLOCK_S ZE];
    Arrays.f ll(block,  n  alValue);
    return block;
  }

  @SuppressWarn ngs("unc cked")
  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  publ c stat c f nal class FlushHandler extends Flushable.Handler< ntBlockPool> {
    pr vate stat c f nal Str ng CURRENT_BLOCK_ NDEX_PROP_NAME = "currentBlock ndex";
    pr vate stat c f nal Str ng CURRENT_BLOCK_OFFSET_PROP_NAME = "currentBlockOffset";
    pr vate stat c f nal Str ng POOL_NAME = "poolNa ";

    publ c FlushHandler() {
      super();
    }

    publ c FlushHandler( ntBlockPool objToFlush) {
      super(objToFlush);
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
       ntBlockPool pool = getObjectToFlush();
      flush nfo.add ntProperty(CURRENT_BLOCK_ NDEX_PROP_NAME, pool.currBlock ndex);
      flush nfo.add ntProperty(CURRENT_BLOCK_OFFSET_PROP_NAME, pool.currBlockOffset);
      flush nfo.addStr ngProperty(POOL_NAME, pool.poolNa );
      out.wr e ntArray2D(pool.pool.blocks, pool.currBlock ndex + 1);
    }

    @Overr de
    protected  ntBlockPool doLoad(Flush nfo flush nfo, DataDeser al zer  n) throws  OExcept on {
      Str ng poolNa  = flush nfo.getStr ngProperty(POOL_NAME);
      return new  ntBlockPool(
          flush nfo.get ntProperty(CURRENT_BLOCK_ NDEX_PROP_NAME),
          flush nfo.get ntProperty(CURRENT_BLOCK_OFFSET_PROP_NAME),
           n.read ntArray2D(),
          poolNa );
    }
  }
}
