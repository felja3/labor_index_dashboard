import pandas as pd

df = pd.read_csv("job_postings_by_sector_combined_filtered.csv")
df["date"] = pd.to_datetime(df["date"])

# Use total postings only
df_total = df[df["variable"] == "total postings"].copy()

rows = []

for (country, sector), g in df_total.groupby(["jobcountry", "display_name"]):
    g = g.sort_values("date")

    baseline = g.loc[g["date"].sub(pd.Timestamp("2020-02-01")).abs().idxmin()]
    
    peak_period = g[(g["date"] >= "2021-01-01") & (g["date"] <= "2022-12-31")]
    peak = peak_period.loc[peak_period["indeed_job_postings_index"].idxmax()]
    
    gen_ai_start = g.loc[g["date"].sub(pd.Timestamp("2022-12-01")).abs().idxmin()]
    end_2023 = g.loc[g["date"].sub(pd.Timestamp("2023-12-31")).abs().idxmin()]
    latest = g.loc[g["date"].idxmax()]

    rows.append({
        "country": country,
        "sector": sector,
        "baseline_date": baseline["date"].date(),
        "baseline_value": round(baseline["indeed_job_postings_index"], 2),
        "peak_date": peak["date"].date(),
        "peak_value": round(peak["indeed_job_postings_index"], 2),
        "gen_ai_start_date": gen_ai_start["date"].date(),
        "gen_ai_start_value": round(gen_ai_start["indeed_job_postings_index"], 2),
        "end_2023_date": end_2023["date"].date(),
        "end_2023_value": round(end_2023["indeed_job_postings_index"], 2),
        "latest_date": latest["date"].date(),
        "latest_value": round(latest["indeed_job_postings_index"], 2),
        "decline_from_peak_to_latest_pct": round(
            ((latest["indeed_job_postings_index"] - peak["indeed_job_postings_index"]) / peak["indeed_job_postings_index"]) * 100, 2
        )
    })

summary = pd.DataFrame(rows)
summary.to_csv("chapter4_total_postings_summary.csv", index=False)

print(summary)