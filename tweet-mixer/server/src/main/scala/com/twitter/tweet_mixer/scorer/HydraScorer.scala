package com.twitter.tweet_mixer.scorer

import com.twitter.hydra.root.{thriftscala => t}
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.scorer.Scorer
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.ScorerIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.thriftscala.ClientContext
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.feature.AuthorIdFeature
import com.twitter.tweet_mixer.feature.HydraScoreFeature
import com.twitter.tweet_mixer.feature.SourceTweetIdFeature
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.HydraModelName
import com.twitter.util.Future
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HydraScorer @Inject() (
  hydraRootService: t.HydraRoot.MethodPerEndpoint)
    extends Scorer[PipelineQuery, TweetCandidate] {

  override val identifier: ScorerIdentifier = ScorerIdentifier("Hydra")

  override val features: Set[Feature[_, _]] = Set(HydraScoreFeature)

  private val BatchSize = 1500

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]],
  ): Stitch[Seq[FeatureMap]] = {
    val requests = hydraRequest(query, candidates)
    val tweetResultsStitch: Stitch[Seq[t.TweetResult]] =
      Stitch
        .traverse(requests) { request =>
          Stitch.callFuture(getTweetResults(request))
        }.map(_.flatten)
    tweetResultsStitch
      .map { tweetResults =>
        val tweetResponseMap = tweetResults
          .map(scoredTweet => (scoredTweet.tweetId, scoredTweet.scoreMap.toMap)).toMap
        candidates.map { candidate =>
          val id = candidate.features
            .getOrElse(SourceTweetIdFeature, None)
            .getOrElse(candidate.candidate.id)
          FeatureMap(HydraScoreFeature, tweetResponseMap.getOrElse(id, Map.empty[String, Double]))
        }
      }
  }

  private def getTweetResults(hydraRequest: t.HydraRootRequest): Future[Seq[t.TweetResult]] = {
    hydraRootService
      .getRecommendationResponse(hydraRequest)
      .map {
        case t.HydraRootRecommendationResponse
              .HydraRootTweetRankingResponse(t.HydraRootTweetRankingResponse(tweetResponse)) =>
          tweetResponse
      }.handle {
        case _ => Seq.empty
      }
  }

  private def hydraRequest(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]],
    batchSize: Int = BatchSize
  ): Seq[t.HydraRootRequest] = {
    val tweetCandidatesIter = candidates
      .map { candidate =>
        t.TweetCandidate(
          id = candidate.features
            .getOrElse(SourceTweetIdFeature, None)
            .getOrElse(candidate.candidate.id),
          authorId = candidate.features.getOrElse(AuthorIdFeature, None).getOrElse(-1)
        )
      }.distinct.grouped(batchSize).toSeq

    tweetCandidatesIter.map { tweetCandidates =>
      t.HydraRootRequest(
        clientContext = ClientContext(userId = query.clientContext.userId),
        product = t.Product.TweetRanking,
        productContext = Some(
          t.ProductContext.TweetRanking(
            t.TweetRanking(
              candidates = tweetCandidates,
              modelName = query.params(HydraModelName)
            ))),
      )
    }
  }
}
