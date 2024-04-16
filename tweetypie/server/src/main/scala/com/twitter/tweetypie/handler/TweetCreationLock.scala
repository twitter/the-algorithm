package com.tw ter.t etyp e
package handler

 mport com.tw ter.servo.cac .Cac 
 mport com.tw ter.servo.ut l.Scr be
 mport com.tw ter.t etyp e.serverut l.Except onCounter
 mport com.tw ter.t etyp e.thr ftscala.PostT etResult
 mport com.tw ter.t etyp e.ut l.T etCreat onLock.Key
 mport com.tw ter.t etyp e.ut l.T etCreat onLock.State
 mport com.tw ter.ut l.Base64Long
 mport scala.ut l.Random
 mport scala.ut l.control.NoStackTrace
 mport scala.ut l.control.NonFatal

/**
 * T  except on  s returned from T etCreat onLock  f t re  s an
 *  n-progress cac  entry for t  key.    s poss ble that t  key
 * ex sts because t  key was not properly cleaned up, but  's
 *  mposs ble to d fferent ate bet en t se cases.   resolve t  by
 * return ng T etCreat on nProgress and hav ng a (relat vely) short TTL
 * on t  cac  entry so that t  cl ent and/or user may retry.
 */
case object T etCreat on nProgress extends Except on w h NoStackTrace

/**
 * Thrown w n t  T etCreat onLock d scovers that t re  s already
 * a t et w h t  spec f ed un queness  d.
 */
case class Dupl cateT etCreat on(t et d: T et d) extends Except on w h NoStackTrace

tra  T etCreat onLock {
  def apply(
    key: Key,
    dark: Boolean,
    nullcast: Boolean
  )(
     nsert: => Future[PostT etResult]
  ): Future[PostT etResult]
  def unlock(key: Key): Future[Un ]
}

object Cac BasedT etCreat onLock {

  /**
   *  nd cates that sett ng t  lock value fa led because t  state of
   * that key  n t  cac  has been changed (by anot r process or
   * cac  ev ct on).
   */
  case object UnexpectedCac State extends Except on w h NoStackTrace

  /**
   * Thrown w n t  process of updat ng t  lock cac  fa led more
   * than t  allo d number of t  s.
   */
  case class Retr esExhausted(fa lures: Seq[Except on]) extends Except on w h NoStackTrace

  def shouldRetry(e: Except on): Boolean =
    e match {
      case T etCreat on nProgress => false
      case _: Dupl cateT etCreat on => false
      case _: Retr esExhausted => false
      case _ => true
    }

  def ttlChooser(shortTtl: Durat on, longTtl: Durat on): (Key, State) => Durat on =
    (_, state) =>
      state match {
        case _: State.AlreadyCreated => longTtl
        case _ => shortTtl
      }

  /**
   * T  log format  s tab-separated (base 64 t et_ d, base 64
   * un queness_ d).  's logged t  way  n order to m n m ze t 
   * storage requ re nt and to make   easy to analyze. Each log l ne
   * should be 24 bytes,  nclud ng newl ne.
   */
  val formatUn quenessLogEntry: ((Str ng, T et d)) => Str ng = {
    case (un queness d, t et d) => Base64Long.toBase64(t et d) + "\t" + un queness d
  }

  /**
   * Scr be t  un queness  d pa red w h t  t et  d so that   can
   * track t  rate of fa lures of t  un queness  d c ck by
   * detect ng mult ple t ets created w h t  sa  un queness  d.
   *
   * Scr be to a test category because   only need to keep t 
   *  nformat on around for long enough to f nd any dupl cates.
   */
  val Scr beUn queness d: FutureEffect[(Str ng, T et d)] =
    Scr be("test_t etyp e_un queness_ d") contramap formatUn quenessLogEntry

  pr vate[t ] val Un queness dLog = Logger("com.tw ter.t etyp e.handler.Un queness d")

