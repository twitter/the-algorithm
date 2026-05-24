package com.twitter.tweetypie.thriftscala.entities

import com.twitter.tweetypie.thriftscala.MentionEntity
import com.twitter.tweetypie.tweettext.TextEntity

object MentionTextEntity extends TextEntity[MentionEntity] {
  override def fromIndex(entity: MentionEntity): Short = entity.fromIndex
  override def toIndex(entity: MentionEntity): Short = entity.toIndex
  override def move(entity: MentionEntity, fromIndex: Short, toIndex: Short): MentionEntity =
    entity.copy(fromIndex = fromIndex, toIndex = toIndex)
}
