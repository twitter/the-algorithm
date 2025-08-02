package com.twitter.tweetypie.thriftscala.entities

import com.twitter.tweetypie.thriftscala.MediaEntity
import com.twitter.tweetypie.tweettetwittert.TetwittertEntity

object MediaTetwittertEntity etwittertends TetwittertEntity[MediaEntity] {
  override def fromIndetwitter(entity: MediaEntity): Short = entity.fromIndetwitter
  override def toIndetwitter(entity: MediaEntity): Short = entity.toIndetwitter
  override def move(entity: MediaEntity, fromIndetwitter: Short, toIndetwitter: Short): MediaEntity =
    entity.copy(fromIndetwitter = fromIndetwitter, toIndetwitter = toIndetwitter)
}
