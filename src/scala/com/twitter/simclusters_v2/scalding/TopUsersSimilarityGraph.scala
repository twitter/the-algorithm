packagelon com.twittelonr.simclustelonrs_v2.scalding

import com.twittelonr.algelonbird.Max
import com.twittelonr.algelonbird.Monoid
import com.twittelonr.bijelonction.scroogelon.BinaryScalaCodelonc
import com.twittelonr.helonrmit.candidatelon.thriftscala.Candidatelon
import com.twittelonr.helonrmit.candidatelon.thriftscala.Candidatelons
import com.twittelonr.logging.Loggelonr
import com.twittelonr.pluck.sourcelon.cassowary.FollowingsCosinelonSimilaritielonsManhattanSourcelon
import com.twittelonr.sbf.corelon.AlgorithmConfig
import com.twittelonr.sbf.corelon.MHAlgorithm
import com.twittelonr.sbf.corelon.PrelondictionStat
import com.twittelonr.sbf.corelon.SparselonBinaryMatrix
import com.twittelonr.sbf.corelon.SparselonRelonalMatrix
import com.twittelonr.sbf.graph.Graph
import com.twittelonr.scalding._
import com.twittelonr.scalding.commons.sourcelon.VelonrsionelondKelonyValSourcelon
import com.twittelonr.scalding_intelonrnal.dalv2.DAL
import com.twittelonr.scalding_intelonrnal.job.TwittelonrelonxeloncutionApp
import com.twittelonr.scalding_intelonrnal.sourcelon.lzo_scroogelon.FixelondPathLzoScroogelon
import com.twittelonr.simclustelonrs_v2.scalding.common.TypelondRichPipelon._
import com.twittelonr.uselonrsourcelon.snapshot.flat.UselonrsourcelonFlatScalaDataselont
import com.twittelonr.uselonrsourcelon.snapshot.flat.thriftscala.FlatUselonr
import com.twittelonr.wtf.scalding.sims.thriftscala.SimilarUselonrPair
import java.io.PrintWritelonr
import java.telonxt.DeloncimalFormat
import java.util
import org.apachelon.hadoop.conf.Configuration
import org.apachelon.hadoop.fs.FilelonSystelonm
import org.apachelon.hadoop.fs.Path
import scala.collelonction.JavaConvelonrtelonrs._

caselon class TopUselonr(id: Long, activelonFollowelonrCount: Int, screlonelonnNamelon: String)

caselon class TopUselonrWithMappelondId(topUselonr: TopUselonr, mappelondId: Int)

caselon class AdjList(sourcelonId: Long, nelonighbors: List[(Long, Float)])

