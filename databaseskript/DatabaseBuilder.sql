drop schema AMVDatabase;
create database if not exists AMVDatabase;
use AMVDatabase;

CREATE OR REPLACE TABLE ToolCertificate(
    certificateID int NOT NULL auto_increment unique,
    certificateName VARCHAR(50) NOT NULL,
    PRIMARY KEY (certificateID)
);

CREATE OR REPLACE TABLE Tool (
    toolID int NOT NULL auto_increment unique,
    toolName VARCHAR(50) NOT NULL,
    maintenance boolean NOT NULL DEFAULT '0',
    priceFirst int NOT NULL,
    priceAfter int NOT NULL,
    toolCategory VARCHAR(50) NOT NULL,
    certificateID int NOT NULL,
    description VARCHAR(2000) DEFAULT 'No description',
    picturePath varchar(50) DEFAULT 'amv.png',
    PRIMARY KEY (toolID),
    FOREIGN KEY (certificateID) REFERENCES ToolCertificate(certificateID),
    CHECK(toolCategory in
          ('Various_Tools', 'Nailguns', 'Woodcutting', 'Car_Trailers', 'Large_Equipment'))
);

CREATE OR REPLACE TABLE AMVUser (
    userID INT NOT NULL auto_increment unique,
    email VARCHAR(50) NOT NULL unique,
    passwordHash VARCHAR(250) NOT NULL,
    firstName VARCHAR(50),
    lastName VARCHAR(50) NOT NULL,
    phoneNumber VARCHAR(16),
    unionMember boolean NOT NULL,
    userAdmin boolean NOT NULL,
    PRIMARY KEY (userID)
);

CREATE OR REPLACE TABLE Booking (
    orderID int NOT NULL auto_increment unique,
    userID int,
    toolID int,
    startDate date NOT NULL,
    endDate date NOT NULL,
    returnDate date,
    totalPrice int NOT NULL,
    PRIMARY KEY (orderID),
    FOREIGN KEY (userID) REFERENCES AMVUser(userID) on delete set null,
    FOREIGN KEY (toolID) REFERENCES Tool(toolID) on delete set null
);

CREATE OR REPLACE TABLE UsersCertificate (
    userID int NOT NULL,
    certificateID int NOT NULL,
    accomplishDate date NOT NULL,
    Primary key (userID, certificateID, accomplishDate),
    FOREIGN KEY (userID) REFERENCES AMVUser(userID) on delete cascade ,
    FOREIGN KEY (certificateID) REFERENCES ToolCertificate(certificateID) on delete cascade
);

-- now that the database is initialised, this will start loading in the data
-- First up is the tool data. (totally not just a reformatted version of what marius had done)
INSERT INTO ToolCertificate(certificateName)
VALUES ('none'),
       ('Aerial_Work_Platform');

