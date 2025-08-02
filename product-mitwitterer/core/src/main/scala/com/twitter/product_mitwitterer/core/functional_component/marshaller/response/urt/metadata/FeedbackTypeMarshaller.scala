package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata._
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class FeedbackTypeMarshaller @Inject() () {

  def apply(feedbackType: FeedbackType): urt.FeedbackType = feedbackType match {
    case Dismiss => urt.FeedbackType.Dismiss
    case SeeFewer => urt.FeedbackType.SeeFewer
    case DontLike => urt.FeedbackType.DontLike
    case NotRelevant => urt.FeedbackType.NotRelevant
    case SeeMore => urt.FeedbackType.SeeMore
    case NotCredible => urt.FeedbackType.NotCredible
    case GiveFeedback => urt.FeedbackType.GiveFeedback
    case NotRecent => urt.FeedbackType.NotRecent
    case UnfollowEntity => urt.FeedbackType.UnfollowEntity
    case Relevant => urt.FeedbackType.Relevant
    case Moderate => urt.FeedbackType.Moderate
    case RichBehavior => urt.FeedbackType.RichBehavior
    case NotAboutTopic => urt.FeedbackType.NotAboutTopic
    case Generic => urt.FeedbackType.Generic
  }
}