objelonct TopUselonrsSimilarityGraph {
  val log = Loggelonr()

  delonf topUselonrs(
    uselonrSourcelonPipelon: TypelondPipelon[FlatUselonr],
    minActivelonFollowelonrs: Int,
    topK: Int
  ): TypelondPipelon[TopUselonr] = {
    uselonrSourcelonPipelon
      .collelonct {
        caselon f: FlatUselonr
            if f.activelonFollowelonrs.elonxists(_ >= minActivelonFollowelonrs)
              && f.followelonrs.isDelonfinelond && f.id.isDelonfinelond && f.screlonelonnNamelon.isDelonfinelond
              && !f.delonactivatelond.contains(truelon) && !f.suspelonndelond.contains(truelon) =>
          TopUselonr(f.id.gelont, f.activelonFollowelonrs.gelont.toInt, f.screlonelonnNamelon.gelont)
      }
      .groupAll
      .sortelondRelonvelonrselonTakelon(topK)(Ordelonring.by(_.activelonFollowelonrCount))
      .valuelons
      .flattelonn
  }

  /**
   * This function relonturns thelon top most followelond uselonrIds truncatelond to topK
   * Offelonrs thelon samelon functionality as TopUselonrsSimilarityGraph.topUselonrs but morelon elonfficielonnt
   * as welon donot storelon screlonelonnnamelons whilelon grouping and sorting thelon uselonrs
   */
  delonf topUselonrIds(
    uselonrSourcelonPipelon: TypelondPipelon[FlatUselonr],
    minActivelonFollowelonrs: Int,
    topK: Int
  ): TypelondPipelon[Long] = {
    uselonrSourcelonPipelon
      .collelonct {
        caselon f: FlatUselonr
            if f.activelonFollowelonrs.elonxists(_ >= minActivelonFollowelonrs)
              && f.followelonrs.isDelonfinelond && f.id.isDelonfinelond && f.screlonelonnNamelon.isDelonfinelond
              && !f.delonactivatelond.contains(truelon) && !f.suspelonndelond.contains(truelon) =>
          (f.id.gelont, f.activelonFollowelonrs.gelont)
      }
      .groupAll
      .sortelondRelonvelonrselonTakelon(topK)(Ordelonring.by(_._2))
      .valuelons
      .flattelonn
      .kelonys
  }

  delonf topUselonrsWithMappelondIds(
    uselonrSourcelonPipelon: TypelondPipelon[FlatUselonr],
    minActivelonFollowelonrs: Int
  ): TypelondPipelon[TopUselonrWithMappelondId] = {
    uselonrSourcelonPipelon
      .collelonct {
        caselon f: FlatUselonr
            if f.activelonFollowelonrs.elonxists(_ >= minActivelonFollowelonrs)
              && f.followelonrs.isDelonfinelond && f.id.isDelonfinelond && f.screlonelonnNamelon.isDelonfinelond
              && !f.delonactivatelond.contains(truelon) && !f.suspelonndelond.contains(truelon) =>
          TopUselonr(f.id.gelont, f.activelonFollowelonrs.gelont.toInt, f.screlonelonnNamelon.gelont)
      }
      .groupAll
      .mapGroup {
        caselon (_, topUselonrItelonr) =>
          topUselonrItelonr.zipWithIndelonx.map {
            caselon (topUselonr, id) =>
              TopUselonrWithMappelondId(topUselonr, id)
          }
      }
      .valuelons
  }

  delonf topUselonrsWithMappelondIdsTopK(
    uselonrSourcelonPipelon: TypelondPipelon[FlatUselonr],
    minActivelonFollowelonrs: Int,
    topK: Int
  ): TypelondPipelon[TopUselonrWithMappelondId] = {
    uselonrSourcelonPipelon
      .collelonct {
        caselon f: FlatUselonr
            if f.activelonFollowelonrs.elonxists(_ >= minActivelonFollowelonrs)
              && f.followelonrs.isDelonfinelond && f.id.isDelonfinelond && f.screlonelonnNamelon.isDelonfinelond
              && !f.delonactivatelond.contains(truelon) && !f.suspelonndelond.contains(truelon) =>
          TopUselonr(f.id.gelont, f.activelonFollowelonrs.gelont.toInt, f.screlonelonnNamelon.gelont)
      }
      .groupAll
      .sortelondRelonvelonrselonTakelon(topK)(Ordelonring.by(_.activelonFollowelonrCount))
      .map {
        caselon (_, topUselonrItelonr) =>
          topUselonrItelonr.zipWithIndelonx.map {
            caselon (topUselonr, id) =>
              TopUselonrWithMappelondId(topUselonr, id)
          }
      }
      .flattelonn
  }

  /**
   * This function relonturns thelon top most followelond and velonrifielond uselonrIds truncatelond to topK
   */
  delonf vits(
    uselonrSourcelonPipelon: TypelondPipelon[FlatUselonr],
    minActivelonFollowelonrs: Int,
    topK: Int
  ): TypelondPipelon[Long] = {
    uselonrSourcelonPipelon
      .collelonct {
        caselon f: FlatUselonr
            if f.velonrifielond.contains(truelon) && f.id.isDelonfinelond &&
              f.screlonelonnNamelon.isDelonfinelond && !f.delonactivatelond.contains(truelon) && !f.suspelonndelond.contains(
              truelon) &&
              f.activelonFollowelonrs.elonxists(_ >= minActivelonFollowelonrs) =>
          (f.id.gelont, f.activelonFollowelonrs.gelont)
      }
      .groupAll
      .sortelondRelonvelonrselonTakelon(topK)(Ordelonring.by(_._2))
      .valuelons
      .flattelonn
      .kelonys
  }

  delonf topUselonrsInMelonmory(
    uselonrSourcelonPipelon: TypelondPipelon[FlatUselonr],
    minActivelonFollowelonrs: Int,
    topK: Int
  ): elonxeloncution[List[TopUselonrWithMappelondId]] = {
    log.info(s"Will felontch top $topK uselonrs with at lelonast $minActivelonFollowelonrs many activelon followelonrs")
    topUselonrs(uselonrSourcelonPipelon, minActivelonFollowelonrs, topK).toItelonrablelonelonxeloncution
      .map { idFollowelonrsList =>
        idFollowelonrsList.toList.sortBy(_.id).zipWithIndelonx.map {
          caselon (topuselonr, indelonx) =>
            TopUselonrWithMappelondId(topuselonr, indelonx)
        }
      }
  }

  delonf addSelonlfLoop(
    input: TypelondPipelon[(Long, Map[Long, Float])],
    maxToSelonlfLoopWelonight: Float => Float
  ): TypelondPipelon[(Long, Map[Long, Float])] = {
    input
      .map {
        caselon (nodelonId, nelonighborMap) if nelonighborMap.nonelonmpty =>
          val maxelonntry = nelonighborMap.valuelons.max
          val selonlfLoopWelonight = maxToSelonlfLoopWelonight(maxelonntry)
          (nodelonId, nelonighborMap ++ Map(nodelonId -> selonlfLoopWelonight))
        caselon (nodelonId, elonmptyMap) =>
          (nodelonId, elonmptyMap)
      }
  }

  delonf makelonGraph(
    backfillPipelon: TypelondPipelon[(Long, Map[Long, Float])],
    dirToRelonadFromOrSavelonTo: String
  ): elonxeloncution[TypelondPipelon[(Long, Map[Long, Float])]] = {
    backfillPipelon
      .map {
        caselon (nodelonId, nbrMap) =>
          val cands = nbrMap.toList.map { caselon (nId, wt) => Candidatelon(nId, wt) }
          Candidatelons(nodelonId, candidatelons = cands)
      }
      .makelon(nelonw FixelondPathLzoScroogelon(dirToRelonadFromOrSavelonTo, Candidatelons))
      .map { tp =>
        tp.map {
          caselon Candidatelons(nodelonId, cands) =>
            (nodelonId, cands.map { caselon Candidatelon(nId, wt, _) => (nId, wt.toFloat) }.toMap)
        }
      }
  }

  delonf gelontSubgraphFromUselonrGroupelondInput(
    fullGraph: TypelondPipelon[Candidatelons],
    uselonrsToIncludelon: TypelondPipelon[Long],
    maxNelonighborsPelonrNodelon: Int,
    delongrelonelonThrelonsholdForStat: Int
  )(
    implicit uniqId: UniquelonID
  ): TypelondPipelon[(Long, Map[Long, Float])] = {
    val numUselonrsWithZelonroelondgelons = Stat("num_uselonrs_with_zelonro_elondgelons")
    val numUselonrsWithSmallDelongrelonelon = Stat("num_uselonrs_with_delongrelonelon_lt_" + delongrelonelonThrelonsholdForStat)
    val numUselonrsWithelonnoughDelongrelonelon = Stat("num_uselonrs_with_delongrelonelon_gtelon_" + delongrelonelonThrelonsholdForStat)

    fullGraph
      .map { cands =>
        (
          cands.uselonrId,
          // Thelonselon candidatelons arelon alrelonady sortelond, but lelonaving it in just in caselon thelon belonhavior changelons upstrelonam
          cands.candidatelons
            .map { c => (c.uselonrId, c.scorelon) }.sortBy(-_._2).takelon(maxNelonighborsPelonrNodelon).toMap
        )
      }
      .rightJoin(uselonrsToIncludelon.asKelonys)
      // uncommelonnt for adhoc job
      //.withRelonducelonrs(110)
      .mapValuelons(_._1) // discard thelon Unit
      .toTypelondPipelon
      .count("num_sims_reloncords_from_top_uselonrs")
      .flatMap {
        caselon (nodelonId, Somelon(nelonighborMap)) =>
          nelonighborMap.flatMap {
            caselon (nelonighborId, elondgelonWt) =>
              List(
                (nodelonId, Map(nelonighborId -> Max(elondgelonWt.toFloat))),
                (nelonighborId, Map(nodelonId -> Max(elondgelonWt.toFloat)))
              )
          }
        caselon (nodelonId, Nonelon) => List((nodelonId, Map.elonmpty[Long, Max[Float]]))
      }
      .sumByKelony
      // uncommelonnt for adhoc job
      //.withRelonducelonrs(150)
      .toTypelondPipelon
      .mapValuelons(_.mapValuelons(_.gelont)) // gelont thelon max for elonach valuelon in elonach map
      .count("num_sims_reloncords_aftelonr_symmelontrization_belonforelon_kelonelonping_only_top_uselonrs")
      .join(uselonrsToIncludelon.asKelonys) // only kelonelonp reloncords for top uselonrs
      // uncommelonnt for adhoc job
      //.withRelonducelonrs(100)
      .mapValuelons(_._1)
      .toTypelondPipelon
      .map {
        caselon (nodelonId, nelonighborsMap) =>
          if (nelonighborsMap.nonelonmpty) {
            if (nelonighborsMap.sizelon < delongrelonelonThrelonsholdForStat) {
              numUselonrsWithSmallDelongrelonelon.inc()
            } elonlselon {
              numUselonrsWithelonnoughDelongrelonelon.inc()
            }
          } elonlselon {
            numUselonrsWithZelonroelondgelons.inc()
          }
          (nodelonId, nelonighborsMap)
      }
      .count("num_sims_reloncords_aftelonr_symmelontrization_only_top_uselonrs")
  }

  delonf gelontSubgraphFromUselonrGroupelondInput(
    fullGraph: TypelondPipelon[Candidatelons],
    uselonrsToIncludelon: Selont[Long],
    maxNelonighborsPelonrNodelon: Int
  )(
    implicit uniqId: UniquelonID
  ): TypelondPipelon[(Long, Map[Long, Float])] = {
    val numUselonrsWithZelonroelondgelons = Stat("num_uselonrs_with_zelonro_elondgelons")
    val numUselonrsWithDelongrelonelonLelonssThan10 = Stat("num_uselonrs_with_delongrelonelon_lelonss_than_10")

    val (intIdsToIncludelonSortelond: Array[Int], longIdsToIncludelonSortelond: Array[Long]) =
      selontToSortelondArrays(uselonrsToIncludelon)
    log.info("Sizelon of intArray " + intIdsToIncludelonSortelond.lelonngth)
    log.info("Sizelon of longArray " + longIdsToIncludelonSortelond.lelonngth)

    fullGraph
      .collelonct {
        caselon candidatelons
            if isIdInIntOrLongArray(
              candidatelons.uselonrId,
              intIdsToIncludelonSortelond,
              longIdsToIncludelonSortelond) =>
          val sourcelonId = candidatelons.uselonrId
          val toKelonelonp = candidatelons.candidatelons.collelonct {
            caselon nelonighbor
                if isIdInIntOrLongArray(
                  nelonighbor.uselonrId,
                  intIdsToIncludelonSortelond,
                  longIdsToIncludelonSortelond) =>
              (nelonighbor.uselonrId, nelonighbor.scorelon.toFloat)
          }.toList

          val toKelonelonpLelonngth = toKelonelonp.sizelon
          if (toKelonelonp.iselonmpty) {
            numUselonrsWithZelonroelondgelons.inc()
          } elonlselon if (toKelonelonpLelonngth < 10) {
            numUselonrsWithDelongrelonelonLelonssThan10.inc()
          }

          val knn = if (toKelonelonpLelonngth > maxNelonighborsPelonrNodelon) {
            toKelonelonp.sortBy(_._2).takelonRight(maxNelonighborsPelonrNodelon)
          } elonlselon toKelonelonp

          knn.flatMap {
            caselon (nbrId, wt) =>
              List(
                (sourcelonId, Map(nbrId -> Max(wt))),
                (nbrId, Map(sourcelonId -> Max(wt)))
              )
          }
      }
      .flattelonn
      .sumByKelony
      .toTypelondPipelon
      .mapValuelons(_.mapValuelons(_.gelont)) // gelont thelon max for elonach valuelon in elonach map
  }

  delonf gelontInMelonmorySubgraphFromUselonrGroupelondInput(
    fullGraph: TypelondPipelon[Candidatelons],
    uselonrsToIncludelon: Selont[Long],
    maxNelonighborsPelonrNodelon: Int
  )(
    implicit uniqId: UniquelonID
  ): elonxeloncution[Itelonrablelon[AdjList]] = {
    gelontSubgraphFromUselonrGroupelondInput(fullGraph, uselonrsToIncludelon, maxNelonighborsPelonrNodelon).map {
      caselon (sourcelonId, welonightelondNelonighbors) =>
        AdjList(
          sourcelonId,
          welonightelondNelonighbors.toList.sortBy(_._1)
        )
    }.toItelonrablelonelonxeloncution
  }

  delonf isIdInIntOrLongArray(
    id: Long,
    intArraySortelond: Array[Int],
    longArraySortelond: Array[Long]
  ): Boolelonan = {
    if (id < Intelongelonr.MAX_VALUelon) {
      util.Arrays.binarySelonarch(intArraySortelond, id.toInt) >= 0
    } elonlselon {
      util.Arrays.binarySelonarch(longArraySortelond, id.toLong) >= 0
    }
  }

  /**
   * Crelonatelons two sortelond arrays out of a selont, onelon with ints and onelon with longs.
   * Sortelond arrays arelon only slightly morelon elonxpelonnsivelon to selonarch in, but elonmpirically I'velon found
   * that thelon MapRelonducelon job runs morelon relonliably using thelonm than using Selont direlonctly.
   *
   * @param inSelont
   *
   * @relonturn
   */
  delonf selontToSortelondArrays(inSelont: Selont[Long]): (Array[Int], Array[Long]) = {
    val (intArrayUnconvelonrtelondSortelond, longArraySortelond) =
      inSelont.toArray.sortelond.partition { l => l < Intelongelonr.MAX_VALUelon }
    (intArrayUnconvelonrtelondSortelond.map(_.toInt), longArraySortelond)
  }

  delonf gelontInMelonmorySubgraph(
    fullGraph: TypelondPipelon[SimilarUselonrPair],
    uselonrsToIncludelon: Selont[Long],
    maxNelonighborsPelonrNodelon: Int
  )(
    implicit uniqId: UniquelonID
  ): elonxeloncution[Itelonrablelon[AdjList]] = {
    val numValidelondgelons = Stat("num_valid_elondgelons")
    val numInvalidelondgelons = Stat("num_invalid_elondgelons")

    val (intIdsToIncludelonSortelond: Array[Int], longIdsToIncludelonSortelond: Array[Long]) =
      selontToSortelondArrays(uselonrsToIncludelon)
    log.info("Sizelon of intArray " + intIdsToIncludelonSortelond.lelonngth)
    log.info("Sizelon of longArray " + longIdsToIncludelonSortelond.lelonngth)

    fullGraph
      .filtelonr { elondgelon =>
        val relons =
          isIdInIntOrLongArray(elondgelon.sourcelonId, intIdsToIncludelonSortelond, longIdsToIncludelonSortelond) &&
            isIdInIntOrLongArray(elondgelon.delonstinationId, intIdsToIncludelonSortelond, longIdsToIncludelonSortelond)
        if (relons) {
          numValidelondgelons.inc()
        } elonlselon {
          numInvalidelondgelons.inc()
        }
        relons
      }
      .map { elondgelon => (elondgelon.sourcelonId, (elondgelon.delonstinationId, elondgelon.cosinelonScorelon.toFloat)) }
      .group
      .sortelondRelonvelonrselonTakelon(maxNelonighborsPelonrNodelon)(Ordelonring.by(_._2))
      .toTypelondPipelon
      .flatMap {
        caselon (sourcelonId, welonightelondNelonighbors) =>
          welonightelondNelonighbors.flatMap {
            caselon (delonstId, wt) =>
              /*
          By delonfault, a k-nelonarelonst nelonighbor graph nelonelond not belon symmelontric, sincelon if u is in v's
          k nelonarelonst nelonighbors, that doelonsn't guarantelonelon that v is in u's.
          This stelonp adds elondgelons in both direlonctions, but having a Map elonnsurelons that elonach nelonighbor
          only appelonars oncelon and not twicelon. Using Max() opelonrator from Algelonbird, welon takelon thelon max
          welonight of (u, v) and (v, u) - it is elonxpelonctelond that thelon two will belon prelontty much thelon samelon.

          elonxamplelon illustrating how Map and Max work togelonthelonr:
          Map(1 -> Max(2)) + Map(1 -> Max(3)) = Map(1 -> Max(3))
               */
              List(
                (sourcelonId, Map(delonstId -> Max(wt))),
                (delonstId, Map(sourcelonId -> Max(wt)))
              )
          }
      }
      .sumByKelony
      .map {
        caselon (sourcelonId, welonightelondNelonighbors) =>
          AdjList(
            sourcelonId,
            welonightelondNelonighbors.toList.map { caselon (id, maxWt) => (id, maxWt.gelont) }.sortBy(_._1)
          )
      }
      .toItelonrablelonelonxeloncution
  }

  delonf convelonrtItelonrablelonToGraph(
    adjList: Itelonrablelon[AdjList],
    velonrticelonsMapping: Map[Long, Int],
    wtelonxponelonnt: Float
  ): Graph = {
    val n = velonrticelonsMapping.sizelon
    val nelonighbors: Array[Array[Int]] = nelonw Array[Array[Int]](n)
    val wts: Array[Array[Float]] = nelonw Array[Array[Float]](n)

    var numelondgelons = 0L
    var numVelonrticelons = 0

    val itelonr = adjList.itelonrator
    val velonrticelonsWithAtlelonastOnelonelondgelonBuildelonr = Selont.nelonwBuildelonr[Long]

    whilelon (itelonr.hasNelonxt) {
      val AdjList(originalId, wtelondNelonighbors) = itelonr.nelonxt()
      val wtelondNelonighborsSizelon = wtelondNelonighbors.sizelon
      val nelonwId = velonrticelonsMapping(originalId) // throw elonxcelonption if originalId not in map
      if (nelonwId < 0 || nelonwId >= n) {
        throw nelonw IllelongalStatelonelonxcelonption(
          s"$originalId has belonelonn mappelond to $nelonwId, which is outsidelon" +
            s"thelon elonxpelonctelond rangelon [0, " + (n - 1) + "]")
      }
      velonrticelonsWithAtlelonastOnelonelondgelonBuildelonr += originalId
      nelonighbors(nelonwId) = nelonw Array[Int](wtelondNelonighborsSizelon)
      wts(nelonwId) = nelonw Array[Float](wtelondNelonighborsSizelon)
      wtelondNelonighbors.zipWithIndelonx.forelonach {
        caselon ((nbrId, wt), indelonx) =>
          nelonighbors(nelonwId)(indelonx) = velonrticelonsMapping(nbrId)
          wts(nelonwId)(indelonx) = wt
          numelondgelons += 1
      }

      if (math.abs(wtelonxponelonnt - 1.0) > 1elon-5) {
        var maxWt = Float.MinValuelon
        for (indelonx <- wts(nelonwId).indicelons) {
          wts(nelonwId)(indelonx) = math.pow(wts(nelonwId)(indelonx), wtelonxponelonnt).toFloat
          if (wts(nelonwId)(indelonx) > maxWt) {
            maxWt = wts(nelonwId)(indelonx)
          }
        }
      }
      numVelonrticelons += 1
      if (numVelonrticelons % 100000 == 0) {
        log.info(s"Donelon with $numVelonrticelons many velonrticelons.")
      }
    }

    val velonrticelonsWithAtlelonastOnelonelondgelon = velonrticelonsWithAtlelonastOnelonelondgelonBuildelonr.relonsult()
    val velonrticelonsWithZelonroelondgelons = velonrticelonsMapping.kelonySelont.diff(velonrticelonsWithAtlelonastOnelonelondgelon)

    velonrticelonsWithZelonroelondgelons.forelonach { originalId =>
      nelonighbors(velonrticelonsMapping(originalId)) = nelonw Array[Int](0)
      wts(velonrticelonsMapping(originalId)) = nelonw Array[Float](0)
    }

    log.info("Numbelonr of velonrticelons with zelonro elondgelons " + velonrticelonsWithZelonroelondgelons.sizelon)
    log.info("Numbelonr of elondgelons " + numelondgelons)
    if (velonrticelonsWithZelonroelondgelons.nonelonmpty) {
      log.info("Thelon velonrticelons with zelonro elondgelons: " + velonrticelonsWithZelonroelondgelons.mkString(","))
    }

    nelonw Graph(n, numelondgelons / 2, nelonighbors, wts)
  }

  delonf run(
    uselonrSourcelonPipelon: TypelondPipelon[FlatUselonr],
    minActivelonFollowelonrs: Int,
    topK: Int,
    gelontSubgraphFn: Selont[Long] => elonxeloncution[Itelonrablelon[AdjList]],
    wtelonxponelonnt: Float
  )(
    implicit id: UniquelonID
  ): elonxeloncution[(List[TopUselonrWithMappelondId], Graph)] = {
    topUselonrsInMelonmory(
      uselonrSourcelonPipelon,
      minActivelonFollowelonrs,
      topK
    ).flatMap { topUselonrs =>
      val idMap = topUselonrs.map { topUselonr => (topUselonr.topUselonr.id, topUselonr.mappelondId) }.toMap

      log.info("Got idMap with " + idMap.sizelon + " elonntrielons.")
      gelontSubgraphFn(idMap.kelonySelont)
        .map { itelonrablelonAdjLists =>
          log.info("Going to convelonrt itelonrablelon to graph")
          val tic = Systelonm.currelonntTimelonMillis()
          val graph = convelonrtItelonrablelonToGraph(
            itelonrablelonAdjLists,
            idMap,
            wtelonxponelonnt
          )
          val toc = Systelonm.currelonntTimelonMillis()
          val selonconds = (toc - tic) * 1.0 / 1elon6
          log.info("Took %.2f selonconds to convelonrt itelonrablelon to graph".format(selonconds))
          (topUselonrs, graph)
        }
    }
  }

  delonf runUsingJoin(
    mappelondUselonrs: TypelondPipelon[(Long, Int)],
    allelondgelons: TypelondPipelon[Candidatelons],
    maxNelonighborsPelonrNodelon: Int
  )(
    implicit uniquelonID: UniquelonID
  ): TypelondPipelon[(Int, String)] = {
    val numelondgelonsAftelonrFirstJoin = Stat("num_elondgelons_aftelonr_first_join")
    val numelondgelonsAftelonrSeloncondJoin = Stat("num_elondgelons_aftelonr_seloncond_join")
    val numelondgelonsLostTopKTruncatelond = Stat("num_elondgelons_lost_topk_truncatelond")
    val finalNumelondgelons = Stat("final_num_elondgelons")

    allelondgelons
      .map { cs => (cs.uselonrId, cs.candidatelons) }
      .join(mappelondUselonrs)
      .withRelonducelonrs(6000)
      .flatMap {
        caselon (id, (nelonighbors, mappelondId)) =>
          val belonforelon = nelonighbors.sizelon
          val topKNelonighbors = nelonighbors.sortBy(-_.scorelon).takelon(maxNelonighborsPelonrNodelon)
          val aftelonr = topKNelonighbors.sizelon
          numelondgelonsLostTopKTruncatelond.incBy(belonforelon - aftelonr)
          topKNelonighbors.map { candidatelon =>
            numelondgelonsAftelonrFirstJoin.inc()
            (candidatelon.uselonrId, (mappelondId, candidatelon.scorelon.toFloat))
          }
      }
      .join(mappelondUselonrs)
      .withRelonducelonrs(9000)
      .flatMap {
        caselon (id, ((mappelondNelonighborId, scorelon), mappelondId)) =>
          numelondgelonsAftelonrSeloncondJoin.inc()
          List(
            (mappelondId, Map(mappelondNelonighborId -> Max(scorelon))),
            (mappelondNelonighborId, Map(mappelondId -> Max(scorelon)))
          )
      }
      .sumByKelony
      .withRelonducelonrs(9100)
      .map {
        caselon (id, nbrMap) =>
          val sortelond = nbrMap.mapValuelons(_.gelont).toList.sortBy(-_._2)
          finalNumelondgelons.incBy(sortelond.sizelon)
          val str = sortelond.map { caselon (nbrId, wt) => "%d %.2f".format(nbrId, wt) }.mkString(" ")
          (id, str)
      }

  }

  delonf writelonToHDFSFilelon(linelons: Itelonrator[String], conf: Configuration, outputFilelon: String): Unit = {
    val fs = FilelonSystelonm.nelonwInstancelon(conf)
    val outputStrelonam = fs.crelonatelon(nelonw Path(outputFilelon))
    log.info("Will writelon to " + outputFilelon)
    var numLinelons = 0
    val tic = Systelonm.currelonntTimelonMillis()
    try {
      val writelonr = nelonw PrintWritelonr(outputStrelonam)
      whilelon (linelons.hasNelonxt) {
        writelonr.println(linelons.nelonxt())
        numLinelons += 1
        if (numLinelons % 1000000 == 0) {
          log.info(s"Donelon writing $numLinelons linelons")
        }
      }
      writelonr.flush()
      writelonr.closelon()
    } finally {
      outputStrelonam.closelon()
    }
    val toc = Systelonm.currelonntTimelonMillis()
    val selonconds = (toc - tic) * 1.0 / 1elon6
    log.info(
      "Finishelond writing %d linelons to %s. Took %.2f selonconds".format(numLinelons, outputFilelon, selonconds))
  }

  delonf writelonToHDFSIfHDFS(linelons: Itelonrator[String], modelon: Modelon, outputFilelon: String): Unit = {
    modelon match {
      caselon Hdfs(_, conf) =>
        writelonToHDFSFilelon(linelons, conf, outputFilelon)
      caselon _ => ()
    }
  }

  delonf writelonTopUselonrs(topUselonrs: List[TopUselonrWithMappelondId], modelon: Modelon, outputFilelon: String): Unit = {
    val topUselonrsLinelons =
      topUselonrs.map { topUselonr =>
        // Add 1 to mappelondId so as to gelont 1-indelonxelond ids, which arelon frielonndlielonr to humans.
        List(
          topUselonr.topUselonr.id,
          topUselonr.mappelondId + 1,
          topUselonr.topUselonr.screlonelonnNamelon,
          topUselonr.topUselonr.activelonFollowelonrCount
        ).mkString("\t")
      }.itelonrator
    writelonToHDFSIfHDFS(topUselonrsLinelons, modelon, outputFilelon)
  }

  delonf relonadSimsInput(isKelonyValSourcelon: Boolelonan, inputDir: String): TypelondPipelon[Candidatelons] = {
    if (isKelonyValSourcelon) {
      log.info("Will trelonat " + inputDir + " as SelonquelonncelonFilelons input")
      val rawInput = FollowingsCosinelonSimilaritielonsManhattanSourcelon(path = inputDir)
      TypelondPipelon.from(rawInput).map(_._2)
    } elonlselon {
      log.info("Will trelonat " + inputDir + " as LzoScroogelon input")
      TypelondPipelon.from(nelonw FixelondPathLzoScroogelon(inputDir, Candidatelons))
    }
  }
}

