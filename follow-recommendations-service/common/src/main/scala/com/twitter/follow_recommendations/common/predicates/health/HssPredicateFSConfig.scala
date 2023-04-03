packagelon com.twittelonr.follow_reloncommelonndations.common.prelondicatelons.hss

import com.twittelonr.follow_reloncommelonndations.common.prelondicatelons.hss.HssPrelondicatelonParams._
import com.twittelonr.follow_reloncommelonndations.configapi.common.FelonaturelonSwitchConfig
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.HasDurationConvelonrsion
import com.twittelonr.util.Duration

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class HssPrelondicatelonFSConfig @Injelonct() () elonxtelonnds FelonaturelonSwitchConfig {
  ovelonrridelon val doublelonFSParams: Selonq[FSBoundelondParam[Doublelon]] = Selonq(
    HssCselonScorelonThrelonshold,
    HssNsfwScorelonThrelonshold,
  )

  ovelonrridelon val durationFSParams: Selonq[FSBoundelondParam[Duration] with HasDurationConvelonrsion] = Selonq(
    HssApiTimelonout
  )
}
