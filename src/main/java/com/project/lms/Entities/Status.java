package com.project.lms.Entities;


public class Status {

    public boolean requested;
    public boolean returned;
    public boolean approved;
    public boolean cancelled;

    public boolean isRequested() {
        return requested;
    }

    public void setRequested(boolean requested) {
        this.requested = requested;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public String toString() {
        return "Status{" +
                "requested=" + requested +
                ", returned=" + returned +
                ", approved=" + approved +
                ", cancelled=" + cancelled +
                '}';
    }
}
