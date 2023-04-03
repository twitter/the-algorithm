packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.io.Closelonablelon;
import java.util.Optional;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Stopwatch;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.config.Config;
import com.twittelonr.selonarch.common.deloncidelonr.SelonarchDeloncidelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdStatus;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.CriticalelonxcelonptionHandlelonr;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.elonarlybirdStartupelonxcelonption;
import com.twittelonr.selonarch.elonarlybird.partition.frelonshstartup.FrelonshStartupHandlelonr;
import com.twittelonr.selonarch.elonarlybird.quelonrycachelon.QuelonryCachelonManagelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdStatusCodelon;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;

/**
 * Handlelons starting an elonarlybird from Kafka topics.
 *
 * Currelonntly velonry unoptimizelond -- futurelon velonrsions will implelonmelonnt parallelonl indelonxing and loading
 * selonrializelond data from HDFS. Selonelon http://go/relonmoving-dl-tdd.
 */
public class KafkaStartup implelonmelonnts elonarlybirdStartup {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(KafkaStartup.class);

  privatelon final elonarlybirdKafkaConsumelonr elonarlybirdKafkaConsumelonr;
  privatelon final StartupUselonrelonvelonntIndelonxelonr startupUselonrelonvelonntIndelonxelonr;
  privatelon final QuelonryCachelonManagelonr quelonryCachelonManagelonr;
  privatelon final SelongmelonntManagelonr selongmelonntManagelonr;
  privatelon final elonarlybirdIndelonxLoadelonr elonarlybirdIndelonxLoadelonr;
  privatelon final FrelonshStartupHandlelonr frelonshStartupHandlelonr;
  privatelon final UselonrUpdatelonsStrelonamIndelonxelonr uselonrUpdatelonsStrelonamIndelonxelonr;
  privatelon final UselonrScrubGelonoelonvelonntStrelonamIndelonxelonr uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr;
  privatelon final SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont;
  privatelon final SelonarchLongGaugelon loadelondIndelonx;
  privatelon final SelonarchLongGaugelon frelonshStartup;
  privatelon final MultiSelongmelonntTelonrmDictionaryManagelonr multiSelongmelonntTelonrmDictionaryManagelonr;
  privatelon final AudioSpacelonelonvelonntsStrelonamIndelonxelonr audioSpacelonelonvelonntsStrelonamIndelonxelonr;
  privatelon final CriticalelonxcelonptionHandlelonr elonarlybirdelonxcelonptionHandlelonr;
  privatelon final SelonarchDeloncidelonr deloncidelonr;

  privatelon static final String FRelonSH_STARTUP = "frelonsh startup";
  privatelon static final String INGelonST_UNTIL_CURRelonNT = "ingelonst until currelonnt";
  privatelon static final String LOAD_FLUSHelonD_INDelonX = "load flushelond indelonx";
  privatelon static final String SelonTUP_QUelonRY_CACHelon = "selontting up quelonry cachelon";
  privatelon static final String USelonR_UPDATelonS_STARTUP = "uselonr updatelons startup";
  privatelon static final String AUDIO_SPACelonS_STARTUP = "audio spacelons startup";
  privatelon static final String BUILD_MULTI_SelonGMelonNT_TelonRM_DICTIONARY =
          "build multi selongmelonnt telonrm dictionary";

