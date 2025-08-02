package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata

sealed trait RichFeedbackBehavior

case class RichFeedbackBehaviorReportList(listId: Long, userId: Long) etwittertends RichFeedbackBehavior
case class RichFeedbackBehaviorBlockUser(userId: Long) etwittertends RichFeedbackBehavior
case class RichFeedbackBehaviorToggleFollowTopic(topicId: Long) etwittertends RichFeedbackBehavior
case class RichFeedbackBehaviorToggleFollowTopicV2(topicId: String) etwittertends RichFeedbackBehavior
case class RichFeedbackBehaviorToggleMuteList(listId: Long) etwittertends RichFeedbackBehavior
case class RichFeedbackBehaviorMarkNotInterestedTopic(topicId: String) etwittertends RichFeedbackBehavior
case class RichFeedbackBehaviorReplyPinState(replyPinState: ReplyPinState)
    etwittertends RichFeedbackBehavior
case class RichFeedbackBehaviorToggleMuteUser(userId: Long) etwittertends RichFeedbackBehavior
case class RichFeedbackBehaviorToggleFollowUser(userId: Long) etwittertends RichFeedbackBehavior
case class RichFeedbackBehaviorReportTweet(entryId: Long) etwittertends RichFeedbackBehavior
