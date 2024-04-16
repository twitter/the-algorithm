package com.tw ter.fr gate.pushserv ce.take.pred cates

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.pushserv ce.conf g.Conf g
 mport com.tw ter.fr gate.pushserv ce.pred cate.Bqml althModelPred cates
 mport com.tw ter.fr gate.pushserv ce.pred cate.BqmlQual yModelPred cates
 mport com.tw ter.fr gate.pushserv ce.pred cate. althPred cates
 mport com.tw ter.fr gate.pushserv ce.pred cate.OONSpreadControlPred cate
 mport com.tw ter.fr gate.pushserv ce.pred cate.OONT etNegat veFeedbackBasedPred cate
 mport com.tw ter.fr gate.pushserv ce.pred cate.OutOfNetworkCand datesQual yPred cates
 mport com.tw ter.fr gate.pushserv ce.pred cate.Pred catesForCand date
 mport com.tw ter.fr gate.pushserv ce.pred cate.PNegMult modalPred cates
 mport com.tw ter.fr gate.pushserv ce.pred cate.TargetEngage ntPred cate
 mport com.tw ter.fr gate.pushserv ce.pred cate.T etEngage ntRat oPred cate
 mport com.tw ter.fr gate.pushserv ce.pred cate.T etLanguagePred cate
 mport com.tw ter.fr gate.pushserv ce.pred cate.T etW h ldContentPred cate

tra  Bas cT etPred cates {

  def conf g: Conf g

   mpl c  def statsRece ver: StatsRece ver

  f nal lazy val bas cT etPred cates =
    L st(
       althPred cates.sens  ve d aCategoryPred cate(),
       althPred cates.profan yPred cate(),
      Pred catesForCand date.d sableOutNetworkT etPred cate(conf g.edgeStore),
      T etEngage ntRat oPred cate.QTtoNtabCl ckBasedPred cate(),
      T etLanguagePred cate.oonT eetLanguageMatch(),
       althPred cates.user althS gnalsPred cate(conf g.user althS gnalStore),
       althPred cates.authorSens  ve d aPred cate(conf g.producer d aRepresentat onStore),
       althPred cates.authorProf leBasedPred cate(),
      PNegMult modalPred cates. althS gnalScorePNegMult modalPred cate(
        conf g.t et althScoreStore),
      Bqml althModelPred cates. althModelOonPred cate(
        conf g.f lter ngModelScorer,
        conf g.producer d aRepresentat onStore,
        conf g.user althS gnalStore,
        conf g.t et althScoreStore),
      BqmlQual yModelPred cates.BqmlQual yModelOonPred cate(conf g.f lter ngModelScorer),
       althPred cates.t et althS gnalScorePred cate(conf g.t et althScoreStore),
       althPred cates
        .t et althS gnalScorePred cate(conf g.t et althScoreStore, applyToQuoteT et = true),
      Pred catesForCand date.nullCastF1ProtectedExper entPred cate(
        conf g.cac dT etyP eStoreV2
      ),
      OONT etNegat veFeedbackBasedPred cate.ntabD sl keBasedPred cate(),
      OONSpreadControlPred cate.oonT etSpreadControlPred cate(),
      OONSpreadControlPred cate.oonAuthorSpreadControlPred cate(),
       althPred cates. althS gnalScoreMult l ngualPnsfwT etTextPred cate(
        conf g.t et althScoreStore),
      Pred catesForCand date
        .recom ndedT etAuthorAcceptableToTargetUser(conf g.edgeStore),
       althPred cates. althS gnalScorePnsfwT etTextPred cate(conf g.t et althScoreStore),
       althPred cates. althS gnalScoreSpam T etPred cate(conf g.t et althScoreStore),
      OutOfNetworkCand datesQual yPred cates.Negat veKeywordsPred cate(
        conf g.postRank ngFeatureStoreCl ent),
      Pred catesForCand date.authorNotBe ngDev ceFollo d(conf g.edgeStore),
      T etW h ldContentPred cate(),
      Pred catesForCand date.noOptoutFreeForm nterestPred cate,
      Pred catesForCand date.d sable nNetworkT etPred cate(conf g.edgeStore),
      T etEngage ntRat oPred cate.T etReplyL keRat oPred cate(),
      TargetEngage ntPred cate(
        conf g.userT etPerspect veStore,
        defaultForM ss ng = true
      ),
    )
}

/**
 * T  tra   s a new vers on of Bas cT etPred cates
 * D fference from old vers on  s that bas cT etPred cates are d fferent
 * bas cT etPred cates  re don't  nclude Soc al Graph Serv ce related pred cates
 */
tra  Bas cT etPred catesW houtSGSPred cates {

  def conf g: Conf g

   mpl c  def statsRece ver: StatsRece ver

  f nal lazy val bas cT etPred cates = {
    L st(
       althPred cates. althS gnalScoreSpam T etPred cate(conf g.t et althScoreStore),
      Pred catesForCand date.nullCastF1ProtectedExper entPred cate(
        conf g.cac dT etyP eStoreV2
      ),
      T etW h ldContentPred cate(),
      TargetEngage ntPred cate(
        conf g.userT etPerspect veStore,
        defaultForM ss ng = true
      ),
      Pred catesForCand date.noOptoutFreeForm nterestPred cate,
       althPred cates.user althS gnalsPred cate(conf g.user althS gnalStore),
       althPred cates.t et althS gnalScorePred cate(conf g.t et althScoreStore),
      BqmlQual yModelPred cates.BqmlQual yModelOonPred cate(conf g.f lter ngModelScorer),
      Bqml althModelPred cates. althModelOonPred cate(
        conf g.f lter ngModelScorer,
        conf g.producer d aRepresentat onStore,
        conf g.user althS gnalStore,
        conf g.t et althScoreStore),
    )
  }
}