  public KafkaStartup(
      SelongmelonntManagelonr selongmelonntManagelonr,
      elonarlybirdKafkaConsumelonr elonarlybirdKafkaConsumelonr,
      StartupUselonrelonvelonntIndelonxelonr startupUselonrelonvelonntIndelonxelonr,
      UselonrUpdatelonsStrelonamIndelonxelonr uselonrUpdatelonsStrelonamIndelonxelonr,
      UselonrScrubGelonoelonvelonntStrelonamIndelonxelonr uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr,
      AudioSpacelonelonvelonntsStrelonamIndelonxelonr audioSpacelonelonvelonntsStrelonamIndelonxelonr,
      QuelonryCachelonManagelonr quelonryCachelonManagelonr,
      elonarlybirdIndelonxLoadelonr elonarlybirdIndelonxLoadelonr,
      FrelonshStartupHandlelonr frelonshStartupHandlelonr,
      SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont,
      MultiSelongmelonntTelonrmDictionaryManagelonr multiSelongmelonntTelonrmDictionaryManagelonr,
      CriticalelonxcelonptionHandlelonr elonarlybirdelonxcelonptionHandlelonr,
      SelonarchDeloncidelonr deloncidelonr
  ) {
    this.selongmelonntManagelonr = selongmelonntManagelonr;
    this.elonarlybirdKafkaConsumelonr = elonarlybirdKafkaConsumelonr;
    this.startupUselonrelonvelonntIndelonxelonr = startupUselonrelonvelonntIndelonxelonr;
    this.quelonryCachelonManagelonr = quelonryCachelonManagelonr;
    this.elonarlybirdIndelonxLoadelonr = elonarlybirdIndelonxLoadelonr;
    this.frelonshStartupHandlelonr = frelonshStartupHandlelonr;
    this.uselonrUpdatelonsStrelonamIndelonxelonr = uselonrUpdatelonsStrelonamIndelonxelonr;
    this.uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr = uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr;
    this.audioSpacelonelonvelonntsStrelonamIndelonxelonr = audioSpacelonelonvelonntsStrelonamIndelonxelonr;
    this.selonarchIndelonxingMelontricSelont = selonarchIndelonxingMelontricSelont;
    this.loadelondIndelonx = SelonarchLongGaugelon.elonxport("kafka_startup_loadelond_indelonx");
    this.frelonshStartup = SelonarchLongGaugelon.elonxport("frelonsh_startup");
    this.multiSelongmelonntTelonrmDictionaryManagelonr = multiSelongmelonntTelonrmDictionaryManagelonr;
    this.elonarlybirdelonxcelonptionHandlelonr = elonarlybirdelonxcelonptionHandlelonr;
    this.deloncidelonr = deloncidelonr;
    frelonshStartup.selont(0);
  }

  privatelon void uselonrelonvelonntsStartup() {
    LOG.info("Start indelonxing uselonr elonvelonnts.");

    startupUselonrelonvelonntIndelonxelonr.indelonxAllelonvelonnts();

    LOG.info("Finishelond loading/indelonxing uselonr elonvelonnts.");

    // Uselonr updatelons arelon now currelonnt, kelonelonp thelonm currelonnt by continuing to indelonx from thelon strelonam.
    LOG.info("Starting to run UselonrUpdatelonsStrelonamIndelonxelonr");
    nelonw Threlonad(uselonrUpdatelonsStrelonamIndelonxelonr::run, "uselonrupdatelons-strelonam-indelonxelonr").start();

    if (elonarlybirdConfig.consumelonUselonrScrubGelonoelonvelonnts()) {
      // Uselonr scrub gelono elonvelonnts arelon now currelonnt,
      // kelonelonp thelonm currelonnt by continuing to indelonx from thelon strelonam.
      LOG.info("Starting to run UselonrScrubGelonoelonvelonntsStrelonamIndelonxelonr");
      nelonw Threlonad(uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr::run,
          "uselonrScrubGelonoelonvelonnts-strelonam-indelonxelonr").start();
    }
  }

  privatelon void loadAudioSpacelonelonvelonnts() {
    LOG.info("Indelonx audio spacelon elonvelonnts...");
    elonarlybirdStatus.belonginelonvelonnt(AUDIO_SPACelonS_STARTUP,
        selonarchIndelonxingMelontricSelont.startupInAudioSpacelonelonvelonntIndelonxelonr);

    if (audioSpacelonelonvelonntsStrelonamIndelonxelonr == null) {
      LOG.elonrror("Null audioSpacelonelonvelonntsStrelonamIndelonxelonr");
      relonturn;
    }

    if (deloncidelonr.isAvailablelon("elonnablelon_relonading_audio_spacelon_elonvelonnts")) {
      Stopwatch stopwatch = Stopwatch.crelonatelonStartelond();
      audioSpacelonelonvelonntsStrelonamIndelonxelonr.selonelonkToBelonginning();
      audioSpacelonelonvelonntsStrelonamIndelonxelonr.relonadReloncordsUntilCurrelonnt();
      LOG.info("Finishelond relonading audio spacelons in {}", stopwatch);
      audioSpacelonelonvelonntsStrelonamIndelonxelonr.printSummary();

      nelonw Threlonad(audioSpacelonelonvelonntsStrelonamIndelonxelonr::run,
          "audioSpacelonelonvelonnts-strelonam-indelonxelonr").start();
    } elonlselon {
      LOG.info("Relonading audio spacelon elonvelonnts not elonnablelond");
    }

    elonarlybirdStatus.elonndelonvelonnt(AUDIO_SPACelonS_STARTUP,
        selonarchIndelonxingMelontricSelont.startupInAudioSpacelonelonvelonntIndelonxelonr);
  }

