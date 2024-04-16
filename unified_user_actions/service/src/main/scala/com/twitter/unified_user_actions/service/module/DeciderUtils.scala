package com.tw ter.un f ed_user_act ons.serv ce.module

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.dec der.RandomRec p ent
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on

sealed tra  Dec derUt ls {
  def shouldPubl sh(dec der: Dec der, uua: Un f edUserAct on, s nkTop c: Str ng): Boolean
}

object DefaultDec derUt ls extends Dec derUt ls {
  overr de def shouldPubl sh(dec der: Dec der, uua: Un f edUserAct on, s nkTop c: Str ng): Boolean =
    dec der. sAva lable(feature = s"Publ sh${uua.act onType}", So (RandomRec p ent))
}

object Cl entEventDec derUt ls extends Dec derUt ls {
  overr de def shouldPubl sh(dec der: Dec der, uua: Un f edUserAct on, s nkTop c: Str ng): Boolean =
    dec der. sAva lable(
      feature = s"Publ sh${uua.act onType}",
      So (RandomRec p ent)) && (uua.act onType match {
      // for  avy  mpress ons UUA only publ s s to t  "all" top c, not t  engage ntsOnly top c.
      case Act onType.Cl entT etL nger mpress on | Act onType.Cl entT etRender mpress on =>
        s nkTop c == Top csMapp ng().all
      case _ => true
    })
}