INSERT INTO Tool(toolName, picturePath, pricefirst, priceAfter, toolCategory, certificateID, maintenance, description)
VALUES ('Orbital_Sander', 'Eksentersliper 230V.PNG', 0, 20, 'Various_Tools', '1', '0', default),
       ('Belt_Sander', 'Bandsliper 230v.PNG', 0, 20, 'Various_Tools', '1', '0', default),
       ('Hand_Plane', default, 0, 20, 'Various_Tools', '1', '0', default),
       ('Miter_Saw', 'Gjære Kombisag.PNG', 0, 20, 'Various_Tools', '1', '0', default),
       ('Grass_Trimmer','Kantklipper Bensin.PNG', 0, 20, 'Various_Tools', '1', '0', default),
       ('Hammer_Drill','Meiselmaskin 230v.PNG', 0, 20, 'Various_Tools', '1', '0', default),
       ('String_Trimmer',default, 0, 20, 'Various_Tools', '1', '0', 'Bruker oljeblandet bensin. Bruk kun ferdigblandet 2-takt alkylatbensin (2%). Dette er oljeblandet spesialbensinen med lang holdbarhet som kan kjøpes på bl.a. Felleskjøpet, Biltema og Jula.'),
       ('Air_Compressor',default, 0, 20, 'Various_Tools', '1', '0', '• Maks. trykk: 10 bar. Avgitt luftmengde: 255 l/min. Effekt: 1,5 kW.'),
       ('Car_Diagnosis_Tools','Bildiagnose.PNG', 0, 50, 'Various_Tools', '1', '0', default),
       ('Vibrating_Plate','Hoppetusse bensin.PNG', 0, 50, 'Various_Tools', '1', '0', 'Vekt: 86 kg. Bruker ren bensin minimum oktantall 95. Motoroljenivå sjekkes før og etter bruk. Oljetype: Shell Ultra Ect 5W-30 (AMV nr. 0095-0069).'),
       ('Tile Cutter_Ceramic','Flisekutter keramikk.PNG', 0, 20, 'Various_Tools', '1', '0', default),
       ('Automatic_Screwer',default, 0, 20, 'Various_Tools', '1', '0', default),
       ('Motorized_Wheelbarrow','Motorisert trillebår.PNG', 0, 50, 'Various_Tools', '1', '0', 'Brukermanual er også lagret i beholder på selve utstyret. Viktig å lese dette før bruk. Bruk kun ren blyfri 95 oktan bensin. Sjekk alltid motoroljenivå før oppstart. Sørg for å få en rask opplæring i bruk av dette utstyret før første gangs bruk.'),
       ('Nailgun_Pressurized_Large',default, 0, 20, 'Nailguns', '1', '0', default),
       ('Nailgun_Pressurized_Small','Spikerpistol liten luft.PNG', 0, 20, 'Nailguns', '1', '0', default),
       ('Nailgun_Milwaukee_Large','Spikerpistol Milwaukee spiker stor.PNG', 0, 20, 'Nailguns', '1', '0', default),
       ('Nailgun_Milwaukee_Medium','Spikerpistol Milwaukee krampe mellom.PNG', 0, 20, 'Nailguns', '1', '0', default),
       ('Nailgun_Milwaukee_Small','Spikerpistol Milwaukee Dykk liten.PNG', 0, 20, 'Nailguns', '1', '0', default),
       ('Wood_Splitter','Vedkløyver bensin.PNG', 0, 50, 'Woodcutting', '1', '0', 'Bruker ren bensin, minimum oktantall 95. Motoroljenivå sjekkes før og etter bruk. Oljetype: Shell Ultra Ect 5W-30 (AMV nr. 0095-0069). Hydraulikkoljenivå sjekkes før og etter bruk. Oljetype: Shell Tellus S2VX 46 (AMV nr. 0095- 0006). Kløyveren har ikke fjæring og tyngdepunktet er forholdsvis høyt så det må utvises  forsiktighet med sleping etter kjøretøy. Dvs. tilpass farten etter forholdene.'),
       ('Tile_Cutter_Wood','Flisekutter Trevirke.PNG', 0, 50, 'Woodcutting', '1', '0', 'Brukermanual er lagret i beholder på selve utstyret. Viktig å lese dette før bruk! Bruk kun ren blyfri 95 oktan bensin. Sjekk alltid motoroljenivå før oppstart. Sørg for å få en rask opplæring i bruk av dette utstyret før første gangs bruk.'),
       ('Trailer_Buggy','Tilhenger liten.PNG', 0, 50, 'Car_Trailers', '1', '0', 'Hentes og leveres ved gassbu/kaldtlager. Husk å ta med vognkort og el.kabel. Hentes i verktøy- eller lagerluke. Pass også på at ikke el. kabel sleper i bakken slik at den blir ødelagt. Normalt bør ikke parkeringsbrems settes på ved lagring da dette ofte fører til at bremser  «henger seg» og blir vanskelige å løsne. Det er leiers ansvar å sørge for at en ikke trekker tilhenger som er tyngre enn det førerkort  og bil tillater. Skaphenger er tenkt brukt til frakt av litt «finere ting» som ikke tåler vann og skitt. Det er derfor viktig at denne tilhengeren ikke brukes til frakt av grus, stein eller andre veldig  skitne gjenstander. Boggihenger har 13-polet el.kontakt. Har du ikke rett kontakt på bilen kan overgang/adapter kjøpes på bl.a. Biltema, Jula og de  fleste bensinstasjoner.'),
       ('Trailer_Small','Tilhenger liten.PNG', 0, 50, 'Car_Trailers', '1', '0', 'Hentes og leveres ved gassbu/kaldtlager. Husk å ta med vognkort og el.kabel. Hentes i verktøy- eller lagerluke. Pass også på at ikke el. kabel sleper i bakken slik at den blir ødelagt. Normalt bør ikke parkeringsbrems settes på ved lagring da dette ofte fører til at bremser  «henger seg» og blir vanskelige å løsne. Det er leiers ansvar å sørge for at en ikke trekker tilhenger som er tyngre enn det førerkort  og bil tillater. Skaphenger er tenkt brukt til frakt av litt «finere ting» som ikke tåler vann og skitt. Det er derfor viktig at denne tilhengeren ikke brukes til frakt av grus, stein eller andre veldig  skitne gjenstander. Liten henger har 7-polet el.kontakt. Har du ikke rett kontakt på bilen kan overgang/adapter kjøpes på bl.a. Biltema, Jula og de  fleste bensinstasjoner.'),
       ('Trailer_Cargo',default, 0, 50, 'Car_Trailers', '1', '0', 'Hentes og leveres ved gassbu/kaldtlager. Husk å ta med vognkort og el.kabel. Hentes i verktøy- eller lagerluke. Pass også på at ikke el. kabel sleper i bakken slik at den blir ødelagt. Normalt bør ikke parkeringsbrems settes på ved lagring da dette ofte fører til at bremser  «henger seg» og blir vanskelige å løsne. Det er leiers ansvar å sørge for at en ikke trekker tilhenger som er tyngre enn det førerkort  og bil tillater. Skaphenger er tenkt brukt til frakt av litt «finere ting» som ikke tåler vann og skitt. Det er derfor viktig at denne tilhengeren ikke brukes til frakt av grus, stein eller andre veldig  skitne gjenstander. Skaphenger har 13-polet el.kontakt. Har du ikke rett kontakt på bilen kan overgang/adapter kjøpes på bl.a. Biltema, Jula og de  fleste bensinstasjoner.'),
       ('Aerial_Work_Platform','Personløft Niftylift.PNG', 100, 100, 'Large_Equipment', '2', '0', 'Dette utstyret kan kun benyttes av personer som har hatt dokumentert sikkerhetsopplæring iht. Forskrift om utførelse av arbeid §10-1 og 10-2. En slik opplæring består av teoretisk og  praktisk opplæring som gir kunnskap om oppbygging, betjening, bruksegenskaper og  bruksområde samt vedlikehold og kontroll. Kursbevis utstedes til de som har gjennomført dette kurset. Maks løftekapasitet (SWL): 200kg inkl. 2 personer. Maks hastighet ved kjøring langs vei er 72 km/t, men tilpass alltid hastighet etter  veiforhold. Husk også å feste transportlås (eksenterstrammer) før transport, samt å frigjøre denne før bruk. Liften er ikke registrert og det er heller ikke nødvendig, men det er påbudt med lys. Siden  lysbøyle bak på liften er demonterbar er det viktig at bruker sjekker at denne er montert og virker. Det fins en egen manual kalt «Brukerens sikkerhetsveiledning» og «Brukermanual» som skal leses før utstyret tas i bruk. Disse skal alltid oppbevares i plastlomme i arbeidskorg. I samme plastlomme er det også en tegning som viser liftens dekningsareal/rekkevidde. Ved arbeid som kan innebære en del søl f.eks. malerarbeid er det brukers ansvar å dekke til  nødvendige deler av utstyret med plast. Dette vil kunne spare en for mye arbeid i ettertid. Husk å ta med nøkkel ved leie, samt å fjerne denne når utstyret ikke er i bruk. Maks. totalvekt på tilhengerlift er 1.432kg. Det er leiers ansvar å sørge for at en ikke trekker tilhenger som er tyngre enn det førerkort og bil tillater. Siden liften er relativt kostbar er det tegnet ansvar- og kaskoforsikring på denne. Skulle liften bli utsatt for skade som skyldes feil eller uvøren bruk, kan bruker bli erstatningspliktig. Erstatningsplikten vil kunne beløpe seg til egenandelen som for tiden er kr. 6000,-'),
       ('Power_Supply','Strømaggregat 3,7kw.PNG', 0, 50, 'Large_Equipment', '1', '0', ' Bruker avgiftsfri diesel. Motoroljenivå sjekkes før og etter bruk. Oljetype: Shell Ultra Ect 5W-30 (AMV nr. 0095-0069)');

