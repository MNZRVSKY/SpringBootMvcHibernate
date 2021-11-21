/* ========= users ========= */
CREATE TABLE IF NOT EXISTS users(
    id serial PRIMARY KEY,
	username VARCHAR ( 50 ) NOT NULL,
    email VARCHAR ( 50 ) NOT NULL
);

INSERT INTO users (username, email) 
	VALUES  ('Vasia', 'vasia@gmail.com'),
			('Vania', 'vania@gmail.com'),
			('Marusia', 'marusia@gmail.com'),
			('Tania', 'tania@gmail.com');
			
SELECT * FROM users;


/* ========= Events ========= */
CREATE TABLE IF NOT EXISTS events(
	id serial PRIMARY KEY,
	title VARCHAR ( 50 ) NOT NULL,
	date TIMESTAMP NOT NULL	
);

INSERT INTO events (title, date)
	VALUES ('Independence Day', '2021-09-24 10:00:00'),
			('Concert', '2021-10-07 19:00:00'),
			('Theatre', '2021-11-12 20:00:00'),
			('New Year Party', '2021-12-30 18:00:00');

SELECT * FROM events;


/* ========= Tickets category ========= */
CREATE TABLE IF NOT EXISTS ticket_category (
	id serial PRIMARY KEY,
	title VARCHAR ( 50 ) NOT NULL
);

INSERT INTO ticket_category (title)
	VALUES ('STANDARD'), 
			('PREMIUM'), 
			('BAR');
	
SELECT * FROM ticket_category;


/* ========= Tickets ========= */
CREATE TABLE IF NOT EXISTS tickets (
	id serial PRIMARY KEY,
	event_id INT NOT NULL,
	user_id INT NOT NULL,
	ticket_category INT NOT NULL,
	place INT NOT NULL,
	FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
	FOREIGN KEY (ticket_category) REFERENCES ticket_category (id) ON DELETE CASCADE
);

INSERT INTO tickets (event_id, user_id, ticket_category, place)
	VALUES  (1, 1, 1, 1),
			(2, 1, 1, 1),
			(3, 1, 2, 1),
			(4, 1, 3, 1),
			
			(1, 2, 1, 2),
			(2, 2, 1, 2),
			(3, 2, 2, 2),
			(4, 2, 3, 2),
			
			(1, 3, 1, 3),
			(2, 3, 1, 3),
			(3, 3, 2, 3),
			(4, 3, 3, 3),
			
			(1, 4, 1, 4),
			(2, 4, 1, 4),
			(3, 4, 2, 4),
			(4, 4, 3, 4);

SELECT * FROM tickets;

SELECT tickets.id, events.title, events.date, users.username, ticket_category.title, tickets.place 
	FROM tickets, users, events, ticket_category
	WHERE tickets.user_id = users.id 
		AND tickets.event_id = events.id
		AND ticket_category.id = tickets.ticket_category;
		

/* ========= User account ========= */
CREATE TABLE IF NOT EXISTS user_account(
    user_id INT NOT NULL,
    user_money FLOAT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

INSERT INTO user_account (user_id, user_money)
	VALUES (1, 7),
			(2, 12),
			(3, 28),
			(4, 15);


/* ========= Update table event ========= */
ALTER TABLE events 
ADD COLUMN ticket_price FLOAT;

UPDATE events SET ticket_price=10.0 WHERE ticket_price IS NULL;

ALTER TABLE events
ALTER COLUMN ticket_price SET NOT NULL;


			
			