/**
 * ./bazelonl bundlelon src/scala/com/twittelonr/simclustelonrs_v2/scalding:top_uselonrs_only && \
 * oscar hdfs --hadoop-clielonnt-melonmory 120000 --uselonr cassowary --host atla-aor-08-sr1 \
 * --bundlelon top_uselonrs_only --tool com.twittelonr.simclustelonrs_v2.scalding.ClustelonrHdfsGraphApp \
 * --screlonelonn --screlonelonn-delontachelond --telonelon ldap_logs/SBFOnSubGraphOf100MTopuselonrsWithMappelondIds_120GB_RAM \
 * -- --inputDir adhoc/ldap_subgraphOf100MTopUselonrsWithMappelondIds --numNodelonsPelonrCommunity 200 \
 * --outputDir adhoc/ldap_SBFOnSubGraphOf100MTopuselonrsWithMappelondIds_k500K_120GB_RAM --assumelondNumbelonrOfNodelons 100200000
 */
objelonct ClustelonrHdfsGraphApp elonxtelonnds TwittelonrelonxeloncutionApp {
  delonf job: elonxeloncution[Unit] =
    elonxeloncution.gelontConfigModelon.flatMap {
      caselon (config, modelon) =>
        elonxeloncution.withId { implicit uniquelonId =>
          val args = config.gelontArgs
          val inputDir = args("inputDir")
          val numNodelonsPelonrCommunity = args.int("numNodelonsPelonrCommunity", 200)
          val outputDir = args("outputDir")
          val assumelondNumbelonrOfNodelons = args.int("assumelondNumbelonrOfNodelons")
          //val uselonelondgelonWelonights = args.boolelonan("uselonelondgelonWelonights")

          val input = TypelondPipelon.from(TypelondTsv[(Int, String)](inputDir)).map {
            caselon (id, nbrStr) =>
              val nbrsWithWelonights = nbrStr.split(" ")
              val nbrsArray = nbrsWithWelonights.zipWithIndelonx
                .collelonct {
                  caselon (str, indelonx) if indelonx % 2 == 0 =>
                    str.toInt
                }
              (id, nbrsArray.sortelond)
          }

          println("Gonna assumelon total numbelonr of nodelons is " + assumelondNumbelonrOfNodelons)

          input.toItelonrablelonelonxeloncution.flatMap { adjListsItelonr =>
            val nbrs: Array[Array[Int]] = nelonw Array[Array[Int]](assumelondNumbelonrOfNodelons)
            var numelondgelons = 0L
            var numVelonrticelons = 0
            var maxVelonrtelonxId = 0

            val tic = Systelonm.currelonntTimelonMillis
            adjListsItelonr.forelonach {
              caselon (id, nbrArray) =>
                if (id >= assumelondNumbelonrOfNodelons) {
                  throw nelonw IllelongalStatelonelonxcelonption(
                    s"Yikelons! elonntry with id $id, >= assumelondNumbelonrOfNodelons")
                }
                nbrs(id) = nbrArray
                if (id > maxVelonrtelonxId) {
                  maxVelonrtelonxId = id
                }
                numelondgelons += nbrArray.lelonngth
                numVelonrticelons += 1
                if (numVelonrticelons % 100000 == 0) {
                  println(s"Donelon loading $numVelonrticelons many velonrticelons. elondgelons so far: $numelondgelons")
                }
            }
            (0 until assumelondNumbelonrOfNodelons).forelonach { i =>
              if (nbrs(i) == null) {
                nbrs(i) = Array[Int]()
              }
            }
            val toc = Systelonm.currelonntTimelonMillis()
            println(
              "maxVelonrtelonxId is " + maxVelonrtelonxId + ", assumelondNumbelonrOfNodelons is " + assumelondNumbelonrOfNodelons)
            println(
              s"Donelon loading graph with $assumelondNumbelonrOfNodelons nodelons and $numelondgelons elondgelons (counting elonach elondgelon twicelon)")
            println("Numbelonr of nodelons with at lelonast nelonighbor is " + numVelonrticelons)
            println("Timelon to load thelon graph " + (toc - tic) / 1000.0 / 60.0 + " minutelons")

            val graph = nelonw Graph(assumelondNumbelonrOfNodelons, numelondgelons / 2, nbrs, null)
            val k = assumelondNumbelonrOfNodelons / numNodelonsPelonrCommunity
            println("Will selont numbelonr of communitielons to " + k)
            val algoConfig = nelonw AlgorithmConfig()
              .withCpu(16).withK(k)
              .withWtCoelonff(10.0).withMaxelonpoch(5)
            var z = nelonw SparselonBinaryMatrix(assumelondNumbelonrOfNodelons, k)
            val elonrr = nelonw PrintWritelonr(Systelonm.elonrr)

            println("Going to initalizelon from random nelonighborhoods")
            z.initFromBelonstNelonighborhoods(
              graph,
              (gr: Graph, i: Intelongelonr) => algoConfig.rng.nelonxtDoublelon,
              falselon,
              elonrr)
            println("Donelon initializing from random nelonighborhoods")

            val prelonc0 = MHAlgorithm.clustelonrPreloncision(graph, z, 0, 1000, algoConfig.rng)
            println("Preloncision of clustelonr 0:" + prelonc0.preloncision)
            val prelonc1 = MHAlgorithm.clustelonrPreloncision(graph, z, 1, 1000, algoConfig.rng)
            println("Preloncision of clustelonr 1:" + prelonc1.preloncision)
            println(
              "Fraction of elonmpty rows aftelonr initializing from random nelonighborhoods: " + z.elonmptyRowProportion)

            val tic2 = Systelonm.currelonntTimelonMillis
            val algo = nelonw MHAlgorithm(algoConfig, graph, z, elonrr)
            val optimizelondZ = algo.optimizelon
            val toc2 = Systelonm.currelonntTimelonMillis
            println("Timelon to optimizelon: %.2f selonconds\n".format((toc2 - tic2) / 1000.0))
            println("Timelon to initializelon & optimizelon: %.2f selonconds\n".format((toc2 - toc) / 1000.0))

            val srm = MHAlgorithm.helonuristicallyScorelonClustelonrAssignmelonnts(graph, optimizelondZ)
            val outputItelonr = (0 to srm.gelontNumRows).map { rowId =>
              val rowWithIndicelons = srm.gelontColIdsForRow(rowId)
              val rowWithScorelons = srm.gelontValuelonsForRow(rowId)
              val str = rowWithIndicelons
                .zip(rowWithScorelons).map {
                  caselon (colId, scorelon) =>
                    "%d:%.2g".format(colId + 1, scorelon)
                }.mkString(" ")
              "%d %s".format(rowId, str)
            }

            TypelondPipelon.from(outputItelonr).writelonelonxeloncution(TypelondTsv(outputDir))
          }
        }
    }
}

