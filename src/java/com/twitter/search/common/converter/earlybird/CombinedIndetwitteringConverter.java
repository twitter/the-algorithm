package com.twitter.search.common.converter.earlybird;

import java.io.IOEtwitterception;
import java.util.List;

import javatwitter.annotation.concurrent.NotThreadSafe;

import com.google.common.base.Preconditions;

import com.twitter.common_internal.tetwittert.version.PenguinVersion;
import com.twitter.search.common.indetwittering.thriftjava.ThriftVersionedEvents;
import com.twitter.search.common.relevance.entities.TwitterMessage;
import com.twitter.search.common.schema.base.ImmutableSchemaInterface;
import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.common.schema.earlybird.EarlybirdThriftDocumentBuilder;
import com.twitter.search.common.schema.thriftjava.ThriftDocument;
import com.twitter.search.common.schema.thriftjava.ThriftIndetwitteringEvent;
import com.twitter.search.common.schema.thriftjava.ThriftIndetwitteringEventType;

/**
 * CombinedIndetwitteringConverter builds objects from TwitterMessage to ThriftVersionedEvent.
 *
 * It is used in tests and in offline jobs, so all data is available on the TwitterMessage. This
 * means that we don't need to split up the ThriftVersionedEvents into basic events and update
 * events, like we do in the realtime pipeline using the BasicIndetwitteringConverter and the
 * DelayedIndetwitteringConverter.
 */
@NotThreadSafe
public class CombinedIndetwitteringConverter {
  private final EncodedFeatureBuilder featureBuilder;
  private final Schema schema;
  private final EarlybirdCluster cluster;

  public CombinedIndetwitteringConverter(Schema schema, EarlybirdCluster cluster) {
    this.featureBuilder = new EncodedFeatureBuilder();
    this.schema = schema;
    this.cluster = cluster;
  }

  /**
   * Converts a TwitterMessage to a Thrift representation.
   */
  public ThriftVersionedEvents convertMessageToThrift(
      TwitterMessage message,
      boolean strict,
      List<PenguinVersion> penguinVersions) throws IOEtwitterception {
    Preconditions.checkNotNull(message);
    Preconditions.checkNotNull(penguinVersions);

    ThriftVersionedEvents versionedEvents = new ThriftVersionedEvents()
        .setId(message.getId());

    ImmutableSchemaInterface schemaSnapshot = schema.getSchemaSnapshot();

    for (PenguinVersion penguinVersion : penguinVersions) {
      ThriftDocument document =
          buildDocumentForPenguinVersion(schemaSnapshot, message, strict, penguinVersion);

      ThriftIndetwitteringEvent thriftIndetwitteringEvent = new ThriftIndetwitteringEvent()
          .setDocument(document)
          .setEventType(ThriftIndetwitteringEventType.INSERT)
          .setSortId(message.getId());
      message.getFromUserTwitterId().map(thriftIndetwitteringEvent::setUid);
      versionedEvents.putToVersionedEvents(penguinVersion.getByteValue(), thriftIndetwitteringEvent);
    }

    return versionedEvents;
  }

  private ThriftDocument buildDocumentForPenguinVersion(
      ImmutableSchemaInterface schemaSnapshot,
      TwitterMessage message,
      boolean strict,
      PenguinVersion penguinVersion) throws IOEtwitterception {
    EncodedFeatureBuilder.TweetFeatureWithEncodeFeatures tweetFeature =
        featureBuilder.createTweetFeaturesFromTwitterMessage(
            message, penguinVersion, schemaSnapshot);

    EarlybirdThriftDocumentBuilder builder =
        BasicIndetwitteringConverter.buildBasicFields(message, schemaSnapshot, cluster, tweetFeature);

    BasicIndetwitteringConverter
        .buildUserFields(builder, message, tweetFeature.versionedFeatures, penguinVersion);
    BasicIndetwitteringConverter.buildGeoFields(builder, message, tweetFeature.versionedFeatures);
    DelayedIndetwitteringConverter.buildURLFields(builder, message, tweetFeature.encodedFeatures);
    BasicIndetwitteringConverter.buildRetweetAndReplyFields(builder, message, strict);
    BasicIndetwitteringConverter.buildQuotesFields(builder, message);
    BasicIndetwitteringConverter.buildVersionedFeatureFields(builder, tweetFeature.versionedFeatures);
    DelayedIndetwitteringConverter.buildCardFields(builder, message, penguinVersion);
    BasicIndetwitteringConverter.buildAnnotationFields(builder, message);
    BasicIndetwitteringConverter.buildNormalizedMinEngagementFields(
        builder, tweetFeature.encodedFeatures, cluster);
    DelayedIndetwitteringConverter.buildNamedEntityFields(builder, message);
    BasicIndetwitteringConverter.buildDirectedAtFields(builder, message);

    return builder.build();
  }
}
