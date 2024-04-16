package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._

/**
 * Loads t  t et referenced by `T et.quotedT et`.
 */
object QuotedT etHydrator {
  type Type = ValueHydrator[Opt on[QuotedT etResult], Ctx]

  case class Ctx(
    quotedT etF lteredState: Opt on[F lteredState.Unava lable],
    underly ngT etCtx: T etCtx)
      extends T etCtx.Proxy

  def apply(repo: T etResultRepos ory.Type): Type = {
    ValueHydrator[Opt on[QuotedT etResult], Ctx] { (_, ctx) =>
      (ctx.quotedT etF lteredState, ctx.quotedT et) match {

        case (_, None) =>
          //  f t re  s no quoted t et ref, leave t  value as None,
          //  nd cat ng undef ned
          ValueState.St chUnmod f edNone

        case (So (fs), _) =>
          St ch.value(ValueState.mod f ed(So (QuotedT etResult.F ltered(fs))))

        case (None, So (qtRef)) =>
          val qtQueryOpt ons =
            ctx.opts.copy(
              //   don't want to recurs vely load quoted t ets
               nclude = ctx.opts. nclude.copy(quotedT et = false),
              // be sure to get a clean vers on of t  t et
              scrubUnrequestedF elds = true,
              // T etV s b l yL brary f lters quoted t ets sl ghtly d fferently from ot r t ets.
              // Spec f cally, most  nterst  al verd cts are converted to Drops.
               s nnerQuotedT et = true
            )

          repo(qtRef.t et d, qtQueryOpt ons).transform { t =>
            St ch.const {
              QuotedT etResult.fromTry(t).map(r => ValueState.mod f ed(So (r)))
            }
          }
      }
    }.only f((curr, ctx) => curr. sEmpty && ctx.opts. nclude.quotedT et)
  }
}
