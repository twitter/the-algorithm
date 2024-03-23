package com.ExTwitter.home_mixer.functional_component.decorator.urt.builder

import com.ExTwitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.PerspectiveFilteredLikedByUserIdsFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.SGSValidFollowedByUserIdsFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.SGSValidLikedByUserIdsFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.TopicContextFunctionalityTypeFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.TopicIdSocialContextFeature
import com.ExTwitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackAction
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.RecWithEducationTopicContextFunctionalityType
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.RecommendationTopicContextFunctionalityType
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.RichBehavior
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.RichFeedbackBehaviorMarkNotInterestedTopic
import com.ExTwitter.product_mixer.core.product.guice.scope.ProductScoped
import com.ExTwitter.stringcenter.client.StringCenter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class NotInterestedTopicFeedbackActionBuilder @Inject() (
  @ProductScoped stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings) {

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
      val topicIdSocialContext = candidateFeatures.getOrElse(TopicIdSocialContextFeature, None)
      val topicContextFunctionalityType =
        candidateFeatures.getOrElse(TopicContextFunctionalityTypeFeature, None)

      (topicIdSocialContext, topicContextFunctionalityType) match {
        case (Some(topicId), Some(topicContextFunctionalityType))
            if topicContextFunctionalityType == RecommendationTopicContextFunctionalityType ||
              topicContextFunctionalityType == RecWithEducationTopicContextFunctionalityType =>
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
