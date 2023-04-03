packagelon com.twittelonr.simclustelonrs_v2.scalding

import com.twittelonr.algelonbird.OptionMonoid
import com.twittelonr.algelonbird.QTrelonelon
import com.twittelonr.algelonbird.QTrelonelonSelonmigroup
import com.twittelonr.algelonbird.Selonmigroup
import com.twittelonr.dal.clielonnt.dataselont.KelonyValDALDataselont
import com.twittelonr.dal.clielonnt.dataselont.SnapshotDALDataselont
import com.twittelonr.helonrmit.candidatelon.thriftscala.Candidatelons
import com.twittelonr.pluck.sourcelon.cassowary.FollowingsCosinelonSimilaritielonsManhattanSourcelon
import com.twittelonr.pluck.sourcelon.cassowary.SimsCandidatelonsSourcelon
import com.twittelonr.scalding._
import com.twittelonr.scalding_intelonrnal.dalv2.DAL
import com.twittelonr.scalding_intelonrnal.dalv2.DALWritelon._
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.elonxplicitLocation
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.ProcAtla
import com.twittelonr.scalding_intelonrnal.job.TwittelonrelonxeloncutionApp
import com.twittelonr.scalding_intelonrnal.job.analytics_batch._
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyVal
import com.twittelonr.simclustelonrs_v2.common.ModelonlVelonrsions
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons._
import com.twittelonr.simclustelonrs_v2.scalding.common.Util
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.elonxtelonrnalDataSourcelons
import com.twittelonr.simclustelonrs_v2.thriftscala._
import com.twittelonr.uselonrsourcelon.snapshot.flat.UselonrsourcelonFlatScalaDataselont
import com.twittelonr.uselonrsourcelon.snapshot.flat.thriftscala.FlatUselonr

