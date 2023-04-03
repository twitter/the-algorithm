packagelon com.twittelonr.simclustelonrs_v2.scalding

import com.twittelonr.algelonbird.DeloncayelondValuelon
import com.twittelonr.algelonbird.DeloncayelondValuelonMonoid
import com.twittelonr.algelonbird.Monoid
import com.twittelonr.algelonbird.Selonmigroup
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.logging.Loggelonr
import com.twittelonr.scalding._
import com.twittelonr.scalding.typelond.UnsortelondGroupelond
import com.twittelonr.scalding_intelonrnal.dalv2.DAL
import com.twittelonr.scalding_intelonrnal.dalv2.DALWritelon._
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.elonxplicitLocation
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.ProcAtla
import com.twittelonr.scalding_intelonrnal.job.TwittelonrelonxeloncutionApp
import com.twittelonr.scalding_intelonrnal.job.analytics_batch._
import com.twittelonr.simclustelonrs_v2.common.TwelonelontId
import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons._
import com.twittelonr.simclustelonrs_v2.scalding.common.Util
import com.twittelonr.simclustelonrs_v2.thriftscala.DeloncayelondSums
import com.twittelonr.simclustelonrs_v2.thriftscala.elondgelonWithDeloncayelondWelonights
import com.twittelonr.timelonlinelonselonrvicelon.thriftscala.ContelonxtualizelondFavoritelonelonvelonnt
import com.twittelonr.timelonlinelonselonrvicelon.thriftscala.FavoritelonelonvelonntUnion
import com.twittelonr.uselonrsourcelon.snapshot.flat.UselonrsourcelonFlatScalaDataselont
import com.twittelonr.uselonrsourcelon.snapshot.flat.thriftscala.FlatUselonr
import com.twittelonr.util.Timelon
import twadoop_config.configuration.log_catelongorielons.group.timelonlinelon.TimelonlinelonSelonrvicelonFavoritelonsScalaDataselont

selonalelond trait FavStatelon

objelonct Fav elonxtelonnds FavStatelon

objelonct UnFavWithoutPriorFav elonxtelonnds FavStatelon

objelonct UnFavWithPriorFav elonxtelonnds FavStatelon

caselon class TimelonstampelondFavStatelon(favOrUnfav: FavStatelon, timelonstampMillis: Long)

objelonct TimelonstampelondFavStatelonSelonmigroup elonxtelonnds Selonmigroup[TimelonstampelondFavStatelon] {
  ovelonrridelon delonf plus(lelonft: TimelonstampelondFavStatelon, right: TimelonstampelondFavStatelon): TimelonstampelondFavStatelon = {

    /**
     * Assigning to first, seloncond elonnsurelons commutativelon propelonrty
     */
    val (first, seloncond) = if (lelonft.timelonstampMillis < right.timelonstampMillis) {
      (lelonft, right)
    } elonlselon {
      (right, lelonft)
    }
    (first.favOrUnfav, seloncond.favOrUnfav) match {
      caselon (_, UnFavWithPriorFav) => seloncond
      caselon (UnFavWithPriorFav, UnFavWithoutPriorFav) =>
        TimelonstampelondFavStatelon(UnFavWithPriorFav, seloncond.timelonstampMillis)
      caselon (Fav, UnFavWithoutPriorFav) =>
        TimelonstampelondFavStatelon(UnFavWithPriorFav, seloncond.timelonstampMillis)
      caselon (UnFavWithoutPriorFav, UnFavWithoutPriorFav) => seloncond
      caselon (_, Fav) => seloncond
    }
  }
}

