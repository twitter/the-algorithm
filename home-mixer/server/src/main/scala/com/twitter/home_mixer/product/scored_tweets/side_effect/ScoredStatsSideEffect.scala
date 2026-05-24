package com.twitter.home_mixer.product.scored_tweets.side_effect

import com.twitter.core_workflows.user_model.thriftscala.UserState
import com.twitter.finagle.stats.Counter
import com.twitter.finagle.stats.NullStatsReceiver.NullCounter
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.FromInNetworkSourceFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokAnnotationsFeature
import com.twitter.home_mixer.model.HomeFeatures.HasVideoFeature
import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mixer.model.HomeFeatures.LowSignalUserFeature
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedAuthorIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.model.HomeFeatures.SlopAuthorScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetMediaIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.UserStateFeature
import com.twitter.home_mixer.model.HomeFeatures.WeightedModelScoreFeature
import com.twitter.home_mixer.model.PredictedScoreFeature
import com.twitter.home_mixer.model.PredictedScoreFeature.PredictedScoreFeatures
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.AuthorListForDataCollectionParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.RequestNormalizedScoresParam
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.CachedScoredTweetsCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsTweetMixerCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsResponse
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.component_library.feature_hydrator.query.impressed_tweets.ImpressedTweets
import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersFeature
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidatePipelines
import com.twitter.product_mixer.core.model.common.presentation.CandidateSourcePosition
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.util.Memoize
import com.twitter.util.logging.Logging
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class ScoredStatsSideEffect @Inject() (
  statsReceiver: StatsReceiver)
    extends PipelineResultSideEffect[ScoredTweetsQuery, ScoredTweetsResponse]
    with Logging {

  import ScoredStatsSideEffect._
  override val identifier: SideEffectIdentifier = SideEffectIdentifier("ScoredStats")

  private val baseStatsReceiver = statsReceiver.scope(identifier.toString)
  private val authorStatsReceiver = baseStatsReceiver.scope("Author")
  private val authorScoreStatsReceiver = baseStatsReceiver.scope("AuthorScore10000x")
  private val servedTypeStatsReceiver = baseStatsReceiver.scope("ServedType")
  private val contentBalanceStatsReceiver = baseStatsReceiver.scope("ContentBalance")
  private val grokAnnotationsStatsReceiver = baseStatsReceiver.scope("GrokAnnotations")

  private val inNetworkStatsReceiver = contentBalanceStatsReceiver.scope("InNetwork")
  private val outOfNetworkStatsReceiver = contentBalanceStatsReceiver.scope("OutOfNetwork")
  private val replyStatsReceiver = contentBalanceStatsReceiver.scope("Reply")
  private val originalStatsReceiver = contentBalanceStatsReceiver.scope("Original")
  private val retweetStatsReceiver = contentBalanceStatsReceiver.scope("Retweet")

  private val tweetMixerRecallStatsReceiver = baseStatsReceiver.scope("TweetMixerRecall10000x")
  private val deepRetrievalRecallStatsReceiver =
    baseStatsReceiver.scope("DeepRetrievalRecall10000x")
  private val tweetMixerTweetsLandOnTopKStatsReceiver = baseStatsReceiver.scope("TweetMixerTweets")
  private val deepRetrievalTweetsLandOnTopKStatsReceiver =
    baseStatsReceiver.scope("DeepRetrievalTweets")

  private val grokAnnotationsInStatsReceiver = grokAnnotationsStatsReceiver.scope("InNetwork")
  private val grokAnnotationsOonStatsReceiver = grokAnnotationsStatsReceiver.scope("OutOfNetwork")
  private val grokAnnotationsReplyStatsReceiver = grokAnnotationsStatsReceiver.scope("Reply")

  private val signalStatsReceiver = baseStatsReceiver.scope("signal")
  private val authorDiversityStatsReceiver = baseStatsReceiver.scope("authorDiversity")
  private val impressedAuthorSizeStat = authorDiversityStatsReceiver.stat("impressedAuthorSize")
  private val meanImpressedAuthorCountStat =
    authorDiversityStatsReceiver.stat("meanImpressedAuthorCount1000x")

  private val grokSlopScoreStatsReceiver = baseStatsReceiver.scope("GrokSlopScore")

  private val inNetAuthorStatsReceiver = baseStatsReceiver.scope("InNetAuthor")

  private val StatsReadabilityMultiplier = 1000
  private val SmallFollowGraphSize = 25

  private val StatsEligibleGrokTopics = Set(
    "News",
    "Sports",
    "Entertainment",
    "Business & Finance",
    "Technology",
    "Politics",
    "Fashion & Beauty",
    "Gaming",
    "Lifestyle",
    "Movies & TV",
    "Music",
    "Travel",
    "Food",
    "Comedy",
    "Health & Fitness"
  )

  // Group of stats to collect per model
  case class ModelStats(normalized: Boolean, rankingMode: String) {
    val normalizedScope = if (normalized) "normalized" else "weighted"
    val scopedStatsReceiver = baseStatsReceiver.scope(normalizedScope).scope(rankingMode)
    val PredictedScoreCounterName = f"predictedScore${StatsReadabilityMultiplier}xSum"
    val requestCounter = scopedStatsReceiver.counter("candidatesSum")
    private val predictedScoreCounters: Map[PredictedScoreFeature, Counter] =
      PredictedScoreFeatures.map { scoreFeature =>
        (
          scoreFeature,
          scopedStatsReceiver.counter(scoreFeature.statName, PredictedScoreCounterName))
      }.toMap

    def getPredictedScoreCounter(scoreFeature: PredictedScoreFeature): Counter =
      predictedScoreCounters.getOrElse(scoreFeature, NullCounter)
  }

  // Memoize stats object so they are not created per request
  private val statsPerModel = Memoize[(Boolean, String), ModelStats] {
    case (normalized, rankingMode) => ModelStats(normalized, rankingMode)
  }

  override def apply(
    inputs: PipelineResultSideEffect.Inputs[ScoredTweetsQuery, ScoredTweetsResponse]
  ): Stitch[Unit] = {
    val scopeCandidateMap = Map(
      "selected" -> inputs.selectedCandidates,
      "dropped" -> inputs.droppedCandidates,
      "remaining" -> inputs.remainingCandidates
    )
    // We choose 50 because we don't have per head scores for cached candidates
    val topSelectedCandidates = inputs.selectedCandidates.take(50)

    val impressedTweetIds =
      inputs.query.features.map(_.getOrElse(ImpressedTweets, Seq.empty)).getOrElse(Seq.empty).toSet
    val servedAuthorMap: Map[Long, Seq[Long]] =
      inputs.query.features
        .map(_.getOrElse(ServedAuthorIdsFeature, Map.empty[Long, Seq[Long]]))
        .getOrElse(Map.empty[Long, Seq[Long]])
    recordAuthorDiversityStats(impressedTweetIds, servedAuthorMap)

    recordScoreStats(
      topSelectedCandidates,
      inputs.query.params(RequestNormalizedScoresParam),
      inputs.query.params(ScoredTweetsParam.TweetMixerRankingModeForStatsRecallAtKParam),
      inputs.query
    )

    val allCandidates: Seq[CandidateWithDetails] =
      inputs.selectedCandidates ++ inputs.droppedCandidates
    recordInNetworkAuthorStats(allCandidates, inputs.query, "retrieved")
    recordInNetworkAuthorStats(inputs.selectedCandidates, inputs.query, "selected")

    scopeCandidateMap.map {
      case (scope, candidates) =>
        recordAuthorStats(candidates, scope, inputs.query.params(AuthorListForDataCollectionParam))
        recordServedTypeStats(candidates, scope)
        recordGrokAnnotationsStats(candidates, scope)
        recordContentBalanceStats(
          candidates,
          scope,
          inputs.query.params(ScoredTweetsParam.TweetMixerRankingModeForStatsRecallAtKParam)
        )
        recordHasVideoStats(candidates, scope)
        recordHasMediaStats(candidates, scope)
        recordSlopScoreStats(candidates, scope)
    }

    recordTweetMixerRecallTopKStats(
      inputs.selectedCandidates.filter(
        _.source != CachedScoredTweetsCandidatePipelineConfig.Identifier),
      inputs.droppedCandidates.filter(
        _.source != CachedScoredTweetsCandidatePipelineConfig.Identifier),
      inputs.query.params(ScoredTweetsParam.TweetMixerRankingModeForStatsRecallAtKParam)
    )

    recordSignalStats(inputs.query)

    Stitch.Unit
  }

  private def recordAuthorDiversityStats(
    impressedTweetIds: Set[Long],
    servedAuthorMap: Map[Long, Seq[Long]]
  ): Unit = {
    val authorFreq = servedAuthorMap
      .collect {
        case (authorId, tweetIds) =>
          val impressedCount = tweetIds.count(impressedTweetIds.contains)
          if (impressedCount > 0) Some(authorId -> impressedCount) else None
      }.flatten.toMap

    val impressedAuthorSize = authorFreq.size
    val meanImpressedAuthorCount =
      if (authorFreq.isEmpty) 0.0f else authorFreq.values.sum.toFloat / impressedAuthorSize

    impressedAuthorSizeStat.add(impressedAuthorSize)
    meanImpressedAuthorCountStat.add(meanImpressedAuthorCount * 1000)
  }

  private def recordScoreStats(
    candidates: Seq[CandidateWithDetails],
    normalized: Boolean,
    rankingMode: String,
    query: PipelineQuery
  ): Unit = {
    val modelStats = statsPerModel((normalized, rankingMode))
    modelStats.requestCounter.incr()
    PredictedScoreFeatures.map { predictedScoreFeature =>
      val counter = modelStats.getPredictedScoreCounter(predictedScoreFeature)
      val predictedScoreSum = candidates
        .map { candidate =>
          predictedScoreFeature
            .extractScore(candidate.features, query)
            .getOrElse(0.0) * StatsReadabilityMultiplier
        }.sum.toInt
      counter.incr(predictedScoreSum)
    }
  }

  private def recordTweetMixerRecallTopKStats(
    selectedCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    rankingMode: String
  ): Unit = {
    val allCandidates = selectedCandidates ++ droppedCandidates
    val allCandidatesByHeavyRanker = allCandidates
      .sortBy(
        -_.features.getOrElse(WeightedModelScoreFeature, None).getOrElse(Double.NegativeInfinity))
    val tweetMixerTweets = allCandidatesByHeavyRanker.filter(fromTweetMixer)
    val tweetMixerTweetsSortByRank =
      tweetMixerTweets.sortBy(candidate => candidate.features.get(CandidateSourcePosition))

    val deepRetrievalTweets = tweetMixerTweets.filter(tweetMixerTweet =>
      tweetMixerTweet.features.get(
        ServedTypeFeature) == hmt.ServedType.ForYouDeepRetrieval || tweetMixerTweet.features.get(
        ServedTypeFeature) == hmt.ServedType.ForYouEvergreenDeepRetrieval)
    val deepRetrievalTweetsSortByRank =
      deepRetrievalTweets.sortBy(_.features.get(CandidateSourcePosition))

    if (deepRetrievalTweets.nonEmpty) {
      HeavyRankerTopK.foreach { K =>
        deepRetrievalTweetsLandOnTopKStatsReceiver
          .scope(rankingMode)
          .stat(s"LandOnTop${K}")
          .add(allCandidatesByHeavyRanker
            .take(K).count { candidate =>
              fromServedType(candidate, hmt.ServedType.ForYouDeepRetrieval) || fromServedType(
                candidate,
                hmt.ServedType.ForYouEvergreenDeepRetrieval)
            })
      }

      deepRetrievalTweetsLandOnTopKStatsReceiver
        .scope(rankingMode)
        .stat("LandInSelected")
        .add(selectedCandidates.count { candidate =>
          fromServedType(candidate, hmt.ServedType.ForYouDeepRetrieval) || fromServedType(
            candidate,
            hmt.ServedType.ForYouEvergreenDeepRetrieval)
        })

      DeepRetrievalRecallTopK.foreach { K =>
        val rankedByHeavyRankerScoreTopK = deepRetrievalTweets.take(K)
        val rankedByTweetMixerRankTopK = deepRetrievalTweetsSortByRank.take(K)
        val intersectTweets = rankedByHeavyRankerScoreTopK
          .map(candidate => candidate.candidateIdLong)
          .intersect(rankedByTweetMixerRankTopK.map(candidate => candidate.candidateIdLong))

        deepRetrievalRecallStatsReceiver
          .scope(rankingMode)
          .stat(s"RecallAt${K}")
          .add(intersectTweets.size * 10000 / Math.min(K, deepRetrievalTweets.size))
      }
    }

    if (tweetMixerTweets.nonEmpty) {
      TweetMixerRecallTopK.foreach { K =>
        tweetMixerTweetsLandOnTopKStatsReceiver
          .scope(rankingMode)
          .stat(s"LandOnTop${K}")
          .add(allCandidatesByHeavyRanker
            .take(K).count(fromTweetMixer))
      }

      tweetMixerTweetsLandOnTopKStatsReceiver
        .scope(rankingMode)
        .stat("LandInSelected")
        .add(selectedCandidates.count(fromTweetMixer))

      TweetMixerRecallTopK.foreach { K =>
        val rankedByHeavyRankerScoreTopK = tweetMixerTweets.take(K)
        val rankedByTweetMixerRankTopK = tweetMixerTweetsSortByRank.take(K)
        val intersectTweets = rankedByHeavyRankerScoreTopK
          .map(candidate => candidate.candidateIdLong)
          .intersect(rankedByTweetMixerRankTopK.map(candidate => candidate.candidateIdLong))

        tweetMixerRecallStatsReceiver
          .scope(rankingMode)
          .stat(s"RecallAt${K}")
          .add(intersectTweets.size * 10000 / Math.min(K, tweetMixerTweets.size))
      }
    }
  }

  def recordAuthorStats(
    candidates: Seq[CandidateWithDetails],
    scope: String,
    authors: Set[Long]
  ): Unit = {
    candidates
      .filter { candidate =>
        candidate.features.getOrElse(AuthorIdFeature, None).exists(authors.contains) &&
        // Only include original tweets
        (!candidate.features.getOrElse(IsRetweetFeature, false)) &&
        candidate.features.getOrElse(InReplyToTweetIdFeature, None).isEmpty
      }
      .groupBy { candidate =>
        val servedType = candidate.features.getOrElse(ServedTypeFeature, hmt.ServedType.Undefined)
        (servedType.name, candidate.features.get(AuthorIdFeature).get)
      }
      .foreach {
        case ((servedType, authorId), authorCandidates) =>
          val authorStr = authorId.toString.takeRight(9)
          authorStatsReceiver
            .scope(scope).scope(authorStr).counter(servedType)
            .incr(authorCandidates.size)

          authorCandidates.map { candidate =>
            val score =
              candidate.features.getOrElse(ScoreFeature, None).getOrElse(0.0).toFloat * 10000
            authorScoreStatsReceiver.scope(scope).scope(authorStr).stat(servedType).add(score)
          }
      }
  }

  def recordServedTypeStats(
    candidates: Seq[CandidateWithDetails],
    scope: String
  ): Unit = {
    candidates.groupBy(getServedType).foreach {
      case (servedType, servedTypeCandidates) =>
        servedTypeStatsReceiver.scope(scope).counter(servedType).incr(servedTypeCandidates.size)
    }
  }

  private def recordHasVideoStats(
    candidates: Seq[CandidateWithDetails],
    scope: String
  ): Unit = {
    candidates.groupBy(_.features.getOrElse(HasVideoFeature, false)).foreach {
      case (hasVideo, hasVideoCandidates) =>
        servedTypeStatsReceiver
          .scope(scope).counter(if (hasVideo) "HasVideo" else "NoVideo").incr(
            hasVideoCandidates.size)
    }
  }

  private def recordHasMediaStats(
    candidates: Seq[CandidateWithDetails],
    scope: String
  ): Unit = {
    candidates.groupBy(_.features.getOrElse(TweetMediaIdsFeature, Seq[Long]()).nonEmpty).foreach {
      case (hasMedia, hasMediaCandidates) =>
        servedTypeStatsReceiver
          .scope(scope).counter(if (hasMedia) "HasMedia" else "NoMedia").incr(
            hasMediaCandidates.size)
    }
  }

  def recordGrokAnnotationsStats(
    candidates: Seq[CandidateWithDetails],
    scope: String
  ): Unit = {
    candidates.foreach { candidate =>
      val annotations = candidate.features.getOrElse(GrokAnnotationsFeature, None)

      val topics = annotations
        .map(_.topics.filter(StatsEligibleGrokTopics.contains(_)))
        .filter(_.nonEmpty)

      val in = candidate.features.getOrElse(InNetworkFeature, true)
      val reply = candidate.features.getOrElse(InReplyToTweetIdFeature, None).isDefined
      val baseStat =
        if (reply) grokAnnotationsReplyStatsReceiver
        else if (in) grokAnnotationsInStatsReceiver
        else grokAnnotationsOonStatsReceiver

      if (topics.isDefined) baseStat.scope(scope).counter("Available").incr()
      else baseStat.scope(scope).counter("Unavailable").incr()

      topics.map(_.map(topic => baseStat.counter(topic).incr()))
    }
  }

  def recordSignalStats(
    query: ScoredTweetsQuery
  ): Unit = {
    val userState = query.features.flatMap(_.getOrElse(UserStateFeature, None))
    val userStateStr = userState
      .map {
        case UserState.New => "new"
        case UserState.NearZero => "nearZero"
        case UserState.VeryLight => "veryLight"
        case UserState.Light => "light"
        case UserState.MediumTweeter => "medium"
        case UserState.MediumNonTweeter => "medium"
        case UserState.HeavyNonTweeter => "heavy"
        case UserState.HeavyTweeter => "heavy"
        case UserState.EnumUnknownUserState(_) => "none"
      }.getOrElse("none")

    val followGraphSize = query.features.map(_.getOrElse(SGSFollowedUsersFeature, Seq.empty).size)
    val lowFollow = followGraphSize.exists(_ < SmallFollowGraphSize)
    val lowSignalUser =
      query.features.map(_.getOrElse(LowSignalUserFeature, false)).getOrElse(false)

    val stats = signalStatsReceiver.scope(userStateStr)

    if (lowSignalUser) {
      if (lowFollow) stats.counter("lowSignal-lowFollow").incr()
      else stats.counter("lowSignal-okFollow").incr()
    } else {
      if (lowFollow) stats.counter("okSignal-lowFollow").incr()
      else stats.counter("okSignal-okFollow").incr()
    }
  }

  def recordContentBalanceStats(
    candidates: Seq[CandidateWithDetails],
    scope: String,
    rankingMode: String
  ): Unit = {
    // Split by network type
    val (in, oon) = candidates.partition(_.features.getOrElse(InNetworkFeature, true))
    inNetworkStatsReceiver.counter(scope).incr(in.size)
    outOfNetworkStatsReceiver.counter(scope).incr(oon.size)

    // Track tweet types for all candidates (maintaining backward compatibility)
    val (reply, nonReply) =
      candidates.partition(_.features.getOrElse(InReplyToTweetIdFeature, None).isDefined)
    replyStatsReceiver.counter(scope).incr(reply.size)
    replyStatsReceiver.scope(rankingMode).counter(scope).incr(reply.size)
    originalStatsReceiver.scope(rankingMode).counter(scope).incr(nonReply.size)
    originalStatsReceiver.counter(scope).incr(nonReply.size)

    // Enhanced tracking: retweets, replies, and original posts for both in-network and out-of-network
    val (retweets, nonRetweets) =
      candidates.partition(_.features.getOrElse(IsRetweetFeature, false))
    val (replies, originalPosts) =
      nonRetweets.partition(_.features.getOrElse(InReplyToTweetIdFeature, None).isDefined)

    // Record retweet stats
    retweetStatsReceiver.counter(scope).incr(retweets.size)
    retweetStatsReceiver.scope(rankingMode).counter(scope).incr(retweets.size)

    // Track by network type
    val (inRetweets, oonRetweets) = retweets.partition(_.features.getOrElse(InNetworkFeature, true))
    val (inReplies, oonReplies) = replies.partition(_.features.getOrElse(InNetworkFeature, true))
    val (inOriginalPosts, oonOriginalPosts) =
      originalPosts.partition(_.features.getOrElse(InNetworkFeature, true))

    // In-network tweet type stats
    retweetStatsReceiver.scope("InNetwork").counter(scope).incr(inRetweets.size)
    retweetStatsReceiver.scope("InNetwork").scope(rankingMode).counter(scope).incr(inRetweets.size)
    replyStatsReceiver.scope("InNetwork").counter(scope).incr(inReplies.size)
    replyStatsReceiver.scope("InNetwork").scope(rankingMode).counter(scope).incr(inReplies.size)
    originalStatsReceiver.scope("InNetwork").counter(scope).incr(inOriginalPosts.size)
    originalStatsReceiver
      .scope("InNetwork").scope(rankingMode).counter(scope).incr(inOriginalPosts.size)

    // Out-of-network tweet type stats
    retweetStatsReceiver.scope("OutOfNetwork").counter(scope).incr(oonRetweets.size)
    retweetStatsReceiver
      .scope("OutOfNetwork").scope(rankingMode).counter(scope).incr(oonRetweets.size)
    replyStatsReceiver.scope("OutOfNetwork").counter(scope).incr(oonReplies.size)
    replyStatsReceiver.scope("OutOfNetwork").scope(rankingMode).counter(scope).incr(oonReplies.size)
    originalStatsReceiver.scope("OutOfNetwork").counter(scope).incr(oonOriginalPosts.size)
    originalStatsReceiver
      .scope("OutOfNetwork").scope(rankingMode).counter(scope).incr(oonOriginalPosts.size)
  }

  private def getServedType(candidate: CandidateWithDetails): String =
    candidate.features.get(ServedTypeFeature).name

  private def fromTweetMixer(candidate: CandidateWithDetails): Boolean = {
    candidate.features
      .get(CandidatePipelines)
      .contains(ScoredTweetsTweetMixerCandidatePipelineConfig.Identifier)
  }

  private def fromServedType(
    candidate: CandidateWithDetails,
    servedType: hmt.ServedType
  ): Boolean = candidate.features.get(ServedTypeFeature) == servedType

  private def recordSlopScoreStats(
    candidates: Seq[CandidateWithDetails],
    scope: String
  ): Unit = {
    val count1 = candidates.count(_.features.getOrElse(SlopAuthorScoreFeature, None).contains(1))
    val count2 = candidates.count(_.features.getOrElse(SlopAuthorScoreFeature, None).contains(2))
    val count3 = candidates.count(_.features.getOrElse(SlopAuthorScoreFeature, None).contains(3))
    val noneScore = candidates.count(_.features.getOrElse(SlopAuthorScoreFeature, None).isEmpty)
    val total = candidates.size
    grokSlopScoreStatsReceiver.scope(scope).counter("count1").incr(count1)
    grokSlopScoreStatsReceiver.scope(scope).counter("count2").incr(count2)
    grokSlopScoreStatsReceiver.scope(scope).counter("count3").incr(count3)
    grokSlopScoreStatsReceiver.scope(scope).counter("none").incr(noneScore)
    grokSlopScoreStatsReceiver.scope(scope).counter("total").incr(total)
  }

  def recordInNetworkAuthorStats(
    candidates: Seq[CandidateWithDetails],
    query: ScoredTweetsQuery,
    scope: String
  ): Unit = {
    val inNet = candidates.filter { candidate =>
      candidate.features.getOrElse(InNetworkFeature, true) &&
      (candidate.features.getOrElse(FromInNetworkSourceFeature, false))
    }
    val total = inNet.size
    if (total == 0) return

    val authorCounts: Seq[Int] = inNet
      .flatMap(_.features.getOrElse(AuthorIdFeature, None))
      .groupBy(identity).map { case (k, v) => (k, v.size) }.values.toSeq

    val followGraphSize =
      query.features.map(_.getOrElse(SGSFollowedUsersFeature, Seq.empty).size).getOrElse(0)
    val sr = inNetAuthorStatsReceiver.scope(scope)
    if (followGraphSize > 0) {
      val recall1000xSGS = (authorCounts.size * 1000) / followGraphSize
      sr.stat("Recall1000xSGS").add(recall1000xSGS)
    }

    val sorted = authorCounts.sorted(Ordering[Int].reverse)
    def share(k: Int): Int = (sorted.take(k).sum * 1000) / total

    sr.stat("Top1Share1000x").add(share(1))
    sr.stat("Top3Share1000x").add(share(3))
    sr.stat("Top10Share1000x").add(share(10))
  }

  override val alerts = Seq(HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert())
}

object ScoredStatsSideEffect {
  val TweetMixerRecallTopK = Seq(25, 50, 100, 200, 400)
  val DeepRetrievalRecallTopK = Seq(50, 100, 200)
  val HeavyRankerTopK = Seq(1, 5, 10, 25, 50, 100, 200, 400)
}
