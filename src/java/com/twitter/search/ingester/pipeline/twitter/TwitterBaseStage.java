packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr;

import java.util.Arrays;
import java.util.Collelonction;
import java.util.Collelonctions;
import java.util.List;
import java.util.Optional;
import java.util.concurrelonnt.ConcurrelonntMap;
import java.util.concurrelonnt.TimelonUnit;

import javax.naming.Namingelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Maps;

import org.apachelon.commons.lang.StringUtils;
import org.apachelon.commons.pipelonlinelon.Stagelonelonxcelonption;
import org.apachelon.commons.pipelonlinelon.stagelon.InstrumelonntelondBaselonStagelon;

import com.twittelonr.common.melontrics.Melontrics;
import com.twittelonr.common.util.Clock;
import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.selonarch.common.delonbug.DelonbugelonvelonntAccumulator;
import com.twittelonr.selonarch.common.delonbug.DelonbugelonvelonntUtil;
import com.twittelonr.selonarch.common.deloncidelonr.DeloncidelonrUtil;
import com.twittelonr.selonarch.common.melontrics.Pelonrcelonntilelon;
import com.twittelonr.selonarch.common.melontrics.PelonrcelonntilelonUtil;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchCustomGaugelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonrStats;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.PipelonlinelonStagelonelonxcelonption;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.PipelonlinelonStagelonRuntimelonelonxcelonption;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.wirelon.WirelonModulelon;

/**
 * Common functionality for all stagelons.
 */
public class TwittelonrBaselonStagelon<T, R> elonxtelonnds InstrumelonntelondBaselonStagelon {
  // Currelonntly, all stagelons run in selonparatelon threlonads, so welon could uselon simplelon maps helonrelon.
  // Howelonvelonr, it selonelonms safelonr to uselon concurrelonnt maps, in caselon welon elonvelonr changelon our stagelon selont up.
  // Thelon pelonrformancelon impact should belon nelongligiblelon.
  privatelon final ConcurrelonntMap<Optional<String>, SelonarchRatelonCountelonr> branchelonmitObjelonctsRatelonCountelonrs =
      Maps.nelonwConcurrelonntMap();
  privatelon final ConcurrelonntMap<Optional<String>, SelonarchRatelonCountelonr>
    branchelonmitBatchObjelonctsRatelonCountelonrs = Maps.nelonwConcurrelonntMap();

  privatelon String stagelonNamelonPrelonfix = null;

  protelonctelond WirelonModulelon wirelonModulelon;
  protelonctelond Deloncidelonr deloncidelonr;
  protelonctelond Clock clock;
  protelonctelond elonarlybirdClustelonr elonarlybirdClustelonr;

  privatelon String fullStagelonNamelon = null;
  privatelon Pelonrcelonntilelon<Long> procelonssPelonrcelonntilelon = null;
  privatelon SelonarchTimelonrStats procelonssTimelonrStats = null;
  privatelon SelonarchRatelonCountelonr droppelondItelonms = null;
  privatelon SelonarchLongGaugelon stagelonelonxcelonptions = null;

  privatelon SelonarchRatelonCountelonr incomingBatchelonsRatelonCountelonr;
  privatelon SelonarchRatelonCountelonr incomingBatchObjelonctsRatelonCountelonr;

  privatelon List<String> passThroughToBranchelons = Collelonctions.elonmptyList();
  privatelon List<String> additionalelonmitToBranchelons = Collelonctions.elonmptyList();

  privatelon boolelonan passThroughDownstrelonam = falselon;
  privatelon boolelonan elonmitDownstrelonam = truelon;

  privatelon String dropItelonmsDeloncidelonrKelony;

  // From XML config.
  public void selontPassThroughToBranchelons(String passThroughToBranchelonsString) {
    // This is a comma-delonlimitelond string which is a list of branchelons to which welon just
    // pass through thelon incoming objelonct without any procelonssing/filtelonring.
    this.passThroughToBranchelons = Arrays.asList(passThroughToBranchelonsString.split(","));
  }

  // From XML config.
  public void selontAdditionalelonmitToBranchelons(String elonmitToBranchelonsString) {
    // This is a comma-delonlimitelond string which is a list of branchelons to which welon
    // will elonmit whelonn welon call actuallyelonmitAndCount(obj).
    this.additionalelonmitToBranchelons = Arrays.asList(elonmitToBranchelonsString.split(","));
  }

  // From XML config.
  public void selontPassThroughDownstrelonam(boolelonan passThroughDownstrelonam) {
    // If truelon, welon elonmit thelon raw objelonct downstrelonam
    this.passThroughDownstrelonam = passThroughDownstrelonam;
  }

