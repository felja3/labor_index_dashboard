const API_BASE_URL = "http://localhost:8080/api";

export async function getDashboardSummary() {
  const response = await fetch(`${API_BASE_URL}/dashboard/summary`);

  if (!response.ok) {
    throw new Error("Failed to fetch dashboard summary");
  }

  return response.json();
}

export async function getCountries() {
  const response = await fetch(`${API_BASE_URL}/dashboard/countries`);

  if (!response.ok) {
    throw new Error("Failed to fetch countries");
  }

  return response.json();
}

export async function getSectors() {
  const response = await fetch(`${API_BASE_URL}/dashboard/sectors`);

  if (!response.ok) {
    throw new Error("Failed to fetch sectors");
  }

  return response.json();
}

export async function getSectorIndexTrends({
  country = "US",
  sector = "Software Development",
  postingType = "total postings",
} = {}) {
  const params = new URLSearchParams({
    country,
    sector,
    postingType,
  });

  const response = await fetch(
    `${API_BASE_URL}/dashboard/sector-index-trends?${params.toString()}`
  );

  if (!response.ok) {
    throw new Error("Failed to fetch sector index trends");
  }

  return response.json();
}
export async function getPercentageChange({ comparison = "month" } = {}) {
  const params = new URLSearchParams({
    comparison,
  });

  const response = await fetch(
    `${API_BASE_URL}/dashboard/percentage-change?${params.toString()}`
  );

  if (!response.ok) {
    throw new Error("Failed to fetch percentage change data");
  }

  return response.json();
}