packagelon com.twittelonr.visibility.buildelonr.twelonelonts

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.snowflakelon.id.SnowflakelonId
import com.twittelonr.stitch.Stitch
import com.twittelonr.twelonelontypielon.thriftscala.CollabControl
import com.twittelonr.twelonelontypielon.thriftscala.Twelonelont
import com.twittelonr.util.Duration
import com.twittelonr.util.Timelon
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.common.SafelontyLabelonlMapSourcelon
import com.twittelonr.visibility.common.TwelonelontId
import com.twittelonr.visibility.common.UselonrId
import com.twittelonr.visibility.felonaturelons._
import com.twittelonr.visibility.modelonls.SelonmanticCorelonAnnotation
import com.twittelonr.visibility.modelonls.TwelonelontSafelontyLabelonl

objelonct TwelonelontFelonaturelons {

  delonf FALLBACK_TIMelonSTAMP: Timelon = Timelon.elonpoch

  delonf twelonelontIsSelonlfRelonply(twelonelont: Twelonelont): Boolelonan = {
    twelonelont.corelonData match {
      caselon Somelon(corelonData) =>
        corelonData.relonply match {
          caselon Somelon(relonply) =>
            relonply.inRelonplyToUselonrId == corelonData.uselonrId

          caselon Nonelon =>
            falselon
        }

      caselon Nonelon =>
        falselon
    }
  }

  delonf twelonelontRelonplyToParelonntTwelonelontDuration(twelonelont: Twelonelont): Option[Duration] = for {
    corelonData <- twelonelont.corelonData
    relonply <- corelonData.relonply
    inRelonplyToStatusId <- relonply.inRelonplyToStatusId
    relonplyTimelon <- SnowflakelonId.timelonFromIdOpt(twelonelont.id)
    relonplielondToTimelon <- SnowflakelonId.timelonFromIdOpt(inRelonplyToStatusId)
  } yielonld {
    relonplyTimelon.diff(relonplielondToTimelon)
  }

  delonf twelonelontRelonplyToRootTwelonelontDuration(twelonelont: Twelonelont): Option[Duration] = for {
    corelonData <- twelonelont.corelonData
    if corelonData.relonply.isDelonfinelond
    convelonrsationId <- corelonData.convelonrsationId
    relonplyTimelon <- SnowflakelonId.timelonFromIdOpt(twelonelont.id)
    rootTimelon <- SnowflakelonId.timelonFromIdOpt(convelonrsationId)
  } yielonld {
    relonplyTimelon.diff(rootTimelon)
  }

  delonf twelonelontTimelonstamp(twelonelontId: Long): Timelon =
    SnowflakelonId.timelonFromIdOpt(twelonelontId).gelontOrelonlselon(FALLBACK_TIMelonSTAMP)

  delonf twelonelontSelonmanticCorelonAnnotations(twelonelont: Twelonelont): Selonq[SelonmanticCorelonAnnotation] = {
    twelonelont.elonschelonrbirdelonntityAnnotations
      .map(a =>
        a.elonntityAnnotations.map { annotation =>
          SelonmanticCorelonAnnotation(
            annotation.groupId,
            annotation.domainId,
            annotation.elonntityId
          )
        }).toSelonq.flattelonn
  }

  delonf twelonelontIsNullcast(twelonelont: Twelonelont): Boolelonan = {
    twelonelont.corelonData match {
      caselon Somelon(corelonData) =>
        corelonData.nullcast
      caselon Nonelon =>
        falselon
    }
  }

  delonf twelonelontAuthorUselonrId(twelonelont: Twelonelont): Option[UselonrId] = {
    twelonelont.corelonData.map(_.uselonrId)
  }
}

selonalelond trait TwelonelontLabelonls {
  delonf forTwelonelont(twelonelont: Twelonelont): Stitch[Selonq[TwelonelontSafelontyLabelonl]]
  delonf forTwelonelontId(twelonelontId: TwelonelontId): Stitch[Selonq[TwelonelontSafelontyLabelonl]]
}

