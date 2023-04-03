packagelon com.twittelonr.simclustelonrs_v2.candidatelon_sourcelon

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.frigatelon.common.baselon.CandidatelonSourcelon
import com.twittelonr.frigatelon.common.baselon.Stats
import com.twittelonr.simclustelonrs_v2.candidatelon_sourcelon.HelonavyRankelonr.UniformScorelonStorelonRankelonr
import com.twittelonr.simclustelonrs_v2.candidatelon_sourcelon.SimClustelonrsANNCandidatelonSourcelon.SimClustelonrsANNConfig
import com.twittelonr.simclustelonrs_v2.candidatelon_sourcelon.SimClustelonrsANNCandidatelonSourcelon.SimClustelonrsTwelonelontCandidatelon
import com.twittelonr.simclustelonrs_v2.common.ModelonlVelonrsions._
import com.twittelonr.simclustelonrs_v2.common.ClustelonrId
import com.twittelonr.simclustelonrs_v2.common.SimClustelonrselonmbelondding
import com.twittelonr.simclustelonrs_v2.common.TwelonelontId
import com.twittelonr.simclustelonrs_v2.summingbird.storelons.ClustelonrKelony
import com.twittelonr.simclustelonrs_v2.thriftscala.elonmbelonddingTypelon
import com.twittelonr.simclustelonrs_v2.thriftscala.IntelonrnalId
import com.twittelonr.simclustelonrs_v2.thriftscala.ScorelonIntelonrnalId
import com.twittelonr.simclustelonrs_v2.thriftscala.ScoringAlgorithm
import com.twittelonr.simclustelonrs_v2.thriftscala.SimClustelonrselonmbelonddingId
import com.twittelonr.simclustelonrs_v2.thriftscala.SimClustelonrselonmbelonddingPairScorelonId
import com.twittelonr.simclustelonrs_v2.thriftscala.{Scorelon => ThriftScorelon}
import com.twittelonr.simclustelonrs_v2.thriftscala.{ScorelonId => ThriftScorelonId}
import com.twittelonr.snowflakelon.id.SnowflakelonId
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.util.Duration
import com.twittelonr.util.Futurelon
import com.twittelonr.util.Timelon
import scala.collelonction.mutablelon

/**
 * This storelon looks for twelonelonts whoselon similarity is closelon to a Sourcelon SimClustelonrselonmbelonddingId.
 *
 * Approximatelon cosinelon similarity is thelon corelon algorithm to drivelon this storelon.
 *
 * Stelonp 1 - 4 arelon in "felontchCandidatelons" melonthod.
 * 1. Relontrielonvelon thelon SimClustelonrs elonmbelondding by thelon SimClustelonrselonmbelonddingId
 * 2. Felontch top N clustelonrs' top twelonelonts from thelon clustelonrTwelonelontCandidatelonsStorelon (TopTwelonelontsPelonrClustelonr indelonx).
 * 3. Calculatelon all thelon twelonelont candidatelons' dot-product or approximatelon cosinelon similarity to sourcelon twelonelonts.
 * 4. Takelon top M twelonelont candidatelons by thelon stelonp 3's scorelon
 * Stelonp 5-6 arelon in "relonranking" melonthod.
 * 5. Calculatelon thelon similarity scorelon belontwelonelonn sourcelon and candidatelons.
 * 6. Relonturn top N candidatelons by thelon stelonp 5's scorelon.
 *
 * Warning: Only turn off thelon stelonp 5 for Uselonr IntelonrelonstelondIn candidatelon gelonnelonration. It's thelon only uselon
 * caselon in Reloncos that welon uselon dot-product to rank thelon twelonelont candidatelons.
 */
