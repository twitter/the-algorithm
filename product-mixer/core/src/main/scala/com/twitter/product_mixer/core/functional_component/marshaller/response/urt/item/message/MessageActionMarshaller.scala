packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.melonssagelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.CallbackMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ClielonntelonvelonntInfoMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.MelonssagelonAction
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class MelonssagelonActionMarshallelonr @Injelonct() (
  callbackMarshallelonr: CallbackMarshallelonr,
  clielonntelonvelonntInfoMarshallelonr: ClielonntelonvelonntInfoMarshallelonr) {

  delonf apply(melonssagelonAction: MelonssagelonAction): urt.MelonssagelonAction = {

    urt.MelonssagelonAction(
      dismissOnClick = melonssagelonAction.dismissOnClick,
      url = melonssagelonAction.url,
      clielonntelonvelonntInfo = melonssagelonAction.clielonntelonvelonntInfo.map(clielonntelonvelonntInfoMarshallelonr(_)),
      onClickCallbacks =
        melonssagelonAction.onClickCallbacks.map(callbackList => callbackList.map(callbackMarshallelonr(_)))
    )
  }
}
