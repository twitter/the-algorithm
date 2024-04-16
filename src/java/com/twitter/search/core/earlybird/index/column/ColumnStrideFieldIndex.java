package com.tw ter.search.core.earlyb rd. ndex.column;

 mport java. o. OExcept on;

 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene. ndex.Nu r cDocValues;

 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;

/**
 * Get an underly ng data for a f eld by call ng
 * Earlyb rd ndexSeg ntAtom cReader#getNu r cDocValues(Str ng).
 */
publ c abstract class ColumnStr deF eld ndex {
  pr vate f nal Str ng na ;

  publ c ColumnStr deF eld ndex(Str ng na ) {
    t .na  = na ;
  }

  publ c Str ng getNa () {
    return na ;
  }

  /**
   * Returns t  CSF value for t  g ven doc  D.
   */
  publ c abstract long get( nt doc D);

  /**
   * Updates t  CSF value for t  g ven doc  D to t  g ven value.
   */
  publ c vo d setValue( nt doc D, long value) {
    throw new UnsupportedOperat onExcept on();
  }

  /**
   * Loads t  CSF from an Atom cReader.
   */
  publ c vo d load(LeafReader atom cReader, Str ng f eld) throws  OExcept on {
    Nu r cDocValues docValues = atom cReader.getNu r cDocValues(f eld);
     f (docValues != null) {
      for ( nt   = 0;   < atom cReader.maxDoc();  ++) {
         f (docValues.advanceExact( )) {
          setValue( , docValues.longValue());
        }
      }
    }
  }

  /**
   * Opt m zes t  representat on of t  column str de f eld, and remaps  s doc  Ds,  f necessary.
   *
   * @param or g nalT et dMapper T  or g nal t et  D mapper.
   * @param opt m zedT et dMapper T  opt m zed t et  D mapper.
   * @return An opt m zed column str de f eld equ valent to t  CSF,
   *         w h poss bly remapped doc  Ds.
   */
  publ c ColumnStr deF eld ndex opt m ze(
      Doc DToT et DMapper or g nalT et dMapper,
      Doc DToT et DMapper opt m zedT et dMapper) throws  OExcept on {
    return t ;
  }
}
