package com.awesome_folks.quidity.Parse;

/**
 * Created by ritesh on 10/24/15.
 */
public enum NoteTable {
    Author("Author"),
    Description("Description"),
    Link("Link"),
    Note("Note"),
    Subscription("Subscription"),
    Title("Title"),
    CreatedAt("createdAt"),
    ObjectId("objectId"),
    UpdatedAt("updatedAt");
    private String fieldName;

    NoteTable(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
