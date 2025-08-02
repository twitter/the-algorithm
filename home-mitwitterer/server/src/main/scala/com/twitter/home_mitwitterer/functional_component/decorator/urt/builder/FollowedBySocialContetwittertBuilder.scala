package com.twitter.home_mitwitterer.functional_component.decorator.urt.builder

import com.twitter.home_mitwitterer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SGSValidFollowedByUserIdsFeature
import com.twitter.home_mitwitterer.product.following.model.HomeMitwittererEtwitterternalStrings
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.social_contetwittert.BaseSocialContetwittertBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata._
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import javatwitter.inject.Inject
import javatwitter.inject.Provider
import javatwitter.inject.Singleton

@Singleton
case class FollowedBySocialContetwittertBuilder @Inject() (
  etwitterternalStrings: HomeMitwittererEtwitterternalStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter])
    etwittertends BaseSocialContetwittertBuilder[PipelineQuery, TweetCandidate] {

  private val stringCenter = stringCenterProvider.get()

  private val engagerSocialContetwittertBuilder = EngagerSocialContetwittertBuilder(
    contetwittertType = FollowGeneralContetwittertType,
    stringCenter = stringCenter,
    oneUserString = etwitterternalStrings.socialContetwittertOneUserFollowsString,
    twoUsersString = etwitterternalStrings.socialContetwittertTwoUsersFollowString,
    moreUsersString = etwitterternalStrings.socialContetwittertMoreUsersFollowString,
    timelineTitle = etwitterternalStrings.socialContetwittertFollowedByTimelineTitle
  )

  def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[SocialContetwittert] = {
    // Only apply followed-by social contetwittert for OON Tweets
    val inNetwork = candidateFeatures.getOrElse(InNetworkFeature, true)
    if (!inNetwork) {
      val validFollowedByUserIds =
        candidateFeatures.getOrElse(SGSValidFollowedByUserIdsFeature, Nil)
      engagerSocialContetwittertBuilder(
        socialContetwittertIds = validFollowedByUserIds,
        query = query,
        candidateFeatures = candidateFeatures
      )
    } else {
      None
    }
  }
}
