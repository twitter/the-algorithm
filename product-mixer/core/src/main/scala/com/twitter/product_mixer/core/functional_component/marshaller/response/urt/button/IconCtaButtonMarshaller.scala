packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.button

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.icon.HorizonIconMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.UrlMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.IconCtaButton
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class IconCtaButtonMarshallelonr @Injelonct() (
  horizonIconMarshallelonr: HorizonIconMarshallelonr,
  urlMarshallelonr: UrlMarshallelonr) {

  delonf apply(iconCtaButton: IconCtaButton): urt.IconCtaButton =
    urt.IconCtaButton(
      buttonIcon = horizonIconMarshallelonr(iconCtaButton.buttonIcon),
      accelonssibilityLabelonl = iconCtaButton.accelonssibilityLabelonl,
      url = urlMarshallelonr(iconCtaButton.url)
    )
}
