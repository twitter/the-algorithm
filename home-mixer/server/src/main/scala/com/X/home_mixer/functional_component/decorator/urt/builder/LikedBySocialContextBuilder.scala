package com.X.home_mixer.functional_component.decorator.urt.builder

import com.X.home_mixer.model.HomeFeatures.PerspectiveFilteredLikedByUserIdsFeature
import com.X.home_mixer.model.HomeFeatures.SGSValidLikedByUserIdsFeature
import com.X.home_mixer.product.following.model.HomeMixerExternalStrings
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.functional_component.decorator.urt.builder.social_context.BaseSocialContextBuilder
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.LikeGeneralContextType
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.SocialContext
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.product.guice.scope.ProductScoped
import com.X.stringcenter.client.StringCenter
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
    val validLikedByUserIds =
      candidateFeatures
        .getOrElse(SGSValidLikedByUserIdsFeature, Nil)
        .filter(
          candidateFeatures.getOrElse(PerspectiveFilteredLikedByUserIdsFeature, Nil).toSet.contains)

    engagerSocialContextBuilder(
      socialContextIds = validLikedByUserIds,
      query = query,
      candidateFeatures = candidateFeatures
    )
  }
}
