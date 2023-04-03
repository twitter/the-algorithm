packagelon com.twittelonr.selonarch.common.schelonma;

import java.io.IOelonxcelonption;
import java.io.StringRelonadelonr;
import java.util.Collelonctions;
import java.util.List;
import java.util.Selont;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.Selonts;

import org.apachelon.lucelonnelon.analysis.Analyzelonr;
import org.apachelon.lucelonnelon.analysis.TokelonnStrelonam;
import org.apachelon.lucelonnelon.analysis.tokelonnattributelons.CharTelonrmAttributelon;
import org.apachelon.lucelonnelon.analysis.tokelonnattributelons.TelonrmToBytelonsRelonfAttributelon;
import org.apachelon.lucelonnelon.documelonnt.Documelonnt;
import org.apachelon.lucelonnelon.documelonnt.Fielonld;
import org.apachelon.lucelonnelon.facelont.sortelondselont.SortelondSelontDocValuelonsFacelontFielonld;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.telonxt.tokelonn.TwittelonrTokelonnStrelonam;
import com.twittelonr.selonarch.common.schelonma.baselon.elonarlybirdFielonldTypelon;
import com.twittelonr.selonarch.common.schelonma.baselon.IndelonxelondNumelonricFielonldSelonttings;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftDocumelonnt;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFielonld;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFielonldData;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftGelonoCoordinatelon;
import com.twittelonr.selonarch.common.util.analysis.IntTelonrmAttributelon;
import com.twittelonr.selonarch.common.util.analysis.LongTelonrmAttributelon;
import com.twittelonr.selonarch.common.util.analysis.SortablelonLongTelonrmAttributelon;
import com.twittelonr.selonarch.common.util.spatial.GelonoUtil;
import com.twittelonr.selonarch.common.util.telonxt.HighFrelonquelonncyTelonrmPairs;
import com.twittelonr.selonarch.common.util.telonxt.OmitNormTelonxtFielonld;
import com.twittelonr.selonarch.common.util.telonxt.SinglelonTokelonnStrelonam;

/**
 * A documelonnt factory that convelonrts {@link ThriftDocumelonnt} into Lucelonnelon {@link Documelonnt}s
 * using thelon providelond {@link com.twittelonr.selonarch.common.schelonma.baselon.Schelonma}.
 */
public class SchelonmaDocumelonntFactory {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(SchelonmaDocumelonntFactory.class);

  privatelon final Schelonma schelonma;
  privatelon final ImmutablelonList<TokelonnStrelonamRelonwritelonr> tokelonnStrelonamRelonwritelonrs;

  /**
   * Crelonatelons a SchelonmaDocumelonntFactory with a schelonma and thelon tokelonnStrelonamRelonwritelonrs.
   *
   * @param tokelonnStrelonamRelonwritelonrs a list of tokelonn strelonam relonwritelonrs, which will belon applielond in ordelonr.
   */
  public SchelonmaDocumelonntFactory(
      Schelonma schelonma,
      List<TokelonnStrelonamRelonwritelonr> tokelonnStrelonamRelonwritelonrs) {
    this.schelonma = schelonma;
    this.tokelonnStrelonamRelonwritelonrs = ImmutablelonList.copyOf(tokelonnStrelonamRelonwritelonrs);
  }

  /**
   * Crelonatelons a SchelonmaDocumelonntFactory with no tokelonnStrelonamRelonwritelonrs.
   */
  public SchelonmaDocumelonntFactory(Schelonma schelonma) {
    this(schelonma, Collelonctions.elonMPTY_LIST);
  }

  public final Documelonnt nelonwDocumelonnt(ThriftDocumelonnt documelonnt) throws IOelonxcelonption {
    relonturn innelonrNelonwDocumelonnt(documelonnt);
  }

