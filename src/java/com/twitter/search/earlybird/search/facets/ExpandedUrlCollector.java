package com.twitter.search.earlybird.search.facets;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import org.apache.lucene.util.BytesRef;

import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.twitter.search.core.earlybird.facets.FacetLabelProvider;
import com.twitter.search.earlybird.thrift.ThriftSearchResult;
import com.twitter.search.earlybird.thrift.ThriftSearchResultUrl;
import com.twitter.service.spiderduck.gen.MediaTypes;

/**
 * A collector for collecting expanded urls from facets. Note that the only thing connecting this
 * collector with expanded URLs is the fact that we only store the expanded url in the facet fields.
 */
public class ExpandedUrlCollector extends AbstractFacetTermCollector {
  private static final ImmutableSet<String> FACET_CONTAINS_URL = ImmutableSet.of(
      EarlybirdFieldConstant.VIDEOS_FACET,
      EarlybirdFieldConstant.IMAGES_FACET,
      EarlybirdFieldConstant.NEWS_FACET,
      EarlybirdFieldConstant.LINKS_FACET,
      EarlybirdFieldConstant.TWIMG_FACET);

  private final Map<String, ThriftSearchResultUrl> dedupedUrls = new LinkedHashMap<>();


  @Override
  protected String getTermFromProvider(
      String facetName,
      long termID,
      FacetLabelProvider provider) {
    String url = null;
    if (EarlybirdFieldConstant.TWIMG_FACET.equals(facetName)) {
      // Special case extraction of media url for twimg.
      FacetLabelProvider.FacetLabelAccessor photoAccessor = provider.getLabelAccessor();
      BytesRef termPayload = photoAccessor.getTermPayload(termID);
      if (termPayload != null) {
        url = termPayload.utf8ToString();
      }
    } else {
      url = provider.getLabelAccessor().getTermText(termID);
    }
    return url;
  }

  @Override
  public boolean collect(int docID, long termID, int fieldID) {

    String url = getTermFromFacet(termID, fieldID, FACET_CONTAINS_URL);
    if (url == null || url.isEmpty()) {
      return false;
    }

    ThriftSearchResultUrl resultUrl = new ThriftSearchResultUrl();
    resultUrl.setOriginalUrl(url);
    MediaTypes mediaType = getMediaType(findFacetName(fieldID));
    resultUrl.setMediaType(mediaType);

    // Media links will show up twice:
    //   - once in image/native_image/video/news facets
    //   - another time in the links facet
    //
    // For those urls, we only want to return the media version. If it is non-media version, only
    // write to map if doesn't exist already, if media version, overwrite any previous entries.
    if (mediaType == MediaTypes.UNKNOWN) {
      if (!dedupedUrls.containsKey(url)) {
        dedupedUrls.put(url, resultUrl);
      }
    } else {
      dedupedUrls.put(url, resultUrl);
    }

    return true;
  }

  @Override
  public void fillResultAndClear(ThriftSearchResult result) {
    result.getMetadata().setTweetUrls(getExpandedUrls());
    dedupedUrls.clear();
  }

  @VisibleForTesting
  List<ThriftSearchResultUrl> getExpandedUrls() {
    return ImmutableList.copyOf(dedupedUrls.values());
  }

  /**
   * Gets the Spiderduck media type for a given facet name.
   *
   * @param facetName A given facet name.
   * @return {@code MediaTypes} enum corresponding to the facet name.
   */
  private static MediaTypes getMediaType(String facetName) {
    if (facetName == null) {
      return MediaTypes.UNKNOWN;
    }

    switch (facetName) {
      case EarlybirdFieldConstant.TWIMG_FACET:
        return MediaTypes.NATIVE_IMAGE;
      case EarlybirdFieldConstant.IMAGES_FACET:
        return MediaTypes.IMAGE;
      case EarlybirdFieldConstant.VIDEOS_FACET:
        return MediaTypes.VIDEO;
      case EarlybirdFieldConstant.NEWS_FACET:
        return MediaTypes.NEWS;
      default:
        return MediaTypes.UNKNOWN;
    }
  }
}
