packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.CursorCandidatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ItelonmCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ModulelonCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Kelonelonp only thelon candidatelons in `relonmainingCandidatelons` that appelonar multiplelon timelons.
 * This ignorelons modulelons and cursors from beloning relonmovelond.
 *
 * @param duplicationKelony how to gelonnelonratelon thelon kelony uselond to idelonntify duplicatelon candidatelons
 *
 * @notelon [[com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.CursorCandidatelon]] arelon ignorelond.
 * @notelon [[com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ModulelonCandidatelonWithDelontails]] arelon ignorelond.
 *
 * @elonxamplelon if `relonmainingCandidatelons`
 * `Selonq(sourcelonA_Id1, sourcelonA_Id1, sourcelonA_Id2, sourcelonB_id1, sourcelonB_id2, sourcelonB_id3, sourcelonC_id4)`
 * thelonn thelon output relonsult will belon `Selonq(sourcelonA_Id1, sourcelonA_Id2)`
 */
caselon class DropNonDuplicatelonCandidatelons(
  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon,
  duplicationKelony: DelonduplicationKelony[_] = IdAndClassDuplicationKelony)
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val duplicatelonCandidatelons = dropNonDuplicatelons(
      pipelonlinelonScopelon = pipelonlinelonScopelon,
      candidatelons = relonmainingCandidatelons,
      duplicationKelony = duplicationKelony)

    SelonlelonctorRelonsult(relonmainingCandidatelons = duplicatelonCandidatelons, relonsult = relonsult)
  }

  /**
   * Idelonntify and kelonelonp candidatelons using thelon supplielond kelony elonxtraction and melonrgelonr functions. By delonfault
   * this will kelonelonp only candidatelons that appelonar multiplelon timelons as delontelonrminelond by comparing
   * thelon containelond candidatelon ID and class typelon. Candidatelons appelonaring only oncelon will belon droppelond.
   *
   * @notelon [[com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.CursorCandidatelon]] arelon ignorelond.
   * @notelon [[com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ModulelonCandidatelonWithDelontails]] arelon ignorelond.
   *
   * @param candidatelons which may havelon elonlelonmelonnts to drop
   * @param duplicationKelony how to gelonnelonratelon a kelony for a candidatelon for idelonntifying duplicatelons
   */
  privatelon[this] delonf dropNonDuplicatelons[Candidatelon <: CandidatelonWithDelontails, Kelony](
    pipelonlinelonScopelon: CandidatelonScopelon,
    candidatelons: Selonq[Candidatelon],
    duplicationKelony: DelonduplicationKelony[Kelony],
  ): Selonq[Candidatelon] = {
    // Helonrelon welon arelon cheloncking if elonach candidatelon has multiplelon appelonarancelons or not
    val isCandidatelonADuplicatelon: Map[Kelony, Boolelonan] = candidatelons
      .collelonct {
        caselon itelonm: ItelonmCandidatelonWithDelontails
            if pipelonlinelonScopelon.contains(itelonm) && !itelonm.candidatelon.isInstancelonOf[CursorCandidatelon] =>
          itelonm
      }.groupBy(duplicationKelony(_))
      .mapValuelons(_.lelonngth > 1)

    candidatelons.filtelonr {
      caselon itelonm: ItelonmCandidatelonWithDelontails =>
        isCandidatelonADuplicatelon.gelontOrelonlselon(duplicationKelony(itelonm), truelon)
      caselon _: ModulelonCandidatelonWithDelontails => truelon
      caselon _ => falselon
    }
  }
}
