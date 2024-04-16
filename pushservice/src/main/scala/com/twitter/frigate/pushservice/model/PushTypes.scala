package com.tw ter.fr gate.pushserv ce.model

 mport com.tw ter.fr gate.common.base._
 mport com.tw ter.fr gate.common.cand date.UserLanguage
 mport com.tw ter.fr gate.common.cand date._
 mport com.tw ter.fr gate.data_p pel ne.features_common.RequestContextForFeatureStore
 mport com.tw ter.fr gate.pushserv ce.model.cand date.Copy nfo
 mport com.tw ter.fr gate.pushserv ce.model.cand date.MLScores
 mport com.tw ter.fr gate.pushserv ce.model.cand date.Qual yScr b ng
 mport com.tw ter.fr gate.pushserv ce.model.cand date.Scr ber
 mport com.tw ter.fr gate.pushserv ce.model. b s. b s2HydratorForCand date
 mport com.tw ter.fr gate.pushserv ce.model.ntab.NTabRequest
 mport com.tw ter.fr gate.pushserv ce.take.ChannelForCand date
 mport com.tw ter.fr gate.pushserv ce.target._
 mport com.tw ter.ut l.T  

object PushTypes {

  tra  Target
      extends TargetUser
      w h UserDeta ls
      w h TargetW hPushContext
      w h TargetDec der
      w h TargetABDec der
      w h Fr gate tory
      w h PushTarget ng
      w h TargetScor ngDeta ls
      w h T et mpress on tory
      w h CustomConf gForExpt
      w h CaretFeedback tory
      w h Not f cat onFeedback tory
      w h PromptFeedback tory
      w h HTLV s  tory
      w h MaxT etAge
      w h NewUserDeta ls
      w h ResurrectedUserDeta ls
      w h TargetW hSeedUsers
      w h Mag cFanout tory
      w h OptOutUser nterests
      w h RequestContextForFeatureStore
      w h TargetAppPerm ss ons
      w h UserLanguage
      w h  nl neAct on tory
      w h TargetPlaces

  tra  RawCand date extends Cand date w h Target nfo[PushTypes.Target] w h Recom ndat onType {

    val createdAt: T   = T  .now
  }

  tra  PushCand date
      extends RawCand date
      w h Cand dateScor ngDeta ls
      w h MLScores
      w h Qual yScr b ng
      w h Copy nfo
      w h Scr ber
      w h  b s2HydratorForCand date
      w h NTabRequest
      w h ChannelForCand date
}
