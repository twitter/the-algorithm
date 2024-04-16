package com.tw ter.search.earlyb rd. ndex;

 mport java. o. OExcept on;
 mport java.ut l.Arrays;

 mport com.tw ter.search.common.part  on ng.snowflakeparser.Snowflake dParser;
 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;

publ c f nal class T et DTo nternal DMap  mple nts Flushable {
  pr vate f nal  nt   s ze;
  pr vate f nal  nt[] hash;
  publ c f nal  nt   halfS ze;
  pr vate f nal  nt   mask;
  publ c  nt         numMapp ngs;

  stat c f nal  nt PR ME_NUMBER = 37;

  // For FlushHandler.load() use only
  pr vate T et DTo nternal DMap(f nal  nt[] hash,
                                 f nal  nt numMapp ngs) {
    t .hash        = hash;
    t .s ze        = hash.length;
    t .halfS ze    = s ze >> 1;
    t .mask        = s ze - 1;
    t .numMapp ngs = numMapp ngs;
  }

  T et DTo nternal DMap(f nal  nt s ze) {
    t .hash = new  nt[s ze];
    Arrays.f ll(hash, Doc DToT et DMapper. D_NOT_FOUND);
    t .s ze = s ze;
    t .halfS ze = s ze >> 1;
    t .mask = s ze - 1;
    t .numMapp ngs = 0;
  }

  // Sl ghtly d fferent hash funct on from t  one used to part  on t ets to Earlyb rds.
  protected stat c  nt hashCode(f nal long t et D) {
    long t  stamp = Snowflake dParser.getT  stampFromT et d(t et D);
     nt code = ( nt) ((t  stamp - 1) ^ (t  stamp >>> 32));
    code = PR ME_NUMBER * ( nt) (t et D & Snowflake dParser.RESERVED_B TS_MASK) + code;
    return code;
  }

  protected stat c  nt  ncre ntHashCode( nt code) {
    return ((code >> 8) + code) | 1;
  }

  pr vate  nt hashPos( nt code) {
    return code & mask;
  }

  /**
   * Assoc ates t  g ven t et  D w h t  g ven  nternal doc  D.
   *
   * @param t et D T  t et  D.
   * @param  nternal D T  doc  D that should be assoc ated w h t  t et  D.
   * @param  nverseMap T  map that stores t  doc  D to t et  D assoc at ons.
   */
  publ c vo d add(f nal long t et D, f nal  nt  nternal D, f nal long[]  nverseMap) {
     nt code = hashCode(t et D);
     nt hashPos = hashPos(code);
     nt value = hash[hashPos];
    assert  nverseMap[ nternal D] == t et D;

     f (value != Doc DToT et DMapper. D_NOT_FOUND) {
      f nal  nt  nc =  ncre ntHashCode(code);
      do {
        code +=  nc;
        hashPos = hashPos(code);
        value = hash[hashPos];
      } wh le (value != Doc DToT et DMapper. D_NOT_FOUND);
    }

    assert value == Doc DToT et DMapper. D_NOT_FOUND;

    hash[hashPos] =  nternal D;
    numMapp ngs++;
  }

  /**
   * Returns t  doc  D correspond ng to t  g ven t et  D.
   *
   * @param t et D T  t et  D.
   * @param  nverseMap T  map that stores t  doc  D to t et  D assoc at ons.
   * @return T  doc  D correspond ng to t  g ven t et  D.
   */
  publ c  nt get(long t et D, f nal long[]  nverseMap) {
     nt code = hashCode(t et D);
     nt hashPos = hashPos(code);
     nt value = hash[hashPos];

     f (value != Doc DToT et DMapper. D_NOT_FOUND &&  nverseMap[value] != t et D) {
      f nal  nt  nc =  ncre ntHashCode(code);

      do {
        code +=  nc;
        hashPos = hashPos(code);
        value = hash[hashPos];
      } wh le (value != Doc DToT et DMapper. D_NOT_FOUND &&  nverseMap[value] != t et D);
    }

     f (hashPos == -1) {
      return Doc DToT et DMapper. D_NOT_FOUND;
    }
    return hash[hashPos];
  }

  @Overr de
  publ c T et DTo nternal DMap.FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  publ c stat c f nal class FlushHandler extends Flushable.Handler<T et DTo nternal DMap> {
    publ c FlushHandler() {
      super();
    }

    pr vate stat c f nal Str ng HASH_ARRAY_S ZE_PROP_NAME = "HashArrayS ze";
    pr vate stat c f nal Str ng MASK_PROP_NAME = "Mask";
    pr vate stat c f nal Str ng NUM_MAPP NGS_PROP_NAME = "NumMapp ngs";

    publ c FlushHandler(T et DTo nternal DMap objectToFlush) {
      super(objectToFlush);
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out)
      throws  OExcept on {
      T et DTo nternal DMap mapper = getObjectToFlush();

      flush nfo
          .add ntProperty(HASH_ARRAY_S ZE_PROP_NAME, mapper.hash.length)
          .add ntProperty(MASK_PROP_NAME, mapper.mask)
          .add ntProperty(NUM_MAPP NGS_PROP_NAME, mapper.numMapp ngs);

      out.wr e ntArray(mapper.hash);
    }

    @Overr de
    protected T et DTo nternal DMap doLoad(Flush nfo flush nfo, DataDeser al zer  n)
        throws  OExcept on {
      f nal  nt[] hash =  n.read ntArray();

      f nal  nt numMapp ngs = flush nfo.get ntProperty(NUM_MAPP NGS_PROP_NAME);

      return new T et DTo nternal DMap(hash, numMapp ngs);
    }
  }
}
