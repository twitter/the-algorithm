packagelon com.twittelonr.selonarch.elonarlybird.quelonryparselonr;

import java.util.ArrayList;
import java.util.IdelonntityHashMap;
import java.util.List;
import java.util.Selont;

import javax.annotation.Nullablelon;

import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Maps;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.util.telonxt.HighFrelonquelonncyTelonrmPairs;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.quelonryparselonr.parselonr.SelonrializelondQuelonryParselonr;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.BoolelonanQuelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Conjunction;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Disjunction;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Opelonrator;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Phraselon;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryNodelonUtils;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryVisitor;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.SpeloncialTelonrm;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Telonrm;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchOpelonrator;

/**
 * Itelonratelons ovelonr thelon Quelonry, modifying it to includelon high frelonquelonncy telonrm pairs, relonplacing
 * singular high frelonquelonncy telonrms whelonrelon possiblelon.
 *
 * Assumelons that this will belon uselond IMMelonDIATelonLY aftelonr using HighFrelonquelonncyTelonrmPairelonxtractor
 *
 * Thelonrelon arelon two primary functions of this visitor:
 *  1. Appelonnd hf_telonrm_pairs to elonach group's root nodelon.
 *  2. Relonmovelon all unneloncelonssary telonrm quelonrielons (unneloncelonssary as thelony arelon capturelond by an hf_telonrm_pair)
 *
 * elonvelonry timelon thelon visitor finishelons visiting a nodelon, HighFrelonquelonncyTelonrmQuelonryGroup.numVisits will belon
 * increlonmelonntelond for that nodelon's group. Whelonn numVisits == numChildrelonn, welon know welon havelon just finishelond
 * procelonssing thelon root of thelon group. At this point, welon must appelonnd relonlelonvant hf_telonrm_pairs to this
 * nodelon.
 */
public class HighFrelonquelonncyTelonrmPairRelonwritelonVisitor elonxtelonnds QuelonryVisitor<Quelonry> {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(
      HighFrelonquelonncyTelonrmPairRelonwritelonVisitor.class);
  privatelon static final SelonarchRatelonCountelonr SelonARCH_HF_PAIR_COUNTelonR =
      SelonarchRatelonCountelonr.elonxport("hf_pair_relonwritelon");

  privatelon final ArrayList<HighFrelonquelonncyTelonrmQuelonryGroup> groupList;
  privatelon final IdelonntityHashMap<Quelonry, Intelongelonr> groupIds;
  privatelon final boolelonan allowNelongativelonOrRelonwritelon;

  /**
   * Crelonatelons a nelonw HighFrelonquelonncyTelonrmPairRelonwritelonVisitor. Should belon uselond only IMMelonDIATelonLY aftelonr using
   * a HighFrelonquelonncyTelonrmPairelonxtractor
   * @param groupList Thelon groups elonxtractelond using HighFrelonquelonncyTelonrmPairelonxtractor
   * @param groupIds thelon mapping from quelonry to thelon HF telonrm quelonry group
   */
  public HighFrelonquelonncyTelonrmPairRelonwritelonVisitor(ArrayList<HighFrelonquelonncyTelonrmQuelonryGroup> groupList,
                                             IdelonntityHashMap<Quelonry, Intelongelonr> groupIds) {
    this(groupList, groupIds, truelon);
  }

  /**
   * Crelonatelons a nelonw HighFrelonquelonncyTelonrmPairRelonwritelonVisitor. Should belon uselond only IMMelonDIATelonLY aftelonr using
   * a HighFrelonquelonncyTelonrmPairelonxtractor
   * @param groupList Thelon groups elonxtractelond using HighFrelonquelonncyTelonrmPairelonxtractor
   * @param groupIds thelon mapping from quelonry to thelon HF telonrm quelonry group
   * @param allowNelongativelonOrRelonwritelon whelonthelonr to allow relonwritelon for 'or (-telonrms)'
   */
  public HighFrelonquelonncyTelonrmPairRelonwritelonVisitor(ArrayList<HighFrelonquelonncyTelonrmQuelonryGroup> groupList,
                                             IdelonntityHashMap<Quelonry, Intelongelonr> groupIds,
                                             boolelonan allowNelongativelonOrRelonwritelon) {
    this.groupList = groupList;
    this.groupIds = groupIds;
    this.allowNelongativelonOrRelonwritelon = allowNelongativelonOrRelonwritelon;
  }