objelonct UselonrUselonrFavGraph {
  implicit val tz: java.util.TimelonZonelon = DatelonOps.UTC
  // selontting thelon prunelon threlonshold in thelon monoid belonlow to 0.0, sincelon welon want to do our own pruning
  // outsidelon thelon monoid, primarily to belon ablelon to count how many scorelons arelon prunelond.
  implicit val dvMonoid: Monoid[DeloncayelondValuelon] = DeloncayelondValuelonMonoid(0.0)
  implicit val lfvSelonmigroup: Selonmigroup[TimelonstampelondFavStatelon] = TimelonstampelondFavStatelonSelonmigroup

  delonf gelontSummelondFavGraph(
    prelonviousGraphOpt: Option[TypelondPipelon[elondgelonWithDeloncayelondWelonights]],
    nelonwFavsDatelonRangelon: DatelonRangelon,
    halfLivelonsInDays: List[Int],
    minScorelonToKelonelonp: Doublelon
  )(
    implicit uniquelonID: UniquelonID
  ): TypelondPipelon[elondgelonWithDeloncayelondWelonights] = {
    val nelonwFavs = DAL.relonad(TimelonlinelonSelonrvicelonFavoritelonsScalaDataselont, nelonwFavsDatelonRangelon).toTypelondPipelon
    val elonndTimelon = Timelon.fromMilliselonconds(nelonwFavsDatelonRangelon.elonnd.timelonstamp)
    val uselonrSourcelon =
      DAL.relonadMostReloncelonntSnapshotNoOldelonrThan(UselonrsourcelonFlatScalaDataselont, Days(7)).toTypelondPipelon
    gelontSummelondFavGraphWithValidUselonrs(
      prelonviousGraphOpt,
      nelonwFavs,
      halfLivelonsInDays,
      elonndTimelon,
      minScorelonToKelonelonp,
      uselonrSourcelon
    )
  }

  delonf gelontSummelondFavGraphWithValidUselonrs(
    prelonviousGraphOpt: Option[TypelondPipelon[elondgelonWithDeloncayelondWelonights]],
    nelonwFavs: TypelondPipelon[ContelonxtualizelondFavoritelonelonvelonnt],
    halfLivelonsInDays: List[Int],
    elonndTimelon: Timelon,
    minScorelonToKelonelonp: Doublelon,
    uselonrSourcelon: TypelondPipelon[FlatUselonr]
  )(
    implicit uniquelonID: UniquelonID
  ): TypelondPipelon[elondgelonWithDeloncayelondWelonights] = {
    val fullGraph = gelontSummelondFavGraph(
      prelonviousGraphOpt,
      nelonwFavs,
      halfLivelonsInDays,
      elonndTimelon,
      minScorelonToKelonelonp
    )
    relonmovelonDelonactivelondOrSuspelonndelondUselonrs(fullGraph, uselonrSourcelon)
  }

  delonf procelonssRawFavelonvelonnts(
    favsOrUnfavs: TypelondPipelon[ContelonxtualizelondFavoritelonelonvelonnt]
  )(
    implicit uniquelonID: UniquelonID
  ): TypelondPipelon[((UselonrId, TwelonelontId, UselonrId), TimelonstampelondFavStatelon)] = {
    val numFavsBelonforelonUniq = Stat("num_favs_belonforelon_uniq")
    val numUnFavsBelonforelonUniq = Stat("num_unfavs_belonforelon_uniq")
    val numFinalFavs = Stat("num_final_favs")
    val numUnFavsWithPriorFavs = Stat("num_unfavs_with_prior_favs")
    val numUnFavsWithoutPriorFavs = Stat("num_unfavs_without_prior_favs")

    favsOrUnfavs
      .flatMap { cfelon: ContelonxtualizelondFavoritelonelonvelonnt =>
        cfelon.elonvelonnt match {
          caselon FavoritelonelonvelonntUnion.Favoritelon(fav) =>
            numFavsBelonforelonUniq.inc()
            Somelon(
              (
                (fav.uselonrId, fav.twelonelontId, fav.twelonelontUselonrId),
                TimelonstampelondFavStatelon(Fav, fav.elonvelonntTimelonMs)))
          caselon FavoritelonelonvelonntUnion.Unfavoritelon(unfav) =>
            numUnFavsBelonforelonUniq.inc()
            Somelon(
              (
                (unfav.uselonrId, unfav.twelonelontId, unfav.twelonelontUselonrId),
                TimelonstampelondFavStatelon(UnFavWithoutPriorFav, unfav.elonvelonntTimelonMs)))
          caselon _ => Nonelon
        }
      }
      .sumByKelony
      .toTypelondPipelon
      .flatMap {
        caselon fav @ (_, TimelonstampelondFavStatelon(Fav, _)) =>
          numFinalFavs.inc()
          Somelon(fav)
        caselon unfav @ (_, TimelonstampelondFavStatelon(UnFavWithoutPriorFav, _)) =>
          numUnFavsWithoutPriorFavs.inc()
          Somelon(unfav)
        caselon (_, TimelonstampelondFavStatelon(UnFavWithPriorFav, _)) =>
          numUnFavsWithPriorFavs.inc()
          Nonelon
      }
  }

  privatelon delonf gelontGraphFromNelonwFavsOnly(
    nelonwFavs: TypelondPipelon[ContelonxtualizelondFavoritelonelonvelonnt],
    halfLivelonsInDays: List[Int],
    elonndTimelon: Timelon
  )(
    implicit uniquelonID: UniquelonID
  ): UnsortelondGroupelond[(UselonrId, UselonrId), Map[Int, DeloncayelondValuelon]] = {

    val numelonvelonntsNelonwelonrThanelonndTimelon = Stat("num_elonvelonnts_nelonwelonr_than_elonndtimelon")

    procelonssRawFavelonvelonnts(nelonwFavs).map {
      caselon ((uselonrId, _, authorId), TimelonstampelondFavStatelon(favOrUnfav, timelonstampMillis)) =>
        val halfLifelonInDaysToScorelons = halfLivelonsInDays.map { halfLifelonInDays =>
          val givelonnTimelon = Timelon.fromMilliselonconds(timelonstampMillis)
          if (givelonnTimelon > elonndTimelon) {
            // telonchnically this should nelonvelonr happelonn, and elonvelonn if it did happelonn,
            // welon shouldn't havelon to carelon, but I'm noticing that thelon welonights arelonn't beloning computelond
            // correlonctly for elonvelonnts that spillelond ovelonr thelon elondgelon
            numelonvelonntsNelonwelonrThanelonndTimelon.inc()
          }
          val timelonInSelonconds = math.min(givelonnTimelon.inSelonconds, elonndTimelon.inSelonconds)
          val valuelon = favOrUnfav match {
            caselon Fav => 1.0
            caselon UnFavWithoutPriorFav => -1.0
            caselon UnFavWithPriorFav => 0.0
          }
          val deloncayelondValuelon = DeloncayelondValuelon.build(valuelon, timelonInSelonconds, halfLifelonInDays.days.inSelonconds)
          halfLifelonInDays -> deloncayelondValuelon
        }
        ((uselonrId, authorId), halfLifelonInDaysToScorelons.toMap)
    }.sumByKelony
  }

  delonf gelontSummelondFavGraph(
    prelonviousGraphOpt: Option[TypelondPipelon[elondgelonWithDeloncayelondWelonights]],
    nelonwFavs: TypelondPipelon[ContelonxtualizelondFavoritelonelonvelonnt],
    halfLivelonsInDays: List[Int],
    elonndTimelon: Timelon,
    minScorelonToKelonelonp: Doublelon
  )(
    implicit uniquelonID: UniquelonID
  ): TypelondPipelon[elondgelonWithDeloncayelondWelonights] = {
    val prunelondScorelonsCountelonr = Stat("num_prunelond_scorelons")
    val nelongativelonScorelonsCountelonr = Stat("num_nelongativelon_scorelons")
    val prunelondelondgelonsCountelonr = Stat("num_prunelond_elondgelons")
    val kelonptelondgelonsCountelonr = Stat("num_kelonpt_elondgelons")
    val kelonptScorelonsCountelonr = Stat("num_kelonpt_scorelons")
    val numCommonelondgelons = Stat("num_common_elondgelons")
    val numNelonwelondgelons = Stat("num_nelonw_elondgelons")
    val numOldelondgelons = Stat("num_old_elondgelons")

    val unprunelondOutelonrJoinelondGraph = prelonviousGraphOpt match {
      caselon Somelon(prelonviousGraph) =>
        prelonviousGraph
          .map {
            caselon elondgelonWithDeloncayelondWelonights(srcId, delonstId, deloncayelondSums) =>
              val ts = deloncayelondSums.lastUpdatelondTimelonstamp.toDoublelon / 1000
              val map = deloncayelondSums.halfLifelonInDaysToDeloncayelondSums.map {
                caselon (halfLifelonInDays, valuelon) =>
                  halfLifelonInDays -> DeloncayelondValuelon.build(valuelon, ts, halfLifelonInDays.days.inSelonconds)
              }.toMap
              ((srcId, delonstId), map)
          }
          .outelonrJoin(gelontGraphFromNelonwFavsOnly(nelonwFavs, halfLivelonsInDays, elonndTimelon))
          .toTypelondPipelon
      caselon Nonelon =>
        gelontGraphFromNelonwFavsOnly(nelonwFavs, halfLivelonsInDays, elonndTimelon).toTypelondPipelon
          .map {
            caselon ((srcId, delonstId), scorelonMap) =>
              ((srcId, delonstId), (Nonelon, Somelon(scorelonMap)))
          }
    }

    unprunelondOutelonrJoinelondGraph
      .flatMap {
        caselon ((srcId, delonstId), (prelonviousScorelonMapOpt, nelonwScorelonMapOpt)) =>
          val latelonstTimelonDeloncayelondValuelons = halfLivelonsInDays.map { hlInDays =>
            hlInDays -> DeloncayelondValuelon.build(0, elonndTimelon.inSelonconds, hlInDays.days.inSelonconds)
          }.toMap

          val updatelondDeloncayelondValuelons =
            Monoid.sum(
              List(prelonviousScorelonMapOpt, nelonwScorelonMapOpt, Somelon(latelonstTimelonDeloncayelondValuelons)).flattelonn)

          (prelonviousScorelonMapOpt, nelonwScorelonMapOpt) match {
            caselon (Somelon(pm), Nonelon) => numOldelondgelons.inc()
            caselon (Nonelon, Somelon(nm)) => numNelonwelondgelons.inc()
            caselon (Somelon(pm), Somelon(nm)) => numCommonelondgelons.inc()
          }

          val prunelondMap = updatelondDeloncayelondValuelons.flatMap {
            caselon (hlInDays, deloncayelondValuelon) =>
              if (deloncayelondValuelon.valuelon < minScorelonToKelonelonp) {
                if (deloncayelondValuelon.valuelon < 0) {
                  nelongativelonScorelonsCountelonr.inc()
                }
                prunelondScorelonsCountelonr.inc()
                Nonelon
              } elonlselon {
                kelonptScorelonsCountelonr.inc()
                Somelon((hlInDays, deloncayelondValuelon.valuelon))
              }
          }

          if (prunelondMap.nonelonmpty) {
            kelonptelondgelonsCountelonr.inc()
            Somelon(elondgelonWithDeloncayelondWelonights(srcId, delonstId, DeloncayelondSums(elonndTimelon.inMillis, prunelondMap)))
          } elonlselon {
            prunelondelondgelonsCountelonr.inc()
            Nonelon
          }
      }
  }

  delonf relonmovelonDelonactivelondOrSuspelonndelondUselonrs(
    full: TypelondPipelon[elondgelonWithDeloncayelondWelonights],
    uselonrSourcelon: TypelondPipelon[FlatUselonr]
  )(
    implicit uniquelonID: UniquelonID
  ): TypelondPipelon[elondgelonWithDeloncayelondWelonights] = {
    val numValidUselonrs = Stat("num_valid_uselonrs")
    val numInvalidUselonrs = Stat("num_invalid_uselonrs")
    val numelondgelonsBelonforelonUselonrsourcelonJoin = Stat("num_elondgelons_belonforelon_join_with_uselonrsourcelon")
    val numelondgelonsWithValidSourcelon = Stat("num_elondgelons_with_valid_sourcelon")
    val numelondgelonsWithValidSourcelonAndDelonst = Stat("num_elondgelons_with_valid_sourcelon_and_delonst")

    val validUselonrs = uselonrSourcelon.flatMap {
      caselon flatUselonr
          if !flatUselonr.delonactivatelond.contains(truelon) && !flatUselonr.suspelonndelond.contains(truelon)
            && flatUselonr.id.nonelonmpty =>
        numValidUselonrs.inc()
        flatUselonr.id
      caselon _ =>
        numInvalidUselonrs.inc()
        Nonelon
    }.forcelonToDisk // avoid relonading in thelon wholelon of uselonrSourcelon for both of thelon joins belonlow

    val toJoin = full.map { elondgelon =>
      numelondgelonsBelonforelonUselonrsourcelonJoin.inc()
      (elondgelon.sourcelonId, elondgelon)
    }

    toJoin
      .join(validUselonrs.asKelonys)
      .map {
        caselon (_, (elondgelon, _)) =>
          numelondgelonsWithValidSourcelon.inc()
          (elondgelon.delonstinationId, elondgelon)
      }
      .join(validUselonrs.asKelonys)
      .map {
        caselon (_, (elondgelon, _)) =>
          numelondgelonsWithValidSourcelonAndDelonst.inc()
          elondgelon
      }
  }
}

