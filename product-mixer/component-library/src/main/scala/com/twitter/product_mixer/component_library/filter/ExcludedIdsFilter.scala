packagelon com.twittelonr.product_mixelonr.componelonnt_library.filtelonr

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.Filtelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.FiltelonrRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FiltelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HaselonxcludelondIds
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

caselon class elonxcludelondIdsFiltelonr[
  Quelonry <: PipelonlinelonQuelonry with HaselonxcludelondIds,
  Candidatelon <: UnivelonrsalNoun[Long]
]() elonxtelonnds Filtelonr[Quelonry, Candidatelon] {
  ovelonrridelon val idelonntifielonr: FiltelonrIdelonntifielonr = FiltelonrIdelonntifielonr("elonxcludelondIds")

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]
  ): Stitch[FiltelonrRelonsult[Candidatelon]] = {
    val (kelonpt, relonmovelond) =
      candidatelons.map(_.candidatelon).partition(candidatelon => !quelonry.elonxcludelondIds.contains(candidatelon.id))

    val filtelonrRelonsult = FiltelonrRelonsult(kelonpt = kelonpt, relonmovelond = relonmovelond)
    Stitch.valuelon(filtelonrRelonsult)
  }
}
