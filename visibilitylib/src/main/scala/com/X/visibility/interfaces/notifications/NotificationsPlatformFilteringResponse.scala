package com.X.visibility.interfaces.notifications

import com.X.visibility.features.Feature
import com.X.visibility.rules.Action

trait NotificationsPlatformFilteringResponse

case object AllowVerdict extends NotificationsPlatformFilteringResponse

case class FilteredVerdict(action: Action) extends NotificationsPlatformFilteringResponse

case class FailedVerdict(featuresMap: Map[Feature[_], String])
    extends NotificationsPlatformFilteringResponse
