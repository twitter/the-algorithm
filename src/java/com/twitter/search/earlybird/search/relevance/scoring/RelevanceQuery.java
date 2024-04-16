package com.tw ter.search.earlyb rd.search.relevance.scor ng;

 mport java. o. OExcept on;
 mport java.ut l.Objects;
 mport java.ut l.Set;

 mport javax.annotat on.Nullable;

 mport org.apac .lucene. ndex. ndexReader;
 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene.search.Explanat on;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.Scorer;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.  ght;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.results.thr ftjava.F eldH Attr but on;

/**
 * A wrapper for a Lucene query wh ch f rst computes Lucene's query score
 * and t n delegates to a {@l nk Scor ngFunct on} for f nal score computat on.
 */
publ c class RelevanceQuery extends Query {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(RelevanceQuery.class.getNa ());

  protected f nal Query luceneQuery;
  protected f nal Scor ngFunct on scor ngFunct on;

  // True w n t  lucene query's score should be  gnored for debug explanat ons.
  protected f nal boolean  gnoreLuceneQueryScoreExplanat on;

  publ c RelevanceQuery(Query luceneQuery, Scor ngFunct on scor ngFunct on) {
    t (luceneQuery, scor ngFunct on, false);
  }

  publ c RelevanceQuery(Query luceneQuery,
                        Scor ngFunct on scor ngFunct on,
                        boolean  gnoreLuceneQueryScoreExplanat on) {
    t .luceneQuery = luceneQuery;
    t .scor ngFunct on = scor ngFunct on;
    t . gnoreLuceneQueryScoreExplanat on =  gnoreLuceneQueryScoreExplanat on;
  }

  publ c Scor ngFunct on getScor ngFunct on() {
    return scor ngFunct on;
  }

  publ c Query getLuceneQuery() {
    return luceneQuery;
  }

  @Overr de
  publ c Query rewr e( ndexReader reader) throws  OExcept on {
    Query rewr ten = luceneQuery.rewr e(reader);
     f (rewr ten == luceneQuery) {
      return t ;
    }
    return new RelevanceQuery(rewr ten, scor ngFunct on,  gnoreLuceneQueryScoreExplanat on);
  }

  @Overr de
  publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost)
      throws  OExcept on {
      ght lucene  ght = luceneQuery.create  ght(searc r, scoreMode, boost);
     f (lucene  ght == null) {
      return null;
    }
    return new Relevance  ght(searc r, lucene  ght);
  }

  publ c class Relevance  ght extends   ght {
    pr vate f nal   ght lucene  ght;

    publ c Relevance  ght( ndexSearc r searc r,   ght lucene  ght) {
      super(RelevanceQuery.t );
      t .lucene  ght = lucene  ght;
    }

    @Overr de
    publ c vo d extractTerms(Set<Term> terms) {
      t .lucene  ght.extractTerms(terms);
    }


    @Overr de
    publ c Explanat on expla n(LeafReaderContext context,  nt doc) throws  OExcept on {
      return expla n(context, doc, null);
    }

    /**
     * Returns an explanat on of t  scor ng for t  g ven docu nt.
     *
     * @param context T  context of t  reader that returned t  docu nt.
     * @param doc T  docu nt.
     * @param f eldH Attr but on Per-h  f eld attr but on  nformat on.
     * @return An explanat on of t  scor ng for t  g ven docu nt.
     */
    publ c Explanat on expla n(LeafReaderContext context,  nt doc,
        @Nullable F eldH Attr but on f eldH Attr but on) throws  OExcept on {

      Explanat on luceneExplanat on = Explanat on.noMatch("LuceneQuery expla n sk pped");
       f (! gnoreLuceneQueryScoreExplanat on) {
        // get Lucene score
        try {
          luceneExplanat on = lucene  ght.expla n(context, doc);
        } catch (Except on e) {
          //   so t  s see except ons result ng from term quer es that do not store
          // utf8-text, wh ch TermQuery.toStr ng() assu s.  Catch  re and allow at least
          // scor ng funct on explanat ons to be returned.
          LOG.error("Except on  n expla n", e);
          luceneExplanat on = Explanat on.noMatch("LuceneQuery expla n fa led");
        }
      }

      Explanat on scor ngFunct onExplanat on;
      scor ngFunct on.setF eldH Attr but on(f eldH Attr but on);
      scor ngFunct onExplanat on = scor ngFunct on.expla n(
          context.reader(), doc, luceneExplanat on.getValue().floatValue());

      // just add a wrapper for a better structure of t  f nal explanat on
      Explanat on luceneExplanat onWrapper = Explanat on.match(
          luceneExplanat on.getValue(), "LuceneQuery", luceneExplanat on);

      return Explanat on.match(scor ngFunct onExplanat on.getValue(), "RelevanceQuery",
              scor ngFunct onExplanat on, luceneExplanat onWrapper);
    }

    @Overr de
    publ c Scorer scorer(LeafReaderContext context) throws  OExcept on {
      return lucene  ght.scorer(context);
    }

    @Overr de
    publ c boolean  sCac able(LeafReaderContext ctx) {
      return lucene  ght. sCac able(ctx);
    }
  }

  @Overr de
  publ c  nt hashCode() {
    return (luceneQuery == null ? 0 : luceneQuery.hashCode())
        + (scor ngFunct on == null ? 0 : scor ngFunct on.hashCode()) * 13;
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof RelevanceQuery)) {
      return false;
    }

    RelevanceQuery query = RelevanceQuery.class.cast(obj);
    return Objects.equals(luceneQuery, query.luceneQuery)
        && Objects.equals(scor ngFunct on, query.scor ngFunct on);
  }

  @Overr de
  publ c Str ng toStr ng(Str ng f eld) {
    return "RelevanceQuery[q=" + luceneQuery.toStr ng(f eld) + "]";
  }
}
