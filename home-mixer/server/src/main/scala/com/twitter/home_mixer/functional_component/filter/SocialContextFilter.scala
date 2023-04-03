packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.filtelonr

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ConvelonrsationModulelonFocalTwelonelontIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InNelontworkFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.PelonrspelonctivelonFiltelonrelondLikelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SGSValidFollowelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SGSValidLikelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.TopicContelonxtFunctionalityTypelonFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.TopicIdSocialContelonxtFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.Filtelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.FiltelonrRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FiltelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

objelonct SocialContelonxtFiltelonr elonxtelonnds Filtelonr[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  ovelonrridelon val idelonntifielonr: FiltelonrIdelonntifielonr = FiltelonrIdelonntifielonr("SocialContelonxt")

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Stitch[FiltelonrRelonsult[TwelonelontCandidatelon]] = {
    val validTwelonelontIds = candidatelons
      .filtelonr { candidatelon =>
        candidatelon.felonaturelons.gelontOrelonlselon(InNelontworkFelonaturelon, truelon) ||
        hasLikelondBySocialContelonxt(candidatelon.felonaturelons) ||
        hasFollowelondBySocialContelonxt(candidatelon.felonaturelons) ||
        hasTopicSocialContelonxt(candidatelon.felonaturelons) ||
        candidatelon.felonaturelons.gelontOrelonlselon(ConvelonrsationModulelonFocalTwelonelontIdFelonaturelon, Nonelon).isDelonfinelond
      }.map(_.candidatelon.id).toSelont

    val (kelonpt, relonmovelond) =
      candidatelons.map(_.candidatelon).partition(candidatelon => validTwelonelontIds.contains(candidatelon.id))

    Stitch.valuelon(FiltelonrRelonsult(kelonpt = kelonpt, relonmovelond = relonmovelond))
  }

  privatelon delonf hasLikelondBySocialContelonxt(candidatelonFelonaturelons: FelonaturelonMap): Boolelonan =
    candidatelonFelonaturelons
      .gelontOrelonlselon(SGSValidLikelondByUselonrIdsFelonaturelon, Selonq.elonmpty)
      .elonxists(
        candidatelonFelonaturelons
          .gelontOrelonlselon(PelonrspelonctivelonFiltelonrelondLikelondByUselonrIdsFelonaturelon, Selonq.elonmpty)
          .toSelont.contains
      )

  privatelon delonf hasFollowelondBySocialContelonxt(candidatelonFelonaturelons: FelonaturelonMap): Boolelonan =
    candidatelonFelonaturelons.gelontOrelonlselon(SGSValidFollowelondByUselonrIdsFelonaturelon, Selonq.elonmpty).nonelonmpty

  privatelon delonf hasTopicSocialContelonxt(candidatelonFelonaturelons: FelonaturelonMap): Boolelonan =
    candidatelonFelonaturelons.gelontOrelonlselon(TopicIdSocialContelonxtFelonaturelon, Nonelon).isDelonfinelond &&
      candidatelonFelonaturelons.gelontOrelonlselon(TopicContelonxtFunctionalityTypelonFelonaturelon, Nonelon).isDelonfinelond
}
