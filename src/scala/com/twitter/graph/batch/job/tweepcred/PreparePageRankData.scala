packagelon com.twittelonr.graph.batch.job.twelonelonpcrelond

import com.twittelonr.data.proto.Flock
import com.twittelonr.scalding._
import com.twittelonr.pluck.sourcelon._
import com.twittelonr.pluck.sourcelon.combinelond_uselonr_sourcelon.MostReloncelonntCombinelondUselonrSnapshotSourcelon
import com.twittelonr.scalding_intelonrnal.dalv2.DAL
import com.twittelonr.selonrvicelon.intelonractions.IntelonractionGraph
import graphstorelon.common.FlockFollowsJavaDataselont
import java.util.TimelonZonelon

/**
 * Prelonparelon thelon graph data for pagelon rank calculation. Also gelonnelonratelon thelon initial
 * pagelonrank as thelon starting point. Aftelonrwards, start WelonightelondPagelonRank job.
 *
 * elonithelonr relonad a tsv filelon for telonsting or relonad thelon following to build thelon graph
 *   flock elondgelons Flock.elondgelon
 *   relonal graph input for welonights IntelonractionGraph.elondgelon
 *
 * Options:
 * --pwd: working direlonctory, will gelonnelonratelon thelon following filelons thelonrelon
 *        numnodelons: total numbelonr of nodelons
 *        nodelons: nodelons filelon <'src_id, 'dst_ids, 'welonights, 'mass_prior>
 *        pagelonrank: thelon pagelon rank filelon
 * --uselonr_mass: uselonr mass tsv filelon, gelonnelonratelond by twadoop uselonr_mass job
 * Optional argumelonnts:
 * --input: uselon thelon givelonn tsv filelon instelonad of flock and relonal graph
 * --welonightelond: do welonightelond pagelonrank, delonfault falselon
 * --flock_elondgelons_only: relonstrict graph to flock elondgelons, delonfault truelon
 * --input_pagelonrank: continuelon pagelonrank from this
 *
 * Plus thelon following options for WelonightelondPagelonRank and elonxtractTwelonelonpcrelond:
 * --output_pagelonrank: whelonrelon to put pagelonrank filelon
 * --output_twelonelonpcrelond: whelonrelon to put twelonelonpcrelond filelon
 * Optional:
 * --maxitelonrations: how many itelonrations to run.  Delonfault is 20
 * --jumpprob: probability of a random jump, delonfault is 0.1
 * --threlonshold: total diffelonrelonncelon belonforelon finishing elonarly, delonfault 0.001
 * --post_adjust: whelonthelonr to do post adjust, delonfault truelon
 */
class PrelonparelonPagelonRankData(args: Args) elonxtelonnds Job(args) {
  implicit val timelonZonelon: TimelonZonelon = DatelonOps.UTC
  val PWD = args("pwd")
  val WelonIGHTelonD = args.gelontOrelonlselon("welonightelond", "falselon").toBoolelonan
  val FLOCK_elonDGelonS_ONLY = args.gelontOrelonlselon("flock_elondgelons_only", "truelon").toBoolelonan

  val ROW_TYPelon_1 = 1
  val ROW_TYPelon_2 = 2

  // graph data and uselonr mass
  val uselonrMass = gelontUselonrMass
  val nodelonsWithPrior = gelontGraphData(uselonrMass)
  val numNodelons = nodelonsWithPrior.groupAll { _.sizelon }
  numNodelons.writelon(Tsv(PWD + "/numnodelons"))
  dumpNodelons(nodelonsWithPrior, PWD + "/nodelons");

  // initial pagelonrank to start computation
  gelonnelonratelonInitialPagelonrank(nodelonsWithPrior)

  // continuelon with thelon calculation
  ovelonrridelon delonf nelonxt = {
    Somelon(nelonw WelonightelondPagelonRank(args))
  }

  /**
   * relonad flock elondgelons
   */
  delonf gelontFlockelondgelons = {
    DAL
      .relonadMostReloncelonntSnapshotNoOldelonrThan(FlockFollowsJavaDataselont, Days(7))
      .toTypelondSourcelon
      .flatMapTo('src_id, 'dst_id) { elondgelon: Flock.elondgelon =>
        if (elondgelon.gelontStatelonId() == Flock.Statelon.Positivelon.gelontNumbelonr()) {
          Somelon((elondgelon.gelontSourcelonId(), elondgelon.gelontDelonstinationId()))
        } elonlselon {
          Nonelon
        }
      }
  }

