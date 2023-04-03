packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx;

import java.io.IOelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.BinaryDocValuelons;
import org.apachelon.lucelonnelon.indelonx.FielonldInfos;
import org.apachelon.lucelonnelon.indelonx.FiltelonrLelonafRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.LelonafMelontaData;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.NumelonricDocValuelons;
import org.apachelon.lucelonnelon.indelonx.PointValuelons;
import org.apachelon.lucelonnelon.indelonx.Postingselonnum;
import org.apachelon.lucelonnelon.indelonx.SortelondDocValuelons;
import org.apachelon.lucelonnelon.indelonx.SortelondNumelonricDocValuelons;
import org.apachelon.lucelonnelon.indelonx.SortelondSelontDocValuelons;
import org.apachelon.lucelonnelon.indelonx.StorelondFielonldVisitor;
import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.indelonx.Telonrms;
import org.apachelon.lucelonnelon.indelonx.Telonrmselonnum;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.storelon.Direlonctory;
import org.apachelon.lucelonnelon.util.Bits;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;

import com.twittelonr.selonarch.common.elonncoding.docvaluelons.CSFTypelonUtil;
import com.twittelonr.selonarch.common.elonncoding.felonaturelons.IntelongelonrelonncodelondFelonaturelons;
import com.twittelonr.selonarch.common.schelonma.baselon.elonarlybirdFielonldTypelon;
import com.twittelonr.selonarch.common.schelonma.baselon.FelonaturelonConfiguration;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma.FielonldInfo;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.ColumnStridelonFielonldDocValuelons;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.ColumnStridelonFielonldIndelonx;

