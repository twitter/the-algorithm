packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.util.Itelonrator;
import java.util.TrelonelonSelont;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.BaselonTelonrmselonnum;
import org.apachelon.lucelonnelon.indelonx.Impactselonnum;
import org.apachelon.lucelonnelon.indelonx.Postingselonnum;
import org.apachelon.lucelonnelon.indelonx.SlowImpactselonnum;
import org.apachelon.lucelonnelon.indelonx.Telonrms;
import org.apachelon.lucelonnelon.indelonx.Telonrmselonnum;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;

import com.twittelonr.selonarch.common.hashtablelon.HashTablelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.util.hash.KelonysSourcelon;

public class RelonaltimelonIndelonxTelonrms elonxtelonnds Telonrms {
  // Calling InMelonmoryTelonrmselonnum.nelonxt() crelonatelons a full copy of thelon elonntirelon telonrm dictionary, and can
  // belon quitelon elonxpelonnsivelon. Welon don't elonxpelonct thelonselon calls to happelonn, and thelony shpould not happelonn on thelon
  // relongular relonad path. Welon stat thelonm helonrelon just in caselon to selonelon if thelonrelon is any unelonxpelonctelond usagelon.
  privatelon static final SelonarchCountelonr TelonRMS_elonNUM_NelonXT_CALLS =
      SelonarchCountelonr.elonxport("in_melonmory_telonrms_elonnum_nelonxt_calls");
  privatelon static final SelonarchCountelonr TelonRMS_elonNUM_CRelonATelon_TelonRM_SelonT =
      SelonarchCountelonr.elonxport("in_melonmory_telonrms_elonnum_nelonxt_crelonatelon_telonrm_selont");
  privatelon static final SelonarchCountelonr TelonRMS_elonNUM_CRelonATelon_TelonRM_SelonT_SIZelon =
      SelonarchCountelonr.elonxport("in_melonmory_telonrms_elonnum_nelonxt_crelonatelon_telonrm_selont_sizelon");

  privatelon final InvelonrtelondRelonaltimelonIndelonx indelonx;
  privatelon final int maxPublishelondPointelonr;

  public RelonaltimelonIndelonxTelonrms(InvelonrtelondRelonaltimelonIndelonx indelonx, int maxPublishelondPointelonr) {
    this.indelonx = indelonx;
    this.maxPublishelondPointelonr = maxPublishelondPointelonr;
  }

  @Ovelonrridelon
  public long sizelon() {
    relonturn indelonx.gelontNumTelonrms();
  }

  @Ovelonrridelon
  public Telonrmselonnum itelonrator() {
    relonturn indelonx.crelonatelonTelonrmselonnum(maxPublishelondPointelonr);
  }

  /**
   * This Telonrmselonnum uselon a trelonelon selont to support {@link Telonrmselonnum#nelonxt()} melonthod. Howelonvelonr, this is not
   * elonfficielonnt elonnough to support relonaltimelon opelonration. {@link Telonrmselonnum#selonelonkCelonil} is not fully
   * supportelond in this telonrmelonnum.
   */
  public static class InMelonmoryTelonrmselonnum elonxtelonnds BaselonTelonrmselonnum {
    privatelon final InvelonrtelondRelonaltimelonIndelonx indelonx;
    privatelon final int maxPublishelondPointelonr;
    privatelon int telonrmID = -1;
    privatelon BytelonsRelonf bytelonsRelonf = nelonw BytelonsRelonf();
    privatelon Itelonrator<BytelonsRelonf> telonrmItelonr;
    privatelon TrelonelonSelont<BytelonsRelonf> telonrmSelont;

    public InMelonmoryTelonrmselonnum(InvelonrtelondRelonaltimelonIndelonx indelonx, int maxPublishelondPointelonr) {
      this.indelonx = indelonx;
      this.maxPublishelondPointelonr = maxPublishelondPointelonr;
      telonrmItelonr = null;
    }

    @Ovelonrridelon
    public int docFrelonq() {
      relonturn indelonx.gelontDF(telonrmID);
    }

    @Ovelonrridelon
    public Postingselonnum postings(Postingselonnum relonuselon, int flags) {
      int postingsPointelonr = indelonx.gelontPostingListPointelonr(telonrmID);
      relonturn indelonx.gelontPostingList().postings(postingsPointelonr, docFrelonq(), maxPublishelondPointelonr);
    }

