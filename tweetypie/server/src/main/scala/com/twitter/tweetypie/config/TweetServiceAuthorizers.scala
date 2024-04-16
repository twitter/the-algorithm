package com.tw ter.t etyp e
package conf g

 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.transport.S2STransport
 mport com.tw ter.servo.gate.RateL m  ngGate
 mport com.tw ter.servo.request.Cl entRequestAuthor zer.Unauthor zedExcept on
 mport com.tw ter.servo.request.{Cl entRequestAuthor zer, Cl entRequestObserver}
 mport com.tw ter.t etyp e.cl ent_ d.Cl ent d lper
 mport com.tw ter.t etyp e.cl ent_ d.PreferForwardedServ ce dent f erForStrato
 mport com.tw ter.t etyp e.core.RateL m ed
 mport com.tw ter.t etyp e.serv ce. thodAuthor zer
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.ut l.Future

/**
 * Compose a Cl entRequestAuthor zer for
 * Cl entHandl ngT etServ ce
 */
object Cl entHandl ngT etServ ceAuthor zer {
  pr vate val RateL m Exceeded =
    RateL m ed("Y  Cl ent d has exceeded t  rate l m  for non-allowL sted cl ents.")

  def apply(
    sett ngs: T etServ ceSett ngs,
    dynam cConf g: Dynam cConf g,
    statsRece ver: StatsRece ver,
    getServ ce dent f er: () => Serv ce dent f er = S2STransport.peerServ ce dent f er _
  ): Cl entRequestAuthor zer = {
    val author zer =
       f (sett ngs.allowl st ngRequ red) {
        val l m  ngGate = RateL m  ngGate.un form(sett ngs.nonAllowL stedCl entRateL m PerSec)
        allowL stedOrRateL m edAuthor zer(dynam cConf g, l m  ngGate)
          .andT n(rejectNonAllowL stedProdAuthor zer(dynam cConf g))
          .andT n(perm ted thodsAuthor zer(dynam cConf g))
          .andT n(allowProduct onAuthor zer(sett ngs.allowProduct onCl ents))
      } else {
        Cl entRequestAuthor zer.w hCl ent d
      }

    val alternat veCl ent d lper = new Cl ent d lper(PreferForwardedServ ce dent f erForStrato)
    // pass t  author zer  nto an observed author zer for stats track ng.
    // (observed author zers can't be composed w h andT n)
    Cl entRequestAuthor zer.observed(
      author zer,
      new Cl entRequestObserver(statsRece ver) {
        overr de def apply(
           thodNa : Str ng,
          cl ent dScopesOpt: Opt on[Seq[Str ng]]
        ): Future[Un ] = {
          // Mon or for t  m grat on tak ng  nto account forwarded serv ce  dent f er
          // as effect ve cl ent  D for strato.
          val alternat veCl ent dScopes = alternat veCl ent d lper.effect veCl ent d.map(Seq(_))
           f (cl ent dScopesOpt != alternat veCl ent dScopes) {
            scopedRece ver.scope( thodNa )
              .scope("before_m grat on")
              .scope(cl ent dScopesOpt.getOrElse(Seq(Cl ent d lper.UnknownCl ent d)): _*)
              .scope("after_m grat on")
              .counter(alternat veCl ent dScopes.getOrElse(Seq(Cl ent d lper.UnknownCl ent d)): _*)
              . ncr()
          } else {
             scopedRece ver.scope( thodNa ).counter("m grat on_ nd fferent"). ncr()
          }
          super.apply( thodNa , cl ent dScopesOpt)
        }

        overr de def author zed( thodNa : Str ng, cl ent dStr: Str ng): Un  = {
          // Mon or for t  m grat on of us ng serv ce  dent f er
          // as  dent y  nstead of cl ent  D.
          val serv ce dent f er = getServ ce dent f er()
          scopedRece ver.counter(
            "author zed_request",
            cl ent dStr,
            serv ce dent f er.role,
            serv ce dent f er.serv ce,
            serv ce dent f er.env ron nt
          ). ncr()
          val status = dynam cConf g.byServ ce dent f er(serv ce dent f er).toSeq match {
            case Seq() => "none"
            case Seq(cl ent)  f cl ent.cl ent d == cl ent dStr => "equal"
            case Seq(_) => "ot r"
            case _ => "amb guous"
          }
          scopedRece ver.counter(
            "serv ce_ d_match_cl ent_ d",
            cl ent dStr,
            serv ce dent f er.role,
            serv ce dent f er.serv ce,
            serv ce dent f er.env ron nt,
            status
          ). ncr()
        }
      }
    )
  }

