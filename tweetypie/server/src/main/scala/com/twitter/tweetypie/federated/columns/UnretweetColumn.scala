package com.tw ter.t etyp e
package federated.columns

 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.catalog.Op tadata
 mport com.tw ter.strato.conf g.Contact nfo
 mport com.tw ter.strato.conf g.Pol cy
 mport com.tw ter.strato.data.Conv
 mport com.tw ter.strato.data.Descr pt on.Pla nText
 mport com.tw ter.strato.data.L fecycle.Product on
 mport com.tw ter.strato.fed.StratoFed
 mport com.tw ter.strato.opcontext.OpContext
 mport com.tw ter.strato.thr ft.ScroogeConv
 mport com.tw ter.t etyp e.federated.context.GetRequestContext
 mport com.tw ter.t etyp e.federated.context.RequestContext
 mport com.tw ter.t etyp e.thr ftscala.{graphql => gql}
 mport com.tw ter.t etyp e.{thr ftscala => thr ft}

class Unret etColumn(
  unret et: thr ft.Unret etRequest => Future[thr ft.Unret etResult],
  getRequestContext: GetRequestContext,
) extends StratoFed.Column("t etyp e/unret et.T et")
    w h StratoFed.Execute.St chW hContext
    w h StratoFed.HandleDarkRequests {

  overr de val pol cy: Pol cy = AccessPol cy.T etMutat onCommonAccessPol c es

  //  's acceptable to retry or reapply an unret et operat on,
  // as mult ple calls result  n t  sa  end state.
  overr de val  s dempotent: Boolean = true

  overr de type Arg = gql.Unret etRequest
  overr de type Result = gql.Unret etResponseW hSubqueryPrefetch ems

  overr de val argConv: Conv[Arg] = ScroogeConv.fromStruct
  overr de val resultConv: Conv[Result] = ScroogeConv.fromStruct

  overr de val contact nfo: Contact nfo = T etyp eContact nfo
  overr de val  tadata: Op tadata =
    Op tadata(
      So (Product on),
      So (Pla nText("Removes any ret ets by t  call ng user of t  g ven s ce t et.")))

  overr de def execute(gqlRequest: Arg, opContext: OpContext): St ch[Result] = {
    val ctx: RequestContext = getRequestContext(opContext)
    val req = thr ft.Unret etRequest(
      ctx.tw terUser d,
      gqlRequest.s ceT et d,
    )

    val st chUnret et = handleDarkRequest(opContext)(
      l ght = St ch.callFuture(unret et(req)),
      // For dark requests,   don't want to send traff c to t etyp e.
      // S nce t  response  s t  sa  regardless of t  request,   take a no-op
      // act on  nstead.
      dark = St ch.value(thr ft.Unret etResult(state = thr ft.T etDeleteState.Ok))
    )

    st chUnret et.map { _ =>
      gql.Unret etResponseW hSubqueryPrefetch ems(
        data = So (gql.Unret etResponse(So (gqlRequest.s ceT et d)))
      )
    }
  }
}

object Unret etColumn {
  val Path = "t etyp e/unret et.T et"
}
