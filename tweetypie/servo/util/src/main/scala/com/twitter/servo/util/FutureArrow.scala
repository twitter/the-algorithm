package com.tw ter.servo.ut l

 mport com.tw ter.f nagle.serv ce.RetryPol cy
 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.f nagle.Fa ledFastExcept on
 mport com.tw ter.f nagle.F lter
 mport com.tw ter.f nagle.Serv ce
 mport com.tw ter.ut l._
 mport scala.ut l.control.NonFatal

/**
 * A collect on of FutureArrow factory funct ons.
 */
object FutureArrow {

  /**
   * Produce a FutureArrow from a funct on `A => Future[B]`.
   */
  def apply[A, B](f: A => Future[B]): FutureArrow[A, B] =
    new FutureArrow[A, B] {
      overr de def apply(a: A): Future[B] =
        try f(a)
        catch {
          case NonFatal(e) => Future.except on(e)
        }
    }

  /**
   * Produce a FutureArrow that supports recurs ve calls.  Recurs ng from a `Future`
   * cont nuat on  s stack-safe, but d rect recurs on w ll use t  stack, l ke a
   * normal  thod  nvocat on.
   */
  def rec[A, B](f: FutureArrow[A, B] => A => Future[B]): FutureArrow[A, B] =
    new FutureArrow[A, B] { self =>
      pr vate val g: A => Future[B] = f(t )
      overr de def apply(a: A): Future[B] =
        try g(a)
        catch {
          case NonFatal(e) => Future.except on(e)
        }
    }

  /**
   * Produce a FutureArrow from an Funct onArrow.
   */
  def fromFunct onArrow[A, B](f: Funct onArrow[A, B]): FutureArrow[A, B] =
    FutureArrow[A, B](a => Future(f(a)))

  /**
   * Produce a FutureArrow from a funct on.
   */
  def fromFunct on[A, B](f: A => B): FutureArrow[A, B] = fromFunct onArrow(Funct onArrow(f))

  /**
   * Produce a FutureArrow from a funct on `A => Try[B]`.
   *
   * T  Try  s evaluated w h n a Future. Thus, Throw results are translated
   * to `Future.except on`s.
   */
  def fromTry[A, B](f: A => Try[B]): FutureArrow[A, B] =
    FutureArrow[A, B](a => Future.const(f(a)))

  /**
   * A FutureArrow that s mply returns a Future of  s argu nt.
   */
  def  dent y[A]: FutureArrow[A, A] =
    FutureArrow[A, A](a => Future.value(a))

  /**
   * A FutureArrow w h a constant result, regardless of  nput.
   */
  def const[A, B](value: Future[B]): FutureArrow[A, B] =
    FutureArrow[A, B](_ => value)

  /**
   * Appends two FutureArrows toget r.
   *
   * T  forms a category w h ' dent y'.
   */
  def append[A, B, C](a: FutureArrow[A, B], b: FutureArrow[B, C]) = a.andT n(b)

  /**
   * Produce a FutureArrow that appl es an FutureEffect, return ng t  argu nt
   * value as- s on success.  f t  effect returns an Future except on, t n t 
   * result of t  f lter w ll also be that except on.
   */
  def effect[A](effect: FutureEffect[A]): FutureArrow[A, A] =
    apply(a => effect(a).map(_ => a))

  /**
   * Produces a FutureArrow that prox es to one of two ot rs, depend ng on a
   * pred cate.
   */
  def choose[A, B](pred cate: A => Boolean,  fTrue: FutureArrow[A, B],  fFalse: FutureArrow[A, B]) =
    FutureArrow[A, B](a =>  f (pred cate(a))  fTrue(a) else  fFalse(a))

  /**
   * Produces a FutureArrow whose appl cat on  s guarded by a pred cate. `f`  s
   * appl ed  f t  pred cate returns true, ot rw se t  argu nt  s s mply
   * returned.
   */
  def only f[A](pred cate: A => Boolean, f: FutureArrow[A, A]) =
    choose(pred cate, f,  dent y[A])

