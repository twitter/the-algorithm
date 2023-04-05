use {
    npyz::{AutoSerialize, WriteOptions, WriterBuilder},
    std::{
        fs::File,
        io::{BufRead, BufReader, BufWriter},
    },
};

pub fn load_batch_prediction_request_base64(file_name: &str) -> Vec<Vec<u8>> {
    let file = File::open(file_name).expect("could not read file");
    let mut result = vec![];
    for (mut line_count, line) in BufReader::new(file).lines().enumerate() {
        line_count += 1;

        #[allow(deprecated)]
        let decoded = base64::decode(line.unwrap().trim());

        match decoded {
            Ok(payload) => result.push(payload),
            Err(err) => println!("error decoding line {file_name}:{line_count} - {err}"),
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
