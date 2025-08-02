package com.twitter.visibility.models

import com.twitter.visibility.safety_label_store.{thriftscala => s}
import com.twitter.visibility.util.NamingUtils

sealed trait MediaSafetyLabelType etwittertends SafetyLabelType {
  lazy val name: String = NamingUtils.getFriendlyName(this)
}

object MediaSafetyLabelType etwittertends SafetyLabelType {

  val List: List[MediaSafetyLabelType] = s.MediaSafetyLabelType.list.map(fromThrift)

  val ActiveLabels: List[MediaSafetyLabelType] = List.filter { labelType =>
    labelType != Unknown && labelType != Deprecated
  }

  private lazy val nameToValueMap: Map[String, MediaSafetyLabelType] =
    List.map(l => l.name.toLowerCase -> l).toMap
  def fromName(name: String): Option[MediaSafetyLabelType] = nameToValueMap.get(name.toLowerCase)

  private val UnknownThriftSafetyLabelType =
    s.MediaSafetyLabelType.EnumUnknownMediaSafetyLabelType(UnknownEnumValue)

  private lazy val thriftToModelMap: Map[s.MediaSafetyLabelType, MediaSafetyLabelType] = Map(
    s.MediaSafetyLabelType.NsfwHighPrecision -> NsfwHighPrecision,
    s.MediaSafetyLabelType.NsfwHighRecall -> NsfwHighRecall,
    s.MediaSafetyLabelType.NsfwNearPerfect -> NsfwNearPerfect,
    s.MediaSafetyLabelType.NsfwCardImage -> NsfwCardImage,
    s.MediaSafetyLabelType.Pdna -> Pdna,
    s.MediaSafetyLabelType.PdnaNoTreatmentIfVerified -> PdnaNoTreatmentIfVerified,
    s.MediaSafetyLabelType.DmcaWithheld -> DmcaWithheld,
    s.MediaSafetyLabelType.LegalDemandsWithheld -> LegalDemandsWithheld,
    s.MediaSafetyLabelType.LocalLawsWithheld -> LocalLawsWithheld,
    s.MediaSafetyLabelType.Reserved10 -> Deprecated,
    s.MediaSafetyLabelType.Reserved11 -> Deprecated,
    s.MediaSafetyLabelType.Reserved12 -> Deprecated,
    s.MediaSafetyLabelType.Reserved13 -> Deprecated,
    s.MediaSafetyLabelType.Reserved14 -> Deprecated,
    s.MediaSafetyLabelType.Reserved15 -> Deprecated,
    s.MediaSafetyLabelType.Reserved16 -> Deprecated,
    s.MediaSafetyLabelType.Reserved17 -> Deprecated,
    s.MediaSafetyLabelType.Reserved18 -> Deprecated,
    s.MediaSafetyLabelType.Reserved19 -> Deprecated,
    s.MediaSafetyLabelType.Reserved20 -> Deprecated,
    s.MediaSafetyLabelType.Reserved21 -> Deprecated,
    s.MediaSafetyLabelType.Reserved22 -> Deprecated,
    s.MediaSafetyLabelType.Reserved23 -> Deprecated,
    s.MediaSafetyLabelType.Reserved24 -> Deprecated,
    s.MediaSafetyLabelType.Reserved25 -> Deprecated,
    s.MediaSafetyLabelType.Reserved26 -> Deprecated,
    s.MediaSafetyLabelType.Reserved27 -> Deprecated,
  )

  private lazy val modelToThriftMap: Map[MediaSafetyLabelType, s.MediaSafetyLabelType] =
    (for ((k, v) <- thriftToModelMap) yield (v, k)) ++ Map(
      Deprecated -> s.MediaSafetyLabelType.EnumUnknownMediaSafetyLabelType(DeprecatedEnumValue),
    )

  case object NsfwHighPrecision etwittertends MediaSafetyLabelType
  case object NsfwHighRecall etwittertends MediaSafetyLabelType
  case object NsfwNearPerfect etwittertends MediaSafetyLabelType
  case object NsfwCardImage etwittertends MediaSafetyLabelType
  case object Pdna etwittertends MediaSafetyLabelType
  case object PdnaNoTreatmentIfVerified etwittertends MediaSafetyLabelType
  case object DmcaWithheld etwittertends MediaSafetyLabelType
  case object LegalDemandsWithheld etwittertends MediaSafetyLabelType
  case object LocalLawsWithheld etwittertends MediaSafetyLabelType

  case object Deprecated etwittertends MediaSafetyLabelType
  case object Unknown etwittertends MediaSafetyLabelType

  def fromThrift(safetyLabelType: s.MediaSafetyLabelType): MediaSafetyLabelType =
    thriftToModelMap.get(safetyLabelType) match {
      case Some(mediaSafetyLabelType) => mediaSafetyLabelType
      case _ =>
        safetyLabelType match {
          case s.MediaSafetyLabelType.EnumUnknownMediaSafetyLabelType(DeprecatedEnumValue) =>
            Deprecated
          case _ =>
            Unknown
        }
    }

  def toThrift(safetyLabelType: MediaSafetyLabelType): s.MediaSafetyLabelType = {
    modelToThriftMap
      .get(safetyLabelType).getOrElse(UnknownThriftSafetyLabelType)
  }
}
