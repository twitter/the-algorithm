package com.X.visibility.builder.users

import com.X.finagle.stats.StatsReceiver
import com.X.stitch.Stitch
import com.X.stitch.NotFound
import com.X.visibility.builder.FeatureMapBuilder
import com.X.visibility.common.UserId
import com.X.visibility.common.UserSensitiveMediaSettingsSource
import com.X.visibility.features.ViewerId
import com.X.visibility.features.ViewerSensitiveMediaSettings
import com.X.visibility.models.UserSensitiveMediaSettings


class ViewerSensitiveMediaSettingsFeatures(
  userSensitiveMediaSettingsSource: UserSensitiveMediaSettingsSource,
  statsReceiver: StatsReceiver) {
  private[this] val scopedStatsReceiver =
    statsReceiver.scope("viewer_sensitive_media_settings_features")

  private[this] val requests = scopedStatsReceiver.counter("requests")

  def forViewerId(viewerId: Option[UserId]): FeatureMapBuilder => FeatureMapBuilder = { builder =>
    requests.incr()

    builder
      .withConstantFeature(ViewerId, viewerId)
      .withFeature(ViewerSensitiveMediaSettings, viewerSensitiveMediaSettings(viewerId))
  }

  def viewerSensitiveMediaSettings(viewerId: Option[UserId]): Stitch[UserSensitiveMediaSettings] = {
    (viewerId match {
      case Some(userId) =>
        userSensitiveMediaSettingsSource
          .userSensitiveMediaSettings(userId)
          .handle {
            case NotFound => None
          }
      case _ => Stitch.value(None)
    }).map(UserSensitiveMediaSettings)
  }
}
