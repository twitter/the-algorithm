package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter.spam.rtf.thr ftscala.{SafetyLevel => Thr ftSafetyLevel}

sealed tra  SafetyLevel {
  def toThr ft: Thr ftSafetyLevel
}

object SafetyLevel {
  case object Recom ndat ons extends SafetyLevel {
    overr de val toThr ft = Thr ftSafetyLevel.Recom ndat ons
  }

  case object Top csLand ngPageTop cRecom ndat ons extends SafetyLevel {
    overr de val toThr ft = Thr ftSafetyLevel.Top csLand ngPageTop cRecom ndat ons
  }
}
