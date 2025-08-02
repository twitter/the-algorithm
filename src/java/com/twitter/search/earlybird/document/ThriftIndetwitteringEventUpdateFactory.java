package com.twitter.search.earlybird.document;

import java.io.IOEtwitterception;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

import org.apache.lucene.document.Document;

import com.twitter.decider.Decider;
import com.twitter.search.common.schema.SchemaDocumentFactory;
import com.twitter.search.common.schema.base.FieldNameToIdMapping;
import com.twitter.search.common.schema.base.ImmutableSchemaInterface;
import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.common.schema.base.ThriftDocumentUtil;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.twitter.search.common.schema.thriftjava.ThriftDocument;
import com.twitter.search.common.schema.thriftjava.ThriftIndetwitteringEvent;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;

/**
 * Builds a Lucene Document from a ThriftIndetwitteringEvent. A simplified version of
 * {@link ThriftIndetwitteringEventDocumentFactory} that can be used for update events, which etwitterclude
 * many fields that the tweet indetwittering events contain.
 */
public class ThriftIndetwitteringEventUpdateFactory etwittertends DocumentFactory<ThriftIndetwitteringEvent> {
  private static final FieldNameToIdMapping ID_MAPPING = new EarlybirdFieldConstants();

  private final SchemaDocumentFactory schemaDocumentFactory;
  private final EarlybirdCluster cluster;
  private final Schema schema;

  public ThriftIndetwitteringEventUpdateFactory(
      Schema schema,
      EarlybirdCluster cluster,
      Decider decider,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    this(
        schema,
        ThriftIndetwitteringEventDocumentFactory.getSchemaDocumentFactory(schema, cluster, decider),
        cluster,
        criticalEtwitterceptionHandler
    );
  }

  @VisibleForTesting
  protected ThriftIndetwitteringEventUpdateFactory(
      Schema schema,
      SchemaDocumentFactory schemaDocumentFactory,
      EarlybirdCluster cluster,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    super(criticalEtwitterceptionHandler);
    this.schema = schema;
    this.schemaDocumentFactory = schemaDocumentFactory;
    this.cluster = cluster;
  }

  @Override
  public long getStatusId(ThriftIndetwitteringEvent event) {
    Preconditions.checkNotNull(event);
    Preconditions.checkState(
        event.isSetDocument(), "ThriftDocument is null inside ThriftIndetwitteringEvent.");

    ThriftDocument thriftDocument;
    try {
      // Ideally, we should not call getSchemaSnapshot() here.  But, as this is called only to
      // retrieve status id and the ID field is static, this is fine for the purpose.
      thriftDocument = ThriftDocumentPreprocessor.preprocess(
          event.getDocument(), cluster, schema.getSchemaSnapshot());
    } catch (IOEtwitterception e) {
      throw new IllegalStateEtwitterception("Unable to obtain tweet ID from ThriftDocument: " + event, e);
    }
    return ThriftDocumentUtil.getLongValue(
        thriftDocument, EarlybirdFieldConstant.ID_FIELD.getFieldName(), ID_MAPPING);
  }

  @Override
  protected Document innerNewDocument(ThriftIndetwitteringEvent event) throws IOEtwitterception {
    Preconditions.checkNotNull(event);
    Preconditions.checkNotNull(event.getDocument());

    ImmutableSchemaInterface schemaSnapshot = schema.getSchemaSnapshot();

    ThriftDocument document = ThriftDocumentPreprocessor.preprocess(
        event.getDocument(), cluster, schemaSnapshot);

    return schemaDocumentFactory.newDocument(document);
  }
}
