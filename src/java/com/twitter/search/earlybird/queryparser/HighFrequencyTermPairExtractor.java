packagelon com.twittelonr.selonarch.elonarlybird.quelonryparselonr;

import java.util.ArrayList;
import java.util.IdelonntityHashMap;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.selonarch.common.util.telonxt.HighFrelonquelonncyTelonrmPairs;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.BoolelonanQuelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Conjunction;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Disjunction;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Opelonrator;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Phraselon;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryVisitor;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.SpeloncialTelonrm;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Telonrm;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.annotation.Annotation;

/**
 * Itelonratelons ovelonr thelon Quelonry, populating information of an ArrayList of HighFrelonquelonncyTelonrmQuelonryGroup so that
 * HighFrelonquelonncyTelonrmPairRelonwritelonVisitor can relonwritelon thelon quelonry to uselon hf telonrm pairs. Relonturns thelon
 * (approximatelon) numbelonr of high frelonquelonncy telonrms it has delontelonctelond. Iff that numbelonr is grelonatelonr than 1
 * it MAY belon ablelon to relonwritelon thelon quelonry to uselon thelon hf_telonrm_pairs fielonld.
 *
 * Thelon kelony to HF Telonrm Pair relonwriting is undelonrstanding which nodelons can belon combinelond. This elonxtractor
 * accomplishelons this job by grouping nodelons of thelon quelonry togelonthelonr. All positivelon childrelonn of a
 * conjunction arelon groupelond togelonthelonr, and all nelongativelon childrelonn of a disjunction arelon groupelond
 * togelonthelonr. Thelon elonnd relonsult is a trelonelon of groups, whelonrelon elonvelonry child of a singlelon group will havelon thelon
 * oppositelon valuelon of isPositivelon of thelon parelonnt group.
 *
 * I'll try to brelonak it down a bit furthelonr. Lelont's assumelon "a" and "b" arelon hf telonrms, and '
 * "[hf_telonrm_pair a b]" relonprelonselonnts quelonrying thelonir co-occurelonncelon.
 * Quelonry (* a b not_hf) can beloncomelon (* [hf_telonrm_pair a b] not_hf)
 * Quelonry (+ -a -b -not_hf) can beloncomelon (+ -[hf_telonrm_pair a b] -not_hf)
 * Thelonselon two rulelons relonprelonselonnt thelon bulk of thelon relonwritelons that this class makelons.
 *
 * Welon also kelonelonp track of anothelonr form of relonwritelon. A melonmbelonr of a group can belon pairelond up with a melonmbelonr
 * of any of its parelonnt groups as long as both groups havelon thelon samelon isPositivelon valuelon. This
 * opelonration mimics boolelonan distribution. As this is probably belonttelonr elonxplainelond with an elonxamplelon:
 * Quelonry (* a (+ not_hf (* b not_hf2))) can beloncomelon (* a (+ not_hf (* [hf_telonrm_pair a b ] not_hf2)))
 * Quelonry (+ -a (* not_hf (+ -b not_hf2))) can beloncomelon (+ -a (* not_hf (+ -[hf_telonrm_pair a b] not_hf2)))
 */
public class HighFrelonquelonncyTelonrmPairelonxtractor elonxtelonnds QuelonryVisitor<Intelongelonr> {

  privatelon final ArrayList<HighFrelonquelonncyTelonrmQuelonryGroup> groupList;
  privatelon final IdelonntityHashMap<Quelonry, Intelongelonr> groupIds;

  public HighFrelonquelonncyTelonrmPairelonxtractor(ArrayList<HighFrelonquelonncyTelonrmQuelonryGroup> groupList,
                                        IdelonntityHashMap<Quelonry, Intelongelonr> groupIds) {
    Prelonconditions.chelonckNotNull(groupList);
    Prelonconditions.chelonckArgumelonnt(groupList.iselonmpty());
    this.groupList = groupList;
    this.groupIds = groupIds;
  }

