package com.X.search.ingester.pipeline.X;

import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.X.search.common.indexing.thriftjava.ThriftGeoLocationSource;
import com.X.search.common.metrics.SearchRateCounter;
import com.X.search.common.relevance.entities.GeoObject;
import com.X.search.common.relevance.entities.XMessage;
import com.X.search.common.relevance.text.LocationUtils;
import com.X.search.ingester.model.IngesterXMessage;
import com.X.search.ingester.pipeline.util.PipelineStageRuntimeException;

/**
 * Read-only stage to extract lat/lon pairs from the tweet text and populate
 * the geoLocation field.
 * <p>
 * If the tweet is geotagged by mobile devices, the geo coordinates extracted from the JSON
 * is used.
 */
@ConsumedTypes(IngesterXMessage.class)
@ProducesConsumed
public class SingleTweetExtractAndGeocodeLatLonStage extends XBaseStage
    <XMessage, IngesterXMessage> {
  private static final Logger LOG =
      LoggerFactory.getLogger(SingleTweetExtractAndGeocodeLatLonStage.class);

  private SearchRateCounter extractedLatLons;
  private SearchRateCounter badLatLons;

  @Override
  public void initStats() {
    super.initStats();
    innerSetupStats();
  }

  @Override
  protected void innerSetupStats() {
    extractedLatLons = SearchRateCounter.export(getStageNamePrefix() + "_extracted_lat_lons");
    badLatLons = SearchRateCounter.export(getStageNamePrefix() + "_invalid_lat_lons");
  }

  @Override
  public void innerProcess(Object obj) throws StageException {
    if (!(obj instanceof IngesterXMessage)) {
      throw new StageException(this, "Object is not IngesterXMessage object: " + obj);
    }

    IngesterXMessage message = IngesterXMessage.class.cast(obj);
    tryToSetGeoLocation(message);
    emitAndCount(message);
  }

  @Override
  protected IngesterXMessage innerRunStageV2(XMessage message) {
    // Previous stage takes in a XMessage and returns a XMessage. I think it was
    // done to simplify testing. From this stage onwards, we only count the message that are of type
    // IngesterXMessage.
    if (!(message instanceof IngesterXMessage)) {
      throw new PipelineStageRuntimeException("Message needs to be of type IngesterXMessage");
    }

    IngesterXMessage ingesterXMessage = IngesterXMessage.class.cast(message);
    tryToSetGeoLocation(ingesterXMessage);
    return ingesterXMessage;
  }

  private void tryToSetGeoLocation(IngesterXMessage message) {
    if (message.getGeoTaggedLocation() != null) {
      message.setGeoLocation(message.getGeoTaggedLocation());
    } else if (message.hasGeoLocation()) {
      LOG.warn("Message {} already contains geoLocation", message.getId());
    } else {
      try {
        GeoObject extracted = extractLatLon(message);
        if (extracted != null) {
          message.setGeoLocation(extracted);
          extractedLatLons.increment();
        }
      } catch (NumberFormatException e) {
        LOG.debug("Message contains bad latitude and longitude: " + message.getOrigLocation(), e);
        badLatLons.increment();
      } catch (Exception e) {
        LOG.error("Failed to extract geo location from " + message.getOrigLocation() + " for tweet "
            + message.getId(), e);
      }
    }
  }

  private GeoObject extractLatLon(IngesterXMessage message) throws NumberFormatException {
    double[] latlon = LocationUtils.extractLatLon(message);
    return latlon == null
        ? null
        : new GeoObject(latlon[0], latlon[1], ThriftGeoLocationSource.TWEET_TEXT);
  }
}
