package com.tw ter.search.earlyb rd.queryparser;

 mport java.ut l.L st;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableL st;

 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.queryparser.query.Conjunct on;
 mport com.tw ter.search.queryparser.query.D sjunct on;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.search.SearchOperator;
 mport com.tw ter.search.queryparser.query.search.SearchOperatorConstants;

publ c class ProtectedOperatorQueryRewr er {
  pr vate stat c f nal Str ng ERROR_MESSAGE = "Pos  ve 'protected' operator must be  n t  root"
      + " query node and t  root query node must be a Conjunct on.";
  pr vate stat c f nal Query EXCLUDE_PROTECTED_OPERATOR =
      new SearchOperator(SearchOperator.Type.EXCLUDE, SearchOperatorConstants.PROTECTED);

  /**
   * Rewr e a query w h pos  ve 'protected' operator  nto an equ valent query w hout t  pos  ve
   * 'protected' operator. T   thod assu s t  follow ng precond  ons hold:
   *  1. 'follo dUser ds'  s not empty
   *  2. t  query's root node  s of type Conjunct on
   *  3. t  query's root node  s not negated
   *  4. t re  s one pos  ve 'protected' operator  n t  root node
   *  5. t re  s only one 'protected' operator  n t  whole query
   *
   *  Query w h '[ nclude protected]' operator  s rewr ten  nto a D sjunct on of a query w h
   *  protected T ets only and a query w h publ c T ets only.
   *  For example,
   *    Or g nal query:
   *      (* "cat" [ nclude protected])
   *        w h follo dUser ds=[1, 7, 12] w re 1 and 7 are protected users
   *    Rewr ten query:
   *      (+
   *        (* "cat" [mult _term_d sjunct on from_user_ d 1 7])
   *        (* "cat" [exclude protected])
   *      )
   *
   *  Query w h '[f lter protected]' operator  s rewr ten w h mult _term_d sjunct on from_user_ d
   *  operator.
   *  For example,
   *    Or g nal query:
   *      (* "cat" [f lter protected])
   *        w h follo dUser ds=[1, 7, 12] w re 1 and 7 are protected users
   *    Rewr ten query:
   *      (* "cat" [mult _term_d sjunct on from_user_ d 1 7])
   */
  publ c Query rewr e(Query parsedQuery, L st<Long> follo dUser ds, UserTable userTable) {
    Precond  ons.c ckState(follo dUser ds != null && !follo dUser ds. sEmpty(),
        "'follo dUser ds' should not be empty w n pos  ve 'protected' operator ex sts.");
    Precond  ons.c ckState(
        parsedQuery. sTypeOf(com.tw ter.search.queryparser.query.Query.QueryType.CONJUNCT ON),
        ERROR_MESSAGE);
    Conjunct on parsedConjQuery = (Conjunct on) parsedQuery;
    L st<Query> ch ldren = parsedConjQuery.getCh ldren();
     nt op ndex = f ndPos  veProtectedOperator ndex(ch ldren);
    Precond  ons.c ckState(op ndex >= 0, ERROR_MESSAGE);
    SearchOperator protectedOp = (SearchOperator) ch ldren.get(op ndex);

     mmutableL st.Bu lder<Query> ot rCh ldrenBu lder =  mmutableL st.bu lder();
    ot rCh ldrenBu lder.addAll(ch ldren.subL st(0, op ndex));
     f (op ndex + 1 < ch ldren.s ze()) {
      ot rCh ldrenBu lder.addAll(ch ldren.subL st(op ndex + 1, ch ldren.s ze()));
    }
    L st<Query> ot rCh ldren = ot rCh ldrenBu lder.bu ld();

    L st<Long> protectedUser ds = getProtectedUser ds(follo dUser ds, userTable);
     f (protectedOp.getOperatorType() == SearchOperator.Type.F LTER) {
       f (protectedUser ds. sEmpty()) {
        // match none query
        return D sjunct on.EMPTY_D SJUNCT ON;
      } else {
        return parsedConjQuery.newBu lder()
            .setCh ldren(ot rCh ldren)
            .addCh ld(createFromUser dMult TermD sjunct onQuery(protectedUser ds))
            .bu ld();
      }
    } else {
      // ' nclude' or negated 'exclude' operator
      // negated 'exclude'  s cons dered t  sa  as ' nclude' to be cons stent w h t  log c  n
      // Earlyb rdLuceneQueryV s or
       f (protectedUser ds. sEmpty()) {
        // return publ c only query
        return parsedConjQuery.newBu lder()
            .setCh ldren(ot rCh ldren)
            .addCh ld(EXCLUDE_PROTECTED_OPERATOR)
            .bu ld();
      } else {
        // bu ld a d sjunct on of protected only query and publ c only query
        Query protectedOnlyQuery = parsedConjQuery.newBu lder()
            .setCh ldren(ot rCh ldren)
            .addCh ld(createFromUser dMult TermD sjunct onQuery(protectedUser ds))
            .bu ld();
        Query publ cOnlyQuery = parsedConjQuery.newBu lder()
            .setCh ldren(ot rCh ldren)
            .addCh ld(EXCLUDE_PROTECTED_OPERATOR)
            .bu ld();
        return new D sjunct on(protectedOnlyQuery, publ cOnlyQuery);
      }
    }
  }

  pr vate Query createFromUser dMult TermD sjunct onQuery(L st<Long> user ds) {
     mmutableL st.Bu lder<Str ng> operandsBu lder =  mmutableL st.bu lder();
    operandsBu lder
        .add(Earlyb rdF eldConstants.Earlyb rdF eldConstant.FROM_USER_ D_F ELD.getF eldNa ());
    for (Long user d : user ds) {
      operandsBu lder.add(user d.toStr ng());
    }
    L st<Str ng> operands = operandsBu lder.bu ld();
    return new SearchOperator(SearchOperator.Type.MULT _TERM_D SJUNCT ON, operands);
  }

  pr vate L st<Long> getProtectedUser ds(L st<Long> follo dUser ds, UserTable userTable) {
     mmutableL st.Bu lder<Long> protectedUser ds =  mmutableL st.bu lder();
    for (Long user d : follo dUser ds) {
       f (userTable. sSet(user d, UserTable. S_PROTECTED_B T)) {
        protectedUser ds.add(user d);
      }
    }
    return protectedUser ds.bu ld();
  }

  pr vate  nt f ndPos  veProtectedOperator ndex(L st<Query> ch ldren) {
    for ( nt   = 0;   < ch ldren.s ze();  ++) {
      Query ch ld = ch ldren.get( );
       f (ch ld  nstanceof SearchOperator) {
        SearchOperator searchOp = (SearchOperator) ch ld;
         f (SearchOperatorConstants.PROTECTED.equals(searchOp.getOperand())
            && ( sNegateExclude(searchOp) ||  sPos  ve(searchOp))) {
          return  ;
        }
      }
    }

    return -1;
  }

  pr vate boolean  sNegateExclude(SearchOperator searchOp) {
    return searchOp.mustNotOccur()
        && searchOp.getOperatorType() == SearchOperator.Type.EXCLUDE;
  }

  pr vate boolean  sPos  ve(SearchOperator searchOp) {
    return !searchOp.mustNotOccur()
        && (searchOp.getOperatorType() == SearchOperator.Type. NCLUDE
        || searchOp.getOperatorType() == SearchOperator.Type.F LTER);
  }
}
