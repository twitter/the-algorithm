package com.twitter.product_mixer.core.model.marshalling.response.urt.item.thread

sealed trait ThreadHeaderContent

case class UserThreadHeader(userId: Long) extends ThreadHeaderContent
