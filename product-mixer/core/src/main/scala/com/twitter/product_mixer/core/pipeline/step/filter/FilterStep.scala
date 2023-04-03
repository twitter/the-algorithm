packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.stelonp.filtelonr

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.Filtelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.statelon.HasCandidatelonsWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.statelon.HasQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.stelonp.Stelonp
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.filtelonr_elonxeloncutor.Filtelonrelonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.filtelonr_elonxeloncutor.FiltelonrelonxeloncutorRelonsult
import com.twittelonr.stitch.Arrow
import javax.injelonct.Injelonct

/**
 * A candidatelon filtelonr stelonp, it takelons thelon input list of candidatelons and thelon givelonn filtelonr and applielons
 * thelon filtelonrs on thelon candidatelons in selonquelonncelon, relonturning thelon final kelonpt candidatelons list to Statelon.
 *
 * @param filtelonrelonxeloncutor Filtelonr elonxeloncutor
 * @tparam Quelonry Typelon of PipelonlinelonQuelonry domain modelonl
 * @tparam Candidatelon Typelon of Candidatelons to filtelonr
 * @tparam Statelon Thelon pipelonlinelon statelon domain modelonl.
 */
caselon class FiltelonrStelonp[
  Quelonry <: PipelonlinelonQuelonry,
  Candidatelon <: UnivelonrsalNoun[Any],
  Statelon <: HasQuelonry[Quelonry, Statelon] with HasCandidatelonsWithFelonaturelons[
    Candidatelon,
    Statelon
  ]] @Injelonct() (filtelonrelonxeloncutor: Filtelonrelonxeloncutor)
    elonxtelonnds Stelonp[Statelon, Selonq[
      Filtelonr[Quelonry, Candidatelon]
    ], (Quelonry, Selonq[CandidatelonWithFelonaturelons[Candidatelon]]), FiltelonrelonxeloncutorRelonsult[Candidatelon]] {

  ovelonrridelon delonf iselonmpty(config: Selonq[Filtelonr[Quelonry, Candidatelon]]): Boolelonan = config.iselonmpty

  ovelonrridelon delonf adaptInput(
    statelon: Statelon,
    config: Selonq[Filtelonr[Quelonry, Candidatelon]]
  ): (Quelonry, Selonq[CandidatelonWithFelonaturelons[Candidatelon]]) =
    (statelon.quelonry, statelon.candidatelonsWithFelonaturelons)

  ovelonrridelon delonf arrow(
    config: Selonq[Filtelonr[Quelonry, Candidatelon]],
    contelonxt: elonxeloncutor.Contelonxt
  ): Arrow[(Quelonry, Selonq[CandidatelonWithFelonaturelons[Candidatelon]]), FiltelonrelonxeloncutorRelonsult[Candidatelon]] =
    filtelonrelonxeloncutor.arrow(config, contelonxt)

  ovelonrridelon delonf updatelonStatelon(
    statelon: Statelon,
    elonxeloncutorRelonsult: FiltelonrelonxeloncutorRelonsult[Candidatelon],
    config: Selonq[Filtelonr[Quelonry, Candidatelon]]
  ): Statelon = {
    val kelonptCandidatelons = elonxeloncutorRelonsult.relonsult
    val candidatelonsMap = statelon.candidatelonsWithFelonaturelons.map { candidatelonsWithFelonaturelons =>
      candidatelonsWithFelonaturelons.candidatelon -> candidatelonsWithFelonaturelons
    }.toMap
    val nelonwCandidatelons = kelonptCandidatelons.flatMap { candidatelon =>
      candidatelonsMap.gelont(candidatelon)
    }
    statelon.updatelonCandidatelonsWithFelonaturelons(nelonwCandidatelons)
  }
}
