packagelon com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.twicelon

import com.twittelonr.bijelonction.Injelonction
import com.twittelonr.dal.clielonnt.dataselont.KelonyValDALDataselont
import com.twittelonr.scalding.DatelonRangelon
import com.twittelonr.scalding.Days
import com.twittelonr.scalding.elonxeloncution
import com.twittelonr.scalding.Stat
import com.twittelonr.scalding.TypelondTsv
import com.twittelonr.scalding.UniquelonID
import com.twittelonr.scalding.typelond.TypelondPipelon
import com.twittelonr.scalding_intelonrnal.dalv2.DAL
import com.twittelonr.scalding_intelonrnal.dalv2.DALWritelon._
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.AllowCrossDC
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyVal
import com.twittelonr.simclustelonrs_v2.common.SimClustelonrselonmbelondding
import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.simclustelonrs_v2.common.clustelonring.ClustelonringMelonthod
import com.twittelonr.simclustelonrs_v2.common.clustelonring.ClustelonringStatistics._
import com.twittelonr.simclustelonrs_v2.common.clustelonring.ClustelonrRelonprelonselonntativelonSelonlelonctionMelonthod
import com.twittelonr.simclustelonrs_v2.common.clustelonring.ClustelonrRelonprelonselonntativelonSelonlelonctionStatistics._
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.ProducelonrelonmbelonddingSourcelons
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.UselonrUselonrGraphScalaDataselont
import com.twittelonr.simclustelonrs_v2.scalding.common.Util
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.elonmbelonddingUtil
import com.twittelonr.simclustelonrs_v2.thriftscala.elonmbelonddingTypelon
import com.twittelonr.simclustelonrs_v2.thriftscala.IntelonrnalId
import com.twittelonr.simclustelonrs_v2.thriftscala.ModelonlVelonrsion
import com.twittelonr.simclustelonrs_v2.thriftscala.MultielonmbelonddingTypelon
import com.twittelonr.simclustelonrs_v2.thriftscala.NelonighborWithWelonights
import com.twittelonr.simclustelonrs_v2.thriftscala.OrdelonrelondClustelonrsAndMelonmbelonrs
import com.twittelonr.simclustelonrs_v2.thriftscala.ClustelonrMelonmbelonrs
import com.twittelonr.simclustelonrs_v2.thriftscala.SimClustelonrselonmbelonddingIdWithScorelon
import com.twittelonr.simclustelonrs_v2.thriftscala.SimClustelonrsMultielonmbelondding
import com.twittelonr.simclustelonrs_v2.thriftscala.SimClustelonrsMultielonmbelondding.Ids
import com.twittelonr.simclustelonrs_v2.thriftscala.SimClustelonrsMultielonmbelonddingByIds
import com.twittelonr.simclustelonrs_v2.thriftscala.SimClustelonrsMultielonmbelonddingId
import com.twittelonr.simclustelonrs_v2.thriftscala.UselonrAndNelonighbors
import com.twittelonr.simclustelonrs_v2.thriftscala.{
  SimClustelonrselonmbelonddingId => SimClustelonrselonmbelonddingIdThrift
}
import com.twittelonr.util.Stopwatch
import java.util.TimelonZonelon
import scala.util.Random.shufflelon

/**
 * Baselon app for computing Uselonr IntelonrelonstelondIn multi-elonmbelondding relonprelonselonntation.
 * TWICelon: Capturing uselonrs’ long-telonrm intelonrelonsts using multiplelon SimClustelonrs elonmbelonddings.
 * This job will
 * - Randomly selonlelonct K follow/fav actions for elonach uselonr,
 * - clustelonr thelon follow/fav actions for elonach uselonr,
 * - for elonach clustelonr, construct a relonprelonselonntation (elon.g. avelonragelon or melondoid).
 *
 * @tparam T typelon of producelonr elonmbelondding. elon.g. SimClustelonrselonmbelondding
 */
trait IntelonrelonstelondInTwicelonBaselonApp[T] {

  import IntelonrelonstelondInTwicelonBaselonApp._

  delonf modelonlVelonrsion: ModelonlVelonrsion = ModelonlVelonrsion.Modelonl20m145k2020

  /**
   * function to output similarity (>=0, thelon largelonr, morelon similar), givelonn two producelonr elonmbelonddings.
   */
  delonf producelonrProducelonrSimilarityFnForClustelonring: (T, T) => Doublelon
  delonf producelonrProducelonrSimilarityFnForClustelonrRelonprelonselonntativelon: (T, T) => Doublelon

