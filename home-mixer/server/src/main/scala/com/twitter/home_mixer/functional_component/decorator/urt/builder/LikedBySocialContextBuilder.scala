package com.twitter.home_mixer.functional_component.decorator.urt.builder

import com.twitter.home_mixer.model.HomeFeatures.ValidLikedByUserIdsFeature
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.social_context.BaseSocialContextBuilder
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.LikeGeneralContextType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.SocialContext
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
case class LikedBySocialContextBuilder @Inject() (
  externalStrings: HomeMixerExternalStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter])
    extends BaseSocialContextBuilder[PipelineQuery, TweetCandidate] {

  private val stringCenter = stringCenterProvider.get()

  private val engagerSocialContextBuilder = EngagerSocialContextBuilder(
    contextType = LikeGeneralContextType,
    stringCenter = stringCenter,
    oneUserString = externalStrings.socialContextOneUserLikedString,
    twoUsersString = externalStrings.socialContextTwoUsersLikedString,
    moreUsersString = externalStrings.socialContextMoreUsersLikedString,
    timelineTitle = externalStrings.socialContextLikedByTimelineTitle
  )

  def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[SocialContext] = {

    // Liked by users are valid only if they pass both the SGS and Perspective filters.
    val validLikedByUserIds = candidateFeatures.getOrElse(ValidLikedByUserIdsFeature, Nil)

    engagerSocialContextBuilder(
      socialContextIds = validLikedByUserIds,
      query = query,
      candidateFeatures = candidateFeatures
    )
  }
}
