package com.twitter.frigate.pushservice.model.ntab

import com.twitter.frigate.common.util.MRNtabCopy
import com.twitter.frigate.common.util.MrNtabCopyObjects
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.take.InvalidNtabCopyIdException
import com.twitter.frigate.pushservice.take.NtabCopyIdNotFoundException

trait CandidateNTabCopy {
  self: PushCandidate =>

  def ntabCopy: MRNtabCopy =
    ntabCopyId
      .map(getNtabCopyFromCopyId).getOrElse(
        throw new NtabCopyIdNotFoundException(s"NtabCopyId not found for $commonRecType"))

  private def getNtabCopyFromCopyId(ntabCopyId: Int): MRNtabCopy =
    MrNtabCopyObjects
      .getCopyFromId(ntabCopyId).getOrElse(
        throw new InvalidNtabCopyIdException(s"Unknown NTab Copy ID: $ntabCopyId"))
}
