package com.tw ter.search.earlyb rd.part  on;

 mport java. o. OExcept on;
 mport java.ut l.concurrent.Callable;
 mport java.ut l.concurrent.ExecutorServ ce;
 mport java.ut l.concurrent.Executors;
 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.ut l.concurrent.S mpleT  L m er;
 mport com.google.common.ut l.concurrent.T  L m er;

 mport org.apac .hadoop.fs.F leSystem;
 mport org.apac .hadoop.fs.Path;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchT  r;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;

/**
 * Abstracts deta ls of mak ng t   l m ed calls to hadoop.
 *
 * Dur ng  M-3556   d scovered that hadoop AP  calls can take a long t   (seconds, m nutes)
 *  f t  Hadoop clsuter  s  n a bad state.   code was generally not prepared for that and
 * t  caused var ous  ssues. T  class  s a f x on top of t  Hadoop AP 's ex sts call and
 *    ntroduces a t  out.
 *
 * T  ma n mot vat on for hav ng t  as an external class  s for testab l y.
 */
publ c class T  L m edHadoopEx stsCall {
  pr vate f nal T  L m er hadoopCallsT  L m er;
  pr vate f nal F leSystem f leSystem;
  pr vate f nal  nt t  L m  nSeconds;

  pr vate stat c f nal SearchT  rStats EX STS_CALLS_T MER =
      SearchT  rStats.export("hadoop_ex sts_calls");

  pr vate stat c f nal SearchCounter EX STS_CALLS_EXCEPT ON =
      SearchCounter.export("hadoop_ex sts_calls_except on");

  publ c T  L m edHadoopEx stsCall(F leSystem f leSystem) {
    // T  t  s var es. So t  s  's very qu ck, so t  s   takes so  amount of seconds.
    // Do a rate on hadoop_ex sts_calls_latency_ms to see for y self.
    t (f leSystem, 30);
  }

  publ c T  L m edHadoopEx stsCall(F leSystem f leSystem,  nt t  L m  nSeconds) {
    //   do hadoop calls once every "FLUSH_CHECK_PER OD" m nutes.  f a call takes
    // a long t   (say 10 m nutes),  'll use a new thread for t  next call, to g ve  
    // a chance to complete.
    //
    // Let's say every call takes 2 h s. After 5 calls, t  6th call won't be able
    // to take a thread out of t  thread pool and   w ll t   out. That's fa r,   don't
    // want to keep send ng requests to Hadoop  f t  s uat on  s so d re.
    ExecutorServ ce executorServ ce = Executors.newF xedThreadPool(5);
    t .hadoopCallsT  L m er = S mpleT  L m er.create(executorServ ce);
    t .f leSystem = f leSystem;
    t .t  L m  nSeconds = t  L m  nSeconds;
  }


  protected boolean hadoopEx stsCall(Path path) throws  OExcept on {
    SearchT  r t  r = EX STS_CALLS_T MER.startNewT  r();
    boolean res =  f leSystem.ex sts(path);
    EX STS_CALLS_T MER.stopT  rAnd ncre nt(t  r);
    return res;
  }

  /**
   * C cks  f a path ex sts on Hadoop.
   *
   * @return true  f t  path ex sts.
   * @throws Except on see except ons thrown by callW hT  out
   */
  boolean ex sts(Path path) throws Except on {
    try {
      boolean result = hadoopCallsT  L m er.callW hT  out(new Callable<Boolean>() {
        @Overr de
        publ c Boolean call() throws Except on {
          return hadoopEx stsCall(path);
        }
      }, t  L m  nSeconds, T  Un .SECONDS);

      return result;
    } catch (Except on ex) {
      EX STS_CALLS_EXCEPT ON. ncre nt();
      // No need to pr nt and rethrow,   w ll be pr nted w n caught upstream.
      throw ex;
    }
  }
}