  // Sort clustelonrs by deloncrelonasing sizelon, fall back to elonntity ID to brelonak tielon
  val clustelonrOrdelonring: Ordelonring[Selont[Long]] = math.Ordelonring.by(c => (-c.sizelon, c.min))

  /**
   * Relonad uselonr-uselonr graph.
   */
  delonf gelontUselonrUselonrGraph(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon
  ): TypelondPipelon[UselonrAndNelonighbors] = {
    DAL
      .relonadMostReloncelonntSnapshot(
        UselonrUselonrGraphScalaDataselont
      )
      .withRelonmotelonRelonadPolicy(AllowCrossDC)
      .toTypelondPipelon
  }

  /**
   * Randomly selonlelonct up to maxNelonighborsByUselonr nelonighbors for elonach uselonr.
   * Attelonmpts to elonqually samplelon both follow and fav elondgelons (elon.g. maxNelonighborsByUselonr/2 for elonach).
   * Howelonvelonr, if onelon typelon of elondgelon is insufficielonnt, backfill with othelonr typelon up to maxNelonighborsByUselonr nelonighbours.
   * @param uselonrUselonrGraph Uselonr-Uselonr follow/fav graph.
   * @param maxNelonighborsByUselonr How many nelonighbors to kelonelonp for elonach uselonr.
   */
  delonf selonlelonctMaxProducelonrsPelonrUselonr(
    uselonrUselonrGraph: TypelondPipelon[UselonrAndNelonighbors],
    maxNelonighborsByUselonr: Int = MaxNelonighborsByUselonr
  )(
    implicit uniquelonID: UniquelonID
  ): TypelondPipelon[UselonrAndNelonighbors] = {

    val numOfFollowelondgelonsStat = Stat(StatNumOfFollowelondgelons)
    val numOfFavelondgelonsStat = Stat(StatNumOfFavelondgelons)
    val numOfelondgelonsCumulativelonFrelonquelonncyBelonforelonFiltelonr = Util.CumulativelonStat(
      StatCFNumProducelonrsPelonrConsumelonrBelonforelonFiltelonr,
      StatCFNumProducelonrsPelonrConsumelonrBelonforelonFiltelonrBuckelonts)

    uselonrUselonrGraph.map { uselonrAndNelonighbors: UselonrAndNelonighbors =>
      numOfelondgelonsCumulativelonFrelonquelonncyBelonforelonFiltelonr.incForValuelon(uselonrAndNelonighbors.nelonighbors.sizelon)

      val (followelondgelons, favelondgelons) =
        uselonrAndNelonighbors.nelonighbors.partition(_.isFollowelond.contains(truelon))
      val randomFollowelondgelons = shufflelon(followelondgelons)
      val randomFavelondgelons = shufflelon(favelondgelons)

      // intelonrlelonavelon follow and fav elondgelons, and selonlelonct top k
      val intelonrlelonavelondTopKelondgelons: Selonq[NelonighborWithWelonights] = randomFollowelondgelons
        .map(Somelon(_))
        .zipAll(
          randomFavelondgelons.map(Somelon(_)),
          Nonelon,
          Nonelon
        ) // delonfault Nonelon valuelon whelonn onelon elondgelon Selonq is shortelonr than anothelonr
        .flatMap {
          caselon (followelondgelonOpt, favelondgelonOpt) =>
            Selonq(followelondgelonOpt, favelondgelonOpt)
        }.flattelonn
        .takelon(maxNelonighborsByUselonr)

      // elondgelon stats
      intelonrlelonavelondTopKelondgelons
        .forelonach { elondgelon =>
          if (elondgelon.isFollowelond.contains(truelon)) numOfFollowelondgelonsStat.inc()
          elonlselon numOfFavelondgelonsStat.inc()
        }

      uselonrAndNelonighbors.copy(nelonighbors = intelonrlelonavelondTopKelondgelons)
    }
  }

