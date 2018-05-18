package com.camsoulsby.oldmate;

public class Mate {
        private long id;
        private String name;
        private String notes;


        public Mate() {
        }

        public Mate(String name, String notes) {
            this.name = name;
            this.notes = notes;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }


    }

