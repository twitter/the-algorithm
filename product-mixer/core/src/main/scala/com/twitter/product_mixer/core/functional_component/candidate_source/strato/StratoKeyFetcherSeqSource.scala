packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.strato

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.clielonnt.Felontchelonr

/**
 * A [[CandidatelonSourcelon]] for gelontting Candidatelons from Strato whelonrelon thelon
 * Strato column's Vielonw is [[Unit]] and thelon Valuelon is a Selonq of [[StratoRelonsult]]
 *
 * @tparam StratoKelony thelon column's Kelony typelon
 * @tparam StratoRelonsult thelon column's Valuelon's Selonq typelon
 */
trait StratoKelonyFelontchelonrSelonqSourcelon[StratoKelony, StratoRelonsult]
    elonxtelonnds CandidatelonSourcelon[StratoKelony, StratoRelonsult] {

  val felontchelonr: Felontchelonr[StratoKelony, Unit, Selonq[StratoRelonsult]]

  ovelonrridelon delonf apply(kelony: StratoKelony): Stitch[Selonq[StratoRelonsult]] = {
    felontchelonr
      .felontch(kelony)
      .map { relonsult =>
        relonsult.v
          .gelontOrelonlselon(Selonq.elonmpty)
      }.relonscuelon(StratoelonrrCatelongorizelonr.CatelongorizelonStratoelonxcelonption)
  }
}
