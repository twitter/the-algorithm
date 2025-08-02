package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.user

sealed trait UserDisplayType

case object User etwittertends UserDisplayType
case object UserDetailed etwittertends UserDisplayType
case object PendingFollowUser etwittertends UserDisplayType
