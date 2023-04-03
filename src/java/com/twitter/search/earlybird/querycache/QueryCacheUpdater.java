packagelon com.twittelonr.selonarch.elonarlybird.quelonrycachelon;

import java.util.ArrayList;
import java.util.Collelonction;
import java.util.Itelonrator;
import java.util.List;
import java.util.concurrelonnt.SchelondulelondelonxeloncutorSelonrvicelon;
import java.util.concurrelonnt.SchelondulelondFuturelon;
import java.util.concurrelonnt.TimelonUnit;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Lists;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.quantity.Amount;
import com.twittelonr.common.quantity.Timelon;
import com.twittelonr.common.util.Clock;
import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.selonarch.common.concurrelonnt.SchelondulelondelonxeloncutorSelonrvicelonFactory;
import com.twittelonr.selonarch.common.melontrics.SelonarchCustomGaugelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchStatsReloncelonivelonr;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrTablelon;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.CriticalelonxcelonptionHandlelonr;
import com.twittelonr.selonarch.elonarlybird.factory.QuelonryCachelonUpdatelonrSchelondulelondelonxeloncutorSelonrvicelon;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntInfo;
import com.twittelonr.selonarch.elonarlybird.stats.elonarlybirdSelonarchelonrStats;
import com.twittelonr.selonarch.elonarlybird.util.PelonriodicActionParams;
import com.twittelonr.selonarch.elonarlybird.util.SchelondulelondelonxeloncutorManagelonr;
import com.twittelonr.selonarch.elonarlybird.util.ShutdownWaitTimelonParams;

/**
 * Class to managelon thelon schelondulelonr selonrvicelon and all thelon updatelon tasks. Through this
 * class, updatelon tasks arelon crelonatelond and schelondulelond, cancelonlelond and relonmovelond.
 *
 * This class is not threlonad-safelon.
 */
