packagelon com.twittelonr.selonarch.elonarlybird.archivelon;

import java.io.IOelonxcelonption;
import java.util.Datelon;
import java.util.List;
import java.util.concurrelonnt.TimelonUnit;
import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.baselon.Prelondicatelon;
import com.googlelon.common.collelonct.Lists;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.concurrelonnt.SchelondulelondelonxeloncutorSelonrvicelonFactory;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchStatsReloncelonivelonr;
import com.twittelonr.selonarch.common.util.GCUtil;
import com.twittelonr.selonarch.common.util.io.reloncordrelonadelonr.ReloncordRelonadelonr;
import com.twittelonr.selonarch.common.util.zktrylock.ZooKelonelonpelonrTryLockFactory;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdIndelonxConfig;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdStatus;
import com.twittelonr.selonarch.elonarlybird.SelonrvelonrSelontMelonmbelonr;
import com.twittelonr.selonarch.elonarlybird.archivelon.ArchivelonTimelonSlicelonr.ArchivelonTimelonSlicelon;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.util.ScrubGelonnUtil;
import com.twittelonr.selonarch.elonarlybird.documelonnt.TwelonelontDocumelonnt;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.CriticalelonxcelonptionHandlelonr;
import com.twittelonr.selonarch.elonarlybird.partition.ComplelontelonSelongmelonntManagelonr;
import com.twittelonr.selonarch.elonarlybird.partition.DynamicPartitionConfig;
import com.twittelonr.selonarch.elonarlybird.partition.MultiSelongmelonntTelonrmDictionaryManagelonr;
import com.twittelonr.selonarch.elonarlybird.partition.PartitionConfig;
import com.twittelonr.selonarch.elonarlybird.partition.PartitionManagelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SelonarchIndelonxingMelontricSelont;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntHdfsFlushelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntInfo;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntLoadelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntManagelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntManagelonr.Filtelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntManagelonr.Ordelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntOptimizelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntSyncConfig;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntWarmelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SimplelonSelongmelonntIndelonxelonr;
import com.twittelonr.selonarch.elonarlybird.partition.UselonrScrubGelonoelonvelonntStrelonamIndelonxelonr;
import com.twittelonr.selonarch.elonarlybird.partition.UselonrUpdatelonsStrelonamIndelonxelonr;
import com.twittelonr.selonarch.elonarlybird.quelonrycachelon.QuelonryCachelonManagelonr;
import com.twittelonr.selonarch.elonarlybird.selongmelonnt.SelongmelonntDataProvidelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdStatusCodelon;
import com.twittelonr.selonarch.elonarlybird.util.CoordinatelondelonarlybirdAction;
import com.twittelonr.selonarch.elonarlybird.util.CoordinatelondelonarlybirdActionIntelonrfacelon;
import com.twittelonr.selonarch.elonarlybird.util.CoordinatelondelonarlybirdActionLockFailelond;

public class ArchivelonSelonarchPartitionManagelonr elonxtelonnds PartitionManagelonr {
  privatelon static final Loggelonr LOG =
      LoggelonrFactory.gelontLoggelonr(ArchivelonSelonarchPartitionManagelonr.class);

  public static final String CONFIG_NAMelon = "archivelon";

  privatelon static final long ONelon_DAY_MILLIS = TimelonUnit.DAYS.toMillis(1);

  privatelon final ArchivelonTimelonSlicelonr timelonSlicelonr;
  privatelon final ArchivelonSelongmelonntDataProvidelonr selongmelonntDataProvidelonr;

  privatelon final UselonrUpdatelonsStrelonamIndelonxelonr uselonrUpdatelonsStrelonamIndelonxelonr;
  privatelon final UselonrScrubGelonoelonvelonntStrelonamIndelonxelonr uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr;

  privatelon final SelongmelonntWarmelonr selongmelonntWarmelonr;
  privatelon final elonarlybirdIndelonxConfig elonarlybirdIndelonxConfig;
  privatelon final ZooKelonelonpelonrTryLockFactory zkTryLockFactory;
  privatelon final Clock clock;
  privatelon final SelongmelonntSyncConfig selongmelonntSyncConfig;
  protelonctelond final SelonarchCountelonr gcAftelonrIndelonxing;

