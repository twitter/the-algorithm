package com.tw ter.v s b l y.models

sealed tra  V olat onLevel extends Product w h Ser al zable {
  val level:  nt
}

object V olat onLevel {

  case object DefaultLevel extends V olat onLevel {
    overr de val level:  nt = 0
  }

  case object Level1 extends V olat onLevel {
    overr de val level:  nt = 1
  }

  case object Level2 extends V olat onLevel {
    overr de val level:  nt = 2
  }

  case object Level3 extends V olat onLevel {
    overr de val level:  nt = 3
  }

  case object Level4 extends V olat onLevel {
    overr de val level:  nt = 4
  }

  pr vate val safetyLabelToV olat onLevel: Map[T etSafetyLabelType, V olat onLevel] = Map(
    T etSafetyLabelType.FosnrHatefulConduct -> Level3,
    T etSafetyLabelType.FosnrHatefulConductLowSever ySlur -> Level1,
  )

  val v olat onLevelToSafetyLabels: Map[V olat onLevel, Set[T etSafetyLabelType]] =
    safetyLabelToV olat onLevel.groupBy { case (_, v olat onLevel) => v olat onLevel }.map {
      case (v olat onLevel, collect on) => (v olat onLevel, collect on.keySet)
    }

  def fromT etSafetyLabel(
    t etSafetyLabel: T etSafetyLabel
  ): V olat onLevel = {
    safetyLabelToV olat onLevel.getOrElse(t etSafetyLabel.labelType, DefaultLevel)
  }

  def fromT etSafetyLabelOpt(
    t etSafetyLabel: T etSafetyLabel
  ): Opt on[V olat onLevel] = {
    safetyLabelToV olat onLevel.get(t etSafetyLabel.labelType)
  }

}
