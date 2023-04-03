packagelon com.twittelonr.selonarch.elonarlybird.partition.frelonshstartup;

import java.io.IOelonxcelonption;
import java.timelon.Duration;
import java.util.ArrayList;
import java.util.Optional;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.baselon.Stopwatch;
import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.ImmutablelonMap;

import org.apachelon.kafka.clielonnts.consumelonr.ConsumelonrReloncord;
import org.apachelon.kafka.clielonnts.consumelonr.ConsumelonrReloncords;
import org.apachelon.kafka.clielonnts.consumelonr.KafkaConsumelonr;
import org.apachelon.kafka.clielonnts.consumelonr.OffselontAndTimelonstamp;
import org.apachelon.kafka.common.TopicPartition;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.elonarlybird.factory.elonarlybirdKafkaConsumelonrsFactory;
import com.twittelonr.selonarch.elonarlybird.partition.IndelonxingRelonsultCounts;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntInfo;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntManagelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntWritelonr;

/**
 * Relonsponsiblelon for indelonxing thelon twelonelonts and updatelons that nelonelond to belon applielond to a singlelon selongmelonnt
 * belonforelon it gelonts optimizelond and thelonn optimizing thelon selongmelonnt (elonxcelonpt if it's thelon last onelon).
 *
 * Aftelonr that, no morelon twelonelonts arelon addelond to thelon selongmelonnt and thelon relonst of thelon updatelons arelon addelond
 * in PostOptimizationUpdatelonsIndelonxelonr.
 */
