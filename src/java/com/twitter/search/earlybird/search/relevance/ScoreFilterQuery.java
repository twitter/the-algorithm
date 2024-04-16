package com.tw ter.search.earlyb rd.search.relevance;

 mport java. o. OExcept on;

 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene.search.BooleanClause;
 mport org.apac .lucene.search.BooleanQuery;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.  ght;

 mport com.tw ter.search.common.query.DefaultF lter  ght;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.core.earlyb rd. ndex.ut l.RangeF lterD S ;
 mport com.tw ter.search.earlyb rd.search.relevance.scor ng.Scor ngFunct on;
 mport com.tw ter.search.earlyb rd.search.relevance.scor ng.Scor ngFunct onProv der;
 mport com.tw ter.search.earlyb rd.search.relevance.scor ng.Scor ngFunct onProv der.Na dScor ngFunct onProv der;

/**
 * T  f lter only accepts docu nts for wh ch t  prov ded
 * {@l nk com.tw ter.search.earlyb rd.search.relevance.scor ng.Scor ngFunct on}
 * returns a score that's greater or equal to t  passed- n m nScore and smaller or equal
 * to maxScore.
 */
publ c f nal class ScoreF lterQuery extends Query {
  pr vate stat c f nal float DEFAULT_LUCENE_SCORE = 1.0F;

  pr vate f nal float m nScore;
  pr vate f nal float maxScore;
  pr vate f nal Na dScor ngFunct onProv der scor ngFunct onProv der;
  pr vate f nal  mmutableSc ma nterface sc ma;

  /**
   * Returns a score f lter.
   *
   * @param sc ma T  sc ma to use to extract t  feature scores.
   * @param scor ngFunct onProv der T  scor ng funct on prov der.
   * @param m nScore T  m n mum score threshold.
   * @param maxScore T  max mum score threshold.
   * @return A score f lter w h t  g ven conf gurat on.
   */
  publ c stat c Query getScoreF lterQuery(
       mmutableSc ma nterface sc ma,
      Na dScor ngFunct onProv der scor ngFunct onProv der,
      float m nScore,
      float maxScore) {
    return new BooleanQuery.Bu lder()
        .add(new ScoreF lterQuery(sc ma, scor ngFunct onProv der, m nScore, maxScore),
             BooleanClause.Occur.F LTER)
        .bu ld();
  }

  pr vate ScoreF lterQuery( mmutableSc ma nterface sc ma,
                           Na dScor ngFunct onProv der scor ngFunct onProv der,
                           float m nScore,
                           float maxScore) {
    t .sc ma = sc ma;
    t .scor ngFunct onProv der = scor ngFunct onProv der;
    t .m nScore = m nScore;
    t .maxScore = maxScore;
  }

  @Overr de
  publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost)
      throws  OExcept on {
    return new DefaultF lter  ght(t ) {
      @Overr de
      protected Doc dSet erator getDoc dSet erator(LeafReaderContext context) throws  OExcept on {
        Scor ngFunct on scor ngFunct on = scor ngFunct onProv der.getScor ngFunct on();
        scor ngFunct on.setNextReader((Earlyb rd ndexSeg ntAtom cReader) context.reader());
        return new ScoreF lterDoc dSet erator(
            context.reader(), scor ngFunct on, m nScore, maxScore);
      }
    };
  }

  pr vate stat c f nal class ScoreF lterDoc dSet erator extends RangeF lterD S  {
    pr vate f nal Scor ngFunct on scor ngFunct on;
    pr vate f nal float m nScore;
    pr vate f nal float maxScore;

    publ c ScoreF lterDoc dSet erator(LeafReader  ndexReader, Scor ngFunct on scor ngFunct on,
                                       float m nScore, float maxScore) throws  OExcept on {
      super( ndexReader);
      t .scor ngFunct on = scor ngFunct on;
      t .m nScore = m nScore;
      t .maxScore = maxScore;
    }

    @Overr de
    protected boolean shouldReturnDoc() throws  OExcept on {
      float score = scor ngFunct on.score(doc D(), DEFAULT_LUCENE_SCORE);
      return score >= m nScore && score <= maxScore;
    }
  }

  publ c float getM nScoreForTest() {
    return m nScore;
  }

  publ c float getMaxScoreForTest() {
    return maxScore;
  }

  publ c Scor ngFunct onProv der getScor ngFunct onProv derForTest() {
    return scor ngFunct onProv der;
  }

  @Overr de
  publ c  nt hashCode() {
    return ( nt) (m nScore * 29
                  + maxScore * 17
                  + (scor ngFunct onProv der == null ? 0 : scor ngFunct onProv der.hashCode()));
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof ScoreF lterQuery)) {
      return false;
    }

    ScoreF lterQuery f lter = ScoreF lterQuery.class.cast(obj);
    return (m nScore == f lter.m nScore)
        && (maxScore == f lter.maxScore)
        && (scor ngFunct onProv der == null
            ? f lter.scor ngFunct onProv der == null
            : scor ngFunct onProv der.equals(f lter.scor ngFunct onProv der));
  }

  @Overr de
  publ c Str ng toStr ng(Str ng f eld) {
    return "SCORE_F LTER_QUERY[m nScore=" + m nScore + ",maxScore=" + maxScore + "]";
  }
}
