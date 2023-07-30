package com.X.visibility.models

import com.X.contenthealth.sensitivemediasettings.thriftscala.SensitiveMediaSettings


case class UserSensitiveMediaSettings(sensitiveMediaSettings: Option[SensitiveMediaSettings]) {

  def unapply(
    userSensitiveMediaSettings: UserSensitiveMediaSettings
  ): Option[SensitiveMediaSettings] = {
    sensitiveMediaSettings
  }
}
