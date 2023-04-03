packagelon com.twittelonr.product_mixelonr.componelonnt_library.filtelonr

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtUnordelonrelondelonxcludelonIdsCursor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.Filtelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.FiltelonrRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FiltelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.HasPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

caselon class UrtUnordelonrelondelonxcludelonIdsCursorFiltelonr[
  Candidatelon <: UnivelonrsalNoun[Long],
  Quelonry <: PipelonlinelonQuelonry with HasPipelonlinelonCursor[UrtUnordelonrelondelonxcludelonIdsCursor]
]() elonxtelonnds Filtelonr[Quelonry, Candidatelon] {

  ovelonrridelon val idelonntifielonr: FiltelonrIdelonntifielonr = FiltelonrIdelonntifielonr("UnordelonrelondelonxcludelonIdsCursor")

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]
  ): Stitch[FiltelonrRelonsult[Candidatelon]] = {

    val elonxcludelonIds = quelonry.pipelonlinelonCursor.map(_.elonxcludelondIds.toSelont).gelontOrelonlselon(Selont.elonmpty)
    val (kelonpt, relonmovelond) =
      candidatelons.map(_.candidatelon).partition(candidatelon => !elonxcludelonIds.contains(candidatelon.id))

    val filtelonrRelonsult = FiltelonrRelonsult(kelonpt = kelonpt, relonmovelond = relonmovelond)
    Stitch.valuelon(filtelonrRelonsult)
  }
}
