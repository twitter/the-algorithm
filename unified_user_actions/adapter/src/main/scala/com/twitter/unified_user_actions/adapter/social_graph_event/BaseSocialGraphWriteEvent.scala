package com.tw ter.un f ed_user_act ons.adapter.soc al_graph_event

 mport com.tw ter.soc algraph.thr ftscala.LogEventContext
 mport com.tw ter.soc algraph.thr ftscala.SrcTargetRequest
 mport com.tw ter.soc algraph.thr ftscala.Wr eEvent
 mport com.tw ter.soc algraph.thr ftscala.Wr eRequestResult
 mport com.tw ter.un f ed_user_act ons.adapter.common.AdapterUt ls
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Event tadata
 mport com.tw ter.un f ed_user_act ons.thr ftscala. em
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Prof le nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala.S ceL neage
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on
 mport com.tw ter.un f ed_user_act ons.thr ftscala.User dent f er

tra  BaseSoc alGraphWr eEvent[T] {
  def uuaAct onType: Act onType

  def getSrcTargetRequest(
    e: Wr eEvent
  ): Seq[SrcTargetRequest] = getSubType(e) match {
    case So (subType: Seq[T]) =>
      getWr eRequestResultFromSubType(subType).collect {
        case r  f r.val dat onError. sEmpty => r.request
      }
    case _ => N l
  }

  def getSubType(e: Wr eEvent): Opt on[Seq[T]]
  def getWr eRequestResultFromSubType(subType: Seq[T]): Seq[Wr eRequestResult]

  def toUn f edUserAct on(
    wr eEvent: Wr eEvent,
    uuaAct on: BaseSoc alGraphWr eEvent[_]
  ): Seq[Un f edUserAct on] =
    uuaAct on.getSrcTargetRequest(wr eEvent).map { srcTargetRequest =>
      Un f edUserAct on(
        user dent f er = User dent f er(user d = wr eEvent.context.logged nUser d),
         em = getSoc alGraph em(srcTargetRequest),
        act onType = uuaAct on.uuaAct onType,
        event tadata = getEvent tadata(wr eEvent.context)
      )
    }

  def getSoc alGraph em(soc alGraphSrcTargetRequest: SrcTargetRequest):  em = {
     em.Prof le nfo(
      Prof le nfo(
        act onProf le d = soc alGraphSrcTargetRequest.target
      )
    )
  }

  def getEvent tadata(context: LogEventContext): Event tadata = {
    Event tadata(
      s ceT  stampMs = context.t  stamp,
      rece vedT  stampMs = AdapterUt ls.currentT  stampMs,
      s ceL neage = S ceL neage.ServerSoc alGraphEvents,
    )
  }
}
