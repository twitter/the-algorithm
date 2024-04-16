package com.tw ter.search.common.query;

 mport java. o. OExcept on;
 mport java.ut l.Set;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex. ndexReader;
 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.search.Explanat on;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.Scorer;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.  ght;

/**
 * A pa r ng of a query and a f lter. T  h s traversal  s dr ven by t  query's Doc dSet erator,
 * and t  f lter  s used only to do post-f lter ng.  n ot r words, t  f lter  s never used to
 * f nd t  next doc  D:  's only used to f lter out t  doc  Ds returned by t  query's
 * Doc dSet erator. T   s useful w n   need to have a conjunct on bet en a query that can
 * qu ckly  erate through doc  Ds (eg. a post ng l st), and an expens ve f lter (eg. a f lter based
 * on t  values stored  n a CSF).
 *
 * For example, let say   want to bu ld a query that returns all docs that have at least 100 faves.
 *   1. One opt on  s to go w h t  [m n_faves 100] query. T  would be very expens ve though,
 *      because t  query would have to walk through every doc  n t  seg nt and for each one of
 *      t m   would have to extract t  number of faves from t  forward  ndex.
 *   2. Anot r opt on  s to go w h a conjunct on bet en t  query and t  HAS_ENGAGEMENT f lter:
 *      (+[m n_faves 100] +[cac d_f lter has_engage nts]). T  HAS_ENGAGEMENT f lter could
 *      traverse t  doc  D space faster ( f  's backed by a post ng l st). But t  approach would
 *      st ll be slow, because as soon as t  HAS_ENGAGEMENT f lter f nds a doc  D, t  conjunct on
 *      scorer would tr gger an advance(doc D) call on t  m n_faves part of t  query, wh ch has
 *      t  sa  problem as t  f rst opt on.
 *   3. F nally, a better opt on for t  part cular case would be to dr ve by t  HAS_ENGAGEMENT
 *      f lter (because   can qu ckly jump over all docs that do not have any engage nt), and use
 *      t  m n_faves f lter as a post-process ng step, on a much smaller set of docs.
 */