  /**
   * Log t  un queness  ds to a standard logger (for use w n  's
   * not product on traff c).
   */
  val LogUn queness d: FutureEffect[(Str ng, T et d)] = FutureEffect[(Str ng, T et d)] { rec =>
    Un queness dLog. nfo(formatUn quenessLogEntry(rec))
    Future.Un 
  }

  pr vate val log = Logger(getClass)
}

/**
 * T  class adds lock ng around T et creat on, to prevent creat ng
 * dupl cate t ets w n two  dent cal requests arr ve s multaneously.
 * A lock  s created  n cac  us ng t  user  d and a hash of t  t et text
 *  n t  case of t ets, or t  s ce_status_ d  n t  case of ret ets.
 *  f anot r process attempts to lock for t  sa  user and hash, t  request
 * fa ls as a dupl cate.  T  lock lasts for 10 seconds  f    s not deleted.
 * G ven t  hard t  out of 5 seconds on all requests,   should never take
 * us longer than 5 seconds to create a request, but  've observed t  s of up
 * to 10 seconds to create statuses for so  of   more popular users.
 *
 * W n a request w h a un queness  d  s successful, t   d of t 
 * created t et w ll be stored  n t  cac  so that subsequent
 * requests can retr eve t  or g nally-created t et rat r than
 * dupl cat ng creat on or gett ng an except on.
 */
