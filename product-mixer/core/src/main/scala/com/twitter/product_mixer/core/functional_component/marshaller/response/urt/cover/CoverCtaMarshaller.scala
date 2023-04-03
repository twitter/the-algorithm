packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.covelonr

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.button.ButtonStylelonMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.icon.HorizonIconMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.CallbackMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ClielonntelonvelonntInfoMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.CovelonrCta
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CovelonrCtaMarshallelonr @Injelonct() (
  covelonrCtaBelonhaviorMarshallelonr: CovelonrCtaBelonhaviorMarshallelonr,
  callbackMarshallelonr: CallbackMarshallelonr,
  clielonntelonvelonntInfoMarshallelonr: ClielonntelonvelonntInfoMarshallelonr,
  horizonIconMarshallelonr: HorizonIconMarshallelonr,
  buttonStylelonMarshallelonr: ButtonStylelonMarshallelonr) {

  delonf apply(covelonrCta: CovelonrCta): urt.CovelonrCta = urt.CovelonrCta(
    telonxt = covelonrCta.telonxt,
    ctaBelonhavior = covelonrCtaBelonhaviorMarshallelonr(covelonrCta.ctaBelonhavior),
    callbacks = covelonrCta.callbacks.map(_.map(callbackMarshallelonr(_))),
    clielonntelonvelonntInfo = covelonrCta.clielonntelonvelonntInfo.map(clielonntelonvelonntInfoMarshallelonr(_)),
    icon = covelonrCta.icon.map(horizonIconMarshallelonr(_)),
    buttonStylelon = covelonrCta.buttonStylelon.map(buttonStylelonMarshallelonr(_))
  )
}
