package com.tw ter.fr gate.pushserv ce.model.cand date

 mport com.tw ter.fr gate.data_p pel ne.features_common.PushQual yModelFeatureContext.featureContext
 mport com.tw ter.fr gate.data_p pel ne.features_common.PushQual yModelUt l
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base._
 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.common.ut l.Not f cat onScr beUt l
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.OutOfNetworkT etPushCand date
 mport com.tw ter.fr gate.pushserv ce.model.Top cProofT etPushCand date
 mport com.tw ter.fr gate.pushserv ce.ml.Hydrat onContextBu lder
 mport com.tw ter.fr gate.pushserv ce.pred cate.qual y_model_pred cate.PDauCohort
 mport com.tw ter.fr gate.pushserv ce.pred cate.qual y_model_pred cate.PDauCohortUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.Cand date2Fr gateNot f cat on
 mport com.tw ter.fr gate.pushserv ce.ut l. d aAnnotat onsUt l.sens  ve d aCategoryFeatureNa 
 mport com.tw ter.fr gate.scr be.thr ftscala.Fr gateNot f cat onScr beType
 mport com.tw ter.fr gate.scr be.thr ftscala.Not f cat onScr be
 mport com.tw ter.fr gate.scr be.thr ftscala.Pred cateDeta led nfo
 mport com.tw ter.fr gate.scr be.thr ftscala.PushCap nfo
 mport com.tw ter.fr gate.thr ftscala.ChannelNa 
 mport com.tw ter.fr gate.thr ftscala.Fr gateNot f cat on
 mport com.tw ter.fr gate.thr ftscala.Overr de nfo
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter. rm .model.user_state.UserState.UserState
 mport com.tw ter. b s2.serv ce.thr ftscala. b s2Response
 mport com.tw ter.ml.ap .ut l.ScalaToJavaDataRecordConvers ons
 mport com.tw ter.nrel. avyranker.FeatureHydrator
 mport com.tw ter.ut l.Future
 mport java.ut l.UU D
 mport java.ut l.concurrent.ConcurrentHashMap
 mport scala.collect on.concurrent.{Map => CMap}
 mport scala.collect on.Map
 mport scala.collect on.convert.decorateAsScala._