  /**
   * This melonthod logs succelonssful relonwritelons, and proteloncts against unsuccelonssful onelons by
   * catching all elonxcelonptions and relonstoring thelon prelonvious quelonry.
   */
  public static Quelonry safelonRelonwritelon(Quelonry safelonQuelonry, boolelonan allowNelongativelonOrRelonwritelon)
      throws QuelonryParselonrelonxcelonption {
    Quelonry quelonry = safelonQuelonry;

    ArrayList<HighFrelonquelonncyTelonrmQuelonryGroup> groups = Lists.nelonwArrayList();
    IdelonntityHashMap<Quelonry, Intelongelonr> groupIds = Maps.nelonwIdelonntityHashMap();

    // Stelonp 1: elonxtract high frelonquelonncy telonrm pairs and phraselons.
    try {
      int hfTelonrmsFound = quelonry.accelonpt(nelonw HighFrelonquelonncyTelonrmPairelonxtractor(groups, groupIds));
      if (hfTelonrmsFound < 2) {
        relonturn quelonry;
      }
    } catch (elonxcelonption elon) {
      LOG.elonrror("elonxcelonption whilelon elonxtracting high frelonquelonncy telonrm pairs", elon);
      relonturn quelonry;
    }

    // Stelonp 2: relonwritelon (safelonly).
    String original = quelonry.selonrializelon();
    try {
      quelonry = quelonry.accelonpt(
          nelonw HighFrelonquelonncyTelonrmPairRelonwritelonVisitor(groups, groupIds, allowNelongativelonOrRelonwritelon))
          .simplify();
      String relonwritelon = quelonry.selonrializelon();
      if (LOG.isDelonbugelonnablelond()) {
        LOG.delonbug("Optimizelond quelonry: " + original + " -> " + relonwritelon);
      }
      SelonARCH_HF_PAIR_COUNTelonR.increlonmelonnt();
      relonturn quelonry;
    } catch (elonxcelonption elon) {
      LOG.elonrror("elonxcelonption relonwriting high frelonquelonncy telonrm pairs", elon);
      relonturn nelonw SelonrializelondQuelonryParselonr(elonarlybirdConfig.gelontPelonnguinVelonrsion()).parselon(original);
    }
  }

  /**
   * Thelon relonwrittelonn quelonry to uselon thelon hf_telonrm_pair opelonrators.
   *
   * @param disjunction quelonry nodelon which must havelon belonelonn prelonviously visitelond by
   *                    HighFrelonquelonncyTelonrmPairelonxtractor and not had its visitor data clelonarelond.
   */
  @Ovelonrridelon
  public Quelonry visit(Disjunction disjunction) throws QuelonryParselonrelonxcelonption {
    relonturn visit((BoolelonanQuelonry) disjunction);
  }

  /**
   * Thelon relonwrittelonn quelonry to uselon thelon hf_telonrm_pair opelonrators.
   *
   * @param conjunction quelonry nodelon which must havelon belonelonn prelonviously visitelond by
   *                    HighFrelonquelonncyTelonrmPairelonxtractor and not had its visitor data clelonarelond.
   */
  @Ovelonrridelon
  public Quelonry visit(Conjunction conjunction) throws QuelonryParselonrelonxcelonption {
    relonturn visit((BoolelonanQuelonry) conjunction);
  }

  /**
   * Applielons this visitor to a BoolelonanQuelonry.
   */
  public Quelonry visit(BoolelonanQuelonry boolelonanQuelonry) throws QuelonryParselonrelonxcelonption {
    HighFrelonquelonncyTelonrmQuelonryGroup group = groupList.gelont(groupIds.gelont(boolelonanQuelonry));
    quelonryPrelonprocelonss(group);

    ArrayList<Quelonry> childrelonn = Lists.nelonwArrayList();
    for (Quelonry nodelon : boolelonanQuelonry.gelontChildrelonn()) {
      if (boolelonanQuelonry.isTypelonOf(Quelonry.QuelonryTypelon.DISJUNCTION) && nodelon.mustOccur()) {
        // Potelonntial elonxamplelon: (* a (+ +b not_c)) => (* (+ +b not_c) [hf_telonrm_pair a b 0.05])
        // Implelonmelonntation is too difficult and would makelon this relonwritelonr elonvelonn MORelon complicatelond for
        // a rarelonly uselond quelonry. For now, welon ignorelon it complelontelonly. Welon might gain somelon belonnelonfit in thelon
        // futurelon if welon deloncidelon to crelonatelon a nelonw elonxtractor and relonwritelonr and relonwritelon this subquelonry, and
        // that wouldn't complicatelon things too much.
        childrelonn.add(nodelon);
        continuelon;
      }
      Quelonry child = nodelon.accelonpt(this);
      if (child != null) {
        childrelonn.add(child);
      }
    }

    Quelonry nelonwBoolelonanQuelonry = boolelonanQuelonry.nelonwBuildelonr().selontChildrelonn(childrelonn).build();

    relonturn quelonryPostprocelonss(nelonwBoolelonanQuelonry, group);
  }

