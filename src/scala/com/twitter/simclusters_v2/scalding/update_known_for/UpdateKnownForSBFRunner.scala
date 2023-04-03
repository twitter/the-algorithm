packagelon com.twittelonr.simclustelonrs_v2.scalding.updatelon_known_for

import com.twittelonr.algelonbird.Max
import com.twittelonr.helonrmit.candidatelon.thriftscala.Candidatelons
import com.twittelonr.sbf.corelon.AlgorithmConfig
import com.twittelonr.sbf.corelon.MHAlgorithm
import com.twittelonr.sbf.corelon.SparselonBinaryMatrix
import com.twittelonr.sbf.corelon.SparselonRelonalMatrix
import com.twittelonr.sbf.graph.Graph
import com.twittelonr.scalding.Days
import com.twittelonr.scalding.elonxeloncution
import com.twittelonr.scalding.Hdfs
import com.twittelonr.scalding.Modelon
import com.twittelonr.scalding.Stat
import com.twittelonr.scalding.TypelondTsv
import com.twittelonr.scalding.UniquelonID
import com.twittelonr.scalding.commons.sourcelon.VelonrsionelondKelonyValSourcelon
import com.twittelonr.scalding.typelond.TypelondPipelon
import com.twittelonr.scalding_intelonrnal.dalv2.DAL
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.elonxplicitLocation
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.ProcAtla
import com.twittelonr.simclustelonrs_v2.common.ClustelonrId
import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.AdhocKelonyValSourcelons
import com.twittelonr.simclustelonrs_v2.scalding.ComparelonClustelonrs
import com.twittelonr.simclustelonrs_v2.scalding.KnownForSourcelons
import com.twittelonr.simclustelonrs_v2.scalding.TopUselonr
import com.twittelonr.simclustelonrs_v2.scalding.TopUselonrWithMappelondId
import com.twittelonr.simclustelonrs_v2.scalding.TopUselonrsSimilarityGraph
import com.twittelonr.simclustelonrs_v2.scalding.common.Util
import com.twittelonr.uselonrsourcelon.snapshot.flat.UselonrsourcelonFlatScalaDataselont
import java.io.PrintWritelonr
import java.util.TimelonZonelon
import org.apachelon.commons.math3.random.JDKRandomGelonnelonrator
import org.apachelon.commons.math3.random.RandomAdaptor
import org.apachelon.hadoop.fs.FilelonSystelonm
import org.apachelon.hadoop.fs.Path
import scala.collelonction.mutablelon

