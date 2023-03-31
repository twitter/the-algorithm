package com.twitter.search.earlybird_root.collectors;

import java.util.Comparator;
import java.util.List;

import com.twitter.search.common.relevance.utils.ResultComparators;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.ThriftSearchResult;
import com.twitter.search.earlybird.thrift.ThriftSearchResults;

/**
 * {@link RecencyMergeCollector} inherits {@link MultiwayMergeCollector} for the type
 * {@link com.twitter.search.earlybird.thrift.ThriftSearchResult} as the result type.
 * <p/>
 * It also implements two public methods to retrieve the top-k or all results.
 */
public class RecencyMergeCollector extends MultiwayMergeCollector<ThriftSearchResult> {

  // Container for the final results array and also stats like numHitsProcessed etc...
  protected final ThriftSearchResults finalResults = new ThriftSearchResults();

  public RecencyMergeCollector(int numResponses) {
    this(numResponses, ResultComparators.ID_COMPARATOR);
  }

  protected RecencyMergeCollector(int numResponses, Comparator<ThriftSearchResult> comparator) {
    super(numResponses, comparator);
  }

  @Override
  protected void collectStats(EarlybirdResponse response) {
    super.collectStats(response);

    ThriftSearchResults searchResults = response.getSearchResults();
    if (searchResults.isSetNumHitsProcessed()) {
      finalResults.setNumHitsProcessed(
          finalResults.getNumHitsProcessed() + searchResults.getNumHitsProcessed());
    }
    if (searchResults.isSetNumPartitionsEarlyTerminated()) {
      finalResults.setNumPartitionsEarlyTerminated(
              finalResults.getNumPartitionsEarlyTerminated()
                      + searchResults.getNumPartitionsEarlyTerminated());
    }
  }

  @Override
  protected final List<ThriftSearchResult> collectResults(EarlybirdResponse response) {
    if (response != null
        && response.isSetSearchResults()
        && response.getSearchResults().getResultsSize() > 0) {
      return response.getSearchResults().getResults();
    } else {
      return null;
    }
  }

  /**
   * Gets all the results that has been collected.
   *
   * @return {@link ThriftSearchResults} containing a list of results sorted by provided
   *         comparator in descending order.
   */
  public final ThriftSearchResults getAllSearchResults() {
    return finalResults.setResults(getResultsList());
  }

  @Override
  protected final boolean isResponseValid(EarlybirdResponse response) {
    if (response == null || !response.isSetSearchResults()) {
      LOG.warn("searchResults was null: " + response);
      return false;
    }
    return true;
  }
}
