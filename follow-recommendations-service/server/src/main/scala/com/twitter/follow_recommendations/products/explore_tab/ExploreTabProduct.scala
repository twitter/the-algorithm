package com.tw ter.follow_recom ndat ons.products.explore_tab

 mport com.tw ter.follow_recom ndat ons.common.base.BaseRecom ndat onFlow
 mport com.tw ter.follow_recom ndat ons.common.base. dent yTransform
 mport com.tw ter.follow_recom ndat ons.common.base.Transform
 mport com.tw ter.follow_recom ndat ons.common.models.D splayLocat on
 mport com.tw ter.follow_recom ndat ons.common.models.Recom ndat on
 mport com.tw ter.follow_recom ndat ons.flows.post_nux_ml.PostNuxMlFlow
 mport com.tw ter.follow_recom ndat ons.flows.post_nux_ml.PostNuxMlRequestBu lder
 mport com.tw ter.follow_recom ndat ons.products.common.Product
 mport com.tw ter.follow_recom ndat ons.products.common.ProductRequest
 mport com.tw ter.follow_recom ndat ons.products.explore_tab.conf gap .ExploreTabParams
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ExploreTabProduct @ nject() (
  postNuxMlFlow: PostNuxMlFlow,
  postNuxMlRequestBu lder: PostNuxMlRequestBu lder)
    extends Product {
  overr de val na : Str ng = "Explore Tab"

  overr de val  dent f er: Str ng = "explore-tab"

  overr de val d splayLocat on: D splayLocat on = D splayLocat on.ExploreTab

  overr de def selectWorkflows(
    request: ProductRequest
  ): St ch[Seq[BaseRecom ndat onFlow[ProductRequest, _ <: Recom ndat on]]] = {
    postNuxMlRequestBu lder.bu ld(request).map { postNuxMlRequest =>
      Seq(postNuxMlFlow.mapKey({ _: ProductRequest => postNuxMlRequest }))
    }
  }

  overr de val blender: Transform[ProductRequest, Recom ndat on] =
    new  dent yTransform[ProductRequest, Recom ndat on]

  overr de def resultsTransfor r(
    request: ProductRequest
  ): St ch[Transform[ProductRequest, Recom ndat on]] =
    St ch.value(new  dent yTransform[ProductRequest, Recom ndat on])

  overr de def enabled(request: ProductRequest): St ch[Boolean] = {
    //  deally   should hook up  s_soft_user as custom FS f eld and d sable t  product through FS
    val enabledForUserType = !request.recom ndat onRequest. sSoftUser || request.params(
      ExploreTabParams.EnableProductForSoftUser)
    St ch.value(request.params(ExploreTabParams.EnableProduct) && enabledForUserType)
  }
}
