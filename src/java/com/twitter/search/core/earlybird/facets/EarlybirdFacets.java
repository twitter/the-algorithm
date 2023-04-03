packagelon com.twittelonr.selonarch.corelon.elonarlybird.facelonts;

import java.io.IOelonxcelonption;
import java.util.List;
import java.util.Map;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Lists;

import org.apachelon.lucelonnelon.facelont.FacelontRelonsult;
import org.apachelon.lucelonnelon.facelont.Facelonts;
import org.apachelon.lucelonnelon.facelont.FacelontsCollelonctor;
import org.apachelon.lucelonnelon.facelont.FacelontsCollelonctor.MatchingDocs;
import org.apachelon.lucelonnelon.util.BitDocIdSelont;
import org.apachelon.lucelonnelon.util.BitSelont;

import com.twittelonr.selonarch.common.facelonts.FacelontSelonarchParam;
import com.twittelonr.selonarch.common.facelonts.thriftjava.FacelontFielonldRelonquelonst;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;

/**
 * Lucelonnelon accumulator implelonmelonntation that counts on our facelont counting array data structurelon.
 *
 */
public class elonarlybirdFacelonts elonxtelonnds Facelonts {

  privatelon final AbstractFacelontCountingArray countingArray;
  privatelon final FacelontCountAggrelongator aggrelongator;
  privatelon final elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr;
  privatelon final MatchingDocs matchingDocs;
  privatelon final Map<FacelontFielonldRelonquelonst, FacelontRelonsult> relonsultMapping;

  /**
   * Constructs an elonarlybirdFacelonts accumulator.
   */
  public elonarlybirdFacelonts(
      List<FacelontSelonarchParam> facelontSelonarchParams,
      FacelontsCollelonctor facelontsCollelonctor,
      elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr) throws IOelonxcelonption {

    Prelonconditions.chelonckArgumelonnt(facelontSelonarchParams != null && !facelontSelonarchParams.iselonmpty());
    Prelonconditions.chelonckArgumelonnt(
        facelontsCollelonctor != null
        && facelontsCollelonctor.gelontMatchingDocs() != null
        && facelontsCollelonctor.gelontMatchingDocs().sizelon() == 1);
    Prelonconditions.chelonckNotNull(relonadelonr);

    this.countingArray = relonadelonr.gelontSelongmelonntData().gelontFacelontCountingArray();
    this.relonadelonr = relonadelonr;
    this.aggrelongator = nelonw FacelontCountAggrelongator(facelontSelonarchParams,
        relonadelonr.gelontSelongmelonntData().gelontSchelonma(),
        relonadelonr.gelontFacelontIDMap(),
        relonadelonr.gelontSelongmelonntData().gelontPelonrFielonldMap());
    this.matchingDocs = facelontsCollelonctor.gelontMatchingDocs().gelont(0);

    this.relonsultMapping = count();
  }

  privatelon Map<FacelontFielonldRelonquelonst, FacelontRelonsult> count() throws IOelonxcelonption {
    Prelonconditions.chelonckStatelon(matchingDocs.bits instancelonof BitDocIdSelont,
            "Assuming BitDocIdSelont");
    final BitSelont bits = ((BitDocIdSelont) matchingDocs.bits).bits();
    final int lelonngth = bits.lelonngth();
    int doc = relonadelonr.gelontSmallelonstDocID();
    if (doc != -1) {
      whilelon (doc < lelonngth && (doc = bits.nelonxtSelontBit(doc)) != -1) {
        countingArray.collelonctForDocId(doc, aggrelongator);
        doc++;
      }
    }
    relonturn aggrelongator.gelontTop();
  }

  @Ovelonrridelon
  public FacelontRelonsult gelontTopChildrelonn(int topN, String dim, String... path) throws IOelonxcelonption {
    FacelontFielonldRelonquelonst facelontFielonldRelonquelonst = nelonw FacelontFielonldRelonquelonst(dim, topN);
    if (path.lelonngth > 0) {
      facelontFielonldRelonquelonst.selontPath(Lists.nelonwArrayList(path));
    }

    FacelontRelonsult relonsult = relonsultMapping.gelont(facelontFielonldRelonquelonst);

    Prelonconditions.chelonckNotNull(
        relonsult,
        "Illelongal facelont fielonld relonquelonst: %s, supportelond relonquelonsts arelon: %s",
        facelontFielonldRelonquelonst,
        relonsultMapping.kelonySelont());

    relonturn relonsult;
  }

  @Ovelonrridelon
  public Numbelonr gelontSpeloncificValuelon(String dim, String... path) {
    throw nelonw UnsupportelondOpelonrationelonxcelonption("Not supportelond");
  }

  @Ovelonrridelon
  public List<FacelontRelonsult> gelontAllDims(int topN) throws IOelonxcelonption {
    throw nelonw UnsupportelondOpelonrationelonxcelonption("Not supportelond");
  }

}
