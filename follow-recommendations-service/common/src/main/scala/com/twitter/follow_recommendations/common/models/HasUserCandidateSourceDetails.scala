package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter. rm .ml.models.Feature
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter. rm .model.Algor hm.Algor hm
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er

/**
 * Used to keep track of a cand date's s ce not so much as a feature but for f lter ng cand date
 * from spec f c s ces (eg. G zmoduckPred cate)
 */
tra  HasUserCand dateS ceDeta ls { cand dateUser: Cand dateUser =>
  def userCand dateS ceDeta ls: Opt on[UserCand dateS ceDeta ls]

  def getAlgor hm: Algor hm = {
    val algor hm = for {
      deta ls <- userCand dateS ceDeta ls
       dent f er <- deta ls.pr maryCand dateS ce
      algor hm <- Algor hm.w hNa Opt( dent f er.na )
    } y eld algor hm

    algor hm.getOrElse(throw new Except on("Algor hm m ss ng on cand date user!"))
  }

  def getAllAlgor hms: Seq[Algor hm] = {
    getCand dateS ces.keys
      .flatMap( dent f er => Algor hm.w hNa Opt( dent f er.na )).toSeq
  }

  def getAddressBook tadata: Opt on[AddressBook tadata] = {
    userCand dateS ceDeta ls.flatMap(_.addressBook tadata)
  }

  def getCand dateS ces: Map[Cand dateS ce dent f er, Opt on[Double]] = {
    userCand dateS ceDeta ls.map(_.cand dateS ceScores).getOrElse(Map.empty)
  }

  def getCand dateRanks: Map[Cand dateS ce dent f er,  nt] = {
    userCand dateS ceDeta ls.map(_.cand dateS ceRanks).getOrElse(Map.empty)
  }

  def getCand dateFeatures: Map[Cand dateS ce dent f er, Seq[Feature]] = {
    userCand dateS ceDeta ls.map(_.cand dateS ceFeatures).getOrElse(Map.empty)
  }

  def getPr maryCand dateS ce: Opt on[Cand dateS ce dent f er] = {
    userCand dateS ceDeta ls.flatMap(_.pr maryCand dateS ce)
  }

  def w hCand dateS ce(s ce: Cand dateS ce dent f er): Cand dateUser = {
    w hCand dateS ceAndScore(s ce, cand dateUser.score)
  }

  def w hCand dateS ceAndScore(
    s ce: Cand dateS ce dent f er,
    score: Opt on[Double]
  ): Cand dateUser = {
    w hCand dateS ceScoreAndFeatures(s ce, score, N l)
  }

  def w hCand dateS ceAndFeatures(
    s ce: Cand dateS ce dent f er,
    features: Seq[Feature]
  ): Cand dateUser = {
    w hCand dateS ceScoreAndFeatures(s ce, cand dateUser.score, features)
  }

  def w hCand dateS ceScoreAndFeatures(
    s ce: Cand dateS ce dent f er,
    score: Opt on[Double],
    features: Seq[Feature]
  ): Cand dateUser = {
    val cand dateS ceDeta ls =
      cand dateUser.userCand dateS ceDeta ls
        .map { deta ls =>
          deta ls.copy(
            pr maryCand dateS ce = So (s ce),
            cand dateS ceScores = deta ls.cand dateS ceScores + (s ce -> score),
            cand dateS ceFeatures = deta ls.cand dateS ceFeatures + (s ce -> features)
          )
        }.getOrElse(
          UserCand dateS ceDeta ls(
            So (s ce),
            Map(s ce -> score),
            Map.empty,
            None,
            Map(s ce -> features)))
    cand dateUser.copy(
      userCand dateS ceDeta ls = So (cand dateS ceDeta ls)
    )
  }

  def addCand dateS ceScoresMap(
    scoreMap: Map[Cand dateS ce dent f er, Opt on[Double]]
  ): Cand dateUser = {
    val cand dateS ceDeta ls = cand dateUser.userCand dateS ceDeta ls
      .map { deta ls =>
        deta ls.copy(cand dateS ceScores = deta ls.cand dateS ceScores ++ scoreMap)
      }.getOrElse(UserCand dateS ceDeta ls(scoreMap.keys. adOpt on, scoreMap, Map.empty, None))
    cand dateUser.copy(
      userCand dateS ceDeta ls = So (cand dateS ceDeta ls)
    )
  }

  def addCand dateS ceRanksMap(
    rankMap: Map[Cand dateS ce dent f er,  nt]
  ): Cand dateUser = {
    val cand dateS ceDeta ls = cand dateUser.userCand dateS ceDeta ls
      .map { deta ls =>
        deta ls.copy(cand dateS ceRanks = deta ls.cand dateS ceRanks ++ rankMap)
      }.getOrElse(UserCand dateS ceDeta ls(rankMap.keys. adOpt on, Map.empty, rankMap, None))
    cand dateUser.copy(
      userCand dateS ceDeta ls = So (cand dateS ceDeta ls)
    )
  }

  def add nfoPerRank ngStage(
    rank ngStage: Str ng,
    scores: Opt on[Scores],
    rank:  nt
  ): Cand dateUser = {
    val scoresOpt: Opt on[Scores] = scores.orElse(cand dateUser.scores)
    val or g nal nfoPerRank ngStage =
      cand dateUser. nfoPerRank ngStage.getOrElse(Map[Str ng, Rank ng nfo]())
    cand dateUser.copy(
       nfoPerRank ngStage =
        So (or g nal nfoPerRank ngStage + (rank ngStage -> Rank ng nfo(scoresOpt, So (rank))))
    )
  }

  def addAddressBook tadata fAva lable(
    cand dateS ces: Seq[Cand dateS ce dent f er]
  ): Cand dateUser = {

    val addressBook tadata = AddressBook tadata(
       nForwardPhoneBook =
        cand dateS ces.conta ns(AddressBook tadata.ForwardPhoneBookCand dateS ce),
       nReversePhoneBook =
        cand dateS ces.conta ns(AddressBook tadata.ReversePhoneBookCand dateS ce),
       nForwardEma lBook =
        cand dateS ces.conta ns(AddressBook tadata.ForwardEma lBookCand dateS ce),
       nReverseEma lBook =
        cand dateS ces.conta ns(AddressBook tadata.ReverseEma lBookCand dateS ce)
    )

    val newCand dateS ceDeta ls = cand dateUser.userCand dateS ceDeta ls
      .map { deta ls =>
        deta ls.copy(addressBook tadata = So (addressBook tadata))
      }.getOrElse(
        UserCand dateS ceDeta ls(
          None,
          Map.empty,
          Map.empty,
          So (addressBook tadata),
          Map.empty))

    cand dateUser.copy(
      userCand dateS ceDeta ls = So (newCand dateS ceDeta ls)
    )
  }

}
