package com.tw ter.v s b l y.models

 mport com.tw ter.v s b l y.safety_label_store.{thr ftscala => s}
 mport com.tw ter.v s b l y.ut l.Nam ngUt ls

sealed tra  SpaceSafetyLabelType extends SafetyLabelType {
  lazy val na : Str ng = Nam ngUt ls.getFr endlyNa (t )
}

object SpaceSafetyLabelType extends SafetyLabelType {

  val L st: L st[SpaceSafetyLabelType] = s.SpaceSafetyLabelType.l st.map(fromThr ft)

  val Act veLabels: L st[SpaceSafetyLabelType] = L st.f lter { labelType =>
    labelType != Unknown && labelType != Deprecated
  }

  pr vate lazy val na ToValueMap: Map[Str ng, SpaceSafetyLabelType] =
    L st.map(l => l.na .toLo rCase -> l).toMap
  def fromNa (na : Str ng): Opt on[SpaceSafetyLabelType] = na ToValueMap.get(na .toLo rCase)

  pr vate val UnknownThr ftSafetyLabelType =
    s.SpaceSafetyLabelType.EnumUnknownSpaceSafetyLabelType(UnknownEnumValue)

  pr vate lazy val thr ftToModelMap: Map[s.SpaceSafetyLabelType, SpaceSafetyLabelType] = Map(
    s.SpaceSafetyLabelType.DoNotAmpl fy -> DoNotAmpl fy,
    s.SpaceSafetyLabelType.Coord natedHarmfulAct v yH ghRecall -> Coord natedHarmfulAct v yH ghRecall,
    s.SpaceSafetyLabelType.UntrustedUrl -> UntrustedUrl,
    s.SpaceSafetyLabelType.M slead ngH ghRecall -> M slead ngH ghRecall,
    s.SpaceSafetyLabelType.NsfwH ghPrec s on -> NsfwH ghPrec s on,
    s.SpaceSafetyLabelType.NsfwH ghRecall -> NsfwH ghRecall,
    s.SpaceSafetyLabelType.C v c ntegr yM s nfo -> C v c ntegr yM s nfo,
    s.SpaceSafetyLabelType. d calM s nfo ->  d calM s nfo,
    s.SpaceSafetyLabelType.Gener cM s nfo -> Gener cM s nfo,
    s.SpaceSafetyLabelType.DmcaW h ld -> DmcaW h ld,
    s.SpaceSafetyLabelType.HatefulH ghRecall -> HatefulH ghRecall,
    s.SpaceSafetyLabelType.V olenceH ghRecall -> V olenceH ghRecall,
    s.SpaceSafetyLabelType.H ghTox c yModelScore -> H ghTox c yModelScore,
    s.SpaceSafetyLabelType.DeprecatedSpaceSafetyLabel14 -> Deprecated,
    s.SpaceSafetyLabelType.DeprecatedSpaceSafetyLabel15 -> Deprecated,
    s.SpaceSafetyLabelType.Reserved16 -> Deprecated,
    s.SpaceSafetyLabelType.Reserved17 -> Deprecated,
    s.SpaceSafetyLabelType.Reserved18 -> Deprecated,
    s.SpaceSafetyLabelType.Reserved19 -> Deprecated,
    s.SpaceSafetyLabelType.Reserved20 -> Deprecated,
    s.SpaceSafetyLabelType.Reserved21 -> Deprecated,
    s.SpaceSafetyLabelType.Reserved22 -> Deprecated,
    s.SpaceSafetyLabelType.Reserved23 -> Deprecated,
    s.SpaceSafetyLabelType.Reserved24 -> Deprecated,
    s.SpaceSafetyLabelType.Reserved25 -> Deprecated,
  )

  pr vate lazy val modelToThr ftMap: Map[SpaceSafetyLabelType, s.SpaceSafetyLabelType] =
    (for ((k, v) <- thr ftToModelMap) y eld (v, k)) ++ Map(
      Deprecated -> s.SpaceSafetyLabelType.EnumUnknownSpaceSafetyLabelType(DeprecatedEnumValue),
    )

  case object DoNotAmpl fy extends SpaceSafetyLabelType
  case object Coord natedHarmfulAct v yH ghRecall extends SpaceSafetyLabelType
  case object UntrustedUrl extends SpaceSafetyLabelType
  case object M slead ngH ghRecall extends SpaceSafetyLabelType
  case object NsfwH ghPrec s on extends SpaceSafetyLabelType
  case object NsfwH ghRecall extends SpaceSafetyLabelType
  case object C v c ntegr yM s nfo extends SpaceSafetyLabelType
  case object  d calM s nfo extends SpaceSafetyLabelType
  case object Gener cM s nfo extends SpaceSafetyLabelType
  case object DmcaW h ld extends SpaceSafetyLabelType
  case object HatefulH ghRecall extends SpaceSafetyLabelType
  case object V olenceH ghRecall extends SpaceSafetyLabelType
  case object H ghTox c yModelScore extends SpaceSafetyLabelType

  case object Deprecated extends SpaceSafetyLabelType
  case object Unknown extends SpaceSafetyLabelType

  def fromThr ft(safetyLabelType: s.SpaceSafetyLabelType): SpaceSafetyLabelType =
    thr ftToModelMap.get(safetyLabelType) match {
      case So (spaceSafetyLabelType) => spaceSafetyLabelType
      case _ =>
        safetyLabelType match {
          case s.SpaceSafetyLabelType.EnumUnknownSpaceSafetyLabelType(DeprecatedEnumValue) =>
            Deprecated
          case _ =>
            Unknown
        }
    }

  def toThr ft(safetyLabelType: SpaceSafetyLabelType): s.SpaceSafetyLabelType = {
    modelToThr ftMap
      .get(safetyLabelType).getOrElse(UnknownThr ftSafetyLabelType)
  }
}
