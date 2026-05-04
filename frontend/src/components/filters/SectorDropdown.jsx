function SectorDropdown({ sectors, selectedSectors, onChange }) {
  function handleCheckboxChange(sector) {
    if (selectedSectors.includes(sector)) {
      onChange(selectedSectors.filter((selectedSector) => selectedSector !== sector));
    } else {
      onChange([...selectedSectors, sector]);
    }
  }

  return (
    <fieldset style={{ border: "1px solid #ccc", padding: "8px 12px" }}>
      <legend>Sectors</legend>

      {sectors.map((sector) => (
        <label
          key={sector}
          style={{ display: "block", marginBottom: "4px", cursor: "pointer" }}
        >
          <input
            type="checkbox"
            checked={selectedSectors.includes(sector)}
            onChange={() => handleCheckboxChange(sector)}
            style={{ marginRight: "6px" }}
          />
          {sector}
        </label>
      ))}
    </fieldset>
  );
}

export default SectorDropdown;