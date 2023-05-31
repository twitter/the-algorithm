package com.twitter.tweetypie.thriftscala.entities

import com.twitter.tweetypie.thriftscala.UrlEntity
import com.twitter.tweetypie.tweettext.TextEntity

object UrlTextEntity extends TextEntity[UrlEntity] {
  override def fromIndex(entity: UrlEntity): Short = entity.fromIndex
  override def toIndex(entity: UrlEntity): Short = entity.toIndex
  override def move(entity: UrlEntity, fromIndex: Short, toIndex: Short): UrlEntity =
    entity.copy(fromIndex = fromIndex, toIndex = toIndex)
}