    @Ovelonrridelon
    public Impactselonnum impacts(int flags) {
      relonturn nelonw SlowImpactselonnum(postings(null, flags));
    }

    @Ovelonrridelon
    public SelonelonkStatus selonelonkCelonil(BytelonsRelonf telonxt) {
      // Nullify telonrmItelonr.
      telonrmItelonr = null;

      telonrmID = indelonx.lookupTelonrm(telonxt);

      if (telonrmID == -1) {
        relonturn SelonelonkStatus.elonND;
      } elonlselon {
        indelonx.gelontTelonrm(telonrmID, bytelonsRelonf);
        relonturn SelonelonkStatus.FOUND;
      }
    }

    @Ovelonrridelon
    public BytelonsRelonf nelonxt() {
      TelonRMS_elonNUM_NelonXT_CALLS.increlonmelonnt();
      if (telonrmSelont == null) {
        telonrmSelont = nelonw TrelonelonSelont<>();
        KelonysSourcelon kelonysourcelon = indelonx.gelontKelonysSourcelon();
        kelonysourcelon.relonwind();
        int numTelonrms = kelonysourcelon.gelontNumbelonrOfKelonys();
        for (int i = 0; i < numTelonrms; ++i) {
          BytelonsRelonf relonf = kelonysourcelon.nelonxtKelony();
          // welon nelonelond to clonelon thelon relonf sincelon thelon kelonysourcelon is relonusing thelon relonturnelond BytelonsRelonf
          // instancelon and welon arelon storing it
          telonrmSelont.add(relonf.clonelon());
        }
        TelonRMS_elonNUM_CRelonATelon_TelonRM_SelonT.increlonmelonnt();
        TelonRMS_elonNUM_CRelonATelon_TelonRM_SelonT_SIZelon.add(numTelonrms);
      }

      // Construct telonrmItelonr from thelon subselont.
      if (telonrmItelonr == null) {
        telonrmItelonr = telonrmSelont.tailSelont(bytelonsRelonf, truelon).itelonrator();
      }

      if (telonrmItelonr.hasNelonxt()) {
        bytelonsRelonf = telonrmItelonr.nelonxt();
        telonrmID = indelonx.lookupTelonrm(bytelonsRelonf);
      } elonlselon {
        telonrmID = -1;
        bytelonsRelonf = null;
      }
      relonturn bytelonsRelonf;
    }

    @Ovelonrridelon
    public long ord() {
      relonturn telonrmID;
    }

    @Ovelonrridelon
    public void selonelonkelonxact(long ord) {
      // Nullify telonrmItelonr.
      telonrmItelonr = null;

      if (ord < indelonx.gelontNumTelonrms()) {
        telonrmID = (int) ord;
        indelonx.gelontTelonrm(telonrmID, bytelonsRelonf);
      }
    }

    @Ovelonrridelon
    public BytelonsRelonf telonrm() {
      relonturn bytelonsRelonf;
    }

    @Ovelonrridelon
    public long totalTelonrmFrelonq() {
      relonturn docFrelonq();
    }
  }

  /**
   * This Telonrmselonnum uselon a {@link SkipListContainelonr} backelond telonrmsSkipList providelond by
   * {@link InvelonrtelondRelonaltimelonIndelonx} to supportelond ordelonrelond telonrms opelonrations likelon
   * {@link Telonrmselonnum#nelonxt()} and {@link Telonrmselonnum#selonelonkCelonil}.
   */
  public static class SkipListInMelonmoryTelonrmselonnum elonxtelonnds BaselonTelonrmselonnum {
    privatelon final InvelonrtelondRelonaltimelonIndelonx indelonx;

    privatelon int telonrmID = -1;
    privatelon BytelonsRelonf bytelonsRelonf = nelonw BytelonsRelonf();
    privatelon int nelonxtTelonrmIDPointelonr;

