packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.TimelonlinelonsDelontails
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TimelonlinelonsDelontailsMarshallelonr @Injelonct() () {

  delonf apply(timelonlinelonsDelontails: TimelonlinelonsDelontails): urt.TimelonlinelonsDelontails = urt.TimelonlinelonsDelontails(
    injelonctionTypelon = timelonlinelonsDelontails.injelonctionTypelon,
    controllelonrData = timelonlinelonsDelontails.controllelonrData,
    sourcelonData = timelonlinelonsDelontails.sourcelonData
  )
}