INSERT INTO AMVUser(passwordHash, email, phonenumber, firstname, lastname, unionmember, userAdmin)
VALUES ('1f40fc92da241694750979ee6cf582f2d5d7d28e18335de05abc54d0560e0f5302860c652bf08d560252aa5e74210546f369fbbbce8c12cfc7957b2652fe9a75', 'joachimn@uia.no', null, 'Joachim', 'Nilsvik','0', '1'),
       ('1f40fc92da241694750979ee6cf582f2d5d7d28e18335de05abc54d0560e0f5302860c652bf08d560252aa5e74210546f369fbbbce8c12cfc7957b2652fe9a75', 'dilans@uia.no', null, 'Dilan', 'Shwane','1', '1'),
       ('1f40fc92da241694750979ee6cf582f2d5d7d28e18335de05abc54d0560e0f5302860c652bf08d560252aa5e74210546f369fbbbce8c12cfc7957b2652fe9a75', 'mariusbn@uia.no', null, 'Marius Berg', 'Nordbø', '0', '1'),
       ('1f40fc92da241694750979ee6cf582f2d5d7d28e18335de05abc54d0560e0f5302860c652bf08d560252aa5e74210546f369fbbbce8c12cfc7957b2652fe9a75', 'roelandc@uia.no', null, 'Roeland', 'Camps', '1', '1'),
       ('1f40fc92da241694750979ee6cf582f2d5d7d28e18335de05abc54d0560e0f5302860c652bf08d560252aa5e74210546f369fbbbce8c12cfc7957b2652fe9a75', 'paulfe@uia.no', null, 'Paul', 'Feichtenschlager', '0', '1'),
       ('1f40fc92da241694750979ee6cf582f2d5d7d28e18335de05abc54d0560e0f5302860c652bf08d560252aa5e74210546f369fbbbce8c12cfc7957b2652fe9a75', 'johannao@uia.no', null, 'Johanna', 'Ockenfels', '1', '1'),
       ('1f40fc92da241694750979ee6cf582f2d5d7d28e18335de05abc54d0560e0f5302860c652bf08d560252aa5e74210546f369fbbbce8c12cfc7957b2652fe9a75', 'idkwhatbriansmailsis@uia.no', null, 'Brian "Cheuk Long"', 'Chan','0', '1');

