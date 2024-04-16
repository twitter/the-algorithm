package com.tw ter.search. ngester.p pel ne.strato_fetc rs;

 mport com.tw ter.per scope.ap .thr ftjava.Aud oSpacesLookupContext;
 mport com.tw ter.st ch.St ch;
 mport com.tw ter.strato.catalog.Fetch;
 mport com.tw ter.strato.cl ent.Cl ent;
 mport com.tw ter.strato.cl ent.Fetc r;
 mport com.tw ter.strato.data.Conv;
 mport com.tw ter.strato.thr ft.TBaseConv;
 mport com.tw ter.ubs.thr ftjava.Part c pants;
 mport com.tw ter.ut l.Future;

/**
 * Fetc s from t  aud o space part c pants strato column.
 */
publ c class Aud oSpacePart c pantsFetc r {
  pr vate stat c f nal Str ng PART C PANTS_STRATO_COLUMN = "";

  pr vate stat c f nal Aud oSpacesLookupContext
      EMPTY_AUD O_LOOKUP_CONTEXT = new Aud oSpacesLookupContext();

  pr vate f nal Fetc r<Str ng, Aud oSpacesLookupContext, Part c pants> fetc r;

  publ c Aud oSpacePart c pantsFetc r(Cl ent stratoCl ent) {
    fetc r = stratoCl ent.fetc r(
        PART C PANTS_STRATO_COLUMN,
        true, // enables c ck ng types aga nst catalog
        Conv.str ngConv(),
        TBaseConv.forClass(Aud oSpacesLookupContext.class),
        TBaseConv.forClass(Part c pants.class));
  }

  publ c Future<Fetch.Result<Part c pants>> fetch(Str ng space d) {
    return St ch.run(fetc r.fetch(space d, EMPTY_AUD O_LOOKUP_CONTEXT));
  }
}