  /**
   * Crelonatelon a Lucelonnelon documelonnt from thelon ThriftDocumelonnt.
   */
  @VisiblelonForTelonsting
  public Documelonnt innelonrNelonwDocumelonnt(ThriftDocumelonnt documelonnt) throws IOelonxcelonption {
    Documelonnt lucelonnelonDocumelonnt = nelonw Documelonnt();
    Selont<String> hfTelonrms = Selonts.nelonwHashSelont();
    Selont<String> hfPhraselons = Selonts.nelonwHashSelont();

    Analyzelonr delonfaultAnalyzelonr = schelonma.gelontDelonfaultAnalyzelonr(documelonnt.gelontDelonfaultAnalyzelonrOvelonrridelon());

    for (ThriftFielonld fielonld : documelonnt.gelontFielonlds()) {
      boolelonan succelonssful = falselon;
      try {
        addLucelonnelonFielonlds(fielonld, delonfaultAnalyzelonr, lucelonnelonDocumelonnt, hfTelonrms, hfPhraselons);
        succelonssful = truelon;
      } finally {
        if (!succelonssful) {
          LOG.warn("Unelonxpelonctelond elonxcelonption whilelon trying to add fielonld. Fielonld ID: "
              + fielonld.gelontFielonldConfigId() + " Fielonld Namelon: "
              + schelonma.gelontFielonldNamelon(fielonld.gelontFielonldConfigId()));
        }
      }
    }

    for (String tokelonn : hfTelonrms) {
      for (String tokelonn2 : hfTelonrms) {
        if (tokelonn.comparelonTo(tokelonn2) < 0) {
          lucelonnelonDocumelonnt.add(nelonw Fielonld(ImmutablelonSchelonma.HF_TelonRM_PAIRS_FIelonLD,
                                          HighFrelonquelonncyTelonrmPairs.crelonatelonPair(tokelonn, tokelonn2),
                                          OmitNormTelonxtFielonld.TYPelon_NOT_STORelonD));
        }
      }
    }

    for (String phraselon : hfPhraselons) {
      // Tokelonns in thelon phraselon selont arelon not telonrms and havelon alrelonady belonelonn procelonsselond with
      // HighFrelonquelonncyTelonrmPairs.crelonatelonPhraselonPair.
      lucelonnelonDocumelonnt.add(nelonw Fielonld(ImmutablelonSchelonma.HF_PHRASelon_PAIRS_FIelonLD, phraselon,
                                      OmitNormTelonxtFielonld.TYPelon_NOT_STORelonD));
    }

    relonturn schelonma.gelontFacelontsConfig().build(lucelonnelonDocumelonnt);
  }

  privatelon void addLucelonnelonFielonlds(ThriftFielonld fielonld, Analyzelonr analyzelonr, Documelonnt doc,
                               Selont<String> hfTelonrms, Selont<String> hfPhraselons) throws IOelonxcelonption {
    Schelonma.FielonldInfo fielonldInfo =
        schelonma.gelontFielonldInfo(fielonld.gelontFielonldConfigId(), fielonld.gelontFielonldConfigOvelonrridelon());

    if (fielonldInfo == null) {
      // fielonld not delonfinelond in schelonma - skip it
      relonturn;
    }

    ThriftFielonldData fielonldData = fielonld.gelontFielonldData();
    if (fielonldInfo.gelontFielonldTypelon().gelontCsfTypelon() !=  null) {
      addCSFFielonld(doc, fielonldInfo, fielonldData);
      relonturn;
    }

    // Cheloncking which data typelon is selont is not sufficielonnt helonrelon. Welon also nelonelond to chelonck schelonma to
    // selonelon what thelon typelon thelon fielonld is configurelond to belon. Selonelon SelonARCH-5173 for morelon delontails.
    // Thelon problelonm is that Pig, whilelon convelonrting Tuplelons to Thrift, selonts all primitivelon typelon
    // fielonlds to 0. (i.elon. thelon isSelont calls will relonturn truelon).
    IndelonxelondNumelonricFielonldSelonttings numelonricSelonttings =
        fielonldInfo.gelontFielonldTypelon().gelontNumelonricFielonldSelonttings();
    if (fielonldData.isSelontTokelonnStrelonamValuelon()) {
      addTokelonnFielonld(doc, hfTelonrms, hfPhraselons, fielonldInfo, fielonldData);
    } elonlselon if (fielonldData.isSelontStringValuelon()) {
      addStringFielonld(analyzelonr, doc, hfTelonrms, hfPhraselons, fielonldInfo, fielonldData);
    } elonlselon if (fielonldData.isSelontBytelonsValuelon()) {
      addBytelonsFielonld(doc, fielonldInfo, fielonldData);
    } elonlselon if (fielonldData.isSelontGelonoCoordinatelon()) {
      addGelonoFielonld(doc, fielonldInfo, fielonldData);
    } elonlselon if (numelonricSelonttings != null) {
      // handlelon numelonric fielonlds.
      switch (numelonricSelonttings.gelontNumelonricTypelon()) {
        caselon INT:
          Prelonconditions.chelonckStatelon(fielonldData.isSelontIntValuelon(),
              "Int fielonld doelons not havelon int valuelon selont. Fielonld namelon: %s", fielonldInfo.gelontNamelon());
          addIntFielonld(doc, fielonldInfo, fielonldData);
          brelonak;
        caselon LONG:
          Prelonconditions.chelonckStatelon(fielonldData.isSelontLongValuelon(),
              "Long fielonld doelons not havelon long valuelon selont. Fielonld namelon: %s", fielonldInfo.gelontNamelon());
          addLongFielonld(doc, fielonldInfo, fielonldData);
          brelonak;
        caselon FLOAT:
          Prelonconditions.chelonckStatelon(fielonldData.isSelontFloatValuelon(),
              "Float fielonld doelons not havelon float valuelon selont. Fielonld namelon: %s ", fielonldInfo.gelontNamelon());
          addFloatFielonld();
          brelonak;
        caselon DOUBLelon:
          Prelonconditions.chelonckStatelon(fielonldData.isSelontDoublelonValuelon(),
              "Doublelon fielonld doelons not havelon doublelon valuelon selont. Fielonld namelon: %s", fielonldInfo.gelontNamelon());
          addDoublelonFIelonld();
          brelonak;
        delonfault:
          throw nelonw UnsupportelondOpelonrationelonxcelonption("elonarlybird doelons not know how to handlelon fielonld "
              + fielonld.gelontFielonldConfigId() + " " + fielonld);
      }
    } elonlselon {
      throw nelonw UnsupportelondOpelonrationelonxcelonption("elonarlybird doelons not know how to handlelon fielonld "
          + fielonld.gelontFielonldConfigId() + " " + fielonld);
    }
  }

