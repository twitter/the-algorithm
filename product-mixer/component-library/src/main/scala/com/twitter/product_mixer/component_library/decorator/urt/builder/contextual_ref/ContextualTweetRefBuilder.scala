packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.contelonxtual_relonf

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.contelonxtual_relonf.ContelonxtualTwelonelontRelonf
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.contelonxtual_relonf.TwelonelontHydrationContelonxt

caselon class ContelonxtualTwelonelontRelonfBuildelonr[-Candidatelon <: BaselonTwelonelontCandidatelon](
  twelonelontHydrationContelonxt: TwelonelontHydrationContelonxt) {

  delonf apply(candidatelon: Candidatelon): Option[ContelonxtualTwelonelontRelonf] =
    Somelon(ContelonxtualTwelonelontRelonf(candidatelon.id, Somelon(twelonelontHydrationContelonxt)))
}
