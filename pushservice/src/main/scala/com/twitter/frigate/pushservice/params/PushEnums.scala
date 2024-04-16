package com.tw ter.fr gate.pushserv ce.params

/**
 * Enum for nam ng scores   w ll scr be for non-personal zed h gh qual y cand date generat on
 */
object H ghQual yScr b ngScores extends Enu rat on {
  type Na  = Value
  val  avyRank ngScore = Value
  val NonPersonal zedQual yScoreUs ngCnn = Value
  val BqmlNsfwScore = Value
  val BqmlReportScore = Value
}

/**
 * Enum for qual y uprank ng transform
 */
object MrQual yUprank ngTransformTypeEnum extends Enu rat on {
  val L near = Value
  val S gmo d = Value
}

/**
 * Enum for qual y part al uprank ng transform
 */
object MrQual yUprank ngPart alTypeEnum extends Enu rat on {
  val All = Value
  val Oon = Value
}

/**
 * Enum for bucket  mbersh p  n DDG 10220 Mr Bold T le Favor e Ret et Not f cat on exper  nt
 */
object MRBoldT leFavor eAndRet etExper  ntEnum extends Enu rat on {
  val ShortT le = Value
}

/**
 * Enum for ML f lter ng pred cates
 */
object Qual yPred cateEnum extends Enu rat on {
  val   ghtedOpenOrNtabCl ck = Value
  val Expl c OpenOrNtabCl ckF lter = Value
  val AlwaysTrue = Value // D sable ML f lter ng
}

/**
 * Enum to spec fy normal zat on used  n B gF lter ng exper  nts
 */
object B gF lter ngNormal zat onEnum extends Enu rat on {
  val Normal zat onD sabled = Value
  val Normal zeByNotSend ngScore = Value
}

/**
 * Enum for  nl ne act ons
 */
object  nl neAct onsEnum extends Enu rat on {
  val Favor e = Value
  val Follow = Value
  val Reply = Value
  val Ret et = Value
}

/**
 * Enum for template format
 */
object  b sTemplateFormatEnum extends Enu rat on {
  val template1 = Value
}

/**
 * Enum for Store na  for Top T ets By Geo
 */
object TopT etsForGeoComb nat on extends Enu rat on {
  val Default = Value
  val AccountsT etFavAsBackf ll = Value
  val AccountsT etFav nterm xed = Value
}

/**
 * Enum for scor ng funct on for Top T ets By Geo
 */
object TopT etsForGeoRank ngFunct on extends Enu rat on {
  val Score = Value
  val GeohashLengthAndT nScore = Value
}

/**
 * Enum for wh ch vers on of popgeo t ets to be us ng
 */
object PopGeoT etVers on extends Enu rat on {
  val Prod = Value
}

/**
 * Enum for Subtext  n Andro d  ader
 */
object SubtextForAndro dPush ader extends Enu rat on {
  val None = Value
  val TargetHandler = Value
  val TargetTagHandler = Value
  val TargetNa  = Value
  val AuthorTagHandler = Value
  val AuthorNa  = Value
}

object NsfwTextDetect onModel extends Enu rat on {
  val ProdModel = Value
  val Retra nedModel = Value
}

object H ghQual yCand dateGroupEnum extends Enu rat on {
  val AgeBucket = Value
  val Language = Value
  val Top c = Value
  val Country = Value
  val Adm n0 = Value
  val Adm n1 = Value
}

object CrtGroupEnum extends Enu rat on {
  val Tw stly = Value
  val Frs = Value
  val F1 = Value
  val Top c = Value
  val Tr p = Value
  val GeoPop = Value
  val Ot r = Value
  val None = Value
}

object SportGa Enum extends Enu rat on {
  val Soccer = Value
  val Nfl = Value
}
