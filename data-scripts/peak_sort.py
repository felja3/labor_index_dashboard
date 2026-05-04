from pathlib import Path
import pandas as pd


# ====== CONFIG ======
INPUT_FILE = Path("job_postings_by_sector_combined_filtered.csv")
OUTPUT_FILE = Path("chapter4_total_postings_summary.csv")

POSTING_TYPE = "total postings"

PEAK_START = "2021-01-01"
PEAK_END = "2022-12-31"


def main():
    if not INPUT_FILE.exists():
        raise FileNotFoundError(f"Input file not found: {INPUT_FILE}")

    df = pd.read_csv(INPUT_FILE)

    required_columns = {
        "date",
        "jobcountry",
        "indeed_job_postings_index",
        "variable",
        "display_name",
    }

    missing_columns = required_columns - set(df.columns)
    if missing_columns:
        raise ValueError(f"Missing required columns: {missing_columns}")

    df["date"] = pd.to_datetime(df["date"], errors="coerce")
    df["indeed_job_postings_index"] = pd.to_numeric(
        df["indeed_job_postings_index"],
        errors="coerce"
    )

    df = df.dropna(subset=["date", "indeed_job_postings_index"])

    # Use only total postings for this Chapter 4 table
    df_total = df[df["variable"] == POSTING_TYPE].copy()

    if df_total.empty:
        raise ValueError(f"No rows found for posting type: {POSTING_TYPE}")

    results = []

    for (country, sector), group in df_total.groupby(["jobcountry", "display_name"]):
        group = group.sort_values("date")

        peak_period = group[
            (group["date"] >= PEAK_START) &
            (group["date"] <= PEAK_END)
        ]

        if peak_period.empty:
            print(f"Skipping {country} - {sector}: no data in peak period")
            continue

        peak_row = peak_period.loc[
            peak_period["indeed_job_postings_index"].idxmax()
        ]

        latest_row = group.loc[group["date"].idxmax()]

        peak_value = peak_row["indeed_job_postings_index"]
        latest_value = latest_row["indeed_job_postings_index"]

        decline_pct = ((latest_value - peak_value) / peak_value) * 100

        results.append({
            "Country": country,
            "Sector": sector,
            "Peak date": peak_row["date"].date(),
            "Peak index value": round(peak_value, 2),
            "Latest date": latest_row["date"].date(),
            "Latest index value": round(latest_value, 2),
            "Decline from peak to latest (%)": round(decline_pct, 2),
        })

    summary = pd.DataFrame(results)

    # Sort from strongest decline to weakest decline
    summary = summary.sort_values(
        by="Decline from peak to latest (%)",
        ascending=True
    )

    summary.to_csv(OUTPUT_FILE, index=False)

    print("Chapter 4 summary table created successfully.")
    print(f"Output file: {OUTPUT_FILE}")
    print()
    print(summary.to_string(index=False))


if __name__ == "__main__":
    main()