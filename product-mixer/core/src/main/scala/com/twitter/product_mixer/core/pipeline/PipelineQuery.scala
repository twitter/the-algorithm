package com.tw ter.product_m xer.core.p pel ne

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasDebugOpt ons
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasProduct
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.T  

tra  P pel neQuery extends HasParams w h HasCl entContext w h HasProduct w h HasDebugOpt ons {
  self =>

  /** Set a query t   val that  s constant for t  durat on of t  query l fecycle */
  val queryT  : T   = self.debugOpt ons.flatMap(_.requestT  Overr de).getOrElse(T  .now)

  /** T  requested max results  s spec f ed, or not spec f ed, by t  thr ft cl ent */
  def requestedMaxResults: Opt on[ nt]

  /** Retr eves t  max results w h a default Param,  f not spec f ed by t  thr ft cl ent */
  def maxResults(defaultRequestedMaxResultParam: Param[ nt]):  nt =
    requestedMaxResults.getOrElse(params(defaultRequestedMaxResultParam))

  /** Opt onal [[FeatureMap]], t  may be updated later us ng [[w hFeatureMap]] */
  def features: Opt on[FeatureMap]

  /**
   * S nce Query-Level features can be hydrated later,   need t   thod to update t  P pel neQuery
   * usually t  w ll be  mple nted v a `copy(features = So (features))`
   */
  def w hFeatureMap(features: FeatureMap): P pel neQuery
}
