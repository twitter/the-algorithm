packagelon com.twittelonr.follow_reloncommelonndations.common.prelondicatelons.sgs

import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.DurationConvelonrsion
import com.twittelonr.timelonlinelons.configapi.HasDurationConvelonrsion
import com.twittelonr.util.Duration
import com.twittelonr.convelonrsions.DurationOps._

objelonct SgsPrelondicatelonParams {
  caselon objelonct SgsRelonlationshipsPrelondicatelonTimelonout
      elonxtelonnds FSBoundelondParam[Duration](
        namelon = "sgs_prelondicatelon_relonlationships_timelonout_in_millis",
        delonfault = 300.milliseloncond,
        min = 1.milliseloncond,
        max = 1000.milliseloncond)
      with HasDurationConvelonrsion {
    ovelonrridelon delonf durationConvelonrsion: DurationConvelonrsion = DurationConvelonrsion.FromMillis
  }
}
