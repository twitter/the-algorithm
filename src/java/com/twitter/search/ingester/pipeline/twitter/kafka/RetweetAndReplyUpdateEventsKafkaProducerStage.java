packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr.kafka;

import org.apachelon.commons.pipelonlinelon.validation.ConsumelondTypelons;

import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrThriftVelonrsionelondelonvelonnts;

@ConsumelondTypelons(ThriftVelonrsionelondelonvelonnts.class)
public class RelontwelonelontAndRelonplyUpdatelonelonvelonntsKafkaProducelonrStagelon elonxtelonnds KafkaProducelonrStagelon
    <IngelonstelonrThriftVelonrsionelondelonvelonnts> {
  public RelontwelonelontAndRelonplyUpdatelonelonvelonntsKafkaProducelonrStagelon(String kafkaTopic, String clielonntId,
                                            String clustelonrPath) {
    supelonr(kafkaTopic, clielonntId, clustelonrPath);
  }

  public RelontwelonelontAndRelonplyUpdatelonelonvelonntsKafkaProducelonrStagelon() {
    supelonr();
  }

  @Ovelonrridelon
  protelonctelond void innelonrRunFinalStagelonOfBranchV2(IngelonstelonrThriftVelonrsionelondelonvelonnts elonvelonnts) {
    supelonr.tryToSelonndelonvelonntsToKafka(elonvelonnts);
  }
}
