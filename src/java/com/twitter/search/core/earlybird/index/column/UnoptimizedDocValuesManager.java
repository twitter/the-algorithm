package com.tw ter.search.core.earlyb rd. ndex.column;

 mport java. o. OExcept on;
 mport java.ut l.concurrent.ConcurrentHashMap;

 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;

publ c class Unopt m zedDocValuesManager extends DocValuesManager {
  publ c Unopt m zedDocValuesManager(Sc ma sc ma,  nt seg ntS ze) {
    super(sc ma, seg ntS ze);
  }

  pr vate Unopt m zedDocValuesManager(
      Sc ma sc ma,
       nt seg ntS ze,
      ConcurrentHashMap<Str ng, ColumnStr deF eld ndex> columnStr deF elds) {
    super(sc ma, seg ntS ze, columnStr deF elds);
  }

  @Overr de
  protected ColumnStr deF eld ndex newByteCSF(Str ng f eld) {
    return new ColumnStr deByte ndex(f eld, seg ntS ze);
  }

  @Overr de
  protected ColumnStr deF eld ndex new ntCSF(Str ng f eld) {
    return new ColumnStr de nt ndex(f eld, seg ntS ze);
  }

  @Overr de
  protected ColumnStr deF eld ndex newLongCSF(Str ng f eld) {
    return new ColumnStr deLong ndex(f eld, seg ntS ze);
  }

  @Overr de
  protected ColumnStr deF eld ndex newMult  ntCSF(Str ng f eld,  nt num ntsPerF eld) {
    return new ColumnStr deMult  nt ndex(f eld, seg ntS ze, num ntsPerF eld);
  }

  @Overr de
  publ c DocValuesManager opt m ze(Doc DToT et DMapper or g nalT et dMapper,
                                   Doc DToT et DMapper opt m zedT et dMapper) throws  OExcept on {
    return new Opt m zedDocValuesManager(t , or g nalT et dMapper, opt m zedT et dMapper);
  }

  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new Unopt m zedFlushHandler(t );
  }

  publ c stat c class Unopt m zedFlushHandler extends FlushHandler {
    publ c Unopt m zedFlushHandler(Sc ma sc ma) {
      super(sc ma);
    }

    pr vate Unopt m zedFlushHandler(DocValuesManager docValuesManager) {
      super(docValuesManager);
    }

    @Overr de
    protected DocValuesManager createDocValuesManager(
        Sc ma sc ma,
         nt maxSeg ntS ze,
        ConcurrentHashMap<Str ng, ColumnStr deF eld ndex> columnStr deF elds) {
      return new Unopt m zedDocValuesManager(sc ma, maxSeg ntS ze, columnStr deF elds);
    }
  }
}
