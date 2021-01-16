CREATE TABLE  student(
   sid int, 
   sname char(100), 
   sex char, 
   age int, 
   year int, 
   gpa float
);


CREATE TABLE  dept(
   did int, 
   dname char(100), 
   numphds int
);


CREATE TABLE  prof(
   pid int, 
   pname char(100),
   did int
);


CREATE TABLE  course(
   cid int, 
   cname char(100), 
   did int
);


CREATE TABLE  major(
   did int,  
   sid int
);


CREATE TABLE  enroll(
   sid int, 
   cid int,
   grade float
);


create or replace function random_string(length integer) returns text as 
$$
declare
  chars text[] := '{0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z}';
  result text := '';
  i integer := 0;
begin
  if length < 0 then
    raise exception 'Given length cannot be less than 0';
  end if;
  for i in 1..length loop
    result := result || chars[ceil(61 * random())];
  end loop;
  return result;
end;
$$ language plpgsql;

CREATE OR REPLACE FUNCTION genWorkload(num int,query int) RETURNS int AS $$
DECLARE
  queries text[] := '{"select max(numphds) from dept",
"select pname from prof p, dept d where p.did=d.did and numphds>17",
"select sname from student where gpa = (select min(gpa) from student)",
"select DISTINCT d.dname from dept d, major m, student s where s.age<21 and s.sid=m.sid AND m.did=d.did",
"select DISTINCT s.sname from student s, enroll e, course c where s.sid=e.sid AND c.cid=e.cid AND c.cname LIKE ''%abc%''",
"select DISTINCT d.dname from dept d, major m where d.did=m.did AND m.sid IN (SELECT e.sid FROM enroll e, course c, student s where s.sid=e.sid AND c.cid=e.cid AND c.cname LIKE ''%abc%'' and s.sname LIKE ''%ab%'')",
"select dname from dept where did not in (select DISTINCT m.did from major m where m.sid IN (SELECT e.sid FROM enroll e, course c, student s where s.sid=e.sid AND c.cid=e.cid AND c.cname LIKE ''%abc%'' and s.sname LIKE ''%ab%''))",
"select DISTINCT s.sname from student s, enroll e1, enroll e2, course c1, course c2 where e1.cid=c1.cid AND e2.cid=c2.cid AND s.sid=e1.sid AND e1.sid=e2.sid and c1.cname LIKE ''a%'' and c2.cname LIKE ''b%''",
"SELECT c.cname, AVG(s.gpa)
FROM course c, enroll e, student s, dept d
WHERE e.sid = s.sid AND c.did=d.did AND
     d.dname LIKE ''c%'' AND 
	 e.cid=c.cid
GROUP BY c.cid, c.cname;",
"SELECT c2.cname, AVG(s.gpa)
FROM enroll e, student s, course c2
WHERE e.sid = s.sid AND  
  e.cid IN (SELECT cid FROM course c, dept d
              WHERE c.did=d.did AND d.dname LIKE ''c%'')
GROUP BY c2.cid, c2.cname;",
"SELECT t.cname, t.dname
FROM (SELECT c.cname, d.dname
    FROM enroll e, course c, dept d
    WHERE e.cid = c.cid AND d.did=c.did) AS t
GROUP BY t.cname, t.dname
HAVING COUNT(*) > 40;",
"SELECT c.cname, d.dname
FROM course c, dept d, (SELECT e.cid, COUNT(*) AS count
              FROM enroll e
              GROUP BY e.cid) AS t
WHERE c.cid=t.cid AND c.did=d.did AND t.count > 40;",
"SELECT  s.sname
FROM    student s
WHERE s.sid IN (SELECT e.sid
  FROM enroll e GROUP BY e.sid
  HAVING COUNT(*)=(SELECT MAX(count)
    FROM(SELECT COUNT(*) as count
      FROM enroll e2
      GROUP BY e2.sid) AS foo));",
"SELECT s.sname FROM student s, enroll e
WHERE  s.sid=e.sid
GROUP BY s.sid, s.sname
HAVING COUNT(*) >= ALL 
    (SELECT COUNT(*)
     FROM student s2, enroll e2
     WHERE s2.sid=e2.sid
     GROUP BY s2.sid);"}';
  t bigint;
  t1 bigint;
  i int;
  k text;
  r int;
  s char;
  n text;
  m int;
  nextid int;