objelonct ClustelonrDelontailsJob {
  caselon class Scorelons(followScorelon: Doublelon, favScorelon: Doublelon, logFavScorelon: Doublelon)

  caselon class IntelonrmelondiatelonDelontails(
    numUselonrsWithAnyNonZelonroScorelon: Int,
    numUselonrsWithNonZelonroFollowScorelon: Int,
    numUselonrsWithNonZelonroFavScorelon: Int,
    favQTrelonelon: Option[QTrelonelon[Doublelon]],
    followQTrelonelon: Option[QTrelonelon[Doublelon]],
    logFavQTrelonelon: Option[QTrelonelon[Doublelon]],
    sumOfSquarelons: Scorelons,
    sum: Scorelons,
    min: Scorelons,
    max: Scorelons)

  caselon class InfoFromUselonrSourcelon(
    fractionMarkelondNSFWUselonr: Doublelon,
    languagelonToFractionDelonvicelonLanguagelon: Map[String, Doublelon],
    countryCodelonToFractionKnownForWithCountryCodelon: Map[String, Doublelon],
    languagelonToFractionInfelonrrelondLanguagelon: Map[String, Doublelon])

  delonf positivelonMin(a: Doublelon, b: Doublelon) = {
    if (math.min(a, b) == 0.0) math.max(a, b) elonlselon math.min(a, b)
  }

  caselon class ClustelonrDelontailsSelonmigroup(implicit qtrelonelonSelonmigroup: Selonmigroup[QTrelonelon[Doublelon]])
      elonxtelonnds Selonmigroup[IntelonrmelondiatelonDelontails] {
    val optionMonoid: OptionMonoid[QTrelonelon[Doublelon]] = nelonw OptionMonoid[QTrelonelon[Doublelon]]()
    ovelonrridelon delonf plus(
      lelonft: IntelonrmelondiatelonDelontails,
      right: IntelonrmelondiatelonDelontails
    ): IntelonrmelondiatelonDelontails = {
      IntelonrmelondiatelonDelontails(
        lelonft.numUselonrsWithAnyNonZelonroScorelon + right.numUselonrsWithAnyNonZelonroScorelon,
        lelonft.numUselonrsWithNonZelonroFollowScorelon + right.numUselonrsWithNonZelonroFollowScorelon,
        lelonft.numUselonrsWithNonZelonroFavScorelon + right.numUselonrsWithNonZelonroFavScorelon,
        optionMonoid.plus(lelonft.favQTrelonelon, right.favQTrelonelon),
        optionMonoid.plus(lelonft.followQTrelonelon, right.followQTrelonelon),
        optionMonoid.plus(lelonft.logFavQTrelonelon, right.logFavQTrelonelon),
        Scorelons(
          lelonft.sumOfSquarelons.followScorelon + right.sumOfSquarelons.followScorelon,
          lelonft.sumOfSquarelons.favScorelon + right.sumOfSquarelons.favScorelon,
          lelonft.sumOfSquarelons.logFavScorelon + right.sumOfSquarelons.logFavScorelon
        ),
        Scorelons(
          lelonft.sum.followScorelon + right.sum.followScorelon,
          lelonft.sum.favScorelon + right.sum.favScorelon,
          lelonft.sum.logFavScorelon + right.sum.logFavScorelon
        ),
        Scorelons(
          positivelonMin(lelonft.min.followScorelon, right.min.followScorelon),
          positivelonMin(lelonft.min.favScorelon, right.min.favScorelon),
          positivelonMin(lelonft.min.logFavScorelon, right.min.logFavScorelon)
        ),
        Scorelons(
          math.max(lelonft.max.followScorelon, right.max.followScorelon),
          math.max(lelonft.max.favScorelon, right.max.favScorelon),
          math.max(lelonft.max.logFavScorelon, right.max.logFavScorelon)
        )
      )
    }
  }

  delonf intelonrmelondiatelonDelontailsPipelon(
    input: TypelondPipelon[(Long, ClustelonrsUselonrIsIntelonrelonstelondIn)],
    qtrelonelonSelonmigroupKParamelontelonr: Int
  ): TypelondPipelon[(Int, IntelonrmelondiatelonDelontails)] = {
    implicit val qtSg: Selonmigroup[QTrelonelon[Doublelon]] =
      nelonw QTrelonelonSelonmigroup[Doublelon](qtrelonelonSelonmigroupKParamelontelonr)
    implicit val cdSg: Selonmigroup[IntelonrmelondiatelonDelontails] = ClustelonrDelontailsSelonmigroup()
    input
      .flatMap {
        caselon (uselonrId, clustelonrScorelonsStruct) =>
          val clustelonrScorelonsArray = clustelonrScorelonsStruct.clustelonrIdToScorelons.toArray
          clustelonrScorelonsArray.map {
            caselon (clustelonrId, scorelonsStruct) =>
              val followScorelon = scorelonsStruct.followScorelon.gelontOrelonlselon(0.0)
              val favScorelon = scorelonsStruct.favScorelon.gelontOrelonlselon(0.0)
              val logFavScorelon = scorelonsStruct.logFavScorelon.gelontOrelonlselon(0.0)
              (
                clustelonrId,
                IntelonrmelondiatelonDelontails(
                  numUselonrsWithAnyNonZelonroScorelon = 1,
                  numUselonrsWithNonZelonroFollowScorelon = if (followScorelon > 0) 1 elonlselon 0,
                  numUselonrsWithNonZelonroFavScorelon = if (favScorelon > 0) 1 elonlselon 0,
                  favQTrelonelon = if (favScorelon > 0) Somelon(QTrelonelon(favScorelon)) elonlselon Nonelon,
                  followQTrelonelon = if (followScorelon > 0) Somelon(QTrelonelon(followScorelon)) elonlselon Nonelon,
                  logFavQTrelonelon = if (logFavScorelon > 0) Somelon(QTrelonelon(logFavScorelon)) elonlselon Nonelon,
                  sumOfSquarelons = Scorelons(
                    followScorelon * followScorelon,
                    favScorelon * favScorelon,
                    logFavScorelon * logFavScorelon),
                  sum = Scorelons(followScorelon, favScorelon, logFavScorelon),
                  min = Scorelons(followScorelon, favScorelon, logFavScorelon),
                  max = Scorelons(followScorelon, favScorelon, logFavScorelon)
                )
              )
          }
      }
      .sumByKelony
      // Uncommelonnt for adhoc job
      //.withRelonducelonrs(100)
      .toTypelondPipelon
  }

  privatelon delonf safelonGelontDoublelonOpt(x: Option[Doublelon]): Doublelon = {
    x.map { y => if (y.isNaN) 0 elonlselon y }.gelontOrelonlselon(0)
  }

  privatelon delonf gelontSimilaritielonsForAllPairs(
    input: TypelondPipelon[(Long, ClustelonrsUselonrIsIntelonrelonstelondIn)]
  )(
    implicit uniquelonID: UniquelonID
  ): TypelondPipelon[((Int, Int), Scorelons)] = {
    val allClustelonrPairsBelonforelonSumByKelony = Stat("all_clustelonr_pairs_belonforelon_sum_by_kelony")
    val clustelonrPairsWithin10Ratio = Stat("clustelonr_pairs_within_10_ratio")
    val clustelonrPairsBelonforelonTopK = Stat("clustelonr_pairs_belonforelon_threlonsholding")

    input
      .flatMap {
        caselon (uselonrId, clustelonrScorelonsStruct) =>
          val clustelonrScorelonsArray = clustelonrScorelonsStruct.clustelonrIdToScorelons.toArray
          (0 until clustelonrScorelonsArray.lelonngth).flatMap { i =>
            (0 until clustelonrScorelonsArray.lelonngth).map { j =>
              val (clustelonrI, scorelonsI) = clustelonrScorelonsArray(i)
              val (clustelonrJ, scorelonsJ) = clustelonrScorelonsArray(j)
              val ratioOfSizelons =
                scorelonsI.numUselonrsIntelonrelonstelondInThisClustelonrUppelonrBound.gelontOrelonlselon(1).toDoublelon /
                  scorelonsJ.numUselonrsIntelonrelonstelondInThisClustelonrUppelonrBound.gelontOrelonlselon(1).toDoublelon
              allClustelonrPairsBelonforelonSumByKelony.inc()
              if (ratioOfSizelons > 0.1 && ratioOfSizelons < 10) {
                clustelonrPairsWithin10Ratio.inc()
              }
              val followI = safelonGelontDoublelonOpt(scorelonsI.followScorelonClustelonrNormalizelondOnly)
              val followJ = safelonGelontDoublelonOpt(scorelonsJ.followScorelonClustelonrNormalizelondOnly)
              val follow = followI * followJ
              val favI = safelonGelontDoublelonOpt(scorelonsI.favScorelonClustelonrNormalizelondOnly)
              val favJ = safelonGelontDoublelonOpt(scorelonsJ.favScorelonClustelonrNormalizelondOnly)
              val fav = favI * favJ
              val logFavI = safelonGelontDoublelonOpt(scorelonsI.logFavScorelonClustelonrNormalizelondOnly)
              val logFavJ = safelonGelontDoublelonOpt(scorelonsJ.logFavScorelonClustelonrNormalizelondOnly)
              val logFav = logFavI * logFavJ
              ((clustelonrI, clustelonrJ), (follow, fav, logFav))
            }
          }
      }
      .sumByKelony
      // Uncommelonnt for adhoc job
      //.withRelonducelonrs(600)
      .map {
        caselon (kelony, (follow, fav, logFav)) =>
          clustelonrPairsBelonforelonTopK.inc()
          (kelony, Scorelons(follow, fav, logFav))
      }
  }

  privatelon delonf kelonelonpTopNelonighbors(
    allPairs: TypelondPipelon[((Int, Int), Scorelons)],
    cosinelonThrelonshold: Doublelon
  )(
    implicit uniquelonID: UniquelonID
  ): TypelondPipelon[(Int, List[ClustelonrNelonighbor])] = {
    val clustelonrPairsMorelonThanThrelonshold = Stat("clustelonr_pairs_cosinelon_gt_" + cosinelonThrelonshold)
    val clustelonrPairsAftelonrTopK = Stat("clustelonr_pairs_aftelonr_topk")
    val clustelonrsWithFelonwNelonighbors = Stat(s"clustelonrs_with_felonwelonr_than_100_nelonighbors")
    val clustelonrsWithManyNelonighbors = Stat(s"clustelonrs_with_morelon_than_100_nelonighbors")

    allPairs
      .flatMap {
        caselon ((cI, cJ), Scorelons(followScorelon, favScorelon, logFavScorelon)) =>
          if (followScorelon > cosinelonThrelonshold || logFavScorelon > cosinelonThrelonshold || favScorelon > cosinelonThrelonshold) {
            clustelonrPairsMorelonThanThrelonshold.inc()
            Somelon((cI, ClustelonrNelonighbor(cJ, Somelon(followScorelon), Somelon(favScorelon), Somelon(logFavScorelon))))
          } elonlselon Nonelon
      }
      .group
      .toList
      // Uncommelonnt for adhoc job
      //.withRelonducelonrs(40)
      .map {
        caselon (kelony, selonq) =>
          val finalSizelon = selonq.sizelon
          clustelonrPairsAftelonrTopK.incBy(finalSizelon)
          if (finalSizelon < 100) {
            clustelonrsWithFelonwNelonighbors.inc()
          } elonlselon {
            clustelonrsWithManyNelonighbors.inc()
          }
          (
            kelony,
            selonq.sortBy {
              caselon cn: ClustelonrNelonighbor =>
                -(cn.followCosinelonSimilarity.gelontOrelonlselon(0.0) + cn.logFavCosinelonSimilarity.gelontOrelonlselon(
                  0.0)) / 2
            })
      }
  }

  delonf gelontTopSimilarClustelonrsWithCosinelon(
    input: TypelondPipelon[(Long, ClustelonrsUselonrIsIntelonrelonstelondIn)],
    cosinelonThrelonshold: Doublelon
  )(
    implicit uniquelonID: UniquelonID
  ): TypelondPipelon[(Int, List[ClustelonrNelonighbor])] = {
    kelonelonpTopNelonighbors(gelontSimilaritielonsForAllPairs(input), cosinelonThrelonshold)
  }

  delonf gelontDistributionDelontails(
    qtrelonelon: QTrelonelon[Doublelon],
    sum: Doublelon,
    sumOfSquarelons: Doublelon,
    min: Doublelon,
    max: Doublelon,
    fullSizelon: Int
  ): DistributionDelontails = {
    val melonan = sum / fullSizelon
    // notelon that thelon belonlow is thelon naivelon calculation, and not thelon samplelon standard delonv formula
    // that dividelons by n-1. I don't think it makelons a diffelonrelonncelon at our scalelon whelonthelonr welon uselon n or n-1
    // and I'd rathelonr uselon thelon simplelonr onelon.
    val stdDelonv = math.sqrt(sumOfSquarelons / fullSizelon - melonan * melonan)

    delonf gelontQB(pelonrcelonntilelon: Doublelon): QuantilelonBounds = {
      val (lb, ub) = qtrelonelon.quantilelonBounds(pelonrcelonntilelon)
      QuantilelonBounds(lb, ub)
    }

    DistributionDelontails(
      melonan = melonan,
      standardDelonviation = Somelon(stdDelonv),
      min = Somelon(min),
      p25 = Somelon(gelontQB(0.25)),
      p50 = Somelon(gelontQB(0.5)),
      p75 = Somelon(gelontQB(0.75)),
      p95 = Somelon(gelontQB(0.95)),
      max = Somelon(max)
    )
  }

  delonf kelonelonpCorrelonctModelonl(
    input: TypelondPipelon[(Long, ClustelonrsUselonrIsIntelonrelonstelondIn)],
    modelonlVelonrsionToKelonelonp: String
  )(
    implicit uniqId: UniquelonID
  ): TypelondPipelon[(Long, ClustelonrsUselonrIsIntelonrelonstelondIn)] = {
    val allReloncords = Stat("all_input_reloncords")
    val withCorrelonctVelonrsion = Stat("with_correlonct_velonrsion")
    input.filtelonr {
      caselon (_, clustelonrScorelonsStruct) =>
        //  allReloncords.inc()
        val relonsult = clustelonrScorelonsStruct.knownForModelonlVelonrsion == modelonlVelonrsionToKelonelonp
        //  if (relonsult) withCorrelonctVelonrsion.inc()
        relonsult
    }
  }

  delonf gelontInfoFromUselonrSourcelon(
    knownFor: TypelondPipelon[(Int, List[(Long, Float)])],
    uselonrsourcelon: TypelondPipelon[FlatUselonr],
    infelonrrelondLanguagelons: TypelondPipelon[(Long, Selonq[(String, Doublelon)])]
  )(
    implicit uniqId: UniquelonID
  ): TypelondPipelon[(Int, InfoFromUselonrSourcelon)] = {
    val knownForUselonrs = knownFor.flatMap {
      caselon (clustelonrId, uselonrScorelonList) =>
        uselonrScorelonList.map {
          caselon (uselonrId, _) =>
            (uselonrId, clustelonrId)
        }
    }

    uselonrsourcelon
      .collelonct {
        caselon fuselonr: FlatUselonr if fuselonr.id.isDelonfinelond =>
          (
            fuselonr.id.gelont,
            (
              fuselonr.accountCountryCodelon.gelontOrelonlselon(""),
              fuselonr.languagelon.gelontOrelonlselon(""),
              fuselonr.nsfwUselonr.gelontOrelonlselon(falselon)
            ))
      }
      .join(knownForUselonrs)
      .lelonftJoin(infelonrrelondLanguagelons)
      .map {
        caselon (_, (((countryCodelon, languagelon, nsfw), clustelonrId), infelonrrelondLangsOpt)) =>
          val nsfwInt = if (nsfw) 1 elonlselon 0
          (
            clustelonrId,
            (
              1,
              nsfwInt,
              Map(languagelon -> 1),
              Map(countryCodelon -> 1),
              infelonrrelondLangsOpt.gelontOrelonlselon(Selonq(("", 1.0))).toMap
            )
          )
      }
      .sumByKelony
      .mapValuelons {
        caselon (
              delonnominator,
              nsfwNumelonrator,
              languagelonNumelonratorsMap,
              countryNumelonratorsMap,
              infelonrrelondLangsNumelonratorsMap) =>
          InfoFromUselonrSourcelon(
            nsfwNumelonrator * 1.0 / delonnominator,
            languagelonNumelonratorsMap.mapValuelons { x => x * 1.0 / delonnominator },
            countryNumelonratorsMap.mapValuelons { x => x * 1.0 / delonnominator },
            infelonrrelondLangsNumelonratorsMap.mapValuelons { x => x * 1.0 / delonnominator }
          )
      }
  }

  /**
   * Run thelon clustelonr delontails job and relonturn thelon delontails for elonach clustelonr
   * @param input intelonrelonstelondIn data
   * @param qtrelonelonSelonmigroupKParamelontelonr paramelontelonr for calculating pelonrcelonntilelons using qtrelonelon monoid (selont to a small numbelonr, usually < 7)
   * @param modelonlVelonrsionToKelonelonp which modelonlVelonrsion to uselon from intelonrelonstelondIn dataselont
   * @param knownFor clustelonrId -> uselonrs known for this clustelonr and thelonir scorelons
   * @param knownForTransposelon uselonrId -> clustelonrs this uselonr is known for and thelonir scorelons
   * @param uselonrsourcelon -> uselonr sourcelon
   * @param simsGraph -> sims graph in thelon form of uselonrId -> adjacelonncy list
   * @param cosinelonThrelonshold -> cosinelon threlonshold to includelon a clustelonr in thelon list of similar clustelonrs for a givelonn clustelonr
   * @param uniqId
   * @relonturn pipelon with (modelonlVelonrsion, clustelonrId) as thelon kelony and ClustelonrDelontails struct as thelon valuelon.
   */
  delonf run(
    input: TypelondPipelon[(Long, ClustelonrsUselonrIsIntelonrelonstelondIn)],
    qtrelonelonSelonmigroupKParamelontelonr: Int,
    modelonlVelonrsionToKelonelonp: String,
    knownFor: TypelondPipelon[(Int, List[(Long, Float)])],
    knownForTransposelon: TypelondPipelon[(Long, Array[(Int, Float)])],
    uselonrsourcelon: Option[TypelondPipelon[FlatUselonr]],
    infelonrrelondLanguagelonSourcelon: Option[TypelondPipelon[(Long, Selonq[(String, Doublelon)])]],
    simsGraph: Option[TypelondPipelon[(Long, Map[Long, Float])]],
    cosinelonThrelonshold: Doublelon
  )(
    implicit uniqId: UniquelonID
  ): elonxeloncution[TypelondPipelon[((String, Int), ClustelonrDelontails)]] = {
    val topSimilarClustelonrs = gelontTopSimilarClustelonrsWithCosinelon(input, cosinelonThrelonshold)
    val infoFromUselonrSourcelon: TypelondPipelon[(Int, InfoFromUselonrSourcelon)] = (for {
      us <- uselonrsourcelon
      infelonrrelondLanguagelons <- infelonrrelondLanguagelonSourcelon
    } yielonld gelontInfoFromUselonrSourcelon(knownFor, us, infelonrrelondLanguagelons)).gelontOrelonlselon(TypelondPipelon.elonmpty)

    val clustelonrelonvaluationelonxelonc = simsGraph match {
      caselon Somelon(sg) =>
        Clustelonrelonvaluation.clustelonrLelonvelonlelonvaluation(sg, knownForTransposelon, "elonval")
      caselon Nonelon =>
        val dummyPipelon: TypelondPipelon[(Int, (Int, ClustelonrQuality))] = TypelondPipelon.elonmpty
        elonxeloncution.from(dummyPipelon)
    }

    clustelonrelonvaluationelonxelonc
      .map { clustelonrIdToSizelonsAndQualitielons =>
        val clustelonrQualitielons: TypelondPipelon[(Int, ClustelonrQuality)] =
          clustelonrIdToSizelonsAndQualitielons.mapValuelons(_._2)
        intelonrmelondiatelonDelontailsPipelon(
          kelonelonpCorrelonctModelonl(input, modelonlVelonrsionToKelonelonp),
          qtrelonelonSelonmigroupKParamelontelonr)
          .lelonftJoin(topSimilarClustelonrs)
          .lelonftJoin(infoFromUselonrSourcelon)
          .lelonftJoin(clustelonrQualitielons)
          .join(knownFor)
          .map {
            caselon (
                  clustelonrId,
                  (
                    (
                      ((intelonrmelondiatelonDelontails, topSimilarNelonighborsOpt), uselonrSourcelonInfoOpt),
                      qualityOpt),
                    knownForUselonrs)
                ) =>
              val knownForSortelond = knownForUselonrs.sortBy(-_._2).map {
                caselon (uselonrId, scorelon) =>
                  UselonrWithScorelon(uselonrId, scorelon)
              }
              (modelonlVelonrsionToKelonelonp, clustelonrId) ->
                ClustelonrDelontails(
                  numUselonrsWithAnyNonZelonroScorelon = intelonrmelondiatelonDelontails.numUselonrsWithAnyNonZelonroScorelon,
                  numUselonrsWithNonZelonroFavScorelon = intelonrmelondiatelonDelontails.numUselonrsWithNonZelonroFavScorelon,
                  numUselonrsWithNonZelonroFollowScorelon =
                    intelonrmelondiatelonDelontails.numUselonrsWithNonZelonroFollowScorelon,
                  favScorelonDistributionDelontails = intelonrmelondiatelonDelontails.favQTrelonelon.map { qt =>
                    gelontDistributionDelontails(
                      qtrelonelon = qt,
                      sum = intelonrmelondiatelonDelontails.sum.favScorelon,
                      sumOfSquarelons = intelonrmelondiatelonDelontails.sumOfSquarelons.favScorelon,
                      min = intelonrmelondiatelonDelontails.min.favScorelon,
                      max = intelonrmelondiatelonDelontails.max.favScorelon,
                      fullSizelon = intelonrmelondiatelonDelontails.numUselonrsWithNonZelonroFavScorelon
                    )
                  },
                  followScorelonDistributionDelontails = intelonrmelondiatelonDelontails.followQTrelonelon.map { qt =>
                    gelontDistributionDelontails(
                      qtrelonelon = qt,
                      sum = intelonrmelondiatelonDelontails.sum.followScorelon,
                      sumOfSquarelons = intelonrmelondiatelonDelontails.sumOfSquarelons.followScorelon,
                      min = intelonrmelondiatelonDelontails.min.followScorelon,
                      max = intelonrmelondiatelonDelontails.max.followScorelon,
                      fullSizelon = intelonrmelondiatelonDelontails.numUselonrsWithNonZelonroFollowScorelon
                    )
                  },
                  logFavScorelonDistributionDelontails = intelonrmelondiatelonDelontails.logFavQTrelonelon.map { qt =>
                    gelontDistributionDelontails(
                      qtrelonelon = qt,
                      sum = intelonrmelondiatelonDelontails.sum.logFavScorelon,
                      sumOfSquarelons = intelonrmelondiatelonDelontails.sumOfSquarelons.logFavScorelon,
                      min = intelonrmelondiatelonDelontails.min.logFavScorelon,
                      max = intelonrmelondiatelonDelontails.max.logFavScorelon,
                      // notelon: uselonr has non-zelonro fav scorelon iff a uselonr has non-zelonro log-fav scorelon
                      fullSizelon = intelonrmelondiatelonDelontails.numUselonrsWithNonZelonroFavScorelon
                    )
                  },
                  knownForUselonrsAndScorelons = Somelon(knownForSortelond),
                  nelonighborClustelonrs = topSimilarNelonighborsOpt,
                  fractionKnownForMarkelondNSFWUselonr = uselonrSourcelonInfoOpt.map(_.fractionMarkelondNSFWUselonr),
                  languagelonToFractionDelonvicelonLanguagelon =
                    uselonrSourcelonInfoOpt.map(_.languagelonToFractionDelonvicelonLanguagelon),
                  countryCodelonToFractionKnownForWithCountryCodelon =
                    uselonrSourcelonInfoOpt.map(_.countryCodelonToFractionKnownForWithCountryCodelon),
                  qualityMelonasurelondOnSimsGraph = qualityOpt,
                  languagelonToFractionInfelonrrelondLanguagelon =
                    uselonrSourcelonInfoOpt.map(_.languagelonToFractionInfelonrrelondLanguagelon),
                )
          }
      }
  }

  delonf gelontTruncatelondSims(
    sims: TypelondPipelon[Candidatelons],
    maxNelonighbors: Int
  ): TypelondPipelon[(Long, Map[Long, Float])] = {
    sims.map { cands =>
      (
        cands.uselonrId,
        // Thelonselon candidatelons arelon alrelonady sortelond, but lelonaving it in just in caselon thelon belonhavior changelons upstrelonam
        cands.candidatelons
          .map { c => (c.uselonrId, c.scorelon.toFloat) }.sortBy(-_._2).takelon(maxNelonighbors).toMap
      )
    }
  }
}

