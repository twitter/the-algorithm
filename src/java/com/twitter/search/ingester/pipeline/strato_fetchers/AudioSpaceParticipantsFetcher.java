package com.twitter.search.ingester.pipeline.strato_fetchers;

import com.twitter.periscope.api.thriftjava.AudioSpacesLookupContext;
import com.twitter.stitch.Stitch;
import com.twitter.strato.catalog.Fetch;
import com.twitter.strato.client.Client;
import com.twitter.strato.client.Fetcher;
import com.twitter.strato.data.Conv;
import com.twitter.strato.thrift.TBaseConv;
import com.twitter.ubs.thriftjava.Participants;
import com.twitter.util.Future;

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