  /**
   * @return A Cl entRequestAuthor zer that allows unl m ed requests for allowl sted cl ent  ds and
   * rate-l m ed requests for unknown cl ents.
   */
  def allowL stedOrRateL m edAuthor zer(
    dynam cConf g: Dynam cConf g,
    nonAllowL stedL m er: Gate[Un ]
  ): Cl entRequestAuthor zer =
    Cl entRequestAuthor zer.f ltered(
      { (_, cl ent d) =>
        dynam cConf g. sAllowL stedCl ent(cl ent d) || nonAllowL stedL m er()
      },
      RateL m Exceeded)

  /**
   * @return A Cl entRequestAuthor zer that rejects requests from non-allowL sted prod cl ents.
   */
  def rejectNonAllowL stedProdAuthor zer(dynam cConf g: Dynam cConf g): Cl entRequestAuthor zer = {
    object Unallowl stedExcept on
        extends Unauthor zedExcept on(
          "Traff c  s only allo d from allow-l sted *.prod cl ents." +
            " Please create a t cket to reg ster y  cl ent d to enable product on traff c us ng http://go/tp-new-cl ent."
        )

    def  sProdCl ent(cl ent d: Str ng): Boolean =
      cl ent d.endsW h(".prod") || cl ent d.endsW h(".product on")

    Cl entRequestAuthor zer.f ltered(
      { (_, cl ent d) =>
        ! sProdCl ent(cl ent d) || dynam cConf g. sAllowL stedCl ent(cl ent d)
      },
      Unallowl stedExcept on)
  }

  /**
   * @return A Cl entRequestAuthor zer that c cks  f a g ven cl ent's
   * perm ted thods f eld  ncludes t   thod t y are call ng
   */
  def perm ted thodsAuthor zer(dynam cConf g: Dynam cConf g): Cl entRequestAuthor zer =
    dynam cConf g.cl entsByFullyQual f ed d match {
      case So (cl entsBy d) => perm ted thodsAuthor zer(dynam cConf g, cl entsBy d)
      case None => Cl entRequestAuthor zer.perm ss ve
    }

  pr vate def perm ted thodsAuthor zer(
    dynam cConf g: Dynam cConf g,
    cl entsByFullyQual f ed d: Map[Str ng, Cl ent]
  ): Cl entRequestAuthor zer = {
    Cl entRequestAuthor zer.f ltered { ( thodNa , cl ent d) =>
      dynam cConf g.unprotectedEndpo nts( thodNa ) ||
      (cl entsByFullyQual f ed d.get(cl ent d) match {
        case So (cl ent) =>
          cl ent.accessAll thods ||
            cl ent.perm ted thods.conta ns( thodNa )
        case None =>
          false //  f cl ent  d  s unknown, don't allow access
      })
    }
  }

  /**
   * @return A Cl entRequestAuthor zer that fa ls t 
   * request  f    s com ng from a product on cl ent
   * and allowProduct onCl ents  s false
   */
  def allowProduct onAuthor zer(allowProduct onCl ents: Boolean): Cl entRequestAuthor zer =
    Cl entRequestAuthor zer.f ltered { (_, cl ent d) =>
      allowProduct onCl ents || !(cl ent d.endsW h(".prod") || cl ent d.endsW h(".product on"))
    }
}

/**
 * Compose a  thodAuthor zer for t  `getT ets` endpo nt.
 */
object GetT etsAuthor zer {
   mport ProtectedT etsAuthor zer. ncludeProtected

  def apply(
    conf g: Dynam cConf g,
    maxRequestS ze:  nt,
     nstanceCount:  nt,
    enforceRateL m edCl ents: Gate[Un ],
    maxRequestW dthEnabled: Gate[Un ],
    statsRece ver: StatsRece ver,
  ):  thodAuthor zer[GetT etsRequest] =
     thodAuthor zer.all(
      Seq(
        ProtectedT etsAuthor zer(conf g.cl entsByFullyQual f ed d)
          .contramap[GetT etsRequest] { r =>
             ncludeProtected(r.opt ons.ex sts(_.bypassV s b l yF lter ng))
          },
        RequestS zeAuthor zer(maxRequestS ze, maxRequestW dthEnabled)
          .contramap[GetT etsRequest](_.t et ds.s ze),
        RateL m erAuthor zer(conf g,  nstanceCount, enforceRateL m edCl ents, statsRece ver)
          .contramap[GetT etsRequest](_.t et ds.s ze)
      )
    )
}

/**
 * Compose a  thodAuthor zer for t  `getT etF elds` endpo nt.
 */
object GetT etF eldsAuthor zer {
   mport ProtectedT etsAuthor zer. ncludeProtected

