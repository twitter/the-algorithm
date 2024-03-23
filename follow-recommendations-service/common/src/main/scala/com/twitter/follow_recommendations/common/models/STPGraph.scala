package com.ExTwitter.follow_recommendations.common.models

import com.ExTwitter.hermit.model.Algorithm.Algorithm
import com.ExTwitter.wtf.scalding.jobs.strong_tie_prediction.FirstDegreeEdge
import com.ExTwitter.wtf.scalding.jobs.strong_tie_prediction.FirstDegreeEdgeInfo
import com.ExTwitter.wtf.scalding.jobs.strong_tie_prediction.SecondDegreeEdge

case class PotentialFirstDegreeEdge(
  userId: Long,
  connectingId: Long,
  algorithm: Algorithm,
  score: Double,
  edgeInfo: FirstDegreeEdgeInfo)

case class IntermediateSecondDegreeEdge(
  connectingId: Long,
  candidateId: Long,
  edgeInfo: FirstDegreeEdgeInfo)

case class STPGraph(
  firstDegreeEdgeInfoList: List[FirstDegreeEdge],
  secondDegreeEdgeInfoList: List[SecondDegreeEdge])
