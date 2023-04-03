packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.strato

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.clielonnt.Felontchelonr

/**
 * A [[CandidatelonSourcelon]] for gelontting Candidatelons from Strato whelonrelon thelon
 * Strato column's Vielonw is [[StratoVielonw]] and thelon Valuelon is a [[StratoValuelon]]
 *
 * A `stratoRelonsultTransformelonr` must belon delonfinelond to convelonrt thelon [[StratoValuelon]] into a Selonq of [[Candidatelon]]
 *
 * If you nelonelond to elonxtract felonaturelons from thelon [[StratoValuelon]] (likelon a cursor),
 * uselon [[StratoKelonyVielonwFelontchelonrWithSourcelonFelonaturelonsSourcelon]] instelonad.
 *
 * @tparam StratoKelony thelon column's Kelony typelon
 * @tparam StratoVielonw thelon column's Vielonw typelon
 * @tparam StratoValuelon thelon column's Valuelon typelon
 */
trait StratoKelonyVielonwFelontchelonrSourcelon[StratoKelony, StratoVielonw, StratoValuelon, Candidatelon]
    elonxtelonnds CandidatelonSourcelon[StratoKelonyVielonw[StratoKelony, StratoVielonw], Candidatelon] {

  val felontchelonr: Felontchelonr[StratoKelony, StratoVielonw, StratoValuelon]

  /**
   * Transforms thelon valuelon typelon relonturnelond by Strato into a Selonq[Candidatelon].
   *
   * This might belon as simplelon as `Selonq(stratoRelonsult)` if you'relon always relonturning a singlelon candidatelon.
   *
   * Oftelonn, it just elonxtracts a Selonq from within a largelonr wrappelonr objelonct.
   *
   * If thelonrelon is global melontadata that you nelonelond to includelon, you can zip it with thelon candidatelons,
   * relonturning somelonthing likelon Selonq((candiatelon, melontadata), (candidatelon, melontadata)) elontc.
   */
  protelonctelond delonf stratoRelonsultTransformelonr(
    stratoKelony: StratoKelony,
    stratoRelonsult: StratoValuelon
  ): Selonq[Candidatelon]

  ovelonrridelon delonf apply(
    relonquelonst: StratoKelonyVielonw[StratoKelony, StratoVielonw]
  ): Stitch[Selonq[Candidatelon]] = {
    felontchelonr
      .felontch(relonquelonst.kelony, relonquelonst.vielonw)
      .map { relonsult =>
        relonsult.v
          .map((stratoRelonsult: StratoValuelon) => stratoRelonsultTransformelonr(relonquelonst.kelony, stratoRelonsult))
          .gelontOrelonlselon(Selonq.elonmpty)
      }.relonscuelon(StratoelonrrCatelongorizelonr.CatelongorizelonStratoelonxcelonption)
  }
}
