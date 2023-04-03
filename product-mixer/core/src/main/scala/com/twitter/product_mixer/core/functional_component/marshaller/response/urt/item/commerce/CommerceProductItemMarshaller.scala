packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.commelonrcelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.commelonrcelon.CommelonrcelonProductItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CommelonrcelonProductItelonmMarshallelonr @Injelonct() () {

  delonf apply(commelonrcelonProductItelonm: CommelonrcelonProductItelonm): urt.TimelonlinelonItelonmContelonnt =
    urt.TimelonlinelonItelonmContelonnt.CommelonrcelonProduct(urt.CommelonrcelonProduct(commelonrcelonProductItelonm.id))
}
