package com.example.schedulingApp.ViewModel;

public enum DogWeight {

    SMALL{
        @Override
        public String toString() {
            return "Small: Up To 30lbs";
        }
    },
    MEDIUM{
        @Override
        public String toString() {
            return "Medium: Up To 60lbs";
        }
    },
    LARGE{
        @Override
        public String toString() {
            return "Large: Up To 90lbs";
        }
    },
    XLARGE{
        @Override
        public String toString() {
            return "XL: Up To 120lbs";
        }
    },
    XXLARGE{
        @Override
        public String toString() {
            return "XXL: Over 120lbs";
        }
    }
}
