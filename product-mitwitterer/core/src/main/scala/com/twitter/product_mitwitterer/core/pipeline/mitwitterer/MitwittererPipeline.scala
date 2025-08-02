package com.twitter.product_mitwitterer.core.pipeline.mitwitterer

import com.twitter.product_mitwitterer.core.model.common.identifier.MitwittererPipelineIdentifier
import com.twitter.product_mitwitterer.core.pipeline.Pipeline
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Arrow

/**
 * A Mitwitterer Pipeline
 *
 * This is an abstract class, as we only construct these via the [[MitwittererPipelineBuilder]].
 *
 * A [[MitwittererPipeline]] is capable of processing requests (queries) and returning responses (results)
 * in the correct format to directly send to users.
 *
 * @tparam Query the domain model for the query or request
 * @tparam Result the final marshalled result type
 */
abstract class MitwittererPipeline[Query <: PipelineQuery, Result] private[mitwitterer]
    etwittertends Pipeline[Query, Result] {
  override private[core] val config: MitwittererPipelineConfig[Query, _, Result]
  override val arrow: Arrow[Query, MitwittererPipelineResult[Result]]
  override val identifier: MitwittererPipelineIdentifier
}
