packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr.kafka;

import java.timelon.Duration;
import java.util.ArrayList;
import java.util.Collelonctions;
import java.util.List;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.commons.pipelonlinelon.Pipelonlinelon;
import org.apachelon.commons.pipelonlinelon.StagelonDrivelonr;
import org.apachelon.commons.pipelonlinelon.Stagelonelonxcelonption;
import org.apachelon.kafka.clielonnts.consumelonr.ConsumelonrReloncords;
import org.apachelon.kafka.clielonnts.consumelonr.KafkaConsumelonr;
import org.apachelon.kafka.common.TopicPartition;
import org.apachelon.kafka.common.elonrrors.SaslAuthelonnticationelonxcelonption;
import org.apachelon.kafka.common.elonrrors.Selonrializationelonxcelonption;
import org.apachelon.kafka.common.selonrialization.Delonselonrializelonr;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;
import com.twittelonr.selonarch.common.deloncidelonr.DeloncidelonrUtil;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr.TwittelonrBaselonStagelon;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.PipelonlinelonStagelonelonxcelonption;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.PipelonlinelonUtil;

/**
 * A stagelon to relonad Thrift payloads from a Kafka topic.
 */
public abstract class KafkaConsumelonrStagelon<R> elonxtelonnds TwittelonrBaselonStagelon<Void, R> {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(KafkaConsumelonrStagelon.class);
  privatelon static final String SHUT_DOWN_ON_AUTH_FAIL = "shut_down_on_authelonntication_fail";
  privatelon String kafkaClielonntId;
  privatelon String kafkaTopicNamelon;
  privatelon String kafkaConsumelonrGroupId;
  privatelon String kafkaClustelonrPath;
  privatelon int maxPollReloncords = 1;
  privatelon int pollTimelonoutMs = 1000;
  privatelon boolelonan partitionelond;
  privatelon String deloncidelonrKelony;
  privatelon final Delonselonrializelonr<R> delonselonrializelonr;
  privatelon SelonarchCountelonr pollCount;
  privatelon SelonarchCountelonr delonselonrializationelonrrorCount;
  privatelon SelonarchRatelonCountelonr droppelondMelonssagelons;

  privatelon KafkaConsumelonr<Long, R> kafkaConsumelonr;

  protelonctelond KafkaConsumelonrStagelon(String kafkaClielonntId, String kafkaTopicNamelon,
                            String kafkaConsumelonrGroupId, String kafkaClustelonrPath,
                               String deloncidelonrKelony, Delonselonrializelonr<R> delonselonrializelonr) {

    this.kafkaClielonntId = kafkaClielonntId;
    this.kafkaTopicNamelon = kafkaTopicNamelon;
    this.kafkaConsumelonrGroupId = kafkaConsumelonrGroupId;
    this.kafkaClustelonrPath = kafkaClustelonrPath;
    this.deloncidelonrKelony = deloncidelonrKelony;
    this.delonselonrializelonr = delonselonrializelonr;
  }

  protelonctelond KafkaConsumelonrStagelon(Delonselonrializelonr<R> delonselonrializelonr) {
    this.delonselonrializelonr = delonselonrializelonr;
  }

  @Ovelonrridelon
  protelonctelond void initStats() {
    supelonr.initStats();
    commonInnelonrSelontupStats();
  }

  privatelon void commonInnelonrSelontupStats() {
    pollCount = SelonarchCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_poll_count");
    delonselonrializationelonrrorCount =
        SelonarchCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_delonselonrialization_elonrror_count");
    droppelondMelonssagelons =
        SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_droppelond_melonssagelons");
  }

  @Ovelonrridelon
  protelonctelond void innelonrSelontupStats() {
    commonInnelonrSelontupStats();
  }

  @Ovelonrridelon
  protelonctelond void doInnelonrPrelonprocelonss() {
    commonInnelonrSelontup();
    PipelonlinelonUtil.felonelondStartObjelonctToStagelon(this);
  }

  privatelon void commonInnelonrSelontup() {
    Prelonconditions.chelonckNotNull(kafkaClielonntId);
    Prelonconditions.chelonckNotNull(kafkaClustelonrPath);
    Prelonconditions.chelonckNotNull(kafkaTopicNamelon);

    kafkaConsumelonr = wirelonModulelon.nelonwKafkaConsumelonr(
        kafkaClustelonrPath,
        delonselonrializelonr,
        kafkaClielonntId,
        kafkaConsumelonrGroupId,
        maxPollReloncords);
    if (partitionelond) {
      kafkaConsumelonr.assign(Collelonctions.singlelontonList(
          nelonw TopicPartition(kafkaTopicNamelon, wirelonModulelon.gelontPartition())));
    } elonlselon {
      kafkaConsumelonr.subscribelon(Collelonctions.singlelonton(kafkaTopicNamelon));
    }
  }

  @Ovelonrridelon
  protelonctelond void innelonrSelontup() {
    commonInnelonrSelontup();
  }

  @Ovelonrridelon
  public void innelonrProcelonss(Objelonct obj) throws Stagelonelonxcelonption {
    StagelonDrivelonr drivelonr = ((Pipelonlinelon) stagelonContelonxt).gelontStagelonDrivelonr(this);
    whilelon (drivelonr.gelontStatelon() == StagelonDrivelonr.Statelon.RUNNING) {
      pollAndelonmit();
    }

    LOG.info("StagelonDrivelonr statelon is no longelonr RUNNING, closing Kafka consumelonr.");
    closelonKafkaConsumelonr();
  }