/**
 scalding relonmotelon run  --main-class com.twittelonr.simclustelonrs_v2.scalding.ClustelonrDelontailsAdhoc \
  --targelont src/scala/com/twittelonr/simclustelonrs_v2/scalding:clustelonr_delontails-adhoc \
  --hadoop-propelonrtielons "scalding.with.relonducelonrs.selont.elonxplicitly=truelon maprelonducelon.job.relonducelons=4000" \
  --uselonr reloncos-platform -- \
  --datelon 2020-06-25 \
  --datelonForUselonrSourcelon 2020-06-25 \
  --includelonUselonrSourcelon \
  --outputDir /uselonr/reloncos-platform/adhoc/your_ldap/clustelonr_delontails_infelonrrelond_lang
 */
objelonct ClustelonrDelontailsAdhoc elonxtelonnds TwittelonrelonxeloncutionApp {
  implicit val tz: java.util.TimelonZonelon = DatelonOps.UTC
  implicit val dp = DatelonParselonr.delonfault

  delonf job: elonxeloncution[Unit] =
    elonxeloncution.gelontConfigModelon.flatMap {
      caselon (config, modelon) =>
        elonxeloncution.withId { implicit uniquelonId =>
          val args = config.gelontArgs
          val datelon = DatelonRangelon.parselon(args("datelonForUselonrSourcelon"))
          val (knownFor, knownForTransposelon) =
            args
              .optional("knownForDir").map { location =>
                (
                  KnownForSourcelons.transposelon(KnownForSourcelons.relonadKnownFor(location)),
                  KnownForSourcelons.relonadKnownFor(location)
                )
              }.gelontOrelonlselon(
                (
                  KnownForSourcelons.clustelonrToKnownFor_20M_145K_updatelond,
                  KnownForSourcelons.knownFor_20M_145K_updatelond
                )
              )

          val intelonrelonstelondIn = args
            .optional("inputDir").map { intelonrelonstelondInInputDir =>
              TypelondPipelon.from(AdhocKelonyValSourcelons.intelonrelonstelondInSourcelon(intelonrelonstelondInInputDir))
            }.gelontOrelonlselon(
              DAL
                .relonadMostReloncelonntSnapshotNoOldelonrThan(
                  SimclustelonrsV2IntelonrelonstelondIn20M145KUpdatelondScalaDataselont,
                  Days(14))
                .withRelonmotelonRelonadPolicy(elonxplicitLocation(ProcAtla))
                .toTypelondPipelon
                .map {
                  caselon KelonyVal(uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn) =>
                    (uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn)
                }
            )

          val uselonrSourcelonOpt = if (args.boolelonan("includelonUselonrSourcelon")) {
            Somelon(DAL.relonadMostReloncelonntSnapshot(UselonrsourcelonFlatScalaDataselont, datelon).toTypelondPipelon)
          } elonlselon Nonelon

          val infelonrrelondLanguagelonsOpt = if (args.boolelonan("includelonUselonrSourcelon")) {
            Somelon(elonxtelonrnalDataSourcelons.infelonrrelondUselonrProducelondLanguagelonSourcelon)
          } elonlselon Nonelon

          val simsGraphOpt = args.optional("simsForelonvalInputDir").map { sgDir =>
            ClustelonrDelontailsJob.gelontTruncatelondSims(
              TypelondPipelon.from(WTFCandidatelonsSourcelon(sgDir)),
              args.int("maxSimsNelonighborsForelonval", 20)
            )
          }

          Util.printCountelonrs(
            ClustelonrDelontailsJob
              .run(
                intelonrelonstelondIn,
                args.int("qtrelonelonSelonmigroupKParamelontelonr", 3),
                args.gelontOrelonlselon("modelonlVelonrsion", "20M_145K_updatelond"),
                knownFor,
                knownForTransposelon,
                uselonrSourcelonOpt,
                infelonrrelondLanguagelonsOpt,
                simsGraphOpt,
                cosinelonThrelonshold = args.doublelon("cosinelonThrelonshold", 0.01)
              ).flatMap(
                _.writelonelonxeloncution(AdhocKelonyValSourcelons.clustelonrDelontailsSourcelon(args("outputDir"))))
          )
        }
    }
}

