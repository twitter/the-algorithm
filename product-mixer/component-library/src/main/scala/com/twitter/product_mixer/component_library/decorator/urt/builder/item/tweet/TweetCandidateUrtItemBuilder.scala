packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.twelonelont

import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.contelonxtual_relonf.ContelonxtualTwelonelontRelonfBuildelonr
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.twelonelont.TwelonelontCandidatelonUrtItelonmBuildelonr.TwelonelontClielonntelonvelonntInfoelonlelonmelonnt
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTwelonelontCandidatelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.IsPinnelondFelonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.CandidatelonUrtelonntryBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.twelonelont.BaselonelonntryIdToRelonplacelonBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.twelonelont.BaselonTimelonlinelonsScorelonInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.twelonelont.BaselonTwelonelontHighlightsBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonClielonntelonvelonntInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonFelonelondbackActionInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonUrlBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.social_contelonxt.BaselonSocialContelonxtBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.convelonrsation_annotation.ConvelonrsationAnnotation
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.forward_pivot.ForwardPivot
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.tombstonelon.TombstonelonInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twelonelont.Twelonelont
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twelonelont.TwelonelontDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twelonelont.TwelonelontItelonm
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Badgelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.PrelonrollMelontadata
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.PromotelondMelontadata
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon objelonct TwelonelontCandidatelonUrtItelonmBuildelonr {
  val TwelonelontClielonntelonvelonntInfoelonlelonmelonnt = "twelonelont"
}

caselon class TwelonelontCandidatelonUrtItelonmBuildelonr[Quelonry <: PipelonlinelonQuelonry, Candidatelon <: BaselonTwelonelontCandidatelon](
  clielonntelonvelonntInfoBuildelonr: BaselonClielonntelonvelonntInfoBuildelonr[Quelonry, Candidatelon],
  displayTypelon: TwelonelontDisplayTypelon = Twelonelont,
  elonntryIdToRelonplacelonBuildelonr: Option[BaselonelonntryIdToRelonplacelonBuildelonr[Quelonry, Candidatelon]] = Nonelon,
  socialContelonxtBuildelonr: Option[BaselonSocialContelonxtBuildelonr[Quelonry, Candidatelon]] = Nonelon,
  highlightsBuildelonr: Option[BaselonTwelonelontHighlightsBuildelonr[Quelonry, Candidatelon]] = Nonelon,
  innelonrTombstonelonInfo: Option[TombstonelonInfo] = Nonelon,
  timelonlinelonsScorelonInfoBuildelonr: Option[BaselonTimelonlinelonsScorelonInfoBuildelonr[Quelonry, Candidatelon]] = Nonelon,
  hasModelonratelondRelonplielons: Option[Boolelonan] = Nonelon,
  forwardPivot: Option[ForwardPivot] = Nonelon,
  innelonrForwardPivot: Option[ForwardPivot] = Nonelon,
  felonelondbackActionInfoBuildelonr: Option[BaselonFelonelondbackActionInfoBuildelonr[Quelonry, Candidatelon]] = Nonelon,
  promotelondMelontadata: Option[PromotelondMelontadata] = Nonelon,
  convelonrsationAnnotation: Option[ConvelonrsationAnnotation] = Nonelon,
  contelonxtualTwelonelontRelonfBuildelonr: Option[ContelonxtualTwelonelontRelonfBuildelonr[Candidatelon]] = Nonelon,
  prelonrollMelontadata: Option[PrelonrollMelontadata] = Nonelon,
  relonplyBadgelon: Option[Badgelon] = Nonelon,
  delonstinationBuildelonr: Option[BaselonUrlBuildelonr[Quelonry, Candidatelon]] = Nonelon)
    elonxtelonnds CandidatelonUrtelonntryBuildelonr[Quelonry, Candidatelon, TwelonelontItelonm] {

  ovelonrridelon delonf apply(
    pipelonlinelonQuelonry: Quelonry,
    twelonelontCandidatelon: Candidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): TwelonelontItelonm = {
    val isPinnelond = candidatelonFelonaturelons.gelontTry(IsPinnelondFelonaturelon).toOption

    TwelonelontItelonm(
      id = twelonelontCandidatelon.id,
      elonntryNamelonspacelon = TwelonelontItelonm.TwelonelontelonntryNamelonspacelon,
      sortIndelonx = Nonelon, // Sort indelonxelons arelon automatically selont in thelon domain marshallelonr phaselon
      clielonntelonvelonntInfo = clielonntelonvelonntInfoBuildelonr(
        pipelonlinelonQuelonry,
        twelonelontCandidatelon,
        candidatelonFelonaturelons,
        Somelon(TwelonelontClielonntelonvelonntInfoelonlelonmelonnt)),
      felonelondbackActionInfo = felonelondbackActionInfoBuildelonr.flatMap(
        _.apply(pipelonlinelonQuelonry, twelonelontCandidatelon, candidatelonFelonaturelons)),
      isPinnelond = isPinnelond,
      elonntryIdToRelonplacelon =
        elonntryIdToRelonplacelonBuildelonr.flatMap(_.apply(pipelonlinelonQuelonry, twelonelontCandidatelon, candidatelonFelonaturelons)),
      socialContelonxt =
        socialContelonxtBuildelonr.flatMap(_.apply(pipelonlinelonQuelonry, twelonelontCandidatelon, candidatelonFelonaturelons)),
      highlights =
        highlightsBuildelonr.flatMap(_.apply(pipelonlinelonQuelonry, twelonelontCandidatelon, candidatelonFelonaturelons)),
      displayTypelon = displayTypelon,
      innelonrTombstonelonInfo = innelonrTombstonelonInfo,
      timelonlinelonsScorelonInfo = timelonlinelonsScorelonInfoBuildelonr
        .flatMap(_.apply(pipelonlinelonQuelonry, twelonelontCandidatelon, candidatelonFelonaturelons)),
      hasModelonratelondRelonplielons = hasModelonratelondRelonplielons,
      forwardPivot = forwardPivot,
      innelonrForwardPivot = innelonrForwardPivot,
      promotelondMelontadata = promotelondMelontadata,
      convelonrsationAnnotation = convelonrsationAnnotation,
      contelonxtualTwelonelontRelonf = contelonxtualTwelonelontRelonfBuildelonr.flatMap(_.apply(twelonelontCandidatelon)),
      prelonrollMelontadata = prelonrollMelontadata,
      relonplyBadgelon = relonplyBadgelon,
      delonstination =
        delonstinationBuildelonr.map(_.apply(pipelonlinelonQuelonry, twelonelontCandidatelon, candidatelonFelonaturelons))
    )
  }
}
