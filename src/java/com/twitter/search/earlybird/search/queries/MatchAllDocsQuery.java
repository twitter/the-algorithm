package com.tw ter.search.earlyb rd.search.quer es;

 mport java. o. OExcept on;
 mport java.ut l.Set;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene.search.ConstantScoreScorer;
 mport org.apac .lucene.search.Explanat on;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.Scorer;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.  ght;

 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.core.earlyb rd. ndex.ut l.RangeF lterD S ;
 mport com.tw ter.search.earlyb rd. ndex.Earlyb rdS ngleSeg ntSearc r;

/**
 * A MatchAllDocsQuery  mple ntat on that does not assu  that doc  Ds are ass gned sequent ally.
 *  nstead,   wraps t  Earlyb rd ndexSeg ntAtom cReader  nto a RangeF lterD S , and uses
 * t   erator to traverse only t  val d doc  Ds  n t  seg nt.
 *
 * Note that org.apac .lucene. ndex.MatchAllDocsQuery  s f nal, so   cannot extend  .
 */
publ c class MatchAllDocsQuery extends Query {
  pr vate stat c class MatchAllDocs  ght extends   ght {
    pr vate f nal   ght lucene  ght;

    publ c MatchAllDocs  ght(Query query,   ght lucene  ght) {
      super(query);
      t .lucene  ght = lucene  ght;
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
    publ c Scorer scorer(LeafReaderContext context) throws  OExcept on {
      Precond  ons.c ckState(context.reader()  nstanceof Earlyb rd ndexSeg ntAtom cReader,
                               "Expected an Earlyb rd ndexSeg ntAtom cReader, but got a "
                               + context.reader().getClass().getNa () + "  nstance.");
      Earlyb rd ndexSeg ntAtom cReader reader =
          (Earlyb rd ndexSeg ntAtom cReader) context.reader();
      return new ConstantScoreScorer(
          t , 1.0f, ScoreMode.COMPLETE_NO_SCORES, new RangeF lterD S (reader));
    }

    @Overr de
    publ c boolean  sCac able(LeafReaderContext ctx) {
      return lucene  ght. sCac able(ctx);
    }
  }

  @Overr de
  publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost) {
    org.apac .lucene.search.MatchAllDocsQuery luceneMatchAllDocsQuery =
        new org.apac .lucene.search.MatchAllDocsQuery();
      ght lucene  ght = luceneMatchAllDocsQuery.create  ght(searc r, scoreMode, boost);
     f (!(searc r  nstanceof Earlyb rdS ngleSeg ntSearc r)) {
      return lucene  ght;
    }
    return new MatchAllDocs  ght(t , lucene  ght);
  }

  @Overr de
  publ c  nt hashCode() {
    return 0;
  }

  @Overr de
  publ c boolean equals(Object obj) {
    return obj  nstanceof MatchAllDocsQuery;
  }

  // Cop ed from org.apac .lucene.search.MatchAllDocs  ght
  @Overr de
  publ c Str ng toStr ng(Str ng f eld) {
    return "*:*";
  }
}
