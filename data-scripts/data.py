from __future__ import annotations

import csv
import random
import uuid
from dataclasses import dataclass
from datetime import date, timedelta
from pathlib import Path
from typing import Dict, List, Tuple


random.seed(42)


@dataclass(frozen=True)
class RoleConfig:
    title: str
    category: str
    us_salary: Dict[str, Tuple[int, int]]
    eu_salary: Dict[str, Tuple[int, int]]
    skills: List[str]


ROLES: List[RoleConfig] = [
    RoleConfig(
        title="Data Analyst",
        category="Augmentation-Prone",
        us_salary={
            "Junior": (70000, 90000),
            "Mid": (90000, 115000),
            "Senior": (115000, 140000),
            "Lead": (135000, 165000),
        },
        eu_salary={
            "Junior": (40000, 55000),
            "Mid": (55000, 75000),
            "Senior": (75000, 100000),
            "Lead": (95000, 125000),
        },
        skills=[
            "SQL",
            "Python",
            "Pandas",
            "Excel",
            "Tableau",
            "Power BI",
            "Statistics",
            "A/B testing",
            "Data visualization",
            "Reporting",
        ],
    ),
    RoleConfig(
        title="Operations and Support",
        category="Automation-Prone",
        us_salary={
            "Junior": (45000, 60000),
            "Mid": (60000, 80000),
            "Senior": (80000, 100000),
            "Lead": (95000, 120000),
        },
        eu_salary={
            "Junior": (28000, 40000),
            "Mid": (40000, 55000),
            "Senior": (55000, 75000),
            "Lead": (70000, 95000),
        },
        skills=[
            "Ticketing systems",
            "Incident management",
            "Customer support",
            "Troubleshooting",
            "ServiceNow",
            "Documentation",
            "SLA management",
            "Monitoring",
            "Email support",
            "CRM",
        ],
    ),
    RoleConfig(
        title="IT Systems & Solutions",
        category="Mixed/Control",
        us_salary={
            "Junior": (70000, 90000),
            "Mid": (90000, 120000),
            "Senior": (120000, 150000),
            "Lead": (145000, 180000),
        },
        eu_salary={
            "Junior": (40000, 55000),
            "Mid": (55000, 80000),
            "Senior": (80000, 110000),
            "Lead": (100000, 135000),
        },
        skills=[
            "Windows Server",
            "Linux",
            "Active Directory",
            "Networking",
            "VMware",
            "Cloud",
            "Azure",
            "AWS",
            "Security",
            "System administration",
        ],
    ),
    RoleConfig(
        title="Software Development",
        category="Mixed/Control",
        us_salary={
            "Junior": (85000, 110000),
            "Mid": (110000, 140000),
            "Senior": (140000, 180000),
            "Lead": (170000, 220000),
        },
        eu_salary={
            "Junior": (45000, 65000),
            "Mid": (65000, 90000),
            "Senior": (90000, 120000),
            "Lead": (115000, 150000),
        },
        skills=[
            "Python",
            "Java",
            "JavaScript",
            "Git",
            "Docker",
            "REST APIs",
            "SQL",
            "CI/CD",
            "Unit testing",
            "Agile",
        ],
    ),
]


SENIORITY_LEVELS = ["Junior", "Mid", "Senior", "Lead"]
COUNTRIES = ["US", "EU"]


def random_date_within_year(year: int) -> str:
    start_date = date(year, 1, 1)
    end_date = date(year, 12, 31)
    delta_days = (end_date - start_date).days
    chosen = start_date + timedelta(days=random.randint(0, delta_days))
    return chosen.isoformat()


def choose_salary(role: RoleConfig, seniority: str, country: str) -> int:
    band = role.us_salary[seniority] if country == "US" else role.eu_salary[seniority]
    return random.randint(band[0], band[1])


def choose_skills(role: RoleConfig) -> str:
    count = random.randint(3, 8)
    count = min(count, len(role.skills))
    return ", ".join(sorted(random.sample(role.skills, count)))


def generate_job_postings(n_rows: int, year: int) -> List[dict]:
    rows: List[dict] = []

    for _ in range(n_rows):
        role = random.choice(ROLES)
        seniority = random.choice(SENIORITY_LEVELS)
        country = random.choice(COUNTRIES)

        row = {
            "job_id": str(uuid.uuid4()),
            "job_title": role.title,
            "job_category": role.category,
            "posting_date": random_date_within_year(year),
            "seniority": seniority,
            "country": country,
            "salary": choose_salary(role, seniority, country),
            "skills_list": choose_skills(role),
        }
        rows.append(row)

    return rows


def save_to_csv(rows: List[dict], output_path: str) -> None:
    if not rows:
        raise ValueError("No rows to save.")

    path = Path(output_path)
    path.parent.mkdir(parents=True, exist_ok=True)

    with path.open("w", newline="", encoding="utf-8") as f:
        writer = csv.DictWriter(f, fieldnames=list(rows[0].keys()))
        writer.writeheader()
        writer.writerows(rows)


if __name__ == "__main__":
    YEAR = 2025
    N_ROWS = 100
    OUTPUT_FILE = f"data_{YEAR}.csv"

    dataset = generate_job_postings(n_rows=N_ROWS, year=YEAR)
    save_to_csv(dataset, OUTPUT_FILE)

    print(f"Generated {len(dataset)} rows in {OUTPUT_FILE}")
