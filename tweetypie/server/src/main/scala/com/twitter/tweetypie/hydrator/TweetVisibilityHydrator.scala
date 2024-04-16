package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.ut l.Commun yUt l

object T etV s b l yHydrator {
  type Type = ValueHydrator[Opt on[F lteredState.Suppress], Ctx]

  case class Ctx(t et: T et, underly ngT etCtx: T etCtx) extends T etCtx.Proxy

  def apply(
    repo: T etV s b l yRepos ory.Type,
    fa lClosed nVF: Gate[Un ],
    stats: StatsRece ver
  ): Type = {
    val outco Scope = stats.scope("outco ")
    val unava lable = outco Scope.counter("unava lable")
    val suppress = outco Scope.counter("suppress")
    val allow = outco Scope.counter("allow")
    val fa lClosed = outco Scope.counter("fa l_closed")
    val commun yFa lClosed = outco Scope.counter("commun y_fa l_closed")
    val fa lOpen = outco Scope.counter("fa l_open")

    ValueHydrator[Opt on[F lteredState.Suppress], Ctx] { (curr, ctx) =>
      val request = T etV s b l yRepos ory.Request(
        t et = ctx.t et,
        v e r d = ctx.opts.forUser d,
        safetyLevel = ctx.opts.safetyLevel,
         s nnerQuotedT et = ctx.opts. s nnerQuotedT et,
         sRet et = ctx. sRet et,
        hydrateConversat onControl = ctx.t etF eldRequested(T et.Conversat onControlF eld),
         sS ceT et = ctx.opts. sS ceT et
      )

      repo(request).l ftToTry.flatMap {
        //  f F lteredState.Unava lable  s returned from repo t n throw  
        case Return(So (fs: F lteredState.Unava lable)) =>
          unava lable. ncr()
          St ch.except on(fs)
        //  f F lteredState.Suppress  s returned from repo t n return  
        case Return(So (fs: F lteredState.Suppress)) =>
          suppress. ncr()
          St ch.value(ValueState.mod f ed(So (fs)))
        //  f None  s returned from repo t n return unmod f ed
        case Return(None) =>
          allow. ncr()
          ValueState.St chUnmod f edNone
        // Propagate thrown except ons  f fa l closed
        case Throw(e)  f fa lClosed nVF() =>
          fa lClosed. ncr()
          St ch.except on(e)
        // Commun y t ets are spec al cased to fa l closed to avo d
        // leak ng t ets expected to be pr vate to a commun y.
        case Throw(e)  f Commun yUt l.hasCommun y(request.t et.commun  es) =>
          commun yFa lClosed. ncr()
          St ch.except on(e)
        case Throw(_) =>
          fa lOpen. ncr()
          St ch.value(ValueState.unmod f ed(curr))
      }
    }
  }
}
