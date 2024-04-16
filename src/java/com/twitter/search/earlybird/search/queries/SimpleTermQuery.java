package com.tw ter.search.earlyb rd.search.quer es;

 mport java. o. OExcept on;

 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene. ndex.Post ngsEnum;
 mport org.apac .lucene. ndex.TermsEnum;
 mport org.apac .lucene.search.ConstantScoreScorer;
 mport org.apac .lucene.search.ConstantScore  ght;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.Scorer;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.  ght;

/**
 * A vers on of a term query that   can use w n   already know t  term  d ( n case w re  
 * prev ously looked   up), and have a TermsEnum to get t  actual post ngs.
 *
 * T   s can be used for constant score quer es, w re only  erat ng on t  post ngs  s requ red.
 */
class S mpleTermQuery extends Query {
  pr vate f nal TermsEnum termsEnum;
  pr vate f nal long term d;

  publ c S mpleTermQuery(TermsEnum termsEnum, long term d) {
    t .termsEnum = termsEnum;
    t .term d = term d;
  }

  @Overr de
  publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost)
      throws  OExcept on {
    return new S mpleTermQuery  ght(scoreMode);
  }

  @Overr de
  publ c  nt hashCode() {
    return (termsEnum == null ? 0 : termsEnum.hashCode()) * 13 + ( nt) term d;
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof S mpleTermQuery)) {
      return false;
    }

    S mpleTermQuery query = S mpleTermQuery.class.cast(obj);
    return (termsEnum == null ? query.termsEnum == null : termsEnum.equals(query.termsEnum))
        && (term d == query.term d);
  }

  @Overr de
  publ c Str ng toStr ng(Str ng f eld) {
    return "S mpleTermQuery(" + f eld + ":" + term d + ")";
  }

  pr vate class S mpleTermQuery  ght extends ConstantScore  ght {
    pr vate f nal ScoreMode scoreMode;

    publ c S mpleTermQuery  ght(ScoreMode scoreMode) {
      super(S mpleTermQuery.t , 1.0f);
      t .scoreMode = scoreMode;
    }

    @Overr de
    publ c Str ng toStr ng() {
      return "  ght(" + S mpleTermQuery.t  + ")";
    }

    @Overr de
    publ c Scorer scorer(LeafReaderContext context) throws  OExcept on {
      termsEnum.seekExact(term d);

      Post ngsEnum docs = termsEnum.post ngs(
          null, scoreMode.needsScores() ? Post ngsEnum.FREQS : Post ngsEnum.NONE);
      assert docs != null;
      return new ConstantScoreScorer(t , 0, scoreMode, docs);
    }

    @Overr de
    publ c boolean  sCac able(LeafReaderContext ctx) {
      return true;
    }
  }
}
