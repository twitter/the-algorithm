package com.twitter.home_mixer.product.for_you.candidate_source

import com.google.inject.Provider
import com.twitter.home_mixer.model.request.HomeMixerRequest
import com.twitter.home_mixer.model.request.ScoredVideoTweetsProduct
import com.twitter.home_mixer.model.request.ScoredVideoTweetsProductContext
import com.twitter.home_mixer.product.for_you.model.ForYouQuery
import com.twitter.home_mixer.{thriftscala => t}
import com.twitter.product_mixer.core.functional_component.candidate_source.product_pipeline.ProductPipelineCandidateSource
import com.twitter.product_mixer.core.functional_component.configapi.ParamsBuilder
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.product.registry.ProductPipelineRegistry
import com.twitter.snowflake.id.SnowflakeId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoredVideoTweetsCategorizedProductCandidateSource @Inject() (
  override val productPipelineRegistry: Provider[ProductPipelineRegistry],
  override val paramsBuilder: Provider[ParamsBuilder])
    extends ProductPipelineCandidateSource[
      ForYouQuery,
      HomeMixerRequest,
      t.ScoredTweetsResponse,
      ScoredVideoTweetCandidate
    ] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("ScoredVideoTweetsCategorizedProduct")

  private val MAX_RESULTS = 20

  override def fsCustomMapInput(query: ForYouQuery): Map[String, Int] = {
    val userAgeOpt = query.clientContext.userId.map { userId =>
      SnowflakeId.timeFromIdOpt(userId).map(_.untilNow.inDays).getOrElse(Int.MaxValue)
    }
    userAgeOpt.map("account_age_in_days" -> _).toMap
  }

  override def pipelineRequestTransformer(productPipelineQuery: ForYouQuery): HomeMixerRequest = {
    HomeMixerRequest(
      clientContext = productPipelineQuery.clientContext,
      product = ScoredVideoTweetsProduct,
      productContext = Some(
        ScoredVideoTweetsProductContext(
          productPipelineQuery.deviceContext,
          productPipelineQuery.seenTweetIds,
          Some(t.VideoType.Categorized),
          None,
          None,
          None
        )),
      serializedRequestCursor = None,
      maxResults = Some(MAX_RESULTS),
      debugParams = None,
      homeRequestParam = false
    )
  }

  override def productPipelineResultTransformer(
    productPipelineResult: t.ScoredTweetsResponse
  ): Seq[ScoredVideoTweetCandidate] = {
    productPipelineResult.scoredTweets.map { scoredTweet =>
      ScoredVideoTweetCandidate(
        scoredTweet.tweetId,
        scoredTweet.authorId,
        scoredTweet.score,
        scoredTweet.servedType,
        scoredTweet.aspectRatio
      )
    }
  }
}