/**
 * ./bazelonl bundlelon src/scala/com/twittelonr/simclustelonrs_v2/scalding:top_uselonrs_only && \
 * oscar hdfs --hadoop-clielonnt-melonmory 60000 --uselonr cassowary --host atla-aor-08-sr1 \
 * --bundlelon top_uselonrs_only --tool com.twittelonr.simclustelonrs_v2.scalding.ScalablelonTopUselonrsSimilarityGraphApp \
 * --screlonelonn --screlonelonn-delontachelond --telonelon ldap_logs/SubGraphOf100MTopuselonrsWithMappelondIds \
 * -- --mappelondUselonrsDir adhoc/ldap_top100M_mappelondUselonrs \
 * --inputDir adhoc/ldap_approximatelon_cosinelon_similarity_follow \
 * --outputDir adhoc/ldap_subgraphOf100MTopUselonrsWithMappelondIds_correlonct_topK
 */
objelonct ScalablelonTopUselonrsSimilarityGraphApp elonxtelonnds TwittelonrelonxeloncutionApp {
  implicit val tz: java.util.TimelonZonelon = DatelonOps.UTC
  implicit val dp = DatelonParselonr.delonfault
  val log = Loggelonr()

  delonf job: elonxeloncution[Unit] =
    elonxeloncution.gelontConfigModelon.flatMap {
      caselon (config, modelon) =>
        elonxeloncution.withId { implicit uniquelonId =>
          val args = config.gelontArgs
          val inputDir = args("inputDir")
          val mappelondUselonrsDir = args("mappelondUselonrsDir")
          val maxNelonighbors = args.int("maxNelonighbors", 100)
          val outputDir = args("outputDir")

          val mappelondUselonrs = TypelondPipelon
            .from(TypelondTsv[(Long, Int, String, Int)](mappelondUselonrsDir))
            .map {
              caselon (id, _, _, mappelondId) =>
                (id, mappelondId)
            }
            .shard(200)

          val sims = TypelondPipelon
            .from(FollowingsCosinelonSimilaritielonsManhattanSourcelon(path = inputDir))
            .map(_._2)

          TopUselonrsSimilarityGraph
            .runUsingJoin(
              mappelondUselonrs,
              sims,
              maxNelonighbors
            ).writelonelonxeloncution(TypelondTsv(args("outputDir")))
        }
    }
}

