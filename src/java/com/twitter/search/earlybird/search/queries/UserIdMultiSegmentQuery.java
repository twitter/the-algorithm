package com.tw ter.search.earlyb rd.search.quer es;

 mport java. o. OExcept on;
 mport java.ut l.Arrays;
 mport java.ut l.HashMap;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;
 mport java.ut l.concurrent.T  Un ;
 mport java.ut l.stream.Collectors;
 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;

 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene. ndex.Terms;
 mport org.apac .lucene. ndex.TermsEnum;
 mport org.apac .lucene.search.BooleanClause;
 mport org.apac .lucene.search.BooleanQuery;
 mport org.apac .lucene.search.BulkScorer;
 mport org.apac .lucene.search.ConstantScoreQuery;
 mport org.apac .lucene.search.ConstantScore  ght;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.Scorer;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.  ght;
 mport org.apac .lucene.ut l.BytesRef;

 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common.dec der.Dec derUt l;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchT  r;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.common.query.H Attr bute lper;
 mport com.tw ter.search.common.query. DD sjunct onQuery;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.base. ndexedNu r cF eldSett ngs;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.search.term nat on.QueryT  out;
 mport com.tw ter.search.common.ut l.analys s.LongTermAttr bute mpl;
 mport com.tw ter.search.common.ut l.analys s.SortableLongTermAttr bute mpl;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntData;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted. nverted ndex;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted.Mult Seg ntTermD ct onary;
 mport com.tw ter.search.earlyb rd.part  on.Mult Seg ntTermD ct onaryManager;
 mport com.tw ter.search.earlyb rd.queryparser.Earlyb rdQuery lper;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;

/**
 * A var ant of a mult -term  D d sjunct on query (s m lar to {@l nk User dMult Seg ntQuery}),
 * that also uses a {@l nk Mult Seg ntTermD ct onary} w re ava lable, for more eff c ent
 * term lookups for quer es that span mult ple seg nts.
 *
 * By default, a  DD sjunct onQuery (or Lucene's Mult TermQuery), does a term d ct onary lookup
 * for all of t  terms  n  s d sjunct on, and   does   once for each seg nt (or Atom cReader)
 * that t  query  s search ng.
 * T   ans that w n t  term d ct onary  s large, and t  term lookups are expens ve, and w n
 *   are search ng mult ple seg nts, t  query needs to make num_terms * num_seg nts expens ve
 * term d ct onary lookups.
 *
 * W h t   lp of a Mult Seg ntTermD ct onary, t  mult -term d sjunct on query  mple ntat on
 * only does one lookup for all of t  seg nts managed by t  Mult Seg ntTermD ct onary.
 *  f a seg nt  s not supported by t  Mult Seg ntTermD ct onary (e.g.  f  's not opt m zed yet),
 * a regular lookup  n that seg nt's term d ct onary w ll be perfor d.
 *
 * Usually,   w ll make 'num_terms' lookups  n t  current, un-opt m zed seg nt, and t n  f
 * more seg nts need to be searc d,   w ll make anot r 'num_terms' lookups, once for all of
 * t  rema n ng seg nts.
 *
 * W n perform ng lookups  n t  Mult Seg ntTermD ct onary, for each supported seg nt,   save
 * a l st of term ds from that seg nt for all t  searc d terms that appear  n that seg nt.
 *
 * For example, w n query ng for User dMult Seg ntQuery w h user  ds: {1L, 2L, 3L} and
 * seg nts: {1, 2}, w re seg nt 1 has user  ds {1L, 2L}  ndexed under term ds {100, 200},
 * and seg nt 2 has user  ds {1L, 2L, 3L}  ndexed under term ds {200, 300, 400},   w ll bu ld
 * up t  follow ng map once:
 *   seg nt1 -> [100, 200]
 *   seg nt2 -> [200, 300, 400]
 */
