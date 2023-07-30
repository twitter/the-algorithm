package com.X.home_mixer.functional_component.decorator.urt.builder

import com.X.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.X.home_mixer.product.following.model.HomeMixerExternalStrings
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ChildFeedbackAction
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.NotRelevant
import com.X.product_mixer.core.product.guice.scope.ProductScoped
import com.X.stringcenter.client.StringCenter
import com.X.timelines.common.{thriftscala => tlc}
import com.X.timelineservice.model.FeedbackInfo
import com.X.timelineservice.model.FeedbackMetadata
import com.X.timelineservice.{thriftscala => tlst}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class NotRelevantChildFeedbackActionBuilder @Inject() (
  @ProductScoped stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings) {

  def apply(
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[ChildFeedbackAction] = {
    val prompt = stringCenter.prepare(externalStrings.notRelevantString)
    val confirmation = stringCenter.prepare(externalStrings.notRelevantConfirmationString)
    val feedbackMetadata = FeedbackMetadata(
      engagementType = None,
      entityIds = Seq(tlc.FeedbackEntity.TweetId(candidate.id)),
      ttl = Some(FeedbackUtil.FeedbackTtl))
    val feedbackUrl = FeedbackInfo.feedbackUrl(
      feedbackType = tlst.FeedbackType.NotRelevant,
      feedbackMetadata = feedbackMetadata,
      injectionType = candidateFeatures.getOrElse(SuggestTypeFeature, None)
    )

    Some(
      ChildFeedbackAction(
        feedbackType = NotRelevant,
        prompt = Some(prompt),
        confirmation = Some(confirmation),
        feedbackUrl = Some(feedbackUrl),
        hasUndoAction = Some(true),
        confirmationDisplayType = None,
        clientEventInfo = None,
        icon = None,
        richBehavior = None,
        subprompt = None
      )
    )
  }
}