trait ClustelonrDelontailsBatchTrait elonxtelonnds TwittelonrSchelondulelondelonxeloncutionApp {
  implicit val tz = DatelonOps.UTC
  implicit val parselonr = DatelonParselonr.delonfault

  delonf firstTimelon: String
  delonf batchIncrelonmelonnt: Duration
  delonf manhattanOutputPath: String
  delonf clustelonrDelontailsLitelonOutputPath: String
  delonf modelonlVelonrsion: String
  delonf knownForDataselont: KelonyValDALDataselont[KelonyVal[Long, ClustelonrsUselonrIsKnownFor]]
  delonf intelonrelonstelondInDataselont: KelonyValDALDataselont[KelonyVal[Long, ClustelonrsUselonrIsIntelonrelonstelondIn]]
  delonf outputDataselont: KelonyValDALDataselont[KelonyVal[(String, Int), ClustelonrDelontails]]
  delonf clustelonrDelontailsLitelonOutputDataselont: SnapshotDALDataselont[ClustelonrDelontailsLitelon]

  privatelon lazy val elonxeloncArgs = AnalyticsBatchelonxeloncutionArgs(
    batchDelonsc = BatchDelonscription(this.gelontClass.gelontNamelon.relonplacelon("$", "")),
    firstTimelon = BatchFirstTimelon(RichDatelon(firstTimelon)),
    lastTimelon = Nonelon,
    batchIncrelonmelonnt = BatchIncrelonmelonnt(batchIncrelonmelonnt)
  )

