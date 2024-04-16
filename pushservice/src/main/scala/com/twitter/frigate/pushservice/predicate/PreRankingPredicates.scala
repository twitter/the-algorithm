package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Soc alContextAct ons
 mport com.tw ter.fr gate.common.base.Soc alContextUserDeta ls
 mport com.tw ter.fr gate.common.base.Target nfo
 mport com.tw ter.fr gate.common.base.TargetUser
 mport com.tw ter.fr gate.common.base.T etAuthor
 mport com.tw ter.fr gate.common.base.T etAuthorDeta ls
 mport com.tw ter.fr gate.common.base.T etCand date
 mport com.tw ter.fr gate.common.base.T etDeta ls
 mport com.tw ter.fr gate.common.cand date.Fr gate tory
 mport com.tw ter.fr gate.common.cand date.TargetABDec der
 mport com.tw ter.fr gate.common.cand date.T et mpress on tory
 mport com.tw ter.fr gate.common.pred cate.soc alcontext.{Pred cates => Soc alContextPred cates, _}
 mport com.tw ter.fr gate.common.pred cate.t et._
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.pred cate.ntab_caret_fat gue.NtabCaretCl ckContFnFat guePred cate
 mport com.tw ter. rm .pred cate.Na dPred cate

class PreRank ngPred catesBu lder(
)(
   mpl c  statsRece ver: StatsRece ver) {

  pr vate val Soc alProofPred cates = L st[Na dPred cate[PushCand date]](
    Soc alContextPred cates
      .author nSoc alContext()
      .applyOnlyToT etAuthorW hSoc alContextAct ons
      .w hNa ("author_soc al_context"),
    Soc alContextPred cates
      .self nSoc alContext[TargetUser, Soc alContextAct ons w h Target nfo[TargetUser]]()
      .applyOnlyToSoc alContextAct onsW hTargetUser
      .w hNa ("self_soc al_context"),
    Soc alContextPred cates
      .dupl cateSoc alContext[Soc alContextAct ons]()
      .applyOnlyToSoc alContextAct ons
      .w hNa ("dupl cate_soc al_context"),
    Soc alContextPred cates
      .soc alContextProtected[Soc alContextUserDeta ls]()
      .applyOnlyToSoc alContextUserDeta ls
      .w hNa ("soc al_context_protected"),
    Soc alContextPred cates
      .soc alContextUnsu able[Soc alContextUserDeta ls]()
      .applyOnlyToSoc alContextUserDeta ls
      .w hNa ("soc al_context_unsu able"),
    Soc alContextPred cates
      .soc alContextBl nk[Soc alContextUserDeta ls]()
      .applyOnlyToSoc alContextUserDeta ls
      .w hNa ("soc al_context_bl nk")
  )

  pr vate val CommonPred cates = L st[Na dPred cate[PushCand date]](
    Pred catesForCand date.cand dateEnabledForEma lPred cate(),
    Pred catesForCand date.openAppExper  ntUserCand dateAllowL st(statsRece ver)
  )

  pr vate val T etPred cates = L st[Na dPred cate[PushCand date]](
    Pred catesForCand date.t etCand dateW hLessThan2Soc alContexts sAReply.applyOnlyToT etCand datesW hSoc alContextAct ons
      .w hNa ("t et_cand date_w h_less_than_2_soc al_contexts_ s_not_a_reply"),
    Pred catesForCand date.f lterOONCand datePred cate(),
    Pred catesForCand date.oldT etRecsPred cate.applyOnlyToT etCand dateW hTargetAndABDec derAndMaxT etAge
      .w hNa ("old_t et"),
    Dupl catePushT etPred cate
      .apply[
        TargetUser w h Fr gate tory,
        T etCand date w h Target nfo[TargetUser w h Fr gate tory]
      ]
      .applyOnlyToT etCand dateW hTargetAndFr gate tory
      .w hNa ("dupl cate_push_t et"),
    Dupl cateEma lT etPred cate
      .apply[
        TargetUser w h Fr gate tory,
        T etCand date w h Target nfo[TargetUser w h Fr gate tory]
      ]
      .applyOnlyToT etCand dateW hTargetAndFr gate tory
      .w hNa ("dupl cate_ema l_t et"),
    T etAuthorPred cates
      .recT etAuthorUnsu able[T etCand date w h T etAuthorDeta ls]
      .applyOnlyToT etCand dateW hT etAuthorDeta ls
      .w hNa ("t et_author_unsu able"),
    T etObjectEx stsPred cate[
      T etCand date w h T etDeta ls
    ].applyOnlyToT etCand datesW hT etDeta ls
      .w hNa ("t et_object_ex sts"),
    T et mpress onPred cate[
      TargetUser w h T et mpress on tory,
      T etCand date w h Target nfo[TargetUser w h T et mpress on tory]
    ].applyOnlyToT etCand dateW hTargetAndT et mpress on tory
      .w hStats(statsRece ver.scope("t et_ mpress on"))
      .w hNa ("t et_ mpress on"),
    SelfT etPred cate[
      TargetUser,
      T etAuthor w h Target nfo[TargetUser]]().applyOnlyToT etAuthorW hTarget nfo
      .w hNa ("self_author"),
    Pred catesForCand date.t et sNotAreply.applyOnlyToT etCand dateW houtSoc alContextW hT etDeta ls
      .w hNa ("t et_cand date_not_a_reply"),
    Pred catesForCand date.f1Cand date sNotAReply.applyOnlyToF1Cand dateW hTargetAndABDec der
      .w hNa ("f1_cand date_ s_not_a_reply"),
    Pred catesForCand date.outOfNetworkT etCand date sNotAReply.applyOnlyToOutOfNetworkT etCand dateW hTargetAndABDec der
      .w hNa ("out_of_network_t et_cand date_ s_not_a_reply"),
    Pred catesForCand date.outOfNetworkT etCand dateEnabledCrTag.applyOnlyToOutOfNetworkT etCand dateW hTargetAndABDec der
      .w hNa ("out_of_network_t et_cand date_enabled_crtag"),
    Pred catesForCand date.outOfNetworkT etCand dateEnabledCrtGroup.applyOnlyToOutOfNetworkT etCand dateW hTargetAndABDec der
      .w hNa ("out_of_network_t et_cand date_enabled_crt_group"),
    OutOfNetworkCand datesQual yPred cates
      .oonT etLengthBasedPrerank ngPred cate(characterBased = true)
      .applyOnlyToOutOfNetworkT etCand dateW hTargetAndABDec der
      .w hNa ("oon_t et_char_length_too_short"),
    OutOfNetworkCand datesQual yPred cates
      .oonT etLengthBasedPrerank ngPred cate(characterBased = false)
      .applyOnlyToOutOfNetworkT etCand dateW hTargetAndABDec der
      .w hNa ("oon_t et_word_length_too_short"),
    Pred catesForCand date
      .protectedT etF1ExemptPred cate[
        TargetUser w h TargetABDec der,
        T etCand date w h T etAuthorDeta ls w h Target nfo[
          TargetUser w h TargetABDec der
        ]
      ]
      .applyOnlyToT etCand dateW hAuthorDeta lsW hTargetABDec der
      .w hNa ("f1_exempt_t et_author_protected"),
  )

  pr vate val SgsPreRank ngPred cates = L st[Na dPred cate[PushCand date]](
    SGSPred catesForCand date.authorBe ngFollo d.applyOnlyToAuthorBe ngFollowPred cates
      .w hNa ("author_not_be ng_follo d"),
    SGSPred catesForCand date.authorNotBe ngDev ceFollo d.applyOnlyToBas cT etPred cates
      .w hNa ("author_be ng_dev ce_follo d"),
    SGSPred catesForCand date.recom ndedT etAuthorAcceptableToTargetUser.applyOnlyToBas cT etPred cates
      .w hNa ("recom nded_t et_author_not_acceptable_to_target_user"),
    SGSPred catesForCand date.d sable nNetworkT etPred cate.applyOnlyToBas cT etPred cates
      .w hNa ("enable_ n_network_t et"),
    SGSPred catesForCand date.d sableOutNetworkT etPred cate.applyOnlyToBas cT etPred cates
      .w hNa ("enable_out_network_t et")
  )

  pr vate val SeeLessOftenPred cates = L st[Na dPred cate[PushCand date]](
    NtabCaretCl ckContFnFat guePred cate
      .ntabCaretCl ckContFnFat guePred cates(
      )
      .w hNa ("seelessoften_cont_fn_fat gue")
  )

  f nal def bu ld(): L st[Na dPred cate[PushCand date]] = {
    T etPred cates ++
      CommonPred cates ++
      Soc alProofPred cates ++
      SgsPreRank ngPred cates ++
      SeeLessOftenPred cates
  }
}

object PreRank ngPred cates {
  def apply(
    statsRece ver: StatsRece ver
  ): L st[Na dPred cate[PushCand date]] =
    new PreRank ngPred catesBu lder()(statsRece ver).bu ld()
}
