packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ModulelonCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.Param

/**
 * Limit thelon numbelonr of modulelon itelonm candidatelons (for 1 or morelon modulelons) from a celonrtain candidatelon
 * sourcelon.
 *
 * For elonxamplelon, if maxModulelonItelonmsParam is 3, and a candidatelonPipelonlinelon relonturnelond 1 modulelon containing 10
 * itelonms in thelon candidatelon pool, thelonn thelonselon modulelon itelonms will belon relonducelond to thelon first 3 modulelon itelonms.
 * Notelon that to updatelon thelon ordelonring of thelon candidatelons, an UpdatelonModulelonItelonmsCandidatelonOrdelonringSelonlelonctor
 * may belon uselond prior to using this selonlelonctor.
 *
 * Anothelonr elonxamplelon, if maxModulelonItelonmsParam is 3, and a candidatelonPipelonlinelon relonturnelond 5 modulelons elonach
 * containing 10 itelonms in thelon candidatelon pool, thelonn thelon modulelon itelonms in elonach of thelon 5 modulelons will belon
 * relonducelond to thelon first 3 modulelon itelonms.
 *
 * @notelon this updatelons thelon modulelon in thelon `relonmainingCandidatelons`
 */
caselon class DropMaxModulelonItelonmCandidatelons(
  candidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr,
  maxModulelonItelonmsParam: Param[Int])
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon = SpeloncificPipelonlinelons(candidatelonPipelonlinelon)

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {

    val maxModulelonItelonmSelonlelonctions = quelonry.params(maxModulelonItelonmsParam)
    asselonrt(maxModulelonItelonmSelonlelonctions > 0, "Max modulelon itelonm selonlelonctions must belon grelonatelonr than zelonro")

    val relonmainingCandidatelonsLimitelond = relonmainingCandidatelons.map {
      caselon modulelon: ModulelonCandidatelonWithDelontails if pipelonlinelonScopelon.contains(modulelon) =>
        // this applielons to all candidatelons in a modulelon, elonvelonn if thelony arelon from a diffelonrelonnt
        // candidatelon sourcelon which can happelonn if itelonms arelon addelond to a modulelon during selonlelonction
        modulelon.copy(candidatelons = DropSelonlelonctor.takelonUntil(maxModulelonItelonmSelonlelonctions, modulelon.candidatelons))
      caselon candidatelon => candidatelon
    }

    SelonlelonctorRelonsult(relonmainingCandidatelons = relonmainingCandidatelonsLimitelond, relonsult = relonsult)
  }
}