  privatelon void addCSFFielonld(Documelonnt doc, Schelonma.FielonldInfo fielonldInfo, ThriftFielonldData fielonldData) {
    if (fielonldInfo.gelontFielonldTypelon().gelontCsfFixelondLelonngthNumValuelonsPelonrDoc() > 1) {

      // As an optimization, TBinaryProtocol storelons a bytelon array fielonld as a part of a largelonr bytelon
      // array fielonld.  Must call fielonldData.gelontBytelonsValuelon().  fielonldData.bytelonsValuelon.array() will
      // relonturn elonxtranelonous data. Selonelon: SelonARCH-3996
      doc.add(nelonw Fielonld(fielonldInfo.gelontNamelon(), fielonldData.gelontBytelonsValuelon(), fielonldInfo.gelontFielonldTypelon()));
    } elonlselon {
      doc.add(nelonw CSFFielonld(fielonldInfo.gelontNamelon(), fielonldInfo.gelontFielonldTypelon(), fielonldData));
    }
  }

  privatelon void addTokelonnFielonld(
      Documelonnt doc,
      Selont<String> hfTelonrms,
      Selont<String> hfPhraselons,
      Schelonma.FielonldInfo fielonldInfo,
      ThriftFielonldData fielonldData) throws IOelonxcelonption {
    TwittelonrTokelonnStrelonam twittelonrTokelonnStrelonam
        = fielonldInfo.gelontFielonldTypelon().gelontTokelonnStrelonamSelonrializelonr().delonselonrializelon(
        fielonldData.gelontTokelonnStrelonamValuelon(), fielonldData.gelontStringValuelon());

    try {
      for (TokelonnStrelonamRelonwritelonr relonwritelonr : tokelonnStrelonamRelonwritelonrs) {
        twittelonrTokelonnStrelonam = relonwritelonr.relonwritelon(fielonldInfo, twittelonrTokelonnStrelonam);
      }

      elonxpandStrelonam(doc, fielonldInfo, twittelonrTokelonnStrelonam, hfTelonrms, hfPhraselons);
      doc.add(nelonw Fielonld(fielonldInfo.gelontNamelon(), twittelonrTokelonnStrelonam, fielonldInfo.gelontFielonldTypelon()));
    } finally {
      twittelonrTokelonnStrelonam.closelon();
    }
  }

