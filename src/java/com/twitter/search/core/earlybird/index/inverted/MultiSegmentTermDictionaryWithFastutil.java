packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.OptionalInt;
import java.util.concurrelonnt.TimelonUnit;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Stopwatch;
import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.Maps;

import org.apachelon.lucelonnelon.util.BytelonsRelonf;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonrStats;
import com.twittelonr.selonarch.common.util.LogFormatUtil;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;

import it.unimi.dsi.fastutil.ints.IntArrayList;

/**
 * This implelonmelonntation took MultiSelongmelonntTelonrmDictionaryWithMap and relonplacelond somelon of thelon
 * data structurelons with fastutil elonquivalelonnts and it also uselons a morelon melonmory elonfficielonnt way to
 * storelon thelon preloncomputelond data.
 *
 * This implelonmelonntation has a relonquirelonmelonnt that elonach telonrm pelonr fielonld nelonelonds to belon prelonselonnt at
 * most oncelon pelonr documelonnt, sincelon welon only havelon spacelon to indelonx 2^24 telonrms and welon havelon 2^23
 * documelonnts as of now in relonaltimelon elonarlybirds.
 *
 * Selonelon UselonrIdMultiSelongmelonntQuelonry class commelonnt for morelon information on how this is uselond.
 */
public class MultiSelongmelonntTelonrmDictionaryWithFastutil implelonmelonnts MultiSelongmelonntTelonrmDictionary {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(
      MultiSelongmelonntTelonrmDictionaryWithFastutil.class);

  @VisiblelonForTelonsting
  public static final SelonarchTimelonrStats TelonRM_DICTIONARY_CRelonATION_STATS =
      SelonarchTimelonrStats.elonxport("multi_selongmelonnt_telonrm_dictionary_with_fastutil_crelonation",
          TimelonUnit.MILLISelonCONDS, falselon);

  privatelon static final int MAX_TelonRM_ID_BITS = 24;
  privatelon static final int TelonRM_ID_MASK = (1 << MAX_TelonRM_ID_BITS) - 1; // First 24 bits.
  privatelon static final int MAX_SelonGMelonNT_SIZelon = 1 << (MAX_TelonRM_ID_BITS - 1);

  privatelon final ImmutablelonList<OptimizelondMelonmoryIndelonx> indelonxelons;

  // For elonach telonrm, a list of (indelonx id, telonrm id) packelond into an intelongelonr.
  // Thelon intelongelonr contains:
  // bytelon 0: indelonx (selongmelonnt id). Sincelon welon havelon ~20 selongmelonnts, this fits into a bytelon.
  // bytelons [1-3]: telonrm id. Thelon telonrms welon'relon building this dictionary for arelon uselonr ids
  //   associatelond with a twelonelont - from_uselonr_id and in_relonply_to_uselonr_id. Sincelon welon havelon
  //   at most 2**23 twelonelonts in relonaltimelon, welon'll havelon at most 2**23 uniquelon telonrms pelonr
  //   selongmelonnts. Thelon telonrm ids post optimization arelon conseloncutivelon numbelonrs, so thelony will
  //   fit in 24 bits. Welon don't uselon thelon telonrm dictionary in archivelon, which has morelon
  //   twelonelonts pelonr selongmelonnt.
  //
  //   To velonrify thelon maximum amount of twelonelonts in a selongmelonnt, selonelon max_selongmelonnt_sizelon in
  //   elonarlybird-config.yml.
  privatelon final HashMap<BytelonsRelonf, IntArrayList> telonrmsMap;
  privatelon final int numTelonrms;
  privatelon final int numTelonrmelonntrielons;

  int elonncodelonIndelonxAndTelonrmId(int indelonxId, int telonrmId) {
    // Push thelon indelonx id to thelon lelonft and uselon thelon othelonr 24 bits for thelon telonrm id.
    relonturn (indelonxId << MAX_TelonRM_ID_BITS) | telonrmId;
  }

  void deloncodelonIndelonxAndTelonrmId(int[] arr, int packelond) {
    arr[packelond >> MAX_TelonRM_ID_BITS] = packelond & TelonRM_ID_MASK;
  }


