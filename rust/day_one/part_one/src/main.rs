use std::{fs::File, io::BufReader};

fn main() {
    
    let mut list1: Vec<i32> = Vec::new();
    let mut list2: Vec<i32> = Vec::new();
   
    //Read data from file
    let file_name: &str = "../data.txt";
    let file: File = File::open(file_name)
        .expect("Could not open file");

    let reader: BufReader = BufReader::new(file);
}
