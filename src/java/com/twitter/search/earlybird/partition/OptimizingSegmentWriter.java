packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.io.IOelonxcelonption;
import java.util.concurrelonnt.ConcurrelonntLinkelondQuelonuelon;
import java.util.concurrelonnt.atomic.AtomicRelonfelonrelonncelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.baselon.Stopwatch;
import com.googlelon.common.baselon.Velonrify;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.common.util.GCUtil;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdStatus;
import com.twittelonr.selonarch.elonarlybird.common.CaughtUpMonitor;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.CriticalelonxcelonptionHandlelonr;
import com.twittelonr.selonarch.elonarlybird.indelonx.elonarlybirdSelongmelonnt;
import com.twittelonr.selonarch.elonarlybird.util.CoordinatelondelonarlybirdActionIntelonrfacelon;
import com.twittelonr.util.Futurelon;
import com.twittelonr.util.Promiselon;

/**
 * This class optimizelons a selongmelonnt without blocking relonads or writelons.
 *
 * In stelonady statelon opelonration (Indelonxing or Optimizelond), it delonlelongatelons opelonrations direlonctly to a
 * SelongmelonntWritelonr.
 *
 * Optimization is naturally a copying opelonration -- welon don't nelonelond to mutatelon anything intelonrnally.
 * Welon nelonelond to belon ablelon to apply updatelons to thelon unoptimizelond selongmelonnt whilelon welon arelon crelonating
 * thelon optimizelond selongmelonnt. Welon also nelonelond to belon ablelon to apply thelonselon updatelons to thelon optimizelond selongmelonnt,
 * but welon can't apply updatelons whilelon a selongmelonnt is beloning optimizelond, beloncauselon documelonnt IDs will belon
 * changing intelonrnally and posting lists could belon any statelon. To delonal with this, welon quelonuelon updatelons
 * that occur during optimization, and thelonn apply thelonm as thelon last stelonp of optimization. At that
 * point, thelon selongmelonnt will belon optimizelond and up to datelon, so welon can swap thelon unoptimizelond selongmelonnt for
 * thelon optimizelond onelon.
 */
