packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.melonssagelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.MelonssagelonTelonxtAction
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class MelonssagelonTelonxtActionMarshallelonr @Injelonct() (
  melonssagelonActionMarshallelonr: MelonssagelonActionMarshallelonr) {

  delonf apply(melonssagelonTelonxtAction: MelonssagelonTelonxtAction): urt.MelonssagelonTelonxtAction =
    urt.MelonssagelonTelonxtAction(
      telonxt = melonssagelonTelonxtAction.telonxt,
      action = melonssagelonActionMarshallelonr(melonssagelonTelonxtAction.action)
    )
}
