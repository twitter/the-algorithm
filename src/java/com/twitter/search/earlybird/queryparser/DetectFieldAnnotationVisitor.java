package com.tw ter.search.earlyb rd.queryparser;

 mport java.ut l.Set;

 mport com.google.common.collect. mmutableSet;

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
 mport com.tw ter.search.queryparser.query.annotat on.F eldNa W hBoost;

/**
 * Detects w t r t  query tree has certa n f eld annotat ons.
 */
publ c class DetectF eldAnnotat onV s or extends QueryV s or<Boolean> {
  pr vate f nal  mmutableSet<Str ng> f eldNa s;

  /**
   * T  v s or w ll return true  f t  query tree has a F ELD annotat on w h any of t  g ven
   * f eld na s.  f t  set  s empty, any F ELD annotat on w ll match.
   */
  publ c DetectF eldAnnotat onV s or(Set<Str ng> f eldNa s) {
    t .f eldNa s =  mmutableSet.copyOf(f eldNa s);
  }

  /**
   * T  v s or w ll return true  f t  query tree has a F ELD annotat on.
   */
  publ c DetectF eldAnnotat onV s or() {
    t .f eldNa s =  mmutableSet.of();
  }

  @Overr de
  publ c Boolean v s (D sjunct on d sjunct on) throws QueryParserExcept on {
    return v s Query(d sjunct on) || v s BooleanQuery(d sjunct on);
  }

  @Overr de
  publ c Boolean v s (Conjunct on conjunct on) throws QueryParserExcept on {
    return v s Query(conjunct on) || v s BooleanQuery(conjunct on);
  }

  @Overr de
  publ c Boolean v s (Phrase phrase) throws QueryParserExcept on {
    return v s Query(phrase);
  }

  @Overr de
  publ c Boolean v s (Term term) throws QueryParserExcept on {
    return v s Query(term);
  }

  @Overr de
  publ c Boolean v s (Operator operator) throws QueryParserExcept on {
    return v s Query(operator);
  }

  @Overr de
  publ c Boolean v s (Spec alTerm spec al) throws QueryParserExcept on {
    return v s Query(spec al);
  }

  pr vate Boolean v s Query(Query query) throws QueryParserExcept on {
     f (query.hasAnnotat ons()) {
      for (Annotat on annotat on : query.getAnnotat ons()) {
         f (!Annotat on.Type.F ELD.equals(annotat on.getType())) {
          cont nue;
        }
         f (f eldNa s. sEmpty()) {
          return true;
        }
        F eldNa W hBoost value = (F eldNa W hBoost) annotat on.getValue();
         f (f eldNa s.conta ns(value.getF eldNa ())) {
          return true;
        }
      }
    }

    return false;
  }

  pr vate boolean v s BooleanQuery(BooleanQuery query) throws QueryParserExcept on {
    for (Query subQuery : query.getCh ldren()) {
       f (subQuery.accept(t )) {
        return true;
      }
    }

    return false;
  }
}
