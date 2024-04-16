package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.spam.rtf.thr ftscala.F lteredReason
 mport com.tw ter.t etyp e.core.F lteredState
 mport com.tw ter.t etyp e.core.ValueState
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.v s b l y.results.counts.Engage ntCounts

/**
 * Redact T et.counts (StatusCounts) for so  v s b l y results
 */
object ScrubEngage ntHydrator {
  type Type = ValueHydrator[Opt on[StatusCounts], Ctx]

  case class Ctx(f lteredState: Opt on[F lteredState.Suppress])

  def apply(): Type =
    ValueHydrator.map[Opt on[StatusCounts], Ctx] { (curr: Opt on[StatusCounts], ctx: Ctx) =>
      ctx.f lteredState match {
        case So (F lteredState.Suppress(F lteredReason.SafetyResult(result)))  f curr.nonEmpty =>
          ValueState.delta(curr, Engage ntCounts.scrubEngage ntCounts(result.act on, curr))
        case _ =>
          ValueState.unmod f ed(curr)
      }
    }
}