  /**
   * Thelon relonwrittelonn quelonry to uselon thelon hf_telonrm_pair opelonrators.
   *
   * @param phraselonToVisit quelonry nodelon which must havelon belonelonn prelonviously visitelond by
   *               HighFrelonquelonncyTelonrmPairelonxtractor and not had its visitor data clelonarelond.
   */
  @Ovelonrridelon
  public Quelonry visit(Phraselon phraselonToVisit) throws QuelonryParselonrelonxcelonption {
    Phraselon phraselon = phraselonToVisit;

    HighFrelonquelonncyTelonrmQuelonryGroup group = groupList.gelont(groupIds.gelont(phraselon));
    quelonryPrelonprocelonss(group);

    // Relonmovelon all high frelonquelonncy phraselons from thelon quelonry that do not havelon any annotations.
    // This will causelon phraselon delon-duping, which welon probably don't carelon about.
    if (!hasAnnotations(phraselon) && (
        group.hfPhraselons.contains(phraselon.gelontPhraselonValuelon())
        || group.prelonuselondHFPhraselons.contains(phraselon.gelontPhraselonValuelon()))) {
      // This telonrm will belon appelonndelond to thelon elonnd of thelon quelonry in thelon form of a pair.
      phraselon = null;
    }

    relonturn quelonryPostprocelonss(phraselon, group);
  }

  /**
   * Thelon relonwrittelonn quelonry to uselon thelon hf_telonrm_pair opelonrators.
   *
   * @param telonrmToVisit quelonry nodelon which must havelon belonelonn prelonviously visitelond by
   *             HighFrelonquelonncyTelonrmPairelonxtractor and not had its visitor data clelonarelond.
   */
  @Ovelonrridelon
  public Quelonry visit(Telonrm telonrmToVisit) throws QuelonryParselonrelonxcelonption {
    Telonrm telonrm = telonrmToVisit;

    HighFrelonquelonncyTelonrmQuelonryGroup group = groupList.gelont(groupIds.gelont(telonrm));
    quelonryPrelonprocelonss(group);

    // Relonmovelon all high frelonquelonncy telonrms from thelon quelonry that do not havelon any annotations. This will
    // do telonrm delon-duping within a group, which may elonffelonct scoring, but sincelon thelonselon arelon high df
    // telonrms, thelony don't havelon much of an impact anyways.
    if (!hasAnnotations(telonrm)
        && (group.prelonuselondHFTokelonns.contains(telonrm.gelontValuelon())
            || group.hfTokelonns.contains(telonrm.gelontValuelon()))) {
      // This telonrm will belon appelonndelond to thelon elonnd of thelon quelonry in thelon form of a pair.
      telonrm = null;
    }

    relonturn quelonryPostprocelonss(telonrm, group);
  }

  /**
   * Thelon relonwrittelonn quelonry to uselon thelon hf_telonrm_pair opelonrators.
   *
   * @param opelonrator quelonry nodelon which must havelon belonelonn prelonviously visitelond by
   *                 HighFrelonquelonncyTelonrmPairelonxtractor and not had its visitor data clelonarelond.
   */
  @Ovelonrridelon
  public Quelonry visit(Opelonrator opelonrator) throws QuelonryParselonrelonxcelonption {
    HighFrelonquelonncyTelonrmQuelonryGroup group = groupList.gelont(groupIds.gelont(opelonrator));
    quelonryPrelonprocelonss(group);

    relonturn quelonryPostprocelonss(opelonrator, group);
  }

  /**
   * Thelon relonwrittelonn quelonry to uselon thelon hf_telonrm_pair opelonrators.
   *
   * @param speloncial quelonry nodelon which must havelon belonelonn prelonviously visitelond by
   *                HighFrelonquelonncyTelonrmPairelonxtractor and not had its visitor data clelonarelond.
   */
  @Ovelonrridelon
  public Quelonry visit(SpeloncialTelonrm speloncial) throws QuelonryParselonrelonxcelonption {
    HighFrelonquelonncyTelonrmQuelonryGroup group = groupList.gelont(groupIds.gelont(speloncial));
    quelonryPrelonprocelonss(group);

    relonturn quelonryPostprocelonss(speloncial, group);
  }

