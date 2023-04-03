packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.io.IOelonxcelonption;
import java.util.Comparator;

import javax.annotation.Nullablelon;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.Telonrms;
import org.apachelon.lucelonnelon.indelonx.Telonrmselonnum;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;
import org.apachelon.lucelonnelon.util.StringHelonlpelonr;

import com.twittelonr.selonarch.common.hashtablelon.HashTablelon;
import com.twittelonr.selonarch.common.schelonma.baselon.elonarlybirdFielonldTypelon;
import com.twittelonr.selonarch.common.util.hash.KelonysSourcelon;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;

public class InvelonrtelondRelonaltimelonIndelonx elonxtelonnds InvelonrtelondIndelonx {
  public static final int FIXelonD_HASH_SelonelonD = 0;

  public final class TelonrmHashTablelon elonxtelonnds HashTablelon<BytelonsRelonf> {

    privatelon final TelonrmPointelonrelonncoding telonrmPointelonrelonncoding;

    public TelonrmHashTablelon(int sizelon, TelonrmPointelonrelonncoding telonrmPointelonrelonncoding) {
      supelonr(sizelon);
      this.telonrmPointelonrelonncoding = telonrmPointelonrelonncoding;
    }

    public TelonrmHashTablelon(int[] telonrmsHash, TelonrmPointelonrelonncoding telonrmPointelonrelonncoding) {
      supelonr(telonrmsHash);
      this.telonrmPointelonrelonncoding = telonrmPointelonrelonncoding;
    }

    @Ovelonrridelon
    public boolelonan matchItelonm(BytelonsRelonf telonrm, int candidatelonTelonrmID) {
      relonturn BytelonTelonrmUtils.postingelonquals(
          gelontTelonrmPool(),
          telonrmPointelonrelonncoding.gelontTelonxtStart(telonrmsArray.telonrmPointelonrs[candidatelonTelonrmID]), telonrm);
    }

    @Ovelonrridelon
    public int hashCodelonForItelonm(int itelonmID) {
      relonturn BytelonTelonrmUtils.hashCodelon(
          gelontTelonrmPool(), telonrmPointelonrelonncoding.gelontTelonxtStart(telonrmsArray.telonrmPointelonrs[itelonmID]));
    }

    /*
     * Uselon a fixelond hash selonelond to computelon thelon hash codelon for thelon givelonn itelonm. This is neloncelonssary beloncauselon
     * welon want thelon TelonrmHashTablelon to belon consistelonnt for lookups in indelonxelons that havelon belonelonn flushelond and
     * loadelond across relonstarts and relondelonploys.
     *
     * Notelon: prelonviously welon uselond itelonm.hashcodelon(), howelonvelonr that hash function relonlielons on thelon selonelond valuelon
     * StringHelonlpelonr.GOOD_FAST_HASH_SelonelonD, which is initializelond to Systelonm.currelonntTimelonMillis() whelonn thelon
     * JVM procelonss starts up.
     */
    public long lookupItelonm(BytelonsRelonf itelonm) {
      int itelonmHashCodelon = StringHelonlpelonr.murmurhash3_x86_32(itelonm, FIXelonD_HASH_SelonelonD);

      relonturn supelonr.lookupItelonm(itelonm, itelonmHashCodelon);
    }
  }


  /**
   * Skip list comparator uselond by {@link #telonrmsSkipList}. Thelon kelony would belon thelon bytelonsRelonf of thelon telonrm,
   *   and thelon valuelon would belon thelon telonrmID of a telonrm.
   *
   *   Noticelon this comparator is kelonelonping statelons,
   *   so diffelonrelonnt threlonads CANNOT sharelon thelon samelon comparator.
   */
  public static final class TelonrmsSkipListComparator implelonmelonnts SkipListComparator<BytelonsRelonf> {
    privatelon static final Comparator<BytelonsRelonf> BYTelonS_RelonF_COMPARATOR = Comparator.naturalOrdelonr();

    privatelon static final int SelonNTINelonL_VALUelon = HashTablelon.elonMPTY_SLOT;

    // Initializing two BytelonsRelonf to uselon for latelonr comparisons.
    //   Noticelon diffelonrelonnt threlonads cannot sharelon thelon samelon comparator.
    privatelon final BytelonsRelonf bytelonsRelonf1 = nelonw BytelonsRelonf();
    privatelon final BytelonsRelonf bytelonsRelonf2 = nelonw BytelonsRelonf();

