packagelon com.twittelonr.simclustelonrs_v2.summingbird.storm

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.helonron.util.CommonMelontric
import com.twittelonr.scalding.Args
import com.twittelonr.simclustelonrs_v2.summingbird.common.SimClustelonrsProfilelon
import com.twittelonr.simclustelonrs_v2.summingbird.common.SimClustelonrsProfilelon.AltSelontting
import com.twittelonr.simclustelonrs_v2.summingbird.common.SimClustelonrsProfilelon.elonnvironmelonnt
import com.twittelonr.simclustelonrs_v2.summingbird.storelons.elonntityClustelonrScorelonRelonadablelonStorelon
import com.twittelonr.simclustelonrs_v2.summingbird.storelons.TopKClustelonrsForTwelonelontRelonadablelonStorelon
import com.twittelonr.simclustelonrs_v2.summingbird.storelons.TopKTwelonelontsForClustelonrRelonadablelonStorelon
import com.twittelonr.simclustelonrs_v2.summingbird.storelons.UselonrIntelonrelonstelondInRelonadablelonStorelon
import com.twittelonr.simclustelonrs_v2.thriftscala._
import com.twittelonr.storagelon.clielonnt.manhattan.kv.ManhattanKVClielonntMtlsParams
import com.twittelonr.summingbird.onlinelon.option._
import com.twittelonr.summingbird.option._
import com.twittelonr.summingbird.storm.option.FlatMapStormMelontrics
import com.twittelonr.summingbird.storm.option.SummelonrStormMelontrics
import com.twittelonr.summingbird.storm.Storm
import com.twittelonr.summingbird.storm.StormMelontric
import com.twittelonr.summingbird.Options
import com.twittelonr.summingbird.TailProducelonr
import com.twittelonr.summingbird_intelonrnal.runnelonr.common.JobNamelon
import com.twittelonr.summingbird_intelonrnal.runnelonr.common.SBRunConfig
import com.twittelonr.summingbird_intelonrnal.runnelonr.storm.GelonnelonricRunnelonr
import com.twittelonr.summingbird_intelonrnal.runnelonr.storm.StormConfig
import com.twittelonr.tormelonnta_intelonrnal.spout.elonvelonntbus.SubscribelonrId
import com.twittelonr.wtf.summingbird.sourcelons.storm.TimelonlinelonelonvelonntSourcelon
import java.lang
import org.apachelon.helonron.api.{Config => HelonronConfig}
import org.apachelon.helonron.common.basics.BytelonAmount
import org.apachelon.storm.{Config => BTConfig}
import scala.collelonction.JavaConvelonrtelonrs._

objelonct TwelonelontJobRunnelonr {
  delonf main(args: Array[String]): Unit = {
    GelonnelonricRunnelonr(args, TwelonelontStormJob(_))
  }
}

