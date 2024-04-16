package com.tw ter.servo.ut l

 mport com.tw ter.f nagle.stats.{StatsRece ver, Stat}
 mport com.tw ter.logg ng.{Logger, NullLogger}
 mport com.tw ter.ut l._

object FutureEffect {
  pr vate[t ] val _un  = FutureEffect[Any] { _ =>
    Future.Un 
  }

  /**
   * A FutureEffect that always succeeds.
   */
  def un [T]: FutureEffect[T] =
    _un .as nstanceOf[FutureEffect[T]]

  /**
   * A FutureEffect that always fa ls w h t  g ven except on.
   */
  def fa l[T](ex: Throwable): FutureEffect[T] =
    FutureEffect[T] { _ =>
      Future.except on(ex)
    }

  /**
   * L ft a funct on return ng a Future to a FutureEffect.
   */
  def apply[T](f: T => Future[Un ]) =
    new FutureEffect[T] {
      overr de def apply(x: T) = f(x)
    }

  /**
   * Performs all of t  effects  n order.  f any effect fa ls, t 
   * whole operat on fa ls, and t  subsequent effects are not
   * attempted.
   */
  def sequent ally[T](effects: FutureEffect[T]*): FutureEffect[T] =
    effects.foldLeft[FutureEffect[T]](un [T])(_ andT n _)

  /**
   * Perform all of t  effects concurrently.  f any effect fa ls, t 
   * whole operat on fa ls, but any of t  effects may or may not have
   * taken place.
   */
  def  nParallel[T](effects: FutureEffect[T]*): FutureEffect[T] =
    FutureEffect[T] { t =>
      Future.jo n(effects map { _(t) })
    }

  def fromPart al[T](f: Part alFunct on[T, Future[Un ]]) =
    FutureEffect[T] { x =>
       f (f. sDef nedAt(x)) f(x) else Future.Un 
    }

  /**
   * Comb nes two FutureEffects  nto one that d spatc s accord ng to a gate.   f t  gate  s
   * true, use `a`, ot rw se, use `b`.
   */
  def selected[T](cond  on: Gate[Un ], a: FutureEffect[T], b: FutureEffect[T]): FutureEffect[T] =
    selected(() => cond  on(), a, b)

  /**
   * Comb nes two FutureEffects  nto one that d spatc s accord ng to a nullary boolean funct on.
   *  f t  funct on returns true, use `a`, ot rw se, use `b`.
   */
  def selected[T](f: () => Boolean, a: FutureEffect[T], b: FutureEffect[T]): FutureEffect[T] =
    FutureEffect[T] { t =>
       f (f()) a(t) else b(t)
    }
}

/**
 * A funct on whose only result  s a future effect. T  wrapper
 * prov des conven ent comb nators.
 */
