CREATE TABLE IF NOT EXISTS people (
    person_id SERIAL PRIMARY KEY,
    person_name VARCHAR(255) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    email VARCHAR(255),
    address TEXT,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS projects (
    project_id SERIAL PRIMARY KEY,
    project_name VARCHAR(255) NOT NULL,
    building_type VARCHAR(100) NOT NULL,
    project_address TEXT,
    erf_number INTEGER,
    total_fee DECIMAL(10, 2) DEFAULT 0,
    amount_paid_to_date DECIMAL(10, 2) DEFAULT 0,
    project_deadline DATE,
    architect_id INTEGER REFERENCES people(person_id),
    contractor_id INTEGER REFERENCES people(person_id),
    customer_id INTEGER NOT NULL REFERENCES people(person_id),
    engineer_id INTEGER REFERENCES people(person_id),
    manager_id INTEGER REFERENCES people(person_id),
    project_finalised BOOLEAN DEFAULT FALSE,
    completion_date DATE
);