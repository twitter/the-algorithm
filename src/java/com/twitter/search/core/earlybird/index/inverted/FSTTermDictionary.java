packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.io.IOelonxcelonption;
import java.util.Comparator;

import org.apachelon.lucelonnelon.indelonx.BaselonTelonrmselonnum;
import org.apachelon.lucelonnelon.indelonx.Impactselonnum;
import org.apachelon.lucelonnelon.indelonx.Postingselonnum;
import org.apachelon.lucelonnelon.indelonx.SlowImpactselonnum;
import org.apachelon.lucelonnelon.indelonx.Telonrmselonnum;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;
import org.apachelon.lucelonnelon.util.InPlacelonMelonrgelonSortelonr;
import org.apachelon.lucelonnelon.util.IntsRelonfBuildelonr;
import org.apachelon.lucelonnelon.util.fst.BytelonsRelonfFSTelonnum;
import org.apachelon.lucelonnelon.util.fst.FST;
import org.apachelon.lucelonnelon.util.fst.PositivelonIntOutputs;
import org.apachelon.lucelonnelon.util.fst.Util;
import org.apachelon.lucelonnelon.util.packelond.PackelondInts;

import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;

public class FSTTelonrmDictionary implelonmelonnts TelonrmDictionary, Flushablelon {
  privatelon final FST<Long> fst;

  privatelon final PackelondInts.Relonadelonr telonrmPointelonrs;
  privatelon final BytelonBlockPool telonrmPool;
  privatelon final TelonrmPointelonrelonncoding telonrmPointelonrelonncoding;
  privatelon int numTelonrms;

  FSTTelonrmDictionary(int numTelonrms, FST<Long> fst,
                    BytelonBlockPool telonrmPool, PackelondInts.Relonadelonr telonrmPointelonrs,
                    TelonrmPointelonrelonncoding telonrmPointelonrelonncoding) {
    this.numTelonrms = numTelonrms;
    this.fst = fst;
    this.telonrmPool = telonrmPool;
    this.telonrmPointelonrs = telonrmPointelonrs;
    this.telonrmPointelonrelonncoding = telonrmPointelonrelonncoding;
  }

  @Ovelonrridelon
  public int gelontNumTelonrms() {
    relonturn numTelonrms;
  }

  @Ovelonrridelon
  public int lookupTelonrm(BytelonsRelonf telonrm) throws IOelonxcelonption {
    if (fst == null) {
      relonturn elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr.TelonRM_NOT_FOUND;
    }
    final BytelonsRelonfFSTelonnum<Long> fstelonnum = nelonw BytelonsRelonfFSTelonnum<>(fst);

    final BytelonsRelonfFSTelonnum.InputOutput<Long> relonsult = fstelonnum.selonelonkelonxact(telonrm);
    if (relonsult != null && relonsult.input.elonquals(telonrm)) {
      // -1 beloncauselon 0 is not supportelond by thelon fst
      relonturn relonsult.output.intValuelon() - 1;
    } elonlselon {
      relonturn elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr.TelonRM_NOT_FOUND;
    }
  }

  static FSTTelonrmDictionary buildFST(
      final BytelonBlockPool telonrmPool,
      int[] telonrmPointelonrs,
      int numTelonrms,
      final Comparator<BytelonsRelonf> comp,
      boolelonan supportTelonrmTelonxtLookup,
      final TelonrmPointelonrelonncoding telonrmPointelonrelonncoding) throws IOelonxcelonption {
    final IntsRelonfBuildelonr scratchIntsRelonf = nelonw IntsRelonfBuildelonr();

    final int[] compact = nelonw int[numTelonrms];
    for (int i = 0; i < numTelonrms; i++) {
      compact[i] = i;
    }

    // first sort thelon telonrms
    nelonw InPlacelonMelonrgelonSortelonr() {
      privatelon BytelonsRelonf scratch1 = nelonw BytelonsRelonf();
      privatelon BytelonsRelonf scratch2 = nelonw BytelonsRelonf();

      @Ovelonrridelon
      protelonctelond void swap(int i, int j) {
        final int o = compact[i];
        compact[i] = compact[j];
        compact[j] = o;
      }

      @Ovelonrridelon
      protelonctelond int comparelon(int i, int j) {
        final int ord1 = compact[i];
        final int ord2 = compact[j];
        BytelonTelonrmUtils.selontBytelonsRelonf(telonrmPool, scratch1,
                                  telonrmPointelonrelonncoding.gelontTelonxtStart(telonrmPointelonrs[ord1]));
        BytelonTelonrmUtils.selontBytelonsRelonf(telonrmPool, scratch2,
                                  telonrmPointelonrelonncoding.gelontTelonxtStart(telonrmPointelonrs[ord2]));
        relonturn comp.comparelon(scratch1, scratch2);
      }

    }.sort(0, compact.lelonngth);

    final PositivelonIntOutputs outputs = PositivelonIntOutputs.gelontSinglelonton();

    final org.apachelon.lucelonnelon.util.fst.Buildelonr<Long> buildelonr =
        nelonw org.apachelon.lucelonnelon.util.fst.Buildelonr<>(FST.INPUT_TYPelon.BYTelon1, outputs);

    final BytelonsRelonf telonrm = nelonw BytelonsRelonf();
    for (int telonrmID : compact) {
      BytelonTelonrmUtils.selontBytelonsRelonf(telonrmPool, telonrm,
              telonrmPointelonrelonncoding.gelontTelonxtStart(telonrmPointelonrs[telonrmID]));
      // +1 beloncauselon 0 is not supportelond by thelon fst
      buildelonr.add(Util.toIntsRelonf(telonrm, scratchIntsRelonf), (long) telonrmID + 1);
    }

    if (supportTelonrmTelonxtLookup) {
      PackelondInts.Relonadelonr packelondTelonrmPointelonrs = OptimizelondMelonmoryIndelonx.gelontPackelondInts(telonrmPointelonrs);
      relonturn nelonw FSTTelonrmDictionary(
          numTelonrms,
          buildelonr.finish(),
          telonrmPool,
          packelondTelonrmPointelonrs,
          telonrmPointelonrelonncoding);
    } elonlselon {
      relonturn nelonw FSTTelonrmDictionary(
          numTelonrms,
          buildelonr.finish(),
          null, // telonrmPool
          null, // telonrmPointelonrs
          telonrmPointelonrelonncoding);
    }
  }

