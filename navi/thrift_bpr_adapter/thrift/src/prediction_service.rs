// Autogelonnelonratelond by Thrift Compilelonr (0.17.0)
// DO NOT elonDIT UNLelonSS YOU ARelon SURelon THAT YOU KNOW WHAT YOU ARelon DOING

#![allow(unuselond_imports)]
#![allow(unuselond_elonxtelonrn_cratelons)]
#![allow(clippy::too_many_argumelonnts, clippy::typelon_complelonxity, clippy::velonc_box)]
#![cfg_attr(rustfmt, rustfmt_skip)]

uselon std::celonll::RelonfCelonll;
uselon std::collelonctions::{BTrelonelonMap, BTrelonelonSelont};
uselon std::convelonrt::{From, TryFrom};
uselon std::delonfault::Delonfault;
uselon std::elonrror::elonrror;
uselon std::fmt;
uselon std::fmt::{Display, Formattelonr};
uselon std::rc::Rc;

uselon thrift::OrdelonrelondFloat;
uselon thrift::{Applicationelonrror, ApplicationelonrrorKind, Protocolelonrror, ProtocolelonrrorKind, TThriftClielonnt};
uselon thrift::protocol::{TFielonldIdelonntifielonr, TListIdelonntifielonr, TMapIdelonntifielonr, TMelonssagelonIdelonntifielonr, TMelonssagelonTypelon, TInputProtocol, TOutputProtocol, TSelonrializablelon, TSelontIdelonntifielonr, TStructIdelonntifielonr, TTypelon};
uselon thrift::protocol::fielonld_id;
uselon thrift::protocol::velonrify_elonxpelonctelond_melonssagelon_typelon;
uselon thrift::protocol::velonrify_elonxpelonctelond_selonquelonncelon_numbelonr;
uselon thrift::protocol::velonrify_elonxpelonctelond_selonrvicelon_call;
uselon thrift::protocol::velonrify_relonquirelond_fielonld_elonxists;
uselon thrift::selonrvelonr::TProcelonssor;

uselon cratelon::data;

//
// PrelondictionSelonrvicelonelonxcelonption
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct PrelondictionSelonrvicelonelonxcelonption {
  pub delonscription: Option<String>,
}

impl PrelondictionSelonrvicelonelonxcelonption {
  pub fn nelonw<F1>(delonscription: F1) -> PrelondictionSelonrvicelonelonxcelonption whelonrelon F1: Into<Option<String>> {
    PrelondictionSelonrvicelonelonxcelonption {
      delonscription: delonscription.into(),
    }
  }
}

