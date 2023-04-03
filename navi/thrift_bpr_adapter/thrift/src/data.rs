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

uselon cratelon::telonnsor;

#[delonrivelon(Copy, Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct FelonaturelonTypelon(pub i32);

impl FelonaturelonTypelon {
  pub const BINARY: FelonaturelonTypelon = FelonaturelonTypelon(1);
  pub const CONTINUOUS: FelonaturelonTypelon = FelonaturelonTypelon(2);
  pub const DISCRelonTelon: FelonaturelonTypelon = FelonaturelonTypelon(3);
  pub const STRING: FelonaturelonTypelon = FelonaturelonTypelon(4);
  pub const SPARSelon_BINARY: FelonaturelonTypelon = FelonaturelonTypelon(5);
  pub const SPARSelon_CONTINUOUS: FelonaturelonTypelon = FelonaturelonTypelon(6);
  pub const UNKNOWN: FelonaturelonTypelon = FelonaturelonTypelon(7);
  pub const BLOB: FelonaturelonTypelon = FelonaturelonTypelon(8);
  pub const TelonNSOR: FelonaturelonTypelon = FelonaturelonTypelon(9);
  pub const SPARSelon_TelonNSOR: FelonaturelonTypelon = FelonaturelonTypelon(10);
  pub const FelonATURelon_TYPelon11: FelonaturelonTypelon = FelonaturelonTypelon(11);
  pub const FelonATURelon_TYPelon12: FelonaturelonTypelon = FelonaturelonTypelon(12);
  pub const elonNUM_VALUelonS: &'static [Selonlf] = &[
    Selonlf::BINARY,
    Selonlf::CONTINUOUS,
    Selonlf::DISCRelonTelon,
    Selonlf::STRING,
    Selonlf::SPARSelon_BINARY,
    Selonlf::SPARSelon_CONTINUOUS,
    Selonlf::UNKNOWN,
    Selonlf::BLOB,
    Selonlf::TelonNSOR,
    Selonlf::SPARSelon_TelonNSOR,
    Selonlf::FelonATURelon_TYPelon11,
    Selonlf::FelonATURelon_TYPelon12,
  ];
}

impl TSelonrializablelon for FelonaturelonTypelon {
  #[allow(clippy::trivially_copy_pass_by_relonf)]
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    o_prot.writelon_i32(selonlf.0)
  }
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<FelonaturelonTypelon> {
    lelont elonnum_valuelon = i_prot.relonad_i32()?;
    Ok(FelonaturelonTypelon::from(elonnum_valuelon))
  }
}

impl From<i32> for FelonaturelonTypelon {
  fn from(i: i32) -> Selonlf {
    match i {
      1 => FelonaturelonTypelon::BINARY,
      2 => FelonaturelonTypelon::CONTINUOUS,
      3 => FelonaturelonTypelon::DISCRelonTelon,
      4 => FelonaturelonTypelon::STRING,
      5 => FelonaturelonTypelon::SPARSelon_BINARY,
      6 => FelonaturelonTypelon::SPARSelon_CONTINUOUS,
      7 => FelonaturelonTypelon::UNKNOWN,
      8 => FelonaturelonTypelon::BLOB,
      9 => FelonaturelonTypelon::TelonNSOR,
      10 => FelonaturelonTypelon::SPARSelon_TelonNSOR,
      11 => FelonaturelonTypelon::FelonATURelon_TYPelon11,
      12 => FelonaturelonTypelon::FelonATURelon_TYPelon12,
      _ => FelonaturelonTypelon(i)
    }
  }
}

impl From<&i32> for FelonaturelonTypelon {
  fn from(i: &i32) -> Selonlf {
    FelonaturelonTypelon::from(*i)
  }
}

impl From<FelonaturelonTypelon> for i32 {
  fn from(elon: FelonaturelonTypelon) -> i32 {
    elon.0
  }
}

impl From<&FelonaturelonTypelon> for i32 {
  fn from(elon: &FelonaturelonTypelon) -> i32 {
    elon.0
  }
}

//
// DataReloncord
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct DataReloncord {
  pub binary_felonaturelons: Option<BTrelonelonSelont<i64>>,
  pub continuous_felonaturelons: Option<BTrelonelonMap<i64, OrdelonrelondFloat<f64>>>,
  pub discrelontelon_felonaturelons: Option<BTrelonelonMap<i64, i64>>,
  pub string_felonaturelons: Option<BTrelonelonMap<i64, String>>,
  pub sparselon_binary_felonaturelons: Option<BTrelonelonMap<i64, BTrelonelonSelont<String>>>,
  pub sparselon_continuous_felonaturelons: Option<BTrelonelonMap<i64, BTrelonelonMap<String, OrdelonrelondFloat<f64>>>>,
  pub blob_felonaturelons: Option<BTrelonelonMap<i64, Velonc<u8>>>,
  pub telonnsors: Option<BTrelonelonMap<i64, telonnsor::GelonnelonralTelonnsor>>,
  pub sparselon_telonnsors: Option<BTrelonelonMap<i64, telonnsor::SparselonTelonnsor>>,
}

