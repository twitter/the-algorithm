package com.tw ter.search. ngester.p pel ne.strato_fetc rs;

 mport java.ut l.L st;
 mport java.ut l.Set;
 mport java.ut l.stream.Collectors;

 mport com.tw ter.per scope.ap .thr ftjava.Aud oSpacesLookupContext;
 mport com.tw ter.st ch.St ch;
 mport com.tw ter.strato.catalog.Fetch;
 mport com.tw ter.strato.cl ent.Cl ent;
 mport com.tw ter.strato.cl ent.Fetc r;
 mport com.tw ter.strato.data.Conv;
 mport com.tw ter.strato.thr ft.TBaseConv;
 mport com.tw ter.ubs.thr ftjava.Aud oSpace;
 mport com.tw ter.ut l.Future;
 mport com.tw ter.ut l.Try;

/**
 * Fetc s from t  aud o space core strato column.
 */
publ c class Aud oSpaceCoreFetc r {
  pr vate stat c f nal Str ng CORE_STRATO_COLUMN = "";

  pr vate stat c f nal Aud oSpacesLookupContext
      EMPTY_AUD O_LOOKUP_CONTEXT = new Aud oSpacesLookupContext();

  pr vate f nal Fetc r<Str ng, Aud oSpacesLookupContext, Aud oSpace> fetc r;

  publ c Aud oSpaceCoreFetc r(Cl ent stratoCl ent) {
    fetc r = stratoCl ent.fetc r(
        CORE_STRATO_COLUMN,
        true, // enables c ck ng types aga nst catalog
        Conv.str ngConv(),
        TBaseConv.forClass(Aud oSpacesLookupContext.class),
        TBaseConv.forClass(Aud oSpace.class));
  }

  publ c Future<Fetch.Result<Aud oSpace>> fetch(Str ng space d) {
    return St ch.run(fetc r.fetch(space d, EMPTY_AUD O_LOOKUP_CONTEXT));
  }

  /**
   * Use st ch to fetch mul  ple Aud oSpace Objects at once
   */
  publ c Future<L st<Try<Fetch.Result<Aud oSpace>>>> fetchBulkSpaces(Set<Str ng> space ds) {
    return St ch.run(
        St ch.collectToTry(
            space ds
                .stream()
                .map(space d -> fetc r.fetch(space d, EMPTY_AUD O_LOOKUP_CONTEXT))
                .collect(Collectors.toL st())
        )
    );
  }

}