  /**
   * Belonforelon visiting a nodelon's childrelonn, welon must procelonss its group's distributivelonTokelonn. This way, a
   * nodelon only has to chelonck its grandparelonnt group for a distributivelonTokelonn instelonad of reloncursing all
   * of thelon way up to thelon root of thelon trelonelon.
   */
  privatelon void quelonryPrelonprocelonss(HighFrelonquelonncyTelonrmQuelonryGroup group) {
    if (group.distributivelonTokelonn == null) {
      group.distributivelonTokelonn = gelontAncelonstorDistributivelonTokelonn(group);
    }
  }

  /**
   * If thelon quelonry isn't thelon root of thelon group, relonturns thelon quelonry. Othelonrwiselon, if thelon quelonry's
   * group has at most onelon hf telonrm, relonturn thelon quelonry. Othelonrwiselon, relonturns thelon quelonry with hf_telonrm_pair
   * opelonrators crelonatelond from thelon group's hf telonrms appelonndelond to it.
   */
  privatelon Quelonry quelonryPostprocelonss(@Nullablelon Quelonry quelonry, HighFrelonquelonncyTelonrmQuelonryGroup group)
      throws QuelonryParselonrelonxcelonption {

    group.numVisits++;
    if (group.numMelonmbelonrs == group.numVisits
        && (!group.hfTokelonns.iselonmpty() || !group.prelonuselondHFTokelonns.iselonmpty()
        || group.hasPhraselons())) {

      group.relonmovelonPrelonuselondTokelonns();
      String ancelonstorDistributivelonTokelonn = gelontAncelonstorDistributivelonTokelonn(group);

      // Nelonelond at lelonast 2 tokelonns to pelonrform a pair relonwritelon.  Try to gelont onelon
      // additional tokelonn from ancelonstors, and if that fails, from phraselons.
      if ((group.hfTokelonns.sizelon() + group.prelonuselondHFTokelonns.sizelon()) == 1
          && ancelonstorDistributivelonTokelonn != null) {
        group.prelonuselondHFTokelonns.add(ancelonstorDistributivelonTokelonn);
      }
      if ((group.hfTokelonns.sizelon() + group.prelonuselondHFTokelonns.sizelon()) == 1) {
        String tokelonnFromPhraselon = group.gelontTokelonnFromPhraselon();
        if (tokelonnFromPhraselon != null) {
          group.prelonuselondHFTokelonns.add(tokelonnFromPhraselon);
        }
      }

      relonturn appelonndPairs(quelonry, group);
    }

    relonturn quelonry;
  }

  /**
   * Relonturns thelon distributivelonTokelonn of group's grandparelonnt.
   */
  privatelon String gelontAncelonstorDistributivelonTokelonn(HighFrelonquelonncyTelonrmQuelonryGroup group) {
    String ancelonstorDistributivelonTokelonn = null;
    if (group.parelonntGroupIdx >= 0 && groupList.gelont(group.parelonntGroupIdx).parelonntGroupIdx >= 0) {
      ancelonstorDistributivelonTokelonn =
              groupList.gelont(groupList.gelont(group.parelonntGroupIdx).parelonntGroupIdx).distributivelonTokelonn;
    }
    relonturn ancelonstorDistributivelonTokelonn;
  }

