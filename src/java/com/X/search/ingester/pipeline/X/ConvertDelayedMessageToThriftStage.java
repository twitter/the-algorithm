package com.X.search.ingester.pipeline.X;

import java.util.List;

import javax.naming.NamingException;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducedTypes;

import com.X.common_internal.text.version.PenguinVersion;
import com.X.search.common.converter.earlybird.DelayedIndexingConverter;
import com.X.search.common.indexing.thriftjava.ThriftVersionedEvents;
import com.X.search.common.relevance.entities.XMessage;
import com.X.search.common.schema.base.Schema;
import com.X.search.common.schema.earlybird.EarlybirdSchemaCreateTool;
import com.X.search.ingester.model.IngesterThriftVersionedEvents;
import com.X.search.ingester.model.IngesterXMessage;

@ConsumedTypes(IngesterXMessage.class)
@ProducedTypes(IngesterThriftVersionedEvents.class)
public class ConvertDelayedMessageToThriftStage extends XBaseStage
    <XMessage, IngesterThriftVersionedEvents> {
  private List<PenguinVersion> penguinVersionList;
  private FieldStatExporter fieldStatExporter;
  private DelayedIndexingConverter messageConverter;

  @Override
  protected void doInnerPreprocess() throws StageException, NamingException {
    Schema schema;
    try {
      schema = EarlybirdSchemaCreateTool.buildSchema(Preconditions.checkNotNull(earlybirdCluster));
    } catch (Schema.SchemaValidationException e) {
      throw new StageException(this, e);
    }

    penguinVersionList = wireModule.getPenguinVersions();
    messageConverter = new DelayedIndexingConverter(schema, decider);
    fieldStatExporter = new FieldStatExporter("unsorted_urls", schema, penguinVersionList);
  }

  @Override
  public void innerProcess(Object obj) throws StageException {
    if (!(obj instanceof IngesterXMessage)) {
      throw new StageException(this, "Object is not an IngesterXMessage instance: " + obj);
    }

    penguinVersionList = wireModule.getCurrentlyEnabledPenguinVersions();
    fieldStatExporter.updatePenguinVersions(penguinVersionList);

    IngesterXMessage message = IngesterXMessage.class.cast(obj);
    for (IngesterThriftVersionedEvents events : buildVersionedEvents(message)) {
      fieldStatExporter.addFieldStats(events);
      emitAndCount(events);
    }
  }

  /**
   * Method that converts all URL and card related fields and features of a XMessage to a
   * ThriftVersionedEvents instance.
   *
   * @param XMessage An IngesterThriftVersionedEvents instance to be converted.
   * @return The corresponding ThriftVersionedEvents instance.
   */
  private List<IngesterThriftVersionedEvents> buildVersionedEvents(
      IngesterXMessage XMessage) {
    List<ThriftVersionedEvents> versionedEvents =
        messageConverter.convertMessageToOutOfOrderAppendAndFeatureUpdate(
            XMessage, penguinVersionList);
    Preconditions.checkArgument(
        versionedEvents.size() == 2,
        "DelayedIndexingConverter produced an incorrect number of ThriftVersionedEvents.");
    return Lists.newArrayList(
        toIngesterThriftVersionedEvents(versionedEvents.get(0), XMessage),
        toIngesterThriftVersionedEvents(versionedEvents.get(1), XMessage));
  }

  private IngesterThriftVersionedEvents toIngesterThriftVersionedEvents(
      ThriftVersionedEvents versionedEvents, IngesterXMessage XMessage) {
    // We don't want to propagate the same DebugEvents instance to multiple
    // IngesterThriftVersionedEvents instances, because future stages might want to add new events
    // to this list for multiple events at the same time, which would result in a
    // ConcurrentModificationException. So we need to create a DebugEvents deep copy.
    IngesterThriftVersionedEvents ingesterThriftVersionedEvents =
        new IngesterThriftVersionedEvents(XMessage.getUserId());
    ingesterThriftVersionedEvents.setDarkWrite(false);
    ingesterThriftVersionedEvents.setId(XMessage.getTweetId());
    ingesterThriftVersionedEvents.setVersionedEvents(versionedEvents.getVersionedEvents());
    ingesterThriftVersionedEvents.setDebugEvents(XMessage.getDebugEvents().deepCopy());
    return ingesterThriftVersionedEvents;
  }
}