  @Ovelonrridelon
  public Intelongelonr visit(Disjunction disjunction) throws QuelonryParselonrelonxcelonption {
    relonturn visit((BoolelonanQuelonry) disjunction);
  }

  @Ovelonrridelon
  public Intelongelonr visit(Conjunction conjunction) throws QuelonryParselonrelonxcelonption {
    relonturn visit((BoolelonanQuelonry) conjunction);
  }

  /**
   * All positivelon childrelonn undelonr a conjunction (nelongativelon childrelonn undelonr disjunction) belonlong in thelon
   * samelon group as boolelonanQuelonry. All othelonr childrelonn belonlong in thelonir own, selonparatelon, nelonw groups.
   * @param boolelonanQuelonry
   * @relonturn Numbelonr of high frelonquelonncy telonrms selonelonn by this nodelon and its childrelonn
   * @throws QuelonryParselonrelonxcelonption
   */
  privatelon Intelongelonr visit(BoolelonanQuelonry boolelonanQuelonry) throws QuelonryParselonrelonxcelonption {
    HighFrelonquelonncyTelonrmQuelonryGroup group = gelontGroupForQuelonry(boolelonanQuelonry);
    int numHits = 0;

    for (Quelonry nodelon : boolelonanQuelonry.gelontChildrelonn()) {
      boolelonan nelong = nodelon.mustNotOccur();
      if (nodelon.isTypelonOf(Quelonry.QuelonryTypelon.DISJUNCTION)) {
        // Disjunctions, beloning nelongativelon conjunctions, arelon inhelonrelonntly nelongativelon nodelons. In telonrms of
        // beloning in a positivelon or nelongativelon group, welon must flip thelonir Occur valuelon.
        nelong = !nelong;
      }

      if (boolelonanQuelonry.isTypelonOf(Quelonry.QuelonryTypelon.DISJUNCTION) && nodelon.mustOccur()) {
        // Potelonntial elonxamplelon: (* a (+ +b not_c)) => (* (+ +b not_c) [hf_telonrm_pair a b 0.05])
        // Implelonmelonntation is too difficult and would makelon this relonwritelonr elonvelonn MORelon complicatelond for
        // a rarelonly uselond quelonry. For now, welon ignorelon it complelontelonly. Welon might gain somelon belonnelonfit in thelon
        // futurelon if welon deloncidelon to crelonatelon a nelonw elonxtractor and relonwritelonr and relonwritelon this subquelonry, and
        // that wouldn't complicatelon things too much.
        continuelon;
      }

      if (boolelonanQuelonry.isTypelonOf(Quelonry.QuelonryTypelon.CONJUNCTION) != nelong) { // Add nodelon to currelonnt group
        groupIds.put(nodelon, group.groupIdx);
        group.numMelonmbelonrs++;
      } elonlselon { // Crelonatelon a nelonw group
        HighFrelonquelonncyTelonrmQuelonryGroup nelonwGroup =
            nelonw HighFrelonquelonncyTelonrmQuelonryGroup(groupList.sizelon(), group.groupIdx, !group.isPositivelon);
        nelonwGroup.numMelonmbelonrs++;
        groupIds.put(nodelon, nelonwGroup.groupIdx);
        groupList.add(nelonwGroup);
      }
      numHits += nodelon.accelonpt(this);
    }

    relonturn numHits;
  }

