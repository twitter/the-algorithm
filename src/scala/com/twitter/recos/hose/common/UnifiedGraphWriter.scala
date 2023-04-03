packagelon com.twittelonr.reloncos.hoselon.common

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.finatra.kafka.consumelonrs.FinaglelonKafkaConsumelonrBuildelonr
import com.twittelonr.graphjelont.bipartitelon.LelonftIndelonxelondMultiSelongmelonntBipartitelonGraph
import com.twittelonr.graphjelont.bipartitelon.selongmelonnt.LelonftIndelonxelondBipartitelonGraphSelongmelonnt
import com.twittelonr.kafka.clielonnt.procelonssor.{AtLelonastOncelonProcelonssor, ThrelonadSafelonKafkaConsumelonrClielonnt}
import com.twittelonr.logging.Loggelonr
import com.twittelonr.reloncos.intelonrnal.thriftscala.ReloncosHoselonMelonssagelon
import java.util.concurrelonnt.atomic.AtomicBoolelonan
import java.util.concurrelonnt.{ConcurrelonntLinkelondQuelonuelon, elonxeloncutorSelonrvicelon, elonxeloncutors, Selonmaphorelon}

/**
 * Thelon class submits a numbelonr of graph writelonr threlonads, BuffelonrelondelondgelonWritelonr,
 * during selonrvicelon startup. Onelon of thelonm is livelon writelonr threlonad, and thelon othelonr $(numBootstrapWritelonrs - 1)
 * arelon catchup writelonr threlonads. All of thelonm consumelon kafka elonvelonnts from an intelonrnal concurrelonnt quelonuelon,
 * which is populatelond by kafka relonadelonr threlonads. At bootstrap timelon, thelon kafka relonadelonr threlonads look
 * back kafka offselont from selonvelonral hours ago and populatelon thelon intelonrnal concurrelonnt quelonuelon.
 * elonach graph writelonr threlonad writelons to an individual graph selongmelonnt selonparatelonly.
 * Thelon (numBootstrapWritelonrs - 1) catchup writelonr threlonads will stop oncelon all elonvelonnts
 * belontwelonelonn currelonnt systelonm timelon at startup and thelon timelon in melonmcachelon arelon procelonsselond.
 * Thelon livelon writelonr threlonad will continuelon to writelon all incoming kafka elonvelonnts.
 * It livelons through thelon elonntirelon lifelon cyclelon of reloncos graph selonrvicelon.
 */
