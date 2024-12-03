use std::{fs::File, io::BufReader, fs::read_to_string};
use std::str::FromStr;

fn main() {
    
    let mut list1: Vec<i32> = Vec::new();
    let mut list2: Vec<i32> = Vec::new();
   
    let file_name: &str = "../data.txt";
    let lines: Vec<String> = read_lines(file_name);  
   
    // Read file from file
    for line in lines.iter() {
        let columns: Vec<&str> = line.split("   ").collect();
        if columns.len() < 2 || columns.len() > 2 {
            eprintln!("Error spliting line in to columns");
        }
        
        list1.push(FromStr::from_str(columns[0]).expect("Expected &str"));
        list2.push(FromStr::from_str(columns[1]).expect("Expected &str"));
    }

    list1.sort();
    list2.sort();

    let mut distances: Vec<i32> = Vec::new();

    for i in 0..list1.len() {
        if list1[i] == list2[i] {
            continue;
        }
        if list1[i] > list2[i] {
            distances.push(list1[i] - list2[i]);
        }
        if list1[i] < list2[i] {
            distances.push(list2[i] - list1[i]);
        }
    }
    
    let mut result: i32 = 0;
    for distance in distances.iter() {
        result += distance;
    }

    println!("{}", result);
}

// Helper function to read content of file
fn read_lines(file_name: &str) -> Vec<String> {
    read_to_string(file_name)
        .unwrap()
        .lines()
        .map(String::from)
        .collect()
}
