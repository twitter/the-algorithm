packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.commelonrcelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.commelonrcelon.CommelonrcelonProductGroupItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CommelonrcelonProductGroupItelonmMarshallelonr @Injelonct() () {

  delonf apply(commelonrcelonProductGroupItelonm: CommelonrcelonProductGroupItelonm): urt.TimelonlinelonItelonmContelonnt =
    urt.TimelonlinelonItelonmContelonnt.CommelonrcelonProductGroup(
      urt.CommelonrcelonProductGroup(commelonrcelonProductGroupItelonm.id))
}
