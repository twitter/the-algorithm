package com.twitter.search.common.util.earlybird;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import com.twitter.search.common.query.thriftjava.EarlyTerminationInfo;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;

public final class ResponseMergerUtils {

  // Utility class, disallow instantiation.
  private ResponseMergerUtils() {
  }

  /**
   * Merges early termination infos from several earlybird responses.
   *
   * @param responses earlybird responses to merge the early termination infos from
   * @return merged early termination info
   */
  public static EarlyTerminationInfo mergeEarlyTerminationInfo(List<EarlybirdResponse> responses) {
    EarlyTerminationInfo etInfo = new EarlyTerminationInfo(false);
    Set<String> etReasonSet = Sets.newHashSet();
    // Fill in EarlyTerminationStatus
    for (EarlybirdResponse ebResp : responses) {
      if (ebResp.isSetEarlyTerminationInfo()
          && ebResp.getEarlyTerminationInfo().isEarlyTerminated()) {
        etInfo.setEarlyTerminated(true);
        if (ebResp.getEarlyTerminationInfo().isSetEarlyTerminationReason()) {
          etReasonSet.add(ebResp.getEarlyTerminationInfo().getEarlyTerminationReason());
        }
        if (ebResp.getEarlyTerminationInfo().isSetMergedEarlyTerminationReasons()) {
          etReasonSet.addAll(ebResp.getEarlyTerminationInfo().getMergedEarlyTerminationReasons());
        }
      }
    }
    if (etInfo.isEarlyTerminated()) {
      etInfo.setMergedEarlyTerminationReasons(Lists.newArrayList(etReasonSet));
    }
    return etInfo;
  }
}
