package com.tw ter.un f ed_user_act ons.adapter.uua_aggregates

 mport com.tw ter.un f ed_user_act ons.adapter.common.AdapterUt ls
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Event tadata
 mport com.tw ter.un f ed_user_act ons.thr ftscala. em
 mport com.tw ter.un f ed_user_act ons.thr ftscala.KeyedUuaT et
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on

abstract class BaseUuaAct on(act onType: Act onType) {
  def getRekeyedUUA( nput: Un f edUserAct on): Opt on[KeyedUuaT et] =
    getT et dFrom em( nput. em).map { t et d =>
      KeyedUuaT et(
        t et d = t et d,
        act onType =  nput.act onType,
        user dent f er =  nput.user dent f er,
        event tadata = Event tadata(
          s ceT  stampMs =  nput.event tadata.s ceT  stampMs,
          rece vedT  stampMs = AdapterUt ls.currentT  stampMs,
          s ceL neage =  nput.event tadata.s ceL neage
        )
      )
    }

  protected def getT et dFrom em( em:  em): Opt on[Long] = {
     em match {
      case  em.T et nfo(t et nfo) => So (t et nfo.act onT et d)
      case _ => None
    }
  }
}

/**
 * W n t re  s a new user creat on event  n G zmoduck
 */
object Cl entT etRender mpress onUua extends BaseUuaAct on(Act onType.Cl entT etRender mpress on)
