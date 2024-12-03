use std::{fs::File, io::BufReader, fs::read_to_string};
use std::str::FromStr;

fn main() {

    let mut list1: Vec<i32> = Vec::new();
    let mut list2: Vec<i32> = Vec::new();
   
    let file_name: &str = "../data.txt";
    let lines: Vec<String> = read_lines(file_name);  
   
    // Read file from file
    for line in lines.iter() {

        //Create substrings from line, splitting by " "x3
        let columns: Vec<&str> = line.split("   ").collect();

        //Check that vector contains two values
        if columns.len() < 2 || columns.len() > 2 {
            eprintln!("Error spliting line in to columns");
        }
        
        //Add valus to respective list
        list1.push(FromStr::from_str(columns[0]).expect("Expected &str"));
        list2.push(FromStr::from_str(columns[1]).expect("Expected &str"));
    }

    let mut similarity_score: i32 = 0;

    // Compary every value in list2 with every value in list1
    // If left value equals right value increment same value cunter
    // multiply left value with right value and add it to similarity score
    for left_val in list1.iter() {
        let mut same_value_occurrance: i32 = 0;
        for right_val in list2.iter() {
            if left_val == right_val {
                same_value_occurrance += 1;
            }
        }
        similarity_score += left_val * same_value_occurrance;
    }

    println!("{}", similarity_score);
}

// Helper function to read lines from file
fn read_lines(file_name: &str) -> Vec<String> {
    read_to_string(file_name)
        .unwrap()
        .lines()
        .map(String::from)
        .collect()
}
