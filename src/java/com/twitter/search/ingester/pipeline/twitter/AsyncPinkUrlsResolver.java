package com.tw ter.search. ngester.p pel ne.tw ter;

 mport java.ut l.Collect on;
 mport java.ut l.L st;
 mport java.ut l.Map;

 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect.Maps;

 mport com.tw ter.p nk_floyd.thr ft.Cl ent dent f er;
 mport com.tw ter.p nk_floyd.thr ft.Mask;
 mport com.tw ter.p nk_floyd.thr ft.Storer;
 mport com.tw ter.p nk_floyd.thr ft.UrlData;
 mport com.tw ter.p nk_floyd.thr ft.UrlReadRequest;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Future;

/**
 * Resolve compressed URL v a P nk
 */
publ c class AsyncP nkUrlsResolver {
  pr vate f nal Storer.Serv ce face storerCl ent;
  pr vate f nal Cl ent dent f er p nkCl ent d;
  pr vate f nal Mask requestMask;

  // Use ServerSet to construct a  tadata store cl ent
  publ c AsyncP nkUrlsResolver(Storer.Serv ce face storerCl ent, Str ng p nkCl ent d) {
    t .storerCl ent = storerCl ent;
    t .p nkCl ent d = Cl ent dent f er.valueOf(p nkCl ent d);

    requestMask = new Mask();
    requestMask.setResolut on(true);
    requestMask.setHtmlBas cs(true);
    requestMask.setUrlD rect nfo(true);
  }

  /**
   * resolve urls call ng p nk asynchronously
   * @param urls urls to resolve
   * @return Future map of resolved urls
   */
  publ c Future<Map<Str ng, ResolveCompressedUrlsUt ls.Url nfo>> resolveUrls(
      Collect on<Str ng> urls) {
     f (urls == null || urls.s ze() == 0) {
      Future.value(Maps.newHashMap());
    }

    L st<Str ng> urlsL st =  mmutableL st.copyOf(urls);

    UrlReadRequest request = new UrlReadRequest();
    request.setUrls(urlsL st);
    request.setCl ent d(p nkCl ent d);
    request.setMask(requestMask);

    return storerCl ent.read(request).map(Funct on.func(
        response -> {
          Map<Str ng, ResolveCompressedUrlsUt ls.Url nfo> resultMap = Maps.newHashMap();
          for (UrlData urlData : response.getData()) {
             f (ResolveCompressedUrlsUt ls. sResolved(urlData)) {
              resultMap.put(urlData.url, ResolveCompressedUrlsUt ls.getUrl nfo(urlData));
            }
          }
          return resultMap;
        }
    ));
  }
}
