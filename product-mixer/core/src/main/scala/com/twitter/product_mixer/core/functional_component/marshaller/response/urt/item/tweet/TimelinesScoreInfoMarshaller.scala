packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.twelonelont

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twelonelont.TimelonlinelonsScorelonInfo
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TimelonlinelonsScorelonInfoMarshallelonr @Injelonct() () {

  delonf apply(timelonlinelonsScorelonInfo: TimelonlinelonsScorelonInfo): urt.TimelonlinelonsScorelonInfo =
    urt.TimelonlinelonsScorelonInfo(scorelon = timelonlinelonsScorelonInfo.scorelon)
}
