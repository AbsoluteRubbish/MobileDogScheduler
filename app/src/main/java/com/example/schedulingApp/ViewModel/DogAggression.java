package com.example.schedulingApp.ViewModel;

public enum DogAggression {

    YES{
        @Override
        public String toString() {
            return "Yes";
        }
    },
    NO{
        @Override
        public String toString() {
            return "No";
        }
    },
    UNKNOWN{
        @Override
        public String toString() {
            return "Unknown";
        }
    },
    NAILS{
        @Override
        public String toString() {
            return "Only With Nails";
        }
    },
    ONLY_MEN{
        @Override
        public String toString() {
            return "Only With Men";
        }
    },
    ONLY_WOMEN{
        @Override
        public String toString() {
            return "Only With Women";
        }
    }
}
