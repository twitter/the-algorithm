package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;
 mport java.ut l.Arrays;

 mport org.apac .lucene.store.Data nput;
 mport org.apac .lucene.store.DataOutput;
 mport org.apac .lucene.ut l.ArrayUt l;
 mport org.apac .lucene.ut l.ByteBlockPool;
 mport org.apac .lucene.ut l.BytesRef;

 mport stat c org.apac .lucene.ut l.RamUsageEst mator.NUM_BYTES_OBJECT_REF;

/**
 * Base class for BlockPools backed by byte[] arrays.
 */
publ c abstract class BaseByteBlockPool {
  /**
   * T  extra object w h f nal array  s necessary to guarantee v s b l y to
   * ot r threads w hout synchron zat on/us ng volat le.
   *
   * From 'Java Concurrency  n pract ce' by Br an Goetz, p. 349:
   *
   * " n  al zat on safety guarantees that for properly constructed objects, all
   *  threads w ll see t  correct values of f nal f elds that  re set by t  con-
   *  structor, regardless of how t  object  s publ s d. Furt r, any var ables
   *  that can be reac d through a f nal f eld of a properly constructed object
   *  (such as t  ele nts of a f nal array or t  contents of a HashMap refer-
   *  enced by a f nal f eld) are also guaranteed to be v s ble to ot r threads."
   */
  publ c stat c f nal class Pool {
    publ c f nal byte[][] buffers;

    publ c Pool(byte[][] buffers) {
      t .buffers = buffers;
    }

    publ c byte[][] getBlocks() {
      return buffers;
    }
  }

  publ c Pool pool = new Pool(new byte[10][]);
  // T   ndex of t  current buffer  n pool.buffers.
  publ c  nt bufferUpto = -1;
  // T  number of bytes that have been wr ten  n t  current buffer.
  publ c  nt byteUpto = ByteBlockPool.BYTE_BLOCK_S ZE;
  // T  current buffer,  .e. a reference to pool.buffers[bufferUpto]
  publ c byte[] buffer;
  // T  total number of bytes that have been used up to now, exclud ng t  current buffer.
  publ c  nt byteOffset = -ByteBlockPool.BYTE_BLOCK_S ZE;
  // T  one and only Wr eStream for t  pool.
  pr vate Wr eStream wr eStream = new Wr eStream();

  protected BaseByteBlockPool() { }

  /**
   * Used for load ng flus d pool.
   */
  protected BaseByteBlockPool(Pool pool,  nt bufferUpto,  nt byteUpTo,  nt byteOffset) {
    t .pool = pool;
    t .bufferUpto = bufferUpto;
    t .byteUpto = byteUpTo;
    t .byteOffset = byteOffset;
     f (bufferUpto >= 0) {
      t .buffer = pool.buffers[bufferUpto];
    }
  }

  /**
   * Resets t   ndex of t  pool to 0  n t  f rst buffer and resets t  byte arrays of
   * all prev ously allocated buffers to 0s.
   */
  publ c vo d reset() {
     f (bufferUpto != -1) {
      //   allocated at least one buffer

      for ( nt   = 0;   < bufferUpto;  ++) {
        // Fully zero f ll buffers that   fully used
        Arrays.f ll(pool.buffers[ ], (byte) 0);
      }

      // Part al zero f ll t  f nal buffer
      Arrays.f ll(pool.buffers[bufferUpto], 0, byteUpto, (byte) 0);

      bufferUpto = 0;
      byteUpto = 0;
      byteOffset = 0;
      buffer = pool.buffers[0];
    }
  }

  /**
   * Sw c s to t  next buffer and pos  ons t   ndex at  s beg nn ng.
   */
  publ c vo d nextBuffer() {
     f (1 + bufferUpto == pool.buffers.length) {
      byte[][] newBuffers = new byte[ArrayUt l.overs ze(pool.buffers.length + 1,
                                                           NUM_BYTES_OBJECT_REF)][];
      System.arraycopy(pool.buffers, 0, newBuffers, 0, pool.buffers.length);
      pool = new Pool(newBuffers);
    }
    buffer = pool.buffers[1 + bufferUpto] = new byte[ByteBlockPool.BYTE_BLOCK_S ZE];
    bufferUpto++;

    byteUpto = 0;
    byteOffset += ByteBlockPool.BYTE_BLOCK_S ZE;
  }

  /**
   * Returns t  start offset of t  next data that w ll be added to t  pool, UNLESS t  data  s
   * added us ng addBytes and avo dSpl t ng = true
   */
  publ c  nt getOffset() {
    return byteOffset + byteUpto;
  }

  /**
   * Returns t  start offset of b  n t  pool
   * @param b byte to put
   */
  publ c  nt addByte(byte b) {
     nt  n Offset = byteOffset + byteUpto;
     nt rema n ngBytes nBuffer = ByteBlockPool.BYTE_BLOCK_S ZE - byteUpto;
    //  f t  buffer  s full, move on to t  next one.
     f (rema n ngBytes nBuffer <= 0) {
      nextBuffer();
    }
    buffer[byteUpto] = b;
    byteUpto++;
    return  n Offset;
  }

  /**
   * Returns t  start offset of t  bytes  n t  pool.
   *         f avo dSpl t ng  s false, t   s guaranteed to return t  sa  value that would be
   *        returned by getOffset()
   * @param bytes s ce array
   * @param length number of bytes to put
   * @param avo dSpl t ng  f poss ble (t  length  s less than ByteBlockPool.BYTE_BLOCK_S ZE),
   *        t  bytes w ll not be spl  across buffer boundar es. T   s useful for small data
   *        that w ll be read a lot (small amount of space wasted  n return for avo d ng copy ng
   *         mory w n call ng getBytes).
   */
  publ c  nt addBytes(byte[] bytes,  nt offset,  nt length, boolean avo dSpl t ng) {
    // T  f rst t   t   s called, t re may not be an ex st ng buffer yet.
     f (buffer == null) {
      nextBuffer();
    }

     nt rema n ngBytes nBuffer = ByteBlockPool.BYTE_BLOCK_S ZE - byteUpto;

     f (avo dSpl t ng && length < ByteBlockPool.BYTE_BLOCK_S ZE) {
       f (rema n ngBytes nBuffer < length) {
        nextBuffer();
      }
       nt  n Offset = byteOffset + byteUpto;
      System.arraycopy(bytes, offset, buffer, byteUpto, length);
      byteUpto += length;
      return  n Offset;
    } else {
       nt  n Offset = byteOffset + byteUpto;
       f (rema n ngBytes nBuffer < length) {
        // Must spl  t  bytes across buffers.
         nt rema n ngLength = length;
        wh le (rema n ngLength > ByteBlockPool.BYTE_BLOCK_S ZE - byteUpto) {
           nt lengthToCopy = ByteBlockPool.BYTE_BLOCK_S ZE - byteUpto;
          System.arraycopy(bytes, length - rema n ngLength + offset,
                  buffer, byteUpto, lengthToCopy);
          rema n ngLength -= lengthToCopy;
          nextBuffer();
        }
        System.arraycopy(bytes, length - rema n ngLength + offset,
                buffer, byteUpto, rema n ngLength);
        byteUpto += rema n ngLength;
      } else {
        // Just add all bytes to t  current buffer.
        System.arraycopy(bytes, offset, buffer, byteUpto, length);
        byteUpto += length;
      }
      return  n Offset;
    }
  }

  /**
   * Default addBytes. Does not avo d spl t ng.
   * @see #addBytes(byte[],  nt, boolean)
   */
  publ c  nt addBytes(byte[] bytes,  nt length) {
    return addBytes(bytes, 0, length, false);
  }

  /**
   * Default addBytes. Does not avo d spl t ng.
   * @see #addBytes(byte[],  nt, boolean)
   */
  publ c  nt addBytes(byte[] bytes,  nt offset,  nt length) {
    return addBytes(bytes, offset, length, false);
  }

  /**
   * Reads one byte from t  pool.
   * @param offset locat on to read byte from
   */
  publ c byte getByte( nt offset) {
     nt buffer ndex = offset >>> ByteBlockPool.BYTE_BLOCK_SH FT;
     nt bufferOffset = offset & ByteBlockPool.BYTE_BLOCK_MASK;
    return pool.buffers[buffer ndex][bufferOffset];
  }

  /**
   * Returns false  f offset  s  nval d or t re aren't t se many bytes
   * ava lable  n t  pool.
   * @param offset locat on to start read ng bytes from
   * @param length number of bytes to read
   * @param output t  object to wr e t  output to. MUST be non null.
   */
  publ c boolean getBytesToBytesRef( nt offset,  nt length, BytesRef output) {
     f (offset < 0 || offset + length > byteUpto + byteOffset) {
      return false;
    }
     nt currentBuffer = offset >>> ByteBlockPool.BYTE_BLOCK_SH FT;
     nt currentOffset = offset & ByteBlockPool.BYTE_BLOCK_MASK;
    //  f t  requested bytes are spl  across pools,   have to make a new array of bytes
    // to copy t m  nto and return a ref to that.
     f (currentOffset + length <= ByteBlockPool.BYTE_BLOCK_S ZE) {
      output.bytes = pool.buffers[currentBuffer];
      output.offset = currentOffset;
      output.length = length;
    } else {
      byte[] bytes = new byte[length];
       nt rema n ngLength = length;
      wh le (rema n ngLength > ByteBlockPool.BYTE_BLOCK_S ZE - currentOffset) {
         nt lengthToCopy = ByteBlockPool.BYTE_BLOCK_S ZE - currentOffset;
        System.arraycopy(pool.buffers[currentBuffer], currentOffset, bytes,
                         length - rema n ngLength, lengthToCopy);
        rema n ngLength -= lengthToCopy;
        currentBuffer++;
        currentOffset = 0;
      }
      System.arraycopy(pool.buffers[currentBuffer], currentOffset, bytes, length - rema n ngLength,
                       rema n ngLength);
      output.bytes = bytes;
      output.length = bytes.length;
      output.offset = 0;
    }
    return true;

  }

  /**
   * Returns t  read bytes, or null  f offset  s  nval d or t re aren't t se many bytes
   * ava lable  n t  pool.
   * @param offset locat on to start read ng bytes from
   * @param length number of bytes to read
   */
  publ c BytesRef getBytes( nt offset,  nt length) {
    BytesRef result = new BytesRef();
     f (getBytesToBytesRef(offset, length, result)) {
      return result;
    } else {
      return null;
    }
  }

  /**
   * get a new readStream at a g ven offset for t  pool.
   *
   * Not ce that  nd v dual ReadStreams are not threadsafe, but   can get as many ReadStreams as
   *   want.
   */
  publ c ReadStream getReadStream( nt offset) {
    return new ReadStream(offset);
  }

  /**
   * get t  (one and only) Wr eStream for t  pool.
   *
   * Not ce that t re  s exactly one Wr eStream per pool, and    s not threadsafe.
   */
  publ c Wr eStream getWr eStream() {
    return wr eStream;
  }

  /**
   * A DataOutput-l ke  nterface for wr  ng "cont guous" data to a ByteBlockPool.
   *
   * T   s not threadsafe.
   */
  publ c f nal class Wr eStream extends DataOutput {
    pr vate Wr eStream() { }

    /**
     * Returns t  start offset of t  next data that w ll be added to t  pool, UNLESS t  data  s
     * added us ng addBytes and avo dSpl t ng = true
     */
    publ c  nt getOffset() {
      return BaseByteBlockPool.t .getOffset();
    }

    /**
     * Wr e bytes to t  pool.
     * @param bytes  s ce array
     * @param offset  offset  n bytes of t  data to wr e
     * @param length  number of bytes to put
     * @param avo dSpl t ng  sa  as {l nk ByteBlockPool.addBytes}
     * @return  t  start offset of t  bytes  n t  pool
     */
    publ c  nt wr eBytes(byte[] bytes,  nt offset,  nt length, boolean avo dSpl t ng) {
      return addBytes(bytes, offset, length, avo dSpl t ng);
    }

    @Overr de
    publ c vo d wr eBytes(byte[] b,  nt offset,  nt length) throws  OExcept on {
      addBytes(b, offset, length);
    }

    @Overr de
    publ c vo d wr eByte(byte b) {
      addByte(b);
    }
  }

  /**
   * A Data nput-l ke  nterface for read ng "cont guous" data from a ByteBlockPool.
   *
   * T   s not threadsafe.
   *
   * T  does not fully  mple nt t  Data nput  nterface -  s Data nput.readBytes  thod throws
   * UnsupportedOperat onExcept on because t  class prov des a fac l y for no-copy read ng.
   */
  publ c f nal class ReadStream extends Data nput {
    pr vate  nt offset;

    pr vate ReadStream( nt offset) {
      t .offset = offset;
    }

    publ c BytesRef readBytes( nt n) {
      return readBytes(n, false);
    }

    /**
     * read n bytes that  re wr ten w h a g ven value of avo dSpl t ng
     * @param n  number of bytes to read.
     * @param avo dSpl t ng  t  should be t  sa  that was used at wr eBytes t  .
     * @return  a reference to t  bytes read or null.
     */
    publ c BytesRef readBytes( nt n, boolean avo dSpl t ng) {
       nt currentBuffer = offset >>> ByteBlockPool.BYTE_BLOCK_SH FT;
       nt currentOffset = offset & ByteBlockPool.BYTE_BLOCK_MASK;
       f (avo dSpl t ng && n < ByteBlockPool.BYTE_BLOCK_S ZE
          && currentOffset + n > ByteBlockPool.BYTE_BLOCK_S ZE) {
        ++currentBuffer;
        currentOffset = 0;
        offset = currentBuffer << ByteBlockPool.BYTE_BLOCK_SH FT;
      }
      BytesRef result = getBytes(offset, n);
      t .offset += n;
      return result;
    }

    @Overr de
    publ c byte readByte() {
      return getByte(offset++);
    }

    @Overr de
    publ c vo d readBytes(byte[] b,  nt off,  nt len) throws  OExcept on {
      throw new UnsupportedOperat onExcept on("Use t  no-cop es vers on of ReadBytes  nstead.");
    }
  }
}
