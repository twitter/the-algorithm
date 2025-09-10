package com.twitter.home_mixer.product.for_you.side_effect

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.AuthorListForDataCollectionParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.AuthorListForStatsParam
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersFeature
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.response.urt.Timeline
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.util.logging.Logging

case class ServedStatsSideEffect(
  candidatePipelines: Set[CandidatePipelineIdentifier],
  statsReceiver: StatsReceiver)
    extends PipelineResultSideEffect[PipelineQuery, Timeline]
    with Logging {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("ServedStats")

  private val baseStatsReceiver = statsReceiver.scope(identifier.toString)
  private val authorStatsReceiver = baseStatsReceiver.scope("Author")
  private val tweetStatsReceiver = baseStatsReceiver.scope("Tweet")
  private val servedTypeStatsReceiver = baseStatsReceiver.scope("ServedType")
  private val followedUsersStatsReceiver = baseStatsReceiver.scope("FollowedUsers")
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
      .getItemCandidates(inputs.selectedCandidates)
      .filter(candidate => candidatePipelines.contains(candidate.source))

    recordAuthorStats(
      candidates = tweetCandidates,
      authors = inputs.query.params(AuthorListForDataCollectionParam),
      tweetLevelAuthors = inputs.query.params(AuthorListForStatsParam)
    )
    recordServedTypeStats(tweetCandidates)
    recordFollowedUsersStats(
      tweetCandidates,
      inputs.query.features.map(_.getOrElse(SGSFollowedUsersFeature, Seq.empty).size).getOrElse(0),
    )
    recordContentBalanceStats(tweetCandidates)
    recordResponseSizeStats(tweetCandidates)
    logColdContentData(tweetCandidates, inputs.query.getRequiredUserId)
    Stitch.Unit
  }

  def recordAuthorStats(
    candidates: Seq[CandidateWithDetails],
    authors: Set[Long],
    tweetLevelAuthors: Set[Long]
  ): Unit = {
    val filtered = candidates
      .filter { candidate =>
        candidate.features.getOrElse(AuthorIdFeature, None).exists(authors.contains) &&
        // Only include original tweets
        (!candidate.features.getOrElse(IsRetweetFeature, false)) &&
        candidate.features.getOrElse(InReplyToTweetIdFeature, None).isEmpty
      }

    filtered
      .groupBy { candidate =>
        (getServedType(candidate), candidate.features.get(AuthorIdFeature).get)
      }
      .foreach {
        case ((servedType, authorId), authorCandidates) =>
          val authorStr = authorId.toString.takeRight(9)
          authorStatsReceiver.scope(authorStr).counter(servedType).incr(authorCandidates.size)
      }

    filtered.map { candidate =>
      val authorId = candidate.features.get(AuthorIdFeature).get
      val authorStr = authorId.toString.takeRight(9)
      authorStatsReceiver.scope(authorStr).counter(candidate.candidateIdLong.toString).incr()
      if (tweetLevelAuthors.contains(authorId))
        tweetStatsReceiver.counter(candidate.candidateIdLong.toString.takeRight(10)).incr()
    }
  }

  def logColdContentData(candidates: Seq[CandidateWithDetails], viewerId: Long): Unit = {
    candidates.foreach { candidate =>
      val servedType = candidate.features.get(ServedTypeFeature)
      if (servedType == hmt.ServedType.ForYouContentExplorationDeepRetrievalI2i) {
        logger.info("Tier1: " + candidate.candidateIdLong + " viewerId: " + viewerId)
      } else if (servedType == hmt.ServedType.ForYouContentExplorationTier2DeepRetrievalI2i) {
        logger.info("Tier2: " + candidate.candidateIdLong + " viewerId: " + viewerId)
      }
    }
  }

  def recordServedTypeStats(
    candidates: Seq[ItemCandidateWithDetails],
  ): Unit = {
    candidates.groupBy(getServedType).foreach {
      case (servedType, servedTypeCandidates) =>
        servedTypeStatsReceiver.counter(servedType).incr(servedTypeCandidates.size)
    }
  }

  def recordFollowedUsersStats(
    candidates: Seq[ItemCandidateWithDetails],
    followedUsers: Int,
  ): Unit = {
    val followedUsersScope =
      if (followedUsers < 10) "0-9"
      else if (followedUsers < 100) "10-99"
      else if (followedUsers < 1000) "100-999"
      else if (followedUsers < 10000) "1000-9999"
      else ">10000"

    candidates.groupBy(getServedType).foreach {
      case (servedType, servedTypeCandidates) =>
        followedUsersStatsReceiver
          .scope(followedUsersScope).counter(servedType)
          .incr(servedTypeCandidates.size)
    }
  }

  def recordContentBalanceStats(
    candidates: Seq[ItemCandidateWithDetails],
  ): Unit = {
    val (in, oon) = candidates.partition(_.features.getOrElse(InNetworkFeature, true))
    inNetworkStatsReceiver.counter().incr(in.size)
    outOfNetworkStatsReceiver.counter().incr(oon.size)

    val (reply, original) =
      candidates.partition(_.features.getOrElse(InReplyToTweetIdFeature, None).isDefined)
    replyStatsReceiver.counter().incr(reply.size)
    originalStatsReceiver.counter().incr(original.size)
  }

  def recordResponseSizeStats(
    candidates: Seq[ItemCandidateWithDetails],
  ): Unit = {
    if (candidates.size == 0) emptyStatsReceiver.counter().incr()
    if (candidates.size < 5) lessThan5StatsReceiver.counter().incr()
    if (candidates.size < 10) lessThan10StatsReceiver.counter().incr()
  }

  private def getServedType(candidate: CandidateWithDetails): String =
    candidate.features.get(ServedTypeFeature).name

  override val alerts = Seq(HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert())
}