  def apply(
    conf g: Dynam cConf g,
    maxRequestS ze:  nt,
     nstanceCount:  nt,
    enforceRateL m edCl ents: Gate[Un ],
    maxRequestW dthEnabled: Gate[Un ],
    statsRece ver: StatsRece ver
  ):  thodAuthor zer[GetT etF eldsRequest] =
     thodAuthor zer.all(
      Seq(
        ProtectedT etsAuthor zer(conf g.cl entsByFullyQual f ed d)
          .contramap[GetT etF eldsRequest](r =>
             ncludeProtected(r.opt ons.v s b l yPol cy == T etV s b l yPol cy.NoF lter ng)),
        RequestS zeAuthor zer(maxRequestS ze, maxRequestW dthEnabled)
          .contramap[GetT etF eldsRequest](_.t et ds.s ze),
        RateL m erAuthor zer(conf g,  nstanceCount, enforceRateL m edCl ents, statsRece ver)
          .contramap[GetT etF eldsRequest](_.t et ds.s ze)
      )
    )
}

object ProtectedT etsAuthor zer {
  case class  ncludeProtected( nclude: Boolean) extends AnyVal

  class BypassV s b l yF lter ngNotAuthor zedExcept on( ssage: Str ng)
      extends Unauthor zedExcept on( ssage)

  def apply(optCl entsBy d: Opt on[Map[Str ng, Cl ent]]):  thodAuthor zer[ ncludeProtected] = {
    optCl entsBy d match {
      case So (cl entsByFullyQual f ed d) =>
        val cl entsW hBypassV s b l yF lter ng = cl entsByFullyQual f ed d.f lter {
          case (_, cl ent) => cl ent.bypassV s b l yF lter ng
        }
        apply(cl ent d => cl entsW hBypassV s b l yF lter ng.conta ns(cl ent d))

      case None =>
        apply((_: Str ng) => true)
    }
  }

  /**
   * A  thodAuthor zer that fa ls t  request  f a cl ent requests to bypass v s b l y
   * f lter ng but doesn't have BypassV s b l yF lter ng
   */
  def apply(protectedT etsAllowl st: Str ng => Boolean):  thodAuthor zer[ ncludeProtected] =
     thodAuthor zer { ( ncludeProtected, cl ent d) =>
      // T re  s only one unauthor zed case, a cl ent request ng
      // protected t ets w n t y are not  n t  allowl st
      Future.w n( ncludeProtected. nclude && !protectedT etsAllowl st(cl ent d)) {
        Future.except on(
          new BypassV s b l yF lter ngNotAuthor zedExcept on(
            s"$cl ent d  s not author zed to bypass v s b l y f lter ng"
          )
        )
      }
    }
}

/**
 * A  thodAuthor zer[ nt] that fa ls large requests.
 */
object RequestS zeAuthor zer {
  class ExceededMaxW dthExcept on( ssage: Str ng) extends Unauthor zedExcept on( ssage)

  def apply(
    maxRequestS ze:  nt,
    maxW dthL m Enabled: Gate[Un ] = Gate.False
  ):  thodAuthor zer[ nt] =
     thodAuthor zer { (requestS ze, cl ent d) =>
      Future.w n(requestS ze > maxRequestS ze && maxW dthL m Enabled()) {
        Future.except on(
          new ExceededMaxW dthExcept on(
            s"$requestS ze exceeds bulk request s ze l m . $cl ent d can request at most $maxRequestS ze  ems per request"
          )
        )
      }
    }
}

object RateL m erAuthor zer {

  type Cl ent d = Str ng

