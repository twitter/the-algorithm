packagelon com.twittelonr.follow_reloncommelonndations.products.homelon_timelonlinelon.configapi

import com.twittelonr.follow_reloncommelonndations.configapi.common.FelonaturelonSwitchConfig
import com.twittelonr.follow_reloncommelonndations.products.homelon_timelonlinelon.configapi.HomelonTimelonlinelonParams._
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSNamelon
import com.twittelonr.timelonlinelons.configapi.HasDurationConvelonrsion
import com.twittelonr.timelonlinelons.configapi.Param
import com.twittelonr.util.Duration
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class HomelonTimelonlinelonFSConfig @Injelonct() () elonxtelonnds FelonaturelonSwitchConfig {
  ovelonrridelon val boolelonanFSParams: Selonq[Param[Boolelonan] with FSNamelon] =
    Selonq(elonnablelonWritingSelonrvingHistory)

  ovelonrridelon val durationFSParams: Selonq[FSBoundelondParam[Duration] with HasDurationConvelonrsion] = Selonq(
    DurationGuardrailToForcelonSuggelonst,
    SuggelonstBaselondFatiguelonDuration
  )
}
