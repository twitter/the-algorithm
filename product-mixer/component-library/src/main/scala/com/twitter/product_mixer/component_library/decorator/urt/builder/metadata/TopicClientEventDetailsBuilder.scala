package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. tadata

 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter.b ject on.Base64Str ng
 mport com.tw ter.b ject on.{ nject on => Ser al zer}
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEventDeta lsBu lder
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseTop cCand date
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEventDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.T  l nesDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.suggests.controller_data.thr ftscala.ControllerData
 mport com.tw ter.suggests.controller_data.t  l nes_top c.thr ftscala.T  l nesTop cControllerData
 mport com.tw ter.suggests.controller_data.t  l nes_top c.v1.thr ftscala.{
  T  l nesTop cControllerData => T  l nesTop cControllerDataV1
}
 mport com.tw ter.suggests.controller_data.v2.thr ftscala.{ControllerData => ControllerDataV2}

object Top cCl entEventDeta lsBu lder {
   mpl c  val ByteSer al zer: Ser al zer[ControllerData, Array[Byte]] =
    B naryScalaCodec(ControllerData)

  val ControllerDataSer al zer: Ser al zer[ControllerData, Str ng] =
    Ser al zer.connect[ControllerData, Array[Byte], Base64Str ng, Str ng]
}

case class Top cCl entEventDeta lsBu lder[-Query <: P pel neQuery]()
    extends BaseCl entEventDeta lsBu lder[Query, BaseTop cCand date] {

   mport Top cCl entEventDeta lsBu lder._

  overr de def apply(
    query: Query,
    top cCand date: BaseTop cCand date,
    cand dateFeatures: FeatureMap
  ): Opt on[Cl entEventDeta ls] =
    So (
      Cl entEventDeta ls(
        conversat onDeta ls = None,
        t  l nesDeta ls = So (
          T  l nesDeta ls(
             nject onType = None,
            controllerData = bu ldControllerData(top cCand date. d),
            s ceData = None)),
        art cleDeta ls = None,
        l veEventDeta ls = None,
        com rceDeta ls = None
      ))

  pr vate def bu ldControllerData(top c d: Long): Opt on[Str ng] =
    So (
      ControllerData
        .V2(ControllerDataV2.T  l nesTop c(T  l nesTop cControllerData.V1(
          T  l nesTop cControllerDataV1(top cTypesB map = 0L, top c d = top c d)))))
      .map(ControllerDataSer al zer)
}
