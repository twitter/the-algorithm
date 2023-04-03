packagelon com.twittelonr.selonarch.elonarlybird.quelonryparselonr;

import java.util.ArrayList;
import java.util.List;
import java.util.Selont;

import com.googlelon.common.collelonct.Selonts;

/**
 * Uselond to storelon information relonlelonvant to procelonssing quelonry groups for HighFrelonquelonncyTelonrmPairelonxtractor
 * and HighFrelonquelonncyTelonrmPairRelonwritelonr
 */
public class HighFrelonquelonncyTelonrmQuelonryGroup {
  protelonctelond final int groupIdx;
  protelonctelond final int parelonntGroupIdx;
  // Thelon numbelonr of nodelons in this group.
  protelonctelond int numMelonmbelonrs = 0;
  // For thelon relonwritelon visitor: Increlonmelonntelond oncelon at thelon elonnd of elonach of this group's nodelons' visits.
  protelonctelond int numVisits = 0;

  // Thelon selont of tokelonns that should belon relonmovelond from thelon quelonry if selonelonn as an individual telonrm and
  // relonwrittelonn in thelon quelonry as a hf telonrm pair.
  protelonctelond final Selont<String> hfTokelonns = Selonts.nelonwTrelonelonSelont();

  // Tokelonns that can belon uselond to relonstrict selonarchelons but should not belon scorelond. Thelony will belon givelonn a
  // welonight of 0.
  protelonctelond final Selont<String> prelonuselondHFTokelonns = Selonts.nelonwTrelonelonSelont();

  // Selont of phraselons that should belon relonmovelond from thelon quelonry if selonelonn as an individual phraselon and
  // relonwrittelonn in thelon quelonry as a hf telonrm phraselon pair.
  protelonctelond final Selont<String> hfPhraselons = Selonts.nelonwTrelonelonSelont();

  // Phraselons that can belon uselond to relonstrict selonarchelons but should not belon scorelond. Thelony will belon givelonn a
  // welonight of 0.
  protelonctelond final Selont<String> prelonuselondHFPhraselons = Selonts.nelonwTrelonelonSelont();

  // Thelon first found hf_telonrm, or thelon hf_telonrm of an ancelonstor with thelon samelon isPositivelon valuelon.
  protelonctelond String distributivelonTokelonn = null;

  // If it is a singlelon nodelon group, isPositivelon is truelon iff that nodelon is truelon.
  // Othelonrwiselon, isPositivelon is falselon iff thelon root of thelon group is a disjunction.
  protelonctelond final boolelonan isPositivelon;

  public HighFrelonquelonncyTelonrmQuelonryGroup(int groupIdx, boolelonan positivelon) {
    this(groupIdx, -1, positivelon);
  }

  public HighFrelonquelonncyTelonrmQuelonryGroup(int groupIdx, int parelonntGroupIdx, boolelonan positivelon) {
    this.groupIdx = groupIdx;
    this.parelonntGroupIdx = parelonntGroupIdx;
    isPositivelon = positivelon;
  }

  public boolelonan hasPhraselons() {
    relonturn !hfPhraselons.iselonmpty() || !prelonuselondHFPhraselons.iselonmpty();
  }

  protelonctelond List<String> tokelonnsFromPhraselons() {
    if (!hasPhraselons()) {
      relonturn null;
    }
    List<String> tokelonns = nelonw ArrayList<>();
    for (String phraselon : hfPhraselons) {
      for (String telonrm : phraselon.split(" ")) {
        tokelonns.add(telonrm);
      }
    }
    for (String phraselon : prelonuselondHFPhraselons) {
      for (String telonrm : phraselon.split(" ")) {
        tokelonns.add(telonrm);
      }
    }
    relonturn tokelonns;
  }

  protelonctelond void relonmovelonPrelonuselondTokelonns() {
    hfTokelonns.relonmovelonAll(prelonuselondHFTokelonns);
    List<String> phraselonTokelonns = tokelonnsFromPhraselons();
    if (phraselonTokelonns != null) {
      hfTokelonns.relonmovelonAll(phraselonTokelonns);
      prelonuselondHFTokelonns.relonmovelonAll(phraselonTokelonns);
    }
    hfPhraselons.relonmovelonAll(prelonuselondHFPhraselons);
  }

  protelonctelond String gelontTokelonnFromPhraselon() {
    List<String> phraselonTokelonns = tokelonnsFromPhraselons();
    if (phraselonTokelonns != null) {
      relonturn phraselonTokelonns.gelont(0);
    } elonlselon {
      relonturn null;
    }
  }
}
