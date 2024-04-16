package com.tw ter.search.earlyb rd.queryparser;

 mport java.ut l.ArrayL st;
 mport java.ut l. dent yHashMap;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.common.ut l.text.H ghFrequencyTermPa rs;
 mport com.tw ter.search.queryparser.query.BooleanQuery;
 mport com.tw ter.search.queryparser.query.Conjunct on;
 mport com.tw ter.search.queryparser.query.D sjunct on;
 mport com.tw ter.search.queryparser.query.Operator;
 mport com.tw ter.search.queryparser.query.Phrase;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.query.QueryV s or;
 mport com.tw ter.search.queryparser.query.Spec alTerm;
 mport com.tw ter.search.queryparser.query.Term;
 mport com.tw ter.search.queryparser.query.annotat on.Annotat on;

/**
 *  erates over t  Query, populat ng  nformat on of an ArrayL st of H ghFrequencyTermQueryGroup so that
 * H ghFrequencyTermPa rRewr eV s or can rewr e t  query to use hf term pa rs. Returns t 
 * (approx mate) number of h gh frequency terms   has detected.  ff that number  s greater than 1
 *   MAY be able to rewr e t  query to use t  hf_term_pa rs f eld.
 *
 * T  key to HF Term Pa r rewr  ng  s understand ng wh ch nodes can be comb ned. T  extractor
 * accompl s s t  job by group ng nodes of t  query toget r. All pos  ve ch ldren of a
 * conjunct on are grouped toget r, and all negat ve ch ldren of a d sjunct on are grouped
 * toget r. T  end result  s a tree of groups, w re every ch ld of a s ngle group w ll have t 
 * oppos e value of  sPos  ve of t  parent group.
 *
 *  'll try to break   down a b  furt r. Let's assu  "a" and "b" are hf terms, and '
 * "[hf_term_pa r a b]" represents query ng t  r co-occurence.
 * Query (* a b not_hf) can beco  (* [hf_term_pa r a b] not_hf)
 * Query (+ -a -b -not_hf) can beco  (+ -[hf_term_pa r a b] -not_hf)
 * T se two rules represent t  bulk of t  rewr es that t  class makes.
 *
 *   also keep track of anot r form of rewr e. A  mber of a group can be pa red up w h a  mber
 * of any of  s parent groups as long as both groups have t  sa   sPos  ve value. T 
 * operat on m m cs boolean d str but on. As t   s probably better expla ned w h an example:
 * Query (* a (+ not_hf (* b not_hf2))) can beco  (* a (+ not_hf (* [hf_term_pa r a b ] not_hf2)))
 * Query (+ -a (* not_hf (+ -b not_hf2))) can beco  (+ -a (* not_hf (+ -[hf_term_pa r a b] not_hf2)))
 */
