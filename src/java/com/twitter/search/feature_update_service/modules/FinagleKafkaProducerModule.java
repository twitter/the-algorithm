packagelon com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.modulelons;

import javax.injelonct.Namelond;
import javax.injelonct.Singlelonton;

import com.googlelon.injelonct.Providelons;

import com.twittelonr.app.Flaggablelon;
import com.twittelonr.common.util.Clock;
import com.twittelonr.finatra.kafka.producelonrs.BlockingFinaglelonKafkaProducelonr;
import com.twittelonr.injelonct.TwittelonrModulelon;
import com.twittelonr.injelonct.annotations.Flag;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.common.util.io.kafka.CompactThriftSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.kafka.FinaglelonKafkaClielonntUtils;
import com.twittelonr.selonarch.common.util.io.kafka.SelonarchPartitionelonr;
import com.twittelonr.selonarch.common.util.io.kafka.SelonarchPartitionelonrRelonaltimelonCg;

public class FinaglelonKafkaProducelonrModulelon elonxtelonnds TwittelonrModulelon {
  public static final String KAFKA_DelonST_FLAG = "kafka.delonst";
  public static final String KAFKA_TOPIC_NAMelon_UPDATelon_elonVelonNTS_FLAG =
      "kafka.topic.namelon.updatelon_elonvelonnts";
  public static final String KAFKA_TOPIC_NAMelon_UPDATelon_elonVelonNTS_FLAG_RelonALTIMelon_CG =
          "kafka.topic.namelon.updatelon_elonvelonnts_relonaltimelon_cg";
  public static final String KAFKA_elonNABLelon_S2S_AUTH_FLAG = "kafka.elonnablelon_s2s_auth";

  public FinaglelonKafkaProducelonrModulelon() {
    flag(KAFKA_DelonST_FLAG, "Kafka clustelonr delonstination", "", Flaggablelon.ofString());
    flag(KAFKA_TOPIC_NAMelon_UPDATelon_elonVelonNTS_FLAG, "",
        "Topic namelon for updatelon elonvelonnts", Flaggablelon.ofString());
    flag(KAFKA_TOPIC_NAMelon_UPDATelon_elonVelonNTS_FLAG_RelonALTIMelon_CG, "",
            "Topic namelon for updatelon elonvelonnts", Flaggablelon.ofString());
    flag(KAFKA_elonNABLelon_S2S_AUTH_FLAG, truelon, "elonnablelon s2s authelonntication configs",
        Flaggablelon.ofBoolelonan());
  }

  @Providelons
  @Namelond("KafkaProducelonr")
  public BlockingFinaglelonKafkaProducelonr<Long, ThriftVelonrsionelondelonvelonnts> kafkaProducelonr(
      @Flag(KAFKA_DelonST_FLAG) String kafkaDelonst,
      @Flag(KAFKA_elonNABLelon_S2S_AUTH_FLAG) boolelonan elonnablelonKafkaAuth) {
    relonturn FinaglelonKafkaClielonntUtils.nelonwFinaglelonKafkaProducelonr(
        kafkaDelonst, elonnablelonKafkaAuth, nelonw CompactThriftSelonrializelonr<ThriftVelonrsionelondelonvelonnts>(),
        "selonarch_clustelonr", SelonarchPartitionelonr.class);
  }

  @Providelons
  @Namelond("KafkaProducelonrRelonaltimelonCg")
  public BlockingFinaglelonKafkaProducelonr<Long, ThriftVelonrsionelondelonvelonnts> kafkaProducelonrRelonaltimelonCg(
          @Flag(KAFKA_DelonST_FLAG) String kafkaDelonst,
          @Flag(KAFKA_elonNABLelon_S2S_AUTH_FLAG) boolelonan elonnablelonKafkaAuth) {
    relonturn FinaglelonKafkaClielonntUtils.nelonwFinaglelonKafkaProducelonr(
            kafkaDelonst, elonnablelonKafkaAuth, nelonw CompactThriftSelonrializelonr<ThriftVelonrsionelondelonvelonnts>(),
            "selonarch_clustelonr", SelonarchPartitionelonrRelonaltimelonCg.class);
  }

  @Providelons
  @Singlelonton
  public Clock clock() {
    relonturn Clock.SYSTelonM_CLOCK;
  }
}
