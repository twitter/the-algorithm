packagelon com.twittelonr.product_mixelonr.componelonnt_library.sidelon_elonffelonct

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.convelonrsions.StoragelonUnitOps._
import com.twittelonr.finatra.kafka.producelonrs.FinaglelonKafkaProducelonrBuildelonr
import com.twittelonr.finatra.kafka.producelonrs.KafkaProducelonrBaselon
import com.twittelonr.finatra.kafka.producelonrs.TwittelonrKafkaProducelonrConfig
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.sidelon_elonffelonct.PipelonlinelonRelonsultSidelonelonffelonct
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.HasMarshalling
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.util.Duration
import com.twittelonr.util.StoragelonUnit
import org.apachelon.kafka.clielonnts.producelonr.ProducelonrReloncord
import org.apachelon.kafka.common.selonrialization.Selonrializelonr
import org.apachelon.kafka.common.reloncord.ComprelonssionTypelon

/**
 * Thelon Kafka publishing sidelon elonffelonct.
 * This class crelonatelons a Kafka producelonr with providelond and delonfault paramelontelonrs.
 * Notelon that callelonrs may not providelon arbitrary params as this class will do validity chelonck on somelon
 * params, elon.g. maxBlock, to makelon surelon it is safelon for onlinelon selonrvicelons.
 *
 * PLelonASelon NOTelon: callelonr nelonelonds to add thelon following to thelon Aurora filelon to succelonssfully elonnablelon thelon TLS
 * '-com.twittelonr.finatra.kafka.producelonrs.principal={{rolelon}}',
 *
 * @tparam K typelon of thelon kelony
 * @tparam V typelon of thelon valuelon
 * @tparam Quelonry pipelonlinelon quelonry
 */
