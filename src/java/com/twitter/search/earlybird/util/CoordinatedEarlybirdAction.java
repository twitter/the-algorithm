package com.tw ter.search.earlyb rd.ut l;

 mport java.ut l.Opt onal;
 mport java.ut l.Random;
 mport java.ut l.concurrent.atom c.Atom cBoolean;
 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.base.Stopwatch;

 mport org.apac .zookeeper.KeeperExcept on;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.base.Except onalFunct on;
 mport com.tw ter.common.quant y.Amount;
 mport com.tw ter.common.quant y.T  ;
 mport com.tw ter.common.zookeeper.ServerSet;
 mport com.tw ter.common.zookeeper.ZooKeeperCl ent;
 mport com.tw ter.search.common.conf g.Conf g;
 mport com.tw ter.search.common.database.DatabaseConf g;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchCustomGauge;
 mport com.tw ter.search.common.ut l.zktrylock.TryLock;
 mport com.tw ter.search.common.ut l.zktrylock.ZooKeeperTryLockFactory;
 mport com.tw ter.search.earlyb rd.ServerSet mber;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdProperty;
 mport com.tw ter.search.earlyb rd.except on.Already nServerSetUpdateExcept on;
 mport com.tw ter.search.earlyb rd.except on.Earlyb rdExcept on;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd.except on.Not nServerSetUpdateExcept on;
 mport com.tw ter.search.earlyb rd.part  on.Dynam cPart  onConf g;
 mport com.tw ter.search.earlyb rd.part  on.Part  onConf g;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntSyncConf g;

/**
 * Ut l y class for execut ng tasks on Earlyb rds that need to be coord nated across repl cas
 * on t  sa  hash part  on.
 * Can be used for th ngs l ke coord nat ng opt m zat on on t  sa  t  sl ce.
 * W n enabled, a try-lock w ll be taken out  n zookeeper wh le t  task  s perfor d.
 * T  act on w ll attempt to leave t  part  on's server set.  f t  attempt fa ls, t  act on
 *  s aborted.
 */
