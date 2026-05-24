package com.twitter.search.earlybird.querycache;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

import com.twitter.search.common.constants.QueryCacheConstants;
import com.twitter.search.queryparser.query.Query;
import com.twitter.search.queryparser.query.search.SearchOperator;
import com.twitter.search.queryparser.query.search.SearchOperatorConstants;

import static com.twitter.search.common.util.RuleBasedConverter.Rule;

/**
 * Rules to convert exclude operators into cached filters and consolidate them.
 * NOTE: this is copied from blender/core/parser/service/queryparser/QueryCacheConversionRules.java
 * We should remove the blender one once this is in production.
 */
public final class QueryCacheConversionRules {
  static final SearchOperator EXCLUDE_ANTISOCIAL =
      new SearchOperator(SearchOperator.Type.EXCLUDE, SearchOperatorConstants.ANTISOCIAL);
  static final SearchOperator EXCLUDE_SPAM =
      new SearchOperator(SearchOperator.Type.EXCLUDE, SearchOperatorConstants.SPAM);
  static final SearchOperator EXCLUDE_REPLIES =
      new SearchOperator(SearchOperator.Type.EXCLUDE, SearchOperatorConstants.REPLIES);
  static final SearchOperator EXCLUDE_NATIVERETWEETS =
      new SearchOperator(SearchOperator.Type.EXCLUDE, SearchOperatorConstants.NATIVE_RETWEETS);

  public static final SearchOperator CACHED_EXCLUDE_ANTISOCIAL =
      new SearchOperator(SearchOperator.Type.CACHED_FILTER,
                         QueryCacheConstants.EXCLUDE_ANTISOCIAL);
  static final SearchOperator CACHED_EXCLUDE_NATIVERETWEETS =
      new SearchOperator(SearchOperator.Type.CACHED_FILTER,
                         QueryCacheConstants.EXCLUDE_ANTISOCIAL_AND_NATIVERETWEETS);
  static final SearchOperator CACHED_EXCLUDE_SPAM =
      new SearchOperator(SearchOperator.Type.CACHED_FILTER,
                         QueryCacheConstants.EXCLUDE_SPAM);
  static final SearchOperator CACHED_EXCLUDE_SPAM_AND_NATIVERETWEETS =
      new SearchOperator(SearchOperator.Type.CACHED_FILTER,
                         QueryCacheConstants.EXCLUDE_SPAM_AND_NATIVERETWEETS);
  static final SearchOperator CACHED_EXCLUDE_REPLIES =
      new SearchOperator(SearchOperator.Type.CACHED_FILTER,
                         QueryCacheConstants.EXCLUDE_REPLIES);

  private QueryCacheConversionRules() {
  }

  public static final List<Rule<Query>> DEFAULT_RULES = ImmutableList.of(
      // basic translation from exclude:filter to cached filter
      new Rule<>(new Query[]{EXCLUDE_ANTISOCIAL},
                 new Query[]{CACHED_EXCLUDE_ANTISOCIAL}),

      new Rule<>(new Query[]{EXCLUDE_SPAM},
                 new Query[]{CACHED_EXCLUDE_SPAM}),

      new Rule<>(new Query[]{EXCLUDE_NATIVERETWEETS},
                 new Query[]{CACHED_EXCLUDE_NATIVERETWEETS}),

      new Rule<>(new Query[]{EXCLUDE_REPLIES},
                 new Query[]{CACHED_EXCLUDE_REPLIES}),

      // combine two cached filter to a new one
      new Rule<>(new Query[]{CACHED_EXCLUDE_SPAM, CACHED_EXCLUDE_NATIVERETWEETS},
                 new Query[]{CACHED_EXCLUDE_SPAM_AND_NATIVERETWEETS}),

      // Remove redundant filters. A cached filter is redundant when it coexist with a
      // more strict filter. Note all the filter will filter out antisocial.
      new Rule<>(
          new Query[]{CACHED_EXCLUDE_SPAM, CACHED_EXCLUDE_ANTISOCIAL},
          new Query[]{CACHED_EXCLUDE_SPAM}),

      new Rule<>(
          new Query[]{CACHED_EXCLUDE_NATIVERETWEETS, CACHED_EXCLUDE_ANTISOCIAL},
          new Query[]{CACHED_EXCLUDE_NATIVERETWEETS}),

      new Rule<>(
          new Query[]{CACHED_EXCLUDE_SPAM_AND_NATIVERETWEETS, CACHED_EXCLUDE_ANTISOCIAL},
          new Query[]{CACHED_EXCLUDE_SPAM_AND_NATIVERETWEETS}),

      new Rule<>(
          new Query[]{CACHED_EXCLUDE_SPAM_AND_NATIVERETWEETS, CACHED_EXCLUDE_SPAM},
          new Query[]{CACHED_EXCLUDE_SPAM_AND_NATIVERETWEETS}),

      new Rule<>(
          new Query[]{CACHED_EXCLUDE_SPAM_AND_NATIVERETWEETS, CACHED_EXCLUDE_NATIVERETWEETS},
          new Query[]{CACHED_EXCLUDE_SPAM_AND_NATIVERETWEETS})
  );

  public static final List<Query> STRIP_ANNOTATIONS_QUERIES;
  static {
    Set<Query> stripAnnotationsQueries = Sets.newHashSet();
    for (Rule<Query> rule : DEFAULT_RULES) {
      stripAnnotationsQueries.addAll(Arrays.asList(rule.getSources()));
    }
    STRIP_ANNOTATIONS_QUERIES = ImmutableList.copyOf(stripAnnotationsQueries);
  }
}
