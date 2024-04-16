package com.tw ter.search.core.earlyb rd. ndex.column;

 mport java. o. OExcept on;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.B naryDocValues;
 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene.ut l.BytesRef;

 mport com.tw ter.search.common.encod ng.docvalues.CSFTypeUt l;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;

publ c abstract class AbstractColumnStr deMult  nt ndex
    extends ColumnStr deF eld ndex  mple nts Flushable {
  pr vate stat c f nal  nt NUM_BYTES_PER_ NT = java.lang. nteger.S ZE / java.lang.Byte.S ZE;

  pr vate f nal  nt num ntsPerF eld;

  protected AbstractColumnStr deMult  nt ndex(Str ng na ,  nt num ntsPerF eld) {
    super(na );
    t .num ntsPerF eld = num ntsPerF eld;
  }

  publ c  nt getNum ntsPerF eld() {
    return num ntsPerF eld;
  }

  @Overr de
  publ c long get( nt doc D) {
    throw new UnsupportedOperat onExcept on();
  }

  /**
   * Returns t  value stored at t  g ven  ndex for t  g ven doc  D.
   */
  publ c abstract  nt get( nt doc D,  nt value ndex);

  /**
   * Sets t  value stored at t  g ven  ndex for t  g ven doc  D.
   */
  publ c abstract vo d setValue( nt doc D,  nt value ndex,  nt val);

  @Overr de
  publ c vo d load(LeafReader atom cReader, Str ng f eld) throws  OExcept on {
    B naryDocValues docValues = atom cReader.getB naryDocValues(f eld);
     nt numBytesPerDoc = num ntsPerF eld * NUM_BYTES_PER_ NT;

    for ( nt doc D = 0; doc D < atom cReader.maxDoc(); doc D++) {
      Precond  ons.c ckState(docValues.advanceExact(doc D));
      BytesRef scratch = docValues.b naryValue();
      Precond  ons.c ckState(
          scratch.length == numBytesPerDoc,
          "Unexpected doc value length for f eld " + f eld
          + ": Should be " + numBytesPerDoc + ", but was " + scratch.length);

      scratch.length = NUM_BYTES_PER_ NT;
      for ( nt   = 0;   < num ntsPerF eld;  ++) {
        setValue(doc D,  , as nt(scratch));
        scratch.offset += NUM_BYTES_PER_ NT;
      }
    }
  }

  publ c vo d updateDocValues(BytesRef ref,  nt doc D) {
    for ( nt   = 0;   < num ntsPerF eld;  ++) {
      setValue(doc D,  , CSFTypeUt l.convertFromBytes(ref.bytes, ref.offset,  ));
    }
  }

  pr vate stat c  nt as nt(BytesRef b) {
    return as nt(b, b.offset);
  }

  pr vate stat c  nt as nt(BytesRef b,  nt pos) {
     nt p = pos;
    return (b.bytes[p++] << 24) | (b.bytes[p++] << 16) | (b.bytes[p++] << 8) | (b.bytes[p] & 0xFF);
  }
}
