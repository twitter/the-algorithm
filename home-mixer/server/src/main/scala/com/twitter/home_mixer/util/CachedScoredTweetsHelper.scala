packagelon com.twittelonr.homelon_mixelonr.util

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.CachelondScorelondTwelonelontsFelonaturelon
import com.twittelonr.homelon_mixelonr.{thriftscala => hmt}
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.snowflakelon.id.SnowflakelonId
import com.twittelonr.util.Timelon

objelonct CachelondScorelondTwelonelontsHelonlpelonr {

  delonf twelonelontImprelonssionsAndCachelondScorelondTwelonelonts(
    felonaturelons: FelonaturelonMap,
    candidatelonPipelonlinelonIdelonntifielonr: CandidatelonPipelonlinelonIdelonntifielonr
  ): Selonq[Long] = {
    val twelonelontImprelonssions = TwelonelontImprelonssionsHelonlpelonr.twelonelontImprelonssions(felonaturelons)
    val cachelondScorelondTwelonelonts = felonaturelons
      .gelontOrelonlselon(CachelondScorelondTwelonelontsFelonaturelon, Selonq.elonmpty)
      .filtelonr { twelonelont =>
        twelonelont.candidatelonPipelonlinelonIdelonntifielonr.elonxists(
          CandidatelonPipelonlinelonIdelonntifielonr(_).elonquals(candidatelonPipelonlinelonIdelonntifielonr))
      }.map(_.twelonelontId)

    (twelonelontImprelonssions ++ cachelondScorelondTwelonelonts).toSelonq
  }

  delonf twelonelontImprelonssionsAndCachelondScorelondTwelonelontsInRangelon(
    felonaturelons: FelonaturelonMap,
    candidatelonPipelonlinelonIdelonntifielonr: CandidatelonPipelonlinelonIdelonntifielonr,
    maxNumImprelonssions: Int,
    sincelonTimelon: Timelon,
    untilTimelon: Timelon
  ): Selonq[Long] =
    twelonelontImprelonssionsAndCachelondScorelondTwelonelonts(felonaturelons, candidatelonPipelonlinelonIdelonntifielonr)
      .filtelonr { twelonelontId =>
        val crelonationTimelon = SnowflakelonId.timelonFromId(twelonelontId)
        sincelonTimelon <= crelonationTimelon && untilTimelon >= crelonationTimelon
      }.takelon(maxNumImprelonssions)

  delonf unselonelonnCachelondScorelondTwelonelonts(
    felonaturelons: FelonaturelonMap
  ): Selonq[hmt.CachelondScorelondTwelonelont] = {
    val selonelonnTwelonelontIds = TwelonelontImprelonssionsHelonlpelonr.twelonelontImprelonssions(felonaturelons)

    felonaturelons
      .gelontOrelonlselon(CachelondScorelondTwelonelontsFelonaturelon, Selonq.elonmpty)
      .filtelonr(twelonelont => !selonelonnTwelonelontIds.contains(twelonelont.twelonelontId))
  }
}
