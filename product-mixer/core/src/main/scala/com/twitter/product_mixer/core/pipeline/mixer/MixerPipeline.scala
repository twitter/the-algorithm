package com.twitter.product_mixer.core.pipeline.mixer

import com.twitter.product_mixer.core.model.common.identifier.MixerPipelineIdentifier
import com.twitter.product_mixer.core.pipeline.Pipeline
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Arrow

/**
 * A Mixer Pipeline
 *
 * This is an abstract class, as we only construct these via the [[MixerPipelineBuilder]].
 *
 * A [[MixerPipeline]] is capable of processing requests (queries) and returning responses (results)
 * in the correct format to directly send to users.
 *
 * @tparam Query the domain model for the query or request
 * @tparam Result the final marshalled result type
 */
abstract class MixerPipeline[Query <: PipelineQuery, Result] private[mixer]
    extends Pipeline[Query, Result] {
  override private[core] val config: MixerPipelineConfig[Query, _, Result]
  override val arrow: Arrow[Query, MixerPipelineResult[Result]]
  override val identifier: MixerPipelineIdentifier
}
