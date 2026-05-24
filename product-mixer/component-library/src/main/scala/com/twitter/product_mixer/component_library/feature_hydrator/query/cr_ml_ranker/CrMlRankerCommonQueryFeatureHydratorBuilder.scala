package com.twitter.product_mixer.component_library.feature_hydrator.query.cr_ml_ranker

import com.twitter.cr_ml_ranker.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Builds a query hydrator that hydrates Common Features for the given Query from CR ML Ranker
 * to be later used to call CR ML Ranker for scoring using the desired [[RankingConfigBuilder]]
 * for building the ranking config.
 */
@Singleton
class CrMlRankerCommonQueryFeatureHydratorBuilder @Inject() (
  crMlRanker: t.CrMLRanker.MethodPerEndpoint) {

  def build(rankingConfigSelector: RankingConfigBuilder): CrMlRankerCommonQueryFeatureHydrator =
    new CrMlRankerCommonQueryFeatureHydrator(crMlRanker, rankingConfigSelector)
}
