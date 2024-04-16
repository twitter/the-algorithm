package com.tw ter.follow_recom ndat ons.common.pred cates.user_act v y

 mport com.tw ter.core_workflows.user_model.thr ftscala.UserState
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.dec der.RandomRec p ent
 mport com.tw ter.f nagle. mcac d.Cl ent
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cate
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cateResult
 mport com.tw ter.follow_recom ndat ons.common.base.StatsUt l
 mport com.tw ter.follow_recom ndat ons.common.cl ents.cac . mcac Cl ent
 mport com.tw ter.follow_recom ndat ons.common.cl ents.cac .Thr ftEnumOpt onB ject on
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason
 mport com.tw ter.follow_recom ndat ons.conf gap .dec ders.Dec derKey
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.generated.cl ent.onboard ng.UserRecom ndab l yW hLongKeysOnUserCl entColumn
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport javax. nject. nject
 mport javax. nject.S ngleton

abstract case class UserStateAct v yPred cate(
  userRecom ndab l yCl ent: UserRecom ndab l yW hLongKeysOnUserCl entColumn,
  val dCand dateStates: Set[UserState],
  cl ent: Cl ent,
  statsRece ver: StatsRece ver,
  dec der: Dec der = Dec der.False)
    extends Pred cate[(HasParams w h HasCl entContext, Cand dateUser)] {

  pr vate val stats: StatsRece ver = statsRece ver.scope(t .getClass.getS mpleNa )

  // cl ent to  mcac  cluster
  val b ject on = new Thr ftEnumOpt onB ject on[UserState](UserState.apply)
  val  mcac Cl ent =  mcac Cl ent[Opt on[UserState]](
    cl ent = cl ent,
    dest = "/s/cac /follow_recos_serv ce:t mcac s",
    valueB ject on = b ject on,
    ttl = UserAct v yPred cateParams.Cac TTL,
    statsRece ver = stats.scope("t mcac ")
  )

  overr de def apply(
    targetAndCand date: (HasParams w h HasCl entContext, Cand dateUser)
  ): St ch[Pred cateResult] = {
    val userRecom ndab l yFetc r = userRecom ndab l yCl ent.fetc r
    val (_, cand date) = targetAndCand date

    val dec derKey: Str ng = Dec derKey.EnableExper  ntalCach ng.toStr ng
    val enableD str butedCach ng: Boolean = dec der. sAva lable(dec derKey, So (RandomRec p ent))
    val userStateSt ch: St ch[Opt on[UserState]] = 
      enableD str butedCach ng match {
        case true => {
           mcac Cl ent.readThrough(
            // add a key pref x to address cac  key coll s ons
            key = "UserAct v yPred cate" + cand date. d.toStr ng,
            underly ngCall = () => queryUserRecom ndable(cand date. d)
          )
        }
        case false => queryUserRecom ndable(cand date. d)
      }
    val resultSt ch: St ch[Pred cateResult] = 
      userStateSt ch.map { userStateOpt =>
        userStateOpt match {
          case So (userState) => {
             f (val dCand dateStates.conta ns(userState)) {
              Pred cateResult.Val d
            } else {
              Pred cateResult. nval d(Set(F lterReason.M nStateNot t))
            }
          }
          case None => {
            Pred cateResult. nval d(Set(F lterReason.M ss ngRecom ndab l yData))
          }
        }
      }
    
    StatsUt l.prof leSt ch(resultSt ch, stats.scope("apply"))
      .rescue {
        case e: Except on =>
          stats.scope("rescued").counter(e.getClass.getS mpleNa ). ncr()
          St ch(Pred cateResult. nval d(Set(F lterReason.Fa lOpen)))
      }
  }

  def queryUserRecom ndable(
    user d: Long
  ): St ch[Opt on[UserState]] = {
    val userRecom ndab l yFetc r = userRecom ndab l yCl ent.fetc r
    userRecom ndab l yFetc r.fetch(user d).map { userCand date => 
      userCand date.v.flatMap(_.userState)
    }
  }
}

@S ngleton
class M nStateUserAct v yPred cate @ nject() (
  userRecom ndab l yCl ent: UserRecom ndab l yW hLongKeysOnUserCl entColumn,
  cl ent: Cl ent,
  statsRece ver: StatsRece ver)
    extends UserStateAct v yPred cate(
      userRecom ndab l yCl ent,
      Set(
        UserState.L ght,
        UserState. avyNonT eter,
        UserState. d umNonT eter,
        UserState. avyT eter,
        UserState. d umT eter
      ),
      cl ent,
      statsRece ver
    )

@S ngleton
class AllT eterUserAct v yPred cate @ nject() (
  userRecom ndab l yCl ent: UserRecom ndab l yW hLongKeysOnUserCl entColumn,
  cl ent: Cl ent,
  statsRece ver: StatsRece ver)
    extends UserStateAct v yPred cate(
      userRecom ndab l yCl ent,
      Set(
        UserState. avyT eter,
        UserState. d umT eter
      ),
      cl ent,
      statsRece ver
    )

@S ngleton
class  avyT eterUserAct v yPred cate @ nject() (
  userRecom ndab l yCl ent: UserRecom ndab l yW hLongKeysOnUserCl entColumn,
  cl ent: Cl ent,
  statsRece ver: StatsRece ver)
    extends UserStateAct v yPred cate(
      userRecom ndab l yCl ent,
      Set(
        UserState. avyT eter
      ),
      cl ent,
      statsRece ver
    )

@S ngleton
class NonNearZeroUserAct v yPred cate @ nject() (
  userRecom ndab l yCl ent: UserRecom ndab l yW hLongKeysOnUserCl entColumn,
  cl ent: Cl ent,
  statsRece ver: StatsRece ver)
    extends UserStateAct v yPred cate(
      userRecom ndab l yCl ent,
      Set(
        UserState.New,
        UserState.VeryL ght,
        UserState.L ght,
        UserState. d umNonT eter,
        UserState. d umT eter,
        UserState. avyNonT eter,
        UserState. avyT eter
      ),
      cl ent,
      statsRece ver
    )
