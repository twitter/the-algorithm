package com.tw ter.search.common.query;

 mport java. o. OExcept on;
 mport java.ut l.ArrayL st;
 mport java.ut l. erator;
 mport java.ut l.L st;
 mport java.ut l.Objects;
 mport java.ut l.Set;
 mport java.ut l.stream.Collectors;

 mport org.apac .lucene. ndex.F lteredTermsEnum;
 mport org.apac .lucene. ndex. ndexReader;
 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene. ndex.Post ngsEnum;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene. ndex.TermState;
 mport org.apac .lucene. ndex.TermStates;
 mport org.apac .lucene. ndex.Terms;
 mport org.apac .lucene. ndex.TermsEnum;
 mport org.apac .lucene.search.BooleanClause.Occur;
 mport org.apac .lucene.search.BooleanQuery;
 mport org.apac .lucene.search.BulkScorer;
 mport org.apac .lucene.search.ConstantScoreQuery;
 mport org.apac .lucene.search.ConstantScoreScorer;
 mport org.apac .lucene.search.ConstantScore  ght;
 mport org.apac .lucene.search.Doc dSet;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.Mult TermQuery;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.Scorer;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.TermQuery;
 mport org.apac .lucene.search.  ght;
 mport org.apac .lucene.ut l.Attr buteS ce;
 mport org.apac .lucene.ut l.BytesRef;
 mport org.apac .lucene.ut l.Doc dSetBu lder;

 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.base. ndexedNu r cF eldSett ngs;
 mport com.tw ter.search.common.ut l.analys s.LongTermAttr bute mpl;
 mport com.tw ter.search.common.ut l.analys s.SortableLongTermAttr bute mpl;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;

/**
 * An extens on of Lucene's Mult TermQuery wh ch creates a d sjunct on of
 * long  D terms. Lucene tr es to rewr e t  Query depend ng on t  number
 * of clauses to perform as eff c ently as poss ble.
 */
