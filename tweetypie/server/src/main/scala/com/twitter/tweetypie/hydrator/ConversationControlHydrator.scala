package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core.ValueState
 mport com.tw ter.t etyp e.repos ory.Conversat onControlRepos ory
 mport com.tw ter.t etyp e.serverut l.Except onCounter
 mport com.tw ter.t etyp e.thr ftscala.Conversat onControl

pr vate object ReplyT etConversat onControlHydrator {
  type Type = Conversat onControlHydrator.Type
  type Ctx = Conversat onControlHydrator.Ctx

  // T  conversat on control thr ft f eld was added Feb 17th, 2020.
  // No conversat on before t  w ll have a conversat on control f eld to hydrate.
  //   expl c ly short c rcu  to save res ces from query ng for t ets  
  // know do not have conversat on control f elds set.
  val F rstVal dDate: T   = T  .fromM ll seconds(1554076800000L) // 2020-02-17

  def apply(
    repo: Conversat onControlRepos ory.Type,
    stats: StatsRece ver
  ): Type = {
    val except onCounter = Except onCounter(stats)

    ValueHydrator[Opt on[Conversat onControl], Ctx] { (curr, ctx) =>
      repo(ctx.conversat on d.get, ctx.opts.cac Control).l ftToTry.map {
        case Return(conversat onControl) =>
          ValueState.delta(curr, conversat onControl)
        case Throw(except on) => {
          //  n t  case w re   get an except on,   want to count t 
          // except on but fa l open.
          except onCounter(except on)

          // Reply T et T et.Conversat onControlF eld hydrat on should fa l open.
          //  deally   would return ValueState.part al  re to not fy T etyp e t  caller
          // that requested t  T et.Conversat onControlF eld f eld was not hydrated.
          //   cannot do so because GetT etF elds w ll return T etF eldsResultFa led
          // for part al results wh ch would fa l closed.
          ValueState.unmod f ed(curr)
        }
      }
    }.only f { (_, ctx) =>
      // T  hydrator  s spec f cally for repl es so only run w n T et  s a reply
      ctx. nReplyToT et d. sDef ned &&
      // See com nt for F rstVal dDate
      ctx.createdAt > F rstVal dDate &&
      //   need conversat on  d to get Conversat onControl
      ctx.conversat on d. sDef ned &&
      // Only run  f t  Conversat onControl was requested
      ctx.t etF eldRequested(T et.Conversat onControlF eld)
    }
  }
}

/**
 * Conversat onControlHydrator  s used to hydrate t  conversat onControl f eld.
 * For root T ets, t  hydrator just passes through t  ex st ng conversat onControl.
 * For reply T ets,   loads t  conversat onControl from t  root T et of t  conversat on.
 * Only root T ets  n a conversat on ( .e. t  T et po nted to by conversat on d) have
 * a pers sted conversat onControl, so   have to hydrate that f eld for all repl es  n order
 * to know  f a T et  n a conversat on can be repl ed to.
 */
object Conversat onControlHydrator {
  type Type = ValueHydrator[Opt on[Conversat onControl], Ctx]

  case class Ctx(conversat on d: Opt on[Conversat on d], underly ngT etCtx: T etCtx)
      extends T etCtx.Proxy

  pr vate def scrub nv eV a nt on(
    ccOpt: Opt on[Conversat onControl]
  ): Opt on[Conversat onControl] = {
    ccOpt collect {
      case Conversat onControl.By nv at on(by nv at on) =>
        Conversat onControl.By nv at on(by nv at on.copy( nv eV a nt on = None))
      case Conversat onControl.Commun y(commun y) =>
        Conversat onControl.Commun y(commun y.copy( nv eV a nt on = None))
      case Conversat onControl.Follo rs(follo rs) =>
        Conversat onControl.Follo rs(follo rs.copy( nv eV a nt on = None))
    }
  }

  def apply(
    repo: Conversat onControlRepos ory.Type,
    d sable nv eV a nt on: Gate[Un ],
    stats: StatsRece ver
  ): Type = {
    val replyT etConversat onControlHydrator = ReplyT etConversat onControlHydrator(
      repo,
      stats
    )

    ValueHydrator[Opt on[Conversat onControl], Ctx] { (curr, ctx) =>
      val ccUpdated =  f (d sable nv eV a nt on()) {
        scrub nv eV a nt on(curr)
      } else {
        curr
      }

       f (ctx. nReplyToT et d. sEmpty) {
        // For non-reply t ets, pass through t  ex st ng conversat on control
        St ch.value(ValueState.delta(curr, ccUpdated))
      } else {
        replyT etConversat onControlHydrator(ccUpdated, ctx)
      }
    }
  }
}
