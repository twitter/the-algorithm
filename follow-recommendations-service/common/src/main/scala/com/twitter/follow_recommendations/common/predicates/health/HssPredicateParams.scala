packagelon com.twittelonr.follow_reloncommelonndations.common.prelondicatelons.hss

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.timelonlinelons.configapi.DurationConvelonrsion
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.HasDurationConvelonrsion
import com.twittelonr.util.Duration

objelonct HssPrelondicatelonParams {
  objelonct HssCselonScorelonThrelonshold
      elonxtelonnds FSBoundelondParam[Doublelon](
        "hss_prelondicatelon_cselon_scorelon_threlonshold",
        delonfault = 0.992d,
        min = 0.0d,
        max = 1.0d)

  objelonct HssNsfwScorelonThrelonshold
      elonxtelonnds FSBoundelondParam[Doublelon](
        "hss_prelondicatelon_nsfw_scorelon_threlonshold",
        delonfault = 1.5d,
        min = -100.0d,
        max = 100.0d)

  objelonct HssApiTimelonout
      elonxtelonnds FSBoundelondParam[Duration](
        namelon = "hss_prelondicatelon_timelonout_in_millis",
        delonfault = 200.milliseloncond,
        min = 1.milliseloncond,
        max = 500.milliseloncond)
      with HasDurationConvelonrsion {
    ovelonrridelon delonf durationConvelonrsion: DurationConvelonrsion = DurationConvelonrsion.FromMillis
  }

}