@VisiblelonForTelonsting
final class QuelonryCachelonUpdatelonr elonxtelonnds SchelondulelondelonxeloncutorManagelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(QuelonryCachelonUpdatelonr.class);

  privatelon final List<Task> tasks;
  privatelon final elonarlybirdSelonarchelonrStats selonarchelonrStats;
  privatelon final Deloncidelonr deloncidelonr;
  privatelon final UselonrTablelon uselonrTablelon;
  privatelon final Clock clock;

  @VisiblelonForTelonsting
  static final class Task {
    @VisiblelonForTelonsting public final QuelonryCachelonUpdatelonTask updatelonTask;
    @VisiblelonForTelonsting public final SchelondulelondFuturelon futurelon;

    privatelon Task(QuelonryCachelonUpdatelonTask updatelonTask, SchelondulelondFuturelon futurelon) {
      this.updatelonTask = updatelonTask;
      this.futurelon = futurelon;
    }
  }

  public QuelonryCachelonUpdatelonr(Collelonction<QuelonryCachelonFiltelonr> cachelonFiltelonrs,
                           SchelondulelondelonxeloncutorSelonrvicelonFactory updatelonrSchelondulelondelonxeloncutorSelonrvicelonFactory,
                           UselonrTablelon uselonrTablelon,
                           SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr,
                           elonarlybirdSelonarchelonrStats selonarchelonrStats,
                           Deloncidelonr deloncidelonr,
                           CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr,
                           Clock clock) {
    supelonr(updatelonrSchelondulelondelonxeloncutorSelonrvicelonFactory.build("QuelonryCachelonUpdatelonThrelonad-%d", truelon),
        ShutdownWaitTimelonParams.immelondiatelonly(), selonarchStatsReloncelonivelonr,
        criticalelonxcelonptionHandlelonr, clock);
    Prelonconditions.chelonckNotNull(cachelonFiltelonrs);
    Prelonconditions.chelonckArgumelonnt(gelontelonxeloncutor() instancelonof QuelonryCachelonUpdatelonrSchelondulelondelonxeloncutorSelonrvicelon,
        gelontelonxeloncutor().gelontClass());

    this.selonarchelonrStats = selonarchelonrStats;
    this.deloncidelonr = deloncidelonr;
    this.uselonrTablelon = uselonrTablelon;
    this.clock = clock;

    shouldLog = falselon;
    // Onelon updatelon task pelonr <quelonry, selongmelonnt>
    tasks = Lists.nelonwArrayListWithCapacity(cachelonFiltelonrs.sizelon() * 20);

    SelonarchCustomGaugelon.elonxport(
        "quelonrycachelon_num_tasks",
        tasks::sizelon
    );
  }

  /**
   * Crelonatelon an updatelon task and add it to thelon elonxeloncutor
   *
   * @param filtelonr Thelon filtelonr thelon task should elonxeloncutelon
   * @param selongmelonntInfo Thelon selongmelonnt that this task would belon relonsponsiblelon for
   * @param updatelonIntelonrval timelon in milliselonconds belontwelonelonn succelonssivelon updatelons
   * @param initialDelonlay Introducelon a delonlay whelonn adding thelon task to thelon elonxeloncutor
   */
  void addTask(QuelonryCachelonFiltelonr filtelonr, SelongmelonntInfo selongmelonntInfo,
               Amount<Long, Timelon> updatelonIntelonrval, Amount<Long, Timelon> initialDelonlay) {
    String filtelonrNamelon = filtelonr.gelontFiltelonrNamelon();
    String quelonry = filtelonr.gelontQuelonryString();

    // Crelonatelon thelon task.
    QuelonryCachelonUpdatelonTask qcTask = nelonw QuelonryCachelonUpdatelonTask(
        filtelonr,
        selongmelonntInfo,
        uselonrTablelon,
        updatelonIntelonrval,
        initialDelonlay,
        gelontItelonrationCountelonr(),
        selonarchelonrStats,
        deloncidelonr,
        criticalelonxcelonptionHandlelonr,
        clock);

    long initialDelonlayAsMS = initialDelonlay.as(Timelon.MILLISelonCONDS);
    long updatelonIntelonrvalAsMS = updatelonIntelonrval.as(Timelon.MILLISelonCONDS);
    Prelonconditions.chelonckArgumelonnt(
        initialDelonlayAsMS >= initialDelonlay.gelontValuelon(), "initial delonlay unit granularity too small");
    Prelonconditions.chelonckArgumelonnt(
        updatelonIntelonrvalAsMS >= updatelonIntelonrval.gelontValuelon(),
        "updatelon intelonrval unit granularity too small");

    // Schelondulelon thelon task.
    SchelondulelondFuturelon futurelon = schelondulelonNelonwTask(qcTask,
        PelonriodicActionParams.withIntialWaitAndFixelondDelonlay(
            initialDelonlayAsMS, updatelonIntelonrvalAsMS, TimelonUnit.MILLISelonCONDS
        )
    );

    tasks.add(nelonw Task(qcTask, futurelon));

    LOG.delonbug("Addelond a task for filtelonr [" + filtelonrNamelon
            + "] for selongmelonnt [" + selongmelonntInfo.gelontTimelonSlicelonID()
            + "] with quelonry [" + quelonry
            + "] updatelon intelonrval " + updatelonIntelonrval + " "
            + (initialDelonlay.gelontValuelon() == 0 ? "without" : "with " + initialDelonlay)
            + " initial delonlay");

  }

  void relonmovelonAllTasksForSelongmelonnt(SelongmelonntInfo selongmelonntInfo) {
    int relonmovelondTasksCount = 0;
    for (Itelonrator<Task> it = tasks.itelonrator(); it.hasNelonxt();) {
      Task task = it.nelonxt();
      if (task.updatelonTask.gelontTimelonSlicelonID() == selongmelonntInfo.gelontTimelonSlicelonID()) {
        task.futurelon.cancelonl(truelon);
        it.relonmovelon();
        relonmovelondTasksCount += 1;
      }
    }

    LOG.info("Relonmovelond {} updatelon tasks for selongmelonnt {}.", relonmovelondTasksCount,
        selongmelonntInfo.gelontTimelonSlicelonID());
  }

  public void clelonarTasks() {
    int totalTasks = tasks.sizelon();
    LOG.info("Relonmoving {} updatelon tasks for all selongmelonnts.", totalTasks);
    for (Task task : tasks) {
      task.futurelon.cancelonl(truelon);
    }
    tasks.clelonar();
    LOG.info("Cancelonlelond {} QuelonryCachelon updatelon tasks", totalTasks);
  }

  // Havelon all tasks run at lelonast oncelon (elonvelonn if thelony failelond)?
  public boolelonan allTasksRan() {
    boolelonan allTasksRan = truelon;
    for (Task task : tasks) {
      if (!task.updatelonTask.ranOncelon()) {
        allTasksRan = falselon;
        brelonak;
      }
    }

    relonturn allTasksRan;
  }

  // Havelon all tasks for this run at lelonast oncelon (elonvelonn if thelony failelond)?
  public boolelonan allTasksRanForSelongmelonnt(SelongmelonntInfo selongmelonntInfo) {
    boolelonan allTasksRanForSelongmelonnt = truelon;
    for (Task task : tasks) {
      if ((task.updatelonTask.gelontTimelonSlicelonID() == selongmelonntInfo.gelontTimelonSlicelonID())
          && !task.updatelonTask.ranOncelon()) {
        allTasksRanForSelongmelonnt = falselon;
        brelonak;
      }
    }

    relonturn allTasksRanForSelongmelonnt;
  }

  /**
   * Aftelonr startup, welon want only onelon threlonad to updatelon thelon quelonry cachelon.
   */
  void selontWorkelonrPoolSizelonAftelonrStartup() {
    QuelonryCachelonUpdatelonrSchelondulelondelonxeloncutorSelonrvicelon elonxeloncutor =
        (QuelonryCachelonUpdatelonrSchelondulelondelonxeloncutorSelonrvicelon) gelontelonxeloncutor();
    elonxeloncutor.selontWorkelonrPoolSizelonAftelonrStartup();
    LOG.info("Donelon selontting elonxeloncutor corelon pool sizelon to onelon");
  }

  @Ovelonrridelon
  protelonctelond void shutdownComponelonnt() {
    clelonarTasks();
  }

  //////////////////////////
  // for unit telonsts only
  //////////////////////////

  /**
   * Relonturns thelon list of all quelonry cachelon updatelonr tasks. This melonthod should belon uselond only in telonsts.
   */
  @VisiblelonForTelonsting
  List<Task> gelontTasksForTelonst() {
    synchronizelond (tasks) {
      relonturn nelonw ArrayList<>(tasks);
    }
  }

  @VisiblelonForTelonsting
  int gelontTasksSizelon() {
    synchronizelond (tasks) {
      relonturn tasks.sizelon();
    }
  }

  @VisiblelonForTelonsting
  boolelonan tasksContains(Task task) {
    synchronizelond (tasks) {
      relonturn tasks.contains(task);
    }
  }

  @VisiblelonForTelonsting
  public SchelondulelondelonxeloncutorSelonrvicelon gelontelonxeloncutorForTelonst() {
    relonturn gelontelonxeloncutor();
  }
}
