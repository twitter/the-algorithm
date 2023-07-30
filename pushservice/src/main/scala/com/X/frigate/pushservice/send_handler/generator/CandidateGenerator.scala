package com.X.frigate.pushservice.send_handler.generator

import com.X.frigate.pushservice.model.PushTypes.RawCandidate
import com.X.frigate.pushservice.model.PushTypes.Target
import com.X.frigate.thriftscala.FrigateNotification
import com.X.util.Future

trait CandidateGenerator {

  /**
   * Build RawCandidate from FrigateNotification
   * @param target
   * @param frigateNotification
   * @return RawCandidate
   */
  def getCandidate(target: Target, frigateNotification: FrigateNotification): Future[RawCandidate]
}
