package com.tw ter.search.core.earlyb rd. ndex.column;

 mport java. o. OExcept on;
 mport java.ut l. erator;
 mport java.ut l.Map;
 mport java.ut l.Set;
 mport java.ut l.concurrent.ConcurrentHashMap;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.Maps;
 mport com.google.common.collect.Sets;

 mport com.tw ter.search.common.sc ma.base.Earlyb rdF eldType;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;

publ c abstract class DocValuesManager  mple nts Flushable {
  protected f nal Sc ma sc ma;
  protected f nal  nt seg ntS ze;
  protected f nal ConcurrentHashMap<Str ng, ColumnStr deF eld ndex> columnStr deF elds;

  publ c DocValuesManager(Sc ma sc ma,  nt seg ntS ze) {
    t (sc ma, seg ntS ze, new ConcurrentHashMap<>());
  }

  protected DocValuesManager(Sc ma sc ma,
                              nt seg ntS ze,
                             ConcurrentHashMap<Str ng, ColumnStr deF eld ndex> columnStr deF elds) {
    t .sc ma = Precond  ons.c ckNotNull(sc ma);
    t .seg ntS ze = seg ntS ze;
    t .columnStr deF elds = columnStr deF elds;
  }

  protected abstract ColumnStr deF eld ndex newByteCSF(Str ng f eld);
  protected abstract ColumnStr deF eld ndex new ntCSF(Str ng f eld);
  protected abstract ColumnStr deF eld ndex newLongCSF(Str ng f eld);
  protected abstract ColumnStr deF eld ndex newMult  ntCSF(Str ng f eld,  nt num ntsPerF eld);

  /**
   * Opt m ze t  doc values manager, and return a doc values manager a more compact and fast
   * encod ng for doc values (but that   can't add new doc  Ds to).
   */
  publ c abstract DocValuesManager opt m ze(
      Doc DToT et DMapper or g nalT et dMapper,
      Doc DToT et DMapper opt m zedT et dMapper) throws  OExcept on;

  publ c Set<Str ng> getDocValueNa s() {
    return columnStr deF elds.keySet();
  }

  /**
   * Creates a new {@l nk ColumnStr deF eld ndex} for t  g ven f eld and returns  .
   */
  publ c ColumnStr deF eld ndex addColumnStr deF eld(Str ng f eld, Earlyb rdF eldType f eldType) {
    // For CSF v ew f elds,   w ll perform t  sa  c ck on t  base f eld w n   try to create
    // a ColumnStr deF eld ndex for t m  n new ntV ewCSF().
     f (!f eldType. sCsfV ewF eld()) {
      Precond  ons.c ckState(
          f eldType. sCsfLoad ntoRam(), "F eld %s  s not loaded  n RAM", f eld);
    }

     f (columnStr deF elds.conta nsKey(f eld)) {
      return columnStr deF elds.get(f eld);
    }

    f nal ColumnStr deF eld ndex  ndex;
    sw ch (f eldType.getCsfType()) {
      case BYTE:
         ndex = newByteCSF(f eld);
        break;
      case  NT:
         f (f eldType.getCsfF xedLengthNumValuesPerDoc() > 1) {
           ndex = newMult  ntCSF(f eld, f eldType.getCsfF xedLengthNumValuesPerDoc());
        } else  f (f eldType. sCsfV ewF eld()) {
           ndex = new ntV ewCSF(f eld);
        } else {
           ndex = new ntCSF(f eld);
        }
        break;
      case LONG:
         ndex = newLongCSF(f eld);
        break;
      default:
        throw new Runt  Except on(" nval d CsfType.");
    }

    columnStr deF elds.put(f eld,  ndex);
    return  ndex;
  }

  protected ColumnStr deF eld ndex new ntV ewCSF(Str ng f eld) {
    Sc ma.F eld nfo  nfo = Precond  ons.c ckNotNull(sc ma.getF eld nfo(f eld));
    Sc ma.F eld nfo baseF eld nfo = Precond  ons.c ckNotNull(
        sc ma.getF eld nfo( nfo.getF eldType().getCsfV ewBaseF eld d()));

    Precond  ons.c ckState(
        baseF eld nfo.getF eldType(). sCsfLoad ntoRam(),
        "F eld %s has a base f eld (%s) that  s not loaded  n RAM",
        f eld, baseF eld nfo.getNa ());

    //   m ght not have a CSF for t  base f eld yet.
    ColumnStr deF eld ndex baseF eld ndex =
        addColumnStr deF eld(baseF eld nfo.getNa (), baseF eld nfo.getF eldType());
    Precond  ons.c ckNotNull(baseF eld ndex);
    Precond  ons.c ckState(baseF eld ndex  nstanceof AbstractColumnStr deMult  nt ndex);
    return new ColumnStr de ntV ew ndex( nfo, (AbstractColumnStr deMult  nt ndex) baseF eld ndex);
  }

  /**
   * Returns t  ColumnStr deF eld ndex  nstance for t  g ven f eld.
   */
  publ c ColumnStr deF eld ndex getColumnStr deF eld ndex(Str ng f eld) {
    ColumnStr deF eld ndex docValues = columnStr deF elds.get(f eld);
     f (docValues == null) {
      Sc ma.F eld nfo  nfo = sc ma.getF eld nfo(f eld);
       f ( nfo != null &&  nfo.getF eldType(). sCsfDefaultValueSet()) {
        return new ConstantColumnStr deF eld ndex(f eld,  nfo.getF eldType().getCsfDefaultValue());
      }
    }

    return docValues;
  }

  pr vate stat c f nal Str ng CSF_ NDEX_CLASS_NAME_PROP_NAME = "csf ndexClassNa ";
  pr vate stat c f nal Str ng CSF_PROP_NAME = "column_str de_f elds";
  protected stat c f nal Str ng MAX_SEGMENT_S ZE_PROP_NAME = "maxSeg ntS ze";

  pr vate stat c Map<Str ng, Set<Sc ma.F eld nfo>> get ntV ewF elds(Sc ma sc ma) {
    Map<Str ng, Set<Sc ma.F eld nfo>>  ntV ewF elds = Maps.newHashMap();
    for (Sc ma.F eld nfo f eld nfo : sc ma.getF eld nfos()) {
       f (f eld nfo.getF eldType(). sCsfV ewF eld()) {
        Sc ma.F eld nfo baseF eld nfo = Precond  ons.c ckNotNull(
            sc ma.getF eld nfo(f eld nfo.getF eldType().getCsfV ewBaseF eld d()));
        Str ng baseF eldNa  = baseF eld nfo.getNa ();
        Set<Sc ma.F eld nfo>  ntV ewF eldsForBaseF eld =
             ntV ewF elds.compute fAbsent(baseF eldNa , k -> Sets.newHashSet());
         ntV ewF eldsForBaseF eld.add(f eld nfo);
      }
    }
    return  ntV ewF elds;
  }

  publ c abstract stat c class FlushHandler extends Handler<DocValuesManager> {
    pr vate f nal Sc ma sc ma;

    publ c FlushHandler(Sc ma sc ma) {
      t .sc ma = sc ma;
    }

    publ c FlushHandler(DocValuesManager docValuesManager) {
      super(docValuesManager);
      t .sc ma = docValuesManager.sc ma;
    }

    @Overr de
    publ c vo d doFlush(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
      long startT   = getClock().nowM ll s();

      DocValuesManager docValuesManager = getObjectToFlush();
      flush nfo.add ntProperty(MAX_SEGMENT_S ZE_PROP_NAME, docValuesManager.seg ntS ze);
      long s zeBeforeFlush = out.length();
      Flush nfo csfProps = flush nfo.newSubPropert es(CSF_PROP_NAME);
      for (ColumnStr deF eld ndex csf : docValuesManager.columnStr deF elds.values()) {
       f (!(csf  nstanceof ColumnStr de ntV ew ndex)) {
        Precond  ons.c ckState(
            csf  nstanceof Flushable,
            "Cannot flush column str de f eld {} of type {}",
            csf.getNa (), csf.getClass().getCanon calNa ());
        Flush nfo  nfo = csfProps.newSubPropert es(csf.getNa ());
         nfo.addStr ngProperty(CSF_ NDEX_CLASS_NAME_PROP_NAME, csf.getClass().getCanon calNa ());
        ((Flushable) csf).getFlushHandler().flush( nfo, out);
      }
    }
      csfProps.setS ze nBytes(out.length() - s zeBeforeFlush);
      getFlushT  rStats().t  r ncre nt(getClock().nowM ll s() - startT  );
    }

    @Overr de
    publ c DocValuesManager doLoad(Flush nfo flush nfo, DataDeser al zer  n)
        throws  OExcept on {
      long startT   = getClock().nowM ll s();
      Map<Str ng, Set<Sc ma.F eld nfo>>  ntV ewF elds = get ntV ewF elds(sc ma);

      Flush nfo csfProps = flush nfo.getSubPropert es(CSF_PROP_NAME);
      ConcurrentHashMap<Str ng, ColumnStr deF eld ndex> columnStr deF elds =
          new ConcurrentHashMap<>();

       erator<Str ng> csfProp er = csfProps.getKey erator();
      wh le (csfProp er.hasNext()) {
        Str ng f eldNa  = csfProp er.next();
        try {
          Flush nfo  nfo = csfProps.getSubPropert es(f eldNa );
          Str ng classNa  =  nfo.getStr ngProperty(CSF_ NDEX_CLASS_NAME_PROP_NAME);
          Class<? extends ColumnStr deF eld ndex> f eld ndexType =
              (Class<? extends ColumnStr deF eld ndex>) Class.forNa (classNa );
          Precond  ons.c ckNotNull(
              f eld ndexType,
              " nval d f eld conf gurat on: f eld " + f eldNa  + " not found  n conf g.");

          for (Class<?> c : f eld ndexType.getDeclaredClasses()) {
             f (Handler.class. sAss gnableFrom(c)) {
              @SuppressWarn ngs("rawtypes")
              Handler handler = (Handler) c.new nstance();
              ColumnStr deF eld ndex  ndex = (ColumnStr deF eld ndex) handler.load(
                  csfProps.getSubPropert es(f eldNa ),  n);
              columnStr deF elds.put(f eldNa ,  ndex);

              //  f t   s a base f eld, create ColumnStr de ntV ew ndex  nstances for all t 
              // v ew f elds based on  .
               f ( ndex  nstanceof AbstractColumnStr deMult  nt ndex) {
                AbstractColumnStr deMult  nt ndex mult  nt ndex =
                    (AbstractColumnStr deMult  nt ndex)  ndex;

                //   should have AbstractColumnStr deMult  nt ndex  nstances only for base f elds
                // and all   base f elds have v ews def ned on top of t m.
                for (Sc ma.F eld nfo  ntV ewF eld nfo :  ntV ewF elds.get(f eldNa )) {
                  columnStr deF elds.put(
                       ntV ewF eld nfo.getNa (),
                      new ColumnStr de ntV ew ndex( ntV ewF eld nfo, mult  nt ndex));
                }
              }

              break;
            }
          }
        } catch (ClassNotFoundExcept on |  llegalAccessExcept on |  nstant at onExcept on e) {
          throw new  OExcept on(
              " nval d f eld conf gurat on for column str de f eld: " + f eldNa , e);
        }
      }
      getLoadT  rStats().t  r ncre nt(getClock().nowM ll s() - startT  );

      return createDocValuesManager(
          sc ma,
          flush nfo.get ntProperty(MAX_SEGMENT_S ZE_PROP_NAME),
          columnStr deF elds);
    }

    protected abstract DocValuesManager createDocValuesManager(
        Sc ma docValuesSc ma,
         nt maxSeg ntS ze,
        ConcurrentHashMap<Str ng, ColumnStr deF eld ndex> columnStr deF elds);
  }
}
