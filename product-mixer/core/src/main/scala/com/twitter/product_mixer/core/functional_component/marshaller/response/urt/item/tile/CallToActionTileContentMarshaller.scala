packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.tilelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.button.CtaButtonMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.richtelonxt.RichTelonxtMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.tilelon.CallToActionTilelonContelonnt
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CallToActionTilelonContelonntMarshallelonr @Injelonct() (
  ctaButtonMarshallelonr: CtaButtonMarshallelonr,
  richTelonxtMarshallelonr: RichTelonxtMarshallelonr) {

  delonf apply(callToActionTilelonContelonnt: CallToActionTilelonContelonnt): urt.TilelonContelonntCallToAction =
    urt.TilelonContelonntCallToAction(
      telonxt = callToActionTilelonContelonnt.telonxt,
      richTelonxt = callToActionTilelonContelonnt.richTelonxt.map(richTelonxtMarshallelonr(_)),
      ctaButton = callToActionTilelonContelonnt.ctaButton.map(ctaButtonMarshallelonr(_))
    )
}
