package com.tw ter.follow_recom ndat ons.common.pred cates.g zmoduck

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.dec der.RandomRec p ent
 mport com.tw ter.esc rb rd.ut l.st chcac .St chCac 
 mport com.tw ter.f nagle. mcac d.Cl ent
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.ut l.DefaultT  r
 mport com.tw ter.follow_recom ndat ons.common.base.StatsUt l
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cate
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cateResult
 mport com.tw ter.follow_recom ndat ons.common.cl ents.cac . mcac Cl ent
 mport com.tw ter.follow_recom ndat ons.common.cl ents.cac .Thr ftB ject on
 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason._
 mport com.tw ter.follow_recom ndat ons.common.models.AddressBook tadata
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason
 mport com.tw ter.follow_recom ndat ons.common.pred cates.g zmoduck.G zmoduckPred cate._
 mport com.tw ter.follow_recom ndat ons.common.pred cates.g zmoduck.G zmoduckPred cateParams._
 mport com.tw ter.follow_recom ndat ons.conf gap .dec ders.Dec derKey
 mport com.tw ter.g zmoduck.thr ftscala.LabelValue.Bl nkBad
 mport com.tw ter.g zmoduck.thr ftscala.LabelValue.Bl nkWorst
 mport com.tw ter.g zmoduck.thr ftscala.LabelValue
 mport com.tw ter.g zmoduck.thr ftscala.LookupContext
 mport com.tw ter.g zmoduck.thr ftscala.QueryF elds
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.g zmoduck.thr ftscala.UserResult
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.scrooge.CompactThr ftSer al zer
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.g zmoduck.G zmoduck
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.logg ng.Logg ng
 mport java.lang.{Long => JLong}
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 *  n t  f lter,   want to c ck 4 categor es of cond  ons:
 *   -  f cand date  s d scoverable g ven that  's from an address-book/phone-book based s ce
 *   -  f cand date  s unsu able based on  's safety sub-f elds  n g zmoduck
 *   -  f cand date  s w h ld because of country-spec f c take-down pol c es
 *   -  f cand date  s marked as bad/worst based on bl nk labels
 *   fa l close on t  query as t   s a product-cr  cal f lter
 */
