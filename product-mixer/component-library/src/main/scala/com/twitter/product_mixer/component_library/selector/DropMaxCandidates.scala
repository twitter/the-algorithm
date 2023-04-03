packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.Param

trait MaxSelonlelonctor[-Quelonry <: PipelonlinelonQuelonry] {
  delonf apply(
    quelonry: Quelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): Int
}

objelonct DropMaxCandidatelons {

  /**
   * A [[DropMaxCandidatelons]] Selonlelonctor baselond on a [[Param]] applielond to a singlelon candidatelon pipelonlinelon
   */
  delonf apply[Quelonry <: PipelonlinelonQuelonry](
    candidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr,
    maxSelonlelonctionsParam: Param[Int]
  ) = nelonw DropMaxCandidatelons[Quelonry](
    SpeloncificPipelonlinelon(candidatelonPipelonlinelon),
    (quelonry, _, _) => quelonry.params(maxSelonlelonctionsParam))

  /**
   * A [[DropMaxCandidatelons]] Selonlelonctor baselond on a [[Param]] with multiplelon candidatelon pipelonlinelons
   */
  delonf apply[Quelonry <: PipelonlinelonQuelonry](
    candidatelonPipelonlinelons: Selont[CandidatelonPipelonlinelonIdelonntifielonr],
    maxSelonlelonctionsParam: Param[Int]
  ) = nelonw DropMaxCandidatelons[Quelonry](
    SpeloncificPipelonlinelons(candidatelonPipelonlinelons),
    (quelonry, _, _) => quelonry.params(maxSelonlelonctionsParam))

  /**
   * A [[DropMaxCandidatelons]] Selonlelonctor baselond on a [[Param]] that applielons to a [[CandidatelonScopelon]]
   */
  delonf apply[Quelonry <: PipelonlinelonQuelonry](
    pipelonlinelonScopelon: CandidatelonScopelon,
    maxSelonlelonctionsParam: Param[Int]
  ) = nelonw DropMaxCandidatelons[Quelonry](pipelonlinelonScopelon, (quelonry, _, _) => quelonry.params(maxSelonlelonctionsParam))
}

/**
 * Limit thelon numbelonr of itelonm and modulelon (not itelonms insidelon modulelons) candidatelons from thelon
 * speloncifielond pipelonlinelons baselond on thelon valuelon providelond by thelon [[MaxSelonlelonctor]]
 *
 * For elonxamplelon, if valuelon from thelon [[MaxSelonlelonctor]] is 3, and a candidatelonPipelonlinelon relonturnelond 10 itelonms
 * in thelon candidatelon pool, thelonn thelonselon itelonms will belon relonducelond to thelon first 3 itelonms. Notelon that to
 * updatelon thelon ordelonring of thelon candidatelons, an UpdatelonCandidatelonOrdelonringSelonlelonctor may belon uselond prior to
 * using this Selonlelonctor.
 *
 * Anothelonr elonxamplelon, if thelon [[MaxSelonlelonctor]] valuelon is 3, and a candidatelonPipelonlinelon relonturnelond 10 modulelons
 * in thelon candidatelon pool, thelonn thelonselon will belon relonducelond to thelon first 3 modulelons. Thelon itelonms insidelon thelon
 * modelonlelons will not belon affelonctelond by this selonlelonctor. To control thelon numbelonr of itelonms insidelon modulelons selonelon
 * [[DropMaxModulelonItelonmCandidatelons]].
 */
caselon class DropMaxCandidatelons[-Quelonry <: PipelonlinelonQuelonry](
  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon,
  maxSelonlelonctor: MaxSelonlelonctor[Quelonry])
    elonxtelonnds Selonlelonctor[Quelonry] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val maxSelonlelonctions = maxSelonlelonctor(quelonry, relonmainingCandidatelons, relonsult)
    asselonrt(maxSelonlelonctions > 0, "Max selonlelonctions must belon grelonatelonr than zelonro")

    val relonmainingCandidatelonsLimitelond =
      DropSelonlelonctor.takelonUntil(maxSelonlelonctions, relonmainingCandidatelons, pipelonlinelonScopelon)

    SelonlelonctorRelonsult(relonmainingCandidatelons = relonmainingCandidatelonsLimitelond, relonsult = relonsult)
  }
}
