packagelon com.twittelonr.homelon_mixelonr.product.list_reloncommelonndelond_uselonrs.filtelonr

import com.twittelonr.homelon_mixelonr.product.list_reloncommelonndelond_uselonrs.modelonl.ListFelonaturelons.ScorelonFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.UselonrCandidatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.Filtelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.FiltelonrRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FiltelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

objelonct DropMaxCandidatelonsByScorelonFiltelonr elonxtelonnds Filtelonr[PipelonlinelonQuelonry, UselonrCandidatelon] {

  ovelonrridelon val idelonntifielonr: FiltelonrIdelonntifielonr = FiltelonrIdelonntifielonr("DropMaxCandidatelonsByScorelon")

  privatelon val MaxSimilarUselonrCandidatelons = 1000

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[UselonrCandidatelon]]
  ): Stitch[FiltelonrRelonsult[UselonrCandidatelon]] = {

    val sortelondCandidatelons = candidatelons.sortBy(-_.felonaturelons.gelontOrelonlselon(ScorelonFelonaturelon, 0.0))

    val (kelonpt, relonmovelond) = sortelondCandidatelons.map(_.candidatelon).splitAt(MaxSimilarUselonrCandidatelons)

    Stitch.valuelon(FiltelonrRelonsult(kelonpt, relonmovelond))
  }
}
