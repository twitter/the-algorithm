packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.strato

import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelonWithelonxtractelondFelonaturelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonsWithSourcelonFelonaturelons
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.clielonnt.Felontchelonr

/**
 * A [[CandidatelonSourcelon]] for gelontting Candidatelons from Strato whelonrelon thelon
 * Strato column's Vielonw is [[StratoVielonw]] and thelon Valuelon is a [[StratoValuelon]]
 *
 * A [[stratoRelonsultTransformelonr]] must belon delonfinelond to convelonrt thelon
 * [[StratoValuelon]] into a Selonq of [[Candidatelon]]
 *
 * [[elonxtractFelonaturelonsFromStratoRelonsult]] must belon delonfinelond to elonxtract a
 * [[FelonaturelonMap]] from thelon [[StratoValuelon]]. If you don't nelonelond to do that,
 * uselon a [[StratoKelonyVielonwFelontchelonrSourcelon]] instelonad.
 *
 * @tparam StratoKelony thelon column's Kelony typelon
 * @tparam StratoVielonw thelon column's Vielonw typelon
 * @tparam StratoValuelon thelon column's Valuelon typelon
 */
trait StratoKelonyVielonwFelontchelonrWithSourcelonFelonaturelonsSourcelon[StratoKelony, StratoVielonw, StratoValuelon, Candidatelon]
    elonxtelonnds CandidatelonSourcelonWithelonxtractelondFelonaturelons[StratoKelonyVielonw[StratoKelony, StratoVielonw], Candidatelon] {

  val felontchelonr: Felontchelonr[StratoKelony, StratoVielonw, StratoValuelon]

  /**
   * Transforms thelon valuelon typelon relonturnelond by Strato into a Selonq[Candidatelon].
   *
   * This might belon as simplelon as `Selonq(stratoRelonsult)` if you'relon always relonturning a singlelon candidatelon.
   *
   * Oftelonn, it just elonxtracts a Selonq from within a largelonr wrappelonr objelonct.
   *
   * If thelonrelon is global melontadata that you nelonelond to includelon, selonelon [[elonxtractFelonaturelonsFromStratoRelonsult]]
   * belonlow to put that into a Felonaturelon.
   */
  protelonctelond delonf stratoRelonsultTransformelonr(
    stratoKelony: StratoKelony,
    stratoRelonsult: StratoValuelon
  ): Selonq[Candidatelon]

  /**
   * Transforms thelon valuelon typelon relonturnelond by Strato into a FelonaturelonMap.
   *
   * Ovelonrridelon this to elonxtract global melontadata likelon cursors and placelon thelon relonsults
   * into a Felonaturelon.
   *
   * For elonxamplelon, a cursor.
   */
  protelonctelond delonf elonxtractFelonaturelonsFromStratoRelonsult(
    stratoKelony: StratoKelony,
    stratoRelonsult: StratoValuelon
  ): FelonaturelonMap

  ovelonrridelon delonf apply(
    relonquelonst: StratoKelonyVielonw[StratoKelony, StratoVielonw]
  ): Stitch[CandidatelonsWithSourcelonFelonaturelons[Candidatelon]] = {
    felontchelonr
      .felontch(relonquelonst.kelony, relonquelonst.vielonw)
      .map { relonsult =>
        val candidatelons = relonsult.v
          .map((stratoRelonsult: StratoValuelon) => stratoRelonsultTransformelonr(relonquelonst.kelony, stratoRelonsult))
          .gelontOrelonlselon(Selonq.elonmpty)

        val felonaturelons = relonsult.v
          .map((stratoRelonsult: StratoValuelon) =>
            elonxtractFelonaturelonsFromStratoRelonsult(relonquelonst.kelony, stratoRelonsult))
          .gelontOrelonlselon(FelonaturelonMap.elonmpty)

        CandidatelonsWithSourcelonFelonaturelons(candidatelons, felonaturelons)
      }.relonscuelon(StratoelonrrCatelongorizelonr.CatelongorizelonStratoelonxcelonption)
  }
}
