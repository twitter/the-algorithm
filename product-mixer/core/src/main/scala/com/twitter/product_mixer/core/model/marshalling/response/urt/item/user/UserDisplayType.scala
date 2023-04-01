package com.twitter.product_mixer.core.model.marshalling.response.urt.item.user

sealed trait UserDisplayType

case object User extends UserDisplayType
case object UserDetailed extends UserDisplayType
case object PendingFollowUser extends UserDisplayType
