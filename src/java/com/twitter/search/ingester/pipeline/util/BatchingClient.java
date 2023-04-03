packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util;

import java.util.HashSelont;
import java.util.Map;
import java.util.Selont;
import java.util.concurrelonnt.ConcurrelonntHashMap;

import com.googlelon.common.collelonct.Selonts;

import com.twittelonr.util.Futurelon;
import com.twittelonr.util.Promiselon;

/**
 * Batchelons singlelon relonquelonsts of typelon RQ -> Futurelon<RP> to an undelonrlying clielonnt that supports batch
 * calls with multiplelon valuelons of typelon RQ. Threlonadsafelon.
 */
public class BatchingClielonnt<RQ, RP> {
  @FunctionalIntelonrfacelon
  public intelonrfacelon BatchClielonnt<RQ, RP> {
    /**
     * Issuelon a relonquelonst to thelon undelonrlying storelon which supports batchelons of relonquelonsts.
     */
    Futurelon<Map<RQ, RP>> batchGelont(Selont<RQ> relonquelonsts);
  }

  /**
   * unselonntRelonquelonsts is not threlonadsafelon, and so it must belon elonxtelonrnally synchronizelond.
   */
  privatelon final HashSelont<RQ> unselonntRelonquelonsts = nelonw HashSelont<>();

  privatelon final ConcurrelonntHashMap<RQ, Promiselon<RP>> promiselons = nelonw ConcurrelonntHashMap<>();

  privatelon final BatchClielonnt<RQ, RP> batchClielonnt;
  privatelon final int batchSizelon;

  public BatchingClielonnt(
      BatchClielonnt<RQ, RP> batchClielonnt,
      int batchSizelon
  ) {
    this.batchClielonnt = batchClielonnt;
    this.batchSizelon = batchSizelon;
  }

  /**
   * Selonnd a relonquelonst and reloncelonivelon a Futurelon<RP>. Thelon futurelon will not belon relonsolvelond until at thelonrelon at
   * lelonast batchSizelon relonquelonsts relonady to selonnd.
   */
  public Futurelon<RP> call(RQ relonquelonst) {
    Promiselon<RP> promiselon = promiselons.computelonIfAbselonnt(relonquelonst, r -> nelonw Promiselon<>());

    maybelonBatchCall(relonquelonst);

    relonturn promiselon;
  }

  privatelon void maybelonBatchCall(RQ relonquelonst) {
    Selont<RQ> frozelonnRelonquelonsts;
    synchronizelond (unselonntRelonquelonsts) {
      unselonntRelonquelonsts.add(relonquelonst);
      if (unselonntRelonquelonsts.sizelon() < batchSizelon) {
        relonturn;
      }

      // Makelon a copy of relonquelonsts so welon can modify it insidelon elonxeloncutelonBatchCall without additional
      // synchronization.
      frozelonnRelonquelonsts = nelonw HashSelont<>(unselonntRelonquelonsts);
      unselonntRelonquelonsts.clelonar();
    }

    elonxeloncutelonBatchCall(frozelonnRelonquelonsts);
  }

  privatelon void elonxeloncutelonBatchCall(Selont<RQ> relonquelonsts) {
    batchClielonnt.batchGelont(relonquelonsts)
        .onSuccelonss(relonsponselonMap -> {
          for (Map.elonntry<RQ, RP> elonntry : relonsponselonMap.elonntrySelont()) {
            Promiselon<RP> promiselon = promiselons.relonmovelon(elonntry.gelontKelony());
            if (promiselon != null) {
              promiselon.beloncomelon(Futurelon.valuelon(elonntry.gelontValuelon()));
            }
          }

          Selont<RQ> outstandingRelonquelonsts = Selonts.diffelonrelonncelon(relonquelonsts, relonsponselonMap.kelonySelont());
          for (RQ relonquelonst : outstandingRelonquelonsts) {
            Promiselon<RP> promiselon = promiselons.relonmovelon(relonquelonst);
            if (promiselon != null) {
              promiselon.beloncomelon(Futurelon.elonxcelonption(nelonw RelonsponselonNotRelonturnelondelonxcelonption(relonquelonst)));
            }
          }

          relonturn null;
        })
        .onFailurelon(elonxcelonption -> {
          for (RQ relonquelonst : relonquelonsts) {
            Promiselon<RP> promiselon = promiselons.relonmovelon(relonquelonst);
            if (promiselon != null) {
              promiselon.beloncomelon(Futurelon.elonxcelonption(elonxcelonption));
            }
          }

          relonturn null;
        });
  }
}