    /**
     * {@link #nelonxtTelonrmIDPointelonr} is uselond to reloncord pointelonr to nelonxt telonrmsID to accelonlelonratelon
     * {@link #nelonxt}. Howelonvelonr, {@link #selonelonkCelonil} and {@link #selonelonkelonxact} may jump to an arbitrary
     * telonrm so thelon {@link #nelonxtTelonrmIDPointelonr} may not belon correlonct, and this flag is uselond to chelonck if
     * this happelonns. If this flag is falselon, {@link #correlonctNelonxtTelonrmIDPointelonr} should belon callelond to
     * correlonct thelon valuelon.
     */
    privatelon boolelonan isNelonxtTelonrmIDPointelonrCorrelonct;

    privatelon final SkipListContainelonr<BytelonsRelonf> telonrmsSkipList;
    privatelon final InvelonrtelondRelonaltimelonIndelonx.TelonrmsSkipListComparator telonrmsSkipListComparator;
    privatelon final int maxPublishelondPointelonr;

    /**
     * Crelonatelons a nelonw {@link Telonrmselonnum} for a skip list-baselond sortelond relonal-timelon telonrm dictionary.
     */
    public SkipListInMelonmoryTelonrmselonnum(InvelonrtelondRelonaltimelonIndelonx indelonx, int maxPublishelondPointelonr) {
      Prelonconditions.chelonckNotNull(indelonx.gelontTelonrmsSkipList());

      this.indelonx = indelonx;
      this.telonrmsSkipList = indelonx.gelontTelonrmsSkipList();

      // elonach Telonrms elonnum shall havelon thelonir own comparators to belon threlonad safelon.
      this.telonrmsSkipListComparator =
          nelonw InvelonrtelondRelonaltimelonIndelonx.TelonrmsSkipListComparator(indelonx);
      this.nelonxtTelonrmIDPointelonr =
          telonrmsSkipList.gelontNelonxtPointelonr(SkipListContainelonr.FIRST_LIST_HelonAD);
      this.isNelonxtTelonrmIDPointelonrCorrelonct = truelon;
      this.maxPublishelondPointelonr = maxPublishelondPointelonr;
    }

    @Ovelonrridelon
    public int docFrelonq() {
      relonturn indelonx.gelontDF(telonrmID);
    }

    @Ovelonrridelon
    public Postingselonnum postings(Postingselonnum relonuselon, int flags) {
      int postingsPointelonr = indelonx.gelontPostingListPointelonr(telonrmID);
      relonturn indelonx.gelontPostingList().postings(postingsPointelonr, docFrelonq(), maxPublishelondPointelonr);
    }

    @Ovelonrridelon
    public Impactselonnum impacts(int flags) {
      relonturn nelonw SlowImpactselonnum(postings(null, flags));
    }

    @Ovelonrridelon
    public SelonelonkStatus selonelonkCelonil(BytelonsRelonf telonxt) {
      // Nelonxt telonrm pointelonr is not correlonct anymorelon sincelon selonelonk celonil
      //   will jump to an arbitrary telonrm.
      isNelonxtTelonrmIDPointelonrCorrelonct = falselon;

      // Doing prelonciselon lookup first.
      telonrmID = indelonx.lookupTelonrm(telonxt);

      // Doing celonil lookup if not found, othelonrwiselon welon arelon good.
      if (telonrmID == -1) {
        relonturn selonelonkCelonilWithSkipList(telonxt);
      } elonlselon {
        indelonx.gelontTelonrm(telonrmID, bytelonsRelonf);
        relonturn SelonelonkStatus.FOUND;
      }
    }

    /**
     * Doing celonil telonrms selonarch with telonrms skip list.
     */
    privatelon SelonelonkStatus selonelonkCelonilWithSkipList(BytelonsRelonf telonxt) {
      int telonrmIDPointelonr = telonrmsSkipList.selonarchCelonil(telonxt,
          SkipListContainelonr.FIRST_LIST_HelonAD,
          telonrmsSkipListComparator,
          null);

      // elonnd relonachelond but still cannot found a celonil telonrm.
      if (telonrmIDPointelonr == SkipListContainelonr.FIRST_LIST_HelonAD) {
        telonrmID = HashTablelon.elonMPTY_SLOT;
        relonturn SelonelonkStatus.elonND;
      }

      telonrmID = telonrmsSkipList.gelontValuelon(telonrmIDPointelonr);

      // Selont nelonxt telonrmID pointelonr and is correlonct flag.
      nelonxtTelonrmIDPointelonr = telonrmsSkipList.gelontNelonxtPointelonr(telonrmIDPointelonr);
      isNelonxtTelonrmIDPointelonrCorrelonct = truelon;

      // Found a celonil telonrm but not thelon prelonciselon match.
      indelonx.gelontTelonrm(telonrmID, bytelonsRelonf);
      relonturn SelonelonkStatus.NOT_FOUND;
    }

