package com.X.search.ingester.pipeline.X;

import java.util.concurrent.CompletableFuture;
import javax.naming.NamingException;

import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;

import com.X.search.ingester.model.IngesterXMessage;
import com.X.util.Function;

@ConsumedTypes(IngesterXMessage.class)
@ProducesConsumed
public class RetrieveNamedEntitiesSingleTweetStage extends XBaseStage
    <IngesterXMessage, CompletableFuture<IngesterXMessage>> {

  private NamedEntityHandler namedEntityHandler;

  @Override
  protected void doInnerPreprocess() throws StageException, NamingException {
    innerSetup();
  }

  @Override
  protected void innerSetup() {
    namedEntityHandler = new NamedEntityHandler(
        wireModule.getNamedEntityFetcher(), decider, getStageNamePrefix(),
        "single_tweet");
  }

  @Override
  public void innerProcess(Object obj) throws StageException {
    if (!(obj instanceof IngesterXMessage)) {
      throw new StageException(this, "Object is not a IngesterXMessage object: " + obj);
    }
    IngesterXMessage XMessage = (IngesterXMessage) obj;

    if (namedEntityHandler.shouldRetrieve(XMessage)) {
      namedEntityHandler.retrieve(XMessage)
          .onSuccess(Function.cons(result -> {
            namedEntityHandler.addEntitiesToMessage(XMessage, result);
            emitAndCount(XMessage);
          }))
          .onFailure(Function.cons(throwable -> {
            namedEntityHandler.incrementErrorCount();
            emitAndCount(XMessage);
          }));
    } else {
      emitAndCount(XMessage);
    }
  }

  @Override
  protected CompletableFuture<IngesterXMessage> innerRunStageV2(IngesterXMessage
                                                                      message) {
    CompletableFuture<IngesterXMessage> cf = new CompletableFuture<>();

    if (namedEntityHandler.shouldRetrieve(message)) {
      namedEntityHandler.retrieve(message)
          .onSuccess(Function.cons(result -> {
            namedEntityHandler.addEntitiesToMessage(message, result);
            cf.complete(message);
          }))
          .onFailure(Function.cons(throwable -> {
            namedEntityHandler.incrementErrorCount();
            cf.complete(message);
          }));
    } else {
      cf.complete(message);
    }

    return cf;
  }
}
