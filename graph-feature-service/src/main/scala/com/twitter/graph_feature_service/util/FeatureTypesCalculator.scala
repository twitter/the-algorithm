package com.tw ter.graph_feature_serv ce.ut l

 mport com.tw ter.graph_feature_serv ce.thr ftscala.EdgeType._
 mport com.tw ter.graph_feature_serv ce.thr ftscala.{FeatureType, PresetFeatureTypes}

object FeatureTypesCalculator {

  f nal val DefaultTwoHop = Seq(
    FeatureType(Follow ng, Follo dBy),
    FeatureType(Follow ng, Favor edBy),
    FeatureType(Follow ng, Ret etedBy),
    FeatureType(Follow ng,  nt onedBy),
    FeatureType(Follow ng, MutualFollow),
    FeatureType(Favor e, Follo dBy),
    FeatureType(Favor e, Favor edBy),
    FeatureType(Favor e, Ret etedBy),
    FeatureType(Favor e,  nt onedBy),
    FeatureType(Favor e, MutualFollow),
    FeatureType(MutualFollow, Follo dBy),
    FeatureType(MutualFollow, Favor edBy),
    FeatureType(MutualFollow, Ret etedBy),
    FeatureType(MutualFollow,  nt onedBy),
    FeatureType(MutualFollow, MutualFollow)
  )

  f nal val Soc alProofTwoHop = Seq(FeatureType(Follow ng, Follo dBy))

  f nal val HtlTwoHop = DefaultTwoHop

  f nal val WtfTwoHop = Soc alProofTwoHop

  f nal val SqTwoHop = DefaultTwoHop

  f nal val RuxTwoHop = DefaultTwoHop

  f nal val MRTwoHop = DefaultTwoHop

  f nal val UserTypea adTwoHop = Soc alProofTwoHop

  f nal val presetFeatureTypes =
    (HtlTwoHop ++ WtfTwoHop ++ SqTwoHop ++ RuxTwoHop ++ MRTwoHop ++ UserTypea adTwoHop).toSet

  def getFeatureTypes(
    presetFeatureTypes: PresetFeatureTypes,
    featureTypes: Seq[FeatureType]
  ): Seq[FeatureType] = {
    presetFeatureTypes match {
      case PresetFeatureTypes.HtlTwoHop => HtlTwoHop
      case PresetFeatureTypes.WtfTwoHop => WtfTwoHop
      case PresetFeatureTypes.SqTwoHop => SqTwoHop
      case PresetFeatureTypes.RuxTwoHop => RuxTwoHop
      case PresetFeatureTypes.MrTwoHop => MRTwoHop
      case PresetFeatureTypes.UserTypea adTwoHop => UserTypea adTwoHop
      case _ => featureTypes
    }
  }

}
