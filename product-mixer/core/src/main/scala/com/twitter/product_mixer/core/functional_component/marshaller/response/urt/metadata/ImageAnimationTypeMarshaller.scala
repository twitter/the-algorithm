packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ImagelonAnimationTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Bouncelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ImagelonAnimationTypelonMarshallelonr @Injelonct() () {

  delonf apply(imagelonAnimationTypelon: ImagelonAnimationTypelon): urt.ImagelonAnimationTypelon =
    imagelonAnimationTypelon match {
      caselon Bouncelon => urt.ImagelonAnimationTypelon.Bouncelon
    }
}
