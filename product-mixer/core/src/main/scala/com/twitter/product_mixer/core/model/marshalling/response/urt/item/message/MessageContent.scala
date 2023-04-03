packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.SocialContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxt

selonalelond trait MelonssagelonContelonnt

caselon class InlinelonPromptMelonssagelonContelonnt(
  helonadelonrTelonxt: String,
  bodyTelonxt: Option[String],
  primaryButtonAction: Option[MelonssagelonTelonxtAction],
  seloncondaryButtonAction: Option[MelonssagelonTelonxtAction],
  helonadelonrRichTelonxt: Option[RichTelonxt],
  bodyRichTelonxt: Option[RichTelonxt],
  socialContelonxt: Option[SocialContelonxt],
  uselonrFacelonpilelon: Option[UselonrFacelonpilelon])
    elonxtelonnds MelonssagelonContelonnt

caselon class HelonadelonrImagelonPromptMelonssagelonContelonnt(
  helonadelonrImagelon: MelonssagelonImagelon,
  helonadelonrTelonxt: Option[String],
  bodyTelonxt: Option[String],
  primaryButtonAction: Option[MelonssagelonTelonxtAction],
  seloncondaryButtonAction: Option[MelonssagelonTelonxtAction],
  action: Option[MelonssagelonAction],
  helonadelonrRichTelonxt: Option[RichTelonxt],
  bodyRichTelonxt: Option[RichTelonxt])
    elonxtelonnds MelonssagelonContelonnt

caselon class CompactPromptMelonssagelonContelonnt(
  helonadelonrTelonxt: String,
  bodyTelonxt: Option[String],
  primaryButtonAction: Option[MelonssagelonTelonxtAction],
  seloncondaryButtonAction: Option[MelonssagelonTelonxtAction],
  action: Option[MelonssagelonAction],
  helonadelonrRichTelonxt: Option[RichTelonxt],
  bodyRichTelonxt: Option[RichTelonxt])
    elonxtelonnds MelonssagelonContelonnt