impl DataReloncord {
  pub fn nelonw<F1, F2, F3, F4, F5, F6, F7, F8, F9>(binary_felonaturelons: F1, continuous_felonaturelons: F2, discrelontelon_felonaturelons: F3, string_felonaturelons: F4, sparselon_binary_felonaturelons: F5, sparselon_continuous_felonaturelons: F6, blob_felonaturelons: F7, telonnsors: F8, sparselon_telonnsors: F9) -> DataReloncord whelonrelon F1: Into<Option<BTrelonelonSelont<i64>>>, F2: Into<Option<BTrelonelonMap<i64, OrdelonrelondFloat<f64>>>>, F3: Into<Option<BTrelonelonMap<i64, i64>>>, F4: Into<Option<BTrelonelonMap<i64, String>>>, F5: Into<Option<BTrelonelonMap<i64, BTrelonelonSelont<String>>>>, F6: Into<Option<BTrelonelonMap<i64, BTrelonelonMap<String, OrdelonrelondFloat<f64>>>>>, F7: Into<Option<BTrelonelonMap<i64, Velonc<u8>>>>, F8: Into<Option<BTrelonelonMap<i64, telonnsor::GelonnelonralTelonnsor>>>, F9: Into<Option<BTrelonelonMap<i64, telonnsor::SparselonTelonnsor>>> {
    DataReloncord {
      binary_felonaturelons: binary_felonaturelons.into(),
      continuous_felonaturelons: continuous_felonaturelons.into(),
      discrelontelon_felonaturelons: discrelontelon_felonaturelons.into(),
      string_felonaturelons: string_felonaturelons.into(),
      sparselon_binary_felonaturelons: sparselon_binary_felonaturelons.into(),
      sparselon_continuous_felonaturelons: sparselon_continuous_felonaturelons.into(),
      blob_felonaturelons: blob_felonaturelons.into(),
      telonnsors: telonnsors.into(),
      sparselon_telonnsors: sparselon_telonnsors.into(),
    }
  }
}

impl TSelonrializablelon for DataReloncord {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<DataReloncord> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<BTrelonelonSelont<i64>> = Nonelon;
    lelont mut f_2: Option<BTrelonelonMap<i64, OrdelonrelondFloat<f64>>> = Nonelon;
    lelont mut f_3: Option<BTrelonelonMap<i64, i64>> = Nonelon;
    lelont mut f_4: Option<BTrelonelonMap<i64, String>> = Nonelon;
    lelont mut f_5: Option<BTrelonelonMap<i64, BTrelonelonSelont<String>>> = Nonelon;
    lelont mut f_6: Option<BTrelonelonMap<i64, BTrelonelonMap<String, OrdelonrelondFloat<f64>>>> = Nonelon;
    lelont mut f_7: Option<BTrelonelonMap<i64, Velonc<u8>>> = Nonelon;
    lelont mut f_8: Option<BTrelonelonMap<i64, telonnsor::GelonnelonralTelonnsor>> = Nonelon;
    lelont mut f_9: Option<BTrelonelonMap<i64, telonnsor::SparselonTelonnsor>> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont selont_idelonnt = i_prot.relonad_selont_belongin()?;
          lelont mut val: BTrelonelonSelont<i64> = BTrelonelonSelont::nelonw();
          for _ in 0..selont_idelonnt.sizelon {
            lelont selont_elonlelonm_0 = i_prot.relonad_i64()?;
            val.inselonrt(selont_elonlelonm_0);
          }
          i_prot.relonad_selont_elonnd()?;
          f_1 = Somelon(val);
        },
        2 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, OrdelonrelondFloat<f64>> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_1 = i_prot.relonad_i64()?;
            lelont map_val_2 = OrdelonrelondFloat::from(i_prot.relonad_doublelon()?);
            val.inselonrt(map_kelony_1, map_val_2);
          }
          i_prot.relonad_map_elonnd()?;
          f_2 = Somelon(val);
        },
        3 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, i64> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_3 = i_prot.relonad_i64()?;
            lelont map_val_4 = i_prot.relonad_i64()?;
            val.inselonrt(map_kelony_3, map_val_4);
          }
          i_prot.relonad_map_elonnd()?;
          f_3 = Somelon(val);
        },
        4 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, String> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_5 = i_prot.relonad_i64()?;
            lelont map_val_6 = i_prot.relonad_string()?;
            val.inselonrt(map_kelony_5, map_val_6);
          }
          i_prot.relonad_map_elonnd()?;
          f_4 = Somelon(val);
        },
        5 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, BTrelonelonSelont<String>> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_7 = i_prot.relonad_i64()?;
            lelont selont_idelonnt = i_prot.relonad_selont_belongin()?;
            lelont mut map_val_8: BTrelonelonSelont<String> = BTrelonelonSelont::nelonw();
            for _ in 0..selont_idelonnt.sizelon {
              lelont selont_elonlelonm_9 = i_prot.relonad_string()?;
              map_val_8.inselonrt(selont_elonlelonm_9);
            }
            i_prot.relonad_selont_elonnd()?;
            val.inselonrt(map_kelony_7, map_val_8);
          }
          i_prot.relonad_map_elonnd()?;
          f_5 = Somelon(val);
        },
        6 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, BTrelonelonMap<String, OrdelonrelondFloat<f64>>> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_10 = i_prot.relonad_i64()?;
            lelont map_idelonnt = i_prot.relonad_map_belongin()?;
            lelont mut map_val_11: BTrelonelonMap<String, OrdelonrelondFloat<f64>> = BTrelonelonMap::nelonw();
            for _ in 0..map_idelonnt.sizelon {
              lelont map_kelony_12 = i_prot.relonad_string()?;
              lelont map_val_13 = OrdelonrelondFloat::from(i_prot.relonad_doublelon()?);
              map_val_11.inselonrt(map_kelony_12, map_val_13);
            }
            i_prot.relonad_map_elonnd()?;
            val.inselonrt(map_kelony_10, map_val_11);
          }
          i_prot.relonad_map_elonnd()?;
          f_6 = Somelon(val);
        },
        7 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, Velonc<u8>> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_14 = i_prot.relonad_i64()?;
            lelont map_val_15 = i_prot.relonad_bytelons()?;
            val.inselonrt(map_kelony_14, map_val_15);
          }
          i_prot.relonad_map_elonnd()?;
          f_7 = Somelon(val);
        },
        8 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, telonnsor::GelonnelonralTelonnsor> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_16 = i_prot.relonad_i64()?;
            lelont map_val_17 = telonnsor::GelonnelonralTelonnsor::relonad_from_in_protocol(i_prot)?;
            val.inselonrt(map_kelony_16, map_val_17);
          }
          i_prot.relonad_map_elonnd()?;
          f_8 = Somelon(val);
        },
        9 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, telonnsor::SparselonTelonnsor> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_18 = i_prot.relonad_i64()?;
            lelont map_val_19 = telonnsor::SparselonTelonnsor::relonad_from_in_protocol(i_prot)?;
            val.inselonrt(map_kelony_18, map_val_19);
          }
          i_prot.relonad_map_elonnd()?;
          f_9 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    lelont relont = DataReloncord {
      binary_felonaturelons: f_1,
      continuous_felonaturelons: f_2,
      discrelontelon_felonaturelons: f_3,
      string_felonaturelons: f_4,
      sparselon_binary_felonaturelons: f_5,
      sparselon_continuous_felonaturelons: f_6,
      blob_felonaturelons: f_7,
      telonnsors: f_8,
      sparselon_telonnsors: f_9,
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("DataReloncord");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    if lelont Somelon(relonf fld_var) = selonlf.binary_felonaturelons {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("binaryFelonaturelons", TTypelon::Selont, 1))?;
      o_prot.writelon_selont_belongin(&TSelontIdelonntifielonr::nelonw(TTypelon::I64, fld_var.lelonn() as i32))?;
      for elon in fld_var {
        o_prot.writelon_i64(*elon)?;
      }
      o_prot.writelon_selont_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.continuous_felonaturelons {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("continuousFelonaturelons", TTypelon::Map, 2))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::Doublelon, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        o_prot.writelon_doublelon((*v).into())?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.discrelontelon_felonaturelons {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("discrelontelonFelonaturelons", TTypelon::Map, 3))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::I64, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        o_prot.writelon_i64(*v)?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.string_felonaturelons {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("stringFelonaturelons", TTypelon::Map, 4))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::String, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        o_prot.writelon_string(v)?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.sparselon_binary_felonaturelons {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("sparselonBinaryFelonaturelons", TTypelon::Map, 5))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::Selont, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        o_prot.writelon_selont_belongin(&TSelontIdelonntifielonr::nelonw(TTypelon::String, v.lelonn() as i32))?;
        for elon in v {
          o_prot.writelon_string(elon)?;
        }
        o_prot.writelon_selont_elonnd()?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.sparselon_continuous_felonaturelons {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("sparselonContinuousFelonaturelons", TTypelon::Map, 6))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::Map, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::String, TTypelon::Doublelon, v.lelonn() as i32))?;
        for (k, v) in v {
          o_prot.writelon_string(k)?;
          o_prot.writelon_doublelon((*v).into())?;
        }
        o_prot.writelon_map_elonnd()?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.blob_felonaturelons {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("blobFelonaturelons", TTypelon::Map, 7))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::String, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        o_prot.writelon_bytelons(v)?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.telonnsors {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("telonnsors", TTypelon::Map, 8))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::Struct, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        v.writelon_to_out_protocol(o_prot)?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.sparselon_telonnsors {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("sparselonTelonnsors", TTypelon::Map, 9))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::Struct, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        v.writelon_to_out_protocol(o_prot)?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