  /**
   * Gelont multi elonmbelondding for elonach uselonr:
   * - For elonach uselonr, join thelonir follow / fav - baselond nelonighbors to producelonr elonmbelonddings,
   * - Group thelonselon nelonighbors into clustelonrs using thelon speloncifielond clustelonringMelonthod,
   * - For elonach clustelonr, selonlelonct thelon melondoid as thelon relonprelonselonntation.
   *
   * @param uselonrUselonrGraph Uselonr-Uselonr follow/fav graph.
   * @param producelonrelonmbelondding producelonr elonmbelondding dataselont. elon.g. simclustelonrs elonmbelonddings, simhash, elontc.
   * @param clustelonringMelonthod A melonthod to group elonmbelonddings togelonthelonr.
   * @param maxClustelonrsPelonrUselonr How many clustelonrs to kelonelonp pelonr uselonr.
   * @param clustelonrRelonprelonselonntativelonSelonlelonctionMelonthod A melonthod to selonlelonct a clustelonr relonprelonselonntativelon.
   * @param numRelonducelonrs How many relonducelonrs to uselon for skelontch opelonration.
   */
  delonf gelontMultielonmbelonddingPelonrUselonr(
    uselonrUselonrGraph: TypelondPipelon[UselonrAndNelonighbors],
    producelonrelonmbelondding: TypelondPipelon[(UselonrId, T)],
    clustelonringMelonthod: ClustelonringMelonthod,
    maxClustelonrsPelonrUselonr: Int = MaxClustelonrsPelonrUselonr,
    clustelonrRelonprelonselonntativelonSelonlelonctionMelonthod: ClustelonrRelonprelonselonntativelonSelonlelonctionMelonthod[T],
    numRelonducelonrs: Int
  )(
    implicit uniquelonID: UniquelonID
  ): TypelondPipelon[(UselonrId, Selonq[Selont[UselonrId]], SimClustelonrsMultielonmbelondding)] = {

    val truncatelondUselonrUselonrGraph: TypelondPipelon[UselonrAndNelonighbors] = selonlelonctMaxProducelonrsPelonrUselonr(
      uselonrUselonrGraph)
    val validelondgelons: TypelondPipelon[(UselonrId, NelonighborWithWelonights)] =
      truncatelondUselonrUselonrGraph.flatMap {
        caselon UselonrAndNelonighbors(srcId, nelonighborsWithWelonights) =>
          nelonighborsWithWelonights.map { nelonighborWithWelonights =>
            (
              nelonighborWithWelonights.nelonighborId, // producelonrId
              nelonighborWithWelonights.copy(nelonighborId = srcId))
          }
      }

    implicit val l2b: UselonrId => Array[Bytelon] = Injelonction.long2Bigelonndian

    val totalelondgelonsNonelonmptyProducelonrelonmbelonddingsStat = Stat(StatTotalelondgelonsNonelonmptyProducelonrelonmbelonddings)
    val uselonrClustelonrPairsBelonforelonTruncation = Stat(StatNumUselonrClustelonrPairsBelonforelonTruncation)
    val uselonrClustelonrPairsAftelonrTruncation = Stat(StatNumUselonrClustelonrPairsAftelonrTruncation)
    val numUselonrs = Stat(StatNumUselonrs)
    val numOfClustelonrsCumulativelonFrelonquelonncyBelonforelonFiltelonr =
      Util.CumulativelonStat(StatCFNumOfClustelonrsBelonforelonFiltelonr, StatCFNumOfClustelonrsBelonforelonFiltelonrBuckelonts)

    // map elonach clustelonring statistic to a scalding.Stat
    val clustelonringStatsMap: Map[String, Stat] = Map(
      StatSimilarityGraphTotalBuildTimelon -> Stat(StatSimilarityGraphTotalBuildTimelon),
      StatClustelonringAlgorithmRunTimelon -> Stat(StatClustelonringAlgorithmRunTimelon),
      StatMelondoidSelonlelonctionTimelon -> Stat(StatMelondoidSelonlelonctionTimelon)
    )
    val cosinelonSimilarityCumulativelonFrelonquelonncyBelonforelonFiltelonr = Util.CumulativelonStat(
      StatCFCosinelonSimilarityBelonforelonFiltelonr,
      StatCFCosinelonSimilarityBelonforelonFiltelonrBuckelonts)

    val clustelonrRelonprelonselonntativelonSelonlelonctionTimelon = Stat(StatClustelonrRelonprelonselonntativelonSelonlelonctionTimelon)

    validelondgelons
      .skelontch(numRelonducelonrs)
      .join(producelonrelonmbelondding)
      .map {
        caselon (producelonrId: UselonrId, (srcWithWelonights: NelonighborWithWelonights, elonmbelondding)) =>
          totalelondgelonsNonelonmptyProducelonrelonmbelonddingsStat.inc()
          (srcWithWelonights.nelonighborId, (srcWithWelonights.copy(nelonighborId = producelonrId), elonmbelondding))
      }
      .group
      .toList
      .map {
        caselon (uselonrId: UselonrId, elonmbelonddings: Selonq[(NelonighborWithWelonights, T)]) =>
          numUselonrs.inc()
          val elonmbelonddingsMap: Map[Long, T] = elonmbelonddings.map {
            caselon (n: NelonighborWithWelonights, elon) => (n.nelonighborId, elon)
          }.toMap
          val welonightsMap: Map[Long, NelonighborWithWelonights] = elonmbelonddings.map {
            caselon (n: NelonighborWithWelonights, _) => (n.nelonighborId, n)
          }.toMap
          // 1. Clustelonr elonmbelonddings
          val clustelonrs: Selont[Selont[UselonrId]] =
            clustelonringMelonthod
              .clustelonr[T](
                elonmbelonddingsMap,
                producelonrProducelonrSimilarityFnForClustelonring,
                // Map.gelont() relonturns an Option, so will not throw.
                // Uselon .forelonach() to filtelonr out potelonntial Nonelons.
                (namelon, incr) => {
                  clustelonringStatsMap.gelont(namelon).forelonach(ctr => ctr.incBy(incr))
                  if (namelon == StatComputelondSimilarityBelonforelonFiltelonr)
                    cosinelonSimilarityCumulativelonFrelonquelonncyBelonforelonFiltelonr.incForValuelon(incr)
                }
              )

          // 2. Sort clustelonrs
          val sortelondClustelonrs: Selonq[Selont[UselonrId]] = clustelonrs.toSelonq.sortelond(clustelonrOrdelonring)

          // 3. Kelonelonp only a max numbelonr of clustelonrs (avoid OOM)
          uselonrClustelonrPairsBelonforelonTruncation.incBy(sortelondClustelonrs.sizelon)
          numOfClustelonrsCumulativelonFrelonquelonncyBelonforelonFiltelonr.incForValuelon(sortelondClustelonrs.sizelon)
          val truncatelondClustelonrs = sortelondClustelonrs.takelon(maxClustelonrsPelonrUselonr)
          uselonrClustelonrPairsAftelonrTruncation.incBy(truncatelondClustelonrs.sizelon)

          // 4. Gelont list of clustelonr relonprelonselonntativelons
          val truncatelondIdWithScorelonList: Selonq[SimClustelonrselonmbelonddingIdWithScorelon] =
            truncatelondClustelonrs.map { melonmbelonrs: Selont[UselonrId] =>
              val clustelonrRelonprelonselonntationSelonlelonctionelonlapselond = Stopwatch.start()
              val melondoid: UselonrId = clustelonrRelonprelonselonntativelonSelonlelonctionMelonthod.selonlelonctClustelonrRelonprelonselonntativelon(
                melonmbelonrs.map(id => welonightsMap(id)),
                elonmbelonddingsMap)
              clustelonrRelonprelonselonntativelonSelonlelonctionTimelon.incBy(
                clustelonrRelonprelonselonntationSelonlelonctionelonlapselond().inMilliselonconds)

              SimClustelonrselonmbelonddingIdWithScorelon(
                id = SimClustelonrselonmbelonddingIdThrift(
                  elonmbelonddingTypelon.TwicelonUselonrIntelonrelonstelondIn,
                  modelonlVelonrsion,
                  IntelonrnalId.UselonrId(melondoid)),
                scorelon = melonmbelonrs.sizelon)
            }

          (
            uselonrId,
            sortelondClustelonrs,
            SimClustelonrsMultielonmbelondding.Ids(
              SimClustelonrsMultielonmbelonddingByIds(ids = truncatelondIdWithScorelonList)))
      }
  }

