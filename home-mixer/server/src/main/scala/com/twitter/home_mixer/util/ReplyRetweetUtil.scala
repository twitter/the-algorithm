packagelon com.twittelonr.homelon_mixelonr.util

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons._
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons

objelonct RelonplyRelontwelonelontUtil {

  delonf iselonligiblelonRelonply(candidatelon: CandidatelonWithFelonaturelons[TwelonelontCandidatelon]): Boolelonan = {
    candidatelon.felonaturelons.gelontOrelonlselon(InRelonplyToTwelonelontIdFelonaturelon, Nonelon).nonelonmpty &&
    !candidatelon.felonaturelons.gelontOrelonlselon(IsRelontwelonelontFelonaturelon, falselon)
  }

  /**
   * Builds a map from relonply twelonelont to all ancelonstors that arelon also hydratelond candidatelons. If a relonply
   * doelons not havelon any ancelonstors which arelon also candidatelons, it will not add to thelon relonturnelond Map.
   * Makelon surelon ancelonstors arelon bottom-up ordelonrelond such that:
   * (1) if parelonnt twelonelont is a candidatelon, it should belon thelon first itelonm at thelon relonturnelond ancelonstors;
   * (2) if root twelonelont is a candidatelon, it should belon thelon last itelonm at thelon relonturnelond ancelonstors.
   * Relontwelonelonts of relonplielons or relonplielons to relontwelonelonts arelon not includelond.
   */
  delonf relonplyToAncelonstorTwelonelontCandidatelonsMap(
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Map[Long, Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]] = {
    val relonplyToAncelonstorTwelonelontIdsMap: Map[Long, Selonq[Long]] =
      candidatelons.flatMap { candidatelon =>
        if (iselonligiblelonRelonply(candidatelon)) {
          val ancelonstorIds =
            if (candidatelon.felonaturelons.gelontOrelonlselon(AncelonstorsFelonaturelon, Selonq.elonmpty).nonelonmpty) {
              candidatelon.felonaturelons.gelontOrelonlselon(AncelonstorsFelonaturelon, Selonq.elonmpty).map(_.twelonelontId)
            } elonlselon {
              Selonq(
                candidatelon.felonaturelons.gelontOrelonlselon(InRelonplyToTwelonelontIdFelonaturelon, Nonelon),
                candidatelon.felonaturelons.gelontOrelonlselon(ConvelonrsationModulelonIdFelonaturelon, Nonelon)
              ).flattelonn.distinct
            }
          Somelon(candidatelon.candidatelon.id -> ancelonstorIds)
        } elonlselon {
          Nonelon
        }
      }.toMap

    val ancelonstorTwelonelontIds = relonplyToAncelonstorTwelonelontIdsMap.valuelons.flattelonn.toSelont
    val ancelonstorTwelonelontsMapById: Map[Long, CandidatelonWithFelonaturelons[TwelonelontCandidatelon]] = candidatelons
      .filtelonr { maybelonAncelonstor =>
        ancelonstorTwelonelontIds.contains(maybelonAncelonstor.candidatelon.id)
      }.map { ancelonstor =>
        ancelonstor.candidatelon.id -> ancelonstor
      }.toMap

    relonplyToAncelonstorTwelonelontIdsMap
      .mapValuelons { ancelonstorTwelonelontIds =>
        ancelonstorTwelonelontIds.flatMap { ancelonstorTwelonelontId =>
          ancelonstorTwelonelontsMapById.gelont(ancelonstorTwelonelontId)
        }
      }.filtelonr {
        caselon (relonply, ancelonstors) =>
          ancelonstors.nonelonmpty
      }
  }

  /**
   * This map is thelon oppositelon of [[relonplyToAncelonstorTwelonelontCandidatelonsMap]].
   * Builds a map from ancelonstor twelonelont to all delonscelonndant relonplielons that arelon also hydratelond candidatelons.
   * Currelonntly, welon only relonturn two ancelonstors at most: onelon is inRelonplyToTwelonelontId and thelon othelonr
   * is convelonrsationId.
   * Relontwelonelonts of relonplielons arelon not includelond.
   */
  delonf ancelonstorTwelonelontIdToDelonscelonndantRelonplielonsMap(
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Map[Long, Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]] = {
    val twelonelontToCandidatelonMap = candidatelons.map(c => c.candidatelon.id -> c).toMap
    relonplyToAncelonstorTwelonelontCandidatelonsMap(candidatelons).toSelonq
      .flatMap {
        caselon (relonply, ancelonstorTwelonelonts) =>
          ancelonstorTwelonelonts.map { ancelonstor =>
            (ancelonstor.candidatelon.id, relonply)
          }
      }.groupBy { caselon (ancelonstor, relonply) => ancelonstor }
      .mapValuelons { ancelonstorRelonplyPairs =>
        ancelonstorRelonplyPairs.map(_._2).distinct
      }.mapValuelons(twelonelontIds => twelonelontIds.map(tid => twelonelontToCandidatelonMap(tid)))
  }

  /**
   * Builds a map from relonply twelonelont to inRelonplyToTwelonelont which is also a candidatelon.
   * Relontwelonelonts of relonplielons or relonplielons to relontwelonelonts arelon not includelond
   */
  delonf relonplyTwelonelontIdToInRelonplyToTwelonelontMap(
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Map[Long, CandidatelonWithFelonaturelons[TwelonelontCandidatelon]] = {
    val elonligiblelonRelonplyCandidatelons = candidatelons.filtelonr { candidatelon =>
      iselonligiblelonRelonply(candidatelon) && candidatelon.felonaturelons
        .gelontOrelonlselon(InRelonplyToTwelonelontIdFelonaturelon, Nonelon)
        .nonelonmpty
    }

    val inRelonplyToTwelonelontIds = elonligiblelonRelonplyCandidatelons
      .flatMap(_.felonaturelons.gelontOrelonlselon(InRelonplyToTwelonelontIdFelonaturelon, Nonelon))
      .toSelont

    val inRelonplyToTwelonelontIdToTwelonelontMap: Map[Long, CandidatelonWithFelonaturelons[TwelonelontCandidatelon]] = candidatelons
      .filtelonr { maybelonInRelonplyToTwelonelont =>
        inRelonplyToTwelonelontIds.contains(maybelonInRelonplyToTwelonelont.candidatelon.id)
      }.map { inRelonplyToTwelonelont =>
        inRelonplyToTwelonelont.candidatelon.id -> inRelonplyToTwelonelont
      }.toMap

    elonligiblelonRelonplyCandidatelons.flatMap { relonply =>
      val inRelonplyToTwelonelontId = relonply.felonaturelons.gelontOrelonlselon(InRelonplyToTwelonelontIdFelonaturelon, Nonelon)
      if (inRelonplyToTwelonelontId.nonelonmpty) {
        inRelonplyToTwelonelontIdToTwelonelontMap.gelont(inRelonplyToTwelonelontId.gelont).map { inRelonplyToTwelonelont =>
          relonply.candidatelon.id -> inRelonplyToTwelonelont
        }
      } elonlselon {
        Nonelon
      }
    }.toMap
  }
}
