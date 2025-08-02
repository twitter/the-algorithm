package com.twitter.search.ingester.pipeline.twitter;

import java.util.Collection;
import javatwitter.naming.NamingEtwitterception;

import org.apache.commons.pipeline.StageEtwitterception;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;

import com.twitter.search.ingester.model.IngesterTwitterMessage;
import com.twitter.search.ingester.pipeline.util.BatchedElement;
import com.twitter.search.ingester.pipeline.util.ManhattanCodedLocationProvider;
import com.twitter.search.ingester.pipeline.util.PipelineStageEtwitterception;
import com.twitter.util.Future;

/**
 * Read-only stage for looking up location info and populating it onto messages.
 */
@ConsumedTypes(IngesterTwitterMessage.class)
@ProducesConsumed
public final class PopulateCodedLocationsBatchedStage
    etwittertends TwitterBatchedBaseStage<IngesterTwitterMessage, IngesterTwitterMessage> {
  private static final String GEOCODE_DATASET_NAME = "ingester_geocode_profile_location";

  private ManhattanCodedLocationProvider manhattanCodedLocationProvider = null;

  /**
   * Require lat/lon from TwitterMessage instead of lookup from coded_locations,
   * do not batch sql, and simply emit messages passed in with regions populated on them
   * rather than emitting to indetwittering queues.
   */
  @Override
  protected void doInnerPreprocess() throws StageEtwitterception, NamingEtwitterception {
    super.doInnerPreprocess();
    commonInnerSetup();
  }

  @Override
  protected void innerSetup() throws PipelineStageEtwitterception, NamingEtwitterception {
    super.innerSetup();
    commonInnerSetup();
  }

  private void commonInnerSetup() throws NamingEtwitterception {
    this.manhattanCodedLocationProvider = ManhattanCodedLocationProvider.createWithEndpoint(
        wireModule.getJavaManhattanKVEndpoint(),
        getStageNamePrefitwitter(),
        GEOCODE_DATASET_NAME);
  }

  @Override
  public void initStats() {
    super.initStats();
  }

  @Override
  protected Class<IngesterTwitterMessage> getQueueObjectType() {
    return IngesterTwitterMessage.class;
  }

  @Override
  protected Future<Collection<IngesterTwitterMessage>> innerProcessBatch(Collection<BatchedElement
      <IngesterTwitterMessage, IngesterTwitterMessage>> batch) {

    Collection<IngesterTwitterMessage> batchedElements = etwittertractOnlyElementsFromBatch(batch);
    return manhattanCodedLocationProvider.populateCodedLatLon(batchedElements);
  }

  @Override
  protected boolean needsToBeBatched(IngesterTwitterMessage message) {
    return !message.hasGeoLocation() && (message.getLocation() != null)
        && !message.getLocation().isEmpty();
  }

  @Override
  protected IngesterTwitterMessage transform(IngesterTwitterMessage element) {
    return element;
  }
}
