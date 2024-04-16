package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. tadata

 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter.b ject on.Base64Str ng
 mport com.tw ter.b ject on.{ nject on => Ser al zer}
 mport com.tw ter. nterests_m xer.model.request.{HasTop c d =>  nterestsM xerHasTop c d}
 mport com.tw ter.explore_m xer.model.request.{HasTop c d => ExploreM xerHasTop c d}
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEventDeta lsBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEventDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.T  l nesDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.suggests.controller_data.ho _t ets.thr ftscala.Ho T etsControllerData
 mport com.tw ter.suggests.controller_data.ho _t ets.v1.thr ftscala.{
  Ho T etsControllerData => Ho T etsControllerDataV1
}
 mport com.tw ter.suggests.controller_data.thr ftscala.ControllerData
 mport com.tw ter.suggests.controller_data.v2.thr ftscala.{ControllerData => ControllerDataV2}

object Top cT etCl entEventDeta lsBu lder {
   mpl c  val ByteSer al zer: Ser al zer[ControllerData, Array[Byte]] =
    B naryScalaCodec(ControllerData)

  val ControllerDataSer al zer: Ser al zer[ControllerData, Str ng] =
    Ser al zer.connect[ControllerData, Array[Byte], Base64Str ng, Str ng]
}

case class Top cT etCl entEventDeta lsBu lder[-Query <: P pel neQuery]()
    extends BaseCl entEventDeta lsBu lder[Query, T etCand date] {

   mport Top cT etCl entEventDeta lsBu lder._

  overr de def apply(
    query: Query,
    top cT etCand date: T etCand date,
    cand dateFeatures: FeatureMap
  ): Opt on[Cl entEventDeta ls] =
    So (
      Cl entEventDeta ls(
        conversat onDeta ls = None,
        t  l nesDeta ls = So (
          T  l nesDeta ls(
             nject onType = None,
            controllerData = bu ldControllerData(getTop c d(query)),
            s ceData = None)),
        art cleDeta ls = None,
        l veEventDeta ls = None,
        com rceDeta ls = None
      ))

  pr vate def getTop c d(query: Query): Opt on[Long] = {
    query match {
      case query:  nterestsM xerHasTop c d => query.top c d
      case query: ExploreM xerHasTop c d => query.top c d
      case _ => None
    }
  }

  pr vate def bu ldControllerData(top c d: Opt on[Long]): Opt on[Str ng] =
    So (
      ControllerData
        .V2(ControllerDataV2.Ho T ets(Ho T etsControllerData.V1(
          Ho T etsControllerDataV1(t etTypesB map = 0L, top c d = top c d)))))
      .map(ControllerDataSer al zer)
}
