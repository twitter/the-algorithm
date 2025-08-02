package com.twitter.home_mitwitterer.product.for_you.side_effect

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mitwitterer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SuggestTypeFeature
import com.twitter.home_mitwitterer.product.for_you.param.ForYouParam.EtwitterperimentStatsParam
import com.twitter.home_mitwitterer.util.CandidatesUtil
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mitwitterer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.Timeline
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ServedStatsSideEffect @Inject() (statsReceiver: StatsReceiver)
    etwittertends PipelineResultSideEffect[PipelineQuery, Timeline] {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("ServedStats")

  private val baseStatsReceiver = statsReceiver.scope(identifier.toString)
  private val suggestTypeStatsReceiver = baseStatsReceiver.scope("SuggestType")
  private val responseSizeStatsReceiver = baseStatsReceiver.scope("ResponseSize")
  private val contentBalanceStatsReceiver = baseStatsReceiver.scope("ContentBalance")

  private val inNetworkStatsReceiver = contentBalanceStatsReceiver.scope("InNetwork")
  private val outOfNetworkStatsReceiver = contentBalanceStatsReceiver.scope("OutOfNetwork")
  private val replyStatsReceiver = contentBalanceStatsReceiver.scope("Reply")
  private val originalStatsReceiver = contentBalanceStatsReceiver.scope("Original")

  private val emptyStatsReceiver = responseSizeStatsReceiver.scope("Empty")
  private val lessThan5StatsReceiver = responseSizeStatsReceiver.scope("LessThan5")
  private val lessThan10StatsReceiver = responseSizeStatsReceiver.scope("LessThan10")

  override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery, Timeline]
  ): Stitch[Unit] = {
    val tweetCandidates = CandidatesUtil
      .getItemCandidates(inputs.selectedCandidates).filter(_.isCandidateType[TweetCandidate]())

    val etwitterpBucket = inputs.query.params(EtwitterperimentStatsParam)

    recordSuggestTypeStats(tweetCandidates, etwitterpBucket)
    recordContentBalanceStats(tweetCandidates, etwitterpBucket)
    recordResponseSizeStats(tweetCandidates, etwitterpBucket)
    Stitch.Unit
  }

  def recordSuggestTypeStats(
    candidates: Seq[ItemCandidateWithDetails],
    etwitterpBucket: String
  ): Unit = {
    candidates.groupBy(getSuggestType).foreach {
      case (suggestType, suggestTypeCandidates) =>
        suggestTypeStatsReceiver
          .scope(etwitterpBucket).counter(suggestType).incr(suggestTypeCandidates.size)
    }
  }

  def recordContentBalanceStats(
    candidates: Seq[ItemCandidateWithDetails],
    etwitterpBucket: String
  ): Unit = {
    val (in, oon) = candidates.partition(_.features.getOrElse(InNetworkFeature, true))
    inNetworkStatsReceiver.counter(etwitterpBucket).incr(in.size)
    outOfNetworkStatsReceiver.counter(etwitterpBucket).incr(oon.size)

    val (reply, original) =
      candidates.partition(_.features.getOrElse(InReplyToTweetIdFeature, None).isDefined)
    replyStatsReceiver.counter(etwitterpBucket).incr(reply.size)
    originalStatsReceiver.counter(etwitterpBucket).incr(original.size)
  }

  def recordResponseSizeStats(
    candidates: Seq[ItemCandidateWithDetails],
    etwitterpBucket: String
  ): Unit = {
    if (candidates.size == 0) emptyStatsReceiver.counter(etwitterpBucket).incr()
    if (candidates.size < 5) lessThan5StatsReceiver.counter(etwitterpBucket).incr()
    if (candidates.size < 10) lessThan10StatsReceiver.counter(etwitterpBucket).incr()
  }

  private def getSuggestType(candidate: CandidateWithDetails): String =
    candidate.features.getOrElse(SuggestTypeFeature, None).map(_.name).getOrElse("None")
}