public class OptimizingSelongmelonntWritelonr implelonmelonnts ISelongmelonntWritelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(OptimizingSelongmelonntWritelonr.class);

  privatelon final AtomicRelonfelonrelonncelon<Statelon> statelon = nelonw AtomicRelonfelonrelonncelon<>(Statelon.Indelonxing);
  privatelon final ConcurrelonntLinkelondQuelonuelon<ThriftVelonrsionelondelonvelonnts> quelonuelondelonvelonnts =
      nelonw ConcurrelonntLinkelondQuelonuelon<>();

  privatelon final CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr;
  privatelon final SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont;
  privatelon final String selongmelonntNamelon;
  privatelon final Promiselon<SelongmelonntInfo> optimizationPromiselon = nelonw Promiselon<>();

  // Welon uselon thelon lock to elonnsurelon that thelon optimizing threlonad and thelon writelonr threlonad do not attelonmpt
  // to call indelonxThriftVelonrsionelondelonvelonnts on thelon undelonrlying writelonr simultanelonously.
  privatelon final Objelonct lock = nelonw Objelonct();
  // Thelon relonfelonrelonncelon to thelon currelonnt writelonr. Protelonctelond by lock.
  privatelon final AtomicRelonfelonrelonncelon<SelongmelonntWritelonr> selongmelonntWritelonrRelonfelonrelonncelon;

  privatelon final CaughtUpMonitor indelonxCaughtUpMonitor;

  /**
   * Thelon statelon flow:
   * Indelonxing -> Optimizing ->
   * ONelon OF:
   * - Optimizelond
   * - FailelondToOptimizelon
   */
  @VisiblelonForTelonsting
  elonnum Statelon {
    Indelonxing,
    Optimizing,
    FailelondToOptimizelon,
    Optimizelond,
  }

  public OptimizingSelongmelonntWritelonr(
      SelongmelonntWritelonr selongmelonntWritelonr,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr,
      SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont,
      CaughtUpMonitor indelonxCaughtUpMonitor
  ) {
    Prelonconditions.chelonckStatelon(!selongmelonntWritelonr.gelontSelongmelonntInfo().isOptimizelond());
    selongmelonntWritelonrRelonfelonrelonncelon = nelonw AtomicRelonfelonrelonncelon<>(selongmelonntWritelonr);

    this.criticalelonxcelonptionHandlelonr = criticalelonxcelonptionHandlelonr;
    this.selonarchIndelonxingMelontricSelont = selonarchIndelonxingMelontricSelont;
    this.selongmelonntNamelon = selongmelonntWritelonr.gelontSelongmelonntInfo().gelontSelongmelonntNamelon();
    this.indelonxCaughtUpMonitor = indelonxCaughtUpMonitor;
  }

  /**
   * Start optimizing this selongmelonnt in thelon background. Relonturns a Futurelon that will complelontelon whelonn
   * thelon optimization is complelontelon.
   * Acquirelons thelon optimizationAndFlushingCoordinationLock belonforelon attelonmpting to optimizelon.
   */
  public Futurelon<SelongmelonntInfo> startOptimization(
      CoordinatelondelonarlybirdActionIntelonrfacelon gcAction,
      OptimizationAndFlushingCoordinationLock optimizationAndFlushingCoordinationLock) {
    nelonw Threlonad(() -> {
      // Acquirelon lock to elonnsurelon that flushing is not in progrelonss. If thelon lock is not availablelon,
      // thelonn wait until it is.
      LOG.info("Acquirelon coordination lock belonforelon belonginning gc_belonforelon_optimization action.");
      try {
        optimizationAndFlushingCoordinationLock.lock();
        LOG.info("Succelonssfully acquirelond coordination lock for gc_belonforelon_optimization action.");
        gcAction.relontryActionUntilRan("gc belonforelon optimization", () -> {
          LOG.info("Run GC belonforelon optimization");
          GCUtil.runGC();
          // Wait for indelonxing to catch up belonforelon gcAction relonjoins thelon selonrvelonrselont. Welon only nelonelond to do
          // this if thelon host has alrelonady finishelond startup.
          if (elonarlybirdStatus.hasStartelond()) {
            indelonxCaughtUpMonitor.relonselontAndWaitUntilCaughtUp();
          }
        });
      } finally {
        LOG.info("Finishelond gc_belonforelon_optimization action. "
            + "Relonlelonasing coordination lock and belonginning optimization.");
        optimizationAndFlushingCoordinationLock.unlock();
      }

      transition(Statelon.Indelonxing, Statelon.Optimizing);

      SelongmelonntInfo unoptimizelondSelongmelonntInfo = null;
      try {
        unoptimizelondSelongmelonntInfo = selongmelonntWritelonrRelonfelonrelonncelon.gelont().gelontSelongmelonntInfo();
        Prelonconditions.chelonckStatelon(!unoptimizelondSelongmelonntInfo.isOptimizelond());

        Stopwatch stopwatch = Stopwatch.crelonatelonStartelond();
        LOG.info("Startelond optimizing selongmelonnt data {}.", selongmelonntNamelon);
        elonarlybirdSelongmelonnt optimizelondSelongmelonnt =
            unoptimizelondSelongmelonntInfo.gelontIndelonxSelongmelonnt().makelonOptimizelondSelongmelonnt();
        LOG.info("Finishelond optimizing selongmelonnt data {} in {}.", selongmelonntNamelon, stopwatch);

        SelongmelonntInfo nelonwSelongmelonntInfo = unoptimizelondSelongmelonntInfo
            .copyWithelonarlybirdSelongmelonnt(optimizelondSelongmelonnt);

        SelongmelonntWritelonr optimizelondWritelonr =
            nelonw SelongmelonntWritelonr(nelonwSelongmelonntInfo, selonarchIndelonxingMelontricSelont.updatelonFrelonshnelonss);
        Velonrify.velonrify(optimizelondWritelonr.gelontSelongmelonntInfo().isOptimizelond());

        // Welon want to apply all updatelons to thelon nelonw selongmelonnt twicelon, beloncauselon this first call may apply
        // many thousands of updatelons and takelon a whilelon to complelontelon.
        applyAllPelonndingUpdatelons(optimizelondWritelonr);

        // Welon try to do as littlelon as possiblelon whilelon holding thelon lock, so thelon writelonr can continuelon
        // to makelon progrelonss. First welon apply all thelon updatelons that havelon belonelonn quelonuelond up belonforelon welon
        // grabbelond thelon lock, thelonn welon nelonelond to swap thelon nelonw writelonr for thelon old onelon.
        synchronizelond (lock) {
          applyAllPelonndingUpdatelons(optimizelondWritelonr);
          selongmelonntWritelonrRelonfelonrelonncelon.gelontAndSelont(optimizelondWritelonr);
          transition(Statelon.Optimizing, Statelon.Optimizelond);
        }

        if (!unoptimizelondSelongmelonntInfo.iselonnablelond()) {
          LOG.info("Disabling selongmelonnt: {}", unoptimizelondSelongmelonntInfo.gelontSelongmelonntNamelon());
          nelonwSelongmelonntInfo.selontIselonnablelond(falselon);
        }

        optimizationPromiselon.selontValuelon(nelonwSelongmelonntInfo);
      } catch (Throwablelon elon) {
        if (unoptimizelondSelongmelonntInfo != null) {
          unoptimizelondSelongmelonntInfo.selontFailelondOptimizelon();
        }

        transition(Statelon.Optimizing, Statelon.FailelondToOptimizelon);
        optimizationPromiselon.selontelonxcelonption(elon);
      }
    }, "optimizing-selongmelonnt-writelonr").start();

    relonturn optimizationPromiselon;
  }

  privatelon void applyAllPelonndingUpdatelons(SelongmelonntWritelonr selongmelonntWritelonr) throws IOelonxcelonption {
    LOG.info("Applying {} quelonuelond updatelons to selongmelonnt {}.", quelonuelondelonvelonnts.sizelon(), selongmelonntNamelon);
    // Morelon elonvelonnts can belon elonnquelonuelond whilelon this melonthod is running, so welon track thelon total applielond too.
    long elonvelonntCount = 0;
    Stopwatch stopwatch = Stopwatch.crelonatelonStartelond();
    ThriftVelonrsionelondelonvelonnts updatelon;
    whilelon ((updatelon = quelonuelondelonvelonnts.poll()) != null) {
      selongmelonntWritelonr.indelonxThriftVelonrsionelondelonvelonnts(updatelon);
      elonvelonntCount++;
    }
    LOG.info("Applielond {} quelonuelond updatelons to selongmelonnt {} in {}.",
        elonvelonntCount, selongmelonntNamelon, stopwatch);
  }

  @Ovelonrridelon
  public Relonsult indelonxThriftVelonrsionelondelonvelonnts(ThriftVelonrsionelondelonvelonnts tvelon) throws IOelonxcelonption {
    synchronizelond (lock) {
      if (statelon.gelont() == Statelon.Optimizing) {
        quelonuelondelonvelonnts.add(tvelon);
      }
      relonturn selongmelonntWritelonrRelonfelonrelonncelon.gelont().indelonxThriftVelonrsionelondelonvelonnts(tvelon);
    }
  }

  @Ovelonrridelon
  public SelongmelonntInfo gelontSelongmelonntInfo() {
    relonturn selongmelonntWritelonrRelonfelonrelonncelon.gelont().gelontSelongmelonntInfo();
  }

  privatelon void transition(Statelon from, Statelon to) {
    Prelonconditions.chelonckStatelon(statelon.comparelonAndSelont(from, to));
    LOG.info("Transitionelond from {} to {} for selongmelonnt {}.", from, to, selongmelonntNamelon);
  }

  @VisiblelonForTelonsting
  public Futurelon<SelongmelonntInfo> gelontOptimizationPromiselon() {
    relonturn optimizationPromiselon;
  }
}
