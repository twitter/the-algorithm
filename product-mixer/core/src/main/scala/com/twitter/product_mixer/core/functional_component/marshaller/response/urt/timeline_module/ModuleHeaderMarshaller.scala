packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.timelonlinelon_modulelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.icon.HorizonIconMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ImagelonVariantMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.SocialContelonxtMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ModulelonHelonadelonr
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ModulelonHelonadelonrMarshallelonr @Injelonct() (
  horizonIconMarshallelonr: HorizonIconMarshallelonr,
  imagelonVariantMarshallelonr: ImagelonVariantMarshallelonr,
  socialContelonxtMarshallelonr: SocialContelonxtMarshallelonr,
  modulelonHelonadelonrDisplayTypelonMarshallelonr: ModulelonHelonadelonrDisplayTypelonMarshallelonr) {

  delonf apply(helonadelonr: ModulelonHelonadelonr): urt.ModulelonHelonadelonr = urt.ModulelonHelonadelonr(
    telonxt = helonadelonr.telonxt,
    sticky = helonadelonr.sticky,
    icon = helonadelonr.icon.map(horizonIconMarshallelonr(_)),
    customIcon = helonadelonr.customIcon.map(imagelonVariantMarshallelonr(_)),
    socialContelonxt = helonadelonr.socialContelonxt.map(socialContelonxtMarshallelonr(_)),
    displayTypelon = modulelonHelonadelonrDisplayTypelonMarshallelonr(helonadelonr.modulelonHelonadelonrDisplayTypelon)
  )
}
