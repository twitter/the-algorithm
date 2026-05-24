package com.twitter.follow_recommendations.common.models

import com.twitter.core_workflows.user_model.thriftscala.UserState

trait HasUserState {
  def userState: Option[UserState]
}
