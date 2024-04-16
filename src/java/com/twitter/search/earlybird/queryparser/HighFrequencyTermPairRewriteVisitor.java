package com.tw ter.search.earlyb rd.queryparser;

 mport java.ut l.ArrayL st;
 mport java.ut l. dent yHashMap;
 mport java.ut l.L st;
 mport java.ut l.Set;

 mport javax.annotat on.Nullable;

 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.ut l.text.H ghFrequencyTermPa rs;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.queryparser.parser.Ser al zedQueryParser;
 mport com.tw ter.search.queryparser.query.BooleanQuery;
 mport com.tw ter.search.queryparser.query.Conjunct on;
 mport com.tw ter.search.queryparser.query.D sjunct on;
 mport com.tw ter.search.queryparser.query.Operator;
 mport com.tw ter.search.queryparser.query.Phrase;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryNodeUt ls;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.query.QueryV s or;
 mport com.tw ter.search.queryparser.query.Spec alTerm;
 mport com.tw ter.search.queryparser.query.Term;
 mport com.tw ter.search.queryparser.query.search.SearchOperator;

/**
 *  erates over t  Query, mod fy ng   to  nclude h gh frequency term pa rs, replac ng
 * s ngular h gh frequency terms w re poss ble.
 *
 * Assu s that t  w ll be used  MMED ATELY after us ng H ghFrequencyTermPa rExtractor
 *
 * T re are two pr mary funct ons of t  v s or:
 *  1. Append hf_term_pa rs to each group's root node.
 *  2. Remove all unnecessary term quer es (unnecessary as t y are captured by an hf_term_pa r)
 *
 * Every t   t  v s or f n s s v s  ng a node, H ghFrequencyTermQueryGroup.numV s s w ll be
 *  ncre nted for that node's group. W n numV s s == numCh ldren,   know   have just f n s d
 * process ng t  root of t  group. At t  po nt,   must append relevant hf_term_pa rs to t 
 * node.
 */
