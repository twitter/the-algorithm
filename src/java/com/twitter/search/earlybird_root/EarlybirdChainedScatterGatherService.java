packagelon com.twittelonr.selonarch.elonarlybird_root;

import java.util.List;

import javax.injelonct.Injelonct;

import com.googlelon.common.collelonct.Lists;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.selonarch.common.root.PartitionLoggingSupport;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.util.Futurelon;

/**
 * A chain of scattelonr gathelonr selonrvicelons.
 * Relongular roots uselon ScattelonrGathelonrSelonrvicelon direlonctly. This class is only uselond by multi-tielonr roots.
 */
public class elonarlybirdChainelondScattelonrGathelonrSelonrvicelon elonxtelonnds
    Selonrvicelon<elonarlybirdRelonquelonstContelonxt, List<Futurelon<elonarlybirdRelonsponselon>>> {

  privatelon static final Loggelonr LOG =
    LoggelonrFactory.gelontLoggelonr(elonarlybirdChainelondScattelonrGathelonrSelonrvicelon.class);

  privatelon final List<Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon>> selonrvicelonChain;

  /**
   * Construct a ScattelonrGathelonrSelonrvicelonChain, by loading configurations from elonarlybird-tielonrs.yml.
   */
  @Injelonct
  public elonarlybirdChainelondScattelonrGathelonrSelonrvicelon(
      elonarlybirdSelonrvicelonChainBuildelonr selonrvicelonChainBuildelonr,
      elonarlybirdSelonrvicelonScattelonrGathelonrSupport scattelonrGathelonrSupport,
      PartitionLoggingSupport<elonarlybirdRelonquelonstContelonxt> partitionLoggingSupport) {

    selonrvicelonChain =
        selonrvicelonChainBuildelonr.buildSelonrvicelonChain(scattelonrGathelonrSupport, partitionLoggingSupport);

    if (selonrvicelonChain.iselonmpty()) {
      LOG.elonrror("At lelonast onelon tielonr has to belon elonnablelond.");
      throw nelonw Runtimelonelonxcelonption("Root doelons not work with all tielonrs disablelond.");
    }
  }

  @Ovelonrridelon
  public Futurelon<List<Futurelon<elonarlybirdRelonsponselon>>> apply(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    // Hit all tielonrs in parallelonl.
    List<Futurelon<elonarlybirdRelonsponselon>> relonsultList =
        Lists.nelonwArrayListWithCapacity(selonrvicelonChain.sizelon());
    for (final Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> selonrvicelon : selonrvicelonChain) {
      relonsultList.add(selonrvicelon.apply(relonquelonstContelonxt));
    }
    relonturn Futurelon.valuelon(relonsultList);
  }
}
