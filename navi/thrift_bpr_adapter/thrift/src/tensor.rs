// autogenewated by thwift compiwew (0.17.0)
// do n-nyot edit unwess y-you awe suwe t-that you know nyani y-you awe doing

#![awwow(unused_impowts)]
#![awwow(unused_extewn_cwates)]
#![awwow(cwippy::too_many_awguments, (U ﹏ U) c-cwippy::type_compwexity, rawr c-cwippy::vec_box)]
#![cfg_attw(wustfmt, -.- w-wustfmt_skip)]

u-use std::ceww::wefceww;
use std::cowwections::{btweemap, ( ͡o ω ͡o ) btweeset};
use std::convewt::{fwom, >_< twyfwom};
use std::defauwt::defauwt;
u-use std::ewwow::ewwow;
use std::fmt;
use std::fmt::{dispway, o.O f-fowmattew};
use std::wc::wc;

use t-thwift::owdewedfwoat;
use thwift::{appwicationewwow, σωσ appwicationewwowkind, -.- pwotocowewwow, σωσ p-pwotocowewwowkind, :3 tthwiftcwient};
u-use thwift::pwotocow::{tfiewdidentifiew, ^^ t-twistidentifiew, òωó tmapidentifiew, (ˆ ﻌ ˆ)♡ tmessageidentifiew, XD tmessagetype, òωó tinputpwotocow, (ꈍᴗꈍ) t-toutputpwotocow, UwU tsewiawizabwe, >w< tsetidentifiew, ʘwʘ tstwuctidentifiew, :3 ttype};
use thwift::pwotocow::fiewd_id;
u-use thwift::pwotocow::vewify_expected_message_type;
use thwift::pwotocow::vewify_expected_sequence_numbew;
u-use thwift::pwotocow::vewify_expected_sewvice_caww;
u-use thwift::pwotocow::vewify_wequiwed_fiewd_exists;
u-use thwift::sewvew::tpwocessow;

#[dewive(copy, c-cwone, ^•ﻌ•^ debug, eq, (ˆ ﻌ ˆ)♡ hash, owd, pawtiaweq, 🥺 p-pawtiawowd)]
pub stwuct datatype(pub i32);

impw d-datatype {
  pub const fwoat: datatype = datatype(0);
  pub const doubwe: datatype = datatype(1);
  p-pub const int32: datatype = d-datatype(2);
  p-pub const int64: d-datatype = datatype(3);
  pub const uint8: datatype = datatype(4);
  p-pub const s-stwing: datatype = datatype(5);
  p-pub const byte: d-datatype = datatype(6);
  pub c-const boow: datatype = datatype(7);
  p-pub const wesewved_1: datatype = datatype(8);
  p-pub const wesewved_2: datatype = d-datatype(9);
  pub const w-wesewved_3: datatype = d-datatype(10);
  pub const enum_vawues: &'static [sewf] = &[
    sewf::fwoat, OwO
    sewf::doubwe,
    sewf::int32, 🥺
    sewf::int64, OwO
    s-sewf::uint8, (U ᵕ U❁)
    sewf::stwing, ( ͡o ω ͡o )
    s-sewf::byte, ^•ﻌ•^
    sewf::boow, o.O
    s-sewf::wesewved_1, (⑅˘꒳˘)
    s-sewf::wesewved_2, (ˆ ﻌ ˆ)♡
    s-sewf::wesewved_3, :3
  ];
}

impw tsewiawizabwe fow datatype {
  #[awwow(cwippy::twiviawwy_copy_pass_by_wef)]
  fn wwite_to_out_pwotocow(&sewf, /(^•ω•^) o-o_pwot: &mut dyn toutputpwotocow) -> thwift::wesuwt<()> {
    o_pwot.wwite_i32(sewf.0)
  }
  fn wead_fwom_in_pwotocow(i_pwot: &mut dyn t-tinputpwotocow) -> thwift::wesuwt<datatype> {
    w-wet enum_vawue = i-i_pwot.wead_i32()?;
    o-ok(datatype::fwom(enum_vawue))
  }
}

impw fwom<i32> f-fow datatype {
  f-fn fwom(i: i32) -> s-sewf {
    m-match i {
      0 => datatype::fwoat, òωó
      1 => datatype::doubwe, :3
      2 => d-datatype::int32, (˘ω˘)
      3 => d-datatype::int64, 😳
      4 => d-datatype::uint8, σωσ
      5 => d-datatype::stwing,
      6 => datatype::byte, UwU
      7 => d-datatype::boow, -.-
      8 => datatype::wesewved_1, 🥺
      9 => datatype::wesewved_2, 😳😳😳
      10 => datatype::wesewved_3, 🥺
      _ => d-datatype(i)
    }
  }
}

impw fwom<&i32> fow datatype {
  fn fwom(i: &i32) -> sewf {
    datatype::fwom(*i)
  }
}

i-impw fwom<datatype> fow i32 {
  fn fwom(e: datatype) -> i-i32 {
    e.0
  }
}

i-impw fwom<&datatype> f-fow i32 {
  fn fwom(e: &datatype) -> i-i32 {
    e.0
  }
}

//
// stwingtensow
//

#[dewive(cwone, ^^ debug, e-eq, ^^;; hash, o-owd, >w< pawtiaweq, pawtiawowd)]
pub stwuct stwingtensow {
  pub stwings: vec<stwing>, σωσ
  pub shape: o-option<vec<i64>>, >w<
}

impw stwingtensow {
  p-pub fn nyew<f2>(stwings: v-vec<stwing>, (⑅˘꒳˘) s-shape: f2) -> stwingtensow whewe f2: into<option<vec<i64>>> {
    s-stwingtensow {
      s-stwings, òωó
      shape: shape.into(), (⑅˘꒳˘)
    }
  }
}

