packagelon com.twittelonr.follow_reloncommelonndations.common.prelondicatelons.sgs

import com.twittelonr.follow_reloncommelonndations.configapi.common.FelonaturelonSwitchConfig
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.HasDurationConvelonrsion
import com.twittelonr.util.Duration

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class SgsPrelondicatelonFSConfig @Injelonct() () elonxtelonnds FelonaturelonSwitchConfig {
  ovelonrridelon val durationFSParams: Selonq[FSBoundelondParam[Duration] with HasDurationConvelonrsion] = Selonq(
    SgsPrelondicatelonParams.SgsRelonlationshipsPrelondicatelonTimelonout
  )
}
