package com.tw ter.search.earlyb rd.querycac ;

 mport java.ut l.Arrays;
 mport java.ut l.L st;
 mport java.ut l.Set;

 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect.Sets;

 mport com.tw ter.search.common.constants.QueryCac Constants;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.search.SearchOperator;
 mport com.tw ter.search.queryparser.query.search.SearchOperatorConstants;

 mport stat c com.tw ter.search.common.ut l.RuleBasedConverter.Rule;

/**
 * Rules to convert exclude operators  nto cac d f lters and consol date t m.
 * NOTE: t   s cop ed from blender/core/parser/serv ce/queryparser/QueryCac Convers onRules.java
 *   should remove t  blender one once t   s  n product on.
 */
publ c f nal class QueryCac Convers onRules {
  stat c f nal SearchOperator EXCLUDE_ANT SOC AL =
      new SearchOperator(SearchOperator.Type.EXCLUDE, SearchOperatorConstants.ANT SOC AL);
  stat c f nal SearchOperator EXCLUDE_SPAM =
      new SearchOperator(SearchOperator.Type.EXCLUDE, SearchOperatorConstants.SPAM);
  stat c f nal SearchOperator EXCLUDE_REPL ES =
      new SearchOperator(SearchOperator.Type.EXCLUDE, SearchOperatorConstants.REPL ES);
  stat c f nal SearchOperator EXCLUDE_NAT VERETWEETS =
      new SearchOperator(SearchOperator.Type.EXCLUDE, SearchOperatorConstants.NAT VE_RETWEETS);

  publ c stat c f nal SearchOperator CACHED_EXCLUDE_ANT SOC AL =
      new SearchOperator(SearchOperator.Type.CACHED_F LTER,
                         QueryCac Constants.EXCLUDE_ANT SOC AL);
  stat c f nal SearchOperator CACHED_EXCLUDE_NAT VERETWEETS =
      new SearchOperator(SearchOperator.Type.CACHED_F LTER,
                         QueryCac Constants.EXCLUDE_ANT SOC AL_AND_NAT VERETWEETS);
  stat c f nal SearchOperator CACHED_EXCLUDE_SPAM =
      new SearchOperator(SearchOperator.Type.CACHED_F LTER,
                         QueryCac Constants.EXCLUDE_SPAM);
  stat c f nal SearchOperator CACHED_EXCLUDE_SPAM_AND_NAT VERETWEETS =
      new SearchOperator(SearchOperator.Type.CACHED_F LTER,
                         QueryCac Constants.EXCLUDE_SPAM_AND_NAT VERETWEETS);
  stat c f nal SearchOperator CACHED_EXCLUDE_REPL ES =
      new SearchOperator(SearchOperator.Type.CACHED_F LTER,
                         QueryCac Constants.EXCLUDE_REPL ES);

  pr vate QueryCac Convers onRules() {
  }

  publ c stat c f nal L st<Rule<Query>> DEFAULT_RULES =  mmutableL st.of(
      // bas c translat on from exclude:f lter to cac d f lter
      new Rule<>(new Query[]{EXCLUDE_ANT SOC AL},
                 new Query[]{CACHED_EXCLUDE_ANT SOC AL}),

      new Rule<>(new Query[]{EXCLUDE_SPAM},
                 new Query[]{CACHED_EXCLUDE_SPAM}),

      new Rule<>(new Query[]{EXCLUDE_NAT VERETWEETS},
                 new Query[]{CACHED_EXCLUDE_NAT VERETWEETS}),

      new Rule<>(new Query[]{EXCLUDE_REPL ES},
                 new Query[]{CACHED_EXCLUDE_REPL ES}),

      // comb ne two cac d f lter to a new one
      new Rule<>(new Query[]{CACHED_EXCLUDE_SPAM, CACHED_EXCLUDE_NAT VERETWEETS},
                 new Query[]{CACHED_EXCLUDE_SPAM_AND_NAT VERETWEETS}),

      // Remove redundant f lters. A cac d f lter  s redundant w n   coex st w h a
      // more str ct f lter. Note all t  f lter w ll f lter out ant soc al.
      new Rule<>(
          new Query[]{CACHED_EXCLUDE_SPAM, CACHED_EXCLUDE_ANT SOC AL},
          new Query[]{CACHED_EXCLUDE_SPAM}),

      new Rule<>(
          new Query[]{CACHED_EXCLUDE_NAT VERETWEETS, CACHED_EXCLUDE_ANT SOC AL},
          new Query[]{CACHED_EXCLUDE_NAT VERETWEETS}),

      new Rule<>(
          new Query[]{CACHED_EXCLUDE_SPAM_AND_NAT VERETWEETS, CACHED_EXCLUDE_ANT SOC AL},
          new Query[]{CACHED_EXCLUDE_SPAM_AND_NAT VERETWEETS}),

      new Rule<>(
          new Query[]{CACHED_EXCLUDE_SPAM_AND_NAT VERETWEETS, CACHED_EXCLUDE_SPAM},
          new Query[]{CACHED_EXCLUDE_SPAM_AND_NAT VERETWEETS}),

      new Rule<>(
          new Query[]{CACHED_EXCLUDE_SPAM_AND_NAT VERETWEETS, CACHED_EXCLUDE_NAT VERETWEETS},
          new Query[]{CACHED_EXCLUDE_SPAM_AND_NAT VERETWEETS})
  );

  publ c stat c f nal L st<Query> STR P_ANNOTAT ONS_QUER ES;
  stat c {
    Set<Query> str pAnnotat onsQuer es = Sets.newHashSet();
    for (Rule<Query> rule : DEFAULT_RULES) {
      str pAnnotat onsQuer es.addAll(Arrays.asL st(rule.getS ces()));
    }
    STR P_ANNOTAT ONS_QUER ES =  mmutableL st.copyOf(str pAnnotat onsQuer es);
  }
}
