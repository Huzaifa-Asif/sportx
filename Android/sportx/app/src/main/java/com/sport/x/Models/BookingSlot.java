package com.sport.x.Models;

public class BookingSlot {
    private String start,end;
    private boolean available;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;

    public String getStart() {
        return start;
    }

    public BookingSlot(String start, String end, boolean available,boolean isSelected) {
        this.isSelected=isSelected;
        this.start = start;
        this.end = end;
        this.available = available;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