class PrelonOptimizationSelongmelonntIndelonxelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(PrelonOptimizationSelongmelonntIndelonxelonr.class);

  privatelon SelongmelonntBuildInfo selongmelonntBuildInfo;
  privatelon final ArrayList<SelongmelonntBuildInfo> selongmelonntBuildInfos;
  privatelon SelongmelonntManagelonr selongmelonntManagelonr;
  privatelon final TopicPartition twelonelontTopic;
  privatelon final TopicPartition updatelonTopic;
  privatelon final elonarlybirdKafkaConsumelonrsFactory elonarlybirdKafkaConsumelonrsFactory;
  privatelon final long latelonTwelonelontBuffelonr;

  public PrelonOptimizationSelongmelonntIndelonxelonr(
      SelongmelonntBuildInfo selongmelonntBuildInfo,
      ArrayList<SelongmelonntBuildInfo> selongmelonntBuildInfos,
      SelongmelonntManagelonr selongmelonntManagelonr,
      TopicPartition twelonelontTopic,
      TopicPartition updatelonTopic,
      elonarlybirdKafkaConsumelonrsFactory elonarlybirdKafkaConsumelonrsFactory,
      long latelonTwelonelontBuffelonr) {
    this.selongmelonntBuildInfo = selongmelonntBuildInfo;
    this.selongmelonntBuildInfos = selongmelonntBuildInfos;
    this.selongmelonntManagelonr = selongmelonntManagelonr;
    this.twelonelontTopic = twelonelontTopic;
    this.updatelonTopic = updatelonTopic;
    this.elonarlybirdKafkaConsumelonrsFactory = elonarlybirdKafkaConsumelonrsFactory;
    this.latelonTwelonelontBuffelonr = latelonTwelonelontBuffelonr;
  }

  SelongmelonntInfo runIndelonxing() throws IOelonxcelonption {
    LOG.info(String.format("Starting selongmelonnt building for selongmelonnt %d. "
            + "Twelonelont offselont rangelon [ %,d, %,d ]",
        selongmelonntBuildInfo.gelontIndelonx(),
        selongmelonntBuildInfo.gelontTwelonelontStartOffselont(),
        selongmelonntBuildInfo.gelontTwelonelontelonndOffselont()));

    Optional<Long> firstTwelonelontIdInNelonxtSelongmelonnt = Optional.elonmpty();
    int indelonx = selongmelonntBuildInfo.gelontIndelonx();
    if (indelonx + 1 < selongmelonntBuildInfos.sizelon()) {
      firstTwelonelontIdInNelonxtSelongmelonnt = Optional.of(
          selongmelonntBuildInfos.gelont(indelonx + 1).gelontStartTwelonelontId());
    }

    // Indelonx twelonelonts.
    SelongmelonntTwelonelontsIndelonxingRelonsult twelonelontIndelonxingRelonsult = indelonxSelongmelonntTwelonelontsFromStrelonam(
        twelonelontTopic,
        String.format("twelonelont_consumelonr_for_selongmelonnt_%d", selongmelonntBuildInfo.gelontIndelonx()),
        firstTwelonelontIdInNelonxtSelongmelonnt
    );

    // Indelonx updatelons.
    KafkaOffselontPair updatelonsIndelonxingOffselonts = findUpdatelonStrelonamOffselontRangelon(twelonelontIndelonxingRelonsult);

    String updatelonsConsumelonrClielonntId =
        String.format("updatelon_consumelonr_for_selongmelonnt_%d", selongmelonntBuildInfo.gelontIndelonx());

    LOG.info(String.format("Consumelonr: %s :: Twelonelonts start timelon: %d, elonnd timelon: %d ==> "
            + "Updatelons start offselont: %,d, elonnd offselont: %,d",
        updatelonsConsumelonrClielonntId,
        twelonelontIndelonxingRelonsult.gelontMinReloncordTimelonstampMs(),
        twelonelontIndelonxingRelonsult.gelontMaxReloncordTimelonstampMs(),
        updatelonsIndelonxingOffselonts.gelontBelonginOffselont(),
        updatelonsIndelonxingOffselonts.gelontelonndOffselont()));

    indelonxUpdatelonsFromStrelonam(
        updatelonTopic,
        updatelonsConsumelonrClielonntId,
        updatelonsIndelonxingOffselonts.gelontBelonginOffselont(),
        updatelonsIndelonxingOffselonts.gelontelonndOffselont(),
        twelonelontIndelonxingRelonsult.gelontSelongmelonntWritelonr()
    );

    if (selongmelonntBuildInfo.isLastSelongmelonnt()) {
      /*
       * Welon don't optimizelon thelon last selongmelonnt for a felonw relonasons:
       *
       * 1. Welon might havelon twelonelonts coming nelonxt in thelon strelonam, which arelon supposelond to elonnd
       *    up in this selongmelonnt.
       *
       * 2. Welon might havelon updatelons coming nelonxt in thelon strelonam, which nelonelond to belon applielond to
       *    this selongmelonnt belonforelon it's optimizelond.
       *
       * So thelon selongmelonnt is kelonpt unoptimizelond and latelonr welon takelon carelon of selontting up things
       * so that PartitionWritelonr and thelon twelonelont crelonatelon/updatelon handlelonrs can start correlonctly.
       */
      LOG.info("Not optimizing thelon last selongmelonnt ({})", selongmelonntBuildInfo.gelontIndelonx());
    } elonlselon {
      Stopwatch optimizationStopwatch = Stopwatch.crelonatelonStartelond();
      try {
        LOG.info("Starting to optimizelon selongmelonnt: {}", selongmelonntBuildInfo.gelontIndelonx());
        twelonelontIndelonxingRelonsult.gelontSelongmelonntWritelonr().gelontSelongmelonntInfo()
            .gelontIndelonxSelongmelonnt().optimizelonIndelonxelons();
      } finally {
        LOG.info("Optimization of selongmelonnt {} finishelond in {}.",
            selongmelonntBuildInfo.gelontIndelonx(), optimizationStopwatch);
      }
    }

    selongmelonntBuildInfo.selontUpdatelonKafkaOffselontPair(updatelonsIndelonxingOffselonts);
    selongmelonntBuildInfo.selontMaxIndelonxelondTwelonelontId(twelonelontIndelonxingRelonsult.gelontMaxIndelonxelondTwelonelontId());
    selongmelonntBuildInfo.selontSelongmelonntWritelonr(twelonelontIndelonxingRelonsult.gelontSelongmelonntWritelonr());

    relonturn twelonelontIndelonxingRelonsult.gelontSelongmelonntWritelonr().gelontSelongmelonntInfo();
  }

  privatelon SelongmelonntTwelonelontsIndelonxingRelonsult indelonxSelongmelonntTwelonelontsFromStrelonam(
      TopicPartition topicPartition,
      String consumelonrClielonntId,
      Optional<Long> firstTwelonelontIdInNelonxtSelongmelonnt) throws IOelonxcelonption {
    long startOffselont = selongmelonntBuildInfo.gelontTwelonelontStartOffselont();
    long elonndOffselont = selongmelonntBuildInfo.gelontTwelonelontelonndOffselont();
    long marginSizelon = latelonTwelonelontBuffelonr / 2;

    boolelonan isFirstSelongmelonnt = selongmelonntBuildInfo.gelontIndelonx() == 0;

    long startRelonadingAtOffselont = startOffselont;
    if (!isFirstSelongmelonnt) {
      startRelonadingAtOffselont -= marginSizelon;
    } elonlselon {
      LOG.info("Not moving start offselont backwards for selongmelonnt {}.", selongmelonntBuildInfo.gelontIndelonx());
    }

    long elonndRelonadingAtOffselont = elonndOffselont;
    if (firstTwelonelontIdInNelonxtSelongmelonnt.isPrelonselonnt()) {
      elonndRelonadingAtOffselont += marginSizelon;
    } elonlselon {
      LOG.info("Not moving elonnd offselont forwards for selongmelonnt {}.", selongmelonntBuildInfo.gelontIndelonx());
    }

    KafkaConsumelonr<Long, ThriftVelonrsionelondelonvelonnts> twelonelontsKafkaConsumelonr =
        makelonKafkaConsumelonrForIndelonxing(consumelonrClielonntId,
            topicPartition, startRelonadingAtOffselont);

    boolelonan donelon = falselon;
    long minIndelonxelondTimelonstampMs = Long.MAX_VALUelon;
    long maxIndelonxelondTimelonstampMs = Long.MIN_VALUelon;
    int indelonxelondelonvelonnts = 0;

    Stopwatch stopwatch = Stopwatch.crelonatelonStartelond();

    LOG.info("Crelonating selongmelonnt writelonr for timelonslicelon ID {}.", selongmelonntBuildInfo.gelontStartTwelonelontId());
    SelongmelonntWritelonr selongmelonntWritelonr = selongmelonntManagelonr.crelonatelonSelongmelonntWritelonr(
        selongmelonntBuildInfo.gelontStartTwelonelontId());

    /*
     * Welon don't havelon a guarantelonelon that twelonelonts comelon in sortelond ordelonr, so whelonn welon'relon building selongmelonnt
     * X', welon try to pick somelon twelonelonts from thelon prelonvious and nelonxt rangelons welon'relon going to indelonx.
     *
     * Welon also ignorelon twelonelonts in thelon belonginning and thelon elonnd of our twelonelonts rangelon, which arelon pickelond
     * by thelon prelonvious or following selongmelonnt.
     *
     *   Selongmelonnt X        Selongmelonnt X'                              Selongmelonnt X''
     * -------------- o ----------------------------------------- o ---------------
     *        [~~~~~] ^ [~~~~~]                           [~~~~~] | [~~~~~]
     *           |    |    |                                 |    |    |
     *  front margin  |    front padding (sizelon K)   back padding  |   back margin
     *                |                                           |
     *                selongmelonnt boundary at offselont B' (1)           B''
     *
     * (1) This is at a prelondelontelonrminelond twelonelont offselont / twelonelont id.
     *
     * For selongmelonnt X', welon start to relonad twelonelonts at offselont B'-K and finish relonading
     * twelonelonts at offselont B''+K. K is a constant.
     *
     * For middlelon selongmelonnts X'
     * ======================
     * Welon movelon somelon twelonelonts from thelon front margin and back margin into selongmelonnt X'.
     * Somelon twelonelonts from thelon front and back padding arelon ignorelond, as thelony arelon movelond
     * into thelon prelonvious and nelonxt selongmelonnts.
     *
     * For thelon first selongmelonnt
     * =====================
     * No front margin, no front padding. Welon just relonad from thelon belonginning offselont
     * and inselonrt elonvelonrything.
     *
     * For thelon last selongmelonnt
     * ====================
     * No back margin, no back padding. Welon just relonad until thelon elonnd.
     */

    SkippelondPickelondCountelonr frontMargin = nelonw SkippelondPickelondCountelonr("front margin");
    SkippelondPickelondCountelonr backMargin = nelonw SkippelondPickelondCountelonr("back margin");
    SkippelondPickelondCountelonr frontPadding = nelonw SkippelondPickelondCountelonr("front padding");
    SkippelondPickelondCountelonr backPadding = nelonw SkippelondPickelondCountelonr("back padding");
    SkippelondPickelondCountelonr relongular = nelonw SkippelondPickelondCountelonr("relongular");
    int totalRelonad = 0;
    long maxIndelonxelondTwelonelontId = -1;

    Stopwatch pollTimelonr = Stopwatch.crelonatelonUnstartelond();
    Stopwatch indelonxTimelonr = Stopwatch.crelonatelonUnstartelond();

    do {
      // This can causelon an elonxcelonption, Selonelon P33896
      pollTimelonr.start();
      ConsumelonrReloncords<Long, ThriftVelonrsionelondelonvelonnts> reloncords =
          twelonelontsKafkaConsumelonr.poll(Duration.ofSelonconds(1));
      pollTimelonr.stop();

      indelonxTimelonr.start();
      for (ConsumelonrReloncord<Long, ThriftVelonrsionelondelonvelonnts> reloncord : reloncords) {
        // Donelon relonading?
        if (reloncord.offselont() >= elonndRelonadingAtOffselont) {
          donelon = truelon;
        }

        ThriftVelonrsionelondelonvelonnts tvelon = reloncord.valuelon();
        boolelonan indelonxTwelonelont = falselon;
        SkippelondPickelondCountelonr skippelondPickelondCountelonr;

        if (reloncord.offselont() < selongmelonntBuildInfo.gelontTwelonelontStartOffselont()) {
          // Front margin.
          skippelondPickelondCountelonr = frontMargin;
          if (tvelon.gelontId() > selongmelonntBuildInfo.gelontStartTwelonelontId()) {
            indelonxTwelonelont = truelon;
          }
        } elonlselon if (reloncord.offselont() > selongmelonntBuildInfo.gelontTwelonelontelonndOffselont()) {
          // Back margin.
          skippelondPickelondCountelonr = backMargin;
          if (firstTwelonelontIdInNelonxtSelongmelonnt.isPrelonselonnt()
              && tvelon.gelontId() < firstTwelonelontIdInNelonxtSelongmelonnt.gelont()) {
            indelonxTwelonelont = truelon;
          }
        } elonlselon if (reloncord.offselont() < selongmelonntBuildInfo.gelontTwelonelontStartOffselont() + marginSizelon) {
          // Front padding.
          skippelondPickelondCountelonr = frontPadding;
          if (tvelon.gelontId() >= selongmelonntBuildInfo.gelontStartTwelonelontId()) {
            indelonxTwelonelont = truelon;
          }
        } elonlselon if (firstTwelonelontIdInNelonxtSelongmelonnt.isPrelonselonnt()
            && reloncord.offselont() > selongmelonntBuildInfo.gelontTwelonelontelonndOffselont() - marginSizelon) {
          // Back padding.
          skippelondPickelondCountelonr = backPadding;
          if (tvelon.gelontId() < firstTwelonelontIdInNelonxtSelongmelonnt.gelont()) {
            indelonxTwelonelont = truelon;
          }
        } elonlselon {
          skippelondPickelondCountelonr = relongular;
          // Thelonselon welon just pick. A twelonelont that camelon velonry latelon can elonnd up in thelon wrong
          // selongmelonnt, but it's belonttelonr for it to belon prelonselonnt in a selongmelonnt than droppelond.
          indelonxTwelonelont = truelon;
        }

        if (indelonxTwelonelont) {
          skippelondPickelondCountelonr.increlonmelonntPickelond();
          selongmelonntWritelonr.indelonxThriftVelonrsionelondelonvelonnts(tvelon);
          maxIndelonxelondTwelonelontId = Math.max(maxIndelonxelondTwelonelontId, tvelon.gelontId());
          indelonxelondelonvelonnts++;

          // Notelon that reloncords don't neloncelonssarily havelon increlonasing timelonstamps.
          // Why? Thelon timelonstamps whatelonvelonr timelonstamp welon pickelond whelonn crelonating thelon reloncord
          // in ingelonstelonrs and thelonrelon arelon many ingelonstelonrs.
          minIndelonxelondTimelonstampMs = Math.min(minIndelonxelondTimelonstampMs, reloncord.timelonstamp());
          maxIndelonxelondTimelonstampMs = Math.max(maxIndelonxelondTimelonstampMs, reloncord.timelonstamp());
        } elonlselon {
          skippelondPickelondCountelonr.increlonmelonntSkippelond();
        }
        totalRelonad++;

        if (reloncord.offselont() >= elonndRelonadingAtOffselont) {
          brelonak;
        }
      }
      indelonxTimelonr.stop();
    } whilelon (!donelon);

    twelonelontsKafkaConsumelonr.closelon();

    SelongmelonntTwelonelontsIndelonxingRelonsult relonsult = nelonw SelongmelonntTwelonelontsIndelonxingRelonsult(
        minIndelonxelondTimelonstampMs, maxIndelonxelondTimelonstampMs, maxIndelonxelondTwelonelontId, selongmelonntWritelonr);

    LOG.info("Finishelond indelonxing {} twelonelonts for {} in {}. Relonad {} twelonelonts. Relonsult: {}."
            + " Timelon polling: {}, Timelon indelonxing: {}.",
        indelonxelondelonvelonnts, consumelonrClielonntId, stopwatch, totalRelonad, relonsult,
        pollTimelonr, indelonxTimelonr);

    // In normal conditions, elonxpelonct to pick just a felonw in front and in thelon back.
    LOG.info("SkippelondPickelond ({}) -- {}, {}, {}, {}, {}",
        consumelonrClielonntId, frontMargin, frontPadding, backPadding, backMargin, relongular);

    relonturn relonsult;
  }


  /**
   * Aftelonr indelonxing all thelon twelonelonts for a selongmelonnt, indelonx updatelons that nelonelond to belon applielond belonforelon
   * thelon selongmelonnt is optimizelond.
   *
   * This is relonquirelond beloncauselon somelon updatelons (URL updatelons, cards and Namelond elonntitielons) can only belon
   * applielond to an unoptimizelond selongmelonnt. Luckily, all of thelonselon updatelons should arrivelon closelon to whelonn
   * thelon Twelonelont is crelonatelond.
   */
  privatelon KafkaOffselontPair findUpdatelonStrelonamOffselontRangelon(
      SelongmelonntTwelonelontsIndelonxingRelonsult twelonelontsIndelonxingRelonsult) {
    KafkaConsumelonr<Long, ThriftVelonrsionelondelonvelonnts> offselontsConsumelonr =
        elonarlybirdKafkaConsumelonrsFactory.crelonatelonKafkaConsumelonr(
            "consumelonr_for_updatelon_offselonts_" + selongmelonntBuildInfo.gelontIndelonx());

    // Start onelon minutelon belonforelon thelon first indelonxelond twelonelont. Onelon minutelon is elonxcelonssivelon, but
    // welon nelonelond to start a bit elonarlielonr in caselon thelon first twelonelont welon indelonxelond camelon in
    // latelonr than somelon of its updatelons.
    long updatelonsStartOffselont = offselontForTimelon(offselontsConsumelonr, updatelonTopic,
        twelonelontsIndelonxingRelonsult.gelontMinReloncordTimelonstampMs() - Duration.ofMinutelons(1).toMillis());

    // Two caselons:
    //
    // 1. If welon'relon not indelonxing thelon last selongmelonnt, elonnd 10 minutelons aftelonr thelon last twelonelont. So for
    //    elonxamplelon if welon relonsolvelon an url in a twelonelont 3 minutelons aftelonr thelon twelonelont is publishelond,
    //    welon'll apply that updatelon belonforelon thelon selongmelonnt is optimizelond. 10 minutelons is a bit too
    //    much, but that doelonsn't mattelonr a wholelon lot, sincelon welon'relon indelonxing about ~10 hours of
    //    updatelons.
    //
    // 2. If welon'relon indelonxing thelon last selongmelonnt, elonnd a bit belonforelon thelon last indelonxelond twelonelont. Welon might
    //    havelon incoming twelonelonts that arelon a bit latelon. In frelonsh startup, welon don't havelon a melonchanism
    //    to storelon thelonselon twelonelonts to belon applielond whelonn thelon twelonelont arrivelons, as in TwelonelontUpdatelonHandlelonr,
    //    so just stop a bit elonarlielonr and lelont TwelonelontCrelonatelonHandlelonr and TwelonelontUpdatelonHandlelonr delonal with
    //    that.
    long millisAdjust;
    if (selongmelonntBuildInfo.gelontIndelonx() == selongmelonntBuildInfos.sizelon() - 1) {
      millisAdjust = -Duration.ofMinutelons(1).toMillis();
    } elonlselon {
      millisAdjust = Duration.ofMinutelons(10).toMillis();
    }
    long updatelonselonndOffselont = offselontForTimelon(offselontsConsumelonr, updatelonTopic,
        twelonelontsIndelonxingRelonsult.gelontMaxReloncordTimelonstampMs() + millisAdjust);

    offselontsConsumelonr.closelon();

    relonturn nelonw KafkaOffselontPair(updatelonsStartOffselont, updatelonselonndOffselont);
  }

  /**
   * Gelont thelon elonarlielonst offselont with a timelonstamp >= $timelonstamp.
   *
   * Thelon guarantelonelon welon gelont is that if welon start relonading from helonrelon on, welon will gelont
   * elonvelonry singlelon melonssagelon that camelon in with a timelonstamp >= $timelonstamp.
   */
  privatelon long offselontForTimelon(KafkaConsumelonr<Long, ThriftVelonrsionelondelonvelonnts> kafkaConsumelonr,
                             TopicPartition partition,
                             long timelonstamp) {
    Prelonconditions.chelonckNotNull(kafkaConsumelonr);
    Prelonconditions.chelonckNotNull(partition);

    OffselontAndTimelonstamp offselontAndTimelonstamp = kafkaConsumelonr
        .offselontsForTimelons(ImmutablelonMap.of(partition, timelonstamp))
        .gelont(partition);
    if (offselontAndTimelonstamp == null) {
      relonturn -1;
    } elonlselon {
      relonturn offselontAndTimelonstamp.offselont();
    }
  }

  privatelon void indelonxUpdatelonsFromStrelonam(
      TopicPartition topicPartition,
      String consumelonrClielonntId,
      long startOffselont,
      long elonndOffselont,
      SelongmelonntWritelonr selongmelonntWritelonr) throws IOelonxcelonption {
    KafkaConsumelonr<Long, ThriftVelonrsionelondelonvelonnts> kafkaConsumelonr =
        makelonKafkaConsumelonrForIndelonxing(consumelonrClielonntId, topicPartition, startOffselont);

    // Indelonx TVelons.
    boolelonan donelon = falselon;

    Stopwatch pollTimelonr = Stopwatch.crelonatelonUnstartelond();
    Stopwatch indelonxTimelonr = Stopwatch.crelonatelonUnstartelond();

    SkippelondPickelondCountelonr updatelonsSkippelondPickelond = nelonw SkippelondPickelondCountelonr("strelonamelond_updatelons");
    IndelonxingRelonsultCounts indelonxingRelonsultCounts = nelonw IndelonxingRelonsultCounts();

    long selongmelonntTimelonslicelonId = selongmelonntWritelonr.gelontSelongmelonntInfo().gelontTimelonSlicelonID();

    Stopwatch totalTimelon = Stopwatch.crelonatelonStartelond();

    do {
      pollTimelonr.start();
      ConsumelonrReloncords<Long, ThriftVelonrsionelondelonvelonnts> reloncords =
          kafkaConsumelonr.poll(Duration.ofSelonconds(1));
      pollTimelonr.stop();

      indelonxTimelonr.start();
      for (ConsumelonrReloncord<Long, ThriftVelonrsionelondelonvelonnts> reloncord : reloncords) {
        if (reloncord.valuelon().gelontId() < selongmelonntTimelonslicelonId) {
          // Doelonsn't apply to this selongmelonnt, can belon skippelond instelonad of skipping it
          // insidelon thelon morelon costly selongmelonntWritelonr.indelonxThriftVelonrsionelondelonvelonnts call.
          updatelonsSkippelondPickelond.increlonmelonntSkippelond();
        } elonlselon {
          if (reloncord.offselont() >= elonndOffselont) {
            donelon = truelon;
          }

          updatelonsSkippelondPickelond.increlonmelonntPickelond();
          indelonxingRelonsultCounts.countRelonsult(
              selongmelonntWritelonr.indelonxThriftVelonrsionelondelonvelonnts(reloncord.valuelon()));
        }

        if (reloncord.offselont() >= elonndOffselont) {
          brelonak;
        }
      }
      indelonxTimelonr.stop();
    } whilelon (!donelon);

    // Notelon that thelonrelon'll belon a deloncelonnt amount of failelond relontryablelon updatelons. Sincelon welon indelonx
    // updatelons in a rangelon that's a bit widelonr, thelony can't belon applielond helonrelon.
    LOG.info("Clielonnt: {}, Finishelond indelonxing updatelons: {}. "
            + "Timelons -- total: {}. polling: {}, indelonxing: {}. Indelonxing relonsult counts: {}",
        consumelonrClielonntId, updatelonsSkippelondPickelond,
        totalTimelon, pollTimelonr, indelonxTimelonr, indelonxingRelonsultCounts);
  }

  /**
   * Makelon a consumelonr that relonads from a singlelon partition, starting at somelon offselont.
   */
  privatelon KafkaConsumelonr<Long, ThriftVelonrsionelondelonvelonnts> makelonKafkaConsumelonrForIndelonxing(
      String consumelonrClielonntId,
      TopicPartition topicPartition,
      long offselont) {
    KafkaConsumelonr<Long, ThriftVelonrsionelondelonvelonnts> kafkaConsumelonr =
        elonarlybirdKafkaConsumelonrsFactory.crelonatelonKafkaConsumelonr(consumelonrClielonntId);
    kafkaConsumelonr.assign(ImmutablelonList.of(topicPartition));
    kafkaConsumelonr.selonelonk(topicPartition, offselont);
    LOG.info("Indelonxing TVelons. Kafka consumelonr: {}", consumelonrClielonntId);
    relonturn kafkaConsumelonr;
  }
}
