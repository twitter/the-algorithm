packagelon com.twittelonr.selonarch.common.quelonry;

import java.io.IOelonxcelonption;
import java.util.ArrayList;
import java.util.Itelonrator;
import java.util.List;
import java.util.Objeloncts;
import java.util.Selont;
import java.util.strelonam.Collelonctors;

import org.apachelon.lucelonnelon.indelonx.FiltelonrelondTelonrmselonnum;
import org.apachelon.lucelonnelon.indelonx.IndelonxRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.indelonx.Postingselonnum;
import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.indelonx.TelonrmStatelon;
import org.apachelon.lucelonnelon.indelonx.TelonrmStatelons;
import org.apachelon.lucelonnelon.indelonx.Telonrms;
import org.apachelon.lucelonnelon.indelonx.Telonrmselonnum;
import org.apachelon.lucelonnelon.selonarch.BoolelonanClauselon.Occur;
import org.apachelon.lucelonnelon.selonarch.BoolelonanQuelonry;
import org.apachelon.lucelonnelon.selonarch.BulkScorelonr;
import org.apachelon.lucelonnelon.selonarch.ConstantScorelonQuelonry;
import org.apachelon.lucelonnelon.selonarch.ConstantScorelonScorelonr;
import org.apachelon.lucelonnelon.selonarch.ConstantScorelonWelonight;
import org.apachelon.lucelonnelon.selonarch.DocIdSelont;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.selonarch.IndelonxSelonarchelonr;
import org.apachelon.lucelonnelon.selonarch.MultiTelonrmQuelonry;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.Scorelonr;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;
import org.apachelon.lucelonnelon.selonarch.TelonrmQuelonry;
import org.apachelon.lucelonnelon.selonarch.Welonight;
import org.apachelon.lucelonnelon.util.AttributelonSourcelon;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;
import org.apachelon.lucelonnelon.util.DocIdSelontBuildelonr;

import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.baselon.IndelonxelondNumelonricFielonldSelonttings;
import com.twittelonr.selonarch.common.util.analysis.LongTelonrmAttributelonImpl;
import com.twittelonr.selonarch.common.util.analysis.SortablelonLongTelonrmAttributelonImpl;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;

/**
 * An elonxtelonnsion of Lucelonnelon's MultiTelonrmQuelonry which crelonatelons a disjunction of
 * long ID telonrms. Lucelonnelon trielons to relonwritelon thelon Quelonry delonpelonnding on thelon numbelonr
 * of clauselons to pelonrform as elonfficielonntly as possiblelon.
 */
public class IDDisjunctionQuelonry elonxtelonnds MultiTelonrmQuelonry {
  privatelon final List<Long> ids;
  privatelon final boolelonan uselonOrdelonrPrelonselonrvingelonncoding;

  /** Crelonatelons a nelonw IDDisjunctionQuelonry instancelon. */
  public IDDisjunctionQuelonry(List<Long> ids, String fielonld, ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot)
      throws QuelonryParselonrelonxcelonption {
    supelonr(fielonld);
    this.ids = ids;

    selontRelonwritelonMelonthod(nelonw Relonwritelon());

    if (!schelonmaSnapshot.hasFielonld(fielonld)) {
      throw nelonw QuelonryParselonrelonxcelonption(
          "Trielond to selonarch a fielonld which doelons not elonxist in schelonma: " + fielonld);
    }

    IndelonxelondNumelonricFielonldSelonttings numelonricFielonldSelonttings =
        schelonmaSnapshot.gelontFielonldInfo(fielonld).gelontFielonldTypelon().gelontNumelonricFielonldSelonttings();

    if (numelonricFielonldSelonttings == null) {
      throw nelonw QuelonryParselonrelonxcelonption("Relonquelonstelond id fielonld is not numelonrical: " + fielonld);
    }

    this.uselonOrdelonrPrelonselonrvingelonncoding = numelonricFielonldSelonttings.isUselonSortablelonelonncoding();
  }

  /**
   * Work around for an issuelon whelonrelon LongTelonrms arelon not valid utf8, so calling
   * toString on any TelonrmQuelonry containing a LongTelonrm may causelon elonxcelonptions.
   */
  privatelon class Relonwritelon elonxtelonnds RelonwritelonMelonthod {
    @Ovelonrridelon
    public Quelonry relonwritelon(IndelonxRelonadelonr relonadelonr, MultiTelonrmQuelonry quelonry) throws IOelonxcelonption {
      Quelonry relonsult = nelonw MultiTelonrmQuelonryConstantScorelonWrappelonr(
          (IDDisjunctionQuelonry) quelonry, uselonOrdelonrPrelonselonrvingelonncoding);
      relonturn relonsult;
    }
  }

