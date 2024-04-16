package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala.{ em => LogEvent em}
 mport com.tw ter.suggests.controller_data.ho _t ets.thr ftscala.Ho T etsControllerData
 mport com.tw ter.suggests.controller_data.ho _t ets.thr ftscala.Ho T etsControllerDataAl ases.V1Al as
 mport com.tw ter.suggests.controller_data.thr ftscala.ControllerData
 mport com.tw ter.suggests.controller_data.v2.thr ftscala.{ControllerData => ControllerDataV2}

object Ho  nfoUt ls {

  def getHo T etControllerDataV1(ce em: LogEvent em): Opt on[V1Al as] = {
    ce em.suggest onDeta ls
      .flatMap(_.decodedControllerData)
      .flatMap(_ match {
        case ControllerData.V2(
              ControllerDataV2.Ho T ets(
                Ho T etsControllerData.V1(ho T etsControllerDataV1)
              )) =>
          So (ho T etsControllerDataV1)
        case _ => None
      })
  }

  def getTrace d(ce em: LogEvent em): Opt on[Long] =
    getHo T etControllerDataV1(ce em).flatMap(_.trace d)

  def getSuggestType(ce em: LogEvent em): Opt on[Str ng] =
    ce em.suggest onDeta ls.flatMap(_.suggest onType)

  def getRequestJo n d(ce em: LogEvent em): Opt on[Long] =
    getHo T etControllerDataV1(ce em).flatMap(_.requestJo n d)
}
