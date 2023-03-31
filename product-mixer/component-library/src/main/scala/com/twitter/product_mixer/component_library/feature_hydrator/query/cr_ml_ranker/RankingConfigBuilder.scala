package com.twitter.product_mixer.component_library.feature_hydrator.query.cr_ml_ranker

import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.cr_ml_ranker.{thriftscala => t}

/**
 * Builder for constructing a ranking config from a query
 */
trait RankingConfigBuilder {
  def apply(query: PipelineQuery): t.RankingConfig
}