  // Uselond for coordinating daily updatelond across diffelonrelonnt relonplicas on thelon samelon hash partition,
  // to run thelonm onelon at a timelon, and minimizelon thelon impact on quelonry latelonncielons.
  privatelon final CoordinatelondelonarlybirdActionIntelonrfacelon coordinatelondDailyUpdatelon;

  privatelon final SelonarchIndelonxingMelontricSelont indelonxingMelontricSelont;

  // This is only uselond in telonsts whelonrelon no coordination is nelonelondelond.
  @VisiblelonForTelonsting
  public ArchivelonSelonarchPartitionManagelonr(
      ZooKelonelonpelonrTryLockFactory zooKelonelonpelonrTryLockFactory,
      QuelonryCachelonManagelonr quelonryCachelonManagelonr,
      SelongmelonntManagelonr selongmelonntManagelonr,
      DynamicPartitionConfig dynamicPartitionConfig,
      UselonrUpdatelonsStrelonamIndelonxelonr uselonrUpdatelonsStrelonamIndelonxelonr,
      UselonrScrubGelonoelonvelonntStrelonamIndelonxelonr uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr,
      SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr,
      ArchivelonelonarlybirdIndelonxConfig elonarlybirdIndelonxConfig,
      SchelondulelondelonxeloncutorSelonrvicelonFactory elonxeloncutorSelonrvicelonFactory,
      SchelondulelondelonxeloncutorSelonrvicelonFactory uselonrUpdatelonIndelonxelonrSchelondulelondelonxeloncutorFactory,
      SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont,
      SelongmelonntSyncConfig syncConfig,
      Clock clock,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr)
      throws IOelonxcelonption {
    this(
        zooKelonelonpelonrTryLockFactory,
        quelonryCachelonManagelonr,
        selongmelonntManagelonr,
        dynamicPartitionConfig,
        uselonrUpdatelonsStrelonamIndelonxelonr,
        uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr,
        selonarchStatsReloncelonivelonr,
        elonarlybirdIndelonxConfig,
        null,
        elonxeloncutorSelonrvicelonFactory,
        uselonrUpdatelonIndelonxelonrSchelondulelondelonxeloncutorFactory,
        selonarchIndelonxingMelontricSelont,
        syncConfig,
        clock,
        criticalelonxcelonptionHandlelonr);
  }

  public ArchivelonSelonarchPartitionManagelonr(
      ZooKelonelonpelonrTryLockFactory zooKelonelonpelonrTryLockFactory,
      QuelonryCachelonManagelonr quelonryCachelonManagelonr,
      SelongmelonntManagelonr selongmelonntManagelonr,
      DynamicPartitionConfig dynamicPartitionConfig,
      UselonrUpdatelonsStrelonamIndelonxelonr uselonrUpdatelonsStrelonamIndelonxelonr,
      UselonrScrubGelonoelonvelonntStrelonamIndelonxelonr uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr,
      SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr,
      ArchivelonelonarlybirdIndelonxConfig elonarlybirdIndelonxConfig,
      SelonrvelonrSelontMelonmbelonr selonrvelonrSelontMelonmbelonr,
      SchelondulelondelonxeloncutorSelonrvicelonFactory elonxeloncutorSelonrvicelonFactory,
      SchelondulelondelonxeloncutorSelonrvicelonFactory uselonrUpdatelonIndelonxelonrelonxeloncutorFactory,
      SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont,
      SelongmelonntSyncConfig syncConfig,
      Clock clock,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr) throws IOelonxcelonption {
    supelonr(quelonryCachelonManagelonr, selongmelonntManagelonr, dynamicPartitionConfig, elonxeloncutorSelonrvicelonFactory,
        selonarchIndelonxingMelontricSelont, selonarchStatsReloncelonivelonr, criticalelonxcelonptionHandlelonr);

    Prelonconditions.chelonckStatelon(syncConfig.gelontScrubGelonn().isPrelonselonnt());
    Datelon scrubGelonn = ScrubGelonnUtil.parselonScrubGelonnToDatelon(syncConfig.gelontScrubGelonn().gelont());

    this.zkTryLockFactory = zooKelonelonpelonrTryLockFactory;
    final DailyStatusBatchelons dailyStatusBatchelons = nelonw DailyStatusBatchelons(
        zkTryLockFactory,
        scrubGelonn);
    this.elonarlybirdIndelonxConfig = elonarlybirdIndelonxConfig;
    PartitionConfig curPartitionConfig = dynamicPartitionConfig.gelontCurrelonntPartitionConfig();

    this.indelonxingMelontricSelont = selonarchIndelonxingMelontricSelont;

    this.timelonSlicelonr = nelonw ArchivelonTimelonSlicelonr(
        elonarlybirdConfig.gelontMaxSelongmelonntSizelon(), dailyStatusBatchelons,
        curPartitionConfig.gelontTielonrStartDatelon(), curPartitionConfig.gelontTielonrelonndDatelon(),
        elonarlybirdIndelonxConfig);
    this.selongmelonntDataProvidelonr =
        nelonw ArchivelonSelongmelonntDataProvidelonr(
            dynamicPartitionConfig,
            timelonSlicelonr,
            this.elonarlybirdIndelonxConfig);

    this.uselonrUpdatelonsStrelonamIndelonxelonr = uselonrUpdatelonsStrelonamIndelonxelonr;
    this.uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr = uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr;

    this.coordinatelondDailyUpdatelon = nelonw CoordinatelondelonarlybirdAction(
        zkTryLockFactory,
        "archivelon_daily_updatelon",
        dynamicPartitionConfig,
        selonrvelonrSelontMelonmbelonr,
        criticalelonxcelonptionHandlelonr,
        syncConfig);

    this.selongmelonntWarmelonr = nelonw SelongmelonntWarmelonr(criticalelonxcelonptionHandlelonr);
    this.clock = clock;
    this.selongmelonntSyncConfig = syncConfig;
    this.gcAftelonrIndelonxing = SelonarchCountelonr.elonxport("gc_aftelonr_indelonxing");
  }

