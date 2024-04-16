package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.who_to_follow_module

 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter.b ject on.Base64Str ng
 mport com.tw ter.b ject on.{ nject on => Ser al zer}
 mport com.tw ter. rm . nternal.thr ftscala. rm Track ngToken
 mport com.tw ter.product_m xer.component_l brary.model.cand date.UserCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEventDeta lsBu lder
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEventDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.T  l nesDeta ls
 mport com.tw ter.servo.cac .Thr ftSer al zer
 mport com.tw ter.suggests.controller_data.thr ftscala.ControllerData
 mport com.tw ter.ut l.Try
 mport org.apac .thr ft.protocol.TB naryProtocol

object WhoToFollowCl entEventDeta lsBu lder {

  val  nject onType = "WhoToFollow"

  pr vate  mpl c  val ByteSer al zer: Ser al zer[ControllerData, Array[Byte]] =
    B naryScalaCodec(ControllerData)

  pr vate val Track ngTokenSer al zer =
    new Thr ftSer al zer[ rm Track ngToken]( rm Track ngToken, new TB naryProtocol.Factory())

  val ControllerDataSer al zer: Ser al zer[ControllerData, Str ng] =
    Ser al zer.connect[ControllerData, Array[Byte], Base64Str ng, Str ng]

  def deser al zeTrack ngToken(token: Opt on[Str ng]): Opt on[ rm Track ngToken] =
    token.flatMap(t => Try(Track ngTokenSer al zer.fromStr ng(t)).toOpt on)

  def ser al zeControllerData(cd: ControllerData): Str ng = ControllerDataSer al zer(cd)
}

case class WhoToFollowCl entEventDeta lsBu lder[-Query <: P pel neQuery](
  track ngTokenFeature: Feature[_, Opt on[Str ng]],
) extends BaseCl entEventDeta lsBu lder[Query, UserCand date] {

  overr de def apply(
    query: Query,
    cand date: UserCand date,
    cand dateFeatures: FeatureMap
  ): Opt on[Cl entEventDeta ls] = {
    val ser al zedTrack ngToken = cand dateFeatures.getOrElse(track ngTokenFeature, None)

    val controllerData = WhoToFollowCl entEventDeta lsBu lder
      .deser al zeTrack ngToken(ser al zedTrack ngToken)
      .flatMap(_.controllerData)
      .map(WhoToFollowCl entEventDeta lsBu lder.ser al zeControllerData)

    So (
      Cl entEventDeta ls(
        conversat onDeta ls = None,
        t  l nesDeta ls = So (
          T  l nesDeta ls(
             nject onType = So (WhoToFollowCl entEventDeta lsBu lder. nject onType),
            controllerData = controllerData,
            s ceData = ser al zedTrack ngToken)),
        art cleDeta ls = None,
        l veEventDeta ls = None,
        com rceDeta ls = None
      ))
  }
}
