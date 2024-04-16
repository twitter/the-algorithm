package com.tw ter.fr gate.pushserv ce.take

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.logger.MRLogger
 mport com.tw ter.fr gate.common.ntab. nval dNTABWr eRequestExcept on
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.not f cat onserv ce.thr ftscala._
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Future
 mport scala.ut l.control.NoStackTrace

class NtabCopy dNotFoundExcept on(pr vate val  ssage: Str ng)
    extends Except on( ssage)
    w h NoStackTrace

class  nval dNtabCopy dExcept on(pr vate val  ssage: Str ng)
    extends Except on( ssage)
    w h NoStackTrace

object Not f cat onServ ceSender {

  def generateSoc alContextTextEnt  es(
    ntabD splayNa sAnd dsFut: Future[Seq[(Str ng, Long)]],
    ot rCountFut: Future[ nt]
  ): Future[Seq[D splayTextEnt y]] = {
    Future.jo n(ntabD splayNa sAnd dsFut, ot rCountFut).map {
      case (na sW h d nOrder, ot rCount) =>
        val d splays = na sW h d nOrder.z pW h ndex.map {
          case ((na ,  d),  ndex) =>
            D splayTextEnt y(
              na  = "user" + s"${ ndex + 1}",
              value = TextValue.Text(na ),
              emphas s = true,
              user d = So ( d)
            )
        } ++ Seq(
          D splayTextEnt y(na  = "na Count", value = TextValue.Number(na sW h d nOrder.s ze))
        )

        val ot rD splay =  f (ot rCount > 0) {
          So (
            D splayTextEnt y(
              na  = "ot rCount",
              value = TextValue.Number(ot rCount)
            )
          )
        } else None
        d splays ++ ot rD splay
    }
  }

  def getD splayTextEnt yFromUser(
    userOpt: Opt on[User],
    f eldNa : Str ng,
     sBold: Boolean
  ): Opt on[D splayTextEnt y] = {
    for {
      user <- userOpt
      prof le <- user.prof le
    } y eld {
      D splayTextEnt y(
        na  = f eldNa ,
        value = TextValue.Text(prof le.na ),
        emphas s =  sBold,
        user d = So (user. d)
      )
    }
  }

  def getD splayTextEnt yFromUser(
    user: Future[Opt on[User]],
    f eldNa : Str ng,
     sBold: Boolean
  ): Future[Opt on[D splayTextEnt y]] = {
    user.map { getD splayTextEnt yFromUser(_, f eldNa ,  sBold) }
  }
}

case class Not f cat onServ ceRequest(
  cand date: PushCand date,
   mpress on d: Str ng,
   sBadgeUpdate: Boolean,
  overr de d: Opt on[Str ng] = None)