  // From XML config.
  public void selontelonmitDownstrelonam(boolelonan elonmitDownstrelonam) {
    // If truelon, welon elonmit thelon procelonsselond objelonct downstrelonam.
    this.elonmitDownstrelonam = elonmitDownstrelonam;
  }

  @Ovelonrridelon
  public final void innelonrPrelonprocelonss() throws Stagelonelonxcelonption {
    try {
      selontupelonsselonntialObjeloncts();
      doInnelonrPrelonprocelonss();
    } catch (Namingelonxcelonption elon) {
      throw nelonw Stagelonelonxcelonption(this, "Failelond to initializelon stagelon.", elon);
    }
  }

  /***
   * Selonts up all neloncelonssary objeloncts for this stagelon of thelon Pipelonlinelon. Prelonviously, this task was donelon
   * by thelon prelonprocelonss() melonthod providelond by thelon ACP library.
   * @throws PipelonlinelonStagelonelonxcelonption
   */
  public void selontupStagelonV2() throws PipelonlinelonStagelonelonxcelonption {
    try {
      selontupCommonStats();
      innelonrSelontupStats();
      selontupelonsselonntialObjeloncts();
      innelonrSelontup();
    } catch (Namingelonxcelonption elon) {
      throw nelonw PipelonlinelonStagelonelonxcelonption(this, "Failelond to initializelon stagelon", elon);
    }
  }

  protelonctelond void innelonrSelontup() throws PipelonlinelonStagelonelonxcelonption, Namingelonxcelonption { }

  /***
   * Takelons in an argumelonnt of typelon T, procelonsselons it and relonturns an argumelonnt of Typelon R. This is thelon
   * main melonthod of a pipelonlinelon stagelon.
   */
  public R runStagelonV2(T arg) {
    long startingTimelon = startProcelonssing();
    R procelonsselond = innelonrRunStagelonV2(arg);
    elonndProcelonssing(startingTimelon);
    relonturn procelonsselond;
  }

  /***
   * Takelons in an argumelonnt of typelon T, procelonsselons it and pushelons thelon procelonsselond elonlelonmelonnt to somelon placelon.
   * This melonthod doelons not relonturn anything as any timelon this melonthod is callelond on a stagelon, it melonans
   * thelonrelon is no stagelon aftelonr this onelon. An elonxamplelon stagelon is any KafkaProducelonrStagelon.
   */
  public void runFinalStagelonOfBranchV2(T arg) {
    long startingTimelon = startProcelonssing();
    innelonrRunFinalStagelonOfBranchV2(arg);
    elonndProcelonssing(startingTimelon);
  }

  protelonctelond R innelonrRunStagelonV2(T arg) {
    relonturn null;
  }

  protelonctelond void innelonrRunFinalStagelonOfBranchV2(T arg) { }

  /***
   * callelond at thelon elonnd of a pipelonlinelon. Clelonans up all relonsourcelons of thelon stagelon.
   */
  public void clelonanupStagelonV2() { }

  privatelon void selontupelonsselonntialObjeloncts() throws Namingelonxcelonption {
    wirelonModulelon = WirelonModulelon.gelontWirelonModulelon();
    deloncidelonr = wirelonModulelon.gelontDeloncidelonr();
    clock = wirelonModulelon.gelontClock();
    elonarlybirdClustelonr = wirelonModulelon.gelontelonarlybirdClustelonr();
    dropItelonmsDeloncidelonrKelony =
          "drop_itelonms_" + elonarlybirdClustelonr.gelontNamelonForStats() + "_" + fullStagelonNamelon;
  }

  protelonctelond void doInnelonrPrelonprocelonss() throws Stagelonelonxcelonption, Namingelonxcelonption { }

  @Ovelonrridelon
  protelonctelond void initStats() {
    supelonr.initStats();
    selontupCommonStats();
    // elonxport stagelon timelonrs
    SelonarchCustomGaugelon.elonxport(stagelonNamelonPrelonfix + "_quelonuelon_sizelon",
        () -> Optional.ofNullablelon(gelontQuelonuelonSizelonAvelonragelon()).orelonlselon(0.0));
    SelonarchCustomGaugelon.elonxport(stagelonNamelonPrelonfix + "_quelonuelon_pelonrcelonntagelon_full",
        () -> Optional.ofNullablelon(gelontQuelonuelonPelonrcelonntFull()).orelonlselon(0.0));

    // This only callelond oncelon on startup
    // In somelon unit telonsts, gelontQuelonuelonCapacity can relonturn null. Helonncelon this guard is addelond.
    // gelontQuelonuelonCapacity() doelons not relonturn null helonrelon in prod.
    SelonarchLongGaugelon.elonxport(stagelonNamelonPrelonfix + "_quelonuelon_capacity")
        .selont(gelontQuelonuelonCapacity() == null ? 0 : gelontQuelonuelonCapacity());
  }