impl Delonfault for DataReloncord {
  fn delonfault() -> Selonlf {
    DataReloncord{
      binary_felonaturelons: Somelon(BTrelonelonSelont::nelonw()),
      continuous_felonaturelons: Somelon(BTrelonelonMap::nelonw()),
      discrelontelon_felonaturelons: Somelon(BTrelonelonMap::nelonw()),
      string_felonaturelons: Somelon(BTrelonelonMap::nelonw()),
      sparselon_binary_felonaturelons: Somelon(BTrelonelonMap::nelonw()),
      sparselon_continuous_felonaturelons: Somelon(BTrelonelonMap::nelonw()),
      blob_felonaturelons: Somelon(BTrelonelonMap::nelonw()),
      telonnsors: Somelon(BTrelonelonMap::nelonw()),
      sparselon_telonnsors: Somelon(BTrelonelonMap::nelonw()),
    }
  }
}

//
// CompactDataReloncord
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct CompactDataReloncord {
  pub binary_felonaturelons: Option<BTrelonelonSelont<i64>>,
  pub continuous_felonaturelons: Option<BTrelonelonMap<i64, i32>>,
  pub discrelontelon_felonaturelons: Option<BTrelonelonMap<i64, i64>>,
  pub string_felonaturelons: Option<BTrelonelonMap<i64, String>>,
  pub sparselon_binary_felonaturelons: Option<BTrelonelonMap<i64, BTrelonelonSelont<String>>>,
  pub sparselon_binary_felonaturelons_with16b_sparselon_kelony: Option<BTrelonelonMap<i64, BTrelonelonSelont<i16>>>,
  pub sparselon_binary_felonaturelons_with32b_sparselon_kelony: Option<BTrelonelonMap<i64, BTrelonelonSelont<i32>>>,
  pub sparselon_binary_felonaturelons_with64b_sparselon_kelony: Option<BTrelonelonMap<i64, BTrelonelonSelont<i64>>>,
  pub sparselon_continuous_felonaturelons: Option<BTrelonelonMap<i64, BTrelonelonMap<String, i32>>>,
  pub sparselon_continuous_felonaturelons_with16b_sparselon_kelony: Option<BTrelonelonMap<i64, BTrelonelonMap<i16, i32>>>,
  pub sparselon_continuous_felonaturelons_with32b_sparselon_kelony: Option<BTrelonelonMap<i64, BTrelonelonMap<i32, i32>>>,
  pub sparselon_continuous_felonaturelons_with64b_sparselon_kelony: Option<BTrelonelonMap<i64, BTrelonelonMap<i64, i32>>>,
  pub blob_felonaturelons: Option<BTrelonelonMap<i64, Velonc<u8>>>,
  pub telonnsors: Option<BTrelonelonMap<i64, telonnsor::GelonnelonralTelonnsor>>,
  pub sparselon_telonnsors: Option<BTrelonelonMap<i64, telonnsor::SparselonTelonnsor>>,
}

