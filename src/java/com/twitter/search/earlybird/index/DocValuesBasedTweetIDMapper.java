package com.tw ter.search.earlyb rd. ndex;

 mport java. o. OExcept on;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene. ndex.Nu r cDocValues;
 mport org.apac .lucene.search.Doc dSet erator;

 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants;
 mport com.tw ter.search.common.ut l.analys s.SortableLongTermAttr bute mpl;
 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;
 mport com.tw ter.search.core.earlyb rd. ndex.column.ColumnStr deF eld ndex;

/**
 * A few caveats w n us ng t  class:
 *   - Before actually us ng t  class, one must call prepareToRead() w h a Lucene Atom cReader
 *   - prepareToRead() w ll load doc D to t et D mapp ng  nto  mory,  f not already done.
 */
publ c class DocValuesBasedT et DMapper extends T et DMapper  mple nts Flushable {
  pr vate LeafReader reader;
  pr vate ColumnStr deF eld ndex docValues;

  /**
   * W n  ndex ng f n s s, t   thod should be called w h a  ndex reader that
   * can see all docu nts.
   * @param leafReader Lucene  ndex reader used to access T et D to  nternal  D mapp ng
   */
  publ c vo d  n  al zeW hLuceneReader(LeafReader leafReader, ColumnStr deF eld ndex csf)
      throws  OExcept on {
    reader = Precond  ons.c ckNotNull(leafReader);
    docValues = Precond  ons.c ckNotNull(csf);

    Nu r cDocValues onD skDocValues = reader.getNu r cDocValues(
        Earlyb rdF eldConstants.Earlyb rdF eldConstant. D_CSF_F ELD.getF eldNa ());
    for ( nt   = 0;   < reader.maxDoc(); ++ ) {
      Precond  ons.c ckArgu nt(onD skDocValues.advanceExact( ));
      docValues.setValue( , onD skDocValues.longValue());
    }

    //  n t  arch ve, t ets are always sorted  n descend ng order of t et  D.
    setM nT et D(docValues.get(reader.maxDoc() - 1));
    setMaxT et D(docValues.get(0));
    setM nDoc D(0);
    setMaxDoc D(reader.maxDoc() - 1);
    setNumDocs(reader.maxDoc());
  }

  @Overr de
  publ c  nt getDoc D(long t et D) throws  OExcept on {
     nt doc d = DocValues lper.getF rstDoc dW hValue(
        reader,
        Earlyb rdF eldConstants.Earlyb rdF eldConstant. D_F ELD.getF eldNa (),
        SortableLongTermAttr bute mpl.copy ntoNewBytesRef(t et D));
     f (doc d == Doc dSet erator.NO_MORE_DOCS) {
      return  D_NOT_FOUND;
    }
    return doc d;
  }

  @Overr de
  protected  nt getNextDoc D nternal( nt doc D) {
    // T  doc  Ds are consecut ve and T et DMapper already c cked t  boundary cond  ons.
    return doc D + 1;
  }

  @Overr de
  protected  nt getPrev ousDoc D nternal( nt doc D) {
    // T  doc  Ds are consecut ve and T et DMapper already c cked t  boundary cond  ons.
    return doc D - 1;
  }

  @Overr de
  publ c long getT et D( nt  nternal D) {
     f ( nternal D < 0 ||  nternal D > getMaxDoc D()) {
      return  D_NOT_FOUND;
    }
    return docValues.get( nternal D);
  }

  @Overr de
  protected  nt addMapp ng nternal(long t et D) {
    throw new UnsupportedOperat onExcept on(
        "Arch veT et DMapper should be wr ten through Lucene  nstead of T et DMapp ngWr er");
  }

  @Overr de
  protected f nal  nt f ndDoc DBound nternal(long t et D,
                                             boolean f ndMaxDoc D) throws  OExcept on {
    // TermsEnum has a seekCe l()  thod, but doesn't have a seekFloor()  thod, so t  best   can
    // do  re  s  gnore f ndLow and always return t  ce l ng  f t  t et  D cannot be found.
    // Ho ver,  n pract ce,   do a seekExact()  n both cases: see t   nner classes  n
    // com.tw ter.search.core.earlyb rd. ndex. nverted.Realt   ndexTerms.
     nt doc d = DocValues lper.getLargestDoc dW hCe lOfValue(
        reader,
        Earlyb rdF eldConstants.Earlyb rdF eldConstant. D_F ELD.getF eldNa (),
        SortableLongTermAttr bute mpl.copy ntoNewBytesRef(t et D));
     f (doc d == Doc dSet erator.NO_MORE_DOCS) {
      return  D_NOT_FOUND;
    }

    // T  doc d  s t  upper bound of t  search, so  f   want t  lo r bound,
    // because doc  Ds are dense,   subtract one.
    return f ndMaxDoc D ? doc d : doc d - 1;
  }

  @Overr de
  publ c Doc DToT et DMapper opt m ze() {
    // DocValuesBasedT et DMapper  nstances are not flus d or loaded,
    // so t  r opt m zat on  s a no-op.
    return t ;
  }

  @Overr de
  publ c Flushable.Handler<DocValuesBasedT et DMapper> getFlushHandler() {
    // Earlyb rd ndexSeg ntData w ll st ll try to flush t  DocValuesBasedT et DMapper
    // for t  respect ve seg nt, so   need to pass  n a DocValuesBasedT et DMapper  nstance to
    // t  flus r: ot rw se, Flushable.Handler.flush() w ll throw a NullPo nterExcept on.
    return new FlushHandler(new DocValuesBasedT et DMapper());
  }

  // Full arch ve earlyb rds don't actually flush or load t  DocValuesBasedT et DMapper. T   s
  // why doFlush()  s a no-op, and doLoad() returns a new DocValuesBasedT et DMapper  nstance
  // ( n  al zeW hLuceneReader() w ll be called at load t   to  n  al ze t  new
  // DocValuesBasedT et DMapper  nstance).
  publ c stat c class FlushHandler extends Flushable.Handler<DocValuesBasedT et DMapper> {
    publ c FlushHandler() {
      super();
    }

    publ c FlushHandler(DocValuesBasedT et DMapper objectToFlush) {
      super(objectToFlush);
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out) {
    }

    @Overr de
    protected DocValuesBasedT et DMapper doLoad(Flush nfo flush nfo, DataDeser al zer  n) {
      return new DocValuesBasedT et DMapper();
    }
  }
}
