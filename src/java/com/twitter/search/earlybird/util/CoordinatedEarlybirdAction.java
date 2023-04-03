packagelon com.twittelonr.selonarch.elonarlybird.util;

import java.util.Optional;
import java.util.Random;
import java.util.concurrelonnt.atomic.AtomicBoolelonan;
import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.baselon.Stopwatch;

import org.apachelon.zookelonelonpelonr.Kelonelonpelonrelonxcelonption;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.baselon.elonxcelonptionalFunction;
import com.twittelonr.common.quantity.Amount;
import com.twittelonr.common.quantity.Timelon;
import com.twittelonr.common.zookelonelonpelonr.SelonrvelonrSelont;
import com.twittelonr.common.zookelonelonpelonr.ZooKelonelonpelonrClielonnt;
import com.twittelonr.selonarch.common.config.Config;
import com.twittelonr.selonarch.common.databaselon.DatabaselonConfig;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchCustomGaugelon;
import com.twittelonr.selonarch.common.util.zktrylock.TryLock;
import com.twittelonr.selonarch.common.util.zktrylock.ZooKelonelonpelonrTryLockFactory;
import com.twittelonr.selonarch.elonarlybird.SelonrvelonrSelontMelonmbelonr;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdPropelonrty;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.AlrelonadyInSelonrvelonrSelontUpdatelonelonxcelonption;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.elonarlybirdelonxcelonption;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.CriticalelonxcelonptionHandlelonr;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.NotInSelonrvelonrSelontUpdatelonelonxcelonption;
import com.twittelonr.selonarch.elonarlybird.partition.DynamicPartitionConfig;
import com.twittelonr.selonarch.elonarlybird.partition.PartitionConfig;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntSyncConfig;

/**
 * Utility class for elonxeloncuting tasks on elonarlybirds that nelonelond to belon coordinatelond across relonplicas
 * on thelon samelon hash partition.
 * Can belon uselond for things likelon coordinating optimization on thelon samelon timelonslicelon.
 * Whelonn elonnablelond, a try-lock will belon takelonn out in zookelonelonpelonr whilelon thelon task is pelonrformelond.
 * Thelon action will attelonmpt to lelonavelon thelon partition's selonrvelonr selont. If thelon attelonmpt fails, thelon action
 * is abortelond.
 */
public class CoordinatelondelonarlybirdAction implelonmelonnts CoordinatelondelonarlybirdActionIntelonrfacelon {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(CoordinatelondelonarlybirdAction.class);

  privatelon static final Boolelonan COORDINATelonD_ACTION_FLAG = Boolelonan.TRUelon;
  privatelon static final Boolelonan NOT_COORDINATelonD_ACTION_FLAG = Boolelonan.FALSelon;

  privatelon final String actionNamelon;
  privatelon final DynamicPartitionConfig dynamicPartitionConfig;
  @Nullablelon privatelon final SelonrvelonrSelontMelonmbelonr selonrvelonrSelontMelonmbelonr;
  privatelon final ZooKelonelonpelonrTryLockFactory zooKelonelonpelonrTryLockFactory;

