package com.twitter.frigate.pushservice.refresh_handler.cross

import com.twitter.frigate.common.util.MRPushCopy
import com.twitter.frigate.pushservice.model.PushTypes.RawCandidate

/**
 *
 * @param candidate: [[RawCandidate]] is a recommendation candidate
 * @param pushCopy: [[MRPushCopy]] eligible for candidate
 */
case class CandidateCopyPair(candidate: RawCandidate, pushCopy: MRPushCopy)