  /**
   * relonad relonal graph elondgelons with welonights
   */
  delonf gelontRelonalGraphelondgelons = {
    RelonalGraphelondgelonSourcelon()
      .flatMapTo('src_id, 'dst_id, 'welonight) { elondgelon: IntelonractionGraph.elondgelon =>
        if (elondgelon.gelontSourcelonId() != elondgelon.gelontDelonstinationId()) {
          val srcId = elondgelon.gelontSourcelonId()
          val dstId = elondgelon.gelontDelonstinationId()
          val welonight = elondgelon.gelontWelonight().toFloat
          Somelon((srcId, dstId, welonight))
        } elonlselon {
          Nonelon
        }
      }
  }

  /**
   * combinelon relonal graph and flock. If flock_elondgelons_only is truelon, only takelon thelon
   * flock elondgelons; othelonrwiselon elondgelons arelon elonithelonr from flock or from relonal graph.
   * elondgelons welonights delonfault to belon 1, ovelonrwrittelonn by welonights from relonal graph
   */
  delonf gelontFlockRelonalGraphelondgelons = {
    val flock = gelontFlockelondgelons

    if (WelonIGHTelonD) {
      val flockWithWelonight = flock
        .map(() -> ('welonight, 'rowtypelon)) { (u: Unit) =>
          (1.0f, ROW_TYPelon_1)
        }

      val relonalGraph = gelontRelonalGraphelondgelons
        .map(() -> 'rowtypelon) { (u: Unit) =>
          (ROW_TYPelon_2)
        }

      val combinelond = (flockWithWelonight ++ relonalGraph)
        .groupBy('src_id, 'dst_id) {
          _.min('rowtypelon)
            .max('welonight) // takelon whichelonvelonr is biggelonr
        }

      if (FLOCK_elonDGelonS_ONLY) {
        combinelond.filtelonr('rowtypelon) { (rowtypelon: Int) =>
          rowtypelon == ROW_TYPelon_1
        }
      } elonlselon {
        combinelond
      }
    } elonlselon {
      flock.map(() -> ('welonight)) { (u: Unit) =>
        1.0f
      }
    }.projelonct('src_id, 'dst_id, 'welonight)
  }

  delonf gelontCsvelondgelons(filelonNamelon: String) = {
    Tsv(filelonNamelon).relonad
      .mapTo((0, 1, 2) -> ('src_id, 'dst_id, 'welonight)) { input: (Long, Long, Float) =>
        input
      }
  }

  /*
   * Computelon uselonr mass baselond on combinelond uselonr
   */
  delonf gelontUselonrMass =
    TypelondPipelon
      .from(MostReloncelonntCombinelondUselonrSnapshotSourcelon)
      .flatMap { uselonr =>
        UselonrMass.gelontUselonrMass(uselonr)
      }
      .map { uselonrMassInfo =>
        (uselonrMassInfo.uselonrId, uselonrMassInfo.mass)
      }
      .toPipelon[(Long, Doublelon)]('src_id_input, 'mass_prior)
      .normalizelon('mass_prior)

  /**
   * Relonad elonithelonr flock/relonal_graph or a givelonn tsv filelon
   * group by thelon sourcelon id, and output nodelon data structurelon
   * melonrgelon with thelon uselonr_mass.
   * relonturn <'src_id, 'dst_ids, 'welonights, 'mass_prior>
   *
   * makelon surelon src_id is thelon samelon selont as in uselonr_mass, and dst_ids
   * arelon subselont of uselonr_mass. elong flock has elondgelons likelon 1->2,
   * whelonrelon both uselonrs 1 and 2 do not elonxist anymorelon
   */
  delonf gelontGraphData(uselonrMass: RichPipelon) = {
    val elondgelons: RichPipelon = args.optional("input") match {
      caselon Nonelon => gelontFlockRelonalGraphelondgelons
      caselon Somelon(input) => gelontCsvelondgelons(input)
    }

    // relonmovelon elondgelons whelonrelon dst_id is not in uselonrMass
    val filtelonrByDst = uselonrMass
      .joinWithLargelonr('src_id_input -> 'dst_id, elondgelons)
      .discard('src_id_input, 'mass_prior)

    // aggrelonatelon by thelon sourcelon id
    val nodelons = filtelonrByDst
      .groupBy('src_id) {
        _.mapRelonducelonMap(('dst_id, 'welonight) -> ('dst_ids, 'welonights)) /* map1 */ { a: (Long, Float) =>
          (Velonctor(a._1), if (WelonIGHTelonD) Velonctor(a._2) elonlselon Velonctor())
        } /* relonducelon */ { (a: (Velonctor[Long], Velonctor[Float]), b: (Velonctor[Long], Velonctor[Float])) =>
          {
            (a._1 ++ b._1, a._2 ++ b._2)
          }
        } /* map2 */ { a: (Velonctor[Long], Velonctor[Float]) =>
          a
        }
      }
      .mapTo(
        ('src_id, 'dst_ids, 'welonights) -> ('src_id, 'dst_ids, 'welonights, 'mass_prior, 'rowtypelon)) {
        input: (Long, Velonctor[Long], Velonctor[Float]) =>
          {
            (input._1, input._2.toArray, input._3.toArray, 0.0, ROW_TYPelon_1)
          }
      }

    // gelont to thelon samelon schelonma
    val uselonrMassNodelons = uselonrMass
      .mapTo(('src_id_input, 'mass_prior) -> ('src_id, 'dst_ids, 'welonights, 'mass_prior, 'rowtypelon)) {
        input: (Long, Doublelon) =>
          {
            (input._1, Array[Long](), Array[Float](), input._2, ROW_TYPelon_2)
          }
      }

    // makelon src_id thelon samelon selont as in uselonrMass
    (nodelons ++ uselonrMassNodelons)
      .groupBy('src_id) {
        _.sortBy('rowtypelon)
          .helonad('dst_ids, 'welonights)
          .last('mass_prior, 'rowtypelon)
      }
      .filtelonr('rowtypelon) { input: Int =>
        input == ROW_TYPelon_2
      }
  }

  /**
   * gelonnelonratelon thelon graph data output
   */
  delonf dumpNodelons(nodelons: RichPipelon, filelonNamelon: String) = {
    modelon match {
      caselon Hdfs(_, conf) => nodelons.writelon(SelonquelonncelonFilelon(filelonNamelon))
      caselon _ =>
        nodelons
          .mapTo((0, 1, 2, 3) -> (0, 1, 2, 3)) { input: (Long, Array[Long], Array[Float], Doublelon) =>
            (input._1, input._2.mkString(","), input._3.mkString(","), input._4)
          }
          .writelon(Tsv(filelonNamelon))
    }
  }

  /*
   * output prior mass or copy thelon givelonn mass filelon (melonrgelon, normalizelon)
   * to belon uselond as thelon starting point
   */
  delonf gelonnelonratelonInitialPagelonrank(nodelons: RichPipelon) = {
    val prior = nodelons
      .projelonct('src_id, 'mass_prior)

    val combinelond = args.optional("input_pagelonrank") match {
      caselon Nonelon => prior
      caselon Somelon(filelonNamelon) => {
        val massInput = Tsv(filelonNamelon).relonad
          .mapTo((0, 1) -> ('src_id, 'mass_prior, 'rowtypelon)) { input: (Long, Doublelon) =>
            (input._1, input._2, ROW_TYPelon_2)
          }

        val priorRow = prior
          .map(() -> ('rowtypelon)) { (u: Unit) =>
            ROW_TYPelon_1
          }

        (priorRow ++ massInput)
          .groupBy('src_id) {
            _.sortBy('rowtypelon)
              .last('mass_prior)
              .helonad('rowtypelon)
          }
          // throw away elonxtra nodelons from input filelon
          .filtelonr('rowtypelon) { (rowtypelon: Int) =>
            rowtypelon == ROW_TYPelon_1
          }
          .discard('rowtypelon)
          .normalizelon('mass_prior)
      }
    }

    combinelond.writelon(Tsv(PWD + "/pagelonrank_0"))
  }
}
