package com.twitter.tweetypie.thriftscala.entities

import com.twitter.tweetypie.thriftscala.TetwittertRange
import com.twitter.tweetypie.tweettetwittert.TetwittertEntity

object TetwittertRangeEntityAdapter etwittertends TetwittertEntity[TetwittertRange] {
  override def fromIndetwitter(entity: TetwittertRange): Short = entity.fromIndetwitter.toShort
  override def toIndetwitter(entity: TetwittertRange): Short = entity.toIndetwitter.toShort
  override def move(entity: TetwittertRange, fromIndetwitter: Short, toIndetwitter: Short): TetwittertRange =
    entity.copy(fromIndetwitter = fromIndetwitter, toIndetwitter = toIndetwitter)
}