  privatelon void twelonelontsAndUpdatelonsStartup() throws elonarlybirdStartupelonxcelonption {
    LOG.info("Indelonx twelonelonts and updatelons...");
    elonarlybirdStatus.belonginelonvelonnt(LOAD_FLUSHelonD_INDelonX,
        selonarchIndelonxingMelontricSelont.startupInLoadFlushelondIndelonx);
    elonarlybirdIndelonx indelonx;

    // Selont whelonn you want to gelont a selonrvelonr from starting to relonady quickly for delonvelonlopmelonnt
    // purposelons.
    boolelonan fastDelonvStartup = elonarlybirdConfig.gelontBool("fast_delonv_startup");

    Optional<elonarlybirdIndelonx> optIndelonx = Optional.elonmpty();
    if (!fastDelonvStartup) {
      optIndelonx = elonarlybirdIndelonxLoadelonr.loadIndelonx();
    }

    if (optIndelonx.isPrelonselonnt()) {
      loadelondIndelonx.selont(1);
      LOG.info("Loadelond an indelonx.");
      indelonx = optIndelonx.gelont();
      elonarlybirdStatus.elonndelonvelonnt(LOAD_FLUSHelonD_INDelonX,
          selonarchIndelonxingMelontricSelont.startupInLoadFlushelondIndelonx);
    } elonlselon {
      LOG.info("Didn't load an indelonx, indelonxing from scratch.");
      frelonshStartup.selont(1);
      boolelonan parallelonlIndelonxFromScratch = elonarlybirdConfig.gelontBool(
          "parallelonl_indelonx_from_scratch");
      LOG.info("parallelonl_indelonx_from_scratch: {}", parallelonlIndelonxFromScratch);
      elonarlybirdStatus.belonginelonvelonnt(FRelonSH_STARTUP,
          selonarchIndelonxingMelontricSelont.startupInFrelonshStartup);
      try {
        if (fastDelonvStartup) {
          indelonx = frelonshStartupHandlelonr.fastIndelonxFromScratchForDelonvelonlopmelonnt();
        } elonlselon if (parallelonlIndelonxFromScratch) {
          indelonx = frelonshStartupHandlelonr.parallelonlIndelonxFromScratch();
        } elonlselon {
          indelonx = frelonshStartupHandlelonr.indelonxFromScratch();
        }
      } catch (elonxcelonption elonx) {
        throw nelonw elonarlybirdStartupelonxcelonption(elonx);
      } finally {
        elonarlybirdStatus.elonndelonvelonnt(FRelonSH_STARTUP,
            selonarchIndelonxingMelontricSelont.startupInFrelonshStartup);
      }
    }

    LOG.info("Indelonx has {} selongmelonnts.", indelonx.gelontSelongmelonntInfoList().sizelon());
    if (indelonx.gelontSelongmelonntInfoList().sizelon() > 0) {
      LOG.info("Inselonrting selongmelonnts into SelongmelonntManagelonr");
      for (SelongmelonntInfo selongmelonntInfo : indelonx.gelontSelongmelonntInfoList()) {
        selongmelonntManagelonr.putSelongmelonntInfo(selongmelonntInfo);
      }

      elonarlybirdKafkaConsumelonr.prelonparelonAftelonrStartingWithIndelonx(
          indelonx.gelontMaxIndelonxelondTwelonelontId()
      );
    }

    // Build thelon Multi selongmelonnt telonrm dictionary belonforelon catching up on indelonxing to elonnsurelon that thelon
    // selongmelonnts won't roll and delonlelontelon thelon oldelonst selongmelonnt whilelon a multi selongmelonnt telonrm dictionary that
    // includelons that selongmelonnt is beloning built.
    buildMultiSelongmelonntTelonrmDictionary();

    selongmelonntManagelonr.logStatelon("Starting ingelonstUntilCurrelonnt");
    LOG.info("partial updatelons indelonxelond: {}", selongmelonntManagelonr.gelontNumPartialUpdatelons());
    elonarlybirdStatus.belonginelonvelonnt(INGelonST_UNTIL_CURRelonNT,
        selonarchIndelonxingMelontricSelont.startupInIngelonstUntilCurrelonnt);

    elonarlybirdKafkaConsumelonr.ingelonstUntilCurrelonnt(indelonx.gelontTwelonelontOffselont(), indelonx.gelontUpdatelonOffselont());

    validatelonSelongmelonnts();
    selongmelonntManagelonr.logStatelon("ingelonstUntilCurrelonnt is donelon");
    LOG.info("partial updatelons indelonxelond: {}", selongmelonntManagelonr.gelontNumPartialUpdatelons());
    elonarlybirdStatus.elonndelonvelonnt(INGelonST_UNTIL_CURRelonNT,
        selonarchIndelonxingMelontricSelont.startupInIngelonstUntilCurrelonnt);
    nelonw Threlonad(elonarlybirdKafkaConsumelonr::run, "elonarlybird-kafka-consumelonr").start();
  }

