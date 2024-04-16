package com.tw ter.fr gate.pushserv ce.adaptor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base._
 mport com.tw ter.fr gate.common.cand date._
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter.fr gate.pushserv ce.ut l.PushDev ceUt l
 mport com.tw ter.st ch.t etyp e.T etyP e.T etyP eResult
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

object Gener cCand dates {
  type Target =
    TargetUser
      w h UserDeta ls
      w h TargetDec der
      w h TargetABDec der
      w h T et mpress on tory
      w h HTLV s  tory
      w h MaxT etAge
      w h NewUserDeta ls
      w h Fr gate tory
      w h TargetW hSeedUsers
}

case class Gener cCand dateAdaptor(
  gener cCand dates: Cand dateS ce[Gener cCand dates.Target, Cand date],
  t etyP eStore: ReadableStore[Long, T etyP eResult],
  t etyP eStoreNoVF: ReadableStore[Long, T etyP eResult],
  stats: StatsRece ver)
    extends Cand dateS ce[Target, RawCand date]
    w h Cand dateS ceEl g ble[Target, RawCand date] {

  overr de val na : Str ng = gener cCand dates.na 

  pr vate def generateT etFavCand date(
    _target: Target,
    _t et d: Long,
    _soc alContextAct ons: Seq[Soc alContextAct on],
    soc alContextAct onsAllTypes: Seq[Soc alContextAct on],
    _t etyP eResult: Opt on[T etyP eResult]
  ): RawCand date = {
    new RawCand date w h T etFavor eCand date {
      overr de val soc alContextAct ons = _soc alContextAct ons
      overr de val soc alContextAllTypeAct ons =
        soc alContextAct onsAllTypes
      val t et d = _t et d
      val target = _target
      val t etyP eResult = _t etyP eResult
    }
  }

  pr vate def generateT etRet etCand date(
    _target: Target,
    _t et d: Long,
    _soc alContextAct ons: Seq[Soc alContextAct on],
    soc alContextAct onsAllTypes: Seq[Soc alContextAct on],
    _t etyP eResult: Opt on[T etyP eResult]
  ): RawCand date = {
    new RawCand date w h T etRet etCand date {
      overr de val soc alContextAct ons = _soc alContextAct ons
      overr de val soc alContextAllTypeAct ons = soc alContextAct onsAllTypes
      val t et d = _t et d
      val target = _target
      val t etyP eResult = _t etyP eResult
    }
  }

  overr de def get( nputTarget: Target): Future[Opt on[Seq[RawCand date]]] = {
    gener cCand dates.get( nputTarget).map { cand datesOpt =>
      cand datesOpt
        .map { cand dates =>
          val cand datesSeq =
            cand dates.collect {
              case t etRet et: T etRet etCand date
                   f  nputTarget.params(PushParams.MRT etRet etRecsParam) =>
                generateT etRet etCand date(
                   nputTarget,
                  t etRet et.t et d,
                  t etRet et.soc alContextAct ons,
                  t etRet et.soc alContextAllTypeAct ons,
                  t etRet et.t etyP eResult)
              case t etFavor e: T etFavor eCand date
                   f  nputTarget.params(PushParams.MRT etFavRecsParam) =>
                generateT etFavCand date(
                   nputTarget,
                  t etFavor e.t et d,
                  t etFavor e.soc alContextAct ons,
                  t etFavor e.soc alContextAllTypeAct ons,
                  t etFavor e.t etyP eResult)
            }
          cand datesSeq.foreach { cand date =>
            stats.counter(s"${cand date.commonRecType}_count"). ncr()
          }
          cand datesSeq
        }
    }
  }

  overr de def  sCand dateS ceAva lable(target: Target): Future[Boolean] = {
    PushDev ceUt l. sRecom ndat onsEl g ble(target).map {  sAva lable =>
       sAva lable && target.params(PushParams.Gener cCand dateAdaptorDec der)
    }
  }
}
