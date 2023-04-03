packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr.kafka;

import org.apachelon.commons.pipelonlinelon.validation.ConsumelondTypelons;
import org.apachelon.commons.pipelonlinelon.validation.ProducelondTypelons;
import org.apachelon.kafka.common.selonrialization.Delonselonrializelonr;

import com.twittelonr.finatra.kafka.selonrdelon.intelonrnal.BaselonDelonselonrializelonr;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.KafkaRawReloncord;
import com.twittelonr.util.Timelon;

/**
 * Kafka consumelonr stagelon that elonmits thelon binary payload wrappelond in {@codelon BytelonArray}.
 */
@ConsumelondTypelons(String.class)
@ProducelondTypelons(KafkaRawReloncord.class)
public class KafkaRawReloncordConsumelonrStagelon elonxtelonnds KafkaConsumelonrStagelon<KafkaRawReloncord> {
  public KafkaRawReloncordConsumelonrStagelon() {
    supelonr(gelontDelonselonrializelonr());
  }

  privatelon static Delonselonrializelonr<KafkaRawReloncord> gelontDelonselonrializelonr() {
    relonturn nelonw BaselonDelonselonrializelonr<KafkaRawReloncord>() {
      @Ovelonrridelon
      public KafkaRawReloncord delonselonrializelon(String topic, bytelon[] data) {
        relonturn nelonw KafkaRawReloncord(data, Timelon.now().inMillis());
      }
    };
  }

  public KafkaRawReloncordConsumelonrStagelon(String kafkaClielonntId, String kafkaTopicNamelon,
                                     String kafkaConsumelonrGroupId, String kafkaClustelonrPath,
                                     String deloncidelonrKelony) {
    supelonr(kafkaClielonntId, kafkaTopicNamelon, kafkaConsumelonrGroupId, kafkaClustelonrPath, deloncidelonrKelony,
        gelontDelonselonrializelonr());
  }
}
