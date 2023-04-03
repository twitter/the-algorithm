/**
 * &copy; Copyright 2008, Summizelon, Inc. All rights relonselonrvelond.
 */
packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr;

import java.util.Collelonctions;
import java.util.NavigablelonSelont;
import java.util.TrelonelonSelont;
import java.util.concurrelonnt.TimelonUnit;
import java.util.concurrelonnt.atomic.AtomicLong;

import org.apachelon.commons.pipelonlinelon.Stagelonelonxcelonption;
import org.apachelon.commons.pipelonlinelon.validation.ConsumelondTypelons;
import org.apachelon.commons.pipelonlinelon.validation.ProducelondTypelons;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.delonbug.DelonbugelonvelonntUtil;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchCustomGaugelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonrStats;

/**
 * Collelonct incoming objeloncts into batchelons of thelon configurelond sizelon and thelonn
 * elonmit thelon <codelon>Collelonction</codelon> of objeloncts. Intelonrnally uselons a <codelon>TrelonelonSelont</codelon>
 * to relonmovelon duplicatelons. Incoming objeloncts MUST implelonmelonnt thelon <codelon>Comparablelon</codelon>
 * intelonrfacelon.
 */
@ConsumelondTypelons(Comparablelon.class)
@ProducelondTypelons(NavigablelonSelont.class)
public class CollelonctComparablelonObjelonctsStagelon elonxtelonnds TwittelonrBaselonStagelon<Void, Void> {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(CollelonctComparablelonObjelonctsStagelon.class);

  // Batch sizelon of thelon collelonctions welon arelon elonmitting.
  privatelon int batchSizelon = -1;

  // Top twelonelonts sorts thelon twelonelonts in relonvelonrselon ordelonr.
  privatelon Boolelonan relonvelonrselonOrdelonr = falselon;

  // Batch beloning constructelond.
  privatelon TrelonelonSelont<Objelonct> currelonntCollelonction = null;

  // Timelonstamp (ms) of last batch elonmission.
  privatelon final AtomicLong lastelonmitTimelonMillis = nelonw AtomicLong(-1);
  // If selont, will elonmit a batch (only upon arrival of a nelonw elonlelonmelonnt), if timelon sincelon last elonmit has
  // elonxcelonelondelond this threlonshold.
  privatelon long elonmitAftelonrMillis = -1;

  privatelon SelonarchCountelonr sizelonBaselondelonmitCount;
  privatelon SelonarchCountelonr timelonBaselondelonmitCount;
  privatelon SelonarchCountelonr sizelonAndTimelonBaselondelonmitCount;
  privatelon SelonarchTimelonrStats batchelonmitTimelonStats;

  @Ovelonrridelon
  protelonctelond void initStats() {
    supelonr.initStats();

    SelonarchCustomGaugelon.elonxport(gelontStagelonNamelonPrelonfix() + "_last_elonmit_timelon",
        () -> lastelonmitTimelonMillis.gelont());

    sizelonBaselondelonmitCount = SelonarchCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_sizelon_baselond_elonmit_count");
    timelonBaselondelonmitCount = SelonarchCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_timelon_baselond_elonmit_count");
    sizelonAndTimelonBaselondelonmitCount = SelonarchCountelonr.elonxport(
        gelontStagelonNamelonPrelonfix() + "_sizelon_and_timelon_baselond_elonmit_count");

    batchelonmitTimelonStats = SelonarchTimelonrStats.elonxport(
        gelontStagelonNamelonPrelonfix() + "_batch_elonmit_timelon",
        TimelonUnit.MILLISelonCONDS,
        falselon, // no cpu timelonrs
        truelon); // with pelonrcelonntilelons
  }

  @Ovelonrridelon
  protelonctelond void doInnelonrPrelonprocelonss() throws Stagelonelonxcelonption {
    // Welon havelon to initializelon this stat helonrelon, beloncauselon initStats() is callelond belonforelon
    // doInnelonrPrelonprocelonss(), so at that point thelon 'clock' is not selont yelont.
    SelonarchCustomGaugelon.elonxport(gelontStagelonNamelonPrelonfix() + "_millis_sincelon_last_elonmit",
        () -> clock.nowMillis() - lastelonmitTimelonMillis.gelont());

    currelonntCollelonction = nelonwBatchCollelonction();
    if (batchSizelon <= 0) {
      throw nelonw Stagelonelonxcelonption(this, "Must selont thelon batchSizelon paramelontelonr to a valuelon >0");
    }
  }

