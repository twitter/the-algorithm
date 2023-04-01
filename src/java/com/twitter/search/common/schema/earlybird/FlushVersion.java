package com.twitter.search.common.schema.earlybird;

import javax.annotation.Nullable;

import com.twitter.search.common.config.Config;

public enum FlushVersion {
  /* =======================================================
   * Versions
   * ======================================================= */
  VERSION_0("Initial version of partition flushing."),
  VERSION_1("Added timestamps and corresponding mapper to SegmentData."),
  VERSION_2("Add column stride fields."),
  VERSION_3("Change facet field configuration."),
  VERSION_4("Add per term offensive counters to parallel posting arrays."),
  VERSION_5("Add native photo facet."),
  VERSION_6("Add UserFeature column stride field"),
  VERSION_7("Index segment optimizations; new facet data structures."),
  VERSION_8("Store statuses in memory in Earlybird."),
  VERSION_9("Index from_user_ids into a searchable field."),
  VERSION_10("Change from_user_id dictionary from fst to mphf"),
  VERSION_11("Write image and video facet in separate lucene field."),
  VERSION_12("Add retweeted status ID to the sparse CSF."),
  VERSION_13("Add isOffensive field for profanity filter."),
  VERSION_14("Fix features column stride field corruption."),
  VERSION_15("Upgrade Lucene version, which has a different FST serialization format."),
  VERSION_16("Remove maxDoc in favor of lastDocID"),
  VERSION_17("Added partition and timeslice identifiers to SegmentData."),
  VERSION_18("Per-term payloads"),
  VERSION_19("Multiple per-doc payload fields"),
  VERSION_20("Unify and fix hash codes"),
  VERSION_21("Super awesome new flexible realtime posting list format."),
  VERSION_22("Added new geo implementation."),
  VERSION_23("Upgrade to Lucene 4.0.0 Final"),
  VERSION_24("Added tweet topic ids."),
  VERSION_25("Turn on skip list for mention facet."),
  VERSION_26("Added new EncodedTweetFeaturesColumnStrideField."),
  VERSION_27("Topic ids facet field."),
  VERSION_28("From-user discover stories skiplist field."),
  VERSION_29("Move tokenized screen name to the new username field"),
  VERSION_30("Enable HF term pairs index."),
  VERSION_31("Remove reverse doc ids."),
  VERSION_32("Switch shared status id CSF to non-sparse long CSF index."),
  VERSION_33("New skip lists for optimized high df posting lists."),
  VERSION_34("Store tweet signature in EarlybirdEncodedFeatures."),
  VERSION_35("Don't store shared status id csf in archive indexes."),
  VERSION_36("Don't store norms."),
  VERSION_37("64 bit user ids."),
  VERSION_38("Index links in archive."),
  VERSION_39("Fix pic.twitter.com image link handling not setting the internal field correctly."),
  VERSION_40("Fix all archive tweets being marked as replies."),
  VERSION_41("Avoid flushing event_ids field; event clusters are applied as updates."),
  VERSION_42("No position fields refactoring; made a few fields to not use position."),
  VERSION_43("Index private geo coordinates"),
  VERSION_44("Materialize last doc id in HighDFCompressedPostinglists", true),
  VERSION_45("Removing from_user_id facets support", true),
  VERSION_46("Guard against badly out of order tweets in the search archive.", true),
  VERSION_47("Added card title and description fields.", true),
  VERSION_48("Added card type CSF.", true),
  VERSION_49("Lucene 4.4 upgrade", true),
  VERSION_50("Put mem-archive back on non-lucene optimized indexes", true),
  VERSION_51("Force index rebuild to fix blank text field. See SEARCH-2505.", true),
  VERSION_52("Refactoring of docValues/CSF.", true),
  VERSION_53("Remove SegmentData.Configuration", true),
  VERSION_54("Fix bad indices caused by SEARCH-2723.", true),
  VERSION_55("Fixed non-deterministic facetIds across restarts. SEARCH-2815.", true),
  VERSION_56("Flush FacetIDMap.", true),
  VERSION_57("Remove LatLonMapper and use standard DocValues instead.", true),
  VERSION_58("Longterm Attribute Optimization.", true),
  VERSION_59("Renamed archive segment names. Current segment is no longer mutable.", true),
  // Flush version 60 and 59 have the same format.
  // Flush version is increased to trigger a rebuild, because we noticed incomplete segments.
  // More details can be found on SEARCH-3664
  VERSION_60("Flush version change to trigger segment rebuild.", true),
  VERSION_61("Adding back from_user_id", true),
  VERSION_62("Add retweet facet.", true),
  VERSION_63("Switch to new index API in com.twitter.search.core.earlybird.", true),
  VERSION_64("Sort merge archive day and part-* data. SEARCH-4692.", true),
  VERSION_65("Fix ID_FIELD and CREATED_AT_FIELD sort order. SEARCH-4004 SEARCH-912 ", true),
  VERSION_66("Rebuild data for 1/5/2015. Data on HDFS fixed as part of SEARCH-5347.", true),
  VERSION_67("Upgrade to Lucene 4.10.3.", true),
  VERSION_68("Switching to Penguin v4", true),
  VERSION_69("Fix 16% archive segments: SEARCH-6073", true),
  VERSION_70("Switching to Penguin v4 for full archive cluster. SEARCH-5302", true),
  VERSION_71("Switching to Penguin v4 for ssd archive cluster.", true),
  VERSION_72("Added Escherbird annotations for full archive.", true),
  VERSION_73("Lucene 5.2.1 upgrade.", true, 0),
  VERSION_74("Hanndle geo scurbbed data and archive geo index accuracy", true, 0),
  VERSION_75("Delete from_user_id_stories from indices", true, 0),
  VERSION_76("Allow multiple index extensions.", true, 0),
  VERSION_77("Removed EarlybirdCodec", true, 0),
  // minor version 2: added embedded tweet features
  // minor version 3: change embedded tweet features to INC_ONLY
  VERSION_78("Added 80 bytes of extended features", true, 3),
  // minor version 1: SEARCH-8564 - Reference Tweet Author ID, using
  //                  EXTENDED_TEST_FEATURE_UNUSED_BITS_2 and EXTENDED_TEST_FEATURE_UNUSED_BITS_3
  VERSION_79("Renamed UNUSED_BIT to HAS_VISIBLE_LINK", true, 1),
  // minor version 2: SEARCH-8564 / http://go/rb/770373
  //                  Made REFERENCE_AUTHOR_ID_LEAST_SIGNIFICANT_INT and
  //                  REFERENCE_AUTHOR_ID_MOST_SIGNIFICANT_INT immutable field
  VERSION_80("Facet for links: SEARCH-8331", true, 2),
  // minor version 1: added video view count
  VERSION_81("Adding LowDF posting list with packed ints", true, 1),
  VERSION_82("Enabling HighDF posting list with packed ints", true, 0),
  // minor version 1: SEARCH-9379 - Added bitset for nullcast tweets
  // minor version 2: SEARCH-8765 - Added visible token ratio
  VERSION_83("Add bits in encoded features for media type flags. SEARCH-9131", true, 2),
  VERSION_84("Enable archive rebuild for __has_links field. SEARCH-9635", true, 0),
  // minor version 1: SEARCHQUAL-8130, add engagement v2
  VERSION_85("New archive build gen for missing geo data. SEARCH-9894", true, 1),
  VERSION_86("Added new fields to the index", true, 0),
  // During this rebuild both the statuses and the engagement counts were regenerated.
  // minor version 1: added quote_count
  VERSION_87("Periodic archive full rebuild. SEARCH-9423", true, 1),
  // minor version 1: make new tokenized user name/handle fields textSearchable
  //                  (see go/rb/847134/)
  // minor version 2: added has_quote
  VERSION_88("Fixing missing day in the full archive index. SEARCH-11233", true, 2),
  VERSION_89("Index and store conversation ids.", true, 0),
  VERSION_90("Fixing inconsistent days in the full archive index. SEARCH-11744", true, 0),
  VERSION_91("Making in_reply_to_user_id field use MPH. SEARCH-10836", true, 0),
  VERSION_92("Allow searches by any field. SEARCH-11251", true, 0),
  // During this rebuild we regenerated engagement counts and merged the annotations in the
  // aggregate job.
  VERSION_93("Periodic archive full rebuild. SEARCH-11076", true, 0),
  // minor version 1: add ThriftCSFViewSettings.outputCSFType
  VERSION_94("Indexing a bunch of geo fields. SEARCH-10283", true, 1),
  VERSION_95("Removing topic ID fields. SEARCH-8616", true, 0),
    // minor version 1: add ThriftCSFViewSettings.normalizationType
  VERSION_96("Enabling conversation ID for all clusters. SEARCH-11989", true, 1),
  // minor version 1: set several feature configuration to be correct double type
  // minor version 2: set some more feature configuration to be correct double type
  // minor version 3: add safety labels SEARCHQUAL-9561
  // minor version 4: add weighted engagement counts SEARCHQUAL-9574
  // minor version 5: add Dopamine non personalized score SEARCHQUAL-9743
  VERSION_97("Changing CSF type to BOOLEAN for some has_* flags.", true, 5),
  VERSION_98("Periodic archive full rebuild. PCM-56871.", true, 1),
  VERSION_99("Removing named_entities field. SEARCH-13708", true, 0),
  // minor version 1: add periscope features (SEARCHQUAL-10008)
  // minor version 2: add raw_earlybird_score to TweetExternalFeatures (SEARCHQUAL-10347)
  VERSION_100("Upgrade Penguin Version from V4 to V6. SEARCH-12991", true, 2),
  // minor version 1: adjust for normalizer type for some engagement counters (SEARCHQUAL-9537)
  // minor version 2: add decaying engagement counts and last engaged timestamps (SEARCHQUAL-10532)
  VERSION_101("Add emoji to the index. SEARCH-12991", true, 2),
  VERSION_102("Periodic full archive rebuild. PCM-67851", true, 0),
  VERSION_103("Add liked_by_user_id field. SEARCH-15341", true, 0),
  // minor version 1: remove last engaged timestamp with 3-hour increment (SEARCHQUAL-10903)
  // minor version 2: add fake engagement counts (SEARCHQUAL-10795)
  // minor version 3: add last engaged timestamp with 1-hour increment (SEARCHQUAL-10942)
  VERSION_104("Reverting to the 20170109_pc100_par30 build gen. SEARCH-15731", true, 3),
  VERSION_105("Add 3 new fields to archive index for engagement features. SEARCH-16102", true, 0),
  // This is the last rebuild based on /tables/statuses. Starting 9/14 this build-gen is powered
  // by TweetSource. During this rebuild both statuses and engagement counts were rebuilt.
  VERSION_106("Periodic archive full rebuild. PCM-74652", true, 0),
  VERSION_107("Removing card fields from full archive index.", true, 0),
  VERSION_108("Removing the tms_id field from all schemas.", true, 0),
  VERSION_109("Removing LAT_LON_FIELD from all schemas.", true, 0),
  VERSION_110("Adding the card fields back to the full archive index.", true, 1),
  // minor version 1: Add composer source csf field (SEARCH-22494)
  VERSION_111("Adding composer_source to index. SEARCH-20377.", true, 1),
  VERSION_112("Partial rebuild to fix SEARCH-22529.", true, 0),
  VERSION_113("Full archive build gen 20180312_pc100_par30.", true, 0),
  VERSION_114("Fix for SEARCH-23761.", true, 0),
  VERSION_115("Add fields for quoted tweets. SEARCH-23919", true, 0),
  // minor version 1: Add 4 bit hashtag count, mention count and stock count (SEARCH-24336)
  VERSION_116("Bump flush version for scrubbing pipeline. SEARCH-24225", true, 1),
  VERSION_117("Add retweeted_by_user_id and replied_to_by_user_id fields. SEARCH-24463", true, 0),
  // minor version 1: Removed dopamine_non_personalized_score (SEARCHQUAL-10321)
  VERSION_118("Adding the reply and retweet source tweet IDs: SEARCH-23702, SEARCH-24502", true, 1),
  // minor version 1: add blink engagement counts (SEARCHQUAL-15176)
  VERSION_119("Remove public inferred location: SEARCH-24235", true, 1),
  VERSION_120("Flush extensions before fields when flushing segments.", true, 0),
  VERSION_121("Flush the startingDocIdForSearch field. SEARCH-25464.", true, 0),
  VERSION_122("Do not flush the startingDocIdForSearch field.", true, 0),
  VERSION_123("Renaming the largestDocID flushed property to firstAddedDocID.", true, 0),
  VERSION_124("Use the skip list posting list for all fields.", true, 0),
  VERSION_125("Use hashmap for tweet ID lookup.", true, 0),
  VERSION_126("Use the skip list posting list for all fields.", true, 0),
  VERSION_127("Flushing the min and max doc IDs in each segment.", true, 0),
  VERSION_128("Add card_lang to index. SEARCH-26539", true, 0),
  VERSION_129("Move the tweet ID mapper to the segment data.", true, 0),
  VERSION_130("Move the time mapper to the segment data.", true, 0),
  VERSION_131("Change the facets classes to work with any doc IDs.", true, 0),
  VERSION_132("Make the CSF classes work with any doc IDs.", true, 0),
  VERSION_133("Removing smallestDocID property.", true, 0),
  VERSION_134("Optimize DeletedDocs before flushing.", true, 0),
  VERSION_135("Add payloads to skiplists.", true, 0),
  VERSION_136("Add name to int pools.", true, 0),
  VERSION_137("Add unsorted stream offset.", true, 0),
  VERSION_138("Switch to the OutOfOrderRealtimeTweetIDMapper.", true, 0),
  VERSION_139("Remove realtime posting lists.", true, 0),
  VERSION_140("Add named_entity field. SEARCH-27547", true, 0),
  VERSION_141("Flush the out of order updates count.", true, 0),
  VERSION_142("Add named_entity facet support. SEARCH-28054", true, 0),
  VERSION_143("Index updates before optimizing segment.", true, 0),
  VERSION_144("Refactor TermsArray.", true, 0),
  VERSION_145("Remove SmallestDocID.", true, 0),
  VERSION_146("Add entity_id facet support. SEARCH-28071", true, 0),
  VERSION_147("Enable updating facets", true, 0),
  VERSION_148("Rename the counter for feature updates to partial updates", true, 0),
  VERSION_149("Stop flushing offsets for sorted updates DL streams.", true, 0),
  VERSION_150("Update the name of the property for the updates DL stream offset.", true, 0),
  VERSION_151("Upgrade Lucene version to 5.5.5.", true, 0),
  VERSION_152("Upgrade Lucene version to 6.0.0.", true, 0),
  VERSION_153("Upgrade Lucene version to 6.6.6.", true, 0),
  VERSION_154("Store the timeslice ID on EarlybirdIndexSegmentData.", true, 0),
  VERSION_155("Do not flush index extensions.", true, 0),
  VERSION_156("Deprecate ThriftIndexedFieldSettings.defaultFieldBoost.", true, 0),
  VERSION_157("Load CREATED_AT_CSF_FIELD into RAM in archive.", true, 0),
  VERSION_158("Added directed at user ID field and CSF.", true, 0),
  VERSION_159("Changing deleted docs serialization format.", true, 0),
  VERSION_160("Add fields for health model scores. SEARCH-31907, HML-2099", true, 0),
  VERSION_161("Switch to the 'search' Kafka cluster.", true, 0),
  VERSION_162("Update Lucene version to 7.0.0.", true, 0),
  VERSION_163("Update Lucene version to 7.7.2.", true, 0),
  // minor version 1: add IS_TRENDING_NOW_FLAG
  VERSION_164("Collect per-term stats in the realtime segments.", true, 1),
  VERSION_165("Update Lucene version to 8.5.2.", true, 0),
  VERSION_166("Serialize maxPosition field for InvertedRealtimeIndex", true, 0),
  VERSION_167("Add field for pSpammyTweetScore. HML-2557", true, 0),
  VERSION_168("Add field for pReportedTweetScore. HML-2644", true, 0),
  VERSION_169("Add field for spammyTweetContentScore. PFM-70", true, 0),
  VERSION_170("Add reference author id CSF. SEARCH-34715", true, 0),
  VERSION_171("Add space_id field. SEARCH-36156", true, 0),
  VERSION_172("Add facet support for space_id. SEARCH-36388", true, 0),
  VERSION_173("Add space admin and title fields. SEARCH-36986", true, 0),
  VERSION_174("Switching to Penguin v7 for realtime-exp0 cluster. SEARCH-36068", true, 0),
  VERSION_175("Adding exclusive conversation author id CSF", true, 0),
  VERSION_176("Adding card URI CSF", true, 0),
  // minor version 1: add FROM_BLUE_VERIFIED_ACCOUNT_FLAG
  // minor version 2: Adding new cluster REALTIME_CG. SEARCH-45692
  VERSION_177("Adding URL Description and Title fields. SEARCH-41641", true, 2),

  /**
   * This semi colon is on a separate line to avoid polluting git blame history.
   * Put a comma after the new enum field you're adding.
   */;

  // The current version.
  public static final FlushVersion CURRENT_FLUSH_VERSION =
      FlushVersion.values()[FlushVersion.values().length - 1];

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
    this(description, false, 0);
  }

  private FlushVersion(String description, boolean isOfficial) {
    this(description, isOfficial, 0);
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
    if (this == VERSION_0) {
      return "";
    } else {
      return DELIMITER + ordinal();
    }
  }

  /**
   * Returns file extension given flush version number.
   * If the flush version is unknown (e.g. higher than current flush version or lower than 0), null
   * is returned.
   */
  @Nullable
  public static String getVersionFileExtension(int flushVersion) {
    if (flushVersion > CURRENT_FLUSH_VERSION.ordinal() || flushVersion < 0) {
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
    return compareTo(other) >= 0;
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
   * As of now, this is hardcoded to 0. We will start using this soon.
   * @deprecated Please consult schema for minor version. This should only be used to build schema.
   */
  @Deprecated
  public int getMinorVersion() {
    return minorVersion;
  }
}
