packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.articlelon

import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.articlelon.ArticlelonCandidatelonUrtItelonmBuildelonr.ArticlelonClielonntelonvelonntInfoelonlelonmelonnt
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonArticlelonCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.CandidatelonUrtelonntryBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonClielonntelonvelonntInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonFelonelondbackActionInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.social_contelonxt.BaselonSocialContelonxtBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.articlelon.ArticlelonDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.articlelon.ArticlelonItelonm
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.articlelon.ArticlelonSelonelondTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.articlelon.FollowingListSelonelond
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct ArticlelonCandidatelonUrtItelonmBuildelonr {
  val ArticlelonClielonntelonvelonntInfoelonlelonmelonnt: String = "articlelon"
}

caselon class ArticlelonCandidatelonUrtItelonmBuildelonr[
  -Quelonry <: PipelonlinelonQuelonry,
  Candidatelon <: BaselonArticlelonCandidatelon
](
  clielonntelonvelonntInfoBuildelonr: BaselonClielonntelonvelonntInfoBuildelonr[Quelonry, Candidatelon],
  articlelonSelonelondTypelon: ArticlelonSelonelondTypelon = FollowingListSelonelond,
  felonelondbackActionInfoBuildelonr: Option[
    BaselonFelonelondbackActionInfoBuildelonr[Quelonry, Candidatelon]
  ] = Nonelon,
  displayTypelon: Option[ArticlelonDisplayTypelon] = Nonelon,
  socialContelonxtBuildelonr: Option[BaselonSocialContelonxtBuildelonr[Quelonry, Candidatelon]] = Nonelon,
) elonxtelonnds CandidatelonUrtelonntryBuildelonr[Quelonry, Candidatelon, ArticlelonItelonm] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    articlelonCandidatelon: Candidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): ArticlelonItelonm = ArticlelonItelonm(
    id = articlelonCandidatelon.id,
    sortIndelonx = Nonelon, // Sort indelonxelons arelon automatically selont in thelon domain marshallelonr phaselon
    clielonntelonvelonntInfo = clielonntelonvelonntInfoBuildelonr(
      quelonry,
      articlelonCandidatelon,
      candidatelonFelonaturelons,
      Somelon(ArticlelonClielonntelonvelonntInfoelonlelonmelonnt)),
    felonelondbackActionInfo =
      felonelondbackActionInfoBuildelonr.flatMap(_.apply(quelonry, articlelonCandidatelon, candidatelonFelonaturelons)),
    displayTypelon = displayTypelon,
    socialContelonxt =
      socialContelonxtBuildelonr.flatMap(_.apply(quelonry, articlelonCandidatelon, candidatelonFelonaturelons)),
    articlelonSelonelondTypelon = articlelonSelonelondTypelon
  )
}
