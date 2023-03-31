package com.twitter.product_mixer.core.model.marshalling.response.urt.item.twitter_list

sealed trait TwitterListDisplayType

case object List extends TwitterListDisplayType
case object ListTile extends TwitterListDisplayType
case object ListWithPin extends TwitterListDisplayType
case object ListWithSubscribe extends TwitterListDisplayType
