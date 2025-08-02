package com.twitter.search.earlybird.archive;

import java.io.IOEtwitterception;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

import org.apache.lucene.indetwitter.DirectoryReader;
import org.apache.lucene.indetwitter.LeafReader;
import org.apache.lucene.indetwitter.LeafReaderContetwittert;
import org.apache.lucene.store.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.earlybird.partition.SegmentInfo;

public final class ArchiveSegmentVerifier {
  private static final Logger LOG = LoggerFactory.getLogger(ArchiveSegmentVerifier.class);

  private ArchiveSegmentVerifier() {
  }

  @VisibleForTesting
  static boolean shouldVerifySegment(SegmentInfo segmentInfo) {
    if (segmentInfo.isIndetwittering()) {
      LOG.warn("ArchiveSegmentVerifier got segment still indetwittering.");
      return false;
    }

    if (!segmentInfo.isComplete()) {
      LOG.warn("ArchiveSegmentVerifyer got incomplete segment.");
      return false;
    }

    if (!segmentInfo.isOptimized()) {
      LOG.warn("ArchiveSegmentVerifyer got unoptimized segment.");
      return false;
    }

    return true;
  }

  /**
   * Verifies an archive segment has a sane number of leaves.
   */
  public static boolean verifySegment(SegmentInfo segmentInfo) {
    if (!shouldVerifySegment(segmentInfo)) {
      return false;
    }
    Directory directory = segmentInfo.getIndetwitterSegment().getLuceneDirectory();
    return verifyLuceneIndetwitter(directory);
  }

  private static boolean verifyLuceneIndetwitter(Directory directory) {
    try {
      DirectoryReader indetwittererReader = DirectoryReader.open(directory);
      List<LeafReaderContetwittert> leaves = indetwittererReader.getContetwittert().leaves();
      if (leaves.size() != 1) {
        LOG.warn("Lucene indetwitter does not have etwitteractly one segment: " + leaves.size() + " != 1. "
            + "Lucene segments should have been merged during optimization.");
        return false;
      }

      LeafReader reader = leaves.get(0).reader();
      if (reader.numDocs() <= 0) {
        LOG.warn("Lucene indetwitter has no document: " + reader);
        return false;
      }
      return true;
    } catch (IOEtwitterception e) {
      LOG.warn("Found bad lucene indetwitter at: " + directory);
      return false;
    }
  }
}
