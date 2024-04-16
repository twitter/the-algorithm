package com.tw ter.t etyp e.context

 mport com.tw ter.context.Tw terContext
 mport com.tw ter.f nagle.F lter
 mport com.tw ter.f nagle.Serv ce
 mport com.tw ter.f nagle.S mpleF lter
 mport com.tw ter.f nagle.context.Contexts
 mport com.tw ter. o.Buf
 mport com.tw ter. o.Buf.ByteArray.Owned
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.graphql.common.core.GraphQlCl entAppl cat on
 mport com.tw ter.ut l.Try
 mport java.n o.charset.StandardCharsets.UTF_8
 mport scala.ut l.match ng.Regex

/**
 * Context and f lters to  lp track callers of T etyp e's endpo nts. T  context and  s
 * f lters  re or g nally added to prov de v s b l y  nto callers of T etyp e who are
 * us ng t  b rd rd l brary to access t ets.
 *
 * T  context data  s  ntended to be marshalled by callers to T etyp e, but t n t 
 * context data  s str pped (moved from broadcast to local). T  happens so that t 
 * context data  s not forwarded down t etyp e's backend rpc cha ns, wh ch often result
 *  n trans  ve calls back  nto t etyp e. T  effect vely creates s ngle-hop marshall ng.
 */
object T etyp eContext {
  // Br ng T etyp e perm ted Tw terContext  nto scope
  val Tw terContext: Tw terContext =
    com.tw ter.context.Tw terContext(com.tw ter.t etyp e.Tw terContextPerm )

  case class Ctx(v a: Str ng)
  val Empty = Ctx("")

  object Broadcast {
    pr vate[t ] object Key extends Contexts.broadcast.Key[Ctx]( d = Ctx.getClass.getNa ) {

      overr de def marshal(value: Ctx): Buf =
        Owned(value.v a.getBytes(UTF_8))

      overr de def tryUnmarshal(buf: Buf): Try[Ctx] =
        Try(Ctx(new Str ng(Owned.extract(buf), UTF_8)))
    }

    pr vate[T etyp eContext] def current(): Opt on[Ctx] =
      Contexts.broadcast.get(Key)

    def currentOrElse(default: Ctx): Ctx =
      current().getOrElse(default)

    def letClear[T](f: => T): T =
      Contexts.broadcast.letClear(Key)(f)

    def let[T](ctx: Ctx)(f: => T): T =
       f (Empty == ctx) {
        letClear(f)
      } else {
        Contexts.broadcast.let(Key, ctx)(f)
      }

    // ctx has to be by na  so   can re-evaluate   for every request (for usage  n Serv ceTw ter.scala)
    def f lter(ctx: => Ctx): F lter.TypeAgnost c =
      new F lter.TypeAgnost c {
        overr de def toF lter[Req, Rep]: F lter[Req, Rep, Req, Rep] =
          (request: Req, serv ce: Serv ce[Req, Rep]) => Broadcast.let(ctx)(serv ce(request))
      }
  }

  object Local {
    pr vate[t ] val Key =
      new Contexts.local.Key[Ctx]

    pr vate[T etyp eContext] def let[T](ctx: Opt on[Ctx])(f: => T): T =
      ctx match {
        case So (ctx)  f ctx != Empty => Contexts.local.let(Key, ctx)(f)
        case None => Contexts.local.letClear(Key)(f)
      }

    def current(): Opt on[Ctx] =
      Contexts.local.get(Key)

    def f lter[Req, Rep]: S mpleF lter[Req, Rep] =
      (request: Req, serv ce: Serv ce[Req, Rep]) => {
        val ctx = Broadcast.current()
        Broadcast.letClear(Local.let(ctx)(serv ce(request)))
      }

    pr vate[t ] def cl entApp dToNa (cl entApp d: Long) =
      GraphQlCl entAppl cat on.AllBy d.get(cl entApp d).map(_.na ).getOrElse("nonTOO")

    pr vate[t ] val pathRegexes: Seq[(Regex, Str ng)] = Seq(
      ("t  l ne_conversat on_.*_json".r, "t  l ne_conversat on__slug__json"),
      ("user_t  l ne_.*_json".r, "user_t  l ne__user__json"),
      ("[0-9]{2,}".r, "_ d_")
    )

    // `context.v a` w ll e  r be a str ng l ke: "b rd rd" or "b rd rd:/1.1/statuses/show/123.json,
    // depend ng on w t r b rd rd code was able to determ ne t  path of t  request.
    pr vate[t ] def getV aAndPath(v a: Str ng): (Str ng, Opt on[Str ng]) =
      v a.spl (":", 2) match {
        case Array(v a, path) =>
          val san  zedPath = path
            .replace('/', '_')
            .replace('.', '_')

          // Apply each regex  n turn
          val normal zedPath = pathRegexes.foldLeft(san  zedPath) {
            case (path, (regex, replace nt)) => regex.replaceAll n(path, replace nt)
          }

          (v a, So (normal zedPath))
        case Array(v a) => (v a, None)
      }

    def trackStats[U](scopes: StatsRece ver*): Un  =
      for {
        t etyp eCtx <- T etyp eContext.Local.current()
        (v a, pathOpt) = getV aAndPath(t etyp eCtx.v a)
        tw terCtx <- Tw terContext()
        cl entApp d <- tw terCtx.cl entAppl cat on d
      } y eld {
        val cl entAppNa  = cl entApp dToNa (cl entApp d)
        scopes.foreach { stats =>
          val ctxStats = stats.scope("context")
          val v aStats = ctxStats.scope("v a", v a)
          v aStats.scope("all").counter("requests"). ncr()
          val v aCl entStats = v aStats.scope("by_cl ent", cl entAppNa )
          v aCl entStats.counter("requests"). ncr()
          pathOpt.foreach { path =>
            val v aPathStats = v aStats.scope("by_path", path)
            v aPathStats.counter("requests"). ncr()
          }
        }
      }
  }
}
