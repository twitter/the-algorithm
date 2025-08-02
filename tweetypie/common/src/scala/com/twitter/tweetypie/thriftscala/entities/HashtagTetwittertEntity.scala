package com.twitter.tweetypie.thriftscala.entities

import com.twitter.tweetypie.thriftscala.HashtagEntity
import com.twitter.tweetypie.tweettetwittert.TetwittertEntity

object HashtagTetwittertEntity etwittertends TetwittertEntity[HashtagEntity] {
  override def fromIndetwitter(entity: HashtagEntity): Short = entity.fromIndetwitter
  override def toIndetwitter(entity: HashtagEntity): Short = entity.toIndetwitter
  override def move(entity: HashtagEntity, fromIndetwitter: Short, toIndetwitter: Short): HashtagEntity =
    entity.copy(fromIndetwitter = fromIndetwitter, toIndetwitter = toIndetwitter)
}
