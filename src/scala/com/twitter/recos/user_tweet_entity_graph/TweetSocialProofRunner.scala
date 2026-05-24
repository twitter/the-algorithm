package com.twitter.recos.user_tweet_entity_graph

import java.util.Random
import com.twitter.concurrent.AsyncQueue
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.graphjet.bipartite.NodeMetadataLeftIndexedMultiSegmentBipartiteGraph
import com.twitter.graphjet.algorithms.RecommendationInfo
import com.twitter.graphjet.algorithms.socialproof.{
  SocialProofResult,
  TweetSocialProofGenerator,
  SocialProofRequest => SocialProofJavaRequest,
  SocialProofResponse => SocialProofJavaResponse
}
import com.twitter.logging.Logger
import com.twitter.recos.model.SalsaQueryRunner.SalsaRunnerConfig
import com.twitter.recos.user_tweet_entity_graph.thriftscala.{
  RecommendationType,
  RecommendationSocialProofRequest => RecommendationSocialProofThriftRequest,
  SocialProofRequest => SocialProofThriftRequest
}
import com.twitter.util.{Future, Try}
import it.unimi.dsi.fastutil.longs.{Long2DoubleMap, Long2DoubleOpenHashMap, LongArraySet}
import scala.collection.JavaConverters._

/**
 * TweetSocialProofRunner creates a queue of reader threads, TweetSocialProofGenerator, and each one
 * reads from the graph and computes social proofs.
 */
class TweetSocialProofRunner(
  bipartiteGraph: NodeMetadataLeftIndexedMultiSegmentBipartiteGraph,
  salsaRunnerConfig: SalsaRunnerConfig,
  statsReceiver: StatsReceiver) {
  private val log: Logger = Logger()
  private val stats = statsReceiver.scope(this.getClass.getSimpleName)
  private val socialProofSizeStat = stats.stat("socialProofSize")

  private val socialProofFailureCounter = stats.counter("failure")
  private val pollCounter = stats.counter("poll")
  private val pollTimeoutCounter = stats.counter("pollTimeout")
  private val offerCounter = stats.counter("offer")
  private val pollLatencyStat = stats.stat("pollLatency")
  private val socialProofRunnerPool = initSocialProofRunnerPool()

  private def initSocialProofRunnerPool(): AsyncQueue[TweetSocialProofGenerator] = {
    val socialProofQueue = new AsyncQueue[TweetSocialProofGenerator]
    (0 until salsaRunnerConfig.numSalsaRunners).foreach { _ =>
      socialProofQueue.offer(new TweetSocialProofGenerator(bipartiteGraph))
    }
    socialProofQueue
  }

  /**
   * Helper method to interpret the output of SocialProofJavaResponse
   *
   * @param socialProofResponse is the response from running TweetSocialProof
   * @return a sequence of SocialProofResult
   */
  private def transformSocialProofResponse(
    socialProofResponse: Option[SocialProofJavaResponse]
  ): Seq[RecommendationInfo] = {
    socialProofResponse match {
      case Some(response) =>
        val scalaResponse = response.getRankedRecommendations.asScala
        scalaResponse.foreach { result =>
          socialProofSizeStat.add(result.asInstanceOf[SocialProofResult].getSocialProofSize)
        }
        scalaResponse.toSeq
      case _ => Nil
    }
  }

  /**
   * Helper method to run social proof computation and convert the results to Option
   *
   * @param socialProof is socialProof reader on bipartite graph
   * @param request is the socialProof request
   * @return is an option of SocialProofJavaResponse
   */
  private def getSocialProofResponse(
    socialProof: TweetSocialProofGenerator,
    request: SocialProofJavaRequest,
    random: Random
  )(
    implicit statsReceiver: StatsReceiver
  ): Option[SocialProofJavaResponse] = {
    val attempt = Try(socialProof.computeRecommendations(request, random)).onFailure { e =>
      socialProofFailureCounter.incr()
      log.error(e, "SocialProof computation failed")
    }
    attempt.toOption
  }

  /**
   * Attempt to retrieve a TweetSocialProof thread from the runner pool
   * to execute a socialProofRequest
   */
  private def handleSocialProofRequest(socialProofRequest: SocialProofJavaRequest) = {
    pollCounter.incr()
    val t0 = System.currentTimeMillis()
    socialProofRunnerPool.poll().map { tweetSocialProof =>
      val pollTime = System.currentTimeMillis - t0
      pollLatencyStat.add(pollTime)
      val socialProofResponse = Try {
        if (pollTime < salsaRunnerConfig.timeoutSalsaRunner) {
          val response = getSocialProofResponse(tweetSocialProof, socialProofRequest, new Random())(
            statsReceiver
          )
          transformSocialProofResponse(response)
        } else {
          // if we did not get a social proof in time, then fail fast here and immediately put it back
          log.warning("socialProof polling timeout")
          pollTimeoutCounter.incr()
          throw new RuntimeException("socialProof poll timeout")
          Nil
        }
      } ensure {
        socialProofRunnerPool.offer(tweetSocialProof)
        offerCounter.incr()
      }
      socialProofResponse.toOption getOrElse Nil
    }
  }

  /**
   * This apply() supports requests coming from the old tweet social proof endpoint.
   * Currently this supports clients such as Email Recommendations, MagicRecs, and HomeTimeline.
   * In order to avoid heavy migration work, we are retaining this endpoint.
   */
  def apply(request: SocialProofThriftRequest): Future[Seq[RecommendationInfo]] = {
    val tweetSet = new LongArraySet(request.inputTweets.toArray)
    val leftSeedNodes: Long2DoubleMap = new Long2DoubleOpenHashMap(
      request.seedsWithWeights.keys.toArray,
      request.seedsWithWeights.values.toArray
    )

    val socialProofRequest = new SocialProofJavaRequest(
      tweetSet,
      leftSeedNodes,
      UserTweetEdgeTypeMask.getUserTweetGraphSocialProofTypes(request.socialProofTypes)
    )

    handleSocialProofRequest(socialProofRequest)
  }

  /**
   * This apply() supports requests coming from the new social proof endpoint in UTEG that works for
   * tweet social proof generation, as well as hashtag and url social proof generation.
   * Currently this endpoint supports url social proof generation for Guide.
   */
  def apply(request: RecommendationSocialProofThriftRequest): Future[Seq[RecommendationInfo]] = {
    val tweetIds = request.recommendationIdsForSocialProof.collect {
      case (RecommendationType.Tweet, ids) => ids
    }.flatten
    val tweetSet = new LongArraySet(tweetIds.toArray)
    val leftSeedNodes: Long2DoubleMap = new Long2DoubleOpenHashMap(
      request.seedsWithWeights.keys.toArray,
      request.seedsWithWeights.values.toArray
    )

    val socialProofRequest = new SocialProofJavaRequest(
      tweetSet,
      leftSeedNodes,
      UserTweetEdgeTypeMask.getUserTweetGraphSocialProofTypes(request.socialProofTypes)
    )

    handleSocialProofRequest(socialProofRequest)
  }
}