tra  FutureEffect[T] extends (T => Future[Un ]) { self =>

  /**
   * S mpl f ed vers on of `apply` w n type  s `Un `.
   */
  def apply()( mpl c  ev: Un  <:< T): Future[Un ] = self(())

  /**
   * Comb nes two Future effects, perform ng t  one f rst and
   * perform ng t  next one  f t  one succeeds.
   */
  def andT n(next: FutureEffect[T]): FutureEffect[T] =
    FutureEffect[T] { x =>
      self(x) flatMap { _ =>
        next(x)
      }
    }

  /**
   * Wraps t  FutureEffect w h a fa lure handl ng funct on that w ll be cha ned to
   * t  Future returned by t  FutureEffect.
   */
  def rescue(
    handler: Part alFunct on[Throwable, FutureEffect[T]]
  ): FutureEffect[T] =
    FutureEffect[T] { x =>
      self(x) rescue {
        case t  f handler. sDef nedAt(t) =>
          handler(t)(x)
      }
    }

  /**
   * Comb nes two future effects, perform ng t m both s multaneously.
   *  f e  r effect fa ls, t  result w ll be fa lure, but t  ot r
   * effects w ll have occurred.
   */
  def  nParallel(ot r: FutureEffect[T]) =
    FutureEffect[T] { x =>
      Future.jo n(Seq(self(x), ot r(x)))
    }

  /**
   * Perform t  effect only  f t  prov ded gate returns true.
   */
  def enabledBy(enabled: Gate[Un ]): FutureEffect[T] =
    enabledBy(() => enabled())

  /**
   * Perform t  effect only  f t  prov ded gate returns true.
   */
  def enabledBy(enabled: () => Boolean): FutureEffect[T] =
    only f { _ =>
      enabled()
    }

  /**
   * Perform t  effect only  f t  prov ded pred cate returns true
   * for t   nput.
   */
  def only f(pred cate: T => Boolean) =
    FutureEffect[T] { x =>
       f (pred cate(x)) self(x) else Future.Un 
    }

  /**
   *  Perform t  effect w h arg only  f t  cond  on  s true. Ot rw se just return Future Un 
   */
  def w n(cond  on: Boolean)(arg: => T): Future[Un ] =
     f (cond  on) self(arg) else Future.Un 

  /**
   * Adapt t  effect to take a d fferent  nput v a t  prov ded convers on.
   *
   * (Contravar ant map)
   */
  def contramap[U](g: U => T) = FutureEffect[U] { u =>
    self(g(u))
  }

  /**
   * Adapt t  effect to take a d fferent  nput v a t  prov ded convers on.
   *
   * (Contravar ant map)
   */
  def contramapFuture[U](g: U => Future[T]) = FutureEffect[U] { u =>
    g(u) flatMap self
  }

  /**
   * Adapt t  effect to take a d fferent  nput v a t  prov ded convers on.
   *  f t  output value of t  g ven funct on  s None, t  effect  s a no-op.
   */
  def contramapOpt on[U](g: U => Opt on[T]) =
    FutureEffect[U] {
      g andT n {
        case None => Future.Un 
        case So (t) => self(t)
      }
    }

  /**
   * Adapt t  effect to take a d fferent  nput v a t  prov ded convers on.
   *  f t  output value of t  g ven funct on  s future-None, t  effect  s a no-op.
   * (Contravar ant map)
   */
  def contramapFutureOpt on[U](g: U => Future[Opt on[T]]) =
    FutureEffect[U] { u =>
      g(u) flatMap {
        case None => Future.Un 
        case So (x) => self(x)
      }
    }

  /**
   * Adapt t  effect to take a sequence of  nput values.
   */
  def l ftSeq: FutureEffect[Seq[T]] =
    FutureEffect[Seq[T]] { seqT =>
      Future.jo n(seqT.map(self))
    }

  /**
   * Allow t  effect to fa l, but  m d ately return success. T 
   * effect  s not guaranteed to have f n s d w n  s future  s
   * ava lable.
   */
  def  gnoreFa lures: FutureEffect[T] =
    FutureEffect[T] { x =>
      Try(self(x)); Future.Un 
    }

  /**
   * Allow t  effect to fa l but always return success.  Unl ke  gnoreFa lures, t 
   * effect  s guaranteed to have f n s d w n  s future  s ava lable.
   */
  def  gnoreFa luresUponComplet on: FutureEffect[T] =
    FutureEffect[T] { x =>
      Try(self(x)) match {
        case Return(f) => f.handle { case _ => () }
        case Throw(_) => Future.Un 
      }
    }

  /**
   * Returns a cha ned FutureEffect  n wh ch t  g ven funct on w ll be called for any
   *  nput that succeeds.
   */
  def onSuccess(f: T => Un ): FutureEffect[T] =
    FutureEffect[T] { x =>
      self(x).onSuccess(_ => f(x))
    }

  /**
   * Returns a cha ned FutureEffect  n wh ch t  g ven funct on w ll be called for any
   *  nput that fa ls.
   */
  def onFa lure(f: (T, Throwable) => Un ): FutureEffect[T] =
    FutureEffect[T] { x =>
      self(x).onFa lure(t => f(x, t))
    }

  /**
   * Translate except on returned by a FutureEffect accord ng to a
   * Part alFunct on.
   */
  def translateExcept ons(
    translateExcept on: Part alFunct on[Throwable, Throwable]
  ): FutureEffect[T] =
    FutureEffect[T] { request =>
      self(request) rescue {
        case t  f translateExcept on. sDef nedAt(t) => Future.except on(translateExcept on(t))
        case t => Future.except on(t)
      }
    }

  /**
   * Wraps an effect w h retry log c.  W ll retry aga nst any fa lure.
   */
  def retry(backoffs: Stream[Durat on], t  r: T  r, stats: StatsRece ver): FutureEffect[T] =
    retry(RetryHandler.fa luresOnly(backoffs, t  r, stats))

  /**
   * Returns a new FutureEffect that executes t  effect w h n t  g ven RetryHandler, wh ch
   * may retry t  operat on on fa lures.
   */
  def retry(handler: RetryHandler[Un ]): FutureEffect[T] =
    FutureEffect[T](handler.wrap(self))

  @deprecated("use trackOutco ", "2.11.1")
  def countExcept ons(stats: StatsRece ver, getScope: T => Str ng) = {
    val except onCounterFactory = new  mo zedExcept onCounterFactory(stats)
    FutureEffect[T] { t =>
      except onCounterFactory(getScope(t)) { self(t) }
    }
  }

  /**
   * Produces a FutureEffect that tracks t  latency of t  underly ng operat on.
   */
  def trackLatency(stats: StatsRece ver, extractNa : T => Str ng): FutureEffect[T] =
    FutureEffect[T] { t =>
      Stat.t  Future(stats.stat(extractNa (t), "latency_ms")) { self(t) }
    }

  def trackOutco (
    stats: StatsRece ver,
    extractNa : T => Str ng,
    logger: Logger = NullLogger
  ): FutureEffect[T] = trackOutco (stats, extractNa , logger, _ => None)

  /**
   * Produces a FutureEffect that tracks t  outco  ( .e. success vs fa lure) of
   * requests,  nclud ng count ng except ons by classna .
   */
  def trackOutco (
    stats: StatsRece ver,
    extractNa : T => Str ng,
    logger: Logger,
    except onCategor zer: Throwable => Opt on[Str ng]
  ): FutureEffect[T] =
    FutureEffect[T] { t =>
      val na  = extractNa (t)
      val scope = stats.scope(na )

      self(t) respond { r =>
        scope.counter("requests"). ncr()

        r match {
          case Return(_) =>
            scope.counter("success"). ncr()

          case Throw(t) =>
            val category = except onCategor zer(t).getOrElse("fa lures")
            scope.counter(category). ncr()
            scope.scope(category).counter(Throwable lper.san  zeClassna Cha n(t): _*). ncr()
            logger.warn ng(t, s"fa lure  n $na ")
        }
      }
    }

  /**
   * Observe latency and success rate for any FutureEffect
   * @param statsScope a funct on to produce a parent stats scope from t  argu nt
   * to t  FutureEffect
   * @param except onCategor zer a funct on to ass gn d fferent Throwables w h custom stats scopes.
   */
  def observed(
    statsRece ver: StatsRece ver,
    statsScope: T => Str ng,
    logger: Logger = NullLogger,
    except onCategor zer: Throwable => Opt on[Str ng] = _ => None
  ): FutureEffect[T] =
    self
      .trackLatency(statsRece ver, statsScope)
      .trackOutco (statsRece ver, statsScope, logger, except onCategor zer)

  /**
   * Produces a new FutureEffect w re t  g ven funct on  s appl ed to t  result of t 
   * FutureEffect.
   */
  def mapResult(f: Future[Un ] => Future[Un ]): FutureEffect[T] =
    FutureEffect[T] { x =>
      f(self(x))
    }

  /**
   * Produces a new FutureEffect w re t  returned Future must complete w h n t  spec f ed
   * t  out, ot rw se t  Future fa ls w h a com.tw ter.ut l.T  outExcept on.
   *
   * ''Note'': On t  out, t  underly ng future  s NOT  nterrupted.
   */
  def w hT  out(t  r: T  r, t  out: Durat on): FutureEffect[T] =
    mapResult(_.w h n(t  r, t  out))

  /**
   * Produces a new FutureEffect w re t  returned Future must complete w h n t  spec f ed
   * t  out, ot rw se t  Future fa ls w h t  spec f ed Throwable.
   *
   * ''Note'': On t  out, t  underly ng future  s NOT  nterrupted.
   */
  def w hT  out(t  r: T  r, t  out: Durat on, exc: => Throwable): FutureEffect[T] =
    mapResult(_.w h n(t  r, t  out, exc))

  /**
   * Produces a new FutureEffect w re t  returned Future must complete w h n t  spec f ed
   * t  out, ot rw se t  Future fa ls w h a com.tw ter.ut l.T  outExcept on.
   *
   * ''Note'': On t  out, t  underly ng future  s  nterrupted.
   */
  def ra seW h n(t  r: T  r, t  out: Durat on): FutureEffect[T] =
    mapResult(_.ra seW h n(t  out)(t  r))

  /**
   * Produces a new FutureEffect w re t  returned Future must complete w h n t  spec f ed
   * t  out, ot rw se t  Future fa ls w h t  spec f ed Throwable.
   *
   * ''Note'': On t  out, t  underly ng future  s  nterrupted.
   */
  def ra seW h n(t  r: T  r, t  out: Durat on, exc: => Throwable): FutureEffect[T] =
    mapResult(_.ra seW h n(t  r, t  out, exc))
}