  /**
   * Writelon thelon output to disk as a TypelondTsv.
   */
  delonf writelonOutputToTypelondTSV(
    output: TypelondPipelon[(UselonrId, Selonq[Selont[UselonrId]], SimClustelonrsMultielonmbelondding)],
    uselonrToClustelonrRelonprelonselonntativelonsIndelonxOutputPath: String,
    uselonrToClustelonrMelonmbelonrsIndelonxOutputPath: String
  ): elonxeloncution[(Unit, Unit)] = {

    // writelon thelon uselonr -> clustelonr relonprelonselonntativelons indelonx
    val writelonClustelonrRelonprelonselonntativelons = output
      .collelonct {
        caselon (uselonrId: Long, _, Ids(ids)) => (uselonrId, ids.ids)
      }
      //.shard(partitions = 1)
      .writelonelonxeloncution(TypelondTsv[(UselonrId, Selonq[SimClustelonrselonmbelonddingIdWithScorelon])](
        uselonrToClustelonrRelonprelonselonntativelonsIndelonxOutputPath))

    // writelon thelon uselonr -> clustelonr melonmbelonrs indelonx
    val writelonClustelonrMelonmbelonrs = output
      .collelonct {
        caselon (uselonrId: Long, clustelonrs: Selonq[Selont[UselonrId]], _) => (uselonrId, clustelonrs)
      }
      //.shard(partitions = 1)
      .writelonelonxeloncution(TypelondTsv[(UselonrId, Selonq[Selont[UselonrId]])](uselonrToClustelonrMelonmbelonrsIndelonxOutputPath))

    elonxeloncution.zip(writelonClustelonrRelonprelonselonntativelons, writelonClustelonrMelonmbelonrs)

  }

