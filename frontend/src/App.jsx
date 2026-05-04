import { useEffect, useState } from "react";
import {
  getCountries,
  getSectors,
  getSectorIndexTrends,
  getPercentageChange,
} from "./api/dashboardApi";

import SectorIndexLineChart from "./components/SectorIndexLineChart";
import PercentageChangeBarChart from "./components/PercentageChangeBarChart";
import CountryDropdown from "./components/filters/CountryDropdown";
import SectorDropdown from "./components/filters/SectorDropdown";
import ComparisonDropdown from "./components/filters/ComparisonDropdown";

function App() {
  const [countries, setCountries] = useState([]);
  const [sectors, setSectors] = useState([]);

  const [selectedCountry, setSelectedCountry] = useState("US");
  const [selectedSectors, setSelectedSectors] = useState([
    "Software Development",
  ]);
  const [selectedComparison, setSelectedComparison] = useState("month");

  const [trendData, setTrendData] = useState([]);
  const [percentageChangeData, setPercentageChangeData] = useState([]);

  const [loading, setLoading] = useState(false);
  const [percentageChangeLoading, setPercentageChangeLoading] = useState(false);

  const [error, setError] = useState("");
  const [percentageChangeError, setPercentageChangeError] = useState("");

  useEffect(() => {
    async function loadInitialData() {
      try {
        const [countriesData, sectorsData] = await Promise.all([
          getCountries(),
          getSectors(),
        ]);

        setCountries(countriesData);
        setSectors(sectorsData);
      } catch (err) {
        setError(err.message);
      }
    }

    loadInitialData();
  }, []);

  useEffect(() => {
    async function loadTrendData() {
      try {
        setLoading(true);
        setError("");

        const results = await Promise.all(
          selectedSectors.map((sector) =>
            getSectorIndexTrends({
              country: selectedCountry,
              sector,
              postingType: "total postings",
            })
          )
        );

        setTrendData(results.flat());
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }

    if (selectedSectors.length > 0) {
      loadTrendData();
    } else {
      setTrendData([]);
    }
  }, [selectedCountry, selectedSectors]);

  useEffect(() => {
    async function loadPercentageChangeData() {
      try {
        setPercentageChangeLoading(true);
        setPercentageChangeError("");

        const data = await getPercentageChange({
          comparison: selectedComparison,
        });

        setPercentageChangeData(data);
      } catch (err) {
        setPercentageChangeError(err.message);
      } finally {
        setPercentageChangeLoading(false);
      }
    }

    loadPercentageChangeData();
  }, [selectedComparison]);

  return (
    <main style={{ padding: "24px", fontFamily: "Arial, sans-serif" }}>
      <h1>AI IT Labor Market Dashboard</h1>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <section
        style={{
          display: "flex",
          gap: "16px",
          marginBottom: "24px",
          alignItems: "flex-start",
        }}
      >
        <CountryDropdown
          countries={countries}
          selectedCountry={selectedCountry}
          onChange={setSelectedCountry}
        />

        <SectorDropdown
          sectors={sectors}
          selectedSectors={selectedSectors}
          onChange={setSelectedSectors}
        />
      </section>

      <section>
        <h2>
          {selectedSectors.join(", ")} — Total Postings Index — {selectedCountry}
        </h2>

        {loading ? (
          <p>Loading chart data...</p>
        ) : trendData.length > 0 ? (
          <SectorIndexLineChart
            data={trendData}
            selectedSectors={selectedSectors}
          />
        ) : (
          <p>No data available for selected filters.</p>
        )}
      </section>

      <section style={{ marginTop: "40px" }}>
        <h2>Percentage Change Analysis</h2>

        <p>
          Values show percentage change in the Indeed Job Postings Index
          compared to the selected reference period. The analysis uses total
          postings only.
        </p>

        <div style={{ marginBottom: "16px" }}>
          <ComparisonDropdown
            selectedComparison={selectedComparison}
            onChange={setSelectedComparison}
          />
        </div>

        {percentageChangeError && (
          <p style={{ color: "red" }}>{percentageChangeError}</p>
        )}

        {percentageChangeLoading ? (
          <p>Loading percentage change data...</p>
        ) : percentageChangeData.length > 0 ? (
          <PercentageChangeBarChart data={percentageChangeData} />
        ) : (
          <p>No percentage change data available.</p>
        )}
      </section>
    </main>
  );
}

export default App;