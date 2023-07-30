package com.X.cr_mixer.similarity_engine

import com.X.cr_mixer.model.SimilarityEngineInfo
import com.X.cr_mixer.model.TweetWithScore
import com.X.cr_mixer.thriftscala.SimilarityEngineType
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.Stats
import com.X.product_mixer.core.thriftscala.ClientContext
import com.X.qig_ranker.thriftscala.Product
import com.X.qig_ranker.thriftscala.ProductContext
import com.X.qig_ranker.thriftscala.QigRanker
import com.X.qig_ranker.thriftscala.QigRankerProductResponse
import com.X.qig_ranker.thriftscala.QigRankerRequest
import com.X.qig_ranker.thriftscala.QigRankerResponse
import com.X.qig_ranker.thriftscala.TwistlySimilarTweetsProductContext
import com.X.simclusters_v2.thriftscala.InternalId
import com.X.storehaus.ReadableStore
import com.X.timelines.configapi
import com.X.util.Future
import javax.inject.Singleton

/**
 * This store looks for similar tweets from QueryInteractionGraph (QIG) for a source tweet id.
 * For a given query tweet, QIG returns us the similar tweets that have an overlap of engagements
 * (with the query tweet) on different search queries
 */
@Singleton
case class TweetBasedQigSimilarityEngine(
  qigRanker: QigRanker.MethodPerEndpoint,
  statsReceiver: StatsReceiver)
    extends ReadableStore[
      TweetBasedQigSimilarityEngine.Query,
      Seq[TweetWithScore]
    ] {

  private val stats = statsReceiver.scope(this.getClass.getSimpleName)
  private val fetchCandidatesStat = stats.scope("fetchCandidates")

  override def get(
    query: TweetBasedQigSimilarityEngine.Query
  ): Future[Option[Seq[TweetWithScore]]] = {
    query.sourceId match {
      case InternalId.TweetId(tweetId) =>
        val qigSimilarTweetsRequest = getQigSimilarTweetsRequest(tweetId)

        Stats.trackOption(fetchCandidatesStat) {
          qigRanker
            .getSimilarCandidates(qigSimilarTweetsRequest)
            .map { qigSimilarTweetsResponse =>
              getCandidatesFromQigResponse(qigSimilarTweetsResponse)
            }
        }
      case _ =>
        Future.value(None)
    }
  }

  private def getQigSimilarTweetsRequest(
    tweetId: Long
  ): QigRankerRequest = {
    // Note: QigRanker needs a non-empty userId to be passed to return results.
    // We are passing in a dummy userId until we fix this on QigRanker side
    val clientContext = ClientContext(userId = Some(0L))
    val productContext = ProductContext.TwistlySimilarTweetsProductContext(
      TwistlySimilarTweetsProductContext(tweetId = tweetId))

    QigRankerRequest(
      clientContext = clientContext,
      product = Product.TwistlySimilarTweets,
      productContext = Some(productContext),
    )
  }

  private def getCandidatesFromQigResponse(
    qigSimilarTweetsResponse: QigRankerResponse
  ): Option[Seq[TweetWithScore]] = {
    qigSimilarTweetsResponse.productResponse match {
      case QigRankerProductResponse
            .TwistlySimilarTweetCandidatesResponse(response) =>
        val tweetsWithScore = response.similarTweets
          .map { similarTweetResult =>
            TweetWithScore(
              similarTweetResult.tweetResult.tweetId,
              similarTweetResult.tweetResult.score.getOrElse(0L))
          }
        Some(tweetsWithScore)

      case _ => None
    }
  }
}

object TweetBasedQigSimilarityEngine {

  def toSimilarityEngineInfo(score: Double): SimilarityEngineInfo = {
    SimilarityEngineInfo(
      similarityEngineType = SimilarityEngineType.Qig,
      modelId = None,
      score = Some(score))
  }

  case class Query(sourceId: InternalId)

  def fromParams(
    sourceId: InternalId,
    params: configapi.Params,
  ): EngineQuery[Query] = {
    EngineQuery(
      Query(sourceId = sourceId),
      params
    )
  }

}
