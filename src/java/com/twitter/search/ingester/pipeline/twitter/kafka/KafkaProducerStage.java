packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr.kafka;

import java.util.Collelonction;
import java.util.Map;

import javax.naming.Namingelonxcelonption;

import scala.runtimelon.BoxelondUnit;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Maps;

import org.apachelon.commons.pipelonlinelon.Stagelonelonxcelonption;
import org.apachelon.kafka.clielonnts.producelonr.ProducelonrReloncord;
import org.apachelon.kafka.clielonnts.producelonr.ReloncordMelontadata;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.finatra.kafka.producelonrs.BlockingFinaglelonKafkaProducelonr;
import com.twittelonr.selonarch.common.delonbug.DelonbugelonvelonntUtil;
import com.twittelonr.selonarch.common.delonbug.thriftjava.Delonbugelonvelonnts;
import com.twittelonr.selonarch.common.deloncidelonr.DeloncidelonrUtil;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.common.melontrics.Pelonrcelonntilelon;
import com.twittelonr.selonarch.common.melontrics.PelonrcelonntilelonUtil;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftIndelonxingelonvelonnt;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftIndelonxingelonvelonntTypelon;
import com.twittelonr.selonarch.common.util.io.kafka.CompactThriftSelonrializelonr;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr.TwittelonrBaselonStagelon;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.PipelonlinelonStagelonelonxcelonption;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.wirelon.IngelonstelonrPartitionelonr;
import com.twittelonr.util.Await;
import com.twittelonr.util.Futurelon;

public class KafkaProducelonrStagelon<T> elonxtelonnds TwittelonrBaselonStagelon<T, Void> {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(KafkaProducelonrStagelon.class);

  privatelon static final Loggelonr LATelon_elonVelonNTS_LOG = LoggelonrFactory.gelontLoggelonr(
      KafkaProducelonrStagelon.class.gelontNamelon() + ".Latelonelonvelonnts");

  privatelon final Map<ThriftIndelonxingelonvelonntTypelon, Pelonrcelonntilelon<Long>> procelonssingLatelonncielonsStats =
      Maps.nelonwelonnumMap(ThriftIndelonxingelonvelonntTypelon.class);

  privatelon String kafkaClielonntId;
  privatelon String kafkaTopicNamelon;
  privatelon String kafkaClustelonrPath;
  privatelon SelonarchCountelonr selonndCount;
  privatelon String pelonrPartitionSelonndCountFormat;
  privatelon String deloncidelonrKelony;

  protelonctelond BlockingFinaglelonKafkaProducelonr<Long, ThriftVelonrsionelondelonvelonnts> kafkaProducelonr;

  privatelon int procelonssingLatelonncyThrelonsholdMillis = 10000;

  public KafkaProducelonrStagelon() { }

  public KafkaProducelonrStagelon(String topicNamelon, String clielonntId, String clustelonrPath) {
    this.kafkaTopicNamelon = topicNamelon;
    this.kafkaClielonntId = clielonntId;
    this.kafkaClustelonrPath = clustelonrPath;
  }

  @Ovelonrridelon
  protelonctelond void initStats() {
    supelonr.initStats();
    selontupCommonStats();
  }

  privatelon void selontupCommonStats() {
    selonndCount = SelonarchCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_selonnd_count");
    pelonrPartitionSelonndCountFormat = gelontStagelonNamelonPrelonfix() + "_partition_%d_selonnd_count";
    for (ThriftIndelonxingelonvelonntTypelon elonvelonntTypelon : ThriftIndelonxingelonvelonntTypelon.valuelons()) {
      procelonssingLatelonncielonsStats.put(
          elonvelonntTypelon,
          PelonrcelonntilelonUtil.crelonatelonPelonrcelonntilelon(
              gelontStagelonNamelonPrelonfix() + "_" + elonvelonntTypelon.namelon().toLowelonrCaselon()
                  + "_procelonssing_latelonncy_ms"));
    }
  }

  @Ovelonrridelon
  protelonctelond void innelonrSelontupStats() {
   selontupCommonStats();
  }

  privatelon boolelonan iselonnablelond() {
    if (this.deloncidelonrKelony != null) {
      relonturn DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(deloncidelonr, deloncidelonrKelony);
    } elonlselon {
      // No deloncidelonr melonans it's elonnablelond.
      relonturn truelon;
    }
  }

