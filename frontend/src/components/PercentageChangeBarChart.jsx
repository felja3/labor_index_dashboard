import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  ResponsiveContainer,
  Legend,
  ReferenceLine,
} from "recharts";

const countryColors = {
  DE: "#dc2626",
  FR: "#f97316",
  GB: "#16a34a",
  US: "#2563eb",
};

function transformData(data) {
  const groupedBySector = {};

  data.forEach((item) => {
    const sector = item.sectorName;

    if (!groupedBySector[sector]) {
      groupedBySector[sector] = {
        sectorName: sector,
      };
    }

    groupedBySector[sector][item.country] = Number(item.percentageChange);
  });

  return Object.values(groupedBySector);
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
          {item.dataKey}: {Number(item.value).toFixed(2)}%
        </p>
      ))}
    </div>
  );
}

function PercentageChangeBarChart({ data }) {
  const chartData = transformData(data);
  const countries = ["GB", "US", "DE", "FR"];

  return (
    <div style={{ width: "100%", height: 420 }}>
      <ResponsiveContainer>
        <BarChart data={chartData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis
            dataKey="sectorName"
            interval={0}
            angle={-15}
            textAnchor="end"
            height={90}
          />
          <YAxis tickFormatter={(value) => `${value}%`} />
          <Tooltip content={<CustomTooltip />} />
          <Legend />
          <ReferenceLine y={0} stroke="#000" />

          {countries.map((country) => (
            <Bar
              key={country}
              dataKey={country}
              fill={countryColors[country]}
            />
          ))}
        </BarChart>
      </ResponsiveContainer>
    </div>
  );
}

export default PercentageChangeBarChart;