package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter.follow_recom ndat ons.logg ng.{thr ftscala => offl ne}
 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}
 mport com.tw ter. rm .constants.Algor hmFeedbackTokens
 mport com.tw ter.ml.ap .thr ftscala.{DataRecord => TDataRecord}
 mport com.tw ter.ml.ap .ut l.ScalaToJavaDataRecordConvers ons
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er

tra  FollowableEnt y extends Un versalNoun[Long]

tra  Recom ndat on
    extends FollowableEnt y
    w h HasReason
    w h HasAd tadata
    w h HasTrack ngToken {
  val score: Opt on[Double]

  def toThr ft: t.Recom ndat on

  def toOffl neThr ft: offl ne.Offl neRecom ndat on
}

case class Cand dateUser(
  overr de val  d: Long,
  overr de val score: Opt on[Double] = None,
  overr de val reason: Opt on[Reason] = None,
  overr de val userCand dateS ceDeta ls: Opt on[UserCand dateS ceDeta ls] = None,
  overr de val ad tadata: Opt on[Ad tadata] = None,
  overr de val track ngToken: Opt on[Track ngToken] = None,
  overr de val dataRecord: Opt on[R chDataRecord] = None,
  overr de val scores: Opt on[Scores] = None,
  overr de val  nfoPerRank ngStage: Opt on[scala.collect on.Map[Str ng, Rank ng nfo]] = None,
  overr de val params: Params = Params. nval d,
  overr de val engage nts: Seq[Engage ntType] = N l,
  overr de val recom ndat onFlow dent f er: Opt on[Str ng] = None)
    extends Recom ndat on
    w h HasUserCand dateS ceDeta ls
    w h HasDataRecord
    w h HasScores
    w h HasParams
    w h HasEngage nts
    w h HasRecom ndat onFlow dent f er
    w h Has nfoPerRank ngStage {

  val ranker dsStr: Opt on[Seq[Str ng]] = {
    val strs = scores.map(_.scores.flatMap(_.ranker d.map(_.toStr ng)))
     f (strs.ex sts(_.nonEmpty)) strs else None
  }

  val thr ftDataRecord: Opt on[TDataRecord] = for {
    r chDataRecord <- dataRecord
    dr <- r chDataRecord.dataRecord
  } y eld {
    ScalaToJavaDataRecordConvers ons.javaDataRecord2ScalaDataRecord(dr)
  }

  val toOffl neUserThr ft: offl ne.Offl neUserRecom ndat on = {
    val scor ngDeta ls =
       f (userCand dateS ceDeta ls. sEmpty && score. sEmpty && thr ftDataRecord. sEmpty) {
        None
      } else {
        So (
          offl ne.Scor ngDeta ls(
            cand dateS ceDeta ls = userCand dateS ceDeta ls.map(_.toOffl neThr ft),
            score = score,
            dataRecord = thr ftDataRecord,
            ranker ds = ranker dsStr,
             nfoPerRank ngStage =  nfoPerRank ngStage.map(_.mapValues(_.toOffl neThr ft))
          )
        )
      }
    offl ne
      .Offl neUserRecom ndat on(
         d,
        reason.map(_.toOffl neThr ft),
        ad tadata.map(_.ad mpress on),
        track ngToken.map(_.toOffl neThr ft),
        scor ngDeta ls = scor ngDeta ls
      )
  }

  overr de val toOffl neThr ft: offl ne.Offl neRecom ndat on =
    offl ne.Offl neRecom ndat on.User(toOffl neUserThr ft)

  val toUserThr ft: t.UserRecom ndat on = {
    val scor ngDeta ls =
       f (userCand dateS ceDeta ls. sEmpty && score. sEmpty && thr ftDataRecord. sEmpty && scores. sEmpty) {
        None
      } else {
        So (
          t.Scor ngDeta ls(
            cand dateS ceDeta ls = userCand dateS ceDeta ls.map(_.toThr ft),
            score = score,
            dataRecord = thr ftDataRecord,
            ranker ds = ranker dsStr,
            debugDataRecord = dataRecord.flatMap(_.debugDataRecord),
             nfoPerRank ngStage =  nfoPerRank ngStage.map(_.mapValues(_.toThr ft))
          )
        )
      }
    t.UserRecom ndat on(
      user d =  d,
      reason = reason.map(_.toThr ft),
      ad mpress on = ad tadata.map(_.ad mpress on),
      track ng nfo = track ngToken.map(Track ngToken.ser al ze),
      scor ngDeta ls = scor ngDeta ls,
      recom ndat onFlow dent f er = recom ndat onFlow dent f er
    )
  }

  overr de val toThr ft: t.Recom ndat on =
    t.Recom ndat on.User(toUserThr ft)

  def setFollowProof(followProofOpt: Opt on[FollowProof]): Cand dateUser = {
    t .copy(
      reason = reason
        .map { reason =>
          reason.copy(
            accountProof = reason.accountProof
              .map { accountProof =>
                accountProof.copy(followProof = followProofOpt)
              }.orElse(So (AccountProof(followProof = followProofOpt)))
          )
        }.orElse(So (Reason(So (AccountProof(followProof = followProofOpt)))))
    )
  }

  def addScore(score: Score): Cand dateUser = {
    val newScores = scores match {
      case So (ex st ngScores) => ex st ngScores.copy(scores = ex st ngScores.scores :+ score)
      case None => Scores(Seq(score))
    }
    t .copy(scores = So (newScores))
  }
}

