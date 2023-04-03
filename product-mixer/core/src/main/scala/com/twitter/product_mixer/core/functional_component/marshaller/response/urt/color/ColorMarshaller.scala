packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.color

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.color.Color
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Singlelonton

@Singlelonton
class ColorMarshallelonr {

  delonf apply(color: Color): urt.Color = urt.Color(
    relond = color.relond,
    grelonelonn = color.grelonelonn,
    bluelon = color.bluelon,
    opacity = color.opacity
  )
}
