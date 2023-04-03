use npyz::WriterBuilder;
use npyz::{AutoSerialize, WriteOptions};
use std::io::BufWriter;
use std::{
    fs::File,
    io::{self, BufRead},
};

pub fn load_batch_prediction_request_base64(file_name: &str) -> Vec<Vec<u8>> {
    let file = File::open(file_name).expect("could not read file");
    let mut result = vec![];
    for line in io::BufReader::new(file).lines() {
        #[allow(deprecated)]
        let decoded = base64::decode(line.unwrap().trim());
        match decoded {
            Ok(payload) => result.push(payload),
            Err(err) => println!("error decoding line {}", err),
        }
    }
    println!("reslt len: {}", result.len());
    return result;
}
pub fn save_to_npy<T: npyz::Serialize + AutoSerialize>(data: &[T], save_to: String) {
    let mut writer = WriteOptions::new()
        .default_dtype()
        .shape(&[data.len() as u64, 1])
        .writer(BufWriter::new(File::create(save_to).unwrap()))
        .begin_nd()
        .unwrap();
    writer.extend(data.to_owned()).unwrap();
    writer.finish().unwrap();
}