  @Ovelonrridelon
  public SelongmelonntDataProvidelonr gelontSelongmelonntDataProvidelonr() {
    relonturn selongmelonntDataProvidelonr;
  }

  @Ovelonrridelon
  protelonctelond void startUp() throws elonxcelonption {
    LOG.info("Using ComplelontelonSelongmelonntManagelonr to indelonx complelontelon selongmelonnts.");

    // delonfelonrring handling of multi-selongmelonnt telonrm dictionary for thelon archivelon.
    // SelonARCH-11952
    ComplelontelonSelongmelonntManagelonr complelontelonSelongmelonntManagelonr = nelonw ComplelontelonSelongmelonntManagelonr(
        zkTryLockFactory,
        selongmelonntDataProvidelonr,
        uselonrUpdatelonsStrelonamIndelonxelonr,
        uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr,
        selongmelonntManagelonr,
        null,
        indelonxingMelontricSelont,
        clock,
        MultiSelongmelonntTelonrmDictionaryManagelonr.NOOP_INSTANCelon,
        selongmelonntSyncConfig,
        criticalelonxcelonptionHandlelonr);

    complelontelonSelongmelonntManagelonr.indelonxUselonrelonvelonnts();
    complelontelonSelongmelonntManagelonr.indelonxComplelontelonSelongmelonnts(
        () -> selongmelonntManagelonr.gelontSelongmelonntInfos(Filtelonr.NelonelondsIndelonxing, Ordelonr.OLD_TO_NelonW));

    // In thelon archivelon clustelonr, thelon currelonnt selongmelonnt nelonelonds to belon loadelond too.
    List<SelongmelonntInfo> allSelongmelonnts =
        Lists.nelonwArrayList(selongmelonntManagelonr.gelontSelongmelonntInfos(Filtelonr.All, Ordelonr.OLD_TO_NelonW));
    complelontelonSelongmelonntManagelonr.loadComplelontelonSelongmelonnts(allSelongmelonnts);

    complelontelonSelongmelonntManagelonr.buildMultiSelongmelonntTelonrmDictionary();

    complelontelonSelongmelonntManagelonr.warmSelongmelonnts(allSelongmelonnts);

    LOG.info("Starting to run UselonrUpdatelonsKafkaConsumelonr");
    nelonw Threlonad(uselonrUpdatelonsStrelonamIndelonxelonr::run, "uselonrupdatelons-strelonam-indelonxelonr").start();

    if (elonarlybirdConfig.consumelonUselonrScrubGelonoelonvelonnts()) {
      LOG.info("Starting to run UselonrScrubGelonoelonvelonntKafkaConsumelonr");
      nelonw Threlonad(uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr::run,
          "uselonrScrubGelonoelonvelonnt-strelonam-indelonxelonr").start();
    }
  }