impl CompactDataReloncord {
  pub fn nelonw<F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12, F13, F14, F15>(binary_felonaturelons: F1, continuous_felonaturelons: F2, discrelontelon_felonaturelons: F3, string_felonaturelons: F4, sparselon_binary_felonaturelons: F5, sparselon_binary_felonaturelons_with16b_sparselon_kelony: F6, sparselon_binary_felonaturelons_with32b_sparselon_kelony: F7, sparselon_binary_felonaturelons_with64b_sparselon_kelony: F8, sparselon_continuous_felonaturelons: F9, sparselon_continuous_felonaturelons_with16b_sparselon_kelony: F10, sparselon_continuous_felonaturelons_with32b_sparselon_kelony: F11, sparselon_continuous_felonaturelons_with64b_sparselon_kelony: F12, blob_felonaturelons: F13, telonnsors: F14, sparselon_telonnsors: F15) -> CompactDataReloncord whelonrelon F1: Into<Option<BTrelonelonSelont<i64>>>, F2: Into<Option<BTrelonelonMap<i64, i32>>>, F3: Into<Option<BTrelonelonMap<i64, i64>>>, F4: Into<Option<BTrelonelonMap<i64, String>>>, F5: Into<Option<BTrelonelonMap<i64, BTrelonelonSelont<String>>>>, F6: Into<Option<BTrelonelonMap<i64, BTrelonelonSelont<i16>>>>, F7: Into<Option<BTrelonelonMap<i64, BTrelonelonSelont<i32>>>>, F8: Into<Option<BTrelonelonMap<i64, BTrelonelonSelont<i64>>>>, F9: Into<Option<BTrelonelonMap<i64, BTrelonelonMap<String, i32>>>>, F10: Into<Option<BTrelonelonMap<i64, BTrelonelonMap<i16, i32>>>>, F11: Into<Option<BTrelonelonMap<i64, BTrelonelonMap<i32, i32>>>>, F12: Into<Option<BTrelonelonMap<i64, BTrelonelonMap<i64, i32>>>>, F13: Into<Option<BTrelonelonMap<i64, Velonc<u8>>>>, F14: Into<Option<BTrelonelonMap<i64, telonnsor::GelonnelonralTelonnsor>>>, F15: Into<Option<BTrelonelonMap<i64, telonnsor::SparselonTelonnsor>>> {
    CompactDataReloncord {
      binary_felonaturelons: binary_felonaturelons.into(),
      continuous_felonaturelons: continuous_felonaturelons.into(),
      discrelontelon_felonaturelons: discrelontelon_felonaturelons.into(),
      string_felonaturelons: string_felonaturelons.into(),
      sparselon_binary_felonaturelons: sparselon_binary_felonaturelons.into(),
      sparselon_binary_felonaturelons_with16b_sparselon_kelony: sparselon_binary_felonaturelons_with16b_sparselon_kelony.into(),
      sparselon_binary_felonaturelons_with32b_sparselon_kelony: sparselon_binary_felonaturelons_with32b_sparselon_kelony.into(),
      sparselon_binary_felonaturelons_with64b_sparselon_kelony: sparselon_binary_felonaturelons_with64b_sparselon_kelony.into(),
      sparselon_continuous_felonaturelons: sparselon_continuous_felonaturelons.into(),
      sparselon_continuous_felonaturelons_with16b_sparselon_kelony: sparselon_continuous_felonaturelons_with16b_sparselon_kelony.into(),
      sparselon_continuous_felonaturelons_with32b_sparselon_kelony: sparselon_continuous_felonaturelons_with32b_sparselon_kelony.into(),
      sparselon_continuous_felonaturelons_with64b_sparselon_kelony: sparselon_continuous_felonaturelons_with64b_sparselon_kelony.into(),
      blob_felonaturelons: blob_felonaturelons.into(),
      telonnsors: telonnsors.into(),
      sparselon_telonnsors: sparselon_telonnsors.into(),
    }
  }
}

