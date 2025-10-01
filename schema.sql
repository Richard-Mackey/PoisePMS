-- People table
CREATE TABLE IF NOT EXISTS people (
    person_id SERIAL PRIMARY KEY,
    person_name VARCHAR(255) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    email VARCHAR(255),
    address VARCHAR(500),
    role VARCHAR(100) NOT NULL
);

-- Projects table
CREATE TABLE IF NOT EXISTS projects (
    project_id SERIAL PRIMARY KEY,
    project_name VARCHAR(255) NOT NULL,
    building_type VARCHAR(100) NOT NULL,
    project_address VARCHAR(500),
    ERF_number INTEGER,
    total_fee DECIMAL(15, 2),
    amount_paid_to_date DECIMAL(15, 2),
    project_deadline DATE,
    architect_id INTEGER,
    contractor_id INTEGER,
    customer_id INTEGER NOT NULL,
    engineer_id INTEGER,
    manager_id INTEGER,
    project_finalised BOOLEAN DEFAULT FALSE,
    completion_date DATE,
    FOREIGN KEY (architect_id) REFERENCES people(person_id) ON DELETE SET NULL,
    FOREIGN KEY (contractor_id) REFERENCES people(person_id) ON DELETE SET NULL,
    FOREIGN KEY (customer_id) REFERENCES people(person_id) ON DELETE RESTRICT,
    FOREIGN KEY (engineer_id) REFERENCES people(person_id) ON DELETE SET NULL,
    FOREIGN KEY (manager_id) REFERENCES people(person_id) ON DELETE SET NULL
);