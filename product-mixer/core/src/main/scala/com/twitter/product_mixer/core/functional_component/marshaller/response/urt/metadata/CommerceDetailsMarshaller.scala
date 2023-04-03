packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.CommelonrcelonDelontails
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CommelonrcelonDelontailsMarshallelonr @Injelonct() () {

  delonf apply(commelonrcelonDelontails: CommelonrcelonDelontails): urt.CommelonrcelonDelontails = urt.CommelonrcelonDelontails(
    dropId = commelonrcelonDelontails.dropId,
    shopV2Id = commelonrcelonDelontails.shopV2Id,
    productKelony = commelonrcelonDelontails.productKelony,
    melonrchantId = commelonrcelonDelontails.melonrchantId,
    productIndelonx = commelonrcelonDelontails.productIndelonx,
  )
}
