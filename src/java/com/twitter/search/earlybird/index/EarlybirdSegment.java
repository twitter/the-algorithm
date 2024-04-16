package com.tw ter.search.earlyb rd. ndex;

 mport java. o.Closeable;
 mport java. o.F le;
 mport java. o. OExcept on;
 mport java.t  . nstant;
 mport java.t  .ZoneOffset;
 mport java.t  .ZonedDateT  ;
 mport java.t  .format.DateT  Formatter;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Objects;
 mport java.ut l.concurrent.atom c.Atom cReference;
 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.HashBasedTable;
 mport com.google.common.collect.Table;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;

 mport org.apac .commons. o.F leUt ls;
 mport org.apac .lucene.docu nt.Docu nt;
 mport org.apac .lucene. ndex.D rectoryReader;
 mport org.apac .lucene. ndex. ndexWr erConf g;
 mport org.apac .lucene. ndex. ndexableF eld;
 mport org.apac .lucene.store.D rectory;
 mport org.apac .lucene.store.FSD rectory;
 mport org.apac .lucene.store. OContext;
 mport org.apac .lucene.store. ndexOutput;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.collect ons.Pa r;
 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.sc ma.base.FeatureConf gurat on;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.base.Thr ftDocu ntUt l;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdEncodedFeatures;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdEncodedFeaturesUt l;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftDocu nt;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftF eld;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEvent;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEventType;
 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntData;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntWr er;
 mport com.tw ter.search.core.earlyb rd. ndex.column.ColumnStr deF eld ndex;
 mport com.tw ter.search.core.earlyb rd. ndex.column.DocValuesUpdate;
 mport com.tw ter.search.core.earlyb rd. ndex.extens ons.Earlyb rd ndexExtens onsFactory;
 mport com.tw ter.search.earlyb rd.Earlyb rd ndexConf g;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.docu nt.T etDocu nt;
 mport com.tw ter.search.earlyb rd.except on.FlushVers onM smatchExcept on;
 mport com.tw ter.search.earlyb rd.part  on.Search ndex ng tr cSet;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt ndexStats;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;
 mport com.tw ter.snowflake. d.Snowflake d;

