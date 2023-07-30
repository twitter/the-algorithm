package com.X.product_mixer.component_library.feature_hydrator.query.cr_ml_ranker

import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.cr_ml_ranker.{thriftscala => t}

/**
 * Builder for constructing a ranking config from a query
 */
trait RankingConfigBuilder {
  def apply(query: PipelineQuery): t.RankingConfig
}