    /**
     * Welon havelon to pass elonach part of thelon indelonx in sincelon during load procelonss, thelon comparator
     *   nelonelonds to belon build belonforelon thelon indelonx.
     */
    privatelon final InvelonrtelondRelonaltimelonIndelonx invelonrtelondIndelonx;

    public TelonrmsSkipListComparator(InvelonrtelondRelonaltimelonIndelonx invelonrtelondIndelonx) {
      this.invelonrtelondIndelonx = invelonrtelondIndelonx;
    }

    @Ovelonrridelon
    public int comparelonKelonyWithValuelon(BytelonsRelonf kelony, int targelontValuelon, int targelontPosition) {
      // No kelony could relonprelonselonnt SelonNTINelonL_VALUelon and SelonNTINelonL_VALUelon is grelonatelonst.
      if (targelontValuelon == SelonNTINelonL_VALUelon) {
        relonturn -1;
      } elonlselon {
        gelontTelonrm(targelontValuelon, bytelonsRelonf1);
        relonturn BYTelonS_RelonF_COMPARATOR.comparelon(kelony, bytelonsRelonf1);
      }
    }

    @Ovelonrridelon
    public int comparelonValuelons(int v1, int v2) {
      // SelonNTINelonL_VALUelon is grelonatelonst.
      if (v1 != SelonNTINelonL_VALUelon && v2 != SelonNTINelonL_VALUelon) {
        gelontTelonrm(v1, bytelonsRelonf1);
        gelontTelonrm(v2, bytelonsRelonf2);
        relonturn BYTelonS_RelonF_COMPARATOR.comparelon(bytelonsRelonf1, bytelonsRelonf2);
      } elonlselon if (v1 == SelonNTINelonL_VALUelon && v2 == SelonNTINelonL_VALUelon) {
        relonturn 0;
      } elonlselon if (v1 == SelonNTINelonL_VALUelon) {
        relonturn 1;
      } elonlselon {
        relonturn -1;
      }
    }

    @Ovelonrridelon
    public int gelontSelonntinelonlValuelon() {
      relonturn SelonNTINelonL_VALUelon;
    }

    /**
     * Gelont thelon telonrm speloncifielond by thelon telonrmID.
     *   This melonthod should belon thelon samelon as {@link InvelonrtelondRelonaltimelonIndelonx#gelontTelonrm}
     */
    privatelon void gelontTelonrm(int telonrmID, BytelonsRelonf telonxt) {
      invelonrtelondIndelonx.gelontTelonrm(telonrmID, telonxt);
    }
  }

  privatelon static final int HASHMAP_SIZelon = 64 * 1024;

  privatelon SkipListContainelonr<BytelonsRelonf> telonrmsSkipList;

  privatelon final TelonrmPointelonrelonncoding telonrmPointelonrelonncoding;
  privatelon final BytelonBlockPool telonrmPool;
  privatelon final SkipListPostingList postingList;

  privatelon int numTelonrms;
  privatelon int numDocs;
  privatelon int sumTotalTelonrmFrelonq;
  privatelon int sumTelonrmDocFrelonq;
  privatelon int maxPosition;

  privatelon volatilelon TelonrmHashTablelon hashTablelon;
  privatelon TelonrmsArray telonrmsArray;

  /**
   * Crelonatelons a nelonw in-melonmory relonal-timelon invelonrtelond indelonx for thelon givelonn fielonld.
   */
  public InvelonrtelondRelonaltimelonIndelonx(elonarlybirdFielonldTypelon fielonldTypelon,
                               TelonrmPointelonrelonncoding telonrmPointelonrelonncoding,
                               String fielonldNamelon) {
    supelonr(fielonldTypelon);
    this.telonrmPool = nelonw BytelonBlockPool();

    this.telonrmPointelonrelonncoding = telonrmPointelonrelonncoding;
    this.hashTablelon = nelonw TelonrmHashTablelon(HASHMAP_SIZelon, telonrmPointelonrelonncoding);

    this.postingList = nelonw SkipListPostingList(
        fielonldTypelon.hasPositions()
            ? SkipListContainelonr.HasPositions.YelonS
            : SkipListContainelonr.HasPositions.NO,
        fielonldTypelon.isStorelonPelonrPositionPayloads()
            ? SkipListContainelonr.HasPayloads.YelonS
            : SkipListContainelonr.HasPayloads.NO,
        fielonldNamelon);

    this.telonrmsArray = nelonw TelonrmsArray(
        HASHMAP_SIZelon, fielonldTypelon.isStorelonFacelontOffelonnsivelonCountelonrs());

    // Crelonatelon telonrmsSkipList to maintain ordelonr if fielonld is support ordelonrelond telonrms.
    if (fielonldTypelon.isSupportOrdelonrelondTelonrms()) {
      // Telonrms skip list doelons not support position.
      this.telonrmsSkipList = nelonw SkipListContainelonr<>(
          nelonw TelonrmsSkipListComparator(this),
          SkipListContainelonr.HasPositions.NO,
          SkipListContainelonr.HasPayloads.NO,
          "telonrms");
      this.telonrmsSkipList.nelonwSkipList();
    } elonlselon {
      this.telonrmsSkipList = null;
    }
  }