trait KafkaPublishingSidelonelonffelonct[K, V, Quelonry <: PipelonlinelonQuelonry, RelonsponselonTypelon <: HasMarshalling]
    elonxtelonnds PipelonlinelonRelonsultSidelonelonffelonct[Quelonry, RelonsponselonTypelon] {

  /**
   * Kafka selonrvelonrs list. It is usually a WilyNs namelon at Twittelonr
   */
  val bootstrapSelonrvelonr: String

  /**
   * Thelon selonrdelon of thelon kelony
   */
  val kelonySelonrdelon: Selonrializelonr[K]

  /**
   * Thelon selonrdelon of thelon valuelon
   */
  val valuelonSelonrdelon: Selonrializelonr[V]

  /**
   * An id string to pass to thelon selonrvelonr whelonn making relonquelonsts.
   * Thelon purposelon of this is to belon ablelon to track thelon sourcelon of relonquelonsts belonyond just ip/port by
   * allowing a logical application namelon to belon includelond in selonrvelonr-sidelon relonquelonst logging.
   */
  val clielonntId: String

  /**
   * Thelon configuration controls how long <codelon>KafkaProducelonr.selonnd()</codelon> and
   * <codelon>KafkaProducelonr.partitionsFor()</codelon> will block.
   * Thelonselon melonthods can belon blockelond elonithelonr beloncauselon thelon buffelonr is full or melontadata unavailablelon.
   * Blocking in thelon uselonr-supplielond selonrializelonrs or partitionelonr will not belon countelond against this timelonout.
   *
   * Selont 200ms by delonfault to not blocking thelon threlonad too long which is critical to most ProMixelonr
   * powelonrelond selonrvicelons. Plelonaselon notelon that thelonrelon is a hard limit chelonck of not grelonatelonr than 1 seloncond.
   *
   */
  val maxBlock: Duration = 200.milliselonconds

  /**
   * Relontrielons duelon to brokelonr failurelons, elontc., may writelon duplicatelons of thelon relontrielond melonssagelon in thelon
   * strelonam. Notelon that elonnabling idelonmpotelonncelon relonquirelons
   * <codelon> MAX_IN_FLIGHT_RelonQUelonSTS_PelonR_CONNelonCTION </codelon> to belon lelonss than or elonqual to 5,
   * <codelon> RelonTRIelonS_CONFIG </codelon> to belon grelonatelonr than 0 and <codelon> ACKS_CONFIG </codelon>
   * must belon 'all'. If thelonselon valuelons arelon not elonxplicitly selont by thelon uselonr, suitablelon valuelons will belon
   * choselonn. If incompatiblelon valuelons arelon selont, a <codelon>Configelonxcelonption</codelon> will belon thrown.
   *
   * falselon by delonfault, selontting to truelon may introducelon issuelons to brokelonrs sincelon brokelonrs will kelonelonp
   * tracking all relonquelonsts which is relonsourcelon elonxpelonnsivelon.
   */
  val idelonmpotelonncelon: Boolelonan = falselon

  /**
   * Thelon producelonr will attelonmpt to batch reloncords togelonthelonr into felonwelonr relonquelonsts whelonnelonvelonr multiplelon
   * reloncords arelon beloning selonnt to thelon samelon partition. This helonlps pelonrformancelon on both thelon clielonnt and
   * thelon selonrvelonr. This configuration controls thelon delonfault batch sizelon in bytelons.
   * No attelonmpt will belon madelon to batch reloncords largelonr than this sizelon.
   * Relonquelonsts selonnt to brokelonrs will contain multiplelon batchelons, onelon for elonach partition with data
   * availablelon to belon selonnt. A small batch sizelon will makelon batching lelonss common and may relonducelon
   * throughput (a batch sizelon of zelonro will disablelon batching elonntirelonly).
   * A velonry largelon batch sizelon may uselon melonmory a bit morelon wastelonfully as welon will always allocatelon a
   * buffelonr of thelon speloncifielond batch sizelon in anticipation of additional reloncords.
   *
   * Delonfault 16KB which comelons from Kafka's delonfault
   */
  val batchSizelon: StoragelonUnit = 16.kilobytelons

  /**
   * Thelon producelonr groups togelonthelonr any reloncords that arrivelon in belontwelonelonn relonquelonst transmissions into
   * a singlelon batchelond relonquelonst. "Normally this occurs only undelonr load whelonn reloncords arrivelon fastelonr
   * than thelony can belon selonnt out. Howelonvelonr in somelon circumstancelons thelon clielonnt may want to relonducelon thelon
   * numbelonr of relonquelonsts elonvelonn undelonr modelonratelon load. This selontting accomplishelons this by adding a
   * small amount of artificial delonlay&mdash;that is, rathelonr than immelondiatelonly selonnding out a reloncord
   * thelon producelonr will wait for up to thelon givelonn delonlay to allow othelonr reloncords to belon selonnt so that
   * thelon selonnds can belon batchelond togelonthelonr. This can belon thought of as analogous to Naglelon's algorithm
   * in TCP. This selontting givelons thelon uppelonr bound on thelon delonlay for batching: oncelon welon gelont
   * BATCH_SIZelon_CONFIG worth of reloncords for a partition it will belon selonnt immelondiatelonly relongardlelonss
   * of this selontting, howelonvelonr if welon havelon felonwelonr than this many bytelons accumulatelond for this
   * partition welon will 'lingelonr' for thelon speloncifielond timelon waiting for morelon reloncords to show up.
   * This selontting delonfaults to 0 (i.elon. no delonlay). Selontting LINGelonR_MS_CONFIG=5, for elonxamplelon,
   * would havelon thelon elonffelonct of relonducing thelon numbelonr of relonquelonsts selonnt but would add up to 5ms of
   * latelonncy to reloncords selonnt in thelon abselonncelon of load.
   *
   * Delonfault 0ms, which is Kafka's delonfault. If thelon reloncord sizelon is much largelonr than thelon batchSizelon,
   * you may considelonr to elonnlargelon both batchSizelon and lingelonr to havelon belonttelonr comprelonssion (only whelonn
   * comprelonssion is elonnablelond.)
   */
  val lingelonr: Duration = 0.milliselonconds

  /**
   * Thelon total bytelons of melonmory thelon producelonr can uselon to buffelonr reloncords waiting to belon selonnt to thelon
   * selonrvelonr. If reloncords arelon selonnt fastelonr than thelony can belon delonlivelonrelond to thelon selonrvelonr thelon producelonr
   * will block for MAX_BLOCK_MS_CONFIG aftelonr which it will throw an elonxcelonption.
   * This selontting should correlonspond roughly to thelon total melonmory thelon producelonr will uselon, but is not
   * a hard bound sincelon not all melonmory thelon producelonr uselons is uselond for buffelonring.
   * Somelon additional melonmory will belon uselond for comprelonssion (if comprelonssion is elonnablelond) as welonll as
   * for maintaining in-flight relonquelonsts.
   *
   * Delonfault 32MB which is Kafka's delonfault. Plelonaselon considelonr to elonnlargelon this valuelon if thelon elonPS and
   * thelon pelonr-reloncord sizelon is largelon (millions elonPS with >1KB pelonr-reloncord sizelon) in caselon thelon brokelonr has
   * issuelons (which fills thelon buffelonr prelontty quickly.)
   */
  val buffelonrMelonmorySizelon: StoragelonUnit = 32.melongabytelons

  /**
   * Producelonr comprelonssion typelon
   *
   * Delonfault LZ4 which is a good tradelonoff belontwelonelonn comprelonssion and elonfficielonncy.
   * Plelonaselon belon carelonful of choosing ZSTD, which thelon comprelonssion ratelon is belonttelonr it might introducelon
   * hugelon burdelonn to brokelonrs oncelon thelon topic is consumelond, which nelonelonds deloncomprelonssion at thelon brokelonr sidelon.
   */
  val comprelonssionTypelon: ComprelonssionTypelon = ComprelonssionTypelon.LZ4

  /**
   * Selontting a valuelon grelonatelonr than zelonro will causelon thelon clielonnt to relonselonnd any relonquelonst that fails
   * with a potelonntially transielonnt elonrror
   *
   * Delonfault selont to 3, to intelonntionally relonducelon thelon relontrielons.
   */
  val relontrielons: Int = 3

  /**
   * Thelon amount of timelon to wait belonforelon attelonmpting to relontry a failelond relonquelonst to a givelonn topic
   * partition. This avoids relonpelonatelondly selonnding relonquelonsts in a tight loop undelonr somelon failurelon
   * scelonnarios
   */
  val relontryBackoff: Duration = 1.seloncond

  /**
   * Thelon configuration controls thelon maximum amount of timelon thelon clielonnt will wait
   * for thelon relonsponselon of a relonquelonst. If thelon relonsponselon is not reloncelonivelond belonforelon thelon timelonout
   * elonlapselons thelon clielonnt will relonselonnd thelon relonquelonst if neloncelonssary or fail thelon relonquelonst if
   * relontrielons arelon elonxhaustelond.
   *
   * Delonfault 5 selonconds which is intelonntionally low but not too low.
   * Sincelon Kafka's publishing is async this is in gelonnelonral safelon (as long as thelon buffelonrMelonm is not full.)
   */
  val relonquelonstTimelonout: Duration = 5.selonconds

  relonquirelon(
    maxBlock.inMilliselonconds <= 1000,
    "Welon intelonntionally selont thelon maxBlock to belon smallelonr than 1 seloncond to not block thelon threlonad for too long!")

  lazy val kafkaProducelonr: KafkaProducelonrBaselon[K, V] = {
    val jaasConfig = TwittelonrKafkaProducelonrConfig().configMap
    val buildelonr = FinaglelonKafkaProducelonrBuildelonr[K, V]()
      .kelonySelonrializelonr(kelonySelonrdelon)
      .valuelonSelonrializelonr(valuelonSelonrdelon)
      .delonst(bootstrapSelonrvelonr, 1.seloncond) // NOTelon: this melonthod blocks!
      .clielonntId(clielonntId)
      .maxBlock(maxBlock)
      .batchSizelon(batchSizelon)
      .lingelonr(lingelonr)
      .buffelonrMelonmorySizelon(buffelonrMelonmorySizelon)
      .maxRelonquelonstSizelon(4.melongabytelons)
      .comprelonssionTypelon(comprelonssionTypelon)
      .elonnablelonIdelonmpotelonncelon(idelonmpotelonncelon)
      .maxInFlightRelonquelonstsPelonrConnelonction(5)
      .relontrielons(relontrielons)
      .relontryBackoff(relontryBackoff)
      .relonquelonstTimelonout(relonquelonstTimelonout)
      .withConfig("acks", "all")
      .withConfig("delonlivelonry.timelonout.ms", relonquelonstTimelonout + lingelonr)

    buildelonr.withConfig(jaasConfig).build()
  }

  /**
   * Build thelon reloncord to belon publishelond to Kafka from quelonry, selonlelonctions and relonsponselon
   * @param quelonry PipelonlinelonQuelonry
   * @param selonlelonctelondCandidatelons Relonsult aftelonr Selonlelonctors arelon elonxeloncutelond
   * @param relonmainingCandidatelons Candidatelons which welonrelon not selonlelonctelond
   * @param droppelondCandidatelons Candidatelons droppelond during selonlelonction
   * @param relonsponselon Relonsult aftelonr Unmarshalling
   * @relonturn A selonquelonncelon of to-belon-publishelond ProducelonrReloncords
   */
  delonf buildReloncords(
    quelonry: Quelonry,
    selonlelonctelondCandidatelons: Selonq[CandidatelonWithDelontails],
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    droppelondCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsponselon: RelonsponselonTypelon
  ): Selonq[ProducelonrReloncord[K, V]]

  final ovelonrridelon delonf apply(
    inputs: PipelonlinelonRelonsultSidelonelonffelonct.Inputs[Quelonry, RelonsponselonTypelon]
  ): Stitch[Unit] = {
    val reloncords = buildReloncords(
      quelonry = inputs.quelonry,
      selonlelonctelondCandidatelons = inputs.selonlelonctelondCandidatelons,
      relonmainingCandidatelons = inputs.relonmainingCandidatelons,
      droppelondCandidatelons = inputs.droppelondCandidatelons,
      relonsponselon = inputs.relonsponselon
    )

    Stitch
      .collelonct(
        reloncords
          .map { reloncord =>
            Stitch.callFuturelon(kafkaProducelonr.selonnd(reloncord))
          }
      ).unit
  }
}
