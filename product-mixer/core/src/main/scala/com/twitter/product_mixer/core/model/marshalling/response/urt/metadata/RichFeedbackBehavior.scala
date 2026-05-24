package com.twitter.product_mixer.core.model.marshalling.response.urt.metadata

sealed trait RichFeedbackBehavior

case class RichFeedbackBehaviorReportList(listId: Long, userId: Long) extends RichFeedbackBehavior
case class RichFeedbackBehaviorBlockUser(userId: Long) extends RichFeedbackBehavior
case class RichFeedbackBehaviorToggleFollowTopic(topicId: Long) extends RichFeedbackBehavior
case class RichFeedbackBehaviorToggleFollowTopicV2(topicId: String) extends RichFeedbackBehavior
case class RichFeedbackBehaviorToggleMuteList(listId: Long) extends RichFeedbackBehavior
case class RichFeedbackBehaviorMarkNotInterestedTopic(topicId: String) extends RichFeedbackBehavior
case class RichFeedbackBehaviorReplyPinState(replyPinState: ReplyPinState)
    extends RichFeedbackBehavior
case class RichFeedbackBehaviorToggleMuteUser(userId: Long) extends RichFeedbackBehavior
case class RichFeedbackBehaviorToggleFollowUser(userId: Long) extends RichFeedbackBehavior
case class RichFeedbackBehaviorReportTweet(entryId: Long) extends RichFeedbackBehavior
