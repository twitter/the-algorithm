package com.X.search.ingester.pipeline.X;

import java.util.Collection;
import javax.naming.NamingException;

import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;

import com.X.search.ingester.model.IngesterXMessage;
import com.X.search.ingester.pipeline.util.BatchedElement;
import com.X.search.ingester.pipeline.util.ManhattanCodedLocationProvider;
import com.X.search.ingester.pipeline.util.PipelineStageException;
import com.X.util.Future;

/**
 * Read-only stage for looking up location info and populating it onto messages.
 */
@ConsumedTypes(IngesterXMessage.class)
@ProducesConsumed
public final class PopulateCodedLocationsBatchedStage
    extends XBatchedBaseStage<IngesterXMessage, IngesterXMessage> {
  private static final String GEOCODE_DATASET_NAME = "ingester_geocode_profile_location";

  private ManhattanCodedLocationProvider manhattanCodedLocationProvider = null;

  /**
   * Require lat/lon from XMessage instead of lookup from coded_locations,
   * do not batch sql, and simply emit messages passed in with regions populated on them
   * rather than emitting to indexing queues.
   */
  @Override
  protected void doInnerPreprocess() throws StageException, NamingException {
    super.doInnerPreprocess();
    commonInnerSetup();
  }

  @Override
  protected void innerSetup() throws PipelineStageException, NamingException {
    super.innerSetup();
    commonInnerSetup();
  }

  private void commonInnerSetup() throws NamingException {
    this.manhattanCodedLocationProvider = ManhattanCodedLocationProvider.createWithEndpoint(
        wireModule.getJavaManhattanKVEndpoint(),
        getStageNamePrefix(),
        GEOCODE_DATASET_NAME);
  }

  @Override
  public void initStats() {
    super.initStats();
  }

  @Override
  protected Class<IngesterXMessage> getQueueObjectType() {
    return IngesterXMessage.class;
  }

  @Override
  protected Future<Collection<IngesterXMessage>> innerProcessBatch(Collection<BatchedElement
      <IngesterXMessage, IngesterXMessage>> batch) {

    Collection<IngesterXMessage> batchedElements = extractOnlyElementsFromBatch(batch);
    return manhattanCodedLocationProvider.populateCodedLatLon(batchedElements);
  }

  @Override
  protected boolean needsToBeBatched(IngesterXMessage message) {
    return !message.hasGeoLocation() && (message.getLocation() != null)
        && !message.getLocation().isEmpty();
  }

  @Override
  protected IngesterXMessage transform(IngesterXMessage element) {
    return element;
  }
}
