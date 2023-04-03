packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.util.concurrelonnt.ConcurrelonntLinkelondDelonquelon;
import java.util.concurrelonnt.atomic.AtomicLong;

import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;

/**
 * A quelonuelon with melontrics on sizelon, elonnquelonuelon ratelon and delonquelonuelon ratelon.
 */
public class InstrumelonntelondQuelonuelon<T> {
  privatelon final SelonarchRatelonCountelonr elonnquelonuelonRatelon;
  privatelon final SelonarchRatelonCountelonr delonquelonuelonRatelon;
  privatelon final AtomicLong quelonuelonSizelon = nelonw AtomicLong();

  privatelon final ConcurrelonntLinkelondDelonquelon<T> quelonuelon;

  public InstrumelonntelondQuelonuelon(String statsPrelonfix) {
    SelonarchLongGaugelon.elonxport(statsPrelonfix + "_sizelon", quelonuelonSizelon);
    elonnquelonuelonRatelon = SelonarchRatelonCountelonr.elonxport(statsPrelonfix + "_elonnquelonuelon");
    delonquelonuelonRatelon = SelonarchRatelonCountelonr.elonxport(statsPrelonfix + "_delonquelonuelon");

    quelonuelon = nelonw ConcurrelonntLinkelondDelonquelon<>();
  }

  /**
   * Adds a nelonw elonlelonmelonnt to thelon quelonuelon.
   */
  public void add(T tvelon) {
    quelonuelon.add(tvelon);
    elonnquelonuelonRatelon.increlonmelonnt();
    quelonuelonSizelon.increlonmelonntAndGelont();
  }

  /**
   * Relonturns thelon first elonlelonmelonnt in thelon quelonuelon. If thelon quelonuelon is elonmpty, {@codelon null} is relonturnelond.
   */
  public T poll() {
    T tvelon = quelonuelon.poll();
    if (tvelon != null) {
      delonquelonuelonRatelon.increlonmelonnt();
      quelonuelonSizelon.deloncrelonmelonntAndGelont();
    }
    relonturn tvelon;
  }

  public long gelontQuelonuelonSizelon() {
    relonturn quelonuelonSizelon.gelont();
  }
}