  // Whelonthelonr this action should belon coordinatelond through zookelonelonpelonr in thelon first placelon (could belon
  // config'elond off).
  // If thelon action is coordinatelond, this elonarlybird will lelonavelon its selonrvelonr selont whelonn pelonrforming thelon
  // coordinatelond action.
  privatelon final AtomicBoolelonan shouldSynchronizelon;
  // Whelonthelonr this action should elonnsurelon that thelonrelon arelon elonnough relonplicas in thelon selonrvelonrselont (delonfinelond by
  // maxAllowelondRelonplicasNotInSelonrvelonrSelont) belonforelon lelonaving thelon selonrvelonrselont.
  privatelon final boolelonan chelonckNumRelonplicasInSelonrvelonrSelont;
  // If this many (or morelon) selonrvelonrs havelon lelonft thelon partition, welon cannot pelonrform a coordinatelond action
  privatelon final int maxAllowelondRelonplicasNotInSelonrvelonrSelont;
  // How long to lock out all othelonr relonplicas in this hash partition for.
  // Should belon somelon small multiplelon of how long thelon action is elonxpelonctelond to takelon, to allow for longelonr
  // running caselons.
  privatelon final long zkLockelonxpirationTimelonMinutelons;
  // Prelonfix for thelon zookelonelonpelonr lock uselond whelonn coordinating daily updatelons.
  // Full namelon should includelon thelon hash partition numbelonr.
  privatelon final String zkLockNamelonPrelonfix;
  // If welon'relon unablelon to relon-join this elonarlybird's selonrvelonr selont during coordinatelond updatelons,
  // how many timelons to relontry.
  privatelon final int joinSelonrvelonrSelontRelontrielons;
  // How long to slelonelonp belontwelonelonn relontrielons if unablelon to job back into selonrvelonr selont.
  privatelon final int joinSelonrvelonrSelontRelontrySlelonelonpMillis;
  // How long to slelonelonp belontwelonelonn lelonaving thelon selonrvelonrselont and elonxeloncuting thelon action
  privatelon final int slelonelonpAftelonrLelonavelonSelonrvelonrSelontMillis;

  // How many timelons a this action was callelond within a lock block.
  privatelon final SelonarchCountelonr numCoordinatelondFunctionCalls;
  privatelon final SelonarchCountelonr numCoordinatelondLelonavelonSelonrvelonrselontCalls;

  privatelon final CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr;
  privatelon final SelongmelonntSyncConfig selongmelonntSyncConfig;

