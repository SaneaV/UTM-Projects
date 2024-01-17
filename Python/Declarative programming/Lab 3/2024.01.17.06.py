# Write the code sequence that allows data to be displayed on time sequences. For example, 
# for each week separately (the function has an input date of type datetime) and returns the rows in the 
# DataFrame for this period or None if there is no data for the indicated days.

import pandas as pd

input_file = 'merged_currency_data.csv'
df = pd.read_csv(input_file, header=None, skiprows=1, names=["Date", "Bank", "Currency", "Buy Rate", "Sell Rate"])

def save_data_for_week(input_start_date, dataframe):
    # Ensure that the 'Date' column is of datetime type
    dataframe['Date'] = pd.to_datetime(dataframe['Date'])

    # Define the end date as one week later
    input_end_date = input_start_date + pd.DateOffset(days=6)

    # Filtering based on the specified date range
    filtered_data = dataframe[(dataframe['Date'] >= input_start_date) & (dataframe['Date'] <= input_end_date)]

    # Check if there is data for the specified date range
    if not filtered_data.empty:
        # Save the data to a CSV file
        output_file = f"{"LAB5 -" + input_start_date.strftime('%Y%m%d')}_{input_end_date.strftime('%Y%m%d')}.csv"
        filtered_data.to_csv(output_file, index=False)
        print(f"Data saved to {output_file}")
    else:
        print("No data available for the specified week.")

input_start_date = pd.to_datetime("2023-12-04")
save_data_for_week(input_start_date, df)