publ c class Earlyb rdSeg nt {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdSeg nt.class);
  pr vate stat c f nal Logger UPDATES_ERRORS_LOG =
      LoggerFactory.getLogger(Earlyb rdSeg nt.class.getNa () + ".UpdatesErrors");
  pr vate stat c f nal Str ng SUCCESS_F LE = "EARLYB RD_SUCCESS";
  pr vate stat c f nal DateT  Formatter HOURLY_COUNT_DATE_T ME_FORMATTER =
      DateT  Formatter.ofPattern("yyyy_MM_dd_HH");

  @V s bleForTest ng
  publ c stat c f nal Str ng NUM_TWEETS_CREATED_AT_PATTERN = "num_t ets_%s_%s_created_at_%s";

  pr vate stat c f nal Str ng  NVAL D_FEATURE_UPDATES_DROPPED_PREF X =
      " nval d_ ndex_feature_update_dropped_";

  // T  number of t ets not  ndexed because t y have been prev ously  ndexed.
  pr vate stat c f nal SearchCounter DUPL CATE_TWEET_SK PPED_COUNTER =
      SearchCounter.export("dupl cate_t et_sk pped");

  // T  number of t ets that ca  out of order.
  pr vate stat c f nal SearchCounter OUT_OF_ORDER_TWEET_COUNTER =
      SearchCounter.export("out_of_order_t et");

  // T  number part al updates dropped because t  f eld could not be found  n t  sc ma.
  // T  counter  s  ncre nted once per f eld rat r than once per part al update event.
  // Note: caller may retry update, t  counter w ll be  ncre nted mult ple t  s for sa  update.
  pr vate stat c f nal SearchCounter  NVAL D_F ELDS_ N_PART AL_UPDATES =
      SearchCounter.export(" nval d_f elds_ n_part al_updates");

  // T  number part al updates dropped because t  t et  d could not be found  n t  seg nt.
  // Note: caller may retry update, t  counter w ll be  ncre nted mult ple t  s for sa  update.
  pr vate stat c f nal SearchCounter PART AL_UPDATE_FOR_TWEET_NOT_ N_ NDEX =
      SearchCounter.export("part al_update_for_t et_ d_not_ n_ ndex");

  // T  number of part al updates that  re appl ed only part ally, because t  update could not
  // be appl ed for at least one of t  f elds.
  pr vate stat c f nal SearchCounter PART AL_UPDATE_PART AL_FA LURE =
      SearchCounter.export("part al_update_part al_fa lure");

  // Both t   ndex ng cha n and t   ndex wr er are laz ly  n  al zed w n add ng docs for
  // t  f rst t  .
  pr vate f nal Atom cReference<Earlyb rd ndexSeg ntWr er> seg ntWr erReference =
      new Atom cReference<>();

  // Stats from t  Part  on ndexer / S mpleSeg nt ndexer.
  pr vate f nal Seg nt ndexStats  ndexStats;
  pr vate f nal Str ng seg ntNa ;
  pr vate f nal  nt maxSeg ntS ze;
  pr vate f nal long t  Sl ce D;
  pr vate f nal Atom cReference<Earlyb rd ndexSeg ntAtom cReader> lucene ndexReader =
      new Atom cReference<>();
  pr vate f nal D rectory luceneD r;
  pr vate f nal F le luceneD rF le;
  pr vate f nal Earlyb rd ndexConf g  ndexConf g;
  pr vate f nal L st<Closeable> closableRes ces = L sts.newArrayL st();
  pr vate long last nOrderT et d = 0;

  pr vate f nal Earlyb rd ndexExtens onsFactory extens onsFactory;
  pr vate f nal Search ndex ng tr cSet search ndex ng tr cSet;
  pr vate f nal Earlyb rdSearc rStats searc rStats;

  pr vate f nal Map<Str ng, SearchCounter>  ndexedT etsCounters = Maps.newHashMap();
  pr vate f nal PerF eldCounters perF eldCounters;
  pr vate f nal Clock clock;

  @V s bleForTest ng
  publ c volat le boolean appendedLucene ndex = false;

  publ c Earlyb rdSeg nt(
      Str ng seg ntNa ,
      long t  Sl ce D,
       nt maxSeg ntS ze,
      D rectory luceneD r,
      Earlyb rd ndexConf g  ndexConf g,
      Search ndex ng tr cSet search ndex ng tr cSet,
      Earlyb rdSearc rStats searc rStats,
      Clock clock) {
    t .seg ntNa  = seg ntNa ;
    t .maxSeg ntS ze = maxSeg ntS ze;
    t .t  Sl ce D = t  Sl ce D;
    t .luceneD r = luceneD r;
    t . ndexConf g =  ndexConf g;
    t . ndexStats = new Seg nt ndexStats();
    t .perF eldCounters = new PerF eldCounters();
    t .extens onsFactory = new T etSearch ndexExtens onsFactory();

     f (luceneD r != null && luceneD r  nstanceof FSD rectory) {
      // getD rectory() throws  f t  luceneD r  s already closed.
      // To delete a d rectory,   need to close   f rst.
      // Obta n a reference to t  F le now, so   can delete   later.
      // See SEARCH-5281
      t .luceneD rF le = ((FSD rectory) luceneD r).getD rectory().toF le();
    } else {
      t .luceneD rF le = null;
    }
    t .search ndex ng tr cSet = Precond  ons.c ckNotNull(search ndex ng tr cSet);
    t .searc rStats = searc rStats;
    t .clock = clock;
  }

  @V s bleForTest ng
  publ c D rectory getLuceneD rectory() {
    return luceneD r;
  }

  publ c Seg nt ndexStats get ndexStats() {
    return  ndexStats;
  }

  /**
   * Returns t  smallest t et  D  n t  seg nt.  f t  seg nt  s not loaded yet, or  s empty,
   * Doc DToT et DMapper. D_NOT_FOUND  s returned (-1).
   *
   * @return T  smallest t et  D  n t  seg nt.
   */
  publ c long getLo stT et d() {
    Earlyb rd ndexSeg ntWr er seg ntWr er = seg ntWr erReference.get();
     f (seg ntWr er == null) {
      return Doc DToT et DMapper. D_NOT_FOUND;
    }

    Doc DToT et DMapper mapper = seg ntWr er.getSeg ntData().getDoc DToT et DMapper();
     nt h g stDoc D = mapper.getPrev ousDoc D( nteger.MAX_VALUE);
    return mapper.getT et D(h g stDoc D);
  }

  /**
   * Returns t  card nal y (s ze) sum of t  card nal y of each
   * query cac  set.
   */
  publ c long getQueryCac sCard nal y() {
    Earlyb rd ndexSeg ntWr er wr er = get ndexSeg ntWr er();
     f (wr er == null) {
      // T  seg nt  s not loaded yet, or t  query cac s for t  seg nt are not bu lt yet.
      return -1;
    }

    Earlyb rd ndexSeg ntData earlyb rd ndexSeg ntData = wr er.getSeg ntData();
    return earlyb rd ndexSeg ntData.getQueryCac sCard nal y();
  }

  publ c L st<Pa r<Str ng, Long>> getQueryCac sData() {
    return get ndexSeg ntWr er().getSeg ntData().getPerQueryCac Card nal y();
  }


  /**
   * Returns t  h g st t et  D  n t  seg nt.  f t  seg nt  s not loaded yet, or  s empty,
   * Doc DToT et DMapper. D_NOT_FOUND  s returned (-1).
   *
   * @return T  h g st t et  D  n t  seg nt.
   */
  publ c long getH g stT et d() {
    Earlyb rd ndexSeg ntWr er seg ntWr er = seg ntWr erReference.get();
     f (seg ntWr er == null) {
      return Doc DToT et DMapper. D_NOT_FOUND;
    }

    Doc DToT et DMapper mapper = seg ntWr er.getSeg ntData().getDoc DToT et DMapper();
     nt lo stDoc D = mapper.getNextDoc D(-1);
    return mapper.getT et D(lo stDoc D);
  }

  /**
   * Opt m zes t  underly ng seg nt data.
   */
  publ c vo d opt m ze ndexes() throws  OExcept on {
    Earlyb rd ndexSeg ntWr er unopt m zedWr er = seg ntWr erReference.get();
    Precond  ons.c ckNotNull(unopt m zedWr er);

    unopt m zedWr er.force rge();
    unopt m zedWr er.close();

    // Opt m ze   own data structures  n t   ndex ng cha n
    //  n t  arch ve t   s pretty much a no-op.
    // T   ndexWr er  n wr eableSeg nt should no longer be used and referenced, and
    // wr eableSeg nt.wr er can be garbage collected at t  po nt.
    Earlyb rd ndexSeg ntData opt m zed =  ndexConf g.opt m ze(unopt m zedWr er.getSeg ntData());
    resetSeg ntWr erReference(newWr eableSeg nt(opt m zed), true);

    addSuccessF le();
  }

  /**
   * Returns a new, opt m zed, realt   seg nt, by copy ng t  data  n t  seg nt.
   */
  publ c Earlyb rdSeg nt makeOpt m zedSeg nt() throws  OExcept on {
    Earlyb rd ndexSeg ntWr er unopt m zedWr er = seg ntWr erReference.get();
    Precond  ons.c ckNotNull(unopt m zedWr er);
    Earlyb rdSeg nt opt m zedSeg nt = new Earlyb rdSeg nt(
        seg ntNa ,
        t  Sl ce D,
        maxSeg ntS ze,
        luceneD r,
         ndexConf g,
        search ndex ng tr cSet,
        searc rStats,
        clock);

    Earlyb rd ndexSeg ntData opt m zedSeg ntData =
         ndexConf g.opt m ze(unopt m zedWr er.getSeg ntData());
    LOG. nfo("Done opt m z ng, sett ng seg nt data");

    opt m zedSeg nt.setSeg ntData(
        opt m zedSeg ntData,
         ndexStats.getPart alUpdateCount(),
         ndexStats.getOutOfOrderUpdateCount());
    return opt m zedSeg nt;
  }

  publ c Str ng getSeg ntNa () {
    return seg ntNa ;
  }

  publ c boolean  sOpt m zed() {
    Earlyb rd ndexSeg ntWr er seg ntWr er = seg ntWr erReference.get();
    return seg ntWr er != null && seg ntWr er.getSeg ntData(). sOpt m zed();
  }

  /**
   * Removes t  docu nt for t  g ven t et  D from t  seg nt,  f t  seg nt conta ns a
   * docu nt for t  t et  D.
   */
  publ c boolean delete(long t et D) throws  OExcept on {
    Earlyb rd ndexSeg ntWr er seg ntWr er = seg ntWr erReference.get();
     f (!hasDocu nt(t et D)) {
      return false;
    }

    seg ntWr er.deleteDocu nts(new T et DQuery(t et D));
    return true;
  }

  protected vo d updateDocValues(long t et D, Str ng f eld, DocValuesUpdate update)
      throws  OExcept on {
    Earlyb rd ndexSeg ntWr er seg ntWr er = seg ntWr erReference.get();
    seg ntWr er.updateDocValues(new T et DQuery(t et D), f eld, update);
  }

  /**
   * Appends t  Lucene  ndex from anot r seg nt to t  seg nt.
   */
  publ c vo d append(Earlyb rdSeg nt ot rSeg nt) throws  OExcept on {
     f ( ndexConf g. s ndexStoredOnD sk()) {
      Earlyb rd ndexSeg ntWr er seg ntWr er = seg ntWr erReference.get();
      Precond  ons.c ckNotNull(seg ntWr er);
      Earlyb rd ndexSeg ntWr er ot rSeg ntWr er = ot rSeg nt.seg ntWr erReference.get();
       f (ot rSeg ntWr er != null) {
        ot rSeg ntWr er.close();
      }
      seg ntWr er.add ndexes(ot rSeg nt.luceneD r);
      LOG. nfo("Call ng force rge now after append ng seg nt.");
      seg ntWr er.force rge();
      appendedLucene ndex = true;
      LOG. nfo("Appended {} docs to seg nt {}. New doc count = {}",
               ot rSeg nt. ndexStats.getStatusCount(), luceneD r.toStr ng(),
                ndexStats.getStatusCount());

       ndexStats.set ndexS zeOnD sk nBytes(getSeg ntS zeOnD sk());
    }
  }

  /**
   * Only needed for t  on d sk arch ve.
   * Creates Tw ter ndexReader used for search ng. T   s shared by all Searc rs.
   * T   thod also  n  al zes t  Lucene based mappers and CSF for t  on d sk arch ve.
   *
   * T   thod should be called after opt m z ng/load ng a seg nt, but before t  seg nt starts
   * to serve search quer es.
   */
  publ c vo d warmSeg nt() throws  OExcept on {
    Earlyb rd ndexSeg ntWr er seg ntWr er = seg ntWr erReference.get();
    Precond  ons.c ckNotNull(seg ntWr er);

    // only need to pre-create reader and  n  al ze mappers and CSF  n t  on d sk arch ve cluster
     f ( ndexConf g. s ndexStoredOnD sk() && lucene ndexReader.get() == null) {
      Earlyb rd ndexSeg ntAtom cReader luceneAtom cReader =
          seg ntWr er.getSeg ntData().createAtom cReader();

      lucene ndexReader.set(luceneAtom cReader);
      closableRes ces.add(luceneAtom cReader);
      closableRes ces.add(luceneD r);
    }
  }

  /**
   * Create a t et  ndex searc r on t  seg nt.
   *
   * For product on search sess on, t  sc ma snapshot should be always passed  n to make sure
   * that t  sc ma usage  ns de scor ng  s cons stent.
   *
   * For non-product on usage, l ke one-off debugg ng search,   can use t  funct on call w hout
   * t  sc ma snapshot.
   */
  @Nullable
  publ c Earlyb rdS ngleSeg ntSearc r getSearc r(
      UserTable userTable,
       mmutableSc ma nterface sc maSnapshot) throws  OExcept on {
    Earlyb rd ndexSeg ntWr er seg ntWr er = seg ntWr erReference.get();
     f (seg ntWr er == null) {
      return null;
    }
    return new Earlyb rdS ngleSeg ntSearc r(
        sc maSnapshot, get ndexReader(seg ntWr er), userTable, searc rStats, clock);
  }

  /**
   * Returns a new searc r for t  seg nt.
   */
  @Nullable
  publ c Earlyb rdS ngleSeg ntSearc r getSearc r(
      UserTable userTable) throws  OExcept on {
    Earlyb rd ndexSeg ntWr er seg ntWr er = seg ntWr erReference.get();
     f (seg ntWr er == null) {
      return null;
    }
    return new Earlyb rdS ngleSeg ntSearc r(
        seg ntWr er.getSeg ntData().getSc ma().getSc maSnapshot(),
        get ndexReader(seg ntWr er),
        userTable,
        searc rStats,
        clock);
  }

  /**
   * Returns a new reader for t  seg nt.
   */
  @Nullable
  publ c Earlyb rd ndexSeg ntAtom cReader get ndexReader() throws  OExcept on {
    Earlyb rd ndexSeg ntWr er seg ntWr er = seg ntWr erReference.get();
     f (seg ntWr er == null) {
      return null;
    }
    return get ndexReader(seg ntWr er);
  }

  pr vate Earlyb rd ndexSeg ntAtom cReader get ndexReader(
      Earlyb rd ndexSeg ntWr er seg ntWr er
  ) throws  OExcept on {
    Earlyb rd ndexSeg ntAtom cReader reader = lucene ndexReader.get();
     f (reader != null) {
      return reader;
    }
    Precond  ons.c ckState(! ndexConf g. s ndexStoredOnD sk());

    // Realt   EB mode.
    return seg ntWr er.getSeg ntData().createAtom cReader();
  }

  /**
   * Gets max t et  d  n t  seg nt.
   *
   * @return t  t et  d or -1  f not found.
   */
  publ c long getMaxT et d() {
    Earlyb rd ndexSeg ntWr er seg ntWr er = seg ntWr erReference.get();
     f (seg ntWr er == null) {
      return -1;
    } else {
      T et DMapper t et DMapper =
          (T et DMapper) seg ntWr er.getSeg ntData().getDoc DToT et DMapper();
      return t et DMapper.getMaxT et D();
    }
  }

  pr vate Earlyb rd ndexSeg ntWr er newWr eableSeg nt(Earlyb rd ndexSeg ntData seg ntData)
      throws  OExcept on {
    Earlyb rd ndexSeg ntWr er old = seg ntWr erReference.get();
     f (old != null) {
      old.close();
    }

    LOG. nfo("Creat ng new seg nt wr er for {} on {}", seg ntNa , luceneD r);
     ndexWr erConf g  ndexWr erConf g =  ndexConf g.new ndexWr erConf g();
    return seg ntData.createEarlyb rd ndexSeg ntWr er( ndexWr erConf g);
  }

  pr vate vo d resetSeg ntWr erReference(
      Earlyb rd ndexSeg ntWr er seg ntWr er, boolean prev ousSeg ntWr erAllo d) {
    Earlyb rd ndexSeg ntWr er prev ousSeg ntWr er =
        seg ntWr erReference.getAndSet(seg ntWr er);
     f (!prev ousSeg ntWr erAllo d) {
      Precond  ons.c ckState(
          prev ousSeg ntWr er == null,
          "A prev ous seg nt wr er must have been set for seg nt " + seg ntNa );
    }

    // Reset t  stats for t  number of  ndexed t ets per h  and recompute t m.
    // See SEARCH-23619
    for (SearchCounter  ndexedT etsCounter :  ndexedT etsCounters.values()) {
       ndexedT etsCounter.reset();
    }

     f (seg ntWr er != null) {
       ndexStats.setSeg ntData(seg ntWr er.getSeg ntData());

       f ( ndexConf g.getCluster() != Earlyb rdCluster.FULL_ARCH VE) {
         n H lyT etCounts(seg ntWr erReference.get());
      }
    } else {
      //  's  mportant to unset seg nt data so that t re are no references to  
      // and   can be GC-ed.
       ndexStats.unsetSeg ntDataAndSaveCounts();
    }
  }

  /**
   * Add a docu nt  f    s not already  n seg nt.
   */
  publ c vo d addDocu nt(T etDocu nt doc) throws  OExcept on {
     f ( ndexConf g. s ndexStoredOnD sk()) {
      addDocu ntToArch veSeg nt(doc);
    } else {
      addDocu ntToRealt  Seg nt(doc);
    }
  }

  pr vate vo d addDocu ntToArch veSeg nt(T etDocu nt doc) throws  OExcept on {
    // For arch ve, t  docu nt  d should co   n order, to drop dupl cates, only need to
    // compare current  d w h last one.
    long t et d = doc.getT et D();
     f (t et d == last nOrderT et d) {
      LOG.warn("Dropped dupl cate t et for arch ve: {}", t et d);
      DUPL CATE_TWEET_SK PPED_COUNTER. ncre nt();
      return;
    }

     f (t et d > last nOrderT et d && last nOrderT et d != 0) {
      // Arch ve orders docu nt from ne st to oldest, so t  shouldn't happen
      LOG.warn("Encountered out-of-order t et for arch ve: {}", t et d);
      OUT_OF_ORDER_TWEET_COUNTER. ncre nt();
    } else {
      last nOrderT et d = t et d;
    }

    addDocu nt nternal(doc);
  }

  pr vate vo d addDocu ntToRealt  Seg nt(T etDocu nt doc) throws  OExcept on {
    long t et d = doc.getT et D();
    boolean outOfOrder = t et d <= last nOrderT et d;
     f (outOfOrder) {
      OUT_OF_ORDER_TWEET_COUNTER. ncre nt();
    } else {
      last nOrderT et d = t et d;
    }

    //   only need to call hasDocu nt() for out-of-order t ets.
     f (outOfOrder && hasDocu nt(t et d)) {
      //   do get dupl cates so t  s so  'll see so  amount of t se.
      DUPL CATE_TWEET_SK PPED_COUNTER. ncre nt();
    } else {
      addDocu nt nternal(doc);
       ncre ntH lyT etCount(doc.getT et D());
    }
  }

  pr vate vo d addDocu nt nternal(T etDocu nt t etDocu nt) throws  OExcept on {
    Docu nt doc = t etDocu nt.getDocu nt();

    // Never wr e blank docu nts  nto t   ndex.
     f (doc == null || doc.getF elds() == null || doc.getF elds().s ze() == 0) {
      return;
    }

    Earlyb rd ndexSeg ntWr er seg ntWr er = seg ntWr erReference.get();
     f (seg ntWr er == null) {
      Earlyb rd ndexSeg ntData seg ntData =  ndexConf g.newSeg ntData(
          maxSeg ntS ze,
          t  Sl ce D,
          luceneD r,
          extens onsFactory);
      seg ntWr er = newWr eableSeg nt(seg ntData);
      resetSeg ntWr erReference(seg ntWr er, false);
    }

    Precond  ons.c ckState(seg ntWr er.numDocs() < maxSeg ntS ze,
                             "Reac d max seg nt s ze %s", maxSeg ntS ze);

     ndexableF eld[] featuresF eld = doc.getF elds(
        Earlyb rdF eldConstants.ENCODED_TWEET_FEATURES_F ELD_NAME);
    Precond  ons.c ckState(featuresF eld.length == 1,
            "featuresF eld.length should be 1, but  s %s", featuresF eld.length);

    //   requ re t  createdAt f eld to be set so   can properly f lter t ets based on t  .
     ndexableF eld[] createdAt =
        doc.getF elds(Earlyb rdF eldConstant.CREATED_AT_F ELD.getF eldNa ());
    Precond  ons.c ckState(createdAt.length == 1);

    Earlyb rdEncodedFeatures features = Earlyb rdEncodedFeaturesUt l.fromBytes(
         ndexConf g.getSc ma().getSc maSnapshot(),
        Earlyb rdF eldConstant.ENCODED_TWEET_FEATURES_F ELD,
        featuresF eld[0].b naryValue().bytes,
        featuresF eld[0].b naryValue().offset);
    boolean currentDoc sOffens ve = features. sFlagSet(Earlyb rdF eldConstant. S_OFFENS VE_FLAG);
    perF eldCounters. ncre nt(Thr ft ndex ngEventType. NSERT, doc);
    seg ntWr er.addT et(doc, t etDocu nt.getT et D(), currentDoc sOffens ve);
  }

  pr vate vo d  ncre ntH lyT etCount(long t et d) {
    // SEARCH-23619,   won't attempt to  ncre nt t  count for pre-snowflake  Ds, s nce
    // extract ng an exact create t    s pretty tr cky at t  po nt, and t  stat  s mostly
    // useful for c ck ng realt   t et  ndex ng.
     f (Snowflake d. sSnowflake d(t et d)) {
      long t etCreateT   = Snowflake d.un xT  M ll sFrom d(t et d);
      Str ng t etH  = HOURLY_COUNT_DATE_T ME_FORMATTER.format(
          ZonedDateT  .of nstant( nstant.ofEpochM ll (t etCreateT  ), ZoneOffset.UTC));

      Str ng seg ntOpt m zedSuff x =  sOpt m zed() ? "opt m zed" : "unopt m zed";
      SearchCounter  ndexedT etsCounter =  ndexedT etsCounters.compute fAbsent(
          t etH  + "_" + seg ntOpt m zedSuff x,
          (t etH Key) -> SearchCounter.export(Str ng.format(
              NUM_TWEETS_CREATED_AT_PATTERN, seg ntOpt m zedSuff x, seg ntNa , t etH )));
       ndexedT etsCounter. ncre nt();
    }
  }

  pr vate vo d  n H lyT etCounts(Earlyb rd ndexSeg ntWr er seg ntWr er) {
    Doc DToT et DMapper mapper = seg ntWr er.getSeg ntData().getDoc DToT et DMapper();
     nt doc d =  nteger.M N_VALUE;
    wh le ((doc d = mapper.getNextDoc D(doc d)) != Doc DToT et DMapper. D_NOT_FOUND) {
       ncre ntH lyT etCount(mapper.getT et D(doc d));
    }
  }

  /**
   * Adds t  g ven docu nt for t  g ven t et  D to t  seg nt, potent ally out of order.
   */
  publ c boolean appendOutOfOrder(Docu nt doc, long t et D) throws  OExcept on {
    // Never wr e blank docu nts  nto t   ndex.
     f (doc == null || doc.getF elds() == null || doc.getF elds().s ze() == 0) {
      return false;
    }

    Earlyb rd ndexSeg ntWr er seg ntWr er = seg ntWr erReference.get();
     f (seg ntWr er == null) {
      logAppendOutOfOrderFa lure(t et D, doc, "seg nt  s null");
      return false;
    }

     f (! ndexConf g.supportOutOfOrder ndex ng()) {
      logAppendOutOfOrderFa lure(t et D, doc, "out of order  ndex ng not supported");
      return false;
    }

     f (!hasDocu nt(t et D)) {
      logAppendOutOfOrderFa lure(t et D, doc, "t et  D  ndex lookup fa led");
      search ndex ng tr cSet.updateOnM ss ngT etCounter. ncre nt();
      perF eldCounters. ncre ntT etNot n ndex(Thr ft ndex ngEventType.OUT_OF_ORDER_APPEND, doc);
      return false;
    }

    perF eldCounters. ncre nt(Thr ft ndex ngEventType.OUT_OF_ORDER_APPEND, doc);
    seg ntWr er.appendOutOfOrder(new T et DQuery(t et D), doc);
     ndexStats. ncre ntOutOfOrderUpdateCount();
    return true;
  }

  pr vate vo d logAppendOutOfOrderFa lure(long t et D, Docu nt doc, Str ng reason) {
    UPDATES_ERRORS_LOG.debug(
        "appendOutOfOrder() fa led to apply update docu nt w h hash {} on t et  D {}: {}",
        Objects.hashCode(doc), t et D, reason);
  }

  /**
   * Determ nes  f t  seg nt conta ns t  g ven t et  D.
   */
  publ c boolean hasDocu nt(long t et D) throws  OExcept on {
    Earlyb rd ndexSeg ntWr er seg ntWr er = seg ntWr erReference.get();
     f (seg ntWr er == null) {
      return false;
    }

    return seg ntWr er.getSeg ntData().getDoc DToT et DMapper().getDoc D(t et D)
        != Doc DToT et DMapper. D_NOT_FOUND;
  }

  pr vate stat c f nal Str ng VERS ON_PROP_NAME = "vers on";
  pr vate stat c f nal Str ng VERS ON_DESC_PROP_NAME = "vers onDescr pt on";
  pr vate stat c f nal Str ng PART AL_UPDATES_COUNT = "part alUpdatesCount";
  pr vate stat c f nal Str ng OUT_OF_ORDER_UPDATES_COUNT = "outOfOrderUpdatesCount";

  pr vate vo d c ck fFlus dDataVers onMatc sExpected(Flush nfo flush nfo) throws  OExcept on {
     nt expectedVers onNumber =  ndexConf g.getSc ma().getMajorVers onNumber();
    Str ng expectedVers onDesc =  ndexConf g.getSc ma().getVers onDescr pt on();
     nt vers on = flush nfo.get ntProperty(VERS ON_PROP_NAME);
    f nal Str ng vers onDesc = flush nfo.getStr ngProperty(VERS ON_DESC_PROP_NAME);

     f (vers on != expectedVers onNumber) {
      throw new FlushVers onM smatchExcept on("Flus d vers on m smatch. Expected: "
          + expectedVers onNumber + ", but was: " + vers on);
    }

     f (!expectedVers onDesc.equals(vers onDesc)) {
      f nal Str ng  ssage = "Flush vers on " + expectedVers onNumber + "  s amb guous"
          + "  Expected: " + expectedVers onDesc
          + "  Found:  "  + vers onDesc
          + "  Please clean up seg nts w h bad flush vers on from HDFS and Earlyb rd local d sk.";
      throw new FlushVers onM smatchExcept on( ssage);
    }
  }

  /**
   * Loads t  seg nt data and propert es from t  g ven deser al zer and flush  nfo.
   *
   * @param  n T  deser al zer from wh ch t  seg nt's data w ll be read.
   * @param flush nfo T  flush  nfo from wh ch t  seg nt's propert es w ll be read.
   */
  publ c vo d load(DataDeser al zer  n, Flush nfo flush nfo) throws  OExcept on {
    c ck fFlus dDataVers onMatc sExpected(flush nfo);

     nt part alUpdatesCount = flush nfo.get ntProperty(PART AL_UPDATES_COUNT);
     nt outOfOrderUpdatesCount = flush nfo.get ntProperty(OUT_OF_ORDER_UPDATES_COUNT);

    Earlyb rd ndexSeg ntData loadedSeg ntData =  ndexConf g.loadSeg ntData(
        flush nfo,  n, luceneD r, extens onsFactory);

    setSeg ntData(loadedSeg ntData, part alUpdatesCount, outOfOrderUpdatesCount);
  }

  /**
   * Update t  data back ng t  Early rdSeg nt.
   */
  publ c vo d setSeg ntData(
      Earlyb rd ndexSeg ntData seg ntData,
       nt part alUpdatesCount,
       nt outOfOrderUpdatesCount) throws  OExcept on {
    resetSeg ntWr erReference(newWr eableSeg nt(seg ntData), false);
    try {
      warmSeg nt();
    } catch ( OExcept on e) {
      LOG.error("Fa led to create  ndexReader for seg nt {}. W ll destroy unreadable seg nt.",
          seg ntNa , e);
      destroy m d ately();
      throw e;
    }

    LOG. nfo("Start ng seg nt {} w h {} part al updates, {} out of order updates and {} deletes.",
        seg ntNa , part alUpdatesCount, outOfOrderUpdatesCount,  ndexStats.getDeleteCount());
     ndexStats.setPart alUpdateCount(part alUpdatesCount);
     ndexStats.setOutOfOrderUpdateCount(outOfOrderUpdatesCount);
     ndexStats.set ndexS zeOnD sk nBytes(getSeg ntS zeOnD sk());
  }

  /**
   * Flus s t  t  seg nt's propert es to t  g ven Flush nfo  nstance, and t  seg nt's data
   * to t  g ven DataSer al zer  nstance.
   *
   * @param flush nfo T  Flush nfo  nstance w re all seg nt propert es should be added.
   * @param out T  ser al zer to wh ch all seg nt data should be flus d.
   */
  publ c vo d flush(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
    flush nfo.add ntProperty(VERS ON_PROP_NAME,  ndexConf g.getSc ma().getMajorVers onNumber());
    flush nfo.addStr ngProperty(VERS ON_DESC_PROP_NAME,
         ndexConf g.getSc ma().getVers onDescr pt on());
    flush nfo.add ntProperty(PART AL_UPDATES_COUNT,  ndexStats.getPart alUpdateCount());
    flush nfo.add ntProperty(OUT_OF_ORDER_UPDATES_COUNT,  ndexStats.getOutOfOrderUpdateCount());
     f (seg ntWr erReference.get() == null) {
      LOG.warn("Seg nt wr er  s null. flush nfo: {}", flush nfo);
    } else  f (seg ntWr erReference.get().getSeg ntData() == null) {
      LOG.warn("Seg nt data  s null. seg nt wr er: {}, flush nfo: {}",
          seg ntWr erReference.get(), flush nfo);
    }
    seg ntWr erReference.get().getSeg ntData().flushSeg nt(flush nfo, out);
     ndexStats.set ndexS zeOnD sk nBytes(getSeg ntS zeOnD sk());
  }

  /**
   * C ck to see  f t  seg nt can be loaded from an on-d sk  ndex, and load    f   can be.
   *
   * T  should only be appl cable to t  current seg nt for t  on-d sk arch ve.  's not
   * fully flus d unt l  's full, but   do have a lucene  ndex on local d sk wh ch can be
   * used at startup (rat r than have to re ndex all t  current t  sl ce docu nts aga n).
   *
   *  f loaded, t   ndex reader w ll be pre-created, and t  seg nt w ll be marked as
   * opt m zed.
   *
   *  f t   ndex d rectory ex sts but   cannot be loaded, t   ndex d rectory w ll be deleted.
   *
   * @return true  f t   ndex ex sts on d sk, and was loaded.
   */
  publ c boolean tryToLoadEx st ng ndex() throws  OExcept on {
    Precond  ons.c ckState(seg ntWr erReference.get() == null);
     f ( ndexConf g. s ndexStoredOnD sk()) {
       f (D rectoryReader. ndexEx sts(luceneD r) && c ckSuccessF le()) {
        LOG. nfo(" ndex d rectory already ex sts for {} at {}", seg ntNa , luceneD r);

        // set t  opt m zed flag, s nce   don't need to opt m ze any more, and pre-create
        // t   ndex reader (for t  on-d sk  ndex opt m ze()  s a noop that just sets t 
        // opt m zed flag).
        Earlyb rd ndexSeg ntData earlyb rd ndexSeg ntData =  ndexConf g.newSeg ntData(
            maxSeg ntS ze,
            t  Sl ce D,
            luceneD r,
            extens onsFactory);
        Earlyb rd ndexSeg ntData opt m zedEarlyb rd ndexSeg ntData =
             ndexConf g.opt m ze(earlyb rd ndexSeg ntData);
        resetSeg ntWr erReference(newWr eableSeg nt(opt m zedEarlyb rd ndexSeg ntData), false);

        warmSeg nt();

        LOG. nfo("Used ex st ng lucene  ndex for {} w h {} docu nts",
                 seg ntNa ,  ndexStats.getStatusCount());

         ndexStats.set ndexS zeOnD sk nBytes(getSeg ntS zeOnD sk());

        return true;
      } else {
        // C ck  f t re  s an ex st ng lucene d r w hout a SUCCESS f le on d sk.
        //  f so,   w ll remove   and re ndex from scratch.
         f (moveFSD rectory fEx sts(luceneD r)) {
          // Throw  re to be cleaned up and retr ed by S mpleSeg nt ndexer.
          throw new  OExcept on("Found  nval d ex st ng lucene d rectory at: " + luceneD r);
        }
      }
    }
    return false;
  }

  /**
   * Part ally updates a docu nt w h t  f eld value(s) spec f ed by event.
   * Returns true  f all wr es  re successful and false  f one or more wr es fa l or  f
   * t et  d  sn't found  n t  seg nt.
   */
  publ c boolean applyPart alUpdate(Thr ft ndex ngEvent event) throws  OExcept on {
    Precond  ons.c ckArgu nt(event.getEventType() == Thr ft ndex ngEventType.PART AL_UPDATE);
    Precond  ons.c ckArgu nt(event. sSetU d());
    Precond  ons.c ckArgu nt(!Thr ftDocu ntUt l.hasDupl cateF elds(event.getDocu nt()));
     mmutableSc ma nterface sc maSnapshot =  ndexConf g.getSc ma().getSc maSnapshot();

    long t et d = event.getU d();
    Thr ftDocu nt doc = event.getDocu nt();

     f (!hasDocu nt(t et d)) {
      // no need to attempt f eld wr es, fa l early
      PART AL_UPDATE_FOR_TWEET_NOT_ N_ NDEX. ncre nt();
       perF eldCounters. ncre ntT etNot n ndex(
           Thr ft ndex ngEventType.PART AL_UPDATE, doc);
      return false;
    }

     nt  nval dF elds = 0;
    for (Thr ftF eld f eld : doc.getF elds()) {
      Str ng featureNa  = sc maSnapshot.getF eldNa (f eld.getF eldConf g d());
      FeatureConf gurat on featureConf g =
          sc maSnapshot.getFeatureConf gurat onByNa (featureNa );
       f (featureConf g == null) {
         NVAL D_F ELDS_ N_PART AL_UPDATES. ncre nt();
         nval dF elds++;
        cont nue;
      }

      perF eldCounters. ncre nt(Thr ft ndex ngEventType.PART AL_UPDATE, featureNa );

      updateDocValues(
          t et d,
          featureNa ,
          (docValues, doc D) -> updateFeatureValue(doc D, featureConf g, docValues, f eld));
    }

     f ( nval dF elds > 0 &&  nval dF elds != doc.getF eldsS ze()) {
      PART AL_UPDATE_PART AL_FA LURE. ncre nt();
    }

     f ( nval dF elds == 0) {
       ndexStats. ncre ntPart alUpdateCount();
    } else {
      UPDATES_ERRORS_LOG.warn("Fa led to apply update for t et D {}, found {}  nval d f elds: {}",
          t et d,  nval dF elds, event);
    }

    return  nval dF elds == 0;
  }

  @V s bleForTest ng
  stat c vo d updateFeatureValue( nt doc D,
                                 FeatureConf gurat on featureConf g,
                                 ColumnStr deF eld ndex docValues,
                                 Thr ftF eld updateF eld) {
     nt oldValue = Math.to ntExact(docValues.get(doc D));
     nt newValue = updateF eld.getF eldData().get ntValue();

     f (!featureConf g.val dateFeatureUpdate(oldValue, newValue)) {
      // Counter values can only  ncrease
      SearchCounter.export(
           NVAL D_FEATURE_UPDATES_DROPPED_PREF X + featureConf g.getNa ()). ncre nt();
    } else {
      docValues.setValue(doc D, newValue);
    }
  }

  /**
   * C cks  f t  prov ded d rectory ex sts and  s not empty,
   * and  f   does moves   out to a d ff d rectory for later  nspect on.
   * @param luceneD rectory t  d r to move  f   ex sts.
   * @return true  ff   found an ex st ng d rectory.
   */
  pr vate stat c boolean moveFSD rectory fEx sts(D rectory luceneD rectory) {
    Precond  ons.c ckState(luceneD rectory  nstanceof FSD rectory);
    F le d rectory = ((FSD rectory) luceneD rectory).getD rectory().toF le();
     f (d rectory != null && d rectory.ex sts() && d rectory.l st().length > 0) {
      // Save t  bad lucene  ndex by mov ng   out, for later  nspect on.
      F le movedD r = new F le(d rectory.getParent(),
          d rectory.getNa () + ".fa led." + System.currentT  M ll s());
      LOG.warn("Mov ng ex st ng non-successful  ndex for {} from {} to {}",
               luceneD rectory, d rectory, movedD r);
      boolean success = d rectory.rena To(movedD r);
       f (!success) {
        LOG.warn("Unable to rena  non-successful  ndex: {}", luceneD rectory);
      }
      return true;
    }
    return false;
  }

  /**
   * For t  on-d sk arch ve,  f    re able to successfully  rge and flush t  Lucene  ndex to
   * d sk,   mark   expl c ly w h a SUCCESS f le, so that   can be safely reused.
   */
  pr vate vo d addSuccessF le() throws  OExcept on {
     f ( ndexConf g. s ndexStoredOnD sk()) {
       ndexOutput successF le = luceneD r.createOutput(SUCCESS_F LE,  OContext.DEFAULT);
      successF le.close();
    }
  }

  /**
   * Returns t  current number of docu nts  n t  seg nt.
   */
  publ c  nt getNumDocs() throws  OExcept on {
    return  ndexStats.getStatusCount();
  }

  /**
   * Recla m res ces used by t  seg nt (E.g. clos ng lucene  ndex reader).
   * Res ces w ll be recla  d w h n t  call ng thread w h no delay.
   */
  publ c vo d destroy m d ately() {
    try {
      closeSeg ntWr er();
      maybeDeleteSeg ntOnD sk();
      unloadSeg ntFrom mory();
    } f nally {
       ndexConf g.getRes ceCloser().closeRes ces m d ately(closableRes ces);
    }
  }

  /**
   * Close t   n- mory res ces belong ng to t  seg nt. T  should allow t   n- mory
   * seg nt data to be garbage collected. After clos ng, t  seg nt  s not wr able.
   */
  publ c vo d close() {
     f (seg ntWr erReference.get() == null) {
      LOG. nfo("Seg nt {} already closed.", seg ntNa );
      return;
    }

    LOG. nfo("Clos ng seg nt {}.", seg ntNa );
    try {
      closeSeg ntWr er();
      unloadSeg ntFrom mory();
    } f nally {
       ndexConf g.getRes ceCloser().closeRes ces m d ately(closableRes ces);
    }
  }

  pr vate vo d closeSeg ntWr er() {
    Earlyb rd ndexSeg ntWr er seg ntWr er = seg ntWr erReference.get();
     f (seg ntWr er != null) {
      closableRes ces.add(() -> {
          LOG. nfo("Clos ng wr er for seg nt: {}", seg ntNa );
          seg ntWr er.close();
      });
    }
  }

  pr vate vo d maybeDeleteSeg ntOnD sk() {
     f ( ndexConf g. s ndexStoredOnD sk()) {
      Precond  ons.c ckState(
          luceneD r  nstanceof FSD rectory,
          "On-d sk  ndexes should have an underly ng d rectory that   can close and remove.");
      closableRes ces.add(luceneD r);

       f (luceneD rF le != null && luceneD rF le.ex sts()) {
        closableRes ces.add(new Closeable() {
          @Overr de
          publ c vo d close() throws  OExcept on {
            F leUt ls.deleteD rectory(luceneD rF le);
          }

          @Overr de
          publ c Str ng toStr ng() {
            return "delete {" + luceneD rF le + "}";
          }
        });
      }
    }
  }

  pr vate vo d unloadSeg ntFrom mory() {
    // Make sure   don't reta n a reference to t   ndexWr er or Seg ntData.
    resetSeg ntWr erReference(null, true);
  }

  pr vate long getSeg ntS zeOnD sk() throws  OExcept on {
    search ndex ng tr cSet.seg ntS zeC ckCount. ncre nt();

    long totalS ze = 0;
     f (luceneD r != null) {
      for (Str ng f le : luceneD r.l stAll()) {
        totalS ze += luceneD r.f leLength(f le);
      }
    }
    return totalS ze;
  }

  //////////////////////////
  // for un  tests only
  //////////////////////////

  publ c Earlyb rd ndexConf g getEarlyb rd ndexConf g() {
    return  ndexConf g;
  }

  @V s bleForTest ng
  publ c boolean c ckSuccessF le() {
    return new F le(luceneD rF le, SUCCESS_F LE).ex sts();
  }

  @V s bleForTest ng
  Earlyb rd ndexSeg ntWr er get ndexSeg ntWr er() {
    return seg ntWr erReference.get();
  }

  //  lper class to encapsulate counter tables, patterns and var ous ways to  ncre nt
  pr vate class PerF eldCounters {
    // T  number of update/append events for each f eld  n t  sc ma.
    pr vate stat c f nal Str ng PER_F ELD_EVENTS_COUNTER_PATTERN = "%s_for_f eld_%s";
    // T  number of dropped update/append events for each f eld due to t et d not found
    pr vate stat c f nal Str ng TWEET_NOT_ N_ NDEX_PER_F ELD_EVENTS_COUNTER_PATTERN =
        "%s_for_t et_ d_not_ n_ ndex_for_f eld_%s";
    pr vate f nal Table<Thr ft ndex ngEventType, Str ng, SearchCounter> perF eldTable =
        HashBasedTable.create();
    pr vate f nal Table<Thr ft ndex ngEventType, Str ng, SearchCounter> not n ndexPerF eldTable =
        HashBasedTable.create();

    publ c vo d  ncre nt(
        Thr ft ndex ngEventType eventType, Thr ftDocu nt doc) {
       mmutableSc ma nterface sc maSnapshot =  ndexConf g.getSc ma().getSc maSnapshot();
      for (Thr ftF eld f eld : doc.getF elds()) {
        Str ng f eldNa  = sc maSnapshot.getF eldNa (f eld.getF eldConf g d());
         ncre ntForPattern(
            eventType, f eldNa , perF eldTable, PER_F ELD_EVENTS_COUNTER_PATTERN);
      }
    }

    publ c vo d  ncre ntT etNot n ndex(
        Thr ft ndex ngEventType eventType, Thr ftDocu nt doc) {
       mmutableSc ma nterface sc maSnapshot =  ndexConf g.getSc ma().getSc maSnapshot();
      for (Thr ftF eld f eld : doc.getF elds()) {
        Str ng f eldNa  = sc maSnapshot.getF eldNa (f eld.getF eldConf g d());
         ncre ntForPattern(
            eventType, f eldNa , not n ndexPerF eldTable,
            TWEET_NOT_ N_ NDEX_PER_F ELD_EVENTS_COUNTER_PATTERN);
      }
    }

    publ c vo d  ncre nt(Thr ft ndex ngEventType eventType, Docu nt doc) {
      for ( ndexableF eld f eld : doc.getF elds()) {
         ncre ntForPattern(
            eventType, f eld.na (),
            perF eldTable, PER_F ELD_EVENTS_COUNTER_PATTERN);
      }
    }

    publ c vo d  ncre nt(Thr ft ndex ngEventType eventType, Str ng f eldNa ) {
       ncre ntForPattern(eventType, f eldNa , perF eldTable, PER_F ELD_EVENTS_COUNTER_PATTERN);
    }

    publ c vo d  ncre ntT etNot n ndex(Thr ft ndex ngEventType eventType, Docu nt doc) {
      for ( ndexableF eld f eld : doc.getF elds()) {
         ncre ntForPattern(
            eventType, f eld.na (),
            not n ndexPerF eldTable,
            TWEET_NOT_ N_ NDEX_PER_F ELD_EVENTS_COUNTER_PATTERN);
      }
    }

    pr vate vo d  ncre ntForPattern(
        Thr ft ndex ngEventType eventType, Str ng f eldNa ,
        Table<Thr ft ndex ngEventType, Str ng, SearchCounter> counterTable, Str ng pattern) {

      SearchCounter stat;
       f (counterTable.conta ns(eventType, f eldNa )) {
        stat = counterTable.get(eventType, f eldNa );
      } else {
        stat = SearchCounter.export(Str ng.format(pattern, eventType, f eldNa ).toLo rCase());
        counterTable.put(eventType, f eldNa , stat);
      }
      stat. ncre nt();
    }
  }
}