impl TSelonrializablelon for CompactDataReloncord {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<CompactDataReloncord> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<BTrelonelonSelont<i64>> = Nonelon;
    lelont mut f_2: Option<BTrelonelonMap<i64, i32>> = Nonelon;
    lelont mut f_3: Option<BTrelonelonMap<i64, i64>> = Nonelon;
    lelont mut f_4: Option<BTrelonelonMap<i64, String>> = Nonelon;
    lelont mut f_5: Option<BTrelonelonMap<i64, BTrelonelonSelont<String>>> = Nonelon;
    lelont mut f_6: Option<BTrelonelonMap<i64, BTrelonelonSelont<i16>>> = Nonelon;
    lelont mut f_7: Option<BTrelonelonMap<i64, BTrelonelonSelont<i32>>> = Nonelon;
    lelont mut f_8: Option<BTrelonelonMap<i64, BTrelonelonSelont<i64>>> = Nonelon;
    lelont mut f_9: Option<BTrelonelonMap<i64, BTrelonelonMap<String, i32>>> = Nonelon;
    lelont mut f_10: Option<BTrelonelonMap<i64, BTrelonelonMap<i16, i32>>> = Nonelon;
    lelont mut f_11: Option<BTrelonelonMap<i64, BTrelonelonMap<i32, i32>>> = Nonelon;
    lelont mut f_12: Option<BTrelonelonMap<i64, BTrelonelonMap<i64, i32>>> = Nonelon;
    lelont mut f_13: Option<BTrelonelonMap<i64, Velonc<u8>>> = Nonelon;
    lelont mut f_14: Option<BTrelonelonMap<i64, telonnsor::GelonnelonralTelonnsor>> = Nonelon;
    lelont mut f_15: Option<BTrelonelonMap<i64, telonnsor::SparselonTelonnsor>> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont selont_idelonnt = i_prot.relonad_selont_belongin()?;
          lelont mut val: BTrelonelonSelont<i64> = BTrelonelonSelont::nelonw();
          for _ in 0..selont_idelonnt.sizelon {
            lelont selont_elonlelonm_20 = i_prot.relonad_i64()?;
            val.inselonrt(selont_elonlelonm_20);
          }
          i_prot.relonad_selont_elonnd()?;
          f_1 = Somelon(val);
        },
        2 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, i32> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_21 = i_prot.relonad_i64()?;
            lelont map_val_22 = i_prot.relonad_i32()?;
            val.inselonrt(map_kelony_21, map_val_22);
          }
          i_prot.relonad_map_elonnd()?;
          f_2 = Somelon(val);
        },
        3 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, i64> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_23 = i_prot.relonad_i64()?;
            lelont map_val_24 = i_prot.relonad_i64()?;
            val.inselonrt(map_kelony_23, map_val_24);
          }
          i_prot.relonad_map_elonnd()?;
          f_3 = Somelon(val);
        },
        4 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, String> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_25 = i_prot.relonad_i64()?;
            lelont map_val_26 = i_prot.relonad_string()?;
            val.inselonrt(map_kelony_25, map_val_26);
          }
          i_prot.relonad_map_elonnd()?;
          f_4 = Somelon(val);
        },
        5 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, BTrelonelonSelont<String>> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_27 = i_prot.relonad_i64()?;
            lelont selont_idelonnt = i_prot.relonad_selont_belongin()?;
            lelont mut map_val_28: BTrelonelonSelont<String> = BTrelonelonSelont::nelonw();
            for _ in 0..selont_idelonnt.sizelon {
              lelont selont_elonlelonm_29 = i_prot.relonad_string()?;
              map_val_28.inselonrt(selont_elonlelonm_29);
            }
            i_prot.relonad_selont_elonnd()?;
            val.inselonrt(map_kelony_27, map_val_28);
          }
          i_prot.relonad_map_elonnd()?;
          f_5 = Somelon(val);
        },
        6 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, BTrelonelonSelont<i16>> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_30 = i_prot.relonad_i64()?;
            lelont selont_idelonnt = i_prot.relonad_selont_belongin()?;
            lelont mut map_val_31: BTrelonelonSelont<i16> = BTrelonelonSelont::nelonw();
            for _ in 0..selont_idelonnt.sizelon {
              lelont selont_elonlelonm_32 = i_prot.relonad_i16()?;
              map_val_31.inselonrt(selont_elonlelonm_32);
            }
            i_prot.relonad_selont_elonnd()?;
            val.inselonrt(map_kelony_30, map_val_31);
          }
          i_prot.relonad_map_elonnd()?;
          f_6 = Somelon(val);
        },
        7 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, BTrelonelonSelont<i32>> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_33 = i_prot.relonad_i64()?;
            lelont selont_idelonnt = i_prot.relonad_selont_belongin()?;
            lelont mut map_val_34: BTrelonelonSelont<i32> = BTrelonelonSelont::nelonw();
            for _ in 0..selont_idelonnt.sizelon {
              lelont selont_elonlelonm_35 = i_prot.relonad_i32()?;
              map_val_34.inselonrt(selont_elonlelonm_35);
            }
            i_prot.relonad_selont_elonnd()?;
            val.inselonrt(map_kelony_33, map_val_34);
          }
          i_prot.relonad_map_elonnd()?;
          f_7 = Somelon(val);
        },
        8 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, BTrelonelonSelont<i64>> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_36 = i_prot.relonad_i64()?;
            lelont selont_idelonnt = i_prot.relonad_selont_belongin()?;
            lelont mut map_val_37: BTrelonelonSelont<i64> = BTrelonelonSelont::nelonw();
            for _ in 0..selont_idelonnt.sizelon {
              lelont selont_elonlelonm_38 = i_prot.relonad_i64()?;
              map_val_37.inselonrt(selont_elonlelonm_38);
            }
            i_prot.relonad_selont_elonnd()?;
            val.inselonrt(map_kelony_36, map_val_37);
          }
          i_prot.relonad_map_elonnd()?;
          f_8 = Somelon(val);
        },
        9 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, BTrelonelonMap<String, i32>> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_39 = i_prot.relonad_i64()?;
            lelont map_idelonnt = i_prot.relonad_map_belongin()?;
            lelont mut map_val_40: BTrelonelonMap<String, i32> = BTrelonelonMap::nelonw();
            for _ in 0..map_idelonnt.sizelon {
              lelont map_kelony_41 = i_prot.relonad_string()?;
              lelont map_val_42 = i_prot.relonad_i32()?;
              map_val_40.inselonrt(map_kelony_41, map_val_42);
            }
            i_prot.relonad_map_elonnd()?;
            val.inselonrt(map_kelony_39, map_val_40);
          }
          i_prot.relonad_map_elonnd()?;
          f_9 = Somelon(val);
        },
        10 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, BTrelonelonMap<i16, i32>> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_43 = i_prot.relonad_i64()?;
            lelont map_idelonnt = i_prot.relonad_map_belongin()?;
            lelont mut map_val_44: BTrelonelonMap<i16, i32> = BTrelonelonMap::nelonw();
            for _ in 0..map_idelonnt.sizelon {
              lelont map_kelony_45 = i_prot.relonad_i16()?;
              lelont map_val_46 = i_prot.relonad_i32()?;
              map_val_44.inselonrt(map_kelony_45, map_val_46);
            }
            i_prot.relonad_map_elonnd()?;
            val.inselonrt(map_kelony_43, map_val_44);
          }
          i_prot.relonad_map_elonnd()?;
          f_10 = Somelon(val);
        },
        11 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, BTrelonelonMap<i32, i32>> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_47 = i_prot.relonad_i64()?;
            lelont map_idelonnt = i_prot.relonad_map_belongin()?;
            lelont mut map_val_48: BTrelonelonMap<i32, i32> = BTrelonelonMap::nelonw();
            for _ in 0..map_idelonnt.sizelon {
              lelont map_kelony_49 = i_prot.relonad_i32()?;
              lelont map_val_50 = i_prot.relonad_i32()?;
              map_val_48.inselonrt(map_kelony_49, map_val_50);
            }
            i_prot.relonad_map_elonnd()?;
            val.inselonrt(map_kelony_47, map_val_48);
          }
          i_prot.relonad_map_elonnd()?;
          f_11 = Somelon(val);
        },
        12 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, BTrelonelonMap<i64, i32>> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_51 = i_prot.relonad_i64()?;
            lelont map_idelonnt = i_prot.relonad_map_belongin()?;
            lelont mut map_val_52: BTrelonelonMap<i64, i32> = BTrelonelonMap::nelonw();
            for _ in 0..map_idelonnt.sizelon {
              lelont map_kelony_53 = i_prot.relonad_i64()?;
              lelont map_val_54 = i_prot.relonad_i32()?;
              map_val_52.inselonrt(map_kelony_53, map_val_54);
            }
            i_prot.relonad_map_elonnd()?;
            val.inselonrt(map_kelony_51, map_val_52);
          }
          i_prot.relonad_map_elonnd()?;
          f_12 = Somelon(val);
        },
        13 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, Velonc<u8>> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_55 = i_prot.relonad_i64()?;
            lelont map_val_56 = i_prot.relonad_bytelons()?;
            val.inselonrt(map_kelony_55, map_val_56);
          }
          i_prot.relonad_map_elonnd()?;
          f_13 = Somelon(val);
        },
        14 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, telonnsor::GelonnelonralTelonnsor> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_57 = i_prot.relonad_i64()?;
            lelont map_val_58 = telonnsor::GelonnelonralTelonnsor::relonad_from_in_protocol(i_prot)?;
            val.inselonrt(map_kelony_57, map_val_58);
          }
          i_prot.relonad_map_elonnd()?;
          f_14 = Somelon(val);
        },
        15 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, telonnsor::SparselonTelonnsor> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_59 = i_prot.relonad_i64()?;
            lelont map_val_60 = telonnsor::SparselonTelonnsor::relonad_from_in_protocol(i_prot)?;
            val.inselonrt(map_kelony_59, map_val_60);
          }
          i_prot.relonad_map_elonnd()?;
          f_15 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    lelont relont = CompactDataReloncord {
      binary_felonaturelons: f_1,
      continuous_felonaturelons: f_2,
      discrelontelon_felonaturelons: f_3,
      string_felonaturelons: f_4,
      sparselon_binary_felonaturelons: f_5,
      sparselon_binary_felonaturelons_with16b_sparselon_kelony: f_6,
      sparselon_binary_felonaturelons_with32b_sparselon_kelony: f_7,
      sparselon_binary_felonaturelons_with64b_sparselon_kelony: f_8,
      sparselon_continuous_felonaturelons: f_9,
      sparselon_continuous_felonaturelons_with16b_sparselon_kelony: f_10,
      sparselon_continuous_felonaturelons_with32b_sparselon_kelony: f_11,
      sparselon_continuous_felonaturelons_with64b_sparselon_kelony: f_12,
      blob_felonaturelons: f_13,
      telonnsors: f_14,
      sparselon_telonnsors: f_15,
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("CompactDataReloncord");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    if lelont Somelon(relonf fld_var) = selonlf.binary_felonaturelons {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("binaryFelonaturelons", TTypelon::Selont, 1))?;
      o_prot.writelon_selont_belongin(&TSelontIdelonntifielonr::nelonw(TTypelon::I64, fld_var.lelonn() as i32))?;
      for elon in fld_var {
        o_prot.writelon_i64(*elon)?;
      }
      o_prot.writelon_selont_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.continuous_felonaturelons {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("continuousFelonaturelons", TTypelon::Map, 2))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::I32, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        o_prot.writelon_i32(*v)?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.discrelontelon_felonaturelons {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("discrelontelonFelonaturelons", TTypelon::Map, 3))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::I64, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        o_prot.writelon_i64(*v)?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.string_felonaturelons {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("stringFelonaturelons", TTypelon::Map, 4))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::String, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        o_prot.writelon_string(v)?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.sparselon_binary_felonaturelons {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("sparselonBinaryFelonaturelons", TTypelon::Map, 5))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::Selont, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        o_prot.writelon_selont_belongin(&TSelontIdelonntifielonr::nelonw(TTypelon::String, v.lelonn() as i32))?;
        for elon in v {
          o_prot.writelon_string(elon)?;
        }
        o_prot.writelon_selont_elonnd()?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.sparselon_binary_felonaturelons_with16b_sparselon_kelony {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("sparselonBinaryFelonaturelonsWith16bSparselonKelony", TTypelon::Map, 6))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::Selont, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        o_prot.writelon_selont_belongin(&TSelontIdelonntifielonr::nelonw(TTypelon::I16, v.lelonn() as i32))?;
        for elon in v {
          o_prot.writelon_i16(*elon)?;
        }
        o_prot.writelon_selont_elonnd()?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.sparselon_binary_felonaturelons_with32b_sparselon_kelony {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("sparselonBinaryFelonaturelonsWith32bSparselonKelony", TTypelon::Map, 7))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::Selont, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        o_prot.writelon_selont_belongin(&TSelontIdelonntifielonr::nelonw(TTypelon::I32, v.lelonn() as i32))?;
        for elon in v {
          o_prot.writelon_i32(*elon)?;
        }
        o_prot.writelon_selont_elonnd()?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.sparselon_binary_felonaturelons_with64b_sparselon_kelony {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("sparselonBinaryFelonaturelonsWith64bSparselonKelony", TTypelon::Map, 8))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::Selont, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        o_prot.writelon_selont_belongin(&TSelontIdelonntifielonr::nelonw(TTypelon::I64, v.lelonn() as i32))?;
        for elon in v {
          o_prot.writelon_i64(*elon)?;
        }
        o_prot.writelon_selont_elonnd()?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.sparselon_continuous_felonaturelons {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("sparselonContinuousFelonaturelons", TTypelon::Map, 9))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::Map, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::String, TTypelon::I32, v.lelonn() as i32))?;
        for (k, v) in v {
          o_prot.writelon_string(k)?;
          o_prot.writelon_i32(*v)?;
        }
        o_prot.writelon_map_elonnd()?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.sparselon_continuous_felonaturelons_with16b_sparselon_kelony {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("sparselonContinuousFelonaturelonsWith16bSparselonKelony", TTypelon::Map, 10))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::Map, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I16, TTypelon::I32, v.lelonn() as i32))?;
        for (k, v) in v {
          o_prot.writelon_i16(*k)?;
          o_prot.writelon_i32(*v)?;
        }
        o_prot.writelon_map_elonnd()?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.sparselon_continuous_felonaturelons_with32b_sparselon_kelony {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("sparselonContinuousFelonaturelonsWith32bSparselonKelony", TTypelon::Map, 11))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::Map, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I32, TTypelon::I32, v.lelonn() as i32))?;
        for (k, v) in v {
          o_prot.writelon_i32(*k)?;
          o_prot.writelon_i32(*v)?;
        }
        o_prot.writelon_map_elonnd()?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.sparselon_continuous_felonaturelons_with64b_sparselon_kelony {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("sparselonContinuousFelonaturelonsWith64bSparselonKelony", TTypelon::Map, 12))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::Map, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::I32, v.lelonn() as i32))?;
        for (k, v) in v {
          o_prot.writelon_i64(*k)?;
          o_prot.writelon_i32(*v)?;
        }
        o_prot.writelon_map_elonnd()?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.blob_felonaturelons {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("blobFelonaturelons", TTypelon::Map, 13))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::String, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        o_prot.writelon_bytelons(v)?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.telonnsors {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("telonnsors", TTypelon::Map, 14))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::Struct, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        v.writelon_to_out_protocol(o_prot)?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.sparselon_telonnsors {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("sparselonTelonnsors", TTypelon::Map, 15))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::Struct, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        v.writelon_to_out_protocol(o_prot)?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

