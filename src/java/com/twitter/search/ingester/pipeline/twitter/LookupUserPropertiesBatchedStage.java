package com.twitter.search.ingester.pipeline.twitter;

import java.util.Collection;
import javatwitter.naming.NamingEtwitterception;

import org.apache.commons.pipeline.StageEtwitterception;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;

import com.twitter.search.ingester.model.IngesterTwitterMessage;
import com.twitter.search.ingester.pipeline.util.BatchedElement;
import com.twitter.search.ingester.pipeline.util.PipelineStageEtwitterception;
import com.twitter.search.ingester.pipeline.util.UserPropertiesManager;
import com.twitter.util.Future;

@ConsumedTypes(IngesterTwitterMessage.class)
@ProducesConsumed
public class LookupUserPropertiesBatchedStage etwittertends TwitterBatchedBaseStage
    <IngesterTwitterMessage, IngesterTwitterMessage> {

  protected UserPropertiesManager userPropertiesManager;

  @Override
  protected Class<IngesterTwitterMessage> getQueueObjectType() {
    return IngesterTwitterMessage.class;
  }

  @Override
  protected Future<Collection<IngesterTwitterMessage>> innerProcessBatch(Collection<BatchedElement
      <IngesterTwitterMessage, IngesterTwitterMessage>> batch) {
    Collection<IngesterTwitterMessage> batchedElements = etwittertractOnlyElementsFromBatch(batch);
    return userPropertiesManager.populateUserProperties(batchedElements);
  }

  @Override
  protected boolean needsToBeBatched(IngesterTwitterMessage element) {
    return true;
  }

  @Override
  protected IngesterTwitterMessage transform(IngesterTwitterMessage element) {
    return element;
  }

  @Override
  public synchronized void doInnerPreprocess() throws StageEtwitterception, NamingEtwitterception {
    super.doInnerPreprocess();
    commonInnerSetup();
  }

  @Override
  protected void innerSetup() throws PipelineStageEtwitterception, NamingEtwitterception {
    super.innerSetup();
    commonInnerSetup();
  }

  private void commonInnerSetup() throws NamingEtwitterception {
    userPropertiesManager = new UserPropertiesManager(wireModule.getMetastoreClient());
  }
}
