function CountryDropdown({ countries, selectedCountry, onChange }) {
  return (
    <label>
      Country:{" "}
      <select
        value={selectedCountry}
        onChange={(event) => onChange(event.target.value)}
      >
        {countries.map((country) => (
          <option key={country} value={country}>
            {country}
          </option>
        ))}
      </select>
    </label>
  );
}

export default CountryDropdown;