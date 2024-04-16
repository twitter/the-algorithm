package com.tw ter.search.common.search;

 mport java. o. OExcept on;
 mport java.ut l.L st;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex. ndexReader;
 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene. ndex.Mult DocValues;
 mport org.apac .lucene. ndex.Nu r cDocValues;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene. ndex.Terms;
 mport org.apac .lucene.search.Collect onStat st cs;
 mport org.apac .lucene.search.Collector;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.LeafCollector;
 mport org.apac .lucene.search.Scorer;
 mport org.apac .lucene.search.TermStat st cs;
 mport org.apac .lucene.search.  ght;

/**
 * An  ndexSearch that works w h Tw terEarlyTerm nat onCollector.
 *  f a stock Lucene collector  s passed  nto search(), t   ndexSearch.search() behaves t 
 * sa  as Lucene's stock  ndexSearc r.  Ho ver,  f a Tw terEarlyTerm nat onCollector  s passed
 *  n, t   ndexSearc r performs early term nat on w hout rely ng on
 * Collect onTerm natedExcept on.
 */
publ c class Tw ter ndexSearc r extends  ndexSearc r {
  publ c Tw ter ndexSearc r( ndexReader r) {
    super(r);
  }

  /**
   * search() ma n loop.
   * T  behaves exactly l ke  ndexSearc r.search()  f a stock Lucene collector passed  n.
   * Ho ver,  f a Tw terCollector  s passed  n, t  class performs Tw ter style early
   * term nat on w hout rely ng on
   * {@l nk org.apac .lucene.search.Collect onTerm natedExcept on}.
   */
  @Overr de
  protected vo d search(L st<LeafReaderContext> leaves,   ght   ght, Collector coll)
      throws  OExcept on {

    //  f an Tw terCollector  s passed  n,   can do a few extra th ngs  n  re, such
    // as early term nat on.  Ot rw se   can just fall back to  ndexSearc r.search().
     f (coll  nstanceof Tw terCollector) {
      Tw terCollector collector = (Tw terCollector) coll;

      for (LeafReaderContext ctx : leaves) { // search each subreader
         f (collector. sTerm nated()) {
          return;
        }

        // Not fy t  collector that  're start ng t  seg nt, and c ck for early
        // term nat on cr er a aga n.  setNextReader() performs 'expens ve' early
        // term nat on c cks  n so   mple ntat ons such as Tw terEarlyTerm nat onCollector.
        LeafCollector leafCollector = collector.getLeafCollector(ctx);
         f (collector. sTerm nated()) {
          return;
        }

        //  n  al ze t  scorer -   should not be null.  Note that construct ng t  scorer
        // may actually do real work, such as advanc ng to t  f rst h .
        Scorer scorer =   ght.scorer(ctx);

         f (scorer == null) {
          collector.f n shSeg nt(Doc dSet erator.NO_MORE_DOCS);
          cont nue;
        }

        leafCollector.setScorer(scorer);

        // Start search ng.
        Doc dSet erator doc dSet erator = scorer. erator();
         nt doc D = doc dSet erator.nextDoc();
         f (doc D != Doc dSet erator.NO_MORE_DOCS) {
          // Collect results.  Note: c ck  sTerm nated() before call ng nextDoc().
          do {
            leafCollector.collect(doc D);
          } wh le (!collector. sTerm nated()
                   && (doc D = doc dSet erator.nextDoc()) != Doc dSet erator.NO_MORE_DOCS);
        }

        // Always f n sh t  seg nt, prov d ng t  last doc D advanced to.
        collector.f n shSeg nt(doc D);
      }
    } else {
      // T  collector g ven  s not a Tw terCollector, just use stock lucene search().
      super.search(leaves,   ght, coll);
    }
  }

  /** Returns {@l nk Nu r cDocValues} for t  f eld, or
   *  null  f no {@l nk Nu r cDocValues}  re  ndexed for
   *  t  f eld.  T  returned  nstance should only be
   *  used by a s ngle thread. */
  publ c Nu r cDocValues getNu r cDocValues(Str ng f eld) throws  OExcept on {
    return Mult DocValues.getNu r cValues(get ndexReader(), f eld);
  }

  @Overr de
  publ c Collect onStat st cs collect onStat st cs(Str ng f eld) throws  OExcept on {
    return collect onStat st cs(f eld, get ndexReader());
  }

  @Overr de
  publ c TermStat st cs termStat st cs(Term term,  nt docFreq, long totalTermFreq) {
    return termStats(term, docFreq, totalTermFreq);
  }

  /**
   * Lucene rel es on t  fact that maxDoc D  s typ cally equal to t  number of docu nts  n t 
   *  ndex, wh ch  s false w n   have sparse doc  Ds or w n   start from 8 m ll on docs and
   * decre nt, so  n t  class   pass  n numDocs  nstead of t  max mum ass gned docu nt  D.
   * Note that t  com nt on {@l nk Collect onStat st cs#maxDoc()} says that   returns t  number
   * of docu nts  n t  seg nt, not t  max mum  D, and that    s only used t  way. T   s
   * necessary for all lucene scor ng  thods, e.g.
   * {@l nk org.apac .lucene.search.s m lar  es.TF DFS m lar y# dfExpla n}. T   thod body  s
   * largely cop ed from {@l nk  ndexSearc r#collect onStat st cs(Str ng)}.
   */
  publ c stat c Collect onStat st cs collect onStat st cs(Str ng f eld,  ndexReader  ndexReader)
      throws  OExcept on {
    Precond  ons.c ckNotNull(f eld);

     nt docsW hF eld = 0;
    long sumTotalTermFreq = 0;
    long sumDocFreq = 0;
    for (LeafReaderContext leaf :  ndexReader.leaves()) {
      Terms terms = leaf.reader().terms(f eld);
       f (terms == null) {
        cont nue;
      }

      docsW hF eld += terms.getDocCount();
      sumTotalTermFreq += terms.getSumTotalTermFreq();
      sumDocFreq += terms.getSumDocFreq();
    }

     f (docsW hF eld == 0) {
      // T  Collect onStat st cs AP   n Lucene  s des gned poorly. On one hand, start ng w h
      // Lucene 8.0.0, searc rs are expected to always produce val d Collect onStat st cs  nstances
      // and all  nt f elds  n t se  nstances are expected to be str ctly greater than 0. On t 
      // ot r hand, Lucene  self produces null Collect onStat st cs  nstances  n a few places.
      // Also, t re's no good placeholder value to  nd cate that a f eld  s empty, wh ch  s a very
      // reasonable th ng to happen (for example, t  f rst few t ets  n a new seg nt m ght not
      // have any l nks, so t n t  resolved_l nks_text would be empty). So to get around t 
      //  ssue,   do  re what Lucene does:   return a Collect onStat st cs  nstance w h all
      // f elds set to 1.
      return new Collect onStat st cs(f eld, 1, 1, 1, 1);
    }

    // T  wr er could have added more docs to t   ndex s nce t  searc r started process ng
    // t  request, or could be  n t  m ddle of add ng a doc, wh ch could  an that only so  of
    // t  docsW hF eld, sumTotalTermFreq and sumDocFreq stats have been updated.   don't th nk
    // t   s a b g deal, as t se stats are only used for comput ng a h 's score, and m nor
    //  naccurac es should have very l tle effect on a h 's f nal score. But Collect onStat st c's
    // constructor has so  str ct asserts for t  relat onsh p bet en t se stats. So   need to
    // make sure   cap t  values of t se stats appropr ately.
    //
    // Adjust numDocs based on docsW hF eld ( nstead of do ng t  oppos e), because:
    //   1.  f new docu nts  re added to t  seg nt after t  reader was created,   seems
    //      reasonable to take t  more recent  nformat on  nto account.
    //   2. T  termStats()  thod below w ll return t  most recent docFreq (not t  value that
    //      docFreq was set to w n t  reader was created).  f t  value  s h g r than numDocs,
    //      t n Lucene m ght end up produc ng negat ve scores, wh ch must never happen.
     nt numDocs = Math.max( ndexReader.numDocs(), docsW hF eld);
    sumDocFreq = Math.max(sumDocFreq, docsW hF eld);
    sumTotalTermFreq = Math.max(sumTotalTermFreq, sumDocFreq);
    return new Collect onStat st cs(f eld, numDocs, docsW hF eld, sumTotalTermFreq, sumDocFreq);
  }

  /**
   * T   thod body  s largely cop ed from {@l nk  ndexSearc r#termStat st cs(Term,  nt, long)}.
   * T  only d fference  s that   make sure all para ters   pass to t  TermStat st cs  nstance
   *   create are set to at least 1 (because Lucene 8.0.0 expects t m to be).
   */
  publ c stat c TermStat st cs termStats(Term term,  nt docFreq, long totalTermFreq) {
    // Lucene expects t  doc frequency and total term frequency to be at least 1. T  assumpt on
    // doesn't always make sense (t  seg nt can be empty -- see com nt above), but to make Lucene
    // happy, make sure to always set t se para ters to at least 1.
     nt adjustedDocFreq = Math.max(docFreq, 1);
    return new TermStat st cs(
        term.bytes(),
        adjustedDocFreq,
        Math.max(totalTermFreq, adjustedDocFreq));
  }
}