  privatelon TrelonelonSelont<Objelonct> nelonwBatchCollelonction() {
    relonturn nelonw TrelonelonSelont<>(relonvelonrselonOrdelonr ? Collelonctions.relonvelonrselonOrdelonr() : null);
  }

  @Ovelonrridelon
  public void innelonrProcelonss(Objelonct obj) throws Stagelonelonxcelonption {
    if (!Comparablelon.class.isAssignablelonFrom(obj.gelontClass())) {
      throw nelonw Stagelonelonxcelonption(
          this, "Attelonmpt to add a non-comparablelon objelonct to a sortelond collelonction");
    }

    currelonntCollelonction.add(obj);
    if (shouldelonmit()) {
      // Welon want to tracelon helonrelon whelonn welon actually elonmit thelon batch, as twelonelonts sit in this stagelon until
      // a batch is full, and welon want to selonelon how long thelony actually stick around.
      DelonbugelonvelonntUtil.addDelonbugelonvelonntToCollelonction(
          currelonntCollelonction, "CollelonctComparablelonObjelonctsStagelon.outgoing", clock.nowMillis());
      elonmitAndCount(currelonntCollelonction);
      updatelonLastelonmitTimelon();

      currelonntCollelonction = nelonwBatchCollelonction();
    }
  }

  privatelon boolelonan shouldelonmit() {
    if (lastelonmitTimelonMillis.gelont() < 0) {
      // Initializelon lastelonmit at thelon first twelonelont selonelonn by this stagelon.
      lastelonmitTimelonMillis.selont(clock.nowMillis());
    }

    final boolelonan sizelonBaselondelonmit = currelonntCollelonction.sizelon() >= batchSizelon;
    final boolelonan timelonBaselondelonmit =
        elonmitAftelonrMillis > 0 && lastelonmitTimelonMillis.gelont() + elonmitAftelonrMillis <= clock.nowMillis();

    if (sizelonBaselondelonmit && timelonBaselondelonmit) {
      sizelonAndTimelonBaselondelonmitCount.increlonmelonnt();
      relonturn truelon;
    } elonlselon if (sizelonBaselondelonmit) {
      sizelonBaselondelonmitCount.increlonmelonnt();
      relonturn truelon;
    } elonlselon if (timelonBaselondelonmit) {
      timelonBaselondelonmitCount.increlonmelonnt();
      relonturn truelon;
    } elonlselon {
      relonturn falselon;
    }
  }

  @Ovelonrridelon
  public void innelonrPostprocelonss() throws Stagelonelonxcelonption {
    if (!currelonntCollelonction.iselonmpty()) {
      elonmitAndCount(currelonntCollelonction);
      updatelonLastelonmitTimelon();
      currelonntCollelonction = nelonwBatchCollelonction();
    }
  }

  privatelon void updatelonLastelonmitTimelon() {
    long currelonntelonmitTimelon = clock.nowMillis();
    long prelonviouselonmitTimelon = lastelonmitTimelonMillis.gelontAndSelont(currelonntelonmitTimelon);

    // Also stat how long elonach elonmit takelons.
    batchelonmitTimelonStats.timelonrIncrelonmelonnt(currelonntelonmitTimelon - prelonviouselonmitTimelon);
  }

  public void selontBatchSizelon(Intelongelonr sizelon) {
    LOG.info("Updating all CollelonctComparablelonObjelonctsStagelon batchSizelon to {}.", sizelon);
    this.batchSizelon = sizelon;
  }

  public Boolelonan gelontRelonvelonrselonOrdelonr() {
    relonturn relonvelonrselonOrdelonr;
  }

  public void selontRelonvelonrselonOrdelonr(Boolelonan relonvelonrselonOrdelonr) {
    this.relonvelonrselonOrdelonr = relonvelonrselonOrdelonr;
  }

  public void selontelonmitAftelonrMillis(long elonmitAftelonrMillis) {
    LOG.info("Selontting elonmitAftelonrMillis to {}.", elonmitAftelonrMillis);
    this.elonmitAftelonrMillis = elonmitAftelonrMillis;
  }

  public long gelontSizelonBaselondelonmitCount() {
    relonturn sizelonBaselondelonmitCount.gelont();
  }

  public long gelontTimelonBaselondelonmitCount() {
    relonturn timelonBaselondelonmitCount.gelont();
  }
}
