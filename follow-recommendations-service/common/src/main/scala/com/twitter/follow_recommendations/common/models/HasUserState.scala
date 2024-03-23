package com.ExTwitter.follow_recommendations.common.models

import com.ExTwitter.core_workflows.user_model.thriftscala.UserState

trait HasUserState {
  def userState: Option[UserState]
}
