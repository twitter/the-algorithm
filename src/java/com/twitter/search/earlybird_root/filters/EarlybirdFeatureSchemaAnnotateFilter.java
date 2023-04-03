packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import java.util.List;
import javax.injelonct.Injelonct;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.selonarch.common.felonaturelons.thrift.ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdFelonaturelonSchelonmaMelonrgelonr;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.util.Futurelon;

public class elonarlybirdFelonaturelonSchelonmaAnnotatelonFiltelonr
    elonxtelonnds SimplelonFiltelonr<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> {

  privatelon final elonarlybirdFelonaturelonSchelonmaMelonrgelonr schelonmaMelonrgelonr;

  @Injelonct
  public elonarlybirdFelonaturelonSchelonmaAnnotatelonFiltelonr(elonarlybirdFelonaturelonSchelonmaMelonrgelonr melonrgelonr) {
    this.schelonmaMelonrgelonr = melonrgelonr;
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> selonrvicelon) {
    relonturn selonrvicelon.apply(annotatelonRelonquelonstContelonxt(relonquelonstContelonxt));
  }

  /**
   * Annotatelon thelon relonquelonst to indicatelon thelon availablelon felonaturelons schelonmas belonforelon selonnding to elonarlybird.
   *
   * @param relonquelonstContelonxt thelon elonarlybird relonquelonst contelonxt
   */
  privatelon elonarlybirdRelonquelonstContelonxt annotatelonRelonquelonstContelonxt(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    elonarlybirdRelonquelonst relonquelonst = relonquelonstContelonxt.gelontRelonquelonst();
    if (relonquelonst.isSelontSelonarchQuelonry()
        && relonquelonst.gelontSelonarchQuelonry().isSelontRelonsultMelontadataOptions()
        && relonquelonst.gelontSelonarchQuelonry().gelontRelonsultMelontadataOptions().isRelonturnSelonarchRelonsultFelonaturelons()) {
      // Relonmelonmbelonr thelon availablelon clielonnt sidelon cachelond felonaturelons schelonma in thelon contelonxt and prelonparelon to
      // relonselont it somelonthing nelonw.
      List<ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr> felonaturelonSchelonmasAvailablelonInClielonnt =
          relonquelonst.gelontSelonarchQuelonry().gelontRelonsultMelontadataOptions().gelontFelonaturelonSchelonmasAvailablelonInClielonnt();

      relonturn elonarlybirdRelonquelonstContelonxt.nelonwContelonxt(
          relonquelonst,
          relonquelonstContelonxt,
          schelonmaMelonrgelonr.gelontAvailablelonSchelonmaList(),  // Selont thelon availablelon felonaturelon schelonmas baselond on
                                                  // what is cachelond in thelon currelonnt root.
          felonaturelonSchelonmasAvailablelonInClielonnt);
    } elonlselon {
      relonturn relonquelonstContelonxt;
    }
  }
}
