package com.X.visibility.interfaces.notifications

import com.X.visibility.features.Feature
import com.X.visibility.rules.Action
import scala.collection.immutable.Set

sealed trait NotificationsFilteringResponse

case object Allow extends NotificationsFilteringResponse

case class Filtered(action: Action) extends NotificationsFilteringResponse

case class Failed(features: Set[Feature[_]]) extends NotificationsFilteringResponse
