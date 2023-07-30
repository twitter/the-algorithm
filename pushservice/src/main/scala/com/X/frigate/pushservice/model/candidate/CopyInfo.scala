package com.X.frigate.pushservice.model.candidate

import com.X.frigate.common.util.MRPushCopy
import com.X.frigate.common.util.MrPushCopyObjects
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.util.CandidateUtil

case class CopyIds(
  pushCopyId: Option[Int] = None,
  ntabCopyId: Option[Int] = None,
  aggregationId: Option[String] = None)

trait CopyInfo {
  self: PushCandidate =>

  import com.X.frigate.data_pipeline.common.FrigateNotificationUtil._

  def getPushCopy: Option[MRPushCopy] =
    pushCopyId match {
      case Some(pushCopyId) => MrPushCopyObjects.getCopyFromId(pushCopyId)
      case _ =>
        crt2PushCopy(
          commonRecType,
          CandidateUtil.getSocialContextActionsFromCandidate(self).size
        )
    }

  def pushCopyId: Option[Int]

  def ntabCopyId: Option[Int]

  def copyAggregationId: Option[String]
}
