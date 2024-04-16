package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene.facet.FacetsConf g;
 mport org.apac .lucene. ndex.DocValuesType;
 mport org.apac .lucene. ndex. ndexableF eld;

 mport com.tw ter.search.common.sc ma.base.Earlyb rdF eldType;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rdRealt   ndexSeg ntWr er;
 mport com.tw ter.search.core.earlyb rd. ndex.column.AbstractColumnStr deMult  nt ndex;
 mport com.tw ter.search.core.earlyb rd. ndex.column.ColumnStr deF eld ndex;
 mport com.tw ter.search.core.earlyb rd. ndex.column.DocValuesManager;

/**
 * Handler for docvalues  n t   ndex ng cha n.
 */
publ c class Earlyb rdCSFDocValuesProcessor
     mple nts Earlyb rdRealt   ndexSeg ntWr er.StoredF eldsConsu r {

  pr vate f nal DocValuesManager docValuesManager;

  publ c Earlyb rdCSFDocValuesProcessor(DocValuesManager docValuesManager) {
    t .docValuesManager = docValuesManager;
  }

  @Overr de
  publ c vo d addF eld( nt doc D,  ndexableF eld f eld) throws  OExcept on {
    f nal DocValuesType dvType = f eld.f eldType().docValuesType();
     f (dvType != null) {

      //  gnore lucene facet f elds for realt    ndex,   are handl ng   d fferently
       f (f eld.na ().startsW h(FacetsConf g.DEFAULT_ NDEX_F ELD_NAME)) {
        return;
      }
       f (!(f eld.f eldType()  nstanceof Earlyb rdF eldType)) {
        throw new Runt  Except on(
            "f eldType must be an Earlyb rdF eldType  nstance for f eld " + f eld.na ());
      }
      Earlyb rdF eldType f eldType = (Earlyb rdF eldType) f eld.f eldType();

       f (dvType == DocValuesType.NUMER C) {
         f (!(f eld.nu r cValue()  nstanceof Long)) {
          throw new  llegalArgu ntExcept on(
              " llegal type " + f eld.nu r cValue().getClass()
              + ": DocValues types must be Long");
        }

        ColumnStr deF eld ndex csf ndex =
            docValuesManager.addColumnStr deF eld(f eld.na (), f eldType);
         f (f eldType.getCsfF xedLengthNumValuesPerDoc() > 1) {
          throw new UnsupportedOperat onExcept on("unsupported mult  nu r c values");
        } else {
          csf ndex.setValue(doc D, f eld.nu r cValue().longValue());
        }

      } else  f (dvType == DocValuesType.B NARY) {
        ColumnStr deF eld ndex csf ndex =
            docValuesManager.addColumnStr deF eld(f eld.na (), f eldType);
         f (f eldType.getCsfF xedLengthNumValuesPerDoc() > 1) {
          Precond  ons.c ckArgu nt(
              csf ndex  nstanceof AbstractColumnStr deMult  nt ndex,
              "Unsupported mult -value b nary CSF class: " + csf ndex);
          ((AbstractColumnStr deMult  nt ndex) csf ndex).updateDocValues(
              f eld.b naryValue(), doc D);
        }
      } else {
        throw new UnsupportedOperat onExcept on("unsupported DocValues.Type: " + dvType);
      }
    }
  }
}