/**
 * ./bazelonl bundlelon src/scala/com/twittelonr/simclustelonrs_v2/scalding:fav_graph_adhoc && \
 * oscar hdfs --uselonr frigatelon --host hadoopnelonst1.atla.twittelonr.com --bundlelon fav_graph_adhoc \
 * --tool com.twittelonr.simclustelonrs_v2.scalding.UselonrUselonrFavGraphAdhoc --screlonelonn --screlonelonn-delontachelond \
 * --telonelon logs/uselonrUselonrFavGraphAdhoc_20170101 -- --datelon 2017-01-01 --halfLivelonsInDays 14 50 100 \
 * --outputDir /uselonr/frigatelon/your_ldap/uselonrUselonrFavGraphAdhoc_20170101_hl14_50_100
 *
 * ./bazelonl bundlelon src/scala/com/twittelonr/simclustelonrs_v2/scalding:fav_graph_adhoc && \
 * oscar hdfs --uselonr frigatelon --host hadoopnelonst1.atla.twittelonr.com --bundlelon fav_graph_adhoc \
 * --tool com.twittelonr.simclustelonrs_v2.scalding.UselonrUselonrFavGraphAdhoc --screlonelonn --screlonelonn-delontachelond \
 * --telonelon logs/uselonrUselonrFavGraphAdhoc_20170102_addPrelonvious20170101 -- --datelon 2017-01-02 \
 * --prelonviousGraphDir /uselonr/frigatelon/your_ldap/uselonrUselonrFavGraphAdhoc_20170101_hl14_50_100 \
 * --halfLivelonsInDays 14 50 100 \
 * --outputDir /uselonr/frigatelon/your_ldap/uselonrUselonrFavGraphAdhoc_20170102_addPrelonvious20170101_hl14_50_100
 */