i-impw tsewiawizabwe f-fow stwingtensow {
  fn wead_fwom_in_pwotocow(i_pwot: &mut dyn tinputpwotocow) -> thwift::wesuwt<stwingtensow> {
    i-i_pwot.wead_stwuct_begin()?;
    w-wet mut f_1: option<vec<stwing>> = n-nyone;
    wet mut f_2: option<vec<i64>> = n-nyone;
    woop {
      w-wet fiewd_ident = i_pwot.wead_fiewd_begin()?;
      i-if fiewd_ident.fiewd_type == ttype::stop {
        bweak;
      }
      wet fiewd_id = fiewd_id(&fiewd_ident)?;
      match fiewd_id {
        1 => {
          w-wet w-wist_ident = i_pwot.wead_wist_begin()?;
          wet mut vaw: vec<stwing> = vec::with_capacity(wist_ident.size a-as usize);
          f-fow _ in 0..wist_ident.size {
            wet wist_ewem_0 = i_pwot.wead_stwing()?;
            vaw.push(wist_ewem_0);
          }
          i-i_pwot.wead_wist_end()?;
          f_1 = some(vaw);
        }, (ꈍᴗꈍ)
        2 => {
          wet wist_ident = i_pwot.wead_wist_begin()?;
          wet mut vaw: vec<i64> = v-vec::with_capacity(wist_ident.size as usize);
          fow _ in 0..wist_ident.size {
            w-wet wist_ewem_1 = i-i_pwot.wead_i64()?;
            vaw.push(wist_ewem_1);
          }
          i_pwot.wead_wist_end()?;
          f_2 = s-some(vaw);
        }, rawr x3
        _ => {
          i-i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, ( ͡o ω ͡o )
      };
      i_pwot.wead_fiewd_end()?;
    }
    i_pwot.wead_stwuct_end()?;
    vewify_wequiwed_fiewd_exists("stwingtensow.stwings", UwU &f_1)?;
    w-wet wet = stwingtensow {
      stwings: f_1.expect("auto-genewated c-code shouwd have checked fow pwesence of wequiwed fiewds"), ^^
      s-shape: f_2, (˘ω˘)
    };
    o-ok(wet)
  }
  fn w-wwite_to_out_pwotocow(&sewf, (ˆ ﻌ ˆ)♡ o_pwot: &mut d-dyn toutputpwotocow) -> thwift::wesuwt<()> {
    w-wet s-stwuct_ident = tstwuctidentifiew::new("stwingtensow");
    o-o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("stwings", OwO t-ttype::wist, 😳 1))?;
    o-o_pwot.wwite_wist_begin(&twistidentifiew::new(ttype::stwing, UwU sewf.stwings.wen() as i32))?;
    f-fow e i-in &sewf.stwings {
      o-o_pwot.wwite_stwing(e)?;
    }
    o_pwot.wwite_wist_end()?;
    o_pwot.wwite_fiewd_end()?;
    i-if wet some(wef fwd_vaw) = s-sewf.shape {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("shape", 🥺 ttype::wist, 2))?;
      o_pwot.wwite_wist_begin(&twistidentifiew::new(ttype::i64, 😳😳😳 fwd_vaw.wen() a-as i32))?;
      f-fow e in fwd_vaw {
        o-o_pwot.wwite_i64(*e)?;
      }
      o-o_pwot.wwite_wist_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    o-o_pwot.wwite_fiewd_stop()?;
    o_pwot.wwite_stwuct_end()
  }
}

//
// int32tensow
//

#[dewive(cwone, ʘwʘ debug, eq, /(^•ω•^) hash, owd, pawtiaweq, :3 pawtiawowd)]
p-pub stwuct int32tensow {
  p-pub ints: vec<i32>, :3
  pub s-shape: option<vec<i64>>, mya
}

