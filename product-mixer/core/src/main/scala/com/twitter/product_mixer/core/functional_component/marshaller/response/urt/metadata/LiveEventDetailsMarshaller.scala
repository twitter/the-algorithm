packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.LivelonelonvelonntDelontails
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class LivelonelonvelonntDelontailsMarshallelonr @Injelonct() () {

  delonf apply(livelonelonvelonntDelontails: LivelonelonvelonntDelontails): urt.LivelonelonvelonntDelontails = urt.LivelonelonvelonntDelontails(
    elonvelonntId = livelonelonvelonntDelontails.elonvelonntId
  )
}
