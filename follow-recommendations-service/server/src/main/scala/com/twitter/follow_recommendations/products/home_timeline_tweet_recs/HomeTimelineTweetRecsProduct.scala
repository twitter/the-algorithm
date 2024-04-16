package com.tw ter.follow_recom ndat ons.products.ho _t  l ne_t et_recs

 mport com.tw ter.follow_recom ndat ons.common.base.BaseRecom ndat onFlow
 mport com.tw ter.follow_recom ndat ons.common.base. dent yTransform
 mport com.tw ter.follow_recom ndat ons.common.base.Transform
 mport com.tw ter.follow_recom ndat ons.common.models.D splayLocat on
 mport com.tw ter.follow_recom ndat ons.common.models.Recom ndat on
 mport com.tw ter.follow_recom ndat ons.flows.content_recom nder_flow.ContentRecom nderFlow
 mport com.tw ter.follow_recom ndat ons.flows.content_recom nder_flow.ContentRecom nderRequestBu lder
 mport com.tw ter.follow_recom ndat ons.products.common.Product
 mport com.tw ter.follow_recom ndat ons.products.common.ProductRequest
 mport com.tw ter.follow_recom ndat ons.products.ho _t  l ne_t et_recs.conf gap .Ho T  l neT etRecsParams._
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

/*
 * T  "D splayLocat on"  s used to generate user recom ndat ons us ng t  ContentRecom nderFlow. T se recom ndat ons are later used downstream
 * to generate recom nded t ets on Ho  T  l ne.
 */
@S ngleton
class Ho T  l neT etRecsProduct @ nject() (
  contentRecom nderFlow: ContentRecom nderFlow,
  contentRecom nderRequestBu lder: ContentRecom nderRequestBu lder)
    extends Product {
  overr de val na : Str ng = "Ho  T  l ne T et Recs"

  overr de val  dent f er: Str ng = "ho -t  l ne-t et-recs"

  overr de val d splayLocat on: D splayLocat on = D splayLocat on.Ho T  l neT etRecs

  overr de def selectWorkflows(
    request: ProductRequest
  ): St ch[Seq[BaseRecom ndat onFlow[ProductRequest, _ <: Recom ndat on]]] = {
    contentRecom nderRequestBu lder.bu ld(request).map { contentRecom nderRequest =>
      Seq(contentRecom nderFlow.mapKey({ request: ProductRequest => contentRecom nderRequest }))
    }
  }

  overr de val blender: Transform[ProductRequest, Recom ndat on] =
    new  dent yTransform[ProductRequest, Recom ndat on]

  overr de def resultsTransfor r(
    request: ProductRequest
  ): St ch[Transform[ProductRequest, Recom ndat on]] =
    St ch.value(new  dent yTransform[ProductRequest, Recom ndat on])

  overr de def enabled(request: ProductRequest): St ch[Boolean] =
    St ch.value(request.params(EnableProduct))
}