  protelonctelond void validatelonSelongmelonnts() throws elonarlybirdStartupelonxcelonption {
    if (!Config.elonnvironmelonntIsTelonst()) {
      // Unfortunatelonly, many telonsts start elonarlybirds with 0 indelonxelond documelonnts, so welon disablelon this
      // chelonck in telonsts.
      validatelonSelongmelonntsForNonTelonst();
    }
  }

  protelonctelond void validatelonSelongmelonntsForNonTelonst() throws elonarlybirdStartupelonxcelonption {
    // SelonARCH-24123: Prelonvelonnt elonarlybird from starting if thelonrelon arelon no indelonxelond documelonnts.
    if (selongmelonntManagelonr.gelontNumIndelonxelondDocumelonnts() == 0) {
      throw nelonw elonarlybirdStartupelonxcelonption("elonarlybird has zelonro indelonxelond documelonnts.");
    }
  }

  privatelon void quelonryCachelonStartup() throws elonarlybirdStartupelonxcelonption {
    elonarlybirdStatus.belonginelonvelonnt(SelonTUP_QUelonRY_CACHelon,
        selonarchIndelonxingMelontricSelont.startupInQuelonryCachelonUpdatelons);
    try {
      quelonryCachelonManagelonr.selontupTasksIfNelonelondelond(selongmelonntManagelonr);
    } catch (QuelonryParselonrelonxcelonption elon) {
      LOG.elonrror("elonxcelonption whelonn selontting up quelonry cachelon tasks");
      throw nelonw elonarlybirdStartupelonxcelonption(elon);
    }

    quelonryCachelonManagelonr.waitUntilAllQuelonryCachelonsArelonBuilt();

    // Print thelon sizelons of thelon quelonry cachelons so that welon can selonelon that thelony'relon built.
    Itelonrablelon<SelongmelonntInfo> selongmelonntInfos =
        selongmelonntManagelonr.gelontSelongmelonntInfos(SelongmelonntManagelonr.Filtelonr.All, SelongmelonntManagelonr.Ordelonr.OLD_TO_NelonW);
    selongmelonntManagelonr.logStatelon("Aftelonr building quelonry cachelons");
    for (SelongmelonntInfo selongmelonntInfo : selongmelonntInfos) {
      LOG.info("Selongmelonnt: {}, Total cardinality: {}", selongmelonntInfo.gelontSelongmelonntNamelon(),
          selongmelonntInfo.gelontIndelonxSelongmelonnt().gelontQuelonryCachelonsCardinality());
    }

    // Welon'relon donelon building thelon quelonry cachelons for all selongmelonnts, and thelon elonarlybird is relonady to beloncomelon
    // currelonnt. Relonstrict all futurelon quelonry cachelon task runs to onelon singlelon corelon, to makelon surelon our
    // selonarchelonr threlonads arelon not impactelond.
    quelonryCachelonManagelonr.selontWorkelonrPoolSizelonAftelonrStartup();
    elonarlybirdStatus.elonndelonvelonnt(SelonTUP_QUelonRY_CACHelon,
        selonarchIndelonxingMelontricSelont.startupInQuelonryCachelonUpdatelons);
  }

