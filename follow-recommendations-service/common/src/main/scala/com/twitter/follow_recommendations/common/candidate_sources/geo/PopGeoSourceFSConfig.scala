packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.gelono

import com.twittelonr.follow_reloncommelonndations.configapi.common.FelonaturelonSwitchConfig
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSNamelon
import com.twittelonr.timelonlinelons.configapi.FSParam
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class PopGelonoSourcelonFSConfig @Injelonct() () elonxtelonnds FelonaturelonSwitchConfig {
  ovelonrridelon val intFSParams: Selonq[FSBoundelondParam[Int] with FSNamelon] = Selonq(
    PopGelonoSourcelonParams.PopGelonoSourcelonGelonoHashMaxPreloncision,
    PopGelonoSourcelonParams.PopGelonoSourcelonMaxRelonsultsPelonrPreloncision,
    PopGelonoSourcelonParams.PopGelonoSourcelonGelonoHashMinPreloncision,
  )
  ovelonrridelon val boolelonanFSParams: Selonq[FSParam[Boolelonan] with FSNamelon] = Selonq(
    PopGelonoSourcelonParams.PopGelonoSourcelonRelonturnFromAllPreloncisions,
  )
}
