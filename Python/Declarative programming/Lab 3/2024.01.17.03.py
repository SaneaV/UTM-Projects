# Determine the min, max, average for the pooled values for all stored data

import pandas as pd
import numpy as np

# Read data from the input CSV file
input_file = 'merged_currency_data.csv'
df = pd.read_csv(input_file, header=None, names=["Date", "Bank", "Currency", "Buy Rate", "Sell Rate"])

# Replace "-" with NaN
df.replace('-', np.nan, inplace=True)

# Convert Buy Rate and Sell Rate columns to numeric
df['Buy Rate'] = pd.to_numeric(df['Buy Rate'].str.replace(',', '.'), errors='coerce')
df['Sell Rate'] = pd.to_numeric(df['Sell Rate'].str.replace(',', '.'), errors='coerce')

# Group by Currency and calculate max, min, mean for Buy Rate and Sell Rate
result = df.groupby(['Currency']).agg({
    'Buy Rate': ['max', 'min', 'mean'],
    'Sell Rate': ['max', 'min', 'mean']
}).reset_index()

# Flatten MultiIndex columns and round the numeric columns to 2 decimal places
result.columns = [col[0] if col[1] == '' else f"{col[0]} {col[1]}" for col in result.columns]
result[['Buy Rate max', 'Buy Rate min', 'Buy Rate mean', 'Sell Rate max', 'Sell Rate min', 'Sell Rate mean']] = \
    result[['Buy Rate max', 'Buy Rate min', 'Buy Rate mean', 'Sell Rate max', 'Sell Rate min', 'Sell Rate mean']].round(2)

# Rename columns for the final format and remove the unnecessary line
result.columns = ['CURRENCY', 'MAX BUY', 'MIN BUY', 'MEDIA BUY', 'MAX SELL', 'MIN SELL', 'MEDIA SELL']
result = result[~result['CURRENCY'].str.startswith('Currency')]

# Save the result to a CSV file
output_file = 'all_currency_data_monthly.csv'
result.to_csv(output_file, index=False, sep=",")

# Displaying the result
print(f"Currency data for all months (rounded to 2 decimal places) has been saved to {output_file}.")