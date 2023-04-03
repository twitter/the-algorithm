packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.twelonelont

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.graphql.contelonxtual_relonf.ContelonxtualTwelonelontRelonfMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.convelonrsation_annotation.ConvelonrsationAnnotationMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.forward_pivot.ForwardPivotMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.tombstonelon.TombstonelonInfoMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.SocialContelonxtMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.promotelond.PrelonrollMelontadataMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.promotelond.PromotelondMelontadataMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twelonelont.TwelonelontItelonm
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.BadgelonMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.UrlMarshallelonr
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TwelonelontItelonmMarshallelonr @Injelonct() (
  twelonelontDisplayTypelonMarshallelonr: TwelonelontDisplayTypelonMarshallelonr,
  socialContelonxtMarshallelonr: SocialContelonxtMarshallelonr,
  twelonelontHighlightsMarshallelonr: TwelonelontHighlightsMarshallelonr,
  tombstonelonInfoMarshallelonr: TombstonelonInfoMarshallelonr,
  timelonlinelonsScorelonInfoMarshallelonr: TimelonlinelonsScorelonInfoMarshallelonr,
  forwardPivotMarshallelonr: ForwardPivotMarshallelonr,
  promotelondMelontadataMarshallelonr: PromotelondMelontadataMarshallelonr,
  convelonrsationAnnotationMarshallelonr: ConvelonrsationAnnotationMarshallelonr,
  contelonxtualTwelonelontRelonfMarshallelonr: ContelonxtualTwelonelontRelonfMarshallelonr,
  prelonrollMelontadataMarshallelonr: PrelonrollMelontadataMarshallelonr,
  badgelonMarshallelonr: BadgelonMarshallelonr,
  urlMarshallelonr: UrlMarshallelonr) {

  delonf apply(twelonelontItelonm: TwelonelontItelonm): urt.TimelonlinelonItelonmContelonnt.Twelonelont = urt.TimelonlinelonItelonmContelonnt.Twelonelont(
    urt.Twelonelont(
      id = twelonelontItelonm.id,
      displayTypelon = twelonelontDisplayTypelonMarshallelonr(twelonelontItelonm.displayTypelon),
      socialContelonxt = twelonelontItelonm.socialContelonxt.map(socialContelonxtMarshallelonr(_)),
      highlights = twelonelontItelonm.highlights.map(twelonelontHighlightsMarshallelonr(_)),
      innelonrTombstonelonInfo = twelonelontItelonm.innelonrTombstonelonInfo.map(tombstonelonInfoMarshallelonr(_)),
      timelonlinelonsScorelonInfo = twelonelontItelonm.timelonlinelonsScorelonInfo.map(timelonlinelonsScorelonInfoMarshallelonr(_)),
      hasModelonratelondRelonplielons = twelonelontItelonm.hasModelonratelondRelonplielons,
      forwardPivot = twelonelontItelonm.forwardPivot.map(forwardPivotMarshallelonr(_)),
      innelonrForwardPivot = twelonelontItelonm.innelonrForwardPivot.map(forwardPivotMarshallelonr(_)),
      promotelondMelontadata = twelonelontItelonm.promotelondMelontadata.map(promotelondMelontadataMarshallelonr(_)),
      convelonrsationAnnotation =
        twelonelontItelonm.convelonrsationAnnotation.map(convelonrsationAnnotationMarshallelonr(_)),
      contelonxtualTwelonelontRelonf = twelonelontItelonm.contelonxtualTwelonelontRelonf.map(contelonxtualTwelonelontRelonfMarshallelonr(_)),
      prelonrollMelontadata = twelonelontItelonm.prelonrollMelontadata.map(prelonrollMelontadataMarshallelonr(_)),
      relonplyBadgelon = twelonelontItelonm.relonplyBadgelon.map(badgelonMarshallelonr(_)),
      delonstination = twelonelontItelonm.delonstination.map(urlMarshallelonr(_))
    )
  )
}
