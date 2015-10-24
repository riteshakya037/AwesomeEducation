package com.awesome_folks.quidity.Parse;

import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by ritesh on 10/24/15.
 */
public class NoteRow {
    String Author;
    String Description;
    String Link;
    ParseFile Note;
    String Subscription;
    String Title;
    String CreatedAt;
    String ObjectId;
    String UpdatedAt;

    public String getAuthor() {
        return Author;
    }

    public String getDescription() {
        return Description;
    }

    public String getLink() {
        return Link;
    }

    public ParseFile getNote() {
        return Note;
    }

    public String getSubscription() {
        return Subscription;
    }

    public String getTitle() {
        return Title;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public String getObjectId() {
        return ObjectId;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public NoteRow(ParseObject post) {
        Author = post.getString(String.valueOf(NoteTable.Author.getFieldName()));
        Description = post.getString(String.valueOf(NoteTable.Description.getFieldName()));
        Link = post.getString(String.valueOf(NoteTable.Link.getFieldName()));
        Note = post.getParseFile(String.valueOf(NoteTable.Note.getFieldName()));
        Subscription = post.getString(String.valueOf(NoteTable.Subscription.getFieldName()));
        Title = post.getString(String.valueOf(NoteTable.Title.getFieldName()));
        CreatedAt = post.getString(String.valueOf(NoteTable.CreatedAt.getFieldName()));
        ObjectId = post.getString(String.valueOf(NoteTable.ObjectId.getFieldName()));
        UpdatedAt = post.getString(String.valueOf(NoteTable.UpdatedAt.getFieldName()));
    }
}
