package com.twitter.search.earlybird.search.facets;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.twitter.search.core.earlybird.facets.FacetIDMap;
import com.twitter.search.core.earlybird.facets.FacetLabelProvider;
import com.twitter.search.core.earlybird.facets.FacetTermCollector;
import com.twitter.search.core.earlybird.index.EarlybirdIndexSegmentAtomicReader;
import com.twitter.search.earlybird.thrift.ThriftFacetLabel;

/**
 * A collector for facet labels of given fields.
 */
public class FacetLabelCollector implements FacetTermCollector {

  private final Set<String> requiredFields;
  private FacetIDMap facetIDMap;
  private Map<String, FacetLabelProvider> facetLabelProviders;

  private final List<ThriftFacetLabel> labels = new ArrayList<>();

  public FacetLabelCollector(Set<String> requiredFields) {
    this.requiredFields = requiredFields;
  }

  public void resetFacetLabelProviders(Map<String, FacetLabelProvider> facetLabelProvidersToReset,
                                       FacetIDMap facetIDMapToReset) {
    this.facetLabelProviders = facetLabelProvidersToReset;
    this.facetIDMap = facetIDMapToReset;
    labels.clear();
  }

  @Override
  public boolean collect(int docID, long termID, int fieldID) {
    String facetName = facetIDMap.getFacetFieldByFacetID(fieldID).getFacetName();
    if (facetName == null || !requiredFields.contains(facetName)) {
      return false;
    }
    if (termID != EarlybirdIndexSegmentAtomicReader.TERM_NOT_FOUND && fieldID >= 0) {
      final FacetLabelProvider provider = facetLabelProviders.get(facetName);
      if (provider != null) {
        FacetLabelProvider.FacetLabelAccessor labelAccessor = provider.getLabelAccessor();
        String label = labelAccessor.getTermText(termID);
        int offensiveCount = labelAccessor.getOffensiveCount(termID);
        labels.add(new ThriftFacetLabel()
            .setFieldName(facetName)
            .setLabel(label)
            .setOffensiveCount(offensiveCount));
        return true;
      }
    }
    return false;
  }

  public List<ThriftFacetLabel> getLabels() {
    // Make a copy
    return new ArrayList<>(labels);
  }
}
