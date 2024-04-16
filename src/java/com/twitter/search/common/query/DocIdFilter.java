package com.tw ter.search.common.query;

 mport java. o. OExcept on;
 mport java.ut l.Set;

 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene.search.ConstantScoreScorer;
 mport org.apac .lucene.search.Explanat on;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.Scorer;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.  ght;

/**
 * Lucene f lter on top of a known doc d
 *
 */
publ c class Doc dF lter extends Query {
  pr vate f nal  nt doc d;

  publ c Doc dF lter( nt doc d) {
    t .doc d = doc d;
  }

  @Overr de
  publ c   ght create  ght(
       ndexSearc r searc r, ScoreMode scoreMode, float boost) throws  OExcept on {
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
        return new ConstantScoreScorer(t , 0.0f, scoreMode, new S ngleDocDoc dSet erator(doc d));
      }

      @Overr de
      publ c boolean  sCac able(LeafReaderContext ctx) {
        return true;
      }
    };
  }

  @Overr de
  publ c  nt hashCode() {
    return doc d;
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof Doc dF lter)) {
      return false;
    }

    return doc d == Doc dF lter.class.cast(obj).doc d;
  }

  @Overr de
  publ c Str ng toStr ng(Str ng f eld) {
    return "DOC_ D_F LTER[doc d=" + doc d + " + ]";
  }
}
