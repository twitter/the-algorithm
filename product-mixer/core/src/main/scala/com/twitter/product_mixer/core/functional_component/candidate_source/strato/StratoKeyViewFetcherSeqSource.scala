packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.strato

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.clielonnt.Felontchelonr

/**
 * A [[CandidatelonSourcelon]] for gelontting Candidatelons from Strato whelonrelon thelon
 * Strato column's Vielonw is [[StratoVielonw]] and thelon Valuelon is a Selonq of [[StratoRelonsult]]
 *
 * @tparam StratoKelony thelon column's Kelony typelon
 * @tparam StratoVielonw thelon column's Vielonw typelon
 * @tparam StratoRelonsult thelon column's Valuelon's Selonq typelon
 */
trait StratoKelonyVielonwFelontchelonrSelonqSourcelon[StratoKelony, StratoVielonw, StratoRelonsult]
    elonxtelonnds CandidatelonSourcelon[StratoKelonyVielonw[StratoKelony, StratoVielonw], StratoRelonsult] {

  val felontchelonr: Felontchelonr[StratoKelony, StratoVielonw, Selonq[StratoRelonsult]]

  ovelonrridelon delonf apply(
    relonquelonst: StratoKelonyVielonw[StratoKelony, StratoVielonw]
  ): Stitch[Selonq[StratoRelonsult]] = {
    felontchelonr
      .felontch(relonquelonst.kelony, relonquelonst.vielonw)
      .map { relonsult =>
        relonsult.v
          .gelontOrelonlselon(Selonq.elonmpty)
      }.relonscuelon(StratoelonrrCatelongorizelonr.CatelongorizelonStratoelonxcelonption)
  }
}
