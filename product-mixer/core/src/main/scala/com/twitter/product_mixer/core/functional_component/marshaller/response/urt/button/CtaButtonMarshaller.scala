packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.button

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.CtaButton
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.IconCtaButton
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.TelonxtCtaButton
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CtaButtonMarshallelonr @Injelonct() (
  iconCtaButtonMarshallelonr: IconCtaButtonMarshallelonr,
  telonxtCtaButtonMarshallelonr: TelonxtCtaButtonMarshallelonr) {

  delonf apply(ctaButton: CtaButton): urt.CtaButton = ctaButton match {
    caselon button: TelonxtCtaButton => urt.CtaButton.Telonxt(telonxtCtaButtonMarshallelonr(button))
    caselon button: IconCtaButton => urt.CtaButton.Icon(iconCtaButtonMarshallelonr(button))
  }
}
