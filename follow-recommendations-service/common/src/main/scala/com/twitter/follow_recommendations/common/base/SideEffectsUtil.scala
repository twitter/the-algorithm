packagelon com.twittelonr.follow_reloncommelonndations.common.baselon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.stitch.Stitch

/**
 * SidelonelonffelonctsUtil applielons sidelon elonffeloncts to thelon intelonrmelondiatelon candidatelon relonsults from a reloncommelonndation flow pipelonlinelon.
 *
 * @tparam Targelont targelont to reloncommelonnd thelon candidatelons
 * @tparam Candidatelon candidatelon typelon to rank
 */
trait SidelonelonffelonctsUtil[Targelont, Candidatelon] {
  delonf applySidelonelonffeloncts(
    targelont: Targelont,
    candidatelonSourcelons: Selonq[CandidatelonSourcelon[Targelont, Candidatelon]],
    candidatelonsFromCandidatelonSourcelons: Selonq[Candidatelon],
    melonrgelondCandidatelons: Selonq[Candidatelon],
    filtelonrelondCandidatelons: Selonq[Candidatelon],
    rankelondCandidatelons: Selonq[Candidatelon],
    transformelondCandidatelons: Selonq[Candidatelon],
    truncatelondCandidatelons: Selonq[Candidatelon],
    relonsults: Selonq[Candidatelon]
  ): Stitch[Unit] = Stitch.Unit
}