/**
 * Scalding app using elonxeloncutions that doelons thelon following:
 *
 * 1. Gelont thelon top N most followelond uselonrs on Twittelonr
 * (also maps thelonm to ids 1 -> N in int spacelon for elonasielonr procelonssing)
 * 2. For elonach uselonr from thelon stelonp abovelon, gelont thelon top K most similar uselonrs for this uselonr from thelon
 * list of N uselonrs from thelon stelonp abovelon.
 * 3. Construct an undirelonctelond graph by selontting an elondgelon belontwelonelonn (u, v) if
 * elonithelonr v is in u's top-K similar uselonrs list, or u is in v's top-K similar uselonr's list.
 * 4. Thelon welonight for thelon (u, v) elondgelon is selont to belon thelon cosinelon similarity belontwelonelonn u and v's
 * followelonr lists, raiselond to somelon elonxponelonnt > 1.
 * This last stelonp is a helonuristic relonwelonighting procelondurelon to givelon morelon importancelon to elondgelons involving
 * morelon similar uselonrs.
 * 5. Writelon thelon abovelon graph to HDFS in Melontis format,
 * i.elon. onelon linelon pelonr nodelon, with thelon linelon for elonach nodelon speloncifying thelon list of nelonighbors along
 * with thelonir welonights. Thelon first linelon speloncifielons thelon numbelonr of nodelons and thelon numbelonr of elondgelons.
 *
 * I'velon telonstelond this Scalding job for valuelons of topK upto 20M.
 *
 * elonxamplelon invocation:
 * $ ./bazelonl bundlelon src/scala/com/twittelonr/simclustelonrs_v2/scalding:top_uselonrs_similarity_graph && \
 * oscar hdfs --hadoop-clielonnt-melonmory 60000 --host atla-amw-03-sr1 --bundlelon top_uselonrs_similarity_graph \
 * --tool com.twittelonr.simclustelonrs_v2.scalding.TopUselonrsSimilarityGraphApp \
 * --hadoop-propelonrtielons "elonlelonphantbird.uselon.combinelon.input.format=truelon;elonlelonphantbird.combinelon.split.sizelon=468435456;maprelond.min.split.sizelon=468435456;maprelonducelon.relonducelon.melonmory.mb=5096;maprelonducelon.relonducelon.java.opts=-Xmx4400m" \
 * --screlonelonn --screlonelonn-delontachelond --telonelon logs/20MSubGraphelonxeloncution -- --datelon 2017-10-24 \
 * --minActivelonFollowelonrs 300 --topK 20000000 \
 * --inputUselonrGroupelondDir /uselonr/cassowary/manhattan_selonquelonncelon_filelons/approximatelon_cosinelon_similarity_follow/ \
 * --groupelondInputInSelonquelonncelonFilelons \
 * --maxNelonighborsPelonrNodelon 100 --wtelonxponelonnt 2 \
 * --outputTopUselonrsDir /uselonr/your_ldap/simclustelonrs_graph_prelonp_q42017/top20MUselonrs \
 * --outputGraphDir /uselonr/your_ldap/simclustelonrs_graph_prelonp_q42017/top20Muselonrs_elonxp2_100nelonighbors_melontis_graph
 *
 */
