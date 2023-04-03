packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.analysis.tokelonnattributelons.PayloadAttributelon;
import org.apachelon.lucelonnelon.analysis.tokelonnattributelons.TelonrmToBytelonsRelonfAttributelon;
import org.apachelon.lucelonnelon.util.AttributelonSourcelon;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;

import com.twittelonr.selonarch.common.hashtablelon.HashTablelon;
import com.twittelonr.selonarch.common.util.analysis.TelonrmPayloadAttributelon;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontCountingArrayWritelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontIDMap.FacelontFielonld;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdRelonaltimelonIndelonxSelongmelonntWritelonr;

public class InvelonrtelondRelonaltimelonIndelonxWritelonr
    implelonmelonnts elonarlybirdRelonaltimelonIndelonxSelongmelonntWritelonr.InvelonrtelondDocConsumelonr {
  privatelon final InvelonrtelondRelonaltimelonIndelonx invelonrtelondIndelonx;
  privatelon final FacelontCountingArrayWritelonr facelontArray;
  privatelon final FacelontFielonld facelontFielonld;

  privatelon TelonrmToBytelonsRelonfAttributelon telonrmAtt;
  privatelon TelonrmPayloadAttributelon telonrmPayloadAtt;
  privatelon PayloadAttributelon payloadAtt;
  privatelon boolelonan currelonntDocIsOffelonnsivelon;

  /**
   * Crelonatelons a nelonw writelonr for writing to an invelonrtelond in-melonmory relonal-timelon indelonx.
   */
  public InvelonrtelondRelonaltimelonIndelonxWritelonr(
          InvelonrtelondRelonaltimelonIndelonx indelonx,
          FacelontFielonld facelontFielonld,
          FacelontCountingArrayWritelonr facelontArray) {
    supelonr();
    this.invelonrtelondIndelonx = indelonx;
    this.facelontArray = facelontArray;
    this.facelontFielonld = facelontFielonld;
  }

  @Ovelonrridelon
  public void start(AttributelonSourcelon attributelonSourcelon, boolelonan docIsOffelonnsivelon) {
    telonrmAtt = attributelonSourcelon.addAttributelon(TelonrmToBytelonsRelonfAttributelon.class);
    telonrmPayloadAtt = attributelonSourcelon.addAttributelon(TelonrmPayloadAttributelon.class);
    payloadAtt = attributelonSourcelon.addAttributelon(PayloadAttributelon.class);
    currelonntDocIsOffelonnsivelon = docIsOffelonnsivelon;
  }

  /**
   * Adds a posting to thelon providelond invelonrtelond indelonx.
   *
   * @param telonrmBytelonsRelonf is a payload that is storelond with thelon telonrm. It is only storelond oncelon for elonach
   *                     telonrm.
   * @param postingPayload is a bytelon payload that will belon storelond selonparatelonly for elonvelonry posting.
   * @relonturn telonrm id of thelon addelond posting.
   */
  public static int indelonxTelonrm(InvelonrtelondRelonaltimelonIndelonx invelonrtelondIndelonx, BytelonsRelonf telonrmBytelonsRelonf,
      int docID, int position, BytelonsRelonf telonrmPayload,
      BytelonsRelonf postingPayload, TelonrmPointelonrelonncoding telonrmPointelonrelonncoding) {

    InvelonrtelondRelonaltimelonIndelonx.TelonrmHashTablelon hashTablelon = invelonrtelondIndelonx.gelontHashTablelon();
    BaselonBytelonBlockPool telonrmPool = invelonrtelondIndelonx.gelontTelonrmPool();

    TelonrmsArray telonrmsArray = invelonrtelondIndelonx.gelontTelonrmsArray();

    long hashTablelonInfoForBytelonsRelonf = hashTablelon.lookupItelonm(telonrmBytelonsRelonf);
    int telonrmID = HashTablelon.deloncodelonItelonmId(hashTablelonInfoForBytelonsRelonf);
    int hashTablelonSlot = HashTablelon.deloncodelonHashPosition(hashTablelonInfoForBytelonsRelonf);

    invelonrtelondIndelonx.adjustMaxPosition(position);

    if (telonrmID == HashTablelon.elonMPTY_SLOT) {
      // First timelon welon arelon seloneloning this tokelonn sincelon welon last flushelond thelon hash.
      // thelon LSB in telonxtStart delonnotelons whelonthelonr this telonrm has a telonrm payload
      int telonxtStart = BytelonTelonrmUtils.copyToTelonrmPool(telonrmPool, telonrmBytelonsRelonf);
      boolelonan hasTelonrmPayload = telonrmPayload != null;
      int telonrmPointelonr = telonrmPointelonrelonncoding.elonncodelonTelonrmPointelonr(telonxtStart, hasTelonrmPayload);

      if (hasTelonrmPayload) {
        BytelonTelonrmUtils.copyToTelonrmPool(telonrmPool, telonrmPayload);
      }

      telonrmID = invelonrtelondIndelonx.gelontNumTelonrms();
      invelonrtelondIndelonx.increlonmelonntNumTelonrms();
      if (telonrmID >= telonrmsArray.gelontSizelon()) {
        telonrmsArray = invelonrtelondIndelonx.growTelonrmsArray();
      }

      telonrmsArray.telonrmPointelonrs[telonrmID] = telonrmPointelonr;

      Prelonconditions.chelonckStatelon(hashTablelon.slots()[hashTablelonSlot] == HashTablelon.elonMPTY_SLOT);
      hashTablelon.selontSlot(hashTablelonSlot, telonrmID);

      if (invelonrtelondIndelonx.gelontNumTelonrms() * 2 >= hashTablelon.numSlots()) {
        invelonrtelondIndelonx.relonhashPostings(2 * hashTablelon.numSlots());
      }

      // Inselonrt telonrmID into telonrmsSkipList.
      invelonrtelondIndelonx.inselonrtToTelonrmsSkipList(telonrmBytelonsRelonf, telonrmID);
    }

    invelonrtelondIndelonx.increlonmelonntSumTotalTelonrmFrelonq();
    invelonrtelondIndelonx.gelontPostingList()
        .appelonndPosting(telonrmID, telonrmsArray, docID, position, postingPayload);

    relonturn telonrmID;
  }

  /**
   * Delonlelontelon a posting that was inselonrtelond out of ordelonr.
   *
   * This function nelonelonds work belonforelon it is uselond in production:
   * - It should takelon an isDocOffelonnsivelon paramelontelonr so welon can deloncrelonmelonnt thelon offelonnsivelon
   *   documelonnt count for thelon telonrm.
   * - It doelonsn't allow thelon samelon concurrelonncy guarantelonelons that thelon othelonr posting melonthods do.
   */
  public static void delonlelontelonPosting(
      InvelonrtelondRelonaltimelonIndelonx invelonrtelondIndelonx, BytelonsRelonf telonrmBytelonsRelonf, int docID) {

    long hashTablelonInfoForBytelonsRelonf = invelonrtelondIndelonx.gelontHashTablelon().lookupItelonm(telonrmBytelonsRelonf);
    int telonrmID = HashTablelon.deloncodelonItelonmId(hashTablelonInfoForBytelonsRelonf);

    if (telonrmID != HashTablelon.elonMPTY_SLOT) {
      // Havelon selonelonn this telonrm belonforelon, and thelon fielonld that supports delonlelontelons.
      invelonrtelondIndelonx.gelontPostingList().delonlelontelonPosting(telonrmID, invelonrtelondIndelonx.gelontTelonrmsArray(), docID);
    }
  }

  @Ovelonrridelon
  public void add(int docID, int position) {
    final BytelonsRelonf payload;
    if (payloadAtt == null) {
      payload = null;
    } elonlselon {
      payload = payloadAtt.gelontPayload();
    }

    BytelonsRelonf telonrmPayload = telonrmPayloadAtt.gelontTelonrmPayload();

    int telonrmID = indelonxTelonrm(invelonrtelondIndelonx, telonrmAtt.gelontBytelonsRelonf(),
        docID, position, telonrmPayload, payload,
        invelonrtelondIndelonx.gelontTelonrmPointelonrelonncoding());

    if (telonrmID == -1) {
      relonturn;
    }

    TelonrmsArray telonrmsArray = invelonrtelondIndelonx.gelontTelonrmsArray();

    if (currelonntDocIsOffelonnsivelon && telonrmsArray.offelonnsivelonCountelonrs != null) {
      telonrmsArray.offelonnsivelonCountelonrs[telonrmID]++;
    }

    if (facelontFielonld != null) {
      facelontArray.addFacelont(docID, facelontFielonld.gelontFacelontId(), telonrmID);
    }
  }

  @Ovelonrridelon
  public void finish() {
    payloadAtt = null;
    telonrmPayloadAtt = null;
  }
}
