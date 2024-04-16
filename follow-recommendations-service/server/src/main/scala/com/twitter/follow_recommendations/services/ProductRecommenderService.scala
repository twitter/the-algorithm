package com.tw ter.follow_recom ndat ons.serv ces

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.StatsUt l
 mport com.tw ter.follow_recom ndat ons.common.models.Recom ndat on
 mport com.tw ter.follow_recom ndat ons.models.Recom ndat onRequest
 mport com.tw ter.follow_recom ndat ons.products.common.ProductReg stry
 mport com.tw ter.follow_recom ndat ons.products.common.ProductRequest
 mport com.tw ter.st ch.St ch
 mport com.tw ter.follow_recom ndat ons.conf gap .params.GlobalParams.EnableWhoToFollowProducts
 mport com.tw ter.t  l nes.conf gap .Params
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ProductRecom nderServ ce @ nject() (
  productReg stry: ProductReg stry,
  statsRece ver: StatsRece ver) {

  pr vate val stats = statsRece ver.scope("ProductRecom nderServ ce")

  def getRecom ndat ons(
    request: Recom ndat onRequest,
    params: Params
  ): St ch[Seq[Recom ndat on]] = {
    val d splayLocat on = request.d splayLocat on
    val d splayLocat onStatNa  = d splayLocat on.toStr ng
    val locat onStats = stats.scope(d splayLocat onStatNa )
    val logged nOrOutStats =  f (request.cl entContext.user d. sDef ned) {
      stats.scope("logged_ n").scope(d splayLocat onStatNa )
    } else {
      stats.scope("logged_out").scope(d splayLocat onStatNa )
    }

    logged nOrOutStats.counter("requests"). ncr()
    val product = productReg stry.getProductByD splayLocat on(d splayLocat on)
    val productRequest = ProductRequest(request, params)
    val productEnabledSt ch =
      StatsUt l.prof leSt ch(product.enabled(productRequest), locat onStats.scope("enabled"))
    productEnabledSt ch.flatMap { productEnabled =>
       f (productEnabled && params(EnableWhoToFollowProducts)) {
        logged nOrOutStats.counter("enabled"). ncr()
        val st ch = for {
          workflows <- StatsUt l.prof leSt ch(
            product.selectWorkflows(productRequest),
            locat onStats.scope("select_workflows"))
          workflowRecos <- StatsUt l.prof leSt ch(
            St ch.collect(
              workflows.map(_.process(productRequest).map(_.result.getOrElse(Seq.empty)))),
            locat onStats.scope("execute_workflows")
          )
          blendedCand dates <- StatsUt l.prof leSt ch(
            product.blender.transform(productRequest, workflowRecos.flatten),
            locat onStats.scope("blend_results"))
          resultsTransfor r <- StatsUt l.prof leSt ch(
            product.resultsTransfor r(productRequest),
            locat onStats.scope("results_transfor r"))
          transfor dCand dates <- StatsUt l.prof leSt ch(
            resultsTransfor r.transform(productRequest, blendedCand dates),
            locat onStats.scope("execute_results_transfor r"))
        } y eld {
          transfor dCand dates
        }
        StatsUt l.prof leSt chResults[Seq[Recom ndat on]](st ch, locat onStats, _.s ze)
      } else {
        logged nOrOutStats.counter("d sabled"). ncr()
        locat onStats.counter("d sabled_product"). ncr()
        St ch.N l
      }
    }
  }
}