publ c class F lteredQuery extends Query {
  /**
   * A doc  D pred cate that determ nes  f t  g ven doc  D should be accepted.
   */
  @Funct onal nterface
  publ c stat c  nterface Doc dF lter {
    /**
     * Determ nes  f t  g ven doc  D should be accepted.
     */
    boolean accept( nt doc d) throws  OExcept on;
  }

  /**
   * A factory for creat ng Doc dF lter  nstances based on a g ven LeafReaderContext  nstance.
   */
  @Funct onal nterface
  publ c stat c  nterface Doc dF lterFactory {
    /**
     * Returns a Doc dF lter  nstance for t  g ven LeafReaderContext  nstance.
     */
    Doc dF lter getDoc dF lter(LeafReaderContext context) throws  OExcept on;
  }

  pr vate stat c class F lteredQueryDoc dSet erator extends Doc dSet erator {
    pr vate f nal Doc dSet erator queryScorer erator;
    pr vate f nal Doc dF lter doc dF lter;

    publ c F lteredQueryDoc dSet erator(
        Doc dSet erator queryScorer erator, Doc dF lter doc dF lter) {
      t .queryScorer erator = Precond  ons.c ckNotNull(queryScorer erator);
      t .doc dF lter = Precond  ons.c ckNotNull(doc dF lter);
    }

    @Overr de
    publ c  nt doc D() {
      return queryScorer erator.doc D();
    }

    @Overr de
    publ c  nt nextDoc() throws  OExcept on {
       nt doc d;
      do {
        doc d = queryScorer erator.nextDoc();
      } wh le (doc d != NO_MORE_DOCS && !doc dF lter.accept(doc d));
      return doc d;
    }

    @Overr de
    publ c  nt advance( nt target) throws  OExcept on {
       nt doc d = queryScorer erator.advance(target);
       f (doc d == NO_MORE_DOCS || doc dF lter.accept(doc d)) {
        return doc d;
      }
      return nextDoc();
    }

    @Overr de
    publ c long cost() {
      return queryScorer erator.cost();
    }
  }

  pr vate stat c class F lteredQueryScorer extends Scorer {
    pr vate f nal Scorer queryScorer;
    pr vate f nal Doc dF lter doc dF lter;

    publ c F lteredQueryScorer(  ght   ght, Scorer queryScorer, Doc dF lter doc dF lter) {
      super(  ght);
      t .queryScorer = Precond  ons.c ckNotNull(queryScorer);
      t .doc dF lter = Precond  ons.c ckNotNull(doc dF lter);
    }

    @Overr de
    publ c  nt doc D() {
      return queryScorer.doc D();
    }

    @Overr de
    publ c float score() throws  OExcept on {
      return queryScorer.score();
    }

    @Overr de
    publ c Doc dSet erator  erator() {
      return new F lteredQueryDoc dSet erator(queryScorer. erator(), doc dF lter);
    }

    @Overr de
    publ c float getMaxScore( nt upTo) throws  OExcept on {
      return queryScorer.getMaxScore(upTo);
    }
  }

  pr vate stat c class F lteredQuery  ght extends   ght {
    pr vate f nal   ght query  ght;
    pr vate f nal Doc dF lterFactory doc dF lterFactory;

    publ c F lteredQuery  ght(
        F lteredQuery query,   ght query  ght, Doc dF lterFactory doc dF lterFactory) {
      super(query);
      t .query  ght = Precond  ons.c ckNotNull(query  ght);
      t .doc dF lterFactory = Precond  ons.c ckNotNull(doc dF lterFactory);
    }

    @Overr de
    publ c vo d extractTerms(Set<Term> terms) {
      query  ght.extractTerms(terms);
    }

    @Overr de
    publ c Explanat on expla n(LeafReaderContext context,  nt doc) throws  OExcept on {
      return query  ght.expla n(context, doc);
    }

    @Overr de
    publ c Scorer scorer(LeafReaderContext context) throws  OExcept on {
      Scorer queryScorer = query  ght.scorer(context);
       f (queryScorer == null) {
        return null;
      }

      return new F lteredQueryScorer(t , queryScorer, doc dF lterFactory.getDoc dF lter(context));
    }

    @Overr de
    publ c boolean  sCac able(LeafReaderContext ctx) {
      return query  ght. sCac able(ctx);
    }
  }

  pr vate f nal Query query;
  pr vate f nal Doc dF lterFactory doc dF lterFactory;

  publ c F lteredQuery(Query query, Doc dF lterFactory doc dF lterFactory) {
    t .query = Precond  ons.c ckNotNull(query);
    t .doc dF lterFactory = Precond  ons.c ckNotNull(doc dF lterFactory);
  }

  publ c Query getQuery() {
    return query;
  }

  @Overr de
  publ c Query rewr e( ndexReader reader) throws  OExcept on {
    Query rewr tenQuery = query.rewr e(reader);
     f (rewr tenQuery != query) {
      return new F lteredQuery(rewr tenQuery, doc dF lterFactory);
    }
    return t ;
  }

  @Overr de
  publ c  nt hashCode() {
    return query.hashCode() * 13 + doc dF lterFactory.hashCode();
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof F lteredQuery)) {
      return false;
    }

    F lteredQuery f lteredQuery = F lteredQuery.class.cast(obj);
    return query.equals(f lteredQuery.query)
        && doc dF lterFactory.equals(f lteredQuery.doc dF lterFactory);
  }

  @Overr de
  publ c Str ng toStr ng(Str ng f eld) {
    Str ngBu lder sb = new Str ngBu lder();
    sb.append("F lteredQuery(")
        .append(query)
        .append(" -> ")
        .append(doc dF lterFactory)
        .append(")");
    return sb.toStr ng();
  }

  @Overr de
  publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost)
      throws  OExcept on {
      ght query  ght = Precond  ons.c ckNotNull(query.create  ght(searc r, scoreMode, boost));
    return new F lteredQuery  ght(t , query  ght, doc dF lterFactory);
  }
}
