packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.alelonrt

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.ShowAlelonrtCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.ShowAlelonrtColorConfiguration
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

trait BaselonShowAlelonrtColorConfigurationBuildelonr[-Quelonry <: PipelonlinelonQuelonry] {

  delonf apply(
    quelonry: Quelonry,
    candidatelon: ShowAlelonrtCandidatelon,
    felonaturelons: FelonaturelonMap
  ): ShowAlelonrtColorConfiguration
}
