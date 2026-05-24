package com.twitter.product_mixer.core.pipeline.recommendation

import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.common.identifier.RecommendationPipelineIdentifier
import com.twitter.product_mixer.core.pipeline.Pipeline
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Arrow

/**
 * A Recommendation Pipeline
 *
 * This is an abstract class, as we only construct these via the [[RecommendationPipelineBuilder]].
 *
 * A [[RecommendationPipeline]] is capable of processing requests (queries) and returning responses (results)
 * in the correct format to directly send to users.
 *
 * @tparam Query the domain model for the query or request
 * @tparam Candidate the type of the candidates
 * @tparam Result the final marshalled result type
 */
abstract class RecommendationPipeline[
  Query <: PipelineQuery,
  Candidate <: UniversalNoun[Any],
  Result]
    extends Pipeline[Query, Result] {
  override private[core] val config: RecommendationPipelineConfig[Query, Candidate, _, Result]
  override val arrow: Arrow[Query, RecommendationPipelineResult[Candidate, Result]]
  override val identifier: RecommendationPipelineIdentifier
}
