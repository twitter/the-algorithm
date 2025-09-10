package com.twitter.tweet_mixer.functional_component.side_effect

import com.twitter.hydra.root.{thriftscala => t}
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.thriftscala.ClientContext
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.feature.AuthorIdFeature
import com.twitter.tweet_mixer.model.response.TweetMixerResponse
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableHydraScoringSideEffect
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.HydraModelName
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Side effect that calls hydra dark traffic to score tweets.
 */
@Singleton
class HydraScoringSideEffect @Inject() (
  hydraRootService: t.HydraRoot.MethodPerEndpoint,
) extends PipelineResultSideEffect[PipelineQuery, TweetMixerResponse]
    with PipelineResultSideEffect.Conditionally[
      PipelineQuery,
      TweetMixerResponse
    ] {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("HydraScoring")

  override def onlyIf(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: TweetMixerResponse
  ): Boolean = query.params(EnableHydraScoringSideEffect)

  override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery, TweetMixerResponse]
  ): Stitch[Unit] = {
    val query = inputs.query
    val candidates =
      inputs.selectedCandidates ++ inputs.remainingCandidates ++ inputs.droppedCandidates
    val request = hydraRequest(query, candidates)
    Stitch
      .callFuture(hydraRootService.getRecommendationResponse(request))
      .flatMap(_ => Stitch.Unit)
  }

  private def hydraRequest(
    query: PipelineQuery,
    candidates: Seq[CandidateWithDetails]
  ): t.HydraRootRequest = {
    val modelName = query.params(HydraModelName)
    val tweetCandidates = candidates.map { candidate =>
      t.TweetCandidate(
        id = candidate.candidateIdLong,
        authorId = candidate.features.getOrElse(AuthorIdFeature, None).getOrElse(-1)
      )
    }

    t.HydraRootRequest(
      clientContext = ClientContext(userId = query.clientContext.userId),
      product = t.Product.TweetRanking,
      productContext = Some(
        t.ProductContext.TweetRanking(
          t.TweetRanking(
            candidates = tweetCandidates,
            modelName = modelName
          )
        )
      )
    )
  }
}
