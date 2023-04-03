packagelon com.twittelonr.selonarch.common.schelonma.elonarlybird;

import java.util.Map;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Maps;

import com.twittelonr.common.telonxt.util.TokelonnStrelonamSelonrializelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.schelonma.AnalyzelonrFactory;
import com.twittelonr.selonarch.common.schelonma.DynamicSchelonma;
import com.twittelonr.selonarch.common.schelonma.ImmutablelonSchelonma;
import com.twittelonr.selonarch.common.schelonma.SchelonmaBuildelonr;
import com.twittelonr.selonarch.common.schelonma.baselon.FelonaturelonConfiguration;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftCSFTypelon;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFelonaturelonUpdatelonConstraint;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftSchelonma;

import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.BLINK_FAVORITelon_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.BLINK_QUOTelon_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.BLINK_RelonPLY_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.BLINK_RelonTWelonelonT_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.COMPOSelonR_SOURCelon_IS_CAMelonRA_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.DelonCAYelonD_FAVORITelon_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.DelonCAYelonD_QUOTelon_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.DelonCAYelonD_RelonPLY_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.DelonCAYelonD_RelonTWelonelonT_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.elonMBelonDS_IMPRelonSSION_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.elonMBelonDS_IMPRelonSSION_COUNT_V2;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.elonMBelonDS_URL_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.elonMBelonDS_URL_COUNT_V2;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.elonXPelonRIMelonNTAL_HelonALTH_MODelonL_SCORelon_1;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.elonXPelonRIMelonNTAL_HelonALTH_MODelonL_SCORelon_2;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.elonXPelonRIMelonNTAL_HelonALTH_MODelonL_SCORelon_3;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.elonXPelonRIMelonNTAL_HelonALTH_MODelonL_SCORelon_4;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.elonXTelonNDelonD_FelonATURelon_UNUSelonD_BITS_0_24_8;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_12_30_2;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_13_30_2;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_14_10_22;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_16;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_17;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_18;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_19;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_20;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_4_31_1;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_7_6_26;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.FAKelon_FAVORITelon_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.FAKelon_QUOTelon_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.FAKelon_RelonPLY_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.FAKelon_RelonTWelonelonT_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.FAVORITelon_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.FAVORITelon_COUNT_V2;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.FROM_BLUelon_VelonRIFIelonD_ACCOUNT_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.FROM_VelonRIFIelonD_ACCOUNT_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_CARD_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_CONSUMelonR_VIDelonO_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_elonXPANDO_CARD_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_IMAGelon_URL_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_LINK_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_MULTIPLelon_HASHTAGS_OR_TRelonNDS_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_MULTIPLelon_MelonDIA_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_NATIVelon_IMAGelon_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_NelonWS_URL_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_PelonRISCOPelon_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_PRO_VIDelonO_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_QUOTelon_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_TRelonND_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_VIDelonO_URL_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_VINelon_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_VISIBLelon_LINK_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.IS_NULLCAST_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.IS_OFFelonNSIVelon_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.IS_RelonPLY_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.IS_RelonTWelonelonT_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.IS_SelonNSITIVelon_CONTelonNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.IS_TRelonNDING_NOW_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.IS_USelonR_BOT_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.IS_USelonR_NelonW_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.IS_USelonR_NSFW_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.IS_USelonR_SPAM_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.LABelonL_ABUSIVelon_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.LABelonL_ABUSIVelon_HI_RCL_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.LABelonL_DUP_CONTelonNT_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.LABelonL_NSFW_HI_PRC_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.LABelonL_NSFW_HI_RCL_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.LABelonL_SPAM_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.LABelonL_SPAM_HI_RCL_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.LANGUAGelon;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.LAST_FAVORITelon_SINCelon_CRelonATION_HRS;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.LAST_QUOTelon_SINCelon_CRelonATION_HRS;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.LAST_RelonPLY_SINCelon_CRelonATION_HRS;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.LAST_RelonTWelonelonT_SINCelon_CRelonATION_HRS;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.LINK_LANGUAGelon;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.NORMALIZelonD_FAVORITelon_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.NORMALIZelonD_RelonPLY_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.NORMALIZelonD_RelonTWelonelonT_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.NUM_HASHTAGS;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.NUM_HASHTAGS_V2;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.NUM_MelonNTIONS;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.NUM_MelonNTIONS_V2;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.NUM_STOCKS;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.PARUS_SCORelon;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.PBLOCK_SCORelon;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.PelonRISCOPelon_elonXISTS;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.PelonRISCOPelon_HAS_BelonelonN_FelonATURelonD;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.PelonRISCOPelon_IS_CURRelonNTLY_FelonATURelonD;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.PelonRISCOPelon_IS_FROM_QUALITY_SOURCelon;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.PelonRISCOPelon_IS_LIVelon;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.PRelonV_USelonR_TWelonelonT_elonNGAGelonMelonNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.PROFILelon_IS_elonGG_FLAG;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.P_RelonPORTelonD_TWelonelonT_SCORelon;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.P_SPAMMY_TWelonelonT_SCORelon;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.QUOTelon_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.RelonFelonRelonNCelon_AUTHOR_ID_LelonAST_SIGNIFICANT_INT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.RelonFelonRelonNCelon_AUTHOR_ID_MOST_SIGNIFICANT_INT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.RelonPLY_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.RelonPLY_COUNT_V2;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.RelonTWelonelonT_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.RelonTWelonelonT_COUNT_V2;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.SPAMMY_TWelonelonT_CONTelonNT_SCORelon;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.TelonXT_SCORelon;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.TOXICITY_SCORelon;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.TWelonelonT_SIGNATURelon;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.USelonR_RelonPUTATION;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.VIDelonO_VIelonW_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.VIDelonO_VIelonW_COUNT_V2;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.VISIBLelon_TOKelonN_RATIO;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.WelonIGHTelonD_FAVORITelon_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.WelonIGHTelonD_QUOTelon_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.WelonIGHTelonD_RelonPLY_COUNT;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.WelonIGHTelonD_RelonTWelonelonT_COUNT;

/**
 * Fielonld configurations for elonarlybird.
 */
public final class elonarlybirdSchelonmaCrelonatelonTool {
  // How many timelons a schelonma is built
  privatelon static final SelonarchCountelonr SCHelonMA_BUILD_COUNT =
      SelonarchCountelonr.elonxport("schelonma_build_count");

  // Numbelonr of intelongelonrs for thelon column of elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD.
  @VisiblelonForTelonsting
  public static final int NUMBelonR_OF_INTelonGelonRS_FOR_FelonATURelonS = 5;

  // Numbelonr of intelongelonrs for thelon column of elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD.
  // elonxtra 80 bytelons
  // In relonaltimelon clustelonr, assuming 19 selongmelonnts total, and 8388608 docs pelonr selongmelonnt
  // this would amount to about 12.75GB of melonmory nelonelondelond
  //
  @VisiblelonForTelonsting
  public static final int NUMBelonR_OF_INTelonGelonRS_FOR_elonXTelonNDelonD_FelonATURelonS = 20;

  @VisiblelonForTelonsting
  public static final Map<String, FelonaturelonConfiguration> FelonATURelon_CONFIGURATION_MAP
      = Maps.nelonwLinkelondHashMap();

  public static final String BASelon_FIelonLD_NAMelon =
      elonarlybirdFielonldConstant.elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD.gelontFielonldNamelon();