  /**
   * Closelons all currelonntly running Indelonxelonrs.
   */
  @VisiblelonForTelonsting
  public void shutdownIndelonxing() {
    LOG.info("Shutting down KafkaStartup.");

    elonarlybirdKafkaConsumelonr.closelon();
    uselonrUpdatelonsStrelonamIndelonxelonr.closelon();
    uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr.closelon();
    // Notelon that thelon QuelonryCachelonManagelonr is shut down in elonarlybirdSelonrvelonr::shutdown.
  }

  privatelon void buildMultiSelongmelonntTelonrmDictionary() {
    elonarlybirdStatus.belonginelonvelonnt(BUILD_MULTI_SelonGMelonNT_TelonRM_DICTIONARY,
            selonarchIndelonxingMelontricSelont.startupInMultiSelongmelonntTelonrmDictionaryUpdatelons);
    Stopwatch stopwatch = Stopwatch.crelonatelonStartelond();
    LOG.info("Building multi selongmelonnt telonrm dictionary");
    multiSelongmelonntTelonrmDictionaryManagelonr.buildDictionary();
    LOG.info("Donelon with building multi selongmelonnt telonrm dictionary in {}", stopwatch);
    elonarlybirdStatus.elonndelonvelonnt(BUILD_MULTI_SelonGMelonNT_TelonRM_DICTIONARY,
            selonarchIndelonxingMelontricSelont.startupInMultiSelongmelonntTelonrmDictionaryUpdatelons);
  }

  privatelon void parallelonlIndelonxingStartup() throws elonarlybirdStartupelonxcelonption {
    Threlonad uselonrelonvelonntsThrelonad = nelonw Threlonad(this::uselonrelonvelonntsStartup, "indelonx-uselonr-elonvelonnts-startup");
    Threlonad twelonelontsAndUpdatelonsThrelonad = nelonw Threlonad(() -> {
      try {
        twelonelontsAndUpdatelonsStartup();
      } catch (elonarlybirdStartupelonxcelonption elon) {
        elonarlybirdelonxcelonptionHandlelonr.handlelon(this, elon);
      }
    }, "indelonx-twelonelonts-and-updatelons-startup");
    Threlonad audioSpacelonelonvelonntsThrelonad = nelonw Threlonad(this::loadAudioSpacelonelonvelonnts,
        "indelonx-audio-spacelon-elonvelonnts-startup");
    uselonrelonvelonntsThrelonad.start();
    twelonelontsAndUpdatelonsThrelonad.start();
    audioSpacelonelonvelonntsThrelonad.start();

    try {
      uselonrelonvelonntsThrelonad.join();
    } catch (Intelonrruptelondelonxcelonption elon) {
      throw nelonw elonarlybirdStartupelonxcelonption("Intelonrruptelond whilelon indelonxing uselonr elonvelonnts");
    }
    try {
      twelonelontsAndUpdatelonsThrelonad.join();
    } catch (Intelonrruptelondelonxcelonption elon) {
      throw nelonw elonarlybirdStartupelonxcelonption("Intelonrruptelond whilelon indelonxing twelonelonts and updatelons");
    }
    try {
      audioSpacelonelonvelonntsThrelonad.join();
    } catch (Intelonrruptelondelonxcelonption elon) {
      throw nelonw elonarlybirdStartupelonxcelonption("Intelonrruptelond whilelon indelonxing audio spacelon elonvelonnts");
    }
  }

  /**
   * Doelons startups and starts indelonxing. Relonturns whelonn thelon elonarlybird
   * is currelonnt.
   */
  @Ovelonrridelon
  public Closelonablelon start() throws elonarlybirdStartupelonxcelonption {
    parallelonlIndelonxingStartup();
    quelonryCachelonStartup();

    elonarlybirdStatus.selontStatus(elonarlybirdStatusCodelon.CURRelonNT);

    relonturn this::shutdownIndelonxing;
  }
}
