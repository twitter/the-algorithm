package com.twitter.tweet_mixer.functional_component.gate

import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationOnceInTimesShow
import scala.util.Random

/**
 * Gate continues probablistically based on the FS config.
 * If FS returns 3, it should return true once in 3 times.
 */
object ProbablisticPassGate extends Gate[PipelineQuery] {

  override val identifier: GateIdentifier = GateIdentifier("ProbablisticPass")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = Stitch.value {
    val onceInTimesShow = query.params(ContentExplorationOnceInTimesShow)
    val randomDouble = Random.nextDouble()
    randomDouble < (1.0 / onceInTimesShow)
  }
}
