drop database NutritionClinicDB;
create database NutritionClinicDB;
use NutritionClinicDB;
CREATE TABLE `AppointmentSatus` (
  `StatusID` INT AUTO_INCREMENT,
  `Status` VARCHAR(64),
  PRIMARY KEY (`StatusID`)
);

CREATE TABLE `NutritionistStatus` (
  `StatusID` INT AUTO_INCREMENT,
  `Status` VARCHAR(128),
  PRIMARY KEY (`StatusID`)
);

CREATE TABLE `Disability` (
  `DisabilityID` INT AUTO_INCREMENT,
  `Name` VARCHAR(256),
  `Discription` VARCHAR(512),
  PRIMARY KEY (`DisabilityID`)
);

CREATE TABLE `Supplement` (
  `ProductID` INT AUTO_INCREMENT,
  `SerialNo` VARCHAR(20),
  `Name` VARCHAR(256),
  `Manufacturer` VARCHAR(256),
  `Price` DECIMAL(6,2),
  `Quantity` INT,
  `ExpireDate` DATE,
  `Form` VARCHAR(64),
  `SideEffects` VARCHAR(512),
  `DosageInfo` VARCHAR(512),
  PRIMARY KEY (`ProductID`),
  KEY `Key` (`SerialNo`)
);

CREATE TABLE `PatientStatus` (
  `StatusID` INT AUTO_INCREMENT,
  `Status` VARCHAR(128),
  PRIMARY KEY (`StatusID`)
);

CREATE TABLE `Patient` (
  `PatientID` INT AUTO_INCREMENT,
  `FirstName` VARCHAR(128),
  `LastName` VARCHAR(128),
  `BirthDate` DATE,
  `Weight` DECIMAL(5,2),
  `Height` DECIMAL(5,2),
  `Gender` VARCHAR(32),
  `PhoneNumber` VARCHAR(20),
  `Email` VARCHAR(256),
  `Address` VARCHAR(256),
  `StatusID` INT,
  PRIMARY KEY (`PatientID`),
  FOREIGN KEY (`StatusID`) REFERENCES `PatientStatus`(`StatusID`)
);

CREATE TABLE `Allergy` (
  `AllergyID` INT AUTO_INCREMENT,
  `Name` VARCHAR(256),
  `Discription` VARCHAR(512),
  PRIMARY KEY (`AllergyID`)
);

DROP TABLE IF EXISTS `SupplementOrder`;
CREATE TABLE `SupplementOrder` (
  `OrderID` INT AUTO_INCREMENT,
  `PatientID` INT,
  `Date` DATE,
  `SpecialDiscountPercent` DECIMAL(4,2),
  PRIMARY KEY (`OrderID`),
  FOREIGN KEY (`PatientID`) REFERENCES `Patient`(`PatientID`)
);

DROP TABLE IF EXISTS `Nutritionist`;
CREATE TABLE `Nutritionist` (
  `NutritionistID` INT AUTO_INCREMENT,
  `SSN` INT,
  `IsManager` BOOLEAN,
  `FirstName` VARCHAR(128),
  `LastName` VARCHAR(128),
  `BirthDate` DATE,
  `AnnualSalary` DECIMAL,
  `Gender` VARCHAR(32),
  `PhoneNumber` VARCHAR(20),
  `Email` VARCHAR(255),
  `Address` VARCHAR(255),
  `StatusID` INT,
  PRIMARY KEY (`NutritionistID`),
  FOREIGN KEY (`StatusID`) REFERENCES `NutritionistStatus`(`StatusID`)
);

CREATE TABLE `SupplementOrderItem` (
  `OrderItemID` INT AUTO_INCREMENT,
  `OrderID` INT,
  `ProductID` INT,
  `Quantity` INT,
  `DiscountPercent` DECIMAL(4,2),
  PRIMARY KEY (`OrderItemID`),
  FOREIGN KEY (`ProductID`) REFERENCES `Supplement`(`ProductID`),
  FOREIGN KEY (`OrderID`) REFERENCES `SupplementOrder`(`OrderID`)
);

CREATE TABLE `TerminalIllenss` (
  `IllnessID` INT AUTO_INCREMENT,
  `Name` VARCHAR(256),
  `Discription` VARCHAR(512),
  PRIMARY KEY (`IllnessID`)
);

CREATE TABLE `PatientDisability` (
  `PatientID` INT,
  `DisabilityID` INT,
  `Date` DATE,
  PRIMARY KEY (`PatientID`, `DisabilityID`),
  FOREIGN KEY (`DisabilityID`) REFERENCES `Disability`(`DisabilityID`),
  FOREIGN KEY (`PatientID`) REFERENCES `Patient`(`PatientID`)
);