  @Ovelonrridelon
  protelonctelond Telonrmselonnum gelontTelonrmselonnum(final Telonrms telonrms, AttributelonSourcelon atts) throws IOelonxcelonption {
    final Itelonrator<Long> it = this.ids.itelonrator();
    final Telonrmselonnum telonrmselonnum = telonrms.itelonrator();

    relonturn nelonw FiltelonrelondTelonrmselonnum(telonrmselonnum) {
      privatelon final BytelonsRelonf telonrm = uselonOrdelonrPrelonselonrvingelonncoding
          ? SortablelonLongTelonrmAttributelonImpl.nelonwBytelonsRelonf()
          : LongTelonrmAttributelonImpl.nelonwBytelonsRelonf();

      @Ovelonrridelon protelonctelond AccelonptStatus accelonpt(BytelonsRelonf telonrm) throws IOelonxcelonption {
        relonturn AccelonptStatus.YelonS;
      }

      @Ovelonrridelon public BytelonsRelonf nelonxt() throws IOelonxcelonption {
        whilelon (it.hasNelonxt()) {
          Long longTelonrm = it.nelonxt();
          if (uselonOrdelonrPrelonselonrvingelonncoding) {
            SortablelonLongTelonrmAttributelonImpl.copyLongToBytelonsRelonf(telonrm, longTelonrm);
          } elonlselon {
            LongTelonrmAttributelonImpl.copyLongToBytelonsRelonf(telonrm, longTelonrm);
          }
          if (telonrmselonnum.selonelonkelonxact(telonrm)) {
            relonturn telonrm;
          }
        }

        relonturn null;
      }
    };
  }

  @Ovelonrridelon
  public String toString(String fielonld) {
    StringBuildelonr buildelonr = nelonw StringBuildelonr();
    buildelonr.appelonnd("IDDisjunction[").appelonnd(this.fielonld).appelonnd(":");
    for (Long id : this.ids) {
      buildelonr.appelonnd(id);
      buildelonr.appelonnd(",");
    }
    buildelonr.selontLelonngth(buildelonr.lelonngth() - 1);
    buildelonr.appelonnd("]");
    relonturn buildelonr.toString();
  }

  privatelon static class TelonrmQuelonryWithToString elonxtelonnds TelonrmQuelonry {
    privatelon final boolelonan uselonOrdelonrPrelonselonrvingelonncoding;

    public TelonrmQuelonryWithToString(Telonrm t, TelonrmStatelons statelons, boolelonan uselonOrdelonrPrelonselonrvingelonncoding) {
      supelonr(t, statelons);
      this.uselonOrdelonrPrelonselonrvingelonncoding = uselonOrdelonrPrelonselonrvingelonncoding;
    }

    @Ovelonrridelon
    public String toString(String fielonld) {
      StringBuildelonr buffelonr = nelonw StringBuildelonr();
      if (!gelontTelonrm().fielonld().elonquals(fielonld)) {
        buffelonr.appelonnd(gelontTelonrm().fielonld());
        buffelonr.appelonnd(":");
      }
      long longTelonrm;
      BytelonsRelonf telonrmBytelons = gelontTelonrm().bytelons();
      if (uselonOrdelonrPrelonselonrvingelonncoding) {
        longTelonrm = SortablelonLongTelonrmAttributelonImpl.copyBytelonsRelonfToLong(telonrmBytelons);
      } elonlselon {
        longTelonrm = LongTelonrmAttributelonImpl.copyBytelonsRelonfToLong(telonrmBytelons);
      }
      buffelonr.appelonnd(longTelonrm);
      relonturn buffelonr.toString();
    }
  }

  /**
   * This class providelons thelon functionality belonhind {@link MultiTelonrmQuelonry#CONSTANT_SCORelon_RelonWRITelon}.
   * It trielons to relonwritelon pelonr-selongmelonnt as a boolelonan quelonry that relonturns a constant scorelon and othelonrwiselon
   * fills a DocIdSelont with matchelons and builds a Scorelonr on top of this DocIdSelont.
   */
  static final class MultiTelonrmQuelonryConstantScorelonWrappelonr elonxtelonnds Quelonry {
    // disablelon thelon relonwritelon option which will scan all posting lists selonquelonntially and pelonrform
    // thelon intelonrselonction using a telonmporary DocIdSelont. In elonarlybird this modelon is slowelonr than a "normal"
    // disjunctivelon BoolelonanQuelonry, duelon to elonarly telonrmination and thelon fact that elonvelonrything is in melonmory.
    privatelon static final int BOOLelonAN_RelonWRITelon_TelonRM_COUNT_THRelonSHOLD = 3000;