caselon class SimClustelonrsANNCandidatelonSourcelon(
  clustelonrTwelonelontCandidatelonsStorelon: RelonadablelonStorelon[ClustelonrKelony, Selonq[(TwelonelontId, Doublelon)]],
  simClustelonrselonmbelonddingStorelon: RelonadablelonStorelon[SimClustelonrselonmbelonddingId, SimClustelonrselonmbelondding],
  helonavyRankelonr: HelonavyRankelonr.HelonavyRankelonr,
  configs: Map[elonmbelonddingTypelon, SimClustelonrsANNConfig],
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds CandidatelonSourcelon[SimClustelonrsANNCandidatelonSourcelon.Quelonry, SimClustelonrsTwelonelontCandidatelon] {

  import SimClustelonrsANNCandidatelonSourcelon._

  ovelonrridelon val namelon: String = this.gelontClass.gelontNamelon
  privatelon val stats = statsReloncelonivelonr.scopelon(this.gelontClass.gelontNamelon)

  privatelon val felontchSourcelonelonmbelonddingStat = stats.scopelon("felontchSourcelonelonmbelondding")
  protelonctelond val felontchCandidatelonelonmbelonddingsStat = stats.scopelon("felontchCandidatelonelonmbelonddings")
  privatelon val felontchCandidatelonsStat = stats.scopelon("felontchCandidatelons")
  privatelon val relonrankingStat = stats.scopelon("relonranking")

  ovelonrridelon delonf gelont(
    quelonry: SimClustelonrsANNCandidatelonSourcelon.Quelonry
  ): Futurelon[Option[Selonq[SimClustelonrsTwelonelontCandidatelon]]] = {
    val sourcelonelonmbelonddingId = quelonry.sourcelonelonmbelonddingId
    loadConfig(quelonry) match {
      caselon Somelon(config) =>
        for {
          maybelonSimClustelonrselonmbelondding <- Stats.track(felontchSourcelonelonmbelonddingStat) {
            simClustelonrselonmbelonddingStorelon.gelont(quelonry.sourcelonelonmbelonddingId)
          }
          maybelonFiltelonrelondCandidatelons <- maybelonSimClustelonrselonmbelondding match {
            caselon Somelon(sourcelonelonmbelondding) =>
              for {
                rawCandidatelons <- Stats.trackSelonq(felontchCandidatelonsStat) {
                  felontchCandidatelons(sourcelonelonmbelonddingId, config, sourcelonelonmbelondding)
                }
                rankelondCandidatelons <- Stats.trackSelonq(relonrankingStat) {
                  relonranking(sourcelonelonmbelonddingId, config, rawCandidatelons)
                }
              } yielonld {
                felontchCandidatelonsStat
                  .stat(
                    sourcelonelonmbelonddingId.elonmbelonddingTypelon.namelon,
                    sourcelonelonmbelonddingId.modelonlVelonrsion.namelon).add(rankelondCandidatelons.sizelon)
                Somelon(rankelondCandidatelons)
              }
            caselon Nonelon =>
              felontchCandidatelonsStat
                .stat(
                  sourcelonelonmbelonddingId.elonmbelonddingTypelon.namelon,
                  sourcelonelonmbelonddingId.modelonlVelonrsion.namelon).add(0)
              Futurelon.Nonelon
          }
        } yielonld {
          maybelonFiltelonrelondCandidatelons
        }
      caselon _ =>
        // Skip ovelonr quelonrielons whoselon config is not delonfinelond
        Futurelon.Nonelon
    }
  }

  privatelon delonf felontchCandidatelons(
    sourcelonelonmbelonddingId: SimClustelonrselonmbelonddingId,
    config: SimClustelonrsANNConfig,
    sourcelonelonmbelondding: SimClustelonrselonmbelondding
  ): Futurelon[Selonq[SimClustelonrsTwelonelontCandidatelon]] = {
    val now = Timelon.now
    val elonarlielonstTwelonelontId = SnowflakelonId.firstIdFor(now - config.maxTwelonelontCandidatelonAgelon)
    val latelonstTwelonelontId = SnowflakelonId.firstIdFor(now - config.minTwelonelontCandidatelonAgelon)
    val clustelonrIds =
      sourcelonelonmbelondding
        .truncatelon(config.maxScanClustelonrs).clustelonrIds
        .map { clustelonrId: ClustelonrId =>
          ClustelonrKelony(clustelonrId, sourcelonelonmbelonddingId.modelonlVelonrsion, config.candidatelonelonmbelonddingTypelon)
        }.toSelont

    Futurelon
      .collelonct {
        clustelonrTwelonelontCandidatelonsStorelon.multiGelont(clustelonrIds)
      }.map { clustelonrTwelonelontsMap =>
        // Uselon Mutablelon map to optimizelon pelonrformancelon. Thelon melonthod is threlonad-safelon.
        // Selont initial map sizelon to around p75 of map sizelon distribution to avoid too many copying
        // from elonxtelonnding thelon sizelon of thelon mutablelon hashmap
        val candidatelonScorelonsMap =
          nelonw SimClustelonrsANNCandidatelonSourcelon.HashMap[TwelonelontId, Doublelon](InitialCandidatelonMapSizelon)
        val candidatelonNormalizationMap =
          nelonw SimClustelonrsANNCandidatelonSourcelon.HashMap[TwelonelontId, Doublelon](InitialCandidatelonMapSizelon)

        clustelonrTwelonelontsMap.forelonach {
          caselon (ClustelonrKelony(clustelonrId, _, _, _), Somelon(twelonelontScorelons))
              if sourcelonelonmbelondding.contains(clustelonrId) =>
            val sourcelonClustelonrScorelon = sourcelonelonmbelondding.gelontOrelonlselon(clustelonrId)

            for (i <- 0 until Math.min(twelonelontScorelons.sizelon, config.maxTopTwelonelontsPelonrClustelonr)) {
              val (twelonelontId, scorelon) = twelonelontScorelons(i)

              if (!parselonTwelonelontId(sourcelonelonmbelonddingId).contains(twelonelontId) &&
                twelonelontId >= elonarlielonstTwelonelontId && twelonelontId <= latelonstTwelonelontId) {
                candidatelonScorelonsMap.put(
                  twelonelontId,
                  candidatelonScorelonsMap.gelontOrelonlselon(twelonelontId, 0.0) + scorelon * sourcelonClustelonrScorelon)
                if (config.elonnablelonPartialNormalization) {
                  candidatelonNormalizationMap
                    .put(twelonelontId, candidatelonNormalizationMap.gelontOrelonlselon(twelonelontId, 0.0) + scorelon * scorelon)
                }
              }
            }
          caselon _ => ()
        }

        stats.stat("candidatelonScorelonsMap").add(candidatelonScorelonsMap.sizelon)
        stats.stat("candidatelonNormalizationMap").add(candidatelonNormalizationMap.sizelon)

        // Relon-Rank thelon candidatelon by configuration
        val procelonsselondCandidatelonScorelons = candidatelonScorelonsMap.map {
          caselon (candidatelonId, scorelon) =>
            // elonnablelon Partial Normalization
            val procelonsselondScorelon =
              if (config.elonnablelonPartialNormalization) {
                // Welon applielond thelon "log" velonrsion of partial normalization whelonn welon rank candidatelons
                // by log cosinelon similarity
                if (config.rankingAlgorithm == ScoringAlgorithm.PairelonmbelonddingLogCosinelonSimilarity) {
                  scorelon / sourcelonelonmbelondding.l2norm / math.log(
                    1 + candidatelonNormalizationMap(candidatelonId))
                } elonlselon {
                  scorelon / sourcelonelonmbelondding.l2norm / math.sqrt(candidatelonNormalizationMap(candidatelonId))
                }
              } elonlselon scorelon
            SimClustelonrsTwelonelontCandidatelon(candidatelonId, procelonsselondScorelon, sourcelonelonmbelonddingId)
        }.toSelonq

        procelonsselondCandidatelonScorelons
          .sortBy(-_.scorelon)
      }
  }

  privatelon delonf relonranking(
    sourcelonelonmbelonddingId: SimClustelonrselonmbelonddingId,
    config: SimClustelonrsANNConfig,
    candidatelons: Selonq[SimClustelonrsTwelonelontCandidatelon]
  ): Futurelon[Selonq[SimClustelonrsTwelonelontCandidatelon]] = {
    val rankelondCandidatelons = if (config.elonnablelonHelonavyRanking) {
      helonavyRankelonr
        .rank(
          scoringAlgorithm = config.rankingAlgorithm,
          sourcelonelonmbelonddingId = sourcelonelonmbelonddingId,
          candidatelonelonmbelonddingTypelon = config.candidatelonelonmbelonddingTypelon,
          minScorelon = config.minScorelon,
          candidatelons = candidatelons.takelon(config.maxRelonRankingCandidatelons)
        ).map(_.sortBy(-_.scorelon))
    } elonlselon {
      Futurelon.valuelon(candidatelons)
    }
    rankelondCandidatelons.map(_.takelon(config.maxNumRelonsults))
  }

  privatelon[candidatelon_sourcelon] delonf loadConfig(quelonry: Quelonry): Option[SimClustelonrsANNConfig] = {
    configs.gelont(quelonry.sourcelonelonmbelonddingId.elonmbelonddingTypelon).map { baselonConfig =>
      // apply ovelonrridelons if any
      quelonry.ovelonrridelonConfig match {
        caselon Somelon(ovelonrridelons) =>
          baselonConfig.copy(
            maxNumRelonsults = ovelonrridelons.maxNumRelonsults.gelontOrelonlselon(baselonConfig.maxNumRelonsults),
            maxTwelonelontCandidatelonAgelon =
              ovelonrridelons.maxTwelonelontCandidatelonAgelon.gelontOrelonlselon(baselonConfig.maxTwelonelontCandidatelonAgelon),
            minScorelon = ovelonrridelons.minScorelon.gelontOrelonlselon(baselonConfig.minScorelon),
            candidatelonelonmbelonddingTypelon =
              ovelonrridelons.candidatelonelonmbelonddingTypelon.gelontOrelonlselon(baselonConfig.candidatelonelonmbelonddingTypelon),
            elonnablelonPartialNormalization =
              ovelonrridelons.elonnablelonPartialNormalization.gelontOrelonlselon(baselonConfig.elonnablelonPartialNormalization),
            elonnablelonHelonavyRanking =
              ovelonrridelons.elonnablelonHelonavyRanking.gelontOrelonlselon(baselonConfig.elonnablelonHelonavyRanking),
            rankingAlgorithm = ovelonrridelons.rankingAlgorithm.gelontOrelonlselon(baselonConfig.rankingAlgorithm),
            maxRelonRankingCandidatelons =
              ovelonrridelons.maxRelonRankingCandidatelons.gelontOrelonlselon(baselonConfig.maxRelonRankingCandidatelons),
            maxTopTwelonelontsPelonrClustelonr =
              ovelonrridelons.maxTopTwelonelontsPelonrClustelonr.gelontOrelonlselon(baselonConfig.maxTopTwelonelontsPelonrClustelonr),
            maxScanClustelonrs = ovelonrridelons.maxScanClustelonrs.gelontOrelonlselon(baselonConfig.maxScanClustelonrs),
            minTwelonelontCandidatelonAgelon =
              ovelonrridelons.minTwelonelontCandidatelonAgelon.gelontOrelonlselon(baselonConfig.minTwelonelontCandidatelonAgelon)
          )
        caselon _ => baselonConfig
      }
    }
  }
}

objelonct SimClustelonrsANNCandidatelonSourcelon {

  final val ProductionMaxNumRelonsults = 200
  final val InitialCandidatelonMapSizelon = 16384

  delonf apply(
    clustelonrTwelonelontCandidatelonsStorelon: RelonadablelonStorelon[ClustelonrKelony, Selonq[(TwelonelontId, Doublelon)]],
    simClustelonrselonmbelonddingStorelon: RelonadablelonStorelon[SimClustelonrselonmbelonddingId, SimClustelonrselonmbelondding],
    uniformScoringStorelon: RelonadablelonStorelon[ThriftScorelonId, ThriftScorelon],
    configs: Map[elonmbelonddingTypelon, SimClustelonrsANNConfig],
    statsReloncelonivelonr: StatsReloncelonivelonr
  ) = nelonw SimClustelonrsANNCandidatelonSourcelon(
    clustelonrTwelonelontCandidatelonsStorelon = clustelonrTwelonelontCandidatelonsStorelon,
    simClustelonrselonmbelonddingStorelon = simClustelonrselonmbelonddingStorelon,
    helonavyRankelonr = nelonw UniformScorelonStorelonRankelonr(uniformScoringStorelon, statsReloncelonivelonr),
    configs = configs,
    statsReloncelonivelonr = statsReloncelonivelonr
  )

  privatelon delonf parselonTwelonelontId(elonmbelonddingId: SimClustelonrselonmbelonddingId): Option[TwelonelontId] = {
    elonmbelonddingId.intelonrnalId match {
      caselon IntelonrnalId.TwelonelontId(twelonelontId) =>
        Somelon(twelonelontId)
      caselon _ =>
        Nonelon
    }
  }

  caselon class Quelonry(
    sourcelonelonmbelonddingId: SimClustelonrselonmbelonddingId,
    // Only ovelonrridelon thelon config in DDG and Delonbuggelonrs.
    // Uselon Post-filtelonr for thelon holdbacks for belonttelonr cachelon hit ratelon.
    ovelonrridelonConfig: Option[SimClustelonrsANNConfigOvelonrridelon] = Nonelon)

  caselon class SimClustelonrsTwelonelontCandidatelon(
    twelonelontId: TwelonelontId,
    scorelon: Doublelon,
    sourcelonelonmbelonddingId: SimClustelonrselonmbelonddingId)

  class HashMap[A, B](initSizelon: Int) elonxtelonnds mutablelon.HashMap[A, B] {
    ovelonrridelon delonf initialSizelon: Int = initSizelon // 16 - by delonfault
  }

  /**
   * Thelon Configuration of elonach SimClustelonrs ANN Candidatelon Sourcelon.
   * elonxpelonct Onelon SimClustelonrs elonmbelondding Typelon mapping to a SimClustelonrs ANN Configuration in Production.
   */
  caselon class SimClustelonrsANNConfig(
    // Thelon max numbelonr of candidatelons for a ANN Quelonry
    // Plelonaselon don't ovelonrridelon this valuelon in Production.
    maxNumRelonsults: Int = ProductionMaxNumRelonsults,
    // Thelon max twelonelont candidatelon duration from now.
    maxTwelonelontCandidatelonAgelon: Duration,
    // Thelon min scorelon of thelon candidatelons
    minScorelon: Doublelon,
    // Thelon Candidatelon elonmbelondding Typelon of Twelonelont.
    candidatelonelonmbelonddingTypelon: elonmbelonddingTypelon,
    // elonnablelons normalization of approximatelon SimClustelonrs velonctors to relonmovelon popularity bias
    elonnablelonPartialNormalization: Boolelonan,
    // Whelonthelonr to elonnablelon elonmbelondding Similarity ranking
    elonnablelonHelonavyRanking: Boolelonan,
    // Thelon ranking algorithm for Sourcelon Candidatelon Similarity
    rankingAlgorithm: ScoringAlgorithm,
    // Thelon max numbelonr of candidatelons in RelonRanking Stelonp
    maxRelonRankingCandidatelons: Int,
    // Thelon max numbelonr of Top Twelonelonts from elonvelonry clustelonr twelonelont indelonx
    maxTopTwelonelontsPelonrClustelonr: Int,
    // Thelon max numbelonr of Clustelonrs in thelon sourcelon elonmbelonddings.
    maxScanClustelonrs: Int,
    // Thelon min twelonelont candidatelon duration from now.
    minTwelonelontCandidatelonAgelon: Duration)

  /**
   * Contains samelon fielonlds as [[SimClustelonrsANNConfig]], to speloncify which fielonlds arelon to belon ovelonrridelonn
   * for elonxpelonrimelonntal purposelons.
   *
   * All fielonlds in this class must belon optional.
   */
  caselon class SimClustelonrsANNConfigOvelonrridelon(
    maxNumRelonsults: Option[Int] = Nonelon,
    maxTwelonelontCandidatelonAgelon: Option[Duration] = Nonelon,
    minScorelon: Option[Doublelon] = Nonelon,
    candidatelonelonmbelonddingTypelon: Option[elonmbelonddingTypelon] = Nonelon,
    elonnablelonPartialNormalization: Option[Boolelonan] = Nonelon,
    elonnablelonHelonavyRanking: Option[Boolelonan] = Nonelon,
    rankingAlgorithm: Option[ScoringAlgorithm] = Nonelon,
    maxRelonRankingCandidatelons: Option[Int] = Nonelon,
    maxTopTwelonelontsPelonrClustelonr: Option[Int] = Nonelon,
    maxScanClustelonrs: Option[Int] = Nonelon,
    minTwelonelontCandidatelonAgelon: Option[Duration] = Nonelon,
    elonnablelonLookbackSourcelon: Option[Boolelonan] = Nonelon)

  final val DelonfaultMaxTopTwelonelontsPelonrClustelonr = 200
  final val DelonfaultelonnablelonHelonavyRanking = falselon
  objelonct SimClustelonrsANNConfig {
    val DelonfaultSimClustelonrsANNConfig: SimClustelonrsANNConfig =
      SimClustelonrsANNConfig(
        maxTwelonelontCandidatelonAgelon = 1.days,
        minScorelon = 0.7,
        candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
        elonnablelonPartialNormalization = truelon,
        elonnablelonHelonavyRanking = falselon,
        rankingAlgorithm = ScoringAlgorithm.PairelonmbelonddingCosinelonSimilarity,
        maxRelonRankingCandidatelons = 250,
        maxTopTwelonelontsPelonrClustelonr = 200,
        maxScanClustelonrs = 50,
        minTwelonelontCandidatelonAgelon = 0.selonconds
      )
  }

  val LookbackMelondiaMinDays: Int = 0
  val LookbackMelondiaMaxDays: Int = 2
  val LookbackMelondiaMaxTwelonelontsPelonrDay: Int = 2000
  val maxTopTwelonelontsPelonrClustelonr: Int =
    (LookbackMelondiaMaxDays - LookbackMelondiaMinDays + 1) * LookbackMelondiaMaxTwelonelontsPelonrDay

  val LookbackMelondiaTwelonelontConfig: Map[elonmbelonddingTypelon, SimClustelonrsANNConfig] = {
    val candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont
    val minTwelonelontAgelon = LookbackMelondiaMinDays.days
    val maxTwelonelontAgelon =
      LookbackMelondiaMaxDays.days - 1.hour // To compelonnsatelon for thelon cachelon TTL that might push thelon twelonelont agelon belonyond max agelon
    val rankingAlgorithm = ScoringAlgorithm.PairelonmbelonddingCosinelonSimilarity

    val maxScanClustelonrs = 50
    val minScorelon = 0.5
    Map(
      elonmbelonddingTypelon.FavBaselondProducelonr -> SimClustelonrsANNConfig(
        minTwelonelontCandidatelonAgelon = minTwelonelontAgelon,
        maxTwelonelontCandidatelonAgelon = maxTwelonelontAgelon,
        minScorelon =
          minScorelon, // for twistly candidatelons. To speloncify a highelonr threlonshold, uselon a post-filtelonr
        candidatelonelonmbelonddingTypelon = candidatelonelonmbelonddingTypelon,
        elonnablelonPartialNormalization = truelon,
        elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
        rankingAlgorithm = rankingAlgorithm,
        maxRelonRankingCandidatelons = 250,
        maxTopTwelonelontsPelonrClustelonr = maxTopTwelonelontsPelonrClustelonr,
        maxScanClustelonrs = maxScanClustelonrs,
      ),
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont -> SimClustelonrsANNConfig(
        minTwelonelontCandidatelonAgelon = minTwelonelontAgelon,
        maxTwelonelontCandidatelonAgelon = maxTwelonelontAgelon,
        minScorelon =
          minScorelon, // for twistly candidatelons. To speloncify a highelonr threlonshold, uselon a post-filtelonr
        candidatelonelonmbelonddingTypelon = candidatelonelonmbelonddingTypelon,
        elonnablelonPartialNormalization = truelon,
        elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
        rankingAlgorithm = rankingAlgorithm,
        maxRelonRankingCandidatelons = 250,
        maxTopTwelonelontsPelonrClustelonr = maxTopTwelonelontsPelonrClustelonr,
        maxScanClustelonrs = maxScanClustelonrs,
      ),
      elonmbelonddingTypelon.FavTfgTopic -> SimClustelonrsANNConfig(
        minTwelonelontCandidatelonAgelon = minTwelonelontAgelon,
        maxTwelonelontCandidatelonAgelon = maxTwelonelontAgelon,
        minScorelon = minScorelon,
        candidatelonelonmbelonddingTypelon = candidatelonelonmbelonddingTypelon,
        elonnablelonPartialNormalization = truelon,
        elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
        rankingAlgorithm = rankingAlgorithm,
        maxRelonRankingCandidatelons = 400,
        maxTopTwelonelontsPelonrClustelonr = 200,
        maxScanClustelonrs = maxScanClustelonrs,
      ),
      elonmbelonddingTypelon.LogFavBaselondKgoApelonTopic -> SimClustelonrsANNConfig(
        minTwelonelontCandidatelonAgelon = minTwelonelontAgelon,
        maxTwelonelontCandidatelonAgelon = maxTwelonelontAgelon,
        minScorelon = minScorelon,
        candidatelonelonmbelonddingTypelon = candidatelonelonmbelonddingTypelon,
        elonnablelonPartialNormalization = truelon,
        elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
        rankingAlgorithm = rankingAlgorithm,
        maxRelonRankingCandidatelons = 400,
        maxTopTwelonelontsPelonrClustelonr = 200,
        maxScanClustelonrs = maxScanClustelonrs,
      ),
    )
  }

  val DelonfaultConfigMappings: Map[elonmbelonddingTypelon, SimClustelonrsANNConfig] = Map(
    elonmbelonddingTypelon.FavBaselondProducelonr -> SimClustelonrsANNConfig(
      maxTwelonelontCandidatelonAgelon = 1.days,
      minScorelon = 0.0, // for twistly candidatelons. To speloncify a highelonr threlonshold, uselon a post-filtelonr
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
      elonnablelonPartialNormalization = truelon,
      elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairelonmbelonddingCosinelonSimilarity,
      maxRelonRankingCandidatelons = 250,
      maxTopTwelonelontsPelonrClustelonr = DelonfaultMaxTopTwelonelontsPelonrClustelonr,
      maxScanClustelonrs = 50,
      minTwelonelontCandidatelonAgelon = 0.selonconds
    ),
    elonmbelonddingTypelon.LogFavBaselondUselonrIntelonrelonstelondMaxpoolingAddrelonssBookFromIIAPelon -> SimClustelonrsANNConfig(
      maxTwelonelontCandidatelonAgelon = 1.days,
      minScorelon = 0.0, // for twistly candidatelons. To speloncify a highelonr threlonshold, uselon a post-filtelonr
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
      elonnablelonPartialNormalization = truelon,
      elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairelonmbelonddingCosinelonSimilarity,
      maxRelonRankingCandidatelons = 250,
      maxTopTwelonelontsPelonrClustelonr = DelonfaultMaxTopTwelonelontsPelonrClustelonr,
      maxScanClustelonrs = 50,
      minTwelonelontCandidatelonAgelon = 0.selonconds
    ),
    elonmbelonddingTypelon.LogFavBaselondUselonrIntelonrelonstelondAvelonragelonAddrelonssBookFromIIAPelon -> SimClustelonrsANNConfig(
      maxTwelonelontCandidatelonAgelon = 1.days,
      minScorelon = 0.0, // for twistly candidatelons. To speloncify a highelonr threlonshold, uselon a post-filtelonr
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
      elonnablelonPartialNormalization = truelon,
      elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairelonmbelonddingCosinelonSimilarity,
      maxRelonRankingCandidatelons = 250,
      maxTopTwelonelontsPelonrClustelonr = DelonfaultMaxTopTwelonelontsPelonrClustelonr,
      maxScanClustelonrs = 50,
      minTwelonelontCandidatelonAgelon = 0.selonconds
    ),
    elonmbelonddingTypelon.LogFavBaselondUselonrIntelonrelonstelondBooktypelonMaxpoolingAddrelonssBookFromIIAPelon -> SimClustelonrsANNConfig(
      maxTwelonelontCandidatelonAgelon = 1.days,
      minScorelon = 0.0, // for twistly candidatelons. To speloncify a highelonr threlonshold, uselon a post-filtelonr
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
      elonnablelonPartialNormalization = truelon,
      elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairelonmbelonddingCosinelonSimilarity,
      maxRelonRankingCandidatelons = 250,
      maxTopTwelonelontsPelonrClustelonr = DelonfaultMaxTopTwelonelontsPelonrClustelonr,
      maxScanClustelonrs = 50,
      minTwelonelontCandidatelonAgelon = 0.selonconds
    ),
    elonmbelonddingTypelon.LogFavBaselondUselonrIntelonrelonstelondLargelonstDimMaxpoolingAddrelonssBookFromIIAPelon -> SimClustelonrsANNConfig(
      maxTwelonelontCandidatelonAgelon = 1.days,
      minScorelon = 0.0, // for twistly candidatelons. To speloncify a highelonr threlonshold, uselon a post-filtelonr
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
      elonnablelonPartialNormalization = truelon,
      elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairelonmbelonddingCosinelonSimilarity,
      maxRelonRankingCandidatelons = 250,
      maxTopTwelonelontsPelonrClustelonr = DelonfaultMaxTopTwelonelontsPelonrClustelonr,
      maxScanClustelonrs = 50,
      minTwelonelontCandidatelonAgelon = 0.selonconds
    ),
    elonmbelonddingTypelon.LogFavBaselondUselonrIntelonrelonstelondLouvainMaxpoolingAddrelonssBookFromIIAPelon -> SimClustelonrsANNConfig(
      maxTwelonelontCandidatelonAgelon = 1.days,
      minScorelon = 0.0, // for twistly candidatelons. To speloncify a highelonr threlonshold, uselon a post-filtelonr
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
      elonnablelonPartialNormalization = truelon,
      elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairelonmbelonddingCosinelonSimilarity,
      maxRelonRankingCandidatelons = 250,
      maxTopTwelonelontsPelonrClustelonr = DelonfaultMaxTopTwelonelontsPelonrClustelonr,
      maxScanClustelonrs = 50,
      minTwelonelontCandidatelonAgelon = 0.selonconds
    ),
    elonmbelonddingTypelon.LogFavBaselondUselonrIntelonrelonstelondConnelonctelondMaxpoolingAddrelonssBookFromIIAPelon -> SimClustelonrsANNConfig(
      maxTwelonelontCandidatelonAgelon = 1.days,
      minScorelon = 0.0, // for twistly candidatelons. To speloncify a highelonr threlonshold, uselon a post-filtelonr
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
      elonnablelonPartialNormalization = truelon,
      elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairelonmbelonddingCosinelonSimilarity,
      maxRelonRankingCandidatelons = 250,
      maxTopTwelonelontsPelonrClustelonr = DelonfaultMaxTopTwelonelontsPelonrClustelonr,
      maxScanClustelonrs = 50,
      minTwelonelontCandidatelonAgelon = 0.selonconds
    ),
    elonmbelonddingTypelon.RelonlaxelondAggrelongatablelonLogFavBaselondProducelonr -> SimClustelonrsANNConfig(
      maxTwelonelontCandidatelonAgelon = 1.days,
      minScorelon = 0.25, // for twistly candidatelons. To speloncify a highelonr threlonshold, uselon a post-filtelonr
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
      elonnablelonPartialNormalization = truelon,
      elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairelonmbelonddingCosinelonSimilarity,
      maxRelonRankingCandidatelons = 250,
      maxTopTwelonelontsPelonrClustelonr = DelonfaultMaxTopTwelonelontsPelonrClustelonr,
      maxScanClustelonrs = 50,
      minTwelonelontCandidatelonAgelon = 0.selonconds
    ),
    elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont -> SimClustelonrsANNConfig(
      maxTwelonelontCandidatelonAgelon = 1.days,
      minScorelon = 0.3, // for twistly candidatelons. To speloncify a highelonr threlonshold, uselon a post-filtelonr
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
      elonnablelonPartialNormalization = truelon,
      elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairelonmbelonddingCosinelonSimilarity,
      maxRelonRankingCandidatelons = 400,
      maxTopTwelonelontsPelonrClustelonr = DelonfaultMaxTopTwelonelontsPelonrClustelonr,
      maxScanClustelonrs = 50,
      minTwelonelontCandidatelonAgelon = 0.selonconds
    ),
    elonmbelonddingTypelon.FiltelonrelondUselonrIntelonrelonstelondInFromPelon -> SimClustelonrsANNConfig(
      maxTwelonelontCandidatelonAgelon = 1.days,
      minScorelon = 0.7, // unuselond, helonavy ranking disablelond
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
      elonnablelonPartialNormalization = falselon,
      elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
      rankingAlgorithm =
        ScoringAlgorithm.PairelonmbelonddingCosinelonSimilarity, // Unuselond, helonavy ranking disablelond
      maxRelonRankingCandidatelons = 150, // unuselond, helonavy ranking disablelond
      maxTopTwelonelontsPelonrClustelonr = DelonfaultMaxTopTwelonelontsPelonrClustelonr,
      maxScanClustelonrs = 50,
      minTwelonelontCandidatelonAgelon = 0.selonconds
    ),
    elonmbelonddingTypelon.FiltelonrelondUselonrIntelonrelonstelondIn -> SimClustelonrsANNConfig(
      maxTwelonelontCandidatelonAgelon = 1.days,
      minScorelon = 0.7, // unuselond, helonavy ranking disablelond
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
      elonnablelonPartialNormalization = falselon,
      elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
      rankingAlgorithm =
        ScoringAlgorithm.PairelonmbelonddingCosinelonSimilarity, // Unuselond, helonavy ranking disablelond
      maxRelonRankingCandidatelons = 150, // unuselond, helonavy ranking disablelond
      maxTopTwelonelontsPelonrClustelonr = DelonfaultMaxTopTwelonelontsPelonrClustelonr,
      maxScanClustelonrs = 50,
      minTwelonelontCandidatelonAgelon = 0.selonconds
    ),
    elonmbelonddingTypelon.UnfiltelonrelondUselonrIntelonrelonstelondIn -> SimClustelonrsANNConfig(
      maxTwelonelontCandidatelonAgelon = 1.days,
      minScorelon = 0.0,
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
      elonnablelonPartialNormalization = truelon,
      elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairelonmbelonddingLogCosinelonSimilarity,
      maxRelonRankingCandidatelons = 400,
      maxTopTwelonelontsPelonrClustelonr = DelonfaultMaxTopTwelonelontsPelonrClustelonr,
      maxScanClustelonrs = 50,
      minTwelonelontCandidatelonAgelon = 0.selonconds
    ),
    elonmbelonddingTypelon.FollowBaselondUselonrIntelonrelonstelondInFromAPelon -> SimClustelonrsANNConfig(
      maxTwelonelontCandidatelonAgelon = 1.days,
      minScorelon = 0.0,
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
      elonnablelonPartialNormalization = truelon,
      elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairelonmbelonddingCosinelonSimilarity,
      maxRelonRankingCandidatelons = 200,
      maxTopTwelonelontsPelonrClustelonr = DelonfaultMaxTopTwelonelontsPelonrClustelonr,
      maxScanClustelonrs = 50,
      minTwelonelontCandidatelonAgelon = 0.selonconds
    ),
    elonmbelonddingTypelon.LogFavBaselondUselonrIntelonrelonstelondInFromAPelon -> SimClustelonrsANNConfig(
      maxTwelonelontCandidatelonAgelon = 1.days,
      minScorelon = 0.0,
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
      elonnablelonPartialNormalization = truelon,
      elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairelonmbelonddingCosinelonSimilarity,
      maxRelonRankingCandidatelons = 200,
      maxTopTwelonelontsPelonrClustelonr = DelonfaultMaxTopTwelonelontsPelonrClustelonr,
      maxScanClustelonrs = 50,
      minTwelonelontCandidatelonAgelon = 0.selonconds
    ),
    elonmbelonddingTypelon.FavTfgTopic -> SimClustelonrsANNConfig(
      maxTwelonelontCandidatelonAgelon = 1.days,
      minScorelon = 0.5,
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
      elonnablelonPartialNormalization = truelon,
      elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairelonmbelonddingCosinelonSimilarity,
      maxRelonRankingCandidatelons = 400,
      maxTopTwelonelontsPelonrClustelonr = DelonfaultMaxTopTwelonelontsPelonrClustelonr,
      maxScanClustelonrs = 50,
      minTwelonelontCandidatelonAgelon = 0.selonconds
    ),
    elonmbelonddingTypelon.LogFavBaselondKgoApelonTopic -> SimClustelonrsANNConfig(
      maxTwelonelontCandidatelonAgelon = 1.days,
      minScorelon = 0.5,
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
      elonnablelonPartialNormalization = truelon,
      elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairelonmbelonddingCosinelonSimilarity,
      maxRelonRankingCandidatelons = 400,
      maxTopTwelonelontsPelonrClustelonr = DelonfaultMaxTopTwelonelontsPelonrClustelonr,
      maxScanClustelonrs = 50,
      minTwelonelontCandidatelonAgelon = 0.selonconds
    ),
    elonmbelonddingTypelon.UselonrNelonxtIntelonrelonstelondIn -> SimClustelonrsANNConfig(
      maxTwelonelontCandidatelonAgelon = 1.days,
      minScorelon = 0.0,
      candidatelonelonmbelonddingTypelon = elonmbelonddingTypelon.LogFavBaselondTwelonelont,
      elonnablelonPartialNormalization = truelon,
      elonnablelonHelonavyRanking = DelonfaultelonnablelonHelonavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairelonmbelonddingCosinelonSimilarity,
      maxRelonRankingCandidatelons = 200,
      maxTopTwelonelontsPelonrClustelonr = DelonfaultMaxTopTwelonelontsPelonrClustelonr,
      maxScanClustelonrs = 50,
      minTwelonelontCandidatelonAgelon = 0.selonconds
    )
  )

  /**
   * Only cachelon thelon candidatelons if it's not Consumelonr-sourcelon. For elonxamplelon, TwelonelontSourcelon, ProducelonrSourcelon,
   * TopicSourcelon. Welon don't cachelon consumelonr-sourcelons (elon.g. UselonrIntelonrelonstelondIn) sincelon a cachelond consumelonr
   * objelonct is going rarelonly hit, sincelon it can't belon sharelond by multiplelon uselonrs.
   */
  val CachelonablelonShortTTLelonmbelonddingTypelons: Selont[elonmbelonddingTypelon] =
    Selont(
      elonmbelonddingTypelon.FavBaselondProducelonr,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
    )

  val CachelonablelonLongTTLelonmbelonddingTypelons: Selont[elonmbelonddingTypelon] =
    Selont(
      elonmbelonddingTypelon.FavTfgTopic,
      elonmbelonddingTypelon.LogFavBaselondKgoApelonTopic
    )
}
