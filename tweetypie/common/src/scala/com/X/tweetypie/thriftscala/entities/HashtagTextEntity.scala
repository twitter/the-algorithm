package com.X.tweetypie.thriftscala.entities

import com.X.tweetypie.thriftscala.HashtagEntity
import com.X.tweetypie.tweettext.TextEntity

object HashtagTextEntity extends TextEntity[HashtagEntity] {
  override def fromIndex(entity: HashtagEntity): Short = entity.fromIndex
  override def toIndex(entity: HashtagEntity): Short = entity.toIndex
  override def move(entity: HashtagEntity, fromIndex: Short, toIndex: Short): HashtagEntity =
    entity.copy(fromIndex = fromIndex, toIndex = toIndex)
}
