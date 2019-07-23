insert into offer (id, jobtitle, createdtime) values(1, 'SeniorJavaEngineer', now());
insert into offer (id, jobtitle, createdtime) values(2, 'SeniorScalaEngineer', now());
insert into offer (id, jobtitle, createdtime) values(3, 'SeniorPythonEngineer', now());

insert into application (id, offer_id, applicationstatus, candidateemail, resumetext, createdtime, updatedtime) values(1, 2, 'APPLIED', 'testuser1@testmail.com', '5+ Years of experience with Spring', now(), now());
insert into application (id, offer_id, applicationstatus, candidateemail, resumetext, createdtime, updatedtime) values(2, 2, 'INVITED', 'testuser2@testmail.com', '6+ Years of experience with Spring', now(), now());