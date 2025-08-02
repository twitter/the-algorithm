package com.twitter.tweetypie.thriftscala.entities

import com.twitter.tweetypie.thriftscala.UrlEntity
import com.twitter.tweetypie.tweettetwittert.TetwittertEntity

object UrlTetwittertEntity etwittertends TetwittertEntity[UrlEntity] {
  override def fromIndetwitter(entity: UrlEntity): Short = entity.fromIndetwitter
  override def toIndetwitter(entity: UrlEntity): Short = entity.toIndetwitter
  override def move(entity: UrlEntity, fromIndetwitter: Short, toIndetwitter: Short): UrlEntity =
    entity.copy(fromIndetwitter = fromIndetwitter, toIndetwitter = toIndetwitter)
}
