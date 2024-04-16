package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport org.apac .lucene.ut l.ByteBlockPool;
 mport org.apac .lucene.ut l.BytesRef;
 mport org.apac .lucene.ut l.Str ng lper;

/**
 * Ut l y class for BytePools wh ch have each term's length encoded before t  contents  n t 
 * ByteBlockPool
 * Anot r solut on  s to have a class that encapsulates both textStarts and t  byteBlockPool and
 * knows how t  byteBlockPool  s used to store t  str ngs
 **/
publ c abstract class ByteTermUt ls {
  /**
   * F ll  n a BytesRef from term's length & bytes encoded  n byte block
   */
  publ c stat c  nt setBytesRef(f nal BaseByteBlockPool byteBlockPool,
                                BytesRef term,
                                f nal  nt textStart) {
    f nal byte[] block = term.bytes =
            byteBlockPool.pool.buffers[textStart >>> ByteBlockPool.BYTE_BLOCK_SH FT];
    f nal  nt start = textStart & ByteBlockPool.BYTE_BLOCK_MASK;
     nt pos = start;

    byte b = block[pos++];
    term.length = b & 0x7F;
    for ( nt sh ft = 7; (b & 0x80) != 0; sh ft += 7) {
      b = block[pos++];
      term.length |= (b & 0x7F) << sh ft;
    }
    term.offset = pos;

    assert term.length >= 0;
    return textStart + (pos - start) + term.length;
  }

   /**
    * Test w t r t  text for current RawPost ngL st p equals
    * current tokenText  n utf8.
    */
   publ c stat c boolean post ngEquals(f nal BaseByteBlockPool termPool,
       f nal  nt textStart, f nal BytesRef ot r) {
     f nal byte[] block = termPool.pool.getBlocks()[textStart >>> ByteBlockPool.BYTE_BLOCK_SH FT];
     assert block != null;

      nt pos = textStart & ByteBlockPool.BYTE_BLOCK_MASK;

     byte b = block[pos++];
      nt len = b & 0x7F;
     for ( nt sh ft = 7; (b & 0x80) != 0; sh ft += 7) {
       b = block[pos++];
       len |= (b & 0x7F) << sh ft;
     }

      f (len == ot r.length) {
       f nal byte[] utf8Bytes = ot r.bytes;
       for ( nt tokenPos = ot r.offset;
               tokenPos < ot r.length + ot r.offset; pos++, tokenPos++) {
          f (utf8Bytes[tokenPos] != block[pos]) {
           return false;
         }
       }
       return true;
     } else {
       return false;
     }
   }

   /**
    * Returns t  hashCode of t  term stored at t  g ven pos  on  n t  block pool.
    */
   publ c stat c  nt hashCode(
       f nal BaseByteBlockPool termPool, f nal  nt textStart) {
    f nal byte[] block = termPool.pool.getBlocks()[textStart >>> ByteBlockPool.BYTE_BLOCK_SH FT];
    f nal  nt start = textStart & ByteBlockPool.BYTE_BLOCK_MASK;

     nt pos = start;

    byte b = block[pos++];
     nt len = b & 0x7F;
    for ( nt sh ft = 7; (b & 0x80) != 0; sh ft += 7) {
      b = block[pos++];
      len |= (b & 0x7F) << sh ft;
    }

    // Hash code returned  re must be cons stent w h t  one used  n TermHashTable.lookup em, so
    // use t  f xed hash seed. See TermHashTable.lookup em for explanat on of f xed hash seed.
    return Str ng lper.murmurhash3_x86_32(block, pos, len,  nvertedRealt   ndex.F XED_HASH_SEED);
  }

  /**
   * Cop es t  utf8 encoded byte ref to t  termPool.
   * @param termPool
   * @param utf8
   * @return T  text's start pos  on  n t  termPool
   */
  publ c stat c  nt copyToTermPool(BaseByteBlockPool termPool, BytesRef bytes) {
    // Maybe grow t  termPool before   wr e.  Assu    need 5 bytes  n
    // t  worst case to store t  V nt.
     f (bytes.length + 5 + termPool.byteUpto > ByteBlockPool.BYTE_BLOCK_S ZE) {
      // Not enough room  n current block
      termPool.nextBuffer();
    }

    f nal  nt textStart = termPool.byteUpto + termPool.byteOffset;

    wr eV nt(termPool, bytes.length);
    System.arraycopy(bytes.bytes, bytes.offset, termPool.buffer, termPool.byteUpto, bytes.length);
    termPool.byteUpto += bytes.length;

    return textStart;
  }

  pr vate stat c vo d wr eV nt(f nal BaseByteBlockPool termPool, f nal  nt v) {
     nt value = v;
    f nal byte[] block = termPool.buffer;
     nt blockUpto = termPool.byteUpto;

    wh le ((value & ~0x7F) != 0) {
      block[blockUpto++] = (byte) ((value & 0x7f) | 0x80);
      value >>>= 7;
    }
    block[blockUpto++] =  (byte) value;
    termPool.byteUpto = blockUpto;
  }
}