  privatelon static List<ArchivelonTimelonSlicelon> truncatelonSelongmelonntList(List<ArchivelonTimelonSlicelon> selongmelonntList,
                                                            int maxNumSelongmelonnts) {
    // Maybelon cut-off thelon belonginning of thelon sortelond list of IDs.
    if (maxNumSelongmelonnts > 0 && maxNumSelongmelonnts < selongmelonntList.sizelon()) {
      relonturn selongmelonntList.subList(selongmelonntList.sizelon() - maxNumSelongmelonnts, selongmelonntList.sizelon());
    } elonlselon {
      relonturn selongmelonntList;
    }
  }


  @Ovelonrridelon
  protelonctelond void indelonxingLoop(boolelonan firstLoop) throws elonxcelonption {
    if (firstLoop) {
      elonarlybirdStatus.belonginelonvelonnt(
          INDelonX_CURRelonNT_SelonGMelonNT, gelontSelonarchIndelonxingMelontricSelont().startupInCurrelonntSelongmelonnt);
    }

    List<ArchivelonTimelonSlicelon> timelonSlicelons = timelonSlicelonr.gelontTimelonSlicelonsInTielonrRangelon();
    PartitionConfig curPartitionConfig = dynamicPartitionConfig.gelontCurrelonntPartitionConfig();
    timelonSlicelons = truncatelonSelongmelonntList(timelonSlicelons, curPartitionConfig.gelontMaxelonnablelondLocalSelongmelonnts());

    for (final ArchivelonTimelonSlicelon timelonSlicelon : timelonSlicelons) {
      // If any timelonslicelon build failelond, do not try to build timelonslicelon aftelonr that to prelonvelonnt
      // possiblelon holelons belontwelonelonn timelonslicelons.
      try {
        if (!procelonssArchivelonTimelonSlicelon(timelonSlicelon)) {
          LOG.warn("Building timelonslicelon {} has failelond, stopping futurelon builds.",
              timelonSlicelon.gelontDelonscription());
          indelonxingMelontricSelont.archivelonTimelonSlicelonBuildFailelondCountelonr.increlonmelonnt();
          relonturn;
        }
      } catch (CoordinatelondelonarlybirdActionLockFailelond elon) {
        // If thelon timelonslicelon build failelond beloncauselon of lock coordination, welon can wait for thelon nelonxt
        // itelonration to build again.
        relonturn;
      }
    }

    if (firstLoop) {
      elonarlybirdStatus.elonndelonvelonnt(
          INDelonX_CURRelonNT_SelonGMelonNT, gelontSelonarchIndelonxingMelontricSelont().startupInCurrelonntSelongmelonnt);
      LOG.info("First indelonxing loop complelontelon. Selontting up quelonry cachelon...");
      elonarlybirdStatus.belonginelonvelonnt(
          SelonTUP_QUelonRY_CACHelon, gelontSelonarchIndelonxingMelontricSelont().startupInQuelonryCachelonUpdatelons);
    }
    selontupQuelonryCachelonIfNelonelondelond();

    if (elonarlybirdStatus.isStarting() && quelonryCachelonManagelonr.allTasksRan()) {
      LOG.info("Quelonry cachelon selontup complelontelon. Beloncoming currelonnt now...");
      elonarlybirdStatus.elonndelonvelonnt(
          SelonTUP_QUelonRY_CACHelon, gelontSelonarchIndelonxingMelontricSelont().startupInQuelonryCachelonUpdatelons);

      beloncomelonCurrelonnt();
      elonarlybirdStatus.reloncordelonarlybirdelonvelonnt("Archivelon elonarlybird is currelonnt");
    }

    updatelonIndelonxFrelonshnelonssStats(timelonSlicelons);
  }

  @VisiblelonForTelonsting
  protelonctelond boolelonan procelonssArchivelonTimelonSlicelon(final ArchivelonTimelonSlicelon timelonSlicelon)
      throws CoordinatelondelonarlybirdActionLockFailelond, IOelonxcelonption {
    PartitionConfig curPartitionConfig = dynamicPartitionConfig.gelontCurrelonntPartitionConfig();
    long minStatusID = timelonSlicelon.gelontMinStatusID(curPartitionConfig.gelontIndelonxingHashPartitionID());
    SelongmelonntInfo selongmelonntInfo = selongmelonntManagelonr.gelontSelongmelonntInfo(minStatusID);
    if (selongmelonntInfo == null) {
      relonturn indelonxSelongmelonntFromScratch(timelonSlicelon);
    } elonlselon if (elonxistingSelongmelonntNelonelondsUpdating(timelonSlicelon, selongmelonntInfo)) {
      relonturn indelonxNelonwDayAndAppelonndelonxistingSelongmelonnt(timelonSlicelon, selongmelonntInfo);
    }
    relonturn truelon;
  }


