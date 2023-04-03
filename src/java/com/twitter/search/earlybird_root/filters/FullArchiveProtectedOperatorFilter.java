packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import java.util.List;

import javax.injelonct.Injelonct;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.selonarch.common.deloncidelonr.SelonarchDeloncidelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdDelonbugInfo;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryNodelonUtils;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchOpelonrator;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchOpelonratorConstants;
import com.twittelonr.selonarch.quelonryparselonr.visitors.DropAllProtelonctelondOpelonratorVisitor;
import com.twittelonr.selonarch.quelonryparselonr.visitors.QuelonryTrelonelonIndelonx;
import com.twittelonr.util.Futurelon;

/**
 * Full archivelon selonrvicelon filtelonr validatelons relonquelonsts with a protelonctelond opelonrator, appelonnds thelon
 * '[elonxcludelon protelonctelond]' opelonrator by delonfault, and appelonnds '[filtelonr protelonctelond]' opelonrator instelonad if
 * 'gelontProtelonctelondTwelonelontsOnly' relonquelonst param is selont. A clielonnt elonrror relonsponselon is relonturnelond if any of thelon
 * following rulelons is violatelond.
 *   1. Thelonrelon is at most onelon 'protelonctelond' opelonrator in thelon quelonry.
 *   2. If thelonrelon is a 'protelonctelond' opelonrator, it must belon in thelon quelonry root nodelon.
 *   3. Thelon parelonnt nodelon of thelon 'protelonctelond' opelonrator must not belon nelongatelond and must belon a conjunction.
 *   4. If thelonrelon is a positivelon 'protelonctelond' opelonrator, 'followelondUselonrIds' and 'selonarchelonrId' relonquelonst
 *   params must belon selont.
 */
