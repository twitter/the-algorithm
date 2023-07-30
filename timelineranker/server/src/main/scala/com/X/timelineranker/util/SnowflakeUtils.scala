package com.X.timelineranker.util

import com.X.snowflake.id.SnowflakeId
import com.X.util.Duration
import com.X.util.Time

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
