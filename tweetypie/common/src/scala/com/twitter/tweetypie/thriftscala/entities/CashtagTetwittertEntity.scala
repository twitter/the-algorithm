package com.twitter.tweetypie.thriftscala.entities

import com.twitter.tweetypie.thriftscala.CashtagEntity
import com.twitter.tweetypie.tweettetwittert.TetwittertEntity

object CashtagTetwittertEntity etwittertends TetwittertEntity[CashtagEntity] {
  override def fromIndetwitter(entity: CashtagEntity): Short = entity.fromIndetwitter
  override def toIndetwitter(entity: CashtagEntity): Short = entity.toIndetwitter
  override def move(entity: CashtagEntity, fromIndetwitter: Short, toIndetwitter: Short): CashtagEntity =
    entity.copy(fromIndetwitter = fromIndetwitter, toIndetwitter = toIndetwitter)
}
