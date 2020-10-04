CREATE TABLE person (
	id SERIAL PRIMARY KEY,
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
	email VARCHAR(50) NOT NULL,
	password VARCHAR(50) NOT NULL
)

CREATE TABLE post (
	id SERIAL PRIMARY KEY,
	text VARCHAR(1000) NOT NULL,
	person_id BIGINT NOT NULL REFERENCES person(id)
)

CREATE TABLE friendship (
	first_person_id BIGINT REFERENCES person(id),
	second_person_id BIGINT REFERENCES person(id),
	PRIMARY KEY (first_person_id, second_person_id)
)

ALTER TABLE person ADD CONSTRAINT email_unique UNIQUE(email)

CREATE TABLE friendship_request (
	sender_id BIGINT REFERENCES person(id),
	receiver_id BIGINT REFERENCES person(id),
	PRIMARY KEY (sender_id, receiver_id)
)