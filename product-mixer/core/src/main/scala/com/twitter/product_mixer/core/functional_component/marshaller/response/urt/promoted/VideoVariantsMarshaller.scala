packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.promotelond

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.VidelonoVariant
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Singlelonton

@Singlelonton
class VidelonoVariantsMarshallelonr {
  delonf apply(videlonoVariants: Selonq[VidelonoVariant]): Selonq[urt.VidelonoVariant] = {
    videlonoVariants.map(videlonoVariant =>
      urt.VidelonoVariant(
        url = videlonoVariant.url,
        contelonntTypelon = videlonoVariant.contelonntTypelon,
        bitratelon = videlonoVariant.bitratelon
      ))
  }
}