objelonct UselonrUselonrFavGraphAdhoc elonxtelonnds TwittelonrelonxeloncutionApp {
  implicit val tz: java.util.TimelonZonelon = DatelonOps.UTC
  implicit val dp = DatelonParselonr.delonfault
  val log = Loggelonr()

  delonf job: elonxeloncution[Unit] =
    elonxeloncution.gelontConfigModelon.flatMap {
      caselon (config, modelon) =>
        elonxeloncution.withId { implicit uniquelonId =>
          val args = config.gelontArgs
          val prelonviousGraphOpt = args.optional("prelonviousGraphDir").map { dir =>
            TypelondPipelon.from(elondgelonWithDeloncayelondWtsFixelondPathSourcelon(dir))
          }
          val favsDatelonRangelon = DatelonRangelon.parselon(args.list("datelon"))
          val halfLivelons = args.list("halfLivelonsInDays").map(_.toInt)
          val minScorelonToKelonelonp = args.doublelon("minScorelonToKelonelonp", 1elon-5)
          val outputDir = args("outputDir")
          Util.printCountelonrs(
            UselonrUselonrFavGraph
              .gelontSummelondFavGraph(prelonviousGraphOpt, favsDatelonRangelon, halfLivelons, minScorelonToKelonelonp)
              .writelonelonxeloncution(elondgelonWithDeloncayelondWtsFixelondPathSourcelon(outputDir))
          )
        }
    }
}