@S ngleton
case class G zmoduckPred cate @ nject() (
  g zmoduck: G zmoduck,
  cl ent: Cl ent,
  statsRece ver: StatsRece ver,
  dec der: Dec der = Dec der.False)
    extends Pred cate[(HasCl entContext w h HasParams, Cand dateUser)]
    w h Logg ng {
  pr vate val stats: StatsRece ver = statsRece ver.scope(t .getClass.getNa )

  // track # of G zmoduck pred cate quer es that y elded val d &  nval d pred cate results
  pr vate val val dPred cateResultCounter = stats.counter("pred cate_val d")
  pr vate val  nval dPred cateResultCounter = stats.counter("pred cate_ nval d")

  // track # of cases w re no G zmoduck user was found
  pr vate val noG zmoduckUserCounter = stats.counter("no_g zmoduck_user_found")

  pr vate val g zmoduckCac  = St chCac [JLong, UserResult](
    maxCac S ze = MaxCac S ze,
    ttl = Cac TTL,
    statsRece ver = stats.scope("cac "),
    underly ngCall = getByUser d
  )

  // D str buted T mcac  to store UserResult objects keyed on user  Ds
  val b ject on = new Thr ftB ject on[UserResult] {
    overr de val ser al zer = CompactThr ftSer al zer(UserResult)
  }
  val  mcac Cl ent =  mcac Cl ent[UserResult](
    cl ent = cl ent,
    dest = "/s/cac /frs:t mcac s",
    valueB ject on = b ject on,
    ttl = Cac TTL,
    statsRece ver = stats.scope("t mcac ")
  )

  // ma n  thod used to apply G zmoduckPred cate to a cand date user
  overr de def apply(
    pa r: (HasCl entContext w h HasParams, Cand dateUser)
  ): St ch[Pred cateResult] = {
    val (request, cand date) = pa r
    //  asure t  latency of t  getG zmoduckPred cateResult, s nce t  pred cate
    // c ck  s product-cr  cal and rel es on query ng a core serv ce (G zmoduck)
    StatsUt l.prof leSt ch(
      getG zmoduckPred cateResult(request, cand date),
      stats.scope("getG zmoduckPred cateResult")
    )
  }

  pr vate def getG zmoduckPred cateResult(
    request: HasCl entContext w h HasParams,
    cand date: Cand dateUser
  ): St ch[Pred cateResult] = {
    val t  out: Durat on = request.params(G zmoduckGetT  out)

    val dec derKey: Str ng = Dec derKey.EnableG zmoduckCach ng.toStr ng
    val enableD str butedCach ng: Boolean = dec der. sAva lable(dec derKey, So (RandomRec p ent))

    // try gett ng an ex st ng UserResult from cac   f poss ble
    val userResultSt ch: St ch[UserResult] = 
      enableD str butedCach ng match {
        // read from  mcac 
        case true =>  mcac Cl ent.readThrough(
          // add a key pref x to address cac  key coll s ons
          key = "G zmoduckPred cate" + cand date. d.toStr ng,
          underly ngCall = () => getByUser d(cand date. d)
        )
        // read from local cac 
        case false => g zmoduckCac .readThrough(cand date. d)
      }

    val pred cateResultSt ch = userResultSt ch.map {
      userResult => {
        val pred cateResult = getPred cateResult(request, cand date, userResult)
         f (enableD str butedCach ng) {
          pred cateResult match {
            case Pred cateResult.Val d => 
              stats.scope("t mcac ").counter("pred cate_val d"). ncr()
            case Pred cateResult. nval d(reasons) => 
              stats.scope("t mcac ").counter("pred cate_ nval d"). ncr()
          }
          // log  tr cs to c ck  f local cac  value matc s d str buted cac  value  
          logPred cateResultEqual y(
            request,
            cand date,
            pred cateResult
          )
        } else {
          pred cateResult match {
            case Pred cateResult.Val d => 
              stats.scope("cac ").counter("pred cate_val d"). ncr()
            case Pred cateResult. nval d(reasons) => 
              stats.scope("cac ").counter("pred cate_ nval d"). ncr()
          }
        }
        pred cateResult
      }
    }
    pred cateResultSt ch
      .w h n(t  out)(DefaultT  r)
      .rescue { // fa l-open w n t  out or except on
        case e: Except on =>
          stats.scope("rescued").counter(e.getClass.getS mpleNa ). ncr()
           nval dPred cateResultCounter. ncr()
          St ch(Pred cateResult. nval d(Set(Fa lOpen)))
      }
  }

  pr vate def logPred cateResultEqual y(
    request: HasCl entContext w h HasParams,
    cand date: Cand dateUser,
    pred cateResult: Pred cateResult
  ): Un  = {
    val localCac dUserResult = Opt on(g zmoduckCac .cac .get fPresent(cand date. d))
     f (localCac dUserResult. sDef ned) {
      val localPred cateResult = getPred cateResult(request, cand date, localCac dUserResult.get)
      localPred cateResult.equals(pred cateResult) match {
        case true => stats.scope("has_equal_pred cate_value").counter("true"). ncr()
        case false => stats.scope("has_equal_pred cate_value").counter("false"). ncr()
      }
    } else {
      stats.scope("has_equal_pred cate_value").counter("undef ned"). ncr()
    }
  }

  //  thod to get Pred cateResult from UserResult
  def getPred cateResult(
    request: HasCl entContext w h HasParams,
    cand date: Cand dateUser,
    userResult: UserResult,
  ): Pred cateResult = {
    userResult.user match {
      case So (user) =>
        val abPbReasons = getAbPbReason(user, cand date.getAddressBook tadata)
        val safetyReasons = getSafetyReasons(user)
        val countryTakedownReasons = getCountryTakedownReasons(user, request.getCountryCode)
        val bl nkReasons = getBl nkReasons(user)
        val allReasons =
          abPbReasons ++ safetyReasons ++ countryTakedownReasons ++ bl nkReasons
         f (allReasons.nonEmpty) {
           nval dPred cateResultCounter. ncr()
          Pred cateResult. nval d(allReasons)
        } else {
          val dPred cateResultCounter. ncr()
          Pred cateResult.Val d
        }
      case None =>
        noG zmoduckUserCounter. ncr()
         nval dPred cateResultCounter. ncr()
        Pred cateResult. nval d(Set(NoUser))
    }
  }

  pr vate def getByUser d(user d: JLong): St ch[UserResult] = {
    StatsUt l.prof leSt ch(
      g zmoduck.getBy d(user d = user d, queryF elds = queryF elds, context = lookupContext),
      stats.scope("getByUser d")
    )
  }
}

