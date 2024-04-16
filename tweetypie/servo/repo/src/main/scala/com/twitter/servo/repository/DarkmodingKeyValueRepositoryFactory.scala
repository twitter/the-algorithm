package com.tw ter.servo.repos ory

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.cac .{Cac Observer, Cac d, Lock ngCac }
 mport com.tw ter.servo.repos ory
 mport com.tw ter.servo.repos ory.Cac dResult.{Handler, HandlerFactory}
 mport com.tw ter.servo.ut l._
 mport com.tw ter.ut l._

 mport scala.ut l.control.NoStackTrace

object Darkmod ngKeyValueRepos oryFactory {
  val DefaultEwmaHalfL fe = 5.m nutes
  val DefaultRecentW ndow = 10.seconds
  val DefaultW ndowS ze = 5000
  val DefaultAva lab l yFromSuccessRate =
    Ava lab l y.l nearlyScaled(h ghWaterMark = 0.98, lowWaterMark = 0.75, m nAva lab l y = 0.02)

  def DefaultEwmaTracker = new EwmaSuccessRateTracker(DefaultEwmaHalfL fe)
  def DefaultRecentW ndowTracker = SuccessRateTracker.recentW ndo d(DefaultRecentW ndow)
  def DefaultRoll ngW ndowTracker = SuccessRateTracker.roll ngW ndow(DefaultW ndowS ze)

  /**
   * Wraps an underly ng repos ory, wh ch can be manually or automat cally darkmoded.
   *
   * Auto-darkmod ng  s based on success rate (SR) as reported by a [[SuccessRateTracker]].
   *
   * @param readFromUnderly ng Open: operate normally. Closed: read from backupRepo regardless of SR.
   * @param autoDarkmode Open: auto-darkmod ng k cks  n based on SR. Closed: auto-darkmod ng w ll not k ck  n regardless of SR.
   * @param stats Used to record success rate and ava lab l y; often should be scoped to t  repo for stats nam ng
   * @param underly ngRepo T  underly ng repo; read from w n not darkmoded
   * @param backupRepo T  repo to read from w n darkmoded; defaults to an always-fa l ng repo.
   * @param successRateTracker Strategy for report ng SR, usually over a mov ng w ndow
   * @param ava lab l yFromSuccessRate Funct on to calculate ava lab l y based on success rate
   * @param should gnore don't count certa n except ons as fa lures, e.g. cancellat ons
   */
  def darkmod ng[Q <: Seq[K], K, V](
    readFromUnderly ng: Gate[Un ],
    autoDarkmode: Gate[Un ],
    stats: StatsRece ver,
    underly ngRepo: KeyValueRepos ory[Q, K, V],
    backupRepo: KeyValueRepos ory[Q, K, V] =
      KeyValueRepos ory.alwaysFa l ng[Q, K, V](DarkmodedExcept on),
    successRateTracker: SuccessRateTracker = DefaultRecentW ndowTracker,
    ava lab l yFromSuccessRate: Double => Double = DefaultAva lab l yFromSuccessRate,
    should gnore: Throwable => Boolean = SuccessRateTrack ngRepos ory. sCancellat on
  ): KeyValueRepos ory[Q, K, V] = {
    val (successRateTrack ngRepoFactory, successRateGate) =
      SuccessRateTrack ngRepos ory.w hGate[Q, K, V](
        stats,
        ava lab l yFromSuccessRate,
        successRateTracker.observed(stats),
        should gnore
      )
    val gate = mkGate(successRateGate, readFromUnderly ng, autoDarkmode)

    Repos ory.selected(
      q => gate(()),
      successRateTrack ngRepoFactory(underly ngRepo),
      backupRepo
    )
  }

