packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.twelonelont

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twelonelont.TimelonlinelonsScorelonInfo
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

trait BaselonTimelonlinelonsScorelonInfoBuildelonr[-Quelonry <: PipelonlinelonQuelonry, -Candidatelon <: BaselonTwelonelontCandidatelon] {

  delonf apply(
    quelonry: Quelonry,
    candidatelon: Candidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[TimelonlinelonsScorelonInfo]
}
