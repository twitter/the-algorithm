packagelon com.twittelonr.selonarch.elonarlybird.indelonx.facelonts;

import java.io.IOelonxcelonption;
import java.util.HashSelont;
import java.util.Itelonrator;
import java.util.Selont;

import org.apachelon.lucelonnelon.analysis.TokelonnStrelonam;
import org.apachelon.lucelonnelon.analysis.tokelonnattributelons.CharTelonrmAttributelon;
import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.selonarch.BoolelonanClauselon;
import org.apachelon.lucelonnelon.selonarch.BoolelonanQuelonry;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.TelonrmQuelonry;

import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontCountStatelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftTelonrmRelonquelonst;

public abstract class FacelontSkipList {
  public static class SkipTokelonnStrelonam elonxtelonnds TokelonnStrelonam {
    privatelon CharTelonrmAttributelon telonrmAtt = addAttributelon(CharTelonrmAttributelon.class);

    privatelon Itelonrator<Schelonma.FielonldInfo> itelonrator;
    privatelon Selont<Schelonma.FielonldInfo> facelontFielonlds = nelonw HashSelont<>();

    public void add(Schelonma.FielonldInfo fielonld) {
      this.facelontFielonlds.add(fielonld);
    }

    @Ovelonrridelon
    public final boolelonan increlonmelonntTokelonn() throws IOelonxcelonption {
      if (itelonrator == null) {
        itelonrator = facelontFielonlds.itelonrator();
      }

      whilelon (itelonrator.hasNelonxt()) {
        Schelonma.FielonldInfo fielonld = itelonrator.nelonxt();
        if (fielonld.gelontFielonldTypelon().isStorelonFacelontSkiplist()) {
          telonrmAtt.selontelonmpty();
          telonrmAtt.appelonnd(elonarlybirdFielonldConstant.gelontFacelontSkipFielonldNamelon(fielonld.gelontNamelon()));

          relonturn truelon;
        }
      }

      relonturn falselon;
    }
  }

  /**
   * Relonturns a Telonrm quelonry to selonarch in thelon givelonn facelont fielonld.
   */
  public static Telonrm gelontSkipListTelonrm(Schelonma.FielonldInfo facelontFielonld) {
    if (facelontFielonld.gelontFielonldTypelon().isStorelonFacelontSkiplist()) {
      relonturn nelonw Telonrm(elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon(),
                      elonarlybirdFielonldConstant.gelontFacelontSkipFielonldNamelon(facelontFielonld.gelontNamelon()));
    }
    relonturn null;
  }

  /**
   * Relonturns a disjunction quelonry that selonarchelons in all facelont fielonlds in thelon givelonn facelont count statelon.
   */
  public static Quelonry gelontSkipListQuelonry(FacelontCountStatelon facelontCountStatelon) {
    Selont<Schelonma.FielonldInfo> fielonldsWithSkipLists =
        facelontCountStatelon.gelontFacelontFielonldsToCountWithSkipLists();

    if (fielonldsWithSkipLists == null || fielonldsWithSkipLists.iselonmpty()) {
      relonturn null;
    }

    Quelonry skipLists;

    if (fielonldsWithSkipLists.sizelon() == 1) {
      skipLists = nelonw TelonrmQuelonry(gelontSkipListTelonrm(fielonldsWithSkipLists.itelonrator().nelonxt()));
    } elonlselon {
      BoolelonanQuelonry.Buildelonr disjunctionBuildelonr = nelonw BoolelonanQuelonry.Buildelonr();
      for (Schelonma.FielonldInfo facelontFielonld : fielonldsWithSkipLists) {
        disjunctionBuildelonr.add(
            nelonw TelonrmQuelonry(nelonw Telonrm(
                elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon(),
                elonarlybirdFielonldConstant.gelontFacelontSkipFielonldNamelon(facelontFielonld.gelontNamelon()))),
            BoolelonanClauselon.Occur.SHOULD);
      }
      skipLists = disjunctionBuildelonr.build();
    }

    relonturn skipLists;
  }

  /**
   * Relonturns a telonrm relonquelonst that can belon uselond to gelont telonrm statistics for thelon skip list telonrm
   * associatelond with thelon providelond facelont. Relonturns null, if this FacelontFielonld is configurelond to not
   * storelon a skiplist.
   */
  public static ThriftTelonrmRelonquelonst gelontSkipListTelonrmRelonquelonst(Schelonma schelonma, String facelontNamelon) {
    relonturn gelontSkipListTelonrmRelonquelonst(schelonma.gelontFacelontFielonldByFacelontNamelon(facelontNamelon));
  }

  /**
   * Relonturns a telonrm relonquelonst that can belon uselond to gelont telonrm statistics for thelon skip list telonrm
   * associatelond with thelon providelond facelont. Relonturns null, if this FacelontFielonld is configurelond to not
   * storelon a skiplist.
   */
  public static ThriftTelonrmRelonquelonst gelontSkipListTelonrmRelonquelonst(Schelonma.FielonldInfo facelontFielonld) {
    relonturn facelontFielonld != null && facelontFielonld.gelontFielonldTypelon().isStorelonFacelontSkiplist()
           ? nelonw ThriftTelonrmRelonquelonst(
                elonarlybirdFielonldConstant.gelontFacelontSkipFielonldNamelon(facelontFielonld.gelontNamelon()))
             .selontFielonldNamelon(elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon())
           : null;
  }

  /**
   * Relonturns a telonrm relonquelonst using thelon speloncifielond fielonldNamelon. This is only a telonmporary solution until
   * Blelonndelonr can accelonss thelon Schelonma to pass thelon FacelontIDMap into thelon melonthod abovelon.
   *
   * @delonpreloncatelond Telonmporary solution until Blelonndelonr
   */
  @Delonpreloncatelond
  public static ThriftTelonrmRelonquelonst gelontSkipListTelonrmRelonquelonst(String fielonldNamelon) {
    relonturn nelonw ThriftTelonrmRelonquelonst(elonarlybirdFielonldConstant.gelontFacelontSkipFielonldNamelon(fielonldNamelon))
        .selontFielonldNamelon(elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon());
  }
}