object Cand dateUser {
  val DefaultCand dateScore = 1.0

  // for convert ng cand date  n Scor ngUserRequest
  def fromUserRecom ndat on(cand date: t.UserRecom ndat on): Cand dateUser = {
    //   only use t  pr mary cand date s ce for now
    val userCand dateS ceDeta ls = for {
      scor ngDeta ls <- cand date.scor ngDeta ls
      cand dateS ceDeta ls <- scor ngDeta ls.cand dateS ceDeta ls
    } y eld UserCand dateS ceDeta ls(
      pr maryCand dateS ce = cand dateS ceDeta ls.pr maryS ce
        .flatMap(Algor hmFeedbackTokens.TokenToAlgor hmMap.get).map { algo =>
          Cand dateS ce dent f er(algo.toStr ng)
        },
      cand dateS ceScores = fromThr ftScoreMap(cand dateS ceDeta ls.cand dateS ceScores),
      cand dateS ceRanks = fromThr ftRankMap(cand dateS ceDeta ls.cand dateS ceRanks),
      addressBook tadata = None
    )
    Cand dateUser(
       d = cand date.user d,
      score = cand date.scor ngDeta ls.flatMap(_.score),
      reason = cand date.reason.map(Reason.fromThr ft),
      userCand dateS ceDeta ls = userCand dateS ceDeta ls,
      track ngToken = cand date.track ng nfo.map(Track ngToken.deser al ze),
      recom ndat onFlow dent f er = cand date.recom ndat onFlow dent f er,
       nfoPerRank ngStage = cand date.scor ngDeta ls.flatMap(
        _. nfoPerRank ngStage.map(_.mapValues(Rank ng nfo.fromThr ft)))
    )
  }

  def fromThr ftScoreMap(
    thr ftMapOpt: Opt on[scala.collect on.Map[Str ng, Double]]
  ): Map[Cand dateS ce dent f er, Opt on[Double]] = {
    (for {
      thr ftMap <- thr ftMapOpt.toSeq
      (algoNa , score) <- thr ftMap.toSeq
    } y eld {
      Cand dateS ce dent f er(algoNa ) -> So (score)
    }).toMap
  }

  def fromThr ftRankMap(
    thr ftMapOpt: Opt on[scala.collect on.Map[Str ng,  nt]]
  ): Map[Cand dateS ce dent f er,  nt] = {
    (for {
      thr ftMap <- thr ftMapOpt.toSeq
      (algoNa , rank) <- thr ftMap.toSeq
    } y eld {
      Cand dateS ce dent f er(algoNa ) -> rank
    }).toMap
  }
}