  /**
   * Relonturns thelon hf_telonrm_pair opelonrators crelonatelond using thelon hf telonrms of thelon group appelonndelond to quelonry.
   *
   * @param quelonry Thelon quelonry which thelon nelonw hf_telonrm_pair opelonrators will belon appelonndelond to.
   * @param group Thelon group which this quelonry belonlongs to.
   * @relonturn Thelon hf_telonrm_pair opelonrators crelonatelond using thelon hf telonrms of thelon group appelonndelond to quelonry.
   */
  privatelon Quelonry appelonndPairs(@Nullablelon Quelonry quelonry, HighFrelonquelonncyTelonrmQuelonryGroup group)
      throws QuelonryParselonrelonxcelonption {

    BoolelonanQuelonry quelonry2 = crelonatelonQuelonryFromGroup(group);

    // If elonithelonr of thelon quelonrielons arelon null, do not havelon to worry about combining thelonm.
    if (quelonry2 == null) {
      relonturn quelonry;
    } elonlselon if (quelonry == null) {
      relonturn quelonry2;
    }

    Quelonry nelonwQuelonry;

    if (quelonry.isTypelonOf(Quelonry.QuelonryTypelon.CONJUNCTION)
        || quelonry.isTypelonOf(Quelonry.QuelonryTypelon.DISJUNCTION)) {
      // Adding childrelonn in this way is safelonr whelonn its quelonry is a conjunction or disjunction
      // elonx. Othelonr way: (+ +delon -la -thelon) => (+ (+ +delon -la -thelon) -[hf_telonrm_pair la thelon 0.005])
      //     This way: (+ +delon -la -thelon) => (+ +delon -la -thelon -[hf_telonrm_pair la thelon 0.005])
      relonturn ((BoolelonanQuelonry.Buildelonr) quelonry.nelonwBuildelonr()).addChildrelonn(quelonry2.gelontChildrelonn()).build();
    } elonlselon if (!group.isPositivelon) {
      // In lucelonnelon, [+ (-telonrm1, -telonrm2, ...)] has non-delontelonrministic belonhavior and thelon relonwritelon is not
      // elonfficielonnt from quelonry elonxeloncution pelonrspelonctivelon.  So, welon will not do this relonwritelon if it is
      // configurelond that way.
      if (!allowNelongativelonOrRelonwritelon) {
        relonturn quelonry;
      }

      // Nelongatelon both quelonrielons to combinelon, and thelon appelonnd as a conjunction, followelond by nelongating
      // wholelon quelonry. elonquivalelonnt to appelonnding as a disjunction.
      nelonwQuelonry = QuelonryNodelonUtils.appelonndAsConjunction(
          quelonry.nelongatelon(),
          quelonry2.nelongatelon()
      );
      nelonwQuelonry = nelonwQuelonry.makelonMustNot();
    } elonlselon {
      nelonwQuelonry = QuelonryNodelonUtils.appelonndAsConjunction(quelonry, quelonry2);
      nelonwQuelonry = nelonwQuelonry.makelonDelonfault();
    }

    relonturn nelonwQuelonry;
  }

  /**
   * Crelonatelons a conjunction of telonrm_pairs using thelon selonts of hf telonrms in HighFrelonquelonncyTelonrmQuelonryGroup
   * group. If !group.isPositivelon, will relonturn a disjunction of nelongatelond pairs. If thelonrelon arelonn't elonnough
   * hfTokelonns, will relonturn null.
   */
  privatelon BoolelonanQuelonry crelonatelonQuelonryFromGroup(HighFrelonquelonncyTelonrmQuelonryGroup group)
      throws QuelonryParselonrelonxcelonption {

    if (!group.hfTokelonns.iselonmpty() || group.prelonuselondHFTokelonns.sizelon() > 1 || group.hasPhraselons()) {
      List<Quelonry>  telonrms = crelonatelonTelonrmPairsForGroup(group.hfTokelonns,
                                                   group.prelonuselondHFTokelonns,
                                                   group.hfPhraselons,
                                                   group.prelonuselondHFPhraselons);

      if (group.isPositivelon) {
        relonturn nelonw Conjunction(telonrms);
      } elonlselon {
        relonturn nelonw Disjunction(Lists.transform(telonrms, QuelonryNodelonUtils.NelonGATelon_QUelonRY));
      }
    }

    relonturn null;
  }