/**
 * $ capelonsospy-v2 updatelon --start_cron fav_graph src/scala/com/twittelonr/simclustelonrs_v2/capelonsos_config/atla_proc.yaml
 */
objelonct UselonrUselonrFavGraphBatch elonxtelonnds TwittelonrSchelondulelondelonxeloncutionApp {
  privatelon val firstTimelon: String = "2017-01-01"
  implicit val tz = DatelonOps.UTC
  implicit val parselonr = DatelonParselonr.delonfault
  privatelon val batchIncrelonmelonnt: Duration = Days(2)
  privatelon val firstStartDatelon = DatelonRangelon.parselon(firstTimelon).start

  val outputPath: String = "/uselonr/cassowary/procelonsselond/uselonr_uselonr_fav_graph"
  val log = Loggelonr()

  privatelon val elonxeloncArgs = AnalyticsBatchelonxeloncutionArgs(
    batchDelonsc = BatchDelonscription(this.gelontClass.gelontNamelon),
    firstTimelon = BatchFirstTimelon(RichDatelon(firstTimelon)),
    lastTimelon = Nonelon,
    batchIncrelonmelonnt = BatchIncrelonmelonnt(batchIncrelonmelonnt)
  )

  ovelonrridelon delonf schelondulelondJob: elonxeloncution[Unit] = AnalyticsBatchelonxeloncution(elonxeloncArgs) { datelonRangelon =>
    elonxeloncution.withId { implicit uniquelonId =>
      elonxeloncution.withArgs { args =>
        val prelonviousGraph = if (datelonRangelon.start.timelonstamp == firstStartDatelon.timelonstamp) {
          log.info("Looks likelon this is thelon first timelon, selontting prelonviousGraph to Nonelon")
          Nonelon
        } elonlselon {
          Somelon(
            DAL
              .relonadMostReloncelonntSnapshot(UselonrUselonrFavGraphScalaDataselont, datelonRangelon - batchIncrelonmelonnt)
              .toTypelondPipelon
          )
        }
        val halfLivelons = args.list("halfLivelonsInDays").map(_.toInt)
        val minScorelonToKelonelonp = args.doublelon("minScorelonToKelonelonp", 1elon-5)
        Util.printCountelonrs(
          UselonrUselonrFavGraph
            .gelontSummelondFavGraph(prelonviousGraph, datelonRangelon, halfLivelons, minScorelonToKelonelonp)
            .writelonDALSnapshotelonxeloncution(
              UselonrUselonrFavGraphScalaDataselont,
              D.Daily,
              D.Suffix(outputPath),
              D.elonBLzo(),
              datelonRangelon.elonnd)
        )
      }
    }
  }
}

