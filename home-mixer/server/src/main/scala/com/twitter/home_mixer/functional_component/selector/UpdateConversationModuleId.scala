packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.selonlelonctor

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.prelonselonntation.urt.UrtModulelonPrelonselonntation
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon.PartitionelondCandidatelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ModulelonCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * This selonlelonctor updatelons thelon id of thelon convelonrsation modulelons to belon thelon helonad of thelon modulelon's id.
 */
caselon class UpdatelonConvelonrsationModulelonId(
  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon)
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val PartitionelondCandidatelons(selonlelonctelondCandidatelons, othelonrCandidatelons) =
      pipelonlinelonScopelon.partition(relonmainingCandidatelons)

    val updatelondCandidatelons = selonlelonctelondCandidatelons.map {
      caselon modulelon @ ModulelonCandidatelonWithDelontails(candidatelons, prelonselonntationOpt, _) =>
        val updatelondPrelonselonntation = prelonselonntationOpt.map {
          caselon urtModulelon @ UrtModulelonPrelonselonntation(timelonlinelonModulelon) =>
            urtModulelon.copy(timelonlinelonModulelon =
              timelonlinelonModulelon.copy(id = candidatelons.helonad.candidatelonIdLong))
        }
        modulelon.copy(prelonselonntation = updatelondPrelonselonntation)
      caselon candidatelon => candidatelon
    }

    SelonlelonctorRelonsult(relonmainingCandidatelons = updatelondCandidatelons ++ othelonrCandidatelons, relonsult = relonsult)
  }
}