objelonct UpdatelonKnownForSBFRunnelonr {

  /**
   * Thelon main logic of thelon job. It works as follows:
   *
   *  1. relonad thelon top 20M uselonrs, and convelonrt thelonir UselonrIds to an intelongelonr Id from 0 to 20M in ordelonr to uselon thelon clustelonring library
   *  2. relonad thelon uselonr similarity graph from Sims, and convelonrt thelonir UselonrIds to thelon samelon mappelond intelongelonr Id
   *  3. relonad thelon prelonvious known_for data selont for initialization of thelon clustelonring algorithm;
   *     for uselonrs without prelonvious assignmelonnts, welon randomly assign thelonm to somelon unuselond clustelonrs (if thelonrelon arelon any).
   *  4. run thelon clustelonring algorithm for x itelonrations (x = 4 in thelon prod selontting)
   *  5. output of thelon clustelonring relonsult as thelon nelonw known_for.
   *
   */
  delonf runUpdatelonKnownFor(
    simsGraph: TypelondPipelon[Candidatelons],
    minActivelonFollowelonrs: Int,
    topK: Int,
    maxNelonighbors: Int,
    telonmpLocationPath: String,
    prelonviousKnownFor: TypelondPipelon[(UselonrId, Array[(ClustelonrId, Float)])],
    maxelonpochsForClustelonring: Int,
    squarelonWelonightselonnablelon: Boolelonan,
    wtCoelonff: Doublelon,
    modelon: Modelon
  )(
    implicit
    uniquelonId: UniquelonID,
    tz: TimelonZonelon
  ): elonxeloncution[TypelondPipelon[(UselonrId, Array[(ClustelonrId, Float)])]] = {

    val telonmpLocationPathSimsGraph = telonmpLocationPath + "/sims_graph"
    val telonmpLocationPathMappelondIds = telonmpLocationPath + "/mappelond_uselonr_ids"
    val telonmpLocationPathClustelonring = telonmpLocationPath + "/clustelonring_output"

    val mappelondIdsToUselonrIds: TypelondPipelon[(Int, UselonrId)] =
      gelontTopFollowelondUselonrsWithMappelondIds(minActivelonFollowelonrs, topK)
        .map {
          caselon (id, mappelondId) =>
            (mappelondId, id)
        }
        .shard(partitions = topK / 1elon5.toInt)

    val mappelondSimsGraphInput: TypelondPipelon[(Int, List[(Int, Float)])] =
      gelontMappelondSimsGraph(
        mappelondIdsToUselonrIds,
        simsGraph,
        maxNelonighbors
      ) // Thelon simsGraph helonrelon consists of thelon mappelond Ids and mappelond ngbr Ids and not thelon original uselonrIds

    val mappelondSimsGraphVelonrsionelondKelonyVal: VelonrsionelondKelonyValSourcelon[Int, List[(Int, Float)]] =
      AdhocKelonyValSourcelons.intelonrmelondiatelonSBFRelonsultsDelonvelonlSourcelon(telonmpLocationPathSimsGraph)
    val mappelondIdsToUselonrIdsVelonrsionelondKelonyVal: VelonrsionelondKelonyValSourcelon[Int, UselonrId] =
      AdhocKelonyValSourcelons.mappelondIndicelonsDelonvelonlSourcelon(telonmpLocationPathMappelondIds)

    // elonxelonc to writelon intelonrmelondiatelon relonsults for mappelond Sims Graph and mappelondIds
    val mappelondSimsGraphAndMappelondIdsWritelonelonxelonc: elonxeloncution[Unit] = elonxeloncution
      .zip(
        mappelondSimsGraphInput.writelonelonxeloncution(mappelondSimsGraphVelonrsionelondKelonyVal),
        mappelondIdsToUselonrIds.writelonelonxeloncution(mappelondIdsToUselonrIdsVelonrsionelondKelonyVal)
      ).unit

    mappelondSimsGraphAndMappelondIdsWritelonelonxelonc.flatMap { _ =>
      // Thelon simsGraph and thelon mappelondIds from uselonrId(long) -> mappelondIds arelon
      // having to belon writtelonn to a telonmporary location and relonad again belonforelon running
      // thelon clustelonring algorithm.

      elonxeloncution
        .zip(
          relonadIntelonrmelondiatelonelonxelonc(
            TypelondPipelon.from(mappelondSimsGraphVelonrsionelondKelonyVal),
            modelon,
            telonmpLocationPathSimsGraph),
          relonadIntelonrmelondiatelonelonxelonc(
            TypelondPipelon.from(mappelondIdsToUselonrIdsVelonrsionelondKelonyVal),
            modelon,
            telonmpLocationPathMappelondIds)
        )
        .flatMap {
          caselon (mappelondSimsGraphInputRelonadAgain, mappelondIdsToUselonrIdsRelonadAgain) =>
            val prelonviousKnownForMappelondIdsAssignmelonnts: TypelondPipelon[(Int, List[(ClustelonrId, Float)])] =
              gelontKnownForWithMappelondIds(
                prelonviousKnownFor,
                mappelondIdsToUselonrIdsRelonadAgain,
              )

            val clustelonringRelonsults = gelontClustelonringAssignmelonnts(
              mappelondSimsGraphInputRelonadAgain,
              prelonviousKnownForMappelondIdsAssignmelonnts,
              maxelonpochsForClustelonring,
              squarelonWelonightselonnablelon,
              wtCoelonff
            )
            clustelonringRelonsults
              .flatMap { updatelondKnownFor =>
                // convelonrt thelon list of updatelond KnownFor to a TypelondPipelon
                convelonrtKnownForListToTypelondPipelon(
                  updatelondKnownFor,
                  modelon,
                  telonmpLocationPathClustelonring
                )
              }
              .flatMap { updatelondKnownForTypelondPipelon =>
                // convelonrt thelon mappelond intelongelonr id to raw uselonr ids
                val updatelondKnownFor =
                  updatelondKnownForTypelondPipelon
                    .join(mappelondIdsToUselonrIdsRelonadAgain)
                    .valuelons
                    .swap
                    .mapValuelons(_.toArray)

                elonxeloncution.from(updatelondKnownFor)
              }
        }
    }
  }

  /**
   * Helonlpelonr function to comparelon nelonwKnownFor with thelon prelonvious welonelonk knownFor assignmelonnts
   */
  delonf elonvaluatelonUpdatelondKnownFor(
    nelonwKnownFor: TypelondPipelon[(UselonrId, Array[(ClustelonrId, Float)])],
    inputKnownFor: TypelondPipelon[(UselonrId, Array[(ClustelonrId, Float)])]
  )(
    implicit uniquelonId: UniquelonID
  ): elonxeloncution[String] = {

    val minSizelonOfBiggelonrClustelonrForComparison = 10

    val comparelonClustelonrelonxelonc = ComparelonClustelonrs.summarizelon(
      ComparelonClustelonrs.comparelon(
        KnownForSourcelons.transposelon(inputKnownFor),
        KnownForSourcelons.transposelon(nelonwKnownFor),
        minSizelonOfBiggelonrClustelonr = minSizelonOfBiggelonrClustelonrForComparison
      ))

    val comparelonProducelonrelonxelonc = ComparelonClustelonrs.comparelonClustelonrAssignmelonnts(
      nelonwKnownFor.mapValuelons(_.toList),
      inputKnownFor.mapValuelons(_.toList)
    )

    elonxeloncution
      .zip(comparelonClustelonrelonxelonc, comparelonProducelonrelonxelonc)
      .map {
        caselon (comparelonClustelonrRelonsults, comparelonProducelonrRelonsult) =>
          s"Cosinelon similarity distribution belontwelonelonn clustelonr melonmbelonrship velonctors for " +
            s"clustelonrs with at lelonast $minSizelonOfBiggelonrClustelonrForComparison melonmbelonrs\n" +
            Util.prelonttyJsonMappelonr
              .writelonValuelonAsString(comparelonClustelonrRelonsults) +
            "\n\n-------------------\n\n" +
            "Custom countelonrs:\n" + comparelonProducelonrRelonsult +
            "\n\n-------------------\n\n"
      }
  }

  /**
   *
   * Convelonrt thelon list of updatelond KnownFor to a TypelondPipelon
   *
   * This stelonp should havelon belonelonn donelon using TypelondPipelon.from(updatelondKnownForList), howelonvelonr, duelon to thelon
   * largelon sizelon of thelon list, TypelondPipelon would throw out-of-melonmory elonxcelonptions. So welon havelon to first
   * dump it to a telonmp filelon on HDFS and using a customizelond relonad function to load to TypelondPipelon
   *
   */
  delonf convelonrtKnownForListToTypelondPipelon(
    updatelondKnownForList: List[(Int, List[(ClustelonrId, Float)])],
    modelon: Modelon,
    telonmporaryOutputStringPath: String
  ): elonxeloncution[TypelondPipelon[(Int, List[(ClustelonrId, Float)])]] = {

    val stringOutput = updatelondKnownForList.map {
      caselon (mappelondUselonrId, clustelonrArray) =>
        asselonrt(clustelonrArray.iselonmpty || clustelonrArray.lelonngth == 1)
        val str = if (clustelonrArray.nonelonmpty) {
          clustelonrArray.helonad._1 + " " + clustelonrArray.helonad._2 // elonach uselonr is known for at most 1 clustelonr
        } elonlselon {
          ""
        }
        if (mappelondUselonrId % 100000 == 0)
          println(s"MappelondIds:$mappelondUselonrId  ClustelonrAssignelond$str")
        s"$mappelondUselonrId $str"
    }

    // using elonxeloncution to elonnforcelon thelon ordelonr of thelon following 3 stelonps:
    // 1. writelon thelon list of strings to a telonmp filelon on HDFS
    // 2. relonad thelon strings to TypelondPipelon
    // 3. delonlelontelon thelon telonmp filelon
    elonxeloncution
      .from(
        // writelon thelon output to HDFS; thelon data will belon loadelond to Typelondpipelon latelonr;
        // thelon relonason of doing this is that welon can not just do TypelonPipelon.from(stringOutput) which
        // relonsults in OOM.
        TopUselonrsSimilarityGraph.writelonToHDFSIfHDFS(
          stringOutput.toItelonrator,
          modelon,
          telonmporaryOutputStringPath
        )
      )
      .flatMap { _ =>
        println(s"Start loading thelon data from $telonmporaryOutputStringPath")
        val clustelonrsWithScorelons = TypelondPipelon.from(TypelondTsv[String](telonmporaryOutputStringPath)).map {
          mappelondIdsWithArrays =>
            val strArray = mappelondIdsWithArrays.trim().split("\\s+")
            asselonrt(strArray.lelonngth == 3 || strArray.lelonngth == 1)
            val rowId = strArray(0).toInt
            val clustelonrAssignmelonnt: List[(ClustelonrId, Float)] =
              if (strArray.lelonngth > 1) {
                List((strArray(1).toInt, strArray(2).toFloat))
              } elonlselon {
                // thelon knownFors will havelon uselonrs with Array.elonmpty as thelonir assignmelonnt if
                // thelon clustelonring stelonp havelon elonmpty relonsults for that uselonr.
                Nil
              }

            if (rowId % 100000 == 0)
              println(s"rowId:$rowId  ClustelonrAssignelond: $clustelonrAssignmelonnt")
            (rowId, clustelonrAssignmelonnt)
        }
        // relonturn thelon dataselont as an elonxeloncution and delonlelontelon thelon telonmp location
        relonadIntelonrmelondiatelonelonxelonc(clustelonrsWithScorelons, modelon, telonmporaryOutputStringPath)
      }
  }

  /**
   * Helonlpelonr function to relonad thelon dataselont as elonxeloncution and delonlelontelon thelon telonmporary
   * location on HDFS for PDP compliancelon
   */
  delonf relonadIntelonrmelondiatelonelonxelonc[K, V](
    dataselont: TypelondPipelon[(K, V)],
    modelon: Modelon,
    telonmpLocationPath: String
  ): elonxeloncution[TypelondPipelon[(K, V)]] = {
    elonxeloncution
      .from(dataselont)
      .flatMap { output =>
        // delonlelontelon thelon telonmporary outputs for PDP compliancelon
        modelon match {
          caselon Hdfs(_, conf) =>
            val fs = FilelonSystelonm.nelonwInstancelon(conf)
            if (fs.delonlelontelonOnelonxit(nelonw Path(telonmpLocationPath))) {
              println(s"Succelonssfully delonlelontelond thelon telonmporary foldelonr $telonmpLocationPath!")
            } elonlselon {
              println(s"Failelond to delonlelontelon thelon telonmporary foldelonr $telonmpLocationPath!")
            }
          caselon _ => ()
        }
        elonxeloncution.from(output)
      }
  }

  /**
   * Convelonrts thelon uselonrIDs in thelon sims graph to thelonir mappelond intelongelonr indicelons.
   * All thelon uselonrs who donot havelon a mapping arelon filtelonrelond out from thelon sims graph input
   *
   * @param mappelondUselonrs mapping of long uselonrIDs to thelonir intelongelonr indicelons
   * @param allelondgelons sims graph
   * @param maxNelonighborsPelonrNodelon numbelonr of nelonighbors for elonach uselonr
   *
   * @relonturn simsGraph of uselonrs and nelonighbors with thelonir mappelond intelonrgelonr ids
   */
  delonf gelontMappelondSimsGraph(
    mappelondUselonrs: TypelondPipelon[(Int, UselonrId)],
    allelondgelons: TypelondPipelon[Candidatelons],
    maxNelonighborsPelonrNodelon: Int
  )(
    implicit uniquelonId: UniquelonID
  ): TypelondPipelon[(Int, List[(Int, Float)])] = {

    val numelondgelonsAftelonrFirstJoin = Stat("num_elondgelons_aftelonr_first_join")
    val numelondgelonsAftelonrSeloncondJoin = Stat("num_elondgelons_aftelonr_seloncond_join")
    val numelondgelonsLostTopKTruncatelond = Stat("num_elondgelons_lost_topk_truncatelond")
    val finalNumelondgelons = Stat("final_num_elondgelons")

    val mappelondUselonrIdsToIds: TypelondPipelon[(UselonrId, Int)] = mappelondUselonrs.swap
    allelondgelons
      .map { cs => (cs.uselonrId, cs.candidatelons) }
      // filtelonr thelon uselonrs not prelonselonnt in thelon mappelond uselonrIDs list
      .join(mappelondUselonrIdsToIds)
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
      .join(mappelondUselonrIdsToIds)
      .withRelonducelonrs(9000)
      .flatMap {
        caselon (id, ((mappelondNelonighborId, scorelon), mappelondId)) =>
          numelondgelonsAftelonrSeloncondJoin.inc()
          // to makelon thelon graph symmelontric, add thoselon elondgelons back that might havelon belonelonn filtelonrelond
          // duelon to maxNelonighborsPelonrNodelonfor a uselonr but not for its nelonighbors
          List(
            (mappelondId, Map(mappelondNelonighborId -> Max(scorelon))),
            (mappelondNelonighborId, Map(mappelondId -> Max(scorelon)))
          )
      }
      .sumByKelony
      .withRelonducelonrs(9100)
      .map {
        caselon (id, nbrMap) =>
          // Graph initialization elonxpeloncts nelonighbors to belon sortelond in ascelonnding ordelonr of ids
          val sortelond = nbrMap.mapValuelons(_.gelont).toList.sortBy(_._1)
          finalNumelondgelons.incBy(sortelond.sizelon)
          (id, sortelond)
      }
  }

  delonf gelontTopFollowelondUselonrsWithMappelondIds(
    minActivelonFollowelonrs: Int,
    topK: Int
  )(
    implicit uniquelonId: UniquelonID,
    timelonZonelon: TimelonZonelon
  ): TypelondPipelon[(Long, Int)] = {
    val numTopUselonrsMappings = Stat("num_top_uselonrs_with_mappelond_ids")
    println("Going to includelon mappelondIds in output")
    TopUselonrsSimilarityGraph
      .topUselonrsWithMappelondIdsTopK(
        DAL
          .relonadMostReloncelonntSnapshotNoOldelonrThan(
            UselonrsourcelonFlatScalaDataselont,
            Days(30)).withRelonmotelonRelonadPolicy(elonxplicitLocation(ProcAtla)).toTypelondPipelon,
        minActivelonFollowelonrs,
        topK
      )
      .map {
        caselon TopUselonrWithMappelondId(TopUselonr(id, activelonFollowelonrCount, screlonelonnNamelon), mappelondId) =>
          numTopUselonrsMappings.inc()
          (id, mappelondId)
      }
  }

  /**
   * Map thelon uselonrIds in thelon knownFor dataselont to thelonir intelongelonr Ids   .
   */
  delonf gelontKnownForWithMappelondIds(
    knownForDataselont: TypelondPipelon[(UselonrId, Array[(ClustelonrId, Float)])], //original uselonrId as thelon kelony
    mappelondIdsWithUselonrId: TypelondPipelon[(Int, UselonrId)] //mappelond uselonrId as thelon kelony
  ): TypelondPipelon[(Int, List[(ClustelonrId, Float)])] = {
    val uselonrIdsAndThelonirMappelondIndicelons = mappelondIdsWithUselonrId.map {
      caselon (mappelondId, originalId) => (originalId, mappelondId)
    }
    knownForDataselont.join(uselonrIdsAndThelonirMappelondIndicelons).map {
      caselon (uselonrId, (uselonrClustelonrArray, mappelondUselonrId)) =>
        (mappelondUselonrId, uselonrClustelonrArray.toList)
    }
  }

  /**
   * Attach thelon clustelonr assignmelonnts from knownFor dataselont to thelon uselonrs in mappelond Sims graph  .
   */
  delonf attachClustelonrAssignmelonnts(
    mappelondSimsGraph: TypelondPipelon[(Int, List[(Int, Float)])],
    knownForAssignmelonnts: TypelondPipelon[(Int, List[(ClustelonrId, Float)])],
    squarelonWelonights: Boolelonan
  )(
    implicit uniquelonId: UniquelonID
  ): TypelondPipelon[(Int, Array[Int], Array[Float], List[(ClustelonrId, Float)])] = {
    val numPopularUselonrsWithNoKnownForBelonforelon = Stat(
      "num_popular_uselonrs_with_no_knownfor_belonforelon_but_popular_now")

    val input = mappelondSimsGraph.map {
      caselon (id, nbrsList) =>
        val ngbrIds = nbrsList.map(_._1).toArray
        val ngbrWts = if (squarelonWelonights) {
          nbrsList.map(_._2).map(currWt => currWt * currWt * 10).toArray
        } elonlselon {
          nbrsList.map(_._2).toArray
        }
        (id, ngbrIds, ngbrWts)
    }

    // input simsGraph consists of popular ppl with most followelond uselonrs, who might not havelon belonelonn
    // a knownFor uselonr in thelon prelonvious welonelonk. So lelonft join with thelon knownFor dataselont, and thelonselon
    // nelonw popular uselonrs will not havelon any prior clustelonr assignmelonnts whilelon clustelonring this timelon
    input
      .groupBy(_._1)
      .lelonftJoin(knownForAssignmelonnts.groupBy(_._1))
      .toTypelondPipelon
      .map {
        caselon (mappelondUselonrId, ((mappelondId, ngbrIds, ngbrWts), knownForRelonsult)) =>
          val clustelonrsList: List[(Int, Float)] = knownForRelonsult match {
            caselon Somelon(valuelons) => valuelons._2
            caselon Nonelon =>
              numPopularUselonrsWithNoKnownForBelonforelon.inc()
              List.elonmpty
          }
          (mappelondUselonrId, ngbrIds, ngbrWts, clustelonrsList)
      }
  }

  /**
   * Initializelon graph with uselonrs and nelonighbors with elondgelon welonights  .
   */
  delonf gelontGraphFromSimsInput(
    mappelondSimsItelonr: Itelonrablelon[
      (Int, Array[Int], Array[Float], List[(ClustelonrId, Float)])
    ],
    numUselonrs: Int
  ): Graph = {
    val nbrsIds: Array[Array[Int]] = nelonw Array[Array[Int]](numUselonrs)
    val nbrsWts: Array[Array[Float]] = nelonw Array[Array[Float]](numUselonrs)
    var numelondgelons = 0L
    var numVelonrticelons = 0
    var numVelonrticelonsWithNoNgbrs = 0
    mappelondSimsItelonr.forelonach {
      caselon (id, nbrArrayIds, nbArrayScorelons, _) =>
        nbrsIds(id) = nbrArrayIds
        nbrsWts(id) = nbArrayScorelons
        numelondgelons += nbrArrayIds.lelonngth
        numVelonrticelons += 1
        if (numVelonrticelons % 100000 == 0) {
          println(s"Donelon loading $numVelonrticelons many velonrticelons. elondgelons so far: $numelondgelons")
        }
    }

    (0 until numUselonrs).forelonach { i =>
      if (nbrsIds(i) == null) {
        numVelonrticelonsWithNoNgbrs += 1
        nbrsIds(i) = Array[Int]()
        nbrsWts(i) = Array[Float]()
      }
    }

    println(
      s"Donelon loading graph with $numUselonrs nodelons and $numelondgelons elondgelons (counting elonach elondgelon twicelon)")
    println("Numbelonr of nodelons with at lelonast onelon nelonighbor is " + numVelonrticelons)
    println("Numbelonr of nodelons with at no nelonighbors is " + numVelonrticelonsWithNoNgbrs)
    nelonw Graph(numUselonrs, numelondgelons / 2, nbrsIds, nbrsWts)
  }

  /**
   * Helonlpelonr function that initializelons uselonrs to clustelonrs baselond on prelonvious knownFor assignmelonnts
   * and for uselonrs with no prelonvious assignmelonnts, assign thelonm randomly to any of thelon elonmpty clustelonrs
   */
  delonf initializelonSparselonBinaryMatrix(
    graph: Graph,
    mappelondSimsGraphItelonr: Itelonrablelon[
      (Int, Array[Int], Array[Float], List[(ClustelonrId, Float)])
    ], // uselonr with nelonighbors, nelonighbor wts and prelonvious knownfor assignmelonnts
    numUselonrs: Int,
    numClustelonrs: Int,
    algoConfig: AlgorithmConfig,
  ): SparselonBinaryMatrix = {
    var clustelonrsSelonelonnFromPrelonviousWelonelonk: Selont[Int] = Selont.elonmpty
    var elonmptyClustelonrsFromPrelonviousWelonelonk: Selont[Int] = Selont.elonmpty
    var uselonrsWithNoAssignmelonntsFromPrelonviousWelonelonk: Selont[Int] = Selont.elonmpty
    mappelondSimsGraphItelonr.forelonach {
      caselon (id, _, _, knownFor) =>
        if (knownFor.iselonmpty) {
          uselonrsWithNoAssignmelonntsFromPrelonviousWelonelonk += id
        }
        knownFor.forelonach {
          caselon (clustelonrId, _) =>
            clustelonrsSelonelonnFromPrelonviousWelonelonk += clustelonrId
        }
    }
    (1 to numClustelonrs).forelonach { i =>
      if (!clustelonrsSelonelonnFromPrelonviousWelonelonk.contains(i)) elonmptyClustelonrsFromPrelonviousWelonelonk += i
    }
    var z = nelonw SparselonBinaryMatrix(numUselonrs, numClustelonrs)
    println("Going to initializelon from prelonvious KnownFor")
    var zelonroIndelonxelondClustelonrIdsFromPrelonviousWelonelonk: Selont[Int] = Selont.elonmpty
    for (clustelonrIdOnelonIndelonxelond <- elonmptyClustelonrsFromPrelonviousWelonelonk) {
      zelonroIndelonxelondClustelonrIdsFromPrelonviousWelonelonk += (clustelonrIdOnelonIndelonxelond - 1)
    }
    // Initializelon z - uselonrs with no prelonvious assignmelonnts arelon assignelond to elonmpty clustelonrs
    z.initFromSubselontOfRowsForSpeloncifielondColumns(
      graph,
      (gr: Graph, i: Intelongelonr) => algoConfig.rng.nelonxtDoublelon,
      zelonroIndelonxelondClustelonrIdsFromPrelonviousWelonelonk.toArray,
      uselonrsWithNoAssignmelonntsFromPrelonviousWelonelonk.toArray,
      nelonw PrintWritelonr(Systelonm.elonrr)
    )
    println("Initializelond thelon elonmpty clustelonrs")
    mappelondSimsGraphItelonr.forelonach {
      caselon (id, _, _, knownFor) =>
        val currClustelonrsForUselonrZelonroIndelonxelond = knownFor.map(_._1).map(x => x - 1)
        // Uselonrs who havelon a prelonvious clustelonr assignmelonnt arelon initializelond with thelon samelon clustelonr
        if (currClustelonrsForUselonrZelonroIndelonxelond.nonelonmpty) {
          z.updatelonRow(id, currClustelonrsForUselonrZelonroIndelonxelond.sortelond.toArray)
        }
    }
    println("Donelon initializing from prelonvious knownFor assignmelonnt")
    z
  }

  /**
   * Optimizelon thelon sparselonBinaryMatrix. This function runs thelon clustelonring elonpochs and computelons thelon
   * clustelonr assignmelonnts for thelon nelonxt welonelonk, baselond on thelon undelonrlying uselonr-uselonr graph
   */
  delonf optimizelonSparselonBinaryMatrix(
    algoConfig: AlgorithmConfig,
    graph: Graph,
    z: SparselonBinaryMatrix
  ): SparselonBinaryMatrix = {
    val prelonc0 = MHAlgorithm.clustelonrPreloncision(graph, z, 0, 1000, algoConfig.rng)
    println("Preloncision of clustelonr 0:" + prelonc0.preloncision)
    val prelonc1 = MHAlgorithm.clustelonrPreloncision(graph, z, 1, 1000, algoConfig.rng)
    println("Preloncision of clustelonr 1:" + prelonc1.preloncision)
    val algo = nelonw MHAlgorithm(algoConfig, graph, z, nelonw PrintWritelonr(Systelonm.elonrr))
    val optimizelondZ = algo.optimizelon
    optimizelondZ
  }

  /**
   * Helonlpelonr function that takelons thelon helonuristically scorelond association of uselonr to a clustelonr
   * and relonturns thelon knownFor relonsult
   * @param srm SparselonRelonalMatrix with (row, col) scorelon delonnoting thelon melonmbelonrship scorelon of uselonr in thelon clustelonr
   * @relonturn assignmelonnts of uselonrs (mappelond intelongelonr indicelons) to clustelonrs with knownFor scorelons.
   */
  delonf gelontKnownForHelonuristicScorelons(srm: SparselonRelonalMatrix): List[(Int, List[(ClustelonrId, Float)])] = {
    val knownForAssignmelonntsFromClustelonrScorelons = (0 until srm.gelontNumRows).map { rowId =>
      val rowWithIndicelons = srm.gelontColIdsForRow(rowId)
      val rowWithScorelons = srm.gelontValuelonsForRow(rowId)
      val allClustelonrsWithScorelons: Array[(ClustelonrId, Float)] =
        rowWithIndicelons.zip(rowWithScorelons).map {
          caselon (colId, scorelon) => (colId + 1, scorelon.toFloat)
        }
      if (rowId % 100000 == 0) {
        println("Insidelon outputItelonr:" + rowId + " " + srm.gelontNumRows)
      }

      val clustelonrAssignmelonntWithMaxScorelon: List[(ClustelonrId, Float)] =
        if (allClustelonrsWithScorelons.lelonngth > 1) {
          // if sparselonBinaryMatrix z has rows with morelon than onelon non-zelonro column (i.elon a uselonr
          // initializelond with morelon than onelon clustelonr), and thelon clustelonring algorithm doelonsnot find
          // a belonttelonr proposal for clustelonr assignmelonnt, thelon uselonr's multi-clustelonr melonmbelonrship
          // from thelon initialization stelonp can continuelon.
          // Welon found that this happelonns in ~0.1% of thelon knownFor uselonrs. Helonncelon chooselon thelon
          // clustelonr with thelon highelonst scorelon to delonal with such elondgelon caselons.
          val relonsult: (ClustelonrId, Float) = allClustelonrsWithScorelons.maxBy(_._2)
          println(
            "Found a uselonr with mappelondId: %s with morelon than 1 clustelonr assignmelonnt:%s; Assignelond to thelon belonst clustelonr: %s"
              .format(
                rowId.toString,
                allClustelonrsWithScorelons.mkString("Array(", ", ", ")"),
                relonsult
                  .toString()))
          List(relonsult)
        } elonlselon {
          allClustelonrsWithScorelons.toList
        }
      (rowId, clustelonrAssignmelonntWithMaxScorelon)
    }
    knownForAssignmelonntsFromClustelonrScorelons.toList
  }

  /**
   * Function that computelons thelon clustelonring assignmelonnts to uselonrs
   *
   * @param mappelondSimsGraph uselonr-uselonr graph as input to clustelonring
   * @param prelonviousKnownForAssignmelonnts prelonvious welonelonk clustelonring assignmelonnts
   * @param maxelonpochsForClustelonring numbelonr of nelonighbors for elonach uselonr
   * @param squarelonWelonights boolelonan flag for thelon elondgelon welonights in thelon sims graph
   * @param wtCoelonff wtCoelonff
   *
   * @relonturn uselonrs with clustelonrs assignelond
   */
  delonf gelontClustelonringAssignmelonnts(
    mappelondSimsGraph: TypelondPipelon[(Int, List[(Int, Float)])],
    prelonviousKnownForAssignmelonnts: TypelondPipelon[(Int, List[(ClustelonrId, Float)])],
    maxelonpochsForClustelonring: Int,
    squarelonWelonights: Boolelonan,
    wtCoelonff: Doublelon
  )(
    implicit uniquelonId: UniquelonID
  ): elonxeloncution[List[(Int, List[(ClustelonrId, Float)])]] = {

    attachClustelonrAssignmelonnts(
      mappelondSimsGraph,
      prelonviousKnownForAssignmelonnts,
      squarelonWelonights).toItelonrablelonelonxeloncution.flatMap { mappelondSimsGraphWithClustelonrsItelonr =>
      val tic = Systelonm.currelonntTimelonMillis
      var maxVelonrtelonxId = 0
      var maxClustelonrIdInPrelonviousAssignmelonnt = 0
      mappelondSimsGraphWithClustelonrsItelonr.forelonach {
        caselon (id, _, _, knownFor) =>
          maxVelonrtelonxId = Math.max(id, maxVelonrtelonxId)
          knownFor.forelonach {
            caselon (clustelonrId, _) =>
              maxClustelonrIdInPrelonviousAssignmelonnt =
                Math.max(clustelonrId, maxClustelonrIdInPrelonviousAssignmelonnt)
          }
      }

      val numUselonrsToClustelonr =
        maxVelonrtelonxId + 1 //sincelon uselonrs welonrelon mappelond with indelonx starting from 0, using zipWithIndelonx
      println("Total numbelonr of topK uselonrs to belon clustelonrelond this timelon:" + numUselonrsToClustelonr)
      println(
        "Total numbelonr of clustelonrs in thelon prelonvious knownFor assignmelonnt:" + maxClustelonrIdInPrelonviousAssignmelonnt)
      println("Will selont numbelonr of communitielons to " + maxClustelonrIdInPrelonviousAssignmelonnt)

      // Initializelon thelon graph with uselonrs, nelonighbors and thelon correlonsponding elondgelon welonights
      val graph = gelontGraphFromSimsInput(mappelondSimsGraphWithClustelonrsItelonr, numUselonrsToClustelonr)
      val toc = Systelonm.currelonntTimelonMillis()
      println("Timelon to load thelon graph " + (toc - tic) / 1000.0 / 60.0 + " minutelons")

      // delonfinelon thelon algoConfig paramelontelonrs
      val algoConfig = nelonw AlgorithmConfig()
        .withCpu(16).withK(maxClustelonrIdInPrelonviousAssignmelonnt)
        .withWtCoelonff(wtCoelonff.toDoublelon)
        .withMaxelonpoch(maxelonpochsForClustelonring)
      algoConfig.dividelonRelonsultIntoConnelonctelondComponelonnts = falselon
      algoConfig.minClustelonrSizelon = 1
      algoConfig.updatelonImmelondiatelonly = truelon
      algoConfig.rng = nelonw RandomAdaptor(nelonw JDKRandomGelonnelonrator(1))

      // Initializelon a sparselonBinaryMatrix with uselonrs assignelond to thelonir prelonvious welonelonk knownFor
      // assignmelonnts. For thoselon uselonrs who do not a prior assignmelonnt, welon assign
      // thelon (uselonr + thelon nelonighbors from thelon graph) to thelon elonmpty clustelonrs.
      // Plelonaselon notelon that this nelonighborhood-baselond initialization to elonmpty clustelonrs can
      // havelon a felonw caselons whelonrelon thelon samelon uselonr was assignelond to morelon than onelon clustelonr
      val z = initializelonSparselonBinaryMatrix(
        graph,
        mappelondSimsGraphWithClustelonrsItelonr,
        numUselonrsToClustelonr,
        maxClustelonrIdInPrelonviousAssignmelonnt,
        algoConfig
      )

      // Run thelon elonpochs of thelon clustelonring algorithm to find thelon nelonw clustelonr assignmelonnts
      val tic2 = Systelonm.currelonntTimelonMillis
      val optimizelondZ = optimizelonSparselonBinaryMatrix(algoConfig, graph, z)
      val toc2 = Systelonm.currelonntTimelonMillis
      println("Timelon to optimizelon: %.2f selonconds\n".format((toc2 - tic2) / 1000.0))
      println("Timelon to initializelon & optimizelon: %.2f selonconds\n".format((toc2 - toc) / 1000.0))

      // Attach scorelons to thelon clustelonr assignmelonnts
      val srm = MHAlgorithm.helonuristicallyScorelonClustelonrAssignmelonnts(graph, optimizelondZ)

      // Gelont thelon knownfor assignmelonnts of uselonrs from thelon helonuristic scorelons
      // assignelond baselond on nelonigbhorhood of thelon uselonr and thelonir clustelonr assignmelonnts
      // Thelon relonturnelond relonsult has uselonrIDs in thelon mappelond intelongelonr indicelons
      val knownForAssignmelonntsFromClustelonrScorelons: List[(Int, List[(ClustelonrId, Float)])] =
        gelontKnownForHelonuristicScorelons(srm)

      elonxeloncution.from(knownForAssignmelonntsFromClustelonrScorelons)
    }
  }
}
