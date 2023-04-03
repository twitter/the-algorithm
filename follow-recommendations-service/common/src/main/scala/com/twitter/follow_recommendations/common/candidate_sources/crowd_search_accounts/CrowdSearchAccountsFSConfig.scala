packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.crowd_selonarch_accounts

import com.twittelonr.follow_reloncommelonndations.configapi.common.FelonaturelonSwitchConfig
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSNamelon
import com.twittelonr.timelonlinelons.configapi.Param
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CrowdSelonarchAccountsFSConfig @Injelonct() () elonxtelonnds FelonaturelonSwitchConfig {
  ovelonrridelon val boolelonanFSParams: Selonq[Param[Boolelonan] with FSNamelon] = Selonq(
    CrowdSelonarchAccountsParams.CandidatelonSourcelonelonnablelond,
  )
  ovelonrridelon val doublelonFSParams: Selonq[FSBoundelondParam[Doublelon]] = Selonq(
    CrowdSelonarchAccountsParams.CandidatelonSourcelonWelonight,
  )
}