  /**
   * @return cl ent  D to   ghted RateL m  ngGate map
   *
   *   want to rate-l m  based on requests per sec for every  nstance.
   * W n   allowl st new cl ents to T etyp e,   ass gn t ets per sec quota.
   * That's why,   compute per nstanceQuota [1] and create a   ghted rate-l m  ng gate [2]
   * wh ch returns true  f acqu r ng requestS ze number of perm s  s successful. [3]
   *
   * [1] tps quota dur ng allowl st ng  s for both DCs and  nstanceCount  s for one DC.
   * T refore,   are over-compensat ng per nstanceQuota for all low-pr or y cl ents.
   * t  w ll act a fudge-factor to account for cluster-w de traff c  mbalances.
   *
   * val per nstanceQuota : Double = math.max(1.0, math.ce l(tpsL m .toFloat /  nstanceCount))
   *
   *   have so  cl ents l ke deferredRPC w h 0K tps quota and rate l m er expects > 0 perm s.
   *
   * [2]  f a cl ent has mult ple env ron nts - stag ng, devel, prod.   prov s on t 
   * sa  rate-l m s for all envs  nstead of d str but ng t  tps quota across envs.
   *
   * Example:
   *
   * val c = Cl ent(..., l m  = 10k, ...)
   * Map("foo.prod" -> c, "foo.stag ng" -> c, "foo.devel" -> c)
   *
   * Above cl ent conf g turns  nto 3 separate RateL m  ngGate.  ghted(), each w h 10k
   *
   * [3] RateL m  ngGate w ll always g ve perm  to t   n  al request that exceeds
   * t  l m . ex: start ng w h rate-l m  of 1 tps per  nstance. f rst request w h
   * 100 batch s ze  s allo d.
   *
   * RateL m FudgeFactor  s a mult pl er for per- nstance quota to account for:
   *
   * a) H gh l kel hood of concurrent batc s h t ng t  sa  t etyp e shard due to
   * non-un form load d str but on (t  can be allev ated by us ng Determ n st c Aperture)
   * b) Cl ents w h no retry backoffs and custom batch ng/concurrency.
   *
   *   are add ng default st ch batch s ze to per  nstance quota, to g ve more  adroom for low-tps cl ents.
   * https://cg .tw ter.b z/s ce/tree/st ch/st ch-t etyp e/src/ma n/scala/com/tw ter/st ch/t etyp e/T etyP e.scala#n47
   *
   */
  case class RateL m erConf g(l m  ngGate: Gate[ nt], enforceRateL m : Boolean)

  def perCl entRateL m ers(
    dynam cConf g: Dynam cConf g,
     nstanceCount:  nt
  ): Map[Cl ent d, RateL m erConf g] = {
    val RateL m FudgeFactor: Double = 1.5
    val DefaultSt chBatchS ze: Double = 25.0
    dynam cConf g.cl entsByFullyQual f ed d match {
      case So (cl ents) =>
        cl ents.collect {
          case (cl ent d, cl ent)  f cl ent.tpsL m . sDef ned =>
            val per nstanceQuota: Double =
              math.max(
                1.0,
                math.ce l(
                  cl ent.tpsL m .get.toFloat /  nstanceCount)) * RateL m FudgeFactor + DefaultSt chBatchS ze
            cl ent d -> RateL m erConf g(
              RateL m  ngGate.  ghted(per nstanceQuota),
              cl ent.enforceRateL m 
            )
        }
      case None => Map.empty
    }
  }

  /*
    enforce rate-l m  ng on get_t ets and get_t et_f elds requests
    g ven enable_rate_l m ed_cl ents dec der  s true and rate l m  ng gate
     s not g v ng any more perm s.
   */
  def apply(
    conf g: Dynam cConf g,
    l m ers: Map[Cl ent d, RateL m erConf g],
     nstanceCount:  nt,
    enforceRateL m edCl ents: Gate[Un ],
    statsRece ver: StatsRece ver
  ):  thodAuthor zer[ nt] = {

    val tpsExceededScope = statsRece ver.scope("tps_exceeded")
    val tpsRejectedScope = statsRece ver.scope("tps_rejected")
    val qpsExceededScope = statsRece ver.scope("qps_exceeded")
    val qpsRejectedScope = statsRece ver.scope("qps_rejected")

     thodAuthor zer { (requestS ze, cl ent d) =>
      val pos  veRequestS ze = math.max(1, requestS ze)
      val shouldRateL m : Boolean = l m ers.get(cl ent d).ex sts { conf g =>
        val exceededL m  = !conf g.l m  ngGate(pos  veRequestS ze)
         f (exceededL m ) {
          qpsExceededScope.counter(cl ent d). ncr()
          tpsExceededScope.counter(cl ent d). ncr(pos  veRequestS ze)
        }
        exceededL m  && conf g.enforceRateL m 
      }

      Future.w n(shouldRateL m  && enforceRateL m edCl ents()) {
        qpsRejectedScope.counter(cl ent d). ncr()
        tpsRejectedScope.counter(cl ent d). ncr(pos  veRequestS ze)
        Future.except on(
          RateL m ed(s"Y  cl ent  D $cl ent d has exceeded  s reserved tps quota.")
        )
      }
    }
  }

  def apply(
    conf g: Dynam cConf g,
     nstanceCount:  nt,
    enforceRateL m edCl ents: Gate[Un ],
    statsRece ver: StatsRece ver
  ):  thodAuthor zer[ nt] = {
    val l m ers = perCl entRateL m ers(conf g,  nstanceCount)
    apply(conf g, l m ers,  nstanceCount, enforceRateL m edCl ents, statsRece ver)
  }
}