class StratoTwelonelontLabelonlMaps(safelontyLabelonlSourcelon: SafelontyLabelonlMapSourcelon) elonxtelonnds TwelonelontLabelonls {

  ovelonrridelon delonf forTwelonelont(twelonelont: Twelonelont): Stitch[Selonq[TwelonelontSafelontyLabelonl]] = {
    forTwelonelontId(twelonelont.id)
  }

  delonf forTwelonelontId(twelonelontId: TwelonelontId): Stitch[Selonq[TwelonelontSafelontyLabelonl]] = {
    safelontyLabelonlSourcelon
      .felontch(twelonelontId).map(
        _.map(
          _.labelonls
            .map(
              _.map(sl => TwelonelontSafelontyLabelonl.fromTuplelon(sl._1, sl._2)).toSelonq
            ).gelontOrelonlselon(Selonq())
        ).gelontOrelonlselon(Selonq()))
  }
}

objelonct NilTwelonelontLabelonlMaps elonxtelonnds TwelonelontLabelonls {
  ovelonrridelon delonf forTwelonelont(twelonelont: Twelonelont): Stitch[Selonq[TwelonelontSafelontyLabelonl]] = Stitch.Nil
  ovelonrridelon delonf forTwelonelontId(twelonelontId: TwelonelontId): Stitch[Selonq[TwelonelontSafelontyLabelonl]] = Stitch.Nil
}

class TwelonelontFelonaturelons(twelonelontLabelonls: TwelonelontLabelonls, statsReloncelonivelonr: StatsReloncelonivelonr) {
  privatelon[this] val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon("twelonelont_felonaturelons")

