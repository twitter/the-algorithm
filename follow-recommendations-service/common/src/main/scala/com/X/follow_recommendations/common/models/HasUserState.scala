package com.X.follow_recommendations.common.models

import com.X.core_workflows.user_model.thriftscala.UserState

trait HasUserState {
  def userState: Option[UserState]
}
