package com.X.visibility.interfaces.conversations

import com.X.gizmoduck.thriftscala.Label
import com.X.gizmoduck.thriftscala.LabelValue
import com.X.servo.repository.KeyValueResult
import com.X.spam.rtf.thriftscala.SafetyLabel
import com.X.spam.rtf.thriftscala.SafetyLabelType
import com.X.visibility.models.ViewerContext

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