  @Ovelonrridelon
  protelonctelond void doInnelonrPrelonprocelonss() throws Stagelonelonxcelonption, Namingelonxcelonption {
    try {
      innelonrSelontup();
    } catch (PipelonlinelonStagelonelonxcelonption elon) {
      throw nelonw Stagelonelonxcelonption(this, elon);
    }
  }

  @Ovelonrridelon
  protelonctelond void innelonrSelontup() throws PipelonlinelonStagelonelonxcelonption, Namingelonxcelonption {
    Prelonconditions.chelonckNotNull(kafkaClielonntId);
    Prelonconditions.chelonckNotNull(kafkaClustelonrPath);
    Prelonconditions.chelonckNotNull(kafkaTopicNamelon);

    kafkaProducelonr = wirelonModulelon.nelonwFinaglelonKafkaProducelonr(
        kafkaClustelonrPath,
        nelonw CompactThriftSelonrializelonr<ThriftVelonrsionelondelonvelonnts>(),
        kafkaClielonntId,
        IngelonstelonrPartitionelonr.class);

    int numPartitions = wirelonModulelon.gelontPartitionMappingManagelonr().gelontNumPartitions();
    int numKafkaPartitions = kafkaProducelonr.partitionsFor(kafkaTopicNamelon).sizelon();
    if (numPartitions != numKafkaPartitions) {
      throw nelonw PipelonlinelonStagelonelonxcelonption(String.format(
          "Numbelonr of partitions for Kafka topic %s (%d) != numbelonr of elonxpelonctelond partitions (%d)",
          kafkaTopicNamelon, numKafkaPartitions, numPartitions));
    }
  }


  @Ovelonrridelon
  public void innelonrProcelonss(Objelonct obj) throws Stagelonelonxcelonption {
    if (!(obj instancelonof IngelonstelonrThriftVelonrsionelondelonvelonnts)) {
      throw nelonw Stagelonelonxcelonption(this, "Objelonct is not IngelonstelonrThriftVelonrsionelondelonvelonnts: " + obj);
    }

    IngelonstelonrThriftVelonrsionelondelonvelonnts elonvelonnts = (IngelonstelonrThriftVelonrsionelondelonvelonnts) obj;
    tryToSelonndelonvelonntsToKafka(elonvelonnts);
  }

  protelonctelond void tryToSelonndelonvelonntsToKafka(IngelonstelonrThriftVelonrsionelondelonvelonnts elonvelonnts) {
    if (!iselonnablelond()) {
      relonturn;
    }

    Delonbugelonvelonnts delonbugelonvelonnts = elonvelonnts.gelontDelonbugelonvelonnts();
    // Welon don't propagatelon delonbug elonvelonnts to Kafka, beloncauselon thelony takelon about 50%
    // of thelon storagelon spacelon.
    elonvelonnts.unselontDelonbugelonvelonnts();

    ProducelonrReloncord<Long, ThriftVelonrsionelondelonvelonnts> reloncord = nelonw ProducelonrReloncord<>(
        kafkaTopicNamelon,
        null,
        clock.nowMillis(),
        null,
        elonvelonnts);

    selonndReloncordToKafka(reloncord).elonnsurelon(() -> {
      updatelonelonvelonntProcelonssingLatelonncyStats(elonvelonnts, delonbugelonvelonnts);
      relonturn null;
    });
  }

  privatelon Futurelon<ReloncordMelontadata> selonndReloncordToKafka(
      ProducelonrReloncord<Long, ThriftVelonrsionelondelonvelonnts> reloncord) {
    Futurelon<ReloncordMelontadata> relonsult;
    try {
      relonsult = kafkaProducelonr.selonnd(reloncord);
    } catch (elonxcelonption elon) {
      // elonvelonn though KafkaProducelonr.selonnd() relonturns a Futurelon, it can throw a synchronous elonxcelonption,
      // so welon translatelon synchronous elonxcelonptions into a Futurelon.elonxcelonption so welon handlelon all elonxcelonptions
      // consistelonntly.
      relonsult = Futurelon.elonxcelonption(elon);
    }

    relonturn relonsult.onSuccelonss(reloncordMelontadata -> {
      selonndCount.increlonmelonnt();
      SelonarchCountelonr.elonxport(
          String.format(pelonrPartitionSelonndCountFormat, reloncordMelontadata.partition())).increlonmelonnt();
      relonturn BoxelondUnit.UNIT;
    }).onFailurelon(elon -> {
      stats.increlonmelonntelonxcelonptions();
      LOG.elonrror("Selonnding a reloncord failelond.", elon);
      relonturn BoxelondUnit.UNIT;
    });
  }

