package com.X.search.ingester.pipeline.strato_fetchers;

import scala.Option;

import com.X.cuad.ner.plain.thriftjava.NamedEntities;
import com.X.cuad.ner.plain.thriftjava.NamedEntitiesRequestOptions;
import com.X.cuad.ner.thriftjava.ModelFamily;
import com.X.cuad.ner.thriftjava.NERCalibrateRequest;
import com.X.cuad.thriftjava.CalibrationLevel;
import com.X.cuad.thriftjava.NERCandidateSource;
import com.X.stitch.Stitch;
import com.X.strato.catalog.Fetch;
import com.X.strato.client.Client;
import com.X.strato.client.Fetcher;
import com.X.strato.data.Conv;
import com.X.strato.opcontext.ServeWithin;
import com.X.strato.thrift.TBaseConv;
import com.X.util.Duration;
import com.X.util.Future;

public class NamedEntityFetcher {
  private static final String NAMED_ENTITY_STRATO_COLUMN = "";

  private static final ServeWithin SERVE_WITHIN = new ServeWithin(
      Duration.fromMilliseconds(100), Option.empty());

  private static final NamedEntitiesRequestOptions REQUEST_OPTIONS =
      new NamedEntitiesRequestOptions(
      new NERCalibrateRequest(CalibrationLevel.HIGH_PRECISION, NERCandidateSource.NER_CRF)
          .setModel_family(ModelFamily.CFB))
      .setDisplay_entity_info(false);

  private final Fetcher<Long, NamedEntitiesRequestOptions, NamedEntities> fetcher;

  public NamedEntityFetcher(Client stratoClient) {
    fetcher = stratoClient.fetcher(
        NAMED_ENTITY_STRATO_COLUMN,
        true, // enables checking types against catalog
        Conv.longConv(),
        TBaseConv.forClass(NamedEntitiesRequestOptions.class),
        TBaseConv.forClass(NamedEntities.class)).serveWithin(SERVE_WITHIN);
  }

  public Future<Fetch.Result<NamedEntities>> fetch(long tweetId) {
    return Stitch.run(fetcher.fetch(tweetId, REQUEST_OPTIONS));
  }
}
