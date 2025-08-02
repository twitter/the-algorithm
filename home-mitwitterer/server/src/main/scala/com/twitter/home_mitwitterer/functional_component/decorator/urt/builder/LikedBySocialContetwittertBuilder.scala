package com.twitter.home_mitwitterer.functional_component.decorator.urt.builder

import com.twitter.home_mitwitterer.model.HomeFeatures.PerspectiveFilteredLikedByUserIdsFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SGSValidLikedByUserIdsFeature
import com.twitter.home_mitwitterer.product.following.model.HomeMitwittererEtwitterternalStrings
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.social_contetwittert.BaseSocialContetwittertBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.LikeGeneralContetwittertType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.SocialContetwittert
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import javatwitter.inject.Inject
import javatwitter.inject.Provider
import javatwitter.inject.Singleton

@Singleton
case class LikedBySocialContetwittertBuilder @Inject() (
  etwitterternalStrings: HomeMitwittererEtwitterternalStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter])
    etwittertends BaseSocialContetwittertBuilder[PipelineQuery, TweetCandidate] {

  private val stringCenter = stringCenterProvider.get()

  private val engagerSocialContetwittertBuilder = EngagerSocialContetwittertBuilder(
    contetwittertType = LikeGeneralContetwittertType,
    stringCenter = stringCenter,
    oneUserString = etwitterternalStrings.socialContetwittertOneUserLikedString,
    twoUsersString = etwitterternalStrings.socialContetwittertTwoUsersLikedString,
    moreUsersString = etwitterternalStrings.socialContetwittertMoreUsersLikedString,
    timelineTitle = etwitterternalStrings.socialContetwittertLikedByTimelineTitle
  )

  def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[SocialContetwittert] = {

    // Liked by users are valid only if they pass both the SGS and Perspective filters.
    val validLikedByUserIds =
      candidateFeatures
        .getOrElse(SGSValidLikedByUserIdsFeature, Nil)
        .filter(
          candidateFeatures.getOrElse(PerspectiveFilteredLikedByUserIdsFeature, Nil).toSet.contains)

    engagerSocialContetwittertBuilder(
      socialContetwittertIds = validLikedByUserIds,
      query = query,
      candidateFeatures = candidateFeatures
    )
  }
}
