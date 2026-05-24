package com.twitter.tweet_mixer.functional_component.gate

import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableNonEmptySearchHistoryUserCheck

/**
 * Continue for users with non-empty search history
 *
 * Check gate param last to only evaluate for eligible users and avoid experimental dilution.
 */
case class AllowNonEmptySearchHistoryUserGate(
  signalFn: PipelineQuery => Seq[String],
) extends Gate[PipelineQuery] {
  override val identifier: GateIdentifier = GateIdentifier("AllowNonEmptySearchHistoryUser")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = {
    val queries: Seq[String] = signalFn(query)
    val continue = queries.nonEmpty && query.params(EnableNonEmptySearchHistoryUserCheck)
    Stitch.value(continue)
  }
}
