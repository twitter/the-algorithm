package com.twitter.search.core.earlybird.index;

import java.io.IOException;

/**
 * An interface for mapping the doc IDs in our indexes to the corresponding tweet IDs.
 */
public interface DocIDToTweetIDMapper {
  /** A constant indicating that a doc ID was not found in the mapper. */
  int ID_NOT_FOUND = -1;

  /**
   * Returns the tweet ID corresponding to the given doc ID.
   *
   * @param docID The doc ID stored in our indexes.
   * @return The tweet ID corresponding to the given doc ID.
   */
  long getTweetID(int docID);

  /**
   * Returns the internal doc ID corresponding to the given tweet ID. Returns ID_NOT_FOUND if the
   * given tweet ID cannot be found in the index.
   *
   * @param tweetID The tweet ID.
   * @return The doc ID corresponding to the given tweet ID.
   */
  int getDocID(long tweetID) throws IOException;

  /**
   * Returns the smallest valid doc ID in this mapper that's strictly higher than the given doc ID.
   * If no such doc ID exists, ID_NOT_FOUND is returned.
   *
   * @param docID The current doc ID.
   * @return The smallest valid doc ID in this mapper that's strictly higher than the given doc ID,
   *         or a negative number, if no such doc ID exists.
   */
  int getNextDocID(int docID);

  /**
   * Returns the largest valid doc ID in this mapper that's strictly smaller than the given doc ID.
   * If no such doc ID exists, ID_NOT_FOUND is returned.
   *
   * @param docID The current doc ID.
   * @return The largest valid doc ID in this mapper that's strictly smaller than the given doc ID,
   *         or a negative number, if no such doc ID exists.
   */
  int getPreviousDocID(int docID);

  /**
   * Returns the total number of documents stored in this mapper.
   *
   * @return The total number of documents stored in this mapper.
   */
  int getNumDocs();

  /**
   * Adds a mapping for the given tweet ID. Returns the doc ID assigned to this tweet ID.
   * This method does not check if the tweet ID is already present in the mapper. It always assigns
   * a new doc ID to the given tweet.
   *
   * @param tweetID The tweet ID to be added to the mapper.
   * @return The doc ID assigned to the given tweet ID, or ID_NOT_FOUND if a doc ID could not be
   *         assigned to this tweet.
   */
  int addMapping(long tweetID);

  /**
   * Converts the current DocIDToTweetIDMapper to a DocIDToTweetIDMapper instance with the same
   * tweet IDs. The tweet IDs in the original and optimized instances can be mapped to different
   * doc IDs. However, we expect doc IDs to be assigned such that tweets created later have smaller
   * have smaller doc IDs.
   *
   * This method should be called when an earlybird segment is being optimized, right before
   * flushing it to disk.
   *
   * @return An optimized DocIDToTweetIDMapper with the same tweet IDs.
   */
  DocIDToTweetIDMapper optimize() throws IOException;
}
