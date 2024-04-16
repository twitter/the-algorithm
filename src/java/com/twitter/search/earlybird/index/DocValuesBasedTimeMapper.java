package com.tw ter.search.earlyb rd. ndex;

 mport java. o. OExcept on;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene. ndex.Nu r cDocValues;
 mport org.apac .lucene.search.Doc dSet erator;

 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants;
 mport com.tw ter.search.common.ut l.analys s. ntTermAttr bute mpl;
 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;
 mport com.tw ter.search.core.earlyb rd. ndex.T  Mapper;
 mport com.tw ter.search.core.earlyb rd. ndex.column.ColumnStr deF eld ndex;

/**
 * A few caveats w n us ng t  class:
 *   - T  class only supports  n-order createdAt!
 *   - Before actually us ng t  class, one must call prepareToRead() w h a Lucene Atom cReader
 *   - prepareToRead() w ll load doc D to createdAt mapp ng  nto  mory,  f not already done.
 */
publ c class DocValuesBasedT  Mapper  mple nts T  Mapper {
  pr vate LeafReader reader;
  pr vate ColumnStr deF eld ndex docValues;

  protected  nt m nT  stamp =  LLEGAL_T ME;
  protected  nt maxT  stamp =  LLEGAL_T ME;

  /**
   * W n  ndex ng f n s s, t   thod should be called w h a  ndex reader that
   * can see all docu nts.
   * @param leafReader Lucene  ndex reader used to access "T et D" to "createdAt" mapp ng.
   */
  publ c vo d  n  al zeW hLuceneReader(LeafReader leafReader, ColumnStr deF eld ndex csf)
      throws  OExcept on {
    reader = Precond  ons.c ckNotNull(leafReader);
    docValues = Precond  ons.c ckNotNull(csf);

    // F nd t  m n and max t  stamps.
    // See SEARCH-5534
    //  n t  arch ve, t ets are always sorted  n descend ng order by t et  D, but
    // that does not  an that t  docu nts are necessar ly sorted by t  .  've observed t et  D
    // generat on be decoupled from t  stamp creat on ( .e. a larger t et  D hav ng a smaller
    // created_at t  ).
    m nT  stamp =  nteger.MAX_VALUE;
    maxT  stamp =  nteger.M N_VALUE;

    Nu r cDocValues onD skDocValues = reader.getNu r cDocValues(
        Earlyb rdF eldConstants.Earlyb rdF eldConstant.CREATED_AT_CSF_F ELD.getF eldNa ());
    for ( nt   = 0;   < reader.maxDoc(); ++ ) {
      Precond  ons.c ckArgu nt(onD skDocValues.advanceExact( ));
       nt t  stamp = ( nt) onD skDocValues.longValue();
      docValues.setValue( , t  stamp);

       f (t  stamp < m nT  stamp) {
        m nT  stamp = t  stamp;
      }
       f (t  stamp > maxT  stamp) {
        maxT  stamp = t  stamp;
      }
    }
  }

  @Overr de
  publ c  nt getLastT  () {
    return maxT  stamp;
  }

  @Overr de
  publ c  nt getF rstT  () {
    return m nT  stamp;
  }

  @Overr de
  publ c  nt getT  ( nt doc D) {
     f (doc D < 0 || doc D > reader.maxDoc()) {
      return  LLEGAL_T ME;
    }
    return ( nt) docValues.get(doc D);
  }

  @Overr de
  publ c  nt f ndF rstDoc d( nt t  Seconds,  nt smallestDoc D) throws  OExcept on {
    //  n t  full arch ve, t  smallest doc  d corresponds to largest t  stamp.
     f (t  Seconds > maxT  stamp) {
      return smallestDoc D;
    }
     f (t  Seconds < m nT  stamp) {
      return reader.maxDoc() - 1;
    }

     nt doc d = DocValues lper.getLargestDoc dW hCe lOfValue(
        reader,
        Earlyb rdF eldConstants.Earlyb rdF eldConstant.CREATED_AT_F ELD.getF eldNa (),
         ntTermAttr bute mpl.copy ntoNewBytesRef(t  Seconds));
     f (doc d == Doc dSet erator.NO_MORE_DOCS) {
      return  LLEGAL_T ME;
    }

    return doc d;
  }

  @Overr de
  publ c T  Mapper opt m ze(Doc DToT et DMapper or g nalT et dMapper,
                             Doc DToT et DMapper opt m zedT et dMapper) {
    // DocValuesBasedT  rMapper  nstances are not flus d or loaded,
    // so t  r opt m zat on  s a no-op.
    return t ;
  }

  @Overr de
  publ c Flushable.Handler<DocValuesBasedT  Mapper> getFlushHandler() {
    // Earlyb rd ndexSeg ntData w ll st ll try to flush t  DocValuesBasedT  Mapper for t 
    // respect ve seg nt, so   need to pass  n a DocValuesBasedT  Mapper  nstance to t 
    // flus r: ot rw se, Flushable.Handler.flush() w ll throw a NullPo nterExcept on.
    return new FlushHandler(new DocValuesBasedT  Mapper());
  }

  // Full arch ve earlyb rds don't actually flush or load t  DocValuesBasedT  Mapper. T   s
  // why doFlush()  s a no-op, and doLoad() returns a new DocValuesBasedT  Mapper  nstance
  // ( n  al zeW hLuceneReader() w ll be called at load t   to  n  al ze t  new
  // DocValuesBasedT  Mapper  nstance).
  publ c stat c class FlushHandler extends Flushable.Handler<DocValuesBasedT  Mapper> {
    publ c FlushHandler() {
      super();
    }

    publ c FlushHandler(DocValuesBasedT  Mapper objectToFlush) {
      super(objectToFlush);
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out) {
    }

    @Overr de
    protected DocValuesBasedT  Mapper doLoad(Flush nfo flush nfo, DataDeser al zer  n) {
      return new DocValuesBasedT  Mapper();
    }
  }
}