impw i-int32tensow {
  pub fn nyew<f2>(ints: v-vec<i32>, (///ˬ///✿) s-shape: f2) -> i-int32tensow whewe f-f2: into<option<vec<i64>>> {
    i-int32tensow {
      ints, (⑅˘꒳˘)
      shape: shape.into(), :3
    }
  }
}

impw tsewiawizabwe fow int32tensow {
  fn wead_fwom_in_pwotocow(i_pwot: &mut dyn tinputpwotocow) -> t-thwift::wesuwt<int32tensow> {
    i-i_pwot.wead_stwuct_begin()?;
    w-wet mut f_1: option<vec<i32>> = n-nyone;
    wet mut f_2: option<vec<i64>> = nyone;
    w-woop {
      wet f-fiewd_ident = i_pwot.wead_fiewd_begin()?;
      i-if fiewd_ident.fiewd_type == ttype::stop {
        bweak;
      }
      w-wet fiewd_id = f-fiewd_id(&fiewd_ident)?;
      match fiewd_id {
        1 => {
          w-wet wist_ident = i-i_pwot.wead_wist_begin()?;
          wet mut vaw: vec<i32> = vec::with_capacity(wist_ident.size as usize);
          f-fow _ in 0..wist_ident.size {
            w-wet wist_ewem_2 = i-i_pwot.wead_i32()?;
            v-vaw.push(wist_ewem_2);
          }
          i-i_pwot.wead_wist_end()?;
          f_1 = some(vaw);
        }, /(^•ω•^)
        2 => {
          w-wet wist_ident = i-i_pwot.wead_wist_begin()?;
          wet mut vaw: vec<i64> = v-vec::with_capacity(wist_ident.size a-as usize);
          fow _ in 0..wist_ident.size {
            w-wet wist_ewem_3 = i_pwot.wead_i64()?;
            vaw.push(wist_ewem_3);
          }
          i-i_pwot.wead_wist_end()?;
          f_2 = s-some(vaw);
        }, ^^;;
        _ => {
          i-i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, (U ᵕ U❁)
      };
      i_pwot.wead_fiewd_end()?;
    }
    i-i_pwot.wead_stwuct_end()?;
    vewify_wequiwed_fiewd_exists("int32tensow.ints", (U ﹏ U) &f_1)?;
    wet wet = int32tensow {
      ints: f-f_1.expect("auto-genewated c-code shouwd have c-checked fow pwesence of wequiwed fiewds"),
      shape: f_2, mya
    };
    o-ok(wet)
  }
  fn wwite_to_out_pwotocow(&sewf, ^•ﻌ•^ o_pwot: &mut d-dyn toutputpwotocow) -> t-thwift::wesuwt<()> {
    wet stwuct_ident = t-tstwuctidentifiew::new("int32tensow");
    o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("ints", (U ﹏ U) t-ttype::wist, :3 1))?;
    o_pwot.wwite_wist_begin(&twistidentifiew::new(ttype::i32, rawr x3 sewf.ints.wen() a-as i32))?;
    fow e in &sewf.ints {
      o-o_pwot.wwite_i32(*e)?;
    }
    o-o_pwot.wwite_wist_end()?;
    o_pwot.wwite_fiewd_end()?;
    i-if wet some(wef fwd_vaw) = sewf.shape {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("shape", 😳😳😳 t-ttype::wist, >w< 2))?;
      o-o_pwot.wwite_wist_begin(&twistidentifiew::new(ttype::i64, òωó fwd_vaw.wen() as i32))?;
      fow e in fwd_vaw {
        o_pwot.wwite_i64(*e)?;
      }
      o_pwot.wwite_wist_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    o_pwot.wwite_fiewd_stop()?;
    o_pwot.wwite_stwuct_end()
  }
}

//
// int64tensow
//

#[dewive(cwone, 😳 debug, eq, (✿oωo) hash, owd, pawtiaweq, OwO p-pawtiawowd)]
p-pub stwuct int64tensow {
  pub wongs: vec<i64>, (U ﹏ U)
  p-pub shape: o-option<vec<i64>>, (ꈍᴗꈍ)
}

i-impw int64tensow {
  pub f-fn nyew<f2>(wongs: vec<i64>, rawr shape: f-f2) -> int64tensow w-whewe f2: into<option<vec<i64>>> {
    int64tensow {
      w-wongs, ^^
      shape: shape.into(), rawr
    }
  }
}

i-impw tsewiawizabwe f-fow int64tensow {
  fn wead_fwom_in_pwotocow(i_pwot: &mut dyn tinputpwotocow) -> t-thwift::wesuwt<int64tensow> {
    i-i_pwot.wead_stwuct_begin()?;
    w-wet mut f-f_1: option<vec<i64>> = n-nyone;
    w-wet mut f_2: o-option<vec<i64>> = n-nyone;
    woop {
      w-wet fiewd_ident = i_pwot.wead_fiewd_begin()?;
      i-if fiewd_ident.fiewd_type == t-ttype::stop {
        b-bweak;
      }
      wet fiewd_id = f-fiewd_id(&fiewd_ident)?;
      match fiewd_id {
        1 => {
          wet wist_ident = i-i_pwot.wead_wist_begin()?;
          wet mut vaw: v-vec<i64> = vec::with_capacity(wist_ident.size a-as usize);
          f-fow _ in 0..wist_ident.size {
            wet wist_ewem_4 = i-i_pwot.wead_i64()?;
            vaw.push(wist_ewem_4);
          }
          i_pwot.wead_wist_end()?;
          f-f_1 = some(vaw);
        }, nyaa~~
        2 => {
          wet wist_ident = i-i_pwot.wead_wist_begin()?;
          wet m-mut vaw: vec<i64> = vec::with_capacity(wist_ident.size as usize);
          fow _ in 0..wist_ident.size {
            w-wet wist_ewem_5 = i_pwot.wead_i64()?;
            v-vaw.push(wist_ewem_5);
          }
          i-i_pwot.wead_wist_end()?;
          f_2 = some(vaw);
        }, nyaa~~
        _ => {
          i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, o.O
      };
      i_pwot.wead_fiewd_end()?;
    }
    i-i_pwot.wead_stwuct_end()?;
    vewify_wequiwed_fiewd_exists("int64tensow.wongs", òωó &f_1)?;
    wet w-wet = int64tensow {
      w-wongs: f-f_1.expect("auto-genewated code shouwd have checked fow pwesence o-of wequiwed f-fiewds"), ^^;;
      shape: f_2, rawr
    };
    o-ok(wet)
  }
  fn wwite_to_out_pwotocow(&sewf, ^•ﻌ•^ o_pwot: &mut d-dyn toutputpwotocow) -> thwift::wesuwt<()> {
    w-wet stwuct_ident = t-tstwuctidentifiew::new("int64tensow");
    o-o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("wongs", nyaa~~ t-ttype::wist, nyaa~~ 1))?;
    o-o_pwot.wwite_wist_begin(&twistidentifiew::new(ttype::i64, 😳😳😳 s-sewf.wongs.wen() a-as i32))?;
    fow e i-in &sewf.wongs {
      o-o_pwot.wwite_i64(*e)?;
    }
    o-o_pwot.wwite_wist_end()?;
    o-o_pwot.wwite_fiewd_end()?;
    i-if wet some(wef f-fwd_vaw) = s-sewf.shape {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("shape", 😳😳😳 ttype::wist, σωσ 2))?;
      o-o_pwot.wwite_wist_begin(&twistidentifiew::new(ttype::i64, o.O fwd_vaw.wen() as i-i32))?;
      fow e in fwd_vaw {
        o-o_pwot.wwite_i64(*e)?;
      }
      o_pwot.wwite_wist_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    o-o_pwot.wwite_fiewd_stop()?;
    o-o_pwot.wwite_stwuct_end()
  }
}

//
// fwoattensow
//

#[dewive(cwone, σωσ debug, eq, nyaa~~ hash, owd, p-pawtiaweq, rawr x3 pawtiawowd)]
p-pub stwuct f-fwoattensow {
  pub fwoats: vec<owdewedfwoat<f64>>, (///ˬ///✿)
  pub shape: option<vec<i64>>, o.O
}

i-impw fwoattensow {
  p-pub fn nyew<f2>(fwoats: v-vec<owdewedfwoat<f64>>, òωó s-shape: f2) -> fwoattensow whewe f2: into<option<vec<i64>>> {
    fwoattensow {
      f-fwoats, OwO
      s-shape: shape.into(), σωσ
    }
  }
}

i-impw tsewiawizabwe f-fow fwoattensow {
  fn wead_fwom_in_pwotocow(i_pwot: &mut dyn tinputpwotocow) -> t-thwift::wesuwt<fwoattensow> {
    i-i_pwot.wead_stwuct_begin()?;
    wet mut f_1: option<vec<owdewedfwoat<f64>>> = n-nyone;
    wet mut f_2: option<vec<i64>> = n-nyone;
    woop {
      wet fiewd_ident = i-i_pwot.wead_fiewd_begin()?;
      if f-fiewd_ident.fiewd_type == ttype::stop {
        b-bweak;
      }
      w-wet fiewd_id = fiewd_id(&fiewd_ident)?;
      m-match fiewd_id {
        1 => {
          wet wist_ident = i-i_pwot.wead_wist_begin()?;
          w-wet mut vaw: v-vec<owdewedfwoat<f64>> = v-vec::with_capacity(wist_ident.size as u-usize);
          f-fow _ in 0..wist_ident.size {
            w-wet wist_ewem_6 = owdewedfwoat::fwom(i_pwot.wead_doubwe()?);
            v-vaw.push(wist_ewem_6);
          }
          i_pwot.wead_wist_end()?;
          f_1 = some(vaw);
        }, nyaa~~
        2 => {
          w-wet wist_ident = i-i_pwot.wead_wist_begin()?;
          w-wet mut vaw: vec<i64> = vec::with_capacity(wist_ident.size as usize);
          fow _ in 0..wist_ident.size {
            wet wist_ewem_7 = i-i_pwot.wead_i64()?;
            vaw.push(wist_ewem_7);
          }
          i-i_pwot.wead_wist_end()?;
          f-f_2 = some(vaw);
        }, OwO
        _ => {
          i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, ^^
      };
      i-i_pwot.wead_fiewd_end()?;
    }
    i_pwot.wead_stwuct_end()?;
    v-vewify_wequiwed_fiewd_exists("fwoattensow.fwoats", (///ˬ///✿) &f_1)?;
    w-wet wet = fwoattensow {
      f-fwoats: f_1.expect("auto-genewated c-code shouwd h-have checked fow pwesence of wequiwed fiewds"), σωσ
      shape: f_2, rawr x3
    };
    ok(wet)
  }
  f-fn wwite_to_out_pwotocow(&sewf, (ˆ ﻌ ˆ)♡ o_pwot: &mut d-dyn toutputpwotocow) -> thwift::wesuwt<()> {
    wet stwuct_ident = tstwuctidentifiew::new("fwoattensow");
    o-o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("fwoats", 🥺 ttype::wist, 1))?;
    o_pwot.wwite_wist_begin(&twistidentifiew::new(ttype::doubwe, (⑅˘꒳˘) sewf.fwoats.wen() a-as i32))?;
    f-fow e in &sewf.fwoats {
      o_pwot.wwite_doubwe((*e).into())?;
    }
    o-o_pwot.wwite_wist_end()?;
    o_pwot.wwite_fiewd_end()?;
    if wet some(wef fwd_vaw) = s-sewf.shape {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("shape", 😳😳😳 ttype::wist, 2))?;
      o-o_pwot.wwite_wist_begin(&twistidentifiew::new(ttype::i64, /(^•ω•^) fwd_vaw.wen() a-as i32))?;
      fow e in fwd_vaw {
        o_pwot.wwite_i64(*e)?;
      }
      o-o_pwot.wwite_wist_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    o_pwot.wwite_fiewd_stop()?;
    o-o_pwot.wwite_stwuct_end()
  }
}

//
// d-doubwetensow
//

#[dewive(cwone, >w< d-debug, ^•ﻌ•^ eq, hash, owd, 😳😳😳 pawtiaweq, pawtiawowd)]
p-pub stwuct doubwetensow {
  pub doubwes: vec<owdewedfwoat<f64>>, :3
  pub shape: option<vec<i64>>, (ꈍᴗꈍ)
}

impw doubwetensow {
  p-pub fn n-nyew<f2>(doubwes: v-vec<owdewedfwoat<f64>>, ^•ﻌ•^ s-shape: f2) -> doubwetensow whewe f2: i-into<option<vec<i64>>> {
    d-doubwetensow {
      doubwes, >w<
      shape: shape.into(), ^^;;
    }
  }
}

i-impw tsewiawizabwe fow doubwetensow {
  fn wead_fwom_in_pwotocow(i_pwot: &mut d-dyn tinputpwotocow) -> thwift::wesuwt<doubwetensow> {
    i_pwot.wead_stwuct_begin()?;
    w-wet m-mut f_1: option<vec<owdewedfwoat<f64>>> = nyone;
    w-wet mut f_2: o-option<vec<i64>> = n-nyone;
    woop {
      wet fiewd_ident = i_pwot.wead_fiewd_begin()?;
      i-if fiewd_ident.fiewd_type == ttype::stop {
        bweak;
      }
      w-wet fiewd_id = fiewd_id(&fiewd_ident)?;
      match fiewd_id {
        1 => {
          wet wist_ident = i-i_pwot.wead_wist_begin()?;
          w-wet mut vaw: v-vec<owdewedfwoat<f64>> = v-vec::with_capacity(wist_ident.size a-as usize);
          fow _ in 0..wist_ident.size {
            wet w-wist_ewem_8 = owdewedfwoat::fwom(i_pwot.wead_doubwe()?);
            vaw.push(wist_ewem_8);
          }
          i-i_pwot.wead_wist_end()?;
          f_1 = some(vaw);
        }, (✿oωo)
        2 => {
          w-wet wist_ident = i_pwot.wead_wist_begin()?;
          wet mut vaw: v-vec<i64> = vec::with_capacity(wist_ident.size a-as usize);
          f-fow _ in 0..wist_ident.size {
            wet w-wist_ewem_9 = i_pwot.wead_i64()?;
            vaw.push(wist_ewem_9);
          }
          i-i_pwot.wead_wist_end()?;
          f_2 = some(vaw);
        }, òωó
        _ => {
          i-i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, ^^
      };
      i-i_pwot.wead_fiewd_end()?;
    }
    i_pwot.wead_stwuct_end()?;
    v-vewify_wequiwed_fiewd_exists("doubwetensow.doubwes", ^^ &f_1)?;
    wet wet = doubwetensow {
      doubwes: f-f_1.expect("auto-genewated code s-shouwd have checked fow pwesence of wequiwed fiewds"), rawr
      s-shape: f-f_2, XD
    };
    o-ok(wet)
  }
  fn wwite_to_out_pwotocow(&sewf, rawr o-o_pwot: &mut dyn t-toutputpwotocow) -> thwift::wesuwt<()> {
    w-wet stwuct_ident = tstwuctidentifiew::new("doubwetensow");
    o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("doubwes", 😳 ttype::wist, 1))?;
    o-o_pwot.wwite_wist_begin(&twistidentifiew::new(ttype::doubwe, 🥺 s-sewf.doubwes.wen() as i32))?;
    fow e in &sewf.doubwes {
      o_pwot.wwite_doubwe((*e).into())?;
    }
    o_pwot.wwite_wist_end()?;
    o-o_pwot.wwite_fiewd_end()?;
    i-if wet some(wef fwd_vaw) = sewf.shape {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("shape", (U ᵕ U❁) ttype::wist, 2))?;
      o-o_pwot.wwite_wist_begin(&twistidentifiew::new(ttype::i64, 😳 fwd_vaw.wen() a-as i32))?;
      f-fow e in fwd_vaw {
        o_pwot.wwite_i64(*e)?;
      }
      o_pwot.wwite_wist_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    o_pwot.wwite_fiewd_stop()?;
    o-o_pwot.wwite_stwuct_end()
  }
}

//
// boowtensow
//

#[dewive(cwone, 🥺 debug, e-eq, (///ˬ///✿) hash, owd, pawtiaweq, mya pawtiawowd)]
p-pub stwuct b-boowtensow {
  pub booweans: v-vec<boow>, (✿oωo)
  pub s-shape: option<vec<i64>>, ^•ﻌ•^
}

i-impw b-boowtensow {
  p-pub fn nyew<f2>(booweans: v-vec<boow>, o.O shape: f2) -> boowtensow whewe f2: into<option<vec<i64>>> {
    boowtensow {
      booweans,
      s-shape: s-shape.into(), o.O
    }
  }
}

i-impw t-tsewiawizabwe fow b-boowtensow {
  f-fn wead_fwom_in_pwotocow(i_pwot: &mut dyn tinputpwotocow) -> thwift::wesuwt<boowtensow> {
    i_pwot.wead_stwuct_begin()?;
    wet mut f_1: option<vec<boow>> = nyone;
    wet m-mut f_2: option<vec<i64>> = n-nyone;
    woop {
      wet fiewd_ident = i_pwot.wead_fiewd_begin()?;
      i-if fiewd_ident.fiewd_type == t-ttype::stop {
        b-bweak;
      }
      wet fiewd_id = fiewd_id(&fiewd_ident)?;
      match f-fiewd_id {
        1 => {
          wet wist_ident = i_pwot.wead_wist_begin()?;
          wet m-mut vaw: vec<boow> = v-vec::with_capacity(wist_ident.size as usize);
          fow _ in 0..wist_ident.size {
            w-wet wist_ewem_10 = i_pwot.wead_boow()?;
            v-vaw.push(wist_ewem_10);
          }
          i-i_pwot.wead_wist_end()?;
          f_1 = some(vaw);
        }, XD
        2 => {
          w-wet wist_ident = i-i_pwot.wead_wist_begin()?;
          w-wet mut v-vaw: vec<i64> = v-vec::with_capacity(wist_ident.size a-as usize);
          fow _ i-in 0..wist_ident.size {
            w-wet wist_ewem_11 = i_pwot.wead_i64()?;
            v-vaw.push(wist_ewem_11);
          }
          i_pwot.wead_wist_end()?;
          f_2 = some(vaw);
        }, ^•ﻌ•^
        _ => {
          i-i_pwot.skip(fiewd_ident.fiewd_type)?;
        },
      };
      i_pwot.wead_fiewd_end()?;
    }
    i-i_pwot.wead_stwuct_end()?;
    vewify_wequiwed_fiewd_exists("boowtensow.booweans", ʘwʘ &f_1)?;
    w-wet wet = boowtensow {
      b-booweans: f_1.expect("auto-genewated code shouwd have c-checked fow pwesence of wequiwed fiewds"), (U ﹏ U)
      s-shape: f_2, 😳😳😳
    };
    o-ok(wet)
  }
  fn wwite_to_out_pwotocow(&sewf, 🥺 o_pwot: &mut d-dyn toutputpwotocow) -> t-thwift::wesuwt<()> {
    wet stwuct_ident = t-tstwuctidentifiew::new("boowtensow");
    o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("booweans", (///ˬ///✿) ttype::wist, 1))?;
    o-o_pwot.wwite_wist_begin(&twistidentifiew::new(ttype::boow, (˘ω˘) s-sewf.booweans.wen() as i32))?;
    f-fow e in &sewf.booweans {
      o-o_pwot.wwite_boow(*e)?;
    }
    o_pwot.wwite_wist_end()?;
    o_pwot.wwite_fiewd_end()?;
    i-if wet some(wef f-fwd_vaw) = sewf.shape {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("shape", :3 t-ttype::wist, /(^•ω•^) 2))?;
      o_pwot.wwite_wist_begin(&twistidentifiew::new(ttype::i64, :3 fwd_vaw.wen() as i32))?;
      fow e in fwd_vaw {
        o_pwot.wwite_i64(*e)?;
      }
      o-o_pwot.wwite_wist_end()?;
      o-o_pwot.wwite_fiewd_end()?
    }
    o-o_pwot.wwite_fiewd_stop()?;
    o-o_pwot.wwite_stwuct_end()
  }
}

//
// w-wawtypedtensow
//

#[dewive(cwone, mya d-debug, XD eq, hash, owd, p-pawtiaweq, (///ˬ///✿) pawtiawowd)]
p-pub stwuct wawtypedtensow {
  p-pub data_type: d-datatype, 🥺
  pub content: vec<u8>, o.O
  pub shape: o-option<vec<i64>>,
}

impw wawtypedtensow {
  pub fn nyew<f3>(data_type: d-datatype, mya content: vec<u8>, rawr x3 s-shape: f3) -> w-wawtypedtensow whewe f3: into<option<vec<i64>>> {
    w-wawtypedtensow {
      d-data_type, 😳
      c-content, 😳😳😳
      shape: shape.into(), >_<
    }
  }
}

i-impw tsewiawizabwe f-fow wawtypedtensow {
  fn wead_fwom_in_pwotocow(i_pwot: &mut d-dyn tinputpwotocow) -> thwift::wesuwt<wawtypedtensow> {
    i-i_pwot.wead_stwuct_begin()?;
    w-wet mut f_1: option<datatype> = n-nyone;
    wet mut f_2: option<vec<u8>> = n-nyone;
    wet mut f_3: option<vec<i64>> = n-nyone;
    woop {
      wet fiewd_ident = i_pwot.wead_fiewd_begin()?;
      if fiewd_ident.fiewd_type == ttype::stop {
        bweak;
      }
      w-wet fiewd_id = fiewd_id(&fiewd_ident)?;
      match fiewd_id {
        1 => {
          wet vaw = datatype::wead_fwom_in_pwotocow(i_pwot)?;
          f_1 = some(vaw);
        }, >w<
        2 => {
          wet vaw = i_pwot.wead_bytes()?;
          f-f_2 = some(vaw);
        }, rawr x3
        3 => {
          wet wist_ident = i_pwot.wead_wist_begin()?;
          w-wet mut vaw: vec<i64> = v-vec::with_capacity(wist_ident.size as usize);
          fow _ i-in 0..wist_ident.size {
            wet wist_ewem_12 = i-i_pwot.wead_i64()?;
            vaw.push(wist_ewem_12);
          }
          i-i_pwot.wead_wist_end()?;
          f-f_3 = some(vaw);
        }, XD
        _ => {
          i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, ^^
      };
      i_pwot.wead_fiewd_end()?;
    }
    i-i_pwot.wead_stwuct_end()?;
    vewify_wequiwed_fiewd_exists("wawtypedtensow.data_type", (✿oωo) &f_1)?;
    vewify_wequiwed_fiewd_exists("wawtypedtensow.content", >w< &f_2)?;
    wet wet = w-wawtypedtensow {
      data_type: f-f_1.expect("auto-genewated code shouwd have c-checked fow pwesence of wequiwed f-fiewds"), 😳😳😳
      c-content: f_2.expect("auto-genewated code shouwd have checked fow p-pwesence of wequiwed fiewds"), (ꈍᴗꈍ)
      shape: f_3, (✿oωo)
    };
    ok(wet)
  }
  f-fn wwite_to_out_pwotocow(&sewf, (˘ω˘) o_pwot: &mut dyn toutputpwotocow) -> thwift::wesuwt<()> {
    w-wet stwuct_ident = t-tstwuctidentifiew::new("wawtypedtensow");
    o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("datatype", nyaa~~ t-ttype::i32, ( ͡o ω ͡o ) 1))?;
    sewf.data_type.wwite_to_out_pwotocow(o_pwot)?;
    o-o_pwot.wwite_fiewd_end()?;
    o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("content", 🥺 ttype::stwing, (U ﹏ U) 2))?;
    o_pwot.wwite_bytes(&sewf.content)?;
    o_pwot.wwite_fiewd_end()?;
    if wet s-some(wef fwd_vaw) = s-sewf.shape {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("shape", ( ͡o ω ͡o ) t-ttype::wist, (///ˬ///✿) 3))?;
      o-o_pwot.wwite_wist_begin(&twistidentifiew::new(ttype::i64, (///ˬ///✿) fwd_vaw.wen() a-as i32))?;
      fow e in fwd_vaw {
        o_pwot.wwite_i64(*e)?;
      }
      o-o_pwot.wwite_wist_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    o_pwot.wwite_fiewd_stop()?;
    o-o_pwot.wwite_stwuct_end()
  }
}

//
// b-binawytensow
//

#[dewive(cwone, (✿oωo) debug, (U ᵕ U❁) eq, hash, owd, ʘwʘ pawtiaweq, p-pawtiawowd)]
pub stwuct binawytensow {
  pub binawies: vec<vec<u8>>, ʘwʘ
  pub shape: option<vec<i64>>, XD
}

impw binawytensow {
  pub f-fn nyew<f2>(binawies: v-vec<vec<u8>>, (✿oωo) shape: f2) -> b-binawytensow w-whewe f2: into<option<vec<i64>>> {
    binawytensow {
      b-binawies, ^•ﻌ•^
      shape: shape.into(), ^•ﻌ•^
    }
  }
}

impw tsewiawizabwe fow binawytensow {
  f-fn wead_fwom_in_pwotocow(i_pwot: &mut dyn tinputpwotocow) -> thwift::wesuwt<binawytensow> {
    i_pwot.wead_stwuct_begin()?;
    w-wet mut f_1: o-option<vec<vec<u8>>> = n-nyone;
    wet mut f_2: option<vec<i64>> = nyone;
    w-woop {
      wet f-fiewd_ident = i-i_pwot.wead_fiewd_begin()?;
      if fiewd_ident.fiewd_type == ttype::stop {
        b-bweak;
      }
      wet fiewd_id = f-fiewd_id(&fiewd_ident)?;
      match fiewd_id {
        1 => {
          w-wet wist_ident = i_pwot.wead_wist_begin()?;
          w-wet mut vaw: vec<vec<u8>> = vec::with_capacity(wist_ident.size a-as usize);
          fow _ i-in 0..wist_ident.size {
            w-wet wist_ewem_13 = i_pwot.wead_bytes()?;
            v-vaw.push(wist_ewem_13);
          }
          i-i_pwot.wead_wist_end()?;
          f_1 = s-some(vaw);
        }, >_<
        2 => {
          wet wist_ident = i-i_pwot.wead_wist_begin()?;
          wet mut vaw: v-vec<i64> = vec::with_capacity(wist_ident.size a-as usize);
          fow _ in 0..wist_ident.size {
            wet wist_ewem_14 = i-i_pwot.wead_i64()?;
            vaw.push(wist_ewem_14);
          }
          i_pwot.wead_wist_end()?;
          f_2 = some(vaw);
        }, mya
        _ => {
          i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, σωσ
      };
      i_pwot.wead_fiewd_end()?;
    }
    i_pwot.wead_stwuct_end()?;
    vewify_wequiwed_fiewd_exists("binawytensow.binawies", rawr &f_1)?;
    wet w-wet = binawytensow {
      binawies: f_1.expect("auto-genewated c-code shouwd have checked fow p-pwesence of wequiwed fiewds"), (✿oωo)
      shape: f_2, :3
    };
    o-ok(wet)
  }
  fn wwite_to_out_pwotocow(&sewf, rawr x3 o_pwot: &mut d-dyn toutputpwotocow) -> thwift::wesuwt<()> {
    wet stwuct_ident = tstwuctidentifiew::new("binawytensow");
    o-o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("binawies", ^^ ttype::wist, ^^ 1))?;
    o-o_pwot.wwite_wist_begin(&twistidentifiew::new(ttype::stwing, OwO sewf.binawies.wen() as i32))?;
    f-fow e in &sewf.binawies {
      o-o_pwot.wwite_bytes(e)?;
    }
    o_pwot.wwite_wist_end()?;
    o_pwot.wwite_fiewd_end()?;
    i-if wet some(wef f-fwd_vaw) = sewf.shape {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("shape", ʘwʘ ttype::wist, /(^•ω•^) 2))?;
      o-o_pwot.wwite_wist_begin(&twistidentifiew::new(ttype::i64, ʘwʘ fwd_vaw.wen() as i32))?;
      f-fow e in fwd_vaw {
        o_pwot.wwite_i64(*e)?;
      }
      o_pwot.wwite_wist_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    o-o_pwot.wwite_fiewd_stop()?;
    o_pwot.wwite_stwuct_end()
  }
}

//
// genewawtensow
//

#[dewive(cwone, (⑅˘꒳˘) debug, e-eq, hash, UwU owd, p-pawtiaweq, -.- pawtiawowd)]
p-pub enum genewawtensow {
  wawtypedtensow(wawtypedtensow), :3
  stwingtensow(stwingtensow), >_<
  i-int32tensow(int32tensow), nyaa~~
  int64tensow(int64tensow), ( ͡o ω ͡o )
  fwoattensow(fwoattensow), o.O
  d-doubwetensow(doubwetensow), :3
  boowtensow(boowtensow), (˘ω˘)
  b-binawytensow(binawytensow), rawr x3
}

i-impw tsewiawizabwe fow genewawtensow {
  fn wead_fwom_in_pwotocow(i_pwot: &mut dyn tinputpwotocow) -> thwift::wesuwt<genewawtensow> {
    wet m-mut wet: option<genewawtensow> = n-nyone;
    wet mut weceived_fiewd_count = 0;
    i_pwot.wead_stwuct_begin()?;
    w-woop {
      wet fiewd_ident = i_pwot.wead_fiewd_begin()?;
      i-if fiewd_ident.fiewd_type == t-ttype::stop {
        b-bweak;
      }
      w-wet f-fiewd_id = fiewd_id(&fiewd_ident)?;
      m-match fiewd_id {
        1 => {
          wet vaw = wawtypedtensow::wead_fwom_in_pwotocow(i_pwot)?;
          i-if wet.is_none() {
            w-wet = some(genewawtensow::wawtypedtensow(vaw));
          }
          w-weceived_fiewd_count += 1;
        }, (U ᵕ U❁)
        2 => {
          w-wet v-vaw = stwingtensow::wead_fwom_in_pwotocow(i_pwot)?;
          i-if wet.is_none() {
            w-wet = s-some(genewawtensow::stwingtensow(vaw));
          }
          w-weceived_fiewd_count += 1;
        }, 🥺
        3 => {
          wet vaw = int32tensow::wead_fwom_in_pwotocow(i_pwot)?;
          if wet.is_none() {
            w-wet = some(genewawtensow::int32tensow(vaw));
          }
          weceived_fiewd_count += 1;
        }, >_<
        4 => {
          wet vaw = int64tensow::wead_fwom_in_pwotocow(i_pwot)?;
          i-if wet.is_none() {
            wet = some(genewawtensow::int64tensow(vaw));
          }
          weceived_fiewd_count += 1;
        }, :3
        5 => {
          w-wet vaw = fwoattensow::wead_fwom_in_pwotocow(i_pwot)?;
          i-if wet.is_none() {
            wet = some(genewawtensow::fwoattensow(vaw));
          }
          weceived_fiewd_count += 1;
        }, :3
        6 => {
          wet vaw = d-doubwetensow::wead_fwom_in_pwotocow(i_pwot)?;
          i-if wet.is_none() {
            wet = some(genewawtensow::doubwetensow(vaw));
          }
          w-weceived_fiewd_count += 1;
        }, (ꈍᴗꈍ)
        7 => {
          w-wet vaw = boowtensow::wead_fwom_in_pwotocow(i_pwot)?;
          if wet.is_none() {
            wet = some(genewawtensow::boowtensow(vaw));
          }
          w-weceived_fiewd_count += 1;
        }, σωσ
        8 => {
          w-wet vaw = binawytensow::wead_fwom_in_pwotocow(i_pwot)?;
          if wet.is_none() {
            w-wet = s-some(genewawtensow::binawytensow(vaw));
          }
          weceived_fiewd_count += 1;
        }, 😳
        _ => {
          i_pwot.skip(fiewd_ident.fiewd_type)?;
          weceived_fiewd_count += 1;
        }, mya
      };
      i-i_pwot.wead_fiewd_end()?;
    }
    i_pwot.wead_stwuct_end()?;
    if weceived_fiewd_count == 0 {
      eww(
        thwift::ewwow::pwotocow(
          pwotocowewwow::new(
            p-pwotocowewwowkind::invawiddata, (///ˬ///✿)
            "weceived empty union fwom wemote genewawtensow"
          )
        )
      )
    } e-ewse i-if weceived_fiewd_count > 1 {
      e-eww(
        thwift::ewwow::pwotocow(
          p-pwotocowewwow::new(
            p-pwotocowewwowkind::invawiddata, ^^
            "weceived m-muwtipwe f-fiewds fow union f-fwom wemote genewawtensow"
          )
        )
      )
    } ewse {
      o-ok(wet.expect("wetuwn v-vawue shouwd h-have been constwucted"))
    }
  }
  fn wwite_to_out_pwotocow(&sewf, (✿oωo) o-o_pwot: &mut d-dyn toutputpwotocow) -> t-thwift::wesuwt<()> {
    wet stwuct_ident = t-tstwuctidentifiew::new("genewawtensow");
    o-o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    m-match *sewf {
      g-genewawtensow::wawtypedtensow(wef f-f) => {
        o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("wawtypedtensow", ( ͡o ω ͡o ) t-ttype::stwuct, ^^;; 1))?;
        f.wwite_to_out_pwotocow(o_pwot)?;
        o-o_pwot.wwite_fiewd_end()?;
      }, :3
      genewawtensow::stwingtensow(wef f) => {
        o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("stwingtensow", 😳 ttype::stwuct, XD 2))?;
        f-f.wwite_to_out_pwotocow(o_pwot)?;
        o_pwot.wwite_fiewd_end()?;
      }, (///ˬ///✿)
      genewawtensow::int32tensow(wef f) => {
        o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("int32tensow", o.O ttype::stwuct, o.O 3))?;
        f-f.wwite_to_out_pwotocow(o_pwot)?;
        o-o_pwot.wwite_fiewd_end()?;
      }, XD
      genewawtensow::int64tensow(wef f) => {
        o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("int64tensow", ^^;; t-ttype::stwuct, 😳😳😳 4))?;
        f.wwite_to_out_pwotocow(o_pwot)?;
        o_pwot.wwite_fiewd_end()?;
      }, (U ᵕ U❁)
      g-genewawtensow::fwoattensow(wef f-f) => {
        o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("fwoattensow", /(^•ω•^) t-ttype::stwuct, 😳😳😳 5))?;
        f-f.wwite_to_out_pwotocow(o_pwot)?;
        o-o_pwot.wwite_fiewd_end()?;
      }, rawr x3
      genewawtensow::doubwetensow(wef f) => {
        o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("doubwetensow", ʘwʘ ttype::stwuct, 6))?;
        f.wwite_to_out_pwotocow(o_pwot)?;
        o_pwot.wwite_fiewd_end()?;
      }, UwU
      genewawtensow::boowtensow(wef f-f) => {
        o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("boowtensow", (⑅˘꒳˘) ttype::stwuct, ^^ 7))?;
        f.wwite_to_out_pwotocow(o_pwot)?;
        o_pwot.wwite_fiewd_end()?;
      }, 😳😳😳
      g-genewawtensow::binawytensow(wef f-f) => {
        o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("binawytensow", òωó ttype::stwuct, ^^;; 8))?;
        f-f.wwite_to_out_pwotocow(o_pwot)?;
        o_pwot.wwite_fiewd_end()?;
      }, (✿oωo)
    }
    o-o_pwot.wwite_fiewd_stop()?;
    o-o_pwot.wwite_stwuct_end()
  }
}

//
// c-coospawsetensow
//

#[dewive(cwone, rawr debug, XD eq, hash, owd, 😳 pawtiaweq, pawtiawowd)]
p-pub stwuct coospawsetensow {
  p-pub dense_shape: vec<i64>, (U ᵕ U❁)
  p-pub indices: int64tensow, UwU
  pub vawues: genewawtensow, OwO
}

impw c-coospawsetensow {
  pub fn nyew(dense_shape: v-vec<i64>, 😳 indices: int64tensow, (˘ω˘) vawues: genewawtensow) -> c-coospawsetensow {
    coospawsetensow {
      d-dense_shape, òωó
      indices, OwO
      vawues, (✿oωo)
    }
  }
}

impw tsewiawizabwe fow coospawsetensow {
  fn wead_fwom_in_pwotocow(i_pwot: &mut dyn tinputpwotocow) -> t-thwift::wesuwt<coospawsetensow> {
    i-i_pwot.wead_stwuct_begin()?;
    wet m-mut f_1: option<vec<i64>> = nyone;
    w-wet mut f_2: option<int64tensow> = none;
    w-wet mut f_3: option<genewawtensow> = nyone;
    woop {
      w-wet fiewd_ident = i-i_pwot.wead_fiewd_begin()?;
      i-if fiewd_ident.fiewd_type == t-ttype::stop {
        bweak;
      }
      wet fiewd_id = fiewd_id(&fiewd_ident)?;
      match fiewd_id {
        1 => {
          w-wet wist_ident = i-i_pwot.wead_wist_begin()?;
          wet mut vaw: vec<i64> = vec::with_capacity(wist_ident.size a-as usize);
          fow _ i-in 0..wist_ident.size {
            w-wet wist_ewem_15 = i-i_pwot.wead_i64()?;
            vaw.push(wist_ewem_15);
          }
          i_pwot.wead_wist_end()?;
          f_1 = some(vaw);
        }, (⑅˘꒳˘)
        2 => {
          wet vaw = int64tensow::wead_fwom_in_pwotocow(i_pwot)?;
          f-f_2 = some(vaw);
        },
        3 => {
          wet vaw = g-genewawtensow::wead_fwom_in_pwotocow(i_pwot)?;
          f_3 = some(vaw);
        }, /(^•ω•^)
        _ => {
          i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, 🥺
      };
      i_pwot.wead_fiewd_end()?;
    }
    i_pwot.wead_stwuct_end()?;
    v-vewify_wequiwed_fiewd_exists("coospawsetensow.dense_shape", -.- &f_1)?;
    vewify_wequiwed_fiewd_exists("coospawsetensow.indices", ( ͡o ω ͡o ) &f_2)?;
    v-vewify_wequiwed_fiewd_exists("coospawsetensow.vawues", 😳😳😳 &f_3)?;
    wet wet = coospawsetensow {
      dense_shape: f-f_1.expect("auto-genewated c-code s-shouwd have checked f-fow pwesence o-of wequiwed fiewds"), (˘ω˘)
      indices: f-f_2.expect("auto-genewated c-code shouwd have checked fow pwesence o-of wequiwed fiewds"), ^^
      vawues: f_3.expect("auto-genewated c-code shouwd have checked fow p-pwesence of wequiwed f-fiewds"),
    };
    ok(wet)
  }
  f-fn wwite_to_out_pwotocow(&sewf, σωσ o-o_pwot: &mut dyn toutputpwotocow) -> thwift::wesuwt<()> {
    wet stwuct_ident = t-tstwuctidentifiew::new("coospawsetensow");
    o-o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("denseshape", t-ttype::wist, 🥺 1))?;
    o_pwot.wwite_wist_begin(&twistidentifiew::new(ttype::i64, 🥺 sewf.dense_shape.wen() as i32))?;
    fow e in &sewf.dense_shape {
      o-o_pwot.wwite_i64(*e)?;
    }
    o_pwot.wwite_wist_end()?;
    o_pwot.wwite_fiewd_end()?;
    o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("indices", /(^•ω•^) ttype::stwuct, (⑅˘꒳˘) 2))?;
    sewf.indices.wwite_to_out_pwotocow(o_pwot)?;
    o-o_pwot.wwite_fiewd_end()?;
    o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("vawues", -.- ttype::stwuct, 😳 3))?;
    sewf.vawues.wwite_to_out_pwotocow(o_pwot)?;
    o-o_pwot.wwite_fiewd_end()?;
    o_pwot.wwite_fiewd_stop()?;
    o-o_pwot.wwite_stwuct_end()
  }
}

//
// s-spawsetensow
//

#[dewive(cwone, 😳😳😳 d-debug, eq, >w< hash, owd, p-pawtiaweq, UwU pawtiawowd)]
p-pub enum spawsetensow {
  c-coospawsetensow(coospawsetensow), /(^•ω•^)
}

i-impw t-tsewiawizabwe fow s-spawsetensow {
  fn wead_fwom_in_pwotocow(i_pwot: &mut d-dyn tinputpwotocow) -> t-thwift::wesuwt<spawsetensow> {
    w-wet mut wet: option<spawsetensow> = n-nyone;
    wet mut weceived_fiewd_count = 0;
    i_pwot.wead_stwuct_begin()?;
    woop {
      wet fiewd_ident = i_pwot.wead_fiewd_begin()?;
      i-if fiewd_ident.fiewd_type == t-ttype::stop {
        bweak;
      }
      w-wet fiewd_id = fiewd_id(&fiewd_ident)?;
      match fiewd_id {
        1 => {
          w-wet vaw = c-coospawsetensow::wead_fwom_in_pwotocow(i_pwot)?;
          if w-wet.is_none() {
            w-wet = some(spawsetensow::coospawsetensow(vaw));
          }
          w-weceived_fiewd_count += 1;
        }, 🥺
        _ => {
          i_pwot.skip(fiewd_ident.fiewd_type)?;
          weceived_fiewd_count += 1;
        }, >_<
      };
      i-i_pwot.wead_fiewd_end()?;
    }
    i-i_pwot.wead_stwuct_end()?;
    if weceived_fiewd_count == 0 {
      eww(
        thwift::ewwow::pwotocow(
          pwotocowewwow::new(
            p-pwotocowewwowkind::invawiddata, rawr
            "weceived empty union f-fwom wemote spawsetensow"
          )
        )
      )
    } ewse if weceived_fiewd_count > 1 {
      eww(
        t-thwift::ewwow::pwotocow(
          pwotocowewwow::new(
            p-pwotocowewwowkind::invawiddata, (ꈍᴗꈍ)
            "weceived muwtipwe fiewds fow union fwom wemote s-spawsetensow"
          )
        )
      )
    } ewse {
      o-ok(wet.expect("wetuwn vawue s-shouwd have been c-constwucted"))
    }
  }
  fn wwite_to_out_pwotocow(&sewf, -.- o_pwot: &mut d-dyn toutputpwotocow) -> thwift::wesuwt<()> {
    wet stwuct_ident = t-tstwuctidentifiew::new("spawsetensow");
    o-o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    m-match *sewf {
      spawsetensow::coospawsetensow(wef f) => {
        o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("coospawsetensow", ( ͡o ω ͡o ) ttype::stwuct, (⑅˘꒳˘) 1))?;
        f.wwite_to_out_pwotocow(o_pwot)?;
        o-o_pwot.wwite_fiewd_end()?;
      }, mya
    }
    o_pwot.wwite_fiewd_stop()?;
    o_pwot.wwite_stwuct_end()
  }
}

