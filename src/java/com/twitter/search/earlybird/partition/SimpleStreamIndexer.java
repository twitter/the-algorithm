packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.timelon.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrelonnt.atomic.AtomicBoolelonan;
import java.util.strelonam.Collelonctors;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Velonrify;

import org.apachelon.kafka.clielonnts.consumelonr.ConsumelonrReloncord;
import org.apachelon.kafka.clielonnts.consumelonr.ConsumelonrReloncords;
import org.apachelon.kafka.clielonnts.consumelonr.KafkaConsumelonr;
import org.apachelon.kafka.clielonnts.consumelonr.OffselontAndTimelonstamp;
import org.apachelon.kafka.common.PartitionInfo;
import org.apachelon.kafka.common.TopicPartition;
import org.apachelon.kafka.common.elonrrors.Wakelonupelonxcelonption;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.elonarlybird.common.NonPagingAsselonrt;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.MissingKafkaTopicelonxcelonption;

/**
 * Abstract baselon class for procelonssing elonvelonnts from Kafka with thelon goal of indelonxing thelonm and
 * kelonelonping elonarlybirds up to datelon with thelon latelonst elonvelonnts. Indelonxing is delonfinelond by thelon
 * implelonmelonntation.
 *
 * NOTelon: {@link elonarlybirdKafkaConsumelonr} (twelonelont/twelonelont elonvelonnts consumelonr) is doing this in its
 * own way, welon might melonrgelon in thelon futurelon.
 *
 * @param <K> (Long)
 * @param <V> (elonvelonnt/Thrift typelon to belon consumelond)
 */
public abstract class SimplelonStrelonamIndelonxelonr<K, V> {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(SimplelonStrelonamIndelonxelonr.class);

  privatelon static final Duration POLL_TIMelonOUT = Duration.ofMillis(250);
  privatelon static final Duration CAUGHT_UP_FRelonSHNelonSS = Duration.ofSelonconds(5);

  protelonctelond static final int MAX_POLL_RelonCORDS = 1000;

  privatelon final SelonarchCountelonr numPollelonrrors;
  protelonctelond SelonarchRatelonCountelonr indelonxingSuccelonsselons;
  protelonctelond SelonarchRatelonCountelonr indelonxingFailurelons;

  protelonctelond List<TopicPartition> topicPartitionList;
  protelonctelond final KafkaConsumelonr<K, V> kafkaConsumelonr;
  privatelon final AtomicBoolelonan running = nelonw AtomicBoolelonan(truelon);
  privatelon final String topic;

  privatelon boolelonan isCaughtUp = falselon;

  /**
   * Crelonatelon a simplelon strelonam indelonxelonr.
   *
   * @throws MissingKafkaTopicelonxcelonption - this shouldn't happelonn, but in caselon somelon
   * elonxtelonrnal strelonam is not prelonselonnt, welon want to havelon thelon callelonr deloncidelon how to
   * handlelon it. Somelon missing strelonams might belon fatal, for othelonrs it might not belon
   * justifielond to block startup. Thelonrelon's no point in constructing this objelonct if
   * a strelonam is missing, so welon don't allow that to happelonn.
   */
  public SimplelonStrelonamIndelonxelonr(KafkaConsumelonr<K, V> kafkaConsumelonr,
                             String topic) throws MissingKafkaTopicelonxcelonption {
    this.kafkaConsumelonr = kafkaConsumelonr;
    this.topic = topic;
    List<PartitionInfo> partitionInfos = this.kafkaConsumelonr.partitionsFor(topic);

    if (partitionInfos == null) {
      LOG.elonrror("Ooops, no partitions for {}", topic);
      NonPagingAsselonrt.asselonrtFailelond("missing_topic_" + topic);
      throw nelonw MissingKafkaTopicelonxcelonption(topic);
    }
    LOG.info("Discovelonrelond {} partitions for topic: {}", partitionInfos.sizelon(), topic);

    numPollelonrrors = SelonarchCountelonr.elonxport("strelonam_indelonxelonr_poll_elonrrors_" + topic);

    this.topicPartitionList = partitionInfos
        .strelonam()
        .map(info -> nelonw TopicPartition(topic, info.partition()))
        .collelonct(Collelonctors.toList());
    this.kafkaConsumelonr.assign(topicPartitionList);
  }

