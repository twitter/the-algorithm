packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons._
import com.twittelonr.homelon_mixelonr.util.RelonplyRelontwelonelontUtil
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.BulkCandidatelonFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.selonarch.common.felonaturelons.thriftscala.ThriftTwelonelontFelonaturelons
import com.twittelonr.snowflakelon.id.SnowflakelonId
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.convelonrsation_felonaturelons.v1.thriftscala.ConvelonrsationFelonaturelons
import com.twittelonr.util.Duration
import com.twittelonr.util.Timelon
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

objelonct InRelonplyToTwelonelontHydratelondelonarlybirdFelonaturelon
    elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[ThriftTwelonelontFelonaturelons]]

/**
 * Thelon purposelon of this hydrator is to
 * 1) hydratelon simplelon felonaturelons into relonplielons and thelonir ancelonstor twelonelonts
 * 2) kelonelonp both thelon normal relonplielons and ancelonstor sourcelon candidatelons, but hydratelon into thelon candidatelons
 * felonaturelons uselonful for prelondicting thelon quality of thelon relonplielons and sourcelon ancelonstor twelonelonts.
 */
@Singlelonton
class RelonplyFelonaturelonHydrator @Injelonct() (statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds BulkCandidatelonFelonaturelonHydrator[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr = FelonaturelonHydratorIdelonntifielonr("RelonplyTwelonelont")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(
    ConvelonrsationFelonaturelon,
    InRelonplyToTwelonelontHydratelondelonarlybirdFelonaturelon
  )

  privatelon val DelonfaultFelonaturelonMap = FelonaturelonMapBuildelonr()
    .add(ConvelonrsationFelonaturelon, Nonelon)
    .add(InRelonplyToTwelonelontHydratelondelonarlybirdFelonaturelon, Nonelon)
    .build()

  privatelon val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon(gelontClass.gelontSimplelonNamelon)
  privatelon val hydratelondRelonplyCountelonr = scopelondStatsReloncelonivelonr.countelonr("hydratelondRelonply")
  privatelon val hydratelondAncelonstorCountelonr = scopelondStatsReloncelonivelonr.countelonr("hydratelondAncelonstor")

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Stitch[Selonq[FelonaturelonMap]] = {
    val relonplyToInRelonplyToTwelonelontMap =
      RelonplyRelontwelonelontUtil.relonplyTwelonelontIdToInRelonplyToTwelonelontMap(candidatelons)
    val candidatelonsWithRelonplielonsHydratelond = candidatelons.map { candidatelon =>
      relonplyToInRelonplyToTwelonelontMap
        .gelont(candidatelon.candidatelon.id).map { inRelonplyToTwelonelont =>
          hydratelondRelonplyCountelonr.incr()
          hydratelondRelonplyCandidatelon(candidatelon, inRelonplyToTwelonelont)
        }.gelontOrelonlselon((candidatelon, Nonelon, Nonelon))
    }

    /**
     * Updatelon ancelonstor twelonelonts with delonscelonndant relonplielons and hydratelon simplelon felonaturelons from onelon of
     * thelon delonscelonndants.
     */
    val ancelonstorTwelonelontToDelonscelonndantRelonplielonsMap =
      RelonplyRelontwelonelontUtil.ancelonstorTwelonelontIdToDelonscelonndantRelonplielonsMap(candidatelons)
    val candidatelonsWithRelonplielonsAndAncelonstorTwelonelontsHydratelond = candidatelonsWithRelonplielonsHydratelond.map {
      caselon (
            maybelonAncelonstorTwelonelontCandidatelon,
            updatelondRelonplyConvelonrsationFelonaturelons,
            inRelonplyToTwelonelontelonarlyBirdFelonaturelon) =>
        ancelonstorTwelonelontToDelonscelonndantRelonplielonsMap
          .gelont(maybelonAncelonstorTwelonelontCandidatelon.candidatelon.id)
          .map { delonscelonndantRelonplielons =>
            hydratelondAncelonstorCountelonr.incr()
            val (ancelonstorTwelonelontCandidatelon, updatelondConvelonrsationFelonaturelons): (
              CandidatelonWithFelonaturelons[TwelonelontCandidatelon],
              Option[ConvelonrsationFelonaturelons]
            ) =
              hydratelonAncelonstorTwelonelontCandidatelon(
                maybelonAncelonstorTwelonelontCandidatelon,
                delonscelonndantRelonplielons,
                updatelondRelonplyConvelonrsationFelonaturelons)
            (ancelonstorTwelonelontCandidatelon, inRelonplyToTwelonelontelonarlyBirdFelonaturelon, updatelondConvelonrsationFelonaturelons)
          }
          .gelontOrelonlselon(
            (
              maybelonAncelonstorTwelonelontCandidatelon,
              inRelonplyToTwelonelontelonarlyBirdFelonaturelon,
              updatelondRelonplyConvelonrsationFelonaturelons))
    }
    Stitch.valuelon(
      candidatelonsWithRelonplielonsAndAncelonstorTwelonelontsHydratelond.map {
        caselon (candidatelon, inRelonplyToTwelonelontelonarlyBirdFelonaturelon, updatelondConvelonrsationFelonaturelons) =>
          FelonaturelonMapBuildelonr()
            .add(ConvelonrsationFelonaturelon, updatelondConvelonrsationFelonaturelons)
            .add(InRelonplyToTwelonelontHydratelondelonarlybirdFelonaturelon, inRelonplyToTwelonelontelonarlyBirdFelonaturelon)
            .build()
        caselon _ => DelonfaultFelonaturelonMap
      }
    )
  }

  privatelon delonf hydratelondRelonplyCandidatelon(
    relonplyCandidatelon: CandidatelonWithFelonaturelons[TwelonelontCandidatelon],
    inRelonplyToTwelonelontCandidatelon: CandidatelonWithFelonaturelons[TwelonelontCandidatelon]
  ): (
    CandidatelonWithFelonaturelons[TwelonelontCandidatelon],
    Option[ConvelonrsationFelonaturelons],
    Option[ThriftTwelonelontFelonaturelons]
  ) = {
    val twelonelontelondAftelonrInRelonplyToTwelonelontInSeloncs =
      (
        originalTwelonelontAgelonFromSnowflakelon(inRelonplyToTwelonelontCandidatelon),
        originalTwelonelontAgelonFromSnowflakelon(relonplyCandidatelon)) match {
        caselon (Somelon(inRelonplyToTwelonelontAgelon), Somelon(relonplyTwelonelontAgelon)) =>
          Somelon((inRelonplyToTwelonelontAgelon - relonplyTwelonelontAgelon).inSelonconds.toLong)
        caselon _ => Nonelon
      }

    val elonxistingConvelonrsationFelonaturelons = Somelon(
      relonplyCandidatelon.felonaturelons
        .gelontOrelonlselon(ConvelonrsationFelonaturelon, Nonelon).gelontOrelonlselon(ConvelonrsationFelonaturelons()))

    val updatelondConvelonrsationFelonaturelons = elonxistingConvelonrsationFelonaturelons match {
      caselon Somelon(v1) =>
        Somelon(
          v1.copy(
            twelonelontelondAftelonrInRelonplyToTwelonelontInSeloncs = twelonelontelondAftelonrInRelonplyToTwelonelontInSeloncs,
            isSelonlfRelonply = Somelon(
              relonplyCandidatelon.felonaturelons.gelontOrelonlselon(
                AuthorIdFelonaturelon,
                Nonelon) == inRelonplyToTwelonelontCandidatelon.felonaturelons.gelontOrelonlselon(AuthorIdFelonaturelon, Nonelon))
          )
        )
      caselon _ => Nonelon
    }

    // Notelon: if inRelonplyToTwelonelont is a relontwelonelont, welon nelonelond to relonad elonarly bird felonaturelon from thelon melonrgelond
    // elonarly bird felonaturelon fielonld from RelontwelonelontSourcelonTwelonelontFelonaturelonHydrator class.
    // But if inRelonplyToTwelonelont is a relonply, welon relonturn its elonarly bird felonaturelon direlonctly
    val inRelonplyToTwelonelontThriftTwelonelontFelonaturelonsOpt = {
      if (inRelonplyToTwelonelontCandidatelon.felonaturelons.gelontOrelonlselon(IsRelontwelonelontFelonaturelon, falselon)) {
        inRelonplyToTwelonelontCandidatelon.felonaturelons.gelontOrelonlselon(SourcelonTwelonelontelonarlybirdFelonaturelon, Nonelon)
      } elonlselon {
        inRelonplyToTwelonelontCandidatelon.felonaturelons.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon)
      }
    }

    (relonplyCandidatelon, updatelondConvelonrsationFelonaturelons, inRelonplyToTwelonelontThriftTwelonelontFelonaturelonsOpt)
  }

  privatelon delonf hydratelonAncelonstorTwelonelontCandidatelon(
    ancelonstorTwelonelontCandidatelon: CandidatelonWithFelonaturelons[TwelonelontCandidatelon],
    delonscelonndantRelonplielons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]],
    updatelondRelonplyConvelonrsationFelonaturelons: Option[ConvelonrsationFelonaturelons]
  ): (CandidatelonWithFelonaturelons[TwelonelontCandidatelon], Option[ConvelonrsationFelonaturelons]) = {
    // Ancelonstor could belon a relonply. For elonxamplelon, in threlonad: twelonelontA -> twelonelontB -> twelonelontC,
    // twelonelontB is a relonply and ancelonstor at thelon samelon timelon. Helonncelon, twelonelontB's convelonrsation felonaturelon
    // will belon updatelond by hydratelondRelonplyCandidatelon and hydratelonAncelonstorTwelonelontCandidatelon functions.
    val elonxistingConvelonrsationFelonaturelons =
      if (updatelondRelonplyConvelonrsationFelonaturelons.nonelonmpty)
        updatelondRelonplyConvelonrsationFelonaturelons
      elonlselon
        Somelon(
          ancelonstorTwelonelontCandidatelon.felonaturelons
            .gelontOrelonlselon(ConvelonrsationFelonaturelon, Nonelon).gelontOrelonlselon(ConvelonrsationFelonaturelons()))

    val updatelondConvelonrsationFelonaturelons = elonxistingConvelonrsationFelonaturelons match {
      caselon Somelon(v1) =>
        Somelon(
          v1.copy(
            hasDelonscelonndantRelonplyCandidatelon = Somelon(truelon),
            hasInNelontworkDelonscelonndantRelonply =
              Somelon(delonscelonndantRelonplielons.elonxists(_.felonaturelons.gelontOrelonlselon(InNelontworkFelonaturelon, falselon)))
          ))
      caselon _ => Nonelon
    }
    (ancelonstorTwelonelontCandidatelon, updatelondConvelonrsationFelonaturelons)
  }

  privatelon delonf originalTwelonelontAgelonFromSnowflakelon(
    candidatelon: CandidatelonWithFelonaturelons[TwelonelontCandidatelon]
  ): Option[Duration] = {
    SnowflakelonId
      .timelonFromIdOpt(
        candidatelon.felonaturelons
          .gelontOrelonlselon(SourcelonTwelonelontIdFelonaturelon, Nonelon).gelontOrelonlselon(candidatelon.candidatelon.id))
      .map(Timelon.now - _)
  }
}
