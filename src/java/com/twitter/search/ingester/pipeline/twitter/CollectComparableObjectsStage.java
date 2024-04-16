/**
 * &copy; Copyr ght 2008, Summ ze,  nc. All r ghts reserved.
 */
package com.tw ter.search. ngester.p pel ne.tw ter;

 mport java.ut l.Collect ons;
 mport java.ut l.Nav gableSet;
 mport java.ut l.TreeSet;
 mport java.ut l.concurrent.T  Un ;
 mport java.ut l.concurrent.atom c.Atom cLong;

 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducedTypes;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.debug.DebugEventUt l;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchCustomGauge;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;

/**
 * Collect  ncom ng objects  nto batc s of t  conf gured s ze and t n
 * em  t  <code>Collect on</code> of objects.  nternally uses a <code>TreeSet</code>
 * to remove dupl cates.  ncom ng objects MUST  mple nt t  <code>Comparable</code>
 *  nterface.
 */
@Consu dTypes(Comparable.class)
@ProducedTypes(Nav gableSet.class)
publ c class CollectComparableObjectsStage extends Tw terBaseStage<Vo d, Vo d> {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(CollectComparableObjectsStage.class);

  // Batch s ze of t  collect ons   are em t ng.
  pr vate  nt batchS ze = -1;

  // Top t ets sorts t  t ets  n reverse order.
  pr vate Boolean reverseOrder = false;

  // Batch be ng constructed.
  pr vate TreeSet<Object> currentCollect on = null;

  // T  stamp (ms) of last batch em ss on.
  pr vate f nal Atom cLong lastEm T  M ll s = new Atom cLong(-1);
  //  f set, w ll em  a batch (only upon arr val of a new ele nt),  f t   s nce last em  has
  // exceeded t  threshold.
  pr vate long em AfterM ll s = -1;

  pr vate SearchCounter s zeBasedEm Count;
  pr vate SearchCounter t  BasedEm Count;
  pr vate SearchCounter s zeAndT  BasedEm Count;
  pr vate SearchT  rStats batchEm T  Stats;

  @Overr de
  protected vo d  n Stats() {
    super. n Stats();

    SearchCustomGauge.export(getStageNa Pref x() + "_last_em _t  ",
        () -> lastEm T  M ll s.get());

    s zeBasedEm Count = SearchCounter.export(getStageNa Pref x() + "_s ze_based_em _count");
    t  BasedEm Count = SearchCounter.export(getStageNa Pref x() + "_t  _based_em _count");
    s zeAndT  BasedEm Count = SearchCounter.export(
        getStageNa Pref x() + "_s ze_and_t  _based_em _count");

    batchEm T  Stats = SearchT  rStats.export(
        getStageNa Pref x() + "_batch_em _t  ",
        T  Un .M LL SECONDS,
        false, // no cpu t  rs
        true); // w h percent les
  }

  @Overr de
  protected vo d do nnerPreprocess() throws StageExcept on {
    //   have to  n  al ze t  stat  re, because  n Stats()  s called before
    // do nnerPreprocess(), so at that po nt t  'clock'  s not set yet.
    SearchCustomGauge.export(getStageNa Pref x() + "_m ll s_s nce_last_em ",
        () -> clock.nowM ll s() - lastEm T  M ll s.get());

    currentCollect on = newBatchCollect on();
     f (batchS ze <= 0) {
      throw new StageExcept on(t , "Must set t  batchS ze para ter to a value >0");
    }
  }

  pr vate TreeSet<Object> newBatchCollect on() {
    return new TreeSet<>(reverseOrder ? Collect ons.reverseOrder() : null);
  }

  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
     f (!Comparable.class. sAss gnableFrom(obj.getClass())) {
      throw new StageExcept on(
          t , "Attempt to add a non-comparable object to a sorted collect on");
    }

    currentCollect on.add(obj);
     f (shouldEm ()) {
      //   want to trace  re w n   actually em  t  batch, as t ets s   n t  stage unt l
      // a batch  s full, and   want to see how long t y actually st ck around.
      DebugEventUt l.addDebugEventToCollect on(
          currentCollect on, "CollectComparableObjectsStage.outgo ng", clock.nowM ll s());
      em AndCount(currentCollect on);
      updateLastEm T  ();

      currentCollect on = newBatchCollect on();
    }
  }

  pr vate boolean shouldEm () {
     f (lastEm T  M ll s.get() < 0) {
      //  n  al ze lastEm  at t  f rst t et seen by t  stage.
      lastEm T  M ll s.set(clock.nowM ll s());
    }

    f nal boolean s zeBasedEm  = currentCollect on.s ze() >= batchS ze;
    f nal boolean t  BasedEm  =
        em AfterM ll s > 0 && lastEm T  M ll s.get() + em AfterM ll s <= clock.nowM ll s();

     f (s zeBasedEm  && t  BasedEm ) {
      s zeAndT  BasedEm Count. ncre nt();
      return true;
    } else  f (s zeBasedEm ) {
      s zeBasedEm Count. ncre nt();
      return true;
    } else  f (t  BasedEm ) {
      t  BasedEm Count. ncre nt();
      return true;
    } else {
      return false;
    }
  }

  @Overr de
  publ c vo d  nnerPostprocess() throws StageExcept on {
     f (!currentCollect on. sEmpty()) {
      em AndCount(currentCollect on);
      updateLastEm T  ();
      currentCollect on = newBatchCollect on();
    }
  }

  pr vate vo d updateLastEm T  () {
    long currentEm T   = clock.nowM ll s();
    long prev ousEm T   = lastEm T  M ll s.getAndSet(currentEm T  );

    // Also stat how long each em  takes.
    batchEm T  Stats.t  r ncre nt(currentEm T   - prev ousEm T  );
  }

  publ c vo d setBatchS ze( nteger s ze) {
    LOG. nfo("Updat ng all CollectComparableObjectsStage batchS ze to {}.", s ze);
    t .batchS ze = s ze;
  }

  publ c Boolean getReverseOrder() {
    return reverseOrder;
  }

  publ c vo d setReverseOrder(Boolean reverseOrder) {
    t .reverseOrder = reverseOrder;
  }

  publ c vo d setEm AfterM ll s(long em AfterM ll s) {
    LOG. nfo("Sett ng em AfterM ll s to {}.", em AfterM ll s);
    t .em AfterM ll s = em AfterM ll s;
  }

  publ c long getS zeBasedEm Count() {
    return s zeBasedEm Count.get();
  }

  publ c long getT  BasedEm Count() {
    return t  BasedEm Count.get();
  }
}
