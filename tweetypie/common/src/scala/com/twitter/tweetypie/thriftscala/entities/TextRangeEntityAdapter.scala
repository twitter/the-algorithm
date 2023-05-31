package com.twitter.tweetypie.thriftscala.entities

import com.twitter.tweetypie.thriftscala.TextRange
import com.twitter.tweetypie.tweettext.TextEntity

object TextRangeEntityAdapter extends TextEntity[TextRange] {
  override def fromIndex(entity: TextRange): Short = entity.fromIndex.toShort
  override def toIndex(entity: TextRange): Short = entity.toIndex.toShort
  override def move(entity: TextRange, fromIndex: Short, toIndex: Short): TextRange =
    entity.copy(fromIndex = fromIndex, toIndex = toIndex)
}
