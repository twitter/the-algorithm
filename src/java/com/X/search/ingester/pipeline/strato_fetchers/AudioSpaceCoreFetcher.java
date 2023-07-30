package com.X.search.ingester.pipeline.strato_fetchers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.X.periscope.api.thriftjava.AudioSpacesLookupContext;
import com.X.stitch.Stitch;
import com.X.strato.catalog.Fetch;
import com.X.strato.client.Client;
import com.X.strato.client.Fetcher;
import com.X.strato.data.Conv;
import com.X.strato.thrift.TBaseConv;
import com.X.ubs.thriftjava.AudioSpace;
import com.X.util.Future;
import com.X.util.Try;

/**
 * Fetches from the audio space core strato column.
 */
public class AudioSpaceCoreFetcher {
  private static final String CORE_STRATO_COLUMN = "";

  private static final AudioSpacesLookupContext
      EMPTY_AUDIO_LOOKUP_CONTEXT = new AudioSpacesLookupContext();

  private final Fetcher<String, AudioSpacesLookupContext, AudioSpace> fetcher;

  public AudioSpaceCoreFetcher(Client stratoClient) {
    fetcher = stratoClient.fetcher(
        CORE_STRATO_COLUMN,
        true, // enables checking types against catalog
        Conv.stringConv(),
        TBaseConv.forClass(AudioSpacesLookupContext.class),
        TBaseConv.forClass(AudioSpace.class));
  }

  public Future<Fetch.Result<AudioSpace>> fetch(String spaceId) {
    return Stitch.run(fetcher.fetch(spaceId, EMPTY_AUDIO_LOOKUP_CONTEXT));
  }

  /**
   * Use stitch to fetch mulitiple AudioSpace Objects at once
   */
  public Future<List<Try<Fetch.Result<AudioSpace>>>> fetchBulkSpaces(Set<String> spaceIds) {
    return Stitch.run(
        Stitch.collectToTry(
            spaceIds
                .stream()
                .map(spaceId -> fetcher.fetch(spaceId, EMPTY_AUDIO_LOOKUP_CONTEXT))
                .collect(Collectors.toList())
        )
    );
  }

}