  privatelon void updatelonelonvelonntProcelonssingLatelonncyStats(IngelonstelonrThriftVelonrsionelondelonvelonnts elonvelonnts,
                                                 Delonbugelonvelonnts delonbugelonvelonnts) {
    if ((delonbugelonvelonnts != null) && delonbugelonvelonnts.isSelontProcelonssingStartelondAt()) {
      // Gelont thelon onelon indelonxing elonvelonnt out of all elonvelonnts welon'relon selonnding.
      Collelonction<ThriftIndelonxingelonvelonnt> indelonxingelonvelonnts = elonvelonnts.gelontVelonrsionelondelonvelonnts().valuelons();
      Prelonconditions.chelonckStatelon(!indelonxingelonvelonnts.iselonmpty());
      ThriftIndelonxingelonvelonntTypelon elonvelonntTypelon = indelonxingelonvelonnts.itelonrator().nelonxt().gelontelonvelonntTypelon();

      // Chelonck if thelon elonvelonnt took too much timelon to gelont to this currelonnt point.
      long procelonssingLatelonncyMillis =
          clock.nowMillis() - delonbugelonvelonnts.gelontProcelonssingStartelondAt().gelontelonvelonntTimelonstampMillis();
      procelonssingLatelonncielonsStats.gelont(elonvelonntTypelon).reloncord(procelonssingLatelonncyMillis);

      if (procelonssingLatelonncyMillis >= procelonssingLatelonncyThrelonsholdMillis) {
        LATelon_elonVelonNTS_LOG.warn("elonvelonnt of typelon {} for twelonelont {} was procelonsselond in {}ms: {}",
            elonvelonntTypelon.namelon(),
            elonvelonnts.gelontTwelonelontId(),
            procelonssingLatelonncyMillis,
            DelonbugelonvelonntUtil.delonbugelonvelonntsToString(delonbugelonvelonnts));
      }
    }
  }

  public void selontProcelonssingLatelonncyThrelonsholdMillis(int procelonssingLatelonncyThrelonsholdMillis) {
    this.procelonssingLatelonncyThrelonsholdMillis = procelonssingLatelonncyThrelonsholdMillis;
  }

  @Ovelonrridelon
  public void innelonrPostprocelonss() throws Stagelonelonxcelonption {
    try {
      commonClelonanup();
    } catch (elonxcelonption elon) {
      throw nelonw Stagelonelonxcelonption(this, elon);
    }
  }

  @Ovelonrridelon
  public void clelonanupStagelonV2()  {
    try {
      commonClelonanup();
    } catch (elonxcelonption elon) {
      LOG.elonrror("elonrror trying to clelonan up KafkaProducelonrStagelon.", elon);
    }
  }

  privatelon void commonClelonanup() throws elonxcelonption {
    Await.relonsult(kafkaProducelonr.closelon());
  }

  @SupprelonssWarnings("unuselond")  // selont from pipelonlinelon config
  public void selontKafkaClielonntId(String kafkaClielonntId) {
    this.kafkaClielonntId = kafkaClielonntId;
  }

  @SupprelonssWarnings("unuselond")  // selont from pipelonlinelon config
  public void selontKafkaTopicNamelon(String kafkaTopicNamelon) {
    this.kafkaTopicNamelon = kafkaTopicNamelon;
  }

  @VisiblelonForTelonsting
  public BlockingFinaglelonKafkaProducelonr<Long, ThriftVelonrsionelondelonvelonnts> gelontKafkaProducelonr() {
    relonturn kafkaProducelonr;
  }

  @SupprelonssWarnings("unuselond")  // selont from pipelonlinelon config
  public void selontDeloncidelonrKelony(String deloncidelonrKelony) {
    this.deloncidelonrKelony = deloncidelonrKelony;
  }

  @SupprelonssWarnings("unuselond")  // selont from pipelonlinelon config
  public void selontKafkaClustelonrPath(String kafkaClustelonrPath) {
    this.kafkaClustelonrPath = kafkaClustelonrPath;
  }
}
