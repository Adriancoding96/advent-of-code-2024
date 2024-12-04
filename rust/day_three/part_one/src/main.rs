use std::fs::read_to_string;
use regex::Regex;

fn main() {

    const FILE_PATH: &str = "../data.txt";
    let corrupted_data: &str = read_to_string(FILE_PATH)
        .expect("Expected String").as_str();

    let re: Regex = Regex::new(r"mul\\(\\d+,\\d+\\)").unwrap();
    let matches: Vec<_> = re.find_iter(corrupted_data).collect();

}
