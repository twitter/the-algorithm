package com.twitter.visibility.interfaces.conversations

import com.twitter.gizmoduck.thriftscala.Label
import com.twitter.gizmoduck.thriftscala.LabelValue
import com.twitter.servo.repository.KeyValueResult
import com.twitter.spam.rtf.thriftscala.SafetyLabel
import com.twitter.spam.rtf.thriftscala.SafetyLabelType
import com.twitter.visibility.models.ViewerContext

case class TimelineConversationsVisibilityRequest(
  conversationId: Long,
  tweetIds: Seq[Long],
  viewerContext: ViewerContext,
  minimalSectioningOnly: Boolean = false,
  prefetchedSafetyLabels: Option[KeyValueResult[Long, Map[SafetyLabelType, SafetyLabel]]] = None,
  prefetchedTweetAuthorUserLabels: Option[KeyValueResult[Long, Map[LabelValue, Label]]] = None,
  innerCircleOfFriendsRelationships: Option[KeyValueResult[Long, Boolean]] = None,
  tweetParentIdMap: Option[Map[Long, Option[Long]]] = None,
  rootAuthorIsVerified: Boolean = false,
  tweetAuthors: Option[KeyValueResult[Long, Long]] = None)
