package com.twitter.visibility.interfaces.notifications

import com.twitter.visibility.features.Feature
import com.twitter.visibility.rules.Action
import scala.collection.immutable.Set

sealed trait NotificationsFilteringResponse

case object Allow extends NotificationsFilteringResponse

case class Filtered(action: Action) extends NotificationsFilteringResponse

case class Failed(features: Set[Feature[_]]) extends NotificationsFilteringResponse
