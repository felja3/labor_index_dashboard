import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  ResponsiveContainer,
  Legend,
} from "recharts";

const sectorColors = {
  "Data & Analytics": "#2563eb",
  "IT Infrastructure, Operations & Support": "#16a34a",
  "IT Systems & Solutions": "#f97316",
  "Software Development": "#dc2626",
};

function transformData(data) {
  const groupedByDate = {};

  data.forEach((item) => {
    const date = item.date;

    if (!groupedByDate[date]) {
      groupedByDate[date] = { date };
    }

    groupedByDate[date][item.sectorName] = Number(item.indexValue);
  });

  return Object.values(groupedByDate).sort((a, b) =>
    a.date.localeCompare(b.date)
  );
}

function getYearTicks(chartData) {
  const seenYears = new Set();

  return chartData
    .filter((item) => {
      const year = item.date.slice(0, 4);

      if (seenYears.has(year)) {
        return false;
      }

      seenYears.add(year);
      return true;
    })
    .map((item) => item.date);
}

function CustomTooltip({ active, payload, label }) {
  if (!active || !payload || payload.length === 0) {
    return null;
  }

  const sortedPayload = [...payload]
    .filter((item) => item.value !== undefined && item.value !== null)
    .sort((a, b) => b.value - a.value);

  return (
    <div
      style={{
        backgroundColor: "white",
        border: "1px solid #ccc",
        padding: "12px 16px",
        fontSize: "14px",
      }}
    >
      <p style={{ margin: "0 0 8px 0", fontWeight: "bold" }}>{label}</p>

      {sortedPayload.map((item) => (
        <p
          key={item.dataKey}
          style={{
            margin: "4px 0",
            color: item.color,
          }}
        >
          {item.dataKey}: {Number(item.value).toFixed(2)}
        </p>
      ))}
    </div>
  );
}

function SectorIndexLineChart({ data, selectedSectors }) {
  const chartData = transformData(data);
  const yearTicks = getYearTicks(chartData);

  return (
    <div style={{ width: "100%", height: 400 }}>
      <ResponsiveContainer>
        <LineChart data={chartData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis
            dataKey="date"
            ticks={yearTicks}
            tickFormatter={(date) => date.slice(0, 4)}
          />
          <YAxis />
          <Tooltip content={<CustomTooltip />} />
          <Legend />

          {selectedSectors.map((sector) => (
            <Line
              key={sector}
              type="monotone"
              dataKey={sector}
              stroke={sectorColors[sector]}
              strokeWidth={2}
              dot={false}
            />
          ))}
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
}

export default SectorIndexLineChart;