package com.tw ter.un f ed_user_act ons.adapter.user_mod f cat on

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter.g zmoduck.thr ftscala.UserMod f cat on
 mport com.tw ter.un f ed_user_act ons.adapter.AbstractAdapter
 mport com.tw ter.un f ed_user_act ons.adapter.user_mod f cat on_event.UserCreate
 mport com.tw ter.un f ed_user_act ons.adapter.user_mod f cat on_event.UserUpdate
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on

class UserMod f cat onAdapter
    extends AbstractAdapter[UserMod f cat on, UnKeyed, Un f edUserAct on] {

   mport UserMod f cat onAdapter._

  overr de def adaptOneToKeyedMany(
     nput: UserMod f cat on,
    statsRece ver: StatsRece ver = NullStatsRece ver
  ): Seq[(UnKeyed, Un f edUserAct on)] =
    adaptEvent( nput).map { e => (UnKeyed, e) }
}

object UserMod f cat onAdapter {

  def adaptEvent( nput: UserMod f cat on): Seq[Un f edUserAct on] =
    Opt on( nput).toSeq.flatMap { e =>
       f (e.create. sDef ned) { // User create
        So (UserCreate.getUUA( nput))
      } else  f (e.update. sDef ned) { // User updates
        So (UserUpdate.getUUA( nput))
      } else  f (e.destroy. sDef ned) {
        None
      } else  f (e.erase. sDef ned) {
        None
      } else {
        throw new  llegalArgu ntExcept on(
          "None of t  poss ble events  s def ned, t re must be so th ng w h t  s ce")
      }
    }
}
