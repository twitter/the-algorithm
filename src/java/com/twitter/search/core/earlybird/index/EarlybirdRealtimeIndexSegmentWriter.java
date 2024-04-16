package com.tw ter.search.core.earlyb rd. ndex;

 mport java. o. OExcept on;
 mport java.ut l.ArrayL st;
 mport java.ut l.Arrays;
 mport java.ut l.HashMap;
 mport java.ut l.HashSet;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;
 mport java.ut l.concurrent.ConcurrentHashMap;

 mport javax.annotat on.Nullable;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;
 mport org.apac .lucene.analys s.Analyzer;
 mport org.apac .lucene.analys s.TokenStream;
 mport org.apac .lucene.analys s.tokenattr butes.OffsetAttr bute;
 mport org.apac .lucene.analys s.tokenattr butes.Pos  on ncre ntAttr bute;
 mport org.apac .lucene.analys s.tokenattr butes.TermToBytesRefAttr bute;
 mport org.apac .lucene.docu nt.Docu nt;
 mport org.apac .lucene.docu nt.F eld;
 mport org.apac .lucene.facet.FacetsConf g;
 mport org.apac .lucene. ndex.DocValuesType;
 mport org.apac .lucene. ndex.F eld nvertState;
 mport org.apac .lucene. ndex. ndexOpt ons;
 mport org.apac .lucene. ndex. ndexableF eld;
 mport org.apac .lucene. ndex. ndexableF eldType;
 mport org.apac .lucene.search.s m lar  es.S m lar y;
 mport org.apac .lucene.store.D rectory;
 mport org.apac .lucene.ut l.Attr buteS ce;
 mport org.apac .lucene.ut l.BytesRef;
 mport org.apac .lucene.ut l.BytesRefHash;
 mport org.apac .lucene.ut l.Vers on;

 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.sc ma.base.Earlyb rdF eldType;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants;
 mport com.tw ter.search.core.earlyb rd.facets.FacetCount ngArrayWr er;
 mport com.tw ter.search.core.earlyb rd.facets.Facet DMap.FacetF eld;
 mport com.tw ter.search.core.earlyb rd.facets.FacetLabelProv der;
 mport com.tw ter.search.core.earlyb rd.facets.FacetUt l;
 mport com.tw ter.search.core.earlyb rd. ndex.column.ColumnStr deByte ndex;
 mport com.tw ter.search.core.earlyb rd. ndex.extens ons.Earlyb rdRealt   ndexExtens onsData;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted.Earlyb rdCSFDocValuesProcessor;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted. nvertedRealt   ndex;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted. nvertedRealt   ndexWr er;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted.TermPo nterEncod ng;
 mport com.tw ter.search.core.earlyb rd. ndex.ut l.AllDocs erator;

/**
 * Earlyb rd ndexWr er  mple ntat on that wr es realt    n- mory seg nts.
 * Note that    s used by both Earlyb rds and ExpertSearch.
 */
