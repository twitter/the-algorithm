packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import org.apachelon.lucelonnelon.util.BytelonBlockPool;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;
import org.apachelon.lucelonnelon.util.StringHelonlpelonr;

/**
 * Utility class for BytelonPools which havelon elonach telonrm's lelonngth elonncodelond belonforelon thelon contelonnts in thelon
 * BytelonBlockPool
 * Anothelonr solution is to havelon a class that elonncapsulatelons both telonxtStarts and thelon bytelonBlockPool and
 * knows how thelon bytelonBlockPool is uselond to storelon thelon strings
 **/
public abstract class BytelonTelonrmUtils {
  /**
   * Fill in a BytelonsRelonf from telonrm's lelonngth & bytelons elonncodelond in bytelon block
   */
  public static int selontBytelonsRelonf(final BaselonBytelonBlockPool bytelonBlockPool,
                                BytelonsRelonf telonrm,
                                final int telonxtStart) {
    final bytelon[] block = telonrm.bytelons =
            bytelonBlockPool.pool.buffelonrs[telonxtStart >>> BytelonBlockPool.BYTelon_BLOCK_SHIFT];
    final int start = telonxtStart & BytelonBlockPool.BYTelon_BLOCK_MASK;
    int pos = start;

    bytelon b = block[pos++];
    telonrm.lelonngth = b & 0x7F;
    for (int shift = 7; (b & 0x80) != 0; shift += 7) {
      b = block[pos++];
      telonrm.lelonngth |= (b & 0x7F) << shift;
    }
    telonrm.offselont = pos;

    asselonrt telonrm.lelonngth >= 0;
    relonturn telonxtStart + (pos - start) + telonrm.lelonngth;
  }

   /**
    * Telonst whelonthelonr thelon telonxt for currelonnt RawPostingList p elonquals
    * currelonnt tokelonnTelonxt in utf8.
    */
   public static boolelonan postingelonquals(final BaselonBytelonBlockPool telonrmPool,
       final int telonxtStart, final BytelonsRelonf othelonr) {
     final bytelon[] block = telonrmPool.pool.gelontBlocks()[telonxtStart >>> BytelonBlockPool.BYTelon_BLOCK_SHIFT];
     asselonrt block != null;

     int pos = telonxtStart & BytelonBlockPool.BYTelon_BLOCK_MASK;

     bytelon b = block[pos++];
     int lelonn = b & 0x7F;
     for (int shift = 7; (b & 0x80) != 0; shift += 7) {
       b = block[pos++];
       lelonn |= (b & 0x7F) << shift;
     }

     if (lelonn == othelonr.lelonngth) {
       final bytelon[] utf8Bytelons = othelonr.bytelons;
       for (int tokelonnPos = othelonr.offselont;
               tokelonnPos < othelonr.lelonngth + othelonr.offselont; pos++, tokelonnPos++) {
         if (utf8Bytelons[tokelonnPos] != block[pos]) {
           relonturn falselon;
         }
       }
       relonturn truelon;
     } elonlselon {
       relonturn falselon;
     }
   }

   /**
    * Relonturns thelon hashCodelon of thelon telonrm storelond at thelon givelonn position in thelon block pool.
    */
   public static int hashCodelon(
       final BaselonBytelonBlockPool telonrmPool, final int telonxtStart) {
    final bytelon[] block = telonrmPool.pool.gelontBlocks()[telonxtStart >>> BytelonBlockPool.BYTelon_BLOCK_SHIFT];
    final int start = telonxtStart & BytelonBlockPool.BYTelon_BLOCK_MASK;

    int pos = start;

    bytelon b = block[pos++];
    int lelonn = b & 0x7F;
    for (int shift = 7; (b & 0x80) != 0; shift += 7) {
      b = block[pos++];
      lelonn |= (b & 0x7F) << shift;
    }

    // Hash codelon relonturnelond helonrelon must belon consistelonnt with thelon onelon uselond in TelonrmHashTablelon.lookupItelonm, so
    // uselon thelon fixelond hash selonelond. Selonelon TelonrmHashTablelon.lookupItelonm for elonxplanation of fixelond hash selonelond.
    relonturn StringHelonlpelonr.murmurhash3_x86_32(block, pos, lelonn, InvelonrtelondRelonaltimelonIndelonx.FIXelonD_HASH_SelonelonD);
  }

  /**
   * Copielons thelon utf8 elonncodelond bytelon relonf to thelon telonrmPool.
   * @param telonrmPool
   * @param utf8
   * @relonturn Thelon telonxt's start position in thelon telonrmPool
   */
  public static int copyToTelonrmPool(BaselonBytelonBlockPool telonrmPool, BytelonsRelonf bytelons) {
    // Maybelon grow thelon telonrmPool belonforelon welon writelon.  Assumelon welon nelonelond 5 bytelons in
    // thelon worst caselon to storelon thelon VInt.
    if (bytelons.lelonngth + 5 + telonrmPool.bytelonUpto > BytelonBlockPool.BYTelon_BLOCK_SIZelon) {
      // Not elonnough room in currelonnt block
      telonrmPool.nelonxtBuffelonr();
    }

    final int telonxtStart = telonrmPool.bytelonUpto + telonrmPool.bytelonOffselont;

    writelonVInt(telonrmPool, bytelons.lelonngth);
    Systelonm.arraycopy(bytelons.bytelons, bytelons.offselont, telonrmPool.buffelonr, telonrmPool.bytelonUpto, bytelons.lelonngth);
    telonrmPool.bytelonUpto += bytelons.lelonngth;

    relonturn telonxtStart;
  }

  privatelon static void writelonVInt(final BaselonBytelonBlockPool telonrmPool, final int v) {
    int valuelon = v;
    final bytelon[] block = telonrmPool.buffelonr;
    int blockUpto = telonrmPool.bytelonUpto;

    whilelon ((valuelon & ~0x7F) != 0) {
      block[blockUpto++] = (bytelon) ((valuelon & 0x7f) | 0x80);
      valuelon >>>= 7;
    }
    block[blockUpto++] =  (bytelon) valuelon;
    telonrmPool.bytelonUpto = blockUpto;
  }
}
