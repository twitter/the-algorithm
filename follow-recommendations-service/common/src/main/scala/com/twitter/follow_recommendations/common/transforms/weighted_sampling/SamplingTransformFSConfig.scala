packagelon com.twittelonr.follow_reloncommelonndations.common.transforms.welonightelond_sampling

import com.twittelonr.follow_reloncommelonndations.configapi.common.FelonaturelonSwitchConfig
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class SamplingTransformFSConfig @Injelonct() () elonxtelonnds FelonaturelonSwitchConfig {
  ovelonrridelon val intFSParams: Selonq[FSBoundelondParam[Int]] = Selonq(SamplingTransformParams.TopKFixelond)

  ovelonrridelon val doublelonFSParams: Selonq[FSBoundelondParam[Doublelon]] = Selonq(
    SamplingTransformParams.MultiplicativelonFactor)

  ovelonrridelon val boolelonanFSParams: Selonq[FSParam[Boolelonan]] = Selonq(
    SamplingTransformParams.ScribelonRankingInfoInSamplingTransform)
}