class Not f cat onServ ceSender(
  send: (Target, CreateGener cNot f cat onRequest) => Future[CreateGener cNot f cat onResponse],
  enableWr esParam: Param[Boolean],
  enableForEmployeesParam: Param[Boolean],
  enableForEveryoneParam: Param[Boolean]
)(
   mpl c  globalStats: StatsRece ver)
    extends ReadableStore[Not f cat onServ ceRequest, CreateGener cNot f cat onResponse] {

  val log = MRLogger(t .getClass.getNa )

  val stats = globalStats.scope("Not f cat onServ ceSender")
  val requestEmpty = stats.scope("request_empty")
  val requestNonEmpty = stats.counter("request_non_empty")

  val requestBadgeCount = stats.counter("request_badge_count")

  val successfulWr e = stats.counter("successful_wr e")
  val successfulWr eScope = stats.scope("successful_wr e")
  val fa ledWr eScope = stats.scope("fa led_wr e")
  val gotNonSuccessResponse = stats.counter("got_non_success_response")
  val gotEmptyResponse = stats.counter("got_empty_response")
  val dec derTurnedOffResponse = stats.scope("dec der_turned_off_response")

  val d sabledByDec derForCand date = stats.scope("model/cand date").counter("d sabled_by_dec der")
  val sentToAlphaUserForCand date =
    stats.scope("model/cand date").counter("send_to_employee_or_team")
  val sentToNonBucketedUserForCand date =
    stats.scope("model/cand date").counter("send_to_non_bucketed_dec dered_user")
  val noSendForCand date = stats.scope("model/cand date").counter("no_send")

  val  nel g bleUsersForCand date = stats.scope("model/cand date").counter(" nel g ble_users")

  val darkWr eRequestsForCand date = stats.scope("model/cand date").counter("dark_wr e_traff c")

  val  avyUserForCand dateCounter = stats.scope("model/cand date").counter("target_ avy")
  val non avyUserForCand dateCounter = stats.scope("model/cand date").counter("target_non_ avy")

  val sk pWr  ngToNTAB = stats.counter("sk p_wr  ng_to_ntab")

  val ntabWr eD sabledForCand date = stats.scope("model/cand date").counter("ntab_wr e_d sabled")

  val ntabOverr deEnabledForCand date = stats.scope("model/cand date").counter("overr de_enabled")
  val ntabTTLForCand date = stats.scope("model/cand date").counter("ttl_enabled")

  overr de def get(
    not fRequest: Not f cat onServ ceRequest
  ): Future[Opt on[CreateGener cNot f cat onResponse]] = {
    not fRequest.cand date.target.dev ce nfo.flatMap { dev ce nfoOpt =>
      val d sableWr  ngToNtab =
        not fRequest.cand date.target.params(PushParams.D sableWr  ngToNTAB)

       f (d sableWr  ngToNtab) {
        sk pWr  ngToNTAB. ncr()
        Future.None
      } else {
         f (not fRequest.overr de d.nonEmpty) { ntabOverr deEnabledForCand date. ncr() }
        Future
          .jo n(
            not fRequest.cand date.ntabRequest,
            ntabWr esEnabledForCand date(not fRequest.cand date)).flatMap {
            case (So (ntabRequest), ntabWr esEnabled)  f ntabWr esEnabled =>
               f (ntabRequest.exp ryT  M ll s.nonEmpty) { ntabTTLForCand date. ncr() }
              sendNTabRequest(
                ntabRequest,
                not fRequest.cand date.target,
                not fRequest. sBadgeUpdate,
                not fRequest.cand date.commonRecType,
                 sFromCand date = true,
                overr de d = not fRequest.overr de d
              )
            case (So (_), ntabWr esEnabled)  f !ntabWr esEnabled =>
              ntabWr eD sabledForCand date. ncr()
              Future.None
            case (None, ntabWr esEnabled) =>
               f (!ntabWr esEnabled) ntabWr eD sabledForCand date. ncr()
              requestEmpty.counter(s"cand date_${not fRequest.cand date.commonRecType}"). ncr()
              Future.None
          }
      }
    }
  }

  pr vate def sendNTabRequest(
    gener cNot f cat onRequest: CreateGener cNot f cat onRequest,
    target: Target,
     sBadgeUpdate: Boolean,
    crt: CommonRecom ndat onType,
     sFromCand date: Boolean,
    overr de d: Opt on[Str ng]
  ): Future[Opt on[CreateGener cNot f cat onResponse]] = {
    requestNonEmpty. ncr()
    val not fSvcReq =
      gener cNot f cat onRequest.copy(
        sendBadgeCountUpdate =  sBadgeUpdate,
        overr de d = overr de d
      )
    requestBadgeCount. ncr()
    send(target, not fSvcReq)
      .map { response =>
         f (response.responseType.equals(CreateGener cNot f cat onResponseType.Dec deredOff)) {
          dec derTurnedOffResponse.counter(s"$crt"). ncr()
          dec derTurnedOffResponse.counter(s"${gener cNot f cat onRequest.gener cType}"). ncr()
          throw  nval dNTABWr eRequestExcept on("Dec der  s turned off")
        } else {
          So (response)
        }
      }
      .onFa lure { ex =>
        stats.counter(s"error_${ex.getClass.getCanon calNa }"). ncr()
        fa ledWr eScope.counter(s"${crt}"). ncr()
        log
          .error(
            ex,
            s"NTAB fa lure $not fSvcReq"
          )
      }
      .onSuccess {
        case So (response) =>
          successfulWr e. ncr()
          val successfulWr eScopeStr ng =  f ( sFromCand date) "model/cand date" else "envelope"
          successfulWr eScope.scope(successfulWr eScopeStr ng).counter(s"$crt"). ncr()
           f (response.responseType != CreateGener cNot f cat onResponseType.Success) {
            gotNonSuccessResponse. ncr()
            log.warn ng(s"NTAB dropped $not fSvcReq w h response $response")
          }

        case _ =>
          gotEmptyResponse. ncr()
      }
  }

  pr vate def ntabWr esEnabledForCand date(cand: PushCand date): Future[Boolean] = {
     f (!cand.target.params(enableWr esParam)) {
      d sabledByDec derForCand date. ncr()
      Future.False
    } else {
      Future
        .jo n(
          cand.target. sAnEmployee,
          cand.target. s nNot f cat onsServ ceWh el st,
          cand.target. sTeam mber
        )
        .flatMap {
          case ( sEmployee,  s nNot f cat onsServ ceWh el st,  sTeam mber) =>
            cand.target.dev ce nfo.flatMap { dev ce nfoOpt =>
              dev ce nfoOpt
                .map { dev ce nfo =>
                  cand.target. s avyUserState.map {  s avyUser =>
                    val  sAlphaTester = ( sEmployee && cand.target
                      .params(enableForEmployeesParam)) ||  s nNot f cat onsServ ceWh el st ||  sTeam mber
                     f (cand.target. sDarkWr e) {
                      stats
                        .scope("model/cand date").counter(
                          s"dark_wr e_${cand.commonRecType}"). ncr()
                      darkWr eRequestsForCand date. ncr()
                      false
                    } else  f ( sAlphaTester || dev ce nfo. sMR nNTabEl g ble
                      || cand.target. nsertMag crecs ntoNTabForNonPushableUsers) {
                       f ( s avyUser)  avyUserForCand dateCounter. ncr()
                      else non avyUserForCand dateCounter. ncr()

                      val enabledForDes redUsers = cand.target.params(enableForEveryoneParam)
                       f ( sAlphaTester) {
                        sentToAlphaUserForCand date. ncr()
                        true
                      } else  f (enabledForDes redUsers) {
                        sentToNonBucketedUserForCand date. ncr()
                        true
                      } else {
                        noSendForCand date. ncr()
                        false
                      }
                    } else {
                       nel g bleUsersForCand date. ncr()
                      false
                    }
                  }
                }.getOrElse(Future.False)
            }
        }
    }
  }
}
