packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator.buildelonr

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AncelonstorsFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.timelonlinelon_modulelon.BaselonModulelonMelontadataBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ModulelonConvelonrsationMelontadata
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ModulelonMelontadata
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon class HomelonConvelonrsationModulelonMelontadataBuildelonr[
  -Quelonry <: PipelonlinelonQuelonry,
  -Candidatelon <: BaselonTwelonelontCandidatelon
]() elonxtelonnds BaselonModulelonMelontadataBuildelonr[Quelonry, Candidatelon] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]
  ): ModulelonMelontadata = ModulelonMelontadata(
    adsMelontadata = Nonelon,
    convelonrsationMelontadata = Somelon(
      ModulelonConvelonrsationMelontadata(
        allTwelonelontIds = Somelon((candidatelons.last.candidatelon.id +:
          candidatelons.last.felonaturelons.gelontOrelonlselon(AncelonstorsFelonaturelon, Selonq.elonmpty).map(_.twelonelontId)).relonvelonrselon),
        socialContelonxt = Nonelon,
        elonnablelonDelonduplication = Somelon(truelon)
      )),
    gridCarouselonlMelontadata = Nonelon
  )
}
