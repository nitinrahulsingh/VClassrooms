package com.vclassrooms.Entity;

import java.util.List;

/**
 * Created by Rahul on 12,July,2020
 */
public class ExamTimeTableResponse {
    private int StatusCode;

    private String StatusMessaage;

    private Data data;

    public void setStatusCode(int StatusCode) {
        this.StatusCode = StatusCode;
    }

    public int getStatusCode() {
        return this.StatusCode;
    }

    public void setStatusMessaage(String StatusMessaage) {
        this.StatusMessaage = StatusMessaage;
    }

    public String getStatusMessaage() {
        return this.StatusMessaage;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return this.data;
    }

    public class Timetable {
        private int Exam_Id;

        private String ExamType;

        private String Sem_Name;

        private String Standard_name;

        private String SubjectName;

        private String Day_Name;

        private String ExamDate;

        private String StartTime;

        private String EndTime;

        private int TotalMark;

        private int PassingMark;

        public void setExam_Id(int Exam_Id) {
            this.Exam_Id = Exam_Id;
        }

        public int getExam_Id() {
            return this.Exam_Id;
        }

        public void setExamType(String ExamType) {
            this.ExamType = ExamType;
        }

        public String getExamType() {
            return this.ExamType;
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

        public void setExamDate(String ExamDate) {
            this.ExamDate = ExamDate;
        }

        public String getExamDate() {
            return this.ExamDate;
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

        public void setTotalMark(int TotalMark) {
            this.TotalMark = TotalMark;
        }

        public int getTotalMark() {
            return this.TotalMark;
        }

        public void setPassingMark(int PassingMark) {
            this.PassingMark = PassingMark;
        }

        public int getPassingMark() {
            return this.PassingMark;
        }
    }

    public class Data {
        private List<Timetable> timetable;

        public void setTimetable(List<Timetable> timetable) {
            this.timetable = timetable;
        }

        public List<Timetable> getTimetable() {
            return this.timetable;
        }
    }


}