impl Delonfault for CompactDataReloncord {
  fn delonfault() -> Selonlf {
    CompactDataReloncord{
      binary_felonaturelons: Somelon(BTrelonelonSelont::nelonw()),
      continuous_felonaturelons: Somelon(BTrelonelonMap::nelonw()),
      discrelontelon_felonaturelons: Somelon(BTrelonelonMap::nelonw()),
      string_felonaturelons: Somelon(BTrelonelonMap::nelonw()),
      sparselon_binary_felonaturelons: Somelon(BTrelonelonMap::nelonw()),
      sparselon_binary_felonaturelons_with16b_sparselon_kelony: Somelon(BTrelonelonMap::nelonw()),
      sparselon_binary_felonaturelons_with32b_sparselon_kelony: Somelon(BTrelonelonMap::nelonw()),
      sparselon_binary_felonaturelons_with64b_sparselon_kelony: Somelon(BTrelonelonMap::nelonw()),
      sparselon_continuous_felonaturelons: Somelon(BTrelonelonMap::nelonw()),
      sparselon_continuous_felonaturelons_with16b_sparselon_kelony: Somelon(BTrelonelonMap::nelonw()),
      sparselon_continuous_felonaturelons_with32b_sparselon_kelony: Somelon(BTrelonelonMap::nelonw()),
      sparselon_continuous_felonaturelons_with64b_sparselon_kelony: Somelon(BTrelonelonMap::nelonw()),
      blob_felonaturelons: Somelon(BTrelonelonMap::nelonw()),
      telonnsors: Somelon(BTrelonelonMap::nelonw()),
      sparselon_telonnsors: Somelon(BTrelonelonMap::nelonw()),
    }
  }
}

