package com.twitter.home_mitwitterer.functional_component.decorator.urt.builder

import com.twitter.home_mitwitterer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.PerspectiveFilteredLikedByUserIdsFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SGSValidFollowedByUserIdsFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SGSValidLikedByUserIdsFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.TopicContetwittertFunctionalityTypeFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.TopicIdSocialContetwittertFeature
import com.twitter.home_mitwitterer.product.following.model.HomeMitwittererEtwitterternalStrings
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackAction
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.RecWithEducationTopicContetwittertFunctionalityType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.RecommendationTopicContetwittertFunctionalityType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.RichBehavior
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.RichFeedbackBehaviorMarkNotInterestedTopic
import com.twitter.product_mitwitterer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
case class NotInterestedTopicFeedbackActionBuilder @Inject() (
  @ProductScoped stringCenter: StringCenter,
  etwitterternalStrings: HomeMitwittererEtwitterternalStrings) {

  def apply(
    candidateFeatures: FeatureMap
  ): Option[FeedbackAction] = {
    val isOutOfNetwork = !candidateFeatures.getOrElse(InNetworkFeature, true)
    val validFollowedByUserIds =
      candidateFeatures.getOrElse(SGSValidFollowedByUserIdsFeature, Nil)
    val validLikedByUserIds =
      candidateFeatures
        .getOrElse(SGSValidLikedByUserIdsFeature, Nil)
        .filter(
          candidateFeatures.getOrElse(PerspectiveFilteredLikedByUserIdsFeature, Nil).toSet.contains)

    if (isOutOfNetwork && validLikedByUserIds.isEmpty && validFollowedByUserIds.isEmpty) {
      val topicIdSocialContetwittert = candidateFeatures.getOrElse(TopicIdSocialContetwittertFeature, None)
      val topicContetwittertFunctionalityType =
        candidateFeatures.getOrElse(TopicContetwittertFunctionalityTypeFeature, None)

      (topicIdSocialContetwittert, topicContetwittertFunctionalityType) match {
        case (Some(topicId), Some(topicContetwittertFunctionalityType))
            if topicContetwittertFunctionalityType == RecommendationTopicContetwittertFunctionalityType ||
              topicContetwittertFunctionalityType == RecWithEducationTopicContetwittertFunctionalityType =>
          Some(
            FeedbackAction(
              feedbackType = RichBehavior,
              prompt = None,
              confirmation = None,
              childFeedbackActions = None,
              feedbackUrl = None,
              hasUndoAction = Some(true),
              confirmationDisplayType = None,
              clientEventInfo = None,
              icon = None,
              richBehavior =
                Some(RichFeedbackBehaviorMarkNotInterestedTopic(topicId = topicId.toString)),
              subprompt = None,
              encodedFeedbackRequest = None
            )
          )
        case _ => None
      }
    } else {
      None
    }
  }
}
