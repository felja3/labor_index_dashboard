import pandas as pd

input_files = {
    "FR": "job_postings_by_sector_FR_filtered.csv",
    "DE": "job_postings_by_sector_DE_filtered.csv",
    "GB": "job_postings_by_sector_GB_filtered.csv",
    "US": "job_postings_by_sector_US_filtered.csv",
}

output_file = "job_postings_by_sector_combined_filtered.csv"

sectors_to_keep = [
    "Data & Analytics",
    "IT Infrastructure, Operations & Support",
    "IT Systems & Solutions",
    "Software Development",
]

all_filtered_data = []

for country, input_file in input_files.items():
    print(f"Reading {input_file}...")

    df = pd.read_csv(input_file)

    filtered_df = df[df["display_name"].isin(sectors_to_keep)]

    print(f"{country}: {len(filtered_df)} rows kept from {len(df)}")

    all_filtered_data.append(filtered_df)

combined_df = pd.concat(all_filtered_data, ignore_index=True)
combined_df.to_csv(output_file, index=False)

print(f"\nSaved combined file: {output_file}")
print(f"Total rows: {len(combined_df)}")