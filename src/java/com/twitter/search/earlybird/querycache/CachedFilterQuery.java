package com.tw ter.search.earlyb rd.querycac ;

 mport java. o. OExcept on;
 mport java.ut l.Objects;
 mport java.ut l.Set;

 mport org.apac .lucene. ndex. ndexReader;
 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene.search.BooleanClause;
 mport org.apac .lucene.search.BooleanQuery;
 mport org.apac .lucene.search.ConstantScoreScorer;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.search.Explanat on;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.Scorer;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.  ght;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.query.DefaultF lter  ght;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.core.earlyb rd. ndex.QueryCac ResultForSeg nt;

/**
 * Query to  erate QueryCac  result (t  cac )
 */
publ c f nal class Cac dF lterQuery extends Query {
  pr vate stat c f nal Str ng STAT_PREF X = "querycac _serv ng_";
  pr vate stat c f nal SearchCounter REWR TE_CALLS = SearchCounter.export(
      STAT_PREF X + "rewr e_calls");
  pr vate stat c f nal SearchCounter NO_CACHE_FOUND = SearchCounter.export(
      STAT_PREF X + "no_cac _found");
  pr vate stat c f nal SearchCounter USED_CACHE_AND_FRESH_DOCS = SearchCounter.export(
      STAT_PREF X + "used_cac _and_fresh_docs");
  pr vate stat c f nal SearchCounter USED_CACHE_ONLY = SearchCounter.export(
      STAT_PREF X + "used_cac _only");


  publ c stat c class NoSuchF lterExcept on extends Except on {
    NoSuchF lterExcept on(Str ng f lterNa ) {
      super("F lter [" + f lterNa  + "] does not ex sts");
    }
  }

  pr vate stat c class Cac dResultQuery extends Query {
    pr vate f nal QueryCac ResultForSeg nt cac dResult;

    publ c Cac dResultQuery(QueryCac ResultForSeg nt cac dResult) {
      t .cac dResult = cac dResult;
    }

    @Overr de
    publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost) {
      return new DefaultF lter  ght(t ) {
        @Overr de
        protected Doc dSet erator getDoc dSet erator(LeafReaderContext context)
            throws  OExcept on {
          return cac dResult.getDoc dSet(). erator();
        }
      };
    }

    @Overr de
    publ c  nt hashCode() {
      return cac dResult == null ? 0 : cac dResult.hashCode();
    }

    @Overr de
    publ c boolean equals(Object obj) {
       f (!(obj  nstanceof Cac dResultQuery)) {
        return false;
      }

      Cac dResultQuery query = (Cac dResultQuery) obj;
      return Objects.equals(cac dResult, query.cac dResult);
    }

    @Overr de
    publ c Str ng toStr ng(Str ng f eld) {
      return "CACHED_RESULT";
    }
  }

  pr vate stat c class Cac dResultAndFreshDocsQuery extends Query {
    pr vate f nal Query cac LuceneQuery;
    pr vate f nal QueryCac ResultForSeg nt cac dResult;

    publ c Cac dResultAndFreshDocsQuery(
        Query cac LuceneQuery, QueryCac ResultForSeg nt cac dResult) {
      t .cac LuceneQuery = cac LuceneQuery;
      t .cac dResult = cac dResult;
    }

    @Overr de
    publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost) {
      return new   ght(t ) {
        @Overr de
        publ c vo d extractTerms(Set<Term> terms) {
        }

        @Overr de
        publ c Explanat on expla n(LeafReaderContext context,  nt doc) throws  OExcept on {
          Scorer scorer = scorer(context);
           f ((scorer != null) && (scorer. erator().advance(doc) == doc)) {
            return Explanat on.match(0f, "Match on  d " + doc);
          }
          return Explanat on.match(0f, "No match on  d " + doc);
        }

        @Overr de
        publ c Scorer scorer(LeafReaderContext context) throws  OExcept on {
            ght lucene  ght;
          try  {
            lucene  ght = cac LuceneQuery.create  ght(searc r, scoreMode, boost);
          } catch (UnsupportedOperat onExcept on e) {
            // So  quer es do not support   ghts. T   s f ne,   s mply  ans t  query has
            // no docs, and  ans t  sa  th ng as a null scorer.
            return null;
          }

          Scorer luceneScorer = lucene  ght.scorer(context);
           f (luceneScorer == null) {
            return null;
          }

          Doc dSet erator  erator = new Cac dResultDoc dSet erator(
              cac dResult.getSmallestDoc D(),
              luceneScorer. erator(),
              cac dResult.getDoc dSet(). erator());
          return new ConstantScoreScorer(lucene  ght, 0.0f, scoreMode,  erator);
        }

        @Overr de
        publ c boolean  sCac able(LeafReaderContext ctx) {
          return true;
        }
      };
    }

    @Overr de
    publ c  nt hashCode() {
      return (cac LuceneQuery == null ? 0 : cac LuceneQuery.hashCode()) * 13
          + (cac dResult == null ? 0 : cac dResult.hashCode());
    }

    @Overr de
    publ c boolean equals(Object obj) {
       f (!(obj  nstanceof Cac dResultAndFreshDocsQuery)) {
        return false;
      }

      Cac dResultAndFreshDocsQuery query = (Cac dResultAndFreshDocsQuery) obj;
      return Objects.equals(cac LuceneQuery, query.cac LuceneQuery)
          && Objects.equals(cac dResult, query.cac dResult);
    }

    @Overr de
    publ c Str ng toStr ng(Str ng f eld) {
      return "CACHED_RESULT_AND_FRESH_DOCS";
    }
  }

  pr vate stat c f nal Query DUMMY_F LTER = wrapF lter(new Query() {
    @Overr de
    publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost) {
      return new DefaultF lter  ght(t ) {
        @Overr de
        protected Doc dSet erator getDoc dSet erator(LeafReaderContext context) {
          return null;
        }
      };
    }

    @Overr de
    publ c  nt hashCode() {
      return System. dent yHashCode(t );
    }

    @Overr de
    publ c boolean equals(Object obj) {
      return t  == obj;
    }

    @Overr de
    publ c Str ng toStr ng(Str ng f eld) {
      return "DUMMY_F LTER";
    }
  });

  pr vate f nal QueryCac F lter queryCac F lter;

  // Lucene Query used to f ll t  cac 
  pr vate f nal Query cac LuceneQuery;

  publ c stat c Query getCac dF lterQuery(Str ng f lterNa , QueryCac Manager queryCac Manager)
      throws NoSuchF lterExcept on {
    return wrapF lter(new Cac dF lterQuery(f lterNa , queryCac Manager));
  }

  pr vate stat c Query wrapF lter(Query f lter) {
    return new BooleanQuery.Bu lder()
        .add(f lter, BooleanClause.Occur.F LTER)
        .bu ld();
  }

  pr vate Cac dF lterQuery(Str ng f lterNa , QueryCac Manager queryCac Manager)
      throws NoSuchF lterExcept on {
    queryCac F lter = queryCac Manager.getF lter(f lterNa );
     f (queryCac F lter == null) {
      throw new NoSuchF lterExcept on(f lterNa );
    }
    queryCac F lter. ncre ntUsageStat();

    // retr eve t  query that was used to populate t  cac 
    cac LuceneQuery = queryCac F lter.getLuceneQuery();
  }

  /**
   * Creates a query base on t  cac  s uat on
   */
  @Overr de
  publ c Query rewr e( ndexReader reader) {
    Earlyb rd ndexSeg ntAtom cReader tw terReader = (Earlyb rd ndexSeg ntAtom cReader) reader;
    QueryCac ResultForSeg nt cac dResult =
        tw terReader.getSeg ntData().getQueryCac Result(queryCac F lter.getF lterNa ());
    REWR TE_CALLS. ncre nt();

     f (cac dResult == null || cac dResult.getSmallestDoc D() == -1) {
      // No cac d result, or cac  has never been updated
      // T  happens to t  newly created seg nt, bet en t  seg nt creat on and f rst
      // query cac  update
      NO_CACHE_FOUND. ncre nt();

       f (queryCac F lter.getCac ModeOnly()) {
        // s nce t  query cac  f lter allows cac  mode only,   return a query that
        // matc s no doc
        return DUMMY_F LTER;
      }

      return wrapF lter(cac LuceneQuery);
    }

     f (!queryCac F lter.getCac ModeOnly() && //  s t  a cac  mode only f lter?
        // t  follow ng c ck  s only necessary for t  realt   seg nt, wh ch
        // grows. S nce   decre nt doc ds  n t  realt   seg nt, a reader
        // hav ng a smallestDoc D less than t  one  n t  cac dResult  nd cates
        // that t  seg nt/reader has new docu nts.
        cac dResult.getSmallestDoc D() > tw terReader.getSmallestDoc D()) {
      // T  seg nt has more docu nts than t  cac d result.  OW, t re are new
      // docu nts that are not cac d. T  happens to latest seg nt that  're  ndex ng to.
      USED_CACHE_AND_FRESH_DOCS. ncre nt();
      return wrapF lter(new Cac dResultAndFreshDocsQuery(cac LuceneQuery, cac dResult));
    }

    // T  seg nt has not grown s nce t  cac  was last updated.
    // T  happens mostly to old seg nts that  're no longer  ndex ng to.
    USED_CACHE_ONLY. ncre nt();
    return wrapF lter(new Cac dResultQuery(cac dResult));
  }

  @Overr de
  publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost)
      throws  OExcept on {
    f nal   ght lucene  ght = cac LuceneQuery.create  ght(searc r, scoreMode, boost);

    return new   ght(t ) {
      @Overr de
      publ c Scorer scorer(LeafReaderContext context) throws  OExcept on {
        return lucene  ght.scorer(context);
      }

      @Overr de
      publ c vo d extractTerms(Set<Term> terms) {
        lucene  ght.extractTerms(terms);
      }

      @Overr de
      publ c Explanat on expla n(LeafReaderContext context,  nt doc) throws  OExcept on {
        return lucene  ght.expla n(context, doc);
      }

      @Overr de
      publ c boolean  sCac able(LeafReaderContext ctx) {
        return lucene  ght. sCac able(ctx);
      }
    };
  }

  @Overr de
  publ c  nt hashCode() {
    return cac LuceneQuery == null ? 0 : cac LuceneQuery.hashCode();
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof Cac dF lterQuery)) {
      return false;
    }

    Cac dF lterQuery f lter = (Cac dF lterQuery) obj;
    return Objects.equals(cac LuceneQuery, f lter.cac LuceneQuery);
  }

  @Overr de
  publ c Str ng toStr ng(Str ng s) {
    return "Cac dF lterQuery[" + queryCac F lter.getF lterNa () + "]";
  }
}
