package com.example.schedulingApp.ViewModel;

public enum Products {
    BATH {
        @Override
        public String toString() {
            return "Basic Bath";
        }
    },
    BATH_DELUXE{
        @Override
        public String toString() {
            return "Deluxe Bath";
        }
    },
    GROOMING{
        @Override
        public String toString() {
            return "Basic Grooming";
        }
    },
    GROOMING_DELUXE{
        @Override
        public String toString() {
            return "Grooming Deluxe";
        }
    },
    FULL_PACKAGE{
        @Override
        public String toString() {
            return "Full Package";
        }
    },
}
