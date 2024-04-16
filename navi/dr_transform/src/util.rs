use npyz::Wr erBu lder;
use npyz::{AutoSer al ze, Wr eOpt ons};
use std:: o::BufWr er;
use std::{
    fs::F le,
     o::{self, BufRead},
};

pub fn load_batch_pred ct on_request_base64(f le_na : &str) -> Vec<Vec<u8>> {
    let f le = F le::open(f le_na ).expect("could not read f le");
    let mut result = vec![];
    for (mut l ne_count, l ne)  n  o::BufReader::new(f le).l nes().enu rate() {
        l ne_count += 1;
        match base64::decode(l ne.unwrap().tr m()) {
            Ok(payload) => result.push(payload),
            Err(err) => pr ntln!("error decod ng l ne {f le_na }:{l ne_count} - {err}"),
        }
    }
    pr ntln!("result len: {}", result.len());
    result
}

pub fn save_to_npy<T: npyz::Ser al ze + AutoSer al ze>(data: &[T], save_to: Str ng) {
    let mut wr er = Wr eOpt ons::new()
        .default_dtype()
        .shape(&[data.len() as u64, 1])
        .wr er(BufWr er::new(F le::create(save_to).unwrap()))
        .beg n_nd()
        .unwrap();
    wr er.extend(data.to_owned()).unwrap();
    wr er.f n sh().unwrap();
}
