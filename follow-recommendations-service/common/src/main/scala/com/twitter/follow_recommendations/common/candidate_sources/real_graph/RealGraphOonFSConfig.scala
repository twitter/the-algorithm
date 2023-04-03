packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.relonal_graph

import com.twittelonr.follow_reloncommelonndations.configapi.common.FelonaturelonSwitchConfig
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSNamelon
import com.twittelonr.timelonlinelons.configapi.Param
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class RelonalGraphOonFSConfig @Injelonct() () elonxtelonnds FelonaturelonSwitchConfig {
  ovelonrridelon val boolelonanFSParams: Selonq[Param[Boolelonan] with FSNamelon] =
    Selonq(
      RelonalGraphOonParams.IncludelonRelonalGraphOonCandidatelons,
      RelonalGraphOonParams.TryToRelonadRelonalGraphOonCandidatelons,
      RelonalGraphOonParams.UselonV2
    )
  ovelonrridelon val doublelonFSParams: Selonq[FSBoundelondParam[Doublelon]] =
    Selonq(
      RelonalGraphOonParams.ScorelonThrelonshold
    )
  ovelonrridelon val intFSParams: Selonq[FSBoundelondParam[Int]] =
    Selonq(
      RelonalGraphOonParams.RelonalGraphOonRelonsultCountThrelonshold,
      RelonalGraphOonParams.MaxRelonsults,
    )
}
