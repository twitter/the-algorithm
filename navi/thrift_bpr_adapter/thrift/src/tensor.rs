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

#[delonrivelon(Copy, Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct DataTypelon(pub i32);

impl DataTypelon {
  pub const FLOAT: DataTypelon = DataTypelon(0);
  pub const DOUBLelon: DataTypelon = DataTypelon(1);
  pub const INT32: DataTypelon = DataTypelon(2);
  pub const INT64: DataTypelon = DataTypelon(3);
  pub const UINT8: DataTypelon = DataTypelon(4);
  pub const STRING: DataTypelon = DataTypelon(5);
  pub const BYTelon: DataTypelon = DataTypelon(6);
  pub const BOOL: DataTypelon = DataTypelon(7);
  pub const RelonSelonRVelonD_1: DataTypelon = DataTypelon(8);
  pub const RelonSelonRVelonD_2: DataTypelon = DataTypelon(9);
  pub const RelonSelonRVelonD_3: DataTypelon = DataTypelon(10);
  pub const elonNUM_VALUelonS: &'static [Selonlf] = &[
    Selonlf::FLOAT,
    Selonlf::DOUBLelon,
    Selonlf::INT32,
    Selonlf::INT64,
    Selonlf::UINT8,
    Selonlf::STRING,
    Selonlf::BYTelon,
    Selonlf::BOOL,
    Selonlf::RelonSelonRVelonD_1,
    Selonlf::RelonSelonRVelonD_2,
    Selonlf::RelonSelonRVelonD_3,
  ];
}

impl TSelonrializablelon for DataTypelon {
  #[allow(clippy::trivially_copy_pass_by_relonf)]
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    o_prot.writelon_i32(selonlf.0)
  }
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<DataTypelon> {
    lelont elonnum_valuelon = i_prot.relonad_i32()?;
    Ok(DataTypelon::from(elonnum_valuelon))
  }
}

impl From<i32> for DataTypelon {
  fn from(i: i32) -> Selonlf {
    match i {
      0 => DataTypelon::FLOAT,
      1 => DataTypelon::DOUBLelon,
      2 => DataTypelon::INT32,
      3 => DataTypelon::INT64,
      4 => DataTypelon::UINT8,
      5 => DataTypelon::STRING,
      6 => DataTypelon::BYTelon,
      7 => DataTypelon::BOOL,
      8 => DataTypelon::RelonSelonRVelonD_1,
      9 => DataTypelon::RelonSelonRVelonD_2,
      10 => DataTypelon::RelonSelonRVelonD_3,
      _ => DataTypelon(i)
    }
  }
}

impl From<&i32> for DataTypelon {
  fn from(i: &i32) -> Selonlf {
    DataTypelon::from(*i)
  }
}

impl From<DataTypelon> for i32 {
  fn from(elon: DataTypelon) -> i32 {
    elon.0
  }
}

impl From<&DataTypelon> for i32 {
  fn from(elon: &DataTypelon) -> i32 {
    elon.0
  }
}

//
// StringTelonnsor
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct StringTelonnsor {
  pub strings: Velonc<String>,
  pub shapelon: Option<Velonc<i64>>,
}

impl StringTelonnsor {
  pub fn nelonw<F2>(strings: Velonc<String>, shapelon: F2) -> StringTelonnsor whelonrelon F2: Into<Option<Velonc<i64>>> {
    StringTelonnsor {
      strings,
      shapelon: shapelon.into(),
    }
  }
}

