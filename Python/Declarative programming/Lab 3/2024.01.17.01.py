# Write the code sequence that regroups the data from the 3 csv files (obtained in the previous lab) by a basic characteristic 
# (eg: exchange rate from 3 banks for the same day).

import pandas as pd

def read_csv_files(files):
    # List to store DataFrame instances from each CSV file
    dataframes = []

    # Reading data from each CSV file and adding it to the list
    for file in files:
        df = pd.read_csv(file)
        dataframes.append(df)

    return dataframes

# File names of the CSV files for the 3 banks
files = ['../Lab 2/currency_data_maib.csv', '../Lab 2/currency_data_micb.csv', '../Lab 2/currency_data_victoriabank.csv']

# List to store data read from the CSV files
maib, micb, victoriabank = read_csv_files(files)

# Concatenating the data from the 3 files into a single DataFrame based on the date
merged_data = pd.concat([maib, micb, victoriabank], ignore_index=True)

# Sorting the merged data based on date and currency
merged_data = merged_data.sort_values(by=['Date', 'Currency'])

# Saving the data to a new CSV file
merged_data.to_csv('merged_currency_data.csv', index=False)

# Displaying the result
print("Data has been grouped and saved to the merged_currency_data.csv file.")