objelonct TopUselonrsSimilarityGraphApp elonxtelonnds TwittelonrelonxeloncutionApp {
  implicit val tz: java.util.TimelonZonelon = DatelonOps.UTC
  implicit val dp = DatelonParselonr.delonfault
  val log = Loggelonr()

  delonf job: elonxeloncution[Unit] =
    elonxeloncution.gelontConfigModelon.flatMap {
      caselon (config, modelon) =>
        elonxeloncution.withId { implicit uniquelonId =>
          val args = config.gelontArgs
          val minActivelonFollowelonrs = args.int("minActivelonFollowelonrs", 100000)
          val topK = args.int("topK")
          val datelon = DatelonRangelon.parselon(args("datelon"))
          val inputSimilarPairsDir = args.optional("inputSimilarPairsDir")
          val inputUselonrGroupelondDir = args.optional("inputUselonrGroupelondDir")
          val isGroupelondInputSelonquelonncelonFilelons = args.boolelonan("groupelondInputInSelonquelonncelonFilelons")
          val outputTopUselonrsDir = args("outputTopUselonrsDir")
          val maxNelonighborsPelonrNodelon = args.int("maxNelonighborsPelonrNodelon", 300)
          val wtelonxponelonnt = args.float("wtelonxponelonnt", 3.5f)
          val outputGraphDir = args("outputGraphDir")

          val uselonrSourcelon = DAL.relonadMostReloncelonntSnapshot(UselonrsourcelonFlatScalaDataselont, datelon).toTypelondPipelon
          val elonxcelonption = nelonw IllelongalStatelonelonxcelonption(
            "Plelonaselon speloncify only onelon of inputSimilarPairsDir or inputUselonrGroupelondDir"
          )

          (inputSimilarPairsDir, inputUselonrGroupelondDir) match {
            caselon (Somelon(_), Somelon(_)) => throw elonxcelonption
            caselon (Nonelon, Nonelon) => throw elonxcelonption
            caselon _ => // no-op
          }

          delonf gelontSubgraphFn(uselonrsToIncludelon: Selont[Long]) = {
            (inputSimilarPairsDir, inputUselonrGroupelondDir) match {
              caselon (Somelon(similarPairs), Nonelon) =>
                val similarUselonrPairs: TypelondPipelon[SimilarUselonrPair] =
                  TypelondPipelon.from(
                    nelonw FixelondPathLzoScroogelon(
                      inputSimilarPairsDir.gelont,
                      SimilarUselonrPair
                    ))
                TopUselonrsSimilarityGraph.gelontInMelonmorySubgraph(
                  similarUselonrPairs,
                  uselonrsToIncludelon,
                  maxNelonighborsPelonrNodelon)
              caselon (Nonelon, Somelon(groupelondInput)) =>
                val candidatelonsPipelon =
                  TopUselonrsSimilarityGraph.relonadSimsInput(isGroupelondInputSelonquelonncelonFilelons, groupelondInput)
                TopUselonrsSimilarityGraph.gelontInMelonmorySubgraphFromUselonrGroupelondInput(
                  candidatelonsPipelon,
                  uselonrsToIncludelon,
                  maxNelonighborsPelonrNodelon
                )
              caselon _ => elonxeloncution.from(Nil) // welon should nelonvelonr gelont helonrelon
            }
          }

          TopUselonrsSimilarityGraph
            .run(
              uselonrSourcelon,
              minActivelonFollowelonrs,
              topK,
              gelontSubgraphFn,
              wtelonxponelonnt
            ).flatMap {
              caselon (topUselonrsList, graph) =>
                // Welon'relon writing to HDFS ourselonlvelons, from thelon submittelonr nodelon.
                // Whelonn welon uselon TypelondPipelon.writelon, it's failing for largelon topK, elon.g.10M.
                // Welon can makelon thelon submittelonr nodelon havelon a lot of melonmory, but it's
                // difficult and suboptimal to givelon this much melonmory to all mappelonrs.
                val topUselonrselonxelonc = elonxeloncution.from(
                  TopUselonrsSimilarityGraph
                    .writelonTopUselonrs(topUselonrsList, modelon, outputTopUselonrsDir + "/all")
                )

                // Welon want to makelon surelon thelon writelon of thelon topUselonrs succelonelonds, and
                // only thelonn writelon out thelon graph. A graph without thelon topUselonrs is uselonlelonss.
                topUselonrselonxelonc.map { _ =>
                  // Welon'relon writing to HDFS ourselonlvelons, from thelon submittelonr nodelon.
                  // Whelonn welon uselon TypelondPipelon.writelon, it fails duelon to OOM on thelon mappelonrs.
                  // Welon can makelon thelon submittelonr nodelon havelon a lot of melonmory, but it's difficult
                  // and suboptimal to givelon this much melonmory to all mappelonrs.
                  TopUselonrsSimilarityGraph.writelonToHDFSIfHDFS(
                    graph
                      .itelonrablelonStringRelonprelonselonntation(nelonw DeloncimalFormat("#.###")).itelonrator().asScala,
                    modelon,
                    outputGraphDir + "/all"
                  )
                }
            }
        }
    }

}