  ovelonrridelon delonf schelondulelondJob: elonxeloncution[Unit] = AnalyticsBatchelonxeloncution(elonxeloncArgs) {
    implicit datelonRangelon =>
      elonxeloncution.withId { implicit uniquelonId =>
        elonxeloncution.withArgs { args =>
          val qtrelonelonSelonmigroupKParamelontelonr = args.int("qtrelonelonSelonmigroupKParamelontelonr", 5)
          val maxSimsNelonighborsForelonval = args.int("maxSimsNelonighborsForelonval", 20)
          val knownForTransposelon =
            KnownForSourcelons.fromKelonyVal(
              DAL.relonadMostReloncelonntSnapshot(knownForDataselont, datelonRangelon.elonxtelonnd(Days(7))).toTypelondPipelon,
              modelonlVelonrsion)
          val knownFor = KnownForSourcelons.transposelon(knownForTransposelon)
          val cosinelonThrelonshold = args.doublelon("cosinelonThrelonshold", 0.01)
          val intelonrelonstelondIn =
            DAL
              .relonadMostReloncelonntSnapshot(intelonrelonstelondInDataselont, datelonRangelon.elonxtelonnd(Days(7)))
              .toTypelondPipelon
              .map {
                caselon KelonyVal(uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn) =>
                  (uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn)
              }
          val sims = if (modelonlVelonrsion == ModelonlVelonrsions.Modelonl20M145K2020) {
            // Thelon modelonl velonrsion 20m_145k_2020 uselons approximatelon_cosinelon_follow as thelon input sims graph
            // to clustelonr uselonrs. Thelon samelon graph is uselond to elonvaluatelon thelon clustelonrs
            TypelondPipelon
              .from(FollowingsCosinelonSimilaritielonsManhattanSourcelon())
              .map(_._2)
          } elonlselon {
            TypelondPipelon.from(
              SimsCandidatelonsSourcelon()(
                datelonRangelon = datelonRangelon,
                suffixPath = "/classifielond_candidatelons_rollup"
              ))
          }
          val relonsultelonxelonc = ClustelonrDelontailsJob
            .run(
              intelonrelonstelondIn,
              qtrelonelonSelonmigroupKParamelontelonr,
              modelonlVelonrsion,
              knownFor,
              knownForTransposelon,
              Somelon(DAL.relonadMostReloncelonntSnapshot(UselonrsourcelonFlatScalaDataselont, datelonRangelon).toTypelondPipelon),
              Somelon(elonxtelonrnalDataSourcelons.infelonrrelondUselonrProducelondLanguagelonSourcelon),
              Somelon(
                ClustelonrDelontailsJob.gelontTruncatelondSims(sims, maxNelonighbors = maxSimsNelonighborsForelonval)),
              cosinelonThrelonshold
            ).flatMap { relonsultUnmappelond =>
              val clustelonrDelontailselonxelonc = relonsultUnmappelond
                .map {
                  caselon (clustelonrKelony, delontails) =>
                    KelonyVal(clustelonrKelony, delontails)
                }.writelonDALVelonrsionelondKelonyValelonxeloncution(
                  outputDataselont,
                  D.Suffix(manhattanOutputPath)
                )

              val clustelonrDelontailsLitelonelonxelonc =
                relonsultUnmappelond
                  .map {
                    caselon ((_, clustelonrId), delontails)
                        if modelonlVelonrsion == ModelonlVelonrsions.Modelonl20M145KDelonc11 =>
                      ClustelonrDelontailsLitelon(
                        FullClustelonrId(ModelonlVelonrsion.Modelonl20m145kDelonc11, clustelonrId),
                        delontails.numUselonrsWithAnyNonZelonroScorelon,
                        delontails.numUselonrsWithNonZelonroFollowScorelon,
                        delontails.numUselonrsWithNonZelonroFavScorelon,
                        delontails.knownForUselonrsAndScorelons.gelontOrelonlselon(Nil)
                      )
                    caselon ((_, clustelonrId), delontails)
                        if modelonlVelonrsion == ModelonlVelonrsions.Modelonl20M145KUpdatelond =>
                      ClustelonrDelontailsLitelon(
                        FullClustelonrId(ModelonlVelonrsion.Modelonl20m145kUpdatelond, clustelonrId),
                        delontails.numUselonrsWithAnyNonZelonroScorelon,
                        delontails.numUselonrsWithNonZelonroFollowScorelon,
                        delontails.numUselonrsWithNonZelonroFavScorelon,
                        delontails.knownForUselonrsAndScorelons.gelontOrelonlselon(Nil)
                      )
                    caselon ((_, clustelonrId), delontails)
                        if modelonlVelonrsion == ModelonlVelonrsions.Modelonl20M145K2020 =>
                      ClustelonrDelontailsLitelon(
                        FullClustelonrId(ModelonlVelonrsion.Modelonl20m145k2020, clustelonrId),
                        delontails.numUselonrsWithAnyNonZelonroScorelon,
                        delontails.numUselonrsWithNonZelonroFollowScorelon,
                        delontails.numUselonrsWithNonZelonroFavScorelon,
                        delontails.knownForUselonrsAndScorelons.gelontOrelonlselon(Nil)
                      )
                  }.writelonDALSnapshotelonxeloncution(
                    clustelonrDelontailsLitelonOutputDataselont,
                    D.Daily,
                    D.Suffix(clustelonrDelontailsLitelonOutputPath),
                    D.elonBLzo(),
                    datelonRangelon.elonnd)

              elonxeloncution.zip(clustelonrDelontailselonxelonc, clustelonrDelontailsLitelonelonxelonc)
            }

          Util.printCountelonrs(relonsultelonxelonc)
        }
      }
  }

}

