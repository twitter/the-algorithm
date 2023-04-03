packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.stp

import com.twittelonr.follow_reloncommelonndations.configapi.common.FelonaturelonSwitchConfig
import com.twittelonr.timelonlinelons.configapi.FSNamelon
import com.twittelonr.timelonlinelons.configapi.FSParam
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class OnlinelonSTPSourcelonFSConfig @Injelonct() () elonxtelonnds FelonaturelonSwitchConfig {
  ovelonrridelon val boolelonanFSParams: Selonq[FSParam[Boolelonan] with FSNamelon] = Selonq(
    OnlinelonSTPSourcelonParams.DisablelonHelonavyRankelonr,
    OnlinelonSTPSourcelonParams.UselonDBv2Scorelonr,
  )
}
