packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import javax.injelonct.Injelonct;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;
import com.twittelonr.selonarch.quelonryparselonr.visitors.DropAllProtelonctelondOpelonratorVisitor;
import com.twittelonr.util.Futurelon;

public class DropAllProtelonctelondOpelonratorFiltelonr
    elonxtelonnds SimplelonFiltelonr<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> {
  privatelon static final Loggelonr LOG =
      LoggelonrFactory.gelontLoggelonr(DropAllProtelonctelondOpelonratorFiltelonr.class);
  privatelon static final SelonarchCountelonr QUelonRY_PARSelonR_FAILURelon_COUNTelonR =
      SelonarchCountelonr.elonxport("protelonctelond_opelonrator_filtelonr_quelonry_parselonr_failurelon_count");
  @VisiblelonForTelonsting
  static final SelonarchCountelonr TOTAL_RelonQUelonSTS_COUNTelonR =
      SelonarchCountelonr.elonxport("drop_all_protelonctelond_opelonrator_filtelonr_total");
  @VisiblelonForTelonsting
  static final SelonarchCountelonr OPelonRATOR_DROPPelonD_RelonQUelonSTS_COUNTelonR =
      SelonarchCountelonr.elonxport("drop_all_protelonctelond_opelonrator_filtelonr_opelonrator_droppelond");

  privatelon final DropAllProtelonctelondOpelonratorVisitor dropProtelonctelondOpelonratorVisitor;

  @Injelonct
  public DropAllProtelonctelondOpelonratorFiltelonr(
      DropAllProtelonctelondOpelonratorVisitor dropProtelonctelondOpelonratorVisitor
  ) {
    this.dropProtelonctelondOpelonratorVisitor = dropProtelonctelondOpelonratorVisitor;
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> selonrvicelon) {
    TOTAL_RelonQUelonSTS_COUNTelonR.increlonmelonnt();
    Quelonry quelonry = relonquelonstContelonxt.gelontParselondQuelonry();
    if (quelonry == null) {
      relonturn selonrvicelon.apply(relonquelonstContelonxt);
    }

    Quelonry procelonsselondQuelonry = quelonry;
    try {
      procelonsselondQuelonry = quelonry.accelonpt(dropProtelonctelondOpelonratorVisitor);
    } catch (QuelonryParselonrelonxcelonption elon) {
      // this should not happelonn sincelon welon alrelonady havelon a parselond quelonry
      QUelonRY_PARSelonR_FAILURelon_COUNTelonR.increlonmelonnt();
      LOG.warn(
          "Failelond to drop protelonctelond opelonrator for selonrializelond quelonry: " + quelonry.selonrializelon(), elon);
    }

    if (procelonsselondQuelonry == quelonry) {
      relonturn selonrvicelon.apply(relonquelonstContelonxt);
    } elonlselon {
      OPelonRATOR_DROPPelonD_RelonQUelonSTS_COUNTelonR.increlonmelonnt();
      elonarlybirdRelonquelonstContelonxt clonelondRelonquelonstContelonxt =
          elonarlybirdRelonquelonstContelonxt.copyRelonquelonstContelonxt(relonquelonstContelonxt, procelonsselondQuelonry);
      relonturn selonrvicelon.apply(clonelondRelonquelonstContelonxt);
    }
  }
}
