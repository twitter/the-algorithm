package com.X.product_mixer.core.util

import com.X.snowflake.id.SnowflakeId
import com.X.util.Time

object SortIndexBuilder {

  /** the [[Time]] from a [[SnowflakeId]] */
  def idToTime(id: Long): Time =
    Time.fromMilliseconds(SnowflakeId.unixTimeMillisOrFloorFromId(id))

  /** the first [[SnowflakeId]] possible for a given [[Time]]  */
  def timeToId(time: Time): Long = SnowflakeId.firstIdFor(time)

  /** the first [[SnowflakeId]] possible for a given unix epoch millis  */
  def timeToId(timeMillis: Long): Long = SnowflakeId.firstIdFor(timeMillis)
}
