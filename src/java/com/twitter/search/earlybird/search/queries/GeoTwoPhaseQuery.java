package com.tw ter.search.earlyb rd.search.quer es;

 mport java. o. OExcept on;
 mport java.ut l.Set;

 mport org.apac .lucene. ndex. ndexReader;
 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene.search.ConstantScoreQuery;
 mport org.apac .lucene.search.ConstantScoreScorer;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.search.Explanat on;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.Scorer;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.TwoPhase erator;
 mport org.apac .lucene.search.  ght;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.search.Term nat onTracker;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;


publ c class GeoTwoPhaseQuery extends Query {
  pr vate stat c f nal boolean ENABLE_GEO_EARLY_TERM NAT ON =
          Earlyb rdConf g.getBool("early_term nate_geo_searc s", true);

  pr vate stat c f nal  nt GEO_T MEOUT_OVERR DE =
          Earlyb rdConf g.get nt("early_term nate_geo_searc s_t  out_overr de", -1);

  // How many geo searc s are early term nated due to t  out.
  pr vate stat c f nal SearchCounter GEO_SEARCH_T MEOUT_COUNT =
      SearchCounter.export("geo_search_t  out_count");

  pr vate f nal SecondPhaseDocAccepter accepter;
  pr vate f nal Term nat onTracker term nat onTracker;
  pr vate f nal ConstantScoreQuery query;

  publ c GeoTwoPhaseQuery(
      Query query, SecondPhaseDocAccepter accepter, Term nat onTracker term nat onTracker) {
    t .accepter = accepter;
    t .term nat onTracker = term nat onTracker;

    t .query = new ConstantScoreQuery(query);
  }

  @Overr de
  publ c Query rewr e( ndexReader reader) throws  OExcept on {
    Query rewr ten = query.getQuery().rewr e(reader);
     f (rewr ten != query.getQuery()) {
      return new GeoTwoPhaseQuery(rewr ten, accepter, term nat onTracker);
    }

    return t ;
  }

  @Overr de
  publ c  nt hashCode() {
    return query.hashCode();
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof GeoTwoPhaseQuery)) {
      return false;
    }
    GeoTwoPhaseQuery that = (GeoTwoPhaseQuery) obj;
    return query.equals(that.query)
        && accepter.equals(that.accepter)
        && term nat onTracker.equals(that.term nat onTracker);
  }

  @Overr de
  publ c Str ng toStr ng(Str ng f eld) {
    return new Str ngBu lder("GeoTwoPhaseQuery(")
      .append("Accepter(")
      .append(accepter.toStr ng())
      .append(") Geohas s(")
      .append(query.getQuery().toStr ng(f eld))
      .append("))")
      .toStr ng();
  }

  @Overr de
  publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost)
      throws  OExcept on {
      ght  nner  ght = query.create  ght(searc r, scoreMode, boost);
    return new GeoTwoPhase  ght(t ,  nner  ght, accepter, term nat onTracker);
  }

  pr vate stat c f nal class GeoTwoPhase  ght extends   ght {
    pr vate f nal   ght  nner  ght;
    pr vate f nal SecondPhaseDocAccepter accepter;
    pr vate f nal Term nat onTracker term nat onTracker;

    pr vate GeoTwoPhase  ght(
        Query query,
          ght  nner  ght,
        SecondPhaseDocAccepter accepter,
        Term nat onTracker term nat onTracker) {
      super(query);
      t . nner  ght =  nner  ght;
      t .accepter = accepter;
      t .term nat onTracker = term nat onTracker;
    }

    @Overr de
    publ c vo d extractTerms(Set<Term> terms) {
       nner  ght.extractTerms(terms);
    }

    @Overr de
    publ c Explanat on expla n(LeafReaderContext context,  nt doc) throws  OExcept on {
      return  nner  ght.expla n(context, doc);
    }

    @Overr de
    publ c Scorer scorer(LeafReaderContext context) throws  OExcept on {
      Scorer  nnerScorer =  nner  ght.scorer(context);
       f ( nnerScorer == null) {
        return null;
      }
       f (ENABLE_GEO_EARLY_TERM NAT ON
          && (term nat onTracker == null || !term nat onTracker.useLastSearc dDoc dOnT  out())) {
         nnerScorer = new ConstantScoreScorer(
            t ,
            0.0f,
            ScoreMode.COMPLETE_NO_SCORES,
            new T  dDoc dSet erator( nnerScorer. erator(),
                                      term nat onTracker,
                                      GEO_T MEOUT_OVERR DE,
                                      GEO_SEARCH_T MEOUT_COUNT));
      }

      accepter. n  al ze(context);
      return new GeoTwoPhaseScorer(t ,  nnerScorer, accepter);
    }

    @Overr de
    publ c boolean  sCac able(LeafReaderContext ctx) {
      return  nner  ght. sCac able(ctx);
    }
  }

  pr vate stat c f nal class GeoTwoPhaseScorer extends Scorer {
    pr vate f nal Scorer  nnerScorer;
    pr vate f nal SecondPhaseDocAccepter accepter;

    pr vate GeoTwoPhaseScorer(  ght   ght, Scorer  nnerScorer, SecondPhaseDocAccepter accepter) {
      super(  ght);
      t . nnerScorer =  nnerScorer;
      t .accepter = accepter;
    }

    @Overr de
    publ c TwoPhase erator twoPhase erator() {
      return new TwoPhase erator( nnerScorer. erator()) {
        @Overr de
        publ c boolean matc s() throws  OExcept on {
          return c ckDocExpens ve( nnerScorer.doc D());
        }

        @Overr de
        publ c float matchCost() {
          return 0.0f;
        }
      };
    }

    @Overr de
    publ c  nt doc D() {
      return  erator().doc D();
    }

    @Overr de
    publ c float score() throws  OExcept on {
      return  nnerScorer.score();
    }

    @Overr de
    publ c Doc dSet erator  erator() {
      return new Doc dSet erator() {
        pr vate  nt doNext( nt start ngDoc d) throws  OExcept on {
           nt doc d = start ngDoc d;
          wh le ((doc d != NO_MORE_DOCS) && !c ckDocExpens ve(doc d)) {
            doc d =  nnerScorer. erator().nextDoc();
          }
          return doc d;
        }

        @Overr de
        publ c  nt doc D() {
          return  nnerScorer. erator().doc D();
        }

        @Overr de
        publ c  nt nextDoc() throws  OExcept on {
          return doNext( nnerScorer. erator().nextDoc());
        }

        @Overr de
        publ c  nt advance( nt target) throws  OExcept on {
          return doNext( nnerScorer. erator().advance(target));
        }

        @Overr de
        publ c long cost() {
          return 2 *  nnerScorer. erator().cost();
        }
      };
    }

    @Overr de
    publ c float getMaxScore( nt upTo) throws  OExcept on {
      return  nnerScorer.getMaxScore(upTo);
    }

    pr vate boolean c ckDocExpens ve( nt doc) throws  OExcept on {
      return accepter.accept(doc);
    }
  }

  publ c abstract stat c class SecondPhaseDocAccepter {
    /**
     *  n  al zes t  accepter w h t  g ven reader context.
     */
    publ c abstract vo d  n  al ze(LeafReaderContext context) throws  OExcept on;

    /**
     * Determ nes  f t  g ven doc  D  s accepted by t  accepter.
     */
    publ c abstract boolean accept( nt doc) throws  OExcept on;

    /**
     * Returns a str ng descr pt on for t  SecondPhaseDocAccepter  nstance.
     */
    publ c abstract Str ng toStr ng();
  }

  publ c stat c f nal SecondPhaseDocAccepter ALL_DOCS_ACCEPTER = new SecondPhaseDocAccepter() {
    @Overr de
    publ c vo d  n  al ze(LeafReaderContext context) { }

    @Overr de
    publ c boolean accept( nt doc) {
      return true;
    }

    @Overr de
    publ c Str ng toStr ng() {
      return "AllDocsAccepter";
    }
  };
}
