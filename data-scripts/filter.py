import pandas as pd

input_file = "job_postings_by_sector_GB.csv"
output_file = "job_postings_by_sector_GB_filtered.csv"

sectors_to_keep = [
    "Data & Analytics",
    "IT Infrastructure, Operations & Support",
    "IT Systems & Solutions",
    "Software Development"
]

# Load CSV
df = pd.read_csv('job_postings_by_sector_GB.csv')

# Optional: check available columns and first rows
print("Columns:")
print(df.columns)

print("\nFirst rows:")
print(df.head())

#Filter only selected sectors
filtered_df = df[df["display_name"].isin(sectors_to_keep)]

# Save filtered CSV
filtered_df.to_csv(output_file, index=False)

print(f"\nOriginal rows: {len(df)}")
print(f"Filtered rows: {len(filtered_df)}")
print(f"Saved to: {output_file}")