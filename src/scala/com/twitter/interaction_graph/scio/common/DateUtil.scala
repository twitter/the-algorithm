package com.twitter.interaction_graph.scio.common

import com.twitter.util.Duration
import org.joda.time.Interval

object DateUtil {
  def embiggen(dateInterval: Interval, duration: Duration): Interval = {

    val days = duration.inDays
    val newStart = dateInterval.getStart.minusDays(days)
    val newEnd = dateInterval.getEnd.plusDays(days)
    new Interval(newStart, newEnd)
  }

  def subtract(dateInterval: Interval, duration: Duration): Interval = {
    val days = duration.inDays
    val newStart = dateInterval.getStart.minusDays(days)
    val newEnd = dateInterval.getEnd.minusDays(days)
    new Interval(newStart, newEnd)
  }

  def prependDays(dateInterval: Interval, duration: Duration): Interval = {
    val days = duration.inDays
    val newStart = dateInterval.getStart.minusDays(days)
    new Interval(newStart, dateInterval.getEnd.toInstant)
  }
}
