package com.X.tweetypie.thriftscala.entities

import com.X.tweetypie.thriftscala.MentionEntity
import com.X.tweetypie.tweettext.TextEntity

object MentionTextEntity extends TextEntity[MentionEntity] {
  override def fromIndex(entity: MentionEntity): Short = entity.fromIndex
  override def toIndex(entity: MentionEntity): Short = entity.toIndex
  override def move(entity: MentionEntity, fromIndex: Short, toIndex: Short): MentionEntity =
    entity.copy(fromIndex = fromIndex, toIndex = toIndex)
}