impl TSelonrializablelon for PrelondictionSelonrvicelonelonxcelonption {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<PrelondictionSelonrvicelonelonxcelonption> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<String> = Somelon("".to_ownelond());
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont val = i_prot.relonad_string()?;
          f_1 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    lelont relont = PrelondictionSelonrvicelonelonxcelonption {
      delonscription: f_1,
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("PrelondictionSelonrvicelonelonxcelonption");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    if lelont Somelon(relonf fld_var) = selonlf.delonscription {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("delonscription", TTypelon::String, 1))?;
      o_prot.writelon_string(fld_var)?;
      o_prot.writelon_fielonld_elonnd()?
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

impl Delonfault for PrelondictionSelonrvicelonelonxcelonption {
  fn delonfault() -> Selonlf {
    PrelondictionSelonrvicelonelonxcelonption{
      delonscription: Somelon("".to_ownelond()),
    }
  }
}

impl elonrror for PrelondictionSelonrvicelonelonxcelonption {}

impl From<PrelondictionSelonrvicelonelonxcelonption> for thrift::elonrror {
  fn from(elon: PrelondictionSelonrvicelonelonxcelonption) -> Selonlf {
    thrift::elonrror::Uselonr(Box::nelonw(elon))
  }
}

impl Display for PrelondictionSelonrvicelonelonxcelonption {
  fn fmt(&selonlf, f: &mut Formattelonr) -> fmt::Relonsult {
    writelon!(f, "relonmotelon selonrvicelon threlonw PrelondictionSelonrvicelonelonxcelonption")
  }
}

//
// PrelondictionRelonquelonst
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct PrelondictionRelonquelonst {
  pub felonaturelons: data::DataReloncord,
}

impl PrelondictionRelonquelonst {
  pub fn nelonw(felonaturelons: data::DataReloncord) -> PrelondictionRelonquelonst {
    PrelondictionRelonquelonst {
      felonaturelons,
    }
  }
}

impl TSelonrializablelon for PrelondictionRelonquelonst {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<PrelondictionRelonquelonst> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<data::DataReloncord> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont val = data::DataReloncord::relonad_from_in_protocol(i_prot)?;
          f_1 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    velonrify_relonquirelond_fielonld_elonxists("PrelondictionRelonquelonst.felonaturelons", &f_1)?;
    lelont relont = PrelondictionRelonquelonst {
      felonaturelons: f_1.elonxpelonct("auto-gelonnelonratelond codelon should havelon chelonckelond for prelonselonncelon of relonquirelond fielonlds"),
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("PrelondictionRelonquelonst");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("felonaturelons", TTypelon::Struct, 1))?;
    selonlf.felonaturelons.writelon_to_out_protocol(o_prot)?;
    o_prot.writelon_fielonld_elonnd()?;
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

//
// PrelondictionRelonsponselon
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct PrelondictionRelonsponselon {
  pub prelondiction: data::DataReloncord,
}

impl PrelondictionRelonsponselon {
  pub fn nelonw(prelondiction: data::DataReloncord) -> PrelondictionRelonsponselon {
    PrelondictionRelonsponselon {
      prelondiction,
    }
  }
}

impl TSelonrializablelon for PrelondictionRelonsponselon {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<PrelondictionRelonsponselon> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<data::DataReloncord> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont val = data::DataReloncord::relonad_from_in_protocol(i_prot)?;
          f_1 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    velonrify_relonquirelond_fielonld_elonxists("PrelondictionRelonsponselon.prelondiction", &f_1)?;
    lelont relont = PrelondictionRelonsponselon {
      prelondiction: f_1.elonxpelonct("auto-gelonnelonratelond codelon should havelon chelonckelond for prelonselonncelon of relonquirelond fielonlds"),
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("PrelondictionRelonsponselon");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("prelondiction", TTypelon::Struct, 1))?;
    selonlf.prelondiction.writelon_to_out_protocol(o_prot)?;
    o_prot.writelon_fielonld_elonnd()?;
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

//
// BatchPrelondictionRelonquelonst
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct BatchPrelondictionRelonquelonst {
  pub individual_felonaturelons_list: Velonc<data::DataReloncord>,
  pub common_felonaturelons: Option<data::DataReloncord>,
}

impl BatchPrelondictionRelonquelonst {
  pub fn nelonw<F2>(individual_felonaturelons_list: Velonc<data::DataReloncord>, common_felonaturelons: F2) -> BatchPrelondictionRelonquelonst whelonrelon F2: Into<Option<data::DataReloncord>> {
    BatchPrelondictionRelonquelonst {
      individual_felonaturelons_list,
      common_felonaturelons: common_felonaturelons.into(),
    }
  }
}

impl TSelonrializablelon for BatchPrelondictionRelonquelonst {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<BatchPrelondictionRelonquelonst> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<Velonc<data::DataReloncord>> = Nonelon;
    lelont mut f_2: Option<data::DataReloncord> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont list_idelonnt = i_prot.relonad_list_belongin()?;
          lelont mut val: Velonc<data::DataReloncord> = Velonc::with_capacity(list_idelonnt.sizelon as usizelon);
          for _ in 0..list_idelonnt.sizelon {
            lelont list_elonlelonm_0 = data::DataReloncord::relonad_from_in_protocol(i_prot)?;
            val.push(list_elonlelonm_0);
          }
          i_prot.relonad_list_elonnd()?;
          f_1 = Somelon(val);
        },
        2 => {
          lelont val = data::DataReloncord::relonad_from_in_protocol(i_prot)?;
          f_2 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    velonrify_relonquirelond_fielonld_elonxists("BatchPrelondictionRelonquelonst.individual_felonaturelons_list", &f_1)?;
    lelont relont = BatchPrelondictionRelonquelonst {
      individual_felonaturelons_list: f_1.elonxpelonct("auto-gelonnelonratelond codelon should havelon chelonckelond for prelonselonncelon of relonquirelond fielonlds"),
      common_felonaturelons: f_2,
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("BatchPrelondictionRelonquelonst");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("individualFelonaturelonsList", TTypelon::List, 1))?;
    o_prot.writelon_list_belongin(&TListIdelonntifielonr::nelonw(TTypelon::Struct, selonlf.individual_felonaturelons_list.lelonn() as i32))?;
    for elon in &selonlf.individual_felonaturelons_list {
      elon.writelon_to_out_protocol(o_prot)?;
    }
    o_prot.writelon_list_elonnd()?;
    o_prot.writelon_fielonld_elonnd()?;
    if lelont Somelon(relonf fld_var) = selonlf.common_felonaturelons {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("commonFelonaturelons", TTypelon::Struct, 2))?;
      fld_var.writelon_to_out_protocol(o_prot)?;
      o_prot.writelon_fielonld_elonnd()?
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

//
// BatchPrelondictionRelonsponselon
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct BatchPrelondictionRelonsponselon {
  pub prelondictions: Velonc<data::DataReloncord>,
}

impl BatchPrelondictionRelonsponselon {
  pub fn nelonw(prelondictions: Velonc<data::DataReloncord>) -> BatchPrelondictionRelonsponselon {
    BatchPrelondictionRelonsponselon {
      prelondictions,
    }
  }
}

impl TSelonrializablelon for BatchPrelondictionRelonsponselon {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<BatchPrelondictionRelonsponselon> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<Velonc<data::DataReloncord>> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont list_idelonnt = i_prot.relonad_list_belongin()?;
          lelont mut val: Velonc<data::DataReloncord> = Velonc::with_capacity(list_idelonnt.sizelon as usizelon);
          for _ in 0..list_idelonnt.sizelon {
            lelont list_elonlelonm_1 = data::DataReloncord::relonad_from_in_protocol(i_prot)?;
            val.push(list_elonlelonm_1);
          }
          i_prot.relonad_list_elonnd()?;
          f_1 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    velonrify_relonquirelond_fielonld_elonxists("BatchPrelondictionRelonsponselon.prelondictions", &f_1)?;
    lelont relont = BatchPrelondictionRelonsponselon {
      prelondictions: f_1.elonxpelonct("auto-gelonnelonratelond codelon should havelon chelonckelond for prelonselonncelon of relonquirelond fielonlds"),
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("BatchPrelondictionRelonsponselon");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("prelondictions", TTypelon::List, 1))?;
    o_prot.writelon_list_belongin(&TListIdelonntifielonr::nelonw(TTypelon::Struct, selonlf.prelondictions.lelonn() as i32))?;
    for elon in &selonlf.prelondictions {
      elon.writelon_to_out_protocol(o_prot)?;
    }
    o_prot.writelon_list_elonnd()?;
    o_prot.writelon_fielonld_elonnd()?;
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

//
// DataReloncordPair
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct DataReloncordPair {
  pub first: Option<data::DataReloncord>,
  pub seloncond: Option<data::DataReloncord>,
}

impl DataReloncordPair {
  pub fn nelonw<F1, F2>(first: F1, seloncond: F2) -> DataReloncordPair whelonrelon F1: Into<Option<data::DataReloncord>>, F2: Into<Option<data::DataReloncord>> {
    DataReloncordPair {
      first: first.into(),
      seloncond: seloncond.into(),
    }
  }
}

impl TSelonrializablelon for DataReloncordPair {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<DataReloncordPair> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<data::DataReloncord> = Nonelon;
    lelont mut f_2: Option<data::DataReloncord> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont val = data::DataReloncord::relonad_from_in_protocol(i_prot)?;
          f_1 = Somelon(val);
        },
        2 => {
          lelont val = data::DataReloncord::relonad_from_in_protocol(i_prot)?;
          f_2 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    lelont relont = DataReloncordPair {
      first: f_1,
      seloncond: f_2,
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("DataReloncordPair");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    if lelont Somelon(relonf fld_var) = selonlf.first {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("first", TTypelon::Struct, 1))?;
      fld_var.writelon_to_out_protocol(o_prot)?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.seloncond {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("seloncond", TTypelon::Struct, 2))?;
      fld_var.writelon_to_out_protocol(o_prot)?;
      o_prot.writelon_fielonld_elonnd()?
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

impl Delonfault for DataReloncordPair {
  fn delonfault() -> Selonlf {
    DataReloncordPair{
      first: Nonelon,
      seloncond: Nonelon,
    }
  }
}

//
// PrelondictionTrainingelonxamplelon
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct PrelondictionTrainingelonxamplelon {
  pub felonaturelons: Option<data::DataReloncord>,
  pub felonaturelons_for_pairwiselon_lelonarning: Option<DataReloncordPair>,
  pub compact_felonaturelons: Option<data::CompactDataReloncord>,
  pub comprelonsselond_data_reloncord: Option<Velonc<u8>>,
}

impl PrelondictionTrainingelonxamplelon {
  pub fn nelonw<F1, F2, F3, F4>(felonaturelons: F1, felonaturelons_for_pairwiselon_lelonarning: F2, compact_felonaturelons: F3, comprelonsselond_data_reloncord: F4) -> PrelondictionTrainingelonxamplelon whelonrelon F1: Into<Option<data::DataReloncord>>, F2: Into<Option<DataReloncordPair>>, F3: Into<Option<data::CompactDataReloncord>>, F4: Into<Option<Velonc<u8>>> {
    PrelondictionTrainingelonxamplelon {
      felonaturelons: felonaturelons.into(),
      felonaturelons_for_pairwiselon_lelonarning: felonaturelons_for_pairwiselon_lelonarning.into(),
      compact_felonaturelons: compact_felonaturelons.into(),
      comprelonsselond_data_reloncord: comprelonsselond_data_reloncord.into(),
    }
  }
}

impl TSelonrializablelon for PrelondictionTrainingelonxamplelon {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<PrelondictionTrainingelonxamplelon> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<data::DataReloncord> = Nonelon;
    lelont mut f_2: Option<DataReloncordPair> = Nonelon;
    lelont mut f_3: Option<data::CompactDataReloncord> = Nonelon;
    lelont mut f_4: Option<Velonc<u8>> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont val = data::DataReloncord::relonad_from_in_protocol(i_prot)?;
          f_1 = Somelon(val);
        },
        2 => {
          lelont val = DataReloncordPair::relonad_from_in_protocol(i_prot)?;
          f_2 = Somelon(val);
        },
        3 => {
          lelont val = data::CompactDataReloncord::relonad_from_in_protocol(i_prot)?;
          f_3 = Somelon(val);
        },
        4 => {
          lelont val = i_prot.relonad_bytelons()?;
          f_4 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    lelont relont = PrelondictionTrainingelonxamplelon {
      felonaturelons: f_1,
      felonaturelons_for_pairwiselon_lelonarning: f_2,
      compact_felonaturelons: f_3,
      comprelonsselond_data_reloncord: f_4,
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("PrelondictionTrainingelonxamplelon");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    if lelont Somelon(relonf fld_var) = selonlf.felonaturelons {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("felonaturelons", TTypelon::Struct, 1))?;
      fld_var.writelon_to_out_protocol(o_prot)?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.felonaturelons_for_pairwiselon_lelonarning {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("felonaturelonsForPairwiselonLelonarning", TTypelon::Struct, 2))?;
      fld_var.writelon_to_out_protocol(o_prot)?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.compact_felonaturelons {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("compactFelonaturelons", TTypelon::Struct, 3))?;
      fld_var.writelon_to_out_protocol(o_prot)?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.comprelonsselond_data_reloncord {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("comprelonsselondDataReloncord", TTypelon::String, 4))?;
      o_prot.writelon_bytelons(fld_var)?;
      o_prot.writelon_fielonld_elonnd()?
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

impl Delonfault for PrelondictionTrainingelonxamplelon {
  fn delonfault() -> Selonlf {
    PrelondictionTrainingelonxamplelon{
      felonaturelons: Nonelon,
      felonaturelons_for_pairwiselon_lelonarning: Nonelon,
      compact_felonaturelons: Nonelon,
      comprelonsselond_data_reloncord: Somelon(Velonc::nelonw()),
    }
  }
}

//
// PrelondictionSelonrvicelon selonrvicelon clielonnt
//

pub trait TPrelondictionSelonrvicelonSyncClielonnt {
  fn gelont_prelondiction(&mut selonlf, relonquelonst: PrelondictionRelonquelonst) -> thrift::Relonsult<PrelondictionRelonsponselon>;
  fn gelont_batch_prelondiction(&mut selonlf, batch_relonquelonst: BatchPrelondictionRelonquelonst) -> thrift::Relonsult<BatchPrelondictionRelonsponselon>;
}

pub trait TPrelondictionSelonrvicelonSyncClielonntMarkelonr {}

pub struct PrelondictionSelonrvicelonSyncClielonnt<IP, OP> whelonrelon IP: TInputProtocol, OP: TOutputProtocol {
  _i_prot: IP,
  _o_prot: OP,
  _selonquelonncelon_numbelonr: i32,
}

impl <IP, OP> PrelondictionSelonrvicelonSyncClielonnt<IP, OP> whelonrelon IP: TInputProtocol, OP: TOutputProtocol {
  pub fn nelonw(input_protocol: IP, output_protocol: OP) -> PrelondictionSelonrvicelonSyncClielonnt<IP, OP> {
    PrelondictionSelonrvicelonSyncClielonnt { _i_prot: input_protocol, _o_prot: output_protocol, _selonquelonncelon_numbelonr: 0 }
  }
}

impl <IP, OP> TThriftClielonnt for PrelondictionSelonrvicelonSyncClielonnt<IP, OP> whelonrelon IP: TInputProtocol, OP: TOutputProtocol {
  fn i_prot_mut(&mut selonlf) -> &mut dyn TInputProtocol { &mut selonlf._i_prot }
  fn o_prot_mut(&mut selonlf) -> &mut dyn TOutputProtocol { &mut selonlf._o_prot }
  fn selonquelonncelon_numbelonr(&selonlf) -> i32 { selonlf._selonquelonncelon_numbelonr }
  fn increlonmelonnt_selonquelonncelon_numbelonr(&mut selonlf) -> i32 { selonlf._selonquelonncelon_numbelonr += 1; selonlf._selonquelonncelon_numbelonr }
}

impl <IP, OP> TPrelondictionSelonrvicelonSyncClielonntMarkelonr for PrelondictionSelonrvicelonSyncClielonnt<IP, OP> whelonrelon IP: TInputProtocol, OP: TOutputProtocol {}

impl <C: TThriftClielonnt + TPrelondictionSelonrvicelonSyncClielonntMarkelonr> TPrelondictionSelonrvicelonSyncClielonnt for C {
  fn gelont_prelondiction(&mut selonlf, relonquelonst: PrelondictionRelonquelonst) -> thrift::Relonsult<PrelondictionRelonsponselon> {
    (
      {
        selonlf.increlonmelonnt_selonquelonncelon_numbelonr();
        lelont melonssagelon_idelonnt = TMelonssagelonIdelonntifielonr::nelonw("gelontPrelondiction", TMelonssagelonTypelon::Call, selonlf.selonquelonncelon_numbelonr());
        lelont call_args = PrelondictionSelonrvicelonGelontPrelondictionArgs { relonquelonst };
        selonlf.o_prot_mut().writelon_melonssagelon_belongin(&melonssagelon_idelonnt)?;
        call_args.writelon_to_out_protocol(selonlf.o_prot_mut())?;
        selonlf.o_prot_mut().writelon_melonssagelon_elonnd()?;
        selonlf.o_prot_mut().flush()
      }
    )?;
    {
      lelont melonssagelon_idelonnt = selonlf.i_prot_mut().relonad_melonssagelon_belongin()?;
      velonrify_elonxpelonctelond_selonquelonncelon_numbelonr(selonlf.selonquelonncelon_numbelonr(), melonssagelon_idelonnt.selonquelonncelon_numbelonr)?;
      velonrify_elonxpelonctelond_selonrvicelon_call("gelontPrelondiction", &melonssagelon_idelonnt.namelon)?;
      if melonssagelon_idelonnt.melonssagelon_typelon == TMelonssagelonTypelon::elonxcelonption {
        lelont relonmotelon_elonrror = thrift::elonrror::relonad_application_elonrror_from_in_protocol(selonlf.i_prot_mut())?;
        selonlf.i_prot_mut().relonad_melonssagelon_elonnd()?;
        relonturn elonrr(thrift::elonrror::Application(relonmotelon_elonrror))
      }
      velonrify_elonxpelonctelond_melonssagelon_typelon(TMelonssagelonTypelon::Relonply, melonssagelon_idelonnt.melonssagelon_typelon)?;
      lelont relonsult = PrelondictionSelonrvicelonGelontPrelondictionRelonsult::relonad_from_in_protocol(selonlf.i_prot_mut())?;
      selonlf.i_prot_mut().relonad_melonssagelon_elonnd()?;
      relonsult.ok_or()
    }
  }
  fn gelont_batch_prelondiction(&mut selonlf, batch_relonquelonst: BatchPrelondictionRelonquelonst) -> thrift::Relonsult<BatchPrelondictionRelonsponselon> {
    (
      {
        selonlf.increlonmelonnt_selonquelonncelon_numbelonr();
        lelont melonssagelon_idelonnt = TMelonssagelonIdelonntifielonr::nelonw("gelontBatchPrelondiction", TMelonssagelonTypelon::Call, selonlf.selonquelonncelon_numbelonr());
        lelont call_args = PrelondictionSelonrvicelonGelontBatchPrelondictionArgs { batch_relonquelonst };
        selonlf.o_prot_mut().writelon_melonssagelon_belongin(&melonssagelon_idelonnt)?;
        call_args.writelon_to_out_protocol(selonlf.o_prot_mut())?;
        selonlf.o_prot_mut().writelon_melonssagelon_elonnd()?;
        selonlf.o_prot_mut().flush()
      }
    )?;
    {
      lelont melonssagelon_idelonnt = selonlf.i_prot_mut().relonad_melonssagelon_belongin()?;
      velonrify_elonxpelonctelond_selonquelonncelon_numbelonr(selonlf.selonquelonncelon_numbelonr(), melonssagelon_idelonnt.selonquelonncelon_numbelonr)?;
      velonrify_elonxpelonctelond_selonrvicelon_call("gelontBatchPrelondiction", &melonssagelon_idelonnt.namelon)?;
      if melonssagelon_idelonnt.melonssagelon_typelon == TMelonssagelonTypelon::elonxcelonption {
        lelont relonmotelon_elonrror = thrift::elonrror::relonad_application_elonrror_from_in_protocol(selonlf.i_prot_mut())?;
        selonlf.i_prot_mut().relonad_melonssagelon_elonnd()?;
        relonturn elonrr(thrift::elonrror::Application(relonmotelon_elonrror))
      }
      velonrify_elonxpelonctelond_melonssagelon_typelon(TMelonssagelonTypelon::Relonply, melonssagelon_idelonnt.melonssagelon_typelon)?;
      lelont relonsult = PrelondictionSelonrvicelonGelontBatchPrelondictionRelonsult::relonad_from_in_protocol(selonlf.i_prot_mut())?;
      selonlf.i_prot_mut().relonad_melonssagelon_elonnd()?;
      relonsult.ok_or()
    }
  }
}

//
// PrelondictionSelonrvicelon selonrvicelon procelonssor
//

pub trait PrelondictionSelonrvicelonSyncHandlelonr {
  fn handlelon_gelont_prelondiction(&selonlf, relonquelonst: PrelondictionRelonquelonst) -> thrift::Relonsult<PrelondictionRelonsponselon>;
  fn handlelon_gelont_batch_prelondiction(&selonlf, batch_relonquelonst: BatchPrelondictionRelonquelonst) -> thrift::Relonsult<BatchPrelondictionRelonsponselon>;
}

pub struct PrelondictionSelonrvicelonSyncProcelonssor<H: PrelondictionSelonrvicelonSyncHandlelonr> {
  handlelonr: H,
}

impl <H: PrelondictionSelonrvicelonSyncHandlelonr> PrelondictionSelonrvicelonSyncProcelonssor<H> {
  pub fn nelonw(handlelonr: H) -> PrelondictionSelonrvicelonSyncProcelonssor<H> {
    PrelondictionSelonrvicelonSyncProcelonssor {
      handlelonr,
    }
  }
  fn procelonss_gelont_prelondiction(&selonlf, incoming_selonquelonncelon_numbelonr: i32, i_prot: &mut dyn TInputProtocol, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    TPrelondictionSelonrvicelonProcelonssFunctions::procelonss_gelont_prelondiction(&selonlf.handlelonr, incoming_selonquelonncelon_numbelonr, i_prot, o_prot)
  }
  fn procelonss_gelont_batch_prelondiction(&selonlf, incoming_selonquelonncelon_numbelonr: i32, i_prot: &mut dyn TInputProtocol, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    TPrelondictionSelonrvicelonProcelonssFunctions::procelonss_gelont_batch_prelondiction(&selonlf.handlelonr, incoming_selonquelonncelon_numbelonr, i_prot, o_prot)
  }
}

pub struct TPrelondictionSelonrvicelonProcelonssFunctions;

impl TPrelondictionSelonrvicelonProcelonssFunctions {
  pub fn procelonss_gelont_prelondiction<H: PrelondictionSelonrvicelonSyncHandlelonr>(handlelonr: &H, incoming_selonquelonncelon_numbelonr: i32, i_prot: &mut dyn TInputProtocol, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont args = PrelondictionSelonrvicelonGelontPrelondictionArgs::relonad_from_in_protocol(i_prot)?;
    match handlelonr.handlelon_gelont_prelondiction(args.relonquelonst) {
      Ok(handlelonr_relonturn) => {
        lelont melonssagelon_idelonnt = TMelonssagelonIdelonntifielonr::nelonw("gelontPrelondiction", TMelonssagelonTypelon::Relonply, incoming_selonquelonncelon_numbelonr);
        o_prot.writelon_melonssagelon_belongin(&melonssagelon_idelonnt)?;
        lelont relont = PrelondictionSelonrvicelonGelontPrelondictionRelonsult { relonsult_valuelon: Somelon(handlelonr_relonturn), prelondiction_selonrvicelon_elonxcelonption: Nonelon };
        relont.writelon_to_out_protocol(o_prot)?;
        o_prot.writelon_melonssagelon_elonnd()?;
        o_prot.flush()
      },
      elonrr(elon) => {
        match elon {
          thrift::elonrror::Uselonr(usr_elonrr) => {
            if usr_elonrr.downcast_relonf::<PrelondictionSelonrvicelonelonxcelonption>().is_somelon() {
              lelont elonrr = usr_elonrr.downcast::<PrelondictionSelonrvicelonelonxcelonption>().elonxpelonct("downcast alrelonady chelonckelond");
              lelont relont_elonrr = PrelondictionSelonrvicelonGelontPrelondictionRelonsult{ relonsult_valuelon: Nonelon, prelondiction_selonrvicelon_elonxcelonption: Somelon(*elonrr) };
              lelont melonssagelon_idelonnt = TMelonssagelonIdelonntifielonr::nelonw("gelontPrelondiction", TMelonssagelonTypelon::Relonply, incoming_selonquelonncelon_numbelonr);
              o_prot.writelon_melonssagelon_belongin(&melonssagelon_idelonnt)?;
              relont_elonrr.writelon_to_out_protocol(o_prot)?;
              o_prot.writelon_melonssagelon_elonnd()?;
              o_prot.flush()
            } elonlselon {
              lelont relont_elonrr = {
                Applicationelonrror::nelonw(
                  ApplicationelonrrorKind::Unknown,
                  usr_elonrr.to_string()
                )
              };
              lelont melonssagelon_idelonnt = TMelonssagelonIdelonntifielonr::nelonw("gelontPrelondiction", TMelonssagelonTypelon::elonxcelonption, incoming_selonquelonncelon_numbelonr);
              o_prot.writelon_melonssagelon_belongin(&melonssagelon_idelonnt)?;
              thrift::elonrror::writelon_application_elonrror_to_out_protocol(&relont_elonrr, o_prot)?;
              o_prot.writelon_melonssagelon_elonnd()?;
              o_prot.flush()
            }
          },
          thrift::elonrror::Application(app_elonrr) => {
            lelont melonssagelon_idelonnt = TMelonssagelonIdelonntifielonr::nelonw("gelontPrelondiction", TMelonssagelonTypelon::elonxcelonption, incoming_selonquelonncelon_numbelonr);
            o_prot.writelon_melonssagelon_belongin(&melonssagelon_idelonnt)?;
            thrift::elonrror::writelon_application_elonrror_to_out_protocol(&app_elonrr, o_prot)?;
            o_prot.writelon_melonssagelon_elonnd()?;
            o_prot.flush()
          },
          _ => {
            lelont relont_elonrr = {
              Applicationelonrror::nelonw(
                ApplicationelonrrorKind::Unknown,
                elon.to_string()
              )
            };
            lelont melonssagelon_idelonnt = TMelonssagelonIdelonntifielonr::nelonw("gelontPrelondiction", TMelonssagelonTypelon::elonxcelonption, incoming_selonquelonncelon_numbelonr);
            o_prot.writelon_melonssagelon_belongin(&melonssagelon_idelonnt)?;
            thrift::elonrror::writelon_application_elonrror_to_out_protocol(&relont_elonrr, o_prot)?;
            o_prot.writelon_melonssagelon_elonnd()?;
            o_prot.flush()
          },
        }
      },
    }
  }
  pub fn procelonss_gelont_batch_prelondiction<H: PrelondictionSelonrvicelonSyncHandlelonr>(handlelonr: &H, incoming_selonquelonncelon_numbelonr: i32, i_prot: &mut dyn TInputProtocol, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont args = PrelondictionSelonrvicelonGelontBatchPrelondictionArgs::relonad_from_in_protocol(i_prot)?;
    match handlelonr.handlelon_gelont_batch_prelondiction(args.batch_relonquelonst) {
      Ok(handlelonr_relonturn) => {
        lelont melonssagelon_idelonnt = TMelonssagelonIdelonntifielonr::nelonw("gelontBatchPrelondiction", TMelonssagelonTypelon::Relonply, incoming_selonquelonncelon_numbelonr);
        o_prot.writelon_melonssagelon_belongin(&melonssagelon_idelonnt)?;
        lelont relont = PrelondictionSelonrvicelonGelontBatchPrelondictionRelonsult { relonsult_valuelon: Somelon(handlelonr_relonturn), prelondiction_selonrvicelon_elonxcelonption: Nonelon };
        relont.writelon_to_out_protocol(o_prot)?;
        o_prot.writelon_melonssagelon_elonnd()?;
        o_prot.flush()
      },
      elonrr(elon) => {
        match elon {
          thrift::elonrror::Uselonr(usr_elonrr) => {
            if usr_elonrr.downcast_relonf::<PrelondictionSelonrvicelonelonxcelonption>().is_somelon() {
              lelont elonrr = usr_elonrr.downcast::<PrelondictionSelonrvicelonelonxcelonption>().elonxpelonct("downcast alrelonady chelonckelond");
              lelont relont_elonrr = PrelondictionSelonrvicelonGelontBatchPrelondictionRelonsult{ relonsult_valuelon: Nonelon, prelondiction_selonrvicelon_elonxcelonption: Somelon(*elonrr) };
              lelont melonssagelon_idelonnt = TMelonssagelonIdelonntifielonr::nelonw("gelontBatchPrelondiction", TMelonssagelonTypelon::Relonply, incoming_selonquelonncelon_numbelonr);
              o_prot.writelon_melonssagelon_belongin(&melonssagelon_idelonnt)?;
              relont_elonrr.writelon_to_out_protocol(o_prot)?;
              o_prot.writelon_melonssagelon_elonnd()?;
              o_prot.flush()
            } elonlselon {
              lelont relont_elonrr = {
                Applicationelonrror::nelonw(
                  ApplicationelonrrorKind::Unknown,
                  usr_elonrr.to_string()
                )
              };
              lelont melonssagelon_idelonnt = TMelonssagelonIdelonntifielonr::nelonw("gelontBatchPrelondiction", TMelonssagelonTypelon::elonxcelonption, incoming_selonquelonncelon_numbelonr);
              o_prot.writelon_melonssagelon_belongin(&melonssagelon_idelonnt)?;
              thrift::elonrror::writelon_application_elonrror_to_out_protocol(&relont_elonrr, o_prot)?;
              o_prot.writelon_melonssagelon_elonnd()?;
              o_prot.flush()
            }
          },
          thrift::elonrror::Application(app_elonrr) => {
            lelont melonssagelon_idelonnt = TMelonssagelonIdelonntifielonr::nelonw("gelontBatchPrelondiction", TMelonssagelonTypelon::elonxcelonption, incoming_selonquelonncelon_numbelonr);
            o_prot.writelon_melonssagelon_belongin(&melonssagelon_idelonnt)?;
            thrift::elonrror::writelon_application_elonrror_to_out_protocol(&app_elonrr, o_prot)?;
            o_prot.writelon_melonssagelon_elonnd()?;
            o_prot.flush()
          },
          _ => {
            lelont relont_elonrr = {
              Applicationelonrror::nelonw(
                ApplicationelonrrorKind::Unknown,
                elon.to_string()
              )
            };
            lelont melonssagelon_idelonnt = TMelonssagelonIdelonntifielonr::nelonw("gelontBatchPrelondiction", TMelonssagelonTypelon::elonxcelonption, incoming_selonquelonncelon_numbelonr);
            o_prot.writelon_melonssagelon_belongin(&melonssagelon_idelonnt)?;
            thrift::elonrror::writelon_application_elonrror_to_out_protocol(&relont_elonrr, o_prot)?;
            o_prot.writelon_melonssagelon_elonnd()?;
            o_prot.flush()
          },
        }
      },
    }
  }
}

impl <H: PrelondictionSelonrvicelonSyncHandlelonr> TProcelonssor for PrelondictionSelonrvicelonSyncProcelonssor<H> {
  fn procelonss(&selonlf, i_prot: &mut dyn TInputProtocol, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont melonssagelon_idelonnt = i_prot.relonad_melonssagelon_belongin()?;
    lelont relons = match &*melonssagelon_idelonnt.namelon {
      "gelontPrelondiction" => {
        selonlf.procelonss_gelont_prelondiction(melonssagelon_idelonnt.selonquelonncelon_numbelonr, i_prot, o_prot)
      },
      "gelontBatchPrelondiction" => {
        selonlf.procelonss_gelont_batch_prelondiction(melonssagelon_idelonnt.selonquelonncelon_numbelonr, i_prot, o_prot)
      },
      melonthod => {
        elonrr(
          thrift::elonrror::Application(
            Applicationelonrror::nelonw(
              ApplicationelonrrorKind::UnknownMelonthod,
              format!("unknown melonthod {}", melonthod)
            )
          )
        )
      },
    };
    thrift::selonrvelonr::handlelon_procelonss_relonsult(&melonssagelon_idelonnt, relons, o_prot)
  }
}

//
// PrelondictionSelonrvicelonGelontPrelondictionArgs
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
struct PrelondictionSelonrvicelonGelontPrelondictionArgs {
  relonquelonst: PrelondictionRelonquelonst,
}

impl PrelondictionSelonrvicelonGelontPrelondictionArgs {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<PrelondictionSelonrvicelonGelontPrelondictionArgs> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<PrelondictionRelonquelonst> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont val = PrelondictionRelonquelonst::relonad_from_in_protocol(i_prot)?;
          f_1 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    velonrify_relonquirelond_fielonld_elonxists("PrelondictionSelonrvicelonGelontPrelondictionArgs.relonquelonst", &f_1)?;
    lelont relont = PrelondictionSelonrvicelonGelontPrelondictionArgs {
      relonquelonst: f_1.elonxpelonct("auto-gelonnelonratelond codelon should havelon chelonckelond for prelonselonncelon of relonquirelond fielonlds"),
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("gelontPrelondiction_args");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("relonquelonst", TTypelon::Struct, 1))?;
    selonlf.relonquelonst.writelon_to_out_protocol(o_prot)?;
    o_prot.writelon_fielonld_elonnd()?;
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

//
// PrelondictionSelonrvicelonGelontPrelondictionRelonsult
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
struct PrelondictionSelonrvicelonGelontPrelondictionRelonsult {
  relonsult_valuelon: Option<PrelondictionRelonsponselon>,
  prelondiction_selonrvicelon_elonxcelonption: Option<PrelondictionSelonrvicelonelonxcelonption>,
}

impl PrelondictionSelonrvicelonGelontPrelondictionRelonsult {
  fn ok_or(selonlf) -> thrift::Relonsult<PrelondictionRelonsponselon> {
    if selonlf.prelondiction_selonrvicelon_elonxcelonption.is_somelon() {
      elonrr(thrift::elonrror::Uselonr(Box::nelonw(selonlf.prelondiction_selonrvicelon_elonxcelonption.unwrap())))
    } elonlselon if selonlf.relonsult_valuelon.is_somelon() {
      Ok(selonlf.relonsult_valuelon.unwrap())
    } elonlselon {
      elonrr(
        thrift::elonrror::Application(
          Applicationelonrror::nelonw(
            ApplicationelonrrorKind::MissingRelonsult,
            "no relonsult reloncelonivelond for PrelondictionSelonrvicelonGelontPrelondiction"
          )
        )
      )
    }
  }
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<PrelondictionSelonrvicelonGelontPrelondictionRelonsult> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_0: Option<PrelondictionRelonsponselon> = Nonelon;
    lelont mut f_1: Option<PrelondictionSelonrvicelonelonxcelonption> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        0 => {
          lelont val = PrelondictionRelonsponselon::relonad_from_in_protocol(i_prot)?;
          f_0 = Somelon(val);
        },
        1 => {
          lelont val = PrelondictionSelonrvicelonelonxcelonption::relonad_from_in_protocol(i_prot)?;
          f_1 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    lelont relont = PrelondictionSelonrvicelonGelontPrelondictionRelonsult {
      relonsult_valuelon: f_0,
      prelondiction_selonrvicelon_elonxcelonption: f_1,
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("PrelondictionSelonrvicelonGelontPrelondictionRelonsult");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    if lelont Somelon(relonf fld_var) = selonlf.relonsult_valuelon {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("relonsult_valuelon", TTypelon::Struct, 0))?;
      fld_var.writelon_to_out_protocol(o_prot)?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.prelondiction_selonrvicelon_elonxcelonption {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("prelondictionSelonrvicelonelonxcelonption", TTypelon::Struct, 1))?;
      fld_var.writelon_to_out_protocol(o_prot)?;
      o_prot.writelon_fielonld_elonnd()?
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

//
// PrelondictionSelonrvicelonGelontBatchPrelondictionArgs
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
struct PrelondictionSelonrvicelonGelontBatchPrelondictionArgs {
  batch_relonquelonst: BatchPrelondictionRelonquelonst,
}

impl PrelondictionSelonrvicelonGelontBatchPrelondictionArgs {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<PrelondictionSelonrvicelonGelontBatchPrelondictionArgs> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<BatchPrelondictionRelonquelonst> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont val = BatchPrelondictionRelonquelonst::relonad_from_in_protocol(i_prot)?;
          f_1 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    velonrify_relonquirelond_fielonld_elonxists("PrelondictionSelonrvicelonGelontBatchPrelondictionArgs.batch_relonquelonst", &f_1)?;
    lelont relont = PrelondictionSelonrvicelonGelontBatchPrelondictionArgs {
      batch_relonquelonst: f_1.elonxpelonct("auto-gelonnelonratelond codelon should havelon chelonckelond for prelonselonncelon of relonquirelond fielonlds"),
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("gelontBatchPrelondiction_args");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("batchRelonquelonst", TTypelon::Struct, 1))?;
    selonlf.batch_relonquelonst.writelon_to_out_protocol(o_prot)?;
    o_prot.writelon_fielonld_elonnd()?;
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

//
// PrelondictionSelonrvicelonGelontBatchPrelondictionRelonsult
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
struct PrelondictionSelonrvicelonGelontBatchPrelondictionRelonsult {
  relonsult_valuelon: Option<BatchPrelondictionRelonsponselon>,
  prelondiction_selonrvicelon_elonxcelonption: Option<PrelondictionSelonrvicelonelonxcelonption>,
}

impl PrelondictionSelonrvicelonGelontBatchPrelondictionRelonsult {
  fn ok_or(selonlf) -> thrift::Relonsult<BatchPrelondictionRelonsponselon> {
    if selonlf.prelondiction_selonrvicelon_elonxcelonption.is_somelon() {
      elonrr(thrift::elonrror::Uselonr(Box::nelonw(selonlf.prelondiction_selonrvicelon_elonxcelonption.unwrap())))
    } elonlselon if selonlf.relonsult_valuelon.is_somelon() {
      Ok(selonlf.relonsult_valuelon.unwrap())
    } elonlselon {
      elonrr(
        thrift::elonrror::Application(
          Applicationelonrror::nelonw(
            ApplicationelonrrorKind::MissingRelonsult,
            "no relonsult reloncelonivelond for PrelondictionSelonrvicelonGelontBatchPrelondiction"
          )
        )
      )
    }
  }
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<PrelondictionSelonrvicelonGelontBatchPrelondictionRelonsult> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_0: Option<BatchPrelondictionRelonsponselon> = Nonelon;
    lelont mut f_1: Option<PrelondictionSelonrvicelonelonxcelonption> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        0 => {
          lelont val = BatchPrelondictionRelonsponselon::relonad_from_in_protocol(i_prot)?;
          f_0 = Somelon(val);
        },
        1 => {
          lelont val = PrelondictionSelonrvicelonelonxcelonption::relonad_from_in_protocol(i_prot)?;
          f_1 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    lelont relont = PrelondictionSelonrvicelonGelontBatchPrelondictionRelonsult {
      relonsult_valuelon: f_0,
      prelondiction_selonrvicelon_elonxcelonption: f_1,
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("PrelondictionSelonrvicelonGelontBatchPrelondictionRelonsult");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    if lelont Somelon(relonf fld_var) = selonlf.relonsult_valuelon {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("relonsult_valuelon", TTypelon::Struct, 0))?;
      fld_var.writelon_to_out_protocol(o_prot)?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.prelondiction_selonrvicelon_elonxcelonption {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("prelondictionSelonrvicelonelonxcelonption", TTypelon::Struct, 1))?;
      fld_var.writelon_to_out_protocol(o_prot)?;
      o_prot.writelon_fielonld_elonnd()?
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