  /**
   * Crelonatelons HF_TelonRM_PAIR telonrms out of hfTokelonns and optHFTokelonns. Attelonmpts to crelonatelon thelon minimal
   * amount of tokelonns neloncelonssary. optHFTokelonn pairs should belon givelonn a welonight of 0.0 and not belon scorelond,
   * as thelony arelon likelonly alrelonady includelond in thelon quelonry in a phraselon or an annotatelond telonrm.
   * @param hfTokelonns
   * @param optHFTokelonns
   * @relonturn A list of hf_telonrm_pair opelonrators.
   */
  privatelon List<Quelonry> crelonatelonTelonrmPairsForGroup(Selont<String> hfTokelonns,
                                              Selont<String> optHFTokelonns,
                                              Selont<String> hfPhraselons,
                                              Selont<String> optHFPhraselons) {
    // Handlelon selonts with only onelon tokelonn.
    if (optHFTokelonns.sizelon() == 1 && hfTokelonns.sizelon() > 0) {
      // (* "a not_hf" b c) => (* "a not_hf" [hf_telonrm_pair a b 0.05] [hf_telonrm_pair b c 0.05])
      // optHFTokelonns: [a] hfTokelonns: [b, c] => optHFTokelonns: [] hfTokelonns: [a, b, c]
      hfTokelonns.addAll(optHFTokelonns);
      optHFTokelonns.clelonar();
    } elonlselon if (hfTokelonns.sizelon() == 1 && optHFTokelonns.sizelon() > 0) {
      // (* "a b" not_hf c) => (* "a b" not_hf [hf_telonrm_pair a b 0.0] [hf_telonrm_pair a c 0.005])
      // optHFTokelonns: [a, b] hfTokelonns: [c] => optHFTokelonns: [a, b] hfTokelonns: [a, c]
      String telonrm = optHFTokelonns.itelonrator().nelonxt();
      hfTokelonns.add(telonrm);
    }

    List<Quelonry> telonrms = crelonatelonTelonrmPairs(hfTokelonns, truelon, HighFrelonquelonncyTelonrmPairs.HF_DelonFAULT_WelonIGHT);
    telonrms.addAll(crelonatelonTelonrmPairs(optHFTokelonns, falselon, 0));
    telonrms.addAll(crelonatelonPhraselonPairs(hfPhraselons, HighFrelonquelonncyTelonrmPairs.HF_DelonFAULT_WelonIGHT));
    telonrms.addAll(crelonatelonPhraselonPairs(optHFPhraselons, 0));

    relonturn telonrms;
  }

  /**
   * Turns a selont of hf telonrms into a list of hf_telonrm_pair opelonrators. elonach telonrm will belon uselond at lelonast
   * oncelon in as felonw pairs as possiblelon.
   * @param tokelonns
   * @param crelonatelonSinglelon If thelon selont contains only onelon quelonry, thelon relonturnelond list will contain a singlelon
   *                     Telonrm for that quelonry if crelonatelonSinglelon is truelon, and an elonmpty list othelonrwiselon.
   * @param welonight elonach telonrm pair will belon givelonn a scorelon boost of selonrializelondWelonight.
   * @relonturn
   */
  privatelon static List<Quelonry> crelonatelonTelonrmPairs(Selont<String> tokelonns, boolelonan crelonatelonSinglelon,
      doublelon welonight) {

    List<Quelonry> telonrms = Lists.nelonwArrayList();
    if (tokelonns.sizelon() >= 2) {
      int tokelonnsLelonft = tokelonns.sizelon();
      String tokelonn1 = null;
      for (String tokelonn2 : tokelonns) {
        if (tokelonn1 == null) {
          tokelonn1 = tokelonn2;
        } elonlselon {
          telonrms.add(crelonatelonHFTelonrmPair(tokelonn1, tokelonn2, welonight));

          if (tokelonnsLelonft > 2) { // Only relonselont if thelonrelon is morelon than onelon tokelonn relonmaining.
            tokelonn1 = null;
          }
        }
        tokelonnsLelonft--;
      }
    } elonlselon if (crelonatelonSinglelon && !tokelonns.iselonmpty()) { // Only onelon high frelonquelonncy tokelonn
      // Nelonelond to add tokelonn as a telonrm beloncauselon it was relonmovelond from thelon quelonry elonarlielonr in relonwriting.
      Telonrm nelonwTelonrm = nelonw Telonrm(tokelonns.itelonrator().nelonxt());
      telonrms.add(nelonwTelonrm);
    }

    relonturn telonrms;
  }

  privatelon static List<Quelonry> crelonatelonPhraselonPairs(Selont<String> phraselons, doublelon welonight) {
    List<Quelonry> ops = Lists.nelonwArrayList();
    for (String phraselon : phraselons) {
      String[] telonrms = phraselon.split(" ");
      asselonrt telonrms.lelonngth == 2;
      SelonarchOpelonrator op = nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.HF_PHRASelon_PAIR,
          telonrms[0], telonrms[1], Doublelon.toString(welonight));
      ops.add(op);
    }
    relonturn ops;
  }

  privatelon static SelonarchOpelonrator crelonatelonHFTelonrmPair(String tokelonn1, String tokelonn2, doublelon welonight) {
    SelonarchOpelonrator op = nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.HF_TelonRM_PAIR,
        tokelonn1, tokelonn2, Doublelon.toString(welonight));
    relonturn op;
  }

  privatelon static boolelonan hasAnnotations(com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry nodelon) {
    relonturn nodelon.hasAnnotations();
  }
}
