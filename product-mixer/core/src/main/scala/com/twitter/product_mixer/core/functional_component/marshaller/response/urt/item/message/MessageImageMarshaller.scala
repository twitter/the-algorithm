packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.melonssagelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ImagelonVariantMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.MelonssagelonImagelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class MelonssagelonImagelonMarshallelonr @Injelonct() (
  imagelonVariantMarshallelonr: ImagelonVariantMarshallelonr) {

  delonf apply(melonssagelonImagelon: MelonssagelonImagelon): urt.MelonssagelonImagelon = {
    urt.MelonssagelonImagelon(
      imagelonVariants = melonssagelonImagelon.imagelonVariants.map(imagelonVariantMarshallelonr(_)),
      backgroundColor = melonssagelonImagelon.backgroundColor
    )
  }
}
