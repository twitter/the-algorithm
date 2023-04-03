packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.alelonrt

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.ShowAlelonrtCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.alelonrt.BaselonShowAlelonrtDisplayLocationBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.ShowAlelonrtDisplayLocation
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon class StaticShowAlelonrtDisplayLocationBuildelonr[-Quelonry <: PipelonlinelonQuelonry](
  location: ShowAlelonrtDisplayLocation)
    elonxtelonnds BaselonShowAlelonrtDisplayLocationBuildelonr[Quelonry] {

  delonf apply(
    quelonry: Quelonry,
    candidatelon: ShowAlelonrtCandidatelon,
    felonaturelons: FelonaturelonMap
  ): ShowAlelonrtDisplayLocation = location
}
