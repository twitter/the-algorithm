package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.thread

sealed trait ThreadHeaderContent

case class UserThreadHeader(userId: Long) etwittertends ThreadHeaderContent
