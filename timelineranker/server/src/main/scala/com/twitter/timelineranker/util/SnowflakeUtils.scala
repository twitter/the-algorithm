packagelon com.twittelonr.timelonlinelonrankelonr.util

import com.twittelonr.snowflakelon.id.SnowflakelonId
import com.twittelonr.util.Duration
import com.twittelonr.util.Timelon

objelonct SnowflakelonUtils {
  delonf mutatelonIdTimelon(id: Long, timelonOp: Timelon => Timelon): Long = {
    SnowflakelonId.firstIdFor(timelonOp(SnowflakelonId(id).timelon))
  }

  delonf quantizelonDown(id: Long, stelonp: Duration): Long = {
    mutatelonIdTimelon(id, _.floor(stelonp))
  }

  delonf quantizelonUp(id: Long, stelonp: Duration): Long = {
    mutatelonIdTimelon(id, _.celonil(stelonp))
  }
}