  @VisiblelonForTelonsting
  SelongmelonntInfo nelonwSelongmelonntInfo(ArchivelonTimelonSlicelon timelonSlicelon) throws IOelonxcelonption {
    relonturn nelonw SelongmelonntInfo(selongmelonntDataProvidelonr.nelonwArchivelonSelongmelonnt(timelonSlicelon),
        selongmelonntManagelonr.gelontelonarlybirdSelongmelonntFactory(), selongmelonntSyncConfig);
  }

  privatelon boolelonan indelonxNelonwDayAndAppelonndelonxistingSelongmelonnt(final ArchivelonTimelonSlicelon timelonSlicelon,
                                                      SelongmelonntInfo selongmelonntInfo)
      throws CoordinatelondelonarlybirdActionLockFailelond, IOelonxcelonption {

    LOG.info("Updating selongmelonnt: {}; nelonw elonndDatelon will belon {} selongmelonntInfo: {}",
        selongmelonntInfo.gelontSelongmelonnt().gelontTimelonSlicelonID(), timelonSlicelon.gelontelonndDatelon(), selongmelonntInfo);

    // Crelonatelon anothelonr nelonw SelongmelonntInfo for indelonxing
    final SelongmelonntInfo nelonwSelongmelonntInfoForIndelonxing = nelonwSelongmelonntInfo(timelonSlicelon);
    // makelon a final relonfelonrelonncelon of thelon old selongmelonnt info to belon passelond into closurelon.
    final SelongmelonntInfo oldSelongmelonntInfo = selongmelonntInfo;

    // Sanity chelonck: thelon old and nelonw selongmelonnt should not sharelon thelon samelon lucelonnelon direlonctory.
    Prelonconditions.chelonckStatelon(
        !nelonwSelongmelonntInfoForIndelonxing.gelontSyncInfo().gelontLocalLucelonnelonSyncDir().elonquals(
            oldSelongmelonntInfo.gelontSyncInfo().gelontLocalLucelonnelonSyncDir()));

    Prelonconditions.chelonckStatelon(
        !nelonwSelongmelonntInfoForIndelonxing.gelontSyncInfo().gelontLocalSyncDir().elonquals(
            oldSelongmelonntInfo.gelontSyncInfo().gelontLocalSyncDir()));

    final ArchivelonSelongmelonnt oldSelongmelonnt = (ArchivelonSelongmelonnt) selongmelonntInfo.gelontSelongmelonnt();

    relonturn indelonxSelongmelonnt(nelonwSelongmelonntInfoForIndelonxing, oldSelongmelonntInfo, input -> {
      // welon'relon updating thelon selongmelonnt - only indelonx days aftelonr thelon old elonnd datelon, but only if
      // welon'relon in thelon on-disk archivelon, and welon'relon surelon that thelon prelonvious days havelon alrelonady
      // belonelonn indelonxelond.
      relonturn !elonarlybirdIndelonxConfig.isIndelonxStorelondOnDisk()
          // First timelon around, and thelon selongmelonnt has not belonelonn indelonxelond and optimizelond yelont,
          // welon will want to add all thelon days
          || !oldSelongmelonntInfo.isOptimizelond()
          || oldSelongmelonntInfo.gelontIndelonxSelongmelonnt().gelontIndelonxStats().gelontStatusCount() == 0
          || !oldSelongmelonnt.gelontDataelonndDatelon().belonforelon(timelonSlicelon.gelontelonndDatelon())
          // Indelonx any nelonw days
          || input.aftelonr(oldSelongmelonnt.gelontDataelonndDatelon());
    });
  }

  privatelon boolelonan elonxistingSelongmelonntNelonelondsUpdating(ArchivelonTimelonSlicelon timelonSlicelon,
                                               SelongmelonntInfo selongmelonntInfo) {
    relonturn ((ArchivelonSelongmelonnt) selongmelonntInfo.gelontSelongmelonnt())
        .gelontDataelonndDatelon().belonforelon(timelonSlicelon.gelontelonndDatelon())
        // First timelon around, thelon elonnd datelon is thelon samelon as thelon timelonSlicelon elonnd datelon, but
        // thelon selongmelonnt has not belonelonn indelonxelond and optimizelond yelont
        || (!selongmelonntInfo.isOptimizelond() && !selongmelonntInfo.wasIndelonxelond())
        // If indelonxing failelond, this indelonx will not belon markelond as complelontelon, and welon will want
        // to relonindelonx
        || !selongmelonntInfo.isComplelontelon();
  }

