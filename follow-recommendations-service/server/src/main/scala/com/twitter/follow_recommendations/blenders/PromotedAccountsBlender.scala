package com.tw ter.follow_recom ndat ons.blenders

 mport com.google.common.annotat ons.V s bleForTest ng
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.Transform
 mport com.tw ter.follow_recom ndat ons.common.models.Ad tadata
 mport com.tw ter.follow_recom ndat ons.common.models.Recom ndat on
 mport com.tw ter. nject.Logg ng
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class PromotedAccountsBlender @ nject() (statsRece ver: StatsRece ver)
    extends Transform[ nt, Recom ndat on]
    w h Logg ng {

   mport PromotedAccountsBlender._
  val stats = statsRece ver.scope(Na )
  val  nputOrgan cAccounts = stats.counter( nputOrgan c)
  val  nputPromotedAccounts = stats.counter( nputPromoted)
  val outputOrgan cAccounts = stats.counter(OutputOrgan c)
  val outputPromotedAccounts = stats.counter(OutputPromoted)
  val promotedAccountsStats = stats.scope(NumPromotedAccounts)

  overr de def transform(
    maxResults:  nt,
     ems: Seq[Recom ndat on]
  ): St ch[Seq[Recom ndat on]] = {
    val (promoted, organ c) =  ems.part  on(_. sPromotedAccount)
    val promoted ds = promoted.map(_. d).toSet
    val dedupedOrgan c = organ c.f lterNot(u => promoted ds.conta ns(u. d))
    val blended = blendPromotedAccount(dedupedOrgan c, promoted, maxResults)
    val (outputPromoted, outputOrgan c) = blended.part  on(_. sPromotedAccount)
     nputOrgan cAccounts. ncr(dedupedOrgan c.s ze)
     nputPromotedAccounts. ncr(promoted.s ze)
    outputOrgan cAccounts. ncr(outputOrgan c.s ze)
    val s ze = outputPromoted.s ze
    outputPromotedAccounts. ncr(s ze)
     f (s ze <= 5) {
      promotedAccountsStats.counter(outputPromoted.s ze.toStr ng). ncr()
    } else {
      promotedAccountsStats.counter(MoreThan5Promoted). ncr()
    }
    St ch.value(blended)
  }

  /**
   *  rge Promoted results and organ c results. Promoted result d ctates t  pos  on
   *  n t   rge l st.
   *
   *  rge a l st of pos  oned users, aka. promoted, and a l st of organ c
   * users.  T  pos  oned promoted users are pre-sorted w h regards to t  r
   * pos  on ascend ngly.  Only requ re nt about pos  on  s to be w h n t 
   * range,  .e, can not exceed t  comb ned length  f  rge  s successful, ok
   * to be at t  last pos  on, but not beyond.
   * For more deta led descr pt on of locat on pos  on:
   * http://confluence.local.tw ter.com/d splay/ADS/Promoted+T ets+ n+T  l ne+Des gn+Docu nt
   */
  @V s bleForTest ng
  pr vate[blenders] def  rgePromotedAccounts(
    organ cUsers: Seq[Recom ndat on],
    promotedUsers: Seq[Recom ndat on]
  ): Seq[Recom ndat on] = {
    def  rgeAccountW h ndex(
      organ cUsers: Seq[Recom ndat on],
      promotedUsers: Seq[Recom ndat on],
       ndex:  nt
    ): Stream[Recom ndat on] = {
       f (promotedUsers. sEmpty) organ cUsers.toStream
      else {
        val promoted ad = promotedUsers. ad
        val promotedTa l = promotedUsers.ta l
        promoted ad.ad tadata match {
          case So (Ad tadata(pos  on, _)) =>
             f (pos  on < 0)  rgeAccountW h ndex(organ cUsers, promotedTa l,  ndex)
            else  f (pos  on ==  ndex)
              promoted ad #::  rgeAccountW h ndex(organ cUsers, promotedTa l,  ndex)
            else  f (organ cUsers. sEmpty) organ cUsers.toStream
            else {
              val organ c ad = organ cUsers. ad
              val organ cTa l = organ cUsers.ta l
              organ c ad #::  rgeAccountW h ndex(organ cTa l, promotedUsers,  ndex + 1)
            }
          case _ =>
            logger.error("Unknown Cand date type  n  rgePromotedAccounts")
            Stream.empty
        }
      }
    }

     rgeAccountW h ndex(organ cUsers, promotedUsers, 0)
  }

  pr vate[t ] def blendPromotedAccount(
    organ c: Seq[Recom ndat on],
    promoted: Seq[Recom ndat on],
    maxResults:  nt
  ): Seq[Recom ndat on] = {

    val  rged =  rgePromotedAccounts(organ c, promoted)
    val  rgedServed =  rged.take(maxResults)
    val promotedServed = promoted. ntersect( rgedServed)

     f ( sBlendPromotedNeeded(
         rgedServed.s ze - promotedServed.s ze,
        promotedServed.s ze,
        maxResults
      )) {
       rgedServed
    } else {
      organ c.take(maxResults)
    }
  }

  @V s bleForTest ng
  pr vate[blenders] def  sBlendPromotedNeeded(
    organ cS ze:  nt,
    promotedS ze:  nt,
    maxResults:  nt
  ): Boolean = {
    (organ cS ze > 1) &&
    (promotedS ze > 0) &&
    (promotedS ze < organ cS ze) &&
    (promotedS ze <= 2) &&
    (maxResults > 1)
  }
}

object PromotedAccountsBlender {
  val Na  = "promoted_accounts_blender"
  val  nputOrgan c = " nput_organ c_accounts"
  val  nputPromoted = " nput_promoted_accounts"
  val OutputOrgan c = "output_organ c_accounts"
  val OutputPromoted = "output_promoted_accounts"
  val NumPromotedAccounts = "num_promoted_accounts"
  val MoreThan5Promoted = "more_than_5"
}