tra  Scr ber {
  self: PushCand date =>

  def statsRece ver: StatsRece ver

  def fr gateNot f cat on: Fr gateNot f cat on = Cand date2Fr gateNot f cat on
    .getFr gateNot f cat on(self)(statsRece ver)
    .copy(copyAggregat on d = self.copyAggregat on d)

  lazy val  mpress on d: Str ng = UU D.randomUU D.toStr ng.replaceAll("-", "")

  // Used to store t  score and threshold for pred cates
  // Map(pred cate na , (score, threshold, f lter?))
  pr vate val pred cateScoreAndThreshold: CMap[Str ng, Pred cateDeta led nfo] =
    new ConcurrentHashMap[Str ng, Pred cateDeta led nfo]().asScala

  def cac Pred cate nfo(
    predNa : Str ng,
    predScore: Double,
    predThreshold: Double,
    predResult: Boolean,
    add  onal nformat on: Opt on[Map[Str ng, Double]] = None
  ) = {
     f (!pred cateScoreAndThreshold.conta ns(predNa )) {
      pred cateScoreAndThreshold += predNa  -> Pred cateDeta led nfo(
        predNa ,
        predScore,
        predThreshold,
        predResult,
        add  onal nformat on)
    }
  }

  def getCac dPred cate nfo(): Seq[Pred cateDeta led nfo] = pred cateScoreAndThreshold.values.toSeq

  def fr gateNot f cat onForPers stence(
    channels: Seq[ChannelNa ],
     sS lentPush: Boolean,
    overr de nfoOpt: Opt on[Overr de nfo] = None,
    copyFeaturesL st: Set[Str ng]
  ): Future[Fr gateNot f cat on] = {

    // record d splay locat on for fr gate not f cat on
    statsRece ver
      .scope("Fr gateNot f cat onForPers stence")
      .scope("d splayLocat on")
      .counter(fr gateNot f cat on.not f cat onD splayLocat on.na )
      . ncr()

    val getModelScores = self.getModelScoresforScr b ng()

    Future.jo n(getModelScores, self.target.targetMrUserState).map {
      case (mlScores, mrUserState) =>
        fr gateNot f cat on.copy(
           mpress on d = So ( mpress on d),
           sS lentPush = So ( sS lentPush),
          overr de nfo = overr de nfoOpt,
          mlModelScores = So (mlScores),
          mrUserState = mrUserState.map(_.na ),
          copyFeatures = So (copyFeaturesL st.toSeq)
        )
    }
  }
  // scr be data
  pr vate def getNot f cat onScr be(
    not fForPers stence: Fr gateNot f cat on,
    userState: Opt on[UserState],
    dauCohort: PDauCohort.Value,
     b s2Response: Opt on[ b s2Response],
    t etAuthor d: Opt on[Long],
    recUser d: Opt on[Long],
    modelScoresMap: Opt on[Map[Str ng, Double]],
    pr maryCl ent: Opt on[Str ng],
     sMrBackf llCR: Opt on[Boolean] = None,
    tagsCR: Opt on[Seq[Str ng]] = None,
    g zmoduckTargetUser: Opt on[User],
    pred cateDeta led nfoL st: Opt on[Seq[Pred cateDeta led nfo]] = None,
    pushCap nfoL st: Opt on[Seq[PushCap nfo]] = None
  ): Not f cat onScr be = {
    Not f cat onScr be(
      Fr gateNot f cat onScr beType.Send ssage,
      System.currentT  M ll s(),
      targetUser d = So (self.target.target d),
      t  stampKeyFor toryV2 = So (createdAt. nSeconds),
      sendType = Not f cat onScr beUt l.convertToScr beD splayLocat on(
        self.fr gateNot f cat on.not f cat onD splayLocat on
      ),
      recom ndat onType = Not f cat onScr beUt l.convertToScr beRecom ndat onType(
        self.fr gateNot f cat on.commonRecom ndat onType
      ),
      commonRecom ndat onType = So (self.fr gateNot f cat on.commonRecom ndat onType),
      fromPushServ ce = So (true),
      fr gateNot f cat on = So (not fForPers stence),
       mpress on d = So ( mpress on d),
      sk pModel nfo = target.sk pModel nfo,
       b s2Response =  b s2Response,
      t etAuthor d = t etAuthor d,
      scr beFeatures = So (target.noSk pButScr beFeatures),
      userState = userState.map(_.toStr ng),
      pDauCohort = So (dauCohort.toStr ng),
      recom ndedUser d = recUser d,
      modelScores = modelScoresMap,
      pr maryCl ent = pr maryCl ent,
       sMrBackf llCR =  sMrBackf llCR,
      tagsCR = tagsCR,
      targetUserType = g zmoduckTargetUser.map(_.userType),
      pred cateDeta led nfoL st = pred cateDeta led nfoL st,
      pushCap nfoL st = pushCap nfoL st
    )
  }

  def scr beData(
     b s2Response: Opt on[ b s2Response] = None,
     sS lentPush: Boolean = false,
    overr de nfoOpt: Opt on[Overr de nfo] = None,
    copyFeaturesL st: Set[Str ng] = Set.empty,
    channels: Seq[ChannelNa ] = Seq.empty
  ): Future[Not f cat onScr be] = {

    val recT etAuthor d = self match {
      case t: T etCand date w h T etAuthor => t.author d
      case _ => None
    }

    val recUser d = self match {
      case u: UserCand date => So (u.user d)
      case _ => None
    }

    val  sMrBackf llCR = self match {
      case t: OutOfNetworkT etPushCand date => t. sMrBackf llCR
      case _ => None
    }

    val tagsCR = self match {
      case t: OutOfNetworkT etPushCand date =>
        t.tagsCR.map { tags =>
          tags.map(_.toStr ng)
        }
      case t: Top cProofT etPushCand date =>
        t.tagsCR.map { tags =>
          tags.map(_.toStr ng)
        }
      case _ => None
    }

    Future
      .jo n(
        fr gateNot f cat onForPers stence(
          channels = channels,
           sS lentPush =  sS lentPush,
          overr de nfoOpt = overr de nfoOpt,
          copyFeaturesL st = copyFeaturesL st
        ),
        target.targetUserState,
        PDauCohortUt l.getPDauCohort(target),
        target.dev ce nfo,
        target.targetUser
      )
      .flatMap {
        case (not fForPers stence, userState, dauCohort, dev ce nfo, g zmoduckTargetUserOpt) =>
          val pr maryCl ent = dev ce nfo.flatMap(_.guessedPr maryCl ent).map(_.toStr ng)
          val cac dPred cate nfo =
             f (self.target.params(PushParams.EnablePred cateDeta led nfoScr b ng)) {
              So (getCac dPred cate nfo())
            } else None

          val cac dPushCap nfo =
             f (self.target
                .params(PushParams.EnablePushCap nfoScr b ng)) {
              So (target.f nalPushcapAndFat gue.values.toSeq)
            } else None

          val data = getNot f cat onScr be(
            not fForPers stence,
            userState,
            dauCohort,
             b s2Response,
            recT etAuthor d,
            recUser d,
            not fForPers stence.mlModelScores,
            pr maryCl ent,
             sMrBackf llCR,
            tagsCR,
            g zmoduckTargetUserOpt,
            cac dPred cate nfo,
            cac dPushCap nfo
          )
          //Don't scr be features for CRTs not el g ble for ML Layer
           f ((target. sModelTra n ngData || target.scr beFeatureW houtHydrat ngNewFeatures)
            && !RecTypes.notEl g bleForModelScoreTrack ng(self.commonRecType)) {
            // scr be all t  features for t  model tra n ng data
            self.getFeaturesForScr b ng.map { scr bedFeatureMap =>
               f (target.params(PushParams.EnableScr b ngMLFeaturesAsDataRecord) && !target.params(
                  PushFeatureSw chParams.EnableMrScr b ngMLFeaturesAsFeatureMapForStag ng)) {
                val scr bedFeatureDataRecord =
                  ScalaToJavaDataRecordConvers ons.javaDataRecord2ScalaDataRecord(
                    PushQual yModelUt l.adaptToDataRecord(scr bedFeatureMap, featureContext))
                data.copy(
                  featureDataRecord = So (scr bedFeatureDataRecord)
                )
              } else {
                data.copy(features =
                  So (PushQual yModelUt l.convertFeatureMapToFeatures(scr bedFeatureMap)))
              }
            }
          } else Future.value(data)
      }
  }

  def getFeaturesForScr b ng: Future[FeatureMap] = {
    target.featureMap
      .flatMap { targetFeatureMap =>
        val onl neFeatureMap = targetFeatureMap ++ self
          .cand dateFeatureMap() // targetFeatureMap  ncludes target core user  tory features

        val f lteredFeatureMap = {
          onl neFeatureMap.copy(
            sparseCont nuousFeatures = onl neFeatureMap.sparseCont nuousFeatures.f lterKeys(
              !_.equals(sens  ve d aCategoryFeatureNa ))
          )
        }

        val targetHydrat onContext = Hydrat onContextBu lder.bu ld(self.target)
        val cand dateHydrat onContext = Hydrat onContextBu lder.bu ld(self)

        val featureMapFut = targetHydrat onContext.jo n(cand dateHydrat onContext).flatMap {
          case (targetContext, cand dateContext) =>
            FeatureHydrator.getFeatures(
              cand dateHydrat onContext = cand dateContext,
              targetHydrat onContext = targetContext,
              onl neFeatures = f lteredFeatureMap,
              statsRece ver = statsRece ver)
        }

        featureMapFut
      }
  }

}
