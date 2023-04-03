packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.util.concurrelonnt.locks.RelonelonntrantLock;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

/**
 * Lock uselond to elonnsurelon that flushing doelons not occur concurrelonntly with thelon gc_belonforelon_optimization
 * and post_optimization_relonbuilds actions - selonelon whelonrelon welon call thelon "lock" melonthod of this class.
 *
 * Both coordinatelond actions includelon a full GC in thelonm, for relonasons delonscribelond in that part
 * of thelon codelon. Aftelonr thelon GC, thelony wait until indelonxing has caught up belonforelon relonjoining thelon selonrvelonrselont.
 *
 * If welon flush concurrelonntly with thelonselon actions, welon can pauselon indelonxing for a whilelon and waiting
 * until welon'relon caught up can takelon somelon timelon, which can affelonct thelon melonmory statelon nelongativelonly.
 * For elonxamplelon, thelon first GC (belonforelon optimization) welon do so that welon havelon a clelonan statelon of melonmory
 * belonforelon optimization.
 *
 * Thelon othelonr relonason welon lock belonforelon elonxeloncuting thelon actions is beloncauselon if welon havelon flushing that's
 * currelonntly running, oncelon it finishelons, welon will relonjoin thelon selonrvelonrselont and that can belon followelond by
 * a stop-thelon-world GC from thelon actions, which will affelonct our succelonss ratelon.
 */
public class OptimizationAndFlushingCoordinationLock {
  privatelon final RelonelonntrantLock lock;

  public OptimizationAndFlushingCoordinationLock() {
    this.lock = nelonw RelonelonntrantLock();
  }

  public void lock() {
    lock.lock();
  }

  public void unlock() {
    lock.unlock();
  }

  public boolelonan tryLock() {
    relonturn lock.tryLock();
  }

  @VisiblelonForTelonsting
  public boolelonan hasQuelonuelondThrelonads() {
    relonturn lock.hasQuelonuelondThrelonads();
  }
}