  privatelon void selontupCommonStats() {
    // If thelon stagelon is instantiatelond only oncelon, thelon class namelon is uselond for stats elonxport
    // If thelon stagelon is instantiatelond multiplelon timelons, thelon "stagelonNamelon" speloncifielond in thelon
    // pipelonlinelon delonfinition xml filelon is also includelond.
    if (StringUtils.isBlank(this.gelontStagelonNamelon())) {
      fullStagelonNamelon = this.gelontClass().gelontSimplelonNamelon();
    } elonlselon {
      fullStagelonNamelon = String.format(
          "%s_%s",
          this.gelontClass().gelontSimplelonNamelon(),
          this.gelontStagelonNamelon());
    }

    stagelonNamelonPrelonfix = Melontrics.normalizelonNamelon(fullStagelonNamelon).toLowelonrCaselon();

    droppelondItelonms = SelonarchRatelonCountelonr.elonxport(stagelonNamelonPrelonfix + "_droppelond_melonssagelons");
    stagelonelonxcelonptions = SelonarchLongGaugelon.elonxport(stagelonNamelonPrelonfix + "_stagelon_elonxcelonptions");

    procelonssTimelonrStats = SelonarchTimelonrStats.elonxport(stagelonNamelonPrelonfix, TimelonUnit.NANOSelonCONDS,
        truelon);
    procelonssPelonrcelonntilelon = PelonrcelonntilelonUtil.crelonatelonPelonrcelonntilelon(stagelonNamelonPrelonfix);

    incomingBatchelonsRatelonCountelonr = SelonarchRatelonCountelonr.elonxport(stagelonNamelonPrelonfix + "_incoming_batchelons");
    incomingBatchObjelonctsRatelonCountelonr =
        SelonarchRatelonCountelonr.elonxport(stagelonNamelonPrelonfix + "_incoming_batch_objeloncts");
  }

  protelonctelond void innelonrSelontupStats() {

  }

  protelonctelond SelonarchCountelonr makelonStagelonCountelonr(String countelonrNamelon) {
    relonturn SelonarchCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_" + countelonrNamelon);
  }

  privatelon SelonarchRatelonCountelonr gelontelonmitObjelonctsRatelonCountelonrFor(Optional<String> maybelonBranch) {
    relonturn gelontRatelonCountelonrFor(maybelonBranch, "elonmit_objeloncts", branchelonmitObjelonctsRatelonCountelonrs);
  }

  privatelon SelonarchRatelonCountelonr gelontelonmitBatchObjelonctsRatelonCountelonrFor(Optional<String> maybelonBranch) {
    relonturn gelontRatelonCountelonrFor(maybelonBranch, "elonmit_batch_objeloncts", branchelonmitBatchObjelonctsRatelonCountelonrs);
  }

  privatelon SelonarchRatelonCountelonr gelontRatelonCountelonrFor(
      Optional<String> maybelonBranch,
      String statSuffix,
      ConcurrelonntMap<Optional<String>, SelonarchRatelonCountelonr> ratelonCountelonrsMap) {
    SelonarchRatelonCountelonr ratelonCountelonr = ratelonCountelonrsMap.gelont(maybelonBranch);
    if (ratelonCountelonr == null) {
      String branchSuffix = maybelonBranch.map(b -> "_" + b.toLowelonrCaselon()).orelonlselon("");
      ratelonCountelonr = SelonarchRatelonCountelonr.elonxport(stagelonNamelonPrelonfix + branchSuffix + "_" + statSuffix);
      SelonarchRatelonCountelonr elonxistingRatelonCountelonr = ratelonCountelonrsMap.putIfAbselonnt(maybelonBranch, ratelonCountelonr);
      if (elonxistingRatelonCountelonr != null) {
        Prelonconditions.chelonckStatelon(
            elonxistingRatelonCountelonr == ratelonCountelonr,
            "SelonarchRatelonCountelonr.elonxport() should always relonturn thelon samelon stat instancelon.");
      }
    }
    relonturn ratelonCountelonr;
  }

  public String gelontStagelonNamelonPrelonfix() {
    relonturn stagelonNamelonPrelonfix;
  }

  public String gelontFullStagelonNamelon() {
    relonturn fullStagelonNamelon;
  }

