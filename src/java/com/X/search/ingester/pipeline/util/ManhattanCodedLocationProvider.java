package com.X.search.ingester.pipeline.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.google.common.base.Preconditions;

import com.X.search.common.indexing.thriftjava.ThriftGeoLocationSource;
import com.X.search.common.indexing.thriftjava.ThriftGeoPoint;
import com.X.search.common.indexing.thriftjava.ThriftGeocodeRecord;
import com.X.search.common.metrics.SearchCounter;
import com.X.search.common.relevance.entities.GeoObject;
import com.X.search.common.util.geocoding.ManhattanGeocodeRecordStore;
import com.X.search.ingester.model.IngesterXMessage;
import com.X.stitch.Stitch;
import com.X.storage.client.manhattan.kv.JavaManhattanKVEndpoint;
import com.X.storage.client.manhattan.kv.ManhattanValue;
import com.X.util.Function;
import com.X.util.Future;


public final class ManhattanCodedLocationProvider {

  private final ManhattanGeocodeRecordStore store;
  private final SearchCounter locationsCounter;

  private static final String LOCATIONS_POPULATED_STAT_NAME = "_locations_populated_count";

  public static ManhattanCodedLocationProvider createWithEndpoint(
      JavaManhattanKVEndpoint endpoint, String metricsPrefix, String datasetName) {
    return new ManhattanCodedLocationProvider(
        ManhattanGeocodeRecordStore.create(endpoint, datasetName), metricsPrefix);
  }

  private ManhattanCodedLocationProvider(ManhattanGeocodeRecordStore store, String metricPrefix) {
    this.locationsCounter = SearchCounter.export(metricPrefix + LOCATIONS_POPULATED_STAT_NAME);
    this.store = store;
  }

  /**
   * Iterates through all given messages, and for each message that has a location set, retrieves
   * the coordinates of that location from Manhattan and sets them back on that message.
   */
  public Future<Collection<IngesterXMessage>> populateCodedLatLon(
      Collection<IngesterXMessage> messages) {
    if (messages.isEmpty()) {
      return Future.value(messages);
    }

    // Batch read requests
    List<Stitch<Optional<ManhattanValue<ThriftGeocodeRecord>>>> readRequests =
        new ArrayList<>(messages.size());
    for (IngesterXMessage message : messages) {
      readRequests.add(store.asyncReadFromManhattan(message.getLocation()));
    }
    Future<List<Optional<ManhattanValue<ThriftGeocodeRecord>>>> batchedRequest =
        Stitch.run(Stitch.collect(readRequests));

    return batchedRequest.map(Function.func(optGeoLocations -> {
      // Iterate over messages and responses simultaneously
      Preconditions.checkState(messages.size() == optGeoLocations.size());
      Iterator<IngesterXMessage> messageIterator = messages.iterator();
      Iterator<Optional<ManhattanValue<ThriftGeocodeRecord>>> optGeoLocationIterator =
          optGeoLocations.iterator();
      while (messageIterator.hasNext() && optGeoLocationIterator.hasNext()) {
        IngesterXMessage message = messageIterator.next();
        Optional<ManhattanValue<ThriftGeocodeRecord>> optGeoLocation =
            optGeoLocationIterator.next();
        if (setGeoLocationForMessage(message, optGeoLocation)) {
          locationsCounter.increment();
        }
      }
      return messages;
    }));
  }

  /**
   * Returns whether a valid geolocation was successfully found and saved in the message.
   */
  private boolean setGeoLocationForMessage(
      IngesterXMessage message,
      Optional<ManhattanValue<ThriftGeocodeRecord>> optGeoLocation) {
    if (optGeoLocation.isPresent()) {
      ThriftGeocodeRecord geoLocation = optGeoLocation.get().contents();
      ThriftGeoPoint geoTags = geoLocation.getGeoPoint();

      if ((geoTags.getLatitude() == GeoObject.DOUBLE_FIELD_NOT_PRESENT)
          && (geoTags.getLongitude() == GeoObject.DOUBLE_FIELD_NOT_PRESENT)) {
        // This case indicates that we have "negative cache" in coded_locations table, so
        // don't try to geocode again.
        message.setUncodeableLocation();
        return false;
      } else {
        GeoObject code = new GeoObject(
            geoTags.getLatitude(),
            geoTags.getLongitude(),
            geoTags.getAccuracy(),
            ThriftGeoLocationSource.USER_PROFILE);
        message.setGeoLocation(code);
        return true;
      }
    } else {
      message.setGeocodeRequired();
      return false;
    }
  }
}
