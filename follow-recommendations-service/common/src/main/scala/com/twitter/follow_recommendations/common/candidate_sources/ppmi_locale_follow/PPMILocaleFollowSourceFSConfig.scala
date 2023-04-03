packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.ppmi_localelon_follow

import com.twittelonr.follow_reloncommelonndations.configapi.common.FelonaturelonSwitchConfig
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSNamelon
import com.twittelonr.timelonlinelons.configapi.Param

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class PPMILocalelonFollowSourcelonFSConfig @Injelonct() () elonxtelonnds FelonaturelonSwitchConfig {
  ovelonrridelon val boolelonanFSParams: Selonq[Param[Boolelonan] with FSNamelon] = Selonq(
    PPMILocalelonFollowSourcelonParams.CandidatelonSourcelonelonnablelond,
  )

  ovelonrridelon val stringSelonqFSParams: Selonq[Param[Selonq[String]] with FSNamelon] = Selonq(
    PPMILocalelonFollowSourcelonParams.LocalelonToelonxcludelonFromReloncommelonndation,
  )

  ovelonrridelon val doublelonFSParams: Selonq[FSBoundelondParam[Doublelon]] = Selonq(
    PPMILocalelonFollowSourcelonParams.CandidatelonSourcelonWelonight,
  )
}