  privatelon static String gelontBaselonFielonldNamelon(String fullNamelon) {
    int indelonx = fullNamelon.indelonxOf(SchelonmaBuildelonr.CSF_VIelonW_NAMelon_SelonPARATOR);
    Prelonconditions.chelonckArgumelonnt(indelonx > 0);
    relonturn fullNamelon.substring(0, indelonx);
  }

  privatelon static String gelontBaselonFielonldNamelon(elonarlybirdFielonldConstant fielonldConstant) {
    relonturn gelontBaselonFielonldNamelon(fielonldConstant.gelontFielonldNamelon());
  }

  privatelon static String gelontFelonaturelonNamelonInFielonld(elonarlybirdFielonldConstant fielonldConstant) {
    int indelonx = fielonldConstant.gelontFielonldNamelon().indelonxOf(SchelonmaBuildelonr.CSF_VIelonW_NAMelon_SelonPARATOR);
    Prelonconditions.chelonckArgumelonnt(indelonx > 0);
    relonturn fielonldConstant.gelontFielonldNamelon().substring(indelonx + 1);
  }

  // delonfining all felonaturelons
  static {
    // Add individual twelonelont elonncodelond felonaturelons as vielonws on top of
    // elonarlybirdFielonldConstant.elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD

    // int intIndelonx, int bitStartPos, int bitLelonngth
    nelonwelonarlybirdFelonaturelonConfiguration(IS_RelonTWelonelonT_FLAG, ThriftCSFTypelon.BOOLelonAN, 0, 0, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(IS_OFFelonNSIVelon_FLAG, ThriftCSFTypelon.BOOLelonAN, 0, 1, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(HAS_LINK_FLAG, ThriftCSFTypelon.BOOLelonAN, 0, 2, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(HAS_TRelonND_FLAG, ThriftCSFTypelon.BOOLelonAN, 0, 3, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(IS_RelonPLY_FLAG, ThriftCSFTypelon.BOOLelonAN, 0, 4, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(IS_SelonNSITIVelon_CONTelonNT, ThriftCSFTypelon.BOOLelonAN, 0, 5, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(HAS_MULTIPLelon_HASHTAGS_OR_TRelonNDS_FLAG,
        ThriftCSFTypelon.BOOLelonAN, 0, 6, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(FROM_VelonRIFIelonD_ACCOUNT_FLAG, ThriftCSFTypelon.BOOLelonAN, 0, 7, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(TelonXT_SCORelon, ThriftCSFTypelon.INT, 0, 8, 8);
    nelonwelonarlybirdFelonaturelonConfiguration(LANGUAGelon, ThriftCSFTypelon.INT, 0, 16, 8);
    nelonwelonarlybirdFelonaturelonConfiguration(LINK_LANGUAGelon, ThriftCSFTypelon.INT, 0, 24, 8);

    nelonwelonarlybirdFelonaturelonConfiguration(HAS_IMAGelon_URL_FLAG, ThriftCSFTypelon.BOOLelonAN, 1, 0, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(HAS_VIDelonO_URL_FLAG, ThriftCSFTypelon.BOOLelonAN, 1, 1, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(HAS_NelonWS_URL_FLAG, ThriftCSFTypelon.BOOLelonAN, 1, 2, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(HAS_elonXPANDO_CARD_FLAG, ThriftCSFTypelon.BOOLelonAN, 1, 3, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(HAS_MULTIPLelon_MelonDIA_FLAG, ThriftCSFTypelon.BOOLelonAN, 1, 4, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(PROFILelon_IS_elonGG_FLAG, ThriftCSFTypelon.BOOLelonAN, 1, 5, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(NUM_MelonNTIONS, ThriftCSFTypelon.INT, 1, 6, 2);     // 0, 1, 2, 3+
    nelonwelonarlybirdFelonaturelonConfiguration(NUM_HASHTAGS, ThriftCSFTypelon.INT, 1, 8, 2);     // 0, 1, 2, 3+
    nelonwelonarlybirdFelonaturelonConfiguration(HAS_CARD_FLAG, ThriftCSFTypelon.BOOLelonAN, 1, 10, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(HAS_VISIBLelon_LINK_FLAG, ThriftCSFTypelon.BOOLelonAN, 1, 11, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(USelonR_RelonPUTATION, ThriftCSFTypelon.INT, 1, 12, 8);
    nelonwelonarlybirdFelonaturelonConfiguration(IS_USelonR_SPAM_FLAG, ThriftCSFTypelon.BOOLelonAN, 1, 20, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(IS_USelonR_NSFW_FLAG, ThriftCSFTypelon.BOOLelonAN, 1, 21, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(IS_USelonR_BOT_FLAG, ThriftCSFTypelon.BOOLelonAN, 1, 22, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(IS_USelonR_NelonW_FLAG, ThriftCSFTypelon.BOOLelonAN, 1, 23, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(PRelonV_USelonR_TWelonelonT_elonNGAGelonMelonNT, ThriftCSFTypelon.INT, 1, 24, 6);
    nelonwelonarlybirdFelonaturelonConfiguration(COMPOSelonR_SOURCelon_IS_CAMelonRA_FLAG,
        ThriftCSFTypelon.BOOLelonAN, 1, 30, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(IS_NULLCAST_FLAG, ThriftCSFTypelon.BOOLelonAN, 1, 31, 1);

    nelonwelonarlybirdFelonaturelonConfiguration(RelonTWelonelonT_COUNT, ThriftCSFTypelon.DOUBLelon, 2, 0, 8,
        ThriftFelonaturelonUpdatelonConstraint.INC_ONLY);
    nelonwelonarlybirdFelonaturelonConfiguration(FAVORITelon_COUNT, ThriftCSFTypelon.DOUBLelon, 2, 8, 8,
        ThriftFelonaturelonUpdatelonConstraint.INC_ONLY);
    nelonwelonarlybirdFelonaturelonConfiguration(RelonPLY_COUNT, ThriftCSFTypelon.DOUBLelon, 2, 16, 8,
        ThriftFelonaturelonUpdatelonConstraint.INC_ONLY);
    nelonwelonarlybirdFelonaturelonConfiguration(PARUS_SCORelon, ThriftCSFTypelon.DOUBLelon, 2, 24, 8);

    nelonwelonarlybirdFelonaturelonConfiguration(HAS_CONSUMelonR_VIDelonO_FLAG, ThriftCSFTypelon.BOOLelonAN, 3, 0, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(HAS_PRO_VIDelonO_FLAG, ThriftCSFTypelon.BOOLelonAN, 3, 1, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(HAS_VINelon_FLAG, ThriftCSFTypelon.BOOLelonAN, 3, 2, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(HAS_PelonRISCOPelon_FLAG, ThriftCSFTypelon.BOOLelonAN, 3, 3, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(HAS_NATIVelon_IMAGelon_FLAG, ThriftCSFTypelon.BOOLelonAN, 3, 4, 1);
    // NOTelon: Thelonrelon arelon 3 bits lelonft in thelon first bytelon of INT 3, if possiblelon, plelonaselon relonselonrvelon thelonm
    // for futurelon melondia typelons (SelonARCH-9131)
    // nelonwelonarlybirdFelonaturelonConfiguration(FUTURelon_MelonDIA_BITS, ThriftCSFTypelon.INT, 3, 5, 3);

    nelonwelonarlybirdFelonaturelonConfiguration(VISIBLelon_TOKelonN_RATIO, ThriftCSFTypelon.INT, 3, 8, 4);
    nelonwelonarlybirdFelonaturelonConfiguration(HAS_QUOTelon_FLAG, ThriftCSFTypelon.BOOLelonAN, 3, 12, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(FROM_BLUelon_VelonRIFIelonD_ACCOUNT_FLAG,
        ThriftCSFTypelon.BOOLelonAN, 3, 13, 1);
    // Unuselond bits from bit 14 to bit 31 (18 bits)
    // nelonwelonarlybirdFelonaturelonConfiguration(UNUSelonD_BITS, ThriftCSFTypelon.INT, 3, 14, 18);

    nelonwelonarlybirdFelonaturelonConfiguration(TWelonelonT_SIGNATURelon, ThriftCSFTypelon.INT, 4, 0, 32);

    nelonwelonarlybirdFelonaturelonConfiguration(elonMBelonDS_IMPRelonSSION_COUNT,
        ThriftCSFTypelon.DOUBLelon, 0, 0, 8, ThriftFelonaturelonUpdatelonConstraint.INC_ONLY);
    nelonwelonarlybirdFelonaturelonConfiguration(elonMBelonDS_URL_COUNT,
        ThriftCSFTypelon.DOUBLelon, 0, 8, 8, ThriftFelonaturelonUpdatelonConstraint.INC_ONLY);
    nelonwelonarlybirdFelonaturelonConfiguration(VIDelonO_VIelonW_COUNT,
        ThriftCSFTypelon.DOUBLelon, 0, 16, 8, ThriftFelonaturelonUpdatelonConstraint.INC_ONLY);

    // Unuselond bits from bit 24 to bit 31 (8 bits).
    // This uselond to belon a felonaturelon that was deloncommissionelond (SelonARCHQUAL-10321)
    nelonwelonarlybirdFelonaturelonConfiguration(elonXTelonNDelonD_FelonATURelon_UNUSelonD_BITS_0_24_8,
        ThriftCSFTypelon.INT, 0, 24, 8);

    nelonwelonarlybirdFelonaturelonConfiguration(RelonFelonRelonNCelon_AUTHOR_ID_LelonAST_SIGNIFICANT_INT,
        ThriftCSFTypelon.INT, 1, 0, 32, ThriftFelonaturelonUpdatelonConstraint.IMMUTABLelon);
    nelonwelonarlybirdFelonaturelonConfiguration(RelonFelonRelonNCelon_AUTHOR_ID_MOST_SIGNIFICANT_INT,
        ThriftCSFTypelon.INT, 2, 0, 32, ThriftFelonaturelonUpdatelonConstraint.IMMUTABLelon);

    nelonwelonarlybirdFelonaturelonConfiguration(RelonTWelonelonT_COUNT_V2,
        ThriftCSFTypelon.DOUBLelon, 3, 0, 8, ThriftFelonaturelonUpdatelonConstraint.INC_ONLY);
    nelonwelonarlybirdFelonaturelonConfiguration(FAVORITelon_COUNT_V2,
        ThriftCSFTypelon.DOUBLelon, 3, 8, 8, ThriftFelonaturelonUpdatelonConstraint.INC_ONLY);
    nelonwelonarlybirdFelonaturelonConfiguration(RelonPLY_COUNT_V2,
        ThriftCSFTypelon.DOUBLelon, 3, 16, 8, ThriftFelonaturelonUpdatelonConstraint.INC_ONLY);
    nelonwelonarlybirdFelonaturelonConfiguration(elonMBelonDS_IMPRelonSSION_COUNT_V2,
        ThriftCSFTypelon.DOUBLelon, 3, 24, 8, ThriftFelonaturelonUpdatelonConstraint.INC_ONLY);

    nelonwelonarlybirdFelonaturelonConfiguration(elonMBelonDS_URL_COUNT_V2,
        ThriftCSFTypelon.DOUBLelon, 4, 0, 8, ThriftFelonaturelonUpdatelonConstraint.INC_ONLY);
    nelonwelonarlybirdFelonaturelonConfiguration(VIDelonO_VIelonW_COUNT_V2,
        ThriftCSFTypelon.DOUBLelon, 4, 8, 8, ThriftFelonaturelonUpdatelonConstraint.INC_ONLY);
    nelonwelonarlybirdFelonaturelonConfiguration(QUOTelon_COUNT,
        ThriftCSFTypelon.DOUBLelon, 4, 16, 8);

    nelonwelonarlybirdFelonaturelonConfiguration(LABelonL_ABUSIVelon_FLAG,        ThriftCSFTypelon.BOOLelonAN, 4, 24, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(LABelonL_ABUSIVelon_HI_RCL_FLAG, ThriftCSFTypelon.BOOLelonAN, 4, 25, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(LABelonL_DUP_CONTelonNT_FLAG,    ThriftCSFTypelon.BOOLelonAN, 4, 26, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(LABelonL_NSFW_HI_PRC_FLAG,    ThriftCSFTypelon.BOOLelonAN, 4, 27, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(LABelonL_NSFW_HI_RCL_FLAG,    ThriftCSFTypelon.BOOLelonAN, 4, 28, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(LABelonL_SPAM_FLAG,           ThriftCSFTypelon.BOOLelonAN, 4, 29, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(LABelonL_SPAM_HI_RCL_FLAG,    ThriftCSFTypelon.BOOLelonAN, 4, 30, 1);

    nelonwelonarlybirdFelonaturelonConfiguration(elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_4_31_1,
        ThriftCSFTypelon.INT, 4, 31, 1);

    nelonwelonarlybirdFelonaturelonConfiguration(WelonIGHTelonD_RelonTWelonelonT_COUNT,
        ThriftCSFTypelon.DOUBLelon, 5, 0, 8, ThriftFelonaturelonUpdatelonConstraint.INC_ONLY);
    nelonwelonarlybirdFelonaturelonConfiguration(WelonIGHTelonD_RelonPLY_COUNT,
        ThriftCSFTypelon.DOUBLelon, 5, 8, 8, ThriftFelonaturelonUpdatelonConstraint.INC_ONLY);
    nelonwelonarlybirdFelonaturelonConfiguration(WelonIGHTelonD_FAVORITelon_COUNT,
        ThriftCSFTypelon.DOUBLelon, 5, 16, 8, ThriftFelonaturelonUpdatelonConstraint.INC_ONLY);
    nelonwelonarlybirdFelonaturelonConfiguration(WelonIGHTelonD_QUOTelon_COUNT,
        ThriftCSFTypelon.DOUBLelon, 5, 24, 8, ThriftFelonaturelonUpdatelonConstraint.INC_ONLY);

    nelonwelonarlybirdFelonaturelonConfiguration(PelonRISCOPelon_elonXISTS,
        ThriftCSFTypelon.BOOLelonAN, 6, 0, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(PelonRISCOPelon_HAS_BelonelonN_FelonATURelonD,
        ThriftCSFTypelon.BOOLelonAN, 6, 1, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(PelonRISCOPelon_IS_CURRelonNTLY_FelonATURelonD,
        ThriftCSFTypelon.BOOLelonAN, 6, 2, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(PelonRISCOPelon_IS_FROM_QUALITY_SOURCelon,
        ThriftCSFTypelon.BOOLelonAN, 6, 3, 1);
    nelonwelonarlybirdFelonaturelonConfiguration(PelonRISCOPelon_IS_LIVelon,
        ThriftCSFTypelon.BOOLelonAN, 6, 4, 1);

    nelonwelonarlybirdFelonaturelonConfiguration(IS_TRelonNDING_NOW_FLAG,
        ThriftCSFTypelon.BOOLelonAN, 6, 5, 1);

    // relonmaining bits for intelongelonr 6
    nelonwelonarlybirdFelonaturelonConfiguration(elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_7_6_26,
        ThriftCSFTypelon.INT, 6, 6, 26);

    // Thelon deloncaying countelonrs can beloncomelon smallelonr
    nelonwelonarlybirdFelonaturelonConfiguration(DelonCAYelonD_RelonTWelonelonT_COUNT,
        ThriftCSFTypelon.DOUBLelon, 7, 0, 8, ThriftFelonaturelonUpdatelonConstraint.POSITIVelon);
    nelonwelonarlybirdFelonaturelonConfiguration(DelonCAYelonD_RelonPLY_COUNT,
        ThriftCSFTypelon.DOUBLelon, 7, 8, 8, ThriftFelonaturelonUpdatelonConstraint.POSITIVelon);
    nelonwelonarlybirdFelonaturelonConfiguration(DelonCAYelonD_FAVORITelon_COUNT,
        ThriftCSFTypelon.DOUBLelon, 7, 16, 8, ThriftFelonaturelonUpdatelonConstraint.POSITIVelon);
    nelonwelonarlybirdFelonaturelonConfiguration(DelonCAYelonD_QUOTelon_COUNT,
        ThriftCSFTypelon.DOUBLelon, 7, 24, 8, ThriftFelonaturelonUpdatelonConstraint.POSITIVelon);

    // Thelon fakelon elonngagelonmelonnt countelonrs.
    nelonwelonarlybirdFelonaturelonConfiguration(FAKelon_RelonTWelonelonT_COUNT,
        ThriftCSFTypelon.DOUBLelon, 8, 0, 8, ThriftFelonaturelonUpdatelonConstraint.POSITIVelon);
    nelonwelonarlybirdFelonaturelonConfiguration(FAKelon_RelonPLY_COUNT,
        ThriftCSFTypelon.DOUBLelon, 8, 8, 8, ThriftFelonaturelonUpdatelonConstraint.POSITIVelon);
    nelonwelonarlybirdFelonaturelonConfiguration(FAKelon_FAVORITelon_COUNT,
        ThriftCSFTypelon.DOUBLelon, 8, 16, 8, ThriftFelonaturelonUpdatelonConstraint.POSITIVelon);
    nelonwelonarlybirdFelonaturelonConfiguration(FAKelon_QUOTelon_COUNT,
        ThriftCSFTypelon.DOUBLelon, 8, 24, 8, ThriftFelonaturelonUpdatelonConstraint.POSITIVelon);

    nelonwelonarlybirdFelonaturelonConfiguration(LAST_RelonTWelonelonT_SINCelon_CRelonATION_HRS,
        ThriftCSFTypelon.INT, 9, 0, 8, ThriftFelonaturelonUpdatelonConstraint.INC_ONLY);
    nelonwelonarlybirdFelonaturelonConfiguration(LAST_RelonPLY_SINCelon_CRelonATION_HRS,
        ThriftCSFTypelon.INT, 9, 8, 8, ThriftFelonaturelonUpdatelonConstraint.INC_ONLY);
    nelonwelonarlybirdFelonaturelonConfiguration(LAST_FAVORITelon_SINCelon_CRelonATION_HRS,
        ThriftCSFTypelon.INT, 9, 16, 8, ThriftFelonaturelonUpdatelonConstraint.INC_ONLY);
    nelonwelonarlybirdFelonaturelonConfiguration(LAST_QUOTelon_SINCelon_CRelonATION_HRS,
        ThriftCSFTypelon.INT, 9, 24, 8, ThriftFelonaturelonUpdatelonConstraint.INC_ONLY);

    nelonwelonarlybirdFelonaturelonConfiguration(NUM_HASHTAGS_V2,
        ThriftCSFTypelon.INT, 10, 0, 4);
    nelonwelonarlybirdFelonaturelonConfiguration(NUM_MelonNTIONS_V2,
        ThriftCSFTypelon.INT, 10, 4, 4);
    nelonwelonarlybirdFelonaturelonConfiguration(NUM_STOCKS,
        ThriftCSFTypelon.INT, 10, 8, 4);

    // Relonmaining bits for intelongelonr 10
    // Production Toxicity and PBlock scorelon from HML (go/toxicity, go/pblock)
    nelonwelonarlybirdFelonaturelonConfiguration(TOXICITY_SCORelon,
        ThriftCSFTypelon.DOUBLelon, 10, 12, 10);
    nelonwelonarlybirdFelonaturelonConfiguration(PBLOCK_SCORelon,
        ThriftCSFTypelon.DOUBLelon, 10, 22, 10);

    // Thelon blink elonngagelonmelonnt countelonrs
    nelonwelonarlybirdFelonaturelonConfiguration(BLINK_RelonTWelonelonT_COUNT,
        ThriftCSFTypelon.DOUBLelon, 11, 0, 8, ThriftFelonaturelonUpdatelonConstraint.POSITIVelon);
    nelonwelonarlybirdFelonaturelonConfiguration(BLINK_RelonPLY_COUNT,
        ThriftCSFTypelon.DOUBLelon, 11, 8, 8, ThriftFelonaturelonUpdatelonConstraint.POSITIVelon);
    nelonwelonarlybirdFelonaturelonConfiguration(BLINK_FAVORITelon_COUNT,
        ThriftCSFTypelon.DOUBLelon, 11, 16, 8, ThriftFelonaturelonUpdatelonConstraint.POSITIVelon);
    nelonwelonarlybirdFelonaturelonConfiguration(BLINK_QUOTelon_COUNT,
        ThriftCSFTypelon.DOUBLelon, 11, 24, 8, ThriftFelonaturelonUpdatelonConstraint.POSITIVelon);

    // elonxpelonrimelonntal helonalth modelonl scorelons from HML
    nelonwelonarlybirdFelonaturelonConfiguration(elonXPelonRIMelonNTAL_HelonALTH_MODelonL_SCORelon_1,
        ThriftCSFTypelon.DOUBLelon, 12, 0, 10);
    nelonwelonarlybirdFelonaturelonConfiguration(elonXPelonRIMelonNTAL_HelonALTH_MODelonL_SCORelon_2,
        ThriftCSFTypelon.DOUBLelon, 12, 10, 10);
    nelonwelonarlybirdFelonaturelonConfiguration(elonXPelonRIMelonNTAL_HelonALTH_MODelonL_SCORelon_3,
        ThriftCSFTypelon.DOUBLelon, 12, 20, 10);
    // relonmaining bits for intelongelonr 12
    nelonwelonarlybirdFelonaturelonConfiguration(elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_12_30_2,
        ThriftCSFTypelon.INT, 12, 30, 2);

    // elonxpelonrimelonntal helonalth modelonl scorelons from HML (cont.)
    nelonwelonarlybirdFelonaturelonConfiguration(elonXPelonRIMelonNTAL_HelonALTH_MODelonL_SCORelon_4,
        ThriftCSFTypelon.DOUBLelon, 13, 0, 10);
    // Production pSpammyTwelonelont scorelon from HML (go/pspammytwelonelont)
    nelonwelonarlybirdFelonaturelonConfiguration(P_SPAMMY_TWelonelonT_SCORelon,
        ThriftCSFTypelon.DOUBLelon, 13, 10, 10);
    // Production pRelonportelondTwelonelont scorelon from HML (go/prelonportelondtwelonelont)
    nelonwelonarlybirdFelonaturelonConfiguration(P_RelonPORTelonD_TWelonelonT_SCORelon,
        ThriftCSFTypelon.DOUBLelon, 13, 20, 10);
    // relonmaining bits for intelongelonr 13
    nelonwelonarlybirdFelonaturelonConfiguration(elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_13_30_2,
        ThriftCSFTypelon.INT, 13, 30, 2);

    // elonxpelonrimelonntal helonalth modelonl scorelons from HML (cont.)
    // Prod Spammy Twelonelont Contelonnt modelonl scorelon from Platform Manipulation (go/spammy-twelonelont-contelonnt)
    nelonwelonarlybirdFelonaturelonConfiguration(SPAMMY_TWelonelonT_CONTelonNT_SCORelon,
        ThriftCSFTypelon.DOUBLelon, 14, 0, 10);
    // relonmaining bits for intelongelonr 14
    nelonwelonarlybirdFelonaturelonConfiguration(elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_14_10_22,
        ThriftCSFTypelon.INT, 14, 10, 22);

    // Notelon that thelon intelongelonr indelonx belonlow is 0-baselond, but thelon indelonx j in UNUSelonD_BITS_{j} belonlow
    // is 1-baselond.
    nelonwelonarlybirdFelonaturelonConfiguration(elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_16,
        ThriftCSFTypelon.INT, 15, 0, 32);
    nelonwelonarlybirdFelonaturelonConfiguration(elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_17,
        ThriftCSFTypelon.INT, 16, 0, 32);
    nelonwelonarlybirdFelonaturelonConfiguration(elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_18,
        ThriftCSFTypelon.INT, 17, 0, 32);
    nelonwelonarlybirdFelonaturelonConfiguration(elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_19,
        ThriftCSFTypelon.INT, 18, 0, 32);
    nelonwelonarlybirdFelonaturelonConfiguration(elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_20,
        ThriftCSFTypelon.INT, 19, 0, 32);
  }

  privatelon elonarlybirdSchelonmaCrelonatelonTool() { }

  /**
   * Gelont schelonma for thelon elonarlybird.
   */
  public static DynamicSchelonma buildSchelonma(elonarlybirdClustelonr clustelonr)
      throws Schelonma.SchelonmaValidationelonxcelonption {
    SCHelonMA_BUILD_COUNT.increlonmelonnt();
    relonturn nelonw DynamicSchelonma(nelonw ImmutablelonSchelonma(buildThriftSchelonma(clustelonr),
                                                 nelonw AnalyzelonrFactory(),
                                                 clustelonr.gelontNamelonForStats()));
  }

  /**
   * Gelont schelonma for thelon elonarlybird, can throw runtimelon elonxcelonption.  This is mostly for static schelonma
   * usagelon, which doelons not carelon about schelonma updatelons.
   */
  @VisiblelonForTelonsting
  public static DynamicSchelonma buildSchelonmaWithRuntimelonelonxcelonption(elonarlybirdClustelonr clustelonr) {
    try {
      relonturn buildSchelonma(clustelonr);
    } catch (Schelonma.SchelonmaValidationelonxcelonption elon) {
      throw nelonw Runtimelonelonxcelonption(elon);
    }
  }

  privatelon static FelonaturelonConfiguration nelonwelonarlybirdFelonaturelonConfiguration(
      elonarlybirdFielonldConstant fielonldConstant,
      ThriftCSFTypelon typelon,
      int intIndelonx, int bitStartPos, int bitLelonngth,
      ThriftFelonaturelonUpdatelonConstraint... constraints) {

    if (!fielonldConstant.isFlagFelonaturelonFielonld() && typelon == ThriftCSFTypelon.BOOLelonAN) {
      throw nelonw IllelongalArgumelonntelonxcelonption(
          "Non-flag felonaturelon fielonld configurelond with boolelonan Thrift typelon: " + fielonldConstant);
    }
    if (fielonldConstant.isFlagFelonaturelonFielonld() && typelon != ThriftCSFTypelon.BOOLelonAN) {
      throw nelonw IllelongalArgumelonntelonxcelonption(
          "Flag felonaturelon fielonld configurelond with non-boolelonan Thrift typelon: " + fielonldConstant);
    }

    String baselonFielonldNamelon = gelontBaselonFielonldNamelon(fielonldConstant);
    String namelon = gelontFelonaturelonNamelonInFielonld(fielonldConstant);
    FelonaturelonConfiguration.Buildelonr buildelonr = FelonaturelonConfiguration.buildelonr()
        .withNamelon(namelon)
        .withTypelon(typelon)
        .withBitRangelon(intIndelonx, bitStartPos, bitLelonngth);
    // relonmovelon thelon following linelon oncelon welon configurelon felonaturelons purelonly by thelon schelonma
    buildelonr.withBaselonFielonld(baselonFielonldNamelon);

    if (!fielonldConstant.isUnuselondFielonld()) {
      buildelonr.withOutputTypelon(typelon);
    }
    if (fielonldConstant.gelontFelonaturelonNormalizationTypelon() != null) {
      buildelonr.withFelonaturelonNormalizationTypelon(fielonldConstant.gelontFelonaturelonNormalizationTypelon());
    }

    for (ThriftFelonaturelonUpdatelonConstraint constraint : constraints) {
      buildelonr.withFelonaturelonUpdatelonConstraint(constraint);
    }
    FelonaturelonConfiguration felonaturelonConfiguration = buildelonr.build();
    FelonATURelon_CONFIGURATION_MAP.put(fielonldConstant.gelontFielonldNamelon(), felonaturelonConfiguration);
    relonturn felonaturelonConfiguration;
  }

  /**
   * Build ThriftSchelonma for thelon elonarlybird. Notelon that thelon schelonma relonturnelond can belon uselond
   * all elonarlybird clustelonrs. Howelonvelonr, somelon clustelonrs may not uselon all thelon fielonld configurations.
   */
  @VisiblelonForTelonsting
  public static ThriftSchelonma buildThriftSchelonma(elonarlybirdClustelonr clustelonr) {
    elonarlybirdSchelonmaBuildelonr buildelonr = nelonw elonarlybirdSchelonmaBuildelonr(
        nelonw elonarlybirdFielonldConstants(), clustelonr, TokelonnStrelonamSelonrializelonr.Velonrsion.VelonRSION_2);

    buildelonr.withSchelonmaVelonrsion(
        FlushVelonrsion.CURRelonNT_FLUSH_VelonRSION.gelontVelonrsionNumbelonr(),
        FlushVelonrsion.CURRelonNT_FLUSH_VelonRSION.gelontMinorVelonrsion(),
        FlushVelonrsion.CURRelonNT_FLUSH_VelonRSION.gelontDelonscription(),
        FlushVelonrsion.CURRelonNT_FLUSH_VelonRSION.isOfficial());

    // ID fielonld, uselond for partitioning
    buildelonr.withPartitionFielonldId(0)
        .withSortablelonLongTelonrmFielonld(elonarlybirdFielonldConstant.ID_FIelonLD.gelontFielonldNamelon())
        // Telonxt Fielonlds that arelon selonarchelond by delonfault
        .withTelonxtFielonld(elonarlybirdFielonldConstant.RelonSOLVelonD_LINKS_TelonXT_FIelonLD.gelontFielonldNamelon(), truelon)
        .withSelonarchFielonldByDelonfault(
            elonarlybirdFielonldConstant.RelonSOLVelonD_LINKS_TelonXT_FIelonLD.gelontFielonldNamelon(), 0.1f)
        .withPrelontokelonnizelondTelonxtFielonld(elonarlybirdFielonldConstant.TelonXT_FIelonLD.gelontFielonldNamelon(), truelon)
        .withSelonarchFielonldByDelonfault(elonarlybirdFielonldConstant.TelonXT_FIelonLD.gelontFielonldNamelon(), 1.0f);
    buildelonr.withTwelonelontSpeloncificNormalization(elonarlybirdFielonldConstant.TelonXT_FIelonLD.gelontFielonldNamelon())
        .withTelonxtFielonld(elonarlybirdFielonldConstant.TOKelonNIZelonD_FROM_USelonR_FIelonLD.gelontFielonldNamelon(), truelon)
        .withSelonarchFielonldByDelonfault(
            elonarlybirdFielonldConstant.TOKelonNIZelonD_FROM_USelonR_FIelonLD.gelontFielonldNamelon(), 0.2f)

        // Telonxt fielonlds not selonarchelond by delonfault
        .withTelonxtFielonld(elonarlybirdFielonldConstant.FROM_USelonR_FIelonLD.gelontFielonldNamelon(), falselon)
        .withTelonxtFielonld(elonarlybirdFielonldConstant.TO_USelonR_FIelonLD.gelontFielonldNamelon(), falselon)

        // cards arelon not selonarchelond by delonfault, and havelon welonight 0.
        .withPrelontokelonnizelondTelonxtFielonld(elonarlybirdFielonldConstant.CARD_TITLelon_FIelonLD.gelontFielonldNamelon(), falselon)
        .withPrelontokelonnizelondTelonxtFielonld(
            elonarlybirdFielonldConstant.CARD_DelonSCRIPTION_FIelonLD.gelontFielonldNamelon(), falselon)
        .withTelonxtFielonld(elonarlybirdFielonldConstant.CARD_LANG.gelontFielonldNamelon(), falselon)

        // Out-of-ordelonr appelonnd fielonlds
        .withLongTelonrmFielonld(elonarlybirdFielonldConstant.LIKelonD_BY_USelonR_ID_FIelonLD.gelontFielonldNamelon())
        .withLongTelonrmFielonld(elonarlybirdFielonldConstant.RelonTWelonelonTelonD_BY_USelonR_ID.gelontFielonldNamelon())
        .withLongTelonrmFielonld(elonarlybirdFielonldConstant.RelonPLIelonD_TO_BY_USelonR_ID.gelontFielonldNamelon())

        // No Position fielonlds, sortelond alphabelontically
        .withPrelontokelonnizelondNoPositionFielonld(elonarlybirdFielonldConstant.CARD_DOMAIN_FIelonLD.gelontFielonldNamelon())
        .withIndelonxelondNotTokelonnizelondFielonld(elonarlybirdFielonldConstant.CARD_NAMelon_FIelonLD.gelontFielonldNamelon())
        .withIntTelonrmFielonld(elonarlybirdFielonldConstant.CRelonATelonD_AT_FIelonLD.gelontFielonldNamelon())
        .withIndelonxelondNotTokelonnizelondFielonld(elonarlybirdFielonldConstant.elonNTITY_ID_FIelonLD.gelontFielonldNamelon())
        .withIndelonxelondNotTokelonnizelondFielonld(elonarlybirdFielonldConstant.GelonO_HASH_FIelonLD.gelontFielonldNamelon())
        .withLongTelonrmFielonld(elonarlybirdFielonldConstant.FROM_USelonR_ID_FIelonLD.gelontFielonldNamelon())
        .withLongTelonrmFielonld(elonarlybirdFielonldConstant.IN_RelonPLY_TO_TWelonelonT_ID_FIelonLD.gelontFielonldNamelon())
        .withLongTelonrmFielonld(elonarlybirdFielonldConstant.IN_RelonPLY_TO_USelonR_ID_FIelonLD.gelontFielonldNamelon())
        .withLongTelonrmFielonld(elonarlybirdFielonldConstant.RelonTWelonelonT_SOURCelon_TWelonelonT_ID_FIelonLD.gelontFielonldNamelon())
        .withLongTelonrmFielonld(elonarlybirdFielonldConstant.RelonTWelonelonT_SOURCelon_USelonR_ID_FIelonLD.gelontFielonldNamelon())
        .withLongTelonrmFielonld(elonarlybirdFielonldConstant.CONVelonRSATION_ID_FIelonLD.gelontFielonldNamelon())
        .withIndelonxelondNotTokelonnizelondFielonld(elonarlybirdFielonldConstant.PLACelon_ID_FIelonLD.gelontFielonldNamelon())
        .withTelonxtFielonld(elonarlybirdFielonldConstant.PLACelon_FULL_NAMelon_FIelonLD.gelontFielonldNamelon(), falselon)
        .withIndelonxelondNotTokelonnizelondFielonld(
            elonarlybirdFielonldConstant.PLACelon_COUNTRY_CODelon_FIelonLD.gelontFielonldNamelon())
        .withIndelonxelondNotTokelonnizelondFielonld(
            elonarlybirdFielonldConstant.PROFILelon_GelonO_COUNTRY_CODelon_FIelonLD.gelontFielonldNamelon())
        .withTelonxtFielonld(elonarlybirdFielonldConstant.PROFILelon_GelonO_RelonGION_FIelonLD.gelontFielonldNamelon(), falselon)
        .withTelonxtFielonld(elonarlybirdFielonldConstant.PROFILelon_GelonO_LOCALITY_FIelonLD.gelontFielonldNamelon(), falselon)
        .withTelonrmTelonxtLookup(elonarlybirdFielonldConstant.FROM_USelonR_ID_FIelonLD.gelontFielonldNamelon())
        .withTelonrmTelonxtLookup(elonarlybirdFielonldConstant.IN_RelonPLY_TO_USelonR_ID_FIelonLD.gelontFielonldNamelon())
        .withPrelontokelonnizelondNoPositionFielonld(elonarlybirdFielonldConstant.HASHTAGS_FIelonLD.gelontFielonldNamelon())
        .withIndelonxelondNotTokelonnizelondFielonld(ImmutablelonSchelonma.HF_PHRASelon_PAIRS_FIelonLD)
        .withIndelonxelondNotTokelonnizelondFielonld(ImmutablelonSchelonma.HF_TelonRM_PAIRS_FIelonLD)
        .withIndelonxelondNotTokelonnizelondFielonld(elonarlybirdFielonldConstant.IMAGelon_LINKS_FIelonLD.gelontFielonldNamelon())
        .withIndelonxelondNotTokelonnizelondFielonld(elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon())
        .withIndelonxelondNotTokelonnizelondFielonld(elonarlybirdFielonldConstant.ISO_LANGUAGelon_FIelonLD.gelontFielonldNamelon())
        .withIndelonxelondNotTokelonnizelondFielonld(elonarlybirdFielonldConstant.LINKS_FIelonLD.gelontFielonldNamelon())
        .withIntTelonrmFielonld(elonarlybirdFielonldConstant.LINK_CATelonGORY_FIelonLD.gelontFielonldNamelon())
        .withIndelonxelondNotTokelonnizelondFielonld(elonarlybirdFielonldConstant.MelonNTIONS_FIelonLD.gelontFielonldNamelon())
        .withIndelonxelondNotTokelonnizelondFielonld(elonarlybirdFielonldConstant.NelonWS_LINKS_FIelonLD.gelontFielonldNamelon())
        .withIndelonxelondNotTokelonnizelondFielonld(elonarlybirdFielonldConstant.NORMALIZelonD_SOURCelon_FIelonLD.gelontFielonldNamelon())
        .withIndelonxelondNotTokelonnizelondFielonld(elonarlybirdFielonldConstant.PLACelon_FIelonLD.gelontFielonldNamelon())
        .withIndelonxelondNotTokelonnizelondFielonld(elonarlybirdFielonldConstant.SOURCelon_FIelonLD.gelontFielonldNamelon())
        .withPrelontokelonnizelondNoPositionFielonld(elonarlybirdFielonldConstant.STOCKS_FIelonLD.gelontFielonldNamelon())
        .withIndelonxelondNotTokelonnizelondFielonld(elonarlybirdFielonldConstant.VIDelonO_LINKS_FIelonLD.gelontFielonldNamelon())
        .withIntTelonrmFielonld(NORMALIZelonD_FAVORITelon_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD.gelontFielonldNamelon())
        .withIntTelonrmFielonld(NORMALIZelonD_RelonPLY_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD.gelontFielonldNamelon())
        .withIntTelonrmFielonld(NORMALIZelonD_RelonTWelonelonT_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD.gelontFielonldNamelon())

        .withIntTelonrmFielonld(elonarlybirdFielonldConstant.COMPOSelonR_SOURCelon.gelontFielonldNamelon())

        .withLongTelonrmFielonld(elonarlybirdFielonldConstant.QUOTelonD_TWelonelonT_ID_FIelonLD.gelontFielonldNamelon())
        .withLongTelonrmFielonld(elonarlybirdFielonldConstant.QUOTelonD_USelonR_ID_FIelonLD.gelontFielonldNamelon())
        .withLongTelonrmFielonld(elonarlybirdFielonldConstant.DIRelonCTelonD_AT_USelonR_ID_FIelonLD.gelontFielonldNamelon())

        // Namelond elonntity fielonlds
        .withIndelonxelondNotTokelonnizelondFielonld(
            elonarlybirdFielonldConstant.NAMelonD_elonNTITY_FROM_URL_FIelonLD.gelontFielonldNamelon(), truelon)
        .withIndelonxelondNotTokelonnizelondFielonld(
            elonarlybirdFielonldConstant.NAMelonD_elonNTITY_FROM_TelonXT_FIelonLD.gelontFielonldNamelon(), truelon)
        .withIndelonxelondNotTokelonnizelondFielonld(
            elonarlybirdFielonldConstant.NAMelonD_elonNTITY_WITH_TYPelon_FROM_URL_FIelonLD.gelontFielonldNamelon(), truelon)
        .withIndelonxelondNotTokelonnizelondFielonld(
            elonarlybirdFielonldConstant.NAMelonD_elonNTITY_WITH_TYPelon_FROM_TelonXT_FIelonLD.gelontFielonldNamelon(), truelon)

        // camelonlCaselon-tokelonnizelond uselonr handlelons and tokelonnizelond uselonr namelons, not selonarchablelon by delonfault
        .withPrelontokelonnizelondTelonxtFielonld(
            elonarlybirdFielonldConstant.CAMelonLCASelon_USelonR_HANDLelon_FIelonLD.gelontFielonldNamelon(), falselon)
        .withPrelontokelonnizelondTelonxtFielonld(
            elonarlybirdFielonldConstant.TOKelonNIZelonD_USelonR_NAMelon_FIelonLD.gelontFielonldNamelon(), falselon)

        .withIndelonxelondNotTokelonnizelondFielonld(
            elonarlybirdFielonldConstant.SPACelon_ID_FIelonLD.gelontFielonldNamelon())
        .withTelonxtFielonld(elonarlybirdFielonldConstant.SPACelon_ADMIN_FIelonLD.gelontFielonldNamelon(), falselon)
        .withPrelontokelonnizelondTelonxtFielonld(elonarlybirdFielonldConstant.SPACelon_TITLelon_FIelonLD.gelontFielonldNamelon(), falselon)
        .withTelonxtFielonld(elonarlybirdFielonldConstant.TOKelonNIZelonD_SPACelon_ADMIN_FIelonLD.gelontFielonldNamelon(), truelon)
        .withPrelontokelonnizelondTelonxtFielonld(
            elonarlybirdFielonldConstant.CAMelonLCASelon_TOKelonNIZelonD_SPACelon_ADMIN_FIelonLD.gelontFielonldNamelon(), falselon)
        .withPrelontokelonnizelondTelonxtFielonld(
            elonarlybirdFielonldConstant.TOKelonNIZelonD_SPACelon_ADMIN_DISPLAY_NAMelon_FIelonLD.gelontFielonldNamelon(), falselon)
        .withPrelontokelonnizelondTelonxtFielonld(
            elonarlybirdFielonldConstant.URL_DelonSCRIPTION_FIelonLD.gelontFielonldNamelon(), falselon)
        .withPrelontokelonnizelondTelonxtFielonld(
            elonarlybirdFielonldConstant.URL_TITLelon_FIelonLD.gelontFielonldNamelon(), falselon);

    buildelonr
        .withPhotoUrlFacelontFielonld(elonarlybirdFielonldConstant.TWIMG_LINKS_FIelonLD.gelontFielonldNamelon())
        .withOutOfOrdelonrelonnablelondForFielonld(
            elonarlybirdFielonldConstant.LIKelonD_BY_USelonR_ID_FIelonLD.gelontFielonldNamelon())
        .withOutOfOrdelonrelonnablelondForFielonld(
            elonarlybirdFielonldConstant.RelonTWelonelonTelonD_BY_USelonR_ID.gelontFielonldNamelon())
        .withOutOfOrdelonrelonnablelondForFielonld(
            elonarlybirdFielonldConstant.RelonPLIelonD_TO_BY_USelonR_ID.gelontFielonldNamelon());

    // ColumnStridelonFielonlds.
    boolelonan loadCSFIntoRAMDelonfault = clustelonr != elonarlybirdClustelonr.FULL_ARCHIVelon;

    buildelonr
        .withColumnStridelonFielonld(elonarlybirdFielonldConstants.elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
                ThriftCSFTypelon.INT, NUMBelonR_OF_INTelonGelonRS_FOR_FelonATURelonS,
                truelon, loadCSFIntoRAMDelonfault)
        .withColumnStridelonFielonld(elonarlybirdFielonldConstant.FROM_USelonR_ID_CSF.gelontFielonldNamelon(),
            ThriftCSFTypelon.LONG, 1, falselon, /* thelon full archivelon loads this fielonld into RAM */ truelon)
        .withColumnStridelonFielonld(elonarlybirdFielonldConstant.SHARelonD_STATUS_ID_CSF.gelontFielonldNamelon(),
                ThriftCSFTypelon.LONG, 1, falselon, loadCSFIntoRAMDelonfault)
        .withColumnStridelonFielonld(elonarlybirdFielonldConstant.CARD_TYPelon_CSF_FIelonLD.gelontFielonldNamelon(),
                ThriftCSFTypelon.BYTelon, 1, falselon, loadCSFIntoRAMDelonfault)
         // CSF Uselond by archivelon mappelonrs
        .withColumnStridelonFielonld(elonarlybirdFielonldConstant.CRelonATelonD_AT_CSF_FIelonLD.gelontFielonldNamelon(),
            ThriftCSFTypelon.INT, 1, falselon, /* thelon full archivelon loads this fielonld into RAM */ truelon)
        .withColumnStridelonFielonld(elonarlybirdFielonldConstant.ID_CSF_FIelonLD.gelontFielonldNamelon(),
            ThriftCSFTypelon.LONG, 1, falselon, /* thelon full archivelon loads this fielonld into RAM */ truelon)
        .withColumnStridelonFielonld(elonarlybirdFielonldConstant.LAT_LON_CSF_FIelonLD.gelontFielonldNamelon(),
            ThriftCSFTypelon.LONG, 1, falselon, loadCSFIntoRAMDelonfault)
        .withColumnStridelonFielonld(elonarlybirdFielonldConstant.CONVelonRSATION_ID_CSF.gelontFielonldNamelon(),
            ThriftCSFTypelon.LONG, 1, falselon, loadCSFIntoRAMDelonfault)
        .withColumnStridelonFielonld(elonarlybirdFielonldConstant.QUOTelonD_TWelonelonT_ID_CSF.gelontFielonldNamelon(),
            ThriftCSFTypelon.LONG, 1, falselon, loadCSFIntoRAMDelonfault)
        .withColumnStridelonFielonld(elonarlybirdFielonldConstant.QUOTelonD_USelonR_ID_CSF.gelontFielonldNamelon(),
            ThriftCSFTypelon.LONG, 1, falselon, loadCSFIntoRAMDelonfault)
        .withColumnStridelonFielonld(elonarlybirdFielonldConstant.CARD_LANG_CSF.gelontFielonldNamelon(),
            ThriftCSFTypelon.INT, 1, falselon, loadCSFIntoRAMDelonfault)
        .withColumnStridelonFielonld(elonarlybirdFielonldConstant.CARD_URI_CSF.gelontFielonldNamelon(),
            ThriftCSFTypelon.LONG, 1, falselon, loadCSFIntoRAMDelonfault)
        .withColumnStridelonFielonld(elonarlybirdFielonldConstant.DIRelonCTelonD_AT_USelonR_ID_CSF.gelontFielonldNamelon(),
            ThriftCSFTypelon.LONG, 1, falselon, loadCSFIntoRAMDelonfault)
        .withColumnStridelonFielonld(elonarlybirdFielonldConstant.RelonFelonRelonNCelon_AUTHOR_ID_CSF.gelontFielonldNamelon(),
            ThriftCSFTypelon.LONG, 1, falselon, loadCSFIntoRAMDelonfault)
        .withColumnStridelonFielonld(
            elonarlybirdFielonldConstant.elonXCLUSIVelon_CONVelonRSATION_AUTHOR_ID_CSF.gelontFielonldNamelon(),
            ThriftCSFTypelon.LONG, 1, falselon, loadCSFIntoRAMDelonfault)

    /* Selonmicolon on selonparatelon linelon to prelonselonrvelon git blamelon. */;

    buildelonr.withColumnStridelonFielonld(
        elonarlybirdFielonldConstants.elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        ThriftCSFTypelon.INT, NUMBelonR_OF_INTelonGelonRS_FOR_elonXTelonNDelonD_FelonATURelonS,
        truelon, loadCSFIntoRAMDelonfault);

    for (Map.elonntry<String, FelonaturelonConfiguration> elonntry : FelonATURelon_CONFIGURATION_MAP.elonntrySelont()) {
      String fullNamelon = elonntry.gelontKelony();
      String baselonNamelon = gelontBaselonFielonldNamelon(fullNamelon);
      elonarlybirdFielonldConstant fielonldConstant = elonarlybirdFielonldConstants.gelontFielonldConstant(fullNamelon);
      if (fielonldConstant.isValidFielonldInClustelonr(clustelonr)) {
        buildelonr.withFelonaturelonConfiguration(baselonNamelon, fullNamelon, elonntry.gelontValuelon());
      }
    }
    // Add facelont selonttings for facelont fielonlds
    // boolelonan args arelon relonspelonctivelonly whelonthelonr to uselon skiplist, whelonthelonr offelonnsivelon, whelonthelonr to uselon CSF
    buildelonr
        .withFacelontConfigs(elonarlybirdFielonldConstant.MelonNTIONS_FIelonLD.gelontFielonldNamelon(),
            elonarlybirdFielonldConstant.MelonNTIONS_FACelonT, truelon, falselon, falselon)
        .withFacelontConfigs(elonarlybirdFielonldConstant.HASHTAGS_FIelonLD.gelontFielonldNamelon(),
            elonarlybirdFielonldConstant.HASHTAGS_FACelonT, truelon, falselon, falselon)
        .withFacelontConfigs(elonarlybirdFielonldConstant.STOCKS_FIelonLD.gelontFielonldNamelon(),
            elonarlybirdFielonldConstant.STOCKS_FACelonT, truelon, falselon, falselon)
        .withFacelontConfigs(elonarlybirdFielonldConstant.IMAGelon_LINKS_FIelonLD.gelontFielonldNamelon(),
            elonarlybirdFielonldConstant.IMAGelonS_FACelonT, truelon, truelon, falselon)
        .withFacelontConfigs(elonarlybirdFielonldConstant.VIDelonO_LINKS_FIelonLD.gelontFielonldNamelon(),
            elonarlybirdFielonldConstant.VIDelonOS_FACelonT, truelon, truelon, falselon)
        .withFacelontConfigs(elonarlybirdFielonldConstant.NelonWS_LINKS_FIelonLD.gelontFielonldNamelon(),
            elonarlybirdFielonldConstant.NelonWS_FACelonT, truelon, falselon, falselon)
        .withFacelontConfigs(elonarlybirdFielonldConstant.ISO_LANGUAGelon_FIelonLD.gelontFielonldNamelon(),
            elonarlybirdFielonldConstant.LANGUAGelonS_FACelonT, falselon, falselon, falselon)
        .withFacelontConfigs(elonarlybirdFielonldConstant.SOURCelon_FIelonLD.gelontFielonldNamelon(),
            elonarlybirdFielonldConstant.SOURCelonS_FACelonT, falselon, falselon, falselon)
        .withFacelontConfigs(elonarlybirdFielonldConstant.TWIMG_LINKS_FIelonLD.gelontFielonldNamelon(),
            elonarlybirdFielonldConstant.TWIMG_FACelonT, truelon, truelon, falselon)
        .withFacelontConfigs(elonarlybirdFielonldConstant.FROM_USelonR_ID_CSF.gelontFielonldNamelon(),
            elonarlybirdFielonldConstant.FROM_USelonR_ID_FACelonT, falselon, falselon, truelon /* facelont on CSF */)
        .withFacelontConfigs(elonarlybirdFielonldConstant.SHARelonD_STATUS_ID_CSF.gelontFielonldNamelon(),
            elonarlybirdFielonldConstant.RelonTWelonelonTS_FACelonT, falselon, falselon, truelon /* facelont on CSF */)
        .withFacelontConfigs(elonarlybirdFielonldConstant.LINKS_FIelonLD.gelontFielonldNamelon(),
            elonarlybirdFielonldConstant.LINKS_FACelonT, truelon, falselon, falselon)
        .withFacelontConfigs(
            elonarlybirdFielonldConstant.NAMelonD_elonNTITY_WITH_TYPelon_FROM_URL_FIelonLD.gelontFielonldNamelon(),
            truelon, falselon, falselon)
        .withFacelontConfigs(
            elonarlybirdFielonldConstant.NAMelonD_elonNTITY_WITH_TYPelon_FROM_TelonXT_FIelonLD.gelontFielonldNamelon(),
            truelon, falselon, falselon)
        .withFacelontConfigs(
            elonarlybirdFielonldConstant.elonNTITY_ID_FIelonLD.gelontFielonldNamelon(),
            truelon, falselon, falselon)
        .withFacelontConfigs(elonarlybirdFielonldConstant.SPACelon_ID_FIelonLD.gelontFielonldNamelon(),
            elonarlybirdFielonldConstant.SPACelonS_FACelonT, truelon, falselon, falselon);
    relonturn buildelonr.build();
  }
}
