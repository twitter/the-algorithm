package com.twitter.tweetypie.thriftscala.entities

import com.twitter.tweetypie.thriftscala.MediaEntity
import com.twitter.tweetypie.tweettext.TextEntity

object MediaTextEntity extends TextEntity[MediaEntity] {
  override def fromIndex(entity: MediaEntity): Short = entity.fromIndex
  override def toIndex(entity: MediaEntity): Short = entity.toIndex
  override def move(entity: MediaEntity, fromIndex: Short, toIndex: Short): MediaEntity =
    entity.copy(fromIndex = fromIndex, toIndex = toIndex)
}
