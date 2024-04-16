package com.tw ter.servo.ut l

 mport com.tw ter.f nagle.ut l.DefaultT  r
 mport com.tw ter.f nagle.{Addr, Na , Na r}
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.ut l._
 mport scala.collect on.JavaConverters._

/**
 * A s mple ut l y class to wa  for serverset na s to be resolved at startup.
 *
 * See [[com.tw ter.f nagle.cl ent.Cl entReg stry.expAllReg steredCl entsResolved()]] for an
 * alternat ve way to wa  for ServerSet resolut on.
 */
object Wa ForServerSets {
  val log = Logger.get("Wa ForServerSets")

  /**
   * Conven ent wrapper for s ngle na   n Java. Prov des t  default t  r from F nagle.
   */
  def ready(na : Na , t  out: Durat on): Future[Un ] =
    ready(Seq(na ), t  out, DefaultT  r)

  /**
   * Java Compat b l y wrapper. Uses java.ut l.L st  nstead of Seq.
   */
  def ready(na s: java.ut l.L st[Na ], t  out: Durat on, t  r: T  r): Future[Un ] =
    ready(na s.asScala, t  out, t  r)

  /**
   * Returns a Future that  s sat sf ed w n no more na s resolve to Addr.Pend ng,
   * or t  spec f ed t  out exp res.
   *
   * T   gnores address resolut on fa lures, so just because t  Future  s sat sf ed
   * doesn't necessar ly  mply that all na s are resolved to so th ng useful.
   */
  def ready(na s: Seq[Na ], t  out: Durat on, t  r: T  r): Future[Un ] = {
    val vars: Var[Seq[(Na , Addr)]] = Var.collect(na s.map {
      case n @ Na .Path(v) => Na r.resolve(v).map((n, _))
      case n @ Na .Bound(v) => v.map((n, _))
    })

    val pend ngs = vars.changes.map { na s =>
      na s.f lter { case (_, addr) => addr == Addr.Pend ng }
    }

    pend ngs
      .f lter(_. sEmpty)
      .toFuture()
      .un 
      .w h n(
        t  r,
        t  out,
        new T  outExcept on(
          "Fa led to resolve: " +
            vars.map(_.map { case (na , _) => na  }).sample()
        )
      )
  }
}