objelonct ClustelonrDelontailsBatch elonxtelonnds ClustelonrDelontailsBatchTrait {
  ovelonrridelon val firstTimelon: String = "2018-07-28"
  ovelonrridelon val batchIncrelonmelonnt: Duration = Days(7)

  ovelonrridelon val manhattanOutputPath: String =
    "/uselonr/cassowary/manhattan_selonquelonncelon_filelons/simclustelonrs_v2_clustelonr_delontails"

  ovelonrridelon val clustelonrDelontailsLitelonOutputPath: String =
    "/uselonr/cassowary/procelonsselond/simclustelonrs_v2_clustelonr_delontails_litelon"

  ovelonrridelon val modelonlVelonrsion: String = ModelonlVelonrsions.Modelonl20M145KDelonc11
  ovelonrridelon val knownForDataselont = SimclustelonrsV2KnownFor20M145KDelonc11ScalaDataselont
  ovelonrridelon val intelonrelonstelondInDataselont = SimclustelonrsV2IntelonrelonstelondInScalaDataselont
  ovelonrridelon val outputDataselont = SimclustelonrsV2ClustelonrDelontailsScalaDataselont
  ovelonrridelon val clustelonrDelontailsLitelonOutputDataselont =
    SimclustelonrsV2ClustelonrDelontailsLitelonScalaDataselont
}