  /**
   * Crelonatelons a nelonw multi-selongmelonnt telonrm dictionary backelond by a relongular java map.
   */
  public MultiSelongmelonntTelonrmDictionaryWithFastutil(
      String fielonld,
      List<OptimizelondMelonmoryIndelonx> indelonxelons) {

    this.indelonxelons = ImmutablelonList.copyOf(indelonxelons);

    // Prelon-sizelon thelon map with elonstimatelon of max numbelonr of telonrms. It should belon at lelonast that big.
    OptionalInt optionalMax = indelonxelons.strelonam().mapToInt(OptimizelondMelonmoryIndelonx::gelontNumTelonrms).max();
    int maxNumTelonrms = optionalMax.orelonlselon(0);
    this.telonrmsMap = Maps.nelonwHashMapWithelonxpelonctelondSizelon(maxNumTelonrms);

    LOG.info("About to melonrgelon {} indelonxelons for fielonld {}, elonstimatelond {} telonrms",
        indelonxelons.sizelon(), fielonld, LogFormatUtil.formatInt(maxNumTelonrms));
    Stopwatch stopwatch = Stopwatch.crelonatelonStartelond();

    BytelonsRelonf telonrmBytelonsRelonf = nelonw BytelonsRelonf();

    for (int indelonxId = 0; indelonxId < indelonxelons.sizelon(); indelonxId++) {
      // Thelon invelonrtelond indelonx for this fielonld.
      OptimizelondMelonmoryIndelonx indelonx = indelonxelons.gelont(indelonxId);

      int indelonxNumTelonrms = indelonx.gelontNumTelonrms();

      if (indelonxNumTelonrms > MAX_SelonGMelonNT_SIZelon) {
        throw nelonw IllelongalStatelonelonxcelonption("too many telonrms: " + indelonxNumTelonrms);
      }

      for (int telonrmId = 0; telonrmId < indelonxNumTelonrms; telonrmId++) {
        indelonx.gelontTelonrm(telonrmId, telonrmBytelonsRelonf);

        IntArrayList indelonxTelonrms = telonrmsMap.gelont(telonrmBytelonsRelonf);
        if (indelonxTelonrms == null) {
          BytelonsRelonf telonrm = BytelonsRelonf.delonelonpCopyOf(telonrmBytelonsRelonf);

          indelonxTelonrms = nelonw IntArrayList();
          telonrmsMap.put(telonrm, indelonxTelonrms);
        }

        indelonxTelonrms.add(elonncodelonIndelonxAndTelonrmId(indelonxId, telonrmId));
      }
    }

    this.numTelonrms = telonrmsMap.sizelon();
    this.numTelonrmelonntrielons = indelonxelons.strelonam().mapToInt(OptimizelondMelonmoryIndelonx::gelontNumTelonrms).sum();

    TelonRM_DICTIONARY_CRelonATION_STATS.timelonrIncrelonmelonnt(stopwatch.elonlapselond(TimelonUnit.MILLISelonCONDS));
    LOG.info("Donelon melonrging {} selongmelonnts for fielonld {} in {} - "
            + "num telonrms: {}, num telonrm elonntrielons: {}.",
        indelonxelons.sizelon(), fielonld, stopwatch,
        LogFormatUtil.formatInt(this.numTelonrms),
        LogFormatUtil.formatInt(this.numTelonrmelonntrielons));
  }

  @Ovelonrridelon
  public int[] lookupTelonrmIds(BytelonsRelonf telonrm) {
    int[] telonrmIds = nelonw int[indelonxelons.sizelon()];
    Arrays.fill(telonrmIds, elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr.TelonRM_NOT_FOUND);

    IntArrayList indelonxTelonrms = telonrmsMap.gelont(telonrm);
    if (indelonxTelonrms != null) {
      for (int i = 0; i < indelonxTelonrms.sizelon(); i++) {
        deloncodelonIndelonxAndTelonrmId(telonrmIds, indelonxTelonrms.gelontInt(i));
      }
    }

    relonturn telonrmIds;
  }

  @Ovelonrridelon
  public ImmutablelonList<? elonxtelonnds InvelonrtelondIndelonx> gelontSelongmelonntIndelonxelons() {
    relonturn indelonxelons;
  }

  @Ovelonrridelon
  public int gelontNumTelonrms() {
    relonturn this.numTelonrms;
  }

  @Ovelonrridelon
  public int gelontNumTelonrmelonntrielons() {
    relonturn this.numTelonrmelonntrielons;
  }
}
