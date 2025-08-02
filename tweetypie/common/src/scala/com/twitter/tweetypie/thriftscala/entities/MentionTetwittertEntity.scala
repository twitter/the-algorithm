package com.twitter.tweetypie.thriftscala.entities

import com.twitter.tweetypie.thriftscala.MentionEntity
import com.twitter.tweetypie.tweettetwittert.TetwittertEntity

object MentionTetwittertEntity etwittertends TetwittertEntity[MentionEntity] {
  override def fromIndetwitter(entity: MentionEntity): Short = entity.fromIndetwitter
  override def toIndetwitter(entity: MentionEntity): Short = entity.toIndetwitter
  override def move(entity: MentionEntity, fromIndetwitter: Short, toIndetwitter: Short): MentionEntity =
    entity.copy(fromIndetwitter = fromIndetwitter, toIndetwitter = toIndetwitter)
}