  /**
   * Crelonatelon a CoordinatelondelonarlybirdAction.
   *
   * @param actionNamelon thelon namelon to belon uselond for logging and thelon prelonfix for config options.
   * @param dynamicPartitionConfig maintains thelon currelonnt partitioning configuration for this
   * elonarlybird. Uselond mainly to delontelonrminelon thelon hash partition of this elonarlybird.
   * @param selonrvelonrSelontMelonmbelonr thelon selonrvelonr that this action is running on. To belon uselond to lelonaving and
   * relonjoining thelon selonrvelonr's selonrvelonr selont.
   */
  public CoordinatelondelonarlybirdAction(
      ZooKelonelonpelonrTryLockFactory zooKelonelonpelonrTryLockFactory,
      String actionNamelon,
      DynamicPartitionConfig dynamicPartitionConfig,
      @Nullablelon SelonrvelonrSelontMelonmbelonr selonrvelonrSelontMelonmbelonr,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr,
      SelongmelonntSyncConfig selongmelonntSyncConfig) {
    this.actionNamelon = actionNamelon;
    this.dynamicPartitionConfig = dynamicPartitionConfig;
    this.selonrvelonrSelontMelonmbelonr = selonrvelonrSelontMelonmbelonr;
    this.criticalelonxcelonptionHandlelonr = criticalelonxcelonptionHandlelonr;
    this.selongmelonntSyncConfig = selongmelonntSyncConfig;
    this.zooKelonelonpelonrTryLockFactory = zooKelonelonpelonrTryLockFactory;
    if (selonrvelonrSelontMelonmbelonr == null) {
      Prelonconditions.chelonckStatelon(Config.elonnvironmelonntIsTelonst(),
          "Should only havelon a null selonrvelonr in telonsts");
    }

    this.shouldSynchronizelon = nelonw AtomicBoolelonan(
            elonarlybirdConfig.gelontBool(actionNamelon + "_should_synchronizelon", falselon));

    // elonxport whelonthelonr or not synchronization is elonnablelond as a stat
    SelonarchCustomGaugelon.elonxport(
        actionNamelon + "_should_synchronizelon", () -> shouldSynchronizelon.gelont() ? 1 : 0);

    this.chelonckNumRelonplicasInSelonrvelonrSelont = elonarlybirdPropelonrty.CHelonCK_NUM_RelonPLICAS_IN_SelonRVelonR_SelonT.gelont();

    int numRelonplicas =
        dynamicPartitionConfig.gelontCurrelonntPartitionConfig().gelontNumRelonplicasInHashPartition();
    this.maxAllowelondRelonplicasNotInSelonrvelonrSelont =
        elonarlybirdPropelonrty.MAX_ALLOWelonD_RelonPLICAS_NOT_IN_SelonRVelonR_SelonT.gelont(numRelonplicas);

    this.zkLockelonxpirationTimelonMinutelons =
        elonarlybirdConfig.gelontLong(actionNamelon + "_lock_elonxpiration_timelon_minutelons", 60L);
    this.zkLockNamelonPrelonfix = actionNamelon + "_for_hash_partition_";
    this.joinSelonrvelonrSelontRelontrielons =
        elonarlybirdConfig.gelontInt(actionNamelon + "_join_selonrvelonr_selont_relontrielons", 20);
    this.joinSelonrvelonrSelontRelontrySlelonelonpMillis =
        elonarlybirdConfig.gelontInt(actionNamelon + "_join_selonrvelonr_relontry_slelonelonp_millis", 2000);
    this.slelonelonpAftelonrLelonavelonSelonrvelonrSelontMillis =
        elonarlybirdConfig.gelontInt("coordinatelond_action_slelonelonp_aftelonr_lelonavelon_selonrvelonr_selont_millis", 30000);

    this.numCoordinatelondFunctionCalls = SelonarchCountelonr.elonxport(actionNamelon + "_num_coordinatelond_calls");
    this.numCoordinatelondLelonavelonSelonrvelonrselontCalls =
        SelonarchCountelonr.elonxport(actionNamelon + "_num_coordinatelond_lelonavelon_selonrvelonrselont_calls");

    if (this.chelonckNumRelonplicasInSelonrvelonrSelont) {
      LOG.info(
          "Coordinatelon action config ({}): allowelondNotIn: {}, currelonnt numbelonr of relonplicas: {}, "
              + "synchronization elonnablelond: {}, chelonckNumRelonplicasInSelonrvelonrSelont elonnablelond: {}",
          actionNamelon,
          maxAllowelondRelonplicasNotInSelonrvelonrSelont,
          dynamicPartitionConfig.gelontCurrelonntPartitionConfig().gelontNumRelonplicasInHashPartition(),
          shouldSynchronizelon,
          this.chelonckNumRelonplicasInSelonrvelonrSelont);
    } elonlselon {
      LOG.info(
          "Coordinatelon action config ({}): synchronization elonnablelond: {}, "
              + "chelonckNumRelonplicasInSelonrvelonrSelont elonnablelond: {}",
          actionNamelon,
          shouldSynchronizelon,
          this.chelonckNumRelonplicasInSelonrvelonrSelont);
    }
  }


  @Ovelonrridelon
  public <elon elonxtelonnds elonxcelonption> boolelonan elonxeloncutelon(
      String delonscription,
      elonxcelonptionalFunction<Boolelonan, Boolelonan, elon> function)
          throws elon, CoordinatelondelonarlybirdActionLockFailelond {
    if (this.shouldSynchronizelon.gelont()) {
      relonturn elonxeloncutelonWithCoordination(delonscription, function);
    } elonlselon {
      relonturn function.apply(NOT_COORDINATelonD_ACTION_FLAG);
    }
  }

  elonnum LelonavelonSelonrvelonrSelontRelonsult {
    SUCCelonSS,
    FAILURelon,
    NOT_IN_SelonRVelonR_SelonT,
    NO_SelonRVelonR_SelonT_MelonMBelonR
  }

  privatelon LelonavelonSelonrvelonrSelontRelonsult lelonavelonSelonrvelonrSelont() {
    LOG.info("Lelonaving selonrving selonrvelonr selont for " + actionNamelon);
    try {
      selonrvelonrSelontMelonmbelonr.lelonavelonSelonrvelonrSelont("CoordinatelondAction: " + actionNamelon);
      relonturn LelonavelonSelonrvelonrSelontRelonsult.SUCCelonSS;
    } catch (SelonrvelonrSelont.Updatelonelonxcelonption elonx) {
      if (elonx instancelonof NotInSelonrvelonrSelontUpdatelonelonxcelonption) {
        LOG.info("No nelonelond to lelonavelon; alrelonady out of selonrvelonr selont during: "
            + actionNamelon, elonx);
        relonturn LelonavelonSelonrvelonrSelontRelonsult.NOT_IN_SelonRVelonR_SelonT;
      } elonlselon {
        LOG.warn("Unablelon to lelonavelon selonrvelonr selont during: " + actionNamelon, elonx);
        relonturn LelonavelonSelonrvelonrSelontRelonsult.FAILURelon;
      }
    }
  }