class Cac BasedT etCreat onLock(
  cac : Cac [Key, State],
  maxTr es:  nt,
  stats: StatsRece ver,
  logUn queness d: FutureEffect[(Str ng, T et d)])
    extends T etCreat onLock {
   mport Cac BasedT etCreat onLock._

  pr vate[t ] val eventCounters = stats.scope("event")

  pr vate[t ] def event(k: Key, na : Str ng): Un  = {
    log.debug(s"$na :$k")
    eventCounters.counter(na ). ncr()
  }

  pr vate[t ] def retryLoop[A](act on: => Future[A]): Future[A] = {
    def go(fa lures: L st[Except on]): Future[A] =
       f (fa lures.length >= maxTr es) {
        Future.except on(Retr esExhausted(fa lures.reverse))
      } else {
        act on.rescue {
          case e: Except on  f shouldRetry(e) => go(e :: fa lures)
        }
      }

    go(N l)
  }

  pr vate[t ] val lockerExcept ons = Except onCounter(stats)

  /**
   * Obta n t  lock for creat ng a t et.  f t   thod completes
   * w hout throw ng an except on, t n t  lock value was
   * successfully set  n cac , wh ch  nd cates a h gh probab l y
   * that t   s t  only process that  s attempt ng to create t 
   * t et. (T  uncerta nty co s from t  poss b l y of lock
   * entr es m ss ng from t  cac .)
   *
   * @throws T etCreat on nProgress  f t re  s anot r process
   *   try ng to create t  t et.
   *
   * @throws Dupl cateT etCreat on  f a t et has already been
   *   created for a dupl cate request. T  except on has t   d of
   *   t  created t et.
   *
   * @throws Retr esExhausted  f obta n ng t  lock fa led more than
   *   t  requ s e number of t  s.
   */
  pr vate[t ] def obta nLock(k: Key, token: Long): Future[T  ] = retryLoop {
    val lockT   = T  .now

    // Get t  current state for t  key.
    cac 
      .getW hC cksum(Seq(k))
      .flatMap( n  alStateKvr => Future.const( n  alStateKvr(k)))
      .flatMap {
        case None =>
          // Noth ng  n cac  for t  key
          cac 
            .add(k, State. nProgress(token, lockT  ))
            .flatMap {
              case true => Future.value(lockT  )
              case false => Future.except on(UnexpectedCac State)
            }
        case So ((Throw(e), _)) =>
          Future.except on(e)
        case So ((Return(st), cs)) =>
          st match {
            case State.Unlocked =>
              // T re  s an Unlocked entry for t  key, wh ch
              //  mpl es that a prev ous attempt was cleaned up.
              cac 
                .c ckAndSet(k, State. nProgress(token, lockT  ), cs)
                .flatMap {
                  case true => Future.value(lockT  )
                  case false => Future.except on(UnexpectedCac State)
                }
            case State. nProgress(cac dToken, creat onStartedT  stamp) =>
               f (cac dToken == token) {
                // T re  s an  n-progress entry for *t  process*. T 
                // can happen on a retry  f t  `add` actually succeeds
                // but t  future fa ls. T  retry can return t  result
                // of t  add that   prev ously tr ed.
                Future.value(creat onStartedT  stamp)
              } else {
                // T re  s an  n-progress entry for *a d fferent
                // process*. T   mpl es that t re  s anot r t et
                // creat on  n progress for *t  t et*.
                val t etCreat onAge = T  .now - creat onStartedT  stamp
                k.un queness d.foreach {  d =>
                  log. nfo(
                    "Found an  n-progress t et creat on for un queness  d %s %s ago"
                      .format( d, t etCreat onAge)
                  )
                }
                stats.stat(" n_progress_age_ms").add(t etCreat onAge. nM ll seconds)
                Future.except on(T etCreat on nProgress)
              }
            case State.AlreadyCreated(t et d, creat onStartedT  stamp) =>
              // Anot r process successfully created a t et for t 
              // key.
              val t etCreat onAge = T  .now - creat onStartedT  stamp
              stats.stat("already_created_age_ms").add(t etCreat onAge. nM ll seconds)
              Future.except on(Dupl cateT etCreat on(t et d))
          }
      }
  }

  /**
   * Attempt to remove t  process' lock entry from t  cac . T 
   *  s done by wr  ng a short-l ved tombstone, so that   can ensure
   * that   only overwr e t  entry  f    s st ll an entry for t 
   * process  nstead of anot r process' entry.
   */
  pr vate[t ] def cleanupLoop(k: Key, token: Long): Future[Un ] =
    retryLoop {
      //  nstead of delet ng t  value,   attempt to wr e Unlocked,
      // because   only want to delete    f   was t  value that  
      // wrote  selves, and t re  s no delete call that  s
      // cond  onal on t  extant value.
      cac 
        .getW hC cksum(Seq(k))
        .flatMap(kvr => Future.const(kvr(k)))
        .flatMap {
          case None =>
            // Noth ng  n t  cac  for t  t et creat on, so cleanup
            //  s successful.
            Future.Un 

          case So ((tryV, cs)) =>
            //  f   fa led try ng to deser al ze t  value, t n  
            // want to let t  error bubble up, because t re  s no good
            // recovery procedure, s nce   can't tell w t r t  entry
            //  s  s.
            Future.const(tryV).flatMap {
              case State. nProgress(presentToken, _) =>
                 f (presentToken == token) {
                  // T   s * *  n-progress marker, so   want to
                  // overwr e   w h t  tombstone.  f c ckAndSet
                  // returns false, that's OK, because that  ans
                  // so one else overwrote t  value, and   don't have
                  // to clean   up anymore.
                  cac .c ckAndSet(k, State.Unlocked, cs).un 
                } else {
                  //  nd cates that anot r request has overwr ten  
                  // state before   cleaned   up. T  should only
                  // happen w n   token was cleared from cac  and
                  // anot r process started a dupl cate create. T 
                  // should be very  nfrequent.   count   just to be
                  // sure.
                  event(k, "ot r_attempt_ n_progress")
                  Future.Un 
                }

              case _ =>
                // Cleanup has succeeded, because   are not respons ble
                // for t  cac  entry anymore.
                Future.Un 
            }
        }
    }.onSuccess { _ => event(k, "cleanup_attempt_succeeded") }
      .handle {
        case _ => event(k, "cleanup_attempt_fa led")
      }

  /**
   * Mark that a t et has been successfully created. Subsequent calls
   * to `apply` w h t  key w ll rece ve a Dupl cateT etCreat on
   * except on w h t  spec f ed  d.
   */
  pr vate[t ] def creat onComplete(k: Key, t et d: T et d, lockT  : T  ): Future[Un ] =
    // Uncond  onally set t  state because regardless of t 
    // value present,   know that   want to trans  on to t 
    // AlreadyCreated state for t  key.
    retryLoop(cac .set(k, State.AlreadyCreated(t et d, lockT  )))
      .onSuccess(_ => event(k, "mark_created_succeeded"))
      .onFa lure { case _ => event(k, "mark_created_fa led") }
      //  f t  fa ls,  's OK for t  request to complete
      // successfully, because  's more harmful to create t  t et
      // and return fa lure than    s to complete   successfully,
      // but fa l to honor t  un queness  d next t  .
      .handle { case NonFatal(_) => }

  pr vate[t ] def createW hLock(
    k: Key,
    create: => Future[PostT etResult]
  ): Future[PostT etResult] = {
    val token = Random.nextLong
    event(k, "lock_attempted")

    obta nLock(k, token)
      .onSuccess { _ => event(k, "lock_obta ned") }
      .handle {
        //  f   run out of retr es w n try ng to get t  lock, t n
        // just go a ad w h t et creat on.   should keep an eye on
        // how frequently t  happens, because t   ans that t 
        // only s gn that t   s happen ng w ll be dupl cate t et
        // creat ons.
        case Retr esExhausted(fa lures) =>
          event(k, "lock_fa lure_ gnored")
          // Treat t  as t  t   that   obta ned t  lock.
          T  .now
      }
      .onFa lure {
        case e => lockerExcept ons(e)
      }
      .flatMap { lockT   =>
        create.transform {
          case r @ Return(PostT etResult(_, So (t et), _, _, _, _, _)) =>
            event(k, "create_succeeded")

            k.un queness d.foreach { u => logUn queness d((u, t et. d)) }

            // Update t  lock entry to re mber t   d of t  t et  
            // created and extend t  TTL.
            creat onComplete(k, t et. d, lockT  ).before(Future.const(r))
          case ot r =>
            ot r match {
              case Throw(e) =>
                log.debug(s"T et creat on fa led for key $k", e)
              case Return(r) =>
                log.debug(s"T et creat on fa led for key $k, so unlock ng: $r")
            }

            event(k, "create_fa led")

            // Attempt to clean up t  lock after t  fa led create.
            cleanupLoop(k, token).before(Future.const(ot r))
        }
      }
  }

  /**
   * Make a best-effort attempt at remov ng t  dupl cate cac  entry
   * for t  key.  f t  fa ls,    s not catastroph c. T  worst-case
   * behav or should be that t  user has to wa  for t  short TTL to
   * elapse before t et ng succeeds.
   */
  def unlock(k: Key): Future[Un ] =
    retryLoop(cac .delete(k).un ).onSuccess(_ => event(k, "deleted"))

  /**
   * Prevent dupl cate t et creat on.
   *
   * Ensures that no more than one t et creat on for t  sa  key  s
   * happen ng at t  sa  t  .  f `create` fa ls, t n t  key w ll
   * be removed from t  cac .  f   succeeds, t n t  key w ll be
   * reta ned.
   *
   * @throws Dupl cateT etCreat on  f a t et has already been
   *   created by a prev ous request. T  except on has t   d of t 
   *   created t et.
   *
   * @throws T etCreat on nProgress. See t  docu ntat on above.
   */
  def apply(
    k: Key,
     sDark: Boolean,
    nullcast: Boolean
  )(
    create: => Future[PostT etResult]
  ): Future[PostT etResult] =
     f ( sDark) {
      event(k, "dark_create")
      create
    } else  f (nullcast) {
      event(k, "nullcast_create")
      create
    } else {
      createW hLock(k, create).onFa lure {
        // Anot r process  s creat ng t  sa  t et (or has already
        // created  )
        case T etCreat on nProgress =>
          event(k, "t et_creat on_ n_progress")
        case _: Dupl cateT etCreat on =>
          event(k, "t et_already_created")
        case _ =>
      }
    }
}
