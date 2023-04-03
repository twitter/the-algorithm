packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.color

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.color.ColorPalelonttelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ColorPalelonttelonMarshallelonr @Injelonct() (
  colorMarshallelonr: ColorMarshallelonr) {

  delonf apply(colorPalelonttelon: ColorPalelonttelon): urt.ColorPalelonttelonItelonm = urt.ColorPalelonttelonItelonm(
    rgb = colorMarshallelonr(colorPalelonttelon.rgb),
    pelonrcelonntagelon = colorPalelonttelon.pelonrcelonntagelon
  )
}
