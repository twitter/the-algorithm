package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.HashSet;
 mport java.ut l.Set;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.collect. mmutableSet;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.ut l.earlyb rd.Earlyb rdResponseUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.queryparser.query.search.SearchOperatorConstants;
 mport com.tw ter.search.queryparser.v s ors.DetectPos  veOperatorV s or;

/**
 * F lter that  s track ng t  unexpected nullcast results from Earlyb rds.
 */
publ c class NullcastTrack ngF lter extends Sens  veResultsTrack ngF lter {
  publ c NullcastTrack ngF lter() {
    super("unexpected nullcast t ets", true);
  }

  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(NullcastTrack ngF lter.class);

  @V s bleForTest ng
  stat c f nal SearchCounter BAD_NULLCAST_QUERY_COUNT =
      SearchCounter.export("unexpected_nullcast_query_count");

  @V s bleForTest ng
  stat c f nal SearchCounter BAD_NULLCAST_RESULT_COUNT =
      SearchCounter.export("unexpected_nullcast_result_count");

  @Overr de
  protected Logger getLogger() {
    return LOG;
  }

  @Overr de
  protected SearchCounter getSens  veQueryCounter() {
    return BAD_NULLCAST_QUERY_COUNT;
  }

  @Overr de
  protected SearchCounter getSens  veResultsCounter() {
    return BAD_NULLCAST_RESULT_COUNT;
  }

  @Overr de
  protected Set<Long> getSens  veResults(Earlyb rdRequestContext requestContext,
                                          Earlyb rdResponse earlyb rdResponse) throws Except on {
     f (!requestContext.getParsedQuery().accept(
        new DetectPos  veOperatorV s or(SearchOperatorConstants.NULLCAST))) {
      return Earlyb rdResponseUt l.f ndUnexpectedNullcastStatus ds(
          earlyb rdResponse.getSearchResults(), requestContext.getRequest());
    } else {
      return new HashSet<>();
    }
  }

  /**
   * So  Earlyb rd requests are not searc s,  nstead, t y are scor ng requests.
   * T se requests supply a l st of  Ds to be scored.
   *    s OK to return nullcast t et result  f t   D  s suppl ed  n t  request.
   * T  extracts t  scor ng request t et  Ds.
   */
  @Overr de
  protected Set<Long> getExceptedResults(Earlyb rdRequestContext requestContext) {
    Earlyb rdRequest request = requestContext.getRequest();
     f (request == null
        || !request. sSetSearchQuery()
        || request.getSearchQuery().getSearchStatus dsS ze() == 0) {
      return  mmutableSet.of();
    }
    return request.getSearchQuery().getSearchStatus ds();
  }
}
