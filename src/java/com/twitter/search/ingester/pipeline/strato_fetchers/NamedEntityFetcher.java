package com.tw ter.search. ngester.p pel ne.strato_fetc rs;

 mport scala.Opt on;

 mport com.tw ter.cuad.ner.pla n.thr ftjava.Na dEnt  es;
 mport com.tw ter.cuad.ner.pla n.thr ftjava.Na dEnt  esRequestOpt ons;
 mport com.tw ter.cuad.ner.thr ftjava.ModelFam ly;
 mport com.tw ter.cuad.ner.thr ftjava.NERCal brateRequest;
 mport com.tw ter.cuad.thr ftjava.Cal brat onLevel;
 mport com.tw ter.cuad.thr ftjava.NERCand dateS ce;
 mport com.tw ter.st ch.St ch;
 mport com.tw ter.strato.catalog.Fetch;
 mport com.tw ter.strato.cl ent.Cl ent;
 mport com.tw ter.strato.cl ent.Fetc r;
 mport com.tw ter.strato.data.Conv;
 mport com.tw ter.strato.opcontext.ServeW h n;
 mport com.tw ter.strato.thr ft.TBaseConv;
 mport com.tw ter.ut l.Durat on;
 mport com.tw ter.ut l.Future;

publ c class Na dEnt yFetc r {
  pr vate stat c f nal Str ng NAMED_ENT TY_STRATO_COLUMN = "";

  pr vate stat c f nal ServeW h n SERVE_W TH N = new ServeW h n(
      Durat on.fromM ll seconds(100), Opt on.empty());

  pr vate stat c f nal Na dEnt  esRequestOpt ons REQUEST_OPT ONS =
      new Na dEnt  esRequestOpt ons(
      new NERCal brateRequest(Cal brat onLevel.H GH_PREC S ON, NERCand dateS ce.NER_CRF)
          .setModel_fam ly(ModelFam ly.CFB))
      .setD splay_ent y_ nfo(false);

  pr vate f nal Fetc r<Long, Na dEnt  esRequestOpt ons, Na dEnt  es> fetc r;

  publ c Na dEnt yFetc r(Cl ent stratoCl ent) {
    fetc r = stratoCl ent.fetc r(
        NAMED_ENT TY_STRATO_COLUMN,
        true, // enables c ck ng types aga nst catalog
        Conv.longConv(),
        TBaseConv.forClass(Na dEnt  esRequestOpt ons.class),
        TBaseConv.forClass(Na dEnt  es.class)).serveW h n(SERVE_W TH N);
  }

  publ c Future<Fetch.Result<Na dEnt  es>> fetch(long t et d) {
    return St ch.run(fetc r.fetch(t et d, REQUEST_OPT ONS));
  }
}
