package com.twitter.product_mixer.core.model.marshalling.response.urt.metadata

sealed trait FeedbackType

case object Dismiss extends FeedbackType
case object SeeFewer extends FeedbackType
case object DontLike extends FeedbackType
case object NotRelevant extends FeedbackType
case object SeeMore extends FeedbackType
case object NotCredible extends FeedbackType
case object GiveFeedback extends FeedbackType
case object NotRecent extends FeedbackType
case object UnfollowEntity extends FeedbackType
case object Relevant extends FeedbackType
case object Moderate extends FeedbackType
case object RichBehavior extends FeedbackType
case object NotAboutTopic extends FeedbackType
case object Generic extends FeedbackType