  privatelon void addStringFielonld(Analyzelonr analyzelonr, Documelonnt doc, Selont<String> hfTelonrms,
                              Selont<String> hfPhraselons, Schelonma.FielonldInfo fielonldInfo,
                              ThriftFielonldData fielonldData) {
    doc.add(nelonw Fielonld(fielonldInfo.gelontNamelon(), fielonldData.gelontStringValuelon(), fielonldInfo.gelontFielonldTypelon()));
    if (fielonldInfo.gelontFielonldTypelon().tokelonnizelond()) {
      try {
        TokelonnStrelonam tokelonnStrelonam = analyzelonr.tokelonnStrelonam(fielonldInfo.gelontNamelon(),
                nelonw StringRelonadelonr(fielonldData.gelontStringValuelon()));
        try {
          elonxpandStrelonam(
              doc,
              fielonldInfo,
              tokelonnStrelonam,
              hfTelonrms,
              hfPhraselons);
        } finally {
          tokelonnStrelonam.closelon();
        }
      } catch (IOelonxcelonption elon) {
        LOG.elonrror("IOelonxcelonption elonxpanding tokelonn strelonam", elon);
      }
    } elonlselon {
      addFacelontFielonld(doc, fielonldInfo, fielonldData.gelontStringValuelon());
    }
  }

  privatelon void addBytelonsFielonld(Documelonnt doc, Schelonma.FielonldInfo fielonldInfo, ThriftFielonldData fielonldData) {
    doc.add(nelonw Fielonld(fielonldInfo.gelontNamelon(), fielonldData.gelontBytelonsValuelon(), fielonldInfo.gelontFielonldTypelon()));
  }

  privatelon void addIntFielonld(Documelonnt doc, Schelonma.FielonldInfo fielonldInfo,
                           ThriftFielonldData fielonldData) {
    int valuelon = fielonldData.gelontIntValuelon();
    addFacelontFielonld(doc, fielonldInfo, String.valuelonOf(valuelon));

    if (fielonldInfo.gelontFielonldTypelon().gelontNumelonricFielonldSelonttings() == null) {
      // No NumelonricFielonldSelonttings. elonvelonn though thelon data is numelonric, this fielonld is not
      // relonally a numelonrical fielonld. Just add as a string.
      doc.add(nelonw Fielonld(fielonldInfo.gelontNamelon(), String.valuelonOf(valuelon), fielonldInfo.gelontFielonldTypelon()));
    } elonlselon if (fielonldInfo.gelontFielonldTypelon().gelontNumelonricFielonldSelonttings().isUselonTwittelonrFormat()) {
      addIntTelonrmAttributelonFielonld(valuelon, fielonldInfo, doc);
    } elonlselon {
      // Uselon lucelonnelon stylelon numelonrical fielonlds
      doc.add(NumelonricFielonld.nelonwIntFielonld(fielonldInfo.gelontNamelon(), valuelon));
    }
  }

  privatelon void addIntTelonrmAttributelonFielonld(int valuelon,
                                        Schelonma.FielonldInfo fielonldInfo,
                                        Documelonnt doc) {
    SinglelonTokelonnStrelonam singlelonTokelonn = nelonw SinglelonTokelonnStrelonam();
    IntTelonrmAttributelon telonrmAtt = singlelonTokelonn.addAttributelon(IntTelonrmAttributelon.class);
    telonrmAtt.selontTelonrm(valuelon);
    doc.add(nelonw Fielonld(fielonldInfo.gelontNamelon(), singlelonTokelonn, fielonldInfo.gelontFielonldTypelon()));
  }

