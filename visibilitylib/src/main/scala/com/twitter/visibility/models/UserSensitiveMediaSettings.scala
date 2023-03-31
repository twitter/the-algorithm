package com.twitter.visibility.models

import com.twitter.contenthealth.sensitivemediasettings.thriftscala.SensitiveMediaSettings


case class UserSensitiveMediaSettings(sensitiveMediaSettings: Option[SensitiveMediaSettings]) {

  def unapply(
    userSensitiveMediaSettings: UserSensitiveMediaSettings
  ): Option[SensitiveMediaSettings] = {
    sensitiveMediaSettings
  }
}
