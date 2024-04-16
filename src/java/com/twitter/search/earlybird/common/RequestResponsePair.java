package com.tw ter.search.earlyb rd.common;

 mport org.apac .lucene.search.Query;

 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;

publ c class RequestResponsePa r {
  pr vate f nal Earlyb rdRequest request;
  pr vate f nal Earlyb rdResponse response;
  pr vate f nal org.apac .lucene.search.Query luceneQuery;

  // T  ser al zed query  n  s f nal form, after var ous mod f cat ons have been appl ed to  .
  // As a note,   have so  code paths  n wh ch t  can be null, but   don't really see t m
  // tr ggered  n product on r ght now.
  pr vate f nal com.tw ter.search.queryparser.query.Query f nalSer al zedQuery;

  publ c RequestResponsePa r(
      Earlyb rdRequest request,
      com.tw ter.search.queryparser.query.Query f nalSer al zedQuery,
      org.apac .lucene.search.Query luceneQuery,
      Earlyb rdResponse response) {
    t .request = request;
    t .luceneQuery = luceneQuery;
    t .response = response;
    t .f nalSer al zedQuery = f nalSer al zedQuery;
  }

  publ c Str ng getF nalSer al zedQuery() {
    return f nalSer al zedQuery != null ? f nalSer al zedQuery.ser al ze() : "N/A";
  }

  publ c Earlyb rdRequest getRequest() {
    return request;
  }

  publ c Earlyb rdResponse getResponse() {
    return response;
  }

  publ c Query getLuceneQuery() {
    return luceneQuery;
  }
}
