package com.twitter.search.feature_update_service.util;


import javax.annotation.Nullable;

import com.twitter.search.common.schema.base.ThriftDocumentUtil;
import com.twitter.search.feature_update_service.thriftjava.FeatureUpdateRequest;
import com.twitter.search.feature_update_service.thriftjava.FeatureUpdateResponse;
import com.twitter.search.feature_update_service.thriftjava.FeatureUpdateResponseCode;

public final class FeatureUpdateValidator {

  private FeatureUpdateValidator() { }

  /**
   * Validates FeatureUpdateRequest
   * @param featureUpdate instance of FeatureUpdateRequest with ThriftIndexingEvent
   * @return null if valid, instance of FeatureUpdateResponse if not.
   * Response will have appropriate error code and message set.
   */
  @Nullable
  public static FeatureUpdateResponse validate(FeatureUpdateRequest featureUpdate) {

    if (ThriftDocumentUtil.hasDuplicateFields(featureUpdate.getEvent().getDocument())) {
      return createResponse(
          String.format("duplicate document fields: %s", featureUpdate.toString()));
    }
    if (!featureUpdate.getEvent().isSetUid()) {
      return createResponse(String.format("unset uid: %s", featureUpdate.toString()));
    }

    return null;
  }

  private static FeatureUpdateResponse createResponse(String errorMsg) {
    FeatureUpdateResponseCode responseCode = FeatureUpdateResponseCode.CLIENT_ERROR;
    FeatureUpdateResponse response = new FeatureUpdateResponse(responseCode);
    response.setDetailMessage(errorMsg);
    return response;
  }
}
