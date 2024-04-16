package com.tw ter.follow_recom ndat ons.products.s debar

 mport com.tw ter.follow_recom ndat ons.common.base.BaseRecom ndat onFlow
 mport com.tw ter.follow_recom ndat ons.common.base. dent yTransform
 mport com.tw ter.follow_recom ndat ons.common.base.Transform
 mport com.tw ter.follow_recom ndat ons.flows.ads.PromotedAccountsFlow
 mport com.tw ter.follow_recom ndat ons.flows.ads.PromotedAccountsFlowRequest
 mport com.tw ter.follow_recom ndat ons.blenders.PromotedAccountsBlender
 mport com.tw ter.follow_recom ndat ons.common.models.D splayLocat on
 mport com.tw ter.follow_recom ndat ons.common.models.Recom ndat on
 mport com.tw ter.follow_recom ndat ons.flows.post_nux_ml.PostNuxMlFlow
 mport com.tw ter.follow_recom ndat ons.flows.post_nux_ml.PostNuxMlRequestBu lder
 mport com.tw ter.follow_recom ndat ons.products.common.Product
 mport com.tw ter.follow_recom ndat ons.products.common.ProductRequest
 mport com.tw ter.follow_recom ndat ons.products.s debar.conf gap .S debarParams
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class S debarProduct @ nject() (
  postNuxMlFlow: PostNuxMlFlow,
  postNuxMlRequestBu lder: PostNuxMlRequestBu lder,
  promotedAccountsFlow: PromotedAccountsFlow,
  promotedAccountsBlender: PromotedAccountsBlender)
    extends Product {
  overr de val na : Str ng = "S debar"

  overr de val  dent f er: Str ng = "s debar"

  overr de val d splayLocat on: D splayLocat on = D splayLocat on.S debar

  overr de def selectWorkflows(
    request: ProductRequest
  ): St ch[Seq[BaseRecom ndat onFlow[ProductRequest, _ <: Recom ndat on]]] = {
    postNuxMlRequestBu lder.bu ld(request).map { postNuxMlRequest =>
      Seq(
        postNuxMlFlow.mapKey({ _: ProductRequest => postNuxMlRequest }),
        promotedAccountsFlow.mapKey(mkPromotedAccountsRequest)
      )
    }
  }

  overr de val blender: Transform[ProductRequest, Recom ndat on] = {
    promotedAccountsBlender.mapTarget[ProductRequest](getMaxResults)
  }

  pr vate[s debar] def mkPromotedAccountsRequest(
    req: ProductRequest
  ): PromotedAccountsFlowRequest = {
    PromotedAccountsFlowRequest(
      req.recom ndat onRequest.cl entContext,
      req.params,
      req.recom ndat onRequest.d splayLocat on,
      None,
      req.recom ndat onRequest.excluded ds.getOrElse(N l)
    )
  }

  pr vate[s debar] def getMaxResults(req: ProductRequest):  nt = {
    req.recom ndat onRequest.maxResults.getOrElse(
      req.params(S debarParams.DefaultMaxResults)
    )
  }

  overr de def resultsTransfor r(
    request: ProductRequest
  ): St ch[Transform[ProductRequest, Recom ndat on]] =
    St ch.value(new  dent yTransform[ProductRequest, Recom ndat on])

  overr de def enabled(request: ProductRequest): St ch[Boolean] =
    St ch.value(request.params(S debarParams.EnableProduct))
}
