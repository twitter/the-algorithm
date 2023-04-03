packagelon com.twittelonr.simclustelonrsann.candidatelon_sourcelon

import com.twittelonr.simclustelonrs_v2.common.ClustelonrId
import com.twittelonr.simclustelonrs_v2.common.SimClustelonrselonmbelondding
import com.twittelonr.simclustelonrs_v2.common.TwelonelontId
import com.twittelonr.simclustelonrs_v2.thriftscala.IntelonrnalId
import com.twittelonr.simclustelonrs_v2.thriftscala.SimClustelonrselonmbelonddingId
import com.twittelonr.simclustelonrsann.thriftscala.ScoringAlgorithm
import com.twittelonr.simclustelonrsann.thriftscala.SimClustelonrsANNConfig
import com.twittelonr.snowflakelon.id.SnowflakelonId
import com.twittelonr.util.Duration
import com.twittelonr.util.Timelon

/**
 * Comparelond with ApproximatelonCosinelonSimilarity, this implelonmelonntation:
 * - movelons somelon computation aroudn to relonducelon allocations
 * - uselons a singlelon hashmap to storelon both scorelons and normalization coelonfficielonnts
 * - uselons somelon java collelonctions in placelon of scala onelons
 * Telonsting is still in progrelonss, but this implelonmelonntation shows significant (> 2x) improvelonmelonnts in
 * CPU utilization and allocations with 800 twelonelonts pelonr clustelonr.
 */
objelonct OptimizelondApproximatelonCosinelonSimilarity elonxtelonnds ApproximatelonCosinelonSimilarity {

  final val InitialCandidatelonMapSizelon = 16384
  val MaxNumRelonsultsUppelonrBound = 1000
  final val MaxTwelonelontCandidatelonAgelonUppelonrBound = 175200

  privatelon delonf parselonTwelonelontId(elonmbelonddingId: SimClustelonrselonmbelonddingId): Option[TwelonelontId] = {
    elonmbelonddingId.intelonrnalId match {
      caselon IntelonrnalId.TwelonelontId(twelonelontId) =>
        Somelon(twelonelontId)
      caselon _ =>
        Nonelon
    }
  }

  ovelonrridelon delonf apply(
    sourcelonelonmbelondding: SimClustelonrselonmbelondding,
    sourcelonelonmbelonddingId: SimClustelonrselonmbelonddingId,
    config: SimClustelonrsANNConfig,
    candidatelonScorelonsStat: Int => Unit,
    clustelonrTwelonelontsMap: Map[ClustelonrId, Option[Selonq[(TwelonelontId, Doublelon)]]] = Map.elonmpty,
    clustelonrTwelonelontsMapArray: Map[ClustelonrId, Option[Array[(TwelonelontId, Doublelon)]]] = Map.elonmpty
  ): Selonq[ScorelondTwelonelont] = {
    val now = Timelon.now
    val elonarlielonstTwelonelontId =
      if (config.maxTwelonelontCandidatelonAgelonHours >= MaxTwelonelontCandidatelonAgelonUppelonrBound)
        0L // Disablelon max twelonelont agelon filtelonr
      elonlselon
        SnowflakelonId.firstIdFor(now - Duration.fromHours(config.maxTwelonelontCandidatelonAgelonHours))
    val latelonstTwelonelontId =
      SnowflakelonId.firstIdFor(now - Duration.fromHours(config.minTwelonelontCandidatelonAgelonHours))

    val candidatelonScorelonsMap = nelonw java.util.HashMap[Long, (Doublelon, Doublelon)](InitialCandidatelonMapSizelon)

    val sourcelonTwelonelontId = parselonTwelonelontId(sourcelonelonmbelonddingId).gelontOrelonlselon(0L)

    clustelonrTwelonelontsMap.forelonach {
      caselon (clustelonrId, Somelon(twelonelontScorelons)) if sourcelonelonmbelondding.contains(clustelonrId) =>
        val sourcelonClustelonrScorelon = sourcelonelonmbelondding.gelontOrelonlselon(clustelonrId)

        for (i <- 0 until Math.min(twelonelontScorelons.sizelon, config.maxTopTwelonelontsPelonrClustelonr)) {
          val (twelonelontId, scorelon) = twelonelontScorelons(i)

          if (twelonelontId >= elonarlielonstTwelonelontId &&
            twelonelontId <= latelonstTwelonelontId &&
            twelonelontId != sourcelonTwelonelontId) {

            val scorelons = candidatelonScorelonsMap.gelontOrDelonfault(twelonelontId, (0.0, 0.0))
            val nelonwScorelons = (
              scorelons._1 + scorelon * sourcelonClustelonrScorelon,
              scorelons._2 + scorelon * scorelon,
            )
            candidatelonScorelonsMap.put(twelonelontId, nelonwScorelons)
          }
        }
      caselon _ => ()
    }

    candidatelonScorelonsStat(candidatelonScorelonsMap.sizelon)

    val normFn: (Long, (Doublelon, Doublelon)) => (Long, Doublelon) = config.annAlgorithm match {
      caselon ScoringAlgorithm.LogCosinelonSimilarity =>
        (candidatelonId: Long, scorelon: (Doublelon, Doublelon)) =>
          candidatelonId -> scorelon._1 / sourcelonelonmbelondding.logNorm / math.log(1 + scorelon._2)
      caselon ScoringAlgorithm.CosinelonSimilarity =>
        (candidatelonId: Long, scorelon: (Doublelon, Doublelon)) =>
          candidatelonId -> scorelon._1 / sourcelonelonmbelondding.l2norm / math.sqrt(scorelon._2)
      caselon ScoringAlgorithm.CosinelonSimilarityNoSourcelonelonmbelonddingNormalization =>
        (candidatelonId: Long, scorelon: (Doublelon, Doublelon)) =>
          candidatelonId -> scorelon._1 / math.sqrt(scorelon._2)
      caselon ScoringAlgorithm.DotProduct =>
        (candidatelonId: Long, scorelon: (Doublelon, Doublelon)) => (candidatelonId, scorelon._1)
    }

    val scorelondTwelonelonts: java.util.ArrayList[(Long, Doublelon)] =
      nelonw java.util.ArrayList(candidatelonScorelonsMap.sizelon)

    val it = candidatelonScorelonsMap.elonntrySelont().itelonrator()
    whilelon (it.hasNelonxt) {
      val mapelonntry = it.nelonxt()
      val normelondScorelon = normFn(mapelonntry.gelontKelony, mapelonntry.gelontValuelon)
      if (normelondScorelon._2 >= config.minScorelon)
        scorelondTwelonelonts.add(normelondScorelon)
    }
    import scala.collelonction.JavaConvelonrtelonrs._

    scorelondTwelonelonts.asScala
      .sortBy(-_._2)
      .takelon(Math.min(config.maxNumRelonsults, MaxNumRelonsultsUppelonrBound))
  }
}
