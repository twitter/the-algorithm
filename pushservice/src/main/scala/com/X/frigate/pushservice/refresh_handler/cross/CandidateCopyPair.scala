package com.X.frigate.pushservice.refresh_handler.cross

import com.X.frigate.common.util.MRPushCopy
import com.X.frigate.pushservice.model.PushTypes.RawCandidate

/**
 *
 * @param candidate: [[RawCandidate]] is a recommendation candidate
 * @param pushCopy: [[MRPushCopy]] eligible for candidate
 */
case class CandidateCopyPair(candidate: RawCandidate, pushCopy: MRPushCopy)
