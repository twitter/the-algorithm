package com.twitter.tweetypie.tweettext

/**
 * A type class for entities found within a piece of tweet text.
 */
trait TextEntity[T] {
  def fromIndex(entity: T): Short
  def toIndex(entity: T): Short
  def move(entity: T, fromIndex: Short, toIndex: Short): T
}

object TextEntity {
  def fromIndex[T: TextEntity](entity: T): Short =
    implicitly[TextEntity[T]].fromIndex(entity)

  def toIndex[T: TextEntity](entity: T): Short =
    implicitly[TextEntity[T]].toIndex(entity)

  def move[T: TextEntity](entity: T, fromIndex: Short, toIndex: Short): T =
    implicitly[TextEntity[T]].move(entity, fromIndex, toIndex)

  def shift[T: TextEntity](entity: T, offset: Short): T =
    move(entity, (fromIndex(entity) + offset).toShort, (toIndex(entity) + offset).toShort)
}
