package com.orangehrmlive.demo.testdata;

import java.util.UUID;

public class JobTitle {
    private String title;
    private String description;
    private String specification;
    private String note;

    private JobTitle(String title, String description, String specification, String note) {
        this.title = title;
        this.description = description;
        this.specification = specification;
        this.note = note;
    }

    public static JobTitle create(String title, String description, String specification, String note) {
        return new JobTitle(title, description, specification, note);
    }

    public static JobTitle generate() {
        String uuid = UUID.randomUUID().toString();
        return create(
                "Job " + uuid,
                "Description for job " + uuid,
                "",
                "Note for job " + uuid
        );
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSpecification() {
        return specification;
    }

    public String getNote() {
        return note;
    }

    public JobTitle withSpecification(String specification) {
        this.specification = specification;
        return this;
    }

    public JobTitle withDescription(String description) {
        this.description = description;
        return this;
    }

    public JobTitle withNote(String note) {
        this.note = note;
        return this;
    }

    @Override
    public String toString() {
        return "JobTitle[" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", specification='" + specification + '\'' +
                ", note='" + note + '\'' +
                ']';
    }


    public JobTitle copy() {
        return create(this.title, this.description, this.specification, this.note);
    }
}
