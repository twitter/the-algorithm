package com.X.home_mixer.functional_component.decorator.urt.builder

import com.X.home_mixer.model.HomeFeatures.InNetworkFeature
import com.X.home_mixer.model.HomeFeatures.SGSValidFollowedByUserIdsFeature
import com.X.home_mixer.product.following.model.HomeMixerExternalStrings
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.functional_component.decorator.urt.builder.social_context.BaseSocialContextBuilder
import com.X.product_mixer.core.model.marshalling.response.urt.metadata._
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.product.guice.scope.ProductScoped
import com.X.stringcenter.client.StringCenter
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
case class FollowedBySocialContextBuilder @Inject() (
  externalStrings: HomeMixerExternalStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter])
    extends BaseSocialContextBuilder[PipelineQuery, TweetCandidate] {

  private val stringCenter = stringCenterProvider.get()

  private val engagerSocialContextBuilder = EngagerSocialContextBuilder(
    contextType = FollowGeneralContextType,
    stringCenter = stringCenter,
    oneUserString = externalStrings.socialContextOneUserFollowsString,
    twoUsersString = externalStrings.socialContextTwoUsersFollowString,
    moreUsersString = externalStrings.socialContextMoreUsersFollowString,
    timelineTitle = externalStrings.socialContextFollowedByTimelineTitle
  )

  def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[SocialContext] = {
    // Only apply followed-by social context for OON Tweets
    val inNetwork = candidateFeatures.getOrElse(InNetworkFeature, true)
    if (!inNetwork) {
      val validFollowedByUserIds =
        candidateFeatures.getOrElse(SGSValidFollowedByUserIdsFeature, Nil)
      engagerSocialContextBuilder(
        socialContextIds = validFollowedByUserIds,
        query = query,
        candidateFeatures = candidateFeatures
      )
    } else {
      None
    }
  }
}
