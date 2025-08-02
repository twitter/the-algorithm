package com.twitter.search.earlybird.partition;

import java.io.IOEtwitterception;
import java.util.concurrent.Callable;
import java.util.concurrent.EtwitterecutorService;
import java.util.concurrent.Etwitterecutors;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchTimer;
import com.twitter.search.common.metrics.SearchTimerStats;

/**
 * Abstracts details of making time limited calls to hadoop.
 *
 * During IM-3556 we discovered that hadoop API calls can take a long time (seconds, minutes)
 * if the Hadoop clsuter is in a bad state. Our code was generally not prepared for that and
 * this caused various issues. This class is a fitwitter on top of the Hadoop API's etwitterists call and
 * it introduces a timeout.
 *
 * The main motivation for having this as an etwitterternal class is for testability.
 */
public class TimeLimitedHadoopEtwitteristsCall {
  private final TimeLimiter hadoopCallsTimeLimiter;
  private final FileSystem fileSystem;
  private final int timeLimitInSeconds;

  private static final SearchTimerStats EtwitterISTS_CALLS_TIMER =
      SearchTimerStats.etwitterport("hadoop_etwitterists_calls");

  private static final SearchCounter EtwitterISTS_CALLS_EtwitterCEPTION =
      SearchCounter.etwitterport("hadoop_etwitterists_calls_etwitterception");

  public TimeLimitedHadoopEtwitteristsCall(FileSystem fileSystem) {
    // This times varies. Sometimes it's very quick, sometimes it takes some amount of seconds.
    // Do a rate on hadoop_etwitterists_calls_latency_ms to see for yourself.
    this(fileSystem, 30);
  }

  public TimeLimitedHadoopEtwitteristsCall(FileSystem fileSystem, int timeLimitInSeconds) {
    // We do hadoop calls once every "FLUSH_CHECK_PERIOD" minutes. If a call takes
    // a long time (say 10 minutes), we'll use a new thread for the netwittert call, to give it
    // a chance to complete.
    //
    // Let's say every call takes 2 hours. After 5 calls, the 6th call won't be able
    // to take a thread out of the thread pool and it will time out. That's fair, we don't
    // want to keep sending requests to Hadoop if the situation is so dire.
    EtwitterecutorService etwitterecutorService = Etwitterecutors.newFitwitteredThreadPool(5);
    this.hadoopCallsTimeLimiter = SimpleTimeLimiter.create(etwitterecutorService);
    this.fileSystem = fileSystem;
    this.timeLimitInSeconds = timeLimitInSeconds;
  }


  protected boolean hadoopEtwitteristsCall(Path path) throws IOEtwitterception {
    SearchTimer timer = EtwitterISTS_CALLS_TIMER.startNewTimer();
    boolean res =  fileSystem.etwitterists(path);
    EtwitterISTS_CALLS_TIMER.stopTimerAndIncrement(timer);
    return res;
  }

  /**
   * Checks if a path etwitterists on Hadoop.
   *
   * @return true if the path etwitterists.
   * @throws Etwitterception see etwitterceptions thrown by callWithTimeout
   */
  boolean etwitterists(Path path) throws Etwitterception {
    try {
      boolean result = hadoopCallsTimeLimiter.callWithTimeout(new Callable<Boolean>() {
        @Override
        public Boolean call() throws Etwitterception {
          return hadoopEtwitteristsCall(path);
        }
      }, timeLimitInSeconds, TimeUnit.SECONDS);

      return result;
    } catch (Etwitterception etwitter) {
      EtwitterISTS_CALLS_EtwitterCEPTION.increment();
      // No need to print and rethrow, it will be printed when caught upstream.
      throw etwitter;
    }
  }
}
