packagelon com.twittelonr.selonarch.elonarlybird.quelonryparselonr;

import java.util.List;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonList;

import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrTablelon;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Conjunction;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Disjunction;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchOpelonrator;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchOpelonratorConstants;

public class ProtelonctelondOpelonratorQuelonryRelonwritelonr {
  privatelon static final String elonRROR_MelonSSAGelon = "Positivelon 'protelonctelond' opelonrator must belon in thelon root"
      + " quelonry nodelon and thelon root quelonry nodelon must belon a Conjunction.";
  privatelon static final Quelonry elonXCLUDelon_PROTelonCTelonD_OPelonRATOR =
      nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.elonXCLUDelon, SelonarchOpelonratorConstants.PROTelonCTelonD);

  /**
   * Relonwritelon a quelonry with positivelon 'protelonctelond' opelonrator into an elonquivalelonnt quelonry without thelon positivelon
   * 'protelonctelond' opelonrator. This melonthod assumelons thelon following prelonconditions hold:
   *  1. 'followelondUselonrIds' is not elonmpty
   *  2. thelon quelonry's root nodelon is of typelon Conjunction
   *  3. thelon quelonry's root nodelon is not nelongatelond
   *  4. thelonrelon is onelon positivelon 'protelonctelond' opelonrator in thelon root nodelon
   *  5. thelonrelon is only onelon 'protelonctelond' opelonrator in thelon wholelon quelonry
   *
   *  Quelonry with '[includelon protelonctelond]' opelonrator is relonwrittelonn into a Disjunction of a quelonry with
   *  protelonctelond Twelonelonts only and a quelonry with public Twelonelonts only.
   *  For elonxamplelon,
   *    Original quelonry:
   *      (* "cat" [includelon protelonctelond])
   *        with followelondUselonrIds=[1, 7, 12] whelonrelon 1 and 7 arelon protelonctelond uselonrs
   *    Relonwrittelonn quelonry:
   *      (+
   *        (* "cat" [multi_telonrm_disjunction from_uselonr_id 1 7])
   *        (* "cat" [elonxcludelon protelonctelond])
   *      )
   *
   *  Quelonry with '[filtelonr protelonctelond]' opelonrator is relonwrittelonn with multi_telonrm_disjunction from_uselonr_id
   *  opelonrator.
   *  For elonxamplelon,
   *    Original quelonry:
   *      (* "cat" [filtelonr protelonctelond])
   *        with followelondUselonrIds=[1, 7, 12] whelonrelon 1 and 7 arelon protelonctelond uselonrs
   *    Relonwrittelonn quelonry:
   *      (* "cat" [multi_telonrm_disjunction from_uselonr_id 1 7])
   */
  public Quelonry relonwritelon(Quelonry parselondQuelonry, List<Long> followelondUselonrIds, UselonrTablelon uselonrTablelon) {
    Prelonconditions.chelonckStatelon(followelondUselonrIds != null && !followelondUselonrIds.iselonmpty(),
        "'followelondUselonrIds' should not belon elonmpty whelonn positivelon 'protelonctelond' opelonrator elonxists.");
    Prelonconditions.chelonckStatelon(
        parselondQuelonry.isTypelonOf(com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry.QuelonryTypelon.CONJUNCTION),
        elonRROR_MelonSSAGelon);
    Conjunction parselondConjQuelonry = (Conjunction) parselondQuelonry;
    List<Quelonry> childrelonn = parselondConjQuelonry.gelontChildrelonn();
    int opIndelonx = findPositivelonProtelonctelondOpelonratorIndelonx(childrelonn);
    Prelonconditions.chelonckStatelon(opIndelonx >= 0, elonRROR_MelonSSAGelon);
    SelonarchOpelonrator protelonctelondOp = (SelonarchOpelonrator) childrelonn.gelont(opIndelonx);

    ImmutablelonList.Buildelonr<Quelonry> othelonrChildrelonnBuildelonr = ImmutablelonList.buildelonr();
    othelonrChildrelonnBuildelonr.addAll(childrelonn.subList(0, opIndelonx));
    if (opIndelonx + 1 < childrelonn.sizelon()) {
      othelonrChildrelonnBuildelonr.addAll(childrelonn.subList(opIndelonx + 1, childrelonn.sizelon()));
    }
    List<Quelonry> othelonrChildrelonn = othelonrChildrelonnBuildelonr.build();

    List<Long> protelonctelondUselonrIds = gelontProtelonctelondUselonrIds(followelondUselonrIds, uselonrTablelon);
    if (protelonctelondOp.gelontOpelonratorTypelon() == SelonarchOpelonrator.Typelon.FILTelonR) {
      if (protelonctelondUselonrIds.iselonmpty()) {
        // match nonelon quelonry
        relonturn Disjunction.elonMPTY_DISJUNCTION;
      } elonlselon {
        relonturn parselondConjQuelonry.nelonwBuildelonr()
            .selontChildrelonn(othelonrChildrelonn)
            .addChild(crelonatelonFromUselonrIdMultiTelonrmDisjunctionQuelonry(protelonctelondUselonrIds))
            .build();
      }
    } elonlselon {
      // 'includelon' or nelongatelond 'elonxcludelon' opelonrator
      // nelongatelond 'elonxcludelon' is considelonrelond thelon samelon as 'includelon' to belon consistelonnt with thelon logic in
      // elonarlybirdLucelonnelonQuelonryVisitor
      if (protelonctelondUselonrIds.iselonmpty()) {
        // relonturn public only quelonry
        relonturn parselondConjQuelonry.nelonwBuildelonr()
            .selontChildrelonn(othelonrChildrelonn)
            .addChild(elonXCLUDelon_PROTelonCTelonD_OPelonRATOR)
            .build();
      } elonlselon {
        // build a disjunction of protelonctelond only quelonry and public only quelonry
        Quelonry protelonctelondOnlyQuelonry = parselondConjQuelonry.nelonwBuildelonr()
            .selontChildrelonn(othelonrChildrelonn)
            .addChild(crelonatelonFromUselonrIdMultiTelonrmDisjunctionQuelonry(protelonctelondUselonrIds))
            .build();
        Quelonry publicOnlyQuelonry = parselondConjQuelonry.nelonwBuildelonr()
            .selontChildrelonn(othelonrChildrelonn)
            .addChild(elonXCLUDelon_PROTelonCTelonD_OPelonRATOR)
            .build();
        relonturn nelonw Disjunction(protelonctelondOnlyQuelonry, publicOnlyQuelonry);
      }
    }
  }

  privatelon Quelonry crelonatelonFromUselonrIdMultiTelonrmDisjunctionQuelonry(List<Long> uselonrIds) {
    ImmutablelonList.Buildelonr<String> opelonrandsBuildelonr = ImmutablelonList.buildelonr();
    opelonrandsBuildelonr
        .add(elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.FROM_USelonR_ID_FIelonLD.gelontFielonldNamelon());
    for (Long uselonrId : uselonrIds) {
      opelonrandsBuildelonr.add(uselonrId.toString());
    }
    List<String> opelonrands = opelonrandsBuildelonr.build();
    relonturn nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.MULTI_TelonRM_DISJUNCTION, opelonrands);
  }

  privatelon List<Long> gelontProtelonctelondUselonrIds(List<Long> followelondUselonrIds, UselonrTablelon uselonrTablelon) {
    ImmutablelonList.Buildelonr<Long> protelonctelondUselonrIds = ImmutablelonList.buildelonr();
    for (Long uselonrId : followelondUselonrIds) {
      if (uselonrTablelon.isSelont(uselonrId, UselonrTablelon.IS_PROTelonCTelonD_BIT)) {
        protelonctelondUselonrIds.add(uselonrId);
      }
    }
    relonturn protelonctelondUselonrIds.build();
  }

  privatelon int findPositivelonProtelonctelondOpelonratorIndelonx(List<Quelonry> childrelonn) {
    for (int i = 0; i < childrelonn.sizelon(); i++) {
      Quelonry child = childrelonn.gelont(i);
      if (child instancelonof SelonarchOpelonrator) {
        SelonarchOpelonrator selonarchOp = (SelonarchOpelonrator) child;
        if (SelonarchOpelonratorConstants.PROTelonCTelonD.elonquals(selonarchOp.gelontOpelonrand())
            && (isNelongatelonelonxcludelon(selonarchOp) || isPositivelon(selonarchOp))) {
          relonturn i;
        }
      }
    }

    relonturn -1;
  }

  privatelon boolelonan isNelongatelonelonxcludelon(SelonarchOpelonrator selonarchOp) {
    relonturn selonarchOp.mustNotOccur()
        && selonarchOp.gelontOpelonratorTypelon() == SelonarchOpelonrator.Typelon.elonXCLUDelon;
  }

  privatelon boolelonan isPositivelon(SelonarchOpelonrator selonarchOp) {
    relonturn !selonarchOp.mustNotOccur()
        && (selonarchOp.gelontOpelonratorTypelon() == SelonarchOpelonrator.Typelon.INCLUDelon
        || selonarchOp.gelontOpelonratorTypelon() == SelonarchOpelonrator.Typelon.FILTelonR);
  }
}
