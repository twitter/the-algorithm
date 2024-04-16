package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.b ject on.Bufferable
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle. mcac d.{Cl ent =>  mcac dCl ent}
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. rm .store.common.Observed mcac dReadableStore
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanRO
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanROConf g
 mport com.tw ter.storehaus_ nternal.ut l.HDFSPath
 mport com.tw ter.core_workflows.user_model.thr ftscala.UserState
 mport com.tw ter.core_workflows.user_model.thr ftscala.CondensedUserState
 mport com.tw ter.cr_m xer.conf g.T  outConf g
 mport com.tw ter.cr_m xer.param.dec der.CrM xerDec der
 mport com.tw ter.cr_m xer.param.dec der.Dec derKey
 mport com.tw ter. rm .store.common.Dec derableReadableStore
 mport com.tw ter.storehaus_ nternal.manhattan.Apollo
 mport com.tw ter.storehaus_ nternal.ut l.Appl cat on D
 mport com.tw ter.storehaus_ nternal.ut l.DatasetNa 
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.JavaT  r
 mport com.tw ter.ut l.T  
 mport com.tw ter.ut l.T  outExcept on
 mport com.tw ter.ut l.T  r
 mport javax. nject.Na d

object UserStateStoreModule extends Tw terModule {
   mpl c  val t  r: T  r = new JavaT  r(true)
  f nal val NewUserCreateDaysThreshold = 7
  f nal val DefaultUnknownUserStateValue = 100

  // Convert CondensedUserState to UserState Enum
  //  f CondensedUserState  s None, back f ll by c ck ng w t r t  user  s new user
  class UserStateStore(
    userStateStore: ReadableStore[User d, CondensedUserState],
    t  out: Durat on,
    statsRece ver: StatsRece ver)
      extends ReadableStore[User d, UserState] {
    overr de def get(user d: User d): Future[Opt on[UserState]] = {
      userStateStore
        .get(user d).map(_.flatMap(_.userState)).map {
          case So (userState) => So (userState)
          case None =>
            val  sNewUser = Snowflake d.t  From dOpt(user d).ex sts { userCreateT   =>
              T  .now - userCreateT   < Durat on.fromDays(NewUserCreateDaysThreshold)
            }
             f ( sNewUser) So (UserState.New)
            else So (UserState.EnumUnknownUserState(DefaultUnknownUserStateValue))

        }.ra seW h n(t  out)(t  r).rescue {
          case _: T  outExcept on =>
            statsRece ver.counter("T  outExcept on"). ncr()
            Future.None
        }
    }
  }

  @Prov des
  @S ngleton
  def prov desUserStateStore(
    crM xerDec der: CrM xerDec der,
    statsRece ver: StatsRece ver,
    manhattanKVCl entMtlsParams: ManhattanKVCl entMtlsParams,
    @Na d(ModuleNa s.Un f edCac ) crM xerUn f edCac Cl ent:  mcac dCl ent,
    t  outConf g: T  outConf g
  ): ReadableStore[User d, UserState] = {

    val underly ngStore = new UserStateStore(
      ManhattanRO
        .getReadableStoreW hMtls[User d, CondensedUserState](
          ManhattanROConf g(
            HDFSPath(""),
            Appl cat on D("cr_m xer_apollo"),
            DatasetNa ("condensed_user_state"),
            Apollo),
          manhattanKVCl entMtlsParams
        )(
           mpl c ly[ nject on[Long, Array[Byte]]],
          B naryScalaCodec(CondensedUserState)
        ),
      t  outConf g.userStateStoreT  out,
      statsRece ver.scope("UserStateStore")
    ).mapValues(_.value) // Read t  value of Enum so that   only cac s t   nt

    val  mCac dStore = Observed mcac dReadableStore
      .fromCac Cl ent(
        back ngStore = underly ngStore,
        cac Cl ent = crM xerUn f edCac Cl ent,
        ttl = 24.h s,
      )(
        value nject on = Bufferable. nject onOf[ nt], // Cac  Value  s Enum Value for UserState
        statsRece ver = statsRece ver.scope(" mCac dUserStateStore"),
        keyToStr ng = { k: User d => s"uState/$k" }
      ).mapValues(value => UserState.getOrUnknown(value))

    Dec derableReadableStore(
       mCac dStore,
      crM xerDec der.dec derGateBu lder. dGate(Dec derKey.enableUserStateStoreDec derKey),
      statsRece ver.scope("UserStateStore")
    )
  }
}
