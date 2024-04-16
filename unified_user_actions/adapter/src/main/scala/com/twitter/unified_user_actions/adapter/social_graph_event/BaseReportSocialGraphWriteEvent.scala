package com.tw ter.un f ed_user_act ons.adapter.soc al_graph_event

 mport com.tw ter.soc algraph.thr ftscala.Act on
 mport com.tw ter.soc algraph.thr ftscala.SrcTargetRequest
 mport com.tw ter.un f ed_user_act ons.thr ftscala. em
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Prof leAct on nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Prof le nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala.ServerProf leReport

abstract class BaseReportSoc alGraphWr eEvent[T] extends BaseSoc alGraphWr eEvent[T] {
  def soc alGraphAct on: Act on

  overr de def getSoc alGraph em(soc alGraphSrcTargetRequest: SrcTargetRequest):  em = {
     em.Prof le nfo(
      Prof le nfo(
        act onProf le d = soc alGraphSrcTargetRequest.target,
        prof leAct on nfo = So (
          Prof leAct on nfo.ServerProf leReport(
            ServerProf leReport(reportType = soc alGraphAct on)
          ))
      )
    )
  }
}
