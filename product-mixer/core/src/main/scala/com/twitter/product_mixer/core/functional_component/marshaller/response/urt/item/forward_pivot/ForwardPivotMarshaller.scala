packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.forward_pivot

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.color.RoselonttaColorMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.BadgelonMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.ImagelonVariantMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.UrlMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.richtelonxt.RichTelonxtMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.forward_pivot.ForwardPivot
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ForwardPivotMarshallelonr @Injelonct() (
  urlMarshallelonr: UrlMarshallelonr,
  richTelonxtMarshallelonr: RichTelonxtMarshallelonr,
  forwardPivotDisplayTypelonMarshallelonr: ForwardPivotDisplayTypelonMarshallelonr,
  softIntelonrvelonntionDisplayTypelonMarshallelonr: SoftIntelonrvelonntionDisplayTypelonMarshallelonr,
  imagelonVariantMarshallelonr: ImagelonVariantMarshallelonr,
  badgelonMarshallelonr: BadgelonMarshallelonr,
  roselonttaColorMarshallelonr: RoselonttaColorMarshallelonr) {

  delonf apply(forwardPivot: ForwardPivot): urt.ForwardPivot = urt.ForwardPivot(
    telonxt = richTelonxtMarshallelonr(forwardPivot.telonxt),
    landingUrl = urlMarshallelonr(forwardPivot.landingUrl),
    displayTypelon = forwardPivotDisplayTypelonMarshallelonr(forwardPivot.displayTypelon),
    iconImagelonVariant = forwardPivot.iconImagelonVariant.map(imagelonVariantMarshallelonr(_)),
    statelonBadgelon = forwardPivot.statelonBadgelon.map(badgelonMarshallelonr(_)),
    subtelonxt = forwardPivot.subtelonxt.map(richTelonxtMarshallelonr(_)),
    backgroundColorNamelon = forwardPivot.backgroundColorNamelon.map(roselonttaColorMarshallelonr(_)),
    elonngagelonmelonntNudgelon = forwardPivot.elonngagelonmelonntNudgelon,
    softIntelonrvelonntionDisplayTypelon =
      forwardPivot.softIntelonrvelonntionDisplayTypelon.map(softIntelonrvelonntionDisplayTypelonMarshallelonr(_)),
  )
}