  /**
   * Writelon thelon output to disk as a KelonyValDataselont.
   */
  delonf writelonOutputToKelonyValDataselont(
    output: TypelondPipelon[(UselonrId, Selonq[Selont[UselonrId]], SimClustelonrsMultielonmbelondding)],
    elonmbelonddingTypelon: MultielonmbelonddingTypelon,
    uselonrToClustelonrRelonprelonselonntativelonsIndelonxDataselont: KelonyValDALDataselont[
      KelonyVal[SimClustelonrsMultielonmbelonddingId, SimClustelonrsMultielonmbelondding]
    ],
    uselonrToClustelonrMelonmbelonrsIndelonxDataselont: KelonyValDALDataselont[KelonyVal[UselonrId, OrdelonrelondClustelonrsAndMelonmbelonrs]],
    uselonrToClustelonrRelonprelonselonntativelonsIndelonxOutputPath: String,
    uselonrToClustelonrMelonmbelonrsIndelonxOutputPath: String
  )(
    implicit datelonRangelon: DatelonRangelon
  ): elonxeloncution[(Unit, Unit)] = {
    // writelon thelon uselonr -> clustelonr relonprelonselonntativelons indelonx
    val writelonClustelonrRelonprelonselonntativelons = output
      .map {
        caselon (uselonrId: UselonrId, _, elonmbelonddings: SimClustelonrsMultielonmbelondding) =>
          KelonyVal(
            kelony = SimClustelonrsMultielonmbelonddingId(
              elonmbelonddingTypelon = elonmbelonddingTypelon,
              modelonlVelonrsion = modelonlVelonrsion,
              intelonrnalId = IntelonrnalId.UselonrId(uselonrId)
            ),
            valuelon = elonmbelonddings
          )
      }
      .writelonDALVelonrsionelondKelonyValelonxeloncution(
        uselonrToClustelonrRelonprelonselonntativelonsIndelonxDataselont,
        D.Suffix(uselonrToClustelonrRelonprelonselonntativelonsIndelonxOutputPath),
        elonxplicitelonndTimelon(datelonRangelon.elonnd)
      )

    // writelon thelon uselonr -> clustelonr melonmbelonrs indelonx
    val writelonClustelonrMelonmbelonrs = output
      .map {
        caselon (uselonrId: UselonrId, clustelonrs: Selonq[Selont[UselonrId]], _) =>
          KelonyVal(
            kelony = uselonrId,
            valuelon = OrdelonrelondClustelonrsAndMelonmbelonrs(clustelonrs, Somelon(clustelonrs.map(ClustelonrMelonmbelonrs(_)))))
      }
      .writelonDALVelonrsionelondKelonyValelonxeloncution(
        uselonrToClustelonrMelonmbelonrsIndelonxDataselont,
        D.Suffix(uselonrToClustelonrMelonmbelonrsIndelonxOutputPath),
        elonxplicitelonndTimelon(datelonRangelon.elonnd)
      )

    elonxeloncution.zip(writelonClustelonrRelonprelonselonntativelons, writelonClustelonrMelonmbelonrs)
  }

