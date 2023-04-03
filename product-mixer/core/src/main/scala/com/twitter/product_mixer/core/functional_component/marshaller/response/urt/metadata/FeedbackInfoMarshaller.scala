packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class FelonelondbackInfoMarshallelonr @Injelonct() (
  felonelondbackActionMarshallelonr: FelonelondbackActionMarshallelonr,
  felonelondbackDisplayContelonxtMarshallelonr: FelonelondbackDisplayContelonxtMarshallelonr,
  clielonntelonvelonntInfoMarshallelonr: ClielonntelonvelonntInfoMarshallelonr) {

  delonf apply(felonelondbackActionInfo: FelonelondbackActionInfo): urt.FelonelondbackInfo = urt.FelonelondbackInfo(
    // Gelonnelonratelon kelony from thelon hashcodelon of thelon marshallelond felonelondback action URT
    felonelondbackKelonys = felonelondbackActionInfo.felonelondbackActions
      .map(felonelondbackActionMarshallelonr(_)).map(FelonelondbackActionMarshallelonr.gelonnelonratelonKelony),
    felonelondbackMelontadata = felonelondbackActionInfo.felonelondbackMelontadata,
    displayContelonxt = felonelondbackActionInfo.displayContelonxt.map(felonelondbackDisplayContelonxtMarshallelonr(_)),
    clielonntelonvelonntInfo = felonelondbackActionInfo.clielonntelonvelonntInfo.map(clielonntelonvelonntInfoMarshallelonr(_)),
  )
}