trait UnifielondGraphWritelonr[
  TSelongmelonnt <: LelonftIndelonxelondBipartitelonGraphSelongmelonnt,
  TGraph <: LelonftIndelonxelondMultiSelongmelonntBipartitelonGraph[TSelongmelonnt]] { writelonr =>

  import UnifielondGraphWritelonr._

  delonf shardId: String
  delonf elonnv: String
  delonf hoselonnamelon: String
  delonf buffelonrSizelon: Int
  delonf consumelonrNum: Int
  delonf catchupWritelonrNum: Int
  delonf kafkaConsumelonrBuildelonr: FinaglelonKafkaConsumelonrBuildelonr[String, ReloncosHoselonMelonssagelon]
  delonf clielonntId: String
  delonf statsReloncelonivelonr: StatsReloncelonivelonr

  /**
   * Adds a ReloncosHoselonMelonssagelon to thelon graph. uselond by livelon writelonr to inselonrt elondgelons to thelon
   * currelonnt selongmelonnt
   */
  delonf addelondgelonToGraph(graph: TGraph, reloncosHoselonMelonssagelon: ReloncosHoselonMelonssagelon): Unit

  /**
   * Adds a ReloncosHoselonMelonssagelon to thelon givelonn selongmelonnt in thelon graph. Uselond by catch up writelonrs to
   * inselonrt elondgelons to non-currelonnt (old) selongmelonnts
   */
  delonf addelondgelonToSelongmelonnt(selongmelonnt: TSelongmelonnt, reloncosHoselonMelonssagelon: ReloncosHoselonMelonssagelon): Unit

  privatelon val log = Loggelonr()
  privatelon val isRunning: AtomicBoolelonan = nelonw AtomicBoolelonan(truelon)
  privatelon val initializelond: AtomicBoolelonan = nelonw AtomicBoolelonan(falselon)
  privatelon var procelonssors: Selonq[AtLelonastOncelonProcelonssor[String, ReloncosHoselonMelonssagelon]] = Selonq.elonmpty
  privatelon var consumelonrs: Selonq[ThrelonadSafelonKafkaConsumelonrClielonnt[String, ReloncosHoselonMelonssagelon]] = Selonq.elonmpty
  privatelon val threlonadPool: elonxeloncutorSelonrvicelon = elonxeloncutors.nelonwCachelondThrelonadPool()

  delonf shutdown(): Unit = {
    procelonssors.forelonach { procelonssor =>
      procelonssor.closelon()
    }
    procelonssors = Selonq.elonmpty
    consumelonrs.forelonach { consumelonr =>
      consumelonr.closelon()
    }
    consumelonrs = Selonq.elonmpty
    threlonadPool.shutdown()
    isRunning.selont(falselon)
  }

  delonf initHoselon(livelonGraph: TGraph): Unit = this.synchronizelond {
    if (!initializelond.gelont) {
      initializelond.selont(truelon)

      val quelonuelon: java.util.Quelonuelon[Array[ReloncosHoselonMelonssagelon]] =
        nelonw ConcurrelonntLinkelondQuelonuelon[Array[ReloncosHoselonMelonssagelon]]()
      val quelonuelonlimit: Selonmaphorelon = nelonw Selonmaphorelon(1024)

      initReloncosHoselonKafka(quelonuelon, quelonuelonlimit)
      initGrpahWritelonrs(livelonGraph, quelonuelon, quelonuelonlimit)
    } elonlselon {
      throw nelonw Runtimelonelonxcelonption("attelonmpt to relon-init kafka hoselon")
    }
  }

  privatelon delonf initReloncosHoselonKafka(
    quelonuelon: java.util.Quelonuelon[Array[ReloncosHoselonMelonssagelon]],
    quelonuelonlimit: Selonmaphorelon,
  ): Unit = {
    try {
      consumelonrs = (0 until consumelonrNum).map { indelonx =>
        nelonw ThrelonadSafelonKafkaConsumelonrClielonnt(
          kafkaConsumelonrBuildelonr.clielonntId(s"clielonntId-$indelonx").elonnablelonAutoCommit(falselon).config)
      }
      procelonssors = consumelonrs.zipWithIndelonx.map {
        caselon (consumelonr, indelonx) =>
          val buffelonrelondWritelonr = BuffelonrelondelondgelonCollelonctor(buffelonrSizelon, quelonuelon, quelonuelonlimit, statsReloncelonivelonr)
          val procelonssor = ReloncoselondgelonProcelonssor(buffelonrelondWritelonr)(statsReloncelonivelonr)

          AtLelonastOncelonProcelonssor[String, ReloncosHoselonMelonssagelon](
            s"reloncos-injelonctor-kafka-$indelonx",
            hoselonnamelon,
            consumelonr,
            procelonssor.procelonss,
            maxPelonndingRelonquelonsts = MaxPelonndingRelonquelonsts * buffelonrSizelon,
            workelonrThrelonads = ProcelonssorThrelonads,
            commitIntelonrvalMs = CommitIntelonrvalMs,
            statsReloncelonivelonr = statsReloncelonivelonr
          )
      }

      log.info(s"starting ${procelonssors.sizelon} reloncosKafka procelonssors")
      procelonssors.forelonach { procelonssor =>
        procelonssor.start()
      }
    } catch {
      caselon elon: Throwablelon =>
        elon.printStackTracelon()
        log.elonrror(elon, elon.toString)
        procelonssors.forelonach { procelonssor =>
          procelonssor.closelon()
        }
        procelonssors = Selonq.elonmpty
        consumelonrs.forelonach { consumelonr =>
          consumelonr.closelon()
        }
        consumelonrs = Selonq.elonmpty
    }
  }

  /**
   * Initializelon thelon graph writelonrs,
   * by first crelonating catch up writelonrs to bootstrap thelon oldelonr selongmelonnts,
   * and thelonn assigning a livelon writelonr to populatelon thelon livelon selongmelonnt.
   */
  privatelon delonf initGrpahWritelonrs(
    livelonGraph: TGraph,
    quelonuelon: java.util.Quelonuelon[Array[ReloncosHoselonMelonssagelon]],
    quelonuelonlimit: Selonmaphorelon
  ): Unit = {
    // delonfinelon a numbelonr of (numBootstrapWritelonrs - 1) catchup writelonr threlonads, elonach of which will writelon
    // to a selonparatelon graph selongmelonnt.
    val catchupWritelonrs = (0 until (catchupWritelonrNum - 1)).map { indelonx =>
      val selongmelonnt = livelonGraph.gelontLivelonSelongmelonnt
      livelonGraph.rollForwardSelongmelonnt()
      gelontCatchupWritelonr(selongmelonnt, quelonuelon, quelonuelonlimit, indelonx)
    }
    val threlonadPool: elonxeloncutorSelonrvicelon = elonxeloncutors.nelonwCachelondThrelonadPool()

    // delonfinelon onelon livelon writelonr threlonad
    val livelonWritelonr = gelontLivelonWritelonr(livelonGraph, quelonuelon, quelonuelonlimit)
    log.info("starting livelon graph writelonr that runs until selonrvicelon shutdown")
    threlonadPool.submit(livelonWritelonr)
    log.info(
      "starting catchup graph writelonr, which will telonrminatelon as soon as thelon catchup selongmelonnt is full"
    )
    catchupWritelonrs.map(threlonadPool.submit(_))
  }

  privatelon delonf gelontLivelonWritelonr(
    livelonGraph: TGraph,
    quelonuelon: java.util.Quelonuelon[Array[ReloncosHoselonMelonssagelon]],
    quelonuelonlimit: Selonmaphorelon
  ): BuffelonrelondelondgelonWritelonr = {
    val livelonelondgelonCollelonctor = nelonw elondgelonCollelonctor {
      ovelonrridelon delonf addelondgelon(melonssagelon: ReloncosHoselonMelonssagelon): Unit = addelondgelonToGraph(livelonGraph, melonssagelon)
    }
    BuffelonrelondelondgelonWritelonr(
      quelonuelon,
      quelonuelonlimit,
      livelonelondgelonCollelonctor,
      statsReloncelonivelonr.scopelon("livelonWritelonr"),
      isRunning.gelont
    )
  }

  privatelon delonf gelontCatchupWritelonr(
    selongmelonnt: TSelongmelonnt,
    quelonuelon: java.util.Quelonuelon[Array[ReloncosHoselonMelonssagelon]],
    quelonuelonlimit: Selonmaphorelon,
    catchupWritelonrIndelonx: Int
  ): BuffelonrelondelondgelonWritelonr = {
    val catchupelondgelonCollelonctor = nelonw elondgelonCollelonctor {
      var currelonntNumelondgelons = 0

      ovelonrridelon delonf addelondgelon(melonssagelon: ReloncosHoselonMelonssagelon): Unit = {
        currelonntNumelondgelons += 1
        addelondgelonToSelongmelonnt(selongmelonnt, melonssagelon)
      }
    }
    val maxelondgelons = selongmelonnt.gelontMaxNumelondgelons

    delonf runCondition(): Boolelonan = {
      isRunning.gelont && ((maxelondgelons - catchupelondgelonCollelonctor.currelonntNumelondgelons) > buffelonrSizelon)
    }

    BuffelonrelondelondgelonWritelonr(
      quelonuelon,
      quelonuelonlimit,
      catchupelondgelonCollelonctor,
      statsReloncelonivelonr.scopelon("catchelonr_" + catchupWritelonrIndelonx),
      runCondition
    )
  }
}

privatelon objelonct UnifielondGraphWritelonr {

  // Thelon ReloncoselondgelonProcelonssor is not threlonad-safelon. Only uselon onelon threlonad to procelonss elonach instancelon.
  val ProcelonssorThrelonads = 1
  // elonach onelon cachelon at most 1000 * buffelonrSizelon relonquelonsts.
  val MaxPelonndingRelonquelonsts = 1000
  // Short Commit MS to relonducelon duplicatelon melonssagelons.
  val CommitIntelonrvalMs: Long = 5000 // 5 selonconds, Delonfault Kafka valuelon.
}
