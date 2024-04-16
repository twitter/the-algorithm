package com.tw ter.search.earlyb rd.ut l;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport com.tw ter.common.base.Except onalFunct on;

publ c  nterface Coord natedEarlyb rdAct on nterface {
    /**
     * Executes t  prov ded Funct on assoc ated w h t  g ven seg nt.
     * @param descr pt on a na  for t  act on to be exected.
     * @param funct on t  funct on to call  n a coord nated manner.
     *        As  nput, t  funct on w ll rece ve a flag  nd cat ng w t r or not    s be ng
     *        called  n a coord nated fash on. true  f    s, and false ot rw se.
     * @return true  ff t  funct on was executed, and funct on.apply() returned true;
     * throws Coord natedEarlyb rdAct onLockFa led  f funct on  s not executed (because lock
     * aqu s  on fa led).
     */
    <E extends Except on> boolean execute(
        Str ng descr pt on,
        Except onalFunct on<Boolean, Boolean, E> funct on)
          throws E, Coord natedEarlyb rdAct onLockFa led;

    /**
     * Set w t r t  act on should be synchron zed.
     *  f not, t  act on  s d rectly appl ed.  f yes, Earlyb rds w ll coord nate execut ng t 
     * act on v a ZooKeeperTryLocks.
     */
    boolean setShouldSynchron ze(boolean shouldSynchron zeParam);

    /**
     * Number of t  s t  coord nated act ons has been executed.
     * @return
     */
    @V s bleForTest ng
    long getNumCoord natedFunct onCalls();

    /**
     * Number of t  s   have left t  serverset.
     * @return
     */
    @V s bleForTest ng
    long getNumCoord natedLeaveServersetCalls();

    /**
     * Retry unt l   can run an act on on a s ngle  nstance  n t  serverset.
     * @param descr pt on Text descr pt on of t  act on.
     * @param act on A runnable to be ran.
     */
    vo d retryAct onUnt lRan(Str ng descr pt on, Runnable act on);
}
