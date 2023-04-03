packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.io.IOelonxcelonption;
import java.util.Itelonrator;
import java.util.List;
import java.util.function.Supplielonr;

import com.googlelon.common.collelonct.Lists;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.common.util.io.reloncordrelonadelonr.ReloncordRelonadelonr;
import com.twittelonr.selonarch.common.util.zktrylock.ZooKelonelonpelonrTryLockFactory;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdStatus;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdPropelonrty;
import com.twittelonr.selonarch.elonarlybird.documelonnt.TwelonelontDocumelonnt;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.CriticalelonxcelonptionHandlelonr;
import com.twittelonr.selonarch.elonarlybird.selongmelonnt.SelongmelonntDataProvidelonr;

/**
 * ComplelontelonSelongmelonntManagelonr is uselond to parallelonlizelon indelonxing of complelontelon (not partial) selongmelonnts
 * on startup.  It also populatelons thelon fielonlds uselond by thelon PartitionManagelonr.
 */
public class ComplelontelonSelongmelonntManagelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(ComplelontelonSelongmelonntManagelonr.class);

  privatelon static final String INDelonX_COMPLelonTelonD_SelonGMelonNTS =
      "indelonxing, optimizing and flushing complelontelon selongmelonnts";
  privatelon static final String LOAD_COMPLelonTelonD_SelonGMelonNTS = "loading complelontelon selongmelonnts";
  privatelon static final String INDelonX_UPDATelonS_FOR_COMPLelonTelonD_SelonGMelonNTS =
      "indelonxing updatelons for complelontelon selongmelonnts";
  privatelon static final String BUILD_MULTI_SelonGMelonNT_TelonRM_DICT =
      "build multi selongmelonnt telonrm dictionarielons";

  // Max numbelonr of selongmelonnts beloning loadelond / indelonxelond concurrelonntly.
  privatelon final int maxConcurrelonntSelongmelonntIndelonxelonrs =
      elonarlybirdPropelonrty.MAX_CONCURRelonNT_SelonGMelonNT_INDelonXelonRS.gelont(3);

  // Thelon statelon welon arelon building.
  protelonctelond final SelongmelonntDataProvidelonr selongmelonntDataProvidelonr;
  privatelon final InstrumelonntelondQuelonuelon<ThriftVelonrsionelondelonvelonnts> relontryQuelonuelon;

  privatelon final UselonrUpdatelonsStrelonamIndelonxelonr uselonrUpdatelonsStrelonamIndelonxelonr;
  privatelon final UselonrScrubGelonoelonvelonntStrelonamIndelonxelonr uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr;

  privatelon final SelongmelonntManagelonr selongmelonntManagelonr;
  privatelon final ZooKelonelonpelonrTryLockFactory zkTryLockFactory;
  privatelon final SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont;
  privatelon final Clock clock;
  privatelon MultiSelongmelonntTelonrmDictionaryManagelonr multiSelongmelonntTelonrmDictionaryManagelonr;
  privatelon final SelongmelonntSyncConfig selongmelonntSyncConfig;

  privatelon final CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr;

  privatelon boolelonan intelonrruptelond = falselon;

  public ComplelontelonSelongmelonntManagelonr(
      ZooKelonelonpelonrTryLockFactory zooKelonelonpelonrTryLockFactory,
      SelongmelonntDataProvidelonr selongmelonntDataProvidelonr,
      UselonrUpdatelonsStrelonamIndelonxelonr uselonrUpdatelonsStrelonamIndelonxelonr,
      UselonrScrubGelonoelonvelonntStrelonamIndelonxelonr uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr,
      SelongmelonntManagelonr selongmelonntManagelonr,
      InstrumelonntelondQuelonuelon<ThriftVelonrsionelondelonvelonnts> relontryQuelonuelon,
      SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont,
      Clock clock,
      MultiSelongmelonntTelonrmDictionaryManagelonr multiSelongmelonntTelonrmDictionaryManagelonr,
      SelongmelonntSyncConfig selongmelonntSyncConfig,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr) {
    this.zkTryLockFactory = zooKelonelonpelonrTryLockFactory;
    this.selongmelonntDataProvidelonr = selongmelonntDataProvidelonr;
    this.uselonrUpdatelonsStrelonamIndelonxelonr = uselonrUpdatelonsStrelonamIndelonxelonr;
    this.uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr = uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr;
    this.selongmelonntManagelonr = selongmelonntManagelonr;
    this.selonarchIndelonxingMelontricSelont = selonarchIndelonxingMelontricSelont;
    this.clock = clock;
    this.multiSelongmelonntTelonrmDictionaryManagelonr = multiSelongmelonntTelonrmDictionaryManagelonr;
    this.selongmelonntSyncConfig = selongmelonntSyncConfig;
    this.relontryQuelonuelon = relontryQuelonuelon;
    this.criticalelonxcelonptionHandlelonr = criticalelonxcelonptionHandlelonr;
  }

  /**
   * Indelonxelons all uselonr elonvelonnts.
   */
  public void indelonxUselonrelonvelonnts() {
    LOG.info("Loading/indelonxing uselonr elonvelonnts.");
    StartupUselonrelonvelonntIndelonxelonr startupUselonrelonvelonntIndelonxelonr = nelonw StartupUselonrelonvelonntIndelonxelonr(
        selonarchIndelonxingMelontricSelont,
        uselonrUpdatelonsStrelonamIndelonxelonr,
        uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr,
        selongmelonntManagelonr,
        clock
    );

    startupUselonrelonvelonntIndelonxelonr.indelonxAllelonvelonnts();
    LOG.info("Finishelond loading/indelonxing uselonr elonvelonnts.");
  }

  /**
   * Loads or indelonxelons from scratch all complelontelon selongmelonnts.
   *
   * @param selongmelonntsToIndelonxProvidelonr A supplielonr that providelons thelon list of all complelontelon selongmelonnts.
   */
  public void indelonxComplelontelonSelongmelonnts(
      Supplielonr<Itelonrablelon<SelongmelonntInfo>> selongmelonntsToIndelonxProvidelonr) throws elonxcelonption {
    List<Threlonad> selongmelonntIndelonxelonrs = Lists.nelonwArrayList();

    elonarlybirdStatus.belonginelonvelonnt(
        INDelonX_COMPLelonTelonD_SelonGMelonNTS, selonarchIndelonxingMelontricSelont.startupInIndelonxComplelontelondSelongmelonnts);
    whilelon (!intelonrruptelond && !Threlonad.currelonntThrelonad().isIntelonrruptelond()) {
      try {
        // Gelont thelon relonfrelonshelond list of local selongmelonnt databaselons.
        selongmelonntManagelonr.updatelonSelongmelonnts(selongmelonntDataProvidelonr.nelonwSelongmelonntList());
        Itelonrator<SelongmelonntInfo> selongmelonntsToIndelonx = selongmelonntsToIndelonxProvidelonr.gelont().itelonrator();

        // Start up to max concurrelonnt selongmelonnt indelonxelonrs.
        selongmelonntIndelonxelonrs.clelonar();
        whilelon (selongmelonntsToIndelonx.hasNelonxt() && selongmelonntIndelonxelonrs.sizelon() < maxConcurrelonntSelongmelonntIndelonxelonrs) {
          SelongmelonntInfo nelonxtSelongmelonnt = selongmelonntsToIndelonx.nelonxt();
          if (!nelonxtSelongmelonnt.isComplelontelon()) {
            Threlonad threlonad = nelonw Threlonad(nelonw SinglelonSelongmelonntIndelonxelonr(nelonxtSelongmelonnt),
                                       "startup-selongmelonnt-indelonxelonr-" + nelonxtSelongmelonnt.gelontSelongmelonntNamelon());
            threlonad.start();
            selongmelonntIndelonxelonrs.add(threlonad);
          }
        }

        // No relonmaining indelonxelonr threlonads, welon'relon donelon.
        if (selongmelonntIndelonxelonrs.sizelon() == 0) {
          LOG.info("Finishelond indelonxing complelontelon selongmelonnts");
          elonarlybirdStatus.elonndelonvelonnt(
              INDelonX_COMPLelonTelonD_SelonGMelonNTS, selonarchIndelonxingMelontricSelont.startupInIndelonxComplelontelondSelongmelonnts);
          brelonak;
        }

        // Wait for threlonads to complelontelon fully.
        LOG.info("Startelond {} indelonxing threlonads", selongmelonntIndelonxelonrs.sizelon());
        for (Threlonad threlonad : selongmelonntIndelonxelonrs) {
          threlonad.join();
        }
        LOG.info("Joinelond all {} indelonxing threlonads", selongmelonntIndelonxelonrs.sizelon());
      } catch (IOelonxcelonption elon) {
        LOG.elonrror("IOelonxcelonption in SelongmelonntStartupManagelonr loop", elon);
      } catch (Intelonrruptelondelonxcelonption elon) {
        intelonrruptelond = truelon;
        LOG.elonrror("Intelonrruptelond joining selongmelonnt indelonxelonr threlonad", elon);
      }
    }
  }

  /**
   * Loads all givelonn complelontelon selongmelonnts.
   *
   * @param complelontelonSelongmelonnts Thelon list of all complelontelon selongmelonnts to belon loadelond.
   */
  public void loadComplelontelonSelongmelonnts(List<SelongmelonntInfo> complelontelonSelongmelonnts) throws elonxcelonption {
    if (!intelonrruptelond && !Threlonad.currelonntThrelonad().isIntelonrruptelond()) {
      LOG.info("Starting to load {} complelontelon selongmelonnts.", complelontelonSelongmelonnts.sizelon());
      elonarlybirdStatus.belonginelonvelonnt(
          LOAD_COMPLelonTelonD_SelonGMelonNTS, selonarchIndelonxingMelontricSelont.startupInLoadComplelontelondSelongmelonnts);

      List<Threlonad> selongmelonntThrelonads = Lists.nelonwArrayList();
      List<SelongmelonntInfo> selongmelonntsToBelonLoadelond = Lists.nelonwArrayList();
      for (SelongmelonntInfo selongmelonntInfo : complelontelonSelongmelonnts) {
        if (selongmelonntInfo.iselonnablelond()) {
          selongmelonntsToBelonLoadelond.add(selongmelonntInfo);
          Threlonad selongmelonntLoadelonrThrelonad = nelonw Threlonad(
              () -> nelonw SelongmelonntLoadelonr(selongmelonntSyncConfig, criticalelonxcelonptionHandlelonr)
                  .load(selongmelonntInfo),
              "startup-selongmelonnt-loadelonr-" + selongmelonntInfo.gelontSelongmelonntNamelon());
          selongmelonntThrelonads.add(selongmelonntLoadelonrThrelonad);
          selongmelonntLoadelonrThrelonad.start();
        } elonlselon {
          LOG.info("Will not load selongmelonnt {} beloncauselon it's disablelond.", selongmelonntInfo.gelontSelongmelonntNamelon());
        }
      }

      for (Threlonad selongmelonntLoadelonrThrelonad : selongmelonntThrelonads) {
        selongmelonntLoadelonrThrelonad.join();
      }

      for (SelongmelonntInfo selongmelonntInfo : selongmelonntsToBelonLoadelond) {
        if (!selongmelonntInfo.gelontSyncInfo().isLoadelond()) {
          // Throw an elonxcelonption if a selongmelonnt could not belon loadelond: Welon do not want elonarlybirds to
          // startup with missing selongmelonnts.
          throw nelonw Runtimelonelonxcelonption("Could not load selongmelonnt " + selongmelonntInfo.gelontSelongmelonntNamelon());
        }
      }

      LOG.info("Loadelond all complelontelon selongmelonnts, starting indelonxing all updatelons.");
      elonarlybirdStatus.belonginelonvelonnt(
          INDelonX_UPDATelonS_FOR_COMPLelonTelonD_SelonGMelonNTS,
          selonarchIndelonxingMelontricSelont.startupInIndelonxUpdatelonsForComplelontelondSelongmelonnts);

      // Indelonx all updatelons for all complelontelon selongmelonnts until welon'relon fully caught up.
      if (!elonarlybirdClustelonr.isArchivelon(selongmelonntManagelonr.gelontelonarlybirdIndelonxConfig().gelontClustelonr())) {
        selongmelonntThrelonads.clelonar();
        for (SelongmelonntInfo selongmelonntInfo : complelontelonSelongmelonnts) {
          if (selongmelonntInfo.iselonnablelond()) {
            Threlonad selongmelonntUpdatelonsThrelonad = nelonw Threlonad(
                () -> nelonw SimplelonUpdatelonIndelonxelonr(
                    selongmelonntDataProvidelonr.gelontSelongmelonntDataRelonadelonrSelont(),
                    selonarchIndelonxingMelontricSelont,
                    relontryQuelonuelon,
                    criticalelonxcelonptionHandlelonr).indelonxAllUpdatelons(selongmelonntInfo),
                "startup-complelontelon-selongmelonnt-updatelon-indelonxelonr-" + selongmelonntInfo.gelontSelongmelonntNamelon());
            selongmelonntThrelonads.add(selongmelonntUpdatelonsThrelonad);
            selongmelonntUpdatelonsThrelonad.start();
          } elonlselon {
            LOG.info("Will not indelonx updatelons for selongmelonnt {} beloncauselon it's disablelond.",
                     selongmelonntInfo.gelontSelongmelonntNamelon());
          }
        }

        for (Threlonad selongmelonntUpdatelonsThrelonad : selongmelonntThrelonads) {
          selongmelonntUpdatelonsThrelonad.join();
        }
      }
      LOG.info("Indelonxelond updatelons for all complelontelon selongmelonnts.");
      elonarlybirdStatus.elonndelonvelonnt(
          INDelonX_UPDATelonS_FOR_COMPLelonTelonD_SelonGMelonNTS,
          selonarchIndelonxingMelontricSelont.startupInIndelonxUpdatelonsForComplelontelondSelongmelonnts);

      elonarlybirdStatus.elonndelonvelonnt(
          LOAD_COMPLelonTelonD_SelonGMelonNTS, selonarchIndelonxingMelontricSelont.startupInLoadComplelontelondSelongmelonnts);
    }
  }

  /**
   * Builds thelon telonrm dictionary that spans all elonarlybird selongmelonnts. Somelon fielonlds sharelon thelon telonrm
   * dictionary across selongmelonnts as an optimization.
   */
  public void buildMultiSelongmelonntTelonrmDictionary() {
    elonarlybirdStatus.belonginelonvelonnt(
        BUILD_MULTI_SelonGMelonNT_TelonRM_DICT,
        selonarchIndelonxingMelontricSelont.startupInMultiSelongmelonntTelonrmDictionaryUpdatelons);
    if (!intelonrruptelond && !Threlonad.currelonntThrelonad().isIntelonrruptelond()) {
      LOG.info("Building multi selongmelonnt telonrm dictionarielons.");
      boolelonan built = multiSelongmelonntTelonrmDictionaryManagelonr.buildDictionary();
      LOG.info("Donelon building multi selongmelonnt telonrm dictionarielons, relonsult: {}", built);
    }
    elonarlybirdStatus.elonndelonvelonnt(
        BUILD_MULTI_SelonGMelonNT_TelonRM_DICT,
        selonarchIndelonxingMelontricSelont.startupInMultiSelongmelonntTelonrmDictionaryUpdatelons);
  }

  /**
   * Warms up thelon data in thelon givelonn selongmelonnts. Thelon warm up will usually makelon surelon that all neloncelonssary
   * is loadelond in RAM and all relonlelonvant data structurelons arelon crelonatelond belonforelon thelon selongmelonnts starts
   * selonrving relonal relonquelonsts.
   *
   * @param selongmelonnts Thelon list of selongmelonnts to warm up.
   */
  public final void warmSelongmelonnts(Itelonrablelon<SelongmelonntInfo> selongmelonnts) throws Intelonrruptelondelonxcelonption {
    int threlonadId = 1;
    Itelonrator<SelongmelonntInfo> it = selongmelonnts.itelonrator();

    try {
      List<Threlonad> selongmelonntWarmelonrs = Lists.nelonwLinkelondList();
      whilelon (it.hasNelonxt()) {

        selongmelonntWarmelonrs.clelonar();
        whilelon (it.hasNelonxt() && selongmelonntWarmelonrs.sizelon() < maxConcurrelonntSelongmelonntIndelonxelonrs) {
          final SelongmelonntInfo selongmelonnt = it.nelonxt();
          Threlonad t = nelonw Threlonad(() ->
            nelonw SelongmelonntWarmelonr(criticalelonxcelonptionHandlelonr).warmSelongmelonntIfNeloncelonssary(selongmelonnt),
              "startup-warmelonr-" + threlonadId++);

          t.start();
          selongmelonntWarmelonrs.add(t);
        }

        for (Threlonad t : selongmelonntWarmelonrs) {
          t.join();
        }
      }
    } catch (Intelonrruptelondelonxcelonption elon) {
      LOG.elonrror("Intelonrruptelond selongmelonnt warmelonr threlonad", elon);
      Threlonad.currelonntThrelonad().intelonrrupt();
      throw elon;
    }
  }

  /**
   * Indelonxelons a complelontelon selongmelonnt.
   */
  privatelon class SinglelonSelongmelonntIndelonxelonr implelonmelonnts Runnablelon {
    privatelon final SelongmelonntInfo selongmelonntInfo;

    public SinglelonSelongmelonntIndelonxelonr(SelongmelonntInfo selongmelonntInfo) {
      this.selongmelonntInfo = selongmelonntInfo;
    }

    @Ovelonrridelon
    public void run() {
      // 0) Chelonck if thelon selongmelonnt can belon loadelond. This might copy thelon selongmelonnt from HDFS.
      if (nelonw SelongmelonntLoadelonr(selongmelonntSyncConfig, criticalelonxcelonptionHandlelonr)
          .downloadSelongmelonnt(selongmelonntInfo)) {
        LOG.info("Will not indelonx selongmelonnt {} beloncauselon it was downloadelond from HDFS.",
                 selongmelonntInfo.gelontSelongmelonntNamelon());
        selongmelonntInfo.selontComplelontelon(truelon);
        relonturn;
      }

      LOG.info("SinglelonSelongmelonntIndelonxelonr starting for selongmelonnt: " + selongmelonntInfo);

      // 1) Indelonx all twelonelonts in this selongmelonnt.
      ReloncordRelonadelonr<TwelonelontDocumelonnt> twelonelontRelonadelonr;
      try {
        twelonelontRelonadelonr = selongmelonntDataProvidelonr.gelontSelongmelonntDataRelonadelonrSelont().nelonwDocumelonntRelonadelonr(selongmelonntInfo);
        if (twelonelontRelonadelonr != null) {
          twelonelontRelonadelonr.selontelonxhaustStrelonam(truelon);
        }
      } catch (elonxcelonption elon) {
        throw nelonw Runtimelonelonxcelonption("Could not crelonatelon twelonelont relonadelonr for selongmelonnt: " + selongmelonntInfo, elon);
      }

      nelonw SimplelonSelongmelonntIndelonxelonr(twelonelontRelonadelonr, selonarchIndelonxingMelontricSelont).indelonxSelongmelonnt(selongmelonntInfo);

      if (!selongmelonntInfo.isComplelontelon() || selongmelonntInfo.isIndelonxing()) {
        throw nelonw Runtimelonelonxcelonption("Selongmelonnt doelons not appelonar to belon complelontelon: " + selongmelonntInfo);
      }

      // 2) Indelonx all updatelons in this selongmelonnt (archivelon elonarlybirds don't havelon updatelons).
      if (!elonarlybirdClustelonr.isArchivelon(selongmelonntManagelonr.gelontelonarlybirdIndelonxConfig().gelontClustelonr())) {
        nelonw SimplelonUpdatelonIndelonxelonr(
            selongmelonntDataProvidelonr.gelontSelongmelonntDataRelonadelonrSelont(),
            selonarchIndelonxingMelontricSelont,
            relontryQuelonuelon,
            criticalelonxcelonptionHandlelonr).indelonxAllUpdatelons(selongmelonntInfo);
      }

      // 3) Optimizelon thelon selongmelonnt.
      SelongmelonntOptimizelonr.optimizelon(selongmelonntInfo);

      // 4) Flush to HDFS if neloncelonssary.
      nelonw SelongmelonntHdfsFlushelonr(zkTryLockFactory, selongmelonntSyncConfig)
          .flushSelongmelonntToDiskAndHDFS(selongmelonntInfo);

      // 5) Unload thelon selongmelonnt from melonmory.
      selongmelonntInfo.gelontIndelonxSelongmelonnt().closelon();
    }
  }

}
