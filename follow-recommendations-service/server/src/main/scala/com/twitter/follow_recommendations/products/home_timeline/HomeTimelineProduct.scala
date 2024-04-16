package com.tw ter.follow_recom ndat ons.products.ho _t  l ne

 mport com.tw ter.follow_recom ndat ons.assembler.models.Act onConf g
 mport com.tw ter.follow_recom ndat ons.assembler.models.Follo dByUsersProof
 mport com.tw ter.follow_recom ndat ons.assembler.models.FooterConf g
 mport com.tw ter.follow_recom ndat ons.assembler.models.GeoContextProof
 mport com.tw ter.follow_recom ndat ons.assembler.models. aderConf g
 mport com.tw ter.follow_recom ndat ons.assembler.models.La t
 mport com.tw ter.follow_recom ndat ons.assembler.models.T leConf g
 mport com.tw ter.follow_recom ndat ons.assembler.models.UserL stLa t
 mport com.tw ter.follow_recom ndat ons.assembler.models.UserL stOpt ons
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
 mport com.tw ter.follow_recom ndat ons.products.ho _t  l ne.conf gap .Ho T  l neParams._
 mport com.tw ter. nject. njector
 mport com.tw ter.product_m xer.core.model.marshall ng.request
 mport com.tw ter.product_m xer.core.product.gu ce.ProductScope
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Ho T  l neProduct @ nject() (
  postNuxMlFlow: PostNuxMlFlow,
  postNuxMlRequestBu lder: PostNuxMlRequestBu lder,
  promotedAccountsFlow: PromotedAccountsFlow,
  promotedAccountsBlender: PromotedAccountsBlender,
  productScope: ProductScope,
   njector:  njector,
) extends Product {

  overr de val na : Str ng = "Ho  T  l ne"

  overr de val  dent f er: Str ng = "ho -t  l ne"

  overr de val d splayLocat on: D splayLocat on = D splayLocat on.Ho T  l ne

  overr de def selectWorkflows(
    request: ProductRequest
  ): St ch[Seq[BaseRecom ndat onFlow[ProductRequest, _ <: Recom ndat on]]] = {
    postNuxMlRequestBu lder.bu ld(request).map { postNuxMlRequest =>
      Seq(
        postNuxMlFlow.mapKey({ request: ProductRequest => postNuxMlRequest }),
        promotedAccountsFlow.mapKey(mkPromotedAccountsRequest))
    }
  }

  overr de val blender: Transform[ProductRequest, Recom ndat on] = {
    promotedAccountsBlender.mapTarget[ProductRequest](getMaxResults)
  }

  pr vate val  dent yTransform = new  dent yTransform[ProductRequest, Recom ndat on]

  overr de def resultsTransfor r(
    request: ProductRequest
  ): St ch[Transform[ProductRequest, Recom ndat on]] = St ch.value( dent yTransform)

  overr de def enabled(request: ProductRequest): St ch[Boolean] =
    St ch.value(request.params(EnableProduct))

  overr de def la t: Opt on[La t] = {
    productM xerProduct.map { product =>
      val ho T  l neStr ngs = productScope.let(product) {
         njector. nstance[Ho T  l neStr ngs]
      }
      UserL stLa t(
         ader = So ( aderConf g(T leConf g(ho T  l neStr ngs.whoToFollowModuleT le))),
        userL stOpt ons = UserL stOpt ons(userB oEnabled = true, userB oTruncated = true, None),
        soc alProofs = So (
          Seq(
            Follo dByUsersProof(
              ho T  l neStr ngs.whoToFollowFollo dByManyUserS ngleStr ng,
              ho T  l neStr ngs.whoToFollowFollo dByManyUserDoubleStr ng,
              ho T  l neStr ngs.whoToFollowFollo dByManyUserMult pleStr ng
            ),
            GeoContextProof(ho T  l neStr ngs.whoToFollowPopular nCountryKey)
          )),
        footer = So (
          FooterConf g(
            So (Act onConf g(ho T  l neStr ngs.whoToFollowModuleFooter, "http://tw ter.com"))))
      )
    }
  }

  overr de def productM xerProduct: Opt on[request.Product] = So (HTLProductM xer)

  pr vate[ho _t  l ne] def mkPromotedAccountsRequest(
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

  pr vate[ho _t  l ne] def getMaxResults(req: ProductRequest):  nt = {
    req.recom ndat onRequest.maxResults.getOrElse(
      req.params(DefaultMaxResults)
    )
  }
}