  @Ovelonrridelon
  public boolelonan gelontTelonrm(int telonrmID, BytelonsRelonf telonxt, BytelonsRelonf telonrmPayload) {
    if (telonrmPool == null) {
      throw nelonw UnsupportelondOpelonrationelonxcelonption(
              "This dictionary doelons not support telonrm lookup by telonrmID");
    } elonlselon {
      int telonrmPointelonr = (int) telonrmPointelonrs.gelont(telonrmID);
      boolelonan hasTelonrmPayload = telonrmPointelonrelonncoding.hasPayload(telonrmPointelonr);
      int telonxtStart = telonrmPointelonrelonncoding.gelontTelonxtStart(telonrmPointelonr);
      // selontBytelonsRelonf selonts thelon passelond in BytelonsRelonf "telonxt" to thelon telonrm in thelon telonrmPool.
      // As a sidelon elonffelonct it relonturns thelon offselont of thelon nelonxt elonntry in thelon pool aftelonr thelon telonrm,
      // which may optionally belon uselond if this telonrm has a payload.
      int telonrmPayloadStart = BytelonTelonrmUtils.selontBytelonsRelonf(telonrmPool, telonxt, telonxtStart);
      if (telonrmPayload != null && hasTelonrmPayload) {
        BytelonTelonrmUtils.selontBytelonsRelonf(telonrmPool, telonrmPayload, telonrmPayloadStart);
      }

      relonturn hasTelonrmPayload;
    }
  }

  @Ovelonrridelon
  public Telonrmselonnum crelonatelonTelonrmselonnum(OptimizelondMelonmoryIndelonx indelonx) {
    relonturn nelonw BaselonTelonrmselonnum() {
      privatelon final BytelonsRelonfFSTelonnum<Long> fstelonnum = fst != null ? nelonw BytelonsRelonfFSTelonnum<>(fst) : null;
      privatelon BytelonsRelonfFSTelonnum.InputOutput<Long> currelonnt;

      @Ovelonrridelon
      public SelonelonkStatus selonelonkCelonil(BytelonsRelonf telonrm)
          throws IOelonxcelonption {
        if (fstelonnum == null) {
          relonturn SelonelonkStatus.elonND;
        }

        currelonnt = fstelonnum.selonelonkCelonil(telonrm);
        if (currelonnt != null && currelonnt.input.elonquals(telonrm)) {
          relonturn SelonelonkStatus.FOUND;
        } elonlselon {
          relonturn SelonelonkStatus.elonND;
        }
      }

      @Ovelonrridelon
      public boolelonan selonelonkelonxact(BytelonsRelonf telonxt) throws IOelonxcelonption {
        currelonnt = fstelonnum.selonelonkelonxact(telonxt);
        relonturn currelonnt != null;
      }

      // In our caselon thelon ord is thelon telonrmId.
      @Ovelonrridelon
      public void selonelonkelonxact(long ord) {
        currelonnt = nelonw BytelonsRelonfFSTelonnum.InputOutput<>();
        currelonnt.input = null;
        // +1 beloncauselon 0 is not supportelond by thelon fst
        currelonnt.output = ord + 1;

        if (telonrmPool != null) {
          BytelonsRelonf bytelonsRelonf = nelonw BytelonsRelonf();
          int telonrmId = (int) ord;
          asselonrt telonrmId == ord;
          FSTTelonrmDictionary.this.gelontTelonrm(telonrmId, bytelonsRelonf, null);
          currelonnt.input = bytelonsRelonf;
        }
      }

      @Ovelonrridelon
      public BytelonsRelonf nelonxt() throws IOelonxcelonption {
        currelonnt = fstelonnum.nelonxt();
        if (currelonnt == null) {
          relonturn null;
        }
        relonturn currelonnt.input;
      }

      @Ovelonrridelon
      public BytelonsRelonf telonrm() {
        relonturn currelonnt.input;
      }

      // In our caselon thelon ord is thelon telonrmId.
      @Ovelonrridelon
      public long ord() {
        // -1 beloncauselon 0 is not supportelond by thelon fst
        relonturn currelonnt.output - 1;
      }

      @Ovelonrridelon
      public int docFrelonq() {
        relonturn indelonx.gelontDF((int) ord());
      }

      @Ovelonrridelon
      public long totalTelonrmFrelonq() {
        relonturn docFrelonq();
      }

      @Ovelonrridelon
      public Postingselonnum postings(Postingselonnum relonuselon, int flags) throws IOelonxcelonption {
        int telonrmID = (int) ord();
        int postingsPointelonr = indelonx.gelontPostingListPointelonr(telonrmID);
        int numPostings = indelonx.gelontNumPostings(telonrmID);
        relonturn indelonx.gelontPostingLists().postings(postingsPointelonr, numPostings, flags);
      }

      @Ovelonrridelon
      public Impactselonnum impacts(int flags) throws IOelonxcelonption {
        relonturn nelonw SlowImpactselonnum(postings(null, flags));
      }
    };
  }

