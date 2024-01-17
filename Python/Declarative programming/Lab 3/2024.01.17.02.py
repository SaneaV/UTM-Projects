# Determine the min, max, average for the pooled values for each separate day

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

# Define custom lambda functions for mean that avoid empty slices
def custom_mean(x):
    if not x.dropna().empty:
        return round(np.nanmean(x), 3)
    else:
        return np.nan

# Group by Date and Currency, and calculate min, mean, and max for Buy Rate and Sell Rate
result = df.groupby(['Date', 'Currency']).agg({
    'Buy Rate': ['min', custom_mean, 'max'],
    'Sell Rate': ['min', custom_mean, 'max']
}).reset_index()

# Flatten MultiIndex columns
result.columns = [' '.join(col).strip() for col in result.columns.values]

# Rename columns for the final format
result.columns = ['DATE', 'CURRENCY', 'MIN BUY', 'MEDIA BUY', 'MAX BUY', 'MIN SELL', 'MEDIA SELL', 'MAX SELL']

# Remove lines where all the values are NaN
result = result.dropna(how='all', subset=['MIN BUY', 'MEDIA BUY', 'MAX BUY', 'MIN SELL', 'MEDIA SELL', 'MAX SELL'])

# Save the result to a CSV file
output_file = 'final_currency_data.csv'
result.to_csv(output_file, index=False, sep=",")

# Displaying the result
print(f"Final data has been saved to {output_file}.")
