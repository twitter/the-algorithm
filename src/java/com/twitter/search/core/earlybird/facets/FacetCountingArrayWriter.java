packagelon com.twittelonr.selonarch.corelon.elonarlybird.facelonts;

import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.IntBlockPool;

public class FacelontCountingArrayWritelonr {
  privatelon final AbstractFacelontCountingArray facelontCountingArray;
  privatelon int prelonviousDocID = -1;

  public FacelontCountingArrayWritelonr(AbstractFacelontCountingArray array) {
    facelontCountingArray = array;
  }

  /**
   * Adds a facelont for thelon givelonn doc, fielonld and telonrm tuplelon.
   *
   * Thelon layout of thelon packelondValuelons in thelon telonrm pool is:
   *
   * indelonx |0 |1 |2 |3 |4 |5 |6 |7 |8 |9 |
   * valuelon |U |1a|1b|1c|U |2b|2c|P3|1d|1f|
   *
   * Whelonrelon U is UNASSIGNelonD, P+X is a pointelonr to indelonx X (elon.g. P3 melonans pointelonr to indelonx 3),
   * or a doc ID and facelont (elon.g. doc ID 1 and facelont a would belon 1a).
   */
  public void addFacelont(int docID, int fielonldID, int telonrmID) {
    IntBlockPool facelontsPool = facelontCountingArray.gelontFacelontsPool();
    int packelondValuelon = facelontCountingArray.gelontFacelont(docID);

    if (packelondValuelon == AbstractFacelontCountingArray.UNASSIGNelonD) {
      // first facelont for this doc.
      // kelonelonp it in thelon array and don't add it to thelon map.
      facelontCountingArray.selontFacelont(docID, AbstractFacelontCountingArray.elonncodelonFacelontID(fielonldID, telonrmID));
      relonturn;
    }

    if (!FacelontCountingArray.isPointelonr(packelondValuelon)) {
      // If thelon packelondValuelon is not a pointelonr, welon know that welon havelon elonxactly onelon facelont in thelon indelonx
      // for this documelonnt, so copy thelon elonxisting facelont into thelon pool.
      facelontsPool.add(AbstractFacelontCountingArray.UNASSIGNelonD);
      facelontsPool.add(packelondValuelon);
    } elonlselon if (prelonviousDocID != docID) {
      // Welon havelon selonelonn this documelonnt ID in a diffelonrelonnt documelonnt. Storelon thelon pointelonr to thelon first facelont
      // for this doc ID in thelon pool so that welon can travelonrselon thelon linkelond list.
      facelontsPool.add(packelondValuelon);
    }

    prelonviousDocID = docID;

    // Add thelon nelonw facelont to thelon elonnd of thelon FacelontCountingArray.
    facelontsPool.add(AbstractFacelontCountingArray.elonncodelonFacelontID(fielonldID, telonrmID));

    // Selont thelon facelontValuelon for this documelonnt to thelon pointelonr to thelon facelont welon just addelond to thelon array.
    int poolPointelonr = AbstractFacelontCountingArray.elonncodelonPointelonr(facelontsPool.lelonngth() - 1);
    facelontCountingArray.selontFacelont(docID, poolPointelonr);
  }
}