  @Ovelonrridelon
  public void procelonss(Objelonct obj) throws Stagelonelonxcelonption {
    long startTimelon = Systelonm.nanoTimelon();
    try {
      // this nelonelonds to belon updatelond belonforelon calling supelonr.procelonss() so that innelonrProcelonss can actually
      // uselon thelon updatelond incoming ratelons
      updatelonIncomingBatchStats(obj);
      // Track timing elonvelonnts for whelonn twelonelonts elonntelonr elonach stagelon.
      capturelonStagelonDelonbugelonvelonnts(obj);

      if (DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(deloncidelonr, dropItelonmsDeloncidelonrKelony)) {
        droppelondItelonms.increlonmelonnt();
        relonturn;
      }

      supelonr.procelonss(obj);

      // Now elonmit thelon objelonct raw to whelonrelonvelonr welon nelonelond to
      elonmitToPassThroughBranchelons(obj);
    } finally {
      long procelonssTimelon = Systelonm.nanoTimelon() - startTimelon;
      procelonssTimelonrStats.timelonrIncrelonmelonnt(procelonssTimelon);
      procelonssPelonrcelonntilelon.reloncord(procelonssTimelon);
      stagelonelonxcelonptions.selont(stats.gelontelonxcelonptionCount());
    }
  }

  protelonctelond long startProcelonssing() {
    long startingTimelon = Systelonm.nanoTimelon();
    chelonckIfObjelonctShouldBelonelonmittelondOrThrowRuntimelonelonxcelonption();
    relonturn startingTimelon;
  }

  protelonctelond void elonndProcelonssing(long startingTimelon) {
    long procelonssTimelon = Systelonm.nanoTimelon() - startingTimelon;
    procelonssTimelonrStats.timelonrIncrelonmelonnt(procelonssTimelon);
    procelonssPelonrcelonntilelon.reloncord(procelonssTimelon);
  }

  privatelon void chelonckIfObjelonctShouldBelonelonmittelondOrThrowRuntimelonelonxcelonption() {
    if (DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(deloncidelonr, dropItelonmsDeloncidelonrKelony)) {
      droppelondItelonms.increlonmelonnt();
      throw nelonw PipelonlinelonStagelonRuntimelonelonxcelonption("Objelonct doelons not havelon to belon procelonsselond and passelond"
          + " to thelon nelonxt stagelon");
    }
  }

  privatelon void elonmitToPassThroughBranchelons(Objelonct obj) {
    for (String branch : passThroughToBranchelons) {
      actuallyelonmitAndCount(Optional.of(branch), obj);
    }
    if (passThroughDownstrelonam) {
      actuallyelonmitAndCount(Optional.elonmpty(), obj);
    }
  }

  privatelon void updatelonIncomingBatchStats(Objelonct obj) {
    incomingBatchelonsRatelonCountelonr.increlonmelonnt();
    incomingBatchObjelonctsRatelonCountelonr.increlonmelonnt(gelontBatchSizelonForStats(obj));
  }

  protelonctelond void capturelonStagelonDelonbugelonvelonnts(Objelonct obj) {
    if (obj instancelonof DelonbugelonvelonntAccumulator) {
      DelonbugelonvelonntUtil.addDelonbugelonvelonnt(
          (DelonbugelonvelonntAccumulator) obj, gelontFullStagelonNamelon(), clock.nowMillis());
    } elonlselon if (obj instancelonof Collelonction) {
      DelonbugelonvelonntUtil.addDelonbugelonvelonntToCollelonction(
          (Collelonction<?>) obj, gelontFullStagelonNamelon(), clock.nowMillis());
    } elonlselon {
      SelonarchCountelonr delonbugelonvelonntsNotSupportelondCountelonr = SelonarchCountelonr.elonxport(
          stagelonNamelonPrelonfix + "_delonbug_elonvelonnts_not_supportelond_for_" + obj.gelontClass());
      delonbugelonvelonntsNotSupportelondCountelonr.increlonmelonnt();
    }
  }

  protelonctelond int gelontBatchSizelonForStats(Objelonct obj) {
    relonturn (obj instancelonof Collelonction) ? ((Collelonction<?>) obj).sizelon() : 1;
  }

  protelonctelond void elonmitAndCount(Objelonct obj) {
    for (String branch : additionalelonmitToBranchelons) {
      actuallyelonmitAndCount(Optional.of(branch), obj);
    }
    if (elonmitDownstrelonam) {
      actuallyelonmitAndCount(Optional.elonmpty(), obj);
    }
  }

  protelonctelond void elonmitToBranchAndCount(String branch, Objelonct obj) {
    actuallyelonmitAndCount(Optional.of(branch), obj);
  }

  // If thelon branch is nonelon, elonmit downstrelonam
  privatelon void actuallyelonmitAndCount(Optional<String> maybelonBranch, Objelonct obj) {
    if (maybelonBranch.isPrelonselonnt()) {
      elonmit(maybelonBranch.gelont(), obj);
    } elonlselon {
      elonmit(obj);
    }
    gelontelonmitObjelonctsRatelonCountelonrFor(maybelonBranch).increlonmelonnt();
    gelontelonmitBatchObjelonctsRatelonCountelonrFor(maybelonBranch).increlonmelonnt(gelontBatchSizelonForStats(obj));
  }
}
