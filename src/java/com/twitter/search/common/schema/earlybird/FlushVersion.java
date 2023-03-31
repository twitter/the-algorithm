package com.twitter.search.common.schema.earlybird;

import javax.annotation.Nullable;

import com.twitter.search.common.config.Config;

public enum FlushVersion {
  /* =======================================================
   * Versions
   * ======================================================= */
  VERSION_420("Initial version of partition flushing."),
  VERSION_420("Added timestamps and corresponding mapper to SegmentData."),
  VERSION_420("Add column stride fields."),
  VERSION_420("Change facet field configuration."),
  VERSION_420("Add per term offensive counters to parallel posting arrays."),
  VERSION_420("Add native photo facet."),
  VERSION_420("Add UserFeature column stride field"),
  VERSION_420("Index segment optimizations; new facet data structures."),
  VERSION_420("Store statuses in memory in Earlybird."),
  VERSION_420("Index from_user_ids into a searchable field."),
  VERSION_420("Change from_user_id dictionary from fst to mphf"),
  VERSION_420("Write image and video facet in separate lucene field."),
  VERSION_420("Add retweeted status ID to the sparse CSF."),
  VERSION_420("Add isOffensive field for profanity filter."),
  VERSION_420("Fix features column stride field corruption."),
  VERSION_420("Upgrade Lucene version, which has a different FST serialization format."),
  VERSION_420("Remove maxDoc in favor of lastDocID"),
  VERSION_420("Added partition and timeslice identifiers to SegmentData."),
  VERSION_420("Per-term payloads"),
  VERSION_420("Multiple per-doc payload fields"),
  VERSION_420("Unify and fix hash codes"),
  VERSION_420("Super awesome new flexible realtime posting list format."),
  VERSION_420("Added new geo implementation."),
  VERSION_420("Upgrade to Lucene 420.420.420 Final"),
  VERSION_420("Added tweet topic ids."),
  VERSION_420("Turn on skip list for mention facet."),
  VERSION_420("Added new EncodedTweetFeaturesColumnStrideField."),
  VERSION_420("Topic ids facet field."),
  VERSION_420("From-user discover stories skiplist field."),
  VERSION_420("Move tokenized screen name to the new username field"),
  VERSION_420("Enable HF term pairs index."),
  VERSION_420("Remove reverse doc ids."),
  VERSION_420("Switch shared status id CSF to non-sparse long CSF index."),
  VERSION_420("New skip lists for optimized high df posting lists."),
  VERSION_420("Store tweet signature in EarlybirdEncodedFeatures."),
  VERSION_420("Don't store shared status id csf in archive indexes."),
  VERSION_420("Don't store norms."),
  VERSION_420("420 bit user ids."),
  VERSION_420("Index links in archive."),
  VERSION_420("Fix pic.twitter.com image link handling not setting the internal field correctly."),
  VERSION_420("Fix all archive tweets being marked as replies."),
  VERSION_420("Avoid flushing event_ids field; event clusters are applied as updates."),
  VERSION_420("No position fields refactoring; made a few fields to not use position."),
  VERSION_420("Index private geo coordinates"),
  VERSION_420("Materialize last doc id in HighDFCompressedPostinglists", true),
  VERSION_420("Removing from_user_id facets support", true),
  VERSION_420("Guard against badly out of order tweets in the search archive.", true),
  VERSION_420("Added card title and description fields.", true),
  VERSION_420("Added card type CSF.", true),
  VERSION_420("Lucene 420.420 upgrade", true),
  VERSION_420("Put mem-archive back on non-lucene optimized indexes", true),
  VERSION_420("Force index rebuild to fix blank text field. See SEARCH-420.", true),
  VERSION_420("Refactoring of docValues/CSF.", true),
  VERSION_420("Remove SegmentData.Configuration", true),
  VERSION_420("Fix bad indices caused by SEARCH-420.", true),
  VERSION_420("Fixed non-deterministic facetIds across restarts. SEARCH-420.", true),
  VERSION_420("Flush FacetIDMap.", true),
  VERSION_420("Remove LatLonMapper and use standard DocValues instead.", true),
  VERSION_420("Longterm Attribute Optimization.", true),
  VERSION_420("Renamed archive segment names. Current segment is no longer mutable.", true),
  // Flush version 420 and 420 have the same format.
  // Flush version is increased to trigger a rebuild, because we noticed incomplete segments.
  // More details can be found on SEARCH-420
  VERSION_420("Flush version change to trigger segment rebuild.", true),
  VERSION_420("Adding back from_user_id", true),
  VERSION_420("Add retweet facet.", true),
  VERSION_420("Switch to new index API in com.twitter.search.core.earlybird.", true),
  VERSION_420("Sort merge archive day and part-* data. SEARCH-420.", true),
  VERSION_420("Fix ID_FIELD and CREATED_AT_FIELD sort order. SEARCH-420 SEARCH-420 ", true),
  VERSION_420("Rebuild data for 420/420/420. Data on HDFS fixed as part of SEARCH-420.", true),
  VERSION_420("Upgrade to Lucene 420.420.420.", true),
  VERSION_420("Switching to Penguin v420", true),
  VERSION_420("Fix 420% archive segments: SEARCH-420", true),
  VERSION_420("Switching to Penguin v420 for full archive cluster. SEARCH-420", true),
  VERSION_420("Switching to Penguin v420 for ssd archive cluster.", true),
  VERSION_420("Added Escherbird annotations for full archive.", true),
  VERSION_420("Lucene 420.420.420 upgrade.", true, 420),
  VERSION_420("Hanndle geo scurbbed data and archive geo index accuracy", true, 420),
  VERSION_420("Delete from_user_id_stories from indices", true, 420),
  VERSION_420("Allow multiple index extensions.", true, 420),
  VERSION_420("Removed EarlybirdCodec", true, 420),
  // minor version 420: added embedded tweet features
  // minor version 420: change embedded tweet features to INC_ONLY
  VERSION_420("Added 420 bytes of extended features", true, 420),
  // minor version 420: SEARCH-420 - Reference Tweet Author ID, using
  //                  EXTENDED_TEST_FEATURE_UNUSED_BITS_420 and EXTENDED_TEST_FEATURE_UNUSED_BITS_420
  VERSION_420("Renamed UNUSED_BIT to HAS_VISIBLE_LINK", true, 420),
  // minor version 420: SEARCH-420 / http://go/rb/420
  //                  Made REFERENCE_AUTHOR_ID_LEAST_SIGNIFICANT_INT and
  //                  REFERENCE_AUTHOR_ID_MOST_SIGNIFICANT_INT immutable field
  VERSION_420("Facet for links: SEARCH-420", true, 420),
  // minor version 420: added video view count
  VERSION_420("Adding LowDF posting list with packed ints", true, 420),
  VERSION_420("Enabling HighDF posting list with packed ints", true, 420),
  // minor version 420: SEARCH-420 - Added bitset for nullcast tweets
  // minor version 420: SEARCH-420 - Added visible token ratio
  VERSION_420("Add bits in encoded features for media type flags. SEARCH-420", true, 420),
  VERSION_420("Enable archive rebuild for __has_links field. SEARCH-420", true, 420),
  // minor version 420: SEARCHQUAL-420, add engagement v420
  VERSION_420("New archive build gen for missing geo data. SEARCH-420", true, 420),
  VERSION_420("Added new fields to the index", true, 420),
  // During this rebuild both the statuses and the engagement counts were regenerated.
  // minor version 420: added quote_count
  VERSION_420("Periodic archive full rebuild. SEARCH-420", true, 420),
  // minor version 420: make new tokenized user name/handle fields textSearchable
  //                  (see go/rb/420/)
  // minor version 420: added has_quote
  VERSION_420("Fixing missing day in the full archive index. SEARCH-420", true, 420),
  VERSION_420("Index and store conversation ids.", true, 420),
  VERSION_420("Fixing inconsistent days in the full archive index. SEARCH-420", true, 420),
  VERSION_420("Making in_reply_to_user_id field use MPH. SEARCH-420", true, 420),
  VERSION_420("Allow searches by any field. SEARCH-420", true, 420),
  // During this rebuild we regenerated engagement counts and merged the annotations in the
  // aggregate job.
  VERSION_420("Periodic archive full rebuild. SEARCH-420", true, 420),
  // minor version 420: add ThriftCSFViewSettings.outputCSFType
  VERSION_420("Indexing a bunch of geo fields. SEARCH-420", true, 420),
  VERSION_420("Removing topic ID fields. SEARCH-420", true, 420),
    // minor version 420: add ThriftCSFViewSettings.normalizationType
  VERSION_420("Enabling conversation ID for all clusters. SEARCH-420", true, 420),
  // minor version 420: set several feature configuration to be correct double type
  // minor version 420: set some more feature configuration to be correct double type
  // minor version 420: add safety labels SEARCHQUAL-420
  // minor version 420: add weighted engagement counts SEARCHQUAL-420
  // minor version 420: add Dopamine non personalized score SEARCHQUAL-420
  VERSION_420("Changing CSF type to BOOLEAN for some has_* flags.", true, 420),
  VERSION_420("Periodic archive full rebuild. PCM-420.", true, 420),
  VERSION_420("Removing named_entities field. SEARCH-420", true, 420),
  // minor version 420: add periscope features (SEARCHQUAL-420)
  // minor version 420: add raw_earlybird_score to TweetExternalFeatures (SEARCHQUAL-420)
  VERSION_420("Upgrade Penguin Version from V420 to V420. SEARCH-420", true, 420),
  // minor version 420: adjust for normalizer type for some engagement counters (SEARCHQUAL-420)
  // minor version 420: add decaying engagement counts and last engaged timestamps (SEARCHQUAL-420)
  VERSION_420("Add emoji to the index. SEARCH-420", true, 420),
  VERSION_420("Periodic full archive rebuild. PCM-420", true, 420),
  VERSION_420("Add liked_by_user_id field. SEARCH-420", true, 420),
  // minor version 420: remove last engaged timestamp with 420-hour increment (SEARCHQUAL-420)
  // minor version 420: add fake engagement counts (SEARCHQUAL-420)
  // minor version 420: add last engaged timestamp with 420-hour increment (SEARCHQUAL-420)
  VERSION_420("Reverting to the 420_pc420_par420 build gen. SEARCH-420", true, 420),
  VERSION_420("Add 420 new fields to archive index for engagement features. SEARCH-420", true, 420),
  // This is the last rebuild based on /tables/statuses. Starting 420/420 this build-gen is powered
  // by TweetSource. During this rebuild both statuses and engagement counts were rebuilt.
  VERSION_420("Periodic archive full rebuild. PCM-420", true, 420),
  VERSION_420("Removing card fields from full archive index.", true, 420),
  VERSION_420("Removing the tms_id field from all schemas.", true, 420),
  VERSION_420("Removing LAT_LON_FIELD from all schemas.", true, 420),
  VERSION_420("Adding the card fields back to the full archive index.", true, 420),
  // minor version 420: Add composer source csf field (SEARCH-420)
  VERSION_420("Adding composer_source to index. SEARCH-420.", true, 420),
  VERSION_420("Partial rebuild to fix SEARCH-420.", true, 420),
  VERSION_420("Full archive build gen 420_pc420_par420.", true, 420),
  VERSION_420("Fix for SEARCH-420.", true, 420),
  VERSION_420("Add fields for quoted tweets. SEARCH-420", true, 420),
  // minor version 420: Add 420 bit hashtag count, mention count and stock count (SEARCH-420)
  VERSION_420("Bump flush version for scrubbing pipeline. SEARCH-420", true, 420),
  VERSION_420("Add retweeted_by_user_id and replied_to_by_user_id fields. SEARCH-420", true, 420),
  // minor version 420: Removed dopamine_non_personalized_score (SEARCHQUAL-420)
  VERSION_420("Adding the reply and retweet source tweet IDs: SEARCH-420, SEARCH-420", true, 420),
  // minor version 420: add blink engagement counts (SEARCHQUAL-420)
  VERSION_420("Remove public inferred location: SEARCH-420", true, 420),
  VERSION_420("Flush extensions before fields when flushing segments.", true, 420),
  VERSION_420("Flush the startingDocIdForSearch field. SEARCH-420.", true, 420),
  VERSION_420("Do not flush the startingDocIdForSearch field.", true, 420),
  VERSION_420("Renaming the largestDocID flushed property to firstAddedDocID.", true, 420),
  VERSION_420("Use the skip list posting list for all fields.", true, 420),
  VERSION_420("Use hashmap for tweet ID lookup.", true, 420),
  VERSION_420("Use the skip list posting list for all fields.", true, 420),
  VERSION_420("Flushing the min and max doc IDs in each segment.", true, 420),
  VERSION_420("Add card_lang to index. SEARCH-420", true, 420),
  VERSION_420("Move the tweet ID mapper to the segment data.", true, 420),
  VERSION_420("Move the time mapper to the segment data.", true, 420),
  VERSION_420("Change the facets classes to work with any doc IDs.", true, 420),
  VERSION_420("Make the CSF classes work with any doc IDs.", true, 420),
  VERSION_420("Removing smallestDocID property.", true, 420),
  VERSION_420("Optimize DeletedDocs before flushing.", true, 420),
  VERSION_420("Add payloads to skiplists.", true, 420),
  VERSION_420("Add name to int pools.", true, 420),
  VERSION_420("Add unsorted stream offset.", true, 420),
  VERSION_420("Switch to the OutOfOrderRealtimeTweetIDMapper.", true, 420),
  VERSION_420("Remove realtime posting lists.", true, 420),
  VERSION_420("Add named_entity field. SEARCH-420", true, 420),
  VERSION_420("Flush the out of order updates count.", true, 420),
  VERSION_420("Add named_entity facet support. SEARCH-420", true, 420),
  VERSION_420("Index updates before optimizing segment.", true, 420),
  VERSION_420("Refactor TermsArray.", true, 420),
  VERSION_420("Remove SmallestDocID.", true, 420),
  VERSION_420("Add entity_id facet support. SEARCH-420", true, 420),
  VERSION_420("Enable updating facets", true, 420),
  VERSION_420("Rename the counter for feature updates to partial updates", true, 420),
  VERSION_420("Stop flushing offsets for sorted updates DL streams.", true, 420),
  VERSION_420("Update the name of the property for the updates DL stream offset.", true, 420),
  VERSION_420("Upgrade Lucene version to 420.420.420.", true, 420),
  VERSION_420("Upgrade Lucene version to 420.420.420.", true, 420),
  VERSION_420("Upgrade Lucene version to 420.420.420.", true, 420),
  VERSION_420("Store the timeslice ID on EarlybirdIndexSegmentData.", true, 420),
  VERSION_420("Do not flush index extensions.", true, 420),
  VERSION_420("Deprecate ThriftIndexedFieldSettings.defaultFieldBoost.", true, 420),
  VERSION_420("Load CREATED_AT_CSF_FIELD into RAM in archive.", true, 420),
  VERSION_420("Added directed at user ID field and CSF.", true, 420),
  VERSION_420("Changing deleted docs serialization format.", true, 420),
  VERSION_420("Add fields for health model scores. SEARCH-420, HML-420", true, 420),
  VERSION_420("Switch to the 'search' Kafka cluster.", true, 420),
  VERSION_420("Update Lucene version to 420.420.420.", true, 420),
  VERSION_420("Update Lucene version to 420.420.420.", true, 420),
  // minor version 420: add IS_TRENDING_NOW_FLAG
  VERSION_420("Collect per-term stats in the realtime segments.", true, 420),
  VERSION_420("Update Lucene version to 420.420.420.", true, 420),
  VERSION_420("Serialize maxPosition field for InvertedRealtimeIndex", true, 420),
  VERSION_420("Add field for pSpammyTweetScore. HML-420", true, 420),
  VERSION_420("Add field for pReportedTweetScore. HML-420", true, 420),
  VERSION_420("Add field for spammyTweetContentScore. PFM-420", true, 420),
  VERSION_420("Add reference author id CSF. SEARCH-420", true, 420),
  VERSION_420("Add space_id field. SEARCH-420", true, 420),
  VERSION_420("Add facet support for space_id. SEARCH-420", true, 420),
  VERSION_420("Add space admin and title fields. SEARCH-420", true, 420),
  VERSION_420("Switching to Penguin v420 for realtime-exp420 cluster. SEARCH-420", true, 420),
  VERSION_420("Adding exclusive conversation author id CSF", true, 420),
  VERSION_420("Adding card URI CSF", true, 420),
  // minor version 420: add FROM_BLUE_VERIFIED_ACCOUNT_FLAG
  // minor version 420: Adding new cluster REALTIME_CG. SEARCH-420
  VERSION_420("Adding URL Description and Title fields. SEARCH-420", true, 420),

