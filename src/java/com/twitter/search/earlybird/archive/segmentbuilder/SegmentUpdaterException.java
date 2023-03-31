package com.twitter.search.earlybird.archive.segmentbuilder;

import com.google.common.annotations.VisibleForTesting;

/**
 * Used when when SegmentUpdater fails processing segments.
 */
@VisibleForTesting
class SegmentUpdaterException extends Exception {
  SegmentUpdaterException(String msg) {
    super(msg);
  }
}
