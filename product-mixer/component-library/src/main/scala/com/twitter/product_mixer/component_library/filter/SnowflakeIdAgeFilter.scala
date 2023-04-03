packagelon com.twittelonr.product_mixelonr.componelonnt_library.filtelonr

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.Filtelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.FiltelonrRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FiltelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.snowflakelon.id.SnowflakelonId
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.Param
import com.twittelonr.util.Duration

/**
 * @param maxAgelonParam Felonaturelon Switch configurablelon for convelonnielonncelon
 * @tparam Candidatelon Thelon typelon of thelon candidatelons
 */
caselon class SnowflakelonIdAgelonFiltelonr[Candidatelon <: UnivelonrsalNoun[Long]](
  maxAgelonParam: Param[Duration])
    elonxtelonnds Filtelonr[PipelonlinelonQuelonry, Candidatelon] {

  ovelonrridelon val idelonntifielonr: FiltelonrIdelonntifielonr = FiltelonrIdelonntifielonr("SnowflakelonIdAgelon")

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]
  ): Stitch[FiltelonrRelonsult[Candidatelon]] = {
    val maxAgelon = quelonry.params(maxAgelonParam)

    val (kelonptCandidatelons, relonmovelondCandidatelons) = candidatelons
      .map(_.candidatelon)
      .partition { filtelonrCandidatelon =>
        SnowflakelonId.timelonFromIdOpt(filtelonrCandidatelon.id) match {
          caselon Somelon(crelonationTimelon) =>
            quelonry.quelonryTimelon.sincelon(crelonationTimelon) <= maxAgelon
          caselon _ => falselon
        }
      }

    Stitch.valuelon(FiltelonrRelonsult(kelonpt = kelonptCandidatelons, relonmovelond = relonmovelondCandidatelons))
  }
}
