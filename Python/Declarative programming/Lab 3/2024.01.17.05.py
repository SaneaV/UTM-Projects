# Write the function that takes an input date of type datetime and returns the rows in the DataFrame 
# (from the concatenated data file) for this date or None if there is no data for this date.

import pandas as pd

input_file = 'merged_currency_data.csv'
df = pd.read_csv(input_file, skiprows=1, names=["Date", "Bank", "Currency", "Buy Rate", "Sell Rate"])

def get_data_for_date(input_date, dataframe):
    # Ensure that the 'Date' column is of datetime type
    dataframe['Date'] = pd.to_datetime(dataframe['Date'])

    # Filtering based on the specified date
    filtered_data = dataframe[dataframe['Date'] == input_date]

    # Check if there is data for the specified date
    if filtered_data.empty:
        return None
    else:
        return filtered_data

# Convert the input date to a datetime object
input_date = pd.to_datetime("2023-12-21")

result = get_data_for_date(input_date, df)
print(result)
