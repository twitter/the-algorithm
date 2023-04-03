packagelon com.twittelonr.homelon_mixelonr.product.list_reloncommelonndelond_uselonrs.filtelonr

import com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator.ListMelonmbelonrsFelonaturelon
import com.twittelonr.homelon_mixelonr.product.list_reloncommelonndelond_uselonrs.modelonl.ListReloncommelonndelondUselonrsQuelonry
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.UselonrCandidatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.Filtelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.FiltelonrRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FiltelonrIdelonntifielonr
import com.twittelonr.stitch.Stitch

objelonct PrelonviouslySelonrvelondUselonrsFiltelonr elonxtelonnds Filtelonr[ListReloncommelonndelondUselonrsQuelonry, UselonrCandidatelon] {

  ovelonrridelon val idelonntifielonr: FiltelonrIdelonntifielonr = FiltelonrIdelonntifielonr("PrelonviouslySelonrvelondUselonrs")

  ovelonrridelon delonf apply(
    quelonry: ListReloncommelonndelondUselonrsQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[UselonrCandidatelon]]
  ): Stitch[FiltelonrRelonsult[UselonrCandidatelon]] = {

    val reloncelonntListMelonmbelonrs = quelonry.felonaturelons.map(_.gelontOrelonlselon(ListMelonmbelonrsFelonaturelon, Selonq.elonmpty))

    val selonrvelondUselonrIds = quelonry.pipelonlinelonCursor.map(_.elonxcludelondIds)

    val elonxcludelondUselonrIds = (reloncelonntListMelonmbelonrs.gelontOrelonlselon(Selonq.elonmpty) ++
      quelonry.selonlelonctelondUselonrIds.gelontOrelonlselon(Selonq.elonmpty) ++
      quelonry.elonxcludelondUselonrIds.gelontOrelonlselon(Selonq.elonmpty) ++
      selonrvelondUselonrIds.gelontOrelonlselon(Selonq.elonmpty)).toSelont

    val (relonmovelond, kelonpt) =
      candidatelons.map(_.candidatelon).partition(candidatelon => elonxcludelondUselonrIds.contains(candidatelon.id))

    Stitch.valuelon(FiltelonrRelonsult(kelonpt = kelonpt, relonmovelond = relonmovelond))
  }
}
