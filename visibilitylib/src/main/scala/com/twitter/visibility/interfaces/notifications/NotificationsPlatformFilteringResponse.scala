package com.twitter.visibility.interfaces.notifications

import com.twitter.visibility.features.Feature
import com.twitter.visibility.rules.Action

trait NotificationsPlatformFilteringResponse

case object AllowVerdict extends NotificationsPlatformFilteringResponse

case class FilteredVerdict(action: Action) extends NotificationsPlatformFilteringResponse

case class FailedVerdict(featuresMap: Map[Feature[_], String])
    extends NotificationsPlatformFilteringResponse
