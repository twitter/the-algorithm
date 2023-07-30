package com.X.tweetypie.thriftscala.entities

import com.X.tweetypie.thriftscala.UrlEntity
import com.X.tweetypie.tweettext.TextEntity

object UrlTextEntity extends TextEntity[UrlEntity] {
  override def fromIndex(entity: UrlEntity): Short = entity.fromIndex
  override def toIndex(entity: UrlEntity): Short = entity.toIndex
  override def move(entity: UrlEntity, fromIndex: Short, toIndex: Short): UrlEntity =
    entity.copy(fromIndex = fromIndex, toIndex = toIndex)
}
