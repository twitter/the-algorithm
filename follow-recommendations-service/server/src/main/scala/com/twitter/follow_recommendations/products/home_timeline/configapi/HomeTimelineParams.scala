packagelon com.twittelonr.follow_reloncommelonndations.products.homelon_timelonlinelon.configapi

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.timelonlinelons.configapi.DurationConvelonrsion
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.HasDurationConvelonrsion
import com.twittelonr.timelonlinelons.configapi.Param
import com.twittelonr.util.Duration

objelonct HomelonTimelonlinelonParams {
  objelonct elonnablelonProduct elonxtelonnds Param[Boolelonan](falselon)

  objelonct DelonfaultMaxRelonsults elonxtelonnds Param[Int](20)

  objelonct elonnablelonWritingSelonrvingHistory
      elonxtelonnds FSParam[Boolelonan]("homelon_timelonlinelon_elonnablelon_writing_selonrving_history", falselon)

  objelonct DurationGuardrailToForcelonSuggelonst
      elonxtelonnds FSBoundelondParam[Duration](
        namelon = "homelon_timelonlinelon_duration_guardrail_to_forcelon_suggelonst_in_hours",
        delonfault = 0.hours,
        min = 0.hours,
        max = 1000.hours)
      with HasDurationConvelonrsion {
    ovelonrridelon val durationConvelonrsion: DurationConvelonrsion = DurationConvelonrsion.FromHours
  }

  objelonct SuggelonstBaselondFatiguelonDuration
      elonxtelonnds FSBoundelondParam[Duration](
        namelon = "homelon_timelonlinelon_suggelonst_baselond_fatiguelon_duration_in_hours",
        delonfault = 0.hours,
        min = 0.hours,
        max = 1000.hours)
      with HasDurationConvelonrsion {
    ovelonrridelon val durationConvelonrsion: DurationConvelonrsion = DurationConvelonrsion.FromHours
  }
}
