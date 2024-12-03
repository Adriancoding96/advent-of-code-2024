use std::fs::read_to_string;
use std::str::FromStr;

fn main() {

    const FILE_NAME: &str = "../data.txt";
    let lines: Vec<String> = read_lines(FILE_NAME);

    let mut reports: Vec<Vec<i32>> = Vec::new();

    for line in lines.iter() {
        let str_values: Vec<&str> = line.split(" ").collect();
        let mut values: Vec<i32> = Vec::new();
        for str_value in str_values.iter() {
            values.push(FromStr::from_str(str_value).expect("Exptected &str"));
        }
        reports.push(values);
    } 

    #[derive(PartialEq)]
    enum State {
        NILL,
        INCREASING,
        DECREASING,
    }

    let mut safe_reports: i32 = 0;

    for i in 0..reports.len() {
        let mut is_safe: bool = true;
        let mut state: State = State::NILL;
        let values: Vec<i32> = reports[i].clone();
        
        let n: usize = values.len() - 1;
        for j in 0..n {
            let curr_value = values[j];
            let next_value = values[j + 1];

            if curr_value == next_value {
                is_safe = false;
                break;
            }

            if curr_value < next_value {
                if state == State::DECREASING {
                    is_safe = false;
                    break;
                }
                if next_value - curr_value > 3 {
                    is_safe = false;
                    break;
                }
                state = State::INCREASING;
            }

            if curr_value > next_value {
                if state == State::INCREASING {
                    is_safe = false;
                    break;
                }

                if curr_value - next_value > 3 {
                    is_safe = false;
                    break;
                }

                state = State::DECREASING;
            }
        }
        if is_safe {
            safe_reports += 1;
        }
    }
    println!("{}", safe_reports);


}

// Helper function to read content of file
fn read_lines(file_name: &str) -> Vec<String> {
    read_to_string(file_name)
        .unwrap()
        .lines()
        .map(String::from)
        .collect()
}
