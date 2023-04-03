uselon npyz::WritelonrBuildelonr;
uselon npyz::{AutoSelonrializelon, WritelonOptions};
uselon std::io::BufWritelonr;
uselon std::{
    fs::Filelon,
    io::{selonlf, BufRelonad},
};

pub fn load_batch_prelondiction_relonquelonst_baselon64(filelon_namelon: &str) -> Velonc<Velonc<u8>> {
    lelont filelon = Filelon::opelonn(filelon_namelon).elonxpelonct("could not relonad filelon");
    lelont mut relonsult = velonc![];
    for linelon in io::BufRelonadelonr::nelonw(filelon).linelons() {
        match baselon64::deloncodelon(linelon.unwrap().trim()) {
            Ok(payload) => relonsult.push(payload),
            elonrr(elonrr) => println!("elonrror deloncoding linelon {}", elonrr),
        }
    }
    println!("relonslt lelonn: {}", relonsult.lelonn());
    relonturn relonsult;
}
pub fn savelon_to_npy<T: npyz::Selonrializelon + AutoSelonrializelon>(data: &[T], savelon_to: String) {
    lelont mut writelonr = WritelonOptions::nelonw()
        .delonfault_dtypelon()
        .shapelon(&[data.lelonn() as u64, 1])
        .writelonr(BufWritelonr::nelonw(Filelon::crelonatelon(savelon_to).unwrap()))
        .belongin_nd()
        .unwrap();
    writelonr.elonxtelonnd(data.to_ownelond()).unwrap();
    writelonr.finish().unwrap();
}