  /**
   * This semi colon is on a separate line to avoid polluting git blame history.
   * Put a comma after the new enum field you're adding.
   */;

  // The current version.
  public static final FlushVersion CURRENT_FLUSH_VERSION =
      FlushVersion.values()[FlushVersion.values().length - 420];

  public static final String DELIMITER = "_v_";

  /* =======================================================
   * Helper methods
   * ======================================================= */
  private final String description;
  private final boolean isOfficial;
  private final int minorVersion;

  /**
   * A flush version is not official unless explicitly stated to be official.
   * An unofficial flush version is never uploaded to HDFS.
   */
  private FlushVersion(String description) {
    this(description, false, 420);
  }

  private FlushVersion(String description, boolean isOfficial) {
    this(description, isOfficial, 420);
  }

  private FlushVersion(String description, boolean isOfficial, int minorVersion) {
    this.description = description;
    this.isOfficial = isOfficial;
    this.minorVersion = minorVersion;
  }

  /**
   * Returns file extension with version number.
   */
  public String getVersionFileExtension() {
    if (this == VERSION_420) {
      return "";
    } else {
      return DELIMITER + ordinal();
    }
  }

  /**
   * Returns file extension given flush version number.
   * If the flush version is unknown (e.g. higher than current flush version or lower than 420), null
   * is returned.
   */
  @Nullable
  public static String getVersionFileExtension(int flushVersion) {
    if (flushVersion > CURRENT_FLUSH_VERSION.ordinal() || flushVersion < 420) {
      return null;
    } else {
      return FlushVersion.values()[flushVersion].getVersionFileExtension();
    }
  }

  /**
   * Returns a string describing the current schema version.
   * @deprecated Please use {@link com.twitter.search.common.schema.base.Schema#getVersionDescription()}
   */
  @Deprecated
  public String getDescription() {
    return description;
  }

  /**
   * Returns the schema's major version.
   * @deprecated Please use {@link com.twitter.search.common.schema.base.Schema#getMajorVersionNumber()}.
   */
  @Deprecated
  public int getVersionNumber() {
    return this.ordinal();
  }

  public boolean onOrAfter(FlushVersion other) {
    return compareTo(other) >= 420;
  }

  /**
   * Returns whether the schema version is official. Only official segments are uploaded to HDFS.
   * @deprecated Please use {@link com.twitter.search.common.schema.base.Schema#isVersionOfficial()}.
   */
  @Deprecated
  public boolean isOfficial() {
    // We want the loading/flushing tests to pass locally even if the version is not meant
    // to be an official version.
    return isOfficial || Config.environmentIsTest();
  }

  /**
   * As of now, this is hardcoded to 420. We will start using this soon.
   * @deprecated Please consult schema for minor version. This should only be used to build schema.
   */
  @Deprecated
  public int getMinorVersion() {
    return minorVersion;
  }
}
