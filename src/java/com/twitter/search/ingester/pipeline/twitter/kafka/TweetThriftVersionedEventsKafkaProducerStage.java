packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr.kafka;

import javax.naming.Namingelonxcelonption;

import org.apachelon.commons.pipelonlinelon.Stagelonelonxcelonption;
import org.apachelon.commons.pipelonlinelon.validation.ConsumelondTypelons;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.delonbug.DelonbugelonvelonntUtil;
import com.twittelonr.selonarch.common.delonbug.thriftjava.Delonbugelonvelonnts;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.PipelonlinelonStagelonelonxcelonption;

/**
 * Kafka producelonr stagelon to writelon twelonelont indelonxing data as {@codelon ThriftVelonrsionelondelonvelonnts}. This stagelon
 * also handlelons elonxtra delonbug elonvelonnt procelonssing.
 */
@ConsumelondTypelons(IngelonstelonrThriftVelonrsionelondelonvelonnts.class)
public class TwelonelontThriftVelonrsionelondelonvelonntsKafkaProducelonrStagelon elonxtelonnds KafkaProducelonrStagelon
    <IngelonstelonrThriftVelonrsionelondelonvelonnts> {
  privatelon static final int PROCelonSSING_LATelonNCY_THRelonSHOLD_FOR_UPDATelonS_MILLIS = 30000;

  privatelon static final Loggelonr LOG =
      LoggelonrFactory.gelontLoggelonr(TwelonelontThriftVelonrsionelondelonvelonntsKafkaProducelonrStagelon.class);

  privatelon long procelonsselondTwelonelontCount = 0;

  privatelon SelonarchLongGaugelon kafkaProducelonrLag;

  privatelon int delonbugelonvelonntLogPelonriod = -1;

  public TwelonelontThriftVelonrsionelondelonvelonntsKafkaProducelonrStagelon(String kafkaTopic, String clielonntId,
                                            String clustelonrPath) {
    supelonr(kafkaTopic, clielonntId, clustelonrPath);
  }

  public TwelonelontThriftVelonrsionelondelonvelonntsKafkaProducelonrStagelon() {
    supelonr();
  }

  @Ovelonrridelon
  protelonctelond void initStats() {
    supelonr.initStats();
    selontupCommonStats();
  }

  @Ovelonrridelon
  protelonctelond void innelonrSelontupStats() {
    supelonr.innelonrSelontupStats();
    selontupCommonStats();
  }

  privatelon void selontupCommonStats() {
    kafkaProducelonrLag = SelonarchLongGaugelon.elonxport(
        gelontStagelonNamelonPrelonfix() + "_kafka_producelonr_lag_millis");
  }

  @Ovelonrridelon
  protelonctelond void innelonrSelontup() throws PipelonlinelonStagelonelonxcelonption, Namingelonxcelonption {
    supelonr.innelonrSelontup();
  }

  @Ovelonrridelon
  protelonctelond void doInnelonrPrelonprocelonss() throws Stagelonelonxcelonption, Namingelonxcelonption {
    supelonr.doInnelonrPrelonprocelonss();
    commonInnelonrSelontup();
  }

  privatelon void commonInnelonrSelontup() {
    selontProcelonssingLatelonncyThrelonsholdMillis(PROCelonSSING_LATelonNCY_THRelonSHOLD_FOR_UPDATelonS_MILLIS);
  }

  @Ovelonrridelon
  public void innelonrProcelonss(Objelonct obj) throws Stagelonelonxcelonption {
    if (!(obj instancelonof IngelonstelonrThriftVelonrsionelondelonvelonnts)) {
      throw nelonw Stagelonelonxcelonption(this, "Objelonct is not IngelonstelonrThriftVelonrsionelondelonvelonnts: " + obj);
    }

    IngelonstelonrThriftVelonrsionelondelonvelonnts elonvelonnts = (IngelonstelonrThriftVelonrsionelondelonvelonnts) obj;
    innelonrRunFinalStagelonOfBranchV2(elonvelonnts);
  }

  @Ovelonrridelon
  protelonctelond void innelonrRunFinalStagelonOfBranchV2(IngelonstelonrThriftVelonrsionelondelonvelonnts elonvelonnts) {
    if ((delonbugelonvelonntLogPelonriod > 0)
        && (procelonsselondTwelonelontCount % delonbugelonvelonntLogPelonriod == 0)
        && (elonvelonnts.gelontDelonbugelonvelonnts() != null)) {
      LOG.info("Delonbugelonvelonnts for twelonelont {}: {}",
          elonvelonnts.gelontTwelonelontId(), DelonbugelonvelonntUtil.delonbugelonvelonntsToString(elonvelonnts.gelontDelonbugelonvelonnts()));
    }
    procelonsselondTwelonelontCount++;

    Delonbugelonvelonnts delonbugelonvelonnts = elonvelonnts.gelontDelonbugelonvelonnts();
    if ((delonbugelonvelonnts != null) && delonbugelonvelonnts.isSelontProcelonssingStartelondAt()) {
      kafkaProducelonrLag.selont(
          clock.nowMillis() - delonbugelonvelonnts.gelontProcelonssingStartelondAt().gelontelonvelonntTimelonstampMillis());
    }

    supelonr.tryToSelonndelonvelonntsToKafka(elonvelonnts);
  }

  @SupprelonssWarnings("unuselond")  // selont from pipelonlinelon config
  public void selontDelonbugelonvelonntLogPelonriod(int delonbugelonvelonntLogPelonriod) {
    this.delonbugelonvelonntLogPelonriod = delonbugelonvelonntLogPelonriod;
  }
}