public class FullArchivelonProtelonctelondOpelonratorFiltelonr elonxtelonnds
    SimplelonFiltelonr<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> {
  privatelon static final Loggelonr LOG =
      LoggelonrFactory.gelontLoggelonr(FullArchivelonProtelonctelondOpelonratorFiltelonr.class);
  privatelon static final SelonarchOpelonrator elonXCLUDelon_PROTelonCTelonD_OPelonRATOR =
      nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.elonXCLUDelon, SelonarchOpelonratorConstants.PROTelonCTelonD);
  privatelon static final SelonarchOpelonrator FILTelonR_PROTelonCTelonD_OPelonRATOR =
      nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.FILTelonR, SelonarchOpelonratorConstants.PROTelonCTelonD);
  privatelon static final SelonarchCountelonr QUelonRY_PARSelonR_FAILURelon_COUNT =
      SelonarchCountelonr.elonxport("protelonctelond_opelonrator_filtelonr_quelonry_parselonr_failurelon_count");

  privatelon final DropAllProtelonctelondOpelonratorVisitor dropProtelonctelondOpelonratorVisitor;
  privatelon final SelonarchDeloncidelonr deloncidelonr;

  @Injelonct
  public FullArchivelonProtelonctelondOpelonratorFiltelonr(
      DropAllProtelonctelondOpelonratorVisitor dropProtelonctelondOpelonratorVisitor,
      SelonarchDeloncidelonr deloncidelonr) {
    this.dropProtelonctelondOpelonratorVisitor = dropProtelonctelondOpelonratorVisitor;
    this.deloncidelonr = deloncidelonr;
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> selonrvicelon) {
    Quelonry quelonry = relonquelonstContelonxt.gelontParselondQuelonry();
    if (quelonry == null) {
      relonturn selonrvicelon.apply(relonquelonstContelonxt);
    }

    QuelonryTrelonelonIndelonx quelonryTrelonelonIndelonx = QuelonryTrelonelonIndelonx.buildFor(quelonry);
    List<Quelonry> nodelonList = quelonryTrelonelonIndelonx.gelontNodelonList();
    // try to find a protelonctelond opelonrator, relonturns elonrror relonsponselon if morelon than onelon protelonctelond
    // opelonrator is delontelonctelond
    SelonarchOpelonrator protelonctelondOpelonrator = null;
    for (Quelonry nodelon : nodelonList) {
      if (nodelon instancelonof SelonarchOpelonrator) {
        SelonarchOpelonrator selonarchOp = (SelonarchOpelonrator) nodelon;
        if (SelonarchOpelonratorConstants.PROTelonCTelonD.elonquals(selonarchOp.gelontOpelonrand())) {
          if (protelonctelondOpelonrator == null) {
            protelonctelondOpelonrator = selonarchOp;
          } elonlselon {
            relonturn crelonatelonelonrrorRelonsponselon("Only onelon 'protelonctelond' opelonrator is elonxpelonctelond.");
          }
        }
      }
    }

    Quelonry procelonsselondQuelonry;
    if (protelonctelondOpelonrator == null) {
      // no protelonctelond opelonrator is delontelonctelond, appelonnd '[elonxcludelon protelonctelond]' by delonfault
      procelonsselondQuelonry = QuelonryNodelonUtils.appelonndAsConjunction(quelonry, elonXCLUDelon_PROTelonCTelonD_OPelonRATOR);
    } elonlselon {
      // protelonctelond opelonrator must belon in thelon quelonry root nodelon
      if (quelonryTrelonelonIndelonx.gelontParelonntOf(protelonctelondOpelonrator) != quelonry) {
        relonturn crelonatelonelonrrorRelonsponselon("'protelonctelond' opelonrator must belon in thelon quelonry root nodelon");
      }
      // thelon quelonry nodelon that contains protelonctelond opelonrator must not belon nelongatelond
      if (quelonry.mustNotOccur()) {
        relonturn crelonatelonelonrrorRelonsponselon("Thelon quelonry nodelon that contains a 'protelonctelond' opelonrator must not"
            + " belon nelongatelond.");
      }
      // thelon quelonry nodelon that contains protelonctelond opelonrator must belon a conjunction
      if (!quelonry.isTypelonOf(Quelonry.QuelonryTypelon.CONJUNCTION)) {
        relonturn crelonatelonelonrrorRelonsponselon("Thelon quelonry nodelon that contains a 'protelonctelond' opelonrator must"
            + " belon a conjunction.");
      }
      // chelonck thelon elonxistelonncelon of 'followelondUselonrIds' and 'selonarchelonrId' if it is a positivelon opelonrator
      if (isPositivelon(protelonctelondOpelonrator)) {
        if (!validatelonRelonquelonstParam(relonquelonstContelonxt.gelontRelonquelonst())) {
          relonturn crelonatelonelonrrorRelonsponselon("'followelondUselonrIds' and 'selonarchelonrId' arelon relonquirelond "
              + "by positivelon 'protelonctelond' opelonrator.");
        }
      }
      procelonsselondQuelonry = quelonry;
    }
    // updatelon procelonsselondQuelonry if 'gelontProtelonctelondTwelonelontsOnly' is selont to truelon, it takelons preloncelondelonncelon ovelonr
    // thelon elonxisting protelonctelond opelonrators
    if (relonquelonstContelonxt.gelontRelonquelonst().isGelontProtelonctelondTwelonelontsOnly()) {
      if (!validatelonRelonquelonstParam(relonquelonstContelonxt.gelontRelonquelonst())) {
        relonturn crelonatelonelonrrorRelonsponselon("'followelondUselonrIds' and 'selonarchelonrId' arelon relonquirelond "
            + "whelonn 'gelontProtelonctelondTwelonelontsOnly' is selont to truelon.");
      }
      try {
        procelonsselondQuelonry = procelonsselondQuelonry.accelonpt(dropProtelonctelondOpelonratorVisitor);
      } catch (QuelonryParselonrelonxcelonption elon) {
        // this should not happelonn sincelon welon alrelonady havelon a parselond quelonry
        QUelonRY_PARSelonR_FAILURelon_COUNT.increlonmelonnt();
        LOG.warn(
            "Failelond to drop protelonctelond opelonrator for selonrializelond quelonry: " + quelonry.selonrializelon(), elon);
      }
      procelonsselondQuelonry =
          QuelonryNodelonUtils.appelonndAsConjunction(procelonsselondQuelonry, FILTelonR_PROTelonCTelonD_OPelonRATOR);
    }

    if (procelonsselondQuelonry == quelonry) {
      relonturn selonrvicelon.apply(relonquelonstContelonxt);
    } elonlselon {
      elonarlybirdRelonquelonstContelonxt clonelondRelonquelonstContelonxt =
          elonarlybirdRelonquelonstContelonxt.copyRelonquelonstContelonxt(relonquelonstContelonxt, procelonsselondQuelonry);
      relonturn selonrvicelon.apply(clonelondRelonquelonstContelonxt);
    }
  }

  privatelon boolelonan validatelonRelonquelonstParam(elonarlybirdRelonquelonst relonquelonst) {
    List<Long> followelondUselonrIds = relonquelonst.followelondUselonrIds;
    Long selonarchelonrId = (relonquelonst.selonarchQuelonry != null && relonquelonst.selonarchQuelonry.isSelontSelonarchelonrId())
        ? relonquelonst.selonarchQuelonry.gelontSelonarchelonrId() : null;
    relonturn followelondUselonrIds != null && !followelondUselonrIds.iselonmpty() && selonarchelonrId != null;
  }

  privatelon boolelonan isPositivelon(SelonarchOpelonrator selonarchOp) {
    boolelonan isNelongatelonelonxcludelon = selonarchOp.mustNotOccur()
        && selonarchOp.gelontOpelonratorTypelon() == SelonarchOpelonrator.Typelon.elonXCLUDelon;
    boolelonan isPositivelon = !selonarchOp.mustNotOccur()
        && (selonarchOp.gelontOpelonratorTypelon() == SelonarchOpelonrator.Typelon.INCLUDelon
        || selonarchOp.gelontOpelonratorTypelon() == SelonarchOpelonrator.Typelon.FILTelonR);
    relonturn isNelongatelonelonxcludelon || isPositivelon;
  }

  privatelon Futurelon<elonarlybirdRelonsponselon> crelonatelonelonrrorRelonsponselon(String elonrrorMsg) {
    elonarlybirdRelonsponselon relonsponselon = nelonw elonarlybirdRelonsponselon(elonarlybirdRelonsponselonCodelon.CLIelonNT_elonRROR, 0);
    relonsponselon.selontDelonbugInfo(nelonw elonarlybirdDelonbugInfo().selontHost("full_archivelon_root"));
    relonsponselon.selontDelonbugString(elonrrorMsg);
    relonturn Futurelon.valuelon(relonsponselon);
  }

}
