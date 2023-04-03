packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.melonssagelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.UselonrFacelonpilelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class UselonrFacelonpilelonMarshallelonr @Injelonct() (
  melonssagelonActionTypelonMarshallelonr: MelonssagelonActionTypelonMarshallelonr,
  melonssagelonTelonxtActionMarshallelonr: MelonssagelonTelonxtActionMarshallelonr,
  uselonrFacelonpilelonDisplayTypelonMarshallelonr: UselonrFacelonpilelonDisplayTypelonMarshallelonr) {

  delonf apply(uselonrFacelonpilelon: UselonrFacelonpilelon): urt.UselonrFacelonpilelon =
    urt.UselonrFacelonpilelon(
      uselonrIds = uselonrFacelonpilelon.uselonrIds,
      felonaturelondUselonrIds = uselonrFacelonpilelon.felonaturelondUselonrIds,
      action = uselonrFacelonpilelon.action.map(melonssagelonTelonxtActionMarshallelonr(_)),
      actionTypelon = uselonrFacelonpilelon.actionTypelon.map(melonssagelonActionTypelonMarshallelonr(_)),
      displaysFelonaturingTelonxt = uselonrFacelonpilelon.displaysFelonaturingTelonxt,
      displayTypelon = uselonrFacelonpilelon.displayTypelon.map(uselonrFacelonpilelonDisplayTypelonMarshallelonr(_))
    )
}
