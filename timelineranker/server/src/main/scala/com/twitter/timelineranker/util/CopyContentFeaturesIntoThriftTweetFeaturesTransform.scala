package com.tw ter.t  l neranker.ut l

 mport com.tw ter.search.common.features.thr ftscala.Thr ftT etFeatures
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.HydratedCand datesAndFeaturesEnvelope
 mport com.tw ter.t  l neranker.recap.model.ContentFeatures
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.ut l.Future

/**
 * Populates features w h t et d -> thr ftT etFeatures pa rs.
 *
 *  f a t et d from contentFeatures  s from searchResults,  s content features are cop ed to
 * thr ftT etFeatures.  f t  t et  s a ret et, t  or g nal t et's content features are cop ed.
 *
 *  f t  t et d  s not found  n searchResults, but  s an  nReplyToT et of a searchResult, t 
 * t et d -> thr ftT etFeatures pa r  s added to features. T   s because  n TLM, reply t ets
 * have features that are t  r  nReplyToT ets' content features. T  also allows scor ng
 *  nReplyToT et w h content features populated w n scor ng repl es.
 */
object CopyContentFeatures ntoThr ftT etFeaturesTransform
    extends FutureArrow[
      HydratedCand datesAndFeaturesEnvelope,
      HydratedCand datesAndFeaturesEnvelope
    ] {

  overr de def apply(
    request: HydratedCand datesAndFeaturesEnvelope
  ): Future[HydratedCand datesAndFeaturesEnvelope] = {

    // Content Features Request Fa lures are handled  n [[T etyp eContentFeaturesProv der]]
    request.contentFeaturesFuture.map { contentFeaturesMap =>
      val features = request.features.map {
        case (t et d: T et d, thr ftT etFeatures: Thr ftT etFeatures) =>
          val contentFeaturesOpt = request.t etS ceT etMap
            .get(t et d)
            .orElse(
              request. nReplyToT et ds.conta ns(t et d) match {
                case true => So (t et d)
                case false => None
              }
            )
            .flatMap(contentFeaturesMap.get)

          val thr ftT etFeaturesW hContentFeatures = contentFeaturesOpt match {
            case So (contentFeatures: ContentFeatures) =>
              copyContentFeatures ntoThr ftT etFeatures(contentFeatures, thr ftT etFeatures)
            case _ => thr ftT etFeatures
          }

          (t et d, thr ftT etFeaturesW hContentFeatures)
      }

      request.copy(features = features)
    }
  }

  def copyContentFeatures ntoThr ftT etFeatures(
    contentFeatures: ContentFeatures,
    thr ftT etFeatures: Thr ftT etFeatures
  ): Thr ftT etFeatures = {
    thr ftT etFeatures.copy(
      t etLength = So (contentFeatures.length.to nt),
      hasQuest on = So (contentFeatures.hasQuest on),
      numCaps = So (contentFeatures.numCaps.to nt),
      numWh espaces = So (contentFeatures.numWh eSpaces.to nt),
      numNewl nes = contentFeatures.numNewl nes,
      v deoDurat onMs = contentFeatures.v deoDurat onMs,
      b Rate = contentFeatures.b Rate,
      aspectRat oNum = contentFeatures.aspectRat oNum,
      aspectRat oDen = contentFeatures.aspectRat oDen,
      w dths = contentFeatures.w dths.map(_.map(_.to nt)),
        ghts = contentFeatures.  ghts.map(_.map(_.to nt)),
      res ze thods = contentFeatures.res ze thods.map(_.map(_.to nt)),
      num d aTags = contentFeatures.num d aTags.map(_.to nt),
       d aTagScreenNa s = contentFeatures. d aTagScreenNa s,
      emoj Tokens = contentFeatures.emoj Tokens,
      emot conTokens = contentFeatures.emot conTokens,
      phrases = contentFeatures.phrases,
      textTokens = contentFeatures.tokens,
      faceAreas = contentFeatures.faceAreas,
      dom nantColorRed = contentFeatures.dom nantColorRed,
      dom nantColorBlue = contentFeatures.dom nantColorBlue,
      dom nantColorGreen = contentFeatures.dom nantColorGreen,
      numColors = contentFeatures.numColors.map(_.to nt),
      st cker ds = contentFeatures.st cker ds,
       d aOr g nProv ders = contentFeatures. d aOr g nProv ders,
       sManaged = contentFeatures. sManaged,
       s360 = contentFeatures. s360,
      v ewCount = contentFeatures.v ewCount,
       sMonet zable = contentFeatures. sMonet zable,
       sEmbeddable = contentFeatures. sEmbeddable,
      hasSelectedPrev ew mage = contentFeatures.hasSelectedPrev ew mage,
      hasT le = contentFeatures.hasT le,
      hasDescr pt on = contentFeatures.hasDescr pt on,
      hasV s S eCallToAct on = contentFeatures.hasV s S eCallToAct on,
      hasApp nstallCallToAct on = contentFeatures.hasApp nstallCallToAct on,
      hasWatchNowCallToAct on = contentFeatures.hasWatchNowCallToAct on,
      dom nantColorPercentage = contentFeatures.dom nantColorPercentage,
      posUn grams = contentFeatures.posUn grams,
      posB grams = contentFeatures.posB grams,
      semant cCoreAnnotat ons = contentFeatures.semant cCoreAnnotat ons,
      conversat onControl = contentFeatures.conversat onControl
    )
  }
}
