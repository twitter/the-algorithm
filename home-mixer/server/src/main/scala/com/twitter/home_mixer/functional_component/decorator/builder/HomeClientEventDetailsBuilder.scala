package com.tw ter.ho _m xer.funct onal_component.decorator.bu lder

 mport com.tw ter.b ject on.Base64Str ng
 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter.b ject on.{ nject on => Ser al zer}
 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.ho _m xer.model.Ho Features.Cand dateS ce dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Pos  onFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.jo nkey.context.RequestJo nKeyContext
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEventDeta lsBu lder
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEventDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.T  l nesDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.suggests.controller_data.Ho 
 mport com.tw ter.suggests.controller_data.T etTypeGenerator
 mport com.tw ter.suggests.controller_data.ho _t ets.v1.{thr ftscala => v1ht}
 mport com.tw ter.suggests.controller_data.ho _t ets.{thr ftscala => ht}
 mport com.tw ter.suggests.controller_data.thr ftscala.ControllerData
 mport com.tw ter.suggests.controller_data.v2.thr ftscala.{ControllerData => ControllerDataV2}

object Ho Cl entEventDeta lsBu lder {
   mpl c  val ByteSer al zer: Ser al zer[ControllerData, Array[Byte]] =
    B naryScalaCodec(ControllerData)

  val ControllerDataSer al zer: Ser al zer[ControllerData, Str ng] =
    Ser al zer.connect[ControllerData, Array[Byte], Base64Str ng, Str ng]

  /**
   * def ne getRequestJo n d as a  thod(def) rat r than a val because each new request
   * needs to call t  context to update t   d.
   */
  pr vate def getRequestJo n d(): Opt on[Long] =
    RequestJo nKeyContext.current.flatMap(_.requestJo n d)
}

case class Ho Cl entEventDeta lsBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]](
) extends BaseCl entEventDeta lsBu lder[Query, Cand date]
    w h T etTypeGenerator[FeatureMap] {

   mport Ho Cl entEventDeta lsBu lder._

  overr de def apply(
    query: Query,
    cand date: Cand date,
    cand dateFeatures: FeatureMap
  ): Opt on[Cl entEventDeta ls] = {

    val t etTypesB maps = mkT etTypesB maps(
      Ho .T etType dxMap,
      Ho T etTypePred cates.Pred cateMap,
      cand dateFeatures)

    val t etTypesL stBytes = mk emTypesB mapsV2(
      Ho .T etType dxMap,
      Ho T etTypePred cates.Pred cateMap,
      cand dateFeatures)

    val cand dateS ce d =
      cand dateFeatures.getOrElse(Cand dateS ce dFeature, None).map(_.value.toByte)

    val ho T etsControllerDataV1 = v1ht.Ho T etsControllerData(
      t etTypesB map = t etTypesB maps.getOrElse(0, 0L),
      t etTypesB mapCont nued1 = t etTypesB maps.get(1),
      cand dateT etS ce d = cand dateS ce d,
      trace d = So (Trace. d.trace d.toLong),
       njectedPos  on = cand dateFeatures.getOrElse(Pos  onFeature, None),
      t etTypesL stBytes = So (t etTypesL stBytes),
      requestJo n d = getRequestJo n d(),
    )

    val ser al zedControllerData = ControllerDataSer al zer(
      ControllerData.V2(
        ControllerDataV2.Ho T ets(ht.Ho T etsControllerData.V1(ho T etsControllerDataV1))))

    val cl entEventDeta ls = Cl entEventDeta ls(
      conversat onDeta ls = None,
      t  l nesDeta ls = So (
        T  l nesDeta ls(
           nject onType = cand dateFeatures.getOrElse(SuggestTypeFeature, None).map(_.na ),
          controllerData = So (ser al zedControllerData),
          s ceData = None)),
      art cleDeta ls = None,
      l veEventDeta ls = None,
      com rceDeta ls = None
    )

    So (cl entEventDeta ls)
  }
}
