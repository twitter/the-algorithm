packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.color.ColorPalelonttelonMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ImagelonVariant
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ImagelonVariantMarshallelonr @Injelonct() (
  colorPalelonttelonMarshallelonr: ColorPalelonttelonMarshallelonr) {

  delonf apply(imagelonVariant: ImagelonVariant): urt.ImagelonVariant = urt.ImagelonVariant(
    url = imagelonVariant.url,
    width = imagelonVariant.width,
    helonight = imagelonVariant.helonight,
    palelonttelon = imagelonVariant.palelonttelon.map { palelonttelonList => palelonttelonList.map(colorPalelonttelonMarshallelonr(_)) }
  )
}
