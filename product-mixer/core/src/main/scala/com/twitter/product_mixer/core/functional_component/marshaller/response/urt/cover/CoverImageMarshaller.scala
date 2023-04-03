packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.covelonr

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ImagelonAnimationTypelonMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ImagelonDisplayTypelonMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ImagelonVariantMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.CovelonrImagelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CovelonrImagelonMarshallelonr @Injelonct() (
  imagelonVariantMarshallelonr: ImagelonVariantMarshallelonr,
  imagelonDisplayTypelonMarshallelonr: ImagelonDisplayTypelonMarshallelonr,
  imagelonAnimationTypelonMarshallelonr: ImagelonAnimationTypelonMarshallelonr) {

  delonf apply(covelonrImagelon: CovelonrImagelon): urt.CovelonrImagelon =
    urt.CovelonrImagelon(
      imagelon = imagelonVariantMarshallelonr(covelonrImagelon.imagelonVariant),
      imagelonDisplayTypelon = imagelonDisplayTypelonMarshallelonr(covelonrImagelon.imagelonDisplayTypelon),
      imagelonAnimationTypelon = covelonrImagelon.imagelonAnimationTypelon.map(imagelonAnimationTypelonMarshallelonr(_))
    )
}