  /**
   * Produces a cach ng repos ory around an underly ng repos ory, wh ch
   * can be manually or automat cally darkmoded.
   *
   * @param underly ngRepo T  underly ng repo from wh ch to read
   * @param cac  T  typed lock ng cac  to fall back to w n darkmoded
   * @param p cker Used to break t es w n a value be ng wr ten  s already present  n cac 
   * @param readFromUnderly ng Open: operate normally. Closed: read from cac  regardless of SR.
   * @param autoDarkmode Open: auto-darkmod ng k cks  n based on SR. Closed: auto-darkmod ng w ll not k ck  n regardless of SR.
   * @param cac Observer Observes  nteract ons w h t  cac ; often should be scoped to t  repo for stats nam ng
   * @param stats Used to record var ous stats; often should be scoped to t  repo for stats nam ng
   * @param handler a [[Handler]] to use w n not darkmoded
   * @param successRateTracker Strategy for report ng SR, usually over a mov ng w ndow
   * @param ava lab l yFromSuccessRate Funct on to calculate ava lab l y based on success rate
   * @param should gnore don't count certa n except ons as fa lures, e.g. cancellat ons
   */
  def darkmod ngCach ng[K, V, Cac Key](
    underly ngRepo: KeyValueRepos ory[Seq[K], K, V],
    cac : Lock ngCac [K, Cac d[V]],
    p cker: Lock ngCac .P cker[Cac d[V]],
    readFromUnderly ng: Gate[Un ],
    autoDarkmode: Gate[Un ],
    cac Observer: Cac Observer,
    stats: StatsRece ver,
    handler: Handler[K, V],
    successRateTracker: SuccessRateTracker = DefaultRecentW ndowTracker,
    ava lab l yFromSuccessRate: Double => Double = DefaultAva lab l yFromSuccessRate,
    should gnore: Throwable => Boolean = SuccessRateTrack ngRepos ory. sCancellat on,
    wr eSoftTtlStep: Gate[Un ] = Gate.False,
    cac ResultObserver: Cach ngKeyValueRepos ory.Cac ResultObserver[K, V] =
      Cac ResultObserver.un [K, V]: Effect[Cac ResultObserver.Cach ngRepos oryResult[K, V]]
  ): Cach ngKeyValueRepos ory[Seq[K], K, V] = {
    val (successRateTrack ngRepoFactory, successRateGate) =
      SuccessRateTrack ngRepos ory.w hGate[Seq[K], K, V](
        stats,
        ava lab l yFromSuccessRate,
        successRateTracker.observed(stats),
        should gnore
      )
    val gate = mkGate(successRateGate, readFromUnderly ng, autoDarkmode)

    new Cach ngKeyValueRepos ory[Seq[K], K, V](
      successRateTrack ngRepoFactory(underly ngRepo),
      cac ,
      repos ory.keysAsQuery,
      mkHandlerFactory(handler, gate),
      p cker,
      cac Observer,
      wr eSoftTtlStep = wr eSoftTtlStep,
      cac ResultObserver = cac ResultObserver
    )
  }

  /**
   * Create a compos e gate su able for controll ng darkmod ng, usually v a dec der
   *
   * @param successRate gate that should close and open accord ng to success rate (SR) changes
   * @param readFromUnderly ng  f open: returned gate operates normally.  f closed: returned gate w ll be closed regardless of SR
   * @param autoDarkMode  f open: close gate accord ng to SR.  f closed: gate  gnores SR changes
   * @return
   */
  def mkGate(
    successRate: Gate[Un ],
    readFromUnderly ng: Gate[Un ],
    autoDarkMode: Gate[Un ]
  ): Gate[Un ] =
    readFromUnderly ng & (successRate | !autoDarkMode)

  /**
   * Construct a [[Cac dResult.HandlerFactory]] w h sane defaults for use w h a cach ng darkmoded repos ory
   * @param softTtl TTL for soft-exp rat on of values  n t  cac 
   * @param exp ry Used to apply t  softTTL (e.g. f xed vs randomly perturbed)
   */
  def mkDefaultHandler[K, V](
    softTtl: Opt on[V] => Durat on,
    exp ry: Cac dResult.Exp ry
  ): Handler[K, V] =
    Cac dResult.Handler(
      Cac dResult.fa luresAreDoNotCac ,
      Cac dResult.Handler(Cac dResult.softTtlExp rat on(softTtl, exp ry))
    )

  pr vate[repos ory] def mkHandlerFactory[Cac Key, V, K](
    handler: Handler[K, V],
    successRateGate: Gate[Un ]
  ): HandlerFactory[Seq[K], K, V] =
    query =>
       f (successRateGate(())) handler
      else Cac dResult.cac Only
}

/**
 * T  except on  s returned from a repos ory w n    s auto-darkmoded due to low backend
 * success rate, or darkmoded manually v a gate (usually a dec der).
 */
class DarkmodedExcept on extends Except on w h NoStackTrace
object DarkmodedExcept on extends DarkmodedExcept on
