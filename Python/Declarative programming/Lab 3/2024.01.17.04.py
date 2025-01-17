# Write the code sequence that will split the csv file obtained after concatenation into N files, 
# where each individual file will correspond to a week (7 rows). The files are named after the 
# first and last date they contain (eg 20231103_20231109.csv).

import pandas as pd

# Read the CSV file
df = pd.read_csv('merged_currency_data.csv')

# Convert the 'Date' column to datetime data type
df['Date'] = pd.to_datetime(df['Date'])

# Sort the dataframe by the 'Date' column
df = df.sort_values(by='Date')

# Add a column with the week number
df['Week'] = df['Date'].dt.strftime('%Y%m%d').astype(int) // 7

# Iterate through each week and save the data into separate files
for week, data in df.groupby('Week'):
    start_date = data['Date'].min().strftime('%Y%m%d')
    end_date = data['Date'].max().strftime('%Y%m%d')
    filename = f'{start_date}_{end_date}.csv'
    data.to_csv(filename, index=False)
    print(f'Created file {filename}')
