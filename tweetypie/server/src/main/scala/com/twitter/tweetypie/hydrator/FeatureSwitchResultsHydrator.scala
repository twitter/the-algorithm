package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.context.thr ftscala.V e r
 mport com.tw ter.featuresw c s.FSRec p ent
 mport com.tw ter.featuresw c s.UserAgent
 mport com.tw ter.featuresw c s.v2.FeatureSw c s
 mport com.tw ter.f nagle.mtls.aut nt cat on.EmptyServ ce dent f er
 mport com.tw ter.strato.callcontext.CallContext
 mport com.tw ter.t etyp e.cl ent_ d.Cl ent d lper
 mport com.tw ter.t etyp e.core.ValueState

/**
 * Hydrate Feature Sw ch results  n T etData.   can do t  once at t 
 * start of t  hydrat on p pel ne so that t  rest of t  hydrators can
 * use t  Feature Sw ch values.
 */
object FeatureSw chResultsHydrator {

  def apply(
    featureSw c sW houtExper  nts: FeatureSw c s,
    cl ent d lper: Cl ent d lper
  ): T etDataValueHydrator = ValueHydrator.map { (td, opts) =>
    val v e r = Tw terContext().getOrElse(V e r())
    val rec p ent =
      FSRec p ent(
        user d = v e r.user d,
        cl entAppl cat on d = v e r.cl entAppl cat on d,
        userAgent = v e r.userAgent.flatMap(UserAgent(_)),
      ).w hCustomF elds(
        "thr ft_cl ent_ d" ->
          cl ent d lper.effect veCl ent dRoot.getOrElse(Cl ent d lper.UnknownCl ent d),
        "forwarded_serv ce_ d" ->
          CallContext.forwardedServ ce dent f er
            .map(_.toStr ng).getOrElse(EmptyServ ce dent f er),
        "safety_level" -> opts.safetyLevel.toStr ng,
        "cl ent_app_ d_ s_def ned" -> v e r.cl entAppl cat on d. sDef ned.toStr ng,
      )
    val results = featureSw c sW houtExper  nts.matchRec p ent(rec p ent)
    ValueState.un (td.copy(featureSw chResults = So (results)))
  }
}
