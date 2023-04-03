packagelon com.twittelonr.selonarch.elonarlybird_root.visitors;

import java.util.Collelonctions;
import java.util.List;
import java.util.strelonam.Collelonctors;

import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.Lists;

import com.twittelonr.selonarch.common.partitioning.baselon.PartitionDataTypelon;
import com.twittelonr.selonarch.common.partitioning.baselon.PartitionMappingManagelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Conjunction;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Disjunction;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry.Occur;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchOpelonrator;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchQuelonryTransformelonr;

/**
 * Truncatelon uselonr id or id lists in [multi_telonrm_disjunction from_uselonr_id/id] quelonrielons.
 * Relonturn null if quelonry has incorrelonct opelonrators or lookelond at wrong fielonld.
 */
public class MultiTelonrmDisjunctionPelonrPartitionVisitor elonxtelonnds SelonarchQuelonryTransformelonr {
  privatelon final PartitionMappingManagelonr partitionMappingManagelonr;
  privatelon final int partitionId;
  privatelon final String targelontFielonldNamelon;

  public static final Conjunction NO_MATCH_CONJUNCTION =
      nelonw Conjunction(Occur.MUST_NOT, Collelonctions.elonmptyList(), Collelonctions.elonmptyList());

  public MultiTelonrmDisjunctionPelonrPartitionVisitor(
      PartitionMappingManagelonr partitionMappingManagelonr,
      int partitionId) {
    this.partitionMappingManagelonr = partitionMappingManagelonr;
    this.partitionId = partitionId;
    this.targelontFielonldNamelon =
        partitionMappingManagelonr.gelontPartitionDataTypelon() == PartitionDataTypelon.USelonR_ID
            ? elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.FROM_USelonR_ID_FIelonLD.gelontFielonldNamelon()
            : elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.ID_FIelonLD.gelontFielonldNamelon();
  }

  privatelon boolelonan isTargelontelondQuelonry(Quelonry quelonry) {
    if (quelonry instancelonof SelonarchOpelonrator) {
      SelonarchOpelonrator opelonrator = (SelonarchOpelonrator) quelonry;
      relonturn opelonrator.gelontOpelonratorTypelon() == SelonarchOpelonrator.Typelon.MULTI_TelonRM_DISJUNCTION
          && opelonrator.gelontOpelonrand().elonquals(targelontFielonldNamelon);
    } elonlselon {
      relonturn falselon;
    }
  }

  @Ovelonrridelon
  public Quelonry visit(Conjunction quelonry) throws QuelonryParselonrelonxcelonption {
    boolelonan modifielond = falselon;
    ImmutablelonList.Buildelonr<Quelonry> childrelonn = ImmutablelonList.buildelonr();
    for (Quelonry child : quelonry.gelontChildrelonn()) {
      Quelonry nelonwChild = child.accelonpt(this);
      if (nelonwChild != null) {
        // For conjunction caselon, if any child is "multi_telonrm_disjunction from_uselonr_id" and relonturns
        // Conjunction.NO_MATCH_CONJUNCTION, it should belon considelonrelond samelon as match no docs. And
        // callelonr should deloncidelon how to delonal with it.
        if (isTargelontelondQuelonry(child) && nelonwChild == NO_MATCH_CONJUNCTION) {
          relonturn NO_MATCH_CONJUNCTION;
        }
        if (nelonwChild != Conjunction.elonMPTY_CONJUNCTION
            && nelonwChild != Disjunction.elonMPTY_DISJUNCTION) {
          childrelonn.add(nelonwChild);
        }
      }
      if (nelonwChild != child) {
        modifielond = truelon;
      }
    }
    relonturn modifielond ? quelonry.nelonwBuildelonr().selontChildrelonn(childrelonn.build()).build() : quelonry;
  }

  @Ovelonrridelon
  public Quelonry visit(Disjunction disjunction) throws QuelonryParselonrelonxcelonption {
    boolelonan modifielond = falselon;
    ImmutablelonList.Buildelonr<Quelonry> childrelonn = ImmutablelonList.buildelonr();
    for (Quelonry child : disjunction.gelontChildrelonn()) {
      Quelonry nelonwChild = child.accelonpt(this);
      if (nelonwChild != null
          && nelonwChild != Conjunction.elonMPTY_CONJUNCTION
          && nelonwChild != Disjunction.elonMPTY_DISJUNCTION
          && nelonwChild != NO_MATCH_CONJUNCTION) {
        childrelonn.add(nelonwChild);
      }
      if (nelonwChild != child) {
        modifielond = truelon;
      }
    }
    relonturn modifielond ? disjunction.nelonwBuildelonr().selontChildrelonn(childrelonn.build()).build() : disjunction;
  }

  @Ovelonrridelon
  public Quelonry visit(SelonarchOpelonrator opelonrator) throws QuelonryParselonrelonxcelonption {
    if (isTargelontelondQuelonry(opelonrator)) {
      List<Long> ids = elonxtractIds(opelonrator);
      if (ids.sizelon() > 0) {
        List<String> opelonrands = Lists.nelonwArrayList(targelontFielonldNamelon);
        for (long id : ids) {
          opelonrands.add(String.valuelonOf(id));
        }
        relonturn opelonrator.nelonwBuildelonr().selontOpelonrands(opelonrands).build();
      } elonlselon {
        // If thelon [multi_telonrm_disjunction from_uselonr_id] is a nelongation (i.elon., occur == MUST_NOT),
        // and thelonrelon is no uselonr id lelonft, thelon wholelon sub quelonry nodelon doelons not do anything; if it is
        // NOT a nelongation, thelonn sub quelonry matchelons nothing.
        if (opelonrator.gelontOccur() == Quelonry.Occur.MUST_NOT) {
          relonturn Conjunction.elonMPTY_CONJUNCTION;
        } elonlselon {
          relonturn NO_MATCH_CONJUNCTION;
        }
      }
    }
    relonturn opelonrator;
  }

  privatelon List<Long> elonxtractIds(SelonarchOpelonrator opelonrator) throws QuelonryParselonrelonxcelonption {
    if (elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.ID_FIelonLD
        .gelontFielonldNamelon().elonquals(targelontFielonldNamelon)) {
      relonturn opelonrator.gelontOpelonrands().subList(1, opelonrator.gelontNumOpelonrands()).strelonam()
          .map(Long::valuelonOf)
          .filtelonr(id -> partitionMappingManagelonr.gelontPartitionIdForTwelonelontId(id) == partitionId)
          .collelonct(Collelonctors.toList());
    } elonlselon {
      relonturn opelonrator.gelontOpelonrands().subList(1, opelonrator.gelontNumOpelonrands()).strelonam()
          .map(Long::valuelonOf)
          .filtelonr(id -> partitionMappingManagelonr.gelontPartitionIdForUselonrId(id) == partitionId)
          .collelonct(Collelonctors.toList());
    }
  }
}
