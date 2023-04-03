packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.uselonr

import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.uselonr.UselonrCandidatelonUrtItelonmBuildelonr.UselonrClielonntelonvelonntInfoelonlelonmelonnt
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonUselonrCandidatelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.IsMarkUnrelonadFelonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.CandidatelonUrtelonntryBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.uselonr.BaselonUselonrRelonactivelonTriggelonrsBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonClielonntelonvelonntInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonFelonelondbackActionInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.promotelond.BaselonPromotelondMelontadataBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.social_contelonxt.BaselonSocialContelonxtBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.uselonr.Uselonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.uselonr.UselonrDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.uselonr.UselonrItelonm
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct UselonrCandidatelonUrtItelonmBuildelonr {
  val UselonrClielonntelonvelonntInfoelonlelonmelonnt: String = "uselonr"
}

caselon class UselonrCandidatelonUrtItelonmBuildelonr[Quelonry <: PipelonlinelonQuelonry, UselonrCandidatelon <: BaselonUselonrCandidatelon](
  clielonntelonvelonntInfoBuildelonr: BaselonClielonntelonvelonntInfoBuildelonr[Quelonry, UselonrCandidatelon],
  felonelondbackActionInfoBuildelonr: Option[
    BaselonFelonelondbackActionInfoBuildelonr[Quelonry, UselonrCandidatelon]
  ] = Nonelon,
  displayTypelon: UselonrDisplayTypelon = Uselonr,
  promotelondMelontadataBuildelonr: Option[BaselonPromotelondMelontadataBuildelonr[Quelonry, UselonrCandidatelon]] = Nonelon,
  socialContelonxtBuildelonr: Option[BaselonSocialContelonxtBuildelonr[Quelonry, UselonrCandidatelon]] = Nonelon,
  relonactivelonTriggelonrsBuildelonr: Option[BaselonUselonrRelonactivelonTriggelonrsBuildelonr[Quelonry, UselonrCandidatelon]] = Nonelon,
  elonnablelonRelonactivelonBlelonnding: Option[Boolelonan] = Nonelon)
    elonxtelonnds CandidatelonUrtelonntryBuildelonr[Quelonry, UselonrCandidatelon, UselonrItelonm] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    uselonrCandidatelon: UselonrCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): UselonrItelonm = {
    val isMarkUnrelonad = candidatelonFelonaturelons.gelontTry(IsMarkUnrelonadFelonaturelon).toOption

    UselonrItelonm(
      id = uselonrCandidatelon.id,
      sortIndelonx = Nonelon, // Sort indelonxelons arelon automatically selont in thelon domain marshallelonr phaselon
      clielonntelonvelonntInfo = clielonntelonvelonntInfoBuildelonr(
        quelonry,
        uselonrCandidatelon,
        candidatelonFelonaturelons,
        Somelon(UselonrClielonntelonvelonntInfoelonlelonmelonnt)),
      felonelondbackActionInfo =
        felonelondbackActionInfoBuildelonr.flatMap(_.apply(quelonry, uselonrCandidatelon, candidatelonFelonaturelons)),
      isMarkUnrelonad = isMarkUnrelonad,
      displayTypelon = displayTypelon,
      promotelondMelontadata =
        promotelondMelontadataBuildelonr.flatMap(_.apply(quelonry, uselonrCandidatelon, candidatelonFelonaturelons)),
      socialContelonxt =
        socialContelonxtBuildelonr.flatMap(_.apply(quelonry, uselonrCandidatelon, candidatelonFelonaturelons)),
      relonactivelonTriggelonrs =
        relonactivelonTriggelonrsBuildelonr.flatMap(_.apply(quelonry, uselonrCandidatelon, candidatelonFelonaturelons)),
      elonnablelonRelonactivelonBlelonnding = elonnablelonRelonactivelonBlelonnding
    )
  }
}