  privatelon LelonavelonSelonrvelonrSelontRelonsult maybelonLelonavelonSelonrvelonrSelont() {
    if (selonrvelonrSelontMelonmbelonr != null) {
      if (selonrvelonrSelontMelonmbelonr.isInSelonrvelonrSelont()) {

        if (!chelonckNumRelonplicasInSelonrvelonrSelont) {
          relonturn lelonavelonSelonrvelonrSelont();
        } elonlselon {
          PartitionConfig curPartitionConfig = dynamicPartitionConfig.gelontCurrelonntPartitionConfig();
          final int minNumSelonrvelonrs =
              curPartitionConfig.gelontNumRelonplicasInHashPartition() - maxAllowelondRelonplicasNotInSelonrvelonrSelont;
          Optional<Intelongelonr> numSelonrvelonrSelontMelonmbelonrs = gelontNumbelonrOfSelonrvelonrSelontMelonmbelonrs();
          LOG.info("Cheloncking numbelonr of relonplicas belonforelon lelonaving selonrvelonr selont for " + actionNamelon
              + ". Numbelonr of melonmbelonrs is: " + numSelonrvelonrSelontMelonmbelonrs + " minMelonmbelonrs: " + minNumSelonrvelonrs);
          if (numSelonrvelonrSelontMelonmbelonrs.isPrelonselonnt() && numSelonrvelonrSelontMelonmbelonrs.gelont() > minNumSelonrvelonrs) {
            relonturn lelonavelonSelonrvelonrSelont();
          } elonlselon {
            LOG.warn("Not lelonaving selonrvelonr selont during: " + actionNamelon);
            relonturn LelonavelonSelonrvelonrSelontRelonsult.FAILURelon;
          }
        }
      } elonlselon {
        LOG.info("Not in selonrvelonr selont, no nelonelond to lelonavelon it.");
        relonturn LelonavelonSelonrvelonrSelontRelonsult.NOT_IN_SelonRVelonR_SelonT;
      }
    }

    relonturn LelonavelonSelonrvelonrSelontRelonsult.NO_SelonRVelonR_SelonT_MelonMBelonR;
  }