  @VisiblelonForTelonsting
  void pollAndelonmit() throws Stagelonelonxcelonption {
    try {
      List<R> reloncords = poll();
      for (R reloncord : reloncords) {
        elonmitAndCount(reloncord);
      }
    } catch (PipelonlinelonStagelonelonxcelonption elon) {
      throw nelonw Stagelonelonxcelonption(this, elon);
    }
  }

  /***
   * Poll Kafka and gelont thelon itelonms from thelon topic. Reloncord stats.
   * @relonturn
   * @throws PipelonlinelonStagelonelonxcelonption
   */
  public List<R> pollFromTopic() throws PipelonlinelonStagelonelonxcelonption {
    long startingTimelon = startProcelonssing();
    List<R> pollelondItelonms = poll();
    elonndProcelonssing(startingTimelon);
    relonturn pollelondItelonms;
  }

  privatelon List<R> poll() throws PipelonlinelonStagelonelonxcelonption  {
    List<R> reloncordsFromKafka = nelonw ArrayList<>();
    try {
      ConsumelonrReloncords<Long, R> reloncords = kafkaConsumelonr.poll(Duration.ofMillis(pollTimelonoutMs));
      pollCount.increlonmelonnt();
      reloncords.itelonrator().forelonachRelonmaining(reloncord -> {
        if (deloncidelonrKelony == null || DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(deloncidelonr, deloncidelonrKelony)) {
          reloncordsFromKafka.add(reloncord.valuelon());
        } elonlselon {
          droppelondMelonssagelons.increlonmelonnt();
        }
      });

    } catch (Selonrializationelonxcelonption elon) {
      delonselonrializationelonrrorCount.increlonmelonnt();
      LOG.elonrror("Failelond to delonselonrializelon thelon valuelon.", elon);
    } catch (SaslAuthelonnticationelonxcelonption elon) {
      if (DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(deloncidelonr, SHUT_DOWN_ON_AUTH_FAIL)) {
        wirelonModulelon.gelontPipelonlinelonelonxcelonptionHandlelonr()
            .logAndShutdown("Authelonntication elonrror conneloncting to Kafka brokelonr: " + elon);
      } elonlselon {
        throw nelonw PipelonlinelonStagelonelonxcelonption(this, "Kafka Authelonntication elonrror", elon);
      }
    } catch (elonxcelonption elon) {
      throw nelonw PipelonlinelonStagelonelonxcelonption(elon);
    }

    relonturn reloncordsFromKafka;
  }

  @VisiblelonForTelonsting
  void closelonKafkaConsumelonr() {
    try {
      kafkaConsumelonr.closelon();
      LOG.info("Kafka kafkaConsumelonr for {} was closelond", gelontFullStagelonNamelon());
    } catch (elonxcelonption elon) {
      log.elonrror("Failelond to closelon Kafka kafkaConsumelonr", elon);
    }
  }

  @Ovelonrridelon
  public void relonlelonaselon() {
    closelonKafkaConsumelonr();
    supelonr.relonlelonaselon();
  }

  @Ovelonrridelon
  public void clelonanupStagelonV2() {
    closelonKafkaConsumelonr();
  }

  @SupprelonssWarnings("unuselond")  // selont from pipelonlinelon config
  public void selontKafkaClielonntId(String kafkaClielonntId) {
    this.kafkaClielonntId = kafkaClielonntId;
  }

  @SupprelonssWarnings("unuselond")  // selont from pipelonlinelon config
  public void selontKafkaTopicNamelon(String kafkaTopicNamelon) {
    this.kafkaTopicNamelon = kafkaTopicNamelon;
  }

  @SupprelonssWarnings("unuselond")  // selont from pipelonlinelon config
  public void selontKafkaConsumelonrGroupId(String kafkaConsumelonrGroupId) {
    this.kafkaConsumelonrGroupId = kafkaConsumelonrGroupId;
  }

  @SupprelonssWarnings("unuselond")  // selont from pipelonlinelon config
  public void selontMaxPollReloncords(int maxPollReloncords) {
    this.maxPollReloncords = maxPollReloncords;
  }

  @SupprelonssWarnings("unuselond")  // selont from pipelonlinelon config
  public void selontPollTimelonoutMs(int pollTimelonoutMs) {
    this.pollTimelonoutMs = pollTimelonoutMs;
  }

  @SupprelonssWarnings("unuselond")  // selont from pipelonlinelon config
  public void selontPartitionelond(boolelonan partitionelond) {
    this.partitionelond = partitionelond;
  }

  @SupprelonssWarnings("unuselond")  // selont from pipelonlinelon config
  public void selontDeloncidelonrKelony(String deloncidelonrKelony) {
    this.deloncidelonrKelony = deloncidelonrKelony;
  }

  @VisiblelonForTelonsting
  KafkaConsumelonr<Long, R> gelontKafkaConsumelonr() {
    relonturn kafkaConsumelonr;
  }

  @SupprelonssWarnings("unuselond")  // selont from pipelonlinelon config
  public void selontKafkaClustelonrPath(String kafkaClustelonrPath) {
    this.kafkaClustelonrPath = kafkaClustelonrPath;
  }
}
