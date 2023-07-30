package com.X.search.ingester.pipeline.strato_fetchers;

import com.X.periscope.api.thriftjava.AudioSpacesLookupContext;
import com.X.stitch.Stitch;
import com.X.strato.catalog.Fetch;
import com.X.strato.client.Client;
import com.X.strato.client.Fetcher;
import com.X.strato.data.Conv;
import com.X.strato.thrift.TBaseConv;
import com.X.ubs.thriftjava.Participants;
import com.X.util.Future;

/**
 * Fetches from the audio space participants strato column.
 */
public class AudioSpaceParticipantsFetcher {
  private static final String PARTICIPANTS_STRATO_COLUMN = "";

  private static final AudioSpacesLookupContext
      EMPTY_AUDIO_LOOKUP_CONTEXT = new AudioSpacesLookupContext();

  private final Fetcher<String, AudioSpacesLookupContext, Participants> fetcher;

  public AudioSpaceParticipantsFetcher(Client stratoClient) {
    fetcher = stratoClient.fetcher(
        PARTICIPANTS_STRATO_COLUMN,
        true, // enables checking types against catalog
        Conv.stringConv(),
        TBaseConv.forClass(AudioSpacesLookupContext.class),
        TBaseConv.forClass(Participants.class));
  }

  public Future<Fetch.Result<Participants>> fetch(String spaceId) {
    return Stitch.run(fetcher.fetch(spaceId, EMPTY_AUDIO_LOOKUP_CONTEXT));
  }
}