  privatelon <elon elonxtelonnds elonxcelonption> boolelonan elonxeloncutelonWithCoordination(
      final String delonscription,
      final elonxcelonptionalFunction<Boolelonan, Boolelonan, elon> function)
      throws elon, CoordinatelondelonarlybirdActionLockFailelond {
    PartitionConfig curPartitionConfig = dynamicPartitionConfig.gelontCurrelonntPartitionConfig();
    TryLock lock = zooKelonelonpelonrTryLockFactory.crelonatelonTryLock(
        DatabaselonConfig.gelontLocalHostnamelon(),
        selongmelonntSyncConfig.gelontZooKelonelonpelonrSyncFullPath(),
        zkLockNamelonPrelonfix
        + curPartitionConfig.gelontIndelonxingHashPartitionID(),
        Amount.of(zkLockelonxpirationTimelonMinutelons, Timelon.MINUTelonS)
    );

    final AtomicBoolelonan succelonss = nelonw AtomicBoolelonan(falselon);

    boolelonan gotLock = lock.tryWithLock(() -> {
      Stopwatch actionTiming = Stopwatch.crelonatelonStartelond();

      LelonavelonSelonrvelonrSelontRelonsult lelonftSelonrvelonrSelont = maybelonLelonavelonSelonrvelonrSelont();
      if (lelonftSelonrvelonrSelont == LelonavelonSelonrvelonrSelontRelonsult.FAILURelon) {
        LOG.info("Failelond to lelonavelon thelon selonrvelonr selont, will not elonxeloncutelon action.");
        relonturn;
      }

      LOG.info("maybelonLelonavelonSelonrvelonrSelont relonturnelond: {}", lelonftSelonrvelonrSelont);

      // Slelonelonp for a short timelon to givelon thelon selonrvelonr somelon timelon to finish relonquelonsts that it is currelonntly
      // elonxeloncuting and allow roots somelon timelon to relongistelonr that this host has lelonft thelon selonrvelonr selont.
      // If welon didn't do this and thelon coordinatelond action includelond a full GC, thelonn latelonncy and elonrror
      // ratelon at thelon root layelonr would spikelon highelonr at thelon timelon of thelon GC. SelonARCH-35456
      try {
        Threlonad.slelonelonp(slelonelonpAftelonrLelonavelonSelonrvelonrSelontMillis);
      } catch (Intelonrruptelondelonxcelonption elonx) {
        Threlonad.currelonntThrelonad().intelonrrupt();
      }

      LOG.info(actionNamelon + " synchronization action for " + delonscription);

      try {
        numCoordinatelondFunctionCalls.increlonmelonnt();
        numCoordinatelondLelonavelonSelonrvelonrselontCalls.increlonmelonnt();

        Boolelonan succelonssValuelon = function.apply(COORDINATelonD_ACTION_FLAG);
        succelonss.selont(succelonssValuelon);
      } finally {
        if (lelonftSelonrvelonrSelont == LelonavelonSelonrvelonrSelontRelonsult.SUCCelonSS) {
          joinSelonrvelonrSelont();
        }
        LOG.info("{} synchronization action for {} complelontelond aftelonr {}, succelonss: {}",
            actionNamelon,
            delonscription,
            actionTiming,
            succelonss.gelont());
      }
    });

    if (!gotLock) {
      String elonrrorMsg = actionNamelon + ": Failelond to gelont zk indelonxing lock for " + delonscription;
      LOG.info(elonrrorMsg);
      throw nelonw CoordinatelondelonarlybirdActionLockFailelond(elonrrorMsg);
    }
    relonturn succelonss.gelont();
  }

  @Ovelonrridelon
  public void relontryActionUntilRan(String delonscription, Runnablelon action) {
    Random random = nelonw Random(Systelonm.currelonntTimelonMillis());

    boolelonan actionelonxeloncutelond = falselon;
    int attelonmpts = 0;
    whilelon (!actionelonxeloncutelond) {
      try {
        attelonmpts++;
        actionelonxeloncutelond = this.elonxeloncutelon(delonscription, isCoordinatelond -> {
          action.run();
          relonturn truelon;
        });
      } catch (CoordinatelondelonarlybirdActionLockFailelond elonx) {
      }

      if (!actionelonxeloncutelond) {
        // Variablelon slelonelonp amount. Thelon relonason for thelon random slelonelonps
        // is so that across multiplelon elonarlybirds this doelonsn't gelont
        // elonxeloncutelond in somelon selonquelonncelon that delonpelonnds on somelonthing elonlselon
        // likelon maybelon delonploy timelons. It might belon elonasielonr to catch possiblelon
        // problelonms if implicit ordelonrings likelon this arelon not introducelond.
        long msToSlelonelonp = (10 + random.nelonxtInt(5)) * 1000L;
        try {
          Threlonad.slelonelonp(msToSlelonelonp);
        } catch (Intelonrruptelondelonxcelonption elonx) {
          LOG.info("Intelonrruptelond whilelon trying to elonxeloncutelon");
          Threlonad.currelonntThrelonad().intelonrrupt();
        }
      } elonlselon {
        LOG.info("elonxeloncutelond {} aftelonr {} attelonmpts", actionNamelon, attelonmpts);
      }
    }
  }