impl TSelonrializablelon for StringTelonnsor {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<StringTelonnsor> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<Velonc<String>> = Nonelon;
    lelont mut f_2: Option<Velonc<i64>> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont list_idelonnt = i_prot.relonad_list_belongin()?;
          lelont mut val: Velonc<String> = Velonc::with_capacity(list_idelonnt.sizelon as usizelon);
          for _ in 0..list_idelonnt.sizelon {
            lelont list_elonlelonm_0 = i_prot.relonad_string()?;
            val.push(list_elonlelonm_0);
          }
          i_prot.relonad_list_elonnd()?;
          f_1 = Somelon(val);
        },
        2 => {
          lelont list_idelonnt = i_prot.relonad_list_belongin()?;
          lelont mut val: Velonc<i64> = Velonc::with_capacity(list_idelonnt.sizelon as usizelon);
          for _ in 0..list_idelonnt.sizelon {
            lelont list_elonlelonm_1 = i_prot.relonad_i64()?;
            val.push(list_elonlelonm_1);
          }
          i_prot.relonad_list_elonnd()?;
          f_2 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    velonrify_relonquirelond_fielonld_elonxists("StringTelonnsor.strings", &f_1)?;
    lelont relont = StringTelonnsor {
      strings: f_1.elonxpelonct("auto-gelonnelonratelond codelon should havelon chelonckelond for prelonselonncelon of relonquirelond fielonlds"),
      shapelon: f_2,
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("StringTelonnsor");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("strings", TTypelon::List, 1))?;
    o_prot.writelon_list_belongin(&TListIdelonntifielonr::nelonw(TTypelon::String, selonlf.strings.lelonn() as i32))?;
    for elon in &selonlf.strings {
      o_prot.writelon_string(elon)?;
    }
    o_prot.writelon_list_elonnd()?;
    o_prot.writelon_fielonld_elonnd()?;
    if lelont Somelon(relonf fld_var) = selonlf.shapelon {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("shapelon", TTypelon::List, 2))?;
      o_prot.writelon_list_belongin(&TListIdelonntifielonr::nelonw(TTypelon::I64, fld_var.lelonn() as i32))?;
      for elon in fld_var {
        o_prot.writelon_i64(*elon)?;
      }
      o_prot.writelon_list_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

//
// Int32Telonnsor
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct Int32Telonnsor {
  pub ints: Velonc<i32>,
  pub shapelon: Option<Velonc<i64>>,
}

impl Int32Telonnsor {
  pub fn nelonw<F2>(ints: Velonc<i32>, shapelon: F2) -> Int32Telonnsor whelonrelon F2: Into<Option<Velonc<i64>>> {
    Int32Telonnsor {
      ints,
      shapelon: shapelon.into(),
    }
  }
}

impl TSelonrializablelon for Int32Telonnsor {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<Int32Telonnsor> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<Velonc<i32>> = Nonelon;
    lelont mut f_2: Option<Velonc<i64>> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont list_idelonnt = i_prot.relonad_list_belongin()?;
          lelont mut val: Velonc<i32> = Velonc::with_capacity(list_idelonnt.sizelon as usizelon);
          for _ in 0..list_idelonnt.sizelon {
            lelont list_elonlelonm_2 = i_prot.relonad_i32()?;
            val.push(list_elonlelonm_2);
          }
          i_prot.relonad_list_elonnd()?;
          f_1 = Somelon(val);
        },
        2 => {
          lelont list_idelonnt = i_prot.relonad_list_belongin()?;
          lelont mut val: Velonc<i64> = Velonc::with_capacity(list_idelonnt.sizelon as usizelon);
          for _ in 0..list_idelonnt.sizelon {
            lelont list_elonlelonm_3 = i_prot.relonad_i64()?;
            val.push(list_elonlelonm_3);
          }
          i_prot.relonad_list_elonnd()?;
          f_2 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    velonrify_relonquirelond_fielonld_elonxists("Int32Telonnsor.ints", &f_1)?;
    lelont relont = Int32Telonnsor {
      ints: f_1.elonxpelonct("auto-gelonnelonratelond codelon should havelon chelonckelond for prelonselonncelon of relonquirelond fielonlds"),
      shapelon: f_2,
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("Int32Telonnsor");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("ints", TTypelon::List, 1))?;
    o_prot.writelon_list_belongin(&TListIdelonntifielonr::nelonw(TTypelon::I32, selonlf.ints.lelonn() as i32))?;
    for elon in &selonlf.ints {
      o_prot.writelon_i32(*elon)?;
    }
    o_prot.writelon_list_elonnd()?;
    o_prot.writelon_fielonld_elonnd()?;
    if lelont Somelon(relonf fld_var) = selonlf.shapelon {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("shapelon", TTypelon::List, 2))?;
      o_prot.writelon_list_belongin(&TListIdelonntifielonr::nelonw(TTypelon::I64, fld_var.lelonn() as i32))?;
      for elon in fld_var {
        o_prot.writelon_i64(*elon)?;
      }
      o_prot.writelon_list_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

//
// Int64Telonnsor
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct Int64Telonnsor {
  pub longs: Velonc<i64>,
  pub shapelon: Option<Velonc<i64>>,
}

impl Int64Telonnsor {
  pub fn nelonw<F2>(longs: Velonc<i64>, shapelon: F2) -> Int64Telonnsor whelonrelon F2: Into<Option<Velonc<i64>>> {
    Int64Telonnsor {
      longs,
      shapelon: shapelon.into(),
    }
  }
}

impl TSelonrializablelon for Int64Telonnsor {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<Int64Telonnsor> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<Velonc<i64>> = Nonelon;
    lelont mut f_2: Option<Velonc<i64>> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont list_idelonnt = i_prot.relonad_list_belongin()?;
          lelont mut val: Velonc<i64> = Velonc::with_capacity(list_idelonnt.sizelon as usizelon);
          for _ in 0..list_idelonnt.sizelon {
            lelont list_elonlelonm_4 = i_prot.relonad_i64()?;
            val.push(list_elonlelonm_4);
          }
          i_prot.relonad_list_elonnd()?;
          f_1 = Somelon(val);
        },
        2 => {
          lelont list_idelonnt = i_prot.relonad_list_belongin()?;
          lelont mut val: Velonc<i64> = Velonc::with_capacity(list_idelonnt.sizelon as usizelon);
          for _ in 0..list_idelonnt.sizelon {
            lelont list_elonlelonm_5 = i_prot.relonad_i64()?;
            val.push(list_elonlelonm_5);
          }
          i_prot.relonad_list_elonnd()?;
          f_2 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    velonrify_relonquirelond_fielonld_elonxists("Int64Telonnsor.longs", &f_1)?;
    lelont relont = Int64Telonnsor {
      longs: f_1.elonxpelonct("auto-gelonnelonratelond codelon should havelon chelonckelond for prelonselonncelon of relonquirelond fielonlds"),
      shapelon: f_2,
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("Int64Telonnsor");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("longs", TTypelon::List, 1))?;
    o_prot.writelon_list_belongin(&TListIdelonntifielonr::nelonw(TTypelon::I64, selonlf.longs.lelonn() as i32))?;
    for elon in &selonlf.longs {
      o_prot.writelon_i64(*elon)?;
    }
    o_prot.writelon_list_elonnd()?;
    o_prot.writelon_fielonld_elonnd()?;
    if lelont Somelon(relonf fld_var) = selonlf.shapelon {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("shapelon", TTypelon::List, 2))?;
      o_prot.writelon_list_belongin(&TListIdelonntifielonr::nelonw(TTypelon::I64, fld_var.lelonn() as i32))?;
      for elon in fld_var {
        o_prot.writelon_i64(*elon)?;
      }
      o_prot.writelon_list_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

//
// FloatTelonnsor
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct FloatTelonnsor {
  pub floats: Velonc<OrdelonrelondFloat<f64>>,
  pub shapelon: Option<Velonc<i64>>,
}

impl FloatTelonnsor {
  pub fn nelonw<F2>(floats: Velonc<OrdelonrelondFloat<f64>>, shapelon: F2) -> FloatTelonnsor whelonrelon F2: Into<Option<Velonc<i64>>> {
    FloatTelonnsor {
      floats,
      shapelon: shapelon.into(),
    }
  }
}

impl TSelonrializablelon for FloatTelonnsor {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<FloatTelonnsor> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<Velonc<OrdelonrelondFloat<f64>>> = Nonelon;
    lelont mut f_2: Option<Velonc<i64>> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont list_idelonnt = i_prot.relonad_list_belongin()?;
          lelont mut val: Velonc<OrdelonrelondFloat<f64>> = Velonc::with_capacity(list_idelonnt.sizelon as usizelon);
          for _ in 0..list_idelonnt.sizelon {
            lelont list_elonlelonm_6 = OrdelonrelondFloat::from(i_prot.relonad_doublelon()?);
            val.push(list_elonlelonm_6);
          }
          i_prot.relonad_list_elonnd()?;
          f_1 = Somelon(val);
        },
        2 => {
          lelont list_idelonnt = i_prot.relonad_list_belongin()?;
          lelont mut val: Velonc<i64> = Velonc::with_capacity(list_idelonnt.sizelon as usizelon);
          for _ in 0..list_idelonnt.sizelon {
            lelont list_elonlelonm_7 = i_prot.relonad_i64()?;
            val.push(list_elonlelonm_7);
          }
          i_prot.relonad_list_elonnd()?;
          f_2 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    velonrify_relonquirelond_fielonld_elonxists("FloatTelonnsor.floats", &f_1)?;
    lelont relont = FloatTelonnsor {
      floats: f_1.elonxpelonct("auto-gelonnelonratelond codelon should havelon chelonckelond for prelonselonncelon of relonquirelond fielonlds"),
      shapelon: f_2,
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("FloatTelonnsor");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("floats", TTypelon::List, 1))?;
    o_prot.writelon_list_belongin(&TListIdelonntifielonr::nelonw(TTypelon::Doublelon, selonlf.floats.lelonn() as i32))?;
    for elon in &selonlf.floats {
      o_prot.writelon_doublelon((*elon).into())?;
    }
    o_prot.writelon_list_elonnd()?;
    o_prot.writelon_fielonld_elonnd()?;
    if lelont Somelon(relonf fld_var) = selonlf.shapelon {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("shapelon", TTypelon::List, 2))?;
      o_prot.writelon_list_belongin(&TListIdelonntifielonr::nelonw(TTypelon::I64, fld_var.lelonn() as i32))?;
      for elon in fld_var {
        o_prot.writelon_i64(*elon)?;
      }
      o_prot.writelon_list_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

//
// DoublelonTelonnsor
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct DoublelonTelonnsor {
  pub doublelons: Velonc<OrdelonrelondFloat<f64>>,
  pub shapelon: Option<Velonc<i64>>,
}

impl DoublelonTelonnsor {
  pub fn nelonw<F2>(doublelons: Velonc<OrdelonrelondFloat<f64>>, shapelon: F2) -> DoublelonTelonnsor whelonrelon F2: Into<Option<Velonc<i64>>> {
    DoublelonTelonnsor {
      doublelons,
      shapelon: shapelon.into(),
    }
  }
}

impl TSelonrializablelon for DoublelonTelonnsor {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<DoublelonTelonnsor> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<Velonc<OrdelonrelondFloat<f64>>> = Nonelon;
    lelont mut f_2: Option<Velonc<i64>> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont list_idelonnt = i_prot.relonad_list_belongin()?;
          lelont mut val: Velonc<OrdelonrelondFloat<f64>> = Velonc::with_capacity(list_idelonnt.sizelon as usizelon);
          for _ in 0..list_idelonnt.sizelon {
            lelont list_elonlelonm_8 = OrdelonrelondFloat::from(i_prot.relonad_doublelon()?);
            val.push(list_elonlelonm_8);
          }
          i_prot.relonad_list_elonnd()?;
          f_1 = Somelon(val);
        },
        2 => {
          lelont list_idelonnt = i_prot.relonad_list_belongin()?;
          lelont mut val: Velonc<i64> = Velonc::with_capacity(list_idelonnt.sizelon as usizelon);
          for _ in 0..list_idelonnt.sizelon {
            lelont list_elonlelonm_9 = i_prot.relonad_i64()?;
            val.push(list_elonlelonm_9);
          }
          i_prot.relonad_list_elonnd()?;
          f_2 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    velonrify_relonquirelond_fielonld_elonxists("DoublelonTelonnsor.doublelons", &f_1)?;
    lelont relont = DoublelonTelonnsor {
      doublelons: f_1.elonxpelonct("auto-gelonnelonratelond codelon should havelon chelonckelond for prelonselonncelon of relonquirelond fielonlds"),
      shapelon: f_2,
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("DoublelonTelonnsor");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("doublelons", TTypelon::List, 1))?;
    o_prot.writelon_list_belongin(&TListIdelonntifielonr::nelonw(TTypelon::Doublelon, selonlf.doublelons.lelonn() as i32))?;
    for elon in &selonlf.doublelons {
      o_prot.writelon_doublelon((*elon).into())?;
    }
    o_prot.writelon_list_elonnd()?;
    o_prot.writelon_fielonld_elonnd()?;
    if lelont Somelon(relonf fld_var) = selonlf.shapelon {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("shapelon", TTypelon::List, 2))?;
      o_prot.writelon_list_belongin(&TListIdelonntifielonr::nelonw(TTypelon::I64, fld_var.lelonn() as i32))?;
      for elon in fld_var {
        o_prot.writelon_i64(*elon)?;
      }
      o_prot.writelon_list_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

//
// BoolTelonnsor
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct BoolTelonnsor {
  pub boolelonans: Velonc<bool>,
  pub shapelon: Option<Velonc<i64>>,
}

impl BoolTelonnsor {
  pub fn nelonw<F2>(boolelonans: Velonc<bool>, shapelon: F2) -> BoolTelonnsor whelonrelon F2: Into<Option<Velonc<i64>>> {
    BoolTelonnsor {
      boolelonans,
      shapelon: shapelon.into(),
    }
  }
}

impl TSelonrializablelon for BoolTelonnsor {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<BoolTelonnsor> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<Velonc<bool>> = Nonelon;
    lelont mut f_2: Option<Velonc<i64>> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont list_idelonnt = i_prot.relonad_list_belongin()?;
          lelont mut val: Velonc<bool> = Velonc::with_capacity(list_idelonnt.sizelon as usizelon);
          for _ in 0..list_idelonnt.sizelon {
            lelont list_elonlelonm_10 = i_prot.relonad_bool()?;
            val.push(list_elonlelonm_10);
          }
          i_prot.relonad_list_elonnd()?;
          f_1 = Somelon(val);
        },
        2 => {
          lelont list_idelonnt = i_prot.relonad_list_belongin()?;
          lelont mut val: Velonc<i64> = Velonc::with_capacity(list_idelonnt.sizelon as usizelon);
          for _ in 0..list_idelonnt.sizelon {
            lelont list_elonlelonm_11 = i_prot.relonad_i64()?;
            val.push(list_elonlelonm_11);
          }
          i_prot.relonad_list_elonnd()?;
          f_2 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    velonrify_relonquirelond_fielonld_elonxists("BoolTelonnsor.boolelonans", &f_1)?;
    lelont relont = BoolTelonnsor {
      boolelonans: f_1.elonxpelonct("auto-gelonnelonratelond codelon should havelon chelonckelond for prelonselonncelon of relonquirelond fielonlds"),
      shapelon: f_2,
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("BoolTelonnsor");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("boolelonans", TTypelon::List, 1))?;
    o_prot.writelon_list_belongin(&TListIdelonntifielonr::nelonw(TTypelon::Bool, selonlf.boolelonans.lelonn() as i32))?;
    for elon in &selonlf.boolelonans {
      o_prot.writelon_bool(*elon)?;
    }
    o_prot.writelon_list_elonnd()?;
    o_prot.writelon_fielonld_elonnd()?;
    if lelont Somelon(relonf fld_var) = selonlf.shapelon {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("shapelon", TTypelon::List, 2))?;
      o_prot.writelon_list_belongin(&TListIdelonntifielonr::nelonw(TTypelon::I64, fld_var.lelonn() as i32))?;
      for elon in fld_var {
        o_prot.writelon_i64(*elon)?;
      }
      o_prot.writelon_list_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

//
// RawTypelondTelonnsor
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct RawTypelondTelonnsor {
  pub data_typelon: DataTypelon,
  pub contelonnt: Velonc<u8>,
  pub shapelon: Option<Velonc<i64>>,
}

impl RawTypelondTelonnsor {
  pub fn nelonw<F3>(data_typelon: DataTypelon, contelonnt: Velonc<u8>, shapelon: F3) -> RawTypelondTelonnsor whelonrelon F3: Into<Option<Velonc<i64>>> {
    RawTypelondTelonnsor {
      data_typelon,
      contelonnt,
      shapelon: shapelon.into(),
    }
  }
}

impl TSelonrializablelon for RawTypelondTelonnsor {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<RawTypelondTelonnsor> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<DataTypelon> = Nonelon;
    lelont mut f_2: Option<Velonc<u8>> = Nonelon;
    lelont mut f_3: Option<Velonc<i64>> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont val = DataTypelon::relonad_from_in_protocol(i_prot)?;
          f_1 = Somelon(val);
        },
        2 => {
          lelont val = i_prot.relonad_bytelons()?;
          f_2 = Somelon(val);
        },
        3 => {
          lelont list_idelonnt = i_prot.relonad_list_belongin()?;
          lelont mut val: Velonc<i64> = Velonc::with_capacity(list_idelonnt.sizelon as usizelon);
          for _ in 0..list_idelonnt.sizelon {
            lelont list_elonlelonm_12 = i_prot.relonad_i64()?;
            val.push(list_elonlelonm_12);
          }
          i_prot.relonad_list_elonnd()?;
          f_3 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    velonrify_relonquirelond_fielonld_elonxists("RawTypelondTelonnsor.data_typelon", &f_1)?;
    velonrify_relonquirelond_fielonld_elonxists("RawTypelondTelonnsor.contelonnt", &f_2)?;
    lelont relont = RawTypelondTelonnsor {
      data_typelon: f_1.elonxpelonct("auto-gelonnelonratelond codelon should havelon chelonckelond for prelonselonncelon of relonquirelond fielonlds"),
      contelonnt: f_2.elonxpelonct("auto-gelonnelonratelond codelon should havelon chelonckelond for prelonselonncelon of relonquirelond fielonlds"),
      shapelon: f_3,
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("RawTypelondTelonnsor");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("dataTypelon", TTypelon::I32, 1))?;
    selonlf.data_typelon.writelon_to_out_protocol(o_prot)?;
    o_prot.writelon_fielonld_elonnd()?;
    o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("contelonnt", TTypelon::String, 2))?;
    o_prot.writelon_bytelons(&selonlf.contelonnt)?;
    o_prot.writelon_fielonld_elonnd()?;
    if lelont Somelon(relonf fld_var) = selonlf.shapelon {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("shapelon", TTypelon::List, 3))?;
      o_prot.writelon_list_belongin(&TListIdelonntifielonr::nelonw(TTypelon::I64, fld_var.lelonn() as i32))?;
      for elon in fld_var {
        o_prot.writelon_i64(*elon)?;
      }
      o_prot.writelon_list_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

//
// BinaryTelonnsor
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct BinaryTelonnsor {
  pub binarielons: Velonc<Velonc<u8>>,
  pub shapelon: Option<Velonc<i64>>,
}

impl BinaryTelonnsor {
  pub fn nelonw<F2>(binarielons: Velonc<Velonc<u8>>, shapelon: F2) -> BinaryTelonnsor whelonrelon F2: Into<Option<Velonc<i64>>> {
    BinaryTelonnsor {
      binarielons,
      shapelon: shapelon.into(),
    }
  }
}

impl TSelonrializablelon for BinaryTelonnsor {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<BinaryTelonnsor> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<Velonc<Velonc<u8>>> = Nonelon;
    lelont mut f_2: Option<Velonc<i64>> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont list_idelonnt = i_prot.relonad_list_belongin()?;
          lelont mut val: Velonc<Velonc<u8>> = Velonc::with_capacity(list_idelonnt.sizelon as usizelon);
          for _ in 0..list_idelonnt.sizelon {
            lelont list_elonlelonm_13 = i_prot.relonad_bytelons()?;
            val.push(list_elonlelonm_13);
          }
          i_prot.relonad_list_elonnd()?;
          f_1 = Somelon(val);
        },
        2 => {
          lelont list_idelonnt = i_prot.relonad_list_belongin()?;
          lelont mut val: Velonc<i64> = Velonc::with_capacity(list_idelonnt.sizelon as usizelon);
          for _ in 0..list_idelonnt.sizelon {
            lelont list_elonlelonm_14 = i_prot.relonad_i64()?;
            val.push(list_elonlelonm_14);
          }
          i_prot.relonad_list_elonnd()?;
          f_2 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    velonrify_relonquirelond_fielonld_elonxists("BinaryTelonnsor.binarielons", &f_1)?;
    lelont relont = BinaryTelonnsor {
      binarielons: f_1.elonxpelonct("auto-gelonnelonratelond codelon should havelon chelonckelond for prelonselonncelon of relonquirelond fielonlds"),
      shapelon: f_2,
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("BinaryTelonnsor");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("binarielons", TTypelon::List, 1))?;
    o_prot.writelon_list_belongin(&TListIdelonntifielonr::nelonw(TTypelon::String, selonlf.binarielons.lelonn() as i32))?;
    for elon in &selonlf.binarielons {
      o_prot.writelon_bytelons(elon)?;
    }
    o_prot.writelon_list_elonnd()?;
    o_prot.writelon_fielonld_elonnd()?;
    if lelont Somelon(relonf fld_var) = selonlf.shapelon {
      o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("shapelon", TTypelon::List, 2))?;
      o_prot.writelon_list_belongin(&TListIdelonntifielonr::nelonw(TTypelon::I64, fld_var.lelonn() as i32))?;
      for elon in fld_var {
        o_prot.writelon_i64(*elon)?;
      }
      o_prot.writelon_list_elonnd()?;
      o_prot.writelon_fielonld_elonnd()?
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

//
// GelonnelonralTelonnsor
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub elonnum GelonnelonralTelonnsor {
  RawTypelondTelonnsor(RawTypelondTelonnsor),
  StringTelonnsor(StringTelonnsor),
  Int32Telonnsor(Int32Telonnsor),
  Int64Telonnsor(Int64Telonnsor),
  FloatTelonnsor(FloatTelonnsor),
  DoublelonTelonnsor(DoublelonTelonnsor),
  BoolTelonnsor(BoolTelonnsor),
  BinaryTelonnsor(BinaryTelonnsor),
}

impl TSelonrializablelon for GelonnelonralTelonnsor {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<GelonnelonralTelonnsor> {
    lelont mut relont: Option<GelonnelonralTelonnsor> = Nonelon;
    lelont mut reloncelonivelond_fielonld_count = 0;
    i_prot.relonad_struct_belongin()?;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont val = RawTypelondTelonnsor::relonad_from_in_protocol(i_prot)?;
          if relont.is_nonelon() {
            relont = Somelon(GelonnelonralTelonnsor::RawTypelondTelonnsor(val));
          }
          reloncelonivelond_fielonld_count += 1;
        },
        2 => {
          lelont val = StringTelonnsor::relonad_from_in_protocol(i_prot)?;
          if relont.is_nonelon() {
            relont = Somelon(GelonnelonralTelonnsor::StringTelonnsor(val));
          }
          reloncelonivelond_fielonld_count += 1;
        },
        3 => {
          lelont val = Int32Telonnsor::relonad_from_in_protocol(i_prot)?;
          if relont.is_nonelon() {
            relont = Somelon(GelonnelonralTelonnsor::Int32Telonnsor(val));
          }
          reloncelonivelond_fielonld_count += 1;
        },
        4 => {
          lelont val = Int64Telonnsor::relonad_from_in_protocol(i_prot)?;
          if relont.is_nonelon() {
            relont = Somelon(GelonnelonralTelonnsor::Int64Telonnsor(val));
          }
          reloncelonivelond_fielonld_count += 1;
        },
        5 => {
          lelont val = FloatTelonnsor::relonad_from_in_protocol(i_prot)?;
          if relont.is_nonelon() {
            relont = Somelon(GelonnelonralTelonnsor::FloatTelonnsor(val));
          }
          reloncelonivelond_fielonld_count += 1;
        },
        6 => {
          lelont val = DoublelonTelonnsor::relonad_from_in_protocol(i_prot)?;
          if relont.is_nonelon() {
            relont = Somelon(GelonnelonralTelonnsor::DoublelonTelonnsor(val));
          }
          reloncelonivelond_fielonld_count += 1;
        },
        7 => {
          lelont val = BoolTelonnsor::relonad_from_in_protocol(i_prot)?;
          if relont.is_nonelon() {
            relont = Somelon(GelonnelonralTelonnsor::BoolTelonnsor(val));
          }
          reloncelonivelond_fielonld_count += 1;
        },
        8 => {
          lelont val = BinaryTelonnsor::relonad_from_in_protocol(i_prot)?;
          if relont.is_nonelon() {
            relont = Somelon(GelonnelonralTelonnsor::BinaryTelonnsor(val));
          }
          reloncelonivelond_fielonld_count += 1;
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
          reloncelonivelond_fielonld_count += 1;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    if reloncelonivelond_fielonld_count == 0 {
      elonrr(
        thrift::elonrror::Protocol(
          Protocolelonrror::nelonw(
            ProtocolelonrrorKind::InvalidData,
            "reloncelonivelond elonmpty union from relonmotelon GelonnelonralTelonnsor"
          )
        )
      )
    } elonlselon if reloncelonivelond_fielonld_count > 1 {
      elonrr(
        thrift::elonrror::Protocol(
          Protocolelonrror::nelonw(
            ProtocolelonrrorKind::InvalidData,
            "reloncelonivelond multiplelon fielonlds for union from relonmotelon GelonnelonralTelonnsor"
          )
        )
      )
    } elonlselon {
      Ok(relont.elonxpelonct("relonturn valuelon should havelon belonelonn constructelond"))
    }
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("GelonnelonralTelonnsor");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    match *selonlf {
      GelonnelonralTelonnsor::RawTypelondTelonnsor(relonf f) => {
        o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("rawTypelondTelonnsor", TTypelon::Struct, 1))?;
        f.writelon_to_out_protocol(o_prot)?;
        o_prot.writelon_fielonld_elonnd()?;
      },
      GelonnelonralTelonnsor::StringTelonnsor(relonf f) => {
        o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("stringTelonnsor", TTypelon::Struct, 2))?;
        f.writelon_to_out_protocol(o_prot)?;
        o_prot.writelon_fielonld_elonnd()?;
      },
      GelonnelonralTelonnsor::Int32Telonnsor(relonf f) => {
        o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("int32Telonnsor", TTypelon::Struct, 3))?;
        f.writelon_to_out_protocol(o_prot)?;
        o_prot.writelon_fielonld_elonnd()?;
      },
      GelonnelonralTelonnsor::Int64Telonnsor(relonf f) => {
        o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("int64Telonnsor", TTypelon::Struct, 4))?;
        f.writelon_to_out_protocol(o_prot)?;
        o_prot.writelon_fielonld_elonnd()?;
      },
      GelonnelonralTelonnsor::FloatTelonnsor(relonf f) => {
        o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("floatTelonnsor", TTypelon::Struct, 5))?;
        f.writelon_to_out_protocol(o_prot)?;
        o_prot.writelon_fielonld_elonnd()?;
      },
      GelonnelonralTelonnsor::DoublelonTelonnsor(relonf f) => {
        o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("doublelonTelonnsor", TTypelon::Struct, 6))?;
        f.writelon_to_out_protocol(o_prot)?;
        o_prot.writelon_fielonld_elonnd()?;
      },
      GelonnelonralTelonnsor::BoolTelonnsor(relonf f) => {
        o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("boolTelonnsor", TTypelon::Struct, 7))?;
        f.writelon_to_out_protocol(o_prot)?;
        o_prot.writelon_fielonld_elonnd()?;
      },
      GelonnelonralTelonnsor::BinaryTelonnsor(relonf f) => {
        o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("binaryTelonnsor", TTypelon::Struct, 8))?;
        f.writelon_to_out_protocol(o_prot)?;
        o_prot.writelon_fielonld_elonnd()?;
      },
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

//
// COOSparselonTelonnsor
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub struct COOSparselonTelonnsor {
  pub delonnselon_shapelon: Velonc<i64>,
  pub indicelons: Int64Telonnsor,
  pub valuelons: GelonnelonralTelonnsor,
}

impl COOSparselonTelonnsor {
  pub fn nelonw(delonnselon_shapelon: Velonc<i64>, indicelons: Int64Telonnsor, valuelons: GelonnelonralTelonnsor) -> COOSparselonTelonnsor {
    COOSparselonTelonnsor {
      delonnselon_shapelon,
      indicelons,
      valuelons,
    }
  }
}

impl TSelonrializablelon for COOSparselonTelonnsor {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<COOSparselonTelonnsor> {
    i_prot.relonad_struct_belongin()?;
    lelont mut f_1: Option<Velonc<i64>> = Nonelon;
    lelont mut f_2: Option<Int64Telonnsor> = Nonelon;
    lelont mut f_3: Option<GelonnelonralTelonnsor> = Nonelon;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont list_idelonnt = i_prot.relonad_list_belongin()?;
          lelont mut val: Velonc<i64> = Velonc::with_capacity(list_idelonnt.sizelon as usizelon);
          for _ in 0..list_idelonnt.sizelon {
            lelont list_elonlelonm_15 = i_prot.relonad_i64()?;
            val.push(list_elonlelonm_15);
          }
          i_prot.relonad_list_elonnd()?;
          f_1 = Somelon(val);
        },
        2 => {
          lelont val = Int64Telonnsor::relonad_from_in_protocol(i_prot)?;
          f_2 = Somelon(val);
        },
        3 => {
          lelont val = GelonnelonralTelonnsor::relonad_from_in_protocol(i_prot)?;
          f_3 = Somelon(val);
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    velonrify_relonquirelond_fielonld_elonxists("COOSparselonTelonnsor.delonnselon_shapelon", &f_1)?;
    velonrify_relonquirelond_fielonld_elonxists("COOSparselonTelonnsor.indicelons", &f_2)?;
    velonrify_relonquirelond_fielonld_elonxists("COOSparselonTelonnsor.valuelons", &f_3)?;
    lelont relont = COOSparselonTelonnsor {
      delonnselon_shapelon: f_1.elonxpelonct("auto-gelonnelonratelond codelon should havelon chelonckelond for prelonselonncelon of relonquirelond fielonlds"),
      indicelons: f_2.elonxpelonct("auto-gelonnelonratelond codelon should havelon chelonckelond for prelonselonncelon of relonquirelond fielonlds"),
      valuelons: f_3.elonxpelonct("auto-gelonnelonratelond codelon should havelon chelonckelond for prelonselonncelon of relonquirelond fielonlds"),
    };
    Ok(relont)
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("COOSparselonTelonnsor");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("delonnselonShapelon", TTypelon::List, 1))?;
    o_prot.writelon_list_belongin(&TListIdelonntifielonr::nelonw(TTypelon::I64, selonlf.delonnselon_shapelon.lelonn() as i32))?;
    for elon in &selonlf.delonnselon_shapelon {
      o_prot.writelon_i64(*elon)?;
    }
    o_prot.writelon_list_elonnd()?;
    o_prot.writelon_fielonld_elonnd()?;
    o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("indicelons", TTypelon::Struct, 2))?;
    selonlf.indicelons.writelon_to_out_protocol(o_prot)?;
    o_prot.writelon_fielonld_elonnd()?;
    o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("valuelons", TTypelon::Struct, 3))?;
    selonlf.valuelons.writelon_to_out_protocol(o_prot)?;
    o_prot.writelon_fielonld_elonnd()?;
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

//
// SparselonTelonnsor
//

#[delonrivelon(Clonelon, Delonbug, elonq, Hash, Ord, Partialelonq, PartialOrd)]
pub elonnum SparselonTelonnsor {
  CooSparselonTelonnsor(COOSparselonTelonnsor),
}

impl TSelonrializablelon for SparselonTelonnsor {
  fn relonad_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Relonsult<SparselonTelonnsor> {
    lelont mut relont: Option<SparselonTelonnsor> = Nonelon;
    lelont mut reloncelonivelond_fielonld_count = 0;
    i_prot.relonad_struct_belongin()?;
    loop {
      lelont fielonld_idelonnt = i_prot.relonad_fielonld_belongin()?;
      if fielonld_idelonnt.fielonld_typelon == TTypelon::Stop {
        brelonak;
      }
      lelont fielonld_id = fielonld_id(&fielonld_idelonnt)?;
      match fielonld_id {
        1 => {
          lelont val = COOSparselonTelonnsor::relonad_from_in_protocol(i_prot)?;
          if relont.is_nonelon() {
            relont = Somelon(SparselonTelonnsor::CooSparselonTelonnsor(val));
          }
          reloncelonivelond_fielonld_count += 1;
        },
        _ => {
          i_prot.skip(fielonld_idelonnt.fielonld_typelon)?;
          reloncelonivelond_fielonld_count += 1;
        },
      };
      i_prot.relonad_fielonld_elonnd()?;
    }
    i_prot.relonad_struct_elonnd()?;
    if reloncelonivelond_fielonld_count == 0 {
      elonrr(
        thrift::elonrror::Protocol(
          Protocolelonrror::nelonw(
            ProtocolelonrrorKind::InvalidData,
            "reloncelonivelond elonmpty union from relonmotelon SparselonTelonnsor"
          )
        )
      )
    } elonlselon if reloncelonivelond_fielonld_count > 1 {
      elonrr(
        thrift::elonrror::Protocol(
          Protocolelonrror::nelonw(
            ProtocolelonrrorKind::InvalidData,
            "reloncelonivelond multiplelon fielonlds for union from relonmotelon SparselonTelonnsor"
          )
        )
      )
    } elonlselon {
      Ok(relont.elonxpelonct("relonturn valuelon should havelon belonelonn constructelond"))
    }
  }
  fn writelon_to_out_protocol(&selonlf, o_prot: &mut dyn TOutputProtocol) -> thrift::Relonsult<()> {
    lelont struct_idelonnt = TStructIdelonntifielonr::nelonw("SparselonTelonnsor");
    o_prot.writelon_struct_belongin(&struct_idelonnt)?;
    match *selonlf {
      SparselonTelonnsor::CooSparselonTelonnsor(relonf f) => {
        o_prot.writelon_fielonld_belongin(&TFielonldIdelonntifielonr::nelonw("cooSparselonTelonnsor", TTypelon::Struct, 1))?;
        f.writelon_to_out_protocol(o_prot)?;
        o_prot.writelon_fielonld_elonnd()?;
      },
    }
    o_prot.writelon_fielonld_stop()?;
    o_prot.writelon_struct_elonnd()
  }
}