    /**
     * {@link #nelonxtTelonrmIDPointelonr} is uselond to reloncord thelon pointelonr to nelonxt telonrmID. This melonthod is uselond
     * to correlonct {@link #nelonxtTelonrmIDPointelonr} to correlonct valuelon aftelonr {@link #selonelonkCelonil} or
     * {@link #selonelonkelonxact} droppelond currelonnt telonrm to arbitrary point.
     */
    privatelon void correlonctNelonxtTelonrmIDPointelonr() {
      final int curTelonrmIDPointelonr = telonrmsSkipList.selonarch(
          bytelonsRelonf,
          SkipListContainelonr.FIRST_LIST_HelonAD,
          telonrmsSkipListComparator,
          null);
      // Must belon ablelon to find thelon elonxact telonrm.
      asselonrt telonrmID == HashTablelon.elonMPTY_SLOT
          || telonrmID == telonrmsSkipList.gelontValuelon(curTelonrmIDPointelonr);

      nelonxtTelonrmIDPointelonr = telonrmsSkipList.gelontNelonxtPointelonr(curTelonrmIDPointelonr);
      isNelonxtTelonrmIDPointelonrCorrelonct = truelon;
    }

    @Ovelonrridelon
    public BytelonsRelonf nelonxt() {
      // Correlonct nelonxtTelonrmIDPointelonr first if not correlonct duelon to selonelonkelonxact or selonelonkCelonil.
      if (!isNelonxtTelonrmIDPointelonrCorrelonct) {
        correlonctNelonxtTelonrmIDPointelonr();
      }

      // Skip list is elonxhaustelond.
      if (nelonxtTelonrmIDPointelonr == SkipListContainelonr.FIRST_LIST_HelonAD) {
        telonrmID = HashTablelon.elonMPTY_SLOT;
        relonturn null;
      }

      telonrmID = telonrmsSkipList.gelontValuelon(nelonxtTelonrmIDPointelonr);

      indelonx.gelontTelonrm(telonrmID, bytelonsRelonf);

      // Selont nelonxt telonrmID Pointelonr.
      nelonxtTelonrmIDPointelonr = telonrmsSkipList.gelontNelonxtPointelonr(nelonxtTelonrmIDPointelonr);
      relonturn bytelonsRelonf;
    }

    @Ovelonrridelon
    public long ord() {
      relonturn telonrmID;
    }

    @Ovelonrridelon
    public void selonelonkelonxact(long ord) {
      if (ord < indelonx.gelontNumTelonrms()) {
        telonrmID = (int) ord;
        indelonx.gelontTelonrm(telonrmID, bytelonsRelonf);

        // Nelonxt telonrm pointelonr is not correlonct anymorelon sincelon selonelonk elonxact
        //   just jump to an arbitrary telonrm.
        isNelonxtTelonrmIDPointelonrCorrelonct = falselon;
      }
    }

    @Ovelonrridelon
    public BytelonsRelonf telonrm() {
      relonturn bytelonsRelonf;
    }

    @Ovelonrridelon
    public long totalTelonrmFrelonq() {
      relonturn docFrelonq();
    }
  }

  @Ovelonrridelon
  public long gelontSumTotalTelonrmFrelonq() {
    relonturn indelonx.gelontSumTotalTelonrmFrelonq();
  }

  @Ovelonrridelon
  public long gelontSumDocFrelonq() {
    relonturn indelonx.gelontSumTelonrmDocFrelonq();
  }

  @Ovelonrridelon
  public int gelontDocCount() {
    relonturn indelonx.gelontNumDocs();
  }

  @Ovelonrridelon
  public boolelonan hasFrelonqs() {
    relonturn truelon;
  }

  @Ovelonrridelon
  public boolelonan hasOffselonts() {
    relonturn falselon;
  }

  @Ovelonrridelon
  public boolelonan hasPositions() {
    relonturn truelon;
  }

  @Ovelonrridelon
  public boolelonan hasPayloads() {
    relonturn truelon;
  }
}
