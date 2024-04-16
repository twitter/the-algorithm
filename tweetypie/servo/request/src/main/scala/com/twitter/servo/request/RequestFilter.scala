package com.tw ter.servo.request

 mport com.tw ter.f nagle.trac ng.Trace d
 mport com.tw ter.servo.ut l.{Funct onArrow, Effect, FutureArrow, FutureEffect, Observable}
 mport com.tw ter.ut l.{Future, Try}

/**
 * Useful m x ns for request types.
 */
tra  HasTrace d {

  /**
   * T  F nagle Trace d of t  request.
   */
  def trace d: Trace d
}

/**
 * A collect on of RequestF lter factory funct ons.
 *
 * type RequestF lter[A] = FutureArrow[A, A]
 */
object RequestF lter {

  /**
   * Produce a RequestF lter from a funct on `A => Future[A]`.
   */
  def apply[A](f: A => Future[A]): RequestF lter[A] = FutureArrow(f)

  /**
   * Produce a RequestF lter from a funct on `A => Try[A]`.
   *
   * T  Try  s evaluated w h n a Future. Thus, Throw results are translated
   * to `Future.except on`s.
   */
  def fromTry[A](f: A => Try[A]): RequestF lter[A] = FutureArrow.fromTry(f)

  /**
   * A no-op RequestF lter;   s mply returns t  request.
   *
   * T  forms a mono d w h `append`.
   */
  def  dent y[A]: RequestF lter[A] = FutureArrow. dent y

  /**
   * Appends two RequestF lters toget r.
   *
   * T  forms a mono d w h ' dent y'.
   */
  def append[A](a: RequestF lter[A], b: RequestF lter[A]): RequestF lter[A] =
    FutureArrow.append(a, b)

  /**
   * Compose an ordered ser es of RequestF lters  nto a s ngle object.
   */
  def all[A](f lters: RequestF lter[A]*): RequestF lter[A] =
    f lters.foldLeft( dent y[A])(append)

  /**
   * Produce a RequestF lter that appl es a s de-effect, return ng t  argu nt
   * request as- s.
   */
  def effect[A](effect: Effect[A]): RequestF lter[A] =
    FutureArrow.fromFunct onArrow(Funct onArrow.effect(effect))

  /**
   * Produce a RequestF lter that appl es a s de-effect, return ng t  argu nt
   * request as- s.
   */
  def effect[A](effect: FutureEffect[A]): RequestF lter[A] = FutureArrow.effect(effect)

  /**
   * Returns a new request f lter w re all Futures returned from `a` have t  r
   * `masked`  thod called
   */
  def masked[A](a: RequestF lter[A]): RequestF lter[A] = a.masked

  /**
   * Produces a RequestF lter that prox es to one of two ot rs, depend ng on a
   * pred cate.
   */
  def choose[A](
    pred cate: A => Boolean,
     fTrue: RequestF lter[A],
     fFalse: RequestF lter[A]
  ): RequestF lter[A] =
    FutureArrow.choose(pred cate,  fTrue,  fFalse)

  /**
   * Guard t  appl cat on of a f lter on a pred cate. T  f lter  s appl ed
   *  f t  pred cate returns true, ot rw se, t  request  s s mply returned.
   */
  def only f[A](pred cate: A => Boolean, f: RequestF lter[A]): RequestF lter[A] =
    FutureArrow.only f(pred cate, f)

  /**
   * Produces a RequestF lter that author zes requests by apply ng an
   * author zat on funct on `A => Future[Un ]`.  f t  author zer funct on
   * results  n a Future except on, requests are fa led. Ot rw se, t y pass.
   */
  def author zed[A <: Observable](author zer: Cl entRequestAuthor zer): RequestF lter[A] =
    RequestF lter[A] { request =>
      author zer(request.requestNa , request.cl ent dStr ng) map { _ =>
        request
      }
    }

  /**
   * Produces a RequestF lter that appl es a Cl entRequestObserver to requests.
   *
   * Used to  ncre nt counters and track stats for requests.
   */
  def observed[A <: Observable](observer: Cl entRequestObserver): RequestF lter[A] =
    RequestF lter[A] { request =>
      val cl ent dScopesOpt = request.cl ent dStr ng map { Seq(_) }
      observer(request.requestNa , cl ent dScopesOpt) map { _ =>
        request
      }
    }
}
