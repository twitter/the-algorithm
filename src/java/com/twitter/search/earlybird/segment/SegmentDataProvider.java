package com.twitter.search.earlybird.segment;

/**
 * SegmentDataProvider provides information about available segments for indexing. This interface
 * abstracts away the actual source of the segment data. It might be a MySQL database, a mock
 * object, or a directory of flat files. It also provides access to the segmentInfoMap itself, which
 * contains information about the indexing state of Segments.
 */
public interface SegmentDataProvider extends SegmentProvider {
  /**
   * Returns the set of segment data record readers.
   */
  SegmentDataReaderSet getSegmentDataReaderSet();
}
