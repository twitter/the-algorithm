package com.X.search.earlybird.archive.segmentbuilder;

import java.io.IOException;

/**
 * Used if exceptions are thrown during creating new SegmentInfo during the indexing loop
 */
class SegmentInfoConstructionException extends Exception {
  SegmentInfoConstructionException(String msg, IOException e) {
    super(msg, e);
  }
}