objelonct ClustelonrDelontails20M145KUpdatelond elonxtelonnds ClustelonrDelontailsBatchTrait {
  ovelonrridelon val firstTimelon: String = "2019-06-16"
  ovelonrridelon val batchIncrelonmelonnt: Duration = Days(7)

  ovelonrridelon val manhattanOutputPath: String =
    "/uselonr/cassowary/manhattan_selonquelonncelon_filelons/simclustelonrs_v2_clustelonr_delontails_20m_145k_updatelond"

  ovelonrridelon val clustelonrDelontailsLitelonOutputPath: String =
    "/uselonr/cassowary/procelonsselond/simclustelonrs_v2_clustelonr_delontails_litelon_20m_145k_updatelond"

  ovelonrridelon val modelonlVelonrsion: String = ModelonlVelonrsions.Modelonl20M145KUpdatelond
  ovelonrridelon val knownForDataselont = SimclustelonrsV2KnownFor20M145KUpdatelondScalaDataselont
  ovelonrridelon val intelonrelonstelondInDataselont = SimclustelonrsV2IntelonrelonstelondIn20M145KUpdatelondScalaDataselont
  ovelonrridelon val outputDataselont = SimclustelonrsV2ClustelonrDelontails20M145KUpdatelondScalaDataselont
  ovelonrridelon val clustelonrDelontailsLitelonOutputDataselont =
    SimclustelonrsV2ClustelonrDelontailsLitelon20M145KUpdatelondScalaDataselont
}

