package com.tw ter.follow_recom ndat ons.ut ls

 mport com.tw ter.follow_recom ndat ons.common.base.Recom ndat onFlow
 mport com.tw ter.follow_recom ndat ons.common.base.S deEffectsUt l
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.st ch.St ch

tra  Recom ndat onFlowBaseS deEffectsUt l[Target <: HasCl entContext, Cand date <: Cand dateUser]
    extends S deEffectsUt l[Target, Cand date] {
  recom ndat onFlow: Recom ndat onFlow[Target, Cand date] =>

  overr de def applyS deEffects(
    target: Target,
    cand dateS ces: Seq[Cand dateS ce[Target, Cand date]],
    cand datesFromCand dateS ces: Seq[Cand date],
     rgedCand dates: Seq[Cand date],
    f lteredCand dates: Seq[Cand date],
    rankedCand dates: Seq[Cand date],
    transfor dCand dates: Seq[Cand date],
    truncatedCand dates: Seq[Cand date],
    results: Seq[Cand date]
  ): St ch[Un ] = {
    St ch.async(
      St ch.collect(
        Seq(
          applyS deEffectsCand dateS ceCand dates(
            target,
            cand dateS ces,
            cand datesFromCand dateS ces),
          applyS deEffects rgedCand dates(target,  rgedCand dates),
          applyS deEffectsF lteredCand dates(target, f lteredCand dates),
          applyS deEffectsRankedCand dates(target, rankedCand dates),
          applyS deEffectsTransfor dCand dates(target, transfor dCand dates),
          applyS deEffectsTruncatedCand dates(target, truncatedCand dates),
          applyS deEffectsResults(target, results)
        )
      ))
  }

  /*
   n subclasses, overr de funct ons below to apply custom s de effects at each step  n p pel ne.
  Call super.applyS deEffectsXYZ to scr be bas c scr bes  mple nted  n t  parent class
   */
  def applyS deEffectsCand dateS ceCand dates(
    target: Target,
    cand dateS ces: Seq[Cand dateS ce[Target, Cand date]],
    cand datesFromCand dateS ces: Seq[Cand date]
  ): St ch[Un ] = {
    val cand datesGroupedByCand dateS ces =
      cand datesFromCand dateS ces.groupBy(
        _.getPr maryCand dateS ce.getOrElse(Cand dateS ce dent f er("NoCand dateS ce")))

    target.getOpt onalUser d match {
      case So (user d) =>
        val userAgeOpt = Snowflake d.t  From dOpt(user d).map(_.unt lNow. nDays)
        userAgeOpt match {
          case So (userAge)  f userAge <= 30 =>
            cand dateS ces.map { cand dateS ce =>
              {
                val cand dateS ceStats = statsRece ver.scope(cand dateS ce. dent f er.na )

                val  sEmpty =
                  !cand datesGroupedByCand dateS ces.keySet.conta ns(cand dateS ce. dent f er)

                 f (userAge <= 1)
                  cand dateS ceStats
                    .scope("user_age", "1", "empty").counter( sEmpty.toStr ng). ncr()
                 f (userAge <= 7)
                  cand dateS ceStats
                    .scope("user_age", "7", "empty").counter( sEmpty.toStr ng). ncr()
                 f (userAge <= 30)
                  cand dateS ceStats
                    .scope("user_age", "30", "empty").counter( sEmpty.toStr ng). ncr()
              }
            }
          case _ => N l
        }
      case None => N l
    }
    St ch.Un 
  }

  def applyS deEffectsBaseCand dates(
    target: Target,
    cand dates: Seq[Cand date]
  ): St ch[Un ] = St ch.Un 

  def applyS deEffects rgedCand dates(
    target: Target,
    cand dates: Seq[Cand date]
  ): St ch[Un ] = applyS deEffectsBaseCand dates(target, cand dates)

  def applyS deEffectsF lteredCand dates(
    target: Target,
    cand dates: Seq[Cand date]
  ): St ch[Un ] = applyS deEffectsBaseCand dates(target, cand dates)

  def applyS deEffectsRankedCand dates(
    target: Target,
    cand dates: Seq[Cand date]
  ): St ch[Un ] = applyS deEffectsBaseCand dates(target, cand dates)

  def applyS deEffectsTransfor dCand dates(
    target: Target,
    cand dates: Seq[Cand date]
  ): St ch[Un ] = applyS deEffectsBaseCand dates(target, cand dates)

  def applyS deEffectsTruncatedCand dates(
    target: Target,
    cand dates: Seq[Cand date]
  ): St ch[Un ] = applyS deEffectsBaseCand dates(target, cand dates)

  def applyS deEffectsResults(
    target: Target,
    cand dates: Seq[Cand date]
  ): St ch[Un ] = applyS deEffectsBaseCand dates(target, cand dates)
}
