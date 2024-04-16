package com.tw ter.t  l neranker.recap.model

 mport com.tw ter.esc rb rd.thr ftscala.T etEnt yAnnotat on
 mport com.tw ter.t  l nes.content_features.v1.thr ftscala.{ContentFeatures => ContentFeaturesV1}
 mport com.tw ter.t  l nes.content_features.{thr ftscala => thr ft}
 mport com.tw ter.t etyp e.thr ftscala.Conversat onControl
 mport com.tw ter.t etyp e.thr ftscala. d aEnt y
 mport com.tw ter.t etyp e.thr ftscala.SelfThread tadata
 mport scala.ut l.Fa lure
 mport scala.ut l.Success
 mport scala.ut l.{Try => ScalaTry}

case class ContentFeatures(
  length: Short,
  hasQuest on: Boolean,
  numCaps: Short,
  numWh eSpaces: Short,
  numNewl nes: Opt on[Short],
  v deoDurat onMs: Opt on[ nt],
  b Rate: Opt on[ nt],
  aspectRat oNum: Opt on[Short],
  aspectRat oDen: Opt on[Short],
  w dths: Opt on[Seq[Short]],
    ghts: Opt on[Seq[Short]],
  res ze thods: Opt on[Seq[Short]],
  num d aTags: Opt on[Short],
   d aTagScreenNa s: Opt on[Seq[Str ng]],
  emoj Tokens: Opt on[Set[Str ng]],
  emot conTokens: Opt on[Set[Str ng]],
  phrases: Opt on[Set[Str ng]],
  faceAreas: Opt on[Seq[ nt]],
  dom nantColorRed: Opt on[Short],
  dom nantColorBlue: Opt on[Short],
  dom nantColorGreen: Opt on[Short],
  numColors: Opt on[Short],
  st cker ds: Opt on[Seq[Long]],
   d aOr g nProv ders: Opt on[Seq[Str ng]],
   sManaged: Opt on[Boolean],
   s360: Opt on[Boolean],
  v ewCount: Opt on[Long],
   sMonet zable: Opt on[Boolean],
   sEmbeddable: Opt on[Boolean],
  hasSelectedPrev ew mage: Opt on[Boolean],
  hasT le: Opt on[Boolean],
  hasDescr pt on: Opt on[Boolean],
  hasV s S eCallToAct on: Opt on[Boolean],
  hasApp nstallCallToAct on: Opt on[Boolean],
  hasWatchNowCallToAct on: Opt on[Boolean],
   d a: Opt on[Seq[ d aEnt y]],
  dom nantColorPercentage: Opt on[Double],
  posUn grams: Opt on[Set[Str ng]],
  posB grams: Opt on[Set[Str ng]],
  semant cCoreAnnotat ons: Opt on[Seq[T etEnt yAnnotat on]],
  selfThread tadata: Opt on[SelfThread tadata],
  tokens: Opt on[Seq[Str ng]],
  t etText: Opt on[Str ng],
  conversat onControl: Opt on[Conversat onControl]) {
  def toThr ft: thr ft.ContentFeatures =
    thr ft.ContentFeatures.V1(toThr ftV1)

  def toThr ftV1: ContentFeaturesV1 =
    ContentFeaturesV1(
      length = length,
      hasQuest on = hasQuest on,
      numCaps = numCaps,
      numWh eSpaces = numWh eSpaces,
      numNewl nes = numNewl nes,
      v deoDurat onMs = v deoDurat onMs,
      b Rate = b Rate,
      aspectRat oNum = aspectRat oNum,
      aspectRat oDen = aspectRat oDen,
      w dths = w dths,
        ghts =   ghts,
      res ze thods = res ze thods,
      num d aTags = num d aTags,
       d aTagScreenNa s =  d aTagScreenNa s,
      emoj Tokens = emoj Tokens,
      emot conTokens = emot conTokens,
      phrases = phrases,
      faceAreas = faceAreas,
      dom nantColorRed = dom nantColorRed,
      dom nantColorBlue = dom nantColorBlue,
      dom nantColorGreen = dom nantColorGreen,
      numColors = numColors,
      st cker ds = st cker ds,
       d aOr g nProv ders =  d aOr g nProv ders,
       sManaged =  sManaged,
       s360 =  s360,
      v ewCount = v ewCount,
       sMonet zable =  sMonet zable,
       sEmbeddable =  sEmbeddable,
      hasSelectedPrev ew mage = hasSelectedPrev ew mage,
      hasT le = hasT le,
      hasDescr pt on = hasDescr pt on,
      hasV s S eCallToAct on = hasV s S eCallToAct on,
      hasApp nstallCallToAct on = hasApp nstallCallToAct on,
      hasWatchNowCallToAct on = hasWatchNowCallToAct on,
      dom nantColorPercentage = dom nantColorPercentage,
      posUn grams = posUn grams,
      posB grams = posB grams,
      semant cCoreAnnotat ons = semant cCoreAnnotat ons,
      selfThread tadata = selfThread tadata,
      tokens = tokens,
      t etText = t etText,
      conversat onControl = conversat onControl,
       d a =  d a
    )
}