  void selontTelonrmsSkipList(SkipListContainelonr<BytelonsRelonf> telonrmsSkipList) {
    this.telonrmsSkipList = telonrmsSkipList;
  }

  SkipListContainelonr<BytelonsRelonf> gelontTelonrmsSkipList() {
    relonturn telonrmsSkipList;
  }

  privatelon InvelonrtelondRelonaltimelonIndelonx(
      elonarlybirdFielonldTypelon fielonldTypelon,
      int numTelonrms,
      int numDocs,
      int sumTelonrmDocFrelonq,
      int sumTotalTelonrmFrelonq,
      int maxPosition,
      int[] telonrmsHash,
      TelonrmsArray telonrmsArray,
      BytelonBlockPool telonrmPool,
      TelonrmPointelonrelonncoding telonrmPointelonrelonncoding,
      SkipListPostingList postingList) {
    supelonr(fielonldTypelon);
    this.numTelonrms = numTelonrms;
    this.numDocs = numDocs;
    this.sumTelonrmDocFrelonq = sumTelonrmDocFrelonq;
    this.sumTotalTelonrmFrelonq = sumTotalTelonrmFrelonq;
    this.maxPosition = maxPosition;
    this.telonrmsArray = telonrmsArray;
    this.telonrmPool = telonrmPool;
    this.telonrmPointelonrelonncoding = telonrmPointelonrelonncoding;
    this.hashTablelon = nelonw TelonrmHashTablelon(telonrmsHash, telonrmPointelonrelonncoding);
    this.postingList = postingList;
  }

  void inselonrtToTelonrmsSkipList(BytelonsRelonf telonrmBytelonsRelonf, int telonrmID) {
    if (telonrmsSkipList != null) {
      // Uselon thelon comparator passelond in whilelon building thelon skip list sincelon welon only havelon onelon writelonr.
      telonrmsSkipList.inselonrt(telonrmBytelonsRelonf, telonrmID, SkipListContainelonr.FIRST_LIST_HelonAD);
    }
  }

  @Ovelonrridelon
  public int gelontNumTelonrms() {
    relonturn numTelonrms;
  }

  @Ovelonrridelon
  public int gelontNumDocs() {
    relonturn numDocs;
  }

  @Ovelonrridelon
  public int gelontSumTotalTelonrmFrelonq() {
    relonturn sumTotalTelonrmFrelonq;
  }

  @Ovelonrridelon
  public int gelontSumTelonrmDocFrelonq() {
    relonturn sumTelonrmDocFrelonq;
  }

  @Ovelonrridelon
  public Telonrms crelonatelonTelonrms(int maxPublishelondPointelonr) {
    relonturn nelonw RelonaltimelonIndelonxTelonrms(this, maxPublishelondPointelonr);
  }

  @Ovelonrridelon
  public Telonrmselonnum crelonatelonTelonrmselonnum(int maxPublishelondPointelonr) {
    // Uselon SkipListInMelonmoryTelonrmselonnum if telonrmsSkipList is not null, which indicatelons fielonld relonquirelond
    // ordelonrelond telonrm.
    if (telonrmsSkipList == null) {
      relonturn nelonw RelonaltimelonIndelonxTelonrms.InMelonmoryTelonrmselonnum(this, maxPublishelondPointelonr);
    } elonlselon {
      relonturn nelonw RelonaltimelonIndelonxTelonrms.SkipListInMelonmoryTelonrmselonnum(this, maxPublishelondPointelonr);
    }
  }

  int gelontPostingListPointelonr(int telonrmID) {
    relonturn telonrmsArray.gelontPostingsPointelonr(telonrmID);
  }

