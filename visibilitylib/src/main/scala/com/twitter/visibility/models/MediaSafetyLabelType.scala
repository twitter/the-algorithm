package com.tw ter.v s b l y.models

 mport com.tw ter.v s b l y.safety_label_store.{thr ftscala => s}
 mport com.tw ter.v s b l y.ut l.Nam ngUt ls

sealed tra   d aSafetyLabelType extends SafetyLabelType {
  lazy val na : Str ng = Nam ngUt ls.getFr endlyNa (t )
}

object  d aSafetyLabelType extends SafetyLabelType {

  val L st: L st[ d aSafetyLabelType] = s. d aSafetyLabelType.l st.map(fromThr ft)

  val Act veLabels: L st[ d aSafetyLabelType] = L st.f lter { labelType =>
    labelType != Unknown && labelType != Deprecated
  }

  pr vate lazy val na ToValueMap: Map[Str ng,  d aSafetyLabelType] =
    L st.map(l => l.na .toLo rCase -> l).toMap
  def fromNa (na : Str ng): Opt on[ d aSafetyLabelType] = na ToValueMap.get(na .toLo rCase)

  pr vate val UnknownThr ftSafetyLabelType =
    s. d aSafetyLabelType.EnumUnknown d aSafetyLabelType(UnknownEnumValue)

  pr vate lazy val thr ftToModelMap: Map[s. d aSafetyLabelType,  d aSafetyLabelType] = Map(
    s. d aSafetyLabelType.NsfwH ghPrec s on -> NsfwH ghPrec s on,
    s. d aSafetyLabelType.NsfwH ghRecall -> NsfwH ghRecall,
    s. d aSafetyLabelType.NsfwNearPerfect -> NsfwNearPerfect,
    s. d aSafetyLabelType.NsfwCard mage -> NsfwCard mage,
    s. d aSafetyLabelType.Pdna -> Pdna,
    s. d aSafetyLabelType.PdnaNoTreat nt fVer f ed -> PdnaNoTreat nt fVer f ed,
    s. d aSafetyLabelType.DmcaW h ld -> DmcaW h ld,
    s. d aSafetyLabelType.LegalDemandsW h ld -> LegalDemandsW h ld,
    s. d aSafetyLabelType.LocalLawsW h ld -> LocalLawsW h ld,
    s. d aSafetyLabelType.Reserved10 -> Deprecated,
    s. d aSafetyLabelType.Reserved11 -> Deprecated,
    s. d aSafetyLabelType.Reserved12 -> Deprecated,
    s. d aSafetyLabelType.Reserved13 -> Deprecated,
    s. d aSafetyLabelType.Reserved14 -> Deprecated,
    s. d aSafetyLabelType.Reserved15 -> Deprecated,
    s. d aSafetyLabelType.Reserved16 -> Deprecated,
    s. d aSafetyLabelType.Reserved17 -> Deprecated,
    s. d aSafetyLabelType.Reserved18 -> Deprecated,
    s. d aSafetyLabelType.Reserved19 -> Deprecated,
    s. d aSafetyLabelType.Reserved20 -> Deprecated,
    s. d aSafetyLabelType.Reserved21 -> Deprecated,
    s. d aSafetyLabelType.Reserved22 -> Deprecated,
    s. d aSafetyLabelType.Reserved23 -> Deprecated,
    s. d aSafetyLabelType.Reserved24 -> Deprecated,
    s. d aSafetyLabelType.Reserved25 -> Deprecated,
    s. d aSafetyLabelType.Reserved26 -> Deprecated,
    s. d aSafetyLabelType.Reserved27 -> Deprecated,
  )

  pr vate lazy val modelToThr ftMap: Map[ d aSafetyLabelType, s. d aSafetyLabelType] =
    (for ((k, v) <- thr ftToModelMap) y eld (v, k)) ++ Map(
      Deprecated -> s. d aSafetyLabelType.EnumUnknown d aSafetyLabelType(DeprecatedEnumValue),
    )

  case object NsfwH ghPrec s on extends  d aSafetyLabelType
  case object NsfwH ghRecall extends  d aSafetyLabelType
  case object NsfwNearPerfect extends  d aSafetyLabelType
  case object NsfwCard mage extends  d aSafetyLabelType
  case object Pdna extends  d aSafetyLabelType
  case object PdnaNoTreat nt fVer f ed extends  d aSafetyLabelType
  case object DmcaW h ld extends  d aSafetyLabelType
  case object LegalDemandsW h ld extends  d aSafetyLabelType
  case object LocalLawsW h ld extends  d aSafetyLabelType

  case object Deprecated extends  d aSafetyLabelType
  case object Unknown extends  d aSafetyLabelType

  def fromThr ft(safetyLabelType: s. d aSafetyLabelType):  d aSafetyLabelType =
    thr ftToModelMap.get(safetyLabelType) match {
      case So ( d aSafetyLabelType) =>  d aSafetyLabelType
      case _ =>
        safetyLabelType match {
          case s. d aSafetyLabelType.EnumUnknown d aSafetyLabelType(DeprecatedEnumValue) =>
            Deprecated
          case _ =>
            Unknown
        }
    }

  def toThr ft(safetyLabelType:  d aSafetyLabelType): s. d aSafetyLabelType = {
    modelToThr ftMap
      .get(safetyLabelType).getOrElse(UnknownThr ftSafetyLabelType)
  }
}
