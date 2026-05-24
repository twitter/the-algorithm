package com.twitter.frigate.pushservice.send_handler.generator

import com.twitter.frigate.pushservice.model.PushTypes.RawCandidate
import com.twitter.frigate.pushservice.model.PushTypes.Target
import com.twitter.frigate.thriftscala.FrigateNotification
import com.twitter.util.Future

trait CandidateGenerator {

  /**
   * Build RawCandidate from FrigateNotification
   * @param target
   * @param frigateNotification
   * @return RawCandidate
   */
  def getCandidate(target: Target, frigateNotification: FrigateNotification): Future[RawCandidate]
}