publ c class H ghFrequencyTermPa rExtractor extends QueryV s or< nteger> {

  pr vate f nal ArrayL st<H ghFrequencyTermQueryGroup> groupL st;
  pr vate f nal  dent yHashMap<Query,  nteger> group ds;

  publ c H ghFrequencyTermPa rExtractor(ArrayL st<H ghFrequencyTermQueryGroup> groupL st,
                                         dent yHashMap<Query,  nteger> group ds) {
    Precond  ons.c ckNotNull(groupL st);
    Precond  ons.c ckArgu nt(groupL st. sEmpty());
    t .groupL st = groupL st;
    t .group ds = group ds;
  }

  @Overr de
  publ c  nteger v s (D sjunct on d sjunct on) throws QueryParserExcept on {
    return v s ((BooleanQuery) d sjunct on);
  }

  @Overr de
  publ c  nteger v s (Conjunct on conjunct on) throws QueryParserExcept on {
    return v s ((BooleanQuery) conjunct on);
  }

  /**
   * All pos  ve ch ldren under a conjunct on (negat ve ch ldren under d sjunct on) belong  n t 
   * sa  group as booleanQuery. All ot r ch ldren belong  n t  r own, separate, new groups.
   * @param booleanQuery
   * @return Number of h gh frequency terms seen by t  node and  s ch ldren
   * @throws QueryParserExcept on
   */
  pr vate  nteger v s (BooleanQuery booleanQuery) throws QueryParserExcept on {
    H ghFrequencyTermQueryGroup group = getGroupForQuery(booleanQuery);
     nt numH s = 0;

    for (Query node : booleanQuery.getCh ldren()) {
      boolean neg = node.mustNotOccur();
       f (node. sTypeOf(Query.QueryType.D SJUNCT ON)) {
        // D sjunct ons, be ng negat ve conjunct ons, are  n rently negat ve nodes.  n terms of
        // be ng  n a pos  ve or negat ve group,   must fl p t  r Occur value.
        neg = !neg;
      }

       f (booleanQuery. sTypeOf(Query.QueryType.D SJUNCT ON) && node.mustOccur()) {
        // Potent al Example: (* a (+ +b not_c)) => (* (+ +b not_c) [hf_term_pa r a b 0.05])
        //  mple ntat on  s too d ff cult and would make t  rewr er even MORE compl cated for
        // a rarely used query. For now,    gnore   completely.   m ght ga n so  benef   n t 
        // future  f   dec de to create a new extractor and rewr er and rewr e t  subquery, and
        // that wouldn't compl cate th ngs too much.
        cont nue;
      }

       f (booleanQuery. sTypeOf(Query.QueryType.CONJUNCT ON) != neg) { // Add node to current group
        group ds.put(node, group.group dx);
        group.num mbers++;
      } else { // Create a new group
        H ghFrequencyTermQueryGroup newGroup =
            new H ghFrequencyTermQueryGroup(groupL st.s ze(), group.group dx, !group. sPos  ve);
        newGroup.num mbers++;
        group ds.put(node, newGroup.group dx);
        groupL st.add(newGroup);
      }
      numH s += node.accept(t );
    }

    return numH s;
  }

  @Overr de
  publ c  nteger v s (Phrase phrase) throws QueryParserExcept on {
    H ghFrequencyTermQueryGroup group = getGroupForQuery(phrase);

     nt numFound = 0;
     f (!phrase.hasAnnotat onType(Annotat on.Type.OPT ONAL)) {
      boolean canBeRewr ten = false;

      // Spec al case: phrases w h exactly 2 terms that are both h gh frequency can be
      // rewr ten.  n all ot r cases terms w ll be treated as pre-used hf term phrases.
       f (!phrase.hasAnnotat ons() && phrase.s ze() == 2
          && H ghFrequencyTermPa rs.HF_TERM_SET.conta ns(phrase.getTerms().get(0))
          && H ghFrequencyTermPa rs.HF_TERM_SET.conta ns(phrase.getTerms().get(1))) {
        canBeRewr ten = true;
      }

      // Spec al case: do not treat phrase conta n ng :prox annotat on as a real phrase.
      boolean prox m yPhrase = phrase.hasAnnotat onType(Annotat on.Type.PROX M TY);

      Str ng lastHFToken = null;
      for (Str ng token : phrase.getTerms()) {
         f (H ghFrequencyTermPa rs.HF_TERM_SET.conta ns(token)) {
          group.preusedHFTokens.add(token);
           f (group.d str but veToken == null) {
            group.d str but veToken = token;
          }
           f (lastHFToken != null && !prox m yPhrase) {
             f (canBeRewr ten) {
              group.hfPhrases.add(lastHFToken + " " + token);
            } else {
              group.preusedHFPhrases.add(lastHFToken + " " + token);
            }
          }
          lastHFToken = token;
          numFound++;
        } else {
          lastHFToken = null;
        }
      }
    }

    return numFound;
  }

  @Overr de
  publ c  nteger v s (Term term) throws QueryParserExcept on {
     f (groupL st. sEmpty()) { // Shortcut for 1 term quer es.
      return 0;
    }

    H ghFrequencyTermQueryGroup group = getGroupForQuery(term);

     f (!term.hasAnnotat onType(Annotat on.Type.OPT ONAL)
        && H ghFrequencyTermPa rs.HF_TERM_SET.conta ns(term.getValue())) {
       f (!term.hasAnnotat ons()) {
        group.hfTokens.add(term.getValue());
      } else { // Should not remove t  annotated term.
        group.preusedHFTokens.add(term.getValue());
      }

       f (group.d str but veToken == null) {
        group.d str but veToken = term.getValue();
      }
      return 1;
    }

    return 0;
  }

  @Overr de
  publ c  nteger v s (Operator operator) throws QueryParserExcept on {
    return 0;
  }

  @Overr de
  publ c  nteger v s (Spec alTerm spec al) throws QueryParserExcept on {
    return 0;
  }

  /**
   * Uses t  query's v s or data as an  ndex and returns t  group   belongs to.  f groupL st  s
   * empty, create a new group and set t  group's v s or data to be  ndex 0.
   * @param query
   * @return t  group wh ch query belongs to.
   */
  pr vate H ghFrequencyTermQueryGroup getGroupForQuery(Query query) {
     f (groupL st. sEmpty()) {
      boolean pos = !query.mustNotOccur();
       f (query  nstanceof D sjunct on) {
        pos = !pos;
      }
      H ghFrequencyTermQueryGroup group = new H ghFrequencyTermQueryGroup(0, pos);
      group.num mbers++;
      groupL st.add(group);
      group ds.put(query, 0);
    }

    return groupL st.get(group ds.get(query));
  }
}
