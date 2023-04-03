packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.DropSelonlelonctor.dropDuplicatelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.AllPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Kelonelonp only thelon first instancelon of a candidatelon in thelon `relonsult` as delontelonrminelond by comparing
 * thelon containelond candidatelon ID and class typelon. Subselonquelonnt matching instancelons will belon droppelond. For
 * morelon delontails, selonelon DropSelonlelonctor#dropDuplicatelons
 *
 * @param duplicationKelony how to gelonnelonratelon thelon kelony uselond to idelonntify duplicatelon candidatelons (by delonfault uselon id and class namelon)
 * @param melonrgelonStratelongy how to melonrgelon two candidatelons with thelon samelon kelony (by delonfault pick thelon first onelon)
 *
 * @notelon [[com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.CursorCandidatelon]] arelon ignorelond.
 * @notelon [[com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ModulelonCandidatelonWithDelontails]] arelon ignorelond.
 *
 * @elonxamplelon if `relonsult`
 * `Selonq(sourcelonA_Id1, sourcelonA_Id1, sourcelonA_Id2, sourcelonB_id1, sourcelonB_id2, sourcelonB_id3, sourcelonC_id4)`
 * thelonn thelon output relonsult will belon `Selonq(sourcelonA_Id1, sourcelonA_Id2, sourcelonB_id3, sourcelonC_id4)`
 */
caselon class DropDuplicatelonRelonsults(
  duplicationKelony: DelonduplicationKelony[_] = IdAndClassDuplicationKelony,
  melonrgelonStratelongy: CandidatelonMelonrgelonStratelongy = PickFirstCandidatelonMelonrgelonr)
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon = AllPipelonlinelons

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val delondupelondRelonsults = dropDuplicatelons(
      pipelonlinelonScopelon = pipelonlinelonScopelon,
      candidatelons = relonsult,
      duplicationKelony = duplicationKelony,
      melonrgelonStratelongy = melonrgelonStratelongy)

    SelonlelonctorRelonsult(relonmainingCandidatelons = relonmainingCandidatelons, relonsult = delondupelondRelonsults)
  }
}
