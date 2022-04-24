package com.example.schedulingApp.ViewModel;

public enum EmployeePosition {
    GROOMER{
        @Override
        public String toString() {
            return "Pet Groomer";
        }
    },
    BATHER{
        @Override
        public String toString() {
            return "Bather";
        }
    },
    MANAGER{
        @Override
        public String toString() {
            return "Manager";
        }
    }
}
