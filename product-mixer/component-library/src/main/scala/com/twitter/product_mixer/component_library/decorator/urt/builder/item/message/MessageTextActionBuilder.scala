packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.melonssagelon

import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonStr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.MelonssagelonAction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.MelonssagelonTelonxtAction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Callback
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct MelonssagelonTelonxtActionBuildelonr {
  val MelonssagelonTelonxtActionClielonntelonvelonntInfoelonlelonmelonnt: String = "melonssagelon-telonxt-action"
}

caselon class MelonssagelonTelonxtActionBuildelonr[-Quelonry <: PipelonlinelonQuelonry, -Candidatelon <: UnivelonrsalNoun[Any]](
  telonxtBuildelonr: BaselonStr[Quelonry, Candidatelon],
  dismissOnClick: Boolelonan,
  url: Option[String] = Nonelon,
  clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo] = Nonelon,
  onClickCallbacks: Option[List[Callback]] = Nonelon) {

  delonf apply(
    quelonry: Quelonry,
    candidatelon: Candidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): MelonssagelonTelonxtAction = MelonssagelonTelonxtAction(
    telonxt = telonxtBuildelonr(quelonry, candidatelon, candidatelonFelonaturelons),
    action = MelonssagelonAction(
      dismissOnClick,
      url,
      clielonntelonvelonntInfo,
      onClickCallbacks
    )
  )
}