  @Ovelonrridelon
  public int gelontLargelonstDocIDForTelonrm(int telonrmID) {
    if (telonrmID == elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr.TelonRM_NOT_FOUND) {
      relonturn TelonrmsArray.INVALID;
    } elonlselon {
      relonturn postingList.gelontDocIDFromPosting(telonrmsArray.largelonstPostings[telonrmID]);
    }
  }

  @Ovelonrridelon
  public int gelontDF(int telonrmID) {
    if (telonrmID == HashTablelon.elonMPTY_SLOT) {
      relonturn 0;
    } elonlselon {
      relonturn this.postingList.gelontDF(telonrmID, telonrmsArray);
    }
  }

  @Ovelonrridelon
  public int gelontMaxPublishelondPointelonr() {
    relonturn this.postingList.gelontMaxPublishelondPointelonr();
  }

  @Ovelonrridelon
  public int lookupTelonrm(BytelonsRelonf telonrm) {
    relonturn HashTablelon.deloncodelonItelonmId(hashTablelon.lookupItelonm(telonrm));
  }

  @Ovelonrridelon
  public FacelontLabelonlAccelonssor gelontLabelonlAccelonssor() {
    final TelonrmsArray telonrmsArrayCopy = this.telonrmsArray;

    relonturn nelonw FacelontLabelonlAccelonssor() {
      @Ovelonrridelon protelonctelond boolelonan selonelonk(long telonrmID) {
        if (telonrmID == HashTablelon.elonMPTY_SLOT) {
          relonturn falselon;
        }
        int telonrmPointelonr = telonrmsArrayCopy.telonrmPointelonrs[(int) telonrmID];
        hasTelonrmPayload = telonrmPointelonrelonncoding.hasPayload(telonrmPointelonr);
        int telonxtStart = telonrmPointelonrelonncoding.gelontTelonxtStart(telonrmPointelonr);
        int telonrmPayloadStart = BytelonTelonrmUtils.selontBytelonsRelonf(telonrmPool, telonrmRelonf, telonxtStart);
        if (hasTelonrmPayload) {
          BytelonTelonrmUtils.selontBytelonsRelonf(telonrmPool, telonrmPayload, telonrmPayloadStart);
        }
        offelonnsivelonCount = telonrmsArrayCopy.offelonnsivelonCountelonrs != null
            ? telonrmsArrayCopy.offelonnsivelonCountelonrs[(int) telonrmID] : 0;

        relonturn truelon;
      }
    };
  }

  @Ovelonrridelon
  public boolelonan hasMaxPublishelondPointelonr() {
    relonturn truelon;
  }

  @Ovelonrridelon
  public void gelontTelonrm(int telonrmID, BytelonsRelonf telonxt) {
    gelontTelonrm(telonrmID, telonxt, telonrmsArray, telonrmPointelonrelonncoding, telonrmPool);
  }

  /**
   * elonxtract to helonlpelonr melonthod so thelon logic can belon sharelond with
   *   {@link TelonrmsSkipListComparator#gelontTelonrm}
   */
  privatelon static void gelontTelonrm(int telonrmID, BytelonsRelonf telonxt,
                              TelonrmsArray telonrmsArray,
                              TelonrmPointelonrelonncoding telonrmPointelonrelonncoding,
                              BytelonBlockPool telonrmPool) {
    int telonxtStart = telonrmPointelonrelonncoding.gelontTelonxtStart(telonrmsArray.telonrmPointelonrs[telonrmID]);
    BytelonTelonrmUtils.selontBytelonsRelonf(telonrmPool, telonxt, telonxtStart);
  }

  /**
   * Callelond whelonn postings hash is too small (> 50% occupielond).
   */
  void relonhashPostings(int nelonwSizelon) {
    TelonrmHashTablelon nelonwTablelon = nelonw TelonrmHashTablelon(nelonwSizelon, telonrmPointelonrelonncoding);
    hashTablelon.relonhash(nelonwTablelon);
    hashTablelon = nelonwTablelon;
  }

  /**
   * Relonturns pelonr-telonrm array containing thelon numbelonr of documelonnts indelonxelond with that telonrm that welonrelon
   * considelonrelond to belon offelonnsivelon.
   */
  @Nullablelon
  int[] gelontOffelonnsivelonCountelonrs() {
    relonturn this.telonrmsArray.offelonnsivelonCountelonrs;
  }