publ c class User dMult Seg ntQuery extends Query {
  @V s bleForTest ng
  publ c stat c f nal SearchT  rStats TERM_LOOKUP_STATS =
      SearchT  rStats.export("mult _seg nt_query_term_lookup", T  Un .NANOSECONDS, false);
  publ c stat c f nal SearchT  rStats QUERY_FROM_PRECOMPUTED =
      SearchT  rStats.export("mult _seg nt_query_from_precomputed", T  Un .NANOSECONDS, false);
  publ c stat c f nal SearchT  rStats QUERY_REGULAR =
      SearchT  rStats.export("mult _seg nt_query_regular", T  Un .NANOSECONDS, false);

  @V s bleForTest ng
  publ c stat c f nal SearchCounter USED_MULT _SEGMENT_TERM_D CT ONARY_COUNT = SearchCounter.export(
      "user_ d_mult _seg nt_query_used_mult _seg nt_term_d ct onary_count");
  @V s bleForTest ng
  publ c stat c f nal SearchCounter USED_OR G NAL_TERM_D CT ONARY_COUNT = SearchCounter.export(
      "user_ d_mult _seg nt_query_used_or g nal_term_d ct onary_count");

  pr vate stat c f nal SearchCounter NEW_QUERY_COUNT =
      SearchCounter.export("user_ d_mult _seg nt_new_query_count");
  pr vate stat c f nal SearchCounter OLD_QUERY_COUNT =
      SearchCounter.export("user_ d_mult _seg nt_old_query_count");

  pr vate stat c f nal HashMap<Str ng, SearchCounter> QUERY_COUNT_BY_QUERY_NAME = new HashMap<>();
  pr vate stat c f nal HashMap<Str ng, SearchCounter> QUERY_COUNT_BY_F ELD_NAME = new HashMap<>();

  pr vate stat c f nal Str ng DEC DER_KEY_PREF X = "use_mult _seg nt_ d_d sjunct on_quer es_ n_";

  /**
   * Returns a new user  D d sjunct on query.
   *
   * @param  ds T  user  Ds.
   * @param f eld T  f eld stor ng t  user  Ds.
   * @param sc maSnapshot A snapshot of earlyb rd's sc ma.
   * @param mult Seg ntTermD ct onaryManager T  manager for t  term d ct onar es that span
   *                                          mult ple seg nts.
   * @param dec der T  dec der.
   * @param earlyb rdCluster T  earlyb rd cluster.
   * @param ranks T  h  attr but on ranks to be ass gned to every user  D.
   * @param h Attr bute lper T   lper that tracks h  attr but ons.
   * @param queryT  out T  t  out to be enforced on t  query.
   * @return A new user  D d sjunct on query.
   */
  publ c stat c Query create dD sjunct onQuery(
      Str ng queryNa ,
      L st<Long>  ds,
      Str ng f eld,
       mmutableSc ma nterface sc maSnapshot,
      Mult Seg ntTermD ct onaryManager mult Seg ntTermD ct onaryManager,
      Dec der dec der,
      Earlyb rdCluster earlyb rdCluster,
      L st< nteger> ranks,
      @Nullable H Attr bute lper h Attr bute lper,
      @Nullable QueryT  out queryT  out) throws QueryParserExcept on {
    QUERY_COUNT_BY_QUERY_NAME.compute fAbsent(queryNa , na  ->
        SearchCounter.export("mult _seg nt_query_na _" + na )). ncre nt();
    QUERY_COUNT_BY_F ELD_NAME.compute fAbsent(f eld, na  ->
        SearchCounter.export("mult _seg nt_query_count_for_f eld_" + na )). ncre nt();

     f (Dec derUt l. sAva lableForRandomRec p ent(dec der, getDec derNa (earlyb rdCluster))) {
      NEW_QUERY_COUNT. ncre nt();
      Mult Seg ntTermD ct onary mult Seg ntTermD ct onary =
          mult Seg ntTermD ct onaryManager.getMult Seg ntTermD ct onary(f eld);
      return new User dMult Seg ntQuery(
           ds,
          f eld,
          sc maSnapshot,
          mult Seg ntTermD ct onary,
          ranks,
          h Attr bute lper,
          queryT  out);
    } else {
      OLD_QUERY_COUNT. ncre nt();
      return new  DD sjunct onQuery( ds, f eld, sc maSnapshot);
    }
  }

  @V s bleForTest ng
  publ c stat c Str ng getDec derNa (Earlyb rdCluster earlyb rdCluster) {
    return DEC DER_KEY_PREF X + earlyb rdCluster.na ().toLo rCase();
  }

  pr vate f nal boolean useOrderPreserv ngEncod ng;
  pr vate f nal H Attr bute lper h Attr bute lper;
  pr vate f nal QueryT  out queryT  out;
  pr vate f nal Mult Seg ntTermD ct onary mult Seg ntTermD ct onary;
  pr vate f nal Sc ma.F eld nfo f eld nfo;
  pr vate f nal Str ng f eld;
  pr vate f nal L st<Long>  ds;

  pr vate f nal L st< nteger> ranks;
  // For each seg nt w re   have a mult -seg nt term d ct onary, t  map w ll conta n t 
  // term ds of all t  terms that actually appear  n that seg nt's  ndex.
  @Nullable
  pr vate Map< nverted ndex, L st<TermRankPa r>> term dsPerSeg nt;

  // A wrap class  lps to assoc ate term d w h correspond ng search operator rank  f ex st
  pr vate f nal class TermRankPa r {
    pr vate f nal  nt term d;
    pr vate f nal  nt rank;

    TermRankPa r( nt term d,  nt rank) {
      t .term d = term d;
      t .rank = rank;
    }

    publ c  nt getTerm d() {
      return term d;
    }

    publ c  nt getRank() {
      return rank;
    }
  }

  @V s bleForTest ng
  publ c User dMult Seg ntQuery(
      L st<Long>  ds,
      Str ng f eld,
       mmutableSc ma nterface sc maSnapshot,
      Mult Seg ntTermD ct onary termD ct onary,
      L st< nteger> ranks,
      @Nullable H Attr bute lper h Attr bute lper,
      @Nullable QueryT  out queryT  out) {
    t .f eld = f eld;
    t . ds =  ds;
    t .mult Seg ntTermD ct onary = termD ct onary;
    t .ranks = ranks;
    t .h Attr bute lper = h Attr bute lper;
    t .queryT  out = queryT  out;

    // c ck  ds and ranks have sa  s ze
    Precond  ons.c ckArgu nt(ranks.s ze() == 0 || ranks.s ze() ==  ds.s ze());
    // h Attr bute lper  s not null  ff ranks  s not empty
     f (ranks.s ze() > 0) {
      Precond  ons.c ckNotNull(h Attr bute lper);
    } else {
      Precond  ons.c ckArgu nt(h Attr bute lper == null);
    }

     f (!sc maSnapshot.hasF eld(f eld)) {
      throw new  llegalStateExcept on("Tr ed to search a f eld wh ch does not ex st  n sc ma");
    }
    t .f eld nfo = Precond  ons.c ckNotNull(sc maSnapshot.getF eld nfo(f eld));

     ndexedNu r cF eldSett ngs nu r cF eldSett ngs =
        f eld nfo.getF eldType().getNu r cF eldSett ngs();
     f (nu r cF eldSett ngs == null) {
      throw new  llegalStateExcept on(" d f eld  s not nu r cal");
    }

    t .useOrderPreserv ngEncod ng = nu r cF eldSett ngs. sUseSortableEncod ng();
  }

  /**
   *  f   hasn't been bu lt yet, bu ld up t  map conta n ng term ds of all t  terms be ng
   * searc d, for all of t  seg nts that are managed by t  mult -seg nt term d ct onary.
   *
   *   only do t  once, w n   have to search t  f rst seg nt that's supported by  
   * mult -seg nt term d ct onary.
   *
   * Flow  re  s to:
   * 1. go through all t   ds be ng quer ed.
   * 2. for each  d, get t  term ds for that term  n all of t  seg nts  n t  term d ct onary
   * 3. for all of t  seg nts that have that term, add t  term d to that seg nt's l st of
   * term  ds ( n t  'term dsPerSeg nt' map).
   */
  pr vate vo d createTerm dsPerSeg nt() {
     f (term dsPerSeg nt != null) {
      // already created t  map
      return;
    }

    long start = System.nanoT  ();

    f nal BytesRef termRef = useOrderPreserv ngEncod ng
        ? SortableLongTermAttr bute mpl.newBytesRef()
        : LongTermAttr bute mpl.newBytesRef();

    term dsPerSeg nt = Maps.newHashMap();
    L st<? extends  nverted ndex> seg nt ndexes = mult Seg ntTermD ct onary.getSeg nt ndexes();

    for ( nt  dx = 0;  dx <  ds.s ze(); ++ dx) {
      long longTerm =  ds.get( dx);

       f (useOrderPreserv ngEncod ng) {
        SortableLongTermAttr bute mpl.copyLongToBytesRef(termRef, longTerm);
      } else {
        LongTermAttr bute mpl.copyLongToBytesRef(termRef, longTerm);
      }

       nt[] term ds = mult Seg ntTermD ct onary.lookupTerm ds(termRef);
      Precond  ons.c ckState(seg nt ndexes.s ze() == term ds.length,
          "Seg nt ndexes: %s, f eld: %s, term ds: %s",
          seg nt ndexes.s ze(), f eld, term ds.length);

      for ( nt  ndex d = 0;  ndex d < term ds.length;  ndex d++) {
         nt term d = term ds[ ndex d];
         f (term d != Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND) {
           nverted ndex f eld ndex = seg nt ndexes.get( ndex d);

          L st<TermRankPa r> term dsL st = term dsPerSeg nt.get(f eld ndex);
           f (term dsL st == null) {
            term dsL st = L sts.newArrayL st();
            term dsPerSeg nt.put(f eld ndex, term dsL st);
          }
          term dsL st.add(new TermRankPa r(
              term d, ranks.s ze() > 0 ? ranks.get( dx) : -1));
        }
      }
    }

    long elapsed = System.nanoT  () - start;
    TERM_LOOKUP_STATS.t  r ncre nt(elapsed);
  }

  @Overr de
  publ c   ght create  ght( ndexSearc r searc r, ScoreMode scoreMode, float boost) {
    return new User dMult Seg ntQuery  ght(searc r, scoreMode, boost);
  }

  @Overr de
  publ c  nt hashCode() {
    return Arrays.hashCode(
        new Object[] {useOrderPreserv ngEncod ng, queryT  out, f eld,  ds, ranks});
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof User dMult Seg ntQuery)) {
      return false;
    }

    User dMult Seg ntQuery query = User dMult Seg ntQuery.class.cast(obj);
    return Arrays.equals(
        new Object[] {useOrderPreserv ngEncod ng, queryT  out, f eld,  ds, ranks},
        new Object[] {query.useOrderPreserv ngEncod ng,
                      query.queryT  out,
                      query.f eld,
                      query. ds,
                      query.ranks});
  }

  @Overr de
  publ c Str ng toStr ng(Str ng f eldNa ) {
    Str ngBu lder bu lder = new Str ngBu lder();
    bu lder.append(getClass().getS mpleNa ()).append("[").append(f eldNa ).append(":");
    for (Long  d : t . ds) {
      bu lder.append( d);
      bu lder.append(",");
    }
    bu lder.setLength(bu lder.length() - 1);
    bu lder.append("]");
    return bu lder.toStr ng();
  }

  pr vate f nal class User dMult Seg ntQuery  ght extends ConstantScore  ght {
    pr vate f nal  ndexSearc r searc r;
    pr vate f nal ScoreMode scoreMode;

    pr vate User dMult Seg ntQuery  ght(
         ndexSearc r searc r,
        ScoreMode scoreMode,
        float boost) {
      super(User dMult Seg ntQuery.t , boost);
      t .searc r = searc r;
      t .scoreMode = scoreMode;
    }

    @Overr de
    publ c Scorer scorer(LeafReaderContext context) throws  OExcept on {
        ght   ght = rewr e(context);
       f (  ght != null) {
        return   ght.scorer(context);
      } else {
        return null;
      }
    }

    @Overr de
    publ c BulkScorer bulkScorer(LeafReaderContext context) throws  OExcept on {
        ght   ght = rewr e(context);
       f (  ght != null) {
        return   ght.bulkScorer(context);
      } else {
        return null;
      }
    }

    @Overr de
    publ c vo d extractTerms(Set<Term> terms) {
      terms.addAll( ds
          .stream()
          .map( d -> new Term(f eld, LongTermAttr bute mpl.copy ntoNewBytesRef( d)))
          .collect(Collectors.toSet()));
    }

    @Overr de
    publ c boolean  sCac able(LeafReaderContext ctx) {
      return true;
    }

    pr vate   ght rewr e(LeafReaderContext context) throws  OExcept on {
      f nal Terms terms = context.reader().terms(f eld);
       f (terms == null) {
        // f eld does not ex st
        return null;
      }
      f nal TermsEnum termsEnum = terms. erator();
      Precond  ons.c ckNotNull(termsEnum, "No termsEnum for f eld: %s", f eld);

      BooleanQuery bq;
      // See  f t  seg nt  s supported by t  mult -seg nt term d ct onary.  f so, bu ld up
      // t  query us ng t  term ds from t  mult -seg nt term d ct onary.
      //  f not (for t  current seg nt), do t  term lookups d rectly  n t  quer ed seg nt.
       nverted ndex f eld ndex = getF eld ndexFromMult TermD ct onary(context);
       f (f eld ndex != null) {
        createTerm dsPerSeg nt();

        USED_MULT _SEGMENT_TERM_D CT ONARY_COUNT. ncre nt();
        SearchT  r t  r = QUERY_FROM_PRECOMPUTED.startNewT  r();
        bq = addPrecomputedTermQuer es(f eld ndex, termsEnum);
        QUERY_FROM_PRECOMPUTED.stopT  rAnd ncre nt(t  r);
      } else {
        USED_OR G NAL_TERM_D CT ONARY_COUNT. ncre nt();
        // T  seg nt  s not supported by t  mult -seg nt term d ct onary. Lookup terms
        // d rectly.
        SearchT  r t  r = QUERY_REGULAR.startNewT  r();
        bq = addTermQuer es(termsEnum);
        QUERY_REGULAR.stopT  rAnd ncre nt(t  r);
      }

      return searc r.rewr e(new ConstantScoreQuery(bq)).create  ght(
          searc r, scoreMode, score());
    }

    /**
     *  f t  mult -seg nt term d ct onary supports t  seg nt/LeafReader, t n return t 
     *  nverted ndex represent ng t  seg nt.
     *
     *  f t  seg nt be ng quer ed r ght now  s not  n t  mult -seg nt term d ct onary (e.g.
     *  f  's not opt m zed yet), return null.
     */
    @Nullable
    pr vate  nverted ndex getF eld ndexFromMult TermD ct onary(LeafReaderContext context)
        throws  OExcept on {
       f (mult Seg ntTermD ct onary == null) {
        return null;
      }

       f (context.reader()  nstanceof Earlyb rd ndexSeg ntAtom cReader) {
        Earlyb rd ndexSeg ntAtom cReader reader =
            (Earlyb rd ndexSeg ntAtom cReader) context.reader();

        Earlyb rd ndexSeg ntData seg ntData = reader.getSeg ntData();
         nverted ndex f eld ndex = seg ntData.getF eld ndex(f eld);

         f (mult Seg ntTermD ct onary.supportSeg nt ndex(f eld ndex)) {
          return f eld ndex;
        }
      }

      return null;
    }

    pr vate BooleanQuery addPrecomputedTermQuer es(
         nverted ndex f eld ndex,
        TermsEnum termsEnum) throws  OExcept on {

      BooleanQuery.Bu lder bqBu lder = new BooleanQuery.Bu lder();
       nt numClauses = 0;

      L st<TermRankPa r> termRankPa rs = term dsPerSeg nt.get(f eld ndex);
       f (termRankPa rs != null) {
        for (TermRankPa r pa r : termRankPa rs) {
           nt term d = pa r.getTerm d();
           f (numClauses >= BooleanQuery.getMaxClauseCount()) {
            BooleanQuery saved = bqBu lder.bu ld();
            bqBu lder = new BooleanQuery.Bu lder();
            bqBu lder.add(saved, BooleanClause.Occur.SHOULD);
            numClauses = 1;
          }

          Query query;
           f (pa r.getRank() != -1) {
            query = Earlyb rdQuery lper.maybeWrapW hH Attr but onCollector(
                new S mpleTermQuery(termsEnum, term d),
                pa r.getRank(),
                f eld nfo,
                h Attr bute lper);
          } else {
            query = new S mpleTermQuery(termsEnum, term d);
          }
          bqBu lder.add(Earlyb rdQuery lper.maybeWrapW hT  out(query, queryT  out),
                        BooleanClause.Occur.SHOULD);
          ++numClauses;
        }
      }
      return bqBu lder.bu ld();
    }

    pr vate BooleanQuery addTermQuer es(TermsEnum termsEnum) throws  OExcept on {
      f nal BytesRef termRef = useOrderPreserv ngEncod ng
          ? SortableLongTermAttr bute mpl.newBytesRef()
          : LongTermAttr bute mpl.newBytesRef();

      BooleanQuery.Bu lder bqBu lder = new BooleanQuery.Bu lder();
       nt numClauses = 0;

      for ( nt  dx = 0;  dx <  ds.s ze(); ++ dx) {
        long longTerm =  ds.get( dx);
         f (useOrderPreserv ngEncod ng) {
          SortableLongTermAttr bute mpl.copyLongToBytesRef(termRef, longTerm);
        } else {
          LongTermAttr bute mpl.copyLongToBytesRef(termRef, longTerm);
        }

         f (termsEnum.seekExact(termRef)) {
           f (numClauses >= BooleanQuery.getMaxClauseCount()) {
            BooleanQuery saved = bqBu lder.bu ld();
            bqBu lder = new BooleanQuery.Bu lder();
            bqBu lder.add(saved, BooleanClause.Occur.SHOULD);
            numClauses = 1;
          }

           f (ranks.s ze() > 0) {
            bqBu lder.add(Earlyb rdQuery lper.maybeWrapW hH Attr but onCollector(
                              new S mpleTermQuery(termsEnum, termsEnum.ord()),
                              ranks.get( dx),
                              f eld nfo,
                              h Attr bute lper),
                          BooleanClause.Occur.SHOULD);
          } else {
            bqBu lder.add(new S mpleTermQuery(termsEnum, termsEnum.ord()),
                          BooleanClause.Occur.SHOULD);
          }
          ++numClauses;
        }
      }

      return bqBu lder.bu ld();
    }
  }
}
