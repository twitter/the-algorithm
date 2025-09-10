package com.twitter.home_mixer.util

import com.twitter.finagle.grpc.FutureConverters
import com.twitter.finagle.service.RetryPolicy
import com.twitter.finagle.stats.Stat
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.QuotedTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.UserActionsFeature
import com.twitter.inject.utils.RetryUtils
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.servo.util.MemoizingStatsReceiver
import com.twitter.stitch.Stitch
import com.twitter.util.Future
import com.twitter.util.Return
import com.twitter.util.Throw
import com.twitter.util.Try
import com.x.user_action_sequence.ActionName
import com.x.user_action_sequence.CandidateSet
import com.x.user_action_sequence.PredictNextActionsRequest
import com.x.user_action_sequence.PredictNextActionsResponse
import com.x.user_action_sequence.RecsysPredictorGrpc
import com.x.user_action_sequence.TweetBoolFeatures
import com.x.user_action_sequence.TweetInfo
import io.grpc.ManagedChannel
import java.util.concurrent.TimeUnit
import scala.collection.JavaConverters._
import scala.util.Random

object PhoenixUtils {
  private val TopLogProbsNum = 50
  private val MaxCandidates = 1400

  def getTweetInfoFromCandidates(
    candidateIds: Seq[TweetCandidate],
    featureMaps: Seq[FeatureMap]
  ): Seq[TweetInfo] = {
    assert(
      candidateIds.length == featureMaps.length,
      "FeatureMap doesn't match candidateIds in length")
    val candidateWithFeatureMaps = candidateIds.zip(featureMaps)
    candidateWithFeatureMaps.take(MaxCandidates).map {
      case (candidate, featureMap) =>
        val tweetBooleanFeatureBuilder = TweetBoolFeatures.newBuilder()
        val (sourceTweetId, sourceAuthorId) = (
          featureMap.getOrElse(SourceTweetIdFeature, None),
          featureMap.getOrElse(SourceUserIdFeature, None)) match {
          case (Some(sourceTweetId), Some(sourceAuthorId)) =>
            tweetBooleanFeatureBuilder.setIsRetweet(true)
            (sourceTweetId, sourceAuthorId)
          case _ =>
            tweetBooleanFeatureBuilder.setIsRetweet(false)
            (candidate.id, featureMap.get(AuthorIdFeature).get)
        }
        tweetBooleanFeatureBuilder
          .setIsForYouPage(true)
          .setIsPromotedTweet(false)
          .setIsReply(featureMap.getOrElse(InReplyToTweetIdFeature, None).isDefined)
          .setIsQuote(featureMap.getOrElse(QuotedTweetIdFeature, None).isDefined)
        TweetInfo
          .newBuilder()
          .setTweetId(sourceTweetId)
          .setAuthorId(sourceAuthorId)
          .setTweetBoolFeatures(tweetBooleanFeatureBuilder.build())
          .build()
    }
  }

  def createCandidateSets(
    query: PipelineQuery,
    tweetInfos: Seq[TweetInfo]
  ): PredictNextActionsRequest = {
    val actionsSeqOpt = query.features.flatMap(_.getOrElse(UserActionsFeature, None))
    val candidateSets = CandidateSet
      .newBuilder()
      .setUserId(query.getRequiredUserId)
      .addAllCandidates(tweetInfos.asJava)
      .build()

    val predictionRequestBuilder = PredictNextActionsRequest
      .newBuilder()
      .addCandidateSets(candidateSets)
      .setReturnLogprob(true)
      .setTopLogprobsNum(TopLogProbsNum)

    actionsSeqOpt.foreach { actionsSeq => predictionRequestBuilder.addSequences(actionsSeq) }
    predictionRequestBuilder.build()
  }

  def predict(
    request: PredictNextActionsRequest,
    channels: Seq[ManagedChannel],
    cluster: String,
    timeoutMs: Int,
    baseStat: MemoizingStatsReceiver,
  ): Stitch[PredictNextActionsResponse] = {

    val timeStat = baseStat.scope("rpcTime")
    val successStat = baseStat.scope("rpcSuccess")
    val failureStat = baseStat.scope("rpcFailure")
    val failureMsgStat = baseStat.scope("rpcFailureMsg")

    def attemptPredict(): Future[PredictNextActionsResponse] = {
      // This is kept inside predict so that it doesn't use the same channel for retries
      val channel = channels(Random.nextInt(channels.length))
      val recsysPredictorFutureStub =
        RecsysPredictorGrpc
          .newFutureStub(channel).withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS)

      FutureConverters
        .RichListenableFuture(recsysPredictorFutureStub.predictNextActions(request))
        .toTwitter
    }

    // Retry configuration: 2 attempts, 500ms per attempt, total 1s max
    val retryPolicy = RetryPolicy
      .tries[Try[PredictNextActionsResponse]](2, { case Throw(_) => true; case Return(_) => false })

    val attemptPredictRetriedFuture =
      RetryUtils.retryFuture[PredictNextActionsResponse](retryPolicy)(attemptPredict)

    Stitch
      .callFuture(Stat.timeFuture(timeStat.stat(cluster))(attemptPredictRetriedFuture))
      .onFailure { e =>
        val errMsg = e.getMessage.replaceAll("\\W", "").takeRight(100)
        failureStat.counter(cluster).incr()
        failureMsgStat.scope(cluster).counter(errMsg).incr()
      }.onSuccess { _ =>
        successStat.counter(cluster).incr()
      }
  }

  def getPredictionResponseMap(
    request: PredictNextActionsRequest,
    channels: Seq[ManagedChannel],
    cluster: String,
    timeoutMs: Int,
    memoizingStatsReceiver: MemoizingStatsReceiver
  ): Stitch[Map[Long, Map[ActionName, Double]]] = {
    val predictionsResponse = predict(request, channels, cluster, timeoutMs, memoizingStatsReceiver)
    predictionsResponse.map { predictions =>
      val distributionSetsList = predictions.getDistributionSetsList
      val candidateDistributions = if (!distributionSetsList.isEmpty) {
        distributionSetsList.get(0).getCandidateDistributionsList.asScala
      } else Seq.empty

      candidateDistributions.map { distribution =>
        val candidateId = distribution.getCandidate.getTweetId
        val probMap = distribution.getTopLogProbsList.asScala.zipWithIndex.map {
          case (logProb, idx) =>
            ActionName.forNumber(idx) -> math.exp(logProb.doubleValue())
        }.toMap
        candidateId -> probMap
      }.toMap
    }
  }
}
