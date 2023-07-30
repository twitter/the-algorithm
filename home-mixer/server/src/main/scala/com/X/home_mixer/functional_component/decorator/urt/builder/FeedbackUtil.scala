package com.X.home_mixer.functional_component.decorator.urt.builder

import com.X.conversions.DurationOps._
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ChildFeedbackAction
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.SeeFewer
import com.X.stringcenter.client.StringCenter
import com.X.stringcenter.client.core.ExternalString
import com.X.timelines.common.{thriftscala => tlc}
import com.X.timelines.service.{thriftscala => t}
import com.X.timelineservice.model.FeedbackInfo
import com.X.timelineservice.model.FeedbackMetadata
import com.X.timelineservice.suggests.{thriftscala => st}
import com.X.timelineservice.{thriftscala => tlst}

object FeedbackUtil {

  val FeedbackTtl = 30.days

  def buildUserSeeFewerChildFeedbackAction(
    userId: Long,
    namesByUserId: Map[Long, String],
    promptExternalString: ExternalString,
    confirmationExternalString: ExternalString,
    engagementType: t.FeedbackEngagementType,
    stringCenter: StringCenter,
    injectionType: Option[st.SuggestType]
  ): Option[ChildFeedbackAction] = {
    namesByUserId.get(userId).map { userScreenName =>
      val prompt = stringCenter.prepare(
        promptExternalString,
        Map("user" -> userScreenName)
      )
      val confirmation = stringCenter.prepare(
        confirmationExternalString,
        Map("user" -> userScreenName)
      )
      val feedbackMetadata = FeedbackMetadata(
        engagementType = Some(engagementType),
        entityIds = Seq(tlc.FeedbackEntity.UserId(userId)),
        ttl = Some(FeedbackTtl))
      val feedbackUrl = FeedbackInfo.feedbackUrl(
        feedbackType = tlst.FeedbackType.SeeFewer,
        feedbackMetadata = feedbackMetadata,
        injectionType = injectionType
      )

      ChildFeedbackAction(
        feedbackType = SeeFewer,
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
    }
  }
}
