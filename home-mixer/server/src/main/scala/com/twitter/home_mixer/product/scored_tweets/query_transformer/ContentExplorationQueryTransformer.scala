package com.twitter.home_mixer.product.scored_tweets.query_transformer

import com.twitter.home_mixer.functional_component.feature_hydrator.UserSubLevelCategoriesFeature
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.ContentExplorationCandidateVersionParam
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer

case class ContentExplorationQueryRequest(
  userCategories: Seq[(String, Double)],
  version: String)

object ContentExplorationQueryTransformer
    extends CandidatePipelineQueryTransformer[
      ScoredTweetsQuery,
      ContentExplorationQueryRequest
    ] {

  override def transform(query: ScoredTweetsQuery): ContentExplorationQueryRequest = {
    ContentExplorationQueryRequest(
      userCategories = query.features.get
        .getOrElse(UserSubLevelCategoriesFeature, Seq.empty[(Long, Double)])
        .map { case (id, score) => (id.toString, score) },
      version = query.params(ContentExplorationCandidateVersionParam)
    )
  }
}
