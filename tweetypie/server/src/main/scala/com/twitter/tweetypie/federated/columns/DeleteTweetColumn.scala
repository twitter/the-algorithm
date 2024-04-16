package com.tw ter.t etyp e.federated.columns

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
 mport com.tw ter.t etyp e.federated.prefetc ddata.Prefetc dDataResponse
 mport com.tw ter.t etyp e.thr ftscala.T etDeleteState
 mport com.tw ter.t etyp e.thr ftscala.{graphql => gql}
 mport com.tw ter.t etyp e.{thr ftscala => thr ft}
 mport com.tw ter.ut l.Future

class DeleteT etColumn(
  deleteT et: thr ft.DeleteT etsRequest => Future[Seq[thr ft.DeleteT etResult]],
  getRequestContext: GetRequestContext,
) extends StratoFed.Column(DeleteT etColumn.Path)
    w h StratoFed.Execute.St chW hContext
    w h StratoFed.HandleDarkRequests {

  overr de val pol cy: Pol cy = AccessPol cy.T etMutat onCommonAccessPol c es

  overr de val  s dempotent: Boolean = true

  overr de type Arg = gql.DeleteT etRequest
  overr de type Result = gql.DeleteT etResponseW hSubqueryPrefetch ems

  overr de val argConv: Conv[Arg] = ScroogeConv.fromStruct
  overr de val resultConv: Conv[Result] = ScroogeConv.fromStruct

  overr de val contact nfo: Contact nfo = T etyp eContact nfo
  overr de val  tadata: Op tadata =
    Op tadata(So (Product on), So (Pla nText("Deletes a t et by t  call ng Tw ter user.")))

  overr de def execute(request: Arg, opContext: OpContext): St ch[Result] = {
    val ctx = getRequestContext(opContext)

    val thr ftDeleteT etRequest = thr ft.DeleteT etsRequest(
      t et ds = Seq(request.t et d),
      // byUser d  s p cked up by t  context  n t etyp e.deleteT et,
      // but  're pass ng    n  re to be expl c 
      byUser d = So (ctx.tw terUser d),
    )

    val st chDeleteT et = handleDarkRequest(opContext)(
      l ght = {
        St ch.callFuture(deleteT et(thr ftDeleteT etRequest))
      },
      // For dark requests,   don't want to send traff c to t etyp e.
      // S nce t  response  s t  sa  regardless of t  request,   take a no-op
      // act on  nstead.
      dark = St ch.value(Seq(thr ft.DeleteT etResult(request.t et d, T etDeleteState.Ok)))
    )

    st chDeleteT et.map { result: Seq[thr ft.DeleteT etResult] =>
      result. adOpt on match {
        case So (thr ft.DeleteT etResult( d, T etDeleteState.Ok)) =>
          gql.DeleteT etResponseW hSubqueryPrefetch ems(
            data = So (gql.DeleteT etResponse(So ( d))),
            // Prefetch data  s always NotFound to prevent subquer es from hydrat ng v a  averb rd
            // and poss bly return ng  ncons stent results,  .e. a Found t et.
            subqueryPrefetch ems = So (Prefetc dDataResponse.notFound( d).value)
          )
        case So (thr ft.DeleteT etResult(_, T etDeleteState.Perm ss onError)) =>
          throw Ap Errors.DeletePerm ss onErr
        case _ =>
          throw Ap Errors.Gener cAccessDen edErr
      }
    }
  }
}

object DeleteT etColumn {
  val Path = "t etyp e/deleteT et.T et"
}
