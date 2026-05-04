function ComparisonDropdown({ selectedComparison, onChange }) {
  return (
    <label>
      Comparison:{" "}
      <select
        value={selectedComparison}
        onChange={(event) => onChange(event.target.value)}
      >
        <option value="month">Month</option>
        <option value="sixMonths">6 months</option>
        <option value="year">Year</option>
        <option value="baseline">Baseline</option>
        <option value="peak">From peak</option>
      </select>
    </label>
  );
}

export default ComparisonDropdown;