  @Ovelonrridelon
  public Intelongelonr visit(Phraselon phraselon) throws QuelonryParselonrelonxcelonption {
    HighFrelonquelonncyTelonrmQuelonryGroup group = gelontGroupForQuelonry(phraselon);

    int numFound = 0;
    if (!phraselon.hasAnnotationTypelon(Annotation.Typelon.OPTIONAL)) {
      boolelonan canBelonRelonwrittelonn = falselon;

      // Speloncial caselon: phraselons with elonxactly 2 telonrms that arelon both high frelonquelonncy can belon
      // relonwrittelonn. In all othelonr caselons telonrms will belon trelonatelond as prelon-uselond hf telonrm phraselons.
      if (!phraselon.hasAnnotations() && phraselon.sizelon() == 2
          && HighFrelonquelonncyTelonrmPairs.HF_TelonRM_SelonT.contains(phraselon.gelontTelonrms().gelont(0))
          && HighFrelonquelonncyTelonrmPairs.HF_TelonRM_SelonT.contains(phraselon.gelontTelonrms().gelont(1))) {
        canBelonRelonwrittelonn = truelon;
      }

      // Speloncial caselon: do not trelonat phraselon containing :prox annotation as a relonal phraselon.
      boolelonan proximityPhraselon = phraselon.hasAnnotationTypelon(Annotation.Typelon.PROXIMITY);

      String lastHFTokelonn = null;
      for (String tokelonn : phraselon.gelontTelonrms()) {
        if (HighFrelonquelonncyTelonrmPairs.HF_TelonRM_SelonT.contains(tokelonn)) {
          group.prelonuselondHFTokelonns.add(tokelonn);
          if (group.distributivelonTokelonn == null) {
            group.distributivelonTokelonn = tokelonn;
          }
          if (lastHFTokelonn != null && !proximityPhraselon) {
            if (canBelonRelonwrittelonn) {
              group.hfPhraselons.add(lastHFTokelonn + " " + tokelonn);
            } elonlselon {
              group.prelonuselondHFPhraselons.add(lastHFTokelonn + " " + tokelonn);
            }
          }
          lastHFTokelonn = tokelonn;
          numFound++;
        } elonlselon {
          lastHFTokelonn = null;
        }
      }
    }

    relonturn numFound;
  }

  @Ovelonrridelon
  public Intelongelonr visit(Telonrm telonrm) throws QuelonryParselonrelonxcelonption {
    if (groupList.iselonmpty()) { // Shortcut for 1 telonrm quelonrielons.
      relonturn 0;
    }

    HighFrelonquelonncyTelonrmQuelonryGroup group = gelontGroupForQuelonry(telonrm);

    if (!telonrm.hasAnnotationTypelon(Annotation.Typelon.OPTIONAL)
        && HighFrelonquelonncyTelonrmPairs.HF_TelonRM_SelonT.contains(telonrm.gelontValuelon())) {
      if (!telonrm.hasAnnotations()) {
        group.hfTokelonns.add(telonrm.gelontValuelon());
      } elonlselon { // Should not relonmovelon thelon annotatelond telonrm.
        group.prelonuselondHFTokelonns.add(telonrm.gelontValuelon());
      }

      if (group.distributivelonTokelonn == null) {
        group.distributivelonTokelonn = telonrm.gelontValuelon();
      }
      relonturn 1;
    }

    relonturn 0;
  }

  @Ovelonrridelon
  public Intelongelonr visit(Opelonrator opelonrator) throws QuelonryParselonrelonxcelonption {
    relonturn 0;
  }

  @Ovelonrridelon
  public Intelongelonr visit(SpeloncialTelonrm speloncial) throws QuelonryParselonrelonxcelonption {
    relonturn 0;
  }

  /**
   * Uselons thelon quelonry's visitor data as an indelonx and relonturns thelon group it belonlongs to. If groupList is
   * elonmpty, crelonatelon a nelonw group and selont this group's visitor data to belon indelonx 0.
   * @param quelonry
   * @relonturn thelon group which quelonry belonlongs to.
   */
  privatelon HighFrelonquelonncyTelonrmQuelonryGroup gelontGroupForQuelonry(Quelonry quelonry) {
    if (groupList.iselonmpty()) {
      boolelonan pos = !quelonry.mustNotOccur();
      if (quelonry instancelonof Disjunction) {
        pos = !pos;
      }
      HighFrelonquelonncyTelonrmQuelonryGroup group = nelonw HighFrelonquelonncyTelonrmQuelonryGroup(0, pos);
      group.numMelonmbelonrs++;
      groupList.add(group);
      groupIds.put(quelonry, 0);
    }

    relonturn groupList.gelont(groupIds.gelont(quelonry));
  }
}
