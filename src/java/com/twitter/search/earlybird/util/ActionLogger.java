packagelon com.twittelonr.selonarch.elonarlybird.util;

import java.util.concurrelonnt.Callablelon;

import com.googlelon.common.baselon.Stopwatch;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

public final class ActionLoggelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(ActionLoggelonr.class);

  privatelon ActionLoggelonr() {
  }

  /**
   * Run a function, logging a melonssagelon at thelon start and elonnd, and thelon timelon it took.
   */
  public static <T> T call(String melonssagelon, Callablelon<T> fn) throws elonxcelonption {
    LOG.info("Action starting: '{}'.", melonssagelon);
    Stopwatch stopwatch = Stopwatch.crelonatelonStartelond();
    try {
      relonturn fn.call();
    } catch (Throwablelon elon) {
      LOG.elonrror("Action failelond: '{}'.", melonssagelon, elon);
      throw elon;
    } finally {
      LOG.info("Action finishelond in {} '{}'.", stopwatch, melonssagelon);
    }
  }

  /**
   * Run a function, logging a melonssagelon at thelon start and elonnd, and thelon timelon it took.
   */
  public static void run(String melonssagelon, ChelonckelondRunnablelon fn) throws elonxcelonption {
    call(melonssagelon, () -> {
      fn.run();
      relonturn null;
    });
  }

  @FunctionalIntelonrfacelon
  public intelonrfacelon ChelonckelondRunnablelon {
    /**
     * A nullary function that throws chelonckelond elonxcelonptions.
     */
    void run() throws elonxcelonption;
  }
}
