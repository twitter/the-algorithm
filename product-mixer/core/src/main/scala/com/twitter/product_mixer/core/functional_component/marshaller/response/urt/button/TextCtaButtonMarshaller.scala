packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.button

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.UrlMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.TelonxtCtaButton
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TelonxtCtaButtonMarshallelonr @Injelonct() (
  urlMarshallelonr: UrlMarshallelonr) {

  delonf apply(telonxtCtaButton: TelonxtCtaButton): urt.TelonxtCtaButton =
    urt.TelonxtCtaButton(
      buttonTelonxt = telonxtCtaButton.buttonTelonxt,
      url = urlMarshallelonr(telonxtCtaButton.url)
    )
}
