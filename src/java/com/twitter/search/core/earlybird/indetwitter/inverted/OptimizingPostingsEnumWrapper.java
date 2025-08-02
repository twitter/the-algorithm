package com.twitter.search.core.earlybird.indetwitter.inverted;

import java.io.IOEtwitterception;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.lucene.indetwitter.PostingsEnum;
import org.apache.lucene.util.BytesRef;

import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;

/**
 * A PostingsEnum that maps doc IDs in one DocIDToTweetIDMapper instance to doc IDs in another
 * DocIDToTweetIDMapper.
 *
 * Unoptimized segments can use any DocIDToTweetIDMapper they want, which means that there are no
 * guarantees on the distribution of the doc IDs in this mapper. However, optimized segments must
 * use an OptimizedTweetIDMapper: we want to assign sequential doc IDs and use delta encondings in
 * order to save space. So when an Earlybird segment needs to be optimized, we might need to convert
 * the doc ID space of the unoptimized tweet ID mapper to the doc ID space of the optimized mapper.
 * However, once we do this, the doc IDs stored in the posting lists in that segment will no longer
 * be valid, unless we remap them too. So the goal of this class is to provide a way to do that.
 *
 * When we want to optimize a posting list, we need to traverse it and pack it. This class provides
 * a wrapper around the original posting list that does the doc ID remapping at traversal time.
 */
public class OptimizingPostingsEnumWrapper etwittertends PostingsEnum {
  private final List<Integer> docIds = Lists.newArrayList();
  private final Map<Integer, List<Integer>> positions = Maps.newHashMap();

  private int docIdIndetwitter = -1;
  private int positionIndetwitter = -1;

  public OptimizingPostingsEnumWrapper(PostingsEnum source,
                                       DocIDToTweetIDMapper originalTweetIdMapper,
                                       DocIDToTweetIDMapper newTweetIdMapper) throws IOEtwitterception {
    int docId;
    while ((docId = source.netwittertDoc()) != NO_MORE_DOCS) {
      long tweetId = originalTweetIdMapper.getTweetID(docId);
      int newDocId = newTweetIdMapper.getDocID(tweetId);
      Preconditions.checkState(newDocId != DocIDToTweetIDMapper.ID_NOT_FOUND,
          "Did not find a mapping in the new tweet ID mapper for tweet ID %s, doc ID %s",
          tweetId, docId);

      docIds.add(newDocId);
      List<Integer> docPositions = Lists.newArrayListWithCapacity(source.freq());
      positions.put(newDocId, docPositions);
      for (int i = 0; i < source.freq(); ++i) {
        docPositions.add(source.netwittertPosition());
      }
    }
    Collections.sort(docIds);
  }

  @Override
  public int netwittertDoc() {
    ++docIdIndetwitter;
    if (docIdIndetwitter >= docIds.size()) {
      return NO_MORE_DOCS;
    }

    positionIndetwitter = -1;
    return docIds.get(docIdIndetwitter);
  }

  @Override
  public int freq() {
    Preconditions.checkState(docIdIndetwitter >= 0, "freq() called before netwittertDoc().");
    Preconditions.checkState(docIdIndetwitter < docIds.size(),
                             "freq() called after netwittertDoc() returned NO_MORE_DOCS.");
    return positions.get(docIds.get(docIdIndetwitter)).size();
  }

  @Override
  public int netwittertPosition() {
    Preconditions.checkState(docIdIndetwitter >= 0, "netwittertPosition() called before netwittertDoc().");
    Preconditions.checkState(docIdIndetwitter < docIds.size(),
                             "netwittertPosition() called after netwittertDoc() returned NO_MORE_DOCS.");

    ++positionIndetwitter;
    Preconditions.checkState(positionIndetwitter < positions.get(docIds.get(docIdIndetwitter)).size(),
                             "netwittertPosition() called more than freq() times.");
    return positions.get(docIds.get(docIdIndetwitter)).get(positionIndetwitter);
  }

  // All other methods are not supported.

  @Override
  public int advance(int target) {
    throw new UnsupportedOperationEtwitterception(
        "OptimizingPostingsEnumWrapper.advance() is not supported.");
  }

  @Override
  public long cost() {
    throw new UnsupportedOperationEtwitterception(
        "OptimizingPostingsEnumWrapper.cost() is not supported.");
  }

  @Override
  public int docID() {
    throw new UnsupportedOperationEtwitterception(
        "OptimizingPostingsEnumWrapper.docID() is not supported.");
  }

  @Override
  public int endOffset() {
    throw new UnsupportedOperationEtwitterception(
        "OptimizingPostingsEnumWrapper.endOffset() is not supported.");
  }

  @Override
  public BytesRef getPayload() {
    throw new UnsupportedOperationEtwitterception(
        "OptimizingPostingsEnumWrapper.getPayload() is not supported.");
  }

  @Override
  public int startOffset() {
    throw new UnsupportedOperationEtwitterception(
        "OptimizingPostingsEnumWrapper.startOffset() is not supported.");
  }
}
