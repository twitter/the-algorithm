package com.twitter.tweetypie.thriftscala.entities

import com.twitter.tweetypie.thriftscala.CashtagEntity
import com.twitter.tweetypie.tweettext.TextEntity

object CashtagTextEntity extends TextEntity[CashtagEntity] {
  override def fromIndex(entity: CashtagEntity): Short = entity.fromIndex
  override def toIndex(entity: CashtagEntity): Short = entity.toIndex
  override def move(entity: CashtagEntity, fromIndex: Short, toIndex: Short): CashtagEntity =
    entity.copy(fromIndex = fromIndex, toIndex = toIndex)
}
