package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport org.apac .lucene.ut l.BytesRef;

/**
 * Ut l  es for encod ng and decod ng BytesRefs  nto  nts. T  encod ng  s:
 * [0..n] n bytes b g-end an decoded  nto  ntegers.
 * n: number of bytes.
 *
 * Example:
 * encode([DE, AD, BE, EF, AB]) => [0xDEADBEEF, 0xAB000000, 5]
 *
 *  's necessary to store t  length at t  end  nstead of t  start so that   can know how far to
 * jump backward from a sk pl st entry.   can't store   after t  sk p l st entry because t re
 * can be a var able number of po nters after t  sk p l st entry.
 *
 * An example sk p l st entry, w h labels on t  follow ng l ne:
 * [0xDEADBEEF,       12,   654,         0x877,       0x78879]
 * [   payload, pos  on, doc D, level0Po nter, level1Po nter]
 */
publ c f nal class PayloadUt l {
  pr vate PayloadUt l() {
  }

  publ c stat c f nal  nt[] EMPTY_PAYLOAD = new  nt[]{0};

  /**
   * Encodes a {@l nk BytesRef}  nto an  nt array (to be  nserted  nto a
   * {@l nk  ntBlockPool}. T  encoder cons ders t   nput to be b g-end an encoded  nts.
   */
  publ c stat c  nt[] encodePayload(BytesRef payload) {
     f (payload == null) {
      return EMPTY_PAYLOAD;
    }

     nt  nts nPayload =  ntsForBytes(payload.length);

     nt[] arr = new  nt[1 +  nts nPayload];

    for ( nt   = 0;   <  nts nPayload;  ++) {
       nt n = 0;
      for ( nt j = 0; j < 4; j++) {
         nt  ndex =   * 4 + j;
         nt b;
         f ( ndex < payload.length) {
          // mask off t  top b s  n case b  s negat ve.
          b = payload.bytes[ ndex] & 0xFF;
        } else {
          b = 0;
        }
        n = n << 8 | b;
      }

      arr[ ] = n;
    }

    arr[ nts nPayload] = payload.length;

    return arr;
  }

  /**
   * Decodes a {@l nk  ntBlockPool} and pos  on  nto a {@l nk BytesRef}. T   nts are
   * converted  nto b g-end an encoded bytes.
   */
  publ c stat c BytesRef decodePayload(
       ntBlockPool b,
       nt po nter) {
     nt length = b.get(po nter);
    BytesRef bytesRef = new BytesRef(length);
    bytesRef.length = length;

     nt num nts =  ntsForBytes(length);

    for ( nt   = 0;   < num nts;  ++) {
       nt n = b.get(po nter - num nts +  );
      for ( nt j = 0; j < 4; j++) {
         nt byte ndex = 4 *   + j;
         f (byte ndex < length) {
          bytesRef.bytes[byte ndex] = (byte) (n >> 8 * (3 - byte ndex % 4));
        }
      }
    }

    return bytesRef;
  }

  pr vate stat c  nt  ntsForBytes( nt byteCount) {
    return (byteCount + 3) / 4;
  }
}