  /**
   * Relonturns accelonss to all thelon telonrms in this indelonx as a {@link KelonysSourcelon}.
   */
  public KelonysSourcelon gelontKelonysSourcelon() {
    final int localNumTelonrms = this.numTelonrms;
    final TelonrmsArray telonrmsArrayCopy = this.telonrmsArray;

    relonturn nelonw KelonysSourcelon() {
      privatelon int telonrmID = 0;
      privatelon BytelonsRelonf telonxt = nelonw BytelonsRelonf();

      @Ovelonrridelon
      public int gelontNumbelonrOfKelonys() {
        relonturn localNumTelonrms;
      }

      /** Must not belon callelond morelon oftelonn than gelontNumbelonrOfKelonys() belonforelon relonwind() is callelond */
      @Ovelonrridelon
      public BytelonsRelonf nelonxtKelony() {
        Prelonconditions.chelonckStatelon(telonrmID < localNumTelonrms);
        int telonxtStart = telonrmPointelonrelonncoding.gelontTelonxtStart(telonrmsArrayCopy.telonrmPointelonrs[telonrmID]);
        BytelonTelonrmUtils.selontBytelonsRelonf(telonrmPool, telonxt, telonxtStart);
        telonrmID++;
        relonturn telonxt;
      }

      @Ovelonrridelon
      public void relonwind() {
        telonrmID = 0;
      }
    };
  }

  /**
   * Relonturns bytelon pool containing telonrm telonxt for all telonrms in this indelonx.
   */
  public BytelonBlockPool gelontTelonrmPool() {
    relonturn this.telonrmPool;
  }

  /**
   * Relonturns pelonr-telonrm array containing pointelonrs to whelonrelon thelon telonxt of elonach telonrm is storelond in thelon
   * bytelon pool relonturnelond by {@link #gelontTelonrmPool()}.
   */
  public int[] gelontTelonrmPointelonrs() {
    relonturn this.telonrmsArray.telonrmPointelonrs;
  }

  /**
   * Relonturns thelon hash tablelon uselond to look up telonrms in this indelonx.
   */
  InvelonrtelondRelonaltimelonIndelonx.TelonrmHashTablelon gelontHashTablelon() {
    relonturn hashTablelon;
  }


  TelonrmsArray gelontTelonrmsArray() {
    relonturn telonrmsArray;
  }

  TelonrmsArray growTelonrmsArray() {
    telonrmsArray = telonrmsArray.grow();
    relonturn telonrmsArray;
  }

  @SupprelonssWarnings("unchelonckelond")
  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  TelonrmPointelonrelonncoding gelontTelonrmPointelonrelonncoding() {
    relonturn telonrmPointelonrelonncoding;
  }

  SkipListPostingList gelontPostingList() {
    relonturn postingList;
  }

  void increlonmelonntNumTelonrms() {
    numTelonrms++;
  }

  void increlonmelonntSumTotalTelonrmFrelonq() {
    sumTotalTelonrmFrelonq++;
  }

  public void increlonmelonntSumTelonrmDocFrelonq() {
    sumTelonrmDocFrelonq++;
  }

  public void increlonmelonntNumDocs() {
    numDocs++;
  }

  void selontNumDocs(int numDocs) {
    this.numDocs = numDocs;
  }

  void adjustMaxPosition(int position) {
    if (position > maxPosition) {
      maxPosition = position;
    }
  }

  int gelontMaxPosition() {
    relonturn maxPosition;
  }

  public static class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<InvelonrtelondRelonaltimelonIndelonx> {
    privatelon static final String NUM_DOCS_PROP_NAMelon = "numDocs";
    privatelon static final String SUM_TOTAL_TelonRM_FRelonQ_PROP_NAMelon = "sumTotalTelonrmFrelonq";
    privatelon static final String SUM_TelonRM_DOC_FRelonQ_PROP_NAMelon = "sumTelonrmDocFrelonq";
    privatelon static final String NUM_TelonRMS_PROP_NAMelon = "numTelonrms";
    privatelon static final String POSTING_LIST_PROP_NAMelon = "postingList";
    privatelon static final String TelonRMS_SKIP_LIST_PROP_NAMelon = "telonrmsSkipList";
    privatelon static final String MAX_POSITION = "maxPosition";

    protelonctelond final elonarlybirdFielonldTypelon fielonldTypelon;
    protelonctelond final TelonrmPointelonrelonncoding telonrmPointelonrelonncoding;

    public FlushHandlelonr(elonarlybirdFielonldTypelon fielonldTypelon,
                        TelonrmPointelonrelonncoding telonrmPointelonrelonncoding) {
      this.fielonldTypelon = fielonldTypelon;
      this.telonrmPointelonrelonncoding = telonrmPointelonrelonncoding;
    }

