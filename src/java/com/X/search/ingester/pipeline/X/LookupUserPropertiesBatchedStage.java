package com.X.search.ingester.pipeline.X;

import java.util.Collection;
import javax.naming.NamingException;

import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;

import com.X.search.ingester.model.IngesterXMessage;
import com.X.search.ingester.pipeline.util.BatchedElement;
import com.X.search.ingester.pipeline.util.PipelineStageException;
import com.X.search.ingester.pipeline.util.UserPropertiesManager;
import com.X.util.Future;

@ConsumedTypes(IngesterXMessage.class)
@ProducesConsumed
public class LookupUserPropertiesBatchedStage extends XBatchedBaseStage
    <IngesterXMessage, IngesterXMessage> {

  protected UserPropertiesManager userPropertiesManager;

  @Override
  protected Class<IngesterXMessage> getQueueObjectType() {
    return IngesterXMessage.class;
  }

  @Override
  protected Future<Collection<IngesterXMessage>> innerProcessBatch(Collection<BatchedElement
      <IngesterXMessage, IngesterXMessage>> batch) {
    Collection<IngesterXMessage> batchedElements = extractOnlyElementsFromBatch(batch);
    return userPropertiesManager.populateUserProperties(batchedElements);
  }

  @Override
  protected boolean needsToBeBatched(IngesterXMessage element) {
    return true;
  }

  @Override
  protected IngesterXMessage transform(IngesterXMessage element) {
    return element;
  }

  @Override
  public synchronized void doInnerPreprocess() throws StageException, NamingException {
    super.doInnerPreprocess();
    commonInnerSetup();
  }

  @Override
  protected void innerSetup() throws PipelineStageException, NamingException {
    super.innerSetup();
    commonInnerSetup();
  }

  private void commonInnerSetup() throws NamingException {
    userPropertiesManager = new UserPropertiesManager(wireModule.getMetastoreClient());
  }
}
