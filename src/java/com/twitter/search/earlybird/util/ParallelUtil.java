package com.tw ter.search.earlyb rd.ut l;

 mport java.ut l.L st;
 mport java.ut l.concurrent.ExecutorServ ce;
 mport java.ut l.concurrent.Executors;
 mport java.ut l.concurrent.ThreadFactory;
 mport java.ut l.stream.Collectors;

 mport com.google.common.ut l.concurrent.ThreadFactoryBu lder;

 mport com.tw ter.ut l.Awa ;
 mport com.tw ter.ut l.Future;
 mport com.tw ter.ut l.Future$;
 mport com.tw ter.ut l.FuturePool;
 mport com.tw ter.ut l.FuturePool$;

publ c f nal class ParallelUt l {
  pr vate ParallelUt l() {
  }

  publ c stat c <T, R> L st<R> parmap(Str ng threadNa , C ckedFunct on<T, R> fn, L st<T>  nput)
      throws Except on {
    return parmap(threadNa ,  nput.s ze(), fn,  nput);
  }

  /**
   * Runs a funct on  n parallel across t  ele nts of t  l st, and throws an except on  f any
   * of t  funct ons throws, or returns t  results.
   *
   * Uses as many threads as t re are ele nts  n t   nput, so only use t  for tasks that
   * requ re s gn f cant CPU for each ele nt, and have less ele nts than t  number of cores.
   */
  publ c stat c <T, R> L st<R> parmap(
      Str ng threadNa ,  nt threadPoolS ze, C ckedFunct on<T, R> fn, L st<T>  nput)
      throws Except on {
    ExecutorServ ce executor = Executors.newF xedThreadPool(threadPoolS ze,
        bu ldThreadFactory(threadNa ));
    FuturePool futurePool = FuturePool$.MODULE$.apply(executor);

    L st<Future<R>> futures =  nput
        .stream()
        .map( n -> futurePool.apply(() -> {
          try {
            return fn.apply( n);
          } catch (Except on e) {
            throw new Runt  Except on(e);
          }
        })).collect(Collectors.toL st());

    try {
      return Awa .result(Future$.MODULE$.collect(futures));
    } f nally {
      executor.shutdownNow();
    }
  }

  pr vate stat c ThreadFactory bu ldThreadFactory(Str ng threadNa Format) {
    return new ThreadFactoryBu lder()
        .setNa Format(threadNa Format)
        .setDaemon(false)
        .bu ld();
  }

  @Funct onal nterface
  publ c  nterface C ckedFunct on<T, R> {
    /**
     * A funct on from T to R that throws c cked Except ons.
     */
    R apply(T t) throws Except on;
  }
}