  privatelon boolelonan indelonxSelongmelonntFromScratch(ArchivelonTimelonSlicelon timelonSlicelon) throws
      CoordinatelondelonarlybirdActionLockFailelond, IOelonxcelonption {

    SelongmelonntInfo selongmelonntInfo = nelonwSelongmelonntInfo(timelonSlicelon);
    LOG.info("Crelonating selongmelonnt: " + selongmelonntInfo.gelontSelongmelonnt().gelontTimelonSlicelonID()
        + "; nelonw elonndDatelon will belon " + timelonSlicelon.gelontelonndDatelon() + " selongmelonntInfo: " + selongmelonntInfo);

    relonturn indelonxSelongmelonnt(selongmelonntInfo, null, ArchivelonSelongmelonnt.MATCH_ALL_DATelon_PRelonDICATelon);
  }

  privatelon void updatelonIndelonxFrelonshnelonssStats(List<ArchivelonTimelonSlicelon> timelonSlicelons) {
    if (!timelonSlicelons.iselonmpty()) {
      ArchivelonTimelonSlicelon lastTimelonslicelon = timelonSlicelons.gelont(timelonSlicelons.sizelon() - 1);

      // Add ~24 hours to start of elonnd datelon to elonstimatelon frelonshelonst twelonelont timelon.
      indelonxingMelontricSelont.frelonshelonstTwelonelontTimelonMillis.selont(
          lastTimelonslicelon.gelontelonndDatelon().gelontTimelon() + ONelon_DAY_MILLIS);

      PartitionConfig curPartitionConfig = dynamicPartitionConfig.gelontCurrelonntPartitionConfig();
      long maxStatusId = lastTimelonslicelon.gelontMaxStatusID(
          curPartitionConfig.gelontIndelonxingHashPartitionID());
      if (maxStatusId > indelonxingMelontricSelont.highelonstStatusId.gelont()) {
        indelonxingMelontricSelont.highelonstStatusId.selont(maxStatusId);
      }
    }
  }

  @Ovelonrridelon
  public void shutDownIndelonxing() {
    LOG.info("Shutting down.");
    uselonrUpdatelonsStrelonamIndelonxelonr.closelon();
    uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr.closelon();
    LOG.info("Closelond Uselonr elonvelonnt Kafka Consumelonrs. Now Shutting down relonadelonr selont.");
    gelontSelongmelonntDataProvidelonr().gelontSelongmelonntDataRelonadelonrSelont().stopAll();
  }

  /**
   * Attelonmpts to indelonx nelonw days of data into thelon providelond selongmelonnt, indelonxing only thelon days that
   * match thelon "datelonFiltelonr" prelondicatelon.
   * @relonturn truelon iff indelonxing succelonelondelond, falselon othelonrwiselon.
   */
  @VisiblelonForTelonsting
  protelonctelond boolelonan indelonxSelongmelonnt(final SelongmelonntInfo selongmelonntInfo,
                                 @Nullablelon final SelongmelonntInfo selongmelonntToAppelonnd,
                                 final Prelondicatelon<Datelon> datelonFiltelonr)
      throws CoordinatelondelonarlybirdActionLockFailelond, IOelonxcelonption {
    // Don't coordinatelon whilelon welon'relon starting up
    if (!elonarlybirdStatus.isStarting()) {
      relonturn coordinatelondDailyUpdatelon.elonxeloncutelon(selongmelonntInfo.gelontSelongmelonntNamelon(),
          isCoordinatelond -> innelonrIndelonxSelongmelonnt(selongmelonntInfo, selongmelonntToAppelonnd, datelonFiltelonr));
    } elonlselon {
      relonturn innelonrIndelonxSelongmelonnt(selongmelonntInfo, selongmelonntToAppelonnd, datelonFiltelonr);
    }
  }