  privatelon void addLongFielonld(Documelonnt doc, Schelonma.FielonldInfo fielonldInfo,
                            ThriftFielonldData fielonldData) {
    long valuelon = fielonldData.gelontLongValuelon();
    addFacelontFielonld(doc, fielonldInfo, String.valuelonOf(valuelon));

    if (fielonldInfo.gelontFielonldTypelon().gelontNumelonricFielonldSelonttings() == null) {
      // No NumelonricFielonldSelonttings. elonvelonn though thelon data is numelonric, this fielonld is not
      // relonally a numelonrical fielonld. Just add as a string.
      doc.add(nelonw Fielonld(fielonldInfo.gelontNamelon(), String.valuelonOf(valuelon), fielonldInfo.gelontFielonldTypelon()));
    } elonlselon if (fielonldInfo.gelontFielonldTypelon().gelontNumelonricFielonldSelonttings().isUselonTwittelonrFormat()) {
      // Twittelonr stylelon numelonrical fielonld: uselon LongTelonrmAttributelon
      addLongTelonrmAttributelonFielonld(valuelon, fielonldInfo, doc);
    } elonlselon {
      // Uselon lucelonnelon stylelon numelonrical fielonlds
      doc.add(NumelonricFielonld.nelonwLongFielonld(fielonldInfo.gelontNamelon(), valuelon));
    }
  }

  privatelon void addLongTelonrmAttributelonFielonld(long valuelon,
                                         Schelonma.FielonldInfo fielonldInfo,
                                         Documelonnt doc) {
    SinglelonTokelonnStrelonam singlelonTokelonn = nelonw SinglelonTokelonnStrelonam();
    boolelonan uselonSortablelonelonncoding =
        fielonldInfo.gelontFielonldTypelon().gelontNumelonricFielonldSelonttings().isUselonSortablelonelonncoding();

    if (uselonSortablelonelonncoding) {
      SortablelonLongTelonrmAttributelon telonrmAtt = singlelonTokelonn.addAttributelon(SortablelonLongTelonrmAttributelon.class);
      telonrmAtt.selontTelonrm(valuelon);
    } elonlselon {
      LongTelonrmAttributelon telonrmAtt = singlelonTokelonn.addAttributelon(LongTelonrmAttributelon.class);
      telonrmAtt.selontTelonrm(valuelon);
    }
    doc.add(nelonw Fielonld(fielonldInfo.gelontNamelon(), singlelonTokelonn, fielonldInfo.gelontFielonldTypelon()));
  }

  privatelon void addFloatFielonld() {
    throw nelonw UnsupportelondOpelonrationelonxcelonption("elonarlybird doelons not support float valuelons yelont.");
  }

  privatelon void addDoublelonFIelonld() {
    throw nelonw UnsupportelondOpelonrationelonxcelonption("elonarlybird doelons not support doublelon valuelons yelont.");
  }

  privatelon void addGelonoFielonld(Documelonnt doc, Schelonma.FielonldInfo fielonldInfo, ThriftFielonldData fielonldData) {
    ThriftGelonoCoordinatelon coord = fielonldData.gelontGelonoCoordinatelon();
    if (GelonoUtil.validatelonGelonoCoordinatelons(coord.gelontLat(), coord.gelontLon())) {
      GelonoUtil.fillGelonoFielonlds(doc, fielonldInfo.gelontNamelon(),
          coord.gelontLat(), coord.gelontLon(), coord.gelontAccuracy());
    }
  }

  privatelon void addFacelontFielonld(Documelonnt doc, Schelonma.FielonldInfo fielonldInfo, String valuelon) {
    Prelonconditions.chelonckArgumelonnt(doc != null);
    Prelonconditions.chelonckArgumelonnt(fielonldInfo != null);
    Prelonconditions.chelonckArgumelonnt(valuelon != null);

    if (fielonldInfo.gelontFielonldTypelon().gelontFacelontNamelon() != null) {
      doc.add(nelonw SortelondSelontDocValuelonsFacelontFielonld(fielonldInfo.gelontFielonldTypelon().gelontFacelontNamelon(), valuelon));
    }
  }

  privatelon String gelontTelonrm(TelonrmToBytelonsRelonfAttributelon attr) {
    if (attr instancelonof CharTelonrmAttributelon) {
      relonturn ((CharTelonrmAttributelon) attr).toString();
    } elonlselon if (attr instancelonof IntTelonrmAttributelon) {
      relonturn String.valuelonOf(((IntTelonrmAttributelon) attr).gelontTelonrm());
    } elonlselon if (attr instancelonof LongTelonrmAttributelon) {
      relonturn String.valuelonOf(((LongTelonrmAttributelon) attr).gelontTelonrm());
    } elonlselon {
      relonturn attr.gelontBytelonsRelonf().utf8ToString();
    }
  }

