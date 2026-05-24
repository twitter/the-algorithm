package com.twitter.timelineranker.util

import com.twitter.snowflake.id.SnowflakeId
import com.twitter.util.Duration
import com.twitter.util.Time

object SnowflakeUtils {
  def mutateIdTime(id: Long, timeOp: Time => Time): Long = {
    SnowflakeId.firstIdFor(timeOp(SnowflakeId(id).time))
  }

  def quantizeDown(id: Long, step: Duration): Long = {
    mutateIdTime(id, _.floor(step))
  }

  def quantizeUp(id: Long, step: Duration): Long = {
    mutateIdTime(id, _.ceil(step))
  }
}
