package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala._

/**
 * Hydrates t  "d rectedAtUser" f eld on t  t et.  T  hydrators uses one of two paths depend ng
 *  f D rectedAtUser tadata  s present:
 *
 * 1.  f D rectedAtUser tadata ex sts,   use  tadata.user d.
 * 2.  f D rectedAtUser tadata does not ex st,   use t  User screenNa  from t   nt on start ng
 *    at  ndex 0  f t  t et also has a reply.  Creat on of a "reply to user" for
 *    lead ng @ nt ons  s controlled by PostT etRequest.enableT etToNarrowcast ng
 */
object D rectedAtHydrator {
  type Type = ValueHydrator[Opt on[D rectedAtUser], Ctx]

  case class Ctx(
     nt ons: Seq[ nt onEnt y],
     tadata: Opt on[D rectedAtUser tadata],
    underly ngT etCtx: T etCtx)
      extends T etCtx.Proxy {
    val d rectedAtScreenNa : Opt on[Str ng] =
       nt ons. adOpt on.f lter(_.from ndex == 0).map(_.screenNa )
  }

  val hydratedF eld: F eldByPath =
    f eldByPath(T et.CoreDataF eld, T etCoreData.D rectedAtUserF eld)

  def once(h: Type): Type =
    T etHydrat on.completeOnlyOnce(
      hydrat onType = Hydrat onType.D rectedAt,
      hydrator = h
    )

  pr vate val part al = ValueState.part al(None, hydratedF eld)

  def apply(repo: User dent yRepos ory.Type, stats: StatsRece ver = NullStatsRece ver): Type = {
    val w h tadata = stats.counter("w h_ tadata")
    val noScreenNa  = stats.counter("no_screen_na ")
    val w hout tadata = stats.counter("w hout_ tadata")

    ValueHydrator[Opt on[D rectedAtUser], Ctx] { (_, ctx) =>
      ctx. tadata match {
        case So (D rectedAtUser tadata(So (u d))) =>
          // 1a. new approach of rely ng exclus vely on d rected-at  tadata  f   ex sts and has a user  d
          w h tadata. ncr()

          repo(UserKey.by d(u d)).l ftToTry.map {
            case Return(u) =>
              ValueState.mod f ed(So (D rectedAtUser(u. d, u.screenNa )))
            case Throw(NotFound) =>
              //  f user  s not found, fallback to d rectedAtScreenNa 
              ctx.d rectedAtScreenNa 
                .map { screenNa  => ValueState.mod f ed(So (D rectedAtUser(u d, screenNa ))) }
                .getOrElse {
                  // T  should never happen, but let's make sure w h a counter
                  noScreenNa . ncr()
                  ValueState.Unmod f edNone
                }
            case Throw(_) => part al
          }

        case So (D rectedAtUser tadata(None)) =>
          w h tadata. ncr()
          // 1b. new approach of rely ng exclus vely on d rected-at  tadata  f   ex sts and has no user d
          ValueState.St chUnmod f edNone

        case None =>
          // 2. w n D rectedAtUser tadata not present, look for f rst lead ng  nt on w n has reply
          w hout tadata. ncr()

          val userKey = ctx.d rectedAtScreenNa 
            .f lter(_ => ctx. sReply)
            .map(UserKey.byScreenNa )

          val results = userKey.map(repo.apply).getOrElse(St ch.NotFound)

          results.l ftToTry.map {
            case Return(u) => ValueState.mod f ed(So (D rectedAtUser(u. d, u.screenNa )))
            case Throw(NotFound) => ValueState.Unmod f edNone
            case Throw(_) => part al
          }
      }
    }.only f((curr, _) => curr. sEmpty)
  }
}
