package com.tw ter.search.earlyb rd_root.v s ors;

 mport java.ut l.Collect ons;
 mport java.ut l.L st;
 mport java.ut l.stream.Collectors;

 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect.L sts;

 mport com.tw ter.search.common.part  on ng.base.Part  onDataType;
 mport com.tw ter.search.common.part  on ng.base.Part  onMapp ngManager;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants;
 mport com.tw ter.search.queryparser.query.Conjunct on;
 mport com.tw ter.search.queryparser.query.D sjunct on;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.Query.Occur;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.query.search.SearchOperator;
 mport com.tw ter.search.queryparser.query.search.SearchQueryTransfor r;

/**
 * Truncate user  d or  d l sts  n [mult _term_d sjunct on from_user_ d/ d] quer es.
 * Return null  f query has  ncorrect operators or looked at wrong f eld.
 */
publ c class Mult TermD sjunct onPerPart  onV s or extends SearchQueryTransfor r {
  pr vate f nal Part  onMapp ngManager part  onMapp ngManager;
  pr vate f nal  nt part  on d;
  pr vate f nal Str ng targetF eldNa ;

  publ c stat c f nal Conjunct on NO_MATCH_CONJUNCT ON =
      new Conjunct on(Occur.MUST_NOT, Collect ons.emptyL st(), Collect ons.emptyL st());

  publ c Mult TermD sjunct onPerPart  onV s or(
      Part  onMapp ngManager part  onMapp ngManager,
       nt part  on d) {
    t .part  onMapp ngManager = part  onMapp ngManager;
    t .part  on d = part  on d;
    t .targetF eldNa  =
        part  onMapp ngManager.getPart  onDataType() == Part  onDataType.USER_ D
            ? Earlyb rdF eldConstants.Earlyb rdF eldConstant.FROM_USER_ D_F ELD.getF eldNa ()
            : Earlyb rdF eldConstants.Earlyb rdF eldConstant. D_F ELD.getF eldNa ();
  }

  pr vate boolean  sTargetedQuery(Query query) {
     f (query  nstanceof SearchOperator) {
      SearchOperator operator = (SearchOperator) query;
      return operator.getOperatorType() == SearchOperator.Type.MULT _TERM_D SJUNCT ON
          && operator.getOperand().equals(targetF eldNa );
    } else {
      return false;
    }
  }

  @Overr de
  publ c Query v s (Conjunct on query) throws QueryParserExcept on {
    boolean mod f ed = false;
     mmutableL st.Bu lder<Query> ch ldren =  mmutableL st.bu lder();
    for (Query ch ld : query.getCh ldren()) {
      Query newCh ld = ch ld.accept(t );
       f (newCh ld != null) {
        // For conjunct on case,  f any ch ld  s "mult _term_d sjunct on from_user_ d" and returns
        // Conjunct on.NO_MATCH_CONJUNCT ON,   should be cons dered sa  as match no docs. And
        // caller should dec de how to deal w h  .
         f ( sTargetedQuery(ch ld) && newCh ld == NO_MATCH_CONJUNCT ON) {
          return NO_MATCH_CONJUNCT ON;
        }
         f (newCh ld != Conjunct on.EMPTY_CONJUNCT ON
            && newCh ld != D sjunct on.EMPTY_D SJUNCT ON) {
          ch ldren.add(newCh ld);
        }
      }
       f (newCh ld != ch ld) {
        mod f ed = true;
      }
    }
    return mod f ed ? query.newBu lder().setCh ldren(ch ldren.bu ld()).bu ld() : query;
  }

  @Overr de
  publ c Query v s (D sjunct on d sjunct on) throws QueryParserExcept on {
    boolean mod f ed = false;
     mmutableL st.Bu lder<Query> ch ldren =  mmutableL st.bu lder();
    for (Query ch ld : d sjunct on.getCh ldren()) {
      Query newCh ld = ch ld.accept(t );
       f (newCh ld != null
          && newCh ld != Conjunct on.EMPTY_CONJUNCT ON
          && newCh ld != D sjunct on.EMPTY_D SJUNCT ON
          && newCh ld != NO_MATCH_CONJUNCT ON) {
        ch ldren.add(newCh ld);
      }
       f (newCh ld != ch ld) {
        mod f ed = true;
      }
    }
    return mod f ed ? d sjunct on.newBu lder().setCh ldren(ch ldren.bu ld()).bu ld() : d sjunct on;
  }

  @Overr de
  publ c Query v s (SearchOperator operator) throws QueryParserExcept on {
     f ( sTargetedQuery(operator)) {
      L st<Long>  ds = extract ds(operator);
       f ( ds.s ze() > 0) {
        L st<Str ng> operands = L sts.newArrayL st(targetF eldNa );
        for (long  d :  ds) {
          operands.add(Str ng.valueOf( d));
        }
        return operator.newBu lder().setOperands(operands).bu ld();
      } else {
        //  f t  [mult _term_d sjunct on from_user_ d]  s a negat on ( .e., occur == MUST_NOT),
        // and t re  s no user  d left, t  whole sub query node does not do anyth ng;  f    s
        // NOT a negat on, t n sub query matc s noth ng.
         f (operator.getOccur() == Query.Occur.MUST_NOT) {
          return Conjunct on.EMPTY_CONJUNCT ON;
        } else {
          return NO_MATCH_CONJUNCT ON;
        }
      }
    }
    return operator;
  }

  pr vate L st<Long> extract ds(SearchOperator operator) throws QueryParserExcept on {
     f (Earlyb rdF eldConstants.Earlyb rdF eldConstant. D_F ELD
        .getF eldNa ().equals(targetF eldNa )) {
      return operator.getOperands().subL st(1, operator.getNumOperands()).stream()
          .map(Long::valueOf)
          .f lter( d -> part  onMapp ngManager.getPart  on dForT et d( d) == part  on d)
          .collect(Collectors.toL st());
    } else {
      return operator.getOperands().subL st(1, operator.getNumOperands()).stream()
          .map(Long::valueOf)
          .f lter( d -> part  onMapp ngManager.getPart  on dForUser d( d) == part  on d)
          .collect(Collectors.toL st());
    }
  }
}