INSERT INTO UsersCertificate(userID, certificateID, accomplishDate)
VALUES ('1','1','2021-10-10'),
       ('2','2','2021-10-11'),
       ('3','1','2021-10-12'),
       ('4','2','2021-10-13'),
       ('5','1','2021-10-14'),
       ('6','2','2021-10-15'),
       ('7','1','2021-10-16');

INSERT INTO Booking (startDate, endDate, totalPrice, userID, toolID,returnDate)
VALUES ('2021-01-20', '2021-01-24', 80, 1, 2, '2021-01-24'),
       ('2021-01-25', '2021-01-26', 20, 4, 5, '2021-01-26'),
       ('2020-05-10', '2020-05-15', 250, 3, 10, '2020-05-13'),
       ('2021-09-05', '2021-09-07', 40, 6, 4, '2021-09-07'),
       ('2021-08-11', '2021-08-13', 100, 1, 13, '2021-08-13'),
       ('2021-10-09', '2021-10-14', 100, 5, 18, '2021-10-15'),
       ('2020-07-14', '2020-07-18', 200, 7, 20, '2020-07-17' ),
       ('2020-12-01', '2020-12-01', 0, 2, 9, '2020-12-01'),
       ('2021-11-22', '2021-11-25', 300, 5, 24, '2021-11-25'),
       ('2021-03-17', '2021-03-21', 80, 3, 6, '2021-03-21'),
       ('2021-10-10', '2021-10-22', 80, 3, 6, null),
       ('2021-11-18', '2021-11-20', 80, 2, 3, null);

-- Listing the 5 first rows of the 5 most important tables (your judgement), sorted.
select *
from ToolCertificate order by certificateID
limit 5;

select *
from Tool order by toolID
limit 5;

select *
from AMVUser order by userID
limit 5;

select *
from UsersCertificate order by userID
limit 5;

select *
from Booking order by orderID
limit 5;

-- List all equipment in the system with their type
Select toolName, toolCategory
from Tool;

-- List all the available (at the moment – not already borrowed) equipment
select toolName, t.toolID
from Tool t
where t.toolID not in
(select t.toolID
from Tool t, Booking b
where t.toolID=b.toolID
  and b.startDate<=current_date()
  and b.returnDate is null);

-- List the names and number of borrows of the three users with most equipment borrowed, sorted by number of borrows
select firstName, lastName, count(*) as 'number of borrows'
from AMVUser as u, Booking as b
where u.userID = b.userID
group by b.userID
order by `number of borrows` desc
limit 3;

-- List all the equipment borrowed by the user with the highest number of equipment borrowed, sorted by date/time
select toolName, startDate
from Tool as t, Booking as b
where t.toolID = b.toolID and b.userID = (select userID from Booking as b group by b.userID order by count(*) desc limit 1)
Order by startDate DESC;

-- List all equipment that is borrowed at the moment
select t.toolID, t.toolName
from Tool t, Booking b
where t.toolID=b.toolID
  and b.returnDate IS NULL;

-- List all overdue equipment with their borrowers
select toolName, u.userID, firstName, lastName
from AMVUser u, Tool t, Booking b
where t.toolID=b.toolID and b.userID = u.userID
  and b.endDate<current_date()
  and b.returnDate is null;


