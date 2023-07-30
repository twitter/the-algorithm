package com.X.search.ingester.pipeline.X;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.naming.NamingException;

import com.google.common.base.Preconditions;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.X.common_internal.text.version.PenguinVersion;
import com.X.search.common.converter.earlybird.BasicIndexingConverter;
import com.X.search.common.indexing.thriftjava.ThriftVersionedEvents;
import com.X.search.common.metrics.SearchCounter;
import com.X.search.common.schema.base.Schema;
import com.X.search.common.schema.earlybird.EarlybirdSchemaCreateTool;
import com.X.search.ingester.model.IngesterThriftVersionedEvents;
import com.X.search.ingester.model.IngesterXMessage;

@ConsumedTypes(IngesterXMessage.class)
@ProducesConsumed
public class ConvertMessageToThriftStage extends XBaseStage
    <IngesterXMessage, IngesterXMessage> {
  private static final Logger LOG = LoggerFactory.getLogger(ConvertMessageToThriftStage.class);

  private List<PenguinVersion> penguinVersionList;
  private String thriftVersionedEventsBranchName;
  private FieldStatExporter fieldStatExporter;
  private BasicIndexingConverter messageConverter;

  private SearchCounter XMessageToTveErrorCount;

  @Override
  public void initStats() {
    super.initStats();
    XMessageToTveErrorCount = SearchCounter.export(
        getStageNamePrefix() + "_ingester_convert_X_message_to_tve_error_count");
  }

  @Override
  protected void doInnerPreprocess() throws StageException, NamingException {
    Schema schema;
    try {
      schema = EarlybirdSchemaCreateTool.buildSchema(Preconditions.checkNotNull(earlybirdCluster));
    } catch (Schema.SchemaValidationException e) {
      throw new StageException(this, e);
    }

    penguinVersionList = wireModule.getPenguinVersions();
    Preconditions.checkState(StringUtils.isNotBlank(thriftVersionedEventsBranchName));
    messageConverter = new BasicIndexingConverter(schema, earlybirdCluster);
    fieldStatExporter = new FieldStatExporter("unsorted_tweets", schema, penguinVersionList);
  }

  @Override
  public void innerProcess(Object obj) throws StageException {
    if (!(obj instanceof IngesterXMessage)) {
      throw new StageException(this, "Object is not an IngesterXMessage instance: " + obj);
    }

    penguinVersionList = wireModule.getCurrentlyEnabledPenguinVersions();
    fieldStatExporter.updatePenguinVersions(penguinVersionList);

    IngesterXMessage message = IngesterXMessage.class.cast(obj);

    Optional<IngesterThriftVersionedEvents> maybeEvents = buildVersionedEvents(message);
    if (maybeEvents.isPresent()) {
      IngesterThriftVersionedEvents events = maybeEvents.get();
      fieldStatExporter.addFieldStats(events);
      emitToBranchAndCount(thriftVersionedEventsBranchName, events);
    }

    emitAndCount(message);
  }

  /**
   * Method that converts a XMessage to a ThriftVersionedEvents.
   *
   * @param XMessage An IngesterThriftVersionedEvents instance to be converted.
   * @return The corresponding ThriftVersionedEvents.
   */
  private Optional<IngesterThriftVersionedEvents> buildVersionedEvents(
      IngesterXMessage XMessage) {
    IngesterThriftVersionedEvents ingesterEvents =
        new IngesterThriftVersionedEvents(XMessage.getUserId());
    ingesterEvents.setDarkWrite(false);
    ingesterEvents.setId(XMessage.getTweetId());

    // We will emit both the original XMessage, and the ThriftVersionedEvents instance, so we
    // need to make sure they have separate DebugEvents copies.
    ingesterEvents.setDebugEvents(XMessage.getDebugEvents().deepCopy());

    try {
      ThriftVersionedEvents versionedEvents =
          messageConverter.convertMessageToThrift(XMessage, true, penguinVersionList);
      ingesterEvents.setVersionedEvents(versionedEvents.getVersionedEvents());
      return Optional.of(ingesterEvents);
    } catch (IOException e) {
      LOG.error("Failed to convert tweet " + XMessage.getTweetId() + " from XMessage "
                + "to ThriftVersionedEvents for Penguin versions " + penguinVersionList,
                e);
      XMessageToTveErrorCount.increment();
    }
    return Optional.empty();
  }

  public void setThriftVersionedEventsBranchName(String thriftVersionedEventsBranchName) {
    this.thriftVersionedEventsBranchName = thriftVersionedEventsBranchName;
  }
}
