packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.melonssagelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.MelonssagelonActionTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.MelonssagelonTelonxtAction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.UselonrFacelonpilelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.UselonrFacelonpilelonDisplayTypelon

caselon class UselonrFacelonPilelonBuildelonr(
  uselonrIds: Selonq[Long],
  felonaturelondUselonrIds: Selonq[Long],
  action: Option[MelonssagelonTelonxtAction],
  actionTypelon: Option[MelonssagelonActionTypelon],
  displaysFelonaturingTelonxt: Option[Boolelonan],
  displayTypelon: Option[UselonrFacelonpilelonDisplayTypelon]) {

  delonf apply(): UselonrFacelonpilelon = UselonrFacelonpilelon(
    uselonrIds = uselonrIds,
    felonaturelondUselonrIds = felonaturelondUselonrIds,
    action = action,
    actionTypelon = actionTypelon,
    displaysFelonaturingTelonxt = displaysFelonaturingTelonxt,
    displayTypelon = displayTypelon
  )
}