object G zmoduckPred cate {

  pr vate[g zmoduck] val lookupContext: LookupContext =
    LookupContext(` ncludeDeact vated` = true, `safetyLevel` = So (SafetyLevel.Recom ndat ons))

  pr vate[g zmoduck] val queryF elds: Set[QueryF elds] =
    Set(
      QueryF elds.D scoverab l y, // needed for Address Book / Phone Book d scoverab l y c cks  n getAbPbReason
      QueryF elds.Safety, // needed for user state safety c cks  n getSafetyReasons, getCountryTakedownReasons
      QueryF elds.Labels, // needed for user label c cks  n getBl nkReasons
      QueryF elds.Takedowns // needed for c ck ng takedown labels for a user  n getCountryTakedownReasons
    )

  pr vate[g zmoduck] val Bl nkLabels: Set[LabelValue] = Set(Bl nkBad, Bl nkWorst)

  pr vate[g zmoduck] def getAbPbReason(
    user: User,
    ab tadataOpt: Opt on[AddressBook tadata]
  ): Set[F lterReason] = {
    (for {
      d scoverab l y <- user.d scoverab l y
      ab tadata <- ab tadataOpt
    } y eld {
      val AddressBook tadata(fwdPb, rvPb, fwdAb, rvAb) = ab tadata
      val abReason: Set[F lterReason] =
         f ((!d scoverab l y.d scoverableByEma l) && (fwdAb || rvAb))
          Set(AddressBookUnd scoverable)
        else Set.empty
      val pbReason: Set[F lterReason] =
         f ((!d scoverab l y.d scoverableByMob lePhone) && (fwdPb || rvPb))
          Set(PhoneBookUnd scoverable)
        else Set.empty
      abReason ++ pbReason
    }).getOrElse(Set.empty)
  }

  pr vate[g zmoduck] def getSafetyReasons(user: User): Set[F lterReason] = {
    user.safety
      .map { s =>
        val deact vatedReason: Set[F lterReason] =
           f (s.deact vated) Set(Deact vated) else Set.empty
        val suspendedReason: Set[F lterReason] =  f (s.suspended) Set(Suspended) else Set.empty
        val restr ctedReason: Set[F lterReason] =  f (s.restr cted) Set(Restr cted) else Set.empty
        val nsfwUserReason: Set[F lterReason] =  f (s.nsfwUser) Set(NsfwUser) else Set.empty
        val nsfwAdm nReason: Set[F lterReason] =  f (s.nsfwAdm n) Set(NsfwAdm n) else Set.empty
        val  sProtectedReason: Set[F lterReason] =  f (s. sProtected) Set( sProtected) else Set.empty
        deact vatedReason ++ suspendedReason ++ restr ctedReason ++ nsfwUserReason ++ nsfwAdm nReason ++  sProtectedReason
      }.getOrElse(Set.empty)
  }

  pr vate[g zmoduck] def getCountryTakedownReasons(
    user: User,
    countryCodeOpt: Opt on[Str ng]
  ): Set[F lterReason] = {
    (for {
      safety <- user.safety.toSeq
       f safety.hasTakedown
      takedowns <- user.takedowns.toSeq
      takedownCountry <- takedowns.countryCodes
      request ngCountry <- countryCodeOpt
       f takedownCountry.toLo rCase == request ngCountry.toLo rCase
    } y eld Set(CountryTakedown(takedownCountry.toLo rCase))).flatten.toSet
  }

  pr vate[g zmoduck] def getBl nkReasons(user: User): Set[F lterReason] = {
    user.labels
      .map(_.labels.map(_.labelValue))
      .getOrElse(N l)
      .ex sts(Bl nkLabels.conta ns)
    for {
      labels <- user.labels.toSeq
      label <- labels.labels
       f (Bl nkLabels.conta ns(label.labelValue))
    } y eld Set(Bl nk)
  }.flatten.toSet
}