  /**
   * Produces a FutureArrow that forwards to mult ple FutureArrows and collects
   * t  results  nto a `Seq[B]`. Results are gat red v a Future.collect, so
   * fa lure semant cs are  n r ed from that  thod.
   */
  def collect[A, B](arrows: Seq[FutureArrow[A, B]]): FutureArrow[A, Seq[B]] =
    apply(a => Future.collect(arrows.map(arrow => arrow(a))))

  pr vate val RetryOnNonFa ledFast: Part alFunct on[Try[Any], Boolean] = {
    case Throw(_: Fa ledFastExcept on) => false
    case Throw(_: Except on) => true
  }
}

/**
 * A funct on encapsulat ng an asynchronous computat on.
 *
 * Background on t  Arrow abstract on:
 * http://en.w k ped a.org/w k /Arrow_(computer_sc ence)
 */
tra  FutureArrow[-A, +B] extends (A => Future[B]) { self =>

  /**
   * Composes two FutureArrows. Produces a new FutureArrow that performs both  n
   * ser es, depend ng on t  success of t  f rst.
   */
  def andT n[C](next: FutureArrow[B, C]): FutureArrow[A, C] =
    FutureArrow[A, C](a => self(a).flatMap(next.apply))

  /**
   * Comb nes t  FutureArrow w h anot r, produc ng one that translates a
   * tuple of  s const uents' argu nts  nto a tuple of t  r results.
   */
  def z pjo n[C, D](ot r: FutureArrow[C, D]): FutureArrow[(A, C), (B, D)] =
    FutureArrow[(A, C), (B, D)] {
      case (a, c) => self(a) jo n ot r(c)
    }

  /**
   * Converts a FutureArrow on a scalar  nput and output value  nto a FutureArrow on a
   * Sequence of  nput values produc ng a pa rw se sequence of output values.  T  ele nts
   * of t   nput sequence are processed  n parallel, so execut on order  s not guaranteed.
   * Results are gat red v a Future.collect, so fa lure semant cs are  n r ed from that  thod.
   */
  def l ftSeq: FutureArrow[Seq[A], Seq[B]] =
    FutureArrow[Seq[A], Seq[B]] { seqA =>
      Future.collect(seqA.map(t ))
    }

  /**
   * Converts t  FutureArrow to a FutureEffect, w re t  result value  s  gnored.
   */
  def asFutureEffect[A2 <: A]: FutureEffect[A2] =
    FutureEffect(t .un )

  /**
   * Comb nes t  FutureArrow w h anot r, produc ng one that appl es both
   *  n parallel, produc ng a tuple of t  r results.
   */
  def  nParallel[A2 <: A, C](ot r: FutureArrow[A2, C]): FutureArrow[A2, (B, C)] = {
    val pa red = self.z pjo n(ot r)
    FutureArrow[A2, (B, C)](a => pa red((a, a)))
  }

  /**
   * Wrap a FutureArrow w h an Except onCounter, thus prov d ng
   * observab l y  nto t  arrow's success and fa lure.
   */
  def countExcept ons(
    except onCounter: Except onCounter
  ): FutureArrow[A, B] =
    FutureArrow[A, B](request => except onCounter(self(request)))

  /**
   * Returns a cha ned FutureArrow  n wh ch t  g ven funct on w ll be called for any
   *  nput that succeeds.
   */
  def onSuccess[A2 <: A](f: (A2, B) => Un ): FutureArrow[A2, B] =
    FutureArrow[A2, B](a => self(a).onSuccess(b => f(a, b)))

  /**
   * Returns a cha ned FutureArrow  n wh ch t  g ven funct on w ll be called for any
   *  nput that fa ls.
   */
  def onFa lure[A2 <: A](f: (A2, Throwable) => Un ): FutureArrow[A2, B] =
    FutureArrow[A2, B](a => self(a).onFa lure(t => f(a, t)))

  /**
   * Translate except on returned by a FutureArrow accord ng to a
   * Part alFunct on.
   */
  def translateExcept ons(
    translateExcept on: Part alFunct on[Throwable, Throwable]
  ): FutureArrow[A, B] =
    FutureArrow[A, B] { request =>
      self(request).rescue {
        case t  f translateExcept on. sDef nedAt(t) => Future.except on(translateExcept on(t))
        case t => Future.except on(t)
      }
    }

  /**
   * Apply a FutureArrow, l ft ng any non-Future except ons thrown  nto
   * `Future.except on`s.
   */
  def l ftExcept ons: FutureArrow[A, B] =
    FutureArrow[A, B] { request =>
      // Flatten ng t  Future[Future[Response]]  s equ valent, but more conc se
      // than wrapp ng t  arrow(request) call  n a try/catch block that transforms
      // t  except on to a Future.except on, or at least was more conc se before
      //   added a f -l ne com nt.
      Future(self(request)).flatten
    }

  /**
   * Wrap a FutureArrow  n except on-track ng and -translat on. G ven a
   * f lter and a handler, except onal results w ll be observed and translated
   * accord ng to t  funct on passed  n t  funct on's second argu nt l st.
   */
  def cleanly(
    except onCounter: Except onCounter
  )(
    translateExcept on: Part alFunct on[Throwable, Throwable] = { case t => t }
  ): FutureArrow[A, B] = {
    l ftExcept ons
      .translateExcept ons(translateExcept on)
      .countExcept ons(except onCounter)
  }

  /**
   * Produces a FutureArrow that tracks  s own appl cat on latency.
   */
  @deprecated("use trackLatency(StatsRece ver, (A2 => Str ng)", "2.11.1")
  def trackLatency[A2 <: A](
    extractNa : (A2 => Str ng),
    statsRece ver: StatsRece ver
  ): FutureArrow[A2, B] =
    trackLatency(statsRece ver, extractNa )

  /**
   * Produces a FutureArrow that tracks  s own appl cat on latency.
   */
  def trackLatency[A2 <: A](
    statsRece ver: StatsRece ver,
    extractNa : (A2 => Str ng)
  ): FutureArrow[A2, B] =
    FutureArrow[A2, B] { request =>
      Stat.t  Future(statsRece ver.stat(extractNa (request), "latency_ms")) {
        self(request)
      }
    }

  /**
   * Produces a FutureArrow that tracks t  outco  ( .e. success vs fa lure) of
   * requests.
   */
  @deprecated("use trackOutco (StatsRece ver, (A2 => Str ng)", "2.11.1")
  def trackOutco [A2 <: A](
    extractNa : (A2 => Str ng),
    statsRece ver: StatsRece ver
  ): FutureArrow[A2, B] =
    trackOutco (statsRece ver, extractNa )

  def trackOutco [A2 <: A](
    statsRece ver: StatsRece ver,
    extractNa : (A2 => Str ng)
  ): FutureArrow[A2, B] =
    trackOutco (statsRece ver, extractNa , _ => None)

  /**
   * Produces a FutureArrow that tracks t  outco  ( .e. success vs fa lure) of
   * requests.
   */
  def trackOutco [A2 <: A](
    statsRece ver: StatsRece ver,
    extractNa : (A2 => Str ng),
    except onCategor zer: Throwable => Opt on[Str ng]
  ): FutureArrow[A2, B] =
    FutureArrow[A2, B] { request =>
      val scope = statsRece ver.scope(extractNa (request))

      self(request).respond { r =>
        statsRece ver.counter("requests"). ncr()
        scope.counter("requests"). ncr()

        r match {
          case Return(_) =>
            statsRece ver.counter("success"). ncr()
            scope.counter("success"). ncr()

          case Throw(t) =>
            val category = except onCategor zer(t).getOrElse("fa lures")
            statsRece ver.counter(category). ncr()
            scope.counter(category). ncr()
            scope.scope(category).counter(Throwable lper.san  zeClassna Cha n(t): _*). ncr()
        }
      }
    }

  /**
   * Observe latency and success rate for any FutureArrow[A, B] w re A  s Observable
   */
  def observed[A2 <: A w h Observable](
    statsRece ver: StatsRece ver
  ): FutureArrow[A2, B] =
    observed(statsRece ver, except onCategor zer = _ => None)

  /**
   * Observe latency and success rate for any FutureArrow[A, B] w re A  s Observable
   */
  def observed[A2 <: A w h Observable](
    statsRece ver: StatsRece ver,
    except onCategor zer: Throwable => Opt on[Str ng]
  ): FutureArrow[A2, B] =
    self.observed(
      statsRece ver.scope("cl ent_request"),
      (a: A2) => a.requestNa ,
      except onCategor zer
    )

  /**
   * Observe latency and success rate for any FutureArrow
   */
  def observed[A2 <: A](
    statsRece ver: StatsRece ver,
    statsScope: A2 => Str ng,
    except onCategor zer: Throwable => Opt on[Str ng] = _ => None
  ): FutureArrow[A2, B] =
    self
      .trackLatency(statsRece ver, statsScope)
      .trackOutco (statsRece ver, statsScope, except onCategor zer)

  /**
   * Trace t  future arrow us ng local spans as docu nted  re:
   * https://docb rd.tw ter.b z/f nagle/Trac ng.html
   */
  def traced[A2 <: A](
    traceScope: A2 => Str ng
  ): FutureArrow[A2, B] = {
    FutureArrow[A2, B] { a =>
      Trace.traceLocalFuture(traceScope(a))(self(a))
    }
  }

  /**
   * Produces a new FutureArrow w re t  g ven funct on  s appl ed to t   nput, and t  result
   * passed to t  FutureArrow.
   */
  def contramap[C](f: C => A): FutureArrow[C, B] =
    FutureArrow[C, B](f.andT n(self))

  /**
   * Produces a new FutureArrow w re t  g ven funct on  s appl ed to t  result of t 
   * FutureArrow.
   */
  def map[C](f: B => C): FutureArrow[A, C] =
    mapResult(_.map(f))

  /**
   * Produces a new FutureArrow w re t  g ven funct on  s appl ed to t  result ng Future of
   * t  FutureArrow.
   */
  def mapResult[C](f: Future[B] => Future[C]): FutureArrow[A, C] =
    FutureArrow[A, C](a => f(self(a)))

  /**
   * Produces a new FutureArrow wh ch translates except ons  nto futures
   */
  def rescue[B2 >: B](
    rescueExcept on: Part alFunct on[Throwable, Future[B2]]
  ): FutureArrow[A, B2] = {
    FutureArrow[A, B2] { a =>
      self(a).rescue(rescueExcept on)
    }
  }

  /**
   * Produces a new FutureArrow w re t  result value  s  gnored, and Un   s returned.
   */
  def un : FutureArrow[A, Un ] =
    mapResult(_.un )

  /**
   * Returns a copy of t  FutureArrow w re t  returned Future has  s `.masked`
   *  thod called.
   */
  def masked: FutureArrow[A, B] =
    mapResult(_.masked)

  /**
   * Wraps t  FutureArrow by pass ng t  underly ng operat on to t  g ven retry handler
   * for poss ble retr es.
   */
  def retry(handler: RetryHandler[B]): FutureArrow[A, B] =
    FutureArrow[A, B](a => handler(self(a)))

  def retry[A2 <: A](
    pol cy: RetryPol cy[Try[B]],
    t  r: T  r,
    statsRece ver: StatsRece ver,
    extractNa : (A2 => Str ng)
  ): FutureArrow[A2, B] =
    FutureArrow[A2, B] { a =>
      val scoped = statsRece ver.scope(extractNa (a))
      RetryHandler(pol cy, t  r, scoped)(self(a))
    }

  /**
   * Produces a new FutureArrow w re t  returned Future[B] must complete w h n t  spec f ed
   * t  out, ot rw se t  Future fa ls w h a com.tw ter.ut l.T  outExcept on.
   *
   * T  [[t  out]]  s passed by na  to take advantage of deadl nes passed  n t  request context.
   *
   * ''Note'': On t  out, t  underly ng future  s NOT  nterrupted.
   */
  def w hT  out(t  r: T  r, t  out: => Durat on): FutureArrow[A, B] =
    mapResult(_.w h n(t  r, t  out))

  /**
   * Produces a new FutureArrow w re t  returned Future must complete w h n t  spec f ed
   * t  out, ot rw se t  Future fa ls w h t  spec f ed Throwable.
   *
   * T  [[t  out]]  s passed by na  to take advantage of deadl nes passed  n t  request context.
   *
   * ''Note'': On t  out, t  underly ng future  s NOT  nterrupted.
   */
  def w hT  out(t  r: T  r, t  out: => Durat on, exc: => Throwable): FutureArrow[A, B] =
    mapResult(_.w h n(t  r, t  out, exc))

  /**
   * Produces a new FutureArrow w re t  returned Future[B] must complete w h n t  spec f ed
   * t  out, ot rw se t  Future fa ls w h a com.tw ter.ut l.T  outExcept on.
   *
   * T  [[t  out]]  s passed by na  to take advantage of deadl nes passed  n t  request context.
   *
   * ''Note'': On t  out, t  underly ng future  s  nterrupted.
   */
  def ra seW h n(t  r: T  r, t  out: => Durat on): FutureArrow[A, B] =
    mapResult(_.ra seW h n(t  out)(t  r))

  /**
   * Produces a new FutureArrow w re t  returned Future must complete w h n t  spec f ed
   * t  out, ot rw se t  Future fa ls w h t  spec f ed Throwable.
   *
   * [[t  out]]  s passed by na  to take advantage of deadl nes passed  n t  request context.
   *
   * ''Note'': On t  out, t  underly ng future  s  nterrupted.
   */
  def ra seW h n(t  r: T  r, t  out: => Durat on, exc: => Throwable): FutureArrow[A, B] =
    mapResult(_.ra seW h n(t  r, t  out, exc))

  /**
   * Produces a f nagle.Serv ce  nstance that  nvokes t  arrow.
   */
  def asServ ce: Serv ce[A, B] = Serv ce.mk(t )

  /**
   * Produces a new FutureArrow w h t  g ven f nagle.F lter appl ed to t   nstance.
   */
  def w hF lter[A2, B2](f lter: F lter[A2, B2, A, B]): FutureArrow[A2, B2] =
    FutureArrow[A2, B2](f lter.andT n(asServ ce))

  /**
   * Produces a new FutureArrow w h t  g ven t  out wh ch retr es on Except ons or t  outs and
   * records stats about t  log cal request.  T   s only appropr ate for  dempotent operat ons.
   */
  def observedW hT  outAndRetry[A2 <: A](
    statsRece ver: StatsRece ver,
    extractNa : (A2 => Str ng),
    t  r: T  r,
    t  out: Durat on,
    numTr es:  nt,
    shouldRetry: Part alFunct on[Try[B], Boolean] = FutureArrow.RetryOnNonFa ledFast
  ): FutureArrow[A2, B] = {
    val retryPol cy = RetryPol cy.tr es(numTr es, shouldRetry)
    w hT  out(t  r, t  out)
      .retry(retryPol cy, t  r, statsRece ver, extractNa )
      .trackLatency(statsRece ver, extractNa )
      .trackOutco (statsRece ver, extractNa )
  }

  /**
   * Produces a new FutureArrow w h t  g ven t  out and records stats about t  log cal request.
   * T  does not retry and  s appropr ate for non- dempotent operat ons.
   */
  def observedW hT  out[A2 <: A](
    statsRece ver: StatsRece ver,
    extractNa : (A2 => Str ng),
    t  r: T  r,
    t  out: Durat on
  ): FutureArrow[A2, B] =
    w hT  out(t  r, t  out)
      .trackLatency(statsRece ver, extractNa )
      .trackOutco (statsRece ver, extractNa )
}