publ c f nal class Earlyb rdRealt   ndexSeg ntWr er extends Earlyb rd ndexSeg ntWr er {
  pr vate stat c f nal Logger LOG =
    LoggerFactory.getLogger(Earlyb rdRealt   ndexSeg ntWr er.class);
  /**
   * Max mum t et length  s 10k, sett ng max mum token pos  on to 25k  n case of   rd un code.
   */
  pr vate stat c f nal  nt MAX_POS T ON = 25000;

  pr vate stat c f nal Str ng OUT_OF_ORDER_APPEND_UNSUPPORTED_STATS_PATTERN =
      "out_of_order_append_unsupported_for_f eld_%s";
  pr vate stat c f nal ConcurrentHashMap<Str ng, SearchRateCounter>
      UNSUPPORTED_OUT_OF_ORDER_APPEND_MAP = new ConcurrentHashMap<>();
  pr vate stat c f nal SearchRateCounter NUM_TWEETS_DROPPED =
      SearchRateCounter.export("Earlyb rdRealt   ndexSeg ntWr er_num_t ets_dropped");

  pr vate long nextF eldGen;

  pr vate HashMap<Str ng, PerF eld> f elds = new HashMap<>();
  pr vate L st<PerF eld> f elds nDocu nt = new ArrayL st<>();

  pr vate f nal Earlyb rdCSFDocValuesProcessor docValuesProcessor;

  pr vate Map<Str ng,  nvertedRealt   ndexWr er> termHashSync = new HashMap<>();
  pr vate Set<Str ng> appendedF elds = new HashSet<>();

  pr vate f nal Analyzer analyzer;
  pr vate f nal S m lar y s m lar y;

  pr vate f nal Earlyb rdRealt   ndexSeg ntData seg ntData;

  pr vate f nal F eld allDocsF eld;

  @Nullable
  pr vate f nal FacetCount ngArrayWr er facetCount ngArrayWr er;

  /**
   * Creates a new wr er for a real-t    n- mory Earlyb rd seg nt.
   *
   * Do not add publ c constructors to t  class. Earlyb rdRealt   ndexSeg ntWr er  nstances
   * should be created only by call ng
   * Earlyb rdRealt   ndexSeg ntData.createEarlyb rd ndexSeg ntWr er(), to make sure everyth ng
   *  s set up properly (such as CSF readers).
   */
  Earlyb rdRealt   ndexSeg ntWr er(
      Earlyb rdRealt   ndexSeg ntData seg ntData,
      Analyzer analyzer,
      S m lar y s m lar y) {
    Precond  ons.c ckNotNull(seg ntData);
    t .seg ntData = seg ntData;
    t .facetCount ngArrayWr er = seg ntData.createFacetCount ngArrayWr er();
    t .docValuesProcessor = new Earlyb rdCSFDocValuesProcessor(seg ntData.getDocValuesManager());
    t .analyzer = analyzer;
    t .s m lar y = s m lar y;
    t .allDocsF eld = bu ldAllDocsF eld(seg ntData);
  }

  @Overr de
  publ c Earlyb rdRealt   ndexSeg ntData getSeg ntData() {
    return seg ntData;
  }

  @Overr de
  publ c  nt numDocsNoDelete() {
    return seg ntData.getDoc DToT et DMapper().getNumDocs();
  }

  @Overr de
  publ c vo d addDocu nt(Docu nt doc) throws  OExcept on {
    // T   thod should be called only from Expertsearch, not t ets Earlyb rds.
    Doc DToT et DMapper doc dToT et dMapper = seg ntData.getDoc DToT et DMapper();
    Precond  ons.c ckState(doc dToT et dMapper  nstanceof Sequent alDoc DMapper);

    // Make sure   have space for a new doc  n t  seg nt.
    Precond  ons.c ckState(doc dToT et dMapper.getNumDocs() < seg ntData.getMaxSeg ntS ze(),
                             "Cannot add a new docu nt to t  seg nt, because  's full.");

    addDocu nt(doc, doc dToT et dMapper.addMapp ng(-1L), false);
  }

  @Overr de
  publ c vo d addT et(Docu nt doc, long t et d, boolean doc sOffens ve) throws  OExcept on {
    Doc DToT et DMapper doc dToT et dMapper = seg ntData.getDoc DToT et DMapper();
    Precond  ons.c ckState(!(doc dToT et dMapper  nstanceof Sequent alDoc DMapper));

    // Make sure   have space for a new doc  n t  seg nt.
    Precond  ons.c ckState(doc dToT et dMapper.getNumDocs() < seg ntData.getMaxSeg ntS ze(),
                             "Cannot add a new docu nt to t  seg nt, because  's full.");

    Precond  ons.c ckNotNull(doc.getF eld(
        Earlyb rdF eldConstants.Earlyb rdF eldConstant.CREATED_AT_F ELD.getF eldNa ()));

    addAllDocsF eld(doc);

     nt doc d = doc dToT et dMapper.addMapp ng(t et d);
    // Make sure   successfully ass gned a doc  D to t  new docu nt/t et before proceed ng.
    //  f t  doc d  s Doc DToT et DMapper. D_NOT_FOUND t n e  r:
    //  1. t  t et  s older than t   OutOfOrderRealt  T et DMapper.seg ntBoundaryT  stamp and
    //     s too old for t  seg nt
    //  2. t  OutOfOrderRealt  T et DMapper does not have any ava lable doc  ds left
     f (doc d == Doc DToT et DMapper. D_NOT_FOUND) {
      LOG. nfo("Could not ass gn doc  d for t et. Dropp ng t et  d " + t et d
          + " for seg nt w h t  sl ce: " + seg ntData.getT  Sl ce D());
      NUM_TWEETS_DROPPED. ncre nt();
      return;
    }

    addDocu nt(doc, doc d, doc sOffens ve);
  }

  pr vate vo d addDocu nt(Docu nt doc,
                            nt doc d,
                           boolean doc sOffens ve) throws  OExcept on {
    f elds nDocu nt.clear();

    long f eldGen = nextF eldGen++;

    // NOTE:   need two passes  re,  n case t re are
    // mult -valued f elds, because   must process all
    //  nstances of a g ven f eld at once, s nce t 
    // analyzer  s free to reuse TokenStream across f elds
    // ( .e.,   cannot have more than one TokenStream
    // runn ng "at once"):

    try {
      for ( ndexableF eld f eld : doc) {
         f (!sk pF eld(f eld.na ())) {
          processF eld(doc d, f eld, f eldGen, doc sOffens ve);
        }
      }
    } f nally {
      // F n sh each  ndexed f eld na  seen  n t  docu nt:
      for (PerF eld f eld : f elds nDocu nt) {
        f eld.f n sh(doc d);
      }

      // W n  ndex ng a dum  docu nt for out-of-order updates  nto a loaded seg nt, that
      // docu nt gets doc D set as maxSeg nt s ze. So   have to make sure that   never
      // sync backwards  n docu nt order.
       nt smallestDoc D = Math.m n(doc d, seg ntData.getSyncData().getSmallestDoc D());
      seg ntData.updateSmallestDoc D(smallestDoc D);
    }
  }

  @Overr de
  protected vo d appendOutOfOrder(Docu nt doc,  nt  nternalDoc D) throws  OExcept on {
    Precond  ons.c ckNotNull(doc);
    f elds nDocu nt.clear();

    long f eldGen = nextF eldGen++;

    try {
      for ( ndexableF eld  ndexableF eld : doc) {
         f (!sk pF eld( ndexableF eld.na ())) {
          Sc ma.F eld nfo f  = seg ntData.getSc ma().getF eld nfo( ndexableF eld.na ());
           f (f  == null) {
            LOG.error("F eld nfo for " +  ndexableF eld.na () + "  s null!");
            cont nue;
          }
           f (seg ntData. sOpt m zed() && f .getF eldType().beco s mmutable()) {
            UNSUPPORTED_OUT_OF_ORDER_APPEND_MAP.compute fAbsent(
                 ndexableF eld.na (),
                f -> SearchRateCounter.export(
                    Str ng.format(OUT_OF_ORDER_APPEND_UNSUPPORTED_STATS_PATTERN, f))
            ). ncre nt();
            cont nue;
          }
          processF eld( nternalDoc D,  ndexableF eld, f eldGen, false);
          appendedF elds.add( ndexableF eld.na ());
        }
      }
    } f nally {
      // F n sh each  ndexed f eld na  seen  n t  docu nt:
      for (PerF eld f eld : f elds nDocu nt) {
        f eld.f n sh( nternalDoc D);
      }
      // force sync
      seg ntData.updateSmallestDoc D(seg ntData.getSyncData().getSmallestDoc D());
    }
  }

  @Overr de
  publ c vo d add ndexes(D rectory... d rs) {
    throw new UnsupportedOperat onExcept on(" n realt   mode add ndexes()  s currently "
            + "not supported.");
  }

  @Overr de
  publ c vo d force rge() {
    //   always have a s ngle seg nt  n realt  -mode
  }

  @Overr de
  publ c vo d close() {
    // noth ng to close
  }

  pr vate vo d processF eld(
       nt doc d,
       ndexableF eld f eld,
      long f eldGen,
      boolean currentDoc sOffens ve) throws  OExcept on {
    Str ng f eldNa  = f eld.na ();
     ndexableF eldType f eldType = f eld.f eldType();

    //  nvert  ndexed f elds:
     f (f eldType. ndexOpt ons() !=  ndexOpt ons.NONE) {
      PerF eld perF eld = getOrAddF eld(f eldNa , f eldType);

      // W t r t   s t  f rst t     have seen t  f eld  n t  docu nt.
      boolean f rst = perF eld.f eldGen != f eldGen;
      perF eld. nvert(f eld, doc d, f rst, currentDoc sOffens ve);

       f (f rst) {
        f elds nDocu nt.add(perF eld);
        perF eld.f eldGen = f eldGen;
      }
    } else {
      Sc ma.F eld nfo facetF eld nfo =
              seg ntData.getSc ma().getFacetF eldByF eldNa (f eldNa );
      FacetF eld facetF eld = facetF eld nfo != null
              ? seg ntData.getFacet DMap().getFacetF eld(facetF eld nfo) : null;
      Earlyb rdF eldType facetF eldType = facetF eld nfo != null
              ? facetF eld nfo.getF eldType() : null;
      Precond  ons.c ckState(
          facetF eld nfo == null || (facetF eld != null && facetF eldType != null));
       f (facetF eld != null && facetF eldType. sUseCSFForFacetCount ng()) {
          seg ntData.getFacetLabelProv ders().put(
              facetF eld.getFacetNa (),
              Precond  ons.c ckNotNull(
                      FacetUt l.chooseFacetLabelProv der(facetF eldType, null)));
       }
    }

     f (f eldType.docValuesType() != DocValuesType.NONE) {
      StoredF eldsConsu rBu lder consu rBu lder = new StoredF eldsConsu rBu lder(
              f eldNa , (Earlyb rdF eldType) f eldType);
      Earlyb rdRealt   ndexExtens onsData  ndexExtens on = seg ntData.get ndexExtens onsData();
       f ( ndexExtens on != null) {
         ndexExtens on.createStoredF eldsConsu r(consu rBu lder);
      }
       f (consu rBu lder. sUseDefaultConsu r()) {
        consu rBu lder.addConsu r(docValuesProcessor);
      }

      StoredF eldsConsu r storedF eldsConsu r = consu rBu lder.bu ld();
       f (storedF eldsConsu r != null) {
        storedF eldsConsu r.addF eld(doc d, f eld);
      }
    }
  }

  /** Returns a prev ously created {@l nk PerF eld}, absorb ng t  type  nformat on from
   * {@l nk org.apac .lucene.docu nt.F eldType}, and creates a new {@l nk PerF eld}  f t  f eld
   * na  wasn't seen yet. */
  pr vate PerF eld getOrAddF eld(Str ng na ,  ndexableF eldType f eldType) {
    // Note that t  could be a compute fAbsent, but that allocates a closure  n t  hot path and
    // slows down  ndex ng.
    PerF eld perF eld = f elds.get(na );
     f (perF eld == null) {
      boolean om Norms = f eldType.om Norms() || f eldType. ndexOpt ons() ==  ndexOpt ons.NONE;
      perF eld = new PerF eld(t , na , f eldType. ndexOpt ons(), om Norms);
      f elds.put(na , perF eld);
    }
    return perF eld;
  }

  /** NOTE: not stat c: accesses at least docState, termsHash. */
  pr vate stat c f nal class PerF eld  mple nts Comparable<PerF eld> {

    pr vate f nal Earlyb rdRealt   ndexSeg ntWr er  ndexSeg ntWr er;

    pr vate f nal Str ng f eldNa ;
    pr vate f nal  ndexOpt ons  ndexOpt ons;
    pr vate f nal boolean om Norms;

    pr vate  nvertedRealt   ndex  nvertedF eld;
    pr vate  nvertedDocConsu r  ndexWr er;

    /**   use t  to know w n a PerF eld  s seen for t 
     *  f rst t    n t  current docu nt. */
    pr vate long f eldGen = -1;

    // reused
    pr vate TokenStream tokenStream;

    pr vate  nt currentPos  on;
    pr vate  nt currentOffset;
    pr vate  nt currentLength;
    pr vate  nt currentOverlap;
    pr vate  nt lastStartOffset;
    pr vate  nt lastPos  on;

    publ c PerF eld(
        Earlyb rdRealt   ndexSeg ntWr er  ndexSeg ntWr er,
        Str ng f eldNa ,
         ndexOpt ons  ndexOpt ons,
        boolean om Norms) {
      t . ndexSeg ntWr er =  ndexSeg ntWr er;
      t .f eldNa  = f eldNa ;
      t . ndexOpt ons =  ndexOpt ons;
      t .om Norms = om Norms;

       n  nvertState();
    }

    vo d  n  nvertState() {
      //  's okay  f t   s null -  n that case Tw terTermHashPerF eld
      // w ll not add   to t  facet array
      f nal Sc ma.F eld nfo facetF eld nfo
          =  ndexSeg ntWr er.seg ntData.getSc ma().getFacetF eldByF eldNa (f eldNa );
      f nal FacetF eld facetF eld = facetF eld nfo != null
              ?  ndexSeg ntWr er.seg ntData.getFacet DMap().getFacetF eld(facetF eld nfo) : null;
      f nal Earlyb rdF eldType facetF eldType
          = facetF eld nfo != null ? facetF eld nfo.getF eldType() : null;
      Precond  ons.c ckState(
          facetF eld nfo == null || (facetF eld != null && facetF eldType != null));

       f (facetF eld != null && facetF eldType. sUseCSFForFacetCount ng()) {
         ndexSeg ntWr er.seg ntData.getFacetLabelProv ders().put(
            facetF eld.getFacetNa (),
            Precond  ons.c ckNotNull(
                FacetUt l.chooseFacetLabelProv der(facetF eldType, null)));
        return;
      }

      Sc ma.F eld nfo f  =  ndexSeg ntWr er.seg ntData.getSc ma().getF eld nfo(f eldNa );
      f nal Earlyb rdF eldType f eldType = f .getF eldType();

       nvertedDocConsu rBu lder consu rBu lder = new  nvertedDocConsu rBu lder(
           ndexSeg ntWr er.seg ntData, f eldNa , f eldType);
      Earlyb rdRealt   ndexExtens onsData  ndexExtens on =
           ndexSeg ntWr er.seg ntData.get ndexExtens onsData();
       f ( ndexExtens on != null) {
         ndexExtens on.create nvertedDocConsu r(consu rBu lder);
      }

       f (consu rBu lder. sUseDefaultConsu r()) {
         f ( ndexSeg ntWr er.seg ntData.getPerF eldMap().conta nsKey(f eldNa )) {
           nvertedF eld = ( nvertedRealt   ndex)  ndexSeg ntWr er
              .seg ntData.getPerF eldMap().get(f eldNa );
        } else {
           nvertedF eld = new  nvertedRealt   ndex(
              f eldType,
              TermPo nterEncod ng.DEFAULT_ENCOD NG,
              f eldNa );
        }

         nvertedRealt   ndexWr er f eldWr er = new  nvertedRealt   ndexWr er(
             nvertedF eld, facetF eld,  ndexSeg ntWr er.facetCount ngArrayWr er);

         f (facetF eld != null) {
          Map<Str ng, FacetLabelProv der> prov derMap =
               ndexSeg ntWr er.seg ntData.getFacetLabelProv ders();
           f (!prov derMap.conta nsKey(facetF eld.getFacetNa ())) {
            prov derMap.put(
                facetF eld.getFacetNa (),
                Precond  ons.c ckNotNull(
                    FacetUt l.chooseFacetLabelProv der(facetF eldType,  nvertedF eld)));
          }
        }

         ndexSeg ntWr er.seg ntData.addF eld(f eldNa ,  nvertedF eld);

         f ( ndexSeg ntWr er.appendedF elds.conta ns(f eldNa )) {
           ndexSeg ntWr er.termHashSync.put(f eldNa , f eldWr er);
        }

        consu rBu lder.addConsu r(f eldWr er);
      }

       ndexWr er = consu rBu lder.bu ld();
    }

    @Overr de
    publ c  nt compareTo(PerF eld ot r) {
      return t .f eldNa .compareTo(ot r.f eldNa );
    }

    @Overr de
    publ c boolean equals(Object ot r) {
       f (!(ot r  nstanceof PerF eld)) {
        return false;
      }

      return t .f eldNa .equals(((PerF eld) ot r).f eldNa );
    }

    @Overr de
    publ c  nt hashCode() {
      return f eldNa .hashCode();
    }

    publ c vo d f n sh( nt doc d) {
       f ( ndexWr er != null) {
         ndexWr er.f n sh();
      }

       f (!om Norms) {
        F eld nvertState state = new F eld nvertState(
            Vers on.LATEST.major,
            f eldNa ,
             ndexOpt ons,
            currentPos  on,
            currentLength,
            currentOverlap,
            currentOffset,
            0,   // maxTermFrequency
            0);  // un queTermCount
        ColumnStr deByte ndex norms ndex =
             ndexSeg ntWr er.seg ntData.createNorm ndex(f eldNa );
         f (norms ndex != null) {
          norms ndex.setValue(doc d, (byte)  ndexSeg ntWr er.s m lar y.computeNorm(state));
        }
      }
    }

    /**  nverts one f eld for one docu nt; f rst  s true
     *   f t   s t  f rst t     are see ng t  f eld
     *  na   n t  docu nt. */
    publ c vo d  nvert( ndexableF eld f eld,
                        nt doc d,
                       boolean f rst,
                       boolean currentDoc sOffens ve) throws  OExcept on {
       f ( ndexWr er == null) {
        return;
      }
       f (f rst) {
        currentPos  on = -1;
        currentOffset = 0;
        lastPos  on = 0;
        lastStartOffset = 0;

         f ( nvertedF eld != null) {
           nvertedF eld. ncre ntNumDocs();
        }
      }

       ndexableF eldType f eldType = f eld.f eldType();
      f nal boolean analyzed = f eldType.token zed() &&  ndexSeg ntWr er.analyzer != null;
      boolean succeeded nProcess ngF eld = false;
      try {
        tokenStream = f eld.tokenStream( ndexSeg ntWr er.analyzer, tokenStream);
        tokenStream.reset();

        Pos  on ncre ntAttr bute pos ncrAttr bute =
            tokenStream.addAttr bute(Pos  on ncre ntAttr bute.class);
        OffsetAttr bute offsetAttr bute = tokenStream.addAttr bute(OffsetAttr bute.class);
        TermToBytesRefAttr bute termAtt = tokenStream.addAttr bute(TermToBytesRefAttr bute.class);

        Set<BytesRef> seenTerms = new HashSet<>();
         ndexWr er.start(tokenStream, currentDoc sOffens ve);
        wh le (tokenStream. ncre ntToken()) {
          //  f   h  an except on  n stream.next below
          // (wh ch  s fa rly common, e.g.  f analyzer
          // chokes on a g ven docu nt), t n  's
          // non-abort ng and (above) t  one docu nt
          // w ll be marked as deleted, but st ll
          // consu  a doc D

           nt pos ncr = pos ncrAttr bute.getPos  on ncre nt();
          currentPos  on += pos ncr;
           f (currentPos  on < lastPos  on) {
             f (pos ncr == 0) {
              throw new  llegalArgu ntExcept on(
                  "f rst pos  on  ncre nt must be > 0 (got 0) for f eld '" + f eld.na () + "'");
            } else  f (pos ncr < 0) {
              throw new  llegalArgu ntExcept on(
                  "pos  on  ncre nts (and gaps) must be >= 0 (got " + pos ncr + ") for f eld '"
                  + f eld.na () + "'");
            } else {
              throw new  llegalArgu ntExcept on(
                  "pos  on overflo d  nteger.MAX_VALUE (got pos ncr=" + pos ncr + " lastPos  on="
                  + lastPos  on + " pos  on=" + currentPos  on + ") for f eld '" + f eld.na ()
                  + "'");
            }
          } else  f (currentPos  on > MAX_POS T ON) {
            throw new  llegalArgu ntExcept on(
                "pos  on " + currentPos  on + "  s too large for f eld '" + f eld.na ()
                + "': max allo d pos  on  s " + MAX_POS T ON);
          }
          lastPos  on = currentPos  on;
           f (pos ncr == 0) {
            currentOverlap++;
          }

           nt startOffset = currentOffset + offsetAttr bute.startOffset();
           nt endOffset = currentOffset + offsetAttr bute.endOffset();
           f (startOffset < lastStartOffset || endOffset < startOffset) {
            throw new  llegalArgu ntExcept on(
                "startOffset must be non-negat ve, and endOffset must be >= startOffset, and "
                + "offsets must not go backwards startOffset=" + startOffset + ",endOffset="
                + endOffset + ",lastStartOffset=" + lastStartOffset + " for f eld '" + f eld.na ()
                + "'");
          }
          lastStartOffset = startOffset;
           ndexWr er.add(doc d, currentPos  on);
          currentLength++;

          BytesRef term = termAtt.getBytesRef();
           f (seenTerms.add(term) && ( nvertedF eld != null)) {
             nvertedF eld. ncre ntSumTermDocFreq();
          }
        }

        tokenStream.end();

        currentPos  on += pos ncrAttr bute.getPos  on ncre nt();
        currentOffset += offsetAttr bute.endOffset();
        succeeded nProcess ngF eld = true;
      } catch (BytesRefHash.MaxBytesLengthExceededExcept on e) {
        byte[] pref x = new byte[30];
        BytesRef b gTerm = tokenStream.getAttr bute(TermToBytesRefAttr bute.class).getBytesRef();
        System.arraycopy(b gTerm.bytes, b gTerm.offset, pref x, 0, 30);
        Str ng msg = "Docu nt conta ns at least one  m nse term  n f eld=\"" + f eldNa 
                + "\" (whose UTF8 encod ng  s longer than t  max length), all of "
                + "wh ch  re sk pped." + "Please correct t  analyzer to not produce such terms. "
                + "T  pref x of t  f rst  m nse term  s: '" + Arrays.toStr ng(pref x)
                + "...', or g nal  ssage: " + e.get ssage();
        LOG.warn(msg);
        // Docu nt w ll be deleted above:
        throw new  llegalArgu ntExcept on(msg, e);
      } f nally {
         f (!succeeded nProcess ngF eld) {
          LOG.warn("An except on was thrown wh le process ng f eld " + f eldNa );
        }
         f (tokenStream != null) {
          try {
            tokenStream.close();
          } catch ( OExcept on e) {
             f (succeeded nProcess ngF eld) {
              // only throw t  except on  f no ot r except on already occurred above
              throw e;
            } else {
              LOG.warn("Except on wh le try ng to close TokenStream.", e);
            }
          }
        }
      }

       f (analyzed) {
        currentPos  on +=  ndexSeg ntWr er.analyzer.getPos  on ncre ntGap(f eldNa );
        currentOffset +=  ndexSeg ntWr er.analyzer.getOffsetGap(f eldNa );
      }
    }
  }

  @Overr de
  publ c  nt numDocs() {
    return seg ntData.getDoc DToT et DMapper().getNumDocs();
  }

  publ c  nterface  nvertedDocConsu r {
    /**
     * Called for each docu nt before  nvers on starts.
     */
    vo d start(Attr buteS ce attr buteS ce, boolean currentDoc sOffens ve);

    /**
     * Called for each token  n t  current docu nt.
     * @param doc D Docu nt  d.
     * @param pos  on Pos  on  n t  token stream for t  docu nt.
     */
    vo d add( nt doc D,  nt pos  on) throws  OExcept on;

    /**
     * Called after t  last token was added and before t  next docu nt  s processed.
     */
    vo d f n sh();
  }

  publ c  nterface StoredF eldsConsu r {
    /**
     * Adds a new stored f elds.
     */
    vo d addF eld( nt doc D,  ndexableF eld f eld) throws  OExcept on;
  }

  /**
   * T  Bu lder allows reg ster ng l steners for a part cular f eld of an  ndexable docu nt.
   * For each f eld na  any number of l steners can be added.
   *
   * Us ng {@l nk #useDefaultConsu r}   can be spec f ed w t r t   ndex wr er w ll use
   * t  default consu r  n add  on to any add  onally reg stered consu rs.
   */
  publ c abstract stat c class Consu rBu lder<T> {
    pr vate boolean useDefaultConsu r;
    pr vate f nal L st<T> consu rs;
    pr vate f nal Earlyb rdF eldType f eldType;
    pr vate f nal Str ng f eldNa ;

    pr vate Consu rBu lder(Str ng f eldNa , Earlyb rdF eldType f eldType) {
      useDefaultConsu r = true;
      consu rs = L sts.newArrayL st();
      t .f eldNa  = f eldNa ;
      t .f eldType = f eldType;
    }

    publ c Str ng getF eldNa () {
      return f eldNa ;
    }

    publ c Earlyb rdF eldType getF eldType() {
      return f eldType;
    }

    /**
     *  f set to true, {@l nk Earlyb rdRealt   ndexSeg ntWr er} w ll use t  default consu r
     * (e.g. bu ld a default  nverted  ndex for an  nverted f eld)  n add  on to any consu rs
     * added v a {@l nk #addConsu r(Object)}.
     */
    publ c vo d setUseDefaultConsu r(boolean useDefaultConsu r) {
      t .useDefaultConsu r = useDefaultConsu r;
    }

    publ c boolean  sUseDefaultConsu r() {
      return useDefaultConsu r;
    }

    /**
     * Allows reg ster ng any number of add  onal consu rs for t  f eld assoc ated w h t 
     * bu lder.
     */
    publ c vo d addConsu r(T consu r) {
      consu rs.add(consu r);
    }

    T bu ld() {
       f (consu rs. sEmpty()) {
        return null;
      } else  f (consu rs.s ze() == 1) {
        return consu rs.get(0);
      } else {
        return bu ld(consu rs);
      }
    }

    abstract T bu ld(L st<T> consu rL st);
  }

  publ c stat c f nal class StoredF eldsConsu rBu lder
          extends Consu rBu lder<StoredF eldsConsu r> {
    pr vate StoredF eldsConsu rBu lder(Str ng f eldNa , Earlyb rdF eldType f eldType) {
      super(f eldNa , f eldType);
    }

    @Overr de
    StoredF eldsConsu r bu ld(f nal L st<StoredF eldsConsu r> consu rs) {
      return (doc D, f eld) -> {
        for (StoredF eldsConsu r consu r : consu rs) {
          consu r.addF eld(doc D, f eld);
        }
      };
    }
  }

  publ c stat c f nal class  nvertedDocConsu rBu lder
      extends Consu rBu lder< nvertedDocConsu r> {
    pr vate f nal Earlyb rd ndexSeg ntData seg ntData;

    pr vate  nvertedDocConsu rBu lder(
        Earlyb rd ndexSeg ntData seg ntData, Str ng f eldNa , Earlyb rdF eldType f eldType) {
      super(f eldNa , f eldType);
      t .seg ntData = seg ntData;
    }

    @Overr de
     nvertedDocConsu r bu ld(f nal L st< nvertedDocConsu r> consu rs) {
      return new  nvertedDocConsu r() {
        @Overr de
        publ c vo d start(Attr buteS ce attr buteS ce, boolean currentDoc sOffens ve) {
          for ( nvertedDocConsu r consu r : consu rs) {
            consu r.start(attr buteS ce, currentDoc sOffens ve);
          }
        }

        @Overr de
        publ c vo d f n sh() {
          for ( nvertedDocConsu r consu r : consu rs) {
            consu r.f n sh();
          }
        }

        @Overr de
        publ c vo d add( nt doc D,  nt pos  on) throws  OExcept on {
          for ( nvertedDocConsu r consu r : consu rs) {
            consu r.add(doc D, pos  on);
          }
        }
      };
    }

    publ c Earlyb rd ndexSeg ntData getSeg ntData() {
      return seg ntData;
    }
  }

  /**
   * Returns true,  f a f eld should not be  ndexed.
   * @deprecated T  wr er should be able to process all f elds  n t  future.
   */
  @Deprecated
  pr vate stat c boolean sk pF eld(Str ng f eldNa ) {
    //  gnore lucene facet f elds for realt    ndex,   are handl ng   d fferently for now.
    return f eldNa .startsW h(FacetsConf g.DEFAULT_ NDEX_F ELD_NAME);
  }

  pr vate stat c F eld bu ldAllDocsF eld(Earlyb rdRealt   ndexSeg ntData seg ntData) {
    Str ng f eldNa  = Earlyb rdF eldConstants.Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa ();
     f (seg ntData.getSc ma().hasF eld(f eldNa )) {
      Sc ma.F eld nfo f  = Precond  ons.c ckNotNull(
          seg ntData.getSc ma().getF eld nfo(f eldNa ));
      return new F eld(f .getNa (), AllDocs erator.ALL_DOCS_TERM, f .getF eldType());
    }

    return null;
  }

  /**
   * Every docu nt must have t  f eld and term, so that   can safely  erate through docu nts
   * us ng {@l nk AllDocs erator}. T   s to prevent t  problem of add ng a t et to t  doc  D
   * mapper, and return ng   for a match-all query w n t  rest of t  docu nt hasn't been
   * publ s d. T  could lead to quer es return ng  ncorrect results for quer es that are only
   * negat ons.
   * */
  pr vate vo d addAllDocsF eld(Docu nt doc) {
     f (allDocsF eld != null) {
      doc.add(allDocsF eld);
    }
  }
}
