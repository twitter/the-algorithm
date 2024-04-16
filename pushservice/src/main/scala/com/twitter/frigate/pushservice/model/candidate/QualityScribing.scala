package com.tw ter.fr gate.pushserv ce.model.cand date

 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.H ghQual yScr b ngScores
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.params.PushMLModel
 mport com.tw ter.ut l.Future
 mport java.ut l.concurrent.ConcurrentHashMap
 mport scala.collect on.concurrent.{Map => CMap}
 mport scala.collect on.convert.decorateAsScala._

tra  Qual yScr b ng {
  self: PushCand date w h MLScores =>

  // Use to store ot r scores (to avo d dupl cate quer es to ot r serv ces, e.g. HSS)
  pr vate val externalCac dScores: CMap[Str ng, Future[Opt on[Double]]] =
    new ConcurrentHashMap[Str ng, Future[Opt on[Double]]]().asScala

  /**
   * Retr eves t  model vers on as spec f ed by t  correspond ng FS param.
   * T  model vers on w ll be used for gett ng t  cac d score or tr gger ng
   * a pred ct on request.
   *
   * @param modelNa  T  score   w ll l ke to scr be
   */
  pr vate def getModelVers on(
    modelNa : H ghQual yScr b ngScores.Na 
  ): Str ng = {
    modelNa  match {
      case H ghQual yScr b ngScores. avyRank ngScore =>
        target.params(PushFeatureSw chParams.H ghQual yCand dates avyRank ngModel)
      case H ghQual yScr b ngScores.NonPersonal zedQual yScoreUs ngCnn =>
        target.params(PushFeatureSw chParams.H ghQual yCand datesNonPersonal zedQual yCnnModel)
      case H ghQual yScr b ngScores.BqmlNsfwScore =>
        target.params(PushFeatureSw chParams.H ghQual yCand datesBqmlNsfwModel)
      case H ghQual yScr b ngScores.BqmlReportScore =>
        target.params(PushFeatureSw chParams.H ghQual yCand datesBqmlReportModel)
    }
  }

  /**
   * Retr eves t  score for scr b ng e  r from a cac d value or
   * by generat ng a pred ct on request. T  w ll  ncrease model QPS
   *
   * @param pushMLModel T  represents t  pref x of t  model na  ( .e. [pushMLModel]_[vers on])
   * @param scoreNa    T  na  to be use w n scr b ng t  score
   */
  def getScr b ngScore(
    pushMLModel: PushMLModel.Value,
    scoreNa : H ghQual yScr b ngScores.Na 
  ): Future[(Str ng, Opt on[Double])] = {
    getMLModelScore(
      pushMLModel,
      getModelVers on(scoreNa )
    ).map { scoreOpt =>
      scoreNa .toStr ng -> scoreOpt
    }
  }

  /**
   * Retr eves t  score for scr b ng  f   has been computed/cac d before ot rw se
   *   w ll return Future.None
   *
   * @param pushMLModel T  represents t  pref x of t  model na  ( .e. [pushMLModel]_[vers on])
   * @param scoreNa    T  na  to be use w n scr b ng t  score
   */
  def getScr b ngScoreW houtUpdate(
    pushMLModel: PushMLModel.Value,
    scoreNa : H ghQual yScr b ngScores.Na 
  ): Future[(Str ng, Opt on[Double])] = {
    getMLModelScoreW houtUpdate(
      pushMLModel,
      getModelVers on(scoreNa )
    ).map { scoreOpt =>
      scoreNa .toStr ng -> scoreOpt
    }
  }

  /**
   * Cac s t  g ven score future
   *
   * @param scoreNa  T  na  to be use w n scr b ng t  score
   * @param scoreFut Future mapp ng scoreNa  -> scoreOpt
   */
  def cac ExternalScore(scoreNa : Str ng, scoreFut: Future[Opt on[Double]]) = {
     f (!externalCac dScores.conta ns(scoreNa )) {
      externalCac dScores += scoreNa  -> scoreFut
    }
  }

  /**
   * Returns all external scores future cac d as a sequence
   */
  def getExternalCac dScores: Seq[Future[(Str ng, Opt on[Double])]] = {
    externalCac dScores.map {
      case (modelNa , scoreFut) =>
        scoreFut.map { scoreOpt => modelNa  -> scoreOpt }
    }.toSeq
  }

  def getExternalCac dScoreByNa (na : Str ng): Future[Opt on[Double]] = {
    externalCac dScores.getOrElse(na , Future.None)
  }
}