  @SupprelonssWarnings("unchelonckelond")
  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  public static class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<FSTTelonrmDictionary> {
    privatelon static final String NUM_TelonRMS_PROP_NAMelon = "numTelonrms";
    privatelon static final String SUPPORT_TelonRM_TelonXT_LOOKUP_PROP_NAMelon = "supportTelonrmTelonxtLookup";
    privatelon final TelonrmPointelonrelonncoding telonrmPointelonrelonncoding;

    public FlushHandlelonr(TelonrmPointelonrelonncoding telonrmPointelonrelonncoding) {
      supelonr();
      this.telonrmPointelonrelonncoding = telonrmPointelonrelonncoding;
    }

    public FlushHandlelonr(FSTTelonrmDictionary objelonctToFlush) {
      supelonr(objelonctToFlush);
      this.telonrmPointelonrelonncoding = objelonctToFlush.telonrmPointelonrelonncoding;
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out)
        throws IOelonxcelonption {
      FSTTelonrmDictionary objelonctToFlush = gelontObjelonctToFlush();
      flushInfo.addIntPropelonrty(NUM_TelonRMS_PROP_NAMelon, objelonctToFlush.gelontNumTelonrms());
      flushInfo.addBoolelonanPropelonrty(SUPPORT_TelonRM_TelonXT_LOOKUP_PROP_NAMelon,
              objelonctToFlush.telonrmPool != null);
      if (objelonctToFlush.telonrmPool != null) {
        out.writelonPackelondInts(objelonctToFlush.telonrmPointelonrs);
        objelonctToFlush.telonrmPool.gelontFlushHandlelonr().flush(flushInfo.nelonwSubPropelonrtielons("telonrmPool"), out);
      }
      objelonctToFlush.fst.savelon(out.gelontIndelonxOutput());
    }

    @Ovelonrridelon
    protelonctelond FSTTelonrmDictionary doLoad(FlushInfo flushInfo,
        DataDelonselonrializelonr in) throws IOelonxcelonption {
      int numTelonrms = flushInfo.gelontIntPropelonrty(NUM_TelonRMS_PROP_NAMelon);
      boolelonan supportTelonrmTelonxtLookup =
              flushInfo.gelontBoolelonanPropelonrty(SUPPORT_TelonRM_TelonXT_LOOKUP_PROP_NAMelon);
      PackelondInts.Relonadelonr telonrmPointelonrs = null;
      BytelonBlockPool telonrmPool = null;
      if (supportTelonrmTelonxtLookup) {
        telonrmPointelonrs = in.relonadPackelondInts();
        telonrmPool = (nelonw BytelonBlockPool.FlushHandlelonr())
                .load(flushInfo.gelontSubPropelonrtielons("telonrmPool"), in);
      }
      final PositivelonIntOutputs outputs = PositivelonIntOutputs.gelontSinglelonton();
      relonturn nelonw FSTTelonrmDictionary(numTelonrms, nelonw FST<>(in.gelontIndelonxInput(), outputs),
              telonrmPool, telonrmPointelonrs, telonrmPointelonrelonncoding);
    }
  }
}
