package com.vclassrooms.Entity;

/**
 * Created by Rahul on 12,July,2020
 */
public class Timetable {
        private int Period_Id;

        private String SessionName;

        private String Sem_Name;

        private String Standard_name;

        private String Division_Name;

        private String SubjectName;

        private String Day_Name;

        private String First_Name;

        private String StartTime;

        private String EndTime;

        public void setPeriod_Id(int Period_Id) {
            this.Period_Id = Period_Id;
        }

        public int getPeriod_Id() {
            return this.Period_Id;
        }

        public void setSessionName(String SessionName) {
            this.SessionName = SessionName;
        }

        public String getSessionName() {
            return this.SessionName;
        }

        public void setSem_Name(String Sem_Name) {
            this.Sem_Name = Sem_Name;
        }

        public String getSem_Name() {
            return this.Sem_Name;
        }

        public void setStandard_name(String Standard_name) {
            this.Standard_name = Standard_name;
        }

        public String getStandard_name() {
            return this.Standard_name;
        }

        public void setDivision_Name(String Division_Name) {
            this.Division_Name = Division_Name;
        }

        public String getDivision_Name() {
            return this.Division_Name;
        }

        public void setSubjectName(String SubjectName) {
            this.SubjectName = SubjectName;
        }

        public String getSubjectName() {
            return this.SubjectName;
        }

        public void setDay_Name(String Day_Name) {
            this.Day_Name = Day_Name;
        }

        public String getDay_Name() {
            return this.Day_Name;
        }

        public void setFirst_Name(String First_Name) {
            this.First_Name = First_Name;
        }

        public String getFirst_Name() {
            return this.First_Name;
        }

        public void setStartTime(String StartTime) {
            this.StartTime = StartTime;
        }

        public String getStartTime() {
            return this.StartTime;
        }

        public void setEndTime(String EndTime) {
            this.EndTime = EndTime;
        }

        public String getEndTime() {
            return this.EndTime;
        }

    public Timetable(String subjectName, String day_Name, String first_Name, String startTime, String endTime) {
        SubjectName = subjectName;
        Day_Name = day_Name;
        First_Name = first_Name;
        StartTime = startTime;
        EndTime = endTime;
    }
}