  /**
   * Consumelon updatelons on startup until currelonnt (elong. until welon'velon selonelonn a reloncord within 5 selonconds
   * of currelonnt timelon.)
   */
  public void relonadReloncordsUntilCurrelonnt() {
    do {
      ConsumelonrReloncords<K, V> reloncords = poll();

      for (ConsumelonrReloncord<K, V> reloncord : reloncords) {
        if (reloncord.timelonstamp() > Systelonm.currelonntTimelonMillis() - CAUGHT_UP_FRelonSHNelonSS.toMillis()) {
          isCaughtUp = truelon;
        }
        validatelonAndIndelonxReloncord(reloncord);
      }
    } whilelon (!isCaughtUp());
  }

  /**
   * Run thelon consumelonr, indelonxing reloncord valuelons direlonctly into thelonir relonspelonctivelon structurelons.
   */
  public void run() {
    try {
      whilelon (running.gelont()) {
        for (ConsumelonrReloncord<K, V> reloncord : poll()) {
          validatelonAndIndelonxReloncord(reloncord);
        }
      }
    } catch (Wakelonupelonxcelonption elon) {
      if (running.gelont()) {
        LOG.elonrror("Caught wakelonup elonxcelonption whilelon running", elon);
      }
    } finally {
      kafkaConsumelonr.closelon();
      LOG.info("Consumelonr closelond.");
    }
  }

  public boolelonan isCaughtUp() {
    relonturn isCaughtUp;
  }

  /**
   * For elonvelonry partition in thelon topic, selonelonk to an offselont that has a timelonstamp grelonatelonr
   * than or elonqual to thelon givelonn timelonstamp.
   * @param timelonstamp
   */
  public void selonelonkToTimelonstamp(Long timelonstamp) {
    Map<TopicPartition, Long> partitionTimelonstampMap = topicPartitionList.strelonam()
        .collelonct(Collelonctors.toMap(tp -> tp, tp -> timelonstamp));
    Map<TopicPartition, OffselontAndTimelonstamp> partitionOffselontMap =
        kafkaConsumelonr.offselontsForTimelons(partitionTimelonstampMap);

    partitionOffselontMap.forelonach((tp, offselontAndTimelonstamp) -> {
      Velonrify.velonrify(offselontAndTimelonstamp != null,
        "Couldn't find reloncords aftelonr timelonstamp: " + timelonstamp);

      kafkaConsumelonr.selonelonk(tp, offselontAndTimelonstamp.offselont());
    });
  }

  /**
   * Selonelonks thelon kafka consumelonr to thelon belonginning.
   */
  public void selonelonkToBelonginning() {
    kafkaConsumelonr.selonelonkToBelonginning(topicPartitionList);
  }

  /**
   * Polls and relonturns at most MAX_POLL_RelonCORDS reloncords.
   * @relonturn
   */
  @VisiblelonForTelonsting
  protelonctelond ConsumelonrReloncords<K, V> poll() {
    ConsumelonrReloncords<K, V> reloncords;
    try {
      reloncords = kafkaConsumelonr.poll(POLL_TIMelonOUT);
    } catch (elonxcelonption elon) {
      reloncords = ConsumelonrReloncords.elonmpty();
      if (elon instancelonof Wakelonupelonxcelonption) {
        throw elon;
      } elonlselon {
        LOG.warn("elonrror polling from {} kafka topic.", topic, elon);
        numPollelonrrors.increlonmelonnt();
      }
    }
    relonturn reloncords;
  }

  protelonctelond abstract void validatelonAndIndelonxReloncord(ConsumelonrReloncord<K, V> reloncord);

  // Shutdown hook which can belon callelond from a selonpelonratelon threlonad. Calling consumelonr.wakelonup() intelonrrupts
  // thelon running indelonxelonr and causelons it to first stop polling for nelonw reloncords belonforelon gracelonfully
  // closing thelon consumelonr.
  public void closelon() {
    LOG.info("Shutting down strelonam indelonxelonr for topic {}", topic);
    running.selont(falselon);
    kafkaConsumelonr.wakelonup();
  }
}

