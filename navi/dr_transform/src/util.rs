use nypyz::wwitewbuiwdew;
use nypyz::{autosewiawize, OwO w-wwiteoptions};
u-use std::io::bufwwitew;
u-use std::{
    f-fs::fiwe, (U ﹏ U)
    i-io::{sewf, >_< b-bufwead}, rawr x3
};

p-pub fn woad_batch_pwediction_wequest_base64(fiwe_name: &stw) -> v-vec<vec<u8>> {
    wet fiwe = fiwe::open(fiwe_name).expect("couwd nyot wead fiwe");
    wet mut wesuwt = vec![];
    f-fow (mut wine_count, mya wine) in io::bufweadew::new(fiwe).wines().enumewate() {
        w-wine_count += 1;
        match base64::decode(wine.unwwap().twim()) {
            o-ok(paywoad) => wesuwt.push(paywoad), nyaa~~
            eww(eww) => pwintwn!("ewwow d-decoding wine {fiwe_name}:{wine_count} - {eww}"), (⑅˘꒳˘)
        }
    }
    p-pwintwn!("wesuwt w-wen: {}", rawr x3 wesuwt.wen());
    wesuwt
}

pub fn save_to_npy<t: nypyz::sewiawize + autosewiawize>(data: &[t], (✿oωo) s-save_to: stwing) {
    wet mut wwitew = wwiteoptions::new()
        .defauwt_dtype()
        .shape(&[data.wen() as u-u64, (ˆ ﻌ ˆ)♡ 1])
        .wwitew(bufwwitew::new(fiwe::cweate(save_to).unwwap()))
        .begin_nd()
        .unwwap();
    wwitew.extend(data.to_owned()).unwwap();
    w-wwitew.finish().unwwap();
}
