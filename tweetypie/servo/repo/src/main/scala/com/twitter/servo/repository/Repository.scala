package com.tw ter.servo.repos ory

 mport com.tw ter.servo.ut l.RetryHandler
 mport com.tw ter.ut l.{Durat on, Future, T  r}

object Repos ory {

  /**
   * Composes a Repos oryF lter onto a Repos ory, produc ng a new Repos ory.
   */
  def composed[Q, R1, R2](
    repo: Repos ory[Q, R1],
    f lter: Repos oryF lter[Q, R1, R2]
  ): Repos ory[Q, R2] =
    q => f lter(q, repo(q))

  /**
   * Cha ns 2 or more Repos oryF lters toget r  nto a s ngle Repos oryF lter.
   */
  def cha ned[Q, R1, R2, R3](
    f1: Repos oryF lter[Q, R1, R2],
    f2: Repos oryF lter[Q, R2, R3],
    fs: Repos oryF lter[Q, R3, R3]*
  ): Repos oryF lter[Q, R1, R3] = {
    val f rst: Repos oryF lter[Q, R1, R3] = (q, r) => f2(q, f1(q, r))
    fs.toL st match {
      case N l => f rst
      case  ad :: ta l => cha ned(f rst,  ad, ta l: _*)
    }
  }

  /**
   * Wraps a Repos ory w h a funct on that transforms quer es on t  way  n, and
   * results on t  way out.
   */
  def transfor d[Q, Q2, R, R2](
    repo: Repos ory[Q, R],
    qmapper: Q2 => Q = ( dent y[Q] _): (Q => Q),
    rmapper: R => R2 = ( dent y[R] _): (R => R)
  ): Repos ory[Q2, R2] =
    qmapper andT n repo andT n { _ map rmapper }

  /**
   * Wraps a Repos ory w h anot r Repos ory that explodes t  query  nto mult ple
   * quer es, executes those quer es  n parallel, t n comb nes (reduces) results.
   */
  def mapReduced[Q, Q2, R, R2](
    repo: Repos ory[Q, R],
    mapper: Q2 => Seq[Q],
    reducer: Seq[R] => R2
  ): Repos ory[Q2, R2] =
    mapReducedW hQuery(repo, mapper, (rs: Seq[(Q, R)]) => reducer(rs map { case (_, r) => r }))

  /**
   * An extens on of mapReduced that passes query and result to t  reducer.
   */
  def mapReducedW hQuery[Q, Q2, R, R2](
    repo: Repos ory[Q, R],
    mapper: Q2 => Seq[Q],
    reducer: Seq[(Q, R)] => R2
  ): Repos ory[Q2, R2] = {
    val queryRepo: Q => Future[(Q, R)] = q => repo(q) map { (q, _) }
    q2 => Future.collect(mapper(q2) map queryRepo) map reducer
  }

  /**
   * Creates a new Repos ory that d spatc s to r1  f t  g ven query pred cate returns true,
   * and d spatc s to r2 ot rw se.
   */
  def selected[Q, R](
    select: Q => Boolean,
    onTrueRepo: Repos ory[Q, R],
    onFalseRepo: Repos ory[Q, R]
  ): Repos ory[Q, R] =
    d spatc d(select andT n {
      case true => onTrueRepo
      case false => onFalseRepo
    })

  /**
   * Creates a new Repos ory that uses a funct on that selects an underly ng repos ory
   * based upon t  query.
   */
  def d spatc d[Q, R](f: Q => Repos ory[Q, R]): Repos ory[Q, R] =
    q => f(q)(q)

  /**
   * Wraps a Repos ory w h t  g ven RetryHandler, wh ch may automat cally retry
   * fa led requests.
   */
  def retry ng[Q, R](handler: RetryHandler[R], repo: Repos ory[Q, R]): Repos ory[Q, R] =
    handler.wrap(repo)

  /**
   * Produces a new Repos ory w re t  returned Future must complete w h n t  spec f ed
   * t  out, ot rw se t  Future fa ls w h a com.tw ter.ut l.T  outExcept on.
   *
   * ''Note'': On t  out, t  underly ng future  s not  nterrupted.
   */
  def w hT  out[Q, R](
    t  r: T  r,
    t  out: Durat on,
    repo: Repos ory[Q, R]
  ): Repos ory[Q, R] =
    repo andT n { _.w h n(t  r, t  out) }

  /**
   * Produces a new Repos ory w re t  returned Future must complete w h n t  spec f ed
   * t  out, ot rw se t  Future fa ls w h t  spec f ed Throwable.
   *
   * ''Note'': On t  out, t  underly ng future  s not  nterrupted.
   */
  def w hT  out[Q, R](
    t  r: T  r,
    t  out: Durat on,
    exc: => Throwable,
    repo: Repos ory[Q, R]
  ): Repos ory[Q, R] =
    repo andT n { _.w h n(t  r, t  out, exc) }

  /**
   * Wraps a Repos ory w h stats record ng funct onal y.
   */
  def observed[Q, R](
    repo: Repos ory[Q, R],
    observer: Repos oryObserver
  ): Repos ory[Q, R] =
    query => {
      observer.t  () {
        repo(query).respond(observer.observeTry)
      }
    }
}
