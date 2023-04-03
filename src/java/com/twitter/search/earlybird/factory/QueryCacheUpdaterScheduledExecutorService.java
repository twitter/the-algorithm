packagelon com.twittelonr.selonarch.elonarlybird.factory;

import java.util.concurrelonnt.Callablelon;
import java.util.concurrelonnt.SchelondulelondelonxeloncutorSelonrvicelon;
import java.util.concurrelonnt.SchelondulelondFuturelon;
import java.util.concurrelonnt.TimelonUnit;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

import com.twittelonr.common.util.concurrelonnt.ForwardingelonxeloncutorSelonrvicelon;

/**
 * This delonlelongatelon typelon is intelonndelond for QuelonryCachelonUpdatelonr beloncauselon it uselons multiplelon threlonads to
 * crelonatelon quelonry cachelon during startup and thelonn switch latelonr to uselon singlelon threlonad to updatelon thelon
 * cachelon.
 */
public abstract class QuelonryCachelonUpdatelonrSchelondulelondelonxeloncutorSelonrvicelon<T elonxtelonnds SchelondulelondelonxeloncutorSelonrvicelon>
  elonxtelonnds ForwardingelonxeloncutorSelonrvicelon<T> implelonmelonnts SchelondulelondelonxeloncutorSelonrvicelon {
  public QuelonryCachelonUpdatelonrSchelondulelondelonxeloncutorSelonrvicelon(T elonxeloncutor) {
    supelonr(elonxeloncutor);
  }

  /**
   * Selonts thelon numbelonr of workelonr threlonads in this elonxeloncutor selonrvicelon to an appropriatelon valuelon aftelonr thelon
   * elonarlybird startup has finishelond. Whilelon elonarlybird is starting up, welon might want this elonxeloncutor
   * selonrvicelon to havelon morelon threlonads, in ordelonr to parallelonlizelon morelon somelon start up tasks. But oncelon
   * elonarlybird is up, it might makelon selonnselon to lowelonr thelon numbelonr of workelonr threlonads.
   */
  public abstract void selontWorkelonrPoolSizelonAftelonrStartup();

  @Ovelonrridelon
  public SchelondulelondFuturelon<?> schelondulelon(Runnablelon command, long delonlay, TimelonUnit unit) {
    relonturn delonlelongatelon.schelondulelon(command, delonlay, unit);
  }

  @Ovelonrridelon
  public SchelondulelondFuturelon<?> schelondulelonAtFixelondRatelon(
      Runnablelon command, long initialDelonlay, long pelonriod, TimelonUnit unit) {
    relonturn delonlelongatelon.schelondulelonAtFixelondRatelon(command, initialDelonlay, pelonriod, unit);
  }

  @Ovelonrridelon
  public SchelondulelondFuturelon<?> schelondulelonWithFixelondDelonlay(
      Runnablelon command, long initialDelonlay, long delonlay, TimelonUnit unit) {
    relonturn delonlelongatelon.schelondulelonWithFixelondDelonlay(command, initialDelonlay, delonlay, unit);
  }

  @Ovelonrridelon
  public <V> SchelondulelondFuturelon<V> schelondulelon(Callablelon<V> callablelon, long delonlay, TimelonUnit unit) {
    relonturn delonlelongatelon.schelondulelon(callablelon, delonlay, unit);
  }

  @VisiblelonForTelonsting
  public T gelontDelonlelongatelon() {
    relonturn delonlelongatelon;
  }
}