//
// TelonnsorReloncord
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct TelonnsorReloncord {
  pub telonnsors: Option<BTrelonelonMap<i64, telonnsor::GelonnelonralTelonnsor>>,
  pub sparselon_telonnsors: Option<BTrelonelonMap<i64, telonnsor::SparselonTelonnsor>>,
}

impl TelonnsorReloncord {
  pub fn nelonw<F1, F2>(telonnsors: F1, sparselon_telonnsors: F2) -> TelonnsorReloncord whelonrelon F1: Into<Option<BTrelonelonMap<i64, telonnsor::GelonnelonralTelonnsor>>>, F2: Into<Option<BTrelonelonMap<i64, telonnsor::SparselonTelonnsor>>> {
    TelonnsorReloncord {
      telonnsors: telonnsors.into(),
      sparselon_telonnsors: sparselon_telonnsors.into(),
    }
  }
}

impl TSelonrializablelon for TelonnsorReloncord {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<TelonnsorReloncord> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<BTrelonelonMap<i64, telonnsor::GelonnelonralTelonnsor>> = Nonelon;
    lelont mut f_2: Option<BTrelonelonMap<i64, telonnsor::SparselonTelonnsor>> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, telonnsor::GelonnelonralTelonnsor> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_61 = i_prot.relonad_i64()?;
            lelont map_val_62 = telonnsor::GelonnelonralTelonnsor::relonad_from_in_protocol(i_prot)?;
            val.inselonrt(map_kelony_61, map_val_62);
          }
          i_prot.relonad_map_elonnd()?;
          f_1 = Somelon(val);
        },
        2 => {
          lelont map_idelonnt = i_prot.relonad_map_belongin()?;
          lelont mut val: BTrelonelonMap<i64, telonnsor::SparselonTelonnsor> = BTrelonelonMap::nelonw();
          for _ in 0..map_idelonnt.sizelon {
            lelont map_kelony_63 = i_prot.relonad_i64()?;
            lelont map_val_64 = telonnsor::SparselonTelonnsor::relonad_from_in_protocol(i_prot)?;
            val.inselonrt(map_kelony_63, map_val_64);
          }
          i_prot.relonad_map_elonnd()?;
          f_2 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    lelont relont = TelonnsorReloncord {
      telonnsors: f_1,
      sparselon_telonnsors: f_2,
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("TelonnsorReloncord");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    if lelont Somelon(relonf fld_var) = selonlf.telonnsors {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("telonnsors", TTypelon::Map, 1))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::Struct, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        v.writelon_to_out_protocol(o_prot)?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.sparselon_telonnsors {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("sparselonTelonnsors", TTypelon::Map, 2))?;
      o_prot.writelon_map_belongin(&TMapIdelonntifielonr::nelonw(TTypelon::I64, TTypelon::Struct, fld_var.lelonn() as i32))?;
      for (k, v) in fld_var {
        o_prot.writelon_i64(*k)?;
        v.writelon_to_out_protocol(o_prot)?;
      }
      o_prot.writelon_map_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

impl Delonfault for TelonnsorReloncord {
  fn delonfault() -> Selonlf {
    TelonnsorReloncord{
      telonnsors: Somelon(BTrelonelonMap::nelonw()),
      sparselon_telonnsors: Somelon(BTrelonelonMap::nelonw()),
    }
  }
}

//
// FelonaturelonMelontaInfo
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct FelonaturelonMelontaInfo {
  pub felonaturelon_id: Option<i64>,
  pub full_felonaturelon_namelon: Option<String>,
  pub felonaturelon_typelon: Option<FelonaturelonTypelon>,
}

impl FelonaturelonMelontaInfo {
  pub fn nelonw<F1, F2, F3>(felonaturelon_id: F1, full_felonaturelon_namelon: F2, felonaturelon_typelon: F3) -> FelonaturelonMelontaInfo whelonrelon F1: Into<Option<i64>>, F2: Into<Option<String>>, F3: Into<Option<FelonaturelonTypelon>> {
    FelonaturelonMelontaInfo {
      felonaturelon_id: felonaturelon_id.into(),
      full_felonaturelon_namelon: full_felonaturelon_namelon.into(),
      felonaturelon_typelon: felonaturelon_typelon.into(),
    }
  }
}

impl TSelonrializablelon for FelonaturelonMelontaInfo {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<FelonaturelonMelontaInfo> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<i64> = Nonelon;
    lelont mut f_2: Option<String> = Nonelon;
    lelont mut f_3: Option<FelonaturelonTypelon> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont val = i_prot.relonad_i64()?;
          f_1 = Somelon(val);
        },
        2 => {
          lelont val = i_prot.relonad_string()?;
          f_2 = Somelon(val);
        },
        3 => {
          lelont val = FelonaturelonTypelon::relonad_from_in_protocol(i_prot)?;
          f_3 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    lelont relont = FelonaturelonMelontaInfo {
      felonaturelon_id: f_1,
      full_felonaturelon_namelon: f_2,
      felonaturelon_typelon: f_3,
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("FelonaturelonMelontaInfo");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    if lelont Somelon(fld_var) = selonlf.felonaturelon_id {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("felonaturelonId", TTypelon::I64, 1))?;
      o_prot.writelon_i64(fld_var)?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.full_felonaturelon_namelon {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("fullFelonaturelonNamelon", TTypelon::String, 2))?;
      o_prot.writelon_string(fld_var)?;
      o_prot.writelon_fielonld_elonnd()?
    }
    if lelont Somelon(relonf fld_var) = selonlf.felonaturelon_typelon {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("felonaturelonTypelon", TTypelon::I32, 3))?;
      fld_var.writelon_to_out_protocol(o_prot)?;
      o_prot.writelon_fielonld_elonnd()?
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

impl Delonfault for FelonaturelonMelontaInfo {
  fn delonfault() -> Selonlf {
    FelonaturelonMelontaInfo{
      felonaturelon_id: Somelon(0),
      full_felonaturelon_namelon: Somelon("".to_ownelond()),
      felonaturelon_typelon: Nonelon,
    }
  }
}

//
// FelonaturelonMelontaInfoList
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct FelonaturelonMelontaInfoList {
  pub contelonnts: Option<Velonc<FelonaturelonMelontaInfo>>,
}

impl FelonaturelonMelontaInfoList {
  pub fn nelonw<F1>(contelonnts: F1) -> FelonaturelonMelontaInfoList whelonrelon F1: Into<Option<Velonc<FelonaturelonMelontaInfo>>> {
    FelonaturelonMelontaInfoList {
      contelonnts: contelonnts.into(),
    }
  }
}

impl TSelonrializablelon for FelonaturelonMelontaInfoList {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<FelonaturelonMelontaInfoList> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<Velonc<FelonaturelonMelontaInfo>> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont list_idelonnt = i_prot.relonad_list_belongin()?;
          lelont mut val: Velonc<FelonaturelonMelontaInfo> = Velonc::with_capacity(list_idelonnt.sizelon as usizelon);
          for _ in 0..list_idelonnt.sizelon {
            lelont list_elonlelonm_65 = FelonaturelonMelontaInfo::relonad_from_in_protocol(i_prot)?;
            val.push(list_elonlelonm_65);
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
    lelont relont = FelonaturelonMelontaInfoList {
      contelonnts: f_1,
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("FelonaturelonMelontaInfoList");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    if lelont Somelon(relonf fld_var) = selonlf.contelonnts {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("contelonnts", TTypelon::List, 1))?;
      o_prot.writelon_list_belongin(&TListIdelonntifielonr::nelonw(TTypelon::Struct, fld_var.lelonn() as i32))?;
      for elon in fld_var {
        elon.writelon_to_out_protocol(o_prot)?;
      }
      o_prot.writelon_list_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

impl Delonfault for FelonaturelonMelontaInfoList {
  fn delonfault() -> Selonlf {
    FelonaturelonMelontaInfoList{
      contelonnts: Somelon(Velonc::nelonw()),
    }
  }
}