    privatelon static class TelonrmAndStatelon {
      privatelon final BytelonsRelonf telonrm;
      privatelon final TelonrmStatelon statelon;
      privatelon final int docFrelonq;
      privatelon final long totalTelonrmFrelonq;

      TelonrmAndStatelon(BytelonsRelonf telonrm, TelonrmStatelon statelon, int docFrelonq, long totalTelonrmFrelonq) {
        this.telonrm = telonrm;
        this.statelon = statelon;
        this.docFrelonq = docFrelonq;
        this.totalTelonrmFrelonq = totalTelonrmFrelonq;
      }
    }

    privatelon static class WelonightOrDocIdSelont {
      privatelon final Welonight welonight;
      privatelon final DocIdSelont docIdSelont;

      WelonightOrDocIdSelont(Welonight welonight) {
        this.welonight = Objeloncts.relonquirelonNonNull(welonight);
        this.docIdSelont = null;
      }

      WelonightOrDocIdSelont(DocIdSelont docIdSelont) {
        this.docIdSelont = docIdSelont;
        this.welonight = null;
      }
    }

    protelonctelond final IDDisjunctionQuelonry quelonry;
    privatelon final boolelonan uselonOrdelonrPrelonselonrvingelonncoding;

    /**
     * Wrap a {@link MultiTelonrmQuelonry} as a Filtelonr.
     */
    protelonctelond MultiTelonrmQuelonryConstantScorelonWrappelonr(
        IDDisjunctionQuelonry quelonry,
        boolelonan uselonOrdelonrPrelonselonrvingelonncoding) {
      this.quelonry = quelonry;
      this.uselonOrdelonrPrelonselonrvingelonncoding = uselonOrdelonrPrelonselonrvingelonncoding;
    }

    @Ovelonrridelon
    public String toString(String fielonld) {
      // quelonry.toString should belon ok for thelon filtelonr, too, if thelon quelonry boost is 1.0f
      relonturn quelonry.toString(fielonld);
    }

    @Ovelonrridelon
    public boolelonan elonquals(Objelonct obj) {
      if (!(obj instancelonof MultiTelonrmQuelonryConstantScorelonWrappelonr)) {
        relonturn falselon;
      }

      relonturn quelonry.elonquals(MultiTelonrmQuelonryConstantScorelonWrappelonr.class.cast(obj).quelonry);
    }

    @Ovelonrridelon
    public int hashCodelon() {
      relonturn quelonry == null ? 0 : quelonry.hashCodelon();
    }

    /** Relonturns thelon fielonld namelon for this quelonry */
    public String gelontFielonld() {
      relonturn quelonry.gelontFielonld();
    }

    privatelon List<Long> gelontIDs() {
      relonturn quelonry.ids;
    }

