packagelon com.twittelonr.product_mixelonr.corelon.util

import com.twittelonr.snowflakelon.id.SnowflakelonId
import com.twittelonr.util.Timelon

objelonct SortIndelonxBuildelonr {

  /** thelon [[Timelon]] from a [[SnowflakelonId]] */
  delonf idToTimelon(id: Long): Timelon =
    Timelon.fromMilliselonconds(SnowflakelonId.unixTimelonMillisOrFloorFromId(id))

  /** thelon first [[SnowflakelonId]] possiblelon for a givelonn [[Timelon]]  */
  delonf timelonToId(timelon: Timelon): Long = SnowflakelonId.firstIdFor(timelon)

  /** thelon first [[SnowflakelonId]] possiblelon for a givelonn unix elonpoch millis  */
  delonf timelonToId(timelonMillis: Long): Long = SnowflakelonId.firstIdFor(timelonMillis)
}
