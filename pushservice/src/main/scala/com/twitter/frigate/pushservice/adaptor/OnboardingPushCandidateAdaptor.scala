package com.tw ter.fr gate.pushserv ce.adaptor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateS ce
 mport com.tw ter.fr gate.common.base.Cand dateS ceEl g ble
 mport com.tw ter.fr gate.common.base.D scoverTw terCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params.{PushFeatureSw chParams => FS}
 mport com.tw ter.fr gate.pushserv ce.pred cate.D scoverTw terPred cate
 mport com.tw ter.fr gate.pushserv ce.pred cate.TargetPred cates
 mport com.tw ter.fr gate.pushserv ce.ut l.PushAppPerm ss onUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.PushDev ceUt l
 mport com.tw ter.fr gate.thr ftscala.{CommonRecom ndat onType => CRT}
 mport com.tw ter.ut l.Future

class Onboard ngPushCand dateAdaptor(
  globalStats: StatsRece ver)
    extends Cand dateS ce[Target, RawCand date]
    w h Cand dateS ceEl g ble[Target, RawCand date] {

  overr de val na : Str ng = t .getClass.getS mpleNa 

  pr vate[t ] val stats = globalStats.scope(na )
  pr vate[t ] val requestNum = stats.counter("request_num")
  pr vate[t ] val addressBookCandNum = stats.counter("address_book_cand_num")
  pr vate[t ] val completeOnboard ngCandNum = stats.counter("complete_onboard ng_cand_num")

  pr vate def generateOnboard ngPushRawCand date(
    _target: Target,
    _commonRecType: CRT
  ): RawCand date = {
    new RawCand date w h D scoverTw terCand date {
      overr de val target = _target
      overr de val commonRecType = _commonRecType
    }
  }

  pr vate def getEl g bleCandsForTarget(
    target: Target
  ): Future[Opt on[Seq[RawCand date]]] = {
    val addressBookFat gue =
      TargetPred cates
        .pushRecTypeFat guePred cate(
          CRT.AddressBookUploadPush,
          FS.Fat gueForOnboard ngPus s,
          FS.MaxOnboard ngPush n nterval,
          stats)(Seq(target)).map(_. ad)
    val completeOnboard ngFat gue =
      TargetPred cates
        .pushRecTypeFat guePred cate(
          CRT.CompleteOnboard ngPush,
          FS.Fat gueForOnboard ngPus s,
          FS.MaxOnboard ngPush n nterval,
          stats)(Seq(target)).map(_. ad)

    Future
      .jo n(
        target.appPerm ss ons,
        addressBookFat gue,
        completeOnboard ngFat gue
      ).map {
        case (appPerm ss onOpt, addressBookPred cate, completeOnboard ngPred cate) =>
          val addressBookUploaded =
            PushAppPerm ss onUt l.hasTargetUploadedAddressBook(appPerm ss onOpt)
          val abUploadCand date =
             f (!addressBookUploaded && addressBookPred cate && target.params(
                FS.EnableAddressBookPush)) {
              addressBookCandNum. ncr()
              So (generateOnboard ngPushRawCand date(target, CRT.AddressBookUploadPush))
            } else  f (!addressBookUploaded && (completeOnboard ngPred cate ||
              target.params(FS.D sableOnboard ngPushFat gue)) && target.params(
                FS.EnableCompleteOnboard ngPush)) {
              completeOnboard ngCandNum. ncr()
              So (generateOnboard ngPushRawCand date(target, CRT.CompleteOnboard ngPush))
            } else None

          val allCand dates =
            Seq(abUploadCand date).f lter(_. sDef ned).flatten
           f (allCand dates.nonEmpty) So (allCand dates) else None
      }
  }

  overr de def get( nputTarget: Target): Future[Opt on[Seq[RawCand date]]] = {
    requestNum. ncr()
    val m nDurat onForMRElapsed =
      D scoverTw terPred cate
        .m nDurat onElapsedS nceLastMrPushPred cate(
          na ,
          FS.MrM nDurat onS ncePushForOnboard ngPus s,
          stats)(Seq( nputTarget)).map(_. ad)
    m nDurat onForMRElapsed.flatMap { m nDurat onElapsed =>
       f (m nDurat onElapsed) getEl g bleCandsForTarget( nputTarget) else Future.None
    }
  }

  overr de def  sCand dateS ceAva lable(target: Target): Future[Boolean] = {
    PushDev ceUt l
      . sRecom ndat onsEl g ble(target).map(_ && target.params(FS.EnableOnboard ngPus s))
  }
}
