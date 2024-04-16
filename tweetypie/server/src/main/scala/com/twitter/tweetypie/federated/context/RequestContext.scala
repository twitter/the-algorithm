package com.tw ter.t etyp e
package federated.context

 mport com.tw ter.common. p_address_ut ls.Cl ent pAddressUt ls
 mport com.tw ter.context.thr ftscala.V e r
 mport com.tw ter.context.Tw terContext
 mport com.tw ter.f nagle.core.ut l. netAddressUt l
 mport com.tw ter.passb rd.b f eld.cl entpr v leges.thr ftscala.{Constants => Cl entAppPr v leges}
 mport com.tw ter.f natra.tfe.Http aderNa s
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport com.tw ter.strato.access.Access.Cl entAppl cat onPr v lege
 mport com.tw ter.strato.access.Access
 mport com.tw ter.strato.access.Cl entAppl cat onPr v legeVar ant
 mport com.tw ter.strato.context.StratoContext
 mport com.tw ter.strato.opcontext.OpContext
 mport com.tw ter.strato.response.Err
 mport com.tw ter. averb rd.common.GetPlatformKey

/**
 * [[RequestContext]] ex sts to avo d w r ng t  federated column
 *  mple ntat ons d rectly to t  request data that  s der ved from t 
 * contextual env ron nt. Columns should not d rectly reference
 * Tw terContext, StratoContext, strato.access.Access, HTTP  aders, etc.
 * Each column operat on operates on two  nput para ters: a request ( .e.
 * a column operat on's Arg) and a [[RequestContext]].
 */
pr vate[federated] case class RequestContext(
  cl entAppl cat on d: Opt on[App d] = None,
  dev ceS ce: Opt on[Str ng] = None,
  knownDev ceToken: Opt on[KnownDev ceToken] = None,
  remoteHost: Opt on[Str ng] = None,
  tw terUser d: User d,
  contr butor d: Opt on[User d] = None,
   sDarkRequest: Boolean = false,
  hasPr v legeNullcast ngAccess: Boolean = false,
  hasPr v legePromotedT ets nT  l ne: Boolean = false,
  sess onHash: Opt on[Str ng] = None,
  cardsPlatformKey: Opt on[Str ng] = None,
  safetyLevel: Opt on[SafetyLevel] = None,
) {
  def  sContr butorRequest = contr butor d.ex sts(_ != tw terUser d)
}

/**
 * Prov des a s ngle place to der ve request data from t  contextual
 * env ron nt. Def ned as a sealed class (vs an object) to allow mock ng
 *  n un  tests.
 */
pr vate[federated] sealed class GetRequestContext() {
  // Br ng T etyp e perm ted Tw terContext  nto scope
  pr vate[t ] val Tw terContext: Tw terContext =
    com.tw ter.context.Tw terContext(com.tw ter.t etyp e.Tw terContextPerm )

  /**
   * W n Tw terUser dNotDef ned  s thrown,  's l kely that t  column
   * access control conf gurat on lacks `AllowTw terUser d` or ot r
   * Pol cy that ensures t  caller  s aut nt cated.
   */
  pr vate[federated] val Tw terUser dNotDef ned =
    Err(Err.Aut nt cat on, "User aut nt cat on  s requ red for t  operat on.")

  pr vate[t ] val Sess onHash aderNa  = "x-tfe-sess on-hash"
  pr vate[t ] def hasCl entAppl cat onPr v lege( d:  nt): Boolean =
    Access.getPr nc pals.conta ns(
      Cl entAppl cat onPr v lege(
        Cl entAppl cat onPr v legeVar ant
          .by d( d.toShort).get))

  pr vate[t ] def getRequest ader( aderNa : Str ng): Opt on[Str ng] =
    StratoContext
      .current()
      .propagated aders
      .flatMap(_.get( aderNa ))

  def apply(opContext: OpContext): RequestContext = {
    val tw terUser d = Access.getTw terUser d match {
      // Access.getTw terUser d should return a value as long as t  column
      // pol cy  ncludes AllowTw terUser d, wh ch guarantees t  presence of
      // t  value.
      case So (tw terUser) => tw terUser. d
      case None => throw Tw terUser dNotDef ned
    }

    // contr butor d should only be def ned w n t  aut nt cated user d ffers
    // from t  "Tw ter user"
    val contr butor d =
      Access.getAut nt catedTw terUser d.map(_. d).f lter(_ != tw terUser d)

    val tw terContext = Tw terContext().getOrElse(V e r())

    val dev ceS ce = tw terContext.cl entAppl cat on d.map("oauth:" + _)

    // Ported from StatusesUpdateController#getB rd rdOpt ons and
    // B rd rdOpt on.User p(request.cl entHost)
    val remoteHost: Opt on[Str ng] =
      getRequest ader(Http aderNa s.X_TW TTER_AUD T_ P_THR FT.toLo rCase) // use t  new  ader
        .flatMap(Cl ent pAddressUt ls.decodeCl ent pAddress(_))
        .flatMap(Cl ent pAddressUt ls.getStr ng(_))
        .orElse(
          getRequest ader(
            Http aderNa s.X_TW TTER_AUD T_ P.toLo rCase
          ) // fallback to old way before m grat on  s completed
            .map(h =>  netAddressUt l.getByNa (h.tr m).getHostAddress)
        )

    val  sDarkRequest = opContext.darkRequest. sDef ned

    val sess onHash = getRequest ader(Sess onHash aderNa )

    val cardsPlatformKey = tw terContext.cl entAppl cat on d.map(GetPlatformKey(_))

    val safetyLevel = opContext.safetyLevel

    RequestContext(
      cl entAppl cat on d = tw terContext.cl entAppl cat on d,
      dev ceS ce = dev ceS ce,
      knownDev ceToken = tw terContext.knownDev ceToken,
      remoteHost = remoteHost,
      tw terUser d = tw terUser d,
      contr butor d = contr butor d,
       sDarkRequest =  sDarkRequest,
      hasPr v legeNullcast ngAccess =
        hasCl entAppl cat onPr v lege(Cl entAppPr v leges.NULLCAST NG_ACCESS),
      hasPr v legePromotedT ets nT  l ne =
        hasCl entAppl cat onPr v lege(Cl entAppPr v leges.PROMOTED_TWEETS_ N_T MEL NE),
      sess onHash = sess onHash,
      cardsPlatformKey = cardsPlatformKey,
      safetyLevel = safetyLevel,
    )
  }
}