CREATE TABLE `PatientIllness` (
  `PatientID` INT,
  `IllnessID` INT,
  `Date` DATE,
  PRIMARY KEY (`PatientID`, `IllnessID`),
  FOREIGN KEY (`IllnessID`) REFERENCES `TerminalIllenss`(`IllnessID`),
  FOREIGN KEY (`PatientID`) REFERENCES `Patient`(`PatientID`)
);

CREATE TABLE `WeeklyMealPlan` (
  `PlanID` INT AUTO_INCREMENT,
  `PatientID` INT,
  `NutritionistID` INT,
  `WeekStartDate` DATE,
  PRIMARY KEY (`PlanID`),
  FOREIGN KEY (`PatientID`) REFERENCES `Patient`(`PatientID`),
  FOREIGN KEY (`NutritionistID`) REFERENCES `Nutritionist`(`NutritionistID`)
);

CREATE TABLE `DayMealPlan` (
  `DayPlanID` INT AUTO_INCREMENT,
  `PlanID` INT,
  `DayOfWeek` VARCHAR(20),
  `Breakfast` VARCHAR(512),
  `Dinner` VARCHAR(512),
  `Supper` VARCHAR(512),
  `Snack` VARCHAR(512),
  PRIMARY KEY (`DayPlanID`),
  FOREIGN KEY (`PlanID`) REFERENCES `WeeklyMealPlan`(`PlanID`)
);

DROP TABLE IF EXISTS `PatientAllergy`;
CREATE TABLE `PatientAllergy` (
  `PatientID` INT,
  `AllergyID` INT,
  `Date` DATE,
  PRIMARY KEY (`PatientID`, `AllergyID`),
  FOREIGN KEY (`AllergyID`) REFERENCES `Allergy`(`AllergyID`),
  FOREIGN KEY (`PatientID`) REFERENCES `Patient`(`PatientID`)
);

DROP TABLE IF EXISTS `Appointment`;
CREATE TABLE `Appointment` (
  `AppointmentID` INT AUTO_INCREMENT,
  `PatientID` INT,
  `NutritionistID` INT,
  `PDate` DATE,
  `PTime` TIME,
  `Cost` DECIMAL(6,2),
  `PaymentDate` DATE NOT NULL,
  `BriefDescription` VARCHAR(512),
  `StatusID` INT,
  PRIMARY KEY (`AppointmentID`,`NutritionistID`,`PDate`,`PTime`),
  FOREIGN KEY (`StatusID`) REFERENCES `AppointmentSatus`(`StatusID`),
  FOREIGN KEY (`NutritionistID`) REFERENCES `Nutritionist`(`NutritionistID`),
  FOREIGN KEY (`PatientID`) REFERENCES `Patient`(`PatientID`)
);


-- SELECT COUNT(`AppointmentID`)FROM `Appointment`;

-- select * from Appointment WHERE PDate >='2023-10-02';
-- select * from Appointment WHERE PTime ='14:30:00';
-- select a.AppointmentID,a.PatientID,a.NutritionistID,
-- a.PDate,a.PTime,a.Cost,a.PaymentDate,a.BriefDescription,
-- a.StatusID from appointment a;
/*
select sum(cost) as totalcost
from appointment;

select n.nutritionistID, n.FirstName,  n.LastName,
count(a.appointmentID) AS TotalAppointments
from  nutritionist n 
inner join appointment a ON n.nutritionistID = a.nutritionistID
group by  n.NutritionistID, n.FirstName, n.LastName
order by TotalAppointments DESC LIMIT 1;

select p.PatientID, p.FirstName,  p.LastName,sum(a.cost) AS Paid,
count(a.appointmentID) AS TotalAppointments
from  patient p 
inner join appointment a ON p.PatientID = a.PatientID 
group by  p.PatientID, p.FirstName, p.LastName
order by TotalAppointments DESC LIMIT 1;
*/
/*insert into `appointment` (`patientID`, `nutritionistID`, `pDate`, `pTime`, `cost`, `paymentDate`, `briefDescription`, `statusID`) values (2, 1, '2023-12-04', '02:10:00', 60.0, '2024-01-02', 'test', 1);
 INSERT INTO `Appointment` (`PatientID`, `NutritionistID`, `PDate`, `PTime`, `Cost`, `PaymentDate`, `BriefDescription`, `StatusID`)
VALUES (10, 1, '2024-01-01', '05:07:00', 50.00, '2024-01-03', 'data', 2);
INSERT INTO `Appointment` (`PatientID`, `NutritionistID`, `PDate`, `PTime`, `Cost`, `PaymentDate`, `BriefDescription`, `StatusID`)VALUES (10, 3, '2024-01-10', '10:10:00', 60.3, '2024-01-11', 'a check up', 2);
select a.AppointmentID,a.PatientID,a.NutritionistID,a.PTime from appointment a, appointmentSatus ap where a.PDate >= curdate() and a.StatusID = ap.StatusID and ap.StatusID = 3;