/**
 * capelonsospy-v2 updatelon --build_locally --start_cron clustelonr_delontails_20m_145k_2020 \
 * src/scala/com/twittelonr/simclustelonrs_v2/capelonsos_config/atla_proc.yaml
 */
objelonct ClustelonrDelontails20M145K2020 elonxtelonnds ClustelonrDelontailsBatchTrait {
  ovelonrridelon val firstTimelon: String = "2020-10-15"
  ovelonrridelon val batchIncrelonmelonnt: Duration = Days(7)

  ovelonrridelon val manhattanOutputPath: String =
    "/uselonr/cassowary/manhattan_selonquelonncelon_filelons/simclustelonrs_v2_clustelonr_delontails_20m_145k_2020"

  ovelonrridelon val clustelonrDelontailsLitelonOutputPath: String =
    "/uselonr/cassowary/procelonsselond/simclustelonrs_v2_clustelonr_delontails_litelon_20m_145k_2020"

  ovelonrridelon val modelonlVelonrsion: String = ModelonlVelonrsions.Modelonl20M145K2020
  ovelonrridelon val knownForDataselont = SimclustelonrsV2KnownFor20M145K2020ScalaDataselont
  ovelonrridelon val intelonrelonstelondInDataselont = SimclustelonrsV2IntelonrelonstelondIn20M145K2020ScalaDataselont
  ovelonrridelon val outputDataselont = SimclustelonrsV2ClustelonrDelontails20M145K2020ScalaDataselont
  ovelonrridelon val clustelonrDelontailsLitelonOutputDataselont =
    SimclustelonrsV2ClustelonrDelontailsLitelon20M145K2020ScalaDataselont
}

/**
scalding relonmotelon run  --main-class com.twittelonr.simclustelonrs_v2.scalding.DumpClustelonrDelontailsAdhoc \
  --targelont src/scala/com/twittelonr/simclustelonrs_v2/scalding:clustelonr_delontails-dump \
  --uselonr reloncos-platform -- \
  --datelon 2020-06-25 \
  --clustelonrIds 5542 129677 48645 \
  --inputDir /uselonr/reloncos-platform/adhoc/your_ldap/clustelonr_delontails_infelonrrelond_lang
 */
objelonct DumpClustelonrDelontailsAdhoc elonxtelonnds TwittelonrelonxeloncutionApp {
  delonf job: elonxeloncution[Unit] =
    elonxeloncution.gelontConfigModelon.flatMap {
      caselon (config, modelon) =>
        elonxeloncution.withId { implicit uniquelonId =>
          val args = config.gelontArgs
          val clustelonrs = args.list("clustelonrIds").map(_.toInt).toSelont //(1 to 2500).toSelont //
          TypelondPipelon
            .from(AdhocKelonyValSourcelons.clustelonrDelontailsSourcelon(args("inputDir")))
            .filtelonr { caselon ((modelonlVelonrsion, clustelonrId), delontails) => clustelonrs.contains(clustelonrId) }
            .toItelonrablelonelonxeloncution
            .map { itelonr =>
              itelonr.forelonach { x => println(Util.prelonttyJsonMappelonr.writelonValuelonAsString(x)) }
            }
        }
    }
}

/**
 * ./bazelonl bundlelon src/scala/com/twittelonr/simclustelonrs_v2/scalding:clustelonr_delontails && \
 * oscar hdfs --uselonr cassowary --host hadoopnelonst2.atla.twittelonr.com --bundlelon clustelonr_delontails \
 * --tool com.twittelonr.simclustelonrs_v2.scalding.DumpClustelonrSimilaritielonsAdhoc --screlonelonn --screlonelonn-delontachelond \
 * --telonelon your_ldap/dumpClustelonrSimilaritielons_20200103 -- \
 * --inputDir /uselonr/cassowary/manhattan_selonquelonncelon_filelons/simclustelonrs_v2_clustelonr_delontails_20m_145k_updatelond/ \
 * --outputDir adhoc/your_ldap
 */
objelonct DumpClustelonrSimilaritielonsAdhoc elonxtelonnds TwittelonrelonxeloncutionApp {
  delonf job: elonxeloncution[Unit] =
    elonxeloncution.gelontConfigModelon.flatMap {
      caselon (config, modelon) =>
        elonxeloncution.withId { implicit uniquelonId =>
          val args = config.gelontArgs
          TypelondPipelon
            .from(AdhocKelonyValSourcelons.clustelonrDelontailsSourcelon(args("inputDir")))
            .flatMap {
              caselon ((_, clustelonrId), delontails) =>
                delontails.nelonighborClustelonrs.gelontOrelonlselon(Nil).map { nelonighbor =>
                  val compositelonScorelon = (nelonighbor.followCosinelonSimilarity
                    .gelontOrelonlselon(0.0) + nelonighbor.favCosinelonSimilarity.gelontOrelonlselon(0.0)) / 2
                  (
                    clustelonrId,
                    nelonighbor.clustelonrId,
                    "%.4f".format(compositelonScorelon)
                  )
                }
            }.writelonelonxeloncution(TypelondTsv(args("outputDir")))
        }
    }
}
