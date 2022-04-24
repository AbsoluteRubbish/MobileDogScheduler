package com.example.schedulingApp.ViewModel;

public enum ProductAddOns {
    NAIL_TRIMMING{
        @Override
        public String toString() {
            return "Nail Trimming";
        }
    },
    ANAL_GLANDS{
        @Override
        public String toString() {
            return "Anal Glands Expressed";
        }
    },
    EAR_HAIR_REMOVAL{
        @Override
        public String toString() { return "Ear Hair Removal"; }
    },
    NAIL_AND_GLANDS{
        @Override
        public String toString() {return "Nails & Anal Glands";}
    },
    NAILS_AND_HAIR{
        @Override
        public String toString() {return "Nails & Hair";}
    },
    HAIR_AND_GLANDS{
        @Override
        public String toString() { return "Hair & Anal Glands"; }
    },
    ALL_Three{
        @Override
        public String toString() { return "ALL Three"; }
    },
    NONE{
        @Override
        public String toString() { return "None"; }
    }

}
