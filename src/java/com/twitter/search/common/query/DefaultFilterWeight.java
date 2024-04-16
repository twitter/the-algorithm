package com.tw ter.search.common.query;

 mport java. o. OExcept on;
 mport java.ut l.Set;

 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene.search.ConstantScoreScorer;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.search.Explanat on;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.Scorer;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.  ght;

/**
 * An abstract   ght  mple ntat on that can be used by all "f lter" classes (Query  nstances that
 * should not contr bute to t  overall query score).
 */
publ c abstract class DefaultF lter  ght extends   ght {
  publ c DefaultF lter  ght(Query query) {
    super(query);
  }

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
    Doc dSet erator d s  = getDoc dSet erator(context);
     f (d s  == null) {
      return null;
    }

    return new ConstantScoreScorer(t , 0.0f, ScoreMode.COMPLETE_NO_SCORES, d s );
  }

  @Overr de
  publ c boolean  sCac able(LeafReaderContext ctx) {
    return false;
  }

  /**
   * Returns t  Doc dSet erator over wh ch t  scorers created by t    ght need to  erate.
   *
   * @param context T  LeafReaderContext  nstance used to create t  scorer.
   */
  protected abstract Doc dSet erator getDoc dSet erator(LeafReaderContext context)
      throws  OExcept on;
}
