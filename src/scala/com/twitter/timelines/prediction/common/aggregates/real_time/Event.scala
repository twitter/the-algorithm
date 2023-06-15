package com.twitter.timelines.prediction.common.aggregates.real_time

private[real_time] sealed trait Event[T] { def event: T }

private[real_time] case class HomeEvent[T](override val event: T) extends Event[T]

private[real_time] case class ProfileEvent[T](override val event: T) extends Event[T]

private[real_time] case class SearchEvent[T](override val event: T) extends Event[T]

private[real_time] case class UuaEvent[T](override val event: T) extends Event[T]