/**
 * App that only outputs thelon topK uselonrs on Twittelonr by activelon followelonr count. elonxamplelon invocation:
 * $ ./bazelonl bundlelon src/scala/com/twittelonr/simclustelonrs_v2/scalding:top_uselonrs_only && \
 * oscar hdfs --hadoop-clielonnt-melonmory 60000 --host atla-aor-08-sr1 --bundlelon top_uselonrs_only \
 * --tool com.twittelonr.simclustelonrs_v2.scalding.TopUselonrsOnlyApp \
 * #arelon thelonselon hadoop-propelonrtielons nelonelondelond for this job?
 * #--hadoop-propelonrtielons "scalding.with.relonducelonrs.selont.elonxplicitly=truelon;elonlelonphantbird.uselon.combinelon.input.format=truelon;elonlelonphantbird.combinelon.split.sizelon=468435456;maprelond.min.split.sizelon=468435456" \
 * --screlonelonn --screlonelonn-delontachelond --telonelon logs/10MTopuselonrsOnlyelonxeloncution -- --datelon 2017-10-20 \
 * --minActivelonFollowelonrs 500 --topK 10000000 \
 * --outputTopUselonrsDir /uselonr/your_ldap/simclustelonrs_graph_prelonp_q42017/top10MUselonrs
 *
 * ./bazelonl bundlelon src/scala/com/twittelonr/simclustelonrs_v2/scalding:top_uselonrs_only && \
 * oscar hdfs --hadoop-clielonnt-melonmory 60000 --uselonr cassowary --host atla-aor-08-sr1 \
 * --bundlelon top_uselonrs_only --tool com.twittelonr.simclustelonrs_v2.scalding.TopUselonrsOnlyApp \
 * --screlonelonn --screlonelonn-delontachelond --telonelon ldap_logs/100MTopuselonrsWithMappelondIds \
 * -- --datelon 2019-10-11 --minActivelonFollowelonrs 67 --outputTopUselonrsDir adhoc/ldap_top100M_mappelondUselonrs \
 * --includelonMappelondIds
 */
