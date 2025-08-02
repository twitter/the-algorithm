package com.twitter.visibility.interfaces.notifications

import com.twitter.visibility.features.Feature
import com.twitter.visibility.rules.Action
import scala.collection.immutable.Set

sealed trait NotificationsFilteringResponse

case object Allow etwittertends NotificationsFilteringResponse

case class Filtered(action: Action) etwittertends NotificationsFilteringResponse

case class Failed(features: Set[Feature[_]]) etwittertends NotificationsFilteringResponse
