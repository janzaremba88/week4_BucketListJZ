package com.example.bucketlist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "entry")

public class Entry implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "entry title")
    private String entryTitle;

    @ColumnInfo(name = "entry description")
    private String entryDescription;

    @ColumnInfo(name = "entry strikethru")
    private int entryStrikeThru;

    public String getEntryTitle() {
        return entryTitle;
    }

    public void setEntryText(String entryTitle) {
        this.entryTitle = entryTitle;
    }

    public String getEntryDescription() {
        return entryDescription;
    }

    public void setEntryDescription(String entryDescription) {
        this.entryDescription = entryDescription;
    }

    public int getEntryStrikeThru() {
        return entryStrikeThru;
    }

    public void setEntryStrikeThru(int StrikeThru) {
        this.entryStrikeThru = StrikeThru;
    }

    public Entry(int id, String entryTitle, String entryDescription) {
        this.id = id;
        this.entryTitle = entryTitle;
        this.entryDescription = entryDescription;
        this.entryStrikeThru = 0;
    }

    protected Entry(Parcel in) {
        this.id = in.readInt();
        String[] data = new String[2];
        in.readStringArray(data);
        entryTitle = data[0];
        entryDescription = data[1];
        this.entryStrikeThru = in.readInt();
    }

    public static final Creator<Entry> CREATOR = new Creator<Entry>() {
        @Override
        public Entry createFromParcel(Parcel in) {
            return new Entry(in);
        }

        @Override
        public Entry[] newArray(int size) {
            return new Entry[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeStringArray(new String[]{this.entryTitle, this.entryDescription});
        dest.writeInt(this.entryStrikeThru);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}