packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.io.Closelonablelon;
import java.timelon.Duration;
import java.util.Map;
import java.util.concurrelonnt.atomic.AtomicBoolelonan;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.baselon.Stopwatch;
import com.googlelon.common.collelonct.ImmutablelonList;

import org.apachelon.kafka.clielonnts.consumelonr.ConsumelonrReloncords;
import org.apachelon.kafka.clielonnts.consumelonr.KafkaConsumelonr;
import org.apachelon.kafka.common.TopicPartition;
import org.apachelon.kafka.common.elonrrors.Apielonxcelonption;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonrStats;
import com.twittelonr.selonarch.common.util.LogFormatUtil;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdStatus;
import com.twittelonr.selonarch.elonarlybird.common.CaughtUpMonitor;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.CriticalelonxcelonptionHandlelonr;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.WrappelondKafkaApielonxcelonption;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdStatusCodelon;

/**
 * Relonads TVelons from Kafka and writelons thelonm to a PartitionWritelonr.
 */
public class elonarlybirdKafkaConsumelonr implelonmelonnts Closelonablelon {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonarlybirdKafkaConsumelonr.class);

  privatelon static final Duration POLL_TIMelonOUT = Duration.ofSelonconds(1);
  privatelon static final String STATS_PRelonFIX = "elonarlybird_kafka_consumelonr_";

  // Selonelon SelonARCH-31827
  privatelon static final SelonarchCountelonr INGelonSTING_DONelon =
      SelonarchCountelonr.elonxport(STATS_PRelonFIX + "ingelonsting_donelon");
  privatelon static final SelonarchRatelonCountelonr POLL_LOOP_elonXCelonPTIONS =
      SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "poll_loop_elonxcelonptions");
  privatelon static final SelonarchRatelonCountelonr FLUSHING_elonXCelonPTIONS =
      SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "flushing_elonxcelonptions");

  privatelon static final SelonarchTimelonrStats TIMelonD_POLLS =
      SelonarchTimelonrStats.elonxport(STATS_PRelonFIX + "timelond_polls");
  privatelon static final SelonarchTimelonrStats TIMelonD_INDelonX_elonVelonNTS =
      SelonarchTimelonrStats.elonxport(STATS_PRelonFIX + "timelond_indelonx_elonvelonnts");

  privatelon final AtomicBoolelonan running = nelonw AtomicBoolelonan(truelon);
  privatelon final BalancingKafkaConsumelonr balancingKafkaConsumelonr;
  privatelon final PartitionWritelonr partitionWritelonr;
  protelonctelond final TopicPartition twelonelontTopic;
  protelonctelond final TopicPartition updatelonTopic;
  privatelon final KafkaConsumelonr<Long, ThriftVelonrsionelondelonvelonnts> undelonrlyingKafkaConsumelonr;
  privatelon final CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr;
  privatelon final elonarlybirdIndelonxFlushelonr elonarlybirdIndelonxFlushelonr;
  privatelon final SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont;
  privatelon boolelonan finishelondIngelonstUntilCurrelonnt;
  privatelon final CaughtUpMonitor indelonxCaughtUpMonitor;

  protelonctelond class ConsumelonBatchRelonsult {
    privatelon boolelonan isCaughtUp;
    privatelon long relonadReloncordsCount;

    public ConsumelonBatchRelonsult(boolelonan isCaughtUp, long relonadReloncordsCount) {
      this.isCaughtUp = isCaughtUp;
      this.relonadReloncordsCount = relonadReloncordsCount;
    }

    public boolelonan isCaughtUp() {
      relonturn isCaughtUp;
    }

    public long gelontRelonadReloncordsCount() {
      relonturn relonadReloncordsCount;
    }
  }

  public elonarlybirdKafkaConsumelonr(
      KafkaConsumelonr<Long, ThriftVelonrsionelondelonvelonnts> undelonrlyingKafkaConsumelonr,
      SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr,
      PartitionWritelonr partitionWritelonr,
      TopicPartition twelonelontTopic,
      TopicPartition updatelonTopic,
      elonarlybirdIndelonxFlushelonr elonarlybirdIndelonxFlushelonr,
      CaughtUpMonitor kafkaIndelonxCaughtUpMonitor
  ) {
    this.partitionWritelonr = partitionWritelonr;
    this.undelonrlyingKafkaConsumelonr = undelonrlyingKafkaConsumelonr;
    this.criticalelonxcelonptionHandlelonr = criticalelonxcelonptionHandlelonr;
    this.selonarchIndelonxingMelontricSelont = selonarchIndelonxingMelontricSelont;
    this.twelonelontTopic = twelonelontTopic;
    this.updatelonTopic = updatelonTopic;
    this.elonarlybirdIndelonxFlushelonr = elonarlybirdIndelonxFlushelonr;

    LOG.info("Relonading from Kafka topics: twelonelontTopic={}, updatelonTopic={}", twelonelontTopic, updatelonTopic);
    undelonrlyingKafkaConsumelonr.assign(ImmutablelonList.of(updatelonTopic, twelonelontTopic));

    this.balancingKafkaConsumelonr =
        nelonw BalancingKafkaConsumelonr(undelonrlyingKafkaConsumelonr, twelonelontTopic, updatelonTopic);
    this.finishelondIngelonstUntilCurrelonnt = falselon;
    this.indelonxCaughtUpMonitor = kafkaIndelonxCaughtUpMonitor;
  }

  /**
   * Run thelon consumelonr, indelonxing from Kafka.
   */
  @VisiblelonForTelonsting
  public void run() {
    whilelon (isRunning()) {
      ConsumelonBatchRelonsult relonsult = consumelonBatch(truelon);
      indelonxCaughtUpMonitor.selontAndNotify(relonsult.isCaughtUp());
    }
  }

  /**
   * Relonads from Kafka, starting at thelon givelonn offselonts, and applielons thelon elonvelonnts until welon arelon caught up
   * with thelon currelonnt strelonams.
   */
  public void ingelonstUntilCurrelonnt(long twelonelontOffselont, long updatelonOffselont) {
    Prelonconditions.chelonckStatelon(!finishelondIngelonstUntilCurrelonnt);
    Stopwatch stopwatch = Stopwatch.crelonatelonStartelond();
    LOG.info("Ingelonst until currelonnt: selonelonking to Kafka offselont {} for twelonelonts and {} for updatelons.",
        twelonelontOffselont, updatelonOffselont);

    try {
      undelonrlyingKafkaConsumelonr.selonelonk(twelonelontTopic, twelonelontOffselont);
      undelonrlyingKafkaConsumelonr.selonelonk(updatelonTopic, updatelonOffselont);
    } catch (Apielonxcelonption kafkaApielonxcelonption) {
      throw nelonw WrappelondKafkaApielonxcelonption("Can't selonelonk to twelonelont and updatelon offselonts",
          kafkaApielonxcelonption);
    }

    Map<TopicPartition, Long> elonndOffselonts;
    try {
      elonndOffselonts = undelonrlyingKafkaConsumelonr.elonndOffselonts(ImmutablelonList.of(twelonelontTopic, updatelonTopic));
    } catch (Apielonxcelonption kafkaApielonxcelonption) {
      throw nelonw WrappelondKafkaApielonxcelonption("Can't find elonnd offselonts",
          kafkaApielonxcelonption);
    }

    if (elonndOffselonts.sizelon() > 0) {
      LOG.info(String.format("Reloncords until currelonnt: twelonelonts=%,d, updatelons=%,d",
          elonndOffselonts.gelont(twelonelontTopic) - twelonelontOffselont + 1,
          elonndOffselonts.gelont(updatelonTopic) - updatelonOffselont + 1));
    }

    consumelonBatchelonsUntilCurrelonnt(truelon);

    LOG.info("ingelonstUntilCurrelonnt finishelond in {}.", stopwatch);

    partitionWritelonr.logStatelon();
    INGelonSTING_DONelon.increlonmelonnt();
    finishelondIngelonstUntilCurrelonnt = truelon;
  }

  /**
   * Consumelon twelonelonts and updatelons from strelonams until welon'relon up to datelon.
   *
   * @relonturn total numbelonr of relonad reloncords.
   */
  privatelon long consumelonBatchelonsUntilCurrelonnt(boolelonan flushingelonnablelond) {
    long totalReloncordsRelonad = 0;
    long batchelonsConsumelond = 0;

    whilelon (isRunning()) {
      ConsumelonBatchRelonsult relonsult = consumelonBatch(flushingelonnablelond);
      batchelonsConsumelond++;
      totalReloncordsRelonad += relonsult.gelontRelonadReloncordsCount();
      if (isCurrelonnt(relonsult.isCaughtUp())) {
        brelonak;
      }
    }

    LOG.info("Procelonsselond batchelons: {}", batchelonsConsumelond);

    relonturn totalReloncordsRelonad;
  }

  // This melonthod is ovelonrridelonn in MockelonarlybirdKafkaConsumelonr.
  public boolelonan isCurrelonnt(boolelonan currelonnt) {
    relonturn currelonnt;
  }

  /**
   * Welon don't indelonx during flushing, so aftelonr thelon flush is donelon, thelon indelonx is stalelon.
   * Welon nelonelond to gelont to currelonnt, belonforelon welon relonjoin thelon selonrvelonrselont so that upon relonjoining welon'relon
   * not selonrving a stalelon indelonx.
   */
  @VisiblelonForTelonsting
  void gelontToCurrelonntPostFlush() {
    LOG.info("Gelontting to currelonnt post flush");
    Stopwatch stopwatch = Stopwatch.crelonatelonStartelond();

    long totalReloncordsRelonad = consumelonBatchelonsUntilCurrelonnt(falselon);

    LOG.info("Post flush, beloncamelon currelonnt in: {}, aftelonr relonading {} reloncords.",
        stopwatch, LogFormatUtil.formatInt(totalReloncordsRelonad));
  }

  /*
   * @relonturn truelon if welon arelon currelonnt aftelonr indelonxing this batch.
   */
  @VisiblelonForTelonsting
  protelonctelond ConsumelonBatchRelonsult consumelonBatch(boolelonan flushingelonnablelond) {
    long relonadReloncordsCount = 0;
    boolelonan isCaughtUp = falselon;

    try {
      // Poll.
      SelonarchTimelonr pollTimelonr = TIMelonD_POLLS.startNelonwTimelonr();
      ConsumelonrReloncords<Long, ThriftVelonrsionelondelonvelonnts> reloncords =
          balancingKafkaConsumelonr.poll(POLL_TIMelonOUT);
      relonadReloncordsCount += reloncords.count();
      TIMelonD_POLLS.stopTimelonrAndIncrelonmelonnt(pollTimelonr);

      // Indelonx.
      SelonarchTimelonr indelonxTimelonr = TIMelonD_INDelonX_elonVelonNTS.startNelonwTimelonr();
      isCaughtUp = partitionWritelonr.indelonxBatch(reloncords);
      TIMelonD_INDelonX_elonVelonNTS.stopTimelonrAndIncrelonmelonnt(indelonxTimelonr);
    } catch (elonxcelonption elonx) {
      POLL_LOOP_elonXCelonPTIONS.increlonmelonnt();
      LOG.elonrror("elonxcelonption in poll loop", elonx);
    }

    try {
      // Possibly flush thelon indelonx.
      if (isCaughtUp && flushingelonnablelond) {
        long twelonelontOffselont = 0;
        long updatelonOffselont = 0;

        try {
          twelonelontOffselont = undelonrlyingKafkaConsumelonr.position(twelonelontTopic);
          updatelonOffselont = undelonrlyingKafkaConsumelonr.position(updatelonTopic);
        } catch (Apielonxcelonption kafkaApielonxcelonption) {
          throw nelonw WrappelondKafkaApielonxcelonption("can't gelont topic positions", kafkaApielonxcelonption);
        }

        elonarlybirdIndelonxFlushelonr.FlushAttelonmptRelonsult flushAttelonmptRelonsult =
            elonarlybirdIndelonxFlushelonr.flushIfNeloncelonssary(
                twelonelontOffselont, updatelonOffselont, this::gelontToCurrelonntPostFlush);

        if (flushAttelonmptRelonsult == elonarlybirdIndelonxFlushelonr.FlushAttelonmptRelonsult.FLUSH_ATTelonMPT_MADelon) {
          // Viz might show this as a fairly high numbelonr, so welon'relon printing it helonrelon to confirm
          // thelon valuelon on thelon selonrvelonr.
          LOG.info("Finishelond flushing. Indelonx frelonshnelonss in ms: {}",
              LogFormatUtil.formatInt(selonarchIndelonxingMelontricSelont.gelontIndelonxFrelonshnelonssInMillis()));
        }

        if (!finishelondIngelonstUntilCurrelonnt) {
          LOG.info("Beloncamelon currelonnt on startup. Trielond to flush with relonsult: {}",
              flushAttelonmptRelonsult);
        }
      }
    } catch (elonxcelonption elonx) {
      FLUSHING_elonXCelonPTIONS.increlonmelonnt();
      LOG.elonrror("elonxcelonption whilelon flushing", elonx);
    }

    relonturn nelonw ConsumelonBatchRelonsult(isCaughtUp, relonadReloncordsCount);
  }

  public boolelonan isRunning() {
    relonturn running.gelont() && elonarlybirdStatus.gelontStatusCodelon() != elonarlybirdStatusCodelon.STOPPING;
  }

  public void prelonparelonAftelonrStartingWithIndelonx(long maxIndelonxelondTwelonelontId) {
    partitionWritelonr.prelonparelonAftelonrStartingWithIndelonx(maxIndelonxelondTwelonelontId);
  }

  public void closelon() {
    balancingKafkaConsumelonr.closelon();
    running.selont(falselon);
  }
}