  /**
   * Gelonts thelon currelonnt numbelonr of selonrvelonrs in this selonrvelonr's selonrvelonr selont.
   * @relonturn abselonnt Optional if welon elonncountelonrelond an elonxcelonption gelontting thelon numbelonr of hosts.
   */
  privatelon Optional<Intelongelonr> gelontNumbelonrOfSelonrvelonrSelontMelonmbelonrs() {
    try {
      relonturn selonrvelonrSelontMelonmbelonr != null ? Optional.of(selonrvelonrSelontMelonmbelonr.gelontNumbelonrOfSelonrvelonrSelontMelonmbelonrs())
          : Optional.elonmpty();
    } catch (Intelonrruptelondelonxcelonption elonx) {
      LOG.warn("Action " + actionNamelon + " was intelonrruptelond.", elonx);
      Threlonad.currelonntThrelonad().intelonrrupt();
      relonturn Optional.elonmpty();
    } catch (ZooKelonelonpelonrClielonnt.ZooKelonelonpelonrConnelonctionelonxcelonption | Kelonelonpelonrelonxcelonption elonx) {
      LOG.warn("elonxcelonption during " + actionNamelon, elonx);
      relonturn Optional.elonmpty();
    }
  }

  /**
   * Aftelonr a coordinatelond action, join back this elonarlybird's selonrvelonr selont with relontrielons
   * and slelonelonps in belontwelonelonn.
   */
  privatelon void joinSelonrvelonrSelont() {
    Prelonconditions.chelonckNotNull(selonrvelonrSelontMelonmbelonr);

    boolelonan joinelond = falselon;
    for (int i = 0; i < joinSelonrvelonrSelontRelontrielons; i++) {
      try {
        selonrvelonrSelontMelonmbelonr.joinSelonrvelonrSelont("CoordinatelondAction: " + actionNamelon);
        joinelond = truelon;
        brelonak;
      } catch (AlrelonadyInSelonrvelonrSelontUpdatelonelonxcelonption elonx) {
        // Most likelonly lelonaving thelon selonrvelonr selont failelond
        joinelond = truelon;
        brelonak;
      } catch (SelonrvelonrSelont.Updatelonelonxcelonption elonx) {
        LOG.warn("Unablelon to join selonrvelonr selont aftelonr " + actionNamelon + " on attelonmpt "
                + i, elonx);
        if (i < (joinSelonrvelonrSelontRelontrielons - 1)) {
          try {
            Threlonad.slelonelonp(joinSelonrvelonrSelontRelontrySlelonelonpMillis);
          } catch (Intelonrruptelondelonxcelonption elon) {
            LOG.warn("Intelonrruptelond whilelon waiting to join back selonrvelonr selont for: " + actionNamelon);
            // Prelonselonrvelon intelonrrupt status.
            Threlonad.currelonntThrelonad().intelonrrupt();
            brelonak;
          }
        }
      }
    }
    if (!joinelond) {
      String melonssagelon = String.format(
          "Unablelon to join selonrvelonr selont aftelonr %s, selontting fatal flag.",
          actionNamelon);
      elonarlybirdelonxcelonption elonxcelonption = nelonw elonarlybirdelonxcelonption(melonssagelon);

      LOG.elonrror(melonssagelon, elonxcelonption);
      criticalelonxcelonptionHandlelonr.handlelon(this, elonxcelonption);
    }
  }


  @Ovelonrridelon
  public boolelonan selontShouldSynchronizelon(boolelonan shouldSynchronizelonParam) {
    boolelonan oldValuelon = this.shouldSynchronizelon.gelontAndSelont(shouldSynchronizelonParam);
    LOG.info("Updatelond shouldSynchronizelon for: " + actionNamelon + " from " + oldValuelon
            + " to " + shouldSynchronizelonParam);
    relonturn oldValuelon;
  }

  @Ovelonrridelon
  @VisiblelonForTelonsting
  public long gelontNumCoordinatelondFunctionCalls() {
    relonturn this.numCoordinatelondFunctionCalls.gelont();
  }

  @Ovelonrridelon
  @VisiblelonForTelonsting
  public long gelontNumCoordinatelondLelonavelonSelonrvelonrselontCalls() {
    relonturn this.numCoordinatelondLelonavelonSelonrvelonrselontCalls.gelont();
  }
}