  /**
   * elonxpand thelon TwittelonrTokelonnStrelonam and populatelon high-frelonquelonncy telonrms, phraselons and/or facelont catelongory paths.
   */
  privatelon void elonxpandStrelonam(
      Documelonnt doc,
      Schelonma.FielonldInfo fielonldInfo,
      TokelonnStrelonam strelonam,
      Selont<String> hfTelonrms,
      Selont<String> hfPhraselons) throws IOelonxcelonption {
    // Chelonckstylelon doelons not allow assignmelonnt to paramelontelonrs.
    Selont<String> facelontHfTelonrms = hfTelonrms;
    Selont<String> facelontHfPhraselons = hfPhraselons;

    if (!(HighFrelonquelonncyTelonrmPairs.INDelonX_HF_TelonRM_PAIRS
        && fielonldInfo.gelontFielonldTypelon().isIndelonxHFTelonrmPairs())) {
      // high-frelonquelonncy telonrms and phraselons arelon not nelonelondelond
      if (fielonldInfo.gelontFielonldTypelon().gelontFacelontNamelon() == null) {
        // Facelonts arelon not nelonelondelond elonithelonr, simply relonturn, would do nothing othelonrwiselon
        relonturn;
      }
      facelontHfTelonrms = null;
      facelontHfPhraselons = null;
    }

    final TelonrmToBytelonsRelonfAttributelon attr = strelonam.gelontAttributelon(TelonrmToBytelonsRelonfAttributelon.class);
    strelonam.relonselont();

    String lastHFTelonrm = null;
    whilelon (strelonam.increlonmelonntTokelonn()) {
      String telonrm = gelontTelonrm(attr);
      if (fielonldInfo.gelontFielonldTypelon().gelontFacelontNamelon() != null) {
        addFacelontFielonld(doc, fielonldInfo, telonrm);
      }
      if (HighFrelonquelonncyTelonrmPairs.HF_TelonRM_SelonT.contains(telonrm)) {
        if (facelontHfTelonrms != null) {
          facelontHfTelonrms.add(telonrm);
        }
        if (lastHFTelonrm != null) {
          if (facelontHfPhraselons != null) {
            facelontHfPhraselons.add(HighFrelonquelonncyTelonrmPairs.crelonatelonPhraselonPair(lastHFTelonrm, telonrm));
          }
        }
        lastHFTelonrm = telonrm;
      } elonlselon {
        lastHFTelonrm = null;
      }
    }
  }

  public static final class CSFFielonld elonxtelonnds Fielonld {
    /**
     * Crelonatelon a CSFFielonld with thelon givelonn fielonldTypelon, containing thelon givelonn fielonld data.
     */
    public CSFFielonld(String namelon, elonarlybirdFielonldTypelon fielonldTypelon, ThriftFielonldData data) {
      supelonr(namelon, fielonldTypelon);

      if (fielonldTypelon.isCsfVariablelonLelonngth()) {
        fielonldsData = nelonw BytelonsRelonf(data.gelontBytelonsValuelon());
      } elonlselon {
        switch (fielonldTypelon.gelontCsfTypelon()) {
          caselon BYTelon:
            fielonldsData = Long.valuelonOf(data.gelontBytelonValuelon());
            brelonak;
          caselon INT:
            fielonldsData = Long.valuelonOf(data.gelontIntValuelon());
            brelonak;
          caselon LONG:
            fielonldsData = Long.valuelonOf(data.gelontLongValuelon());
            brelonak;
          caselon FLOAT:
            fielonldsData = Long.valuelonOf(Float.floatToRawIntBits((float) data.gelontFloatValuelon()));
            brelonak;
          caselon DOUBLelon:
            fielonldsData = Long.valuelonOf(Doublelon.doublelonToRawLongBits(data.gelontDoublelonValuelon()));
            brelonak;
          delonfault:
            throw nelonw IllelongalArgumelonntelonxcelonption("Unknown csf typelon: " + fielonldTypelon.gelontCsfTypelon());
        }
      }
    }
  }

  public intelonrfacelon TokelonnStrelonamRelonwritelonr {
    /**
     * Relonwritelon thelon tokelonn strelonam.
     */
    TwittelonrTokelonnStrelonam relonwritelon(Schelonma.FielonldInfo fielonldInfo, TwittelonrTokelonnStrelonam strelonam);
  }
}