publ c class H ghFrequencyTermPa rRewr eV s or extends QueryV s or<Query> {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(
      H ghFrequencyTermPa rRewr eV s or.class);
  pr vate stat c f nal SearchRateCounter SEARCH_HF_PA R_COUNTER =
      SearchRateCounter.export("hf_pa r_rewr e");

  pr vate f nal ArrayL st<H ghFrequencyTermQueryGroup> groupL st;
  pr vate f nal  dent yHashMap<Query,  nteger> group ds;
  pr vate f nal boolean allowNegat veOrRewr e;

  /**
   * Creates a new H ghFrequencyTermPa rRewr eV s or. Should be used only  MMED ATELY after us ng
   * a H ghFrequencyTermPa rExtractor
   * @param groupL st T  groups extracted us ng H ghFrequencyTermPa rExtractor
   * @param group ds t  mapp ng from query to t  HF term query group
   */
  publ c H ghFrequencyTermPa rRewr eV s or(ArrayL st<H ghFrequencyTermQueryGroup> groupL st,
                                              dent yHashMap<Query,  nteger> group ds) {
    t (groupL st, group ds, true);
  }

  /**
   * Creates a new H ghFrequencyTermPa rRewr eV s or. Should be used only  MMED ATELY after us ng
   * a H ghFrequencyTermPa rExtractor
   * @param groupL st T  groups extracted us ng H ghFrequencyTermPa rExtractor
   * @param group ds t  mapp ng from query to t  HF term query group
   * @param allowNegat veOrRewr e w t r to allow rewr e for 'or (-terms)'
   */
  publ c H ghFrequencyTermPa rRewr eV s or(ArrayL st<H ghFrequencyTermQueryGroup> groupL st,
                                              dent yHashMap<Query,  nteger> group ds,
                                             boolean allowNegat veOrRewr e) {
    t .groupL st = groupL st;
    t .group ds = group ds;
    t .allowNegat veOrRewr e = allowNegat veOrRewr e;
  }

  /**
   * T   thod logs successful rewr es, and protects aga nst unsuccessful ones by
   * catch ng all except ons and restor ng t  prev ous query. 
   */
  publ c stat c Query safeRewr e(Query safeQuery, boolean allowNegat veOrRewr e)
      throws QueryParserExcept on {
    Query query = safeQuery;

    ArrayL st<H ghFrequencyTermQueryGroup> groups = L sts.newArrayL st();
     dent yHashMap<Query,  nteger> group ds = Maps.new dent yHashMap();

    // Step 1: extract h gh frequency term pa rs and phrases.
    try {
       nt hfTermsFound = query.accept(new H ghFrequencyTermPa rExtractor(groups, group ds));
       f (hfTermsFound < 2) {
        return query;
      }
    } catch (Except on e) {
      LOG.error("Except on wh le extract ng h gh frequency term pa rs", e);
      return query;
    }

    // Step 2: rewr e (safely).
    Str ng or g nal = query.ser al ze();
    try {
      query = query.accept(
          new H ghFrequencyTermPa rRewr eV s or(groups, group ds, allowNegat veOrRewr e))
          .s mpl fy();
      Str ng rewr e = query.ser al ze();
       f (LOG. sDebugEnabled()) {
        LOG.debug("Opt m zed query: " + or g nal + " -> " + rewr e);
      }
      SEARCH_HF_PA R_COUNTER. ncre nt();
      return query;
    } catch (Except on e) {
      LOG.error("Except on rewr  ng h gh frequency term pa rs", e);
      return new Ser al zedQueryParser(Earlyb rdConf g.getPengu nVers on()).parse(or g nal);
    }
  }

  /**
   * T  rewr ten query to use t  hf_term_pa r operators.
   *
   * @param d sjunct on query node wh ch must have been prev ously v s ed by
   *                    H ghFrequencyTermPa rExtractor and not had  s v s or data cleared.
   */
  @Overr de
  publ c Query v s (D sjunct on d sjunct on) throws QueryParserExcept on {
    return v s ((BooleanQuery) d sjunct on);
  }

  /**
   * T  rewr ten query to use t  hf_term_pa r operators.
   *
   * @param conjunct on query node wh ch must have been prev ously v s ed by
   *                    H ghFrequencyTermPa rExtractor and not had  s v s or data cleared.
   */
  @Overr de
  publ c Query v s (Conjunct on conjunct on) throws QueryParserExcept on {
    return v s ((BooleanQuery) conjunct on);
  }

  /**
   * Appl es t  v s or to a BooleanQuery.
   */
  publ c Query v s (BooleanQuery booleanQuery) throws QueryParserExcept on {
    H ghFrequencyTermQueryGroup group = groupL st.get(group ds.get(booleanQuery));
    queryPreprocess(group);

    ArrayL st<Query> ch ldren = L sts.newArrayL st();
    for (Query node : booleanQuery.getCh ldren()) {
       f (booleanQuery. sTypeOf(Query.QueryType.D SJUNCT ON) && node.mustOccur()) {
        // Potent al Example: (* a (+ +b not_c)) => (* (+ +b not_c) [hf_term_pa r a b 0.05])
        //  mple ntat on  s too d ff cult and would make t  rewr er even MORE compl cated for
        // a rarely used query. For now,    gnore   completely.   m ght ga n so  benef   n t 
        // future  f   dec de to create a new extractor and rewr er and rewr e t  subquery, and
        // that wouldn't compl cate th ngs too much.
        ch ldren.add(node);
        cont nue;
      }
      Query ch ld = node.accept(t );
       f (ch ld != null) {
        ch ldren.add(ch ld);
      }
    }

    Query newBooleanQuery = booleanQuery.newBu lder().setCh ldren(ch ldren).bu ld();

    return queryPostprocess(newBooleanQuery, group);
  }

  /**
   * T  rewr ten query to use t  hf_term_pa r operators.
   *
   * @param phraseToV s  query node wh ch must have been prev ously v s ed by
   *               H ghFrequencyTermPa rExtractor and not had  s v s or data cleared.
   */
  @Overr de
  publ c Query v s (Phrase phraseToV s ) throws QueryParserExcept on {
    Phrase phrase = phraseToV s ;

    H ghFrequencyTermQueryGroup group = groupL st.get(group ds.get(phrase));
    queryPreprocess(group);

    // Remove all h gh frequency phrases from t  query that do not have any annotat ons.
    // T  w ll cause phrase de-dup ng, wh ch   probably don't care about.
     f (!hasAnnotat ons(phrase) && (
        group.hfPhrases.conta ns(phrase.getPhraseValue())
        || group.preusedHFPhrases.conta ns(phrase.getPhraseValue()))) {
      // T  term w ll be appended to t  end of t  query  n t  form of a pa r.
      phrase = null;
    }

    return queryPostprocess(phrase, group);
  }

  /**
   * T  rewr ten query to use t  hf_term_pa r operators.
   *
   * @param termToV s  query node wh ch must have been prev ously v s ed by
   *             H ghFrequencyTermPa rExtractor and not had  s v s or data cleared.
   */
  @Overr de
  publ c Query v s (Term termToV s ) throws QueryParserExcept on {
    Term term = termToV s ;

    H ghFrequencyTermQueryGroup group = groupL st.get(group ds.get(term));
    queryPreprocess(group);

    // Remove all h gh frequency terms from t  query that do not have any annotat ons. T  w ll
    // do term de-dup ng w h n a group, wh ch may effect scor ng, but s nce t se are h gh df
    // terms, t y don't have much of an  mpact anyways.
     f (!hasAnnotat ons(term)
        && (group.preusedHFTokens.conta ns(term.getValue())
            || group.hfTokens.conta ns(term.getValue()))) {
      // T  term w ll be appended to t  end of t  query  n t  form of a pa r.
      term = null;
    }

    return queryPostprocess(term, group);
  }

  /**
   * T  rewr ten query to use t  hf_term_pa r operators.
   *
   * @param operator query node wh ch must have been prev ously v s ed by
   *                 H ghFrequencyTermPa rExtractor and not had  s v s or data cleared.
   */
  @Overr de
  publ c Query v s (Operator operator) throws QueryParserExcept on {
    H ghFrequencyTermQueryGroup group = groupL st.get(group ds.get(operator));
    queryPreprocess(group);

    return queryPostprocess(operator, group);
  }

  /**
   * T  rewr ten query to use t  hf_term_pa r operators.
   *
   * @param spec al query node wh ch must have been prev ously v s ed by
   *                H ghFrequencyTermPa rExtractor and not had  s v s or data cleared.
   */
  @Overr de
  publ c Query v s (Spec alTerm spec al) throws QueryParserExcept on {
    H ghFrequencyTermQueryGroup group = groupL st.get(group ds.get(spec al));
    queryPreprocess(group);

    return queryPostprocess(spec al, group);
  }

  /**
   * Before v s  ng a node's ch ldren,   must process  s group's d str but veToken. T  way, a
   * node only has to c ck  s grandparent group for a d str but veToken  nstead of recurs ng all
   * of t  way up to t  root of t  tree.
   */
  pr vate vo d queryPreprocess(H ghFrequencyTermQueryGroup group) {
     f (group.d str but veToken == null) {
      group.d str but veToken = getAncestorD str but veToken(group);
    }
  }

  /**
   *  f t  query  sn't t  root of t  group, returns t  query. Ot rw se,  f t  query's
   * group has at most one hf term, return t  query. Ot rw se, returns t  query w h hf_term_pa r
   * operators created from t  group's hf terms appended to  .
   */
  pr vate Query queryPostprocess(@Nullable Query query, H ghFrequencyTermQueryGroup group)
      throws QueryParserExcept on {

    group.numV s s++;
     f (group.num mbers == group.numV s s
        && (!group.hfTokens. sEmpty() || !group.preusedHFTokens. sEmpty()
        || group.hasPhrases())) {

      group.removePreusedTokens();
      Str ng ancestorD str but veToken = getAncestorD str but veToken(group);

      // Need at least 2 tokens to perform a pa r rewr e.  Try to get one
      // add  onal token from ancestors, and  f that fa ls, from phrases.
       f ((group.hfTokens.s ze() + group.preusedHFTokens.s ze()) == 1
          && ancestorD str but veToken != null) {
        group.preusedHFTokens.add(ancestorD str but veToken);
      }
       f ((group.hfTokens.s ze() + group.preusedHFTokens.s ze()) == 1) {
        Str ng tokenFromPhrase = group.getTokenFromPhrase();
         f (tokenFromPhrase != null) {
          group.preusedHFTokens.add(tokenFromPhrase);
        }
      }

      return appendPa rs(query, group);
    }

    return query;
  }

  /**
   * Returns t  d str but veToken of group's grandparent.
   */
  pr vate Str ng getAncestorD str but veToken(H ghFrequencyTermQueryGroup group) {
    Str ng ancestorD str but veToken = null;
     f (group.parentGroup dx >= 0 && groupL st.get(group.parentGroup dx).parentGroup dx >= 0) {
      ancestorD str but veToken =
              groupL st.get(groupL st.get(group.parentGroup dx).parentGroup dx).d str but veToken;
    }
    return ancestorD str but veToken;
  }

  /**
   * Returns t  hf_term_pa r operators created us ng t  hf terms of t  group appended to query.
   *
   * @param query T  query wh ch t  new hf_term_pa r operators w ll be appended to.
   * @param group T  group wh ch t  query belongs to.
   * @return T  hf_term_pa r operators created us ng t  hf terms of t  group appended to query.
   */
  pr vate Query appendPa rs(@Nullable Query query, H ghFrequencyTermQueryGroup group)
      throws QueryParserExcept on {

    BooleanQuery query2 = createQueryFromGroup(group);

    //  f e  r of t  quer es are null, do not have to worry about comb n ng t m.
     f (query2 == null) {
      return query;
    } else  f (query == null) {
      return query2;
    }

    Query newQuery;

     f (query. sTypeOf(Query.QueryType.CONJUNCT ON)
        || query. sTypeOf(Query.QueryType.D SJUNCT ON)) {
      // Add ng ch ldren  n t  way  s safer w n  s query  s a conjunct on or d sjunct on
      // ex. Ot r way: (+ +de -la -t ) => (+ (+ +de -la -t ) -[hf_term_pa r la t  0.005])
      //     T  way: (+ +de -la -t ) => (+ +de -la -t  -[hf_term_pa r la t  0.005])
      return ((BooleanQuery.Bu lder) query.newBu lder()).addCh ldren(query2.getCh ldren()).bu ld();
    } else  f (!group. sPos  ve) {
      //  n lucene, [+ (-term1, -term2, ...)] has non-determ n st c behav or and t  rewr e  s not
      // eff c ent from query execut on perspect ve.  So,   w ll not do t  rewr e  f    s
      // conf gured that way.
       f (!allowNegat veOrRewr e) {
        return query;
      }

      // Negate both quer es to comb ne, and t  append as a conjunct on, follo d by negat ng
      // whole query. Equ valent to append ng as a d sjunct on.
      newQuery = QueryNodeUt ls.appendAsConjunct on(
          query.negate(),
          query2.negate()
      );
      newQuery = newQuery.makeMustNot();
    } else {
      newQuery = QueryNodeUt ls.appendAsConjunct on(query, query2);
      newQuery = newQuery.makeDefault();
    }

    return newQuery;
  }

  /**
   * Creates a conjunct on of term_pa rs us ng t  sets of hf terms  n H ghFrequencyTermQueryGroup
   * group.  f !group. sPos  ve, w ll return a d sjunct on of negated pa rs.  f t re aren't enough
   * hfTokens, w ll return null.
   */
  pr vate BooleanQuery createQueryFromGroup(H ghFrequencyTermQueryGroup group)
      throws QueryParserExcept on {

     f (!group.hfTokens. sEmpty() || group.preusedHFTokens.s ze() > 1 || group.hasPhrases()) {
      L st<Query>  terms = createTermPa rsForGroup(group.hfTokens,
                                                   group.preusedHFTokens,
                                                   group.hfPhrases,
                                                   group.preusedHFPhrases);

       f (group. sPos  ve) {
        return new Conjunct on(terms);
      } else {
        return new D sjunct on(L sts.transform(terms, QueryNodeUt ls.NEGATE_QUERY));
      }
    }

    return null;
  }

  /**
   * Creates HF_TERM_PA R terms out of hfTokens and optHFTokens. Attempts to create t  m n mal
   * amount of tokens necessary. optHFToken pa rs should be g ven a   ght of 0.0 and not be scored,
   * as t y are l kely already  ncluded  n t  query  n a phrase or an annotated term.
   * @param hfTokens
   * @param optHFTokens
   * @return A l st of hf_term_pa r operators.
   */
  pr vate L st<Query> createTermPa rsForGroup(Set<Str ng> hfTokens,
                                              Set<Str ng> optHFTokens,
                                              Set<Str ng> hfPhrases,
                                              Set<Str ng> optHFPhrases) {
    // Handle sets w h only one token.
     f (optHFTokens.s ze() == 1 && hfTokens.s ze() > 0) {
      // (* "a not_hf" b c) => (* "a not_hf" [hf_term_pa r a b 0.05] [hf_term_pa r b c 0.05])
      // optHFTokens: [a] hfTokens: [b, c] => optHFTokens: [] hfTokens: [a, b, c]
      hfTokens.addAll(optHFTokens);
      optHFTokens.clear();
    } else  f (hfTokens.s ze() == 1 && optHFTokens.s ze() > 0) {
      // (* "a b" not_hf c) => (* "a b" not_hf [hf_term_pa r a b 0.0] [hf_term_pa r a c 0.005])
      // optHFTokens: [a, b] hfTokens: [c] => optHFTokens: [a, b] hfTokens: [a, c]
      Str ng term = optHFTokens. erator().next();
      hfTokens.add(term);
    }

    L st<Query> terms = createTermPa rs(hfTokens, true, H ghFrequencyTermPa rs.HF_DEFAULT_WE GHT);
    terms.addAll(createTermPa rs(optHFTokens, false, 0));
    terms.addAll(createPhrasePa rs(hfPhrases, H ghFrequencyTermPa rs.HF_DEFAULT_WE GHT));
    terms.addAll(createPhrasePa rs(optHFPhrases, 0));

    return terms;
  }

  /**
   * Turns a set of hf terms  nto a l st of hf_term_pa r operators. Each term w ll be used at least
   * once  n as few pa rs as poss ble.
   * @param tokens
   * @param createS ngle  f t  set conta ns only one query, t  returned l st w ll conta n a s ngle
   *                     Term for that query  f createS ngle  s true, and an empty l st ot rw se.
   * @param   ght Each term pa r w ll be g ven a score boost of ser al zed  ght.
   * @return
   */
  pr vate stat c L st<Query> createTermPa rs(Set<Str ng> tokens, boolean createS ngle,
      double   ght) {

    L st<Query> terms = L sts.newArrayL st();
     f (tokens.s ze() >= 2) {
       nt tokensLeft = tokens.s ze();
      Str ng token1 = null;
      for (Str ng token2 : tokens) {
         f (token1 == null) {
          token1 = token2;
        } else {
          terms.add(createHFTermPa r(token1, token2,   ght));

           f (tokensLeft > 2) { // Only reset  f t re  s more than one token rema n ng.
            token1 = null;
          }
        }
        tokensLeft--;
      }
    } else  f (createS ngle && !tokens. sEmpty()) { // Only one h gh frequency token
      // Need to add token as a term because   was removed from t  query earl er  n rewr  ng.
      Term newTerm = new Term(tokens. erator().next());
      terms.add(newTerm);
    }

    return terms;
  }

  pr vate stat c L st<Query> createPhrasePa rs(Set<Str ng> phrases, double   ght) {
    L st<Query> ops = L sts.newArrayL st();
    for (Str ng phrase : phrases) {
      Str ng[] terms = phrase.spl (" ");
      assert terms.length == 2;
      SearchOperator op = new SearchOperator(SearchOperator.Type.HF_PHRASE_PA R,
          terms[0], terms[1], Double.toStr ng(  ght));
      ops.add(op);
    }
    return ops;
  }

  pr vate stat c SearchOperator createHFTermPa r(Str ng token1, Str ng token2, double   ght) {
    SearchOperator op = new SearchOperator(SearchOperator.Type.HF_TERM_PA R,
        token1, token2, Double.toStr ng(  ght));
    return op;
  }

  pr vate stat c boolean hasAnnotat ons(com.tw ter.search.queryparser.query.Query node) {
    return node.hasAnnotat ons();
  }
}
