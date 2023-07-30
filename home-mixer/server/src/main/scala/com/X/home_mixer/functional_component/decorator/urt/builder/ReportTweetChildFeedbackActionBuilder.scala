package com.X.home_mixer.functional_component.decorator.urt.builder

import com.X.home_mixer.product.following.model.HomeMixerExternalStrings
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.model.marshalling.response.urt.icon
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ChildFeedbackAction
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.RichBehavior
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.RichFeedbackBehaviorReportTweet
import com.X.product_mixer.core.product.guice.scope.ProductScoped
import com.X.stringcenter.client.StringCenter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class ReportTweetChildFeedbackActionBuilder @Inject() (
  @ProductScoped stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings) {

  def apply(
    candidate: TweetCandidate
  ): Option[ChildFeedbackAction] = {
    Some(
      ChildFeedbackAction(
        feedbackType = RichBehavior,
        prompt = Some(stringCenter.prepare(externalStrings.reportTweetString)),
        confirmation = None,
        feedbackUrl = None,
        hasUndoAction = Some(true),
        confirmationDisplayType = None,
        clientEventInfo = None,
        icon = Some(icon.Flag),
        richBehavior = Some(RichFeedbackBehaviorReportTweet(candidate.id)),
        subprompt = None
      )
    )
  }
}
