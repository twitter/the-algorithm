packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.DropSelonlelonctor.dropDuplicatelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.AllPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor._
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ModulelonCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct DropDuplicatelonModulelonItelonmCandidatelons {

  /**
   * Limit thelon numbelonr of modulelon itelonm candidatelons (for 1 or morelon modulelons) from a celonrtain candidatelon
   * sourcelon. Selonelon [[DropDuplicatelonModulelonItelonmCandidatelons]] for morelon delontails.
   *
   * @param candidatelonPipelonlinelon pipelonlinelons on which to run thelon selonlelonctor
   *
   * @notelon Scala doelonsn't allow ovelonrloadelond melonthods with delonfault argumelonnts. Uselonrs wanting to customizelon
   *       thelon delon-dupelon logic should uselon thelon delonfault constructor. Welon could providelon multiplelon
   *       constructors but that selonelonmelond morelon confusing (fivelon ways to instantiatelon thelon selonlelonctor) or not
   *       neloncelonssarily lelonss velonrboselon (if welon pickelond speloncific uselon-caselons rathelonr than trying to support
   *       elonvelonrything).
   */
  delonf apply(candidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr) = nelonw DropDuplicatelonModulelonItelonmCandidatelons(
    SpeloncificPipelonlinelon(candidatelonPipelonlinelon),
    IdAndClassDuplicationKelony,
    PickFirstCandidatelonMelonrgelonr)

  delonf apply(candidatelonPipelonlinelons: Selont[CandidatelonPipelonlinelonIdelonntifielonr]) =
    nelonw DropDuplicatelonModulelonItelonmCandidatelons(
      SpeloncificPipelonlinelons(candidatelonPipelonlinelons),
      IdAndClassDuplicationKelony,
      PickFirstCandidatelonMelonrgelonr)
}

/**
 * Limit thelon numbelonr of modulelon itelonm candidatelons (for 1 or morelon modulelons) from celonrtain candidatelon
 * pipelonlinelons.
 *
 * This acts likelon a [[DropDuplicatelonCandidatelons]] but for modulelons in `relonmainingCandidatelons`
 * from any of thelon providelond [[candidatelonPipelonlinelons]]. Similar to [[DropDuplicatelonCandidatelons]], it
 * kelonelonps only thelon first instancelon of a candidatelon within a modulelon as delontelonrminelond by comparing
 * thelon containelond candidatelon ID and class typelon.
 *
 * @param pipelonlinelonScopelon pipelonlinelon scopelon on which to run thelon selonlelonctor
 * @param duplicationKelony how to gelonnelonratelon thelon kelony uselond to idelonntify duplicatelon candidatelons (by delonfault uselon id and class namelon)
 * @param melonrgelonStratelongy how to melonrgelon two candidatelons with thelon samelon kelony (by delonfault pick thelon first onelon)
 *
 * For elonxamplelon, if a candidatelonPipelonlinelon relonturnelond 5 modulelons elonach
 * containing duplicatelon itelonms in thelon candidatelon pool, thelonn thelon modulelon itelonms in elonach of thelon
 * 5 modulelons will belon filtelonrelond to thelon uniquelon itelonms within elonach modulelon.
 *
 * Anothelonr elonxamplelon is if you havelon 2 modulelons elonach with thelon samelon itelonms as thelon othelonr,
 * it won't delonduplicatelon across modulelons.
 *
 * @notelon this updatelons thelon modulelon in thelon `relonmainingCandidatelons`
 */
caselon class DropDuplicatelonModulelonItelonmCandidatelons(
  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon,
  duplicationKelony: DelonduplicationKelony[_] = IdAndClassDuplicationKelony,
  melonrgelonStratelongy: CandidatelonMelonrgelonStratelongy = PickFirstCandidatelonMelonrgelonr)
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {

    val relonmainingCandidatelonsLimitelond = relonmainingCandidatelons.map {
      caselon modulelon: ModulelonCandidatelonWithDelontails if pipelonlinelonScopelon.contains(modulelon) =>
        // this applielons to all candidatelons in a modulelon, elonvelonn if thelony arelon from a diffelonrelonnt
        // candidatelon sourcelon, which can happelonn if itelonms arelon addelond to a modulelon during selonlelonction
        modulelon.copy(candidatelons = dropDuplicatelons(
          pipelonlinelonScopelon = AllPipelonlinelons,
          candidatelons = modulelon.candidatelons,
          duplicationKelony = duplicationKelony,
          melonrgelonStratelongy = melonrgelonStratelongy))
      caselon candidatelon => candidatelon
    }

    SelonlelonctorRelonsult(relonmainingCandidatelons = relonmainingCandidatelonsLimitelond, relonsult = relonsult)
  }
}