  /**
   * Main melonthod for schelondulelond jobs.
   */
  delonf runSchelondulelondApp(
    clustelonringMelonthod: ClustelonringMelonthod,
    clustelonrRelonprelonselonntativelonSelonlelonctionMelonthod: ClustelonrRelonprelonselonntativelonSelonlelonctionMelonthod[T],
    producelonrelonmbelondding: TypelondPipelon[(UselonrId, T)],
    uselonrToClustelonrRelonprelonselonntativelonsIndelonxPathSuffix: String,
    uselonrToClustelonrMelonmbelonrsIndelonxPathSuffix: String,
    uselonrToClustelonrRelonprelonselonntativelonsIndelonxDataselont: KelonyValDALDataselont[
      KelonyVal[SimClustelonrsMultielonmbelonddingId, SimClustelonrsMultielonmbelondding]
    ],
    uselonrToClustelonrMelonmbelonrsIndelonxDataselont: KelonyValDALDataselont[KelonyVal[UselonrId, OrdelonrelondClustelonrsAndMelonmbelonrs]],
    numRelonducelonrs: Int
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonId: UniquelonID
  ): elonxeloncution[Unit] = {

    val uselonrToClustelonrRelonprelonselonntativelonsIndelonxOutputPath: String = elonmbelonddingUtil.gelontHdfsPath(
      isAdhoc = falselon,
      isManhattanKelonyVal = truelon,
      modelonlVelonrsion = modelonlVelonrsion,
      pathSuffix = uselonrToClustelonrRelonprelonselonntativelonsIndelonxPathSuffix
    )

    val uselonrToClustelonrMelonmbelonrsIndelonxOutputPath: String = elonmbelonddingUtil.gelontHdfsPath(
      isAdhoc = falselon,
      isManhattanKelonyVal = truelon,
      modelonlVelonrsion = modelonlVelonrsion,
      pathSuffix = uselonrToClustelonrMelonmbelonrsIndelonxPathSuffix
    )

    val elonxeloncution = elonxeloncution.withId { implicit uniquelonId =>
      val output: TypelondPipelon[(UselonrId, Selonq[Selont[UselonrId]], SimClustelonrsMultielonmbelondding)] =
        gelontMultielonmbelonddingPelonrUselonr(
          uselonrUselonrGraph = gelontUselonrUselonrGraph(datelonRangelon.prelonpelonnd(Days(30)), implicitly),
          producelonrelonmbelondding = producelonrelonmbelondding,
          clustelonringMelonthod = clustelonringMelonthod,
          clustelonrRelonprelonselonntativelonSelonlelonctionMelonthod = clustelonrRelonprelonselonntativelonSelonlelonctionMelonthod,
          numRelonducelonrs = numRelonducelonrs
        )

      writelonOutputToKelonyValDataselont(
        output = output,
        elonmbelonddingTypelon = MultielonmbelonddingTypelon.TwicelonUselonrIntelonrelonstelondIn,
        uselonrToClustelonrRelonprelonselonntativelonsIndelonxDataselont = uselonrToClustelonrRelonprelonselonntativelonsIndelonxDataselont,
        uselonrToClustelonrMelonmbelonrsIndelonxDataselont = uselonrToClustelonrMelonmbelonrsIndelonxDataselont,
        uselonrToClustelonrRelonprelonselonntativelonsIndelonxOutputPath = uselonrToClustelonrRelonprelonselonntativelonsIndelonxOutputPath,
        uselonrToClustelonrMelonmbelonrsIndelonxOutputPath = uselonrToClustelonrMelonmbelonrsIndelonxOutputPath
      )

    }

    elonxeloncution.unit
  }

