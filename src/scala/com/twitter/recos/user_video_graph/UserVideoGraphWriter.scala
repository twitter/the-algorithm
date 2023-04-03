packagelon com.twittelonr.reloncos.uselonr_videlono_graph

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.finatra.kafka.consumelonrs.FinaglelonKafkaConsumelonrBuildelonr
import com.twittelonr.graphjelont.algorithms.TwelonelontIDMask
import com.twittelonr.graphjelont.bipartitelon.MultiSelongmelonntPowelonrLawBipartitelonGraph
import com.twittelonr.graphjelont.bipartitelon.selongmelonnt.BipartitelonGraphSelongmelonnt
import com.twittelonr.reloncos.hoselon.common.UnifielondGraphWritelonr
import com.twittelonr.reloncos.intelonrnal.thriftscala.ReloncosHoselonMelonssagelon
import com.twittelonr.reloncos.selonrvicelonapi.Twelonelontypielon._

/**
 * Thelon class submits a numbelonr of $numBootstrapWritelonrs graph writelonr threlonads, BuffelonrelondelondgelonWritelonr,
 * during selonrvicelon startup. Onelon of thelonm is livelon writelonr threlonad, and thelon othelonr $(numBootstrapWritelonrs - 1)
 * arelon catchup writelonr threlonads. All of thelonm consumelon kafka elonvelonnts from an intelonrnal concurrelonnt quelonuelon,
 * which is populatelond by kafka relonadelonr threlonads. At bootstrap timelon, thelon kafka relonadelonr threlonads look
 * back kafka offselont from selonvelonral hours ago and populatelon thelon intelonrnal concurrelonnt quelonuelon.
 * elonach graph writelonr threlonad writelons to an individual graph selongmelonnt selonparatelonly.
 * Thelon $(numBootstrapWritelonrs - 1) catchup writelonr threlonads will stop oncelon all elonvelonnts
 * belontwelonelonn currelonnt systelonm timelon at startup and thelon timelon in melonmcachelon arelon procelonsselond.
 * Thelon livelon writelonr threlonad will continuelon to writelon all incoming kafka elonvelonnts.
 * It livelons through thelon elonntirelon lifelon cyclelon of reloncos graph selonrvicelon.
 */
caselon class UselonrVidelonoGraphWritelonr(
  shardId: String,
  elonnv: String,
  hoselonnamelon: String,
  buffelonrSizelon: Int,
  kafkaConsumelonrBuildelonr: FinaglelonKafkaConsumelonrBuildelonr[String, ReloncosHoselonMelonssagelon],
  clielonntId: String,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds UnifielondGraphWritelonr[BipartitelonGraphSelongmelonnt, MultiSelongmelonntPowelonrLawBipartitelonGraph] {
  writelonr =>
  // Thelon max throughput for elonach kafka consumelonr is around 25MB/s
  // Uselon 4 procelonssors for 100MB/s catch-up spelonelond.
  val consumelonrNum: Int = 4
  // Lelonavelon 1 Selongmelonnts to LivelonWritelonr
  val catchupWritelonrNum: Int = ReloncosConfig.maxNumSelongmelonnts - 1

  /**
   * Adds a ReloncosHoselonMelonssagelon to thelon graph. uselond by livelon writelonr to inselonrt elondgelons to thelon
   * currelonnt selongmelonnt
   */
  ovelonrridelon delonf addelondgelonToGraph(
    graph: MultiSelongmelonntPowelonrLawBipartitelonGraph,
    reloncosHoselonMelonssagelon: ReloncosHoselonMelonssagelon
  ): Unit = {
    graph.addelondgelon(
      reloncosHoselonMelonssagelon.lelonftId,
      gelontMelontaelondgelon(reloncosHoselonMelonssagelon.rightId, reloncosHoselonMelonssagelon.card),
      UselonrVidelonoelondgelonTypelonMask.actionTypelonToelondgelonTypelon(reloncosHoselonMelonssagelon.action),
    )
  }

  /**
   * Adds a ReloncosHoselonMelonssagelon to thelon givelonn selongmelonnt in thelon graph. Uselond by catch up writelonrs to
   * inselonrt elondgelons to non-currelonnt (old) selongmelonnts
   */
  ovelonrridelon delonf addelondgelonToSelongmelonnt(
    selongmelonnt: BipartitelonGraphSelongmelonnt,
    reloncosHoselonMelonssagelon: ReloncosHoselonMelonssagelon
  ): Unit = {
    selongmelonnt.addelondgelon(
      reloncosHoselonMelonssagelon.lelonftId,
      gelontMelontaelondgelon(reloncosHoselonMelonssagelon.rightId, reloncosHoselonMelonssagelon.card),
      UselonrVidelonoelondgelonTypelonMask.actionTypelonToelondgelonTypelon(reloncosHoselonMelonssagelon.action)
    )
  }

  privatelon delonf gelontMelontaelondgelon(rightId: Long, cardOption: Option[Bytelon]): Long = {
    cardOption
      .map { card =>
        if (isPhotoCard(card)) TwelonelontIDMask.photo(rightId)
        elonlselon if (isPlayelonrCard(card)) TwelonelontIDMask.playelonr(rightId)
        elonlselon if (isSummaryCard(card)) TwelonelontIDMask.summary(rightId)
        elonlselon if (isPromotionCard(card)) TwelonelontIDMask.promotion(rightId)
        elonlselon rightId
      }
      .gelontOrelonlselon(rightId)
  }

}