object ContentFeatures {
  val Empty: ContentFeatures = ContentFeatures(
    0.toShort,
    false,
    0.toShort,
    0.toShort,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None
  )

  def fromThr ft(contentFeatures: thr ft.ContentFeatures): Opt on[ContentFeatures] =
    contentFeatures match {
      case thr ft.ContentFeatures.V1(contentFeaturesV1) =>
        So (fromThr ftV1(contentFeaturesV1))
      case _ =>
        None
    }

  pr vate val fa lure =
    Fa lure[ContentFeatures](new Except on("Fa lure to convert content features from thr ft"))

  def tryFromThr ft(contentFeaturesThr ft: thr ft.ContentFeatures): ScalaTry[ContentFeatures] =
    fromThr ft(contentFeaturesThr ft) match {
      case So (contentFeatures) => Success[ContentFeatures](contentFeatures)
      case None => fa lure
    }

  def fromThr ftV1(contentFeaturesV1: ContentFeaturesV1): ContentFeatures =
    ContentFeatures(
      length = contentFeaturesV1.length,
      hasQuest on = contentFeaturesV1.hasQuest on,
      numCaps = contentFeaturesV1.numCaps,
      numWh eSpaces = contentFeaturesV1.numWh eSpaces,
      numNewl nes = contentFeaturesV1.numNewl nes,
      v deoDurat onMs = contentFeaturesV1.v deoDurat onMs,
      b Rate = contentFeaturesV1.b Rate,
      aspectRat oNum = contentFeaturesV1.aspectRat oNum,
      aspectRat oDen = contentFeaturesV1.aspectRat oDen,
      w dths = contentFeaturesV1.w dths,
        ghts = contentFeaturesV1.  ghts,
      res ze thods = contentFeaturesV1.res ze thods,
      num d aTags = contentFeaturesV1.num d aTags,
       d aTagScreenNa s = contentFeaturesV1. d aTagScreenNa s,
      emoj Tokens = contentFeaturesV1.emoj Tokens.map(_.toSet),
      emot conTokens = contentFeaturesV1.emot conTokens.map(_.toSet),
      phrases = contentFeaturesV1.phrases.map(_.toSet),
      faceAreas = contentFeaturesV1.faceAreas,
      dom nantColorRed = contentFeaturesV1.dom nantColorRed,
      dom nantColorBlue = contentFeaturesV1.dom nantColorBlue,
      dom nantColorGreen = contentFeaturesV1.dom nantColorGreen,
      numColors = contentFeaturesV1.numColors,
      st cker ds = contentFeaturesV1.st cker ds,
       d aOr g nProv ders = contentFeaturesV1. d aOr g nProv ders,
       sManaged = contentFeaturesV1. sManaged,
       s360 = contentFeaturesV1. s360,
      v ewCount = contentFeaturesV1.v ewCount,
       sMonet zable = contentFeaturesV1. sMonet zable,
       sEmbeddable = contentFeaturesV1. sEmbeddable,
      hasSelectedPrev ew mage = contentFeaturesV1.hasSelectedPrev ew mage,
      hasT le = contentFeaturesV1.hasT le,
      hasDescr pt on = contentFeaturesV1.hasDescr pt on,
      hasV s S eCallToAct on = contentFeaturesV1.hasV s S eCallToAct on,
      hasApp nstallCallToAct on = contentFeaturesV1.hasApp nstallCallToAct on,
      hasWatchNowCallToAct on = contentFeaturesV1.hasWatchNowCallToAct on,
      dom nantColorPercentage = contentFeaturesV1.dom nantColorPercentage,
      posUn grams = contentFeaturesV1.posUn grams.map(_.toSet),
      posB grams = contentFeaturesV1.posB grams.map(_.toSet),
      semant cCoreAnnotat ons = contentFeaturesV1.semant cCoreAnnotat ons,
      selfThread tadata = contentFeaturesV1.selfThread tadata,
      tokens = contentFeaturesV1.tokens.map(_.toSeq),
      t etText = contentFeaturesV1.t etText,
      conversat onControl = contentFeaturesV1.conversat onControl,
       d a = contentFeaturesV1. d a
    )
}
