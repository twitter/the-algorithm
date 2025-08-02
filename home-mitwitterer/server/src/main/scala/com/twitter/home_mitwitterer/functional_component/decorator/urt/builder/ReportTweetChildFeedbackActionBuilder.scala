package com.twitter.home_mitwitterer.functional_component.decorator.urt.builder

import com.twitter.home_mitwitterer.product.following.model.HomeMitwittererEtwitterternalStrings
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.icon
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ChildFeedbackAction
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.RichBehavior
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.RichFeedbackBehaviorReportTweet
import com.twitter.product_mitwitterer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
case class ReportTweetChildFeedbackActionBuilder @Inject() (
  @ProductScoped stringCenter: StringCenter,
  etwitterternalStrings: HomeMitwittererEtwitterternalStrings) {

  def apply(
    candidate: TweetCandidate
  ): Option[ChildFeedbackAction] = {
    Some(
      ChildFeedbackAction(
        feedbackType = RichBehavior,
        prompt = Some(stringCenter.prepare(etwitterternalStrings.reportTweetString)),
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