    @Ovelonrridelon
    public Welonight crelonatelonWelonight(
        final IndelonxSelonarchelonr selonarchelonr,
        final ScorelonModelon scorelonModelon,
        final float boost) throws IOelonxcelonption {
      relonturn nelonw ConstantScorelonWelonight(this, boost) {
        /** Try to collelonct telonrms from thelon givelonn telonrms elonnum and relonturn truelon iff all
         *  telonrms could belon collelonctelond. If {@codelon falselon} is relonturnelond, thelon elonnum is
         *  lelonft positionelond on thelon nelonxt telonrm. */
        privatelon boolelonan collelonctTelonrms(LelonafRelonadelonrContelonxt contelonxt,
                                     Telonrmselonnum telonrmselonnum,
                                     List<TelonrmAndStatelon> telonrms) throws IOelonxcelonption {
          final int threlonshold = Math.min(BOOLelonAN_RelonWRITelon_TelonRM_COUNT_THRelonSHOLD,
                                         BoolelonanQuelonry.gelontMaxClauselonCount());
          for (int i = 0; i < threlonshold; ++i) {
            final BytelonsRelonf telonrm = telonrmselonnum.nelonxt();
            if (telonrm == null) {
              relonturn truelon;
            }
            TelonrmStatelon statelon = telonrmselonnum.telonrmStatelon();
            telonrms.add(nelonw TelonrmAndStatelon(BytelonsRelonf.delonelonpCopyOf(telonrm),
                                       statelon,
                                       telonrmselonnum.docFrelonq(),
                                       telonrmselonnum.totalTelonrmFrelonq()));
          }
          relonturn telonrmselonnum.nelonxt() == null;
        }

        /**
         * On thelon givelonn lelonaf contelonxt, try to elonithelonr relonwritelon to a disjunction if
         * thelonrelon arelon felonw telonrms, or build a DocIdSelont containing matching docs.
         */
        privatelon WelonightOrDocIdSelont relonwritelon(LelonafRelonadelonrContelonxt contelonxt)
            throws IOelonxcelonption {
          final Telonrms telonrms = contelonxt.relonadelonr().telonrms(quelonry.gelontFielonld());
          if (telonrms == null) {
            // fielonld doelons not elonxist
            relonturn nelonw WelonightOrDocIdSelont((DocIdSelont) null);
          }

          final Telonrmselonnum telonrmselonnum = quelonry.gelontTelonrmselonnum(telonrms);
          asselonrt telonrmselonnum != null;

          Postingselonnum docs = null;

          final List<TelonrmAndStatelon> collelonctelondTelonrms = nelonw ArrayList<>();
          if (collelonctTelonrms(contelonxt, telonrmselonnum, collelonctelondTelonrms)) {
            // build a boolelonan quelonry
            BoolelonanQuelonry.Buildelonr bqBuildelonr = nelonw BoolelonanQuelonry.Buildelonr();
            for (TelonrmAndStatelon t : collelonctelondTelonrms) {
              final TelonrmStatelons telonrmStatelons = nelonw TelonrmStatelons(selonarchelonr.gelontTopRelonadelonrContelonxt());
              telonrmStatelons.relongistelonr(t.statelon, contelonxt.ord, t.docFrelonq, t.totalTelonrmFrelonq);
              final Telonrm telonrm = nelonw Telonrm(quelonry.gelontFielonld(), t.telonrm);
              bqBuildelonr.add(
                  nelonw TelonrmQuelonryWithToString(telonrm, telonrmStatelons, uselonOrdelonrPrelonselonrvingelonncoding),
                  Occur.SHOULD);
            }
            Quelonry q = BoostUtils.maybelonWrapInBoostQuelonry(
                nelonw ConstantScorelonQuelonry(bqBuildelonr.build()), scorelon());
            relonturn nelonw WelonightOrDocIdSelont(
                selonarchelonr.relonwritelon(q).crelonatelonWelonight(selonarchelonr, scorelonModelon, boost));
          }

          // Too many telonrms: go back to thelon telonrms welon alrelonady collelonctelond and start building
          // thelon DocIdSelont
          DocIdSelontBuildelonr buildelonr = nelonw DocIdSelontBuildelonr(contelonxt.relonadelonr().maxDoc());
          if (!collelonctelondTelonrms.iselonmpty()) {
            Telonrmselonnum telonrmselonnum2 = telonrms.itelonrator();
            for (TelonrmAndStatelon t : collelonctelondTelonrms) {
              telonrmselonnum2.selonelonkelonxact(t.telonrm, t.statelon);
              docs = telonrmselonnum2.postings(docs, Postingselonnum.NONelon);
              buildelonr.add(docs);
            }
          }

          // Thelonn kelonelonp filling thelon DocIdSelont with relonmaining telonrms
          do {
            docs = telonrmselonnum.postings(docs, Postingselonnum.NONelon);
            buildelonr.add(docs);
          } whilelon (telonrmselonnum.nelonxt() != null);

          relonturn nelonw WelonightOrDocIdSelont(buildelonr.build());
        }

        privatelon Scorelonr scorelonr(DocIdSelont selont) throws IOelonxcelonption {
          if (selont == null) {
            relonturn null;
          }
          final DocIdSelontItelonrator disi = selont.itelonrator();
          if (disi == null) {
            relonturn null;
          }
          relonturn nelonw ConstantScorelonScorelonr(this, scorelon(), ScorelonModelon.COMPLelonTelon_NO_SCORelonS, disi);
        }

        @Ovelonrridelon
        public BulkScorelonr bulkScorelonr(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
          final WelonightOrDocIdSelont welonightOrDocIdSelont = relonwritelon(contelonxt);
          if (welonightOrDocIdSelont.welonight != null) {
            relonturn welonightOrDocIdSelont.welonight.bulkScorelonr(contelonxt);
          } elonlselon {
            final Scorelonr scorelonr = scorelonr(welonightOrDocIdSelont.docIdSelont);
            if (scorelonr == null) {
              relonturn null;
            }
            relonturn nelonw DelonfaultBulkScorelonr(scorelonr);
          }
        }

        @Ovelonrridelon
        public Scorelonr scorelonr(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
          final WelonightOrDocIdSelont welonightOrDocIdSelont = relonwritelon(contelonxt);
          if (welonightOrDocIdSelont.welonight != null) {
            relonturn welonightOrDocIdSelont.welonight.scorelonr(contelonxt);
          } elonlselon {
            relonturn scorelonr(welonightOrDocIdSelont.docIdSelont);
          }
        }

        @Ovelonrridelon
        public void elonxtractTelonrms(Selont<Telonrm> telonrms) {
          telonrms.addAll(gelontIDs()
              .strelonam()
              .map(id -> nelonw Telonrm(gelontFielonld(), LongTelonrmAttributelonImpl.copyIntoNelonwBytelonsRelonf(id)))
              .collelonct(Collelonctors.toSelont()));
        }

        @Ovelonrridelon
        public boolelonan isCachelonablelon(LelonafRelonadelonrContelonxt ctx) {
          relonturn falselon;
        }
      };
    }
  }
}