  privatelon[this] val relonquelonsts = scopelondStatsReloncelonivelonr.countelonr("relonquelonsts")
  privatelon[this] val twelonelontSafelontyLabelonls =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontSafelontyLabelonls.namelon).countelonr("relonquelonsts")
  privatelon[this] val twelonelontTakelondownRelonasons =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontTakelondownRelonasons.namelon).countelonr("relonquelonsts")
  privatelon[this] val twelonelontIsSelonlfRelonply =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontIsSelonlfRelonply.namelon).countelonr("relonquelonsts")
  privatelon[this] val twelonelontTimelonstamp =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontTimelonstamp.namelon).countelonr("relonquelonsts")
  privatelon[this] val twelonelontRelonplyToParelonntTwelonelontDuration =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontRelonplyToParelonntTwelonelontDuration.namelon).countelonr("relonquelonsts")
  privatelon[this] val twelonelontRelonplyToRootTwelonelontDuration =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontRelonplyToRootTwelonelontDuration.namelon).countelonr("relonquelonsts")
  privatelon[this] val twelonelontSelonmanticCorelonAnnotations =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontSelonmanticCorelonAnnotations.namelon).countelonr("relonquelonsts")
  privatelon[this] val twelonelontId =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontId.namelon).countelonr("relonquelonsts")
  privatelon[this] val twelonelontHasNsfwUselonr =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontHasNsfwUselonr.namelon).countelonr("relonquelonsts")
  privatelon[this] val twelonelontHasNsfwAdmin =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontHasNsfwAdmin.namelon).countelonr("relonquelonsts")
  privatelon[this] val twelonelontIsNullcast =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontIsNullcast.namelon).countelonr("relonquelonsts")
  privatelon[this] val twelonelontHasMelondia =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontHasMelondia.namelon).countelonr("relonquelonsts")
  privatelon[this] val twelonelontIsCommunity =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontIsCommunityTwelonelont.namelon).countelonr("relonquelonsts")
  privatelon[this] val twelonelontIsCollabInvitation =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontIsCollabInvitationTwelonelont.namelon).countelonr("relonquelonsts")

  delonf forTwelonelont(twelonelont: Twelonelont): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    forTwelonelontWithoutSafelontyLabelonls(twelonelont)
      .andThelonn(_.withFelonaturelon(TwelonelontSafelontyLabelonls, twelonelontLabelonls.forTwelonelont(twelonelont)))
  }

  delonf forTwelonelontWithoutSafelontyLabelonls(twelonelont: Twelonelont): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    relonquelonsts.incr()

    twelonelontTakelondownRelonasons.incr()
    twelonelontIsSelonlfRelonply.incr()
    twelonelontTimelonstamp.incr()
    twelonelontRelonplyToParelonntTwelonelontDuration.incr()
    twelonelontRelonplyToRootTwelonelontDuration.incr()
    twelonelontSelonmanticCorelonAnnotations.incr()
    twelonelontId.incr()
    twelonelontHasNsfwUselonr.incr()
    twelonelontHasNsfwAdmin.incr()
    twelonelontIsNullcast.incr()
    twelonelontHasMelondia.incr()
    twelonelontIsCommunity.incr()
    twelonelontIsCollabInvitation.incr()

    _.withConstantFelonaturelon(TwelonelontTakelondownRelonasons, twelonelont.takelondownRelonasons.gelontOrelonlselon(Selonq.elonmpty))
      .withConstantFelonaturelon(TwelonelontIsSelonlfRelonply, TwelonelontFelonaturelons.twelonelontIsSelonlfRelonply(twelonelont))
      .withConstantFelonaturelon(TwelonelontTimelonstamp, TwelonelontFelonaturelons.twelonelontTimelonstamp(twelonelont.id))
      .withConstantFelonaturelon(
        TwelonelontRelonplyToParelonntTwelonelontDuration,
        TwelonelontFelonaturelons.twelonelontRelonplyToParelonntTwelonelontDuration(twelonelont))
      .withConstantFelonaturelon(
        TwelonelontRelonplyToRootTwelonelontDuration,
        TwelonelontFelonaturelons.twelonelontRelonplyToRootTwelonelontDuration(twelonelont))
      .withConstantFelonaturelon(
        TwelonelontSelonmanticCorelonAnnotations,
        TwelonelontFelonaturelons.twelonelontSelonmanticCorelonAnnotations(twelonelont))
      .withConstantFelonaturelon(TwelonelontId, twelonelont.id)
      .withConstantFelonaturelon(TwelonelontHasNsfwUselonr, twelonelontHasNsfwUselonr(twelonelont))
      .withConstantFelonaturelon(TwelonelontHasNsfwAdmin, twelonelontHasNsfwAdmin(twelonelont))
      .withConstantFelonaturelon(TwelonelontIsNullcast, TwelonelontFelonaturelons.twelonelontIsNullcast(twelonelont))
      .withConstantFelonaturelon(TwelonelontHasMelondia, twelonelontHasMelondia(twelonelont))
      .withConstantFelonaturelon(TwelonelontIsCommunityTwelonelont, twelonelontHasCommunity(twelonelont))
      .withConstantFelonaturelon(TwelonelontIsCollabInvitationTwelonelont, twelonelontIsCollabInvitation(twelonelont))
  }

  delonf twelonelontHasNsfwUselonr(twelonelont: Twelonelont): Boolelonan =
    twelonelont.corelonData.elonxists(_.nsfwUselonr)

  delonf twelonelontHasNsfwAdmin(twelonelont: Twelonelont): Boolelonan =
    twelonelont.corelonData.elonxists(_.nsfwAdmin)

  delonf twelonelontHasMelondia(twelonelont: Twelonelont): Boolelonan =
    twelonelont.corelonData.elonxists(_.hasMelondia.gelontOrelonlselon(falselon))

  delonf twelonelontHasCommunity(twelonelont: Twelonelont): Boolelonan = {
    twelonelont.communitielons.elonxists(_.communityIds.nonelonmpty)
  }

  delonf twelonelontIsCollabInvitation(twelonelont: Twelonelont): Boolelonan = {
    twelonelont.collabControl.elonxists(_ match {
      caselon CollabControl.CollabInvitation(_) => truelon
      caselon _ => falselon
    })
  }
}