objelonct TwelonelontStormJob {

  import com.twittelonr.simclustelonrs_v2.summingbird.common.Implicits._

  delonf jLong(num: Long): lang.Long = java.lang.Long.valuelonOf(num)
  delonf jInt(num: Int): Intelongelonr = java.lang.Intelongelonr.valuelonOf(num)
  delonf apply(args: Args): StormConfig = {

    lazy val elonnv: String = args.gelontOrelonlselon("elonnv", "prod")
    lazy val zonelon: String = args.gelontOrelonlselon("dc", "atla")

    // Thelon only SimClustelonrs elonNV is Alt. Will clelonan up soon.
    lazy val profilelon = SimClustelonrsProfilelon.felontchTwelonelontJobProfilelon(elonnvironmelonnt(elonnv), AltSelontting.Alt)

    lazy val favoritelonelonvelonntSourcelon = TimelonlinelonelonvelonntSourcelon(
      // Notelon: do not sharelon thelon samelon subsribelonrId with othelonr jobs. Apply a nelonw onelon if nelonelondelond
      SubscribelonrId(profilelon.timelonlinelonelonvelonntSourcelonSubscribelonrId)
    ).sourcelon

    lazy val commonMelontric =
      StormMelontric(nelonw CommonMelontric(), CommonMelontric.NAMelon, CommonMelontric.POLL_INTelonRVAL)
    lazy val flatMapMelontrics = FlatMapStormMelontrics(Itelonrablelon(commonMelontric))
    lazy val summelonrMelontrics = SummelonrStormMelontrics(Itelonrablelon(commonMelontric))

    lazy val elonntityClustelonrScorelonStorelon: Storm#Storelon[
      (SimClustelonrelonntity, FullClustelonrIdBuckelont),
      ClustelonrsWithScorelons
    ] = {
      Storm.storelon(
        elonntityClustelonrScorelonRelonadablelonStorelon
          .onlinelonMelonrgelonablelonStorelon(profilelon.elonntityClustelonrScorelonPath, profilelon.selonrvicelonIdelonntifielonr(zonelon)))
    }

    lazy val twelonelontTopKStorelon: Storm#Storelon[elonntityWithVelonrsion, TopKClustelonrsWithScorelons] = {
      Storm.storelon(
        TopKClustelonrsForTwelonelontRelonadablelonStorelon
          .onlinelonMelonrgelonablelonStorelon(profilelon.twelonelontTopKClustelonrsPath, profilelon.selonrvicelonIdelonntifielonr(zonelon)))
    }

    lazy val clustelonrTopKTwelonelontsStorelon: Storm#Storelon[FullClustelonrId, TopKTwelonelontsWithScorelons] = {
      Storm.storelon(
        TopKTwelonelontsForClustelonrRelonadablelonStorelon
          .onlinelonMelonrgelonablelonStorelon(profilelon.clustelonrTopKTwelonelontsPath, profilelon.selonrvicelonIdelonntifielonr(zonelon)))
    }

    lazy val clustelonrTopKTwelonelontsLightStorelon: Option[
      Storm#Storelon[FullClustelonrId, TopKTwelonelontsWithScorelons]
    ] = {
      profilelon.clustelonrTopKTwelonelontsLightPath.map { lightPath =>
        Storm.storelon(
          TopKTwelonelontsForClustelonrRelonadablelonStorelon
            .onlinelonMelonrgelonablelonStorelon(lightPath, profilelon.selonrvicelonIdelonntifielonr(zonelon)))
      }
    }

    lazy val uselonrIntelonrelonstelondInSelonrvicelon: Storm#Selonrvicelon[Long, ClustelonrsUselonrIsIntelonrelonstelondIn] = {
      Storm.selonrvicelon(
        UselonrIntelonrelonstelondInRelonadablelonStorelon.delonfaultStorelonWithMtls(
          ManhattanKVClielonntMtlsParams(profilelon.selonrvicelonIdelonntifielonr(zonelon)),
          modelonlVelonrsion = profilelon.modelonlVelonrsionStr
        ))
    }

    nelonw StormConfig {

      val jobNamelon: JobNamelon = JobNamelon(profilelon.jobNamelon)

      implicit val jobID: JobId = JobId(jobNamelon.toString)

      /**
       * Add relongistrars for chill selonrialization for uselonr-delonfinelond typelons.
       */
      ovelonrridelon delonf relongistrars =
        List(
          SBRunConfig.relongistelonr[SimClustelonrelonntity],
          SBRunConfig.relongistelonr[FullClustelonrIdBuckelont],
          SBRunConfig.relongistelonr[ClustelonrsWithScorelons],
          SBRunConfig.relongistelonr[elonntityWithVelonrsion],
          SBRunConfig.relongistelonr[FullClustelonrId],
          SBRunConfig.relongistelonr[elonntityWithVelonrsion],
          SBRunConfig.relongistelonr[TopKelonntitielonsWithScorelons],
          SBRunConfig.relongistelonr[TopKClustelonrsWithScorelons],
          SBRunConfig.relongistelonr[TopKTwelonelontsWithScorelons]
        )

      /***** Job configuration selonttings *****/
      /**
       * Uselon vmSelonttings to configurelon thelon VM
       */
      ovelonrridelon delonf vmSelonttings: Selonq[String] = Selonq()

      privatelon val SourcelonPelonrWorkelonr = 1
      privatelon val FlatMapPelonrWorkelonr = 3
      privatelon val SummelonrPelonrWorkelonr = 3

      privatelon val TotalWorkelonr = 150

      /**
       * Uselon transformConfig to selont Helonron options.
       */
      ovelonrridelon delonf transformConfig(config: Map[String, AnyRelonf]): Map[String, AnyRelonf] = {
        val helonronConfig = nelonw HelonronConfig()

        /**
        Componelonnt namelons (subjelonct to changelon if you add morelon componelonnts, makelon surelon to updatelon this)
          Sourcelon: Tail-FlatMap-FlatMap-Summelonr-FlatMap-Sourcelon
          FlatMap: Tail-FlatMap-FlatMap-Summelonr-FlatMap, Tail-FlatMap-FlatMap, Tail-FlatMap-FlatMap,
          Tail-FlatMap
          Summelonr: Tail-FlatMap-FlatMap-Summelonr * 2, Tail, Tail.2
         */
        val sourcelonNamelon = "Tail-FlatMap-FlatMap-Summelonr-FlatMap-Sourcelon"
        val flatMapFlatMapSummelonrFlatMapNamelon = "Tail-FlatMap-FlatMap-Summelonr-FlatMap"

        // 1 CPU pelonr nodelon, 1 for StrelonamMgr
        // By delonfault, numCpus pelonr componelonnt = totalCPUs / total numbelonr of componelonnts.
        // To add morelon CPUs for a speloncific componelonnt, uselon helonronConfig.selontComponelonntCpu(namelon, numCPUs)
        // add 20% morelon CPUs to addrelonss back prelonssurelon issuelon
        val TotalCPU = jLong(
          (1.2 * (SourcelonPelonrWorkelonr * 1 + FlatMapPelonrWorkelonr * 4 + SummelonrPelonrWorkelonr * 6 + 1)).celonil.toInt)
        helonronConfig.selontContainelonrCpuRelonquelonstelond(TotalCPU.toDoublelon)

        // RAM selonttings
        val RamPelonrSourcelonGB = 8
        val RamPelonrSummelonrFlatMap = 8
        val RamDelonfaultPelonrComponelonnt = 4

        // Thelon elonxtra 4GB is not elonxplicitly assignelond to thelon StrelonamMgr, so it gelonts 2GB by delonfault, and
        // thelon relonmaining 2GB is sharelond among componelonnts. Kelonelonping this configuration for now, sincelon
        // it selonelonms stablelon
        val TotalRamRB =
          RamPelonrSourcelonGB * SourcelonPelonrWorkelonr * 1 +
            RamDelonfaultPelonrComponelonnt * FlatMapPelonrWorkelonr * 4 +
            RamDelonfaultPelonrComponelonnt * SummelonrPelonrWorkelonr * 6 +
            4 // relonselonrvelon 4GB for thelon StrelonamMgr

        // By delonfault, ramGB pelonr componelonnt = totalRAM / total numbelonr of componelonnts.
        // To adjust RAMs for a speloncific componelonnt, uselon helonronConfig.selontComponelonntRam(namelon, ramGB)
        helonronConfig.selontComponelonntRam(sourcelonNamelon, BytelonAmount.fromGigabytelons(RamPelonrSourcelonGB))
        helonronConfig.selontComponelonntRam(
          flatMapFlatMapSummelonrFlatMapNamelon,
          BytelonAmount.fromGigabytelons(RamPelonrSummelonrFlatMap))
        helonronConfig.selontContainelonrRamRelonquelonstelond(BytelonAmount.fromGigabytelons(TotalRamRB))

        supelonr.transformConfig(config) ++ List(
          BTConfig.TOPOLOGY_TelonAM_NAMelon -> "cassowary",
          BTConfig.TOPOLOGY_TelonAM_elonMAIL -> "no-relonply@twittelonr.com",
          BTConfig.TOPOLOGY_WORKelonRS -> jInt(TotalWorkelonr),
          BTConfig.TOPOLOGY_ACKelonR_elonXelonCUTORS -> jInt(0),
          BTConfig.TOPOLOGY_MelonSSAGelon_TIMelonOUT_SelonCS -> jInt(30),
          BTConfig.TOPOLOGY_WORKelonR_CHILDOPTS -> List(
            "-XX:MaxMelontaspacelonSizelon=256M",
            "-Djava.seloncurity.auth.login.config=config/jaas.conf",
            "-Dsun.seloncurity.krb5.delonbug=truelon",
            "-Dcom.twittelonr.elonvelonntbus.clielonnt.elonnablelonKafkaSaslTls=truelon",
            "-Dcom.twittelonr.elonvelonntbus.clielonnt.zonelonNamelon=" + zonelon
          ).mkString(" "),
          "storm.job.uniquelonId" -> jobID.gelont
        ) ++ helonronConfig.asScala.toMap
      }

      /**
       * Uselon gelontNamelondOptions to selont Summingbird runtimelon options
       * Thelon list of availablelon options: com.twittelonr.summingbird.onlinelon.option
       */
      ovelonrridelon delonf gelontNamelondOptions: Map[String, Options] = Map(
        "DelonFAULT" -> Options()
          .selont(FlatMapParallelonlism(TotalWorkelonr * FlatMapPelonrWorkelonr))
          .selont(SourcelonParallelonlism(TotalWorkelonr))
          .selont(SummelonrBatchMultiplielonr(1000))
          .selont(CachelonSizelon(10000))
          .selont(flatMapMelontrics)
          .selont(summelonrMelontrics),
        TwelonelontJob.NodelonNamelon.TwelonelontClustelonrUpdatelondScorelonsFlatMapNodelonNamelon -> Options()
          .selont(FlatMapParallelonlism(TotalWorkelonr * FlatMapPelonrWorkelonr)),
        TwelonelontJob.NodelonNamelon.TwelonelontClustelonrScorelonSummelonrNodelonNamelon -> Options()
        // Most elonxpelonnsivelon stelonp. Doublelon thelon capacity.
          .selont(SummelonrParallelonlism(TotalWorkelonr * SummelonrPelonrWorkelonr * 4))
          .selont(FlushFrelonquelonncy(30.selonconds)),
        TwelonelontJob.NodelonNamelon.ClustelonrTopKTwelonelontsNodelonNamelon -> Options()
          .selont(SummelonrParallelonlism(TotalWorkelonr * SummelonrPelonrWorkelonr))
          .selont(FlushFrelonquelonncy(30.selonconds)),
        TwelonelontJob.NodelonNamelon.ClustelonrTopKTwelonelontsLightNodelonNamelon -> Options()
          .selont(SummelonrParallelonlism(TotalWorkelonr * SummelonrPelonrWorkelonr))
          .selont(FlushFrelonquelonncy(30.selonconds)),
        TwelonelontJob.NodelonNamelon.TwelonelontTopKNodelonNamelon -> Options()
          .selont(SummelonrParallelonlism(TotalWorkelonr * SummelonrPelonrWorkelonr))
          .selont(FlushFrelonquelonncy(30.selonconds))
      )

      /** Relonquirelond job gelonnelonration call for your job, delonfinelond in Job.scala */
      ovelonrridelon delonf graph: TailProducelonr[Storm, Any] = TwelonelontJob.gelonnelonratelon[Storm](
        profilelon,
        favoritelonelonvelonntSourcelon,
        uselonrIntelonrelonstelondInSelonrvicelon,
        elonntityClustelonrScorelonStorelon,
        twelonelontTopKStorelon,
        clustelonrTopKTwelonelontsStorelon,
        clustelonrTopKTwelonelontsLightStorelon
      )
    }
  }
}