publ c class Coord natedEarlyb rdAct on  mple nts Coord natedEarlyb rdAct on nterface {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Coord natedEarlyb rdAct on.class);

  pr vate stat c f nal Boolean COORD NATED_ACT ON_FLAG = Boolean.TRUE;
  pr vate stat c f nal Boolean NOT_COORD NATED_ACT ON_FLAG = Boolean.FALSE;

  pr vate f nal Str ng act onNa ;
  pr vate f nal Dynam cPart  onConf g dynam cPart  onConf g;
  @Nullable pr vate f nal ServerSet mber serverSet mber;
  pr vate f nal ZooKeeperTryLockFactory zooKeeperTryLockFactory;

  // W t r t  act on should be coord nated through zookeeper  n t  f rst place (could be
  // conf g'ed off).
  //  f t  act on  s coord nated, t  earlyb rd w ll leave  s server set w n perform ng t 
  // coord nated act on.
  pr vate f nal Atom cBoolean shouldSynchron ze;
  // W t r t  act on should ensure that t re are enough repl cas  n t  serverset (def ned by
  // maxAllo dRepl casNot nServerSet) before leav ng t  serverset.
  pr vate f nal boolean c ckNumRepl cas nServerSet;
  //  f t  many (or more) servers have left t  part  on,   cannot perform a coord nated act on
  pr vate f nal  nt maxAllo dRepl casNot nServerSet;
  // How long to lock out all ot r repl cas  n t  hash part  on for.
  // Should be so  small mult ple of how long t  act on  s expected to take, to allow for longer
  // runn ng cases.
  pr vate f nal long zkLockExp rat onT  M nutes;
  // Pref x for t  zookeeper lock used w n coord nat ng da ly updates.
  // Full na  should  nclude t  hash part  on number.
  pr vate f nal Str ng zkLockNa Pref x;
  //  f  're unable to re-jo n t  earlyb rd's server set dur ng coord nated updates,
  // how many t  s to retry.
  pr vate f nal  nt jo nServerSetRetr es;
  // How long to sleep bet en retr es  f unable to job back  nto server set.
  pr vate f nal  nt jo nServerSetRetrySleepM ll s;
  // How long to sleep bet en leav ng t  serverset and execut ng t  act on
  pr vate f nal  nt sleepAfterLeaveServerSetM ll s;

  // How many t  s a t  act on was called w h n a lock block.
  pr vate f nal SearchCounter numCoord natedFunct onCalls;
  pr vate f nal SearchCounter numCoord natedLeaveServersetCalls;

  pr vate f nal Cr  calExcept onHandler cr  calExcept onHandler;
  pr vate f nal Seg ntSyncConf g seg ntSyncConf g;

  /**
   * Create a Coord natedEarlyb rdAct on.
   *
   * @param act onNa  t  na  to be used for logg ng and t  pref x for conf g opt ons.
   * @param dynam cPart  onConf g ma nta ns t  current part  on ng conf gurat on for t 
   * earlyb rd. Used ma nly to determ ne t  hash part  on of t  earlyb rd.
   * @param serverSet mber t  server that t  act on  s runn ng on. To be used to leav ng and
   * rejo n ng t  server's server set.
   */
  publ c Coord natedEarlyb rdAct on(
      ZooKeeperTryLockFactory zooKeeperTryLockFactory,
      Str ng act onNa ,
      Dynam cPart  onConf g dynam cPart  onConf g,
      @Nullable ServerSet mber serverSet mber,
      Cr  calExcept onHandler cr  calExcept onHandler,
      Seg ntSyncConf g seg ntSyncConf g) {
    t .act onNa  = act onNa ;
    t .dynam cPart  onConf g = dynam cPart  onConf g;
    t .serverSet mber = serverSet mber;
    t .cr  calExcept onHandler = cr  calExcept onHandler;
    t .seg ntSyncConf g = seg ntSyncConf g;
    t .zooKeeperTryLockFactory = zooKeeperTryLockFactory;
     f (serverSet mber == null) {
      Precond  ons.c ckState(Conf g.env ron nt sTest(),
          "Should only have a null server  n tests");
    }

    t .shouldSynchron ze = new Atom cBoolean(
            Earlyb rdConf g.getBool(act onNa  + "_should_synchron ze", false));

    // Export w t r or not synchron zat on  s enabled as a stat
    SearchCustomGauge.export(
        act onNa  + "_should_synchron ze", () -> shouldSynchron ze.get() ? 1 : 0);

    t .c ckNumRepl cas nServerSet = Earlyb rdProperty.CHECK_NUM_REPL CAS_ N_SERVER_SET.get();

     nt numRepl cas =
        dynam cPart  onConf g.getCurrentPart  onConf g().getNumRepl cas nHashPart  on();
    t .maxAllo dRepl casNot nServerSet =
        Earlyb rdProperty.MAX_ALLOWED_REPL CAS_NOT_ N_SERVER_SET.get(numRepl cas);

    t .zkLockExp rat onT  M nutes =
        Earlyb rdConf g.getLong(act onNa  + "_lock_exp rat on_t  _m nutes", 60L);
    t .zkLockNa Pref x = act onNa  + "_for_hash_part  on_";
    t .jo nServerSetRetr es =
        Earlyb rdConf g.get nt(act onNa  + "_jo n_server_set_retr es", 20);
    t .jo nServerSetRetrySleepM ll s =
        Earlyb rdConf g.get nt(act onNa  + "_jo n_server_retry_sleep_m ll s", 2000);
    t .sleepAfterLeaveServerSetM ll s =
        Earlyb rdConf g.get nt("coord nated_act on_sleep_after_leave_server_set_m ll s", 30000);

    t .numCoord natedFunct onCalls = SearchCounter.export(act onNa  + "_num_coord nated_calls");
    t .numCoord natedLeaveServersetCalls =
        SearchCounter.export(act onNa  + "_num_coord nated_leave_serverset_calls");

     f (t .c ckNumRepl cas nServerSet) {
      LOG. nfo(
          "Coord nate act on conf g ({}): allo dNot n: {}, current number of repl cas: {}, "
              + "synchron zat on enabled: {}, c ckNumRepl cas nServerSet enabled: {}",
          act onNa ,
          maxAllo dRepl casNot nServerSet,
          dynam cPart  onConf g.getCurrentPart  onConf g().getNumRepl cas nHashPart  on(),
          shouldSynchron ze,
          t .c ckNumRepl cas nServerSet);
    } else {
      LOG. nfo(
          "Coord nate act on conf g ({}): synchron zat on enabled: {}, "
              + "c ckNumRepl cas nServerSet enabled: {}",
          act onNa ,
          shouldSynchron ze,
          t .c ckNumRepl cas nServerSet);
    }
  }


  @Overr de
  publ c <E extends Except on> boolean execute(
      Str ng descr pt on,
      Except onalFunct on<Boolean, Boolean, E> funct on)
          throws E, Coord natedEarlyb rdAct onLockFa led {
     f (t .shouldSynchron ze.get()) {
      return executeW hCoord nat on(descr pt on, funct on);
    } else {
      return funct on.apply(NOT_COORD NATED_ACT ON_FLAG);
    }
  }

  enum LeaveServerSetResult {
    SUCCESS,
    FA LURE,
    NOT_ N_SERVER_SET,
    NO_SERVER_SET_MEMBER
  }

  pr vate LeaveServerSetResult leaveServerSet() {
    LOG. nfo("Leav ng serv ng server set for " + act onNa );
    try {
      serverSet mber.leaveServerSet("Coord natedAct on: " + act onNa );
      return LeaveServerSetResult.SUCCESS;
    } catch (ServerSet.UpdateExcept on ex) {
       f (ex  nstanceof Not nServerSetUpdateExcept on) {
        LOG. nfo("No need to leave; already out of server set dur ng: "
            + act onNa , ex);
        return LeaveServerSetResult.NOT_ N_SERVER_SET;
      } else {
        LOG.warn("Unable to leave server set dur ng: " + act onNa , ex);
        return LeaveServerSetResult.FA LURE;
      }
    }
  }

  pr vate LeaveServerSetResult maybeLeaveServerSet() {
     f (serverSet mber != null) {
       f (serverSet mber. s nServerSet()) {

         f (!c ckNumRepl cas nServerSet) {
          return leaveServerSet();
        } else {
          Part  onConf g curPart  onConf g = dynam cPart  onConf g.getCurrentPart  onConf g();
          f nal  nt m nNumServers =
              curPart  onConf g.getNumRepl cas nHashPart  on() - maxAllo dRepl casNot nServerSet;
          Opt onal< nteger> numServerSet mbers = getNumberOfServerSet mbers();
          LOG. nfo("C ck ng number of repl cas before leav ng server set for " + act onNa 
              + ". Number of  mbers  s: " + numServerSet mbers + " m n mbers: " + m nNumServers);
           f (numServerSet mbers. sPresent() && numServerSet mbers.get() > m nNumServers) {
            return leaveServerSet();
          } else {
            LOG.warn("Not leav ng server set dur ng: " + act onNa );
            return LeaveServerSetResult.FA LURE;
          }
        }
      } else {
        LOG. nfo("Not  n server set, no need to leave  .");
        return LeaveServerSetResult.NOT_ N_SERVER_SET;
      }
    }

    return LeaveServerSetResult.NO_SERVER_SET_MEMBER;
  }

  pr vate <E extends Except on> boolean executeW hCoord nat on(
      f nal Str ng descr pt on,
      f nal Except onalFunct on<Boolean, Boolean, E> funct on)
      throws E, Coord natedEarlyb rdAct onLockFa led {
    Part  onConf g curPart  onConf g = dynam cPart  onConf g.getCurrentPart  onConf g();
    TryLock lock = zooKeeperTryLockFactory.createTryLock(
        DatabaseConf g.getLocalHostna (),
        seg ntSyncConf g.getZooKeeperSyncFullPath(),
        zkLockNa Pref x
        + curPart  onConf g.get ndex ngHashPart  on D(),
        Amount.of(zkLockExp rat onT  M nutes, T  .M NUTES)
    );

    f nal Atom cBoolean success = new Atom cBoolean(false);

    boolean gotLock = lock.tryW hLock(() -> {
      Stopwatch act onT m ng = Stopwatch.createStarted();

      LeaveServerSetResult leftServerSet = maybeLeaveServerSet();
       f (leftServerSet == LeaveServerSetResult.FA LURE) {
        LOG. nfo("Fa led to leave t  server set, w ll not execute act on.");
        return;
      }

      LOG. nfo("maybeLeaveServerSet returned: {}", leftServerSet);

      // Sleep for a short t   to g ve t  server so  t   to f n sh requests that    s currently
      // execut ng and allow roots so  t   to reg ster that t  host has left t  server set.
      //  f   d dn't do t  and t  coord nated act on  ncluded a full GC, t n latency and error
      // rate at t  root layer would sp ke h g r at t  t   of t  GC. SEARCH-35456
      try {
        Thread.sleep(sleepAfterLeaveServerSetM ll s);
      } catch ( nterruptedExcept on ex) {
        Thread.currentThread(). nterrupt();
      }

      LOG. nfo(act onNa  + " synchron zat on act on for " + descr pt on);

      try {
        numCoord natedFunct onCalls. ncre nt();
        numCoord natedLeaveServersetCalls. ncre nt();

        Boolean successValue = funct on.apply(COORD NATED_ACT ON_FLAG);
        success.set(successValue);
      } f nally {
         f (leftServerSet == LeaveServerSetResult.SUCCESS) {
          jo nServerSet();
        }
        LOG. nfo("{} synchron zat on act on for {} completed after {}, success: {}",
            act onNa ,
            descr pt on,
            act onT m ng,
            success.get());
      }
    });

     f (!gotLock) {
      Str ng errorMsg = act onNa  + ": Fa led to get zk  ndex ng lock for " + descr pt on;
      LOG. nfo(errorMsg);
      throw new Coord natedEarlyb rdAct onLockFa led(errorMsg);
    }
    return success.get();
  }

  @Overr de
  publ c vo d retryAct onUnt lRan(Str ng descr pt on, Runnable act on) {
    Random random = new Random(System.currentT  M ll s());

    boolean act onExecuted = false;
     nt attempts = 0;
    wh le (!act onExecuted) {
      try {
        attempts++;
        act onExecuted = t .execute(descr pt on,  sCoord nated -> {
          act on.run();
          return true;
        });
      } catch (Coord natedEarlyb rdAct onLockFa led ex) {
      }

       f (!act onExecuted) {
        // Var able sleep amount. T  reason for t  random sleeps
        //  s so that across mult ple earlyb rds t  doesn't get
        // executed  n so  sequence that depends on so th ng else
        // l ke maybe deploy t  s.   m ght be eas er to catch poss ble
        // problems  f  mpl c  order ngs l ke t  are not  ntroduced.
        long msToSleep = (10 + random.next nt(5)) * 1000L;
        try {
          Thread.sleep(msToSleep);
        } catch ( nterruptedExcept on ex) {
          LOG. nfo(" nterrupted wh le try ng to execute");
          Thread.currentThread(). nterrupt();
        }
      } else {
        LOG. nfo("Executed {} after {} attempts", act onNa , attempts);
      }
    }
  }

  /**
   * Gets t  current number of servers  n t  server's server set.
   * @return absent Opt onal  f   encountered an except on gett ng t  number of hosts.
   */
  pr vate Opt onal< nteger> getNumberOfServerSet mbers() {
    try {
      return serverSet mber != null ? Opt onal.of(serverSet mber.getNumberOfServerSet mbers())
          : Opt onal.empty();
    } catch ( nterruptedExcept on ex) {
      LOG.warn("Act on " + act onNa  + " was  nterrupted.", ex);
      Thread.currentThread(). nterrupt();
      return Opt onal.empty();
    } catch (ZooKeeperCl ent.ZooKeeperConnect onExcept on | KeeperExcept on ex) {
      LOG.warn("Except on dur ng " + act onNa , ex);
      return Opt onal.empty();
    }
  }

  /**
   * After a coord nated act on, jo n back t  earlyb rd's server set w h retr es
   * and sleeps  n bet en.
   */
  pr vate vo d jo nServerSet() {
    Precond  ons.c ckNotNull(serverSet mber);

    boolean jo ned = false;
    for ( nt   = 0;   < jo nServerSetRetr es;  ++) {
      try {
        serverSet mber.jo nServerSet("Coord natedAct on: " + act onNa );
        jo ned = true;
        break;
      } catch (Already nServerSetUpdateExcept on ex) {
        // Most l kely leav ng t  server set fa led
        jo ned = true;
        break;
      } catch (ServerSet.UpdateExcept on ex) {
        LOG.warn("Unable to jo n server set after " + act onNa  + " on attempt "
                +  , ex);
         f (  < (jo nServerSetRetr es - 1)) {
          try {
            Thread.sleep(jo nServerSetRetrySleepM ll s);
          } catch ( nterruptedExcept on e) {
            LOG.warn(" nterrupted wh le wa  ng to jo n back server set for: " + act onNa );
            // Preserve  nterrupt status.
            Thread.currentThread(). nterrupt();
            break;
          }
        }
      }
    }
     f (!jo ned) {
      Str ng  ssage = Str ng.format(
          "Unable to jo n server set after %s, sett ng fatal flag.",
          act onNa );
      Earlyb rdExcept on except on = new Earlyb rdExcept on( ssage);

      LOG.error( ssage, except on);
      cr  calExcept onHandler.handle(t , except on);
    }
  }


  @Overr de
  publ c boolean setShouldSynchron ze(boolean shouldSynchron zeParam) {
    boolean oldValue = t .shouldSynchron ze.getAndSet(shouldSynchron zeParam);
    LOG. nfo("Updated shouldSynchron ze for: " + act onNa  + " from " + oldValue
            + " to " + shouldSynchron zeParam);
    return oldValue;
  }

  @Overr de
  @V s bleForTest ng
  publ c long getNumCoord natedFunct onCalls() {
    return t .numCoord natedFunct onCalls.get();
  }

  @Overr de
  @V s bleForTest ng
  publ c long getNumCoord natedLeaveServersetCalls() {
    return t .numCoord natedLeaveServersetCalls.get();
  }
}
