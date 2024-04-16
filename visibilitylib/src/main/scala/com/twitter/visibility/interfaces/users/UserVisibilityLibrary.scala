package com.tw ter.v s b l y. nterfaces.users

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.servo.dec der.Dec derGateBu lder
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.v s b l y.V s b l yL brary
 mport com.tw ter.v s b l y.bu lder.users.AuthorFeatures
 mport com.tw ter.v s b l y.bu lder.users.Relat onsh pFeatures
 mport com.tw ter.v s b l y.bu lder.users.V e rAdvancedF lter ngFeatures
 mport com.tw ter.v s b l y.bu lder.users.V e rFeatures
 mport com.tw ter.v s b l y.bu lder.users.V e rSearchSafetyFeatures
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.bu lder.users.SearchFeatures
 mport com.tw ter.v s b l y.common.UserRelat onsh pS ce
 mport com.tw ter.v s b l y.common.UserSearchSafetyS ce
 mport com.tw ter.v s b l y.common.UserS ce
 mport com.tw ter.v s b l y.conf gap .conf gs.V s b l yDec derGates
 mport com.tw ter.v s b l y.context.thr ftscala.UserV s b l yF lter ngContext
 mport com.tw ter.v s b l y.models.Content d.User d
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.V e rContext
 mport com.tw ter.v s b l y.rules.Reason.Unspec f ed
 mport com.tw ter.v s b l y.rules.Allow
 mport com.tw ter.v s b l y.rules.Drop
 mport com.tw ter.v s b l y.rules.RuleBase

object UserV s b l yL brary {
  type Type =
    (User, SafetyLevel, V e rContext, UserV s b l yF lter ngContext) => St ch[V s b l yResult]

  def apply(
    v s b l yL brary: V s b l yL brary,
    userS ce: UserS ce = UserS ce.empty,
    userRelat onsh pS ce: UserRelat onsh pS ce = UserRelat onsh pS ce.empty,
    stratoCl ent: Cl ent,
    dec der: Dec der
  ): Type = {
    val l braryStatsRece ver = v s b l yL brary.statsRece ver.scope("user_l brary")
    val stratoCl entStatsRece ver = v s b l yL brary.statsRece ver.scope("strato")

    val v s b l yDec derGates = V s b l yDec derGates(dec der)

    val vfEng neCounter = l braryStatsRece ver.counter("vf_eng ne_requests")
    val noUserRulesCounter = l braryStatsRece ver.counter("no_user_rules_requests")
    val v e r sAuthorCounter = l braryStatsRece ver.counter("v e r_ s_author_requests")

    val authorFeatures = new AuthorFeatures(userS ce, l braryStatsRece ver)
    val v e rFeatures = new V e rFeatures(userS ce, l braryStatsRece ver)
    val relat onsh pFeatures =
      new Relat onsh pFeatures(userRelat onsh pS ce, l braryStatsRece ver)
    val searchFeatures = new SearchFeatures(l braryStatsRece ver)

    val v e rSafeSearchFeatures = new V e rSearchSafetyFeatures(
      UserSearchSafetyS ce.fromStrato(stratoCl ent, stratoCl entStatsRece ver),
      l braryStatsRece ver)

    val dec derGateBu lder = new Dec derGateBu lder(dec der)
    val advancedF lter ngFeatures =
      new V e rAdvancedF lter ngFeatures(userS ce, l braryStatsRece ver)

    (user, safetyLevel, v e rContext, userV s b l yF lter ngContext) => {
      val content d = User d(user. d)
      val v e r d = v e rContext.user d

       f (!RuleBase.hasUserRules(safetyLevel)) {
        noUserRulesCounter. ncr()
        St ch.value(V s b l yResult(content d = content d, verd ct = Allow))
      } else {
         f (v e r d.conta ns(user. d)) {
          v e r sAuthorCounter. ncr()

          St ch.value(V s b l yResult(content d = content d, verd ct = Allow))
        } else {
          vfEng neCounter. ncr()

          val featureMap =
            v s b l yL brary.featureMapBu lder(
              Seq(
                v e rFeatures.forV e rContext(v e rContext),
                v e rSafeSearchFeatures.forV e r d(v e r d),
                relat onsh pFeatures.forAuthor(user, v e r d),
                authorFeatures.forAuthor(user),
                advancedF lter ngFeatures.forV e r d(v e r d),
                searchFeatures.forSearchContext(userV s b l yF lter ngContext.searchContext)
              )
            )

          v s b l yL brary.runRuleEng ne(
            content d,
            featureMap,
            v e rContext,
            safetyLevel
          )

        }
      }
    }
  }

  def Const(shouldDrop: Boolean): Type =
    (user, _, _, _) =>
      St ch.value(
        V s b l yResult(
          content d = User d(user. d),
          verd ct =  f (shouldDrop) Drop(Unspec f ed) else Allow,
          f n s d = true
        )
      )
}
