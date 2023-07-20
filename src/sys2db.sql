CREATE DATABASE sys2db.sql;
-- connection to the DATABASE
\c sys2db.sql;
-- creation of the TABLE
CREATE TABLE connection (
 id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
 firstname VARCHAR(200) NOT NULL,
connection_datetime TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_DATE
);