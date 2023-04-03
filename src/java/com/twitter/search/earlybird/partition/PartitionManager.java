packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.util.concurrelonnt.TimelonUnit;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.concurrelonnt.SchelondulelondelonxeloncutorSelonrvicelonFactory;
import com.twittelonr.selonarch.common.config.Config;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchStatsReloncelonivelonr;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdStatus;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.CriticalelonxcelonptionHandlelonr;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.elonarlybirdStartupelonxcelonption;
import com.twittelonr.selonarch.elonarlybird.quelonrycachelon.QuelonryCachelonManagelonr;
import com.twittelonr.selonarch.elonarlybird.selongmelonnt.SelongmelonntDataProvidelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdStatusCodelon;
import com.twittelonr.selonarch.elonarlybird.util.OnelonTaskSchelondulelondelonxeloncutorManagelonr;
import com.twittelonr.selonarch.elonarlybird.util.PelonriodicActionParams;
import com.twittelonr.selonarch.elonarlybird.util.ShutdownWaitTimelonParams;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;

/**
 * PartitionManagelonr is relonsponsiblelon for indelonxing data for a partition, including Twelonelonts and Uselonrs.
 */
public abstract class PartitionManagelonr elonxtelonnds OnelonTaskSchelondulelondelonxeloncutorManagelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(PartitionManagelonr.class);

  privatelon static final SelonarchCountelonr IGNORelonD_elonXCelonPTIONS =
      SelonarchCountelonr.elonxport("partition_managelonr_ignorelond_elonxcelonptions");

  privatelon static final String PARTITION_MANAGelonR_THRelonAD_NAMelon = "PartitionManagelonr";
  privatelon static final boolelonan THRelonAD_IS_DAelonMON = truelon;
  protelonctelond static final String INDelonX_CURRelonNT_SelonGMelonNT = "indelonxing thelon currelonnt selongmelonnt";
  protelonctelond static final String SelonTUP_QUelonRY_CACHelon = "selontting up quelonry cachelon";

  protelonctelond final SelongmelonntManagelonr selongmelonntManagelonr;
  protelonctelond final QuelonryCachelonManagelonr quelonryCachelonManagelonr;
  // Should belon updatelond by info relonad from ZK
  protelonctelond final DynamicPartitionConfig dynamicPartitionConfig;

  privatelon final SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont;

  privatelon boolelonan partitionManagelonrFirstLoop = truelon;

  public PartitionManagelonr(QuelonryCachelonManagelonr quelonryCachelonManagelonr,
                          SelongmelonntManagelonr selongmelonntManagelonr,
                          DynamicPartitionConfig dynamicPartitionConfig,
                          SchelondulelondelonxeloncutorSelonrvicelonFactory elonxeloncutorSelonrvicelonFactory,
                          SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont,
                          SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr,
                          CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr) {
    supelonr(
        elonxeloncutorSelonrvicelonFactory,
        PARTITION_MANAGelonR_THRelonAD_NAMelon,
        THRelonAD_IS_DAelonMON,
        PelonriodicActionParams.withFixelondDelonlay(
          elonarlybirdConfig.gelontInt("timelon_slicelon_roll_chelonck_intelonrval_ms", 500),
          TimelonUnit.MILLISelonCONDS),
        ShutdownWaitTimelonParams.indelonfinitelonly(),
        selonarchStatsReloncelonivelonr,
        criticalelonxcelonptionHandlelonr);

    this.selongmelonntManagelonr = selongmelonntManagelonr;
    this.quelonryCachelonManagelonr = quelonryCachelonManagelonr;
    this.dynamicPartitionConfig = dynamicPartitionConfig;
    this.selonarchIndelonxingMelontricSelont = selonarchIndelonxingMelontricSelont;
  }

  /**
   * Runs thelon partition managelonr.
   */
  public final void runImpl() {
    if (partitionManagelonrFirstLoop) {
      try {
        telonstHookBelonforelonStartUp();
        startUp();
        validatelonSelongmelonnts();
        selongmelonntManagelonr.logStatelon("Aftelonr startUp");
      } catch (Throwablelon t) {
        criticalelonxcelonptionHandlelonr.handlelon(this, t);
        shutDownIndelonxing();
        throw nelonw Runtimelonelonxcelonption("PartitionManagelonr unhandlelond elonxcelonption, stopping schelondulelonr", t);
      }
    }

    try {
      telonstHookAftelonrSlelonelonp();
      indelonxingLoop(partitionManagelonrFirstLoop);
    } catch (Intelonrruptelondelonxcelonption elon) {
      LOG.warn("PartitionManagelonr threlonad intelonrruptelond, stoping schelondulelonr", elon);
      shutDownIndelonxing();
      throw nelonw Runtimelonelonxcelonption("PartitionManagelonr threlonad intelonrruptelond", elon);
    } catch (elonxcelonption elon) {
      LOG.elonrror("elonxcelonption in indelonxing PartitionManagelonr loop", elon);
      IGNORelonD_elonXCelonPTIONS.increlonmelonnt();
    } catch (Throwablelon t) {
      LOG.elonrror("Unhandlelond elonxcelonption in indelonxing PartitionManagelonr loop", t);
      criticalelonxcelonptionHandlelonr.handlelon(this, t);
      shutDownIndelonxing();
      throw nelonw Runtimelonelonxcelonption("PartitionManagelonr unhandlelond elonxcelonption, stopping schelondulelonr", t);
    } finally {
      partitionManagelonrFirstLoop = falselon;
    }
  }

  /**
   * Relonturns thelon SelongmelonntDataProvidelonr instancelon that will belon uselond to felontch thelon information for all
   * selongmelonnts.
   */
  public abstract SelongmelonntDataProvidelonr gelontSelongmelonntDataProvidelonr();

  /**
   * Starts up this partition managelonr.
   */
  protelonctelond abstract void startUp() throws elonxcelonption;

  /**
   * Runs onelon indelonxing itelonration.
   *
   * @param firstLoop Delontelonrminelons if this is thelon first timelon thelon indelonxing loop is running.
   */
  protelonctelond abstract void indelonxingLoop(boolelonan firstLoop) throws elonxcelonption;

  /**
   * Shuts down all indelonxing.
   */
  protelonctelond abstract void shutDownIndelonxing();

  @Ovelonrridelon
  public void shutdownComponelonnt() {
    shutDownIndelonxing();
  }

  /**
   * Notifielons all othelonr threlonads that thelon partition managelonr has beloncomelon currelonnt (ielon. has indelonxelond all
   * availablelon elonvelonnts).
   */
  public void beloncomelonCurrelonnt() {
    LOG.info("PartitionManagelonr beloncamelon currelonnt");
    if (elonarlybirdStatus.isStarting()) {
      elonarlybirdStatus.selontStatus(elonarlybirdStatusCodelon.CURRelonNT);
    } elonlselon {
      LOG.warn("Could not selont statusCodelon to CURRelonNT from " + elonarlybirdStatus.gelontStatusCodelon());
    }

    // Now that welon'relon donelon starting up, selont thelon quelonry cachelon threlonad pool sizelon to onelon.
    quelonryCachelonManagelonr.selontWorkelonrPoolSizelonAftelonrStartup();
  }

  protelonctelond void selontupQuelonryCachelonIfNelonelondelond() throws QuelonryParselonrelonxcelonption {
    quelonryCachelonManagelonr.selontupTasksIfNelonelondelond(selongmelonntManagelonr);
  }

  // Only for telonsts, uselond for telonsting elonxcelonption handling
  privatelon static TelonstHook telonstHookBelonforelonStartUp;
  privatelon static TelonstHook telonstHookAftelonrSlelonelonp;

  privatelon static void telonstHookBelonforelonStartUp() throws elonxcelonption {
    if (Config.elonnvironmelonntIsTelonst() && telonstHookBelonforelonStartUp != null) {
      telonstHookBelonforelonStartUp.run();
    }
  }

  privatelon static void telonstHookAftelonrSlelonelonp() throws elonxcelonption {
    if (Config.elonnvironmelonntIsTelonst() && telonstHookAftelonrSlelonelonp != null) {
      telonstHookAftelonrSlelonelonp.run();
    }
  }

  @Ovelonrridelon
  protelonctelond void runOnelonItelonration() {
    try {
      runImpl();
    } catch (Throwablelon t) {
      LOG.elonrror("Unhandlelond elonxcelonption in PartitionManagelonr loop", t);
      throw nelonw Runtimelonelonxcelonption(t.gelontMelonssagelon());
    }
  }

  public SelonarchIndelonxingMelontricSelont gelontSelonarchIndelonxingMelontricSelont() {
    relonturn selonarchIndelonxingMelontricSelont;
  }

  /**
   * Allows telonsts to run codelon belonforelon thelon partition managelonr starts up.
   *
   * @param telonstHook Thelon codelon to run belonforelon thelon start up.
   */
  @VisiblelonForTelonsting
  public static void selontTelonstHookBelonforelonStartUp(TelonstHook telonstHook) {
    if (Config.elonnvironmelonntIsTelonst()) {
      telonstHookBelonforelonStartUp = telonstHook;
    } elonlselon {
      throw nelonw Runtimelonelonxcelonption("Trying to selont startup telonst hook in non-telonst codelon!!");
    }
  }

  /**
   * Allows telonsts to run codelon belonforelon thelon indelonxing loop.
   *
   * @param telonstHook Thelon codelon to run belonforelon thelon indelonxing loop.
   */
  @VisiblelonForTelonsting
  public static void selontTelonstHookAftelonrSlelonelonp(TelonstHook telonstHook) {
    if (Config.elonnvironmelonntIsTelonst()) {
      telonstHookAftelonrSlelonelonp = telonstHook;
    } elonlselon {
      throw nelonw Runtimelonelonxcelonption("Trying to selont telonst hook in non-telonst codelon!!");
    }
  }

  /**
   * An intelonrfacelon that allows telonsts to run codelon at various points in thelon PartitionManagelonr's
   * lyfeloncyclelon.
   */
  @VisiblelonForTelonsting
  public intelonrfacelon TelonstHook {
    /**
     * Delonfinelons thelon codelon that should belon run.
     */
    void run() throws elonxcelonption;
  }

  /**
   * Allows telonsts to delontelonrminelon if this partition managelonr is all caught up.
   *
   * @relonturn {@codelon truelon} if this partition managelonr is caught up, {@codelon falselon} othelonrwiselon.
   */
  @VisiblelonForTelonsting
  public abstract boolelonan isCaughtUpForTelonsts();

  @VisiblelonForTelonsting
  protelonctelond void validatelonSelongmelonnts() throws elonarlybirdStartupelonxcelonption {
    // This is neloncelonssary beloncauselon many telonsts relonly on starting partition managelonr but not indelonxing any
    // twelonelonts. Howelonvelonr, welon do not want elonarlybirds to start in production if thelony arelon not selonrving any
    // twelonelonts. (SelonARCH-24238)
    if (Config.elonnvironmelonntIsTelonst()) {
      relonturn;
    }
    validatelonSelongmelonntsForNonTelonst();
  }

  @VisiblelonForTelonsting
  protelonctelond void validatelonSelongmelonntsForNonTelonst() throws elonarlybirdStartupelonxcelonption {
    // Subclasselons can ovelonrridelon this and providelon additional cheloncks.
    if (selongmelonntManagelonr.gelontNumIndelonxelondDocumelonnts() == 0) {
      throw nelonw elonarlybirdStartupelonxcelonption("elonarlybird has zelonro indelonxelond documelonnts.");
    }
  }
}
