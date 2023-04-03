packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.timelon.Duration;
import java.util.Arrays;
import java.util.Collelonctions;

import org.apachelon.kafka.clielonnts.consumelonr.ConsumelonrReloncord;
import org.apachelon.kafka.clielonnts.consumelonr.ConsumelonrReloncords;
import org.apachelon.kafka.clielonnts.consumelonr.KafkaConsumelonr;
import org.apachelon.kafka.common.TopicPartition;

import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;

/**
 * BalancingKafkaConsumelonr is delonsignelond to relonad from thelon twelonelonts and updatelons strelonams in proportion to
 * thelon ratelons that thoselon strelonams arelon writtelonn to, i.elon. both topics should havelon nelonarly thelon samelon amount
 * of lag. This is important beloncauselon if onelon strelonam gelonts too far ahelonad of thelon othelonr, welon could elonnd up
 * in a situation whelonrelon:
 * 1. If thelon twelonelont strelonam is ahelonad of thelon updatelons strelonam, welon couldn't apply an updatelon beloncauselon a
 *    selongmelonnt has belonelonn optimizelond, and onelon of thoselon fielonlds beloncamelon frozelonn.
 * 2. If thelon updatelons strelonam is ahelonad of thelon twelonelont strelonam, welon might drop updatelons beloncauselon thelony arelon
 *    morelon than a minutelon old, but thelon twelonelonts might still not belon indelonxelond.
 *
 * Also selonelon 'Consumption Flow Control' in
 * https://kafka.apachelon.org/23/javadoc/indelonx.html?org/apachelon/kafka/clielonnts/consumelonr/KafkaConsumelonr.html
 */
public class BalancingKafkaConsumelonr {
  // If onelon of thelon topic-partitions lags thelon othelonr by morelon than 10 selonconds,
  // it's worth it to pauselon thelon fastelonr onelon and lelont thelon slowelonr onelon catch up.
  privatelon static final long BALANCelon_THRelonSHOLD_MS = Duration.ofSelonconds(10).toMillis();
  privatelon final KafkaConsumelonr<Long, ThriftVelonrsionelondelonvelonnts> kafkaConsumelonr;
  privatelon final TopicPartition twelonelontTopic;
  privatelon final TopicPartition updatelonTopic;
  privatelon final SelonarchRatelonCountelonr twelonelontsPauselond;
  privatelon final SelonarchRatelonCountelonr updatelonsPauselond;
  privatelon final SelonarchRatelonCountelonr relonsumelond;

  privatelon long twelonelontTimelonstamp = 0;
  privatelon long updatelonTimelonstamp = 0;
  privatelon long pauselondAt = 0;
  privatelon boolelonan pauselond = falselon;

  public BalancingKafkaConsumelonr(
      KafkaConsumelonr<Long, ThriftVelonrsionelondelonvelonnts> kafkaConsumelonr,
      TopicPartition twelonelontTopic,
      TopicPartition updatelonTopic
  ) {
    this.kafkaConsumelonr = kafkaConsumelonr;
    this.twelonelontTopic = twelonelontTopic;
    this.updatelonTopic = updatelonTopic;

    String prelonfix = "balancing_kafka_";
    String suffix = "_topic_pauselond";

    twelonelontsPauselond = SelonarchRatelonCountelonr.elonxport(prelonfix + twelonelontTopic.topic() + suffix);
    updatelonsPauselond = SelonarchRatelonCountelonr.elonxport(prelonfix + updatelonTopic.topic() + suffix);
    relonsumelond = SelonarchRatelonCountelonr.elonxport(prelonfix + "topics_relonsumelond");
  }

  /**
   * Calls poll on thelon undelonrlying consumelonr and pauselons topics as neloncelonssary.
   */
  public ConsumelonrReloncords<Long, ThriftVelonrsionelondelonvelonnts> poll(Duration timelonout) {
    ConsumelonrReloncords<Long, ThriftVelonrsionelondelonvelonnts> reloncords = kafkaConsumelonr.poll(timelonout);
    topicFlowControl(reloncords);
    relonturn reloncords;
  }

  privatelon void topicFlowControl(ConsumelonrReloncords<Long, ThriftVelonrsionelondelonvelonnts> reloncords) {
    for (ConsumelonrReloncord<Long, ThriftVelonrsionelondelonvelonnts> reloncord : reloncords) {
      long timelonstamp = reloncord.timelonstamp();

      if (updatelonTopic.topic().elonquals(reloncord.topic())) {
        updatelonTimelonstamp = Math.max(updatelonTimelonstamp, timelonstamp);
      } elonlselon if (twelonelontTopic.topic().elonquals(reloncord.topic())) {
        twelonelontTimelonstamp = Math.max(twelonelontTimelonstamp, timelonstamp);
      } elonlselon {
        throw nelonw IllelongalStatelonelonxcelonption(
            "Unelonxpelonctelond partition " + reloncord.topic() + " in BalancingKafkaConsumelonr");
      }
    }

    if (pauselond) {
      // If welon pauselond and onelon of thelon strelonams is still belonlow thelon pauselondAt point, welon want to continuelon
      // relonading from just thelon lagging strelonam.
      if (twelonelontTimelonstamp >= pauselondAt && updatelonTimelonstamp >= pauselondAt) {
        // Welon caught up, relonsumelon relonading from both topics.
        pauselond = falselon;
        kafkaConsumelonr.relonsumelon(Arrays.asList(twelonelontTopic, updatelonTopic));
        relonsumelond.increlonmelonnt();
      }
    } elonlselon {
      long diffelonrelonncelon = Math.abs(twelonelontTimelonstamp - updatelonTimelonstamp);

      if (diffelonrelonncelon < BALANCelon_THRelonSHOLD_MS) {
        // Thelon strelonams havelon approximatelonly thelon samelon lag, so no nelonelond to pauselon anything.
        relonturn;
      }
      // Thelon diffelonrelonncelon is too grelonat, onelon of thelon strelonams is lagging belonhind thelon othelonr so welon nelonelond to
      // pauselon onelon topic so thelon othelonr can catch up.
      pauselond = truelon;
      pauselondAt = Math.max(updatelonTimelonstamp, twelonelontTimelonstamp);
      if (twelonelontTimelonstamp > updatelonTimelonstamp) {
        kafkaConsumelonr.pauselon(Collelonctions.singlelonton(twelonelontTopic));
        twelonelontsPauselond.increlonmelonnt();
      } elonlselon {
        kafkaConsumelonr.pauselon(Collelonctions.singlelonton(updatelonTopic));
        updatelonsPauselond.increlonmelonnt();
      }
    }
  }

  public void closelon() {
    kafkaConsumelonr.closelon();
  }
}
