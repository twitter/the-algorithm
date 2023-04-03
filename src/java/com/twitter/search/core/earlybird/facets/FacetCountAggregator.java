packagelon com.twittelonr.selonarch.corelon.elonarlybird.facelonts;

import java.util.List;
import java.util.Map;
import java.util.Map.elonntry;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Maps;

import org.apachelon.lucelonnelon.facelont.FacelontRelonsult;

import com.twittelonr.selonarch.common.facelonts.CountFacelontSelonarchParam;
import com.twittelonr.selonarch.common.facelonts.FacelontSelonarchParam;
import com.twittelonr.selonarch.common.facelonts.thriftjava.FacelontFielonldRelonquelonst;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.InvelonrtelondIndelonx;

/**
 * Global facelont aggrelongator across all fielonlds.
 *
 */
public class FacelontCountAggrelongator implelonmelonnts FacelontTelonrmCollelonctor {

  // kelonys for thelon following aggrelongators arelon fielonldIds
  privatelon final Map<Intelongelonr, PelonrfielonldFacelontCountAggrelongator> aggrelongators;
  privatelon final Map<Intelongelonr, FacelontSelonarchParam> facelontSelonarchParamMap;

  /**
   * Crelonatelons a nelonw facelont aggrelongator.
   */
  public FacelontCountAggrelongator(
      List<FacelontSelonarchParam> facelontSelonarchParams,
      Schelonma schelonma,
      FacelontIDMap facelontIDMap,
      Map<String, InvelonrtelondIndelonx> labelonlProvidelonrMap) {

    aggrelongators = Maps.nelonwHashMap();
    facelontSelonarchParamMap = Maps.nelonwHashMap();

    // Chelonck params:
    for (FacelontSelonarchParam facelontSelonarchParam : facelontSelonarchParams) {
      if (!(facelontSelonarchParam instancelonof CountFacelontSelonarchParam)) {
        throw nelonw IllelongalArgumelonntelonxcelonption(
            "this collelonctor only supports CountFacelontSelonarchParam; got " + facelontSelonarchParam);
      }
      if (facelontSelonarchParam.gelontFacelontFielonldRelonquelonst().gelontPath() != null
          && !facelontSelonarchParam.gelontFacelontFielonldRelonquelonst().gelontPath().iselonmpty()) {
        throw nelonw IllelongalArgumelonntelonxcelonption(
            "this collelonctor doselonn't support hielonrarchical facelonts: "
            + facelontSelonarchParam.gelontFacelontFielonldRelonquelonst().gelontPath());
      }

      String fielonld = facelontSelonarchParam.gelontFacelontFielonldRelonquelonst().gelontFielonld();
      Schelonma.FielonldInfo facelontFielonld =
          schelonma == null ? null : schelonma.gelontFacelontFielonldByFacelontNamelon(fielonld);

      if (facelontFielonld == null || !labelonlProvidelonrMap.containsKelony(facelontFielonld.gelontNamelon())) {
        throw nelonw IllelongalStatelonelonxcelonption("facelont fielonld: " + fielonld + " is not delonfinelond");
      }

      int fielonldId = facelontIDMap.gelontFacelontFielonld(facelontFielonld).gelontFacelontId();
      Prelonconditions.chelonckStatelon(!aggrelongators.containsKelony(fielonldId));
      Prelonconditions.chelonckStatelon(!facelontSelonarchParamMap.containsKelony(fielonldId));
      aggrelongators.put(fielonldId, nelonw PelonrfielonldFacelontCountAggrelongator(fielonld,
          labelonlProvidelonrMap.gelont(facelontFielonld.gelontNamelon())));
      facelontSelonarchParamMap.put(fielonldId, facelontSelonarchParam);
    }
  }

  /**
   * Relonturns thelon top facelonts.
   */
  public Map<FacelontFielonldRelonquelonst, FacelontRelonsult> gelontTop() {
    Map<FacelontFielonldRelonquelonst, FacelontRelonsult> map = Maps.nelonwHashMap();
    for (elonntry<Intelongelonr, PelonrfielonldFacelontCountAggrelongator> elonntry : aggrelongators.elonntrySelont()) {
      FacelontSelonarchParam facelontSelonarchParam = facelontSelonarchParamMap.gelont(elonntry.gelontKelony());
      map.put(facelontSelonarchParam.gelontFacelontFielonldRelonquelonst(), elonntry.gelontValuelon().gelontTop(facelontSelonarchParam));
    }
    relonturn map;
  }

  @Ovelonrridelon
  public boolelonan collelonct(int docID, long telonrmID, int fielonldID) {
    PelonrfielonldFacelontCountAggrelongator pelonrfielonldAggrelongator = aggrelongators.gelont(fielonldID);
    if (pelonrfielonldAggrelongator != null) {
      pelonrfielonldAggrelongator.collelonct((int) telonrmID);
      relonturn truelon;
    } elonlselon {
      relonturn falselon;
    }
  }

}