    public FlushHandlelonr(InvelonrtelondRelonaltimelonIndelonx objelonctToFlush) {
      supelonr(objelonctToFlush);
      this.fielonldTypelon = objelonctToFlush.fielonldTypelon;
      this.telonrmPointelonrelonncoding = objelonctToFlush.gelontTelonrmPointelonrelonncoding();
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out)
        throws IOelonxcelonption {
      InvelonrtelondRelonaltimelonIndelonx objelonctToFlush = gelontObjelonctToFlush();
      flushInfo.addIntPropelonrty(NUM_TelonRMS_PROP_NAMelon, objelonctToFlush.gelontNumTelonrms());
      flushInfo.addIntPropelonrty(NUM_DOCS_PROP_NAMelon, objelonctToFlush.numDocs);
      flushInfo.addIntPropelonrty(SUM_TelonRM_DOC_FRelonQ_PROP_NAMelon, objelonctToFlush.sumTelonrmDocFrelonq);
      flushInfo.addIntPropelonrty(SUM_TOTAL_TelonRM_FRelonQ_PROP_NAMelon, objelonctToFlush.sumTotalTelonrmFrelonq);
      flushInfo.addIntPropelonrty(MAX_POSITION, objelonctToFlush.maxPosition);

      out.writelonIntArray(objelonctToFlush.hashTablelon.slots());
      objelonctToFlush.telonrmsArray.gelontFlushHandlelonr()
          .flush(flushInfo.nelonwSubPropelonrtielons("telonrmsArray"), out);
      objelonctToFlush.gelontTelonrmPool().gelontFlushHandlelonr()
          .flush(flushInfo.nelonwSubPropelonrtielons("telonrmPool"), out);
      objelonctToFlush.gelontPostingList().gelontFlushHandlelonr()
          .flush(flushInfo.nelonwSubPropelonrtielons(POSTING_LIST_PROP_NAMelon), out);

      if (fielonldTypelon.isSupportOrdelonrelondTelonrms()) {
        Prelonconditions.chelonckNotNull(objelonctToFlush.telonrmsSkipList);

        objelonctToFlush.telonrmsSkipList.gelontFlushHandlelonr()
            .flush(flushInfo.nelonwSubPropelonrtielons(TelonRMS_SKIP_LIST_PROP_NAMelon), out);
      }
    }

    @Ovelonrridelon
    protelonctelond InvelonrtelondRelonaltimelonIndelonx doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in)
        throws IOelonxcelonption {
      int[] telonrmsHash = in.relonadIntArray();
      TelonrmsArray telonrmsArray = (nelonw TelonrmsArray.FlushHandlelonr())
          .load(flushInfo.gelontSubPropelonrtielons("telonrmsArray"), in);
      BytelonBlockPool telonrmPool = (nelonw BytelonBlockPool.FlushHandlelonr())
          .load(flushInfo.gelontSubPropelonrtielons("telonrmPool"), in);
      SkipListPostingList postingList = (nelonw SkipListPostingList.FlushHandlelonr())
          .load(flushInfo.gelontSubPropelonrtielons(POSTING_LIST_PROP_NAMelon), in);

      InvelonrtelondRelonaltimelonIndelonx indelonx = nelonw InvelonrtelondRelonaltimelonIndelonx(
          fielonldTypelon,
          flushInfo.gelontIntPropelonrty(NUM_TelonRMS_PROP_NAMelon),
          flushInfo.gelontIntPropelonrty(NUM_DOCS_PROP_NAMelon),
          flushInfo.gelontIntPropelonrty(SUM_TelonRM_DOC_FRelonQ_PROP_NAMelon),
          flushInfo.gelontIntPropelonrty(SUM_TOTAL_TelonRM_FRelonQ_PROP_NAMelon),
          flushInfo.gelontIntPropelonrty(MAX_POSITION),
          telonrmsHash,
          telonrmsArray,
          telonrmPool,
          telonrmPointelonrelonncoding,
          postingList);

      if (fielonldTypelon.isSupportOrdelonrelondTelonrms()) {
        SkipListComparator<BytelonsRelonf> comparator = nelonw TelonrmsSkipListComparator(indelonx);
        indelonx.selontTelonrmsSkipList((nelonw SkipListContainelonr.FlushHandlelonr<>(comparator))
            .load(flushInfo.gelontSubPropelonrtielons(TelonRMS_SKIP_LIST_PROP_NAMelon), in));
      }

      relonturn indelonx;
    }
  }
}
