package com.tw ter.search.core.earlyb rd. ndex;

 mport java. o.F le;
 mport java. o. OExcept on;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;
 mport org.slf4j.Marker;
 mport org.slf4j.MarkerFactory;

 mport org.apac .lucene.docu nt.Docu nt;
 mport org.apac .lucene. ndex. ndexWr er;
 mport org.apac .lucene. ndex. ndexWr erConf g;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.store.D rectory;
 mport org.apac .lucene.store.FSD rectory;
 mport org.apac .lucene.store.LockObta nFa ledExcept on;

/**
 * Earlyb rd ndexWr er  mple ntat on that's a wrapper around Lucene's {@l nk  ndexWr er}
 * and wr es Lucene seg nts  nto a {@l nk D rectory}.
 */
publ c class Earlyb rdLucene ndexSeg ntWr er extends Earlyb rd ndexSeg ntWr er {
  pr vate stat c f nal Logger LOG =
    LoggerFactory.getLogger(Earlyb rdLucene ndexSeg ntWr er.class);
  pr vate stat c f nal Marker FATAL = MarkerFactory.getMarker("FATAL");

  pr vate f nal Earlyb rdLucene ndexSeg ntData seg ntData;
  pr vate f nal  ndexWr er  ndexWr er;

  @Overr de
  publ c Earlyb rd ndexSeg ntData getSeg ntData() {
    return seg ntData;
  }

  /**
   * Construct a lucene  ndexWr er-based Earlyb rd seg nt wr er.
   * T  w ll open a Lucene  ndexWr er on seg ntData.getLuceneD rectory().
   * T  constructor w ll throw LockObta nFa ledExcept on  f   cannot obta n t  "wr e.lock"
   *  ns de t  d rectory seg ntData.getLuceneD rectory().
   *
   * Don't add publ c constructors to t  class. Earlyb rdLucene ndexSeg ntWr er  nstances should
   * be created only by call ng Earlyb rdLucene ndexSeg ntData.createEarlyb rd ndexSeg ntWr er(),
   * to make sure everyth ng  s set up properly (such as CSF readers).
   */
  Earlyb rdLucene ndexSeg ntWr er(
      Earlyb rdLucene ndexSeg ntData seg ntData,
       ndexWr erConf g  ndexWr erConf g) throws  OExcept on {
    Precond  ons.c ckNotNull(seg ntData);
    t .seg ntData = seg ntData;
    try {
      t . ndexWr er = new  ndexWr er(seg ntData.getLuceneD rectory(),  ndexWr erConf g);
    } catch (LockObta nFa ledExcept on e) {
      logDebugg ng nfoUponFa lureToObta nLuceneWr eLock(seg ntData, e);
      // Rethrow t  except on, and t  Earlyb rd w ll tr gger cr  cal alerts
      throw e;
    }
  }

  pr vate vo d logDebugg ng nfoUponFa lureToObta nLuceneWr eLock(
      Earlyb rdLucene ndexSeg ntData lucene ndexSeg ntData,
      LockObta nFa ledExcept on e) throws  OExcept on {
    // Every day,   create a new Lucene d r---  do not append  nto ex st ng Lucene d rs.
    // Supposedly,   should never fa l to obta n t  wr e lock from a fresh and empty
    // Lucene d rectory.
    // Add ng debugg ng  nformat on for SEARCH-4454, w re a t  sl ce roll fa led because
    // Earlyb rd fa led to get t  wr e lock for a new t  sl ce.
    D rectory d r = lucene ndexSeg ntData.getLuceneD rectory();
    LOG.error(
      FATAL,
      "Unable to obta n wr e.lock for Lucene d rectory. T  Lucene d rectory  s: " + d r,
      e);

     f (d r  nstanceof FSD rectory) { // t  c ck should always be true  n   current setup.
      FSD rectory fsD r = (FSD rectory) d r;
      // Log  f t  underly ng d rectory on d sk does not ex st.
      F le underly ngD r = fsD r.getD rectory().toF le();
       f (underly ngD r.ex sts()) {
        LOG. nfo("Lucene d rectory conta ns t  follow ng f les: "
            + L sts.newArrayL st(fsD r.l stAll()));
      } else {
        LOG.error(
          FATAL,
          "D rectory " + underly ngD r + " does not ex st on d sk.",
          e);
      }

       f (!underly ngD r.canWr e()) {
        LOG.error(
          FATAL,
          "Cannot wr e  nto d rectory " + underly ngD r,
          e);
      }

      F le wr eLockF le = new F le(underly ngD r, "wr e.lock");
       f (wr eLockF le.ex sts()) {
        LOG.error(
          FATAL,
          "Wr e lock f le " + wr eLockF le + " already ex sts.",
          e);
      }

       f (!wr eLockF le.canWr e()) {
        LOG.error(
          FATAL,
          "No wr e access to lock f le: " + wr eLockF le
            + " Usable space: " + underly ngD r.getUsableSpace(),
          e);
      }

      // L st all f les  n t  seg nt d rectory
      F le seg ntD r = underly ngD r.getParentF le();
      LOG.warn("Seg nt d rectory conta ns t  follow ng f les: "
          + L sts.newArrayL st(seg ntD r.l st()));
    } else {
      LOG.warn("Unable to log debugg ng  nfo upon fa l ng to acqu re Lucene wr e lock."
          + "T  class of t  d rectory  s: " + d r.getClass().getNa ());
    }
  }

  @Overr de
  publ c vo d addDocu nt(Docu nt doc) throws  OExcept on {
     ndexWr er.addDocu nt(doc);
  }

  @Overr de
  publ c vo d addT et(Docu nt doc, long t et d, boolean doc dOffens ve) throws  OExcept on {
     ndexWr er.addDocu nt(doc);
  }

  @Overr de
  protected vo d appendOutOfOrder(Docu nt doc,  nt doc d) throws  OExcept on {
    throw new UnsupportedOperat onExcept on("T  Lucene-based  ndexWr er does not support "
            + "updates and out-of-order appends.");
  }

  @Overr de
  publ c  nt numDocs() {
    return  ndexWr er.getDocStats().maxDoc;
  }

  @Overr de
  publ c  nt numDocsNoDelete() throws  OExcept on {
    return numDocs();
  }

  @Overr de
  publ c vo d deleteDocu nts(Query query) throws  OExcept on {
    super.deleteDocu nts(query);
     ndexWr er.deleteDocu nts(query);
  }

  @Overr de
  publ c vo d add ndexes(D rectory... d rs) throws  OExcept on {
     ndexWr er.add ndexes(d rs);
  }

  @Overr de
  publ c vo d force rge() throws  OExcept on {
     ndexWr er.force rge(1);
  }

  @Overr de
  publ c vo d close() throws  OExcept on {
     ndexWr er.close();
  }
}
