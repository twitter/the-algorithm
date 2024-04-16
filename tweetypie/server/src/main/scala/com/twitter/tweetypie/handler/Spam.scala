package com.tw ter.t etyp e
package handler

 mport com.tw ter.botmaker.thr ftscala.BotMakerResponse
 mport com.tw ter.bouncer.thr ftscala.Bounce
 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.relevance.feature_store.thr ftscala.FeatureData
 mport com.tw ter.relevance.feature_store.thr ftscala.FeatureValue.StrValue
 mport com.tw ter.serv ce.gen.scarecrow.thr ftscala.T eredAct on
 mport com.tw ter.serv ce.gen.scarecrow.thr ftscala.T eredAct onResult
 mport com.tw ter.t etyp e.core.T etCreateFa lure
 mport com.tw ter.t etyp e.thr ftscala.T etCreateState

object Spam {
  sealed tra  Result
  case object Allow extends Result
  case object S lentFa l extends Result
  case object D sabledBy p Pol cy extends Result

  val AllowFuture: Future[Allow.type] = Future.value(Allow)
  val S lentFa lFuture: Future[S lentFa l.type] = Future.value(S lentFa l)
  val D sabledBy p Pol cyFuture: Future[D sabledBy p Pol cy.type] =
    Future.value(D sabledBy p Pol cy)

  def D sabledBy p Fa lure(
    userNa : Opt on[Str ng],
    customDeny ssage: Opt on[Str ng] = None
  ): T etCreateFa lure.State = {
    val errorMsg = (customDeny ssage, userNa ) match {
      case (So (deny ssage), _) => deny ssage
      case (_, So (na )) => s"So  act ons on t  ${na } T et have been d sabled by Tw ter."
      case _ => "So  act ons on t  T et have been d sabled by Tw ter."
    }
    T etCreateFa lure.State(T etCreateState.D sabledBy p Pol cy, So (errorMsg))
  }

  type C cker[T] = T => Future[Result]

  /**
   * Dum  spam c cker that always allows requests.
   */
  val DoNotC ckSpam: C cker[AnyRef] = _ => AllowFuture

  def gated[T](gate: Gate[Un ])(c cker: C cker[T]): C cker[T] =
    req =>  f (gate()) c cker(req) else AllowFuture

  def selected[T](gate: Gate[Un ])( fTrue: C cker[T],  fFalse: C cker[T]): C cker[T] =
    req => gate.select( fTrue,  fFalse)()(req)

  def w hEffect[T](c ck: C cker[T], effect: T => Un ): T => Future[Result] = { t: T =>
    effect(t)
    c ck(t)
  }

  /**
   * Wrapper that  mpl c ly allows ret et or t et creat on w n spam
   * c ck ng fa ls.
   */
  def allowOnExcept on[T](c cker: C cker[T]): C cker[T] =
    req =>
      c cker(req).rescue {
        case e: T etCreateFa lure => Future.except on(e)
        case _ => AllowFuture
      }

  /**
   * Handler for scarecrow result to be used by a C cker.
   */
  def handleScarecrowResult(
    stats: StatsRece ver
  )(
    handler: Part alFunct on[(T eredAct onResult, Opt on[Bounce], Opt on[Str ng]), Future[Result]]
  ): C cker[T eredAct on] =
    result => {
      stats.scope("scarecrow_result").counter(result.resultCode.na ). ncr()
      Trace.record("com.tw ter.t etyp e.Spam.scarecrow_result=" + result.resultCode.na )
      /*
       * A bot can return a custom Deny ssage
       *
       *  f   does,   subst ute t  for t  ' ssage'  n t  Val dat onError.
       */
      val customDeny ssage: Opt on[Str ng] = for {
        botMakeResponse: BotMakerResponse <- result.botMakerResponse
        outputFeatures <- botMakeResponse.outputFeatures
        deny ssageFeature: FeatureData <- outputFeatures.get("Deny ssage")
        deny ssageFeatureValue <- deny ssageFeature.featureValue
        deny ssage <- deny ssageFeatureValue match {
          case str ngValue: StrValue =>
            So (str ngValue.strValue)
          case _ =>
            None
        }
      } y eld deny ssage
      handler.applyOrElse(
        (result.resultCode, result.bounce, customDeny ssage),
        w hEffect(DoNotC ckSpam, (_: AnyRef) => stats.counter("unexpected_result"). ncr())
      )
    }
}