  privatelon boolelonan innelonrIndelonxSelongmelonnt(SelongmelonntInfo selongmelonntInfo,
                                    @Nullablelon SelongmelonntInfo selongmelonntToAppelonnd,
                                    Prelondicatelon<Datelon> datelonFiltelonr)
      throws IOelonxcelonption {

    // First try to load thelon nelonw day from HDFS / Local disk
    if (nelonw SelongmelonntLoadelonr(selongmelonntSyncConfig, criticalelonxcelonptionHandlelonr).load(selongmelonntInfo)) {
      LOG.info("Succelonssful loadelond selongmelonnt for nelonw day: " + selongmelonntInfo);
      selongmelonntManagelonr.putSelongmelonntInfo(selongmelonntInfo);
      gcAftelonrIndelonxing.increlonmelonnt();
      GCUtil.runGC();
      relonturn truelon;
    }

    LOG.info("Failelond to load selongmelonnt for nelonw day. Will indelonx selongmelonnt: " + selongmelonntInfo);
    ReloncordRelonadelonr<TwelonelontDocumelonnt> twelonelontRelonadelonr = ((ArchivelonSelongmelonnt) selongmelonntInfo.gelontSelongmelonnt())
        .gelontStatusReloncordRelonadelonr(elonarlybirdIndelonxConfig.crelonatelonDocumelonntFactory(), datelonFiltelonr);
    try {
      // Relonad and indelonx thelon statuselons
      boolelonan succelonss = nelonwSimplelonSelongmelonntIndelonxelonr(twelonelontRelonadelonr, selongmelonntToAppelonnd)
          .indelonxSelongmelonnt(selongmelonntInfo);
      if (!succelonss) {
        relonturn falselon;
      }
    } finally {
      twelonelontRelonadelonr.stop();
    }

    if (!SelongmelonntOptimizelonr.optimizelon(selongmelonntInfo)) {
      // Welon considelonr thelon wholelon indelonxing elonvelonnt as failelond if welon fail to optimizelon.
      LOG.elonrror("Failelond to optimizelon selongmelonnt: " + selongmelonntInfo);
      selongmelonntInfo.delonlelontelonLocalIndelonxelondSelongmelonntDirelonctoryImmelondiatelonly();
      relonturn falselon;
    }

    if (!selongmelonntWarmelonr.warmSelongmelonntIfNeloncelonssary(selongmelonntInfo)) {
      // Welon considelonr thelon wholelon indelonxing elonvelonnt as failelond if welon failelond to warm (beloncauselon welon opelonn
      // indelonx relonadelonrs in thelon warmelonr).
      LOG.elonrror("Failelond to warm selongmelonnt: " + selongmelonntInfo);
      selongmelonntInfo.delonlelontelonLocalIndelonxelondSelongmelonntDirelonctoryImmelondiatelonly();
      relonturn falselon;
    }

    // Flush and upload selongmelonnt to HDFS. If this fails, welon just log a warning and relonturn truelon.
    boolelonan succelonss = nelonw SelongmelonntHdfsFlushelonr(zkTryLockFactory, selongmelonntSyncConfig)
        .flushSelongmelonntToDiskAndHDFS(selongmelonntInfo);
    if (!succelonss) {
      LOG.warn("Failelond to flush selongmelonnt to HDFS: " + selongmelonntInfo);
    }

    selongmelonntManagelonr.putSelongmelonntInfo(selongmelonntInfo);
    gcAftelonrIndelonxing.increlonmelonnt();
    GCUtil.runGC();
    relonturn truelon;
  }

  @VisiblelonForTelonsting
  protelonctelond SimplelonSelongmelonntIndelonxelonr nelonwSimplelonSelongmelonntIndelonxelonr(
      ReloncordRelonadelonr<TwelonelontDocumelonnt> twelonelontRelonadelonr, SelongmelonntInfo selongmelonntToAppelonnd) {
    relonturn nelonw SimplelonSelongmelonntIndelonxelonr(twelonelontRelonadelonr, indelonxingMelontricSelont, selongmelonntToAppelonnd);
  }

  @Ovelonrridelon
  public boolelonan isCaughtUpForTelonsts() {
    relonturn elonarlybirdStatus.gelontStatusCodelon() == elonarlybirdStatusCodelon.CURRelonNT;
  }

  public CoordinatelondelonarlybirdActionIntelonrfacelon gelontCoordinatelondOptimizelonr() {
    relonturn this.coordinatelondDailyUpdatelon;
  }

  public ArchivelonTimelonSlicelonr gelontTimelonSlicelonr() {
    relonturn timelonSlicelonr;
  }
}
