package com.tw ter.search.core.earlyb rd. ndex.column;

 mport java. o. OExcept on;
 mport java.ut l.Set;
 mport java.ut l.concurrent.ConcurrentHashMap;

 mport com.google.common.collect.Sets;

 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;

publ c class Opt m zedDocValuesManager extends DocValuesManager {
  publ c Opt m zedDocValuesManager(Sc ma sc ma,  nt seg ntS ze) {
    super(sc ma, seg ntS ze);
  }

  publ c Opt m zedDocValuesManager(DocValuesManager docValuesManager,
                                   Doc DToT et DMapper or g nalT et dMapper,
                                   Doc DToT et DMapper opt m zedT et dMapper) throws  OExcept on {
    super(docValuesManager.sc ma, docValuesManager.seg ntS ze);
    Set<ColumnStr de ntV ew ndex>  ntV ew ndexes = Sets.newHashSet();
    for (Str ng f eldNa  : docValuesManager.columnStr deF elds.keySet()) {
      ColumnStr deF eld ndex or g nalColumnStr deF eld =
          docValuesManager.columnStr deF elds.get(f eldNa );
       f (or g nalColumnStr deF eld  nstanceof ColumnStr de ntV ew ndex) {
         ntV ew ndexes.add((ColumnStr de ntV ew ndex) or g nalColumnStr deF eld);
      } else {
        ColumnStr deF eld ndex opt m zedColumnStr deF eld =
            or g nalColumnStr deF eld.opt m ze(or g nalT et dMapper, opt m zedT et dMapper);
        columnStr deF elds.put(f eldNa , opt m zedColumnStr deF eld);
      }
    }

    //   have to process t  ColumnStr de ntV ew ndex  nstances after   process all ot r CSFs,
    // because   need to make sure  've opt m zed t  CSFs for t  base f elds.
    for (ColumnStr de ntV ew ndex  ntV ew ndex :  ntV ew ndexes) {
      Str ng f eldNa  =  ntV ew ndex.getNa ();
      columnStr deF elds.put(f eldNa , new ntV ewCSF(f eldNa ));
    }
  }

  pr vate Opt m zedDocValuesManager(
      Sc ma sc ma,
       nt seg ntS ze,
      ConcurrentHashMap<Str ng, ColumnStr deF eld ndex> columnStr deF elds) {
    super(sc ma, seg ntS ze, columnStr deF elds);
  }

  @Overr de
  protected ColumnStr deF eld ndex newByteCSF(Str ng f eld) {
    return new Opt m zedColumnStr deByte ndex(f eld, seg ntS ze);
  }

  @Overr de
  protected ColumnStr deF eld ndex new ntCSF(Str ng f eld) {
    return new Opt m zedColumnStr de nt ndex(f eld, seg ntS ze);
  }

  @Overr de
  protected ColumnStr deF eld ndex newLongCSF(Str ng f eld) {
    return new Opt m zedColumnStr deLong ndex(f eld, seg ntS ze);
  }

  @Overr de
  protected ColumnStr deF eld ndex newMult  ntCSF(Str ng f eld,  nt num ntsPerF eld) {
    return new Opt m zedColumnStr deMult  nt ndex(f eld, seg ntS ze, num ntsPerF eld);
  }

  @Overr de
  publ c DocValuesManager opt m ze(Doc DToT et DMapper or g nalT et dMapper,
                                   Doc DToT et DMapper opt m zedT et dMapper) throws  OExcept on {
    return t ;
  }

  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new Opt m zedFlushHandler(t );
  }

  publ c stat c class Opt m zedFlushHandler extends FlushHandler {
    publ c Opt m zedFlushHandler(Sc ma sc ma) {
      super(sc ma);
    }

    pr vate Opt m zedFlushHandler(DocValuesManager docValuesManager) {
      super(docValuesManager);
    }

    @Overr de
    protected DocValuesManager createDocValuesManager(
        Sc ma sc ma,
         nt maxSeg ntS ze,
        ConcurrentHashMap<Str ng, ColumnStr deF eld ndex> columnStr deF elds) {
      return new Opt m zedDocValuesManager(sc ma, maxSeg ntS ze, columnStr deF elds);
    }
  }
}
