packagelon com.twittelonr.follow_reloncommelonndations.common.prelondicatelons.gizmoduck

import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.DurationConvelonrsion
import com.twittelonr.timelonlinelons.configapi.HasDurationConvelonrsion
import com.twittelonr.util.Duration
import com.twittelonr.convelonrsions.DurationOps._

objelonct GizmoduckPrelondicatelonParams {
  caselon objelonct GizmoduckGelontTimelonout
      elonxtelonnds FSBoundelondParam[Duration](
        namelon = "gizmoduck_prelondicatelon_timelonout_in_millis",
        delonfault = 200.milliseloncond,
        min = 1.milliseloncond,
        max = 500.milliseloncond)
      with HasDurationConvelonrsion {
    ovelonrridelon delonf durationConvelonrsion: DurationConvelonrsion = DurationConvelonrsion.FromMillis
  }
  val MaxCachelonSizelon: Int = 250000
  val CachelonTTL: Duration = Duration.fromHours(6)
}