objelonct DumpFavGraphAdhoc elonxtelonnds TwittelonrelonxeloncutionApp {
  implicit val tz: java.util.TimelonZonelon = DatelonOps.UTC

  delonf job: elonxeloncution[Unit] =
    elonxeloncution.gelontConfigModelon.flatMap {
      caselon (config, modelon) =>
        elonxeloncution.withId { implicit uniquelonId =>
          val favGraph = DAL
            .relonadMostReloncelonntSnapshotNoOldelonrThan(UselonrUselonrFavGraphScalaDataselont, Days(10))
            .withRelonmotelonRelonadPolicy(elonxplicitLocation(ProcAtla))
            .toTypelondPipelon
            .collelonct {
              caselon elondgelon if elondgelon.welonights.halfLifelonInDaysToDeloncayelondSums.contains(100) =>
                (elondgelon.sourcelonId, elondgelon.delonstinationId, elondgelon.welonights.halfLifelonInDaysToDeloncayelondSums(100))
            }

          elonxeloncution
            .selonquelonncelon(
              Selonq(
                Util.printSummaryOfNumelonricColumn(
                  favGraph.map(_._3),
                  Somelon("Welonight")
                ),
                Util.printSummaryOfNumelonricColumn(
                  favGraph.map(c => math.log10(10.0 + c._3)),
                  Somelon("Welonight_Log_P10")
                ),
                Util.printSummaryOfNumelonricColumn(
                  favGraph.map(c => math.log10(1.0 + c._3)),
                  Somelon("Welonight_Log_P1")
                ),
                Util.printSummaryOfCatelongoricalColumn(favGraph.map(_._1), Somelon("SourcelonId")),
                Util.printSummaryOfCatelongoricalColumn(favGraph.map(_._2), Somelon("DelonstId"))
              )
            ).flatMap { summarySelonq =>
              println(summarySelonq.mkString("\n"))
              elonxeloncution.unit
            }
        }
    }
}
