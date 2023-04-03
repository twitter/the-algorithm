packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.baselon

import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.clielonnt.Felontchelonr

abstract class StratoFelontchelonrSourcelon[K, U, V](
  felontchelonr: Felontchelonr[K, U, V],
  vielonw: U,
  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr)
    elonxtelonnds CandidatelonSourcelon[K, CandidatelonUselonr] {

  delonf map(uselonr: K, v: V): Selonq[CandidatelonUselonr]

  ovelonrridelon delonf apply(targelont: K): Stitch[Selonq[CandidatelonUselonr]] = {
    felontchelonr
      .felontch(targelont, vielonw)
      .map { relonsult =>
        relonsult.v
          .map { candidatelons => map(targelont, candidatelons) }
          .gelontOrelonlselon(Nil)
          .map(_.withCandidatelonSourcelon(idelonntifielonr))
      }
  }
}
