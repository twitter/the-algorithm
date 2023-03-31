package com.twitter.search.earlybird.common;

import org.apache.lucene.search.Query;

import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;

public class RequestResponsePair {
  private final EarlybirdRequest request;
  private final EarlybirdResponse response;
  private final org.apache.lucene.search.Query luceneQuery;

  // The serialized query in its final form, after various modifications have been applied to it.
  // As a note, we have some code paths in which this can be null, but I don't really see them
  // triggered in production right now.
  private final com.twitter.search.queryparser.query.Query finalSerializedQuery;

  public RequestResponsePair(
      EarlybirdRequest request,
      com.twitter.search.queryparser.query.Query finalSerializedQuery,
      org.apache.lucene.search.Query luceneQuery,
      EarlybirdResponse response) {
    this.request = request;
    this.luceneQuery = luceneQuery;
    this.response = response;
    this.finalSerializedQuery = finalSerializedQuery;
  }

  public String getFinalSerializedQuery() {
    return finalSerializedQuery != null ? finalSerializedQuery.serialize() : "N/A";
  }

  public EarlybirdRequest getRequest() {
    return request;
  }

  public EarlybirdResponse getResponse() {
    return response;
  }

  public Query getLuceneQuery() {
    return luceneQuery;
  }
}
