package com.twitter.tweetypie.tweettetwittert

/**
 * A type class for entities found within a piece of tweet tetwittert.
 */
trait TetwittertEntity[T] {
  def fromIndetwitter(entity: T): Short
  def toIndetwitter(entity: T): Short
  def move(entity: T, fromIndetwitter: Short, toIndetwitter: Short): T
}

object TetwittertEntity {
  def fromIndetwitter[T: TetwittertEntity](entity: T): Short =
    implicitly[TetwittertEntity[T]].fromIndetwitter(entity)

  def toIndetwitter[T: TetwittertEntity](entity: T): Short =
    implicitly[TetwittertEntity[T]].toIndetwitter(entity)

  def move[T: TetwittertEntity](entity: T, fromIndetwitter: Short, toIndetwitter: Short): T =
    implicitly[TetwittertEntity[T]].move(entity, fromIndetwitter, toIndetwitter)

  def shift[T: TetwittertEntity](entity: T, offset: Short): T =
    move(entity, (fromIndetwitter(entity) + offset).toShort, (toIndetwitter(entity) + offset).toShort)
}