publ c class  DD sjunct onQuery extends Mult TermQuery {
  pr vate f nal L st<Long>  ds;
  pr vate f nal boolean useOrderPreserv ngEncod ng;

  /** Creates a new  DD sjunct onQuery  nstance. */
  publ c  DD sjunct onQuery(L st<Long>  ds, Str ng f eld,  mmutableSc ma nterface sc maSnapshot)
      throws QueryParserExcept on {
    super(f eld);
    t . ds =  ds;

    setRewr e thod(new Rewr e());

     f (!sc maSnapshot.hasF eld(f eld)) {
      throw new QueryParserExcept on(
          "Tr ed to search a f eld wh ch does not ex st  n sc ma: " + f eld);
    }

     ndexedNu r cF eldSett ngs nu r cF eldSett ngs =
        sc maSnapshot.getF eld nfo(f eld).getF eldType().getNu r cF eldSett ngs();

     f (nu r cF eldSett ngs == null) {
      throw new QueryParserExcept on("Requested  d f eld  s not nu r cal: " + f eld);
    }

    t .useOrderPreserv ngEncod ng = nu r cF eldSett ngs. sUseSortableEncod ng();
  }

  /**
   * Work around for an  ssue w re LongTerms are not val d utf8, so call ng
   * toStr ng on any TermQuery conta n ng a LongTerm may cause except ons.
   */
  pr vate class Rewr e extends Rewr e thod {
    @Overr de
    publ c Query rewr e( ndexReader reader, Mult TermQuery query) throws  OExcept on {
      Query result = new Mult TermQueryConstantScoreWrapper(
          ( DD sjunct onQuery) query, useOrderPreserv ngEncod ng);
      return result;
    }
  }

  @Overr de
  protected TermsEnum getTermsEnum(f nal Terms terms, Attr buteS ce atts) throws  OExcept on {
    f nal  erator<Long>   = t . ds. erator();
    f nal TermsEnum termsEnum = terms. erator();

    return new F lteredTermsEnum(termsEnum) {
      pr vate f nal BytesRef term = useOrderPreserv ngEncod ng
          ? SortableLongTermAttr bute mpl.newBytesRef()
          : LongTermAttr bute mpl.newBytesRef();

      @Overr de protected AcceptStatus accept(BytesRef term) throws  OExcept on {
        return AcceptStatus.YES;
      }

      @Overr de publ c BytesRef next() throws  OExcept on {
        wh le ( .hasNext()) {
          Long longTerm =  .next();
           f (useOrderPreserv ngEncod ng) {
            SortableLongTermAttr bute mpl.copyLongToBytesRef(term, longTerm);
          } else {
            LongTermAttr bute mpl.copyLongToBytesRef(term, longTerm);
          }
           f (termsEnum.seekExact(term)) {
            return term;
          }
        }

        return null;
      }
    };
  }

  @Overr de
  publ c Str ng toStr ng(Str ng f eld) {
    Str ngBu lder bu lder = new Str ngBu lder();
    bu lder.append(" DD sjunct on[").append(t .f eld).append(":");
    for (Long  d : t . ds) {
      bu lder.append( d);
      bu lder.append(",");
    }
    bu lder.setLength(bu lder.length() - 1);
    bu lder.append("]");
    return bu lder.toStr ng();
  }

  pr vate stat c class TermQueryW hToStr ng extends TermQuery {
    pr vate f nal boolean useOrderPreserv ngEncod ng;

    publ c TermQueryW hToStr ng(Term t, TermStates states, boolean useOrderPreserv ngEncod ng) {
      super(t, states);
      t .useOrderPreserv ngEncod ng = useOrderPreserv ngEncod ng;
    }

    @Overr de
    publ c Str ng toStr ng(Str ng f eld) {
      Str ngBu lder buffer = new Str ngBu lder();
       f (!getTerm().f eld().equals(f eld)) {
        buffer.append(getTerm().f eld());
        buffer.append(":");
      }
      long longTerm;
      BytesRef termBytes = getTerm().bytes();
       f (useOrderPreserv ngEncod ng) {
        longTerm = SortableLongTermAttr bute mpl.copyBytesRefToLong(termBytes);
      } else {
        longTerm = LongTermAttr bute mpl.copyBytesRefToLong(termBytes);
      }
      buffer.append(longTerm);
      return buffer.toStr ng();
    }
  }

  /**
   * T  class prov des t  funct onal y beh nd {@l nk Mult TermQuery#CONSTANT_SCORE_REWR TE}.
   *   tr es to rewr e per-seg nt as a boolean query that returns a constant score and ot rw se
   * f lls a Doc dSet w h matc s and bu lds a Scorer on top of t  Doc dSet.
   */
  stat c f nal class Mult TermQueryConstantScoreWrapper extends Query {
    // d sable t  rewr e opt on wh ch w ll scan all post ng l sts sequent ally and perform
    // t   ntersect on us ng a temporary Doc dSet.  n earlyb rd t  mode  s slo r than a "normal"
    // d sjunct ve BooleanQuery, due to early term nat on and t  fact that everyth ng  s  n  mory.
    pr vate stat c f nal  nt BOOLEAN_REWR TE_TERM_COUNT_THRESHOLD = 3000;

    pr vate stat c class TermAndState {
      pr vate f nal BytesRef term;
      pr vate f nal TermState state;
      pr vate f nal  nt docFreq;
      pr vate f nal long totalTermFreq;

      TermAndState(BytesRef term, TermState state,  nt docFreq, long totalTermFreq) {
        t .term = term;
        t .state = state;
        t .docFreq = docFreq;
        t .totalTermFreq = totalTermFreq;
      }
    }

    pr vate stat c class   ghtOrDoc dSet {
      pr vate f nal   ght   ght;
      pr vate f nal Doc dSet doc dSet;

        ghtOrDoc dSet(  ght   ght) {
        t .  ght = Objects.requ reNonNull(  ght);
        t .doc dSet = null;
      }

        ghtOrDoc dSet(Doc dSet doc dSet) {
        t .doc dSet = doc dSet;
        t .  ght = null;
      }
    }

    protected f nal  DD sjunct onQuery query;
    pr vate f nal boolean useOrderPreserv ngEncod ng;

    /**
     * Wrap a {@l nk Mult TermQuery} as a F lter.
     */
    protected Mult TermQueryConstantScoreWrapper(
         DD sjunct onQuery query,
        boolean useOrderPreserv ngEncod ng) {
      t .query = query;
      t .useOrderPreserv ngEncod ng = useOrderPreserv ngEncod ng;
    }

    @Overr de
    publ c Str ng toStr ng(Str ng f eld) {
      // query.toStr ng should be ok for t  f lter, too,  f t  query boost  s 1.0f
      return query.toStr ng(f eld);
    }

    @Overr de
    publ c boolean equals(Object obj) {
       f (!(obj  nstanceof Mult TermQueryConstantScoreWrapper)) {
        return false;
      }

      return query.equals(Mult TermQueryConstantScoreWrapper.class.cast(obj).query);
    }

    @Overr de
    publ c  nt hashCode() {
      return query == null ? 0 : query.hashCode();
    }

    /** Returns t  f eld na  for t  query */
    publ c Str ng getF eld() {
      return query.getF eld();
    }

    pr vate L st<Long> get Ds() {
      return query. ds;
    }

    @Overr de
    publ c   ght create  ght(
        f nal  ndexSearc r searc r,
        f nal ScoreMode scoreMode,
        f nal float boost) throws  OExcept on {
      return new ConstantScore  ght(t , boost) {
        /** Try to collect terms from t  g ven terms enum and return true  ff all
         *  terms could be collected.  f {@code false}  s returned, t  enum  s
         *  left pos  oned on t  next term. */
        pr vate boolean collectTerms(LeafReaderContext context,
                                     TermsEnum termsEnum,
                                     L st<TermAndState> terms) throws  OExcept on {
          f nal  nt threshold = Math.m n(BOOLEAN_REWR TE_TERM_COUNT_THRESHOLD,
                                         BooleanQuery.getMaxClauseCount());
          for ( nt   = 0;   < threshold; ++ ) {
            f nal BytesRef term = termsEnum.next();
             f (term == null) {
              return true;
            }
            TermState state = termsEnum.termState();
            terms.add(new TermAndState(BytesRef.deepCopyOf(term),
                                       state,
                                       termsEnum.docFreq(),
                                       termsEnum.totalTermFreq()));
          }
          return termsEnum.next() == null;
        }

        /**
         * On t  g ven leaf context, try to e  r rewr e to a d sjunct on  f
         * t re are few terms, or bu ld a Doc dSet conta n ng match ng docs.
         */
        pr vate   ghtOrDoc dSet rewr e(LeafReaderContext context)
            throws  OExcept on {
          f nal Terms terms = context.reader().terms(query.getF eld());
           f (terms == null) {
            // f eld does not ex st
            return new   ghtOrDoc dSet((Doc dSet) null);
          }

          f nal TermsEnum termsEnum = query.getTermsEnum(terms);
          assert termsEnum != null;

          Post ngsEnum docs = null;

          f nal L st<TermAndState> collectedTerms = new ArrayL st<>();
           f (collectTerms(context, termsEnum, collectedTerms)) {
            // bu ld a boolean query
            BooleanQuery.Bu lder bqBu lder = new BooleanQuery.Bu lder();
            for (TermAndState t : collectedTerms) {
              f nal TermStates termStates = new TermStates(searc r.getTopReaderContext());
              termStates.reg ster(t.state, context.ord, t.docFreq, t.totalTermFreq);
              f nal Term term = new Term(query.getF eld(), t.term);
              bqBu lder.add(
                  new TermQueryW hToStr ng(term, termStates, useOrderPreserv ngEncod ng),
                  Occur.SHOULD);
            }
            Query q = BoostUt ls.maybeWrap nBoostQuery(
                new ConstantScoreQuery(bqBu lder.bu ld()), score());
            return new   ghtOrDoc dSet(
                searc r.rewr e(q).create  ght(searc r, scoreMode, boost));
          }

          // Too many terms: go back to t  terms   already collected and start bu ld ng
          // t  Doc dSet
          Doc dSetBu lder bu lder = new Doc dSetBu lder(context.reader().maxDoc());
           f (!collectedTerms. sEmpty()) {
            TermsEnum termsEnum2 = terms. erator();
            for (TermAndState t : collectedTerms) {
              termsEnum2.seekExact(t.term, t.state);
              docs = termsEnum2.post ngs(docs, Post ngsEnum.NONE);
              bu lder.add(docs);
            }
          }

          // T n keep f ll ng t  Doc dSet w h rema n ng terms
          do {
            docs = termsEnum.post ngs(docs, Post ngsEnum.NONE);
            bu lder.add(docs);
          } wh le (termsEnum.next() != null);

          return new   ghtOrDoc dSet(bu lder.bu ld());
        }

        pr vate Scorer scorer(Doc dSet set) throws  OExcept on {
           f (set == null) {
            return null;
          }
          f nal Doc dSet erator d s  = set. erator();
           f (d s  == null) {
            return null;
          }
          return new ConstantScoreScorer(t , score(), ScoreMode.COMPLETE_NO_SCORES, d s );
        }

        @Overr de
        publ c BulkScorer bulkScorer(LeafReaderContext context) throws  OExcept on {
          f nal   ghtOrDoc dSet   ghtOrDoc dSet = rewr e(context);
           f (  ghtOrDoc dSet.  ght != null) {
            return   ghtOrDoc dSet.  ght.bulkScorer(context);
          } else {
            f nal Scorer scorer = scorer(  ghtOrDoc dSet.doc dSet);
             f (scorer == null) {
              return null;
            }
            return new DefaultBulkScorer(scorer);
          }
        }

        @Overr de
        publ c Scorer scorer(LeafReaderContext context) throws  OExcept on {
          f nal   ghtOrDoc dSet   ghtOrDoc dSet = rewr e(context);
           f (  ghtOrDoc dSet.  ght != null) {
            return   ghtOrDoc dSet.  ght.scorer(context);
          } else {
            return scorer(  ghtOrDoc dSet.doc dSet);
          }
        }

        @Overr de
        publ c vo d extractTerms(Set<Term> terms) {
          terms.addAll(get Ds()
              .stream()
              .map( d -> new Term(getF eld(), LongTermAttr bute mpl.copy ntoNewBytesRef( d)))
              .collect(Collectors.toSet()));
        }

        @Overr de
        publ c boolean  sCac able(LeafReaderContext ctx) {
          return false;
        }
      };
    }
  }
}
