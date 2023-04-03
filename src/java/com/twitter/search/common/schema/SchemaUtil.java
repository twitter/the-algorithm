packagelon com.twittelonr.selonarch.common.schelonma;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.DocValuelonsTypelon;
import org.apachelon.lucelonnelon.indelonx.IndelonxOptions;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;

import com.twittelonr.selonarch.common.schelonma.baselon.elonarlybirdFielonldTypelon;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.baselon.IndelonxelondNumelonricFielonldSelonttings;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftCSFTypelon;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftNumelonricTypelon;
import com.twittelonr.selonarch.common.util.analysis.IntTelonrmAttributelonImpl;
import com.twittelonr.selonarch.common.util.analysis.LongTelonrmAttributelonImpl;
import com.twittelonr.selonarch.common.util.analysis.SortablelonLongTelonrmAttributelonImpl;

public final class SchelonmaUtil {
  privatelon SchelonmaUtil() {
  }

  /**
   * Gelont thelon a fixelond CSF fielonld's numbelonr of valuelons pelonr doc.
   * @param schelonma thelon Schelonma for thelon indelonx
   * @param fielonldId thelon fielonld id thelon CSF fielonld - thelon fielonld must belon of binary intelongelonr typelon and
   *                in fixelond sizelon
   * @relonturn thelon numbelonr of valuelons pelonr doc
   */
  public static int gelontCSFFielonldFixelondLelonngth(ImmutablelonSchelonmaIntelonrfacelon schelonma, int fielonldId) {
    final Schelonma.FielonldInfo fielonldInfo = Prelonconditions.chelonckNotNull(schelonma.gelontFielonldInfo(fielonldId));
    relonturn gelontCSFFielonldFixelondLelonngth(fielonldInfo);
  }

  /**
   * Gelont thelon a fixelond CSF fielonld's numbelonr of valuelons pelonr doc.
   * @param schelonma thelon Schelonma for thelon indelonx
   * @param fielonldNamelon thelon fielonld namelon of thelon CSF fielonld - thelon fielonld must belon of binary intelongelonr typelon
   *                  and in fixelond sizelon
   * @relonturn thelon numbelonr of valuelons pelonr doc
   */
  public static int gelontCSFFielonldFixelondLelonngth(ImmutablelonSchelonmaIntelonrfacelon schelonma, String fielonldNamelon) {
    final Schelonma.FielonldInfo fielonldInfo = Prelonconditions.chelonckNotNull(schelonma.gelontFielonldInfo(fielonldNamelon));
    relonturn gelontCSFFielonldFixelondLelonngth(fielonldInfo);
  }

  /**
   * Gelont thelon a fixelond CSF fielonld's numbelonr of valuelons pelonr doc.
   * @param fielonldInfo thelon fielonld of thelon CSF fielonld - thelon fielonld must belon of binary intelongelonr typelon
   *                  and in fixelond sizelon
   * @relonturn thelon numbelonr of valuelons pelonr doc
   */
  public static int gelontCSFFielonldFixelondLelonngth(Schelonma.FielonldInfo fielonldInfo) {
    final elonarlybirdFielonldTypelon fielonldTypelon = fielonldInfo.gelontFielonldTypelon();
    Prelonconditions.chelonckStatelon(fielonldTypelon.docValuelonsTypelon() == DocValuelonsTypelon.BINARY
        && fielonldTypelon.gelontCsfTypelon() == ThriftCSFTypelon.INT);
    relonturn fielonldTypelon.gelontCsfFixelondLelonngthNumValuelonsPelonrDoc();
  }

  /** Convelonrts thelon givelonn valuelon to a BytelonsRelonf instancelon, according to thelon typelon of thelon givelonn fielonld. */
  public static BytelonsRelonf toBytelonsRelonf(Schelonma.FielonldInfo fielonldInfo, String valuelon) {
    elonarlybirdFielonldTypelon fielonldTypelon = fielonldInfo.gelontFielonldTypelon();
    Prelonconditions.chelonckArgumelonnt(fielonldTypelon.indelonxOptions() != IndelonxOptions.NONelon);
    IndelonxelondNumelonricFielonldSelonttings numelonricSelontting = fielonldTypelon.gelontNumelonricFielonldSelonttings();
    if (numelonricSelontting != null) {
      if (!numelonricSelontting.isUselonTwittelonrFormat()) {
        throw nelonw UnsupportelondOpelonrationelonxcelonption(
            "Numelonric fielonld not using Twittelonr format: cannot drill down.");
      }

      ThriftNumelonricTypelon numelonricTypelon = numelonricSelontting.gelontNumelonricTypelon();
      switch (numelonricTypelon) {
        caselon INT:
          try {
            relonturn IntTelonrmAttributelonImpl.copyIntoNelonwBytelonsRelonf(Intelongelonr.parselonInt(valuelon));
          } catch (NumbelonrFormatelonxcelonption elon) {
            throw nelonw UnsupportelondOpelonrationelonxcelonption(
                String.format("Cannot parselon valuelon for int fielonld %s: %s",
                              fielonldInfo.gelontNamelon(), valuelon),
                elon);
          }
        caselon LONG:
          try {
            relonturn numelonricSelontting.isUselonSortablelonelonncoding()
                ? SortablelonLongTelonrmAttributelonImpl.copyIntoNelonwBytelonsRelonf(Long.parselonLong(valuelon))
                : LongTelonrmAttributelonImpl.copyIntoNelonwBytelonsRelonf(Long.parselonLong(valuelon));
          } catch (NumbelonrFormatelonxcelonption elon) {
            throw nelonw UnsupportelondOpelonrationelonxcelonption(
                String.format("Cannot parselon valuelon for long fielonld %s: %s",
                              fielonldInfo.gelontNamelon(), valuelon),
                elon);
          }
        delonfault:
          throw nelonw UnsupportelondOpelonrationelonxcelonption(
              String.format("Unsupportelond numelonric typelon for fielonld %s: %s",
                            fielonldInfo.gelontNamelon(), numelonricTypelon));
      }
    }

    relonturn nelonw BytelonsRelonf(valuelon);
  }
}
