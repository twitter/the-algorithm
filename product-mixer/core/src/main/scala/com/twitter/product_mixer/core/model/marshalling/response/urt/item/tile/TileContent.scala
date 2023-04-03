packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.tilelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.CtaButton
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Badgelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxt

selonalelond trait TilelonContelonnt

caselon class StandardTilelonContelonnt(
  titlelon: String,
  supportingTelonxt: String,
  badgelon: Option[Badgelon])
    elonxtelonnds TilelonContelonnt

caselon class CallToActionTilelonContelonnt(
  telonxt: String,
  richTelonxt: Option[RichTelonxt],
  ctaButton: Option[CtaButton])
    elonxtelonnds TilelonContelonnt

//todo: Add othelonr TilelonContelonnt typelons latelonr