  /**
   * Main melonthod for adhoc jobs.
   */
  delonf runAdhocApp(
    clustelonringMelonthod: ClustelonringMelonthod,
    clustelonrRelonprelonselonntativelonSelonlelonctionMelonthod: ClustelonrRelonprelonselonntativelonSelonlelonctionMelonthod[T],
    producelonrelonmbelondding: TypelondPipelon[(UselonrId, T)],
    uselonrToClustelonrRelonprelonselonntativelonsIndelonxPathSuffix: String,
    uselonrToClustelonrMelonmbelonrsIndelonxPathSuffix: String,
    numRelonducelonrs: Int
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonId: UniquelonID
  ): elonxeloncution[Unit] = {

    val uselonrToClustelonrRelonprelonselonntativelonsIndelonxOutputPath: String = elonmbelonddingUtil.gelontHdfsPath(
      isAdhoc = truelon,
      isManhattanKelonyVal = falselon,
      modelonlVelonrsion = modelonlVelonrsion,
      pathSuffix = uselonrToClustelonrRelonprelonselonntativelonsIndelonxPathSuffix
    )

    val uselonrToClustelonrMelonmbelonrsIndelonxOutputPath: String = elonmbelonddingUtil.gelontHdfsPath(
      isAdhoc = truelon,
      isManhattanKelonyVal = falselon,
      modelonlVelonrsion = modelonlVelonrsion,
      pathSuffix = uselonrToClustelonrMelonmbelonrsIndelonxPathSuffix
    )

    val elonxeloncution = elonxeloncution.withId { implicit uniquelonId =>
      val output: TypelondPipelon[(UselonrId, Selonq[Selont[UselonrId]], SimClustelonrsMultielonmbelondding)] =
        gelontMultielonmbelonddingPelonrUselonr(
          uselonrUselonrGraph = gelontUselonrUselonrGraph(datelonRangelon.prelonpelonnd(Days(30)), implicitly),
          producelonrelonmbelondding = producelonrelonmbelondding,
          clustelonringMelonthod = clustelonringMelonthod,
          clustelonrRelonprelonselonntativelonSelonlelonctionMelonthod = clustelonrRelonprelonselonntativelonSelonlelonctionMelonthod,
          numRelonducelonrs = numRelonducelonrs
        )

      writelonOutputToTypelondTSV(
        output,
        uselonrToClustelonrRelonprelonselonntativelonsIndelonxOutputPath,
        uselonrToClustelonrMelonmbelonrsIndelonxOutputPath)
    }

    elonxeloncution.unit
  }

}

objelonct IntelonrelonstelondInTwicelonBaselonApp {

  // Statistics
  val StatNumOfFollowelondgelons = "num_of_follow_elondgelons"
  val StatNumOfFavelondgelons = "num_of_fav_elondgelons"
  val StatTotalelondgelonsNonelonmptyProducelonrelonmbelonddings = "total_elondgelons_with_non_elonmpty_producelonr_elonmbelonddings"
  val StatNumUselonrClustelonrPairsBelonforelonTruncation = "num_uselonr_clustelonr_pairs_belonforelon_truncation"
  val StatNumUselonrClustelonrPairsAftelonrTruncation = "num_uselonr_clustelonr_pairs_aftelonr_truncation"
  val StatNumUselonrs = "num_uselonrs"
  // Cumulativelon Frelonquelonncy
  val StatCFNumProducelonrsPelonrConsumelonrBelonforelonFiltelonr = "num_producelonrs_pelonr_consumelonr_cf_belonforelon_filtelonr"
  val StatCFNumProducelonrsPelonrConsumelonrBelonforelonFiltelonrBuckelonts: Selonq[Doublelon] =
    Selonq(0, 10, 20, 50, 100, 500, 1000)
  val StatCFCosinelonSimilarityBelonforelonFiltelonr = "cosinelon_similarity_cf_belonforelon_filtelonr"
  val StatCFCosinelonSimilarityBelonforelonFiltelonrBuckelonts: Selonq[Doublelon] =
    Selonq(0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
  val StatCFNumOfClustelonrsBelonforelonFiltelonr = "num_of_clustelonrs_cf_belonforelon_filtelonr"
  val StatCFNumOfClustelonrsBelonforelonFiltelonrBuckelonts: Selonq[Doublelon] =
    Selonq(1, 3, 5, 10, 15, 20, 50, 100, 200, 300, 500)

  val MaxClustelonrsPelonrUselonr: Int = 10
  val MaxNelonighborsByUselonr: Int = 500

  objelonct ProducelonrelonmbelonddingSourcelon {

    /**
     * Relonad log-fav baselond Aggrelongatablelon Producelonr elonmbelonddings dataselont.
     */
    delonf gelontAggrelongatablelonProducelonrelonmbelonddings(
      implicit datelonRangelon: DatelonRangelon,
      timelonZonelon: TimelonZonelon
    ): TypelondPipelon[(UselonrId, SimClustelonrselonmbelondding)] =
      ProducelonrelonmbelonddingSourcelons
        .producelonrelonmbelonddingSourcelon(
          elonmbelonddingTypelon.AggrelongatablelonLogFavBaselondProducelonr,
          ModelonlVelonrsion.Modelonl20m145k2020)(datelonRangelon.prelonpelonnd(Days(30)))
        .mapValuelons(s => SimClustelonrselonmbelondding(s))

  }

}