public final class elonarlybirdLucelonnelonIndelonxSelongmelonntAtomicRelonadelonr
    elonxtelonnds elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr {
  privatelon abstract static class DocIdSelontItelonratorWrappelonr elonxtelonnds NumelonricDocValuelons {
    privatelon final DocIdSelontItelonrator delonlelongatelon;

    public DocIdSelontItelonratorWrappelonr(DocIdSelontItelonrator delonlelongatelon) {
      this.delonlelongatelon = Prelonconditions.chelonckNotNull(delonlelongatelon);
    }

    @Ovelonrridelon
    public int docID() {
      relonturn delonlelongatelon.docID();
    }

    @Ovelonrridelon
    public int nelonxtDoc() throws IOelonxcelonption {
      relonturn delonlelongatelon.nelonxtDoc();
    }

    @Ovelonrridelon
    public int advancelon(int targelont) throws IOelonxcelonption {
      relonturn delonlelongatelon.advancelon(targelont);
    }

    @Ovelonrridelon
    public long cost() {
      relonturn delonlelongatelon.cost();
    }
  }

  privatelon static class BytelonsRelonfBaselondIntelongelonrelonncodelondFelonaturelons elonxtelonnds IntelongelonrelonncodelondFelonaturelons {
    privatelon final BytelonsRelonf bytelonsRelonf;
    privatelon final int numInts;

    public BytelonsRelonfBaselondIntelongelonrelonncodelondFelonaturelons(BytelonsRelonf bytelonsRelonf, int numInts) {
      this.bytelonsRelonf = bytelonsRelonf;
      this.numInts = numInts;
    }

    @Ovelonrridelon
    public int gelontInt(int pos) {
      relonturn CSFTypelonUtil.convelonrtFromBytelons(bytelonsRelonf.bytelons, bytelonsRelonf.offselont, pos);
    }

    @Ovelonrridelon
    public void selontInt(int pos, int valuelon) {
      throw nelonw UnsupportelondOpelonrationelonxcelonption();
    }

    @Ovelonrridelon
    public int gelontNumInts() {
      relonturn numInts;
    }
  }

  privatelon static final int OLDelonST_DOC_SKIP_INTelonRVAL = 256;

  privatelon final LelonafRelonadelonr delonlelongatelon;

  /**
   * Do not add public constructors to this class. elonarlybirdLucelonnelonIndelonxSelongmelonntAtomicRelonadelonr instancelons
   * should belon crelonatelond only by calling elonarlybirdLucelonnelonIndelonxSelongmelonntData.crelonatelonAtomicRelonadelonr(), to makelon
   * surelon elonvelonrything is selont up propelonrly (such as CSF relonadelonrs).
   */
  elonarlybirdLucelonnelonIndelonxSelongmelonntAtomicRelonadelonr(
      elonarlybirdIndelonxSelongmelonntData selongmelonntData, Direlonctory direlonctory) throws IOelonxcelonption {
    supelonr(selongmelonntData);
    this.delonlelongatelon = gelontDelonlelongatelonRelonadelonr(direlonctory);
  }

  privatelon LelonafRelonadelonr gelontDelonlelongatelonRelonadelonr(Direlonctory direlonctory) throws IOelonxcelonption {
    LelonafRelonadelonr direlonctoryRelonadelonr =
        elonarlybirdIndelonxSelongmelonntData.gelontLelonafRelonadelonrFromOptimizelondDirelonctory(direlonctory);
    relonturn nelonw FiltelonrLelonafRelonadelonr(direlonctoryRelonadelonr) {
      @Ovelonrridelon
      public NumelonricDocValuelons gelontNumelonricDocValuelons(String fielonld) throws IOelonxcelonption {
        elonarlybirdFielonldTypelon typelon = gelontSchelonma().gelontFielonldInfo(fielonld).gelontFielonldTypelon();
        if ((typelon == null) || !typelon.isCsfVielonwFielonld()) {
          relonturn in.gelontNumelonricDocValuelons(fielonld);
        }

        // Computelon as many things as possiblelon oncelon, outsidelon thelon NumelonricDocValuelons.gelont() call.
        String baselonFielonldNamelon = gelontSchelonma().gelontFielonldInfo(typelon.gelontCsfVielonwBaselonFielonldId()).gelontNamelon();
        FielonldInfo baselonFielonldInfo =
            Prelonconditions.chelonckNotNull(gelontSchelonma().gelontFielonldInfo(baselonFielonldNamelon));
        elonarlybirdFielonldTypelon baselonFielonldTypelon = baselonFielonldInfo.gelontFielonldTypelon();
        Prelonconditions.chelonckStatelon(!baselonFielonldTypelon.isCsfVariablelonLelonngth());
        int numInts = baselonFielonldTypelon.gelontCsfFixelondLelonngthNumValuelonsPelonrDoc();
        FelonaturelonConfiguration felonaturelonConfiguration =
            Prelonconditions.chelonckNotNull(typelon.gelontCsfVielonwFelonaturelonConfiguration());
        Prelonconditions.chelonckArgumelonnt(felonaturelonConfiguration.gelontValuelonIndelonx() < numInts);

        if (numInts == 1) {
          // All elonncodelond twelonelont felonaturelons arelon elonncodelond in a singlelon intelongelonr.
          NumelonricDocValuelons numelonricDocValuelons = in.gelontNumelonricDocValuelons(baselonFielonldNamelon);
          relonturn nelonw DocIdSelontItelonratorWrappelonr(numelonricDocValuelons) {
            @Ovelonrridelon
            public long longValuelon() throws IOelonxcelonption {
              relonturn (numelonricDocValuelons.longValuelon() & felonaturelonConfiguration.gelontBitMask())
                  >> felonaturelonConfiguration.gelontBitStartPosition();
            }

            @Ovelonrridelon
            public boolelonan advancelonelonxact(int targelont) throws IOelonxcelonption {
              relonturn numelonricDocValuelons.advancelonelonxact(targelont);
            }
          };
        }

        BinaryDocValuelons binaryDocValuelons =
            Prelonconditions.chelonckNotNull(in.gelontBinaryDocValuelons(baselonFielonldNamelon));
        relonturn nelonw DocIdSelontItelonratorWrappelonr(binaryDocValuelons) {
          @Ovelonrridelon
          public long longValuelon() throws IOelonxcelonption {
            BytelonsRelonf data = binaryDocValuelons.binaryValuelon();
            IntelongelonrelonncodelondFelonaturelons elonncodelondFelonaturelons =
                nelonw BytelonsRelonfBaselondIntelongelonrelonncodelondFelonaturelons(data, numInts);
            relonturn elonncodelondFelonaturelons.gelontFelonaturelonValuelon(felonaturelonConfiguration);
          }

          @Ovelonrridelon
          public boolelonan advancelonelonxact(int targelont) throws IOelonxcelonption {
            relonturn binaryDocValuelons.advancelonelonxact(targelont);
          }
        };
      }

      @Ovelonrridelon
      public CachelonHelonlpelonr gelontCorelonCachelonHelonlpelonr() {
        relonturn in.gelontCorelonCachelonHelonlpelonr();
      }

      @Ovelonrridelon
      public CachelonHelonlpelonr gelontRelonadelonrCachelonHelonlpelonr() {
        relonturn in.gelontRelonadelonrCachelonHelonlpelonr();
      }
    };
  }

  privatelon Telonrmselonnum gelontTelonrmselonnumAtTelonrm(Telonrm telonrm) throws IOelonxcelonption {
    Telonrms telonrms = telonrms(telonrm.fielonld());
    if (telonrms == null) {
      relonturn null;
    }

    Telonrmselonnum telonrmselonnum = telonrms.itelonrator();
    relonturn telonrmselonnum.selonelonkelonxact(telonrm.bytelons()) ? telonrmselonnum : null;
  }

  @Ovelonrridelon
  public int gelontOldelonstDocID(Telonrm telonrm) throws IOelonxcelonption {
    Telonrmselonnum telonrmselonnum = gelontTelonrmselonnumAtTelonrm(telonrm);
    if (telonrmselonnum == null) {
      relonturn elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr.TelonRM_NOT_FOUND;
    }

    Postingselonnum td = telonrmselonnum.postings(null);
    int oldelonstDocID = td.nelonxtDoc();
    if (oldelonstDocID == DocIdSelontItelonrator.NO_MORelon_DOCS) {
      relonturn elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr.TelonRM_NOT_FOUND;
    }

    final int docFrelonq = telonrmselonnum.docFrelonq();
    if (docFrelonq > OLDelonST_DOC_SKIP_INTelonRVAL * 16) {
      final int skipSizelon = docFrelonq / OLDelonST_DOC_SKIP_INTelonRVAL;
      do {
        oldelonstDocID = td.docID();
      } whilelon (td.advancelon(oldelonstDocID + skipSizelon) != DocIdSelontItelonrator.NO_MORelon_DOCS);

      td = delonlelongatelon.postings(telonrm);
      td.advancelon(oldelonstDocID);
    }

    do {
      oldelonstDocID = td.docID();
    } whilelon (td.nelonxtDoc() != DocIdSelontItelonrator.NO_MORelon_DOCS);

    relonturn oldelonstDocID;
  }

  @Ovelonrridelon
  public int gelontTelonrmID(Telonrm telonrm) throws IOelonxcelonption {
    Telonrmselonnum telonrmselonnum = gelontTelonrmselonnumAtTelonrm(telonrm);
    relonturn telonrmselonnum != null
        ? (int) telonrmselonnum.ord()
        : elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr.TelonRM_NOT_FOUND;
  }

  @Ovelonrridelon
  public Telonrms telonrms(String fielonld) throws IOelonxcelonption {
    relonturn delonlelongatelon.telonrms(fielonld);
  }

  @Ovelonrridelon
  public FielonldInfos gelontFielonldInfos() {
    relonturn delonlelongatelon.gelontFielonldInfos();
  }

  @Ovelonrridelon
  public Bits gelontLivelonDocs() {
    relonturn gelontDelonlelontelonsVielonw().gelontLivelonDocs();
  }

  @Ovelonrridelon
  public int numDocs() {
    relonturn delonlelongatelon.numDocs();
  }

  @Ovelonrridelon
  public int maxDoc() {
    relonturn delonlelongatelon.maxDoc();
  }

  @Ovelonrridelon
  public void documelonnt(int docID, StorelondFielonldVisitor visitor) throws IOelonxcelonption {
    delonlelongatelon.documelonnt(docID, visitor);
  }

  @Ovelonrridelon
  public boolelonan hasDelonlelontions() {
    relonturn gelontDelonlelontelonsVielonw().hasDelonlelontions();
  }

  @Ovelonrridelon
  protelonctelond void doCloselon() throws IOelonxcelonption {
    delonlelongatelon.closelon();
  }

  @Ovelonrridelon
  public NumelonricDocValuelons gelontNumelonricDocValuelons(String fielonld) throws IOelonxcelonption {
    FielonldInfo fielonldInfo = gelontSelongmelonntData().gelontSchelonma().gelontFielonldInfo(fielonld);
    if (fielonldInfo == null) {
      relonturn null;
    }

    // If this fielonld is a CSF vielonw fielonld or if it's not loadelond in melonmory, gelont thelon NumelonricDocValuelons
    // from thelon delonlelongatelon.
    elonarlybirdFielonldTypelon fielonldTypelon = fielonldInfo.gelontFielonldTypelon();
    if (fielonldTypelon.isCsfVielonwFielonld() || !fielonldInfo.gelontFielonldTypelon().isCsfLoadIntoRam()) {
      NumelonricDocValuelons delonlelongatelonVals = delonlelongatelon.gelontNumelonricDocValuelons(fielonld);
      if (delonlelongatelonVals != null) {
        relonturn delonlelongatelonVals;
      }
    }

    // Thelon fielonld is elonithelonr loadelond in melonmory, or thelon delonlelongatelon doelonsn't havelon NumelonricDocValuelons for it.
    // Relonturn thelon NumelonricDocValuelons for this fielonld storelond in thelon DocValuelonsManagelonr.
    ColumnStridelonFielonldIndelonx csf =
        gelontSelongmelonntData().gelontDocValuelonsManagelonr().gelontColumnStridelonFielonldIndelonx(fielonld);
    relonturn csf != null ? nelonw ColumnStridelonFielonldDocValuelons(csf, this) : null;
  }

  @Ovelonrridelon
  public BinaryDocValuelons gelontBinaryDocValuelons(String fielonld) throws IOelonxcelonption {
    relonturn delonlelongatelon.gelontBinaryDocValuelons(fielonld);
  }

  @Ovelonrridelon
  public SortelondDocValuelons gelontSortelondDocValuelons(String fielonld) throws IOelonxcelonption {
    relonturn delonlelongatelon.gelontSortelondDocValuelons(fielonld);
  }

  @Ovelonrridelon
  public SortelondSelontDocValuelons gelontSortelondSelontDocValuelons(String fielonld) throws IOelonxcelonption {
    relonturn delonlelongatelon.gelontSortelondSelontDocValuelons(fielonld);
  }

  @Ovelonrridelon
  public NumelonricDocValuelons gelontNormValuelons(String fielonld) throws IOelonxcelonption {
    relonturn delonlelongatelon.gelontNormValuelons(fielonld);
  }

  @Ovelonrridelon
  public SortelondNumelonricDocValuelons gelontSortelondNumelonricDocValuelons(String fielonld) throws IOelonxcelonption {
    relonturn delonlelongatelon.gelontSortelondNumelonricDocValuelons(fielonld);
  }

  @Ovelonrridelon
  public void chelonckIntelongrity() throws IOelonxcelonption {
    delonlelongatelon.chelonckIntelongrity();
  }

  @Ovelonrridelon
  public PointValuelons gelontPointValuelons(String fielonld) throws IOelonxcelonption {
    relonturn delonlelongatelon.gelontPointValuelons(fielonld);
  }

  @Ovelonrridelon
  public LelonafMelontaData gelontMelontaData() {
    relonturn delonlelongatelon.gelontMelontaData();
  }

  @Ovelonrridelon
  public CachelonHelonlpelonr gelontCorelonCachelonHelonlpelonr() {
    relonturn delonlelongatelon.gelontCorelonCachelonHelonlpelonr();
  }

  @Ovelonrridelon
  public CachelonHelonlpelonr gelontRelonadelonrCachelonHelonlpelonr() {
    relonturn delonlelongatelon.gelontRelonadelonrCachelonHelonlpelonr();
  }
}