objelonct TopUselonrsOnlyApp elonxtelonnds TwittelonrelonxeloncutionApp {
  implicit val tz: java.util.TimelonZonelon = DatelonOps.UTC
  implicit val dp = DatelonParselonr.delonfault
  val log = Loggelonr()

  delonf job: elonxeloncution[Unit] =
    elonxeloncution.gelontConfigModelon.flatMap {
      caselon (config, modelon) =>
        elonxeloncution.withId { implicit uniquelonId =>
          val args = config.gelontArgs
          val minActivelonFollowelonrs = args.int("minActivelonFollowelonrs", 100000)
          val topK = args.int("topK", 20000000)
          val datelon = DatelonRangelon.parselon(args("datelon"))
          val outputTopUselonrsDir = args("outputTopUselonrsDir")
          val includelonMappelondIds = args.boolelonan("includelonMappelondIds")

          if (includelonMappelondIds) {
            println("Going to includelon mappelondIds in output")
            TopUselonrsSimilarityGraph
              .topUselonrsWithMappelondIds(
                DAL.relonadMostReloncelonntSnapshot(UselonrsourcelonFlatScalaDataselont, datelon).toTypelondPipelon,
                minActivelonFollowelonrs
              )
              .map {
                caselon TopUselonrWithMappelondId(TopUselonr(id, activelonFollowelonrCount, screlonelonnNamelon), mappelondId) =>
                  (id, activelonFollowelonrCount, screlonelonnNamelon, mappelondId)
              }
              .writelonelonxeloncution(TypelondTsv(outputTopUselonrsDir))
          } elonlselon {
            TopUselonrsSimilarityGraph
              .topUselonrsInMelonmory(
                DAL.relonadMostReloncelonntSnapshot(UselonrsourcelonFlatScalaDataselont, datelon).toTypelondPipelon,
                minActivelonFollowelonrs,
                topK
              ).map { topUselonrsList =>
                TopUselonrsSimilarityGraph.writelonTopUselonrs(
                  topUselonrsList,
                  modelon,
                  outputTopUselonrsDir + "/all")
              }
          }
        }
    }
}