BEGIN
    PERFORM setseed(0.7);
    SELECT extract(epoch from clock_timestamp())*1000 INTO t;
    nextid:=10000000;
    FOR i IN 1..num LOOP
      nextid:=nextid+1;
      r := round(random()*100);
      IF r<query THEN
        r:=ceil(random()*array_length(queries,1));
		RAISE NOTICE '%: Executing query %', i, r;
		SELECT extract(epoch from clock_timestamp())*1000 INTO t1;
        EXECUTE queries[r];
   	    t1:=extract(epoch from clock_timestamp())*1000-t1;
		RAISE NOTICE 'query %: %', r, t1;
      ELSE
	    r:=ceil(random()*40);
		RAISE NOTICE '%: Doing update %', i, r;
		SELECT extract(epoch from clock_timestamp())*1000 INTO t1;
		IF r<5 THEN
		  IF random()>0.5 THEN s := 'f'; ELSE s:= 'm'; END IF;
		  INSERT INTO student(sid,sname,sex,age,year,gpa) VALUES (nextid,random_string(55),s,round(random()*20)+20,round(random()*10),random()*4);
		ELSIF r=5 THEN
		  INSERT INTO dept(did,dname,numphds) VALUES (nextid,random_string(55),round(random()*30));
		ELSIF r=6 THEN
		  INSERT INTO prof(pid,pname,did) VALUES (nextid,random_string(55),(select max(did) from dept));
		ELSIF r=7 THEN
		  INSERT INTO prof(pid,pname,did) VALUES (nextid,random_string(55),(select min(did) from dept));
		ELSIF r=8 THEN
		  INSERT INTO course(cid,cname,did) VALUES (nextid,random_string(55),(select min(did) from dept));
		ELSIF r=9 THEN
		  INSERT INTO course(cid,cname,did) VALUES (nextid,random_string(55),(select max(did) from dept));
		ELSIF r=10 THEN
		  n:=random_string(55);
		  Select into k  min(cname) from course;
		  UPDATE course SET cname=n WHERE cname=k;
		ELSIF r=11 THEN
		  n:=random_string(55);
		  Select into k  max(cname) from course;
		  UPDATE course SET cname=n WHERE cname=k;
		ELSIF r=12 THEN
		  Select into m  max(cid) from course;
		  DELETE FROM enroll WHERE cid=m;
		ELSIF r=13 THEN
		  Select into m  min(cid) from course;
		  DELETE FROM enroll WHERE cid=m;
		ELSIF r=14 THEN
		  Select into m  max(sid) from student;
		  DELETE FROM enroll WHERE sid=m;
		ELSIF r=15 THEN
		  Select into m  min(sid) from student;
		  DELETE FROM enroll WHERE sid=m;
		ELSIF r=16 THEN
		  Select into m  min(sid) from student;
          DELETE FROM student WHERE sid=m;
		  DELETE FROM enroll WHERE sid=m;
		  DELETE FROM major WHERE sid=m;
		ELSIF r=17 THEN
		  Select into m max(sid) from student;
          DELETE FROM student WHERE sid=m;
		  DELETE FROM enroll WHERE sid=m;
		  DELETE FROM major WHERE sid=m;
		ELSIF r=18 THEN
		  UPDATE Student SET age=age+1;
		ELSIF r=19 THEN
		  UPDATE Student SET year=year+1;
		ELSIF r=20 THEN
		  k:=random_string(2) || '%';
		  UPDATE student SET gpa=gpa*1.1 WHERE sname LIKE k;
		ELSIF r=21 THEN
		  m:=round(random()*(select max(did) from dept));
		  UPDATE dept SET numphds=numphds+1 WHERE did=m;
		ELSIF r=22 THEN
		  UPDATE dept SET numphds=numphds+1 WHERE did=round(random()*10);
		ELSIF r=23 THEN
		  UPDATE enroll SET grade=grade*0.9 WHERE cid=round(random()*3000);
		ELSIF r=24 THEN
		  UPDATE enroll SET grade=grade*1.1 WHERE cid=round(random()*3000+3000);
		ELSIF (r=25 OR r=26) THEN
		  UPDATE enroll SET grade=grade*0.9 WHERE sid=round(random()*10000);
		ELSIF (r=27 OR r=28) THEN
		  UPDATE enroll SET grade=grade*1.1 WHERE sid=round(random()*10000+10000);
		ELSIF (r>=29 AND r<33) THEN
		  UPDATE student SET gpa=(SELECT AVG(grade) FROM enroll e WHERE e.sid=student.sid);
		ELSIF r>=33 THEN
		  UPDATE student SET gpa=(SELECT AVG(grade) FROM enroll e WHERE e.sid=student.sid) WHERE sid=round(random()*(SELECT max(sid) FROM student));
		END IF;
   	    t1:=extract(epoch from clock_timestamp())*1000-t1;
		RAISE NOTICE 'update %: %', r, t1;
      END IF;
    END LOOP;
	t:=extract(epoch from clock_timestamp())*1000-t;
	RAISE NOTICE 'Time used: %', t;
	RAISE EXCEPTION 'Workload completed after % ms, transaction aborted to avoid changes in DB', t;  /* Remove only if you really want to change the DB */
    RETURN t;
END;
$$ LANGUAGE plpgsql;


